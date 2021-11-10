package nts.uk.screen.at.app.kmk004.h;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 *         職場別法定労働時間の登録（フレックス勤務）の初期画面を表示する
 */
@Data
@NoArgsConstructor
public class DisplayInitialFlexScreenByWorkPlaceDto {
	// 会社別フレックス勤務集計方法
//	private GetFlexPredWorkTimeDto flexPredWorkTime;
	// 職場リスト
	private List<String> alreadySettings;

	// 職場別基本設定（フレックス勤務）を表示する
	private DisplayFlexBasicSettingByWorkPlaceDto flexBasicSetting;
	// 職場別年度リストを表示する
	private List<Integer> yearList;
}
