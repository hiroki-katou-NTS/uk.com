package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.AllArgsConstructor;

/**
 * 法定内区分
 * ドメインではありません、パラメータとして使用する為仮作成
 * @author ken_takasu
 *
 */
@AllArgsConstructor
public enum StatutoryDivision {
	
	Nomal(0, "Enum_StatutoryDivision_Nomal"),//通常
	Premium(1, "Enum_StatutoryDivision_Premium");//割増
	
	public final int value;

	public final String nameId;
	
	/**
	 * 通常か判定する
	 * @return 
	 */
	public boolean isNomal() {
		return Nomal.equals(this);
	}
}
