package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryTimeDigesLeavName;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryVacationApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.i18n.TextResource;

/**
 * @author thanh_nx
 *
 *         NRWeb照会休暇申請を取得
 */
public class GetNRWebQueryVacationAppDetail {

	// [S-1] プロセス
	public static List<NRQueryVacationApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.ABSENCE_APPLICATION);
		List<ApplyForLeave> lstAppForLeaveList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstAppForLeaveList.addAll(require.findForLeaveWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstAppForLeaveList.addAll(require.findForLeaveWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstAppForLeaveList.addAll(require.findForLeaveWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}

		List<NRQueryVacationApp> result = new ArrayList<>();
		for (ApplyForLeave app : lstAppForLeaveList) {
			String classification = createClassification(app.getVacationInfo().getHolidayApplicationType());
			val degistTimeNames = app.getReflectFreeTimeApp().getTimeDegestion().map(x -> createDigestVacationName(x))
					.orElse(new ArrayList<>());
			String workTypeName = require
					.findWorkTypeByPK(param.getCid(), app.getReflectFreeTimeApp().getWorkInfo().getWorkTypeCode().v())
					.map(x -> x.getAbbreviationName().v()).orElse("");
			Optional<String> workTimeName = app.getReflectFreeTimeApp().getWorkInfo().getWorkTimeCodeNotNull()
					.flatMap(x -> require.findWorkTimeByCode(param.getCid(), x.v())
							.map(y -> y.getWorkTimeDisplayName().getWorkTimeName().v()));
			for (ReflectionStatusOfDay state : app.getReflectionStatus().getListReflectionStatusOfDay()) {
				NRQueryApp appQuery = NRQueryApp.create(app, state);
				result.add(
						new NRQueryVacationApp(appQuery, classification, workTypeName, workTimeName, degistTimeNames));
			}

		}
		return result;
	}

	// [pvt-1] 休暇区分を作る
	private static String createClassification(HolidayAppType holidayAppType) {
		switch (holidayAppType) {
		case ANNUAL_PAID_LEAVE:
			return TextResource.localize("Com_PaidHoliday");
		case SUBSTITUTE_HOLIDAY:
			return TextResource.localize("Com_CompensationHoliday");
		case ABSENCE:
			return "欠勤";
		case SPECIAL_HOLIDAY:
			return "特別休暇";
		case YEARLY_RESERVE:
			return TextResource.localize("Com_FundedPaidHoliday");
		case HOLIDAY:
			return "欠勤";
		case DIGESTION_TIME:
			return "時間消化";
		default:
			return "";
		}
	}

	// [pvt-2] 時間消化休暇名を作る
	private static List<NRQueryTimeDigesLeavName> createDigestVacationName(TimeDigestApplication timeDigestApp) {
		List<NRQueryTimeDigesLeavName> lstTimeDigestLeavName = new ArrayList<>();
		if (timeDigestApp.getTimeAnnualLeave().v() != 0) {
			lstTimeDigestLeavName
					.add(new NRQueryTimeDigesLeavName(String.format("時間%s", TextResource.localize("Com_PaidHoliday")),
							timeDigestApp.getTimeAnnualLeave().v().toString()));
		}

		if (timeDigestApp.getTimeOff().v() != 0) {
			lstTimeDigestLeavName.add(new NRQueryTimeDigesLeavName(
					String.format("時間%s", TextResource.localize("Com_CompensationHoliday")),
					timeDigestApp.getTimeOff().v().toString()));
		}

		if (timeDigestApp.getOvertime60H().v() != 0) {
			lstTimeDigestLeavName.add(new NRQueryTimeDigesLeavName(TextResource.localize("Com_ExsessHoliday"),
					timeDigestApp.getOvertime60H().v().toString()));
		}

		if (timeDigestApp.getTimeSpecialVacation().v() != 0) {
			lstTimeDigestLeavName
					.add(new NRQueryTimeDigesLeavName("時間特別休暇", timeDigestApp.getTimeSpecialVacation().v().toString()));
		}

		if (timeDigestApp.getChildTime().v() != 0) {
			lstTimeDigestLeavName.add(new NRQueryTimeDigesLeavName(TextResource.localize("Com_ChildNurseHoliday"),
					timeDigestApp.getChildTime().v().toString()));
		}

		if (timeDigestApp.getNursingTime().v() != 0) {
			lstTimeDigestLeavName.add(new NRQueryTimeDigesLeavName(TextResource.localize("Com_CareHoliday"),
					timeDigestApp.getNursingTime().v().toString()));
		}

		return lstTimeDigestLeavName;
	}

	public static interface Require {

		// [R-1] 申請者 と申請日から休暇申請を取得する
		public List<ApplyForLeave> findForLeaveWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-2] 休暇申請を取得する
		public List<ApplyForLeave> findForLeaveWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から休暇申請を取得する
		public List<ApplyForLeave> findForLeaveWithSidDatePeriod(String companyId, String sid, DatePeriod period);

		// [R-4] 就業時間帯コードリストから就業時間帯の設定を取得する
		// WorkTimeSettingRepository.findByCode
		public Optional<WorkTimeSetting> findWorkTimeByCode(String companyId, String workTimeCode);

		// [R-5] 勤務種類コードリストから勤務種類を取得する
		// WorkTypeRepository.findByPK
		public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd);
	}
}
