package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.specificdate;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.specificdate.KsmstSpecificDateItem;

@Stateless
public class JpaSpecificDateItemRepositoryImp extends JpaRepository implements SpecificDateItemRepository{
	
	private static final String SELECT_NO_WHERE = "SELECT s FROM KsmstSpecificDateItem s";
	private static final String GET_ALL = SELECT_NO_WHERE
			+ " WHERE s.ksmstSpecificDateItemPK.companyId = :companyId";
	private static final String GET_BY_USE = SELECT_NO_WHERE 
			+ " WHERE s.ksmstSpecificDateItemPK.companyId = :companyId"
			+ " AND s.useAtr = :useAtr";

	/**
	 * convert Entity to Domain
	 * @param ksmstSpecificDateItem
	 * @return
	 */
	private SpecificDateItem toBonusPaySettingDomain(KsmstSpecificDateItem ksmstSpecificDateItem) {
		return SpecificDateItem.createFromJavaType(ksmstSpecificDateItem.ksmstSpecificDateItemPK.companyId,
				ksmstSpecificDateItem.ksmstSpecificDateItemPK.itemItemId, 
				ksmstSpecificDateItem.useAtr, ksmstSpecificDateItem.itemNo, ksmstSpecificDateItem.name);
	}
	
	/**
	 * Get ALL Specific Date Item by ComId 
	 */
	@Override
	public List<SpecificDateItem> getAll(String companyId) {
		return this.queryProxy().query(GET_ALL, KsmstSpecificDateItem.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPaySettingDomain(x));
	}

	
	/**
	 * Get Specific Date Item by ComId and is USE
	 */
	@Override
	public List<SpecificDateItem> getSpecifiDateByUse(String companyId, int useAtr) {
		return this.queryProxy().query(GET_BY_USE, KsmstSpecificDateItem.class)
				.setParameter("companyId", companyId)
				.setParameter("useAtr", useAtr)
				.getList(x -> this.toBonusPaySettingDomain(x));
	}

}
