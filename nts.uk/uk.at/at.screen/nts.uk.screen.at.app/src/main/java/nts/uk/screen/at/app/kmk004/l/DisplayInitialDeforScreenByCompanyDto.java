package nts.uk.screen.at.app.kmk004.l;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.UsageUnitSettingDto;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayInitialDeforScreenByCompanyDto {

	// 利用単位の設定を取得する
	UsageUnitSettingDto usageUnitSetting;

	// 会社別基本設定（変形労働）を表示する
	DeforLaborMonthTimeComDto deforLaborMonthTimeComDto;

	// 会社別年度リストを表示する
	List<YearDto> years;
}
