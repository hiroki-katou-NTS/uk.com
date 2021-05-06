package nts.uk.ctx.at.function.dom.employmentfunction.commonform;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.帳票共通.帳票共通の設定区分
 * 
 * @author LienPTK
 */
public enum SettingClassification {
	
	/** 定型選択 */
	STANDARD(0, "定型選択"),
	
	/** 自由設定 */
	FREE_SETTING(1, "自由設定");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SettingClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
