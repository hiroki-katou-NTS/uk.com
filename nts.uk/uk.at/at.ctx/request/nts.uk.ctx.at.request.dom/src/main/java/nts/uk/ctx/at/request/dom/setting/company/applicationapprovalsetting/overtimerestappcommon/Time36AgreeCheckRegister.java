package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;
/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.登録不可３６協定チェック区分
 * @author Doan Duy Hung
 *
 */
public enum Time36AgreeCheckRegister {
	/*
	 * チェックしない
	 */
	NOT_CHECK(0,"チェックしない"),
	
	/*
	 * チェックする（登録不可）
	 */
	CHECK(1,"チェックする（登録不可）"),
	
	/*
	 * 時間外労働の上限規制のみチェックする（登録不可）
	 */
	CHECK_ONLY_UPPER_OVERTIME(2,"時間外労働の上限規制のみチェックする（登録不可）");
	
	public final int value;
	
	public final String name;
	
	Time36AgreeCheckRegister(int value, String name){
		this.value = value;
		this.name = name;
	}
}
