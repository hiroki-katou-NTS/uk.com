package nts.uk.ctx.at.request.app.find.application.proxy;

import java.util.List;

import lombok.Value;

@Value
public class ProxyParamFind {
	private List<String> employeeIds;
	private Integer applicationType;
}
