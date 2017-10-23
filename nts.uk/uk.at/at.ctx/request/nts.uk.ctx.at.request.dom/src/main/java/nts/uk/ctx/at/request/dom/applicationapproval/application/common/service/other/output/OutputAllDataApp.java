package nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.other.output;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.request.dom.applicationapproval.application.Application;

@Value
public class OutputAllDataApp {
	Optional<Application> application;
	
	List<OutputPhaseFrame> listOutputPhaseFrame;
}
