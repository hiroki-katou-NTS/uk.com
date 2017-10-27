package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.Application;

@Value
public class OutputAllDataApp {
	Optional<Application> application;
	
	List<OutputPhaseFrame> listOutputPhaseFrame;
}
