package nts.uk.ctx.at.function.app.command.processexecution.createschedule.executionprocess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.command.processexecution.ApprovalPeriodByEmp;
import nts.uk.ctx.at.function.dom.adapter.alarm.BasicScheduleAdapter;
import nts.uk.ctx.at.function.dom.processexecution.TempAbsenceHistoryService;
import nts.uk.ctx.at.function.dom.statement.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.function.dom.statement.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkplaceHistItemImported;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.requestperiodchange.RequestPeriodChangeService;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceImport;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHis;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeService;

/**
 * 異動者・勤務種別変更者の作成対象期間の計算（個人別）
 * 
 * @author tutk
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class CalPeriodTransferAndWorktype {

	@Inject
	private EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter;

	@Inject
	private BusinessTypeOfEmployeeService businessTypeOfEmpHisService;

	 @Inject
    private BasicScheduleAdapter basicScheduleAdapter;

	@Inject
	private RequestPeriodChangeService requestPeriodChangeService;
	
	@Inject
	private TempAbsenceHistoryService tempAbsenceHistoryService;

	/**
	 * @param companyId
	 *            会社ID
	 * @param listEmp
	 *            社員ID(List)
	 * @param period
	 *            期間
	 * @param isTransfer
	 *            異動時に再作成（TRUE、FALSE）
	 * @param isWorkType
	 *            勤務種別変更時に再作成（TRUE、FALSE）
	 * @return List<ApprovalPeriodByEmp> 承認結果の反映対象期間（List）
	 */
	@SuppressWarnings("unchecked")
	public List<ApprovalPeriodByEmp> calPeriodTransferAndWorktype(String companyId, List<String> listEmp,
			DatePeriod period, boolean isTransfer, boolean isWorkType, boolean isLeave) {

		List<ApprovalPeriodByEmp> listApprovalPeriodByEmp = new ArrayList<>();
		List<BusinessTypeOfEmployeeHis> listBusinessTypeOfEmpDto = new ArrayList<>();
		TempAbsenceImport tempAbsence = null;
		
		List<nts.uk.ctx.at.function.dom.statement.dtoimport.ExWorkPlaceHistoryImport> listExWorkPlaceHistoryImport = new ArrayList<>();

		// INPUT．「異動時に再作成」をチェックする
		if (isTransfer) {
			// 社員ID（List）と期間から個人情報を取得する - RQ401
			EmployeeGeneralInfoImport employeeGeneralInfoImport = employeeGeneralInfoAdapter
					.getEmployeeGeneralInfo(listEmp, period, false, false, false, true, false); // 職場を取得するか = True
			if (!employeeGeneralInfoImport.getExWorkPlaceHistoryImports().isEmpty()) {
				listExWorkPlaceHistoryImport = employeeGeneralInfoImport
						.getExWorkPlaceHistoryImports();
//				workplaceItems = exWorkPlaceHistoryImportFn.getWorkplaceItems().stream()
//						.map(c -> new ExWorkplaceHistItemImported(c.getHistoryId(), c.getPeriod(), c.getWorkplaceId()))
//						.collect(Collectors.toList());
			}
		}
		// INPUT．「勤務種別変更時に再作成」をチェックする
		if (isWorkType) {
			// <<Public>> 社員ID(List)、期間で期間分の勤務種別情報を取得する
			listBusinessTypeOfEmpDto = businessTypeOfEmpHisService.find(listEmp, period);
		}
		// INPUT．「休職・休業者再作成」をチェックする
		if (isLeave) {
			// 社員（List）と期間から休職休業を取得する
			tempAbsence = this.tempAbsenceHistoryService.getTempAbsence(companyId, period, listEmp);
		}
		// INPUT．「社員ID(List)」をループする
		for (String empId : listEmp) {

			// ドメインモデル「勤務予定基本情報」を取得する - 年月日が最も大きいレコードを取得する
			Optional<GeneralDate> basicScheduleDateHighest = basicScheduleAdapter.acquireMaxDateBasicSchedule(Arrays.asList(empId));

			// 「勤務予定基本情報」を取得できない場合次の社員へ
			if (!basicScheduleDateHighest.isPresent())
				continue;

			// INPUT「開始日」と取得したデータの年月日から「対象期間」を求める
			DatePeriod newPeriod = new DatePeriod(period.start(), basicScheduleDateHighest.get());

			// INPUT．「異動時に再作成」をチェックする
			List<DatePeriod> dataWorkplace = new ArrayList<>();
			if (isTransfer) {
				List<ExWorkplaceHistItemImported> workplaceItems = new ArrayList<>();
				for(nts.uk.ctx.at.function.dom.statement.dtoimport.ExWorkPlaceHistoryImport data :listExWorkPlaceHistoryImport ) {
					if(data.getEmployeeId().equals(empId)) {
						workplaceItems = data.getWorkplaceItems().stream()
								.map(c -> new ExWorkplaceHistItemImported(c.getHistoryId(), c.getPeriod(), c.getWorkplaceId()))
								.collect(Collectors.toList());
						break;
					}
				}
				// 職場・勤務種別変更期間を求める
				dataWorkplace = requestPeriodChangeService.getPeriodChange(empId, newPeriod, workplaceItems,
						Collections.emptyList(), true, isTransfer);
			}
			List<DatePeriod> dataWorkType = new ArrayList<>();
			// INPUT．「勤務種別変更時に再作成」をチェックする
			if (isWorkType) {
				// 職場・勤務種別変更期間を求める
				dataWorkType = requestPeriodChangeService.getPeriodChange(empId, newPeriod, Collections.emptyList(),
						listBusinessTypeOfEmpDto.stream().filter(c->c.getEmployee().getSId().equals(empId)).collect(Collectors.toList()), 
						false, isWorkType);
			}
			List<DatePeriod> dataLeave = new ArrayList<>();
			// INPUT．「休職・休業者再作成」をチェックする
			if (isLeave) {
				// 休職休業履歴変更期間を求める
				dataLeave = this.tempAbsenceHistoryService.findChangingLeaveHistoryPeriod(empId, newPeriod, tempAbsence, true);
			}
			// 計算するする「期間」がない場合次の社員へ
			if (dataWorkplace.isEmpty() && dataWorkType.isEmpty() && dataLeave.isEmpty())
				continue;

			// 求めた「期間」の重複しているきかんを取除く
			// 取り除いた期間（List）を返す
			List<DatePeriod> listDatePeriodAll = new ArrayList<>();
			listDatePeriodAll.addAll(createListAllPeriod(dataWorkplace, dataWorkType, dataLeave));
			listApprovalPeriodByEmp.add(new ApprovalPeriodByEmp(empId, listDatePeriodAll));
		}
		// 「承認結果の反映対象期間（List）」を返す
		return listApprovalPeriodByEmp;
	}

	@SuppressWarnings("unchecked")
	private List<DatePeriod> createListAllPeriod(List<DatePeriod>... listPeriod) {
		List<DatePeriod> listResult = new ArrayList<>();
		List<DatePeriod> listAll = new ArrayList<>();
		Arrays.asList(listPeriod).forEach(listAll::addAll);
		listAll.sort((x, y) -> x.start().compareTo(y.start()));

		for (int i = 0; i < listAll.size(); i++) {
			DatePeriod merged = new DatePeriod(listAll.get(i).start(), listAll.get(i).end());
			for (int j = i + 1; j < listAll.size(); j++) {
				DatePeriod next = listAll.get(j);
				if (merged.contains(next.start()) && merged.contains(next.end())) {
					i++;
				} else if (merged.contains(next.start()) || merged.end().addDays(1).equals(next.start())) {
					merged = merged.cutOffWithNewEnd(next.end());
					i++;
				} else {
					break;
				}
			}
			listResult.add(merged);
		}
		return listResult;
	}
}
