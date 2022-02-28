package nts.uk.screen.com.app.smm.smm001.screencommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentAndLinkedMonthSettingDto {
	/**
	 * Name: 連動月調整
	 * Type: 連動月の設定区分
	 */
	private Integer interlockingMonthAdjustment;
	
	/**
	 * Name: 選択雇用コード
	 * Type: 雇用コード
	 */
	private String scd;
}
