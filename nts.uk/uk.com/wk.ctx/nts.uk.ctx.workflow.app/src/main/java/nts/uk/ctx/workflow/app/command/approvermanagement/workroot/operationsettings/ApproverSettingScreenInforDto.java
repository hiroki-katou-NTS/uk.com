package nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverInputCareful;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverInputExplanation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverItemName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverSettingScreenInfor;

/**
 * 自分の承認者設定画面に表示情報DTO
 */
@Data
@Builder
public class ApproverSettingScreenInforDto {
	
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
	
	/** 手順の説明 */
	private String processMemo;
	
	/** 表示する注意内容 */
	private String attentionMemo;
	
	public ApproverSettingScreenInfor toValueObject() {
		return new ApproverSettingScreenInfor(
				new ApproverItemName(firstItemName),
				Optional.ofNullable(secondItemName == null ? null : new ApproverItemName(secondItemName)),
				Optional.ofNullable(thirdItemName == null ? null : new ApproverItemName(thirdItemName)),
				Optional.ofNullable(fourthItemName == null ? null : new ApproverItemName(fourthItemName)),
				Optional.ofNullable(fifthItemName == null ? null : new ApproverItemName(fifthItemName)),
				Optional.ofNullable(processMemo == null ? null : new ApproverInputExplanation(processMemo)),
				Optional.ofNullable(attentionMemo == null ? null : new ApproverInputCareful(attentionMemo)));
	}
	
}
