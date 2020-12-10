package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
public class LeaveComDayOffManaDto {
	// 社員ID
	private String sid;

	// 逐次休暇の紐付け情報 . 発生日
	private GeneralDate outbreakDay;

	// 逐次休暇の紐付け情報 . 使用日
	private GeneralDate dateOfUse;

	// 逐次休暇の紐付け情報 . 使用日数
	private double dayNumberUsed;

	// 逐次休暇の紐付け情報 . 対象選択区分
	private int targetSelectionAtr;

	public LeaveComDayOffManagement toDomain() {
		return new LeaveComDayOffManagement(sid, outbreakDay, dateOfUse, dayNumberUsed, targetSelectionAtr);
	}
}
