package nts.uk.screen.at.app.kmk004.i;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentCodeDto;

/**
 * 
 * @author sonnlb
 *
 *         雇用別基本設定（フレックス勤務）を作成・変更・削除した時
 */
@NoArgsConstructor
@Data
public class AfterChangeFlexEmploymentSettingDto {
	
	// 雇用別フレックス勤務集計方法
	DisplayFlexBasicSettingByEmploymentDto flexBasicSetting;
	
	List<EmploymentCodeDto> employmentList;
}
