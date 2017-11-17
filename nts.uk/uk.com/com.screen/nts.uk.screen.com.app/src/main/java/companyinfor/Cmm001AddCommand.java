package companyinfor;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor.AddDivWorkPlaceDifferInforCommand;
import nts.uk.ctx.command.AddCompanyInforCommand;
import nts.uk.ctx.sys.env.app.command.sysusagesetfinder.AddSysUsageSetCommand;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cmm001AddCommand {
	private AddCompanyInforCommand addComCm;
	
	private AddSysUsageSetCommand addSysCm;
	
	private AddDivWorkPlaceDifferInforCommand addDivCm;
}
