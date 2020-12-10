package nts.uk.screen.at.app.kmk004.h;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.kmk004.g.GetFlexPredWorkTimeDto;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceIdDto;

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
	private GetFlexPredWorkTimeDto getFlexPredWorkTime;
	// 職場リスト
	private List<WorkplaceIdDto> wkpIds;
	// 職場を選択する
	private SelectWorkPlaceFlexDto selectWorkPlaceFlex;
}
