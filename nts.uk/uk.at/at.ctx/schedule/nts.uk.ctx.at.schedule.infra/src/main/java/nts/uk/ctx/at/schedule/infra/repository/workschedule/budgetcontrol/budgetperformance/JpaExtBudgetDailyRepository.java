package nts.uk.ctx.at.schedule.infra.repository.workschedule.budgetcontrol.budgetperformance;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.error.ExtBudgetActualValue;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActItemCode;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDaily;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.budgetcontrol.budgetperformance.KscdtExtBudgetDailyNew;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnitTest;

/**
 * 日次の外部予算実績Repository
 * @author HieuLt
 *
 */
@Stateless
public class JpaExtBudgetDailyRepository extends JpaRepository implements ExtBudgetDailyRepository{
	
			private static final String GetDaily = "SELECT c FROM KscdtExtBudgetDailyNew c WHERE c.pk.targetUnit =:targetUnit "
												+ " AND c.pk.itemCd =:itemCode " 							
					                            + " AND c.pk.ymd >= :startDate"
												+ " AND c.pk.ymd <= :endDate"
					                            + " ORDER BY c.pk.ymd ASC ";

	@Override
	public ExtBudgetDaily getDailyExtBudgetResults(TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode,
			GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExtBudgetDaily> getDailyExtBudgetResultsForPeriod(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod,
			ExtBudgetActItemCode itemCode) {
		
	/*	return this.queryProxy().query(GetDaily, KscdtExtBudgetDailyNew.class)
				.setParameter("targetUnit", targetOrg.getUnit())
                .setParameter("itemCode", itemCode) 
                .setParameter("startDate", datePeriod.start())
                .setParameter("endDate", datePeriod.end())
                .getList(c -> c.toDomain());*/
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

	private static ExtBudgetDaily toDomain (KscdtExtBudgetDailyNew entity){
		ExtBudgetDaily domain  = new ExtBudgetDaily(
				new TargetOrgIdenInfor(EnumAdaptor.valueOf( entity.pk.targetUnit, TargetOrganizationUnit.class),
						entity.pk.targetUnit == 0 ? entity.pk.targetID : "", 
						entity.pk.targetUnit == 0 ? "" : entity.pk.targetID ),
				new ExtBudgetActItemCode(entity.pk.itemCd),
				entity.pk.ymd,
				(nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValue) new ExtBudgetActualValue("1"));
		return domain;
	}

}
