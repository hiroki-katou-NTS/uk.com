package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.AffCompanyHistImport;
import nts.uk.ctx.at.function.dom.adapter.EmployeeHistWorkRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.AcquireActualStatus;
import nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.ApprovalStatus;
import nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.EmploymentFixedStatus;
import nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.MonthlyActualSituationOutput;
import nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.MonthlyActualSituationStatus;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.ControlOfMonthlyFinder;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.DisplayAndInputMonthlyDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyItemControlByAuthDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyItemControlByAuthFinder;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTime;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTimeState;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.CorrectionOfMonthlyPerformance;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.DisplayItem;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.FormatMPCorrectionDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPBusinessTypeControl;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPSheetDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
import nts.uk.screen.at.app.monthlyperformance.correction.param.MonthlyPerformaceLockStatus;
import nts.uk.screen.at.app.monthlyperformance.correction.param.MonthlyPerformanceParam;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class MonthlyPerformanceDisplay {
	/** 権限別月次項目制御 */
	@Inject
	MonthlyItemControlByAuthFinder monthlyItemControlByAuthFinder;
	/** 月次の勤怠項目の制御 */
	@Inject
	ControlOfMonthlyFinder controlOfMonthlyFinder;
	@Inject
	private DailyPerformanceScreenRepo repo;
	@Inject
	private MonthlyAttendanceItemRepository monthlyAttendanceItemRepo;
	
	@Inject
	private EmployeeHistWorkRecordAdapter employeeHistWorkRecordAdapter;
	@Inject 
	private MonthlyActualSituationStatus monthlyActualStatus;
	@Inject
	AffWorkplaceAdapter affWorkplaceAdapter;
	//@Inject
	//private AttendanceItemLinkingRepository attendanceItemLinkingRepository;
	/**
	 * 実績の時系列をチェックする
	 */
	@Inject 
	private MonthlyPerformanceCheck monthlyCheck;
	private static final String KMW003_SELECT_FORMATCODE = "KMW003_SELECT_FORMATCODE";
	/**
	 * 表示フォーマットの取得
	 * @param lstEmployees: 社員一覧：List＜社員ID＞
	 * @param formatCodes: 使用するフォーマットコード：月別実績フォーマットコード
	 * 表示する項目一覧
	 */
	public DisplayItem getDisplayFormat(List<String> lstEmployeeIds, SettingUnitType unitType, MonthlyPerformanceCorrectionDto screenDto){
		//会社ID：ログイン会社に一致する
		String cId = AppContexts.user().companyId();
		//ロールID：ログイン社員の就業ロールに一致する
		String employmentRoleID = AppContexts.user().roles().forAttendance();
		//パラメータ
		MonthlyPerformanceParam param = screenDto.getParam();		
		DateRange dateRange = new DateRange(screenDto.getSelectedActualTime().getStartDate(), screenDto.getSelectedActualTime().getEndDate());
		DisplayItem dispItem;
		//権限の場合 
		if(unitType == SettingUnitType.AUTHORITY){
			//アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
			dispItem = getDisplayItemAuthority(cId, param.getFormatCodes(), param.getCorrectionOfMonthly());
		}
		//勤務種別の場合
		else{
			//社員の勤務種別に対応する表示項目を取得する
			//(Lấy các item hiển thị ứng với loại đi làm của employee)
			dispItem = getDisplayItemBussiness(lstEmployeeIds, dateRange,  param.getFormatCodes(), param.getCorrectionOfMonthly());
		}
		//対応するドメインモデル「権限別月次項目制御」を取得する
		MonthlyItemControlByAuthDto monthlyItemAuthDto = monthlyItemControlByAuthFinder.getMonthlyItemControlByRoleID(employmentRoleID);
		//TODO check null monthlyItemAuthDto
		//取得したドメインモデル「権限別月次項目制御」でパラメータ「表示する項目一覧」をしぼり込む
		//Filter param 「表示する項目一覧」  by domain 「権限別月次項目制御」
		Set<Integer> acceptableAttendanceId = monthlyItemAuthDto.getListDisplayAndInputMonthly().stream()
				.map(DisplayAndInputMonthlyDto::getItemMonthlyId).collect(Collectors.toSet());
		
		List<Integer> lstAtdItemUnique = new ArrayList<>();		
		lstAtdItemUnique = dispItem.getLstFormat().stream().
				filter(c -> acceptableAttendanceId.contains(c.getAttendanceItemId()))
				.map(item -> item.getAttendanceItemId())
				.collect(Collectors.toList());
		
		dispItem.setLstAtdItemUnique(lstAtdItemUnique);
		
		if(!CollectionUtil.isEmpty(lstAtdItemUnique)){
			//ドメインモデル「月次の勤怠項目」を取得する
			List<MonthlyAttendanceItem> lstAttendanceIds = monthlyAttendanceItemRepo.findByAttendanceItemId(cId, lstAtdItemUnique);
			
			//TODO 対応するドメインモデル「勤怠項目と枠の紐付け」を取得する  - attendanceItemLinkingRepository
			//Domain hien tai dang nam trong function.
			
			//TODO 取得したドメインモデルの名称をドメインモデル「勤怠項目．名称」に埋め込む 
			//Update Attendance Name
			
			//ドメインモデル「月次の勤怠項目の制御」を取得する
			//Setting Header color & time input
			//ControlOfMonthlyDto ctrOfMonthlyDto = controlOfMonthlyFinder.getControlOfAttendanceItem(1);
			
		}
		//アルゴリズム「ロック状態をチェックする」を実行する
		List<MonthlyPerformaceLockStatus> lstLockStatus = checkLockStatus(cId, lstEmployeeIds, screenDto.getProcessDate(), screenDto.getClosureId(), new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate()), param.getInitScreenMode());
		param.setLstLockStatus(lstLockStatus);
		
		return dispItem;
	}

	/**
	 * アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
	 * (Lấy các item hiển thị ứng với quyền employee)
	 * @param cId
	 * @param formatCodes
	 * @param correctionOfMonthly
	 * @return 表示する項目一覧
	 */
	private DisplayItem getDisplayItemAuthority(String cId, List<String> formatCodes, CorrectionOfMonthlyPerformance correctionOfMonthly){		
		DisplayItem dispItem = new DisplayItem();
		List<FormatMPCorrectionDto> lstFormat = new ArrayList<FormatMPCorrectionDto>();
		List<MPSheetDto> lstSheet = new ArrayList<MPSheetDto>();
		List<Integer> lstAtdItem = new ArrayList<>();
		List<Integer> lstAtdItemUnique = new ArrayList<>();
		if(null == formatCodes){
			//ユーザー固有情報「月別実績のの修正」．前回の表示項目が取得できたかチェックする			
			if(null !=  correctionOfMonthly && !Strings.isEmpty(correctionOfMonthly.getPreviousDisplayItem())){
				//取得したユーザー固有情報「月別実績の修正．前回の表示項目」をパラメータ「使用するフォーマットコード」にセットする
				formatCodes = Arrays.asList(correctionOfMonthly.getPreviousDisplayItem());
			}else{
				//対応するドメインモデル「月次の初期表示フォーマット」を取得する
				//TODO 月次の初期表示フォーマット
				//Optional<MonthlyInitDisplayFormat> initDisp = initDisplay.getMonthlyInitDisplayFormat(cId);
				//if(initDisp.isPresent()){
					//取得したドメインモデル「月次の初期表示フォーマット」をパラメータ「使用するフォーマットコード」にセットする
				//	formatCodes = Arrays.asList(initDisp.get().getCode());
				//TODO Dummy data
				formatCodes = Arrays.asList("001");
				//}else{
					//アルゴリズム「表示項目の選択を起動する」を実行する
					//ダイアログで選択していたフォーマットコードをパラメータ「使用するフォーマットコード」にセットする
					throw new BusinessException(KMW003_SELECT_FORMATCODE);
				//}
			}
		}		
		//TODO 会社の月別実績の修正フォーマット
		//対応するドメインモデル「会社の月別実績の修正フォーマット」を取得する
		//Contains list of sheet (月別実績の修正のシート) and list of display item in sheet (表示する項目)
		
		
		dispItem.setLstFormat(lstFormat);
		dispItem.setLstSheet(lstSheet);
		
		return dispItem;
	}
	
	/**
	 * 社員の勤務種別に対応する表示項目を取得する
	 * (Lấy các item hiển thị ứng với loại đi làm của employee)
	 * @return
	 */
	private DisplayItem getDisplayItemBussiness(
			List<String> lstEmployeeId,
			DateRange dateRange, 
			List<String> formatCodes, 
			CorrectionOfMonthlyPerformance correctionOfDaily){
		DisplayItem dispItem = new DisplayItem();
		if (CollectionUtil.isEmpty(lstEmployeeId)) {
			return dispItem;
		}		
		List<FormatMPCorrectionDto> lstFormat = new ArrayList<FormatMPCorrectionDto>();
		List<MPSheetDto> lstSheet = new ArrayList<MPSheetDto>();
		List<Integer> lstAtdItem = new ArrayList<>();
		List<Integer> lstAtdItemUnique = new ArrayList<>();
		
		//特定の社員が所属する勤務種別をすべて取得する Lấy tất cả các loại đi làm của employee chỉ định
		List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployeeId, dateRange);
		List<MPBusinessTypeControl> lstMPBusinessTypeControl = new ArrayList<>();
		// Create header & sheet
		//取得したImported「（就業機能）勤務種別」の件数をチェックする
		if (lstBusinessTypeCode.size() == 0) {
			//エラーメッセージ（#）を表示する
			//TODO missing message ID
			throw new BusinessException("エラーメッセージ（#）を表示する");
		}else if(lstBusinessTypeCode.size() == 1) {
			//対応するドメインモデル「勤務種別の月別実績の修正のフォーマット」を取得する
			//TODO Lấy domain 「勤務種別の月別実績の修正のフォーマット」
		}else if(lstBusinessTypeCode.size() >= 2) {
			//TODO 対応するドメインモデル「勤務種別の月別実績の修正のフォーマット」を取得する
		}
		
		
		//取得した「勤務種別の月別実績の修正のフォーマット」に表示するすべての項目の列幅があるかチェックする
		//Check xem tất cả item có hiển thị trong 「勤務種別の月別実績の修正のフォーマット」
		
		dispItem.setLstFormat(lstFormat);
		dispItem.setLstSheet(lstSheet);
		return dispItem;
	}
	/**
	 * ロック状態をチェックする
	 * @param empIds 社員一覧：List＜社員ID＞
	 * @param processDateYM 処理年月：年月
	 * @param closureId 締めID：締めID
	 * @param dateRange 締め期間：期間
	 * 
	 * ロック状態一覧：List＜月の実績のロック状態＞
	 */
	public List<MonthlyPerformaceLockStatus> checkLockStatus(String cid, List<String> empIds, Integer processDateYM, Integer closureId, DatePeriod closureTime, int intScreenMode){
		List<MonthlyPerformaceLockStatus> monthlyLockStatusLst = new ArrayList<MonthlyPerformaceLockStatus>();
		//ロック解除モード　の場合
		if (intScreenMode == 1) {
			return monthlyLockStatusLst;
		}
		//社員ID（List）と基準日から所属職場IDを取得
		//基準日：パラメータ「締め期間」の終了日
		List<AffAtWorkplaceImport> affWorkplaceLst = affWorkplaceAdapter.findBySIdAndBaseDate(empIds, closureTime.end());
		if(CollectionUtil.isEmpty(affWorkplaceLst)){
			return monthlyLockStatusLst; 
		}
		//「List＜所属職場履歴項目＞」の件数ループしてください 
		MonthlyPerformaceLockStatus monthlyLockStatus = null;
		for (AffAtWorkplaceImport affWorkplaceImport : affWorkplaceLst) {
			//月の実績の状況を取得する
			AcquireActualStatus param = new AcquireActualStatus(cid, 
					affWorkplaceImport.getEmployeeId(), 
					processDateYM, 
					closureId, 
					closureTime.end(), 
					closureTime, 
					affWorkplaceImport.getWorkplaceId());
			MonthlyActualSituationOutput monthlymonthlyActualStatusOutput = monthlyActualStatus.getMonthlyActualSituationStatus(param);
			//Output「月の実績の状況」を元に「ロック状態一覧」をセットする
			monthlyLockStatus = new MonthlyPerformaceLockStatus(monthlymonthlyActualStatusOutput.getEmployeeClosingInfo().getEmployeeId(),
					//TODO
					LockStatus.LOCK, 
					//職場の就業確定状態
					monthlymonthlyActualStatusOutput.getEmploymentFixedStatus().equals(EmploymentFixedStatus.CONFIRM) ? LockStatus.LOCK : LockStatus.UNLOCK,
					//月の承認状況
					monthlymonthlyActualStatusOutput.getApprovalStatus().equals(ApprovalStatus.APPROVAL) ? LockStatus.LOCK : LockStatus.UNLOCK, 
					//月別実績のロック状態
					monthlymonthlyActualStatusOutput.getMonthlyLockStatus(), 
					//本人確認が完了している	
					monthlymonthlyActualStatusOutput.getDailyActualSituation().isIdentificationCompleted() ? LockStatus.LOCK : LockStatus.UNLOCK,
					//日の実績が存在する						
					monthlymonthlyActualStatusOutput.getDailyActualSituation().isDailyAchievementsExist() ? LockStatus.LOCK : LockStatus.UNLOCK,
					//TODO
					LockStatus.LOCK);
			monthlyLockStatusLst.add(monthlyLockStatus);
		}
		//過去実績の修正ロック
		LockStatus pastLockStatus = editLockOfPastResult(processDateYM, closureId, new ActualTime(closureTime.start(), closureTime.end()));
		//Output「ロック状態」を「ロック状態一覧.過去実績のロック」にセットする
		monthlyLockStatusLst = monthlyLockStatusLst.stream().map(item -> {
																			item.setPastPerformaceLock(pastLockStatus);
																			return item;
																		}).collect(Collectors.toList());
		
		return monthlyLockStatusLst;
	}
	/**
	 * 過去実績の修正ロック
	 * @param processDateYM
	 * @param closureId
	 * @param actualTime
	 * @return ロック状態：ロック or アンロック
	 */
	private LockStatus editLockOfPastResult(Integer processDateYM, Integer closureId, ActualTime actualTime){
		ActualTimeState actualTimeState = monthlyCheck.checkActualTime(processDateYM, closureId, actualTime);
		if (actualTimeState.equals(ActualTimeState.Past)) {
			return LockStatus.LOCK;
		}
		return LockStatus.UNLOCK;
	}
}
