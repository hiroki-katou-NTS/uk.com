package nts.uk.screen.com.app.command.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.company.app.command.company.DeleteCompanyInforCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor.DeleteDivWorkPlaceDifferInforCommand;
import nts.uk.ctx.sys.env.app.command.sysusagesetfinder.DeleteSysUsageSetCommand;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cmm001DeleteCommand {
	private DeleteCompanyInforCommand delComCm;
	private DeleteSysUsageSetCommand delSysCm;
	private DeleteDivWorkPlaceDifferInforCommand delDivCm;
}
