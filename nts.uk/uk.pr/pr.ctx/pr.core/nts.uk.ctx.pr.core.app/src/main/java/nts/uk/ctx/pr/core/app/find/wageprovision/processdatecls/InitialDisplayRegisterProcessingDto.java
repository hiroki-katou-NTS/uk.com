package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class InitialDisplayRegisterProcessingDto {

	private ProcessInformationDto informationDto;

	private SetDaySupportDto setDaySupportDto;

	private CurrProcessDateDto currProcessDateDto;

	private EmpTiedProYearDto empTiedProYearDto;
}
