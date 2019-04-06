package com.bundiconsulting.actuswebapp.controllers;

import com.bundiconsulting.actuswebapp.models.Event;
import com.bundiconsulting.actuswebapp.repositories.EventRepository;
import org.actus.attributes.ContractModel;
import org.actus.attributes.ContractModelProvider;
import org.actus.contracts.Annuity;
import org.actus.contracts.ForeignExchangeOutright;
import org.actus.contracts.LinearAmortizer;
import org.actus.contracts.NegativeAmortizer;
import org.actus.contracts.PrincipalAtMaturity;
import org.actus.contracts.Stock;
import org.actus.contracts.Swap;
import org.actus.events.ContractEvent;
import org.actus.externals.RiskFactorModelProvider;
import org.actus.states.StateSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class EventController {

    @Autowired
    EventRepository eventRepository;

    class MarketModel implements RiskFactorModelProvider {
        public Set<String> keys() {
            Set<String> keys = new HashSet<String>();
            return keys;
        }

        public double stateAt(String id, LocalDateTime time, StateSpace contractStates,
                ContractModelProvider contractAttributes) {
            return 0.0;
        }
    }

    // String -> ArrayList<ContractEvent>
    @RequestMapping(method = RequestMethod.POST, value = "/events")
    @CrossOrigin(origins = "*")
    public List<Event> solveContract(@RequestBody Map<String, Object> json) {

        String ContractType = "";

        Map<String, String> map = new HashMap<String, String>();

        for (Map.Entry<String, Object> entry : json.entrySet()) {

            System.out.println(entry.getKey() + ":" + entry.getValue());

            map.put(entry.getKey(), entry.getValue().toString());

            if (entry.getKey().toString().equals("ContractType")) {
                ContractType = entry.getValue().toString();
            }

        }

        // parse attributes
        ContractModel model = ContractModel.parse(map);

        // define analysis times
        Set<LocalDateTime> analysisTimes = new HashSet<LocalDateTime>();

        // needs to be synced with dates in terms objects
        analysisTimes.add(LocalDateTime.parse("2015-01-01T00:00:00"));

        // define risk factor model
        MarketModel riskFactors = new MarketModel();

        ArrayList<ContractEvent> events = new ArrayList<ContractEvent>();

        switch (ContractType) {
        case "PAM":
            events = PrincipalAtMaturity.lifecycle(analysisTimes, model, riskFactors);
            break;
        case "ANN":
            events = Annuity.lifecycle(analysisTimes, model, riskFactors);
            break;
        case "NAM":
            events = NegativeAmortizer.lifecycle(analysisTimes, model, riskFactors);
            break;
        case "LAM":
            events = LinearAmortizer.lifecycle(analysisTimes, model, riskFactors);
        case "LAX":
            events = LinearAmortizer.lifecycle(analysisTimes, model, riskFactors);
            break;
        case "STK":
            events = Stock.lifecycle(analysisTimes, model, riskFactors);
            break;
        case "SWAPS":
            events = Swap.lifecycle(analysisTimes, model, riskFactors);
            break;
        case "FXOUT":
            events = ForeignExchangeOutright.lifecycle(analysisTimes, model, riskFactors);
            break;

        default:
            System.out.println("no match");
        }

        List<Event> output = events.stream().map(e -> new Event(e)).collect(Collectors.toList());

        return output;

    }

}
