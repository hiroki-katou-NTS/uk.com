package nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@Setter
@Getter
public class TimeReflectParameter {
	/**
	 * 社員ID
	 */
	private String employeeId;
	/**
	 * 年月日
	 */
	private GeneralDate dateData;
	/**
	 * 開始時刻
	 */
	private Integer time;
	/**
	 * 枠番後
	 */
	private Integer frameNo;
	/**
	 * 開始時刻: true
	 * 終了時刻: false
	 */
	private boolean preCheck;

}
