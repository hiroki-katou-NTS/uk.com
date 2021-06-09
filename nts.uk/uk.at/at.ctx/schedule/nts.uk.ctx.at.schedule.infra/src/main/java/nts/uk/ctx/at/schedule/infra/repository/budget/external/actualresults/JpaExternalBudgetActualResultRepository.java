package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresults;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetActualResult;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetActualResultRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetMoneyValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetTimeValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetValues;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresults.KscdtExtBudgetDailyNew;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresults.KscdtExtBudgetDailyPkNew;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;



/**
 * 日次の外部予算実績Repository
 * @author HieuLt
 *
 */
@Stateless
public class JpaExternalBudgetActualResultRepository extends JpaRepository implements ExternalBudgetActualResultRepository {

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
			private static final String GetDailyByPeriod = "SELECT c FROM KscdtExtBudgetDailyNew c WHERE c.pk.targetUnit = :targetUnit "
															+ " AND c.pk.targetID = :targetID "
										                    + " AND c.pk.ymd >= :startDate"
															+ " AND c.pk.ymd <= :endDate"
										                    + " ORDER BY c.pk.ymd ASC ";

			private static final String GetDailyByListTarget= "SELECT c FROM KscdtExtBudgetDailyNew c WHERE c.pk.targetUnit IN :listTargetUnit "
																+ " AND c.pk.targetID IN :listTargetID "
											                    + " AND c.pk.ymd >= :startDate"
																+ " AND c.pk.ymd <= :endDate"
											                    + " ORDER BY c.pk.ymd ASC ";


	@Override
	public List<ExternalBudgetActualResult> get(TargetOrgIdenInfor targetOrg, ExternalBudgetCd itemCode, GeneralDate ymd) {

		List<ExternalBudgetActualResult> data = this.queryProxy().query(Getdata, KscdtExtBudgetDailyNew.class)
											.setParameter("targetUnit", targetOrg.getUnit().value)
											.setParameter("itemCode", itemCode)
											.setParameter("ymd", ymd)
											.getList( c ->toDomain(c));
		return data;
	}

	@Override
	public List<ExternalBudgetActualResult> getByPeriod(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod, ExternalBudgetCd itemCode) {


		return this.queryProxy().query(GetDaily, KscdtExtBudgetDailyNew.class)
				.setParameter("targetUnit", targetOrg.getUnit().value)
				.setParameter("itemCode", itemCode)
				.setParameter("startDate", datePeriod.start())
				.setParameter("endDate", datePeriod.end())
				.getList(c -> toDomain(c));

	}

	@Override
	public List<ExternalBudgetActualResult> getAllByPeriod(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod) {

		return this.queryProxy().query(GetDailyByPeriod,  KscdtExtBudgetDailyNew.class)
				.setParameter("targetUnit", targetOrg.getUnit().value)
				.setParameter("targetID", targetOrg.getTargetId())
				.setParameter("startDate", datePeriod.start())
				.setParameter("endDate", datePeriod.end())
				.getList(c -> toDomain(c));

	}

	@Override
	public List<ExternalBudgetActualResult> getAllByPeriod(List<TargetOrgIdenInfor> lstTargetOrg, DatePeriod datePeriod) {
		if (CollectionUtil.isEmpty(lstTargetOrg))
			return new ArrayList<>();

		List<ExternalBudgetActualResult> results = new ArrayList<>();
		for (TargetOrganizationUnit unit : TargetOrganizationUnit.values()) {
			List<TargetOrgIdenInfor> lstWkp = lstTargetOrg.stream().filter(t -> t.getUnit() == unit).collect(Collectors.toList());
			if (!lstWkp.isEmpty()) {
				List<String> listTargetID = lstWkp.stream().map(TargetOrgIdenInfor::getTargetId).filter(Objects::nonNull).collect(Collectors.toList());
				results.addAll(this.queryProxy().query(GetDailyByListTarget,  KscdtExtBudgetDailyNew.class)
						.setParameter("listTargetUnit", Collections.singletonList(unit.value))
						.setParameter("listTargetID", listTargetID)
						.setParameter("startDate", datePeriod.start())
						.setParameter("endDate", datePeriod.end())
						.getList(JpaExternalBudgetActualResultRepository::toDomain));
			}
		}

		return results;
	}

	@Override
	public void insert(ExternalBudgetActualResult actualResult) {
		if(actualResult.getActualValue() == null)
			return;
		this.commandProxy().insert(toEntity(actualResult));
	}

	@Override
	public void update(ExternalBudgetActualResult actualResult) {
		String target = "";
		if(actualResult.getTargetOrg().getUnit().value == 0){
			 target = actualResult.getTargetOrg().getWorkplaceId().get();
		}
		else{
			 target = actualResult.getTargetOrg().getWorkplaceGroupId().get();
		}

		KscdtExtBudgetDailyNew oldData  = this.queryProxy().query(GetByKey, KscdtExtBudgetDailyNew.class)
										.setParameter("targetUnit", actualResult.getTargetOrg().getUnit().value)
										.setParameter("companyId", AppContexts.user().companyId())
										.setParameter("targetID", target)
										.setParameter("ymd", actualResult.getYmd())
										.setParameter("itemCd", actualResult.getItemCode().v()).getSingle().get();
		KscdtExtBudgetDailyNew newData = toEntity(actualResult);
		oldData.budgetATR = newData.budgetATR;
		oldData.val = newData.val;
		this.commandProxy().update(oldData);
	}

	@Override
	public void delete(TargetOrgIdenInfor targetOrg, ExternalBudgetCd itemCode, GeneralDate ymd) {
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

	private static ExternalBudgetActualResult toDomain (KscdtExtBudgetDailyNew entity){
		ExternalBudgetValues val = null;
		if(entity.budgetATR == 0)
			val = new ExternalBudgetTimeValue(entity.val);
		 else if(entity.budgetATR == 1)
			val = new ExtBudgetNumberPerson(entity.val);
		 else if(entity.budgetATR == 2)
			val = new ExternalBudgetMoneyValue(entity.val);
		 else if(entity.budgetATR == 3)
			val = new ExtBudgetNumericalVal(entity.val);
		ExternalBudgetActualResult domain = new ExternalBudgetActualResult(
				new TargetOrgIdenInfor(EnumAdaptor.valueOf( entity.pk.targetUnit, TargetOrganizationUnit.class),
						entity.pk.targetUnit == 0 ? Optional.ofNullable(entity.pk.targetID): Optional.empty(),
						entity.pk.targetUnit == 0 ? Optional.empty() : Optional.ofNullable(entity.pk.targetID)),
				new ExternalBudgetCd(entity.pk.itemCd),
				entity.pk.ymd,
				val) ;

		return domain;
	}
	private static KscdtExtBudgetDailyNew toEntity(ExternalBudgetActualResult dom){
		int budgetATR = 0;
		int val = 0;
		if(dom.getActualValue() instanceof ExternalBudgetTimeValue) {
			budgetATR = 0;
			ExternalBudgetTimeValue value = (ExternalBudgetTimeValue) dom.getActualValue();
			val = value.v();
		}
		else if(dom.getActualValue() instanceof ExtBudgetNumberPerson) {
			budgetATR = 1;
			ExtBudgetNumberPerson value = (ExtBudgetNumberPerson) dom.getActualValue();
			val = value.v();
		}
		else if(dom.getActualValue() instanceof ExternalBudgetMoneyValue) {
			budgetATR = 2;
			ExternalBudgetMoneyValue value = (ExternalBudgetMoneyValue) dom.getActualValue();
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
