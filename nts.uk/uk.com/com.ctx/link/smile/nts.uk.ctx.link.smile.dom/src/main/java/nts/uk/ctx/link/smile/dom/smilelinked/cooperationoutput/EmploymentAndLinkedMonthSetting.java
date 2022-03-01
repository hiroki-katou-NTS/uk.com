package nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 雇用と連動月設定
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
