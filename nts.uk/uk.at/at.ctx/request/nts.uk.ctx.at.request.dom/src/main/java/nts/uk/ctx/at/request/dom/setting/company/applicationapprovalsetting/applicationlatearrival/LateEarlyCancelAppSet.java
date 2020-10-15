package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.遅刻早退申請.遅刻早退取消申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class LateEarlyCancelAppSet {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 取り消す設定
	 */
	private CancelAtr cancelAtr;
	
	
	/**
	 * 遅刻早退報告を行った場合はアラームとしない
	 */
//	private int lateAlClearAtr;
}
