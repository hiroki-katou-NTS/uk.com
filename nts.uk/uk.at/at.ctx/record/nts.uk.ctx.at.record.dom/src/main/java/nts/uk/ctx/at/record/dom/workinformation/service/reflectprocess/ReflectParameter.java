package nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@Setter
@Getter
public class ReflectParameter {
	/**
	 * 社員ID
	 */
	private String employeeId;
	/**
	 * 年月日
	 */
	private GeneralDate dateData;
	/**
	 * 勤務種類コード
	 */
	private String workTimeCode;
	/**
	 * 就業時間帯コード
	 */
	private String workTypeCode;
	/**
	 * 勤務変更の申請場合　True
	 */
	private boolean workChange = false;
}
