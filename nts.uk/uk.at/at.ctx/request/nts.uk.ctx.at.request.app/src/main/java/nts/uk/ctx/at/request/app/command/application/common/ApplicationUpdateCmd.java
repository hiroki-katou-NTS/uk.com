package nts.uk.ctx.at.request.app.command.application.common;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApplicationUpdateCmd {
	
	private String opAppReason;
	
	private Integer opAppStandardReasonCD;
	
	public Application toDomain(ApplicationDto applicationDto) {
		Application application = applicationDto.toDomain();
		application.setOpAppReason(Strings.isBlank(opAppReason) ? Optional.empty() : Optional.of(new AppReason(opAppReason)));
		application.setOpAppStandardReasonCD(opAppStandardReasonCD == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(opAppStandardReasonCD)));;
		return application;
	}
}
