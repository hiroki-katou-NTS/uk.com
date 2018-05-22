/**
 * 
 */
package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nam.lh
 *
 */
@Stateless
public class AddSubHdManagementService {
	@Inject
	ClosureRepository repoClosure;
	@Inject
	LeaveManaDataRepository repoLeaveManaData;
	@Inject
	ComDayOffManaDataRepository repoComDayOffManaData;
	@Inject
	LeaveComDayOffManaRepository repoLeaveComDayOffMana;

	/**
	 * @param subHdManagementData
	 */
	public List<String> addProcessOfSHManagement(SubHdManagementData subHdManagementData) {
		List<String> errorList = addSubSHManagement(subHdManagementData);
		if (!errorList.isEmpty()) {
			return errorList;
		} else {
			String comDayOffID = IdentifierUtil.randomUniqueId();
			String leaveId = IdentifierUtil.randomUniqueId();

			// 休出の入力あり(có nhập 休出)
			if (subHdManagementData.getCheckedHoliday() == true) {
				int subHDAtr = 0;
				if (subHdManagementData.getDayRemaining() == 0) {
					subHDAtr = 1;
				}
				// Todo #118200
				int equivalentHalfDay = 0;
				int equivalentADay = 0;

				LeaveManagementData domainLeaveManagementData = new LeaveManagementData(IdentifierUtil.randomUniqueId(),
						AppContexts.user().companyId(), subHdManagementData.getEmployeeId(), false,
						subHdManagementData.getDateHoliday(), subHdManagementData.getDuedateHoliday(),
						subHdManagementData.getSelectedCodeHoliday(), 0, Double.valueOf(0), 0, subHDAtr, equivalentADay,
						equivalentHalfDay);
				repoLeaveManaData.create(domainLeaveManagementData);
			}

			// 代休の入力あり(có nhập 代休)
			if (subHdManagementData.getCheckedSubHoliday() == true) {
				CompensatoryDayOffManaData domainCompensatoryDayOffManaData = new CompensatoryDayOffManaData(
						comDayOffID, AppContexts.user().companyId(), subHdManagementData.getEmployeeId(), false,
						subHdManagementData.getDateSubHoliday(), subHdManagementData.getSelectedCodeSubHoliday(), 0,
						Double.valueOf(0), 0);
				repoComDayOffManaData.create(domainCompensatoryDayOffManaData);
				if (subHdManagementData.getCheckedSplit() == true) {
					// Todo #118200
				}
			}

			if (subHdManagementData.getCheckedHoliday() == true && subHdManagementData.getCheckedSubHoliday() == true) {
				// ドメインモデル「振休休出振付け管理」に紐付きチェックされているもの全てを追加する
				// (Add toàn bộ phần được check liên kết với domain 「振休休出振付け管理」
				// )
				BigDecimal usedDays = null;
				Double usedDay = subHdManagementData.getSelectedCodeOptionSubHoliday()
						+ subHdManagementData.getSelectedCodeOptionSubHoliday();

				if (subHdManagementData.getCheckedSplit() == true) {
					usedDays = BigDecimal.valueOf(usedDay);
				} else {
					usedDays = BigDecimal.valueOf(subHdManagementData.getSelectedCodeOptionSubHoliday());
				}

				int targetSelectionAtr = 2;
				int usedHours = 0;
				LeaveComDayOffManagement domainLeaveComDayOffManagement = new LeaveComDayOffManagement(leaveId,
						comDayOffID, usedDays, usedHours, targetSelectionAtr);
				repoLeaveComDayOffMana.add(domainLeaveComDayOffManagement);
			}

			// アルゴリズム「代休残数管理データ更新フラグ処理」を実行する
			// (THực hiện thuật toán 「代休残数管理データ更新フラグ処理」)
			// Todo Pending
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
		// Todo : get closureId, useAtr from screen B
		int closureId = subHdManagementData.getClosureId();
		UseClassification useAtr = null;
		YearMonth processingYm = GeneralDate.today().yearMonth();
		// ドメインモデル「締め」を読み込む
		// Todo #118092
		Optional<Closure> closure = repoClosure.find(companyId, closureId, useAtr, processingYm);

		if (subHdManagementData.getCheckedHoliday() == true) {
			String employeeId = subHdManagementData.getEmployeeId();
			errorList.addAll(checkHoliday(subHdManagementData.getDateHoliday(), closure));

			// ドメインモデル「休出管理データ」を読み込む (Đọc domain 「休出管理データ」)
			// Todo #118097
			GeneralDate dateHoliday = subHdManagementData.getDateHoliday();
			List<LeaveManagementData> leaveManagementDatas = repoLeaveManaData.getBySidWithHolidayDate(companyId,
					employeeId, dateHoliday);
			if (!leaveManagementDatas.isEmpty()) {
				errorList.add("Msg_737");
			}

		} else {
			errorList.add("Msg_728");
		}
		// チェックボタン「代休」をチェックする (Check checkbox 「代休」)
		if (subHdManagementData.getCheckedSubHoliday() == true) {
			// アルゴリズム「代休（年月日）チェック処理」を実行する (Thực hiện thuât toán 「代休（年月日）チェック処理」)
			errorList.addAll(checkDateHoliday(Optional.of(subHdManagementData.getDateHoliday()),
					subHdManagementData.getDateSubHoliday(), closure, closureId));
			// ドメインモデル「代休管理データ」を読み込む (đọc domain 「代休管理データ」)
			String employeeId = subHdManagementData.getEmployeeId();
			errorList.addAll(checkHoliday(subHdManagementData.getDateHoliday(), closure));
			String sid = employeeId;
			String cid = companyId;
			GeneralDate dateSubHoliday = subHdManagementData.getDateSubHoliday();
			List<CompensatoryDayOffManaData> compensatoryDayOffManaDatas = repoComDayOffManaData
					.getBySidWithHolidayDateCondition(cid, sid, dateSubHoliday);
			if (!compensatoryDayOffManaDatas.isEmpty()) {
				errorList.add("Msg_737");
			}
		}

		// アルゴリズム「休出代休日数チェック処理」を実行する
		errorList.addAll(checkHolidayAndSubHoliday(subHdManagementData));

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
	public List<String> checkHoliday(GeneralDate holidayDate, Optional<Closure> closure) {
		List<String> errorList = new ArrayList<>();

		// 休出（年月日）と締め日をチェックする /(Check 休出（年月日） và 締め日)
		if (closure.isPresent()) {
			// Todo #118092
			GeneralDate closingDate = holidayDate;
			if (holidayDate.after(closingDate)) {
				errorList.add("Msg_745");
			}
		}
		return errorList;
	}

	/**
	 * 代休（年月日）チェック処理
	 * 
	 * @param holidayDate
	 * @param subHolidayDate
	 * @param closure
	 * @return
	 */
	public List<String> checkDateHoliday(Optional<GeneralDate> holidayDate, GeneralDate subHolidayDate,
			Optional<Closure> closure, int closureId) {
		List<String> errorList = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		// Todo : get closureId, useAtr from screen B

		UseClassification useAtr = null;
		YearMonth processingYm = GeneralDate.today().yearMonth();

		// 既にドメインモデル「締め」を読み込んでいるかチェックする (Check xem đã đọc domain 「締め」 chưa)
		if (!closure.isPresent()) {
			closure = repoClosure.find(companyId, closureId, useAtr, processingYm);
		}
		// 代休（年月日）と締め日をチェックする (Check 代休（年月日） và 締め日)
		// Todo #118092
		GeneralDate closingDate = subHolidayDate;
		if (subHolidayDate.after(closingDate)) {
			errorList.add("Msg_746");
		}
		// 休出（年月日）と代休（年月日）をチェックする (Check 休出（年月日） và 代休（年月日）)
		if (holidayDate.isPresent() && subHolidayDate == holidayDate.get()) {
			errorList.add("Msg_730");
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
		if (subHdManagementData.getCheckedSubHoliday() == true) {
			// 分割消化フラグをチェックする
			if (subHdManagementData.getCheckedSplit() == true) {
				// １日目の代休日数をチェックする
				if (ItemDays.HALF_DAY.equals(subHdManagementData.getSelectedCodeSubHoliday())) {
					errorList.add("Msg_1256");
				}
				if (ItemDays.HALF_DAY.equals(subHdManagementData.getSelectedCodeSubHoliday())) {
					if (ItemDays.HALF_DAY.equals(subHdManagementData.getSelectedCodeOptionSubHoliday())) {
						// 休出チェックボックスをチェックする
						if (subHdManagementData.getCheckedHoliday() == true) {
							if (subHdManagementData.getCheckedSplit() == true) {
								// 休出日数をチェックする
								if (ItemDays.ONE_DAY.equals(subHdManagementData.getSelectedCodeHoliday())) {
									if (ItemDays.HALF_DAY.equals(subHdManagementData.getSelectedCodeSubHoliday())) {
										if (ItemDays.HALF_DAY
												.equals(subHdManagementData.getSelectedCodeOptionSubHoliday())) {
											errorList.add("Msg_1260");
										}
									} else {
										errorList.add("Msg_1260");
									}
								} else {
									errorList.add("Msg_1256");
								}
							} else {
								// 休出日数と１日目代休日数をチェックする
								if (subHdManagementData.getSelectedCodeHoliday() != subHdManagementData
										.getSelectedCodeSubHoliday()) {
									errorList.add("Msg_1259");
								}
							}
						}
					} else {
						errorList.add("Msg_1256");
					}
				}
			}

		} else {

		}
		return errorList;
	}

}
