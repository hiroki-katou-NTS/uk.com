package nts.uk.screen.at.app.kmk004.i;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author sonnlb
 *
 *         雇用別法定労働時間の登録（フレックス勤務）の初期画面を表示する
 */
@Data
public class DisplayInitialFlexScreenByEmploymentDto {
	// 雇用リスト
	private List<String> alreadySettings;

	// 雇用を選択する
	private DisplayFlexBasicSettingByEmploymentDto flexBasicSetting =  new DisplayFlexBasicSettingByEmploymentDto();
	// 職場別年度リストを表示する
	private List<Integer> yearList;
}
