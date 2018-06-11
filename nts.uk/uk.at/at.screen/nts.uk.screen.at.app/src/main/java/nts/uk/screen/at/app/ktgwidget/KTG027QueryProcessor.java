package nts.uk.screen.at.app.ktgwidget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.auth.dom.adapter.AuthWorkPlaceAdapter;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AgreementTimeDetail;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTimeImpl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.screen.at.app.ktgwidget.find.dto.AgreementTimeList36;
import nts.uk.screen.at.app.ktgwidget.find.dto.AgreementTimeOfMonthlyDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.OvertimeHours;
import nts.uk.screen.at.app.ktgwidget.find.dto.OvertimeHoursDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class KTG027QueryProcessor {
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;

	@Inject
	private AuthWorkPlaceAdapter authWorkPlaceAdapter;

	@Inject
	private WorkClosureQueryProcessor workClosureQueryProcessor;

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;

	@Inject
	private GetAgreementTime getAgreementTime;

	@Inject
	private OvertimeHoursRepository overtimeHoursRepo;

	public GeneralDate checkSysDateOrCloseEndDate() {
		// EA luôn trả v�Systemdate
		return GeneralDate.today();
	}

	public int getLoginClosure(GeneralDate referenceDate) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// ログイン�雔�を取得す�
		// (Lấy employeement của login) request list 31
		Optional<BsEmploymentHistoryImport> employmentHistoryImport = shareEmploymentAdapter.findEmploymentHistory(companyID, employeeID, referenceDate);
		if (!employmentHistoryImport.isPresent()) {
			// If fail return closure ID = 1
			return 1;
		} else {
			Optional<ClosureEmployment> closureEmploymentOpt = closureEmploymentRepo.findByEmploymentCD(companyID, employmentHistoryImport.get().getEmploymentCode());
			if (!closureEmploymentOpt.isPresent()) {
				throw new RuntimeException("Not found Employment history by employeeCD:" + employmentHistoryImport.get().getEmploymentCode());
			}
			return closureEmploymentOpt.get().getClosureId();
		}
	}

	public Optional<DatePeriod> getDatePeriod(int targetMonth, int closureID) {
		String companyID = AppContexts.user().companyId();
		List<DatePeriod> optDatePeriod = new ArrayList<>();
		Optional<Closure> optClosure = closureRepository.findByIdAndUseAtr(companyID, closureID, UseClassification.UseClass_Use);
		if (optClosure.isPresent()) {
			optDatePeriod = optClosure.get().getPeriodByYearMonth(new YearMonth(targetMonth));
		}

		return optDatePeriod.isEmpty() ? Optional.empty() : Optional.of(optDatePeriod.get(0));
	}

	public OvertimeHours buttonPressingProcess(int targetMonth, int closureID) {

		String employeeID = AppContexts.user().employeeId();
		String companyID = AppContexts.user().companyId();
		// �め期間を取得す�
		// (Lấy closing period) tu domain Closure
		/*
		 * ouput: Closing period start date End date
		 */
		Optional<DatePeriod> datePeriod = getDatePeriod(targetMonth, closureID);
		if (!datePeriod.isPresent()) {
			return new OvertimeHours("Msg_1134", null);
		}
		// ログイン�権限篛��職場を取得す�
		// (Lấy workplace trong phạm vi quyền login) = Request List 334
		// referenceDate lấy theo baseDate �xử lý 1
		GeneralDate referenceDate = checkSysDateOrCloseEndDate();
		List<String> listWorkPlaceID = authWorkPlaceAdapter.getListWorkPlaceID(employeeID, referenceDate);
		if (listWorkPlaceID.isEmpty()) {
			return new OvertimeHours("Msg_1135", null);
		}
		// �めに紐づく雇用を取得す�
		// (lấy employeement ứng với closing)
		// Ch�n�y lấy dữ liệu lâu vcl
		List<ClosureEmployment> listClosureEmployment = closureEmploymentRepo.findByClosureId(companyID, closureID);
		if (listClosureEmployment.isEmpty()) {
			return new OvertimeHours("Msg_1136", null);
		}
		// 対象耂�取得す�
		List<String> listEmploymentCD = listClosureEmployment.stream().map(c -> c.getEmploymentCD()).collect(Collectors.toList());
		// (Lấy target) Lấy request list 335
		// imported�権限管琼�「所属�場履歴」を取得す�
		List<AgreementTimeList36> data = new ArrayList<>();
		// for (DatePeriod datePeriod : listDatePeriod) {
		long startTime = System.nanoTime();
		//List<String> listEmpID = empEmployeeAdapter.getListEmpByWkpAndEmpt(listWorkPlaceID, listEmploymentCD, datePeriod.get());
		List<String> listEmpID = getListEmpByWkpAndEmpt(listWorkPlaceID, listEmploymentCD, datePeriod.get());

		if (listEmpID.isEmpty()) {
			return new OvertimeHours("Msg_1137", null);
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // ms;
		System.out.println("RequestList335:" + duration);
		// 対象�時間外労働時間を取得す�
		// (Lấy worktime ngo�i period) lấy RequestList 333
		startTime = System.nanoTime();
		List<AgreementTimeDetail> listAgreementTimeDetail = getAgreementTime.get(companyID, listEmpID, YearMonth.of(targetMonth), ClosureId.valueOf(closureID));
		endTime = System.nanoTime();
		duration = (endTime - startTime) / 1000000; // ms
		System.out.println("RequestList333:" + duration);
		if (listAgreementTimeDetail.isEmpty()) {
			return new OvertimeHours("Msg_1138", null);
		}
		// 取得した時間外労働情報をセッ�
		// (Set thông tin công việc ngo�i gi�đã lấy)
		List<String> lstEmpID = listAgreementTimeDetail.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		// Imported�就業�「社員」を取得す�
		// (Lấy Imported�就業�「employee� Lay Request61
		List<PersonEmpBasicInfoImport> listEmpBasicInfoImport = empEmployeeAdapter.getPerEmpBasicInfo(lstEmpID);
		for (AgreementTimeDetail agreementTimeDetail : listAgreementTimeDetail) {
			Optional<PersonEmpBasicInfoImport> personInfor = listEmpBasicInfoImport.stream().filter(c -> c.getEmployeeId().equals(agreementTimeDetail.getEmployeeId())).findFirst();
			if (!personInfor.isPresent()) {
				break;
			}
			AgreementTimeList36 agreementTimeList36 = new AgreementTimeList36(personInfor.get().getEmployeeCode(), personInfor.get().getBusinessName(), null,
					new AgreementTimeOfMonthlyDto(!agreementTimeDetail.getConfirmed().isPresent()? 0 : agreementTimeDetail.getConfirmed().get().getAgreementTime().v(),
							!agreementTimeDetail.getConfirmed().isPresent() ? 0 : agreementTimeDetail.getConfirmed().get().getLimitErrorTime().v(),
							!agreementTimeDetail.getConfirmed().isPresent() ? 0 : agreementTimeDetail.getConfirmed().get().getLimitAlarmTime().v(),
							!agreementTimeDetail.getConfirmed().isPresent() ? 0 : (!agreementTimeDetail.getConfirmed().get().getExceptionLimitErrorTime().isPresent() ? 0 :agreementTimeDetail.getConfirmed().get().getExceptionLimitErrorTime().get().v()),
							!agreementTimeDetail.getConfirmed().isPresent() ? 0 : (!agreementTimeDetail.getConfirmed().get().getExceptionLimitAlarmTime().isPresent() ? 0 :agreementTimeDetail.getConfirmed().get().getExceptionLimitAlarmTime().get().v()),
							!agreementTimeDetail.getConfirmed().isPresent() ? 0 : agreementTimeDetail.getConfirmed().get().getStatus().value),
					new AgreementTimeOfMonthlyDto(!agreementTimeDetail.getAfterAppReflect().isPresent()? 0 : agreementTimeDetail.getAfterAppReflect().get().getAgreementTime().v(),
							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0 : agreementTimeDetail.getAfterAppReflect().get().getLimitErrorTime().v(),
							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0 : agreementTimeDetail.getAfterAppReflect().get().getLimitAlarmTime().v(),
							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0 : (!agreementTimeDetail.getAfterAppReflect().get().getExceptionLimitErrorTime().isPresent() ? 0 :agreementTimeDetail.getAfterAppReflect().get().getExceptionLimitErrorTime().get().v()),
							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0 : (!agreementTimeDetail.getAfterAppReflect().get().getExceptionLimitAlarmTime().isPresent() ? 0 :agreementTimeDetail.getAfterAppReflect().get().getExceptionLimitAlarmTime().get().v()),
							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0 : agreementTimeDetail.getAfterAppReflect().get().getStatus().value));
			data.add(agreementTimeList36);
		}
		
		data.sort((a, b) ->
			 {
				 if(a.getAfterAppReflect().getAgreementTime()== b.getAfterAppReflect().getAgreementTime()){
					 return a.getEmployeeCD().compareTo(b.getEmployeeCD());
				 }else{
					 return  b.getAfterAppReflect().getAgreementTime()-a.getAfterAppReflect().getAgreementTime();
				 }
			}
		);

		return new OvertimeHours(null, data);
	}

	public boolean displayItem(List<AgreementTimeDetail> listAgreementTimeDetail, GeneralDate targetMonth) {
		// �６協定申請を使用するかどぁ�チェヂ�(Check có sủ dụng đơn xin hiệp ddinh36 ko)
		// 一旦常に「使用する�mặc định la 「sử dungj - 使用する�)
		// �６協定申請を使用する権限があるかどぁ�チェヂ�(check xem có quyển sử dụng đơn hiệp định 36
		// ko) 一旦常に「使用する�mặc định la 「sử dungj - 使用する�)
		// 対象月が過去月かどぁ�チェヂ�(Check targetMonth có phải l� tháng quá khứ ko)
		for (AgreementTimeDetail agreeTime : listAgreementTimeDetail) {
			if (!GeneralDate.today().after(targetMonth) == true) {
				if (agreeTime.getAfterAppReflect().get().getStatus().value == 1 || agreeTime.getAfterAppReflect().get().getStatus().value == 2) {
					return true;
				}
				return true;
			} else
				return false;
		}
		return false;

	}

	public List<ClosureResultModel> getListClosure() {
		GeneralDate referenceDate = checkSysDateOrCloseEndDate();
		List<ClosureResultModel> listClosureResultModel = workClosureQueryProcessor.findClosureByReferenceDate(referenceDate);
		return listClosureResultModel;
	}

	// get request 335
	public List<String> getListEmpByWkpAndEmpt(List<String> wkpsId, List<String> lstemptsCode, DatePeriod dateperiod) {
		// lấy List workplace history items từ dateperiod and list workplaceId
		
		List<String> lstEmpIdOfWkp = overtimeHoursRepo.getAffWkpHistItemByListWkpIdAndDatePeriod(dateperiod, wkpsId);
		if (lstEmpIdOfWkp.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<String> lstEmpIdOfEmpt = overtimeHoursRepo.getListEmptByListCodeAndDatePeriod(dateperiod, lstemptsCode);
		if (lstEmpIdOfEmpt.isEmpty()) {
			return Collections.emptyList();
		}
		// lấy list sid chung từ 2 list lstEmpIdOfWkp vs lstEmpIdOfEmpt
		List<String> lstId = lstEmpIdOfWkp.stream().filter(lstEmpIdOfEmpt::contains).collect(Collectors.toList());

		if (lstId.isEmpty()) {
			return Collections.emptyList();
		}
		// lây list Employee từ list sid v� dateperiod
		long startTime2 = System.nanoTime();
		List<AffCompanyHist> lstAffComHist = overtimeHoursRepo.getAffComHisEmpByLstSidAndPeriod(lstId, dateperiod);
		long endTime2 = System.nanoTime();	
		long duration2 = (endTime2 - startTime2) / 1000000; // ms;
		System.out.println("Time113:" + duration2);		
		if (lstAffComHist.isEmpty()) {
			return Collections.emptyList();
		}
		List<AffCompanyHistByEmployee> lstAffComHistByEmp = getAffCompanyHistByEmployee(lstAffComHist);
		
		// List sid sau khi lọc qua điều kiện datePeriod
		List<String> lstSidAfterFilter = lstAffComHistByEmp.stream().map(m -> m.getSId()).collect(Collectors.toList());
		
		// List sid tồn tại �lstId nhưng không tồn tại �list sid
				List<String> result = lstId.stream().filter(i -> !lstSidAfterFilter.contains(i)).collect(Collectors.toList());
				if (result.isEmpty()) {
					return Collections.emptyList();
				}
			
		return result;
	}
	public List<AffCompanyHistByEmployee> getAffCompanyHistByEmployee(List<AffCompanyHist> lstAffComHist) {
		if (lstAffComHist.isEmpty()) {
			return Collections.emptyList();
		}
		List<AffCompanyHistByEmployee> result = new ArrayList<>();

		lstAffComHist.forEach(m -> {
			result.addAll(m.getLstAffCompanyHistByEmployee());
		});
		return result;
		
	}


	public OvertimeHoursDto initialActivationArocess(int targetMonth) {
		// 基準日を取得す�
		// (Lấy base date)
		GeneralDate referenceDate = checkSysDateOrCloseEndDate();
		// ログイン��めを取得す�
		// (Lấy closing của login)
		int closureIDInit = getLoginClosure(referenceDate);
		// �め�一覧を取得す�
		// (Lấy list closing) Request 142
		List<ClosureResultModel> listClosureResultModel = workClosureQueryProcessor.findClosureByReferenceDate(referenceDate);
		// 時間外労働時間を取得す�
		// (Lấy work time )
		OvertimeHours overtimeHours = buttonPressingProcess(targetMonth, 1);
		OvertimeHoursDto reusult = new OvertimeHoursDto(listClosureResultModel, overtimeHours);
		return reusult;
	}
}