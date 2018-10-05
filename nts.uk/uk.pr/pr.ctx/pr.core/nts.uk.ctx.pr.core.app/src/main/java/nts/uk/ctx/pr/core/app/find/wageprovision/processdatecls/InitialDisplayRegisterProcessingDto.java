package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;

@AllArgsConstructor
@Value
public class InitialDisplayRegisterProcessingDto {

	private List<ProcessInformationDto> informationDto;

	private List<SetDaySupportDto> setDaySupportDto;

	private List<CurrProcessDateDto> currProcessDateDto;

	private List<EmpTiedProYearDto> empTiedProYearDto;
	
	private List<EmpCdNameImport> empCdNameImports;
}
