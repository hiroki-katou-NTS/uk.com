package nts.uk.ctx.hr.develop.dom.interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewRecordAvailability;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewRecordInfo;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewSummary;
import nts.uk.ctx.hr.develop.dom.interview.dto.SubInterviewInfor;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInforAdapter;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;

@Stateless
public class InterviewService {

	@Inject
	private InterviewRecordRepository interviewRecordRepository;
	
	@Inject
	private EmployeeInforAdapter employeeInforAdapter;
	
	// TODO: QA hỏi ngày lấy từ đâu, từ UI bắn lên đúng không hay ngày hiện tại
	public InterviewSummary getInterviewContents(String companyID, int interviewCate, List<String> listEmployeeID,
			boolean getSubInterview, boolean getDepartment, boolean getPosition, boolean getEmployment)
	{
		InterviewSummary result = new InterviewSummary();

		List<InterviewRecord> interviewRecords = interviewRecordRepository.getInterviewContents(companyID, interviewCate, listEmployeeID);
		interviewRecords = interviewRecords.stream().filter(distinctByKey(InterviewRecord::getMainInterviewerSid)).collect(Collectors.toList());
		List<String> interviewRecordMainInterviewerIds = interviewRecords.stream().map(c -> c.getMainInterviewerSid().v()).collect(Collectors.toList());
		
		// InterviewRecordAvailability
		List<InterviewRecordAvailability> listInterviewRecordAvailability = new ArrayList<InterviewRecordAvailability>();
		for (String employeeId : listEmployeeID) {
			boolean has = interviewRecordMainInterviewerIds.contains(employeeId);
			InterviewRecordAvailability interviewRecordAvailability = new InterviewRecordAvailability(employeeId, has);
			listInterviewRecordAvailability.add(interviewRecordAvailability);
		}
		result.listInterviewRecordAvailability = listInterviewRecordAvailability;
		
		// InterviewRecordInfo
		//アルゴリズム [社員の情報を取得する] を実行する (thực hiện thuật toán [lấy thông tin employee]) --- 
		EmployeeInfoQueryImport paramInterview = EmployeeInfoQueryImport.builder()
				.employeeIds(listEmployeeID)
				.referenceDate(GeneralDate.today()) // Xem lai date
				.toGetWorkplace(false)
				.toGetDepartment(getDepartment)
				.toGetPosition(getPosition)
				.toGetEmployment(getEmployment)
				.toGetClassification(false)
				.toGetEmploymentCls(false).build();
		List<EmployeeInformationImport> listInterviewEmployeeImport = employeeInforAdapter.find(paramInterview);
		List<InterviewRecordInfo> InterviewRecordInfos = createInterviewRecordInfo(paramInterview, interviewRecords, listInterviewEmployeeImport);
		result.listInterviewRecordInfo = InterviewRecordInfos;
		
		// SubInterviewInfor
		if (!getSubInterview) {
			InterviewRecordInfos.forEach(f -> f.setListSubInterviewer(Collections.emptyList()));
		} else {
			List<SubInterviewInfor> subInterviewInfors = interviewRecords.stream()
					.flatMap(record -> record.getListSubInterviewer().stream().map(subInterviewer -> {
										return new SubInterviewInfor(record.getInterviewRecordId(), subInterviewer.getSubInterviewerSid().v());
									})
							)
					.filter(distinctByKey(SubInterviewInfor::getSubInterviewerId))
					.collect(Collectors.toList());
			
			//アルゴリズム [社員の情報を取得する] を実行する (thực hiện thuật toán [lấy thông tin employee]) --- 
			EmployeeInfoQueryImport paramSubInterview = EmployeeInfoQueryImport.builder()
					.employeeIds(listEmployeeID)
					.referenceDate(GeneralDate.today()) // Xem lai date
					.toGetWorkplace(false)
					.toGetDepartment(getDepartment)
					.toGetPosition(getPosition)
					.toGetEmployment(getEmployment)
					.toGetClassification(false)
					.toGetEmploymentCls(false).build();
			List<EmployeeInformationImport> listEmployeeInformationImport = employeeInforAdapter.find(paramSubInterview);
			result.listSubInterviewInfor = createSubInterviewInfor(paramSubInterview, subInterviewInfors, listEmployeeInformationImport);
		}
		
		return result;
	}
		
	private List<InterviewRecordInfo> createInterviewRecordInfo(
			EmployeeInfoQueryImport param,
			List<InterviewRecord> interviewRecords,
			List<EmployeeInformationImport> listImport) {
		List<InterviewRecordInfo> listData = new ArrayList<>();	
		for(InterviewRecord itemData :interviewRecords){
			for(EmployeeInformationImport itemInfo : listImport){
				if(itemData.getMainInterviewerSid().v().equals(itemInfo.getEmployeeId())){
					// 面談記録情報リストを生成する
					String departmentCode = null;
					String departmentName = null;
					String positionCode = null;
					String positionName = null;
					String employmentCd = null;
					String employmentName = null;
					if (!param.isToGetDepartment()) {
						departmentCode = itemInfo.getDepartment().getDepartmentCode();
						departmentName = itemInfo.getDepartment().getDepartmentName();
					}
					if(!param.isToGetPosition()){
						positionCode = itemInfo.getPosition().getPositionCode();
						positionName = itemInfo.getPosition().getPositionName();
					}
					if(!param.isToGetEmployment()){
						employmentCd = itemInfo.getEmployment().getEmploymentCode();
						employmentName = itemInfo.getEmployment().getEmploymentName();		
					}
					listData.add(new InterviewRecordInfo(itemData.getIntervieweeSid().v(), itemData.getInterviewRecordId(), itemData.getInterviewDate(),
							itemData.getMainInterviewerSid().v(), itemInfo.getEmployeeCode(), itemInfo.getBusinessName(), itemInfo.getBusinessNameKana(), departmentCode, departmentName,
							positionCode, positionName, employmentCd, employmentName, itemData.getListSubInterviewer()));
					break;
				}
			}
		}
		return listData;
	}
	
	private List<SubInterviewInfor> createSubInterviewInfor(
			EmployeeInfoQueryImport param,
			List<SubInterviewInfor> listSubInterviewInfor,
			List<EmployeeInformationImport> listEmployeeInformationImport) {
		List<SubInterviewInfor> listData = new ArrayList<>();	
		for(SubInterviewInfor subInterviewInfor : listSubInterviewInfor){
			for(EmployeeInformationImport itemInfo : listEmployeeInformationImport){
				if(subInterviewInfor.getSubInterviewerId().equals(itemInfo.getEmployeeId())){
					// 面談記録情報リストを生成する
					String departmentCode = null;
					String departmentName = null;
					String positionCode = null;
					String positionName = null;
					String employmentCd = null;
					String employmentName = null;
					if (!param.isToGetDepartment()) {
						departmentCode = itemInfo.getDepartment().getDepartmentCode();
						departmentName = itemInfo.getDepartment().getDepartmentName();
					}
					if(!param.isToGetPosition()){
						positionCode = itemInfo.getPosition().getPositionCode();
						positionName = itemInfo.getPosition().getPositionName();
					}
					if(!param.isToGetEmployment()){
						employmentCd = itemInfo.getEmployment().getEmploymentCode();
						employmentName = itemInfo.getEmployment().getEmploymentName();		
					}
					subInterviewInfor.setEmployeeCd(subInterviewInfor.getSubInterviewerId());
					subInterviewInfor.setBusinessName(itemInfo.getBusinessName());
					subInterviewInfor.setBusinessnameKana(itemInfo.getBusinessNameKana());
					subInterviewInfor.setDepartmentCd(departmentCode);
					subInterviewInfor.setDepartmentName(departmentName);
					subInterviewInfor.setPositionCd(positionCode);
					subInterviewInfor.setPositionName(positionName);
					subInterviewInfor.setEmploymentCd(employmentCd);
					subInterviewInfor.setEmploymentName(employmentName);
					listData.add(subInterviewInfor);
					break;
				}
			}
		}
		return listData;
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
}
