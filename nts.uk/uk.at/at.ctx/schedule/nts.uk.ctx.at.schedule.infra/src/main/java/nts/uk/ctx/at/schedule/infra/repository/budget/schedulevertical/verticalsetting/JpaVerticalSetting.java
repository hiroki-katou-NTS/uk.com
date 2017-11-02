package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.verticalsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import lombok.experimental.var;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.Attributes;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.CalculateAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.CumulativeAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.DisplayAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeople;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeopleFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.Rounding;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeople;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeopleFunc;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeopleFuncPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtFormPeoplePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertItem;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertItemPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertOrder;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscmtGenVertOrderPK;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class JpaVerticalSetting extends JpaRepository implements VerticalSettingRepository {
	
	private static final String SELECT_ALL_GEN_VERT_SET;
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
	}
	
	/**
	 * convert form Form People entity to Form People domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static FormPeople toDomainFormPeople(KscmtFormPeople entity){
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
			return new VerticalCalItem(t.kscmtGenVertItemPK.companyId, 
					t.kscmtGenVertItemPK.verticalCalCd, 
					t.kscmtGenVertItemPK.itemId, 
					t.itemName, 
					EnumAdaptor.valueOf(t.calculateAtr, CalculateAtr.class),
					EnumAdaptor.valueOf(t.displayAtr, DisplayAtr.class),
					EnumAdaptor.valueOf(t.cumulativeAtr, CumulativeAtr.class),
					EnumAdaptor.valueOf(t.attributes, Attributes.class),
					EnumAdaptor.valueOf(t.rounding, Rounding.class),
					t.genVertOrder.dispOrder,
					formPeople);
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
					KscmtGenVertItemPK key = new KscmtGenVertItemPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId());
					return new KscmtGenVertItem(key, x.getItemName(), x.getCalculateAtr().value, x.getDisplayAtr().value, x.getCumulativeAtr().value,
							x.getAttributes().value, x.getRounding().value, kscstVerticalItemOrder, entity);
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
					KscmtGenVertItemPK key = new KscmtGenVertItemPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId());
					return new KscmtGenVertItem(key, x.getItemName(), x.getCalculateAtr().value, x.getDisplayAtr().value, x.getCumulativeAtr().value,
							x.getAttributes().value, x.getRounding().value, kscstVerticalItemOrder, entity);
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
