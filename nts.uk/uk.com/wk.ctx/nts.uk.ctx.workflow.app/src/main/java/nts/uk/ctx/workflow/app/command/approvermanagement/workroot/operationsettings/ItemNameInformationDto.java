package nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverItemName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ItemNameInformation;

/**
 * 項目の名称情報DTO
 */
@Data
@Builder
public class ItemNameInformationDto {
	/** 項目1の名称 */
	private String firstItemName;
	
	/** 項目2の名称 */
	private String secondItemName;
	
	/** 項目3の名称 */
	private String thirdItemName;
	
	/** 項目4の名称 */
	private String fourthItemName;
	
	/** 項目5の名称 */
	private String fifthItemName;
	
	public ItemNameInformation toValueObject() {
		return new ItemNameInformation(new ApproverItemName(firstItemName),
				secondItemName == null ? null : new ApproverItemName(secondItemName),
				thirdItemName == null ? null : new ApproverItemName(thirdItemName),
				fourthItemName == null ? null : new ApproverItemName(fourthItemName),
				fifthItemName == null ? null : new ApproverItemName(fifthItemName));
	}
}
