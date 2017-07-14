package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.specificdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.specificdate.KsmstSpecificDateItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.specificdate.KsmstSpecificDateItemPK;

@Stateless
public class JpaSpecificDateItemRepositoryImp extends JpaRepository implements SpecificDateItemRepository {

	private static final String GET_ALL = "SELECT s FROM KsmstSpecificDateItem s WHERE s.ksmstSpecificDateItemPK.companyId = :companyId";
	private static final String GET_BY_USE = "SELECT s FROM KsmstSpecificDateItem s WHERE s.ksmstSpecificDateItemPK.companyId = :companyId AND s.useAtr = :useAtr";
	private static final String GET_BY_LIST_CODE = GET_ALL + " AND s.ksmstSpecificDateItemPK.itemItemId IN :lstSpecificDateItem";
	/**
	 * Entity to Domain
	 * 
	 * @param ksmstSpecificDateItem
	 * @return
	 */
	private SpecificDateItem toBonusPaySettingDomain(KsmstSpecificDateItem ksmstSpecificDateItem) {
		return SpecificDateItem.createFromJavaType(ksmstSpecificDateItem.ksmstSpecificDateItemPK.companyId,
				ksmstSpecificDateItem.ksmstSpecificDateItemPK.itemItemId, ksmstSpecificDateItem.useAtr,
				ksmstSpecificDateItem.itemNo, ksmstSpecificDateItem.name);
	}

	/**
	 * hoatt convert domain SpecificDateItem to entity KsmstSpecificDateItem
	 * 
	 * @param domain
	 * @return
	 */
	private static KsmstSpecificDateItem toEntity(SpecificDateItem domain) {
		val entity = new KsmstSpecificDateItem();
		entity.ksmstSpecificDateItemPK = new KsmstSpecificDateItemPK(domain.getCompanyId(), domain.getTimeItemId());
		entity.itemNo = domain.getSpecificDateItemNo().v();
		entity.name = domain.getSpecificName().v();
		entity.useAtr = BigDecimal.valueOf(domain.getUseAtr().value);
		return entity;
	}

	@Override
	public List<SpecificDateItem> getAll(String companyId) {
		return this.queryProxy().query(GET_ALL, KsmstSpecificDateItem.class).setParameter("companyId", companyId)
				.getList(x -> this.toBonusPaySettingDomain(x));
	}

	@Override
	public List<SpecificDateItem> getSpecifiDateByUse(String companyId, int useAtr) {
		return this.queryProxy().query(GET_BY_USE, KsmstSpecificDateItem.class).setParameter("companyId", companyId)
				.setParameter("useAtr", useAtr).getList(x -> this.toBonusPaySettingDomain(x));
	}

	/**
	 * hoatt
	 * update list Specific Date Item
	 * @param lstSpecificDateItem
	 */
	@Override
	public void updateSpecificDateItem(List<SpecificDateItem> lstSpecificDateItem) {
		List<KsmstSpecificDateItem> lstEntity = new ArrayList<>();
		for (SpecificDateItem specificDateItem : lstSpecificDateItem) {
			KsmstSpecificDateItem b = toEntity(specificDateItem);
			KsmstSpecificDateItem x = this.queryProxy().find(b.ksmstSpecificDateItemPK, KsmstSpecificDateItem.class).get();
			x.setUseAtr(b.useAtr);
			x.setItemNo(b.itemNo);
			x.setName(b.name);
			lstEntity.add(x);
		}
		this.commandProxy().updateAll(lstEntity);
	}
	/**
	 * hoatt
	 * add list Specific Date Item
	 * @param lstSpecificDateItem
	 */
	@Override
	public void addSpecificDateItem(List<SpecificDateItem> lstSpecificDateItem) {
		List<KsmstSpecificDateItem> lstEntity = new ArrayList<>();
		for (SpecificDateItem specificDateItem : lstSpecificDateItem) {
			lstEntity.add(toEntity(specificDateItem));
		}
		this.commandProxy().insertAll(lstEntity);
	}
	/**
	 * hoatt
	 * get list Specifi Date By List Code
	 * @param companyId
	 * @param lstSpecificDateItem
	 * @return
	 */
	@Override
	public List<SpecificDateItem> getSpecifiDateByListCode(String companyId, List<String> lstSpecificDateItem) {
		return this.queryProxy().query(GET_BY_LIST_CODE, KsmstSpecificDateItem.class)
				.setParameter("companyId", companyId)
				.setParameter("lstSpecifiDateItem", lstSpecificDateItem)
				.getList(c->toBonusPaySettingDomain(c));
	}

}
