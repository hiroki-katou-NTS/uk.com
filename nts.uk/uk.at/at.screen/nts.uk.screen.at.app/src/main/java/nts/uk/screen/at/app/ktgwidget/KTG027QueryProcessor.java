package nts.uk.screen.at.app.ktgwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.auth.dom.adapter.AuthWorkPlaceAdapter;
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
import nts.uk.screen.at.app.ktgwidget.find.dto.AgreementTimeList36;
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

	
	public GeneralDate checkSysDateOrCloseEndDate() {
		// EA luôn trả về Systemdate
		return GeneralDate.today();
	}

	public int getLoginClosure(GeneralDate referenceDate) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// ログイン者の雇用を取得する
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

	public List<DatePeriod> getDatePeriod(int targetMonth, int closureID) {
		String companyID = AppContexts.user().companyId();
		List<DatePeriod> listDatePeriod = new ArrayList<>();
		Optional<Closure> optClosure = closureRepository.findByIdAndUseAtr(companyID, closureID, UseClassification.UseClass_Use);
		if (optClosure.isPresent())  
			listDatePeriod = optClosure.get().getPeriodByYearMonth(YearMonth.of(targetMonth));
		return listDatePeriod;
	}

	public OvertimeHours buttonPressingProcess(int targetMonth, int closureID) {
		
		String employeeID = AppContexts.user().employeeId();
		String companyID = AppContexts.user().companyId();
		// 締め期間を取得する
		// (Lấy closing period) tu domain Closure
		/*
		 * ouput: Closing period start date End date
		 */
		List<DatePeriod> listDatePeriod = getDatePeriod(targetMonth, closureID);
		if (listDatePeriod.isEmpty()) {
			throw new BusinessException("Msg_1134");
		}
		// ログイン者の権限範囲内の職場を取得する
		// (Lấy workplace trong phạm vi quyền login) = Request List 334
		// referenceDate lấy theo baseDate ở xử lý 1
		GeneralDate referenceDate = checkSysDateOrCloseEndDate();
		List<String> listWorkPlaceID = authWorkPlaceAdapter.getListWorkPlaceID(employeeID, referenceDate);
		if (listWorkPlaceID.isEmpty()) {
			throw new BusinessException("Msg_1135");
		}
		// 締めに紐づく雇用を取得する
		// (lấy employeement ứng với closing)
		List<ClosureEmployment> listClosureEmployment = closureEmploymentRepo.findByClosureId(companyID, closureID);
		if (listClosureEmployment.isEmpty()) {
			throw new BusinessException("Msg_1136");
		}
		// 対象者を取得する
		List<String> listEmploymentCD = listClosureEmployment.stream().map(c -> c.getEmploymentCD()).collect(Collectors.toList());
		// (Lấy target) Lấy request list 335
		// imported（権限管理）「所属職場履歴」を取得する
		List<AgreementTimeList36> data = new ArrayList<>();
		for (DatePeriod datePeriod : listDatePeriod) {
			List<String> listEmpID = empEmployeeAdapter.getListEmpByWkpAndEmpt(listWorkPlaceID, listEmploymentCD, datePeriod);
			if (listEmpID.isEmpty()) {
				throw new BusinessException("Msg_1137");
			}
			// 対象者の時間外労働時間を取得する
			// (Lấy worktime ngoài period) lấy RequestList 333
			List<AgreementTimeDetail> listAgreementTimeDetail = getAgreementTime.get(companyID, listEmpID, YearMonth.of(targetMonth), ClosureId.valueOf(closureID));
			OvertimeHours overTime = new OvertimeHours();
			if (!listAgreementTimeDetail.isEmpty()) {
				// 取得した時間外労働情報をセット
				// (Set thông tin công việc ngoài giờ đã lấy)
					for(AgreementTimeDetail agreementTimeDetail :listAgreementTimeDetail){
						AgreementTimeList36 agreementTimeList36 = new AgreementTimeList36(
								agreementTimeDetail.getEmployeeId(),
								null,
								agreementTimeDetail.getConfirmed(),
								agreementTimeDetail.getAfterAppReflect()
								);
						data.add(agreementTimeList36);
					}
				List<String> lstEmpID = listAgreementTimeDetail.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());	
					// Imported（就業）「社員」を取得する
					// (Lấy Imported（就業）「employee」)	 Lay Request61
			List<PersonEmpBasicInfoImport> listEmpBasicInfoImport = empEmployeeAdapter.getPerEmpBasicInfo(lstEmpID); 	
			 overTime = new OvertimeHours(Optional.of("Msg_1138"), Optional.of(data));
			}
			return overTime;
			
		}

		return new OvertimeHours(Optional.of("asdsadas"),Optional.of(data));
	}

	public boolean displayItem(List<AgreementTimeDetail> listAgreementTimeDetail ,GeneralDate targetMonth){
		//３６協定申請を使用するかどうかチェック(Check có sủ dụng đơn xin hiệp ddinh36 ko) 一旦常に「使用する」(mặc định la 「sử  dungj - 使用する」 )
		//３６協定申請を使用する権限があるかどうかチェック(check xem có quyển sử dụng đơn hiệp định 36 ko) 一旦常に「使用する」(mặc định la 「sử  dungj - 使用する」 )
		//対象月が過去月かどうかチェック(Check targetMonth có phải là tháng quá khứ ko)
		for (AgreementTimeDetail agreeTime : listAgreementTimeDetail ){
			if(!GeneralDate.today().after(targetMonth) == true){
				if(agreeTime.getAfterAppReflect().getStatus().value == 1 || agreeTime.getAfterAppReflect().getStatus().value == 2){
					return true;
				}
				return true;
			}
			else 
				return false;
		}
		return false;
		
	}
	public List<ClosureResultModel> getListClosure(){
		GeneralDate referenceDate = checkSysDateOrCloseEndDate();
		List<ClosureResultModel> listClosureResultModel = workClosureQueryProcessor.findClosureByReferenceDate(referenceDate);
		return listClosureResultModel;
	}
	public OvertimeHoursDto initialActivationArocess(int targetMonth) {
		// 基準日を取得する
		// (Lấy base date)
		GeneralDate referenceDate = checkSysDateOrCloseEndDate();
		// ログイン者の締めを取得する
		// (Lấy closing của login)
		int closureIDInit = getLoginClosure(referenceDate);
		// 締めの一覧を取得する
		// (Lấy list closing) Request 142
		List<ClosureResultModel> listClosureResultModel = workClosureQueryProcessor.findClosureByReferenceDate(referenceDate);
		// 時間外労働時間を取得する
		// (Lấy work time )
		OvertimeHours overtimeHours = buttonPressingProcess(targetMonth, 1);
		OvertimeHoursDto reusult = new OvertimeHoursDto(closureIDInit, overtimeHours);
		return null;
	}
}