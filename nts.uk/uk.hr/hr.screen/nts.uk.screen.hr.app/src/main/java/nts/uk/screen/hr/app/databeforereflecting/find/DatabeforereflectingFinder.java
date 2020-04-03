package nts.uk.screen.hr.app.databeforereflecting.find;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.interview.service.IInterviewRecordSummary;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementInformation;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.service.RetirementInformationService;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class DatabeforereflectingFinder {

	@Inject
	private RetirementInformationService retirementInfoService;
	
	@Inject
	private EmployeeInformationAdaptor empInfoAdaptor;
	
	@Inject
	private IInterviewRecordSummary interview;
	
	// 1.起動する(Khời động)
	public DataBeforeReflectResultDto getDataBeforeReflect() {
		
		DataBeforeReflectResultDto resultDto = new DataBeforeReflectResultDto(new ArrayList<>(), new ArrayList<>());

		String cid = AppContexts.user().companyId();
		Integer workId = 1;
		List<String> listPid = new ArrayList<>();
		Optional<Boolean> includReflected = Optional.empty();
		Optional<String> sortByColumnName = Optional.of("date_01");
		Optional<String> orderType = Optional.of("asc");

		// アルゴリズム[退職者登録一覧の取得]を実行する (Thực hiện thuật toán "Get list đăng ký người nghỉ hưu")
		List<RetirementInformation> listRetirementInfo = retirementInfoService.getRetirementInfo(cid, workId, listPid,
				includReflected, sortByColumnName, orderType);
		
		if (listRetirementInfo.isEmpty()) {
			return new DataBeforeReflectResultDto(new ArrayList<>(), new ArrayList<>());
		}
		
		// 取得した退職者情報を[A221_4 退職者登録一覧]に移送する(Transfer  retiree information đã get vào  [A221_4 Retired employee registration list])
		List<RetiredEmployeeInfoResult> listRetiredEmployeeInfoResult= convertToDto(listRetirementInfo);
		
		resultDto.setRetiredEmployees(listRetiredEmployeeInfoResult);
		
		// アルゴリズム[社員情報リストの取得]を実行する(Thực hiện thuật toán [lấy list thông tin nhân viên])
		List<EmployeeInformationImport> employeeImports = getEmpInfo(listRetiredEmployeeInfoResult);
		
		resultDto.setEmployeeImports(employeeImports);
		
		return resultDto;

	}
	/**
	 * 社員情報リストの取得
	 * @param retirementInfos
	 * @return
	 */
	private List<EmployeeInformationImport> getEmpInfo(List<RetiredEmployeeInfoResult> retirementInfos) {
		List<String> sIDs = retirementInfos.stream().map(x -> x.getSId()).collect(Collectors.toList());
		List<String> pIDs = retirementInfos.stream().map(x -> x.getPId()).collect(Collectors.toList());
		GeneralDate baseDate = GeneralDate.today();

		return this.empInfoAdaptor.getEmployeeInfos(Optional.of(pIDs), sIDs, baseDate, Optional.ofNullable(true),
				Optional.ofNullable(true), Optional.ofNullable(true));
	}

	// 起動する khởi động màn Z
	public DataBeforeReflectResultDto startPageZ() {
		String cID = AppContexts.user().companyId();

		List<String> listSid = Arrays.asList(AppContexts.user().employeeId());

		Optional<Boolean> includReflected = Optional.of(true);

		// アルゴリズム[承認データの取得]を実行する(Thực hiện thuật toán [lấy ApprovalData])
		List<RetiredEmployeeInfoResult> retirementInfos = convertToDto(getApprovalData(cID, listSid, includReflected));

		if (retirementInfos.isEmpty()) {
			throw new BusinessException("MsgJ_JCM007_11");
		}

		// アルゴリズム[社員情報リストの取得]を実行する(Thực hiện thuật toán [Lấy EmployeeInfoList])
		List<EmployeeInformationImport> employeeImports = getEmpInfo(retirementInfos);

		// アルゴリズム[面談記録概要の取得]を実行する(Thực hiện thuật toán [Lấy
		// InterviewRecordOverview])

		int interviewCate = 1;

		boolean getSubInterviewer = false;
		boolean getDepartment = true;
		boolean getPosition = true;
		boolean getEmployment = true;

		this.interview.getInterviewInfo(cID, interviewCate, listSid, getSubInterviewer, getDepartment, getPosition,
				getEmployment);

		return DataBeforeReflectResultDto.builder().retiredEmployees(retirementInfos).employeeImports(employeeImports)
				.build();
	}

	private List<RetirementInformation> getApprovalData(String cID, List<String> listSid, Optional<Boolean> includReflected) {
		
		Optional<String> sortByColumnName = Optional.of("date_01");
		Optional<String> orderType = Optional.of("asc");
		int workId = 1;

		return this.retirementInfoService.getRetirementInfo(cID, workId, listSid, includReflected, sortByColumnName,
				orderType);
	}

	// 退職日変更時処理(xử lý khi ngày nghỉ hưu thay đổi)
	public RetirementRelartedDto ProcessRetirementDateChanges(GeneralDate resignmentDate) {
		if (resignmentDate == null) {
			return RetirementRelartedDto.builder().build();
		}

		// アルゴリズム[退職関連情報の取得]を実行する(Thực hiện thuật toán [lấy thông tin liên quan
		// đến nghỉ hưu])

		return getRetirementRelatedInfor();

	}

	private RetirementRelartedDto getRetirementRelatedInfor() {
		return null;
		// TODO Auto-generated method stub

	}

	private List<RetiredEmployeeInfoResult> convertToDto(List<RetirementInformation> listRetirementInfo ) {

		List<RetiredEmployeeInfoResult> result = listRetirementInfo.stream().map(i -> {
			RetiredEmployeeInfoResult obj = new RetiredEmployeeInfoResult();
			obj.historyId = i.historyId;
			obj.contractCode = i.contractCode;
			obj.companyId = i.companyId;
			obj.companyCode = i.companyCode;
			obj.workId = i.workId;
			obj.workName = i.workName;
			obj.inputDate = i.inputDate;
			obj.pendingFlag = i.pendingFlag;
			obj.histId_Refer = i.histId_Refer;
			obj.releaseDate = i.releaseDate;
			obj.pId = i.pId;
			obj.sId = i.sId;
			obj.scd = i.scd;
			obj.employeeName = i.personName;
			obj.retirementDate = i.retirementDate;
			obj.retirementCategory = i.retirementCategory.value;
			obj.retirementReasonCtg1 = Integer.valueOf(i.retirementReasonCtgCode1);
			obj.retirementReasonCtgName1 = i.retirementReasonCtgName1;
			obj.retirementReasonCtg2 = Integer.valueOf(i.retirementReasonCtgCode2);
			obj.retirementReasonCtgName2 = i.retirementReasonCtgName2;
			obj.retirementRemarks = i.retirementRemarks;
			obj.retirementReasonVal = i.retirementReasonVal;
			obj.dismissalNoticeDate = i.dismissalNoticeDate;
			obj.dismissalNoticeDateAllow = i.dismissalNoticeDateAllow;
			obj.reaAndProForDis = i.reaAndProForDis;
			obj.naturalUnaReasons_1 = i.naturalUnaReasons_1;
			obj.naturalUnaReasons_2 = i.naturalUnaReasons_2;
			obj.naturalUnaReasons_3 = i.naturalUnaReasons_3;
			obj.naturalUnaReasons_4 = i.naturalUnaReasons_4;
			obj.naturalUnaReasons_5 = i.naturalUnaReasons_5;
			obj.naturalUnaReasons_6 = i.naturalUnaReasons_6;
			obj.naturalUnaReasons_1Val = i.naturalUnaReasons_1Val;
			obj.naturalUnaReasons_2Val = i.naturalUnaReasons_2Val;
			obj.naturalUnaReasons_3Val = i.naturalUnaReasons_3Val;
			obj.naturalUnaReasons_4Val = i.naturalUnaReasons_4Val;
			obj.naturalUnaReasons_5Val = i.naturalUnaReasons_5Val;
			obj.naturalUnaReasons_6Val = i.naturalUnaReasons_6Val;
			if (i.status == 0) {
				obj.status = "";
			} else if (i.status == 1) {
				obj.status = TextResource.localize("JCM007_A3_2");
			} else if (i.status == 2) {
				obj.status = TextResource.localize("JCM007_A3_3");
			}
			
			if (i.notificationCategory == 0) {
				obj.notificationCategory = "";
			} else if (i.notificationCategory == 1) {
				obj.notificationCategory = TextResource.localize("JCM007_A3_1");
			}
			
			return obj;
		}).collect(Collectors.toList());

		return result;
	}

}
