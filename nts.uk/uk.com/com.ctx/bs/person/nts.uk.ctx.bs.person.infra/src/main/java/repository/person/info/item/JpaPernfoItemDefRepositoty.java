package repository.person.info.item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.bs.person.dom.person.info.item.PernfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;

@Stateless
public class JpaPernfoItemDefRepositoty extends JpaRepository implements PernfoItemDefRepositoty {

	private final static String SELECT_ITEMS_BY_CATEGORY_ID_QUERY = "SELECT i.perInfoItemDefId, i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " ic.itemParentCd, ic.systemRequiredAtr, ic.requireChangabledAtr, ic.fixedAtr, ic.itemType,"
			+ " ic.dataType, ic.timeItemMin, ic.timeItemMax, ic.timepointItemMin, ic.timepointItemMax, ic.dateItemType,"
			+ " ic.stringItemType, ic.stringItemLength, ic.stringItemDataType, ic.numericItemMin, ic.numericItemMax, ic.numericItemAmountAtr,"
			+ " ic.numericItemMinusAtr, ic.numericItemDecimalPart, ic.numericItemIntegerPart, ic.selectionItemRefType, ic.selectionItemRefCode, i.perInfoCtgId"
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd "
			+ " AND c.cid LIKE '%'+ ic.ppemtPerInfoItemCmPK.contractCd AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd"
			+ " WHERE i.perInfoCtgId = :perInfoCtgId";

	private final static String SELECT_ITEM_BY_ITEM_ID_QUERY = "SELECT i.perInfoItemDefId, i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " ic.itemParentCd, ic.systemRequiredAtr, ic.requireChangabledAtr, ic.fixedAtr, ic.itemType,"
			+ " ic.dataType, ic.timeItemMin, ic.timeItemMax, ic.timepointItemMin, ic.timepointItemMax, ic.dateItemType,"
			+ " ic.stringItemType, ic.stringItemLength, ic.stringItemDataType, ic.numericItemMin, ic.numericItemMax, ic.numericItemAmountAtr,"
			+ " ic.numericItemMinusAtr, ic.numericItemDecimalPart, ic.numericItemIntegerPart, ic.selectionItemRefType, ic.selectionItemRefCode, i.perInfoCtgId"
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd "
			+ " AND c.cid LIKE '%'+ ic.ppemtPerInfoItemCmPK.contractCd AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd"
			+ " WHERE i.ppemtPerInfoItemPK.perInfoItemDefId = :perInfoItemDefId";

	private final static String SELECT_ITEMS_BY_LIST_ITEM_ID_QUERY = "SELECT i.perInfoItemDefId, i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " ic.itemParentCd, ic.systemRequiredAtr, ic.requireChangabledAtr, ic.fixedAtr, ic.itemType,"
			+ " ic.dataType, ic.timeItemMin, ic.timeItemMax, ic.timepointItemMin, ic.timepointItemMax, ic.dateItemType,"
			+ " ic.stringItemType, ic.stringItemLength, ic.stringItemDataType, ic.numericItemMin, ic.numericItemMax, ic.numericItemAmountAtr,"
			+ " ic.numericItemMinusAtr, ic.numericItemDecimalPart, ic.numericItemIntegerPart, ic.selectionItemRefType, ic.selectionItemRefCode, i.perInfoCtgId"
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd "
			+ " AND c.cid LIKE '%'+ ic.ppemtPerInfoItemCmPK.contractCd AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd"
			+ " WHERE i.ppemtPerInfoItemPK.perInfoItemDefId IN : listItemDefId";

	@Override
	public List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCategoryId) {
		return this.queryProxy().query(SELECT_ITEMS_BY_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("perInfoCtgId", perInfoCategoryId).getList(i -> {
					return createDomainFromEntity(i);
				});
	}

	@Override
	public Optional<PersonInfoItemDefinition> getPerInfoItemDefById(String perInfoItemDefId) {
		return this.queryProxy().query(SELECT_ITEM_BY_ITEM_ID_QUERY, Object[].class)
				.setParameter("perInfoItemDefId", perInfoItemDefId).getSingle(i -> {
					return createDomainFromEntity(i);
				});
	}

	@Override
	public List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> listItemDefId) {
		return this.queryProxy().query(SELECT_ITEMS_BY_LIST_ITEM_ID_QUERY, Object[].class)
				.setParameter("listItemDefId", listItemDefId).getList(i -> {
					return createDomainFromEntity(i);
				});
	}

	private PersonInfoItemDefinition createDomainFromEntity(Object[] i) {
		String perInfoItemDefId = i[0].toString();
		String itemCode = i[1].toString();
		String itemName = i[2].toString();
		int isAbolition = Integer.parseInt(i[3].toString());
		int isRequired = Integer.parseInt(i[4].toString());
		String itemParentCode = i[5].toString();
		int systemRequired = Integer.parseInt(i[6].toString());
		int requireChangable = Integer.parseInt(i[7].toString());
		int isFixed = Integer.parseInt(i[8].toString());
		int itemType = Integer.parseInt(i[9].toString());
		BigDecimal dataType = StringUtils.isBlank(i[10].toString()) ? null : new BigDecimal(i[10].toString());
		BigDecimal timeItemMin = StringUtils.isBlank(i[11].toString()) ? null : new BigDecimal(i[11].toString());
		BigDecimal timeItemMax = StringUtils.isBlank(i[12].toString()) ? null : new BigDecimal(i[12].toString());
		BigDecimal timepointItemMin = StringUtils.isBlank(i[13].toString()) ? null : new BigDecimal(i[13].toString());
		BigDecimal timepointItemMax = StringUtils.isBlank(i[14].toString()) ? null : new BigDecimal(i[14].toString());
		BigDecimal dateItemType = StringUtils.isBlank(i[15].toString()) ? null : new BigDecimal(i[15].toString());
		BigDecimal stringItemType = StringUtils.isBlank(i[16].toString()) ? null : new BigDecimal(i[16].toString());
		BigDecimal stringItemLength = StringUtils.isBlank(i[17].toString()) ? null : new BigDecimal(i[17].toString());
		BigDecimal stringItemDataType = StringUtils.isBlank(i[18].toString()) ? null : new BigDecimal(i[18].toString());
		BigDecimal numericItemMin = StringUtils.isBlank(i[19].toString()) ? null : new BigDecimal(i[19].toString());
		BigDecimal numericItemMax = StringUtils.isBlank(i[20].toString()) ? null : new BigDecimal(i[20].toString());
		BigDecimal numericItemAmount = StringUtils.isBlank(i[21].toString()) ? null : new BigDecimal(i[21].toString());
		BigDecimal numericItemMinus = StringUtils.isBlank(i[22].toString()) ? null : new BigDecimal(i[22].toString());
		BigDecimal numericItemDecimalPart = StringUtils.isBlank(i[23].toString()) ? null
				: new BigDecimal(i[23].toString());
		BigDecimal numericItemIntegerPart = StringUtils.isBlank(i[24].toString()) ? null
				: new BigDecimal(i[24].toString());
		BigDecimal selectionItemRefType = StringUtils.isBlank(i[25].toString()) ? null
				: new BigDecimal(i[25].toString());
		String selectionItemRefCode = i[26].toString();
		String perInfoCategoryId = i[27].toString();

		PersonInfoItemDefinition item = PersonInfoItemDefinition.createFromEntity(perInfoItemDefId, perInfoCategoryId,
				itemCode, itemParentCode, itemName, isAbolition, isFixed, isRequired, systemRequired, requireChangable);

		if (itemType == 2) {
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
				dataTypeState = DataTypeState.createTimePointItem(timepointItemMin.longValue(),
						timepointItemMax.longValue());
				break;
			case 6:
				ReferenceTypeState referenceTypeState = null;
				if (selectionItemRefType.intValue() == 1) {
					referenceTypeState = ReferenceTypeState.createMasterReferenceCondition(selectionItemRefCode);
				} else if (selectionItemRefType.intValue() == 2) {
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
}
