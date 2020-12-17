package nts.uk.screen.at.app.kmk004.i;

import java.util.List;

import lombok.Data;
import nts.uk.screen.at.app.kmk004.g.GetFlexPredWorkTimeDto;

/**
 * 
 * @author sonnlb
 *
 *         雇用別法定労働時間の登録（フレックス勤務）の初期画面を表示する
 */
@Data
public class DisplayInitialFlexScreenByEmploymentDto {
	// フレックス勤務所定労働時間取得
	private GetFlexPredWorkTimeDto getFlexPredWorkTime;
	// 雇用リスト
	private List<String> alreadySettings;
	// 雇用を選択する
	private SelectEmploymentFlexDto selectWorkPlaceFlex;
}
