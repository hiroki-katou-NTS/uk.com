package nts.uk.ctx.at.schedule.infra.repository.scheduleitemmanagement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItem;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItemManagementRepository;
import nts.uk.ctx.at.schedule.infra.entity.scheduleitemmanagement.KscmtScheduleItem;
import nts.uk.ctx.at.schedule.infra.entity.scheduleitemmanagement.KscmtScheduleItemPK;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class JpaScheduleItemManagementRepository extends JpaRepository implements ScheduleItemManagementRepository {

	private static final String SELECT_ALL_SCHEDULE_ITEM;
	
	private static final String SELECT_ALL_SCHEDULE_ITEM_BY_ATR;
	
	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtScheduleItem e");
		builderString.append(" WHERE e.kscmtScheduleItemPK.companyId = :companyId");
		SELECT_ALL_SCHEDULE_ITEM = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtScheduleItem e");
		builderString.append(" WHERE e.kscmtScheduleItemPK.companyId = :companyId AND e.scheduleItemAtr = :scheduleItemAtr");
		SELECT_ALL_SCHEDULE_ITEM_BY_ATR = builderString.toString();
	}
	
	/**
	 * Find all 
	 */
	@Override
	public List<ScheduleItem> findAllScheduleItem(String companyId) {
		return this.queryProxy().query(SELECT_ALL_SCHEDULE_ITEM, KscmtScheduleItem.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomainScheduleItem(c));
	}
	
	/**
	 * Find all by attribute
	 */
	@Override
	public List<ScheduleItem> findAllScheduleItemByAtr(String companyId, int attribute) {
		return this.queryProxy().query(SELECT_ALL_SCHEDULE_ITEM_BY_ATR, KscmtScheduleItem.class)
				.setParameter("companyId", companyId)
				.setParameter("scheduleItemAtr", attribute)
				.getList(c -> convertToDomainScheduleItem(c));
	}

	/**
	 * Find by Id
	 */
	@Override
	public Optional<ScheduleItem> getScheduleItemById(String companyId, String scheduleItemId) {
		KscmtScheduleItemPK primaryKey = new KscmtScheduleItemPK(companyId, scheduleItemId);
		
		return this.queryProxy().find(primaryKey, KscmtScheduleItem.class)
				.map(x -> convertToDomainScheduleItem(x));
	}

	/**
	 * Add new
	 */
	@Override
	public void addScheduleItem(ScheduleItem scheduleItem) {
		this.commandProxy().insert(convertToDbTypeScheduleItem(scheduleItem));	
	}

	/**
	 * Update item
	 */
	@Override
	public void updateScheduleItem(ScheduleItem scheduleItem) {
		KscmtScheduleItemPK key = new KscmtScheduleItemPK(scheduleItem.getCompanyId(), scheduleItem.getScheduleItemId());
		ScheduleItem item = this.queryProxy().find(key, ScheduleItem.class).get();
		
		item.scheduleItemName = scheduleItem.getScheduleItemName();
		item.scheduleItemAtr = scheduleItem.getScheduleItemAtr();
		
		this.commandProxy().update(item);
	}

	/**
	 * Delete item
	 */
	@Override
	public void deleteScheduleItem(String companyId, String scheduleItemId) {
		KscmtScheduleItemPK key = new KscmtScheduleItemPK(companyId, scheduleItemId);
		this.commandProxy().remove(KscmtScheduleItem.class, key);
	}
	
	/**
	 * Convert to Domain
	 * @param KscmtScheduleItem
	 * @return
	 */
	private ScheduleItem convertToDomainScheduleItem(KscmtScheduleItem kscmtScheduleItem) {		
		ScheduleItem scheduleItem = ScheduleItem.createFromJavaType(
				kscmtScheduleItem.kscmtScheduleItemPK.companyId,
				kscmtScheduleItem.kscmtScheduleItemPK.scheduleItemId, 
				kscmtScheduleItem.scheduleItemName,
				kscmtScheduleItem.scheduleItemAtr,
				kscmtScheduleItem.scheduleItemOrder != null ? kscmtScheduleItem.scheduleItemOrder.dispOrder : 0);
		
		return scheduleItem;
	}
	
	/**
	 * Convert to Db Type
	 * @param ScheduleItem
	 * @return
	 */
	private KscmtScheduleItem convertToDbTypeScheduleItem(ScheduleItem scheduleItem) {				
		KscmtScheduleItem kscmtScheduleItem = new KscmtScheduleItem();
		
		KscmtScheduleItemPK kscmtScheduleItemPK = new KscmtScheduleItemPK(
				scheduleItem.getCompanyId(),
				scheduleItem.getScheduleItemId());
		
		kscmtScheduleItem.scheduleItemName = scheduleItem.getScheduleItemName();
		kscmtScheduleItem.scheduleItemAtr = scheduleItem.getScheduleItemAtr().value;
		
		kscmtScheduleItem.kscmtScheduleItemPK = kscmtScheduleItemPK;
		
		return kscmtScheduleItem;
	}
}
