package com.camel.jbpm.camel.integration.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jbpm.JBPMConstants;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		Map<String, Object> params = new HashMap<>();
		params.put("employee", "wbadmin");
		params.put("reason", "Camel asks for it");

		from("timer://timerCount?period=50000").routeId("CamelJbpmRoute").log("Camel Jbpm Route Started!!!!!")
				.setHeader(JBPMConstants.PROCESS_ID, constant("evaluation")).process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						exchange.getIn().setHeader(JBPMConstants.PARAMETERS, params);
					}
				})
				.to("jbpm:http://localhost:8080/kie-server/services/rest/server?userName=wbadmin&password=wbadmin&deploymentId=evaluation");
	}

}
