package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Optional;

import lombok.Value;

@Value
public class OutputGetAllDataApp {

	Optional<ApplicationDto> applicationDto;
	
	List<OutputPhaseAndFrame> listOutputPhaseAndFrame;
	
}
