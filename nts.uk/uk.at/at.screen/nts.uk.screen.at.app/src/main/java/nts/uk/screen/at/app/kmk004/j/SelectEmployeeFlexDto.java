package nts.uk.screen.at.app.kmk004.j;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@NoArgsConstructor
public class SelectEmployeeFlexDto {

	private DisplayFlexBasicSettingByEmployeeDto flexBasicSetting = new DisplayFlexBasicSettingByEmployeeDto();

	// 社員別年度リストを表示する
	private List<Integer> yearList;

}
