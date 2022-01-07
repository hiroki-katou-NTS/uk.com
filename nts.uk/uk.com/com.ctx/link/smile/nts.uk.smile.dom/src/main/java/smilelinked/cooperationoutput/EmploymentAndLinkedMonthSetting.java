package smilelinked.cooperationoutput;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AR_連動支払変換
 *
 */
@Getter
@AllArgsConstructor
public class EmploymentAndLinkedMonthSetting extends ValueObject {
	
	/**
	 * Name: 連動月調整
	 * Type: 連動月の設定区分
	 */
	private LinkedMonthSettingClassification interlockingMonthAdjustment;
	
	/**
	 * Name: 選択雇用コード
	 * Type: 雇用コード
	 */
	private String scd;
}
