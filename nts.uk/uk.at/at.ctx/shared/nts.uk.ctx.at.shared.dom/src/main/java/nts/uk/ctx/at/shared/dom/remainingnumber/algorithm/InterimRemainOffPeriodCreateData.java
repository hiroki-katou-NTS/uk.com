package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.AffPeriodEmpCodeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg.UpdateNumberUnoffDaikyuProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg.UpdateNumberUnoffFurikyuProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.EmploymentHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;

/**
 * 指定期間の暫定残数管理データを作成する
 * @author do_dt
 *
 */
public class InterimRemainOffPeriodCreateData {
	/**
	 * 指定期間の暫定残数管理データを作成する
	 * @param inputParam
	 * @return
	 */
	public static Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainDataMng(RequireM4 require, CacheCarrier cacheCarrier,
			InterimRemainCreateDataInputPara inputParam, CompanyHolidayMngSetting comHolidaySetting) {
		Map<GeneralDate, DailyInterimRemainMngData> dataOutput = new HashMap<>();
		//アルゴリズム「社員ID（List）と指定期間から社員の雇用履歴を取得」を実行する
		List<String> lstEmployee = new ArrayList<>();
		lstEmployee.add(inputParam.getSid());
		List<SharedSidPeriodDateEmploymentImport> emloymentHist = require.employmentHistory(cacheCarrier, lstEmployee, inputParam.getDateData());
		//所属雇用履歴を設定する
		List<AffPeriodEmpCodeImport> lstEmployment = !emloymentHist.isEmpty() ? emloymentHist.get(0).getAffPeriodEmpCodeExports() : new ArrayList<>();
		List<EmploymentHolidayMngSetting> lstEmplSetting = lstEmpHolidayMngSetting(require, inputParam.getCid(), lstEmployment);
		GeneralDate sStartDate = inputParam.getDateData().start();
		GeneralDate sEndDate = inputParam.getDateData().end();
		//対象日の雇用別休暇管理設定を抽出する
		List<AffPeriodEmpCodeImport> lstDateEmployment = lstEmployment.stream()
				.filter(x -> x.getPeriod().start().beforeOrEquals(sEndDate) && x.getPeriod().end().afterOrEquals(sStartDate))
				.collect(Collectors.toList());
		EmploymentHolidayMngSetting employmentHolidaySetting = new EmploymentHolidayMngSetting();
		if(!lstDateEmployment.isEmpty() && lstDateEmployment.size() == 1) {
			AffPeriodEmpCodeImport dateEmployment = lstDateEmployment.get(0);
			List<EmploymentHolidayMngSetting> lstEmploymentSetting = lstEmplSetting.stream()
					.filter(y -> y.getEmploymentCode().equals(dateEmployment.getEmploymentCode()))
					.collect(Collectors.toList());
			if(!lstEmploymentSetting.isEmpty()) {
				employmentHolidaySetting = lstEmploymentSetting.get(0);
			}
		}

		for (int i = 0; sStartDate.daysTo(sEndDate) - i >= 0; i++) {
			//作成対象日を設定する
			GeneralDate loopDate = inputParam.getDateData().start().addDays(i);
			//theo thuật toán ở step trước thì 2 cái if dưới này ko bao giờ chạy vào
			if(!inputParam.getAppData().isEmpty()
					&& inputParam.getAppData().get(0).getLstAppDate() != null
					&& !inputParam.getAppData().get(0).getLstAppDate().isEmpty()
					&& inputParam.getAppData().get(0).getLstAppDate().contains(loopDate)) {
				continue;
			}
			if(employmentHolidaySetting == null || employmentHolidaySetting.getEmploymentCode() == null) {
				lstDateEmployment = lstEmployment.stream()
						.filter(x -> x.getPeriod().start().beforeOrEquals(loopDate) && x.getPeriod().end().afterOrEquals(loopDate))
						.collect(Collectors.toList());
				if(!lstDateEmployment.isEmpty()) {
					AffPeriodEmpCodeImport dateEmployment = lstDateEmployment.get(0);
					List<EmploymentHolidayMngSetting> lstEmploymentSetting = lstEmplSetting.stream()
							.filter(y -> y.getEmploymentCode().equals(dateEmployment.getEmploymentCode()))
							.collect(Collectors.toList());
					if(!lstEmploymentSetting.isEmpty()) {
						employmentHolidaySetting = lstEmploymentSetting.get(0);
					}
				}
			}
			//対象日のデータを抽出する
			InterimRemainCreateInfor dataCreate = extractDataOfDate(require, cacheCarrier, inputParam.getCid(),
					loopDate, inputParam);


			//アルゴリズム「指定日の暫定残数管理データを作成する」
			Optional<DailyInterimRemainMngData> outPutdata = InterimRemainOffDateCreateData.createData(
					require,
					inputParam.getCid(),
					inputParam.getSid(),
					loopDate,
					comHolidaySetting.getDayOffSetting().isManagedTime(),
					dataCreate,
					comHolidaySetting,
					employmentHolidaySetting,
					inputParam.getCallFunction());
			if(outPutdata.isPresent()) {
				dataOutput.put(loopDate, outPutdata.get());
			}
		}

		return dataOutput;
	}

	/**
	 * 対象日のデータを抽出する
	 * @param baseDate
	 * @param inputInfor
	 * @return
	 */
	public static InterimRemainCreateInfor extractDataOfDate(RequireM3 require, CacheCarrier cacheCarrier, String cid,GeneralDate baseDate,
			InterimRemainCreateDataInputPara inputInfor) {
		InterimRemainCreateInfor detailData = new InterimRemainCreateInfor(Optional.empty(), Optional.empty(), Collections.emptyList());
		//実績を抽出する
		List<RecordRemainCreateInfor> recordData = inputInfor.getRecordData()
				.stream()
				.filter(x -> x.getSid().equals(inputInfor.getSid()) && x.getYmd().equals(baseDate))
				.collect(Collectors.toList());
		if(!recordData.isEmpty()) {
			detailData.setRecordData(Optional.of(recordData.get(0)));
		}

		//対象日の予定を抽出する
		List<ScheRemainCreateInfor> scheData = inputInfor.getScheData().stream()
				.filter(z -> z.getSid().equals(inputInfor.getSid()) && z.getYmd().equals(baseDate))
				.collect(Collectors.toList());
		if(!scheData.isEmpty()) {
			detailData.setScheData(Optional.of(scheData.get(0)));
		}
		//対象日の申請を抽出する
		List<AppRemainCreateInfor> appData = inputInfor.getAppData().stream()
				.filter(y -> y.getSid().equals(inputInfor.getSid())
						&& (y.getAppDate().equals(baseDate)
								|| (y.getStartDate().isPresent()
										&& y.getEndDate().isPresent()
										&& y.getStartDate().get().beforeOrEquals(baseDate)
										&& y.getEndDate().get().afterOrEquals(baseDate))
								)
						)
				.collect(Collectors.toList());
		appData = appData.stream().sorted((a,b) -> b.getInputDate().compareTo(a.getInputDate())).collect(Collectors.toList());
		//Integer excludeHolidayAtr = null;
//		if(!appData.isEmpty() && appData.get(0).getAppType() == ApplicationType.WORK_CHANGE_APPLICATION) {
//			//excludeHolidayAtr = require.excludeHolidayAtr(cacheCarrier, cid, appData.get(0).getAppId());
//		}
		//申請：　勤務変更申請、休日を除外する
		//又は　休暇申請
//		if((excludeHolidayAtr != null && excludeHolidayAtr == 1)
//				|| (!appData.isEmpty() && appData.get(0).getAppType() == ApplicationType.ABSENCE_APPLICATION)) {
//			//申請日は休日かチェック、休日なら申請データをセットしない
//			if((detailData.getRecordData().isPresent() && WorkTypeIsClosedService.checkHoliday(require, detailData.getRecordData().get().getWorkTypeCode()))
//					|| (detailData.getScheData().isPresent() && WorkTypeIsClosedService.checkHoliday(require, detailData.getScheData().get().getWorkTypeCode()))) {
//				return detailData;
//			}
//		}
		detailData.setAppData(appData);
		return detailData;

	}

	/**
	 * 雇用履歴と休暇管理設定を取得する
	 * @param cid
	 * @param sid
	 * @return
	 */
	public static List<EmploymentHolidayMngSetting> lstEmpHolidayMngSetting(RequireM1 require, String cid, List<AffPeriodEmpCodeImport> lstEmployment) {
		List<EmploymentHolidayMngSetting> lstEmplSetting = new ArrayList<>();
		//雇用別休暇管理設定(List)を作成する
		lstEmployment.stream().forEach(emplData -> {
			//ドメインモデル「雇用振休管理設定」を取得する
			Optional<EmpSubstVacation> optEmpSubData = require.empSubstVacation(cid, emplData.getEmploymentCode());
			//ドメインモデル「雇用代休管理設定」を取得する
			CompensatoryLeaveEmSetting empSetting = require.compensatoryLeaveEmSetting(cid, emplData.getEmploymentCode());
			EmploymentHolidayMngSetting employmentSetting = new EmploymentHolidayMngSetting(emplData.getEmploymentCode(), optEmpSubData, empSetting);
			lstEmplSetting.add(employmentSetting);
		});
		return lstEmplSetting;
	}

	/**
	 * 指定期間の暫定残数管理データを作成する（差分のみ）
	 * @param param
	 * @return
	 */
	public static Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainByScheRecordApp(RequireM2 require,
			CacheCarrier cacheCarrier, InterimRemainCreateDataInputPara param) {
		//Input「予定」がNULLかどうかチェック
		if(param.getScheData().isEmpty()) {
			//(Imported)「残数作成元の勤務予定を取得する」
			param.setScheData(require.scheRemainCreateInfor(param.getCid(), param.getSid(), param.getDateData()));
		}
		//Input「実績」がNULLかどうかチェック
		if(param.getRecordData().isEmpty()) {
			param.setRecordData(require.recordRemainCreateInfor(cacheCarrier, param.getCid(), param.getSid(), param.getDateData()));
		}
		//(Imported)「残数作成元の申請を取得する」
		List<AppRemainCreateInfor> lstAppData = require.appRemainCreateInfor(cacheCarrier, param.getCid(), param.getSid(), param.getDateData());
		//Input「申請」がNULLかどうかチェック
		if(!lstAppData.isEmpty()) {
			lstAppData.addAll(param.getAppData());
			param.setAppData(lstAppData);
		}
		Optional<ComSubstVacation> comSetting = require.comSubstVacation(param.getCid());
		CompensatoryLeaveComSetting leaveComSetting = require.compensatoryLeaveComSetting(param.getCid());
		CompanyHolidayMngSetting comHolidaySetting = new CompanyHolidayMngSetting(param.getCid(), comSetting, leaveComSetting);
		InterimRemainCreateDataInputPara createDataParam = new InterimRemainCreateDataInputPara(param.getCid(),
				param.getSid(),
				param.getDateData(),
				param.getRecordData(),
				param.getScheData(),
				param.getAppData());
		Map<GeneralDate, DailyInterimRemainMngData> result = createInterimRemainDataMng(require, cacheCarrier,
				createDataParam, comHolidaySetting);
		updateUnoffDaikyuFurikyu(require, param.getSid(), result);
		return result;
	}

	// 代休振休の未相殺数を更新する
	private static void updateUnoffDaikyuFurikyu(RequireM2 require, String sid,
			Map<GeneralDate, DailyInterimRemainMngData> result) {
		updateUnoffDaikyu(require, sid, result);
		updateUnoffFurikyu(require, sid, result);
	}

	// 代休の未相殺数を更新する
	private static void updateUnoffDaikyu(RequireM2 require, String sid,
			Map<GeneralDate, DailyInterimRemainMngData> result) {

		List<InterimDayOffMng> lstDayoff = result.values().stream().flatMap(x -> x.getDayOffData().stream())
				.collect(Collectors.toList());
		List<InterimBreakMng> lstBreakoff = result.values().stream().filter(x -> x.getBreakData().isPresent())
				.map(x -> x.getBreakData().get()).collect(Collectors.toList());
		List<GeneralDate> lstDate = Stream.concat(lstBreakoff.stream(), lstDayoff.stream()).map(x -> x.getYmd())
				.distinct().collect(Collectors.toList());
		val updateDaikyu = UpdateNumberUnoffDaikyuProcess.processDaikyu(require, sid, lstDate, lstDayoff, lstBreakoff);
		result.forEach((key, value) -> {
			val kyusyutsu = updateDaikyu.getKyusyutsu().stream().filter(x -> x.getYmd().equals(key)).findFirst();
			kyusyutsu.ifPresent(x -> {
				value.setBreakData(kyusyutsu);
			});

			val daikyu = updateDaikyu.getDaikyu().stream().filter(x -> x.getYmd().equals(key))
					.collect(Collectors.toList());
			if (!daikyu.isEmpty()) {
				value.setDayOffData(daikyu);
			}
		});
	}
	
	// 振休の未相殺数を更新する
	private static void updateUnoffFurikyu(RequireM2 require, String sid,
			Map<GeneralDate, DailyInterimRemainMngData> result) {

		List<InterimAbsMng> lstAbsMng = result.values().stream().filter(x -> x.getInterimAbsData().isPresent())
				.map(x -> x.getInterimAbsData().get()).collect(Collectors.toList());

		List<InterimRecMng> lstRecMng = result.values().stream().filter(x -> x.getRecData().isPresent())
				.map(x -> x.getRecData().get()).collect(Collectors.toList());
		List<GeneralDate> lstDate = Stream.concat(lstAbsMng.stream(), lstRecMng.stream()).map(x -> x.getYmd())
				.distinct().collect(Collectors.toList());
		val updateDaikyu = UpdateNumberUnoffFurikyuProcess.processFurikyu(require, sid, lstDate, lstAbsMng, lstRecMng);
		result.forEach((key, value) -> {
			val furisyutsu = updateDaikyu.getFurisyutsu().stream().filter(x -> x.getYmd().equals(key)).findFirst();
			furisyutsu.ifPresent(x -> {
				value.setRecData(furisyutsu);
			});

			val furikyu = updateDaikyu.getFurikyu().stream().filter(x -> x.getYmd().equals(key)).findFirst();
			furikyu.ifPresent(x -> {
				value.setInterimAbsData(furikyu);
			});
		});
	}
	
	public static interface RequireM4 extends RequireM1, RequireM3, InterimRemainOffDateCreateData.RequireM9 {

		List<SharedSidPeriodDateEmploymentImport> employmentHistory(CacheCarrier cacheCarrier, List<String> sids , DatePeriod datePeriod);
	}

	public static interface RequireM3 extends WorkTypeIsClosedService.RequireM1 {

		//Integer excludeHolidayAtr(CacheCarrier cacheCarrier, String cid,String appID);
	}

	public static interface RequireM2 extends RequireM4, UpdateNumberUnoffFurikyuProcess.Require, UpdateNumberUnoffDaikyuProcess.Require {
		List<ScheRemainCreateInfor> scheRemainCreateInfor(String cid, String sid, DatePeriod dateData);

		List<RecordRemainCreateInfor> recordRemainCreateInfor(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData);

		List<AppRemainCreateInfor> appRemainCreateInfor(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData);

		Optional<ComSubstVacation> comSubstVacation(String companyId);

		CompensatoryLeaveComSetting compensatoryLeaveComSetting(String companyId);
	}

	public static interface RequireM1 {

		Optional<EmpSubstVacation> empSubstVacation(String companyId, String contractTypeCode);

		CompensatoryLeaveEmSetting compensatoryLeaveEmSetting(String companyId, String employmentCode);

	}

}
