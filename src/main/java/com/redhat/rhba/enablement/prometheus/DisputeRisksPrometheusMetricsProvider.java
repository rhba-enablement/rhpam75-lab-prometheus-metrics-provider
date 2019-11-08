package com.redhat.rhba.enablement.prometheus;

import org.jbpm.executor.AsynchronousJobListener;
import org.jbpm.services.api.DeploymentEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.dmn.api.core.event.DMNRuntimeEventListener;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.prometheus.PrometheusMetricsProvider;
import org.optaplanner.core.impl.phase.event.PhaseLifecycleListener;

/**
 * DisputeRisksPrometheusMetricsProvider
 */
public class DisputeRisksPrometheusMetricsProvider implements PrometheusMetricsProvider {

    public DMNRuntimeEventListener createDMNRuntimeEventListener(KieContainerInstance kContainer) {
        return new DisputeRisksDMNRuntimeEventListener();
    }

    public AgendaEventListener createAgendaEventListener(String kieSessionId, KieContainerInstance kContainer) {
        return null;
    }

    public PhaseLifecycleListener createPhaseLifecycleListener(String solverId) {
        return null;
    }

    public AsynchronousJobListener createAsynchronousJobListener() {
        return null;
    }

    public DeploymentEventListener createDeploymentEventListener() {
        return null;
    }
}