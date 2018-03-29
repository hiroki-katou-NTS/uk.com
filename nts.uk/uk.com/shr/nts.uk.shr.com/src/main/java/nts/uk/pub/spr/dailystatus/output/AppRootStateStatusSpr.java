package nts.uk.pub.spr.dailystatus.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootStateStatusSpr {
	/**
	 * 年月日
	 */
	private GeneralDate date;
	/**
	 * 承認対象者
	 */
	private String employeeID;
	/**
	 * 承認状況
	 */
	private Integer dailyConfirmAtr;
}
