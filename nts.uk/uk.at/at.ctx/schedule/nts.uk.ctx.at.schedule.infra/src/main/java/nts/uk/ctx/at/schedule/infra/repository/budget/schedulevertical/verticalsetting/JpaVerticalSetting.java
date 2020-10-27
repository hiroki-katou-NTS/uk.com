package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.verticalsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormBuilt;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeople;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeopleFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTime;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTimeFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaAmount;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaMoney;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaNumerical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaTimeUnit;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaUnitprice;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.MoneyFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.TimeUnitFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormBuilt;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormBuiltPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeople;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeopleFunc;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeopleFuncPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeoplePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormTime;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormTimeFunc;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormTimeFuncPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormTimePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtVerticalItem;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtVerticalItemPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtVerticalSort;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtVerticalSortPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtVerticalSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtVerticalSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormAmount;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormAmountPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormMoney;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormMoneyFunc;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormMoneyFuncPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormMoneyPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormNumerical;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormNumericalPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormTimeUnit;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormTimeUnitPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormTimeunitFunc;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormTimeunitFuncPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormulaUnitPrice;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstFormulaUnitPricePK;

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
	private static final String SELECT_FORMULA_NUMMER;
	private static final String SELECT_FORMULA_PRICE;
	// Form People
	private static final String SELECT_PEOPLE_NO_WHERE = "SELECT c FROM KscmtFormPeople c ";
	private static final String SELECT_PEOPLE_ITEM = SELECT_PEOPLE_NO_WHERE
			+ "WHERE c.kscmtFormPeoplePK.companyId = :companyId";
	// form people func
	private static final String SELECT_FUNC_NO_WHERE = "SELECT c FROM KscmtFormPeopleFunc c ";
	private static final String SELECT_FUNC_ITEM = SELECT_FUNC_NO_WHERE
			+ "WHERE c.KscmtFormPeopleFuncPK.companyId = :companyId";

	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtVerticalSet e");
		builderString.append(" WHERE e.kscmtVerticalSetPK.companyId = :companyId");
		SELECT_ALL_GEN_VERT_SET = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFormAmount e");
		builderString.append(" WHERE e.kscstFormAmountPK.companyId = :companyId");
		SELECT_FORMULA_AMOUNT = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFormTimeUnit e");
		builderString.append(" WHERE e.kscstFormTimeUnitPK.companyId = :companyId");
		SELECT_FORMULA_TIME_UNIT = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtFormTimeunitFunc e");
		builderString.append(" WHERE e.kscmtFormTimeunitFuncPK.companyId = :companyId");
		SELECT_TIME_UNIT_FUNC = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtFormMoneyFunc e");
		builderString.append(" WHERE e.kscmtFormMoneyFuncPK.companyId = :companyId");
		SELECT_MONEY_FUNC = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtFormMoney e");
		builderString.append(" WHERE e.kscmtFormMoneyPK.companyId = :companyId");
		SELECT_FORMULA_MONEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFormNumerical e");
		builderString.append(" WHERE e.kscstFormNumericalPK.companyId = :companyId");
		SELECT_FORMULA_NUMMER = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFormUnitPrice e");
		builderString.append(" WHERE e.kscstFormUnitPricePK.companyId = :companyId");
		SELECT_FORMULA_PRICE = builderString.toString();

	}

	/**
	 * convert form Form People entity to Form People domain
	 * 
	 * @param entity
	 * @return author: Hoang Yen
	 */
	private static FormPeople toDomainFormPeople(KscmtFormPeople entity) {
		if (entity == null) {
			return null;
		}

		List<FormPeopleFunc> lst = new ArrayList<>();
		for (KscmtFormPeopleFunc obj : entity.listPeopleFunc) {
			lst.add(toDomainFormPeopleFunc(obj));
		}
		FormPeople domain = FormPeople.createFromJavaType(entity.kscmtFormPeoplePK.companyId,
				entity.kscmtFormPeoplePK.verticalCalCd, entity.kscmtFormPeoplePK.verticalCalItemId,
				entity.actualDisplayAtr, lst);
		return domain;
	}

	/**
	 * Convert to Domain Formula Amount
	 * 
	 * @author phongtq
	 * @param entity
	 * @return
	 */
	private static FormulaAmount toDomainFormAmount(KscstFormAmount entity) {
			if(entity == null) {return null;}
			FormulaMoney moneyFunc = toDomainFormFormulaMoney(entity.moneyFunc);
			FormulaTimeUnit timeUnit = toDomainFormFormulaTime(entity.timeUnit);
			FormulaAmount amount = FormulaAmount.createFromJavatype(entity.kscstFormulaAmountPK.companyId,
					entity.kscstFormulaAmountPK.verticalCalCd, entity.kscstFormulaAmountPK.verticalCalItemId,
					entity.calMethodAtr, moneyFunc, timeUnit);
			return amount;
	
		
	}

	/**
	 * Convert to Domain Formula Time Unit
	 * 
	 * @author phongtq
	 * @param timeUnit
	 * @return
	 */
	private static FormulaTimeUnit toDomainFormFormulaTime(KscstFormTimeUnit timeUnit) {
		if (timeUnit == null) {
			return null;
		}
		List<TimeUnitFunc> lst = new ArrayList<>();
		if (timeUnit.listTime != null) {
			for (KscmtFormTimeunitFunc obj : timeUnit.listTime) {
				lst.add(toDomainFormTime(obj));
			}
		}
		return FormulaTimeUnit.createFromJavatype(timeUnit.kscstFormulaTimeUnitPK.companyId,
				timeUnit.kscstFormulaTimeUnitPK.verticalCalCd, timeUnit.kscstFormulaTimeUnitPK.verticalCalItemId,
				timeUnit.roundingTime, timeUnit.roundingAtr, timeUnit.unitPrice, timeUnit.actualDisplayAtr, lst);
	}

	/**
	 * Convert to Domain Time Unit Func
	 * 
	 * @author phongtq
	 * @param obj
	 * @return
	 */
	private static TimeUnitFunc toDomainFormTime(KscmtFormTimeunitFunc obj) {
		TimeUnitFunc unitFunc = TimeUnitFunc.createFromJavatype(obj.kscstTimeUnitFuncPK.companyId,
				obj.kscstTimeUnitFuncPK.verticalCalCd, obj.kscstTimeUnitFuncPK.verticalCalItemId,
				obj.kscstTimeUnitFuncPK.dispOrder, obj.attendanceItemId, obj.presetItemId, obj.operatorAtr);
		return unitFunc;
	}

	/**
	 * Convert to Domain Money Func
	 * 
	 * @author phongtq
	 * @param entity
	 * @return
	 */
	private static MoneyFunc toDomainFormMoney(KscmtFormMoneyFunc entity) {

		MoneyFunc domain = MoneyFunc.createFromJavatype(entity.kscstMoneyFuncPK.companyId,
				entity.kscstMoneyFuncPK.verticalCalCd, entity.kscstMoneyFuncPK.verticalCalItemId,
				entity.kscstMoneyFuncPK.dispOrder, entity.externalBudgetCd, entity.attendanceItemId,
				entity.presetItemId, entity.operatorAtr);
		return domain;
	}

	/**
	 * Conver to Domain Formula Numerical
	 * 
	 * @author phongtq
	 * @param numerical
	 * @return
	 */
	private List<FormulaNumerical> toDomainFormNumer(List<KscstFormNumerical> numerical) {
		if (numerical == null) {
			return null;
		}
		List<FormulaNumerical> domain = numerical.stream()
				.map(x -> FormulaNumerical.createFromJavatype(x.kscstFormulaNumericalPK.companyId,
						x.kscstFormulaNumericalPK.verticalCalCd, x.kscstFormulaNumericalPK.verticalCalItemId,
						x.kscstFormulaNumericalPK.dispOrder, x.externalBudgetCd, x.operatorAtr))
				.collect(Collectors.toList());
		return domain;
	}

	/**
	 * Convert to Entity Formula Numerical
	 * 
	 * @author phongtq
	 * @param numerical
	 * @return
	 */
	private List<KscstFormNumerical> toEntityFormNumerical(List<FormulaNumerical> numerical) {
		List<KscstFormNumerical> entity = numerical.stream()
				.map(x -> new KscstFormNumerical(new KscstFormNumericalPK(x.getCompanyId(), x.getVerticalCalCd(),
						x.getVerticalCalItemId(), x.getDispOrder()), x.getExternalBudgetCd(), x.getOperatorAtr().value))
				.collect(Collectors.toList());

		return entity;
	}

	/**
	 * Convert to Domain Formula Unitprice
	 * 
	 * @author phongtq
	 * @param price
	 * @return
	 */
	private FormulaUnitprice toDomainFromPrice(KscstFormulaUnitPrice price) {
		if (price == null) {
			return null;
		}
		FormulaUnitprice domain = FormulaUnitprice.createFromJavatype(price.kscstFormulaUnitPricePK.companyId,
				price.kscstFormulaUnitPricePK.verticalCalCd, price.kscstFormulaUnitPricePK.verticalCalItemId,
				price.attendanceAtr, price.unitPrice);
		return domain;
	}

	/**
	 * Convert to Entity Formula Unitprice
	 * 
	 * @author phongtq
	 * @param unitprice
	 * @return
	 */
	private KscstFormulaUnitPrice toEntityFormPrice(FormulaUnitprice unitprice) {
		KscstFormulaUnitPrice entity = new KscstFormulaUnitPrice();
		entity.kscstFormulaUnitPricePK = new KscstFormulaUnitPricePK(unitprice.getCompanyId(),
				unitprice.getVerticalCalCd(), unitprice.getVerticalCalItemId());
		entity.attendanceAtr = unitprice.getAttendanceAtr().value;
		entity.unitPrice = unitprice.getUnitPrice().value;
		return entity;
	}

	/**
	 * Convert to Domain Formula Money
	 * 
	 * @author phongtq
	 * @param entity
	 * @return
	 */
	private static FormulaMoney toDomainFormFormulaMoney(KscmtFormMoney entity) {
		List<MoneyFunc> lst = new ArrayList<>();
		for (KscmtFormMoneyFunc obj : entity.listMoney) {
			lst.add(toDomainFormMoney(obj));
		}
		FormulaMoney domain = FormulaMoney.createFromJavatype(entity.kscstFormulaMoneyPK.companyId,
				entity.kscstFormulaMoneyPK.verticalCalCd, entity.kscstFormulaMoneyPK.verticalCalItemId,
				entity.categoryIndicator, entity.actualDisplayAtr, lst);
		return domain;
	}

	/**
	 * convert to domain form people func
	 * 
	 * @param entity
	 * @return author: Hoang Yen
	 */
	private static FormPeopleFunc toDomainFormPeopleFunc(KscmtFormPeopleFunc entity) {
		FormPeopleFunc domain = FormPeopleFunc.createFromJavaType(entity.kscmtFormPeopleFuncPK.companyId,
				entity.kscmtFormPeopleFuncPK.verticalCalCd, entity.kscmtFormPeopleFuncPK.verticalCalItemId,
				entity.kscmtFormPeopleFuncPK.dispOrder, entity.externalBudgetCd, entity.categoryAtr,
				entity.operatorAtr);
		return domain;
	}

	/**
	 * convert to entity form people
	 * 
	 * @param domain
	 * @return author: Hoang Yen
	 */
	private KscmtFormPeople toEntityFormPeople(FormPeople domain) {
		val entity = new KscmtFormPeople();
		entity.kscmtFormPeoplePK = new KscmtFormPeoplePK(domain.getCompanyId(), domain.getVerticalCalCd(),
				domain.getVerticalCalItemId());
		entity.actualDisplayAtr = domain.getActualDisplayAtr().value;
		List<KscmtFormPeopleFunc> lst = new ArrayList<>();
		if (domain.getLstPeopleFunc() != null) {
			for (FormPeopleFunc item : domain.getLstPeopleFunc()) {
				lst.add(toEntityFormPeopleFunc(item));
			}
		}
		entity.listPeopleFunc = lst;
		return entity;
	}

	/**
	 * Convert to Entity Money Func
	 * 
	 * @author phongtq
	 * @param func
	 * @return
	 */
	private KscmtFormMoneyFunc toEntityFormMoney(MoneyFunc func) {
		KscmtFormMoneyFunc entity = new KscmtFormMoneyFunc();
		entity.kscstMoneyFuncPK = new KscmtFormMoneyFuncPK(func.getCompanyId(), func.getVerticalCalCd(),
				func.getVerticalCalItemId(), func.getDispOrder());
		entity.externalBudgetCd = func.getExternalBudgetCd();
		entity.attendanceItemId = func.getAttendanceItemId();
		entity.presetItemId = func.getPresetItemId();
		entity.operatorAtr = func.getOperatorAtr().value;

		return entity;
	}

	/**
	 * Conver to Entity Formula Time Unit
	 * 
	 * @author phongtq
	 * @param timeUnit
	 * @return
	 */
	private KscstFormTimeUnit toEntityFormTimeUnit(FormulaTimeUnit timeUnit) {
		if (timeUnit == null) {
			return null;
		}
		KscstFormTimeUnit entity = new KscstFormTimeUnit();
		entity.kscstFormulaTimeUnitPK = new KscstFormTimeUnitPK(timeUnit.getCompanyId(), timeUnit.getVerticalCalCd(),
				timeUnit.getVerticalCalItemId());
		entity.roundingTime = timeUnit.getRoundingTime().value;
		entity.roundingAtr = timeUnit.getRoundingAtr().value;
		entity.unitPrice = timeUnit.getUnitPrice().value;
		entity.actualDisplayAtr = timeUnit.getActualDisplayAtr().value;
		List<KscmtFormTimeunitFunc> lst = new ArrayList<>();
		if (timeUnit.getLstTimeUnitFuncs() != null) {
			for (TimeUnitFunc item : timeUnit.getLstTimeUnitFuncs()) {
				lst.add(toEntityTimeUnitFunc(item));
			}
		}
		entity.listTime = lst;
		return entity;
	}

	/**
	 * Convert to Entity Formula Money
	 * 
	 * @author phongtq
	 * @param item
	 * @return
	 */
	private KscmtFormMoney toEntityFormulaMoney(FormulaMoney item) {
		if (item == null) {
			return null;
		}
		KscmtFormMoney entity = new KscmtFormMoney();
		entity.kscstFormulaMoneyPK = new KscmtFormMoneyPK(item.getCompanyId(), item.getVerticalCalCd(),
				item.getVerticalCalItemId());
		entity.categoryIndicator = item.getCategoryIndicator().value;
		entity.actualDisplayAtr = item.getActualDisplayAtr().value;
		List<KscmtFormMoneyFunc> lst = new ArrayList<>();
		if (item.getLstMoney() != null) {
			for (MoneyFunc itemMoney : item.getLstMoney()) {
				lst.add(toEntityFormMoney(itemMoney));
			}
		}
		entity.listMoney = lst;
		return entity;
	}

	/**
	 * Convert to Entity Time Unit Func
	 * 
	 * @author phongtq
	 * @param unitFunc
	 * @return
	 */
	private KscmtFormTimeunitFunc toEntityTimeUnitFunc(TimeUnitFunc unitFunc) {
		KscmtFormTimeunitFunc entity = new KscmtFormTimeunitFunc();
		entity.kscstTimeUnitFuncPK = new KscmtFormTimeunitFuncPK(unitFunc.getCompanyId(), unitFunc.getVerticalCalCd(),
				unitFunc.getVerticalCalItemId(), unitFunc.getDispOrder());
		entity.attendanceItemId = unitFunc.getAttendanceItemId();
		entity.presetItemId = unitFunc.getPresetItemId();
		entity.operatorAtr = unitFunc.getOperatorAtr().value;
		return entity;
	}

	/**
	 * Convert to Entity Formula Amount
	 * 
	 * @author phongtq
	 * @param formulaAmount
	 * @return
	 */
	private KscstFormAmount toEntityFormAmount(FormulaAmount formulaAmount) {
		KscstFormAmount entity = new KscstFormAmount();
		entity.kscstFormulaAmountPK = new KscstFormAmountPK(formulaAmount.getCompanyId(),
				formulaAmount.getVerticalCalCd(), formulaAmount.getVerticalCalItemId());
		entity.calMethodAtr = formulaAmount.getCalMethodAtr().value;

		entity.moneyFunc = toEntityFormulaMoney(formulaAmount.getMoneyFunc());
		entity.timeUnit = toEntityFormTimeUnit(formulaAmount.getTimeUnit());

		return entity;
	}

	/**
	 * convert to entity form people func
	 * 
	 * @param domain
	 * @return author: Hoang Yen
	 */
	public static KscmtFormPeopleFunc toEntityFormPeopleFunc(FormPeopleFunc domain) {
		val entity = new KscmtFormPeopleFunc();
		entity.kscmtFormPeopleFuncPK = new KscmtFormPeopleFuncPK(domain.getCompanyId(), domain.getVerticalCalCd(),
				domain.getVerticalCalItemId(), domain.getDispOrder());
		entity.externalBudgetCd = domain.getExternalBudgetCd();
		entity.categoryAtr = domain.getCategoryAtr().value;
		entity.operatorAtr = domain.getOperatorAtr().value;
		return entity;
	}

	/**
	 * Find all Vertical Cal Set
	 */
	@Override
	public List<VerticalCalSet> findAllVerticalCalSet(String companyId) {
		return this.queryProxy().query(SELECT_ALL_GEN_VERT_SET, KscmtVerticalSet.class)
				.setParameter("companyId", companyId).getList(c -> convertToDomainVcs(c));
	}

	/**
	 * Convert to Domain Vertical Cal Set
	 * 
	 * @param kscstVerticalCalSet
	 * @return
	 */
	private VerticalCalSet convertToDomainVcs(KscmtVerticalSet kscstVerticalCalSet) {

		List<VerticalCalItem> verticalCalItems = kscstVerticalCalSet.genVertItems.stream().map(t -> {
			FormBuilt formBuilt = toDomainFormBuilt(t.formBuilt);
			FormTime formTime = toDomainFormTime(t.formTime);
			FormPeople formPeople = toDomainFormPeople(t.formPeople);
			FormulaAmount amount = toDomainFormAmount(t.amount);
			List<FormulaNumerical> numerical = toDomainFormNumer(t.numerical);
			FormulaUnitprice unitprice = toDomainFromPrice(t.price);

			return VerticalCalItem.createFromJavatype(t.kscmtVerticalItemPK.companyId,
					t.kscmtVerticalItemPK.verticalCalCd, t.kscmtVerticalItemPK.itemId, t.itemName, t.calculateAtr,
					t.displayAtr, t.cumulativeAtr, t.attributes, t.rounding, t.roundingProcessing,
					t.genVertOrder.dispOrder, formBuilt, formTime, formPeople, amount, numerical, unitprice);
		}).collect(Collectors.toList());

		VerticalCalSet verticalCalSet = VerticalCalSet.createFromJavaType(
				kscstVerticalCalSet.kscmtVerticalSetPK.companyId, kscstVerticalCalSet.kscmtVerticalSetPK.verticalCalCd,
				kscstVerticalCalSet.verticalCalName, kscstVerticalCalSet.unit, kscstVerticalCalSet.useAtr,
				kscstVerticalCalSet.assistanceTabulationAtr, verticalCalItems);

		return verticalCalSet;
	}

	/**
	 * Convert to Domain Form Time
	 * 
	 * @param entity
	 * @return author: TanLV
	 */

	private FormTime toDomainFormTime(KscmtFormTime formTime) {
		if (formTime == null) {
			return null;
		}

		List<FormTimeFunc> lst = new ArrayList<>();

		for (KscmtFormTimeFunc obj : formTime.listFormTimeFunc) {
			lst.add(toDomainFormTimeFunc(obj));
		}

		FormTime domain = FormTime.createFromJavaType(formTime.kscmtFormTimePK.companyId,
				formTime.kscmtFormTimePK.verticalCalCd, formTime.kscmtFormTimePK.verticalCalItemId,
				formTime.categoryIndicator, formTime.actualDisplayAtr, lst);

		return domain;
	}

	/**
	 * Convert to Domain Form Time Func
	 * 
	 * @param entity
	 * @return author: TanLV
	 */
	private FormTimeFunc toDomainFormTimeFunc(KscmtFormTimeFunc entity) {
		FormTimeFunc domain = FormTimeFunc.createFromJavaType(entity.kscmtFormTimeFuncPK.companyId,
				entity.kscmtFormTimeFuncPK.verticalCalCd, entity.kscmtFormTimeFuncPK.verticalCalItemId,
				entity.kscmtFormTimeFuncPK.dispOrder, entity.presetItemId, entity.attendanceItemId,
				entity.externalBudgetCd, entity.operatorAtr);
		return domain;
	}

	/**
	 * Convert to Domain Form Built
	 * 
	 * @param entity
	 * @return author: TanLV
	 */
	private FormBuilt toDomainFormBuilt(KscmtFormBuilt formBuilt) {
		if (formBuilt == null) {
			return null;
		}

		FormBuilt domain = FormBuilt.createFromJavaTypeFormBuilt(formBuilt.kscmtFormBuiltPK.companyId,
				formBuilt.kscmtFormBuiltPK.verticalCalCd, formBuilt.kscmtFormBuiltPK.verticalCalItemId,
				formBuilt.settingMethod1, formBuilt.verticalCalItem1, formBuilt.verticalInputItem1,
				formBuilt.settingMethod2, formBuilt.verticalCalItem2, formBuilt.verticalInputItem2,
				formBuilt.operatorAtr);
		return domain;
	}

	/**
	 * Find Vertical Cal Set by Cd
	 */
	@Override
	public Optional<VerticalCalSet> getVerticalCalSetByCode(String companyId, String verticalCalCd) {
		KscmtVerticalSetPK primaryKey = new KscmtVerticalSetPK(companyId, verticalCalCd);

		return this.queryProxy().find(primaryKey, KscmtVerticalSet.class).map(x -> convertToDomainVcs(x));
	}

	/**
	 * Convert to Vertical Cal Set Db Type
	 * 
	 * @param verticalCalSet
	 * @return
	 */
	private KscmtVerticalSet convertToDbTypeVcs(VerticalCalSet verticalCalSet) {
		List<KscmtVerticalItem> items = verticalCalSet.getVerticalCalItems().stream().map(x -> {
			KscmtVerticalSort kscstVerticalItemOrder = new KscmtVerticalSort(
					new KscmtVerticalSortPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(),
							x.getItemId()),
					x.getDispOrder());

			KscmtFormBuilt built = null;
			if (x.getFormBuilt() != null) {
				built = toEntityFormBuilt(x.getFormBuilt());
			}

			KscmtFormTime time = null;
			if (x.getFormTime() != null) {
				time = toEntityFormTime(x.getFormTime());
			}

			KscmtFormPeople entity = null;
			if (x.getFormPeople() != null) {
				entity = toEntityFormPeople(x.getFormPeople());
			}
			// Check Formula Amount
			KscstFormAmount amount = null;
			if (x.getFormulaAmount() != null) {
				amount = toEntityFormAmount(x.getFormulaAmount());
			}

			List<KscstFormNumerical> numerical = null;
			if (x.getNumerical() != null && x.getNumerical().size() > 0) {
				numerical = toEntityFormNumerical(x.getNumerical());
			}

			KscstFormulaUnitPrice price = null;
			if (x.getUnitprice() != null) {
				price = toEntityFormPrice(x.getUnitprice());
			}

			KscmtVerticalItemPK key = new KscmtVerticalItemPK(verticalCalSet.getCompanyId(),
					verticalCalSet.getVerticalCalCd().v(), x.getItemId());

			return new KscmtVerticalItem(key, x.getItemName(), x.getCalculateAtr().value, x.getDisplayAtr().value,
					x.getCumulativeAtr(), x.getAttributes().value, x.getRounding(), x.getRoundingProcessing(),
					kscstVerticalItemOrder, built, time, entity, amount, numerical, price);
		}).collect(Collectors.toList());

		KscmtVerticalSet kscstVerticalCalSet = new KscmtVerticalSet();

		KscmtVerticalSetPK kscmtVerticalSetPK = new KscmtVerticalSetPK(verticalCalSet.getCompanyId(),
				verticalCalSet.getVerticalCalCd().v());

		kscstVerticalCalSet.verticalCalName = verticalCalSet.getVerticalCalName().v();
		kscstVerticalCalSet.unit = verticalCalSet.getUnit().value;
		kscstVerticalCalSet.useAtr = verticalCalSet.getUseAtr().value;
		kscstVerticalCalSet.assistanceTabulationAtr = verticalCalSet.getAssistanceTabulationAtr().value;
		kscstVerticalCalSet.kscmtVerticalSetPK = kscmtVerticalSetPK;
		kscstVerticalCalSet.genVertItems = items;

		return kscstVerticalCalSet;
	}

	/**
	 * toEntityFormTime
	 * 
	 * @param formTime
	 * @return
	 */
	private KscmtFormTime toEntityFormTime(FormTime formTime) {
		val entity = new KscmtFormTime();

		entity.kscmtFormTimePK = new KscmtFormTimePK(formTime.getCompanyId(), formTime.getVerticalCalCd(),
				formTime.getVerticalCalItemId());
		entity.categoryIndicator = formTime.getCategoryIndicator().value;
		entity.actualDisplayAtr = formTime.getActualDisplayAtr().value;

		List<KscmtFormTimeFunc> lst = new ArrayList<>();

		if (formTime.getLstFormTimeFunc() != null) {
			for (FormTimeFunc item : formTime.getLstFormTimeFunc()) {
				lst.add(toEntityFormTimeFunc(item));
			}
		}

		entity.listFormTimeFunc = lst;

		return entity;
	}

	/**
	 * toEntityFormTimeFunc
	 * 
	 * @param item
	 * @return
	 */
	private KscmtFormTimeFunc toEntityFormTimeFunc(FormTimeFunc item) {
		val entity = new KscmtFormTimeFunc();

		entity.kscmtFormTimeFuncPK = new KscmtFormTimeFuncPK(item.getCompanyId(), item.getVerticalCalCd(),
				item.getVerticalCalItemId(), item.getDispOrder());
		entity.externalBudgetCd = item.getExternalBudgetCd();
		entity.attendanceItemId = item.getAttendanceItemId();
		entity.presetItemId = item.getPresetItemId();
		entity.operatorAtr = item.getOperatorAtr().value;

		return entity;
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
		KscmtVerticalSetPK kscstVerticalCalSetPK = new KscmtVerticalSetPK(verticalCalSet.getCompanyId(),
				verticalCalSet.getVerticalCalCd().v());
		KscmtVerticalSet kscstVerticalCalSet = this.queryProxy().find(kscstVerticalCalSetPK, KscmtVerticalSet.class)
				.get();

		kscstVerticalCalSet.verticalCalName = verticalCalSet.getVerticalCalName().v();
		kscstVerticalCalSet.unit = verticalCalSet.getUnit().value;
		kscstVerticalCalSet.useAtr = verticalCalSet.getUseAtr().value;
		kscstVerticalCalSet.assistanceTabulationAtr = verticalCalSet.getAssistanceTabulationAtr().value;

		List<KscmtVerticalItem> items = verticalCalSet.getVerticalCalItems().stream().map(x -> {
			KscmtVerticalSort kscstVerticalItemOrder = new KscmtVerticalSort(
					new KscmtVerticalSortPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(),
							x.getItemId()),
					x.getDispOrder());

			KscmtFormBuilt built = null;
			if (x.getFormBuilt() != null) {
				built = toEntityFormBuilt(x.getFormBuilt());
			}

			KscmtFormTime time = null;
			if (x.getFormTime() != null) {
				time = toEntityFormTime(x.getFormTime());
			}

			KscmtFormPeople entity = null;
			if (x.getFormPeople() != null) {
				entity = toEntityFormPeople(x.getFormPeople());
			}
			// Check Formula Amount
			KscstFormAmount amount = null;
			if (x.getFormulaAmount() != null) {
				amount = toEntityFormAmount(x.getFormulaAmount());
			}

			List<KscstFormNumerical> numerical = null;
			if (x.getNumerical() != null) {
				numerical = toEntityFormNumerical(x.getNumerical());
			}

			KscstFormulaUnitPrice price = null;
			if (x.getUnitprice() != null) {
				price = toEntityFormPrice(x.getUnitprice());
			}

			KscmtVerticalItemPK key = new KscmtVerticalItemPK(verticalCalSet.getCompanyId(),
					verticalCalSet.getVerticalCalCd().v(), x.getItemId());

			return new KscmtVerticalItem(key, x.getItemName(), x.getCalculateAtr().value, x.getDisplayAtr().value,
					x.getCumulativeAtr(), x.getAttributes().value, x.getRounding(), x.getRoundingProcessing(),
					kscstVerticalItemOrder, built, time, entity, amount, numerical, price);
		}).collect(Collectors.toList());

		kscstVerticalCalSet.genVertItems = items;

		this.commandProxy().update(kscstVerticalCalSet);
	}

	/**
	 * toEntityFormBuilt
	 * 
	 * @param formBuilt
	 * @return
	 */
	private KscmtFormBuilt toEntityFormBuilt(FormBuilt formBuilt) {
		KscmtFormBuilt entity = new KscmtFormBuilt();
		entity.kscmtFormBuiltPK = new KscmtFormBuiltPK(formBuilt.getCompanyId(), formBuilt.getVerticalCalCd(),
				formBuilt.getVerticalCalItemId());
		entity.settingMethod1 = formBuilt.getSettingMethod1().value;
		entity.verticalCalItem1 = formBuilt.getVerticalCalItem1();
		entity.verticalInputItem1 = formBuilt.getVerticalInputItem1();
		entity.settingMethod2 = formBuilt.getSettingMethod2().value;
		entity.verticalCalItem2 = formBuilt.getVerticalCalItem2();
		entity.verticalInputItem2 = formBuilt.getVerticalInputItem2();
		entity.operatorAtr = formBuilt.getOperatorAtr().value;

		return entity;
	}

	/**
	 * Remove Vertical Cal Set
	 */
	@Override
	public void deleteVerticalCalSet(String companyId, String verticalCalCd) {
		KscmtVerticalSetPK kscstVerticalCalSetPK = new KscmtVerticalSetPK(companyId, verticalCalCd);
		this.commandProxy().remove(KscmtVerticalSet.class, kscstVerticalCalSetPK);
	}

	/**
	 * find all form people author: Hoang Yen
	 */
	@Override
	public List<FormPeople> findAllFormPeople(String companyId) {
		return this.queryProxy().query(SELECT_PEOPLE_ITEM, KscmtFormPeople.class).setParameter("companyId", companyId)
				.getList(c -> toDomainFormPeople(c));
	}

	/**
	 * find all Formula Numerical
	 */
	@Override
	public List<FormulaNumerical> findAllFormNumber(String companyId) {
		return this.queryProxy().query(SELECT_FORMULA_NUMMER, KscstFormNumerical.class)
				.setParameter("companyId", companyId)
				.getList(x -> FormulaNumerical.createFromJavatype(x.kscstFormulaNumericalPK.companyId,
						x.kscstFormulaNumericalPK.verticalCalCd, x.kscstFormulaNumericalPK.verticalCalItemId,
						x.kscstFormulaNumericalPK.dispOrder, x.externalBudgetCd, x.operatorAtr));
	}

	/**
	 * find all form people func author: Hoang Yen
	 */
	@Override
	public List<FormPeopleFunc> findAllFormPeopleFunc(String companyId) {
		return this.queryProxy().query(SELECT_FUNC_ITEM, KscmtFormPeopleFunc.class).setParameter("companyId", companyId)
				.getList(c -> toDomainFormPeopleFunc(c));
	}

	/**
	 * Find all from Money Func
	 * 
	 * @author phongtq
	 */
	@Override
	public List<MoneyFunc> findAllMoneyFunc(String companyId) {
		return this.queryProxy().query(SELECT_MONEY_FUNC, KscmtFormMoneyFunc.class).setParameter("companyId", companyId)
				.getList(c -> toDomainFormMoney(c));
	}

	/**
	 * Find all from Formula Money
	 * 
	 * @author phongtq
	 */
	@Override
	public List<FormulaMoney> findAllFormulaMoney(String companyId, String verticalCalCd, String verticalCalItemId) {
		return this.queryProxy().query(SELECT_FORMULA_MONEY, KscmtFormMoney.class).setParameter("companyId", companyId)
				.setParameter("verticalCalCd", verticalCalCd).setParameter("verticalCalItemId", verticalCalItemId)
				.getList(c -> toDomainFormFormulaMoney(c));
	}

	/**
	 * Find all from Formula Unit Price
	 * 
	 * @author phongtq
	 */
	@Override
	public List<FormulaUnitprice> findAllFormulaPrice(String companyId, String verticalCalCd,
			String verticalCalItemId) {
		return this.queryProxy().query(SELECT_FORMULA_PRICE, KscstFormulaUnitPrice.class)
				.setParameter("companyId", companyId).setParameter("verticalCalCd", verticalCalCd)
				.setParameter("verticalCalItemId", verticalCalItemId).getList(c -> toDomainFromPrice(c));
	}

	/**
	 * Find all from Formula Amount
	 * 
	 * @author phongtq
	 */
	@Override
	public List<FormulaAmount> findAllFormulaAmount(String companyId, String verticalCalCd, String verticalCalItemId) {
		return this.queryProxy().query(SELECT_FORMULA_AMOUNT, KscstFormAmount.class)
				.setParameter("companyId", companyId).setParameter("verticalCalCd", verticalCalCd)
				.setParameter("verticalCalItemId", verticalCalItemId).getList(c -> toDomainFormAmount(c));
	}

	/**
	 * Find all from Formula Time Unit
	 * 
	 * @author phongtq
	 */
	@Override
	public List<FormulaTimeUnit> findAllFormulaTimeUnit(String companyId) {
		return this.queryProxy().query(SELECT_FORMULA_TIME_UNIT, KscstFormTimeUnit.class)
				.setParameter("companyId", companyId).getList(c -> toDomainFormFormulaTime(c));
	}

	/**
	 * Find all from Time Unit Func
	 * 
	 * @author phongtq
	 */
	@Override
	public List<TimeUnitFunc> findAllTimeUnit(String companyId, String verticalCalCd, String verticalCalItemId) {
		return this.queryProxy().query(SELECT_TIME_UNIT_FUNC, KscmtFormTimeunitFunc.class)
				.setParameter("companyId", companyId).setParameter("verticalCalCd", verticalCalCd)
				.setParameter("verticalCalItemId", verticalCalItemId).getList(c -> toDomainFormTime(c));
	}

	/**
	 * Add Formula Amount
	 * 
	 * @author phongtq
	 */
	@Override
	public void insertFromAmount(FormulaAmount formulaAmount) {
		this.commandProxy().insert(toEntityFormAmount(formulaAmount));
	}

	/**
	 * Add Formula Unit Price
	 * 
	 * @author phongtq
	 */
	@Override
	public void insertFromPrice(FormulaUnitprice price) {
		this.commandProxy().insert(toEntityFormPrice(price));
	}
}
