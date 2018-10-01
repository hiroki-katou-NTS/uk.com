package nts.uk.ctx.at.shared.dom.ot.autocalsetting;

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

}
