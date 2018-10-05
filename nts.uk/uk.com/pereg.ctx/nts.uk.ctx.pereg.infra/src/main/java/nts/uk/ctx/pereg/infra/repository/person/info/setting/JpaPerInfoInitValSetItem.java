package nts.uk.ctx.pereg.infra.repository.person.info.setting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.setting.init.item.IntValue;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemDetail;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.item.ReferenceMethodType;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;
import nts.uk.ctx.pereg.dom.person.setting.init.item.StringValue;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem;
import nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue.PpemtPersonInitValueSettingItem;
import nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue.PpemtPersonInitValueSettingItemPk;

@Stateless
public class JpaPerInfoInitValSetItem extends JpaRepository implements PerInfoInitValueSetItemRepository {

	private static final String SEL_ALL_ITEM = "SELECT distinct ITEM.ppemtPerInfoItemPK.perInfoItemDefId, ITEM.perInfoCtgId, ITEM.itemName,"
			// 0, 1, 2
			+ " ITEM.requiredAtr, "
			// 3
			+ " CM.dataType, CM.itemType , E.disporder, ITEM.itemCd, CTG.categoryCd, CM.numericItemDecimalPart, CM.numericItemIntegerPart,"
			// 4,5,,7,8,9,10
			+ " CM.timeItemMin, CM.timeItemMax, "
			// 11, 12
			+ " CM.selectionItemRefCode, CM.selectionItemRefType, "
			// 13,14
			+ " CM.dateItemType, "
			// 15,
			+ " CM.timepointItemMin , CM.timepointItemMax, "
			// 16, 17
			+ " CM.numericItemMin, CM.numericItemMax, "
			// 18, 19
			+ " CM.stringItemType, CM.stringItemLength, CM.stringItemDataType,"
			// 20,21,22
			+ " CM.numericItemAmountAtr, CM.numericItemMinusAtr"
			// 23, 24
			+ " FROM  PpemtPerInfoCtg CTG INNER JOIN PpemtPerInfoItemCm CM"
			+ " ON  CTG.categoryCd = CM.ppemtPerInfoItemCmPK.categoryCd" + " INNER JOIN  PpemtPerInfoItem ITEM"
			+ " ON CM.ppemtPerInfoItemCmPK.itemCd = ITEM.itemCd"
			+ " AND CTG.ppemtPerInfoCtgPK.perInfoCtgId =  ITEM.perInfoCtgId " + " INNER JOIN PpemtPerInfoItemOrder E"
			+ " ON  ITEM.ppemtPerInfoItemPK.perInfoItemDefId = E.ppemtPerInfoItemPK.perInfoItemDefId "
			+ " AND ITEM.perInfoCtgId = E.perInfoCtgId" + " WHERE  CTG.abolitionAtr = 0 " + " AND CM.itemType = 2"
			+ " AND ITEM.abolitionAtr = 0 " + " AND CM.dataType <> 9 AND CM.dataType <> 10 AND CM.dataType <> 12 "
			+ " AND CTG.ppemtPerInfoCtgPK.perInfoCtgId =:perInfoCtgId" + " ORDER BY E.disporder";

	private static final String IS_EXITED_ITEM_LST_1 = "SELECT ITEM "
			+ " FROM  PpemtPerInfoItem ITEM INNER JOIN PpemtPerInfoItemCm CM"
			+ " ON  ITEM.itemCd = CM.ppemtPerInfoItemCmPK.itemCd" + " WHERE   ITEM.abolitionAtr = 0"
			+ " AND CM.itemType = 2" + " AND ITEM.perInfoCtgId IN :perInfoCtgId";
	// SONNLB
	private static final String SEL_ALL_INIT_ITEM = "SELECT distinct c.ppemtPerInfoItemPK.perInfoItemDefId, c.perInfoCtgId, c.itemName,"
			+ " c.requiredAtr, b.settingItemPk.settingId, b.refMethodAtr,b.saveDataType, b.stringValue, b.intValue, b.dateValue,c.itemCd , pc.categoryCd,pm.dataType ,pm.selectionItemRefType,pm.itemParentCd,pm.dateItemType,pm.selectionItemRefCode"
			+ " FROM  PpemtPersonInitValueSettingItem b" + " INNER JOIN PpemtPerInfoItem c"
			+ " ON b.settingItemPk.perInfoItemDefId =  c.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg pc" + " ON b.settingItemPk.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm pm"
			+ " ON c.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoItemOrder po "
			+ " ON c.ppemtPerInfoItemPK.perInfoItemDefId = po.ppemtPerInfoItemPK.perInfoItemDefId AND c.perInfoCtgId = po.perInfoCtgId"
			+ " WHERE b.settingItemPk.settingId = :settingId AND b.settingItemPk.perInfoCtgId = :perInfoCtgId AND pc.cid = :cid  ORDER BY po.disporder";
	// SONNLB

	private static final String SEL_ALL_ITEM_BY_CTG_ID = " SELECT c FROM PpemtPersonInitValueSettingItem c"
			+ " WHERE c.settingItemPk.perInfoCtgId =:perInfoCtgId AND c.settingItemPk.settingId =:settingId";

	private static final String SEL_ALL_ITEM_BY_SETTINGID = " SELECT c FROM PpemtPersonInitValueSettingItem c"
			+ " WHERE c.settingItemPk.settingId =:settingId";

	private static final String SEL_A_ITEM = " SELECT c FROM PpemtPersonInitValueSettingItem c"
			+ " WHERE c.settingItemPk.settingId =:settingId" + " AND c.settingItemPk.perInfoCtgId =:perInfoCtgId"
			+ " AND c.settingItemPk.perInfoItemDefId =:perInfoItemDefId";

	private static final String DELETE_ALL_ITEM_BY_ID = "DELETE FROM PpemtPersonInitValueSettingItem c"
			+ " WHERE c.settingItemPk.settingId =:settingId";

	private static final String SEL_ALL_ITEM_DATA = "SELECT id.settingItemPk.perInfoItemDefId"
			+ " FROM PpemtPersonInitValueSettingItem id"
			+ " INNER JOIN PpemtPerInfoItem pi ON id.settingItemPk.perInfoItemDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg pc ON pi.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm pm ON pi.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd"
			+ " WHERE pm.ppemtPerInfoItemCmPK.itemCd =:itemCd" + " AND pi.perInfoCtgId IN :perInfoCtgId";

	private static PerInfoInitValueSetItem toDomain(PpemtPersonInitValueSettingItem entity) {
		PerInfoInitValueSetItem domain = new PerInfoInitValueSetItem();
		domain.setPerInfoItemDefId(entity.settingItemPk.perInfoItemDefId);
		domain.setPerInfoCtgId(entity.settingItemPk.perInfoCtgId);
		domain.setSettingId(entity.settingItemPk.settingId);
		domain.setRefMethodType(EnumAdaptor.valueOf(entity.refMethodAtr, ReferenceMethodType.class));
		domain.setSaveDataType(
				entity.saveDataType == null ? null : EnumAdaptor.valueOf(entity.saveDataType, SaveDataType.class));
		domain.setStringValue(new StringValue(entity.stringValue == null ? "" : entity.stringValue.toString()));
		domain.setIntValue(entity.intValue == null ? null : new IntValue(entity.intValue));
		domain.setDateValue(entity.dateValue == null ? null : entity.dateValue);
		return domain;
	}

	private static PersonInfoItemDefinition toDomainString(PpemtPerInfoItem entity) {
		PersonInfoItemDefinition domain = new PersonInfoItemDefinition();
		domain.setPerInfoCategoryId(entity.perInfoCtgId);
		return domain;
	}

	private static PerInfoInitValueSetItemDetail toDomainOfItemDefined(Object[] entity) {
		PerInfoInitValueSetItemDetail domain = new PerInfoInitValueSetItemDetail();
		domain.setPerInfoItemDefId(entity[0].toString()); // 0
		domain.setPerInfoCtgId(entity[1].toString());// 1
		domain.setItemName(entity[2] == null ? "" : entity[2].toString());// 2
		domain.setIsRequired(entity[3] == null ? 0 : Integer.valueOf(entity[3].toString())); // 3

		if (entity[4] == null) {
			domain.setDataType(0);
		} else {
			domain.setDataType(Integer.valueOf(entity[4].toString()));
		}

		if (entity[5] == null) {
			domain.setItemType(0);
		} else {
			domain.setItemType(Integer.valueOf(entity[5].toString()));
		}

		if (entity[7] != null) {
			domain.setItemCode(entity[7].toString());
			if (entity[7].toString().contains("IS")) {
				domain.setFixedItem(true);
			} else {
				domain.setFixedItem(false);
			}
		}

		if (entity[8] != null) {
			// domain.setCtgCode(entity[14].toString());
			domain.setCtgCode(entity[8].toString());
		}
		if (entity[7] != null && entity[8] != null) {
			domain.setConstraint(PerInfoInitValueSetItem.processs(entity[8].toString(), entity[7].toString()));
		}

		if (entity[4] != null) {
			if (entity[4].toString().equals("2")) {
				if (entity[9] != null) {
					domain.setNumberDecimalPart(Integer.valueOf(entity[9].toString()));
				}

				if (entity[10] != null) {
					domain.setNumberIntegerPart(Integer.valueOf(entity[10].toString()));
				}
			}

		}

		if (entity[11] != null) {
			domain.setTimeItemMin(Integer.valueOf(entity[11].toString()));
		}

		if (entity[12] != null) {
			domain.setTimeItemMax(Integer.valueOf(entity[12].toString()));
		}

		if (entity[13] != null) {
			domain.setSelectionItemId(entity[13].toString());
		}

		if (entity[14] != null) {
			domain.setSelectionItemRefType(Integer.valueOf(entity[14].toString()));
		}

		if (entity[15] != null) {
			domain.setDateType(Integer.valueOf(entity[15].toString()));
		}

		if (entity[16] != null) {
			domain.setTimepointItemMin(Integer.valueOf(entity[16].toString()));
		}

		if (entity[17] != null) {
			domain.setTimepointItemMax(Integer.valueOf(entity[17].toString()));
		}

		if (entity[18] != null) {
			domain.setNumericItemMin(new BigDecimal(entity[18].toString()));
		}

		if (entity[19] != null) {
			domain.setNumericItemMax(new BigDecimal(entity[19].toString()));
		}

		if (entity[20] != null) {
			domain.setStringItemType(Integer.valueOf(entity[20].toString()));
		}

		if (entity[21] != null) {
			domain.setStringItemLength(Integer.valueOf(entity[21].toString()));
		}

		if (entity[22] != null) {
			domain.setStringItemDataType(Integer.valueOf(entity[22].toString()));
		}

		if (entity[23] != null) {
			domain.setNumberItemAmount(Integer.valueOf(entity[23].toString()));
		}
		
		if (entity[24] != null) {
			domain.setNumberItemMinus(Integer.valueOf(entity[24].toString()));
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
		entity.refMethodAtr = domain.getRefMethodType() == null? 1: domain.getRefMethodType().value;
		entity.saveDataType = domain.getSaveDataType() == null ? null : domain.getSaveDataType().value;
		entity.stringValue = domain.getStringValue() == null ? null : domain.getStringValue().v();
		entity.intValue = domain.getIntValue() == null ? null : domain.getIntValue().v();
		entity.dateValue = domain.getDateValue() == null ? null : domain.getDateValue();
		return entity;

	}

	@Override
	public List<PerInfoInitValueSetItemDetail> getAllItem(String settingId, String perInfoCtgId) {
		List<PerInfoInitValueSetItemDetail> itemLst = this.queryProxy().query(SEL_ALL_ITEM, Object[].class)
				.setParameter("perInfoCtgId", perInfoCtgId).getList(c -> toDomainOfItemDefined(c));
		List<PerInfoInitValueSetItem> itemInit = this.getAllInitValueItem(perInfoCtgId, settingId);
		List<PerInfoInitValueSetItemDetail> itemResult = itemLst.stream().map(c -> {
			itemInit.stream().forEach(init -> {
				if (c.getPerInfoItemDefId().equals(init.getPerInfoItemDefId())) {

					c.setSettingId(init.getSettingId());
					c.setRefMethodType(init.getRefMethodType().value);
					if (init.getRefMethodType().value == ReferenceMethodType.FIXEDVALUE.value) {
						c.setSaveDataType(init.getSaveDataType().value);
						switch (init.getSaveDataType()) {
						case STRING:
							c.setStringValue(init.getStringValue().v());
							c.setDateValue(null);
							c.setIntValue(null);
							break;
						case NUMBERIC:
							c.setIntValue(init.getIntValue() == null? null: init.getIntValue().v());
							c.setDateValue(null);
							c.setStringValue(null);
							break;
						case DATE:
							c.setDateValue(init.getDateValue());
							c.setIntValue(null);
							c.setStringValue(null);
							break;
						}
					}
				}

			});

			return c;
		}).collect(Collectors.toList());

		return itemResult;

	}

	// sonnlb
	@Override
	public List<PerInfoInitValueSetItemDetail> getAllInitItem(String settingId, String perInfoCtgId, String cid) {
		return this.queryProxy().query(SEL_ALL_INIT_ITEM, Object[].class).setParameter("perInfoCtgId", perInfoCtgId)
				.setParameter("settingId", settingId)
				.setParameter("cid", cid)
				.getList(c -> toInitDomain(c));

	}

	private static PerInfoInitValueSetItemDetail toInitDomain(Object[] entity) {
		PerInfoInitValueSetItemDetail domain = new PerInfoInitValueSetItemDetail();
		domain.setPerInfoItemDefId(entity[0].toString());
		domain.setPerInfoCtgId(entity[1].toString());
		domain.setItemName(entity[2] == null ? "" : entity[2].toString());
		domain.setIsRequired(Integer.valueOf(entity[3].toString()));
		domain.setSettingId(entity[4] == null ? "" : entity[4].toString());

		String refMethod;

		if (entity[5].toString().equals("0")) {
			// return No setting type
			refMethod = "1";

		} else {

			refMethod = entity[5].toString();

		}

		domain.setRefMethodType(Integer.valueOf(refMethod));

		String saveDataType = entity[6] != null ? entity[6].toString().equals("0") ? "1" : entity[6].toString() : "1";

		domain.setSaveDataType(Integer.valueOf(saveDataType));

		domain.setStringValue(entity[7] == null ? "" : entity[7].toString());
		domain.setIntValue(entity[8] == null ? null : new BigDecimal(entity[8].toString()));

		String dateValue;

		if (saveDataType == "1") {
			dateValue = "9999/12/21";

		} else {
			dateValue = entity[9] == null ? "9999/12/21" : entity[9].toString();
		}

		domain.setDateValue(GeneralDate.fromString(dateValue, "yyyy/MM/dd"));

		domain.setItemCode(entity[10] == null ? null : entity[10].toString());

		domain.setCtgCode(entity[11] == null ? null : entity[11].toString());

		domain.setDataType(new Integer(entity[12] == null ? "0" : entity[12].toString()));

		domain.setSelectionItemRefType(new Integer(entity[13] == null ? "1" : entity[13].toString()));

		domain.setItemParentCd(entity[14] == null ? null : entity[14].toString());

		domain.setDateType(new Integer(entity[15] == null ? "1" : entity[15].toString()));

		domain.setSelectionItemRefCd(entity[16] == null ? null : entity[16].toString());

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
	public boolean isExist(String settingId, String perInfoCtgId) {
		List<PerInfoInitValueSetItem> itemFilter = this.getAllInitValueItem(perInfoCtgId, settingId).stream()
				.filter(c -> (c.getRefMethodType().value != 1)).collect(Collectors.toList());

		if (itemFilter.size() > 0) {

			return true;
		}
		return false;
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

	@Override
	public List<String> isExistItem(List<String> perInfoCtgId) {
		List<PersonInfoItemDefinition> item = this.queryProxy().query(IS_EXITED_ITEM_LST_1, PpemtPerInfoItem.class)
				.setParameter("perInfoCtgId", perInfoCtgId).getList(c -> toDomainString(c));

		if (item.size() > 0) {
			List<String> itemIdList = item.stream().map(c -> c.getPerInfoCategoryId()).distinct()
					.collect(Collectors.toList());
			return itemIdList;
		}
		return new ArrayList<>();
	}

	@Override
	public boolean hasItemData(String itemCd, List<String> perInfoCtgId) {

		List<String> itemLst = this.queryProxy().query(SEL_ALL_ITEM_DATA, String.class).setParameter("itemCd", itemCd)
				.setParameter("perInfoCtgId", perInfoCtgId).getList();
		if (itemLst.size() > 0) {
			return true;
		}
		return false;
	}
}
