/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * The Class BasicSchedule.
 */
// 勤務予定基本情報
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicSchedule extends AggregateRoot {

	/** The sId. */
	// 社員ID
	private String sId;

	/** The date. */
	// 年月日
	private GeneralDate date;

	/** The work type code. */
	// 勤務種類
	private String workTypeCode;

	/** The work time code. */
	// 就業時間帯
	private String workTimeCode;

	/** The confirmed atr. */
	// 確定区分
	private ConfirmedAtr confirmedAtr;

	/** The work day atr. */
	// 稼働日区分
	private WorkdayDivision workDayAtr;

	/**
	 * Instantiates a new basic schedule.
	 *
	 * @param sId
	 *            the s id
	 * @param date
	 *            the date
	 * @param workTypeCode
	 *            the work type code
	 * @param workTimeCode
	 *            the work time code
	 */
//	public BasicSchedule(String sId, GeneralDate date, String workTypeCode, String workTimeCode) {
//		this.sId = sId;
//		this.date = date;
//		this.workTypeCode = workTypeCode;
//		this.workTimeCode = workTimeCode;
//	}

	/**
	 * Creates the from java type.
	 *
	 * @param sId
	 *            the s id
	 * @param date
	 *            the date
	 * @param workTypeCode
	 *            the work type code
	 * @param workTimeCode
	 *            the work time code
	 * @return the basic schedule
	 */
	public static BasicSchedule createFromJavaType(String sId, GeneralDate date, String workTypeCode,
			String workTimeCode, int confirmedAtr, int workDayAtr) {
		return new BasicSchedule(sId, date, workTypeCode, workTimeCode,
				EnumAdaptor.valueOf(confirmedAtr, ConfirmedAtr.class),
				EnumAdaptor.valueOf(workDayAtr, WorkdayDivision.class));
	}
}
