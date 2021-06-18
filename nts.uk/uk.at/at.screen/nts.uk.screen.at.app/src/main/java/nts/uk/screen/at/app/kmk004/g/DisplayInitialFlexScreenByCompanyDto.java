package nts.uk.screen.at.app.kmk004.g;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.UsageUnitSettingDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayInitialFlexScreenByCompanyDto {

	// 利用単位
	UsageUnitSettingDto usageUnitSetting;

	// 会社別基本設定（フレックス勤務）を表示する
	DisplayFlexBasicSettingByCompanyDto flexBasicSetting;

	// 会社別年度リスト
	List<Integer> yearList;
}
