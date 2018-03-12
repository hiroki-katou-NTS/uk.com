package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.Value;
@Value
public class AppInfoStatus {

	private List<ApplicationFullOutput> lstAppFull;
	private ApplicationStatus count;
}
