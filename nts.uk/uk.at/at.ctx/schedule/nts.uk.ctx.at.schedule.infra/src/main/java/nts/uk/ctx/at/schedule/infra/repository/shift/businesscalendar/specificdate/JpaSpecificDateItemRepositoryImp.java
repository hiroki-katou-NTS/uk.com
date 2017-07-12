package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.specificdate;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.specificdate.KsmstSpecificDateItem;

@Stateless
public class JpaSpecificDateItemRepositoryImp extends JpaRepository implements SpecificDateItemRepository{
	
	private static final String GET_ALL = "SELECT s FROM KsmstSpecificDateItem s WHERE s.ksmstSpecificDateItemPK.companyId = :companyId";

	@Override
	public List<SpecificDateItem> getAll(String companyId) {
		return this.queryProxy().query(GET_ALL, KsmstSpecificDateItem.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPaySettingDomain(x));
	}

	private SpecificDateItem toBonusPaySettingDomain(KsmstSpecificDateItem ksmstSpecificDateItem) {
		return SpecificDateItem.createFromJavaType(ksmstSpecificDateItem.ksmstSpecificDateItemPK.companyId,
				ksmstSpecificDateItem.ksmstSpecificDateItemPK.itemItemId, 
				ksmstSpecificDateItem.useAtr, ksmstSpecificDateItem.itemNo, ksmstSpecificDateItem.name);
	}

}
