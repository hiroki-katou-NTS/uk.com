package repository.person.info.setting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingItem;
import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingItemPk;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryCode;
import nts.uk.ctx.bs.person.dom.person.info.item.IsRequired;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemCode;
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


	private final String SEL_ALL_ITEM = "SELECT distinct ITEM.ppemtPerInfoItemPK.perInfoItemDefId, ITEM.perInfoCtgId, ITEM.itemName,"
			+ " ITEM.requiredAtr, "
			+ " SE.settingItemPk.settingId, SE.refMethodAtr, SE.saveDataType, SE.stringValue, SE.intValue, SE.dateValue,"
			+ " CM.dataType, CM.itemType , E.disporder, ITEM.itemCd, CTG.categoryCd, CM.numericItemDecimalPart, CM.numericItemIntegerPart"
			// 10 11 12 13 14
			+ " FROM  PpemtPerInfoCtg CTG INNER JOIN PpemtPerInfoItemCm CM"
			+ " ON  CTG.categoryCd = CM.ppemtPerInfoItemCmPK.categoryCd"

			+ " INNER JOIN  PpemtPerInfoItem ITEM" + " ON CM.ppemtPerInfoItemCmPK.itemCd = ITEM.itemCd"
			+ " AND CTG.ppemtPerInfoCtgPK.perInfoCtgId =  ITEM.perInfoCtgId " + " AND ITEM.perInfoCtgId =:perInfoCtgId"

			+ " INNER JOIN PpemtPerInfoItemOrder E"
			+ " ON  ITEM.ppemtPerInfoItemPK.perInfoItemDefId = E.ppemtPerInfoItemPK.perInfoItemDefId "
			+ " AND ITEM.perInfoCtgId = E.perInfoCtgId"

			+ " LEFT JOIN PpemtPersonInitValueSettingItem SE"
			+ " ON CTG.ppemtPerInfoCtgPK.perInfoCtgId = SE.settingItemPk.perInfoCtgId"
			+ " AND SE.settingItemPk.settingId =:settingId AND SE.settingItemPk.perInfoCtgId =:perInfoCtgId"
			+ " WHERE  CTG.abolitionAtr = 0 AND CTG.ppemtPerInfoCtgPK.perInfoCtgId =:perInfoCtgId"
			+ " AND (SE.settingItemPk.perInfoItemDefId = E.ppemtPerInfoItemPK.perInfoItemDefId OR SE.settingItemPk.perInfoItemDefId IS NULL)"
			+ " ORDER BY E.disporder";

	// SONNLB
	private final String SEL_ALL_INIT_ITEM = "SELECT distinct c.ppemtPerInfoItemPK.perInfoItemDefId, c.perInfoCtgId, c.itemName,"
			+ " c.requiredAtr, b.settingItemPk.settingId, b.refMethodAtr, b.saveDataType, b.stringValue, b.intValue, b.dateValue,c.itemCd"
			+ " FROM  PpemtPersonInitValueSettingItem b" + " INNER JOIN PpemtPerInfoItem c"
			+ " ON b.settingItemPk.perInfoItemDefId =  c.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg pc" + " ON b.settingItemPk.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemOrder po "
			+ " ON c.ppemtPerInfoItemPK.perInfoItemDefId = po.ppemtPerInfoItemPK.perInfoItemDefId AND c.perInfoCtgId = po.perInfoCtgId"
			+ " WHERE c.abolitionAtr = 0 AND b.settingItemPk.settingId = :settingId AND pc.categoryCd = :categoryCd  ORDER BY po.disporder";
	// SONNLB

	private final String SEL_ALL_ITEM_BY_CTG_ID = " SELECT c FROM PpemtPersonInitValueSettingItem c"
			+ " WHERE c.settingItemPk.perInfoCtgId =:perInfoCtgId AND c.settingItemPk.settingId =:settingId";

	private final String SEL_ALL_ITEM_BY_SETTINGID = " SELECT c FROM PpemtPersonInitValueSettingItem c"
			+ " WHERE c.settingItemPk.settingId =:settingId";

	private final String SEL_A_ITEM = " SELECT c FROM PpemtPersonInitValueSettingItem c"
			+ " WHERE c.settingItemPk.settingId =:settingId" + " AND c.settingItemPk.perInfoCtgId =:perInfoCtgId"
			+ " AND c.settingItemPk.perInfoItemDefId =:perInfoItemDefId";

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
		domain.setSaveDataType(
				entity.saveDataType == null ? null : EnumAdaptor.valueOf(entity.saveDataType, SaveDataType.class));

		domain.setStringValue(new StringValue(entity.stringValue == null ? "" : entity.stringValue.toString()));
		domain.setIntValue(entity.intValue == null ? null : new IntValue(entity.intValue));

		domain.setDateValue(entity.dateValue == null ? null
				: GeneralDate.fromString(String.valueOf(entity.dateValue), "yyyy-MM-dd"));

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

		if (entity[6] == null) {
			// return defaul value
			saveDataType = "1";

		} else {

			saveDataType = entity[6].toString();

		}
		domain.setSaveDataType(EnumAdaptor.valueOf(Integer.valueOf(saveDataType), SaveDataType.class));

		domain.setStringValue(new StringValue(entity[7] == null ? " " : entity[7].toString()));
		domain.setIntValue(new IntValue(new BigDecimal(entity[8] == null ? "0" : entity[8].toString())));

		String dateValue;

		if (entity[9] == null) {
			dateValue = "9999-12-21";

		} else {
			dateValue = entity[9].toString();

		}

		domain.setDateValue(GeneralDate.fromString(dateValue, "yyyy-MM-dd"));

		if (entity[10] == null) {
			domain.setDataType(0);
		} else {
			domain.setDataType(Integer.valueOf(entity[10].toString()));
		}

		if (entity[11] == null) {
			domain.setItemType(0);
		} else {
			domain.setItemType(Integer.valueOf(entity[11].toString()));
		}

		if (entity[13] != null) {
			domain.setItemCode(entity[13].toString());
		}

		if (entity[14] != null) {
			//domain.setCtgCode(entity[14].toString());
			domain.setCtgCode("CO00001");
		}
		if (entity[13] != null && entity[14] != null) {
			domain.setConstraint(PerInfoInitValueSetItem.processs("CO00001", entity[13].toString()));
		}
		
		if (entity[15] != null) {
			domain.setNumberDecimalPart(Integer.valueOf(entity[15].toString()));
		}

		if (entity[16] != null) {
			//domain.setCtgCode(entity[14].toString());
			domain.setNumberIntegerPart(Integer.valueOf(entity[16].toString()));
		}
		return domain;

	}

	/**
	 * convert from domain PerInfoInitValueSetItem to entity
	 * PpemtPersonInitValueSettingItem
	 * 
	 * @param domain
	 * @return
	 */
	private static PpemtPersonInitValueSettingItem toEntity(PerInfoInitValueSetItem domain) {
		PpemtPersonInitValueSettingItem entity = new PpemtPersonInitValueSettingItem();
		entity.settingItemPk = new PpemtPersonInitValueSettingItemPk(domain.getPerInfoItemDefId(),
				domain.getPerInfoCtgId(), domain.getSettingId());
		entity.refMethodAtr = domain.getRefMethodType().value;
		entity.saveDataType = domain.getSaveDataType() == null ? null : domain.getSaveDataType().value;
		entity.stringValue = domain.getStringValue() == null ? null : domain.getStringValue().v();
		entity.intValue = domain.getIntValue() == null ? null : domain.getIntValue().v();
		entity.dateValue = domain.getDateValue() == null ? null : domain.getDateValue().toString();
		return entity;

	}

	@Override
	public List<PerInfoInitValueSetItem> getAllItem(String settingId, String perInfoCtgId) {
		List<PerInfoInitValueSetItem> x = this.queryProxy().query(SEL_ALL_ITEM, Object[].class)
				.setParameter("perInfoCtgId", perInfoCtgId).setParameter("settingId", settingId)
				.getList(c -> toDomain(c));
		return x;

	}

	// sonnlb
	@Override
	public List<PerInfoInitValueSetItem> getAllInitItem(String settingId, String categoryCd) {
		return this.queryProxy().query(SEL_ALL_INIT_ITEM, Object[].class).setParameter("categoryCd", categoryCd)
				.setParameter("settingId", settingId).getList(c -> toInitDomain(c));

	}

	private static PerInfoInitValueSetItem toInitDomain(Object[] entity) {
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

		String saveDataType = entity[6] != null ? entity[6].toString().equals("0") ? "1" : entity[6].toString() : "1";

		domain.setSaveDataType(EnumAdaptor.valueOf(Integer.valueOf(saveDataType), SaveDataType.class));

		domain.setStringValue(new StringValue(entity[7] == null ? " " : entity[7].toString()));
		domain.setIntValue(new IntValue(new BigDecimal(entity[8] == null ? "0" : entity[8].toString())));

		String dateValue;

		if (saveDataType == "1") {
			dateValue = "9999-12-21";

		} else {
			dateValue = entity[9] == null ? "9999-12-21" : entity[9].toString();
		}

		domain.setDateValue(GeneralDate.fromString(dateValue, "yyyy-MM-dd"));

		domain.setItemCode(entity[10] == null ? "" : entity[10].toString());

		return domain;

	}

	// sonnlb
	@Override
	public Optional<PerInfoInitValueSetItem> getDetailItem(String settingId, String perInfoCtgId,
			String perInfoItemDefId) {
		// SEL_A_ITEM
		return this.queryProxy().query(SEL_A_ITEM, PpemtPersonInitValueSettingItem.class)
				.setParameter("settingId", settingId).setParameter("perInfoCtgId", perInfoCtgId)
				.setParameter("perInfoItemDefId", perInfoItemDefId).getSingle(c -> toDomain(c));
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
				.setParameter("perInfoCtgId", perInfoCtgId).setParameter("settingId", settingId)
				.getList(c -> toDomain(c));
	}

	// hoatt
	@Override
	public void deleteAllBySetId(String settingId) {
		this.getEntityManager().createQuery(DELETE_ALL_ITEM_BY_ID).setParameter("settingId", settingId).executeUpdate();
	}

	@Override
	public void addAllItem(List<PerInfoInitValueSetItem> lstItem) {
		List<PpemtPersonInitValueSettingItem> lstEntity = new ArrayList<>();
		for (PerInfoInitValueSetItem perSetItem : lstItem) {
			lstEntity.add(toEntity(perSetItem));
		}
		this.commandProxy().insertAll(lstEntity);
	}

	@Override
	public List<PerInfoInitValueSetItem> getAllInitValueItem(String settingId) {
		return this.queryProxy().query(SEL_ALL_ITEM_BY_SETTINGID, PpemtPersonInitValueSettingItem.class)
				.setParameter("settingId", settingId).getList(c -> toDomain(c));

	}

	@Override
	public void addItem(PerInfoInitValueSetItem item) {
		this.commandProxy().insert(toEntity(item));
		this.getEntityManager().flush();
	}

	@Override
	public void update(PerInfoInitValueSetItem item) {
		Optional<PpemtPersonInitValueSettingItem> itemSet = this.queryProxy()
				.find(new PpemtPersonInitValueSettingItemPk(item.getPerInfoItemDefId(), item.getPerInfoCtgId(),
						item.getSettingId()), PpemtPersonInitValueSettingItem.class);
		if (itemSet.isPresent()) {
			this.commandProxy().update(itemSet.get().updateFromDomain(item));
		} else {
			this.commandProxy().insert(toEntity(item));
		}
	}
}
