package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting;

import lombok.Getter;

/**
 * 遅刻早退の自動計算設定
 * 
 * @author ken_takasu
 *
 */
@Getter
public class AutoCalcOfLeaveEarlySetting {

	private boolean late;
	private boolean leaveEarly;

	public AutoCalcOfLeaveEarlySetting(boolean late, boolean leaveEarly) {
		super();
		this.late = late;
		this.leaveEarly = leaveEarly;
	}

	public static AutoCalcOfLeaveEarlySetting defaultValue() {
		return new AutoCalcOfLeaveEarlySetting(false, false);
	}

	/**
	 * 全てtrueで作成する
	 * @return 全てtrueのインスタンス
	 */
	public static AutoCalcOfLeaveEarlySetting createAllTrue() {
		return new AutoCalcOfLeaveEarlySetting(true, true);
	}
}
