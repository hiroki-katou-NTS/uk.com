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
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryBussinessTripApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.TripAppTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author thanh_nx
 *
 *         NRWeb照会出張申請を取得
 */
public class GetNRWebQueryBussinessTripAppDetail {

	// [S-1] プロセス
	public static List<NRQueryBussinessTripApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.BUSINESS_TRIP_APPLICATION);
		List<BusinessTrip> lstBussinessTripList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstBussinessTripList.addAll(require.findBussinessTripWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstBussinessTripList.addAll(require.findBussinessTripWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstBussinessTripList
					.addAll(require.findBussinessTripWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}

		List<NRQueryBussinessTripApp> result = new ArrayList<>();
		for (BusinessTrip app : lstBussinessTripList) {
			for (BusinessTripInfo infro : app.getInfos()) {
				Optional<String> workTypeName = require
						.findWorkTypeByPK(param.getCid(), infro.getWorkInformation().getWorkTypeCode().v())
						.map(x -> x.getAbbreviationName().v());
				Optional<String> workTimeName = infro.getWorkInformation().getWorkTimeCodeNotNull()
						.flatMap(x -> require.findWorkTimeByCode(param.getCid(), x.v())
								.map(y -> y.getWorkTimeDisplayName().getWorkTimeName().v()));
				List<TripAppTimeZone> lstTimeZone = infro.getWorkingHours().stream()
						.map(x -> new TripAppTimeZone(x.getStartDate(), x.getEndDate())).collect(Collectors.toList());
				for (ReflectionStatusOfDay state : app.getReflectionStatus().getListReflectionStatusOfDay().stream()
						.filter(x -> x.getTargetDate().equals(infro.getDate())).collect(Collectors.toList())) {
					NRQueryApp appQuery = NRQueryApp.create(app, state);
					result.add(new NRQueryBussinessTripApp(appQuery, workTimeName, workTypeName, lstTimeZone));
				}
			}
		}
		return result;
	}

	public static interface Require {

		// [R-1] 申請者 と申請日から出張申請を取得する
		public List<BusinessTrip> findBussinessTripWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-2] 出張申請を取得する
		public List<BusinessTrip> findBussinessTripWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から出張申請を取得する
		public List<BusinessTrip> findBussinessTripWithSidDatePeriod(String companyId, String sid, DatePeriod period);

		// [R-4] 就業時間帯コードリストから就業時間帯の設定を取得する
		// WorkTimeSettingRepository.findByCode
		public Optional<WorkTimeSetting> findWorkTimeByCode(String companyId, String workTimeCode);

		// [R-5] 勤務種類コードリストから勤務種類を取得する
		// WorkTypeRepository.findByPK
		public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd);
	}

}
