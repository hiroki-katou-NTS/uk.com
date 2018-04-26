package nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@Setter
@Getter
public class TimeReflectPara {
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
	private Integer startTime;
	
	private Integer endTime;
	/**
	 * 枠番後
	 */
	private Integer frameNo;
	
	private boolean isStart;
	private boolean isEnd;
}
