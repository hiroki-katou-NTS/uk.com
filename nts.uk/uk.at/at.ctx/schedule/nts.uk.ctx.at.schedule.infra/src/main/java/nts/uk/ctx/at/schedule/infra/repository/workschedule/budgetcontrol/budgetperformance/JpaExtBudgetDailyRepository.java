package nts.uk.ctx.at.schedule.infra.repository.workschedule.budgetcontrol.budgetperformance;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActItemCode;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDaily;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 日次の外部予算実績Repository
 * @author HieuLt
 *
 */
@Stateless
public class JpaExtBudgetDailyRepository extends JpaRepository implements ExtBudgetDailyRepository{

	@Override
	public ExtBudgetDaily getDailyExtBudgetResults(TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode,
			GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExtBudgetDaily> getDailyExtBudgetResultsForPeriod(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod,
			ExtBudgetActItemCode itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(ExtBudgetDaily extBudgetDaily) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	@Override
	public void update(ExtBudgetDaily extBudgetDaily) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode, GeneralDate ymd) {
		// TODO Auto-generated method stub
		
	}

	

}
