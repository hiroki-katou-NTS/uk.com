package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptApplication;
import nts.uk.ctx.at.record.dom.stamp.application.StampRecordDis;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplication;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * DS : 打刻後の日別勤怠エラー情報を確認する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻後の日別勤怠エラー情報を確認する
 * 
 * @author tutk
 *
 */
public class CheckAttdErrorAfterStampService {
	/**
	 * 	[1] 取得する
	 * @param require
	 * @param employeeId
	 * @param pageNo
	 * @param buttonDisNo
	 * @return
	 */
	public static List<DailyAttdErrorInfo> get(Require require, String employeeId,StampButton c) {
		if(!needCheckError(require,c)) {
			return Collections.emptyList();
		}
		
		List<StampRecordDis> listStampRecordDis = getSettingPromptApp(require);
		
		if(listStampRecordDis.isEmpty()) {
			return Collections.emptyList();
		}
		
		Optional<DatePeriod> period = getErrorCheckPeriod(require, employeeId);
		if(!period.isPresent()) {
			return Collections.emptyList();
		}
		
		List<EmployeeDailyPerError> listEmployeeDailyPerError = getDailyErrorByPeriod(require, employeeId, period.get());
		List<DailyAttdErrorInfo> listResult = new ArrayList<>();
		for(StampRecordDis stampRecordDis :listStampRecordDis) {
			Optional<DailyAttdErrorInfo> opt = createDailyErrorInfo(require, stampRecordDis, listEmployeeDailyPerError);
			if(opt.isPresent()) {
				listResult.add(opt.get());
			}
		}
		return listResult;
	}
	
	/**
	 * [prv-1] 申請促す設定を取得する
	 * @param require
	 * @return
	 */
	private static List<StampRecordDis>  getSettingPromptApp(Require require) {
		Optional<StampPromptApplication> data =  require.getStampSet();
		if(!data.isPresent()) {
			return Collections.emptyList();
		}
		return data.get().getLstStampRecordDis().stream().filter(c -> c.getUseArt() == NotUseAtr.USE)
				.collect(Collectors.toList());
	}
	
	/**
	 * [prv-2] エラーを確認対象期間を取得する
	 * 
	 * @param require
	 * @param employeeId
	 * @return
	 */
	private static Optional<DatePeriod> getErrorCheckPeriod(Require require, String employeeId) {

		GeneralDate baseDate = GeneralDate.today();

		DatePeriod datePeriod = require.findClosurePeriod(employeeId, baseDate);
		
		GeneralDate dateCompare = datePeriod.start().addMonths(2);
		
		if(dateCompare.afterOrEquals(baseDate)) {
			return Optional.of(datePeriod.newSpan(datePeriod.start(), baseDate));
		}
		
		Optional<ClosurePeriod> opt =  require.getClosurePeriod( employeeId, baseDate);
		return  opt.isPresent()?Optional.of(opt.get().getPeriod()):Optional.empty();

	}
	
	/**
	 * 	[prv-3] 指定期間の日別実績エラーを取得する
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	private static List<EmployeeDailyPerError> getDailyErrorByPeriod(Require require, String employeeId,DatePeriod period) {
		return require.findByPeriodOrderByYmd(employeeId, period);
	}

	/**
	 * 	[prv-4] 日別勤怠エラー情報を作成する
	 * @param require
	 * @param stampRecordDis
	 * @param listEmployeeDailyPerError
	 * @return
	 */
	private static Optional<DailyAttdErrorInfo> createDailyErrorInfo(Require require, StampRecordDis stampRecordDis,
			List<EmployeeDailyPerError> listEmployeeDailyPerError) {
		List<String> listError = stampRecordDis.getCheckErrorType().getErrorAlarm().stream().map(x-> x.v()).collect(Collectors.toList());
		
		List<EmployeeDailyPerError> listDataError = new ArrayList<>();
		
		for(EmployeeDailyPerError employeeDailyPerError :listEmployeeDailyPerError) {
			if(listError.contains(employeeDailyPerError.getErrorAlarmWorkRecordCode().v())) {
				listDataError.add(employeeDailyPerError);
			}
		}
		Optional<EmployeeDailyPerError> employeeDailyPerError  = listDataError.stream().sorted((x,y) -> y.getDate().compareTo(x.getDate())).findFirst();
		
		if(!employeeDailyPerError.isPresent()) {
			return Optional.empty();
		}
		Optional<ErAlApplication> erAlApplication =  require.getAllErAlAppByEralCode(employeeDailyPerError.get().getErrorAlarmWorkRecordCode().v());
			
		return Optional.of(new DailyAttdErrorInfo(stampRecordDis.getCheckErrorType(),
				stampRecordDis.getPromptingMssage().get(), employeeDailyPerError.get().getDate(),
				erAlApplication.isPresent() ? erAlApplication.get().getAppType() : new ArrayList<>()));
	}

	/**
	 * [prv-5] エラー確認する必要があるか
	 * 
	 * @param require
	 * @param pageNo
	 * @param buttonDisNo
	 * @return
	 */
	private static boolean needCheckError(Require require, StampButton stampButton) {

		/*
		 * MutableValue<Boolean> flag = new MutableValue(false);
		 * 
		 * require.getStampSetPer().ifPresent( ssp -> ssp.getButtonSet(pageNo,
		 * buttonDisNo).ifPresent( bs -> bs.getButtonType().getStampType().ifPresent( st
		 * -> flag.set(st.getChangeClockArt().checkWorkingOut()) ) ) );
		 * 
		 * return flag.get();
		 */
		return require.getStampSetPer()
				.flatMap(c -> c.getButtonSet(stampButton))
				.flatMap(c -> c.getButtonType().getStampType())
				.map(c -> c.getChangeClockArt().checkWorkingOut())
				.orElse(false);
	}

	public static interface Require {
		/**
		 * [R-1] 打刻の申請促す設定を取得する StamPromptAppRepository
		 * 
		 * @param companyId
		 * @return
		 */
		Optional<StampPromptApplication> getStampSet();

		/**
		 * [R-2] 当月の締め期間を取得する ClosureService
		 * 
		 * @param employeeId
		 * @param baseDate
		 * @return
		 */
		DatePeriod findClosurePeriod(String employeeId, GeneralDate baseDate);

		/**
		 * [R-3] システム日付を含む締め期間を取得する FindClosureDateService(tham khao)
		 * 
		 * @param employeeId
		 * @param baseDate
		 * @return
		 */
		Optional<ClosurePeriod> getClosurePeriod(String employeeId, GeneralDate baseDate);

		/**
		 * [R-4] エラー発生時に呼び出す申請一覧を取得する ErAlApplicationRepository
		 * 
		 * @param companyID
		 * @param errorAlarmCode
		 * @return
		 */
		Optional<ErAlApplication> getAllErAlAppByEralCode(String errorAlarmCode);

		/**
		 * [R-5] 社員の日別実績エラー一覧を取得する EmployeeDailyPerErrorRepository
		 * 
		 * @param employeeId
		 * @param datePeriod
		 * @return
		 */
		List<EmployeeDailyPerError> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod);

		/**
		 * [R-6] 個人利用の打刻設定を取得する StampSetPerRepository
		 * 
		 * @param companyId
		 * @return
		 */
		Optional<StampSettingPerson> getStampSetPer();

	}
}
