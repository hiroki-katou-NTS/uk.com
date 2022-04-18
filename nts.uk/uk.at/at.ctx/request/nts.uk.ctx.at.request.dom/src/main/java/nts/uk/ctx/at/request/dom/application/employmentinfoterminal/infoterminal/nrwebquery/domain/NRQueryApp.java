package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.GetAllNRWebQueryAppDetail;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;

/**
 * @author thanh_nx
 *
 *         申請のデータ
 */
@AllArgsConstructor
@Getter
public class NRQueryApp {
	// 年月日
	private GeneralDate appDate;

	// 入力日
	private GeneralDateTime inputDate;

	// 申請名
	private String appName;

	// 申請種類
	private int appType;

	// 事前事後区分
	private int beforeAfterType;

	// 承認状況
	private String approvalStatus;

	// [S-1] 申請のデータを作る
	public static NRQueryApp create(Application app, ReflectionStatusOfDay state) {
		return new NRQueryApp(state.getTargetDate(), app.getInputDate(),
				GetAllNRWebQueryAppDetail.createAppName(app.getAppType()), app.getAppType().value,
				app.getPrePostAtr().value, GetAllNRWebQueryAppDetail.createAppStatus(state));
	}

	public static String createValueFormatTime(String value) {
		return createValueFormat(value, DailyAttendanceAtr.Time);
	}
	
	public static String createValueFormatTimeAtr(String value) {
		return createValueFormat(value, DailyAttendanceAtr.TimeOfDay);
	}
	
	public static String createValueFormatMoney(String value) {
		return createValueFormat(value, DailyAttendanceAtr.AmountOfMoney);
	}
	
	public static String createValueFormat(String value, DailyAttendanceAtr dailyAttendanceAtr) {
		if(value == null || value.equals("null")) 
			return "";
		switch (dailyAttendanceAtr) {
		case Code:
		case ReferToMaster:
		case NumberOfTime:
		case Classification:
		case Charater:
			return DataValueAttribute.STRING.format(value);
		case AmountOfMoney:
			return DataValueAttribute.MONEY.format(Double.valueOf(value));
		case Time:
			return DataValueAttribute.TIME.format(Integer.parseInt(value));
		case TimeOfDay:
			return DataValueAttribute.CLOCK.format(Integer.parseInt(value));
		default:
			break;
		}
		return value;
	} 
}
