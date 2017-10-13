package repository.person.info.setting;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.person.dom.person.info.item.IsRequired;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.IntValue;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.ReferenceMethodType;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.StringValue;

@Stateless
public class JpaPerInfoInitValSetItem extends JpaRepository implements PerInfoInitValueSetItemRepository {

	private final String CHECK_ITEM_IS_EXITED = "SELECT a.ppemtPerInfoItemPK.perInfoItemDefId from PpemtPerInfoItem a"
			+ " INNER JOIN PpemtPerInfoItemOrder b"
			+ " ON a.ppemtPerInfoItemPK.perInfoItemDefId = b.ppemtPerInfoItemPK.perInfoItemDefId "
			+ " AND a.perInfoCtgId = b.perInfoCtgId " + " WHERE a.abolitionAtr = 0"
			+ " AND a.perInfoCtgId =:perInfoCtgId" + " ORDER BY b.disporder";

	private final String SEL_ALL_ITEM = " SELECT c.ppemtPerInfoItemPK.perInfoItemDefId, c.perInfoCtgId, c.itemName,"
			+ " c.requiredAtr, b.settingItemPk.settingId, b.refMethodAtr, b.saveDataType, b.stringValue, b.intValue, b.dateValue "
			+ " FROM PpemtPerInfoItem c " + " LEFT JOIN PpemtPersonInitValueSettingItem b"
			+ " ON b.settingItemPk.perInfoItemDefId = c.ppemtPerInfoItemPK.perInfoItemDefId "
			+ " AND b.settingItemPk.perInfoCtgId = c.perInfoCtgId" + " WHERE c.abolitionAtr = 0"
			+ " AND c.perInfoCtgId =:perInfoCtgId";

	private static PerInfoInitValueSetItem toDomain(Object[] entity) {
		PerInfoInitValueSetItem domain = new PerInfoInitValueSetItem();
		domain.setPerInfoItemDefId(entity[0].toString());
		domain.setPerInfoCtgId(entity[1].toString());
		domain.setItemName(entity[2] == null ? "" : entity[2].toString());
		domain.setIsRequired(EnumAdaptor.valueOf(Integer.valueOf(entity[3].toString()), IsRequired.class));
		domain.setSettingId(entity[4] == null ? "" : entity[4].toString());
		
		String refMethod;
		if (entity[5].toString().equals("0")) {
			
			refMethod = "8";

		} else {
			refMethod = entity[5].toString();

		}
		domain.setRefMethodType(EnumAdaptor.valueOf(Integer.valueOf(refMethod), ReferenceMethodType.class));

		String saveDataType;
		if (entity[6].toString().equals("0")) {
			saveDataType = "1";

		} else {
			saveDataType = entity[6].toString();

		}
		domain.setSaveDataType(EnumAdaptor.valueOf(Integer.valueOf(saveDataType), SaveDataType.class));
		domain.setStringValue(new StringValue(entity[7] == null ? " " : entity[7].toString()));
		domain.setIntValue(new IntValue(new BigDecimal(entity[8] == null ? "" : entity[8].toString())));

		String dateValue;
		if (entity[6].toString().equals("0")) {
			dateValue = "9999-12-21";

		} else {
			dateValue = entity[9].toString();

		}

		domain.setDateValue(GeneralDate.fromString(dateValue, "yyyy-MM-dd"));
		return domain;

	}

	@Override
	public List<PerInfoInitValueSetItem> getAllItem(String perInfoCtgId) {
		return this.queryProxy().query(SEL_ALL_ITEM, Object[].class).setParameter("perInfoCtgId", perInfoCtgId)
				.getList(c -> toDomain(c));

	}

	@Override
	public PerInfoInitValueSetItem getDetailItem(String initValueSettingCtgId, String perInfoItemDefId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExist(String perInfoCtgId) {
		List<Object[]> itemDefLst = this.queryProxy().query(CHECK_ITEM_IS_EXITED, Object[].class)
				.setParameter("perInfoCtgId", perInfoCtgId).getList();

		if (CollectionUtil.isEmpty(itemDefLst)) {
			return false;
		}
		return true;
	}

}
