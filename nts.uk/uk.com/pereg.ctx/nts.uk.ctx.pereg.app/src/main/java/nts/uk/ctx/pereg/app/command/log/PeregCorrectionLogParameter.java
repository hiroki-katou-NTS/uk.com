package nts.uk.ctx.pereg.app.command.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ReviseInfo;

@Value
public class PeregCorrectionLogParameter implements Serializable{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private final List<PeregCorrectionTarget> targets;
	
	@Value
	public static class PeregCorrectionTarget implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		
		private final String userId;
		private final String employeeId;
		private final String userName;
		private final GeneralDate date;
		private final PersonInfoProcessAttr processAttr;
		private final List<PeregCategoryCorrectionLog> correctedCategories= new ArrayList<>();
		private final String remark;
		
		
		public PeregCorrectionTarget(String userId,String employeeId, String userName, GeneralDate date,PersonInfoProcessAttr processAttr, String remark){
			this.userId = userId;
			this.employeeId = employeeId;
			this.userName = userName;
			this.date = date;
			this.processAttr = processAttr;
			this.remark = remark;
		}
		
		private PersonInfoCorrectionLog toPersonInfoCorrection(String remark){
			return new PersonInfoCorrectionLog("", processAttr, new UserInfo(this.userId, this.employeeId,this. userName), convertDomain(this.correctedCategories), this.remark);
		}
		
		private List<CategoryCorrectionLog> convertDomain(List<PeregCategoryCorrectionLog> peregCtgCorrectionLog){
			return this.correctedCategories.stream().map(c -> {
				return c.toCategoryInfo();
			}).collect(Collectors.toList());
		}
	}

	@Value
	public static class PeregCategoryCorrectionLog implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		
		private final String categoryName;
		private final InfoOperateAttr infoOperateAttr;
		private final List<PeregCorrectedItemInfo> itemInfos;
		private final TargetDataKey targetKey;
		private final Optional<ReviseInfo> reviseInfo;

		public CategoryCorrectionLog toCategoryInfo() {
			return new CategoryCorrectionLog(this.categoryName, this.infoOperateAttr, this.targetKey, mapToItemInfo(this.itemInfos), this.reviseInfo);
		}
		
		private List<ItemInfo> mapToItemInfo (List<PeregCorrectedItemInfo> itemInfos){
			return itemInfos.stream().map( i -> {
				return ItemInfo.createToView(IdentifierUtil.randomUniqueId(),
						i.itemName,
						DataValueAttribute.of(i.valueType == null? 0: i.valueType.intValue()).format(
								i.convertValue(i.valueType == null? 0: i.valueType.intValue(), i.valueBefore)),
						DataValueAttribute.of(i.valueType == null? 0: i.valueType.intValue()).format(
								i.convertValue(i.valueType == null? 0: i.valueType.intValue(), i.valueAfter)));
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
			return ItemInfo.createToView(IdentifierUtil.randomUniqueId(),
					this.itemName,
					DataValueAttribute.of(valueType).format(
							convertValue(valueType, this.valueBefore)),
					DataValueAttribute.of(valueType).format(
							convertValue(valueType, this.valueAfter)));
		}
		
		private Object convertValue(int valueType, String value) {
			if (valueType == DataValueAttribute.TIME.value) {
				return Integer.parseInt(value);
			} else if (valueType == DataValueAttribute.STRING.value || valueType == DataValueAttribute.COUNT.value) {
				return String.valueOf(value);
			} else if(valueType == DataValueAttribute.DATE.value) {
				return GeneralDate.fromString(value, "yyyy-MM-dd");
			} else{
				return false;
			}
		}
	}

}
