package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryTimeLeaveApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryTimeLeaveAppDetail;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.shr.com.i18n.TextResource;

/**
 * @author thanh_nx
 *
 *         NRWeb照会時間年休申請を取得
 */
public class GetNRWebQueryTimeLeaveAppDetail {

	// [S-1] プロセス
	public static List<NRQueryTimeLeaveApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.ANNUAL_HOLIDAY_APPLICATION);
		List<TimeLeaveApplication> lstTimeLeaveList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstTimeLeaveList.addAll(require.findTimeLeaveWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstTimeLeaveList.addAll(require.findTimeLeaveWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstTimeLeaveList.addAll(require.findTimeLeaveWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}

		return createListResult(lstTimeLeaveList);
	}

	// [pvt-1] 時間を作る
	private static String createTime(TimeDigestApplication timeDigestApplication) {
		int sumTime = timeDigestApplication.sumTime().v();
		return String.format("%02d:%02d", sumTime / 60, sumTime % 60);
	}

	// [pvt-2] 申請のデータを作る
	private static List<NRQueryTimeLeaveApp> createListResult(List<TimeLeaveApplication> lstTimeLeaveList) {
		List<NRQueryTimeLeaveApp> result = new ArrayList<>();
		for (TimeLeaveApplication app : lstTimeLeaveList) {
			List<NRQueryTimeLeaveAppDetail> timeLeavDetail = app.getLeaveApplicationDetails().stream().map(x -> {
				switch (x.getAppTimeType()) {
				case ATWORK:
					return new NRQueryTimeLeaveAppDetail(TextResource.localize("Com_WorkIn"),
							createTime(x.getTimeDigestApplication()));
				case OFFWORK:
					return new NRQueryTimeLeaveAppDetail(TextResource.localize("Com_WorkOut"),
							createTime(x.getTimeDigestApplication()));
				case ATWORK2:
					return new NRQueryTimeLeaveAppDetail(TextResource.localize("Com_WorkIn"),
							createTime(x.getTimeDigestApplication()));
				case OFFWORK2:
					return new NRQueryTimeLeaveAppDetail(TextResource.localize("Com_WorkOut"),
							createTime(x.getTimeDigestApplication()));
				case PRIVATE:
					return new NRQueryTimeLeaveAppDetail("私用" + TextResource.localize("Com_Out"),
							createTime(x.getTimeDigestApplication()));
				case UNION:
					return new NRQueryTimeLeaveAppDetail("組合" + TextResource.localize("Com_Out"),
							createTime(x.getTimeDigestApplication()));
				default:
					throw new RuntimeException("GetNRWebQueryTimeLeaveAppDetail error");
				}
			}).collect(Collectors.toList());

			for (ReflectionStatusOfDay state : app.getReflectionStatus().getListReflectionStatusOfDay()) {
				NRQueryApp appQuery = NRQueryApp.create(app, state);

				result.add(new NRQueryTimeLeaveApp(appQuery, timeLeavDetail));
			}

		}
		return result;
	}

	public static interface Require {
		// [R-1] 申請者 と申請日から時間年休申請を取得する
		public List<TimeLeaveApplication> findTimeLeaveWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-2] 時間年休申請を取得する
		public List<TimeLeaveApplication> findTimeLeaveWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から時間年休申請を取得する
		public List<TimeLeaveApplication> findTimeLeaveWithSidDatePeriod(String companyId, String sid,
				DatePeriod period);

	}
}
