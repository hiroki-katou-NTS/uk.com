package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateHealthInsuranceAvgearnCommand {
	List<HealthInsuranceAvgearnCommandDto> listHealthInsuranceAvgearn;
}
