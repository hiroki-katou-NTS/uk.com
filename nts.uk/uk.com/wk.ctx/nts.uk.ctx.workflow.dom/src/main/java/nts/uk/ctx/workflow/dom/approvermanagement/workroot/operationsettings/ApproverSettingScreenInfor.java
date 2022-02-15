package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自分の承認者設定画面に表示情報
 * @author NWS-DungDV
 *
 */
@Getter
@AllArgsConstructor
public class ApproverSettingScreenInfor extends ValueObject {
	
	/** 項目1の名称 */	
	private ApproverItemName firstItemName;
	
	/** 項目2の名称 */	
	private Optional<ApproverItemName> secondItemName;
	
	/** 項目3の名称 */	
	private Optional<ApproverItemName> thirdItemName;
	
	/** 項目4の名称 */	
	private Optional<ApproverItemName> fourthItemName;
	
	/** 項目5の名称 */
	private Optional<ApproverItemName> fifthItemName;
	
	/** 手順の説明 */
	private Optional<ApproverInputExplanation> processMemo;
	
	/** 表示する注意内容 */
	private Optional<ApproverInputCareful> attentionMemo;
	
	/**
	 * [C-1] 項目の名称情報で作成する
	 * @param itemNameInformation 項目の名称情報
	 */
	public ApproverSettingScreenInfor(ItemNameInformation itemNameInformation) {
		this.firstItemName = itemNameInformation.getFirstItemName();
		this.secondItemName = Optional.ofNullable(itemNameInformation.getSecondItemName());
		this.thirdItemName = Optional.ofNullable(itemNameInformation.getThirdItemName());
		this.fourthItemName = Optional.ofNullable(itemNameInformation.getFourthItemName());
		this.fifthItemName = Optional.ofNullable(itemNameInformation.getFifthItemName());
		this.processMemo = Optional.empty();
		this.attentionMemo = Optional.empty();
	}
	
}
