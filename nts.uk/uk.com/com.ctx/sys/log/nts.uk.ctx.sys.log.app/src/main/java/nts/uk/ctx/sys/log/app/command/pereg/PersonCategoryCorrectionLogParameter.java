package nts.uk.ctx.sys.log.app.command.pereg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ReviseInfo;
import nts.uk.shr.pereg.app.SaveDataType;

@Value
public class PersonCategoryCorrectionLogParameter implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	private final List<CategoryCorrectionTarget> targets;

	@Value
	public static class CategoryCorrectionTarget implements Serializable {
		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		private final String categoryName;
		private final InfoOperateAttr infoOperateAttr;
		private final List<PersonCorrectionItemInfo> itemInfos;
		private final TargetDataKey targetKey;
		private final Optional<ReviseInfo> reviseInfo;

		public CategoryCorrectionLog toCategoryInfo() {
			return new CategoryCorrectionLog(this.categoryName, this.infoOperateAttr, this.targetKey,
					mapToItemInfo(this.itemInfos), this.reviseInfo);
		}
		
		public CategoryCorrectionLog toCategoryInfoCPS002(
				String categoryName,
				InfoOperateAttr infoOperateAttr,
				TargetDataKey targetKey,
				List<ItemInfo> itemInfos,
				Optional<ReviseInfo> reviseInfo
				) {
			return new CategoryCorrectionLog(categoryName, infoOperateAttr, targetKey,itemInfos,reviseInfo);
		}

		private List<ItemInfo> mapToItemInfo(List<PersonCorrectionItemInfo> itemInfos) {
			return itemInfos.stream().map(i -> {
				return i.toCreateItemInfoCPS002();
			}).collect(Collectors.toList());
		}

	}

	@Value
	public static class PersonCorrectionItemInfo implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		private final String itemId;
		private final String itemName;
		private final String valueBefore;
		private final String valueAfter;
		private final Integer valueType;
		
		public ItemInfo toCreateItemInfo() {
			return ItemInfo.create(this.itemId, this.itemName,
					converType(valueType),
					convertValue(valueType, this.valueBefore),
					convertValue(valueType, this.valueAfter));
		}
		
		public ItemInfo toCreateItemInfoCPS002() {
			return ItemInfo.create(this.itemId, this.itemName,
					converType(valueType),
					valueBefore == null ?  null : convertValue(valueType, this.valueBefore),
					valueAfter == null ?  null : convertValue(valueType, this.valueAfter));
		}
		
		
		private Object convertValue(int valueType, String value) {
			if (valueType == SaveDataType.STRING.value) {
				return value;
			} else if (valueType == SaveDataType.NUMERIC.value) {
				return new BigDecimal(value);
			} else if (valueType == SaveDataType.DATE.value) {
				return GeneralDate.fromString(value, "yyyy/MM/dd");
			} else {
				return false;
			}
		}
		
		private DataValueAttribute converType(int valueType) {
			if (valueType == SaveDataType.STRING.value) {
				return DataValueAttribute.STRING;
			} else if (valueType == SaveDataType.NUMERIC.value) {
				return DataValueAttribute.COUNT;
			} else if (valueType == SaveDataType.DATE.value) {
				return DataValueAttribute.DATE;
			} 
			return DataValueAttribute.of(-1);
			
		}
	}

}
