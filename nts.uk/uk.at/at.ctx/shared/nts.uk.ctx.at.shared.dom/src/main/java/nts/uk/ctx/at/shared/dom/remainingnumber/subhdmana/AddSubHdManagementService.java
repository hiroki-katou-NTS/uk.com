/**
 * 
 */
package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.TypeOffsetJudgment;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SyEmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmpAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nam.lh
 *
 */
@Stateless
public class AddSubHdManagementService {
	
	@Inject
	private ClosureRepository closureRepo;

	@Inject
	private LeaveManaDataRepository repoLeaveManaData;

	@Inject
	private ComDayOffManaDataRepository repoComDayOffManaData;

	@Inject
	private LeaveComDayOffManaRepository repoLeaveComDayOffMana;
	
	@Inject
	private SysEmpAdapter syEmployeeAdapter;

	/**
	 * @param subHdManagementData
	 */
	public List<String> addProcessOfSHManagement(SubHdManagementData subHdManagementData) {
		List<String> errorList = addSubSHManagement(subHdManagementData);
		if (!errorList.isEmpty()) {
			return errorList;
		} else {
			String comDayOffID = IdentifierUtil.randomUniqueId();
			String comDayOffIDSub = IdentifierUtil.randomUniqueId();
			String leaveId = IdentifierUtil.randomUniqueId();
			if (subHdManagementData.getCheckedHoliday()) {
				int subHDAtr = 0;
				if (subHdManagementData.getCheckedHoliday() && subHdManagementData.getCheckedSubHoliday()) {
					subHDAtr = 1;
				}
				int equivalentHalfDay = 0;
				int equivalentADay = 0;
				LeaveManagementData domainLeaveManagementData = new LeaveManagementData(leaveId,
						AppContexts.user().companyId(), subHdManagementData.getEmployeeId(), false,
						subHdManagementData.getDateHoliday(), subHdManagementData.getDuedateHoliday(),
						subHdManagementData.getSelectedCodeHoliday(), 0, subHdManagementData.getDayRemaining(), 0,
						subHDAtr, equivalentADay, equivalentHalfDay);
				repoLeaveManaData.create(domainLeaveManagementData);
				if (subHdManagementData.getCheckedSubHoliday()) {
					CompensatoryDayOffManaData domainCompensatoryDayOffManaData = new CompensatoryDayOffManaData(
							comDayOffID, AppContexts.user().companyId(), subHdManagementData.getEmployeeId(), false,
							subHdManagementData.getDateSubHoliday(), subHdManagementData.getSelectedCodeSubHoliday(), 0,
							subHdManagementData.getDayRemaining(), 0);
					repoComDayOffManaData.create(domainCompensatoryDayOffManaData);
					if (subHdManagementData.getCheckedSplit()) {
						CompensatoryDayOffManaData domainCompensatoryDayOffManaDataSub = new CompensatoryDayOffManaData(
								comDayOffIDSub, AppContexts.user().companyId(), subHdManagementData.getEmployeeId(),
								false, subHdManagementData.getDateOptionSubHoliday(),
								subHdManagementData.getSelectedCodeOptionSubHoliday(), 0,
								subHdManagementData.getDayRemaining(), 0);
						repoComDayOffManaData.create(domainCompensatoryDayOffManaDataSub);
					}
				}
			} else {
				if (subHdManagementData.getCheckedSubHoliday()) {
					CompensatoryDayOffManaData domainCompensatoryDayOffManaData = new CompensatoryDayOffManaData(
							comDayOffID, AppContexts.user().companyId(), subHdManagementData.getEmployeeId(), false,
							subHdManagementData.getDateSubHoliday(), subHdManagementData.getSelectedCodeSubHoliday(), 0,
							subHdManagementData.getSelectedCodeSubHoliday(), 0);
					repoComDayOffManaData.create(domainCompensatoryDayOffManaData);
					if (subHdManagementData.getCheckedSplit()) {
						CompensatoryDayOffManaData domainCompensatoryDayOffManaDataSub = new CompensatoryDayOffManaData(
								comDayOffIDSub, AppContexts.user().companyId(), subHdManagementData.getEmployeeId(),
								false, subHdManagementData.getDateOptionSubHoliday(),
								subHdManagementData.getSelectedCodeOptionSubHoliday(), 0,
								subHdManagementData.getSelectedCodeOptionSubHoliday(), 0);
						repoComDayOffManaData.create(domainCompensatoryDayOffManaDataSub);
					}
				}
			}

//			if (subHdManagementData.getCheckedHoliday() && subHdManagementData.getCheckedSubHoliday()) {
//				// 	ドメインモデル「振休休出振付け管理」に紐付きチェックされているもの全てを追加する
//				int targetSelectionAtr = 2; // 固定値：手動
//				int usedHours = 0;
//				if (subHdManagementData.getCheckedSplit()) {
//					LeaveComDayOffManagement domainLeaveComDayOffManagementSub = new LeaveComDayOffManagement(
//							subHdManagementData.getEmployeeId(),
//							comDayOffIDSub, subHdManagementData.getSelectedCodeOptionSubHoliday(), usedHours, targetSelectionAtr);
//					repoLeaveComDayOffMana.add(domainLeaveComDayOffManagementSub);
//				} 
//				LeaveComDayOffManagement domainLeaveComDayOffManagement = new LeaveComDayOffManagement(leaveId,
//						comDayOffID, subHdManagementData.getSelectedCodeSubHoliday(), usedHours, targetSelectionAtr);
//				repoLeaveComDayOffMana.add(domainLeaveComDayOffManagement);
//			}
			
		}
		//	Input．List＜紐付け日付＞をチェック Check Input．List＜紐付け日付＞
		if (subHdManagementData.getLstLinkingDate() != null && !subHdManagementData.getLstLinkingDate().isEmpty()) {
			//	ドメインモデル「休出管理データ」を取得 Nhận domain model 「休出管理データ」
			List<LeaveManagementData> lstLeaveManagement = repoLeaveManaData.getBySidAndDatOff(
					subHdManagementData.getEmployeeId(),
					subHdManagementData.getLstLinkingDate().stream()
					.map(x -> GeneralDate.fromString(x, "yyyy-MM-dd")).collect(Collectors.toList()));
			String comDayOffID = IdentifierUtil.randomUniqueId();
			String comDayOffIDSub = IdentifierUtil.randomUniqueId();
			//	List＜代休管理データ＞
			List<CompensatoryDayOffManaData> lstSubstituteLeaveManagement = new ArrayList<CompensatoryDayOffManaData>();
			if (subHdManagementData.getCheckedSplit()) {
				CompensatoryDayOffManaData item1 = new CompensatoryDayOffManaData(comDayOffID,
						AppContexts.user().companyId(), subHdManagementData.getEmployeeId(), false,
						subHdManagementData.getDateOptionSubHoliday(),
						subHdManagementData.getSelectedCodeOptionSubHoliday(), 0,
						subHdManagementData.getSelectedCodeOptionSubHoliday(), 0);
				lstSubstituteLeaveManagement.add(item1);
			}
			CompensatoryDayOffManaData item2 = new CompensatoryDayOffManaData(comDayOffIDSub,
					AppContexts.user().companyId(), subHdManagementData.getEmployeeId(), false,
					subHdManagementData.getDateSubHoliday(), subHdManagementData.getSelectedCodeSubHoliday(), 0,
					subHdManagementData.getSelectedCodeSubHoliday(), 0);
			lstSubstituteLeaveManagement.add(item2);
			// 未使用日数
			Double unUsedDay = 0.0;
			// 未使用時間数
			int unUsedHour = 0;
			//	Input．List＜代休管理データ＞をループする Loop Input．List＜代休管理データ＞
			for (CompensatoryDayOffManaData compensatoryDayOffManaData : lstSubstituteLeaveManagement) {
				//	取得したList＜休出管理データ＞をループする Loop list ＜休出管理データ＞ đã nhận
				for (LeaveManagementData leaveManagementData : lstLeaveManagement) {
					//	未使用日数、未使用時間数を計算 Tính toán số ngày/ số giờ chưa sử dụng
					if (leaveManagementData.getOccurredDays().v() - compensatoryDayOffManaData.getRequireDays().v() > 0) {
						unUsedDay = leaveManagementData.getOccurredDays().v() - compensatoryDayOffManaData.getRequireDays().v();
					}
					
					if (leaveManagementData.getOccurredDays().v() - compensatoryDayOffManaData.getRequireDays().v() <= 0.0 || unUsedDay > 0) {
						unUsedDay = 0.0;
					}
					
					if (leaveManagementData.getOccurredTimes().v() - compensatoryDayOffManaData.getRequiredTimes().v() > 0) {
						unUsedHour = leaveManagementData.getOccurredTimes().v() - compensatoryDayOffManaData.getRequiredTimes().v();
					}
					
					if (leaveManagementData.getOccurredTimes().v() - compensatoryDayOffManaData.getRequiredTimes().v() < 0 || unUsedHour > 0) {
						unUsedHour = 0;
					}
					//	ループ中の「休出管理データ」を更新する Update "Data quản lý đi làm ngày nghỉ" trong vòng lặp
					LeaveManagementData updateData = new LeaveManagementData(leaveManagementData.getID(),
							leaveManagementData.getCID(), leaveManagementData.getSID(), false,
							leaveManagementData.getComDayOffDate().getDayoffDate().get() , leaveManagementData.getExpiredDate(),
							leaveManagementData.getOccurredDays(), leaveManagementData.getOccurredTimes(), unUsedDay,
							unUsedHour, 1, leaveManagementData.getFullDayTime(), leaveManagementData.getHalfDayTime(),
							leaveManagementData.getDisapearDate());
					repoLeaveManaData.update(updateData);
					LeaveComDayOffManagement domainLeaveComDayOffManagementSub = new LeaveComDayOffManagement(subHdManagementData.getEmployeeId(),
							leaveManagementData.getComDayOffDate().getDayoffDate().get(), 
							compensatoryDayOffManaData.getDayOffDate().getDayoffDate().get(),
							compensatoryDayOffManaData.getRequireDays().v(), 
							TargetSelectionAtr.MANUAL.value);
					repoLeaveComDayOffMana.add(domainLeaveComDayOffManagementSub);
					//	計算した未使用日数　＝　0 AND　計算した未使用時間数　＝　0
					if(!(unUsedDay == 0.0 && unUsedHour == 0)) {
						break;
					}
				}
			}
		}

		return Collections.emptyList();
	}

	/**
	 * 代休管理データの新規追加入力項目チェック処理
	 * 
	 * @param subHdManagementData
	 */
	private List<String> addSubSHManagement(SubHdManagementData subHdManagementData) {
		List<String> errorList = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		int closureId = subHdManagementData.getClosureId();
		YearMonth processYearMonth = GeneralDate.today().yearMonth();
		String employeeId = subHdManagementData.getEmployeeId();
		// ドメインモデル「締め」を読み込む
		Optional<GeneralDate> closureDate = this.getClosureDate(closureId, processYearMonth);

		if (!subHdManagementData.getCheckedHoliday() && !subHdManagementData.getCheckedSubHoliday()) {
			errorList.add("Msg_728");
		} else {
			if (subHdManagementData.getCheckedHoliday()) {
				errorList.addAll(this.checkHoliday(subHdManagementData.getDateHoliday(), closureDate, closureId));
				if (errorList.contains("Msg_1439")) {
					errorList.set(errorList.indexOf("Msg_1439"), "Msg_1439");
				}
			}
			// ドメインモデル「休出管理データ」を読み込む
			GeneralDate dateHoliday = subHdManagementData.getCheckedHoliday() ? subHdManagementData.getDateHoliday()
					: subHdManagementData.getDateSubHoliday();
			List<LeaveManagementData> leaveManagementDatas = repoLeaveManaData.getBySidWithHolidayDate(companyId,
					employeeId, dateHoliday);
			List<CompensatoryDayOffManaData> compensatoryDayOffManaDatas = repoComDayOffManaData
					.getBySidWithHolidayDateCondition(companyId, employeeId, dateHoliday);

			if (!leaveManagementDatas.isEmpty() || !compensatoryDayOffManaDatas.isEmpty()) {
				if (subHdManagementData.getCheckedHoliday()) {
					errorList.add("Msg_737_holiday");
				}
			}
			if (subHdManagementData.getCheckedSubHoliday()) {
				Optional<GeneralDate> dateHolidayCheck = Optional.ofNullable(subHdManagementData.getDateHoliday());
				Optional<GeneralDate> dateOptionSubHolidayCheck = Optional
						.ofNullable(subHdManagementData.getDateOptionSubHoliday());
				// 代休（年月日）チェック処理
				errorList.addAll(this.checkDateHoliday(dateHolidayCheck, subHdManagementData.getDateSubHoliday(),
						closureDate, closureId, subHdManagementData.getCheckedSplit(), dateOptionSubHolidayCheck,
						subHdManagementData.getCheckedHoliday()));
				// ドメインモデル「代休管理データ」を読み込む
				GeneralDate dateSubHoliday = subHdManagementData.getDateSubHoliday();
				leaveManagementDatas = repoLeaveManaData.getBySidWithHolidayDate(companyId, employeeId,
						dateSubHoliday);
				compensatoryDayOffManaDatas = repoComDayOffManaData.getBySidWithHolidayDateCondition(companyId,
						employeeId, dateSubHoliday);
				if (!compensatoryDayOffManaDatas.isEmpty() || !leaveManagementDatas.isEmpty()) {
					errorList.add("Msg_737_sub_holiday");
				}
			}
			// チェックボタン「分割消化」をチェックする
			if (subHdManagementData.getCheckedSplit()) {
				GeneralDate dateOptionSubHoliday = subHdManagementData.getDateOptionSubHoliday();
				leaveManagementDatas = repoLeaveManaData.getBySidWithHolidayDate(companyId, employeeId,
						dateOptionSubHoliday);
				compensatoryDayOffManaDatas = repoComDayOffManaData.getBySidWithHolidayDateCondition(companyId,
						employeeId, dateOptionSubHoliday);
				if (!leaveManagementDatas.isEmpty() || !compensatoryDayOffManaDatas.isEmpty()) {
					errorList.add("Msg_737_sub_option_holiday");
				}
				/*
				 * List<CompensatoryDayOffManaData> dayoff =
				 * repoComDayOffManaData
				 * .getBySidWithHolidayDateCondition(companyId, employeeId,
				 * dateOptionSubHoliday); if (!dayoff.isEmpty()) {
				 * errorList.add("Msg_737_sub_option_holiday_2"); }
				 */
			}
		}
		// アルゴリズム「休出代休日数チェック処理」を実行する
		errorList.addAll(checkHolidayAndSubHoliday(subHdManagementData));
		this.checkHistoryOfCompany(subHdManagementData.getEmployeeId()
				, subHdManagementData.getDateHoliday()
				, subHdManagementData.getDateSubHoliday()
				, subHdManagementData.getDateOptionSubHoliday()
				, TypeOffsetJudgment.REAMAIN.value);
		return errorList;
	}

	/**
	 * 休出（年月日）チェック処理
	 * 
	 * @param subHdManagementData
	 * @param closure
	 * @return
	 * 
	 * @return
	 */
	public List<String> checkHoliday(GeneralDate holidayDate, Optional<GeneralDate> closureDate, int closureId) {
		List<String> errorList = new ArrayList<>();
		YearMonth processYearMonth = GeneralDate.today().yearMonth();
		if (!closureDate.isPresent()) {
			closureDate = this.getClosureDate(closureId, processYearMonth);
		}
		// 休出（年月日）と締め日をチェックする
		if (closureDate.isPresent() && holidayDate != null && !closureDate.get().after(holidayDate)) {
			errorList.add("Msg_1439");
			return errorList;
		}
		return errorList;
	}

	/**
	 * 代休（年月日）チェック処理
	 * 
	 * @param holidayDate
	 * @param subHolidayDate
	 * @param
	 * @param checkSplit
	 * @param closure
	 * @return
	 */
	public List<String> checkDateHoliday(Optional<GeneralDate> holidayDate, GeneralDate subHolidayDate,
			Optional<GeneralDate> closureDate, int closureId, Boolean checkSplit, Optional<GeneralDate> splitDate,
			Boolean checkHoliday) {
		List<String> errorList = new ArrayList<>();
		YearMonth processYearMonth = GeneralDate.today().yearMonth();
		// 既にドメインモデル「締め」を読み込んでいるかチェックする
		if (!closureDate.isPresent()) {
			closureDate = this.getClosureDate(closureId, processYearMonth);
		}
		// 代休（年月日）と締め日をチェックする
		if (closureDate.isPresent() && subHolidayDate != null && !closureDate.get().after(subHolidayDate)) {
			errorList.add("Msg_1440");
		}

		// 休出（年月日）と代休（年月日）をチェックする
		if (checkHoliday) {
			if (holidayDate.isPresent() && subHolidayDate != null && subHolidayDate.compareTo(holidayDate.get()) == 0) {
				errorList.add("Msg_730");
			}
		}
		// チェックボタン「分割消化」をチェックする
		if (checkSplit && splitDate.isPresent()) {
			// 代休（年月日）と分割消化.代休（年月日）をチェックする
			if (subHolidayDate != null && subHolidayDate.compareTo(splitDate.get()) == 0) {
				errorList.add("Msg_1441");
			}
			// 分割消化.代休（年月日）と締め日をチェックする
			if (closureDate.isPresent() && !closureDate.get().after(splitDate.get())) {
				errorList.add("Msg_1442");
			}
			// 分割消化.休出（年月日）と代休（年月日）をチェックする
			if (checkHoliday) {
				holidayDate.ifPresent(x -> {
					if (x.compareTo(splitDate.get()) == 0) {
						errorList.add("Msg_730_1");
					}
				});
			}
		}
		return errorList;
	}

	/**
	 * 休出代休日数チェック処理
	 * 
	 * @param subHdManagementData2
	 * @return
	 */
	public List<String> checkHolidayAndSubHoliday(SubHdManagementData subHdManagementData) {
		List<String> errorList = new ArrayList<>();
		// 代休チェックボックスをチェックする
		if (subHdManagementData.getCheckedSubHoliday()) {
			// 分割消化フラグをチェックする
			if (subHdManagementData.getCheckedSplit()) {
				// １日目の代休日数をチェックする
				if (!ItemDays.HALF_DAY.value.equals(subHdManagementData.getSelectedCodeSubHoliday())) {
					errorList.add("Msg_1256_1");
				} else if (!ItemDays.HALF_DAY.value.equals(subHdManagementData.getSelectedCodeOptionSubHoliday())) {
					errorList.add("Msg_1256_2");
				}
				if (!errorList.isEmpty()) {
					return errorList;
				}
			}
			errorList.addAll(this.checkHolidayAfterSubHoliday(subHdManagementData));
		}
		return errorList;
	}

	private List<String> checkHolidayAfterSubHoliday(SubHdManagementData subHdManagementData) {
		List<String> errorList = new ArrayList<>();
		// 休出チェックボックスをチェックする
		if (subHdManagementData.getCheckedHoliday()) {
			// 休出日数をチェックする
			if (subHdManagementData.getCheckedSplit()) {
				if (!ItemDays.ONE_DAY.value.equals(subHdManagementData.getSelectedCodeHoliday())) {
					errorList.add("Msg_1256_3");
				}
			} else {
				// 休出日数と１日目代休日数をチェックする
				if (!subHdManagementData.getSelectedCodeHoliday()
						.equals(subHdManagementData.getSelectedCodeSubHoliday())) {
					errorList.add("Msg_1259");
				}
			}
		}
		return errorList;
	}

	/**
	 * Get 締め日
	 * 
	 * @param closureId
	 * @param processYearMonth
	 * @return
	 */
	public Optional<GeneralDate> getClosureDate(int closureId, YearMonth processYearMonth) {
		Optional<Closure> optClosure = closureRepo.findById(AppContexts.user().companyId(), closureId);

		// Check exist and active
		if (!optClosure.isPresent() || optClosure.get().getUseClassification()
				.equals(UseClassification.UseClass_NotUse)) {
			return Optional.empty();
		}

		Closure closure = optClosure.get();

		// Get Processing Ym 処理年月
		YearMonth processingYm = closure.getClosureMonth().getProcessingYm();

		DatePeriod closurePeriod = ClosureService.getClosurePeriod(closureId, processingYm, optClosure);
		if (Objects.isNull(closurePeriod)) {
			return Optional.empty();
		}
		return Optional.of(closurePeriod.start());
	}
	
	/**
	 * 所属会社履歴をチェック
	 * @param sid 社員ID
	 * @param occurrenceDate 発生日
	 * @param digestionDate 消化日
	 * @param dividedDigestionDate 分割消化日
	 * @param flag 振休・代休区分
	 */
	private void checkHistoryOfCompany(String sid, GeneralDate occurrenceDate, GeneralDate digestionDate, GeneralDate dividedDigestionDate, Integer flag) {
		SyEmployeeImport sysEmp = syEmployeeAdapter.getPersonInfor(sid);
		
		if (flag == TypeOffsetJudgment.ABSENCE.value) {
			if (occurrenceDate != null && occurrenceDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "Com_SubstituteWork");
			} else if (digestionDate != null && digestionDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "Com_SubstituteHoliday");
			} else if (dividedDigestionDate != null && dividedDigestionDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "分割消化");
			} else if (occurrenceDate != null && occurrenceDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "Com_SubstituteWork");
			} else if (digestionDate != null && digestionDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "Com_SubstituteHoliday");
			} else if (dividedDigestionDate != null && dividedDigestionDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "分割消化");
			}
		}
		
		if (flag == TypeOffsetJudgment.REAMAIN.value) {
			if (occurrenceDate != null && occurrenceDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "休出");
			} else if (digestionDate != null && digestionDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "Com_CompensationHoliday");
			} else if (dividedDigestionDate != null && dividedDigestionDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "分割消化");
			} else if (occurrenceDate != null && occurrenceDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "休出");
			} else if (digestionDate != null && digestionDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "Com_CompensationHoliday");
			} else if (dividedDigestionDate != null && dividedDigestionDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "分割消化");
			}
		}
	}
}
