package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.verticalsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeople;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeopleFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaAmount;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaMoney;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaTimeUnit;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.MoneyFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.TimeUnitFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeople;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeopleFunc;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeopleFuncPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeoplePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertItem;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertItemPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertOrder;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertOrderPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormulaAmount;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormulaAmountPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormulaMoney;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormulaMoneyPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormulaTimeUnit;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormulaTimeUnitPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstMoneyFunc;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstMoneyFuncPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstTimeUnitFunc;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstTimeUnitFuncPK;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class JpaVerticalSetting extends JpaRepository implements VerticalSettingRepository {
	
	private static final String SELECT_ALL_GEN_VERT_SET;
	private static final String SELECT_FORMULA_AMOUNT;
	private static final String SELECT_FORMULA_TIME_UNIT;
	private static final String SELECT_TIME_UNIT_FUNC;
	private static final String SELECT_MONEY_FUNC;
	private static final String SELECT_FORMULA_MONEY;
	// Form People 
	private final String SELECT_PEOPLE_NO_WHERE = "SELECT c FROM KscmtFormPeople c ";
	private final String SELECT_PEOPLE_ITEM = SELECT_PEOPLE_NO_WHERE + "WHERE c.kscmtFormPeoplePK.companyId = :companyId";
	// form people func
	private final String SELECT_FUNC_NO_WHERE = "SELECT c FROM KscmtFormPeopleFunc c ";
	private final String SELECT_FUNC_ITEM = SELECT_FUNC_NO_WHERE + "WHERE c.KscmtFormPeopleFuncPK.companyId = :companyId";
	
	
	
	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtGenVertSet e");
		builderString.append(" WHERE e.kscmtGenVertSetPK.companyId = :companyId");
		SELECT_ALL_GEN_VERT_SET = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFormulaAmount e");
		builderString.append(" WHERE e.kscstFormulaAmountPK.companyId = :companyId");
		SELECT_FORMULA_AMOUNT = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFormulaTimeUnit e");
		builderString.append(" WHERE e.kscstFormulaTimeUnitPK.companyId = :companyId");
		SELECT_FORMULA_TIME_UNIT = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstTimeUnitFunc e");
		builderString.append(" WHERE e.kscstTimeUnitFuncPK.companyId = :companyId");
		SELECT_TIME_UNIT_FUNC = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstMoneyFunc e");
		builderString.append(" WHERE e.kscstMoneyFuncPK.companyId = :companyId");
		SELECT_MONEY_FUNC = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFormulaMoney e");
		builderString.append(" WHERE e.kscstFormulaMoneyPK.companyId = :companyId");
		SELECT_FORMULA_MONEY = builderString.toString();
	}
	
	
	/**
	 * convert form Form People entity to Form People domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static FormPeople toDomainFormPeople(KscmtFormPeople entity){
		if (entity == null) {
			return null;
		}
		
		List<FormPeopleFunc> lst = new ArrayList<>();
		for(KscmtFormPeopleFunc obj: entity.listPeopleFunc){
			lst.add(toDomainFormPeopleFunc(obj));
		}
		FormPeople domain = FormPeople.createFromJavaType(entity.kscmtFormPeoplePK.companyId, 
															entity.kscmtFormPeoplePK.verticalCalCd, 
															entity.kscmtFormPeoplePK.verticalCalItemId, 
															entity.actualDisplayAtr,
															lst);
		return domain;
	}
	
	/**
	 * Convert to Domain Formula Amount
	 * @param entity
	 * @return
	 * author: PhongTQ
	 */
	private static FormulaAmount toDomainFormAmount(KscstFormulaAmount entity){
		FormulaMoney moneyFunc = toDomainFormFormulaMoney(entity.moneyFunc);
		FormulaTimeUnit timeUnit = toDomainFormFormulaTime(entity.timeUnit);
		FormulaAmount amount = FormulaAmount.createFromJavatype(
				entity.kscstFormulaAmountPK.companyId, 
				entity.kscstFormulaAmountPK.verticalCalCd, 
				entity.kscstFormulaAmountPK.verticalCalItemId, 
				entity.calMethodAtr, 
				moneyFunc, 
				timeUnit);
		return amount;
				
	}
	
	/**
	 * Convert to Domain Formula Time Unit
	 * @param timeUnit
	 * @return
	 * author: PhongTQ
	 */
	private static FormulaTimeUnit toDomainFormFormulaTime(KscstFormulaTimeUnit timeUnit) {
		List<TimeUnitFunc> lst = new ArrayList<>();
		for(KscstTimeUnitFunc obj: timeUnit.listTime){
			lst.add(toDomainFormTime(obj));
		}
		return null;
	}
	
	/**
	 * Convert to Domain Time Unit Func
	 * @param obj
	 * @return
	 * author: PhongTQ
	 */
	private static TimeUnitFunc toDomainFormTime(KscstTimeUnitFunc obj) {
		TimeUnitFunc unitFunc = TimeUnitFunc.createFromJavatype(obj.kscstTimeUnitFuncPK.companyId, 
				obj.kscstTimeUnitFuncPK.verticalCalCd, 
				obj.kscstTimeUnitFuncPK.verticalCalItemId, 
				obj.kscstTimeUnitFuncPK.dispOrder, 
				obj.attendanceItemId, 
				obj.presetItemId, 
				obj.operatorAtr);
		return unitFunc;
	}

	/**
	 * Convert to Domain Money Func
	 * @param entity
	 * @return
	 * author: PhongTQ
	 */
	private static MoneyFunc toDomainFormMoney(KscstMoneyFunc entity){
		
		MoneyFunc domain = MoneyFunc.createFromJavatype(entity.kscstMoneyFuncPK.companyId, 
															entity.kscstMoneyFuncPK.verticalCalCd, 
															entity.kscstMoneyFuncPK.verticalCalItemId, 
															entity.kscstMoneyFuncPK.dispOrder,
															entity.externalBudgetCd,
															entity.attendanceItemId,
															entity.presetItemId,
															entity.operatorAtr);
		return domain;
	}
	/**
	 * Convert to Domain Formula Money
	 * @param entity
	 * @return
	 * author: PhongTQ
	 */
	private static FormulaMoney toDomainFormFormulaMoney(KscstFormulaMoney entity){
		List<MoneyFunc> lst = new ArrayList<>();
		for(KscstMoneyFunc obj: entity.listMoney){
			lst.add(toDomainFormMoney(obj));
		}
		FormulaMoney domain = FormulaMoney.createFromJavatype(entity.kscstFormulaMoneyPK.companyId, 
									entity.kscstFormulaMoneyPK.verticalCalCd, 
									entity.kscstFormulaMoneyPK.verticalCalItemId, 
									entity.categoryIndicator, 
									entity.actualDisplayAtr,
									lst);
		return domain;
	}

	
	/**
	 * convert to domain form people func
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static FormPeopleFunc toDomainFormPeopleFunc(KscmtFormPeopleFunc entity){
		FormPeopleFunc domain = FormPeopleFunc.createFromJavaType(entity.kscmtFormPeopleFuncPK.companyId, 
									entity.kscmtFormPeopleFuncPK.verticalCalCd, 
									entity.kscmtFormPeopleFuncPK.verticalCalItemId, 
									entity.kscmtFormPeopleFuncPK.externalBudgetCd, 
									entity.categoryAtr, 
									entity.categoryAtr, entity.dispOrder);
		return domain;
	}
	

	
	/**
	 * convert to entity form people
	 * @param domain
	 * @return
	 * author: Hoang Yen
	 */
	private KscmtFormPeople toEntityFormPeople(FormPeople domain){
		val entity = new KscmtFormPeople();
		entity.kscmtFormPeoplePK = new KscmtFormPeoplePK(domain.getCompanyId(), domain.getVerticalCalCd(), domain.getVerticalCalItemId());
		entity.actualDisplayAtr = domain.getActualDisplayAtr().value;
		List<KscmtFormPeopleFunc> lst = new ArrayList<>();
		if(domain.getLstPeopleFunc() != null){
			for(FormPeopleFunc item : domain.getLstPeopleFunc()){
				lst.add(toEntityFormPeopleFunc(item));
			}
		}
		entity.listPeopleFunc = lst;
		return entity;
	}
	
	/**
	 * Convert to Entity Money Func
	 * @param func
	 * @return
	 * author: PhongTQ
	 */
	private KscstMoneyFunc toEntityFormMoney(MoneyFunc func){
		KscstMoneyFunc entity = new KscstMoneyFunc();
		entity.kscstMoneyFuncPK = new KscstMoneyFuncPK(func.getCompanyId(), func.getVerticalCalCd(), func.getVerticalCalItemId(), func.getDispOrder());
		entity.externalBudgetCd = func.getExternalBudgetCd();
		entity.attendanceItemId = func.getAttendanceItemId();
		entity.presetItemId = func.getPresetItemId();
		entity.operatorAtr = func.getOperatorAtr().value;

		return entity;
	}
	
	/**
	 * Conver to Entity Formula Time Unit
	 * @param timeUnit
	 * @return
	 * author: PhongTQ
	 */
	private KscstFormulaTimeUnit toEntityFormTimeUnit(FormulaTimeUnit timeUnit){
		KscstFormulaTimeUnit entity = new KscstFormulaTimeUnit();
		entity.kscstFormulaTimeUnitPK = new KscstFormulaTimeUnitPK(timeUnit.getCompanyId(), timeUnit.getVerticalCalCd(), timeUnit.getVerticalCalItemId());
		entity.roundingTime = timeUnit.getRoundingTime().value;
		entity.roundingAtr = timeUnit.getRoundingAtr().value;
		entity.unitPrice = timeUnit.getUnitPrice().value;
		entity.actualDisplayAtr = timeUnit.getActualDisplayAtr().value;
		List<KscstTimeUnitFunc> lst = new ArrayList<>();
		if(timeUnit.getLstTimeUnitFuncs() != null){
			for(TimeUnitFunc item : timeUnit.getLstTimeUnitFuncs()){
				lst.add(toEntityTimeUnitFunc(item));
			}
		}
		entity.listTime = lst;
		return entity;
	}
	
	/**
	 * Convert to Entity Formula Money
	 * @param item
	 * @return
	 * author: PhongTQ
	 */
	private KscstFormulaMoney toEntityFormulaMoney(FormulaMoney item) {
		KscstFormulaMoney entity = new KscstFormulaMoney();
		entity.kscstFormulaMoneyPK = new KscstFormulaMoneyPK(item.getCompanyId(), item.getVerticalCalCd(), item.getVerticalCalItemId());
		entity.categoryIndicator = item.getCategoryIndicator().value;
		entity.actualDisplayAtr = item.getActualDisplayAtr().value;
		List<KscstMoneyFunc> lst = new ArrayList<>();
		if(item.getLstMoney() != null){
			for(MoneyFunc itemMoney : item.getLstMoney()){
				lst.add(toEntityFormMoney(itemMoney));
			}
		}
		entity.listMoney = lst;
		return entity;
	}
	
	/**
	 * Convert to Entity Time Unit Func
	 * @param unitFunc
	 * @return
	 * author: PhongTQ
	 */
	private KscstTimeUnitFunc toEntityTimeUnitFunc(TimeUnitFunc unitFunc){
		KscstTimeUnitFunc entity = new KscstTimeUnitFunc();
		entity.kscstTimeUnitFuncPK = new KscstTimeUnitFuncPK(unitFunc.getCompanyId(), unitFunc.getVerticalCalCd(), unitFunc.getVerticalCalItemId(), unitFunc.getDispOrder());
		entity.attendanceItemId = unitFunc.getAttendanceItemId();
		entity.presetItemId = unitFunc.getPresetItemId();
		entity.operatorAtr = unitFunc.getOperatorAtr().value;
		return entity;
	}
	
	/**
	 * Convert to Entity Formula Amount
	 * @param formulaAmount
	 * @return
	 * author: PhongTQ
	 */
	private KscstFormulaAmount toEntityFormAmount(FormulaAmount formulaAmount) {
		KscstFormulaAmount entity = new KscstFormulaAmount();
		entity.kscstFormulaAmountPK = new KscstFormulaAmountPK(formulaAmount.getCompanyId(), formulaAmount.getVerticalCalCd(), formulaAmount.getVerticalCalItemId());
		entity.calMethodAtr = formulaAmount.getCalMethodAtr().value;
		entity.moneyFunc = toEntityFormulaMoney(formulaAmount.getMoneyFunc());
		entity.timeUnit = toEntityFormTimeUnit(formulaAmount.getTimeUnit());
				
		return entity;
	}
	/**
	 * convert to entity form people func
	 * @param domain
	 * @return
	 * author: Hoang Yen
	 */
	public static KscmtFormPeopleFunc toEntityFormPeopleFunc(FormPeopleFunc domain){
		val entity = new KscmtFormPeopleFunc();
		entity.kscmtFormPeopleFuncPK = new KscmtFormPeopleFuncPK(domain.getCompanyId(), domain.getVerticalCalCd(), domain.getVerticalCalItemId(), domain.getExternalBudgetCd());
		entity.dispOrder = domain.getDispOrder();
		entity.categoryAtr = domain.getCategoryAtr().value;
		entity.operatorAtr = domain.getOperatorAtr().value;
		return entity;
	}
	
	/**
	 * Find all Vertical Cal Set
	 */
	@Override
	public List<VerticalCalSet> findAllVerticalCalSet(String companyId) {
		return this.queryProxy().query(SELECT_ALL_GEN_VERT_SET, KscmtGenVertSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomainVcs(c));
	}

	/**
	 * Convert to Domain Vertical Cal Set
	 * @param kscstVerticalCalSet
	 * @return
	 */
	private VerticalCalSet convertToDomainVcs(KscmtGenVertSet kscstVerticalCalSet) {
		
		List<VerticalCalItem> verticalCalItems = kscstVerticalCalSet.genVertItems.stream().map(t -> {
			FormPeople formPeople = toDomainFormPeople(t.formPeople);
			FormulaAmount amount = toDomainFormAmount(t.amount);
			return VerticalCalItem.createFromJavatype(t.kscmtGenVertItemPK.companyId, 
					t.kscmtGenVertItemPK.verticalCalCd, 
					t.kscmtGenVertItemPK.itemId, 
					t.itemName, 
					t.calculateAtr,
					t.displayAtr,
					t.cumulativeAtr,
					t.attributes,
					t.rounding,
					t.genVertOrder.dispOrder,
					formPeople,
					amount);
			}).collect(Collectors.toList());
		
		VerticalCalSet verticalCalSet = VerticalCalSet.createFromJavaType(
				kscstVerticalCalSet.kscmtGenVertSetPK.companyId,
				kscstVerticalCalSet.kscmtGenVertSetPK.verticalCalCd, 
				kscstVerticalCalSet.verticalCalName,
				kscstVerticalCalSet.unit,
				kscstVerticalCalSet.useAtr,
				kscstVerticalCalSet.assistanceTabulationAtr,
				verticalCalItems);
		
		return verticalCalSet;
	}

	/**
	 * Find Vertical Cal Set by Cd
	 */
	@Override
	public Optional<VerticalCalSet> getVerticalCalSetByCode(String companyId, String verticalCalCd) {
		KscmtGenVertSetPK primaryKey = new KscmtGenVertSetPK(companyId, verticalCalCd);
		
		return this.queryProxy().find(primaryKey, KscmtGenVertSet.class)
				.map(x -> convertToDomainVcs(x));
	}

	/**
	 * Convert to Vertical Cal Set Db Type
	 * @param verticalCalSet
	 * @return
	 */
	private KscmtGenVertSet convertToDbTypeVcs(VerticalCalSet verticalCalSet) {
		List<KscmtGenVertItem> items = verticalCalSet.getVerticalCalItems().stream()
				.map(x -> {
					KscmtGenVertOrder kscstVerticalItemOrder = new KscmtGenVertOrder(
							new KscmtGenVertOrderPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId()), x.getDispOrder());
					KscmtFormPeople entity = null;
					if(x.getFormPeople() != null){
						entity = toEntityFormPeople(x.getFormPeople());
					}
					
					KscstFormulaAmount amount = null;
					if(x.getFormulaAmount() != null){
						amount = toEntityFormAmount(x.getFormulaAmount());
					}
					KscmtGenVertItemPK key = new KscmtGenVertItemPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId());
					return new KscmtGenVertItem(key, x.getItemName(), x.getCalculateAtr().value, x.getDisplayAtr().value, x.getCumulativeAtr().value,
							x.getAttributes().value, x.getRounding().value, kscstVerticalItemOrder, entity,amount);
				}).collect(Collectors.toList());
		
		KscmtGenVertSet kscstVerticalCalSet = new KscmtGenVertSet();
		
		KscmtGenVertSetPK kscmtGenVertSetPK = new KscmtGenVertSetPK(
				verticalCalSet.getCompanyId(),
				verticalCalSet.getVerticalCalCd().v());
		
		kscstVerticalCalSet.verticalCalName = verticalCalSet.getVerticalCalName().v();
		kscstVerticalCalSet.unit = verticalCalSet.getUnit().value;
		kscstVerticalCalSet.useAtr = verticalCalSet.getUseAtr().value;
		kscstVerticalCalSet.assistanceTabulationAtr = verticalCalSet.getAssistanceTabulationAtr().value;
		
		kscstVerticalCalSet.kscmtGenVertSetPK = kscmtGenVertSetPK;
		
		kscstVerticalCalSet.genVertItems = items;
		
		return kscstVerticalCalSet;
	}
	

	/**
	 * Add Vertical Cal Set
	 */
	@Override
	public void addVerticalCalSet(VerticalCalSet verticalCalSet) {
		this.commandProxy().insert(convertToDbTypeVcs(verticalCalSet));	
	}

	/**
	 * Update Vertical Cal Set
	 */
	@Override
	public void updateVerticalCalSet(VerticalCalSet verticalCalSet) {
		KscmtGenVertSetPK kscstVerticalCalSetPK = new KscmtGenVertSetPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v());
		KscmtGenVertSet kscstVerticalCalSet = this.queryProxy().find(kscstVerticalCalSetPK, KscmtGenVertSet.class).get();
		
		kscstVerticalCalSet.verticalCalName = verticalCalSet.getVerticalCalName().v();
		kscstVerticalCalSet.unit = verticalCalSet.getUnit().value;
		kscstVerticalCalSet.useAtr = verticalCalSet.getUseAtr().value;
		kscstVerticalCalSet.assistanceTabulationAtr = verticalCalSet.getAssistanceTabulationAtr().value;
		
		List<KscmtGenVertItem> items = verticalCalSet.getVerticalCalItems().stream()
				.map(x -> {
					KscmtGenVertOrder kscstVerticalItemOrder = new KscmtGenVertOrder(
							new KscmtGenVertOrderPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId()), x.getDispOrder());
					KscmtFormPeople entity = null;
					if(x.getFormPeople() != null){
						entity = toEntityFormPeople(x.getFormPeople());
					}
					KscstFormulaAmount amount = null;
					if(x.getFormulaAmount() != null){
						amount = toEntityFormAmount(x.getFormulaAmount());
					}
					KscmtGenVertItemPK key = new KscmtGenVertItemPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId());
					return new KscmtGenVertItem(key, x.getItemName(), x.getCalculateAtr().value, x.getDisplayAtr().value, x.getCumulativeAtr().value,
							x.getAttributes().value, x.getRounding().value, kscstVerticalItemOrder, entity,amount);
				}).collect(Collectors.toList());
		
		kscstVerticalCalSet.genVertItems = items;
		
		this.commandProxy().update(kscstVerticalCalSet);
	}

	/**
	 * Remove Vertical Cal Set
	 */
	@Override
	public void deleteVerticalCalSet(String companyId, String verticalCalCd) {
		KscmtGenVertSetPK kscstVerticalCalSetPK = new KscmtGenVertSetPK(companyId, verticalCalCd);
		this.commandProxy().remove(KscmtGenVertSet.class, kscstVerticalCalSetPK);
	}

	/**
	 * find all form people
	 * author: Hoang Yen
	 */
	@Override
	public List<FormPeople> findAllFormPeople(String companyId) {
		return this.queryProxy().query(SELECT_PEOPLE_ITEM, KscmtFormPeople.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainFormPeople(c));
	}

	/**
	 * find all form people func
	 * author: Hoang Yen
	 */
	@Override
	public List<FormPeopleFunc> findAllFormPeopleFunc(String companyId) {
		return this.queryProxy().query(SELECT_FUNC_ITEM, KscmtFormPeopleFunc.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainFormPeopleFunc(c));
	}
	
	@Override
	public List<MoneyFunc> findAllMoneyFunc(String companyId) {
		return this.queryProxy().query(SELECT_MONEY_FUNC, KscstMoneyFunc.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainFormMoney(c));
	}
	
	@Override
	public List<FormulaMoney> findAllFormulaMoney(String companyId) {
		return this.queryProxy().query(SELECT_FORMULA_MONEY, KscstFormulaMoney.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainFormFormulaMoney(c));
	}
	
	@Override
	public List<FormulaAmount> findAllFormulaAmount(String companyId) {
		return this.queryProxy().query(SELECT_FORMULA_AMOUNT, KscstFormulaAmount.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainFormAmount(c));
	}
	
	@Override
	public List<FormulaTimeUnit> findAllFormulaTimeUnit(String companyId) {
		return this.queryProxy().query(SELECT_FORMULA_TIME_UNIT, KscstFormulaTimeUnit.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainFormFormulaTime(c));
	}
	
	@Override
	public List<TimeUnitFunc> findAllTimeUnit(String companyId) {
		return this.queryProxy().query(SELECT_TIME_UNIT_FUNC, KscstTimeUnitFunc.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainFormTime(c));
	}

	@Override
	public void insertFormPeople(FormPeople formPeople) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertFormPeopleFunc(List<FormPeopleFunc> formPeopleFunc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFormPeople(FormPeople formPeople) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFormPeopleFunc(List<FormPeopleFunc> lstFormPeopleFunc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFormPeople(FormPeople formPeople) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFormPeopleFunc(List<FormPeopleFunc> lstFormPeopleFunc) {
		// TODO Auto-generated method stub
		
	}
}
