package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeforLaborComDto {

	// 会社別変形労働法定労働時間
	private WorkingTimeSettingDto deforLaborTimeComDto;

	// 会社別変形労働集計設定
	private DeforWorkTimeAggrSetDto settingDto;

}
