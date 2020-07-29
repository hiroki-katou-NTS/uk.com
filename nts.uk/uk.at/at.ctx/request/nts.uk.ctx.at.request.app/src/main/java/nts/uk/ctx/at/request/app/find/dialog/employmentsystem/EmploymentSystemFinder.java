package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

/*import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;*/
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecDetailPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecGenerationDigestionHis;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentManaQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AsbRemainTotalInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.UnOffsetOfAbs;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.UnUseOfRec;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffManagementQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffOutputHisData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.UnOffSetOfDayOff;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.UnUserOfBreak;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmploymentSystemFinder {	
	@Inject
	BreakDayOffManagementQuery breakDayOffManagementQuery;
	
	@Inject
	EmployeeRequestAdapter employeeRequestAdapter;
	
	@Inject
	ClosureService closureService;
	
	@Inject
	AbsenceReruitmentManaQuery absenceReruitmentManaQuery;
	
	@Inject
	CompensLeaveEmSetRepository compensLeaveEmSetRepository;
	
	@Inject
	ShareEmploymentAdapter employeeAdaptor;
	@Inject
	private WorkplaceAdapter wpAdapter;
//	@Inject
//	private EmpSubstVacationRepository empSubrepo;
//	@Inject
//	private ComSubstVacationRepository comSubrepo;
	@Inject
	private AbsenceReruitmentMngInPeriodQuery absRertMngInPeriod;
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	@Inject
	private BreakDayOffMngInPeriodQuery breakDayOffMngInPeriod;
	
	@Inject
	private AbsenceTenProcess absenceTenProcess;
	
	/** 
	 * KDL005
	 * アルゴリズム「代休確認ダイア起動」を実行する
	 * 
	 * @param employeeIds
	 * @param baseDate
	 * @return
	 */
	public List<EmployeeBasicInfoDto> getEmployeeData(List<String> employeeIds, String baseDate) {
		String companyId = AppContexts.user().companyId();
		
		// 社員情報リストを取得する
		List<EmployeeBasicInfoDto> data = employeeRequestAdapter.getPerEmpBasicInfo(companyId, employeeIds)
				.stream().map(c -> EmployeeBasicInfoDto.fromDomain(c)).collect(Collectors.toList());
		
		if(data.isEmpty()) {
			return Collections.emptyList();
		}
		
		return data;
	}
	
	/**
	 * KDL005
	 * アルゴリズム「代休確認ダイア詳細取得」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public CofirmDetailsResidualInformationDto getDetailsConfirm(String employeeId, String baseDate) {
		
		CofirmDetailsResidualInformationDto detailsdDto = new CofirmDetailsResidualInformationDto();
		String companyId = AppContexts.user().companyId();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		
		// 基準日（指定がない場合はシステム日付）
		if(baseDate.isEmpty()) {
			baseDate = dtf.format(localDate);
		} else {
			baseDate = GeneralDate.fromString(baseDate, "yyyyMMdd").toString();
		}
		GeneralDate inputDate = GeneralDate.fromString(baseDate, "yyyy/MM/dd");
		// アルゴリズム「社員に対応する締め期間を取得する」を実行する		
		DatePeriod closingPeriod = closureService.findClosurePeriod(employeeId, inputDate);
		
//		// アルゴリズム「休出代休発生消化履歴の取得」を実行する
		Optional<BreakDayOffOutputHisData> data = breakDayOffManagementQuery.getBreakDayOffData(companyId, employeeId, inputDate);
		
		List<BreakDayOffHistoryDto> lstHistory = new ArrayList<>();
		
		if(data.isPresent() && data.get().getLstHistory().size() > 0) {
			for (BreakDayOffHistory item : data.get().getLstHistory()) {
				if(item == null) {
					continue;
				}
				
				ComDayoffDateDto hisDate = new ComDayoffDateDto(item.getHisDate().isUnknownDate(), 
						item.getHisDate().getDayoffDate().isPresent() ? item.getHisDate().getDayoffDate().get() : null);
				
				BreakHistoryData breakHist =null;
				if (item.getBreakHis() == null) {
					breakHist = null;
				} else {
					breakHist = item.getBreakHis().isPresent() ? item.getBreakHis().get() : null;
				}
				ComDayoffDateDto breakDate = new ComDayoffDateDto(breakHist != null ? breakHist.getBreakDate().isUnknownDate() : true, 
						breakHist != null ? (breakHist.getBreakDate().getDayoffDate().isPresent() ? breakHist.getBreakDate().getDayoffDate().get() : null) : null);
				
				BreakHistoryDataDto breakHis = new BreakHistoryDataDto(breakHist != null ? breakHist.getBreakMngId() : null, breakDate,
						breakHist != null ? breakHist.getExpirationDate() : null,
						breakHist != null ? breakHist.isChkDisappeared() : true,
						breakHist != null ? breakHist.getMngAtr().value : 0,
						breakHist != null ? breakHist.getOccurrenceDays() : 0.0,
						breakHist != null ? breakHist.getUnUseDays() : 0.0);
				
				DayOffHistoryData dayOffHist = item.getDayOffHis() != null ? item.getDayOffHis().get() : null;
				
				ComDayoffDateDto dayOffDate = new ComDayoffDateDto(dayOffHist != null ? dayOffHist.getDayOffDate().isUnknownDate() : true, 
						dayOffHist != null ? (dayOffHist.getDayOffDate().getDayoffDate().isPresent() ? dayOffHist.getDayOffDate().getDayoffDate().get(): null) : null);
				
				DayOffHistoryDataDto dayOffHis = new DayOffHistoryDataDto(dayOffHist != null ? dayOffHist.getCreateAtr().value : 0,
						dayOffHist != null ? dayOffHist.getDayOffId() : null, dayOffDate,
						dayOffHist != null ? dayOffHist.getRequeiredDays() : 0.0,
						dayOffHist != null ? dayOffHist.getUnOffsetDays() : 0.0);
				
				Double useDays  = item.getUseDays() != null ? item.getUseDays() : 0.0;
				
				Optional<BsEmploymentHistoryImport> empHistImport = employeeAdaptor.findEmploymentHistory(companyId, employeeId, inputDate);
				if(!empHistImport.isPresent() || empHistImport.get().getEmploymentCode()==null){
					throw new BusinessException("khong co employeeCode");
				}
				
				CompensatoryLeaveEmSetting compensatoryLeaveEmSet = this.compensLeaveEmSetRepository.find(companyId, empHistImport.get().getEmploymentCode());
				int isManaged = compensatoryLeaveEmSet != null ? compensatoryLeaveEmSet.getIsManaged().value : ManageDistinct.YES.value;
				
				BreakDayOffHistoryDto outputDto = new BreakDayOffHistoryDto(hisDate, breakHis, dayOffHis, useDays, isManaged);
				lstHistory.add(outputDto);
			}
		}
		
		DetailConfirmDto result = new DetailConfirmDto();
		result.setClosingPeriod(closingPeriod);
		result.setLstHistory(lstHistory);
		result.setTotalInfor(data.isPresent() ? data.get().getTotalInfor() : null);
		// imported（就業）「所属雇用履歴」を取得する RequestList31
		Optional<EmploymentHistoryImported> empImpOpt = this.wpAdapter.getEmpHistBySid(companyId, employeeId,
				inputDate);
		
		//アルゴリズム「代休確認ダイア使用期限詳細」を実行する
		DeadlineDetails deadLine = getDeadlineDetails(companyId, empImpOpt);
		
		result.setDeadLineDetails(deadLine);
		//アルゴリズム「期間内の休出代休残数を取得する」を実行する
		BreakDayOffRemainMngParam inputParam = new BreakDayOffRemainMngParam(companyId, employeeId, getDatePeroid(closingPeriod.start()), 
				false, inputDate, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty());
		BreakDayOffRemainMngOfInPeriod breakDay = this.breakDayOffMngInPeriod.getBreakDayOffMngInPeriod(inputParam);
		
		result.setBreakDay(breakDay);
		
		// #110215 	残数詳細を作成
		List<RemainNumberDetailDto> remainNumberDetailMapDto = new ArrayList<>(); 
		for (BreakDayOffDetail item : breakDay.getLstDetailData()) { 
			RemainNumberDetailDto mappingDto = new RemainNumberDetailDto();
			mappingDto.setExpiredInCurrentMonth(false);
			if (item.getOccurrentClass().equals(OccurrenceDigClass.OCCURRENCE)) {
				// ・逐次発生の休暇明細．発生消化区分　＝＝　発生　－＞発生日　＝　逐次発生の休暇明細．年月日
				if (item.getYmdData().getDayoffDate().isPresent()) {
					GeneralDate occurrenceDate = item.getYmdData().getDayoffDate().get();
					mappingDto.setOccurrenceDate(occurrenceDate);
					// condition 取得した期間．開始日＜＝発生日＜＝取得した期間．終了日　－＞・当月で期限切れ　＝　True　ELSE　－＞・当月で期限切れ　＝　False
					mappingDto.setExpiredInCurrentMonth(closingPeriod.contains(occurrenceDate));
				}
			} else if (item.getOccurrentClass().equals(OccurrenceDigClass.DIGESTION)) {
				// ・逐次発生の休暇明細．発生消化区分　＝＝　消化　－＞消化日　＝　逐次発生の休暇明細．年月日
				mappingDto.setDigestionDate(item.getYmdData().getDayoffDate().orElse(null));
			}
			if (item.getUnUserOfBreak().isPresent()) {
				UnUserOfBreak unUseOfRec = item.getUnUserOfBreak().get();
				// field ・発生数　＝　取得した逐次発生の休暇明細．発生数．日数
				mappingDto.setOccurrenceNumber(unUseOfRec.getOccurrenceDays());
				// field ・期限日　＝　取得した逐次発生の休暇明細．休暇発生明細．期限日
				mappingDto.setExpirationDate(unUseOfRec.getExpirationDate());
			}
			if (item.getUnOffsetOfDayoff().isPresent()) {
				UnOffSetOfDayOff unOffsetOfAbs = item.getUnOffsetOfDayoff().get();
				// field ・消化数　＝　取得した逐次発生の休暇明細．未相殺数．日数
				mappingDto.setDigestionNumber(unOffsetOfAbs.getUnOffsetDay());
			} 
			// field 管理データ状態区分　＝　取得した逐次発生の休暇明細．状態
			mappingDto.setManagementDataStatus(item.getDataAtr().value);
		}
		
		// #110215 	紐付け情報を生成する ()
		
		// #110215	取得内容を画面に反映させる
		
		return detailsdDto;
	}
	
	private DatePeriod getDatePeroid(GeneralDate startDate) {
		return new DatePeriod(startDate, startDate.addYears(1).addDays(-1));
	}

	private DeadlineDetails getDeadlineDetails(String companyId, Optional<EmploymentHistoryImported> empImpOpt) {
		DeadlineDetails  result  = null;
		
		if (empImpOpt.isPresent()) {
			CompensatoryLeaveEmSetting emSet = this.compensLeaveEmSetRepository.find(companyId,
					empImpOpt.get().getEmploymentCode());

			if (emSet == null) {
				CompensatoryLeaveComSetting comSet = this.compensLeaveComSetRepository.find(companyId);
				if(comSet == null){
					throw new BusinessException("代休管理設定 && 雇用の代休管理設定 = null");
				}
				result = new DeadlineDetails(comSet.getIsManaged().value, comSet.getCompensatoryAcquisitionUse().getExpirationTime().value );
			}else{
				result = new DeadlineDetails(emSet.getIsManaged().value, emSet.getCompensatoryAcquisitionUse().getExpirationTime().value);
			}
		}
		
		return result;
		
	}

	/**
	 * KDL009
	 * アルゴリズム「振休確認ダイアログ開始」を実行する
	 * 
	 * @param employeeIds
	 * @param baseDate
	 * @return
	 */
	public List<EmployeeBasicInfoDto> getEmployee(List<String> employeeIds, String baseDate) {
		String companyId = AppContexts.user().companyId();
		
		// 対象社員の件数をチェックする
		if(employeeIds.size() == 0) {
			// エラーメッセージ(#Msg_918#)を表示する
			throw new BusinessException("Msg_918");
		}
		
		// 社員情報リストを取得する
		List<EmployeeBasicInfoDto> data = employeeRequestAdapter.getPerEmpBasicInfo(companyId, employeeIds)
				.stream().map(c -> EmployeeBasicInfoDto.fromDomain(c)).collect(Collectors.toList());
		
		if(data.isEmpty()) {
			return Collections.emptyList();
		}
		
		return data;
	}

	/**
	 * KDL009
	 * アルゴリズム「振休残数情報の取得」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public AcquisitionNumberRestDayDto getAcquisitionNumberRestDays(String employeeId, String baseDate) {
		String companyId = AppContexts.user().companyId();
		GeneralDate inputDate = GeneralDate.fromString(baseDate, "yyyyMMdd");
		AcquisitionNumberRestDayDto result = new AcquisitionNumberRestDayDto();
		
		// Step 10-3.振休の設定を取得する
		LeaveSetOutput leaveSet = this.absenceTenProcess.getSetForLeave(companyId, employeeId, inputDate);
		result.setIsManagementSection(leaveSet.isSubManageFlag());
		
		// If 取得した振休管理区分　＝＝　False
		if (!leaveSet.isSubManageFlag()) {
			// 「確認残数情報」を返す
			return result;
		}
		
		// Step 社員に対応する締め期間を取得する		
		DatePeriod closingPeriod = this.closureService.findClosurePeriod(employeeId, inputDate);
		
		// Step アルゴリズム「期間内の振出振休残数を取得する」を実行する
		AbsRecMngInPeriodParamInput param = new AbsRecMngInPeriodParamInput(
				companyId,
				employeeId,
				getDatePeroid(closingPeriod.start()),
				GeneralDate.today(),
				false,
				false, 
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.empty(),Optional.empty(),Optional.empty());
		AbsRecRemainMngOfInPeriod absRecMng = this.absRertMngInPeriod.getAbsRecMngInPeriod(param);
		result.setExpiredDay(absRecMng.getUseDays());

		// Step 紐付け情報を生成する ()

		// Step 「残数詳細」を作成
		List<RemainNumberDetailDto> listRemainNumberDetail = new ArrayList<>();
		for (AbsRecDetailPara item : absRecMng.getLstAbsRecMng()) { 
			RemainNumberDetailDto itemDto = new RemainNumberDetailDto();
			itemDto.setExpiredInCurrentMonth(false);
			if (item.getOccurrentClass().equals(OccurrenceDigClass.OCCURRENCE)) {
				// ・逐次発生の休暇明細．発生消化区分　＝＝　発生　－＞発生日　＝　逐次発生の休暇明細．年月日
				if (item.getYmdData().getDayoffDate().isPresent()) {
					GeneralDate occurrenceDate = item.getYmdData().getDayoffDate().get();
					itemDto.setOccurrenceDate(occurrenceDate);
					// condition 取得した期間．開始日＜＝発生日＜＝取得した期間．終了日　－＞・当月で期限切れ　＝　True　ELSE　－＞・当月で期限切れ　＝　False
					itemDto.setExpiredInCurrentMonth(closingPeriod.contains(occurrenceDate));
				}
			} else if (item.getOccurrentClass().equals(OccurrenceDigClass.DIGESTION)) {
				// ・逐次発生の休暇明細．発生消化区分　＝＝　消化　－＞消化日　＝　逐次発生の休暇明細．年月日
				itemDto.setDigestionDate(item.getYmdData().getDayoffDate().orElse(null));
			}
			if (item.getUnUseOfRec().isPresent()) {
				UnUseOfRec unUseOfRec = item.getUnUseOfRec().get();
				// field ・発生数　＝　取得した逐次発生の休暇明細．発生数．日数
				itemDto.setOccurrenceNumber(unUseOfRec.getOccurrenceDays());
				// field ・期限日　＝　取得した逐次発生の休暇明細．休暇発生明細．期限日
				itemDto.setExpirationDate(unUseOfRec.getExpirationDate());
			}
			if (item.getUnOffsetOfAb().isPresent()) {
				UnOffsetOfAbs unOffsetOfAbs = item.getUnOffsetOfAb().get();
				// field ・消化数　＝　取得した逐次発生の休暇明細．未相殺数．日数
				itemDto.setDigestionNumber(unOffsetOfAbs.getUnOffSetDays());
			} 
			// field 管理データ状態区分　＝　取得した逐次発生の休暇明細．状態
			itemDto.setManagementDataStatus(item.getDataAtr().value);
			listRemainNumberDetail.add(itemDto);
		}
		result.setListRemainNumberDetail(listRemainNumberDetail);
		
		
//		List<RecAbsHistoryOutputDto> recAbsHistoryOutput = new ArrayList<>();
//		if(data.isPresent() && data.get().getGreneraGigesHis().size() > 0) {
//			for (RecAbsHistoryOutputPara item : data.get().getGreneraGigesHis()) {
//				if(item == null) {
//					continue;
//				}
//				
//				CompensatoryDayoffDateDto ymdData = new CompensatoryDayoffDateDto(item.getYmdData().isUnknownDate(), 
//						item.getYmdData().getDayoffDate().isPresent() ? item.getYmdData().getDayoffDate().get() : null);
//				
//				Double useDays = item.getUseDays() != null ? item.getUseDays().get() : 0.0;
//				
//				AbsenceHistoryOutputPara absHisDatas = item.getAbsHisData() != null ? item.getAbsHisData().get() : null;
//				
//				CompensatoryDayoffDateDto absDate = new CompensatoryDayoffDateDto(absHisDatas != null ? absHisDatas.getAbsDate().isUnknownDate() : true, 
//						absHisDatas != null ? (absHisDatas.getAbsDate().getDayoffDate().isPresent() ? absHisDatas.getAbsDate().getDayoffDate().get() : null) : null);
//				
//				AbsenceHistoryOutputParaDto absHisData = new AbsenceHistoryOutputParaDto(absHisDatas != null ? absHisDatas.getCreateAtr().value : 0, 
//						absHisDatas != null ? absHisDatas.getAbsId() : null,
//						absDate, absHisDatas != null ? absHisDatas.getRequeiredDays() : 0.0, 
//						absHisDatas != null ? absHisDatas.getUnOffsetDays() : 0.0);
//				
//				RecruitmentHistoryOutPara recHisDatas = item.getRecHisData() != null ? item.getRecHisData().get() : null;
//				
//				CompensatoryDayoffDateDto recDate = new CompensatoryDayoffDateDto(
//						recHisDatas != null ? recHisDatas.getRecDate().isUnknownDate() : true, 
//						recHisDatas != null 
//								? (recHisDatas.getRecDate().getDayoffDate().isPresent() ? recHisDatas.getRecDate().getDayoffDate().get() : null) 
//								: null);
//				
//				RecruitmentHistoryOutParaDto recHisData = new RecruitmentHistoryOutParaDto(recHisDatas != null ? recHisDatas.getExpirationDate() : null, 
//						recHisDatas != null ? recHisDatas.isChkDisappeared() : true,
//						recHisDatas != null ? recHisDatas.getDataAtr().value : 0, 
//						recHisDatas != null ? recHisDatas.getRecId() : null, recDate, 
//						recHisDatas != null ? recHisDatas.getOccurrenceDays() : 0.0,
//						recHisDatas != null ? recHisDatas.getHolidayAtr() : 0, 
//						recHisDatas != null ? recHisDatas.getUnUseDays() : 0.0);
//				
//				RecAbsHistoryOutputDto outputDto = new RecAbsHistoryOutputDto(ymdData, useDays, absHisData, recHisData);
//				recAbsHistoryOutput.add(outputDto);
//			}
//		}
//		result.setRecAbsHistoryOutput(recAbsHistoryOutput);
//		result.setAbsRemainInfor(data.isPresent() ? data.get().getAbsRemainInfor() : null);
//		//imported（就業）「所属雇用履歴」を取得する RequestList31
//		Optional<EmploymentHistoryImported> empImpOpt =  this.wpAdapter.getEmpHistBySid(companyId, employeeId, inputDate);
//		// アルゴリズム「振休管理設定の取得」を実行する
//		SubstVacationSetting setting = getLeaveManagementSetting(companyId, employeeId, empImpOpt);
		
		//アルゴリズム「期間内の振出振休残数を取得する」を実行する
//		AbsRecMngInPeriodParamInput param = new AbsRecMngInPeriodParamInput(companyId,
//																			employeeId,
//																			getDatePeroid(closingPeriod.start()),
//																			GeneralDate.today(),
//																			false,
//																			false, 
//																			Collections.emptyList(),
//																			Collections.emptyList(),
//																			Collections.emptyList(),
//																			Optional.empty(),Optional.empty(),Optional.empty());
//		AbsRecRemainMngOfInPeriod absRecMng = absRertMngInPeriod.getAbsRecMngInPeriod(param);
		
		// Step 振出振休発生消化履歴の取得
		Optional<AbsRecGenerationDigestionHis> data = this.absenceReruitmentManaQuery.generationDigestionHis(companyId, employeeId, inputDate);
		if (data.isPresent()) {
			AsbRemainTotalInfor absRemainInfor = data.get().getAbsRemainInfor();
			// A8_1_2 繰越日数
			result.setCarryForwardDay(absRemainInfor.getCarryForwardDays());
			// A8_2_2 発生日数
			result.setOccurrenceDay(absRemainInfor.getRecordOccurrenceDays());
			// A8_2_3 予定発生日数
			result.setScheduleOccurrencedDay(absRemainInfor.getScheOccurrenceDays());
			// A8_3_2 使用日数
			result.setUsageDay(absRemainInfor.getRecordUseDays());
			// A8_3_3 予定使用日数
			result.setScheduledUsageDay(absRemainInfor.getScheUseDays());
			// A8_4_2 残数
			result.setRemainingDay(absRemainInfor.getRecordOccurrenceDays() - absRemainInfor.getRecordUseDays());
			// A8_4_3 予定残数
			result.setScheduledRemainingDay(absRemainInfor.getScheOccurrenceDays() - absRemainInfor.getScheUseDays());
		}
		return result;
	}

//	private SubstVacationSetting getLeaveManagementSetting(String companyId, String employeeId,
//			Optional<EmploymentHistoryImported> empImpOpt) {
//		SubstVacationSetting setting = null;
//		
//		if (empImpOpt.isPresent()) {
//			EmploymentHistoryImported empImp = empImpOpt.get();
//			String emptCD = empImp.getEmploymentCode();
//
//			Optional<EmpSubstVacation> empSubOpt = empSubrepo.findById(companyId, emptCD);
//			if (empSubOpt.isPresent()) {
//				setting = empSubOpt.get().getSetting();
//			} else {
//				Optional<ComSubstVacation> comSubOpt = comSubrepo.findById(companyId);
//				if (comSubOpt.isPresent()) {
//					setting = comSubOpt.get().getSetting();
//				}
//			}
//			if (setting == null) {
//				throw new BusinessException("振休管理設定 == null");
//			}
//
//		}
//		return setting;
//	}

	@Data
	@AllArgsConstructor
	class DeadlineDetails {
		// 管理区分
		private Integer isManaged;
		// 使用期限
		private Integer expirationTime;
	}
}
