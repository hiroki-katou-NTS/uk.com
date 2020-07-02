package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.登録可否チェック区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum AppDateContradictionAtr{
	
	/**
	 * 0: チェックしない
	 */
	 NOTCHECK(0, "チェックしない"),
	 
	 /**
	 * 1: チェックする（登録可）
	 */
	CHECKREGISTER(1, "チェックする（登録可）"),
	 
	 /**
	 * 2: チェックする（登録不可）
	 */
	CHECKNOTREGISTER(2, "チェックする（登録不可）");
	
	public final int value;
	
	public final String name;
	
}
