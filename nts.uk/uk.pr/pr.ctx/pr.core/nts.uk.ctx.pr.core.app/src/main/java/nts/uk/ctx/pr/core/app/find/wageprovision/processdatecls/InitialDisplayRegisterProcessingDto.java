package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class InitialDisplayRegisterProcessingDto {

	private List<ProcessInformationDto> informationDto;

	private List<SetDaySupportDto> setDaySupportDto;

	private List<CurrProcessDateDto> currProcessDateDto;

	private EmpTiedProYearDto empTiedProYearDto;
}
