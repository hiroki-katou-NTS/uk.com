package nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime;

import lombok.AllArgsConstructor;

/**
 * するしない区分
 * プロジェクトルート.ドメインモデル.NittsuSystem.UniversalK.Common.するしない区分 が存在しない他仮で作成しました
 * 正しいするしない区分が作成されれば不要なクラスです
 * @author ken_takasu
 *
 */
@AllArgsConstructor
public enum NotUseAtr {

	/* しない */
	Donot(0, "Enum_NotUseAtr_Donot"),
	/* する */
	To(1, "Enum_NotUseAtr_To");

	public final int value;

	public final String nameId;
	
	/**
	 * するか判定する
	 * @return 使用する
	 */
	public boolean isUse() {
		return To.equals(this);
	}
	
	/**
	 * しないか判定する
	 * @return 
	 */
	public boolean isDonot() {
		return Donot.equals(this);
	}
	
	
	
}
