package nts.uk.screen.com.app.command.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.company.app.command.company.UpdateCompanyInforCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor.UpdateDivWorkPlaceDifferInforCommand;
import nts.uk.ctx.sys.env.app.command.sysusagesetfinder.UpdateSysUsageSetCommand;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cmm001UpdateCommand {
	private UpdateCompanyInforCommand comCm;
	private UpdateSysUsageSetCommand sysCm;
	private UpdateDivWorkPlaceDifferInforCommand divCm;
}
