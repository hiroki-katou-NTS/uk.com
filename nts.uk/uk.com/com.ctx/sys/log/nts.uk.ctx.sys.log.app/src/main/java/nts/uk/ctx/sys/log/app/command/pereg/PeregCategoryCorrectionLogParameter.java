package nts.uk.ctx.sys.log.app.command.pereg;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ReviseInfo;

@Value
public class PeregCategoryCorrectionLogParameter implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	private final List<CategoryCorrectionTarget> targets;

	@Value
	public static class CategoryCorrectionTarget implements Serializable {
		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		private final String categoryName;
		private final InfoOperateAttr infoOperateAttr;
		private final List<PeregCorrectedItemInfo> itemInfos;
		private final TargetDataKey targetKey;
		private final Optional<ReviseInfo> reviseInfo;

		public CategoryCorrectionLog toCategoryInfo() {
			return new CategoryCorrectionLog(this.categoryName, this.infoOperateAttr, this.targetKey,
					mapToItemInfo(this.itemInfos), this.reviseInfo);
		}

		private List<ItemInfo> mapToItemInfo(List<PeregCorrectedItemInfo> itemInfos) {
			return itemInfos.stream().map(i -> {
				return ItemInfo.createToView(IdentifierUtil.randomUniqueId(), i.itemName,
						DataValueAttribute.of(i.valueType == null ? 0 : i.valueType.intValue()).format(
								i.convertValue(i.valueType == null ? 0 : i.valueType.intValue(), i.valueBefore)),
						DataValueAttribute.of(i.valueType == null ? 0 : i.valueType.intValue()).format(
								i.convertValue(i.valueType == null ? 0 : i.valueType.intValue(), i.valueAfter)));
			}).collect(Collectors.toList());
		}

	}

	@Value
	public static class PeregCorrectedItemInfo implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		private final String itemName;
		private final Integer itemNo;
		private final String valueBefore;
		private final String valueAfter;
		private final Integer valueType;

		public ItemInfo toItemInfo() {
			return ItemInfo.createToView(IdentifierUtil.randomUniqueId(), this.itemName,
					DataValueAttribute.of(valueType).format(convertValue(valueType, this.valueBefore)),
					DataValueAttribute.of(valueType).format(convertValue(valueType, this.valueAfter)));
		}

		private Object convertValue(int valueType, String value) {
			if (valueType == DataValueAttribute.TIME.value) {
				return Integer.parseInt(value);
			} else if (valueType == DataValueAttribute.STRING.value || valueType == DataValueAttribute.COUNT.value) {
				return String.valueOf(value);
			} else if (valueType == DataValueAttribute.DATE.value) {
				return GeneralDate.fromString(value, "yyyy-MM-dd");
			} else {
				return false;
			}
		}
	}

}
