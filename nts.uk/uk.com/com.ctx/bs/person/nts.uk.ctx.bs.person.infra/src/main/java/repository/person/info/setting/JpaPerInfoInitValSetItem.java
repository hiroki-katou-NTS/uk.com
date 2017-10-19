package repository.person.info.setting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingCtg;
import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingCtgPk;
import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingItem;
import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingItemPk;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.person.dom.person.info.item.IsRequired;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtg;
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

	private final String SEL_ALL_ITEM_BY_CTG_ID = " SELECT c FROM PpemtPersonInitValueSettingItem c"
			+ " c.settingItemPk.perInfoCtgId =:perInfoCtgId AND c.settingItemPk.settingId =:settingId";
	
	private final String DELETE_ALL_ITEM_BY_ID = "DELETE FROM PpemtPersonInitValueSettingItem c"
			+ " WHERE c.settingItemPk.settingId =:settingId";

	private static PerInfoInitValueSetItem toDomain(PpemtPersonInitValueSettingItem entity) {
		PerInfoInitValueSetItem domain = new PerInfoInitValueSetItem();
		domain.setPerInfoItemDefId(entity.settingItemPk.perInfoItemDefId);
		domain.setPerInfoCtgId(entity.settingItemPk.perInfoCtgId);
		domain.setSettingId(entity.settingItemPk.settingId);
		domain.setItemName("");
		domain.setIsRequired(EnumAdaptor.valueOf(0, IsRequired.class));

		domain.setRefMethodType(EnumAdaptor.valueOf(entity.refMethodAtr, ReferenceMethodType.class));
		domain.setSaveDataType(EnumAdaptor.valueOf(entity.saveDataType, SaveDataType.class));

		domain.setStringValue(new StringValue(entity.stringValue == null ? "" : entity.stringValue.toString()));
		domain.setIntValue(new IntValue(new BigDecimal(entity.intValue)));

		domain.setDateValue(GeneralDate.fromString(String.valueOf(entity.dateValue), "yyyy-MM-dd"));

		return domain;

	}

	private static PerInfoInitValueSetItem toDomain(Object[] entity) {
		PerInfoInitValueSetItem domain = new PerInfoInitValueSetItem();
		domain.setPerInfoItemDefId(entity[0].toString());
		domain.setPerInfoCtgId(entity[1].toString());
		domain.setItemName(entity[2] == null ? "" : entity[2].toString());
		domain.setIsRequired(EnumAdaptor.valueOf(Integer.valueOf(entity[3].toString()), IsRequired.class));
		domain.setSettingId(entity[4] == null ? "" : entity[4].toString());

		String refMethod;

		if (entity[5].toString().equals("0")) {
			// return No setting type
			refMethod = "1";

		} else {

			refMethod = entity[5].toString();

		}

		domain.setRefMethodType(EnumAdaptor.valueOf(Integer.valueOf(refMethod), ReferenceMethodType.class));

		String saveDataType;

		if (entity[6].toString().equals("0")) {
			// return defaul value
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

	@Override
	public void delete(String perInfoItemDefId, String perInfoCtgId, String settingId) {
		this.commandProxy().remove(PpemtPersonInitValueSettingItem.class,
				new PpemtPersonInitValueSettingItemPk(perInfoItemDefId, perInfoCtgId, settingId));

	}

	@Override
	public List<PerInfoInitValueSetItem> getAllInitValueItem(String perInfoCtgId, String settingId) {
		return this.queryProxy().query(SEL_ALL_ITEM_BY_CTG_ID, PpemtPersonInitValueSettingItem.class)
				.setParameter("perInfoCtgId", perInfoCtgId)
				.setParameter("settingId", settingId)
				.getList(c -> toDomain(c));
	}

		//hoatt
		@Override
		public void deleteAllBySetId(String settingId) {
			this.getEntityManager().createQuery(DELETE_ALL_ITEM_BY_ID)
			.setParameter("settingId", settingId)
			.executeUpdate();
		}

		@Override
		public void addAllItem(List<PerInfoInitValueSetItem> lstItem) {
			List<PpemtPersonInitValueSettingItem> lstEntity = new ArrayList<>();
			for (PerInfoInitValueSetItem perSetItem : lstItem) {
				lstEntity.add(toEntity(perSetItem));
			}
			this.commandProxy().insertAll(lstEntity);
		}
		/**
		 * convert from domain PerInfoInitValueSetItem to entity PpemtPersonInitValueSettingItem
		 * @param domain
		 * @return
		 */
		private static PpemtPersonInitValueSettingItem toEntity(PerInfoInitValueSetItem domain) {
			PpemtPersonInitValueSettingItem entity = new PpemtPersonInitValueSettingItem();
			entity.settingItemPk = new PpemtPersonInitValueSettingItemPk(domain.getPerInfoItemDefId(),
					domain.getPerInfoCtgId(),					
					domain.getSettingId());
			entity.refMethodAtr = domain.getRefMethodType().value;
			entity.saveDataType = domain.getSaveDataType().value;
			entity.stringValue = domain.getStringValue().v();
			entity.intValue = domain.getIntValue().v().intValue();
			entity.dateValue = domain.getDateValue().toString();
			return entity;

		}
}
