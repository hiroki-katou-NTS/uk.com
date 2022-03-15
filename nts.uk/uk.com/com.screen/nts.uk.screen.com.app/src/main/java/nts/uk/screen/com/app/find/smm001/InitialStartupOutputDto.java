package nts.uk.screen.com.app.find.smm001;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InitialStartupOutputDto {
	// Smile連携出力設定
	private SmileLinkageOutputSettingDto smileLinkageOutputSetting;
	
	// 出力条件設定（定型）
	private List<EmploymentAndLinkedMonthSettingDto> listStandardOutputConditionSetting;
	
	// List＜雇用コード、雇用名称＞
	private List<EmploymentDto> employmentDtos;
	
	private List<StdOutputCondSetDto> stdOutputCondSetDtos;
}
