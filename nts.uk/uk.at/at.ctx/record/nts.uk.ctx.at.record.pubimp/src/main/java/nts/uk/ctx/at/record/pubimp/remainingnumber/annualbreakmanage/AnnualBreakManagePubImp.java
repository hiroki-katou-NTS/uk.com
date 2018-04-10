package nts.uk.ctx.at.record.pubimp.remainingnumber.annualbreakmanage;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.AnnualBreakManageExport;
import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.AnnualBreakManagePub;

@Stateless
public class AnnualBreakManagePubImp implements AnnualBreakManagePub {

	@Override
	public List<AnnualBreakManageExport> getEmployeeId(List<String> employeeId, GeneralDate startDate,
			GeneralDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
