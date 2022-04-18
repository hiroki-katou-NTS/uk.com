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
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryWorkChangeApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeZone;

/**
 * @author thanh_nx
 *
 *         NRWeb照会勤務変更申請を取得
 */
public class GetNRWebQueryWorkChangeAppDetail {
	// [S-1] プロセス
	public static List<NRQueryWorkChangeApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {

		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.WORK_CHANGE_APPLICATION);
		List<AppWorkChange> lstAppWorkChangeList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstAppWorkChangeList.addAll(require.findAppWorkChangeWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstAppWorkChangeList.addAll(require.findAppWorkChangeWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstAppWorkChangeList
					.addAll(require.findAppWorkChangeWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}

		List<NRQueryWorkChangeApp> result = new ArrayList<>();
		for (AppWorkChange app : lstAppWorkChangeList) {
			Optional<String> workTypeName = app.getOpWorkTypeCD()
					.flatMap(x -> require.findWorkTypeByPK(param.getCid(), x.v()))
					.map(x -> x.getAbbreviationName().v());
			Optional<String> workTimeName = app.getOpWorkTimeCD()
					.flatMap(x -> require.findWorkTimeByCode(param.getCid(), x.v())
							.map(y -> y.getWorkTimeDisplayName().getWorkTimeName().v()));
			List<TimeZone> lstTimeZone = app.getTimeZoneWithWorkNoLst().stream()
					.map(x -> new TimeZone(x.getTimeZone().getStartTime().v(), x.getTimeZone().getEndTime().v()))
					.collect(Collectors.toList());
			for (ReflectionStatusOfDay state : app.getReflectionStatus().getListReflectionStatusOfDay()) {
				NRQueryApp appQuery = NRQueryApp.create(app, state);
				result.add(new NRQueryWorkChangeApp(appQuery, app.getStraightGo(), app.getStraightBack(), workTimeName,
						workTypeName, lstTimeZone));
			}

		}
		return result;
	}

	public static interface Require {

		// [R-1] 申請者 と申請日から勤務変更申請を取得する
		public List<AppWorkChange> findAppWorkChangeWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-2] 勤務変更申請を取得する
		public List<AppWorkChange> findAppWorkChangeWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から勤務変更申請を取得する
		public List<AppWorkChange> findAppWorkChangeWithSidDatePeriod(String companyId, String sid, DatePeriod period);

		// [R-4] 就業時間帯コードリストから就業時間帯の設定を取得する
		// WorkTimeSettingRepository.findByCode
		public Optional<WorkTimeSetting> findWorkTimeByCode(String companyId, String workTimeCode);

		// [R-5] 勤務種類コードリストから勤務種類を取得する
		// WorkTypeRepository.findByPK
		public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd);
	}
}
