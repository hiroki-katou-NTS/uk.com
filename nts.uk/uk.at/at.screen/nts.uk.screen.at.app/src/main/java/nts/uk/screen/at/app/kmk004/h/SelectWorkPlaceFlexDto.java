package nts.uk.screen.at.app.kmk004.h;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 *         職場を選択する
 */
@NoArgsConstructor
@Data
public class SelectWorkPlaceFlexDto {

	// 職場別基本設定（フレックス勤務）を表示する
	DisplayFlexBasicSettingByWorkPlaceDto flexBasicSetting;
	// 職場別年度リストを表示する
	List<Integer> yearList;
}
