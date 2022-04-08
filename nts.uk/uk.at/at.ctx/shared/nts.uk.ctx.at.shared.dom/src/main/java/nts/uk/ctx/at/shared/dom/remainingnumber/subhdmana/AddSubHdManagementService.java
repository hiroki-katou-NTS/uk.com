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
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
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
	public List<String> addProcessOfSHManagement(SubHdManagementData subHdManagementData, Double linkingDate, Double displayRemainDays) {
		List<String> errorList = addSubSHManagement(subHdManagementData, linkingDate, displayRemainDays);
		if (!errorList.isEmpty()) {
			return errorList;
		} else {
			String comDayOffID = IdentifierUtil.randomUniqueId();
			String comDayOffIDSub = IdentifierUtil.randomUniqueId();
			String leaveId = IdentifierUtil.randomUniqueId();
			if (subHdManagementData.getCheckedHoliday()) {
				int subHDAtr = DigestionAtr.UNUSED.value;
				if (subHdManagementData.getCheckedHoliday() && subHdManagementData.getCheckedSubHoliday()) {
					subHDAtr = DigestionAtr.USED.value;
				} else if (subHdManagementData.getDuedateHoliday().beforeOrEquals(GeneralDate.today())) {
					subHDAtr = DigestionAtr.EXPIRED.value;
				}
				int equivalentHalfDay = 0;
				int equivalentADay = 0;
				LeaveManagementData domainLeaveManagementData = new LeaveManagementData(leaveId,
						AppContexts.user().companyId(), subHdManagementData.getEmployeeId(), false,
						subHdManagementData.getDateHoliday(), subHdManagementData.getDuedateHoliday(),
						subHdManagementData.getSelectedCodeHoliday(), 0, subHdManagementData.getSelectedCodeHoliday(), 0,
						subHDAtr, equivalentADay, equivalentHalfDay);
				domainLeaveManagementData.validate();
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
			}

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
					Double oldUnUseDay = leaveManagementData.getUnUsedDays().v();
					Double requireDays = compensatoryDayOffManaData.getRequireDays().v();
					Integer oldUnUseTimes = leaveManagementData.getUnUsedTimes().v();
					Integer requiredTimes = compensatoryDayOffManaData.getRequiredTimes().v();
					//	未使用日数、未使用時間数を計算 Tính toán số ngày/ số giờ chưa sử dụng
					if (oldUnUseDay - requireDays > 0) {
						unUsedDay = oldUnUseDay - requireDays;
					} else {
						unUsedDay = 0d;
					}

					if (oldUnUseTimes - requiredTimes > 0) {
						unUsedHour = oldUnUseTimes - requiredTimes;
					} else {
						unUsedHour = 0;
					}
					//	ループ中の「休出管理データ」を更新する Update "Data quản lý đi làm ngày nghỉ" trong vòng lặp
					int subHdAtr;
					GeneralDate today = GeneralDate.today();
					if (unUsedDay > 0) {
						if (leaveManagementData.getExpiredDate().afterOrEquals(today)) {
							subHdAtr = DigestionAtr.UNUSED.value;
						} else {
							subHdAtr = DigestionAtr.EXPIRED.value;
						}
					} else {
						subHdAtr = DigestionAtr.USED.value;
					}
					LeaveManagementData updateData = new LeaveManagementData(leaveManagementData.getID(),
							leaveManagementData.getCID(), leaveManagementData.getSID(), false,
							leaveManagementData.getComDayOffDate().getDayoffDate().get() , leaveManagementData.getExpiredDate(),
							leaveManagementData.getOccurredDays(), leaveManagementData.getOccurredTimes(), unUsedDay,
							unUsedHour, subHdAtr, leaveManagementData.getFullDayTime(), leaveManagementData.getHalfDayTime(),
							leaveManagementData.getDisapearDate());
					repoLeaveManaData.update(updateData);
					// ドメインモデル「休出代休紐付け管理」を追加 Bổ sung domain model "Quản lý liên kết đi làm ngày nghỉ/ nghĩ bù)
					LeaveComDayOffManagement domainLeaveComDayOffManagementSub = new LeaveComDayOffManagement(
							subHdManagementData.getEmployeeId(), 								// ・社員ID　＝　Input．社員ID
							leaveManagementData.getComDayOffDate().getDayoffDate().orElse(null),	 	// ・紐付け．発生日　＝　ループ中の休出管理データ．休出日
							compensatoryDayOffManaData.getDayOffDate().getDayoffDate().orElse(null), 	// ・紐付け．使用日　＝　ループ中の代休管理データ．代休日
							oldUnUseDay >= requireDays 											// ・紐付け．使用日数　＝
								? requireDays									 				// 計算した「未使用日数」　＞＝０　－＞　ループ中の代休管理データ．必要日数
								: oldUnUseDay, 													// Else　－＞　ループ中の休出管理データ．未使用日数
							TargetSelectionAtr.MANUAL.value);									// ・紐付け．対象選択区分　＝　手動
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
	private List<String> addSubSHManagement(SubHdManagementData subHdManagementData, Double linkingDate, Double displayRemainDays) {
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
		this.checkHolidayAndSubHoliday(subHdManagementData, linkingDate, displayRemainDays);
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
	public void checkHolidayAndSubHoliday(SubHdManagementData subHdManagementData, Double linkingDate, Double displayRemainDays) {
		Double remainDays = displayRemainDays;
		// 休出日数（I6_3）
		Double selectedCodeHoliday = subHdManagementData.getSelectedCodeHoliday();
		// 代休日数（I11_3）
		Double selectedCodeSubHoliday = subHdManagementData.getSelectedCodeSubHoliday();
		// 代休日数（I12_4）
		Double selectedCodeOptionSubHoliday = subHdManagementData.getSelectedCodeOptionSubHoliday();

		if (!subHdManagementData.getCheckedHoliday()) {
			selectedCodeHoliday = 0.0;
		} else {
			linkingDate = 0.0;
		}
		if (!subHdManagementData.getCheckedSubHoliday()) {
			selectedCodeSubHoliday = 0.0;
		}
		if (!subHdManagementData.getCheckedSplit()) {
			selectedCodeOptionSubHoliday = 0.0;
		}

		// 代休残数をチェック
		if (remainDays < 0) { // 振休残数＜0の場合
			// エラーメッセージ「Msg_2031」を表示する
			throw new BusinessException("Msg_2031");
		}
		// 休出チェックボックスをチェックする
		if (subHdManagementData.getCheckedHoliday()) { // チェックするの場合
			// 代休残数　＝　休出日数（I6_3）+　紐付け日数（I12_8）-　代休日数（I11_3）-　代休日数（I12_4）
			remainDays = selectedCodeHoliday + linkingDate - selectedCodeSubHoliday - selectedCodeOptionSubHoliday;
			// 振休日数をチェック
			if (subHdManagementData.getCheckedSubHoliday() && remainDays < 0) {
				// エラーメッセージ「Msg_2032」
				throw new BusinessException("Msg_2032");
			}
			return;
		}

		// 分割消化チェックボックスをチェック
		if (subHdManagementData.getCheckedSplit()) {
			throw new BusinessException("Msg_1256");
		}
		// 代休残数　＝　紐付け日数（I12_8）-　代休日数（I11_3）
		if (linkingDate != 0) {
			remainDays = linkingDate - selectedCodeSubHoliday;
		} else {
			remainDays = selectedCodeSubHoliday;
		}
		// 代休日数をチェック
		if (remainDays < 0) { // 代休残数　＜0
			throw new BusinessException("Msg_2032");
		}
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
