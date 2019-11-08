package com.redhat.rhba.enablement.prometheus;

import java.math.BigDecimal;

import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.ast.DecisionNode;
import org.kie.dmn.api.core.event.AfterEvaluateDecisionEvent;
import org.kie.dmn.api.core.event.DMNRuntimeEventListener;

import io.prometheus.client.Histogram;

/**
 * DisputeRisksDMNRuntimeEventListener
 */
public class DisputeRisksDMNRuntimeEventListener implements DMNRuntimeEventListener {

    /**
     * Cardholder Risk Rating.
     */
    private static final Histogram crr = Histogram.build().name("cardholder_risk_rating").help("Cardholder Risk Rating")
            .labelNames("decision_namespace", "decision_name").buckets(1, 2, 3, 4, 5).register();

    /**
     * Dispute Risk Rating.
     */
    private static final Histogram drr = Histogram.build().name("dispute_risk_rating").help("Dispute Risk Rating")
            .labelNames("decision_namespace", "decision_name").buckets(1, 2, 3, 4, 5).register();

    public void afterEvaluateDecision(AfterEvaluateDecisionEvent event) {
        DecisionNode decisionNode = event.getDecision();
        String decisionNodeName = decisionNode.getName();
        DMNDecisionResult result = event.getResult().getDecisionResultByName(decisionNodeName);

        double resultAsDouble = 0.0;
        Object resultAsObject = result.getResult();

        switch (decisionNodeName) {
        case "Cardholder Risk Rating":    
            // We only store if we find and Integer.
            if (resultAsObject instanceof BigDecimal) {
                resultAsDouble = ((BigDecimal) resultAsObject).doubleValue();
                crr.labels(decisionNode.getModelName(), decisionNode.getModelNamespace())
                        .observe(resultAsDouble);
            }
            break;
        case "Dispute Risk Rating":
            // We only store if we find and Integer.
            if (resultAsObject instanceof BigDecimal) {
                resultAsDouble = ((BigDecimal) resultAsObject).doubleValue();
                drr.labels(decisionNode.getModelName(), decisionNode.getModelNamespace())
                        .observe(resultAsDouble);
            }
            break;
        default:
            // Not the decision we want to monitor. Discarding.
            break;
        }
    }

}