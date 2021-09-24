package nts.uk.screen.at.app.kmk004.h;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.kmk004.g.ComFlexMonthActCalSetDto;

/**
 * 
 * @author sonnlb
 *
 *         職場別基本設定（フレックス勤務）を作成・変更・削除した時
 */
@NoArgsConstructor
@Data
public class AfterChangeFlexWorkPlaceSettingDto {
	
	// 職場別フレックス勤務集計方法
	WkpFlexMonthActCalSetDto flexMonthActCalSet;
	// 会社別フレックス勤務集計方法
//	GetFlexPredWorkTimeDto flexPredWorkTime;

	List<String> alreadySettings;
	
	ComFlexMonthActCalSetDto comFlexMonthActCalSet;
}
