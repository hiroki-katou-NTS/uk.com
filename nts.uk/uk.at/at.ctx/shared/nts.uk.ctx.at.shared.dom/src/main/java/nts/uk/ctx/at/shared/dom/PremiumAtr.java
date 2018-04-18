package nts.uk.ctx.at.shared.dom;

import lombok.AllArgsConstructor;

/**
 * 割増区分
 * @author ken_takasu
 *
 */

@AllArgsConstructor
public enum PremiumAtr {
	/* 通常 */
	RegularWork(0, "Enum_PremiumAtr_RegularWork"),
	/* 割増 */
	Premium(1, "Enum_PremiumAtr_Premium");

	public final int value;

	public final String nameId;
	
	/**
	 * 通常か判定する
	 * @return 通常
	 */
	public boolean isRegularWork() {
		return RegularWork.equals(this);
	}
	
	/**
	 * 割増か判定する
	 * @return 割増
	 */
	public boolean isPremium() {
		return Premium.equals(this);
	}	
}
