package nts.uk.screen.at.app.kmk004.h;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.kmk004.g.GetFlexPredWorkTimeDto;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceIdDto;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;

/**
 * 
 * @author sonnlb
 *
 *         職場別法定労働時間の登録（フレックス勤務）の初期画面を表示する
 */
@Data
@NoArgsConstructor
public class DisplayInitialFlexScreenByWorkPlaceDto {
	// フレックス勤務所定労働時間取得
	private GetFlexPredWorkTimeDto flexPredWorkTime;
	// 職場リスト
	private List<WorkplaceIdDto> wkpIds;

	// 職場別基本設定（フレックス勤務）を表示する
	private DisplayFlexBasicSettingByWorkPlaceDto flexBasicSetting;
	// 職場別年度リストを表示する
	private List<YearDto> yearList;
}
