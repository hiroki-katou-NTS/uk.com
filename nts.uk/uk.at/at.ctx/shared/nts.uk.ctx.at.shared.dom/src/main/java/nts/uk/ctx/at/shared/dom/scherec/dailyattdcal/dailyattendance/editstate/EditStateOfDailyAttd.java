package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 日別勤怠の編集状態
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.編集状態.日別勤怠の編集状態
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class EditStateOfDailyAttd implements DomainObject {
	/** 勤怠項目ID: 勤怠項目ID */
	private int attendanceItemId;
	
	/** 編集状態: 日別実績の編集状態 */
	@Setter
	private EditStateSetting editStateSetting;

	public EditStateOfDailyAttd(int attendanceItemId, EditStateSetting editStateSetting) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.editStateSetting = editStateSetting;
	}
	
	public boolean isHandCorrect() {
		return this.editStateSetting == EditStateSetting.HAND_CORRECTION_MYSELF
				|| this.editStateSetting == EditStateSetting.HAND_CORRECTION_OTHER;
	}
}
