package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.Attributes;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.CalculateAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.CumulativeAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.DisplayAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.Rounding;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstVerticalCalItem;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstVerticalCalItemPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstVerticalCalSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstVerticalCalSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstVerticalItemOrder;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstVerticalItemOrderPK;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class JpaVerticalSetting extends JpaRepository implements VerticalSettingRepository {
	
	private static final String SELECT_ALL_VERTICAL_CAL_SET;
	
	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstVerticalCalSet e");
		builderString.append(" WHERE e.kscstVerticalCalSetPK.companyId = :companyId");
		SELECT_ALL_VERTICAL_CAL_SET = builderString.toString();
	}

	/**
	 * Find all Vertical Cal Set
	 */
	@Override
	public List<VerticalCalSet> findAllVerticalCalSet(String companyId) {
		return this.queryProxy().query(SELECT_ALL_VERTICAL_CAL_SET, KscstVerticalCalSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomainVcs(c));
	}

	/**
	 * Convert to Domain Vertical Cal Set
	 * @param kscstVerticalCalSet
	 * @return
	 */
	private VerticalCalSet convertToDomainVcs(KscstVerticalCalSet kscstVerticalCalSet) {
		List<VerticalCalItem> verticalCalItems = kscstVerticalCalSet.verticalCalItems.stream().map(t -> {
			return new VerticalCalItem(t.kscstVerticalCalItemPK.companyId, 
					t.kscstVerticalCalItemPK.verticalCalCd, 
					t.kscstVerticalCalItemPK.itemId, 
					t.itemName, 
					EnumAdaptor.valueOf(t.calculateAtr, CalculateAtr.class),
					EnumAdaptor.valueOf(t.displayAtr, DisplayAtr.class),
					EnumAdaptor.valueOf(t.cumulativeAtr, CumulativeAtr.class),
					EnumAdaptor.valueOf(t.attributes, Attributes.class),
					EnumAdaptor.valueOf(t.rounding, Rounding.class),
					t.verticalItemOrder.dispOrder);
			}).collect(Collectors.toList());
		
		VerticalCalSet verticalCalSet = VerticalCalSet.createFromJavaType(
				kscstVerticalCalSet.kscstVerticalCalSetPK.companyId,
				kscstVerticalCalSet.kscstVerticalCalSetPK.verticalCalCd, 
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
		KscstVerticalCalSetPK primaryKey = new KscstVerticalCalSetPK(companyId, verticalCalCd);
		
		return this.queryProxy().find(primaryKey, KscstVerticalCalSet.class)
				.map(x -> convertToDomainVcs(x));
	}

	/**
	 * Convert to Vertical Cal Set Db Type
	 * @param verticalCalSet
	 * @return
	 */
	private KscstVerticalCalSet convertToDbTypeVcs(VerticalCalSet verticalCalSet) {
		List<KscstVerticalCalItem> items = verticalCalSet.getVerticalCalItems().stream()
				.map(x -> {
					KscstVerticalItemOrder kscstVerticalItemOrder = new KscstVerticalItemOrder(
							new KscstVerticalItemOrderPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId()), x.getDispOrder());
							
					KscstVerticalCalItemPK key = new KscstVerticalCalItemPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId());
					return new KscstVerticalCalItem(key, x.getItemName(), x.getCalculateAtr().value, x.getDisplayAtr().value, x.getCumulativeAtr().value,
							x.getAttributes().value, x.getRounding().value, kscstVerticalItemOrder);
				}).collect(Collectors.toList());
				
		KscstVerticalCalSet kscstVerticalCalSet = new KscstVerticalCalSet();
		
		KscstVerticalCalSetPK kscstVerticalCalSetPK = new KscstVerticalCalSetPK(
				verticalCalSet.getCompanyId(),
				verticalCalSet.getVerticalCalCd().v());
		
		kscstVerticalCalSet.verticalCalName = verticalCalSet.getVerticalCalName().v();
		kscstVerticalCalSet.unit = verticalCalSet.getUnit().value;
		kscstVerticalCalSet.useAtr = verticalCalSet.getUseAtr().value;
		kscstVerticalCalSet.assistanceTabulationAtr = verticalCalSet.getAssistanceTabulationAtr().value;
		
		kscstVerticalCalSet.kscstVerticalCalSetPK = kscstVerticalCalSetPK;
		
		kscstVerticalCalSet.verticalCalItems = items;
		
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
		KscstVerticalCalSetPK kscstVerticalCalSetPK = new KscstVerticalCalSetPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v());
		KscstVerticalCalSet kscstVerticalCalSet = this.queryProxy().find(kscstVerticalCalSetPK, KscstVerticalCalSet.class).get();
		
		kscstVerticalCalSet.verticalCalName = verticalCalSet.getVerticalCalName().v();
		kscstVerticalCalSet.unit = verticalCalSet.getUnit().value;
		kscstVerticalCalSet.useAtr = verticalCalSet.getUseAtr().value;
		kscstVerticalCalSet.assistanceTabulationAtr = verticalCalSet.getAssistanceTabulationAtr().value;
		
		List<KscstVerticalCalItem> items = verticalCalSet.getVerticalCalItems().stream()
				.map(x -> {
					KscstVerticalItemOrder kscstVerticalItemOrder = new KscstVerticalItemOrder(
							new KscstVerticalItemOrderPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId()), x.getDispOrder());
					
					KscstVerticalCalItemPK key = new KscstVerticalCalItemPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd().v(), x.getItemId());
					return new KscstVerticalCalItem(key, x.getItemName(), x.getCalculateAtr().value, x.getDisplayAtr().value, x.getCumulativeAtr().value,
							x.getAttributes().value, x.getRounding().value, kscstVerticalItemOrder);
				}).collect(Collectors.toList());
		
		kscstVerticalCalSet.verticalCalItems = items;
		
		this.commandProxy().update(kscstVerticalCalSet);
	}

	/**
	 * Remove Vertical Cal Set
	 */
	@Override
	public void deleteVerticalCalSet(String companyId, String verticalCalCd) {
		KscstVerticalCalSetPK kscstVerticalCalSetPK = new KscstVerticalCalSetPK(companyId, verticalCalCd);
		this.commandProxy().remove(KscstVerticalCalSet.class, kscstVerticalCalSetPK);
	}

	@Override
	public List<VerticalCalItem> findAllVerticalCalItem(String companyId, String verticalCalCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVerticalCalItems(String companyId, String verticalCalCd) {
		// TODO Auto-generated method stub
		
	}
}
