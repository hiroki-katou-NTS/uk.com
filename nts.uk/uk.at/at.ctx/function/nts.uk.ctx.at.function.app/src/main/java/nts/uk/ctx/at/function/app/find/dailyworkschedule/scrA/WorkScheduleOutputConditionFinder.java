/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkScheduleDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputStandardSettingOfDailyWorkScheduleDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.scrB.ErrorAlarmCodeDto;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.ItemSelectionType;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleExportRepoAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportImported;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformAuthorRepo;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceFunctionNo;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkScheduleOutputConditionFinder.
 * @author HoangDD
 */
@Stateless
public class WorkScheduleOutputConditionFinder {
	
	/** The closure employment repository. */
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	/** The s emp hist export adapter. */
	@Inject
	private SEmpHistExportAdapter sEmpHistExportAdapter;
	
	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private FreeSettingOfOutputItemRepository freeSettingOfOutputItemRepository;

	@Inject
	private OutputStandardSettingRepository outputStandardSettingRepository;

	/** The error alarm work record repository. */
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	
	@Inject
	private RoleExportRepoAdapter roleExportRepoAdapter;
	
	@Inject
	private DailyPerformAuthorRepo dailyPerformAuthorRepo;
	
	/** The Constant STRING_EMPTY. */
	private static final String STRING_EMPTY = "";
	
	/** The Constant SHOW_CHARACTERISTIC. */
	private static final String SHOW_CHARACTERISTIC = "SHOW_CHARACTERISTIC";
	
	/**
	 * Start scr.
	 *
	 * @param isExistWorkScheduleOutputCondition the is exist work schedule output condition
	 * @return the work schedule output condition dto
	 */
	public WorkScheduleOutputConditionDto startScr(boolean isExistWorkScheduleOutputCondition) {
		
		WorkScheduleOutputConditionDto dto = new WorkScheduleOutputConditionDto();
		String companyId = AppContexts.user().companyId();
		GeneralDate systemDate = GeneralDate.today();
		String employeeId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forAttendance();
		//「ログイン者が担当者か判断する」で就業担当者かチェックする
		// 出力項目の設定ボタン(A7_6)の活性制御を行う
		if (roleExportRepoAdapter.getRoleWhetherLogin().isEmployeeCharge()) {
			dto.setEmployeeCharge(true);
		} else {
			dto.setEmployeeCharge(false);
		}

		// ログイン社員の就業帳票の権限を取得する
		// ・ロールID：ログイン社員の就業ロールID
		// ・機能NO：51(自由設定区分)
		// ・利用できる：TRUE
		boolean isFreeSetting = this.dailyPerformAuthorRepo.getAuthorityOfEmployee(roleId,
				new DailyPerformanceFunctionNo(BigDecimal.valueOf(51l)), true);

		dto.setConfigFreeSetting(isFreeSetting);
		// 自由設定(A7_7～A7_12)の活性制御を行う
		dto.setSelectionType(isFreeSetting ? ItemSelectionType.FREE_SETTING.value : ItemSelectionType.STANDARD_SELECTION.value);

		// アルゴリズム「社員に対応する締め期間を取得する」を実行する(Execute the algorithm "Acquire closing period corresponding to employee")
		Optional<Closure> optClosure = getDomClosure(employeeId, systemDate);
		
		// 「締め」がある
		if (optClosure.isPresent()) {
			Closure closure = optClosure.get();
			dto.setMsgErrClosingPeriod(null);
			
			// アルゴリズム「当月の期間を算出する」を実行する(Execute the algorithm "Calculate the period of the current month")
			DatePeriod datePeriod = ClosureService.getClosurePeriodNws(
					ClosureService.createRequireM5(closureRepository),
					closure.getClosureId().value, closure.getClosureMonth().getProcessingYm());
			dto.setStartDate(datePeriod.start());
			dto.setEndDate(datePeriod.end());
		} else {
			// 「締め」がない
			dto.setMsgErrClosingPeriod("Msg_1134");
			
			dto.setStartDate(null);
			dto.setEndDate(null);
		}

		// 社員IDから自由設定の出力項目を取得 (Get output item for free setup from employee ID)
		FreeSettingOfOutputItemForDailyWorkScheduleDto freeSettingDto = this.freeSettingOfOutputItemRepository
				.getFreeSettingByCompanyAndEmployee(companyId, employeeId)
				.map(d -> FreeSettingOfOutputItemForDailyWorkScheduleDto.toFreeSettingDto(d)).orElse(null);

		// 定型設定の出力項目を取得(Get output items of standard settings)
		OutputStandardSettingOfDailyWorkScheduleDto standardSettingDto = this.outputStandardSettingRepository
				.getStandardSettingByCompanyId(companyId)
				.map(d -> OutputStandardSettingOfDailyWorkScheduleDto.toStandardDto(d)).orElse(null);

		if (isFreeSetting) {
			if (freeSettingDto != null && !freeSettingDto.getOutputItemDailyWorkSchedules().isEmpty()) {
				dto.setStrReturn(isExistWorkScheduleOutputCondition ? SHOW_CHARACTERISTIC : STRING_EMPTY);
			}
		} else {
			if (standardSettingDto != null && !standardSettingDto.getOutputItemDailyWorkSchedules().isEmpty()) {
				dto.setStrReturn(isExistWorkScheduleOutputCondition ? SHOW_CHARACTERISTIC : STRING_EMPTY);
			}
		}

		dto.setStandardSetting(standardSettingDto);
		dto.setFreeSetting(freeSettingDto);

		return dto;
	}
	
	/**
	 * Gets the dom closure.
	 *
	 * @param employeeId the employee id
	 * @param systemDate the system date
	 * @return the dom closure
	 */
	// アルゴリズム「社員に対応する締め期間を取得する」を実行する(Execute the algorithm "Acquire closing period corresponding to employee")
	public Optional<Closure> getDomClosure(String employeeId, GeneralDate systemDate) {
		
		String companyId = AppContexts.user().companyId();
		
		// Imported「（就業）所属雇用履歴」を取得する (Lấy Imported「（就業）所属雇用履歴」)
		Optional<SEmpHistExportImported> optSEmpHistExportImported = sEmpHistExportAdapter.getSEmpHistExport(companyId, employeeId, systemDate);
		if (!optSEmpHistExportImported.isPresent()) {
			return Optional.empty();
		}
		SEmpHistExportImported sEmpHistExportImported = optSEmpHistExportImported.get();
		
		// Get domain 対応するドメインモデル「雇用に紐づく就業締め」を取得する (Lấy về domain model "Thuê" tương ứng)
		Optional<ClosureEmployment> optClosureEmployment = closureEmploymentRepository.findByEmploymentCD(companyId, sEmpHistExportImported.getEmploymentCode());
		if (optClosureEmployment.isPresent()) {
			// Get domain 対応するドメインモデル「締め」を取得する (Lấy về domain model "Hạn định" tương ứng)
			return closureRepository.findById(companyId, optClosureEmployment.get().getClosureId());
		} else { // 締め1の対応するドメインモデル「締め」を取得する ((get domain model đối ứng của closing day 1)
			return closureRepository.findById(companyId, 1);
		}
	}
	
	/**
	 * Gets the error alarm code dto.
	 *
	 * @return the error alarm code dto
	 */
	public List<ErrorAlarmCodeDto> getErrorAlarmCodeDto() {
		String companyId = AppContexts.user().companyId();
		
		// ドメインモデル「勤務実績のエラーアラーム」を取得する(Acquire domain model "work error actual alarm")
		List<ErrorAlarmWorkRecord> lstErrorAlarmWorkRecord = errorAlarmWorkRecordRepository.getAllErAlCompanyAndUseAtr(companyId, true);
		return lstErrorAlarmWorkRecord.stream()
								.map(domain -> new ErrorAlarmCodeDto(domain.getCode().v(), domain.getName().v()))
								.sorted(Comparator.comparing(ErrorAlarmCodeDto::getCode))
								.collect(Collectors.toList());
	} 

}
