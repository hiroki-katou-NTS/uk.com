package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryGoBackDirectlyApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author thanh_nx
 *
 *         NRWeb照会直行直帰申請を取得
 */
public class GetNRWebQueryGoBackDirectlyAppDetail {

	// [S-1] プロセス
	public static List<NRQueryGoBackDirectlyApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION);
		List<GoBackDirectly> lstGoBackDirectlyList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstGoBackDirectlyList.addAll(require.findGoBackDirectlyWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstGoBackDirectlyList.addAll(require.findGoBackDirectlyWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstGoBackDirectlyList
					.addAll(require.findGoBackDirectlyWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}
		List<NRQueryGoBackDirectlyApp> result = new ArrayList<>();
		for (GoBackDirectly app : lstGoBackDirectlyList) {
			Optional<String> workTypeName = app.getDataWork()
					.flatMap(x -> require.findWorkTypeByPK(param.getCid(), x.getWorkTypeCode().v()))
					.map(x -> x.getAbbreviationName().v());
			Optional<String> workTimeName = app.getDataWork()
					.flatMap(x -> x.getWorkTimeCodeNotNull()
							.flatMap(y -> require.findWorkTimeByCode(param.getCid(), y.v()))
							.map(y -> y.getWorkTimeDisplayName().getWorkTimeName().v()));
			for (ReflectionStatusOfDay state : app.getReflectionStatus().getListReflectionStatusOfDay()) {
				NRQueryApp appQuery = NRQueryApp.create(app, state);
				result.add(new NRQueryGoBackDirectlyApp(appQuery, app.getStraightDistinction(), app.getStraightLine(),
						workTimeName, workTypeName));
			}

		}
		return result;
	}

	public static interface Require {

		// [R-1] 申請者 と申請日から直行直帰申請を取得する
		public List<GoBackDirectly> findGoBackDirectlyWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-2] 直行直帰申請を取得する
		public List<GoBackDirectly> findGoBackDirectlyWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から直行直帰申請を取得する
		public List<GoBackDirectly> findGoBackDirectlyWithSidDatePeriod(String companyId, String sid,
				DatePeriod period);

		// [R-4] 就業時間帯コードリストから就業時間帯の設定を取得する
		// WorkTimeSettingRepository.findByCode
		public Optional<WorkTimeSetting> findWorkTimeByCode(String companyId, String workTimeCode);

		// [R-5] 勤務種類コードリストから勤務種類を取得する
		// WorkTypeRepository.findByPK
		public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd);
	}
}
