package nts.uk.ctx.at.schedule.infra.repository.workschedule.budgetcontrol.budgetperformance;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetMoney;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgetTime;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActItemCode;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValues;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDaily;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.budgetcontrol.budgetperformance.KscdtExtBudgetDailyNew;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.budgetcontrol.budgetperformance.KscdtExtBudgetDailyPkNew;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;



/**
 * 日次の外部予算実績Repository
 * @author HieuLt
 *
 */
@Stateless
public class JpaExtBudgetDailyRepository extends JpaRepository implements ExtBudgetDailyRepository{
	
			private static final String GetDaily = "SELECT c FROM KscdtExtBudgetDailyNew c WHERE c.pk.targetUnit = :targetUnit "
												+ " AND c.pk.itemCd = :itemCode " 							
					                            + " AND c.pk.ymd >= :startDate"
												+ " AND c.pk.ymd <= :endDate"
					                            + " ORDER BY c.pk.ymd ASC ";
			private static final String Getdata = "SELECT c FROM KscdtExtBudgetDailyNew c WHERE c.pk.targetUnit = :targetUnit "
					                              + " AND c.pk.itemCd = :itemCode " 	
					                              + " AND c.pk.ymd = :ymd ";
			private static final String GetByKey = 	" SELECT c FROM KscdtExtBudgetDailyNew c WHERE c.pk.targetUnit = :targetUnit "	
													+ " AND c.pk.companyId = :companyId "
													+ " AND c.pk.targetID = :targetID "
													+ " AND c.pk.ymd = :ymd "
													+ " AND c.pk.itemCd = :itemCd ";
			private static final String DELETE = " DELETE FROM KscdtExtBudgetDailyNew k "
											   + " WHERE k.pk.targetID = :targetID " 
											   + " AND k.pk.itemCd = :itemCd "
											   + " AND k.pk.ymd = :ymd ";
			
	@Override
	public List<ExtBudgetDaily> getDailyExtBudgetResults(TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode,
			GeneralDate ymd) {
		
		List<ExtBudgetDaily> data = this.queryProxy().query(Getdata, KscdtExtBudgetDailyNew.class)
											.setParameter("targetUnit", targetOrg.getUnit().value)
											.setParameter("itemCode", itemCode)
											.setParameter("ymd", ymd)
											.getList( c ->toDomain(c));
		return data;
	}

	@Override
	public List<ExtBudgetDaily> getDailyExtBudgetResultsForPeriod(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod,
			ExtBudgetActItemCode itemCode) {
		

		return this.queryProxy().query(GetDaily, KscdtExtBudgetDailyNew.class)
				.setParameter("targetUnit", targetOrg.getUnit().value)
				.setParameter("itemCode", itemCode)
				.setParameter("startDate", datePeriod.start())
				.setParameter("endDate", datePeriod.end())
				.getList(c -> toDomain(c));
		
	}

	@Override
	public void insert(ExtBudgetDaily extBudgetDaily) {
		if(extBudgetDaily.getActualValue() == null)
			return;
		this.commandProxy().insert(toEntity(extBudgetDaily));
	}

	@Override
	public void update(ExtBudgetDaily extBudgetDaily) {
		String target = "";
		if(extBudgetDaily.getTargetOrg().getUnit().value == 0){
			 target = extBudgetDaily.getTargetOrg().getWorkplaceId().get();
		}
		else{
			 target = extBudgetDaily.getTargetOrg().getWorkplaceGroupId().get();
		}
		
		KscdtExtBudgetDailyNew oldData  = this.queryProxy().query(GetByKey, KscdtExtBudgetDailyNew.class)
										.setParameter("targetUnit", extBudgetDaily.getTargetOrg().getUnit().value)
										.setParameter("companyId", AppContexts.user().companyId())
										.setParameter("targetID", target)
										.setParameter("ymd", extBudgetDaily.getYmd())
										.setParameter("itemCd", extBudgetDaily.getItemCode().v()).getSingle().get();
		KscdtExtBudgetDailyNew newData = toEntity(extBudgetDaily);
		oldData.budgetATR = newData.budgetATR;
		oldData.val = newData.val;
		this.commandProxy().update(oldData);
	}

	@Override
	public void delete(TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode, GeneralDate ymd) {
		String targetID = "";
		if(targetOrg.getUnit().value == 0){
			targetID = targetOrg.getWorkplaceId().get();
		}
		else{
			targetID = targetOrg.getWorkplaceGroupId().get();
		}
		this.getEntityManager().createQuery(DELETE)
						.setParameter("targetID", targetID)
						.setParameter("itemCd", itemCode.v())
						.setParameter("ymd", ymd)
						.executeUpdate();
	}

	private static ExtBudgetDaily toDomain (KscdtExtBudgetDailyNew entity){
		ExtBudgetActualValues val = null;
		if(entity.budgetATR == 0)
			val = new ExtBudgetTime(entity.val);
		 else if(entity.budgetATR == 1)
			val = new ExtBudgetNumberPerson(entity.val);
		 else if(entity.budgetATR == 2)
			val = new ExtBudgetMoney(entity.val);
		 else if(entity.budgetATR == 3)
			val = new ExtBudgetNumericalVal(entity.val);
		ExtBudgetDaily domain = new ExtBudgetDaily(
				new TargetOrgIdenInfor(EnumAdaptor.valueOf( entity.pk.targetUnit, TargetOrganizationUnit.class),
						entity.pk.targetUnit == 0 ? Optional.ofNullable(entity.pk.targetID): Optional.empty(),
						entity.pk.targetUnit == 0 ? Optional.empty() : Optional.ofNullable(entity.pk.targetID)),
				new ExtBudgetActItemCode(entity.pk.itemCd),
				entity.pk.ymd,
				val) ;
		
		return domain;
	}
	private static KscdtExtBudgetDailyNew toEntity(ExtBudgetDaily dom){
		int budgetATR = 0;
		int val = 0;
		if(dom.getActualValue() instanceof ExtBudgetTime) {
			budgetATR = 0;
			ExtBudgetTime value = (ExtBudgetTime) dom.getActualValue(); 
			val = value.v();
		}
		else if(dom.getActualValue() instanceof ExtBudgetNumberPerson) {
			budgetATR = 1;
			ExtBudgetNumberPerson value = (ExtBudgetNumberPerson) dom.getActualValue(); 
			val = value.v();
		}
		else if(dom.getActualValue() instanceof ExtBudgetMoney) {
			budgetATR = 2;
			ExtBudgetMoney value = (ExtBudgetMoney) dom.getActualValue();
			val = value.v();
		}
		else if(dom.getActualValue() instanceof ExtBudgetNumericalVal) {
			budgetATR = 3;
			ExtBudgetNumericalVal value = (ExtBudgetNumericalVal) dom.getActualValue();
			val = value.v();
		}
		
		
		KscdtExtBudgetDailyPkNew pk =  new KscdtExtBudgetDailyPkNew(
				AppContexts.user().companyId(),
				dom.getTargetOrg().getUnit().value,
				dom.getTargetOrg().getUnit().value == 0 ? dom.getTargetOrg().getWorkplaceId().get() : dom.getTargetOrg().getWorkplaceGroupId().get(),
				dom.getYmd(),
				dom.getItemCode().v());
	
		KscdtExtBudgetDailyNew entity = new KscdtExtBudgetDailyNew(
				pk, 
				budgetATR, 
				val);
		
		return entity;
		
	}

}
