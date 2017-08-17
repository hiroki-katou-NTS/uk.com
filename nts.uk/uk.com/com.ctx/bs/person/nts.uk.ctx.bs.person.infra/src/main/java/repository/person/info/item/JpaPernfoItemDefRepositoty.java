package repository.person.info.item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.info.item.PpemtPerInfoItem;
import entity.person.info.item.PpemtPerInfoItemCm;
import entity.person.info.item.PpemtPerInfoItemCmPK;
import entity.person.info.item.PpemtPerInfoItemOrder;
import entity.person.info.item.PpemtPerInfoItemPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.bs.person.dom.person.info.item.PernfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.bs.person.dom.person.info.order.PerInfoItemDefOrder;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.CodeNameReferenceType;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.EnumReferenceCondition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.MasterReferenceCondition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.bs.person.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.bs.person.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.bs.person.dom.person.info.timepointitem.TimePointItem;

@Stateless
public class JpaPernfoItemDefRepositoty extends JpaRepository implements PernfoItemDefRepositoty {

	private final static String SELECT_ITEMS_BY_CATEGORY_ID_QUERY = "SELECT i.ppemtPerInfoItemPK.perInfoItemDefId,"
			+ " i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " ic.itemParentCd, ic.systemRequiredAtr, ic.requireChangabledAtr, ic.fixedAtr, ic.itemType,"
			+ " ic.dataType, ic.timeItemMin, ic.timeItemMax, ic.timepointItemMin, ic.timepointItemMax, ic.dateItemType,"
			+ " ic.stringItemType, ic.stringItemLength, ic.stringItemDataType, ic.numericItemMin, ic.numericItemMax, ic.numericItemAmountAtr,"
			+ " ic.numericItemMinusAtr, ic.numericItemDecimalPart, ic.numericItemIntegerPart,"
			+ " ic.selectionItemRefType, ic.selectionItemRefCode, i.perInfoCtgId"
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd"
			+ " AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd INNER JOIN PpemtPerInfoItemOrder io"
			+ " ON io.ppemtPerInfoItemPK.perInfoItemDefId = i.ppemtPerInfoItemPK.perInfoItemDefId AND io.perInfoCtgId = i.perInfoCtgId"
			+ " WHERE ic.ppemtPerInfoItemCmPK.contractCd = :contractCd AND i.perInfoCtgId = :perInfoCtgId AND ic.itemParentCd IS NULL"
			+ " ORDER BY io.disporder";

	private final static String SELECT_ITEM_BY_ITEM_ID_QUERY = "SELECT i.ppemtPerInfoItemPK.perInfoItemDefId,"
			+ " i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " ic.itemParentCd, ic.systemRequiredAtr, ic.requireChangabledAtr, ic.fixedAtr, ic.itemType,"
			+ " ic.dataType, ic.timeItemMin, ic.timeItemMax, ic.timepointItemMin, ic.timepointItemMax, ic.dateItemType,"
			+ " ic.stringItemType, ic.stringItemLength, ic.stringItemDataType, ic.numericItemMin, ic.numericItemMax, ic.numericItemAmountAtr,"
			+ " ic.numericItemMinusAtr, ic.numericItemDecimalPart, ic.numericItemIntegerPart,"
			+ " ic.selectionItemRefType, ic.selectionItemRefCode, i.perInfoCtgId"
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd "
			+ " AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd"
			+ " WHERE ic.ppemtPerInfoItemCmPK.contractCd = :contractCd AND i.ppemtPerInfoItemPK.perInfoItemDefId = :perInfoCtgId";

	private final static String SELECT_ITEMS_BY_LIST_ITEM_ID_QUERY = "SELECT i.ppemtPerInfoItemPK.perInfoItemDefId,"
			+ " i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " ic.itemParentCd, ic.systemRequiredAtr, ic.requireChangabledAtr, ic.fixedAtr, ic.itemType,"
			+ " ic.dataType, ic.timeItemMin, ic.timeItemMax, ic.timepointItemMin, ic.timepointItemMax, ic.dateItemType,"
			+ " ic.stringItemType, ic.stringItemLength, ic.stringItemDataType, ic.numericItemMin, ic.numericItemMax, ic.numericItemAmountAtr,"
			+ " ic.numericItemMinusAtr, ic.numericItemDecimalPart, ic.numericItemIntegerPart,"
			+ " ic.selectionItemRefType, ic.selectionItemRefCode, i.perInfoCtgId"
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd "
			+ " AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd"
			+ " WHERE ic.ppemtPerInfoItemCmPK.contractCd = :contractCd AND i.ppemtPerInfoItemPK.perInfoItemDefId IN :listItemDefId";

	private final static String SELECT_ITEMS_NAME_QUERY = "SELECT i.itemName, io.disporder FROM PpemtPerInfoItem i"
			+ " INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd"
			+ " AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd INNER JOIN PpemtPerInfoItemOrder io"
			+ " ON io.ppemtPerInfoItemPK.perInfoItemDefId = i.ppemtPerInfoItemPK.perInfoItemDefId AND io.perInfoCtgId = i.perInfoCtgId"
			+ " WHERE ic.ppemtPerInfoItemCmPK.contractCd = :contractCd AND i.perInfoCtgId = :perInfoCtgId AND ic.itemParentCd IS NULL "
			+ " ORDER BY io.disporder";

	private final static String SELECT_GET_ITEM_CODE_LASTEST_QUERY = "SELECT i.ppemtPerInfoItemCmPK.itemCd PpemtPerInfoItemCm i"
			+ " WHERE i.ppemtPerInfoItemCmPK.contractCd = :contractCd AND i.ppemtPerInfoItemCmPK.categoryCd = :categoryCd"
			+ " ORDER BY i.ppemtPerInfoItemCmPK.itemCd DESC";

	private final static String SELECT_GET_DISPORDER_ITEM_QUERY = "SELECT od.disporder PpemtPerInfoItemOrder od"
			+ " WHERE od.perInfoCtgId = :perInfoCtgId ORDER BY od.disporder DESC";

	private final static String SELECT_CHECK_ITEM_NAME_QUERY = "SELECT i.itemName"
			+ " FROM PpemtPerInfoItem i WHERE i.perInfoCtgId = :perInfoCtgId AND i.itemName = :itemName";

	private final static String SELECT_ALL_ITEM_ORDER_BY_CTGID_QUERY = "SELECT o FROM PpemtPerInfoItemOrder o"
			+ " WHERE o.perInfoCtgId = :perInfoCtgId";

	private final static String SELECT_ITEM_DISPORDER_BY_KEY_QUERY = "SELECT o.disporder FROM PpemtPerInfoItemOrder o"
			+ " WHERE o.perInfoCtgId = :perInfoCtgId AND o.ppemtPerInfoItemPK.perInfoItemDefId = :perInfoItemDefId";

	// private final static String SELECT_ITEM_SET_QUERY = "SELECT
	// ic.ppemtPerInfoItemCmPK. FROM PpemtPerInfoItemCm ic"
	// + " WHERE ic.itemParentCd = :itemParentCd";

	@Override
	public List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCtgId, String contractCd) {
		return this.queryProxy().query(SELECT_ITEMS_BY_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("perInfoCtgId", perInfoCtgId).getList(i -> {
					return createDomainFromEntity(i);
				});
	}

	@Override
	public Optional<PersonInfoItemDefinition> getPerInfoItemDefById(String perInfoItemDefId, String contractCd) {
		return this.queryProxy().query(SELECT_ITEM_BY_ITEM_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("perInfoCtgId", perInfoItemDefId).getSingle(i -> {
					return createDomainFromEntity(i);
				});
	}

	@Override
	public List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> listItemDefId, String contractCd) {
		return this.queryProxy().query(SELECT_ITEMS_BY_LIST_ITEM_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("listItemDefId", listItemDefId).getList(i -> {
					return createDomainFromEntity(i);
				});
	}

	@Override
	public List<String> getPerInfoItemsName(String perInfoCtgId, String contractCd) {
		return this.queryProxy().query(SELECT_ITEMS_NAME_QUERY, Object[].class).setParameter("contractCd", contractCd)
				.setParameter("perInfoCtgId", perInfoCtgId).getList(i -> {
					return i[0].toString();
				});
	}

	@Override
	public void addPerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd) {
		this.commandProxy().insert(createPerInfoItemDefCmFromDomain(perInfoItemDef, contractCd));
		this.commandProxy().insert(createPerInfoItemDefFromDomain(perInfoItemDef));
		addOrderItemRoot(perInfoItemDef.getPerInfoItemDefId(), perInfoItemDef.getPerInfoCategoryId());

	}

	@Override
	public void addPerInfoItemDefByCtgIdList(PersonInfoItemDefinition perInfoItemDef, List<String> perInfoCtgId) {
		this.commandProxy().insertAll(perInfoCtgId.stream().map(i -> {
			return createPerInfoItemDefFromDomainWithCtgId(perInfoItemDef, i);
		}).collect(Collectors.toList()));
		addOrderItemWithCtgIdList(perInfoItemDef.getPerInfoItemDefId(), perInfoCtgId);
	}

	@Override
	public void updatePerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd) {
		this.commandProxy().update(createPerInfoItemDefCmFromDomain(perInfoItemDef, contractCd));
		this.commandProxy().update(createPerInfoItemDefFromDomain(perInfoItemDef));
	}

	@Override
	public boolean checkItemNameIsUnique(String perInfoCtgId, String newItemName) {
		List<String> itemNames = this.queryProxy().query(SELECT_CHECK_ITEM_NAME_QUERY, String.class)
				.setParameter("perInfoCtgId", perInfoCtgId).setParameter("itemName", newItemName).getList();
		if (itemNames == null || itemNames.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public String getPerInfoItemCodeLastest(String contractCd, String categoryCd) {
		List<String> itemCodeLastest = this.getEntityManager()
				.createQuery(SELECT_GET_ITEM_CODE_LASTEST_QUERY, String.class).setParameter("contractCd", contractCd)
				.setParameter("categoryCd", categoryCd).setMaxResults(1).getResultList();
		if (itemCodeLastest != null && !itemCodeLastest.isEmpty()) {
			return itemCodeLastest.get(0);
		}
		return null;
	}

	@Override
	public List<PerInfoItemDefOrder> getPerInfoItemDefOrdersByCtgId(String perInfoCtgId) {
		return this.queryProxy().query(SELECT_ALL_ITEM_ORDER_BY_CTGID_QUERY, PpemtPerInfoItemOrder.class)
				.setParameter("perInfoCtgId", perInfoCtgId).getList(o -> createPerInfoItemDefOrderFromEntity(o));
	}

	@Override
	public int getItemDispOrderBy(String perInfoCtgId, String perInfoItemDefId) {
		return this.queryProxy().query(SELECT_ITEM_DISPORDER_BY_KEY_QUERY, Integer.class)
				.setParameter("perInfoCtgId", perInfoCtgId).setParameter("perInfoItemDefId", perInfoItemDefId)
				.getSingle().orElse(0);
	}

	private void addOrderItemRoot(String perInfoItemDefId, String perInfoCtgId) {
		int newdisOrderLastest = getDispOrderLastestItemOfCtg(perInfoCtgId) + 1;
		this.commandProxy().insert(createItemOrder(perInfoItemDefId, perInfoCtgId, newdisOrderLastest));
	}

	private void addOrderItemWithCtgIdList(String perInfoItemDefId, List<String> perInfoCtgId) {
		this.commandProxy().insertAll(perInfoCtgId.stream().map(cid -> {
			int newdisOrderLastest = getDispOrderLastestItemOfCtg(perInfoItemDefId) + 1;
			return createItemOrder(perInfoItemDefId, cid, newdisOrderLastest);
		}).collect(Collectors.toList()));
	}

	private int getDispOrderLastestItemOfCtg(String perInfoCtgId) {
		List<Integer> dispOrderLastests = this.getEntityManager()
				.createQuery(SELECT_GET_DISPORDER_ITEM_QUERY, Integer.class).setParameter("perInfoCtgId", perInfoCtgId)
				.setMaxResults(1).getResultList();
		if (dispOrderLastests != null && !dispOrderLastests.isEmpty()) {
			return dispOrderLastests.get(0);
		}
		return 0;
	}

	private PpemtPerInfoItemOrder createItemOrder(String perInfoItemDefId, String perInfoCtgId, int dispOrder) {
		PpemtPerInfoItemPK perInfoItemPK = new PpemtPerInfoItemPK(perInfoItemDefId);
		return new PpemtPerInfoItemOrder(perInfoItemPK, perInfoCtgId, dispOrder);
	}

	private PersonInfoItemDefinition createDomainFromEntity(Object[] i) {
		String perInfoItemDefId = String.valueOf(i[0]);
		String itemCode = String.valueOf(i[1]);
		String itemName = String.valueOf(i[2]);
		int isAbolition = Integer.parseInt(String.valueOf(i[3]));
		int isRequired = Integer.parseInt(String.valueOf(i[4]));
		String itemParentCode = (i[5] == null) ? null : String.valueOf(i[5]);
		int systemRequired = Integer.parseInt(String.valueOf(i[6]));
		int requireChangable = Integer.parseInt(String.valueOf(i[7]));
		int isFixed = Integer.parseInt(String.valueOf(i[8]));
		int itemType = Integer.parseInt(String.valueOf(i[9]));
		BigDecimal dataType = i[10] == null ? null : new BigDecimal(String.valueOf(i[10]));
		BigDecimal timeItemMin = i[11] == null ? null : new BigDecimal(String.valueOf(i[11]));
		BigDecimal timeItemMax = i[12] == null ? null : new BigDecimal(String.valueOf(i[12]));
		BigDecimal timepointItemMin = i[13] == null ? null : new BigDecimal(String.valueOf(i[13]));
		BigDecimal timepointItemMax = i[14] == null ? null : new BigDecimal(String.valueOf(i[14]));
		BigDecimal dateItemType = i[15] == null ? null : new BigDecimal(String.valueOf(i[15]));
		BigDecimal stringItemType = i[16] == null ? null : new BigDecimal(String.valueOf(i[16]));
		BigDecimal stringItemLength = i[17] == null ? null : new BigDecimal(String.valueOf(i[17]));
		BigDecimal stringItemDataType = i[18] == null ? null : new BigDecimal(String.valueOf(i[18]));
		BigDecimal numericItemMin = i[19] == null ? null : new BigDecimal(String.valueOf(i[19]));
		BigDecimal numericItemMax = i[20] == null ? null : new BigDecimal(String.valueOf(i[20]));
		BigDecimal numericItemAmount = i[21] == null ? null : new BigDecimal(String.valueOf(i[21]));
		BigDecimal numericItemMinus = i[22] == null ? null : new BigDecimal(String.valueOf(i[22]));
		BigDecimal numericItemDecimalPart = i[23] == null ? null : new BigDecimal(String.valueOf(i[23]));
		BigDecimal numericItemIntegerPart = i[24] == null ? null : new BigDecimal(String.valueOf(i[24]));
		BigDecimal selectionItemRefType = i[25] == null ? null : new BigDecimal(String.valueOf(i[25]));
		String selectionItemRefCode = String.valueOf(i[26]);
		String perInfoCategoryId = String.valueOf(i[27]);

		PersonInfoItemDefinition item = PersonInfoItemDefinition.createFromEntity(perInfoItemDefId, perInfoCategoryId,
				itemCode, itemParentCode, itemName, isAbolition, isFixed, isRequired, systemRequired, requireChangable);
		if (itemType == ItemType.SINGLE_ITEM.value) {
			DataTypeState dataTypeState = null;
			switch (dataType.intValue()) {
			case 1:
				dataTypeState = DataTypeState.createStringItem(stringItemLength.intValue(), stringItemType.intValue(),
						stringItemDataType.intValue());
				break;
			case 2:
				dataTypeState = DataTypeState.createNumericItem(numericItemMinus.intValue(),
						numericItemAmount.intValue(), numericItemIntegerPart.intValue(),
						numericItemDecimalPart.intValue(), numericItemMin, numericItemMax);
				break;
			case 3:
				dataTypeState = DataTypeState.createDateItem(dateItemType.intValue());
				break;
			case 4:
				dataTypeState = DataTypeState.createTimeItem(timeItemMin.intValue(), timeItemMax.intValue());
				break;
			case 5:
				dataTypeState = DataTypeState.createTimePointItem(timepointItemMin.intValue(),
						timepointItemMax.intValue());
				break;
			case 6:
				ReferenceTypeState referenceTypeState = null;
				if (selectionItemRefType.intValue() == ReferenceTypes.DESIGNATED_MASTER.value) {
					referenceTypeState = ReferenceTypeState.createMasterReferenceCondition(selectionItemRefCode);
				} else if (selectionItemRefType.intValue() == ReferenceTypes.CODE_NAME.value) {
					referenceTypeState = ReferenceTypeState.createCodeNameReferenceType(selectionItemRefCode);
				} else {
					referenceTypeState = ReferenceTypeState.createEnumReferenceCondition(selectionItemRefCode);
				}
				dataTypeState = DataTypeState.createSelectionItem(referenceTypeState);
				break;
			default:
				break;
			}
			item.setItemTypeState(ItemTypeState.createSingleItem(dataTypeState));
		} else {
			item.setItemTypeState(ItemTypeState.createSetItem(null));
		}
		return item;
	}

	private PpemtPerInfoItem createPerInfoItemDefFromDomain(PersonInfoItemDefinition perInfoItemDef) {
		PpemtPerInfoItemPK perInfoItemPK = new PpemtPerInfoItemPK(perInfoItemDef.getPerInfoItemDefId());
		return new PpemtPerInfoItem(perInfoItemPK, perInfoItemDef.getPerInfoCategoryId(),
				perInfoItemDef.getItemCode().v(), perInfoItemDef.getItemName().v(),
				perInfoItemDef.getIsAbolition().value, perInfoItemDef.getIsRequired().value);
	}

	private PpemtPerInfoItem createPerInfoItemDefFromDomainWithCtgId(PersonInfoItemDefinition perInfoItemDef,
			String perInfoCtgId) {
		PpemtPerInfoItemPK perInfoItemPK = new PpemtPerInfoItemPK(IdentifierUtil.randomUniqueId());
		return new PpemtPerInfoItem(perInfoItemPK, perInfoCtgId, perInfoItemDef.getItemCode().v(),
				perInfoItemDef.getItemName().v(), perInfoItemDef.getIsAbolition().value,
				perInfoItemDef.getIsRequired().value);
	}

	private PpemtPerInfoItemCm createPerInfoItemDefCmFromDomain(PersonInfoItemDefinition perInfoItemDef,
			String contractCd) {
		PpemtPerInfoItemCmPK perInfoItemCmPK = new PpemtPerInfoItemCmPK(contractCd,
				perInfoItemDef.getPerInfoCategoryId(), perInfoItemDef.getItemCode().v());

		int itemType = perInfoItemDef.getItemTypeState().getItemType().value;
		BigDecimal dataType = null;
		BigDecimal timeItemMin = null;
		BigDecimal timeItemMax = null;
		BigDecimal timepointItemMin = null;
		BigDecimal timepointItemMax = null;
		BigDecimal dateItemType = null;
		BigDecimal stringItemType = null;
		BigDecimal stringItemLength = null;
		BigDecimal stringItemDataType = null;
		BigDecimal numericItemMin = null;
		BigDecimal numericItemMax = null;
		BigDecimal numericItemAmountAtr = null;
		BigDecimal numericItemMinusAtr = null;
		BigDecimal numericItemDecimalPart = null;
		BigDecimal numericItemIntegerPart = null;
		BigDecimal selectionItemRefType = null;
		String selectionItemRefCode = null;

		if (itemType == ItemType.SINGLE_ITEM.value) {
			SingleItem singleItem = (SingleItem) perInfoItemDef.getItemTypeState();
			DataTypeState dataTypeState = singleItem.getDataTypeState();
			dataType = new BigDecimal(dataTypeState.getDataTypeValue().value);
			switch (dataType.intValue()) {
			case 1:
				StringItem stringItem = (StringItem) dataTypeState;
				stringItemType = new BigDecimal(stringItem.getDataTypeValue().value);
				stringItemLength = new BigDecimal(stringItem.getStringItemLength().v());
				stringItemDataType = new BigDecimal(stringItem.getStringItemDataType().value);
				break;
			case 2:
				NumericItem numericItem = (NumericItem) dataTypeState;
				numericItemMin = numericItem.getNumericItemMin().v();
				numericItemMax = numericItem.getNumericItemMax().v();
				numericItemAmountAtr = new BigDecimal(numericItem.getNumericItemAmount().value);
				numericItemMinusAtr = new BigDecimal(numericItem.getNumericItemMinus().value);
				numericItemDecimalPart = new BigDecimal(numericItem.getDecimalPart().v());
				numericItemIntegerPart = new BigDecimal(numericItem.getIntegerPart().v());
				break;
			case 3:
				DateItem dateItem = (DateItem) dataTypeState;
				dateItemType = new BigDecimal(dateItem.getDateItemType().value);
				break;
			case 4:
				TimeItem timeItem = (TimeItem) dataTypeState;
				timeItemMin = new BigDecimal(timeItem.getMin().v());
				timeItemMax = new BigDecimal(timeItem.getMax().v());
				break;
			case 5:
				TimePointItem timePointItem = (TimePointItem) dataTypeState;
				timepointItemMin = new BigDecimal(timePointItem.getTimePointItemMin().v());
				timepointItemMax = new BigDecimal(timePointItem.getTimePointItemMax().v());
				break;
			case 6:
				SelectionItem selectionItem = (SelectionItem) dataTypeState;
				ReferenceTypeState rtypeState = selectionItem.getReferenceTypeState();
				selectionItemRefType = new BigDecimal(rtypeState.getReferenceType().value);
				if (rtypeState.getReferenceType() == ReferenceTypes.DESIGNATED_MASTER) {
					MasterReferenceCondition masterref = (MasterReferenceCondition) rtypeState;
					selectionItemRefCode = masterref.getMasterType().v();
				} else if (rtypeState.getReferenceType() == ReferenceTypes.CODE_NAME) {
					CodeNameReferenceType codeNameRef = (CodeNameReferenceType) rtypeState;
					selectionItemRefCode = codeNameRef.getTypeCode().v();
				} else {
					EnumReferenceCondition enumRef = (EnumReferenceCondition) rtypeState;
					selectionItemRefCode = enumRef.getEnumName().v();
				}
				break;
			}
		}
		return new PpemtPerInfoItemCm(perInfoItemCmPK, perInfoItemDef.getItemParentCode().v(),
				perInfoItemDef.getSystemRequired().value, perInfoItemDef.getRequireChangable().value,
				perInfoItemDef.getIsFixed().value, itemType, dataType, timeItemMin, timeItemMax, timepointItemMin,
				timepointItemMax, dateItemType, stringItemType, stringItemLength, stringItemDataType, numericItemMin,
				numericItemMax, numericItemAmountAtr, numericItemMinusAtr, numericItemDecimalPart,
				numericItemIntegerPart, selectionItemRefType, selectionItemRefCode);
	}

	private PerInfoItemDefOrder createPerInfoItemDefOrderFromEntity(PpemtPerInfoItemOrder order) {
		return PerInfoItemDefOrder.createFromJavaType(order.ppemtPerInfoItemPK.perInfoItemDefId, order.perInfoCtgId,
				order.disporder);
	}

}
