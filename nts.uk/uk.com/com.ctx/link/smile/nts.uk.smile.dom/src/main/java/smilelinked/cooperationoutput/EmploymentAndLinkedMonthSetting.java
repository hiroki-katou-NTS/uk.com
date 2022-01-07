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
	private LinkedMonthSettingClassification interlockingMonthAdjustment;
	private String scd;
}
