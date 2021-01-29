package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeforLaborDto {

	// 別変形労働法定労働時間
	private WorkingTimeSettingDto deforLaborTimeComDto;

	// 別変形労働集計設定
	private DeforWorkTimeAggrSetDto settingDto;

}
