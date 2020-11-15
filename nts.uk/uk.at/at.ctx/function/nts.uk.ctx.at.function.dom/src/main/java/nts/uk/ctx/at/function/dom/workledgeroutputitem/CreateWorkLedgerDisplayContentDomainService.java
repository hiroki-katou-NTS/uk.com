package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 勤務台帳の表示内容を作成する
 *
 * @author khai.dh
 */
public class CreateWorkLedgerDisplayContentDomainService {
	@Inject
	private GetAggregableMonthlyAttendanceItemAdapter getAggblMonthlyAtddItemAdapter;

	/**
	 * 勤務台帳の表示内容を作成する
	 *
	 * @param require		Require
	 * @param datePeriod	期間
	 * @param empInfoList	List<社員情報>
	 *
	 * @return List<勤務台帳の帳票表示内容>
	 */
	public static List<WorkLedgerDisplayContent> createWorkLedgerDisplayContent(
			Require require,
			DatePeriod datePeriod,
			List<EmployeeBasicInfoImport> empInfoList,
			WorkLedgerOutputSetting workLedgerOutputSetting,
			List<WorkplaceInfor> workplaceInfoList) {

		List<String> empIdList = empInfoList.stream().map(item -> item.getSid()).collect(Collectors.toList());

		// ① = call() 社員の指定期間中の所属期間を取得する
		List<StatusOfEmployee> empAffPeriodList = getEmpAffPeriodList(datePeriod, empIdList);

		// ② = call() 基準日で社員の雇用と締め日を取得する
		List<ClosureDateEmployment> emtAndDlEmpOnRefDateList = GetClosureDateEmploymentDomainService.getByDate(
				require,
				datePeriod.end(),
				empIdList
		);
		Map<String, ClosureDateEmployment> emtAndDlEmpOnRefDateMap = emtAndDlEmpOnRefDateList.stream().collect(
				Collectors.toMap(x -> x.getEmployeeId(), x -> x));

		// ③ = 取得する(会社ID): 集計可能勤怠項目ID
		int aggregableMonthlyAttendanceItem = getAggregableMonthlyAttendanceItem();

		// ④ = call() 会社の月次項目を取得する
		Map<String, MonthlyAttendanceItem> monthlyAttendanceItemMap = getMontlyAttendanceItemOfCompany(
				AppContexts.user().companyId(),
				empAffPeriodList
		);

		// ・Loop 「社員の会社所属状況」の「対象社員」in ①
		for (val empAffPeriod: empAffPeriodList) {
			val empId = empAffPeriod.getEmployeeId();
			val emtAndDlEmpOnRefDate = emtAndDlEmpOnRefDateMap.get(empId);

			// ⑤ call() 月別実績取得の為に年月日から適切な年月に変換する
			int closureDay = emtAndDlEmpOnRefDate.getClosure()
					.getHistoryByBaseDate(datePeriod.end()).getClosureDate().getClosureDay().v();
			List<YearMonthPeriod> yearMonthPeriodList = empAffPeriod.getListPeriod()
					.stream()
					.map(x -> GetSuitableDateByClosureDateUtility.getByClosureDate(x, closureDay))
					.collect(Collectors.toList());

			// 5.1
			// [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
			// TODO

			// 5.2 <<create>>
			List<MonthlyOutputLine> monthlyDataList = new ArrayList<>();
			MonthlyOutputLine monthlyOutputLine = new MonthlyOutputLine(
					null, // TODO How to init?
					monthlyAttendanceItemMap.get(empId).getAttendanceName().v(),
					-1, // TODO ・※１．順位　→　印刷順位 ?
					-1, // TODO 合計?
					null // TODO ・④　→　属性
			);
			monthlyDataList.add(monthlyOutputLine);
			val workLedgerDisplayContent = new WorkLedgerDisplayContent(
					monthlyDataList,
					emtAndDlEmpOnRefDate.getEmploymentCode(),
					emtAndDlEmpOnRefDate.getEmploymentName(),
					workplaceInfoList.get(0).getWorkplaceCode(), // TODO How to map?
					workplaceInfoList.get(0).getWorkplaceName() // TODO How to map?
			);
		}

		return null;
	}

	/**
	 * 社員の指定期間中の所属期間を取得する
	 *
	 * @param datePeriod
	 * @param empIdList
	 * @return 社員の指定期間中の所属期間
	 */
	private static List<StatusOfEmployee> getEmpAffPeriodList(DatePeriod datePeriod, List<String> empIdList) {
		// import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
		// TODO not implemented
		return null;
	}

	/**
	 * 集計可能な月次の勤怠項目を取得する#取得する(会社ID)
	 */
	private static int getAggregableMonthlyAttendanceItem() {
		// TODO not implemented
		return -1;
	}

	/**
	 * 集計可能な月次の勤怠項目を取得する#取得する(会社ID)
	 */
	private static Map<String, MonthlyAttendanceItem> getMontlyAttendanceItemOfCompany(
			String cid,
			List<StatusOfEmployee> empAffPeriodList) {

		// TODO not implemented
		return null;
	}

	/**
	 * [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
	 */
	private void getMonthlyActualValue() {
		// TODO not implemented
	}

	public interface Require extends GetClosureDateEmploymentDomainService.Require {
		// [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
		Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(
				List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);

	}
}
