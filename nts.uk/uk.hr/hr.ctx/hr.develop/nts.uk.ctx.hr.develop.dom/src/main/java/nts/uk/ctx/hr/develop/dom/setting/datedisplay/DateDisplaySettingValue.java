package nts.uk.ctx.hr.develop.dom.setting.datedisplay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author anhdt
 * 表示設定内容
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DateDisplaySettingValue {
	// 設定区分
	private DateSettingClass settingClass;
	// 設定指定数
	private int settingNum;
	// 設定指定日
	private int settingDate;
	// 設定指定月
	private int settingMonth;
}
