package nts.uk.ctx.sys.log.app.command.pereg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ReviseInfo;
import nts.uk.shr.pereg.app.ItemLog;
import nts.uk.shr.pereg.app.SaveDataType;

@Value
public class PersonCategoryCorrectionLogParameter implements IPeregCorrection, Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private final String categoryId;
	private final String categoryName;
	private final InfoOperateAttr infoOperateAttr;
	private final List<PersonCorrectionItemInfo> itemInfos;
	private final TargetDataKey targetKey;
	private final Optional<ReviseInfo> reviseInfo;
	private final String hashID;

	public PersonCategoryCorrectionLogParameter(String categoryId, String categoryName, InfoOperateAttr infoOperateAttr,
			List<PersonCorrectionItemInfo> itemInfos, TargetDataKey targetKey, Optional<ReviseInfo> reviseInfo) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.infoOperateAttr = infoOperateAttr;
		this.itemInfos = itemInfos;
		this.targetKey  = targetKey;
		this.reviseInfo = reviseInfo;
		
		this.hashID = IdentifierUtil.randomUniqueId();
	}

	public CategoryCorrectionLog toCategoryInfo() {
		return new CategoryCorrectionLog(this.categoryId, this.categoryName, this.infoOperateAttr, this.targetKey,
				mapToItemInfo(this.itemInfos), this.reviseInfo);
	}

	public CategoryCorrectionLog toCategoryInfoCPS002(String categoryId, String categoryName, InfoOperateAttr infoOperateAttr,
			TargetDataKey targetKey, List<ItemInfo> itemInfos, Optional<ReviseInfo> reviseInfo) {
		return new CategoryCorrectionLog(categoryId, categoryName, infoOperateAttr, targetKey, itemInfos, reviseInfo);
	}

	private List<ItemInfo> mapToItemInfo(List<PersonCorrectionItemInfo> itemInfos) {
		return itemInfos.stream().map(i -> {
			return i.toCreateItemInfoCPS002();
		}).collect(Collectors.toList());
	}

	@Override
	public String getHashID() {
		return "CATEGORY_" + this.hashID;
	}

	@Value
	public static class PersonCorrectionItemInfo implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		private final String itemId;
		private final String itemName;
		private final String valueBefore;
		private final String viewValueBefore;
		private final String valueAfter;
		private final String viewValueAfter;
		private final Integer valueType;

		public ItemInfo toCreateItemInfo() {
			return ItemInfo.create("", this.itemId, this.itemName, converType(valueType),
					convertValue(valueType, this.valueBefore), viewValueBefore,
					convertValue(valueType, this.valueAfter), viewValueAfter);
		}

		public ItemInfo toCreateItemInfoCPS002() {
			return ItemInfo.create("", this.itemId, this.itemName, converType(valueType),
					convertValue(valueType, this.valueBefore), this.viewValueBefore,
					convertValue(valueType, this.valueAfter), this.viewValueAfter);
		}
		
		public static  PersonCorrectionItemInfo createItemInfoToItemLog(ItemLog item) {
			return new PersonCorrectionItemInfo(item.getItemId(), item.getItemName(),
					item.getValueBefore(), item.getContentBefore(), item.getValueAfter(),
					item.getContentAfter(), item.getType());
		}

		private Object convertValue(int valueType, String value) {

			if (value == null) return null;

			SaveDataType dataType = EnumAdaptor.valueOf(valueType, SaveDataType.class);

			switch (dataType) {
			case STRING:
				return value;
			case NUMERIC:
				return new BigDecimal(value);
			case DATE:
				return GeneralDate.fromString(value, "yyyy/MM/dd");
			default:
				return null;
			}

		}

		private DataValueAttribute converType(int valueType) {
		    //  enum DataTypeValue
			switch (valueType) {
			case 1:
			case 6: 
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				return DataValueAttribute.STRING;
			case 2:
				return DataValueAttribute.COUNT;
			case 3:
				return DataValueAttribute.DATE;
			case 4:
				return DataValueAttribute.TIME;
			case 5:
				return DataValueAttribute.CLOCK;	
			default:
				return DataValueAttribute.of(-1);

			}
		}
	}
}