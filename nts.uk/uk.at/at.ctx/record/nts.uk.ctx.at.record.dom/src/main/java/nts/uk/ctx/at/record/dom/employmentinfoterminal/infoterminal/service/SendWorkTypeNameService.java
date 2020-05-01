package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.NRWorkType;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendWorkTypeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

/**
 * @author ThanhNX
 *
 *         勤務種類をNRに 送信するデータに変換する
 */
public class SendWorkTypeNameService {

	private SendWorkTypeNameService() {
	};

	// [1] 各種名称送信に変換
	public static List<SendWorkTypeName> send(Require require, EmpInfoTerminalCode empInfoTerCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode);

		if (!requestSetting.isPresent() || requestSetting.get().getWorkTypeCodes().isEmpty())
			return Collections.emptyList();

		List<String> lstWorkTypeCode = requestSetting.get().getWorkTypeCodes().stream().map(x -> x.v())
				.collect(Collectors.toList());

		List<WorkType> lstWorkInfo = require.getPossibleWork(requestSetting.get().getCompanyId().v(), lstWorkTypeCode);

		return convert(lstWorkInfo);
	}

	// [pvt-1] 勤務種類コード名称送信に変換
	private static List<SendWorkTypeName> convert(List<WorkType> lstWorkInfo) {

		return lstWorkInfo.stream().map(x -> {
			String daily = "", morning = "", afternoon = "";
			if (x.getDailyWork().getWorkTypeUnit().value == WorkTypeUnit.OneDay.value) {
				daily = convertNRWType(x.getDailyWork().getOneDay());
			} else {
				morning = convertNRWType(x.getDailyWork().getMorning());
				afternoon = convertNRWType(x.getDailyWork().getAfternoon());
			}
			return new SendWorkTypeName(x.getWorkTypeCode().v(), daily, morning, afternoon, x.getName().v());
		}).sorted((x, y) -> x.getWorkTypeNumber().compareTo(y.getWorkTypeNumber())).collect(Collectors.toList());
	}

	private static String convertNRWType(WorkTypeClassification wt) {

		switch (wt) {
		case Attendance:

			return NRWorkType.ATTENDANCE.value;

		case Holiday:

			return NRWorkType.HOLIDAY.value;

		case HolidayWork:

			return NRWorkType.HOLIDAY_WORK.value;

		case AnnualHoliday:

			return NRWorkType.ANNUAL_HOLIDAY.value;

		case SpecialHoliday:

			return NRWorkType.SPECIAL_HOLIDAY.value;

		case Absence:

			return NRWorkType.ABSENCE.value;

		case SubstituteHoliday:

			return NRWorkType.SUBSTITUTE_HOLIDAY.value;

		case Shooting:

			return NRWorkType.SHOOTING.value;

		case Pause:

			return NRWorkType.PAUSE.value;

		case ContinuousWork:

			return NRWorkType.CONTINUOUS_WORK.value;

		case Closure:

			return NRWorkType.CLOSURE.value;

		case TimeDigestVacation:

			return NRWorkType.TIMEDIGEST_VACATION.value;

		default:
			return "";
		}
	}

	public static interface Require {

		// [R-1] 指定した勤務種類をすべて取得する
		public List<WorkType> getPossibleWork(String companyId, List<String> workTypeCode);

		// [R-2]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode);
	}
}
