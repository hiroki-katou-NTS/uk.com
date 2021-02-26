package nts.uk.screen.at.app.kmk004.i;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
public class SelectEmploymentFlexDto {
	// 雇用を選択する
	DisplayFlexBasicSettingByEmploymentDto flexBasicSetting;
	// 職場別年度リストを表示する
	List<Integer> yearList;
}
