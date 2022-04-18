package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryFurikyuFurishutsuApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.holidayshipment.ApplicationForHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.TypeApplicationHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author thanh_nx
 *
 *         NRWeb照会振休振出申請を取得
 */
public class GetNRWebQueryFurikyuAppDetail {

	// [S-1] プロセス
	public static List<NRQueryFurikyuFurishutsuApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.COMPLEMENT_LEAVE_APPLICATION);
		List<ApplicationForHolidays> lstApplicationForHolidaysList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstApplicationForHolidaysList.addAll(require.findApplicationForHolidaysWithSidDate(param.getCid(),
					param.getSid(), param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstApplicationForHolidaysList.addAll(require.findApplicationForHolidaysWithSidDateApptype(param.getCid(),
					param.getSid(), param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstApplicationForHolidaysList.addAll(
					require.findApplicationForHolidaysWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}

		return lstApplicationForHolidaysList.stream().flatMap(x -> createAppData(require, param.getCid(), x).stream())
				.collect(Collectors.toList());
	}

	// [pvt-1] 振休振出申請を作成する
	private static List<NRQueryFurikyuFurishutsuApp> createAppData(Require require, String cid,
			ApplicationForHolidays app) {

		return app.getReflectionStatus().getListReflectionStatusOfDay().stream().map(x -> {
			NRQueryApp appQuery = NRQueryApp.create(app, x);
			WorkInformation workInfo = null;
			if (app.getTypeApplicationHolidays() == TypeApplicationHolidays.Abs) {
				workInfo = ((AbsenceLeaveApp) app).getWorkInformation();
			} else {
				workInfo = ((RecruitmentApp) app).getWorkInformation();
			}

			return new NRQueryFurikyuFurishutsuApp(appQuery,
					require.findWorkTypeByPK(cid, workInfo.getWorkTypeCode().v()).map(y -> y.getAbbreviationName().v())
							.orElse(""),
					workInfo.getWorkTimeCodeNotNull().flatMap(y -> require.findWorkTimeByCode(cid, y.v()))
							.map(y -> y.getWorkTimeDisplayName().getWorkTimeName().v()),
					app.getTypeApplicationHolidays() == TypeApplicationHolidays.Abs);
		}).collect(Collectors.toList());
	}

	public static interface Require {

		// [R-1] 申請者 と申請日から勤務変更申請を取得する
		public List<ApplicationForHolidays> findApplicationForHolidaysWithSidDate(String companyId, String sid,
				GeneralDate date);

		// [R-2] 勤務変更申請を取得する
		public List<ApplicationForHolidays> findApplicationForHolidaysWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から勤務変更申請を取得する
		public List<ApplicationForHolidays> findApplicationForHolidaysWithSidDatePeriod(String companyId, String sid,
				DatePeriod period);

		// [R-4] 就業時間帯コードリストから就業時間帯の設定を取得する
		// WorkTimeSettingRepository.findByCode
		public Optional<WorkTimeSetting> findWorkTimeByCode(String companyId, String workTimeCode);

		// [R-5] 勤務種類コードリストから勤務種類を取得する
		// WorkTypeRepository.findByPK
		public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd);
	}
}
