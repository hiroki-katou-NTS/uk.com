package nts.uk.ctx.hr.develop.dom.interview.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecord;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecordRepository;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewRecordInfo;
import nts.uk.ctx.hr.develop.dom.interview.dto.SubInterviewInfor;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;

@Stateless
public class InterviewRecordSummaryImpl implements IInterviewRecordSummary {

	@Inject
	private InterviewRecordRepository interviewRecordRepository;
	
	@Inject
	private EmployeeInformationAdaptor employeeInforAdapter;
	
	/**
	 * 面談記録概要の取得
	 * @param companyID
	 * @param interviewCate
	 * @param listEmployeeID
	 * @param getSubInterview
	 * @param getDepartment
	 * @param getPosition
	 * @param getEmployment
	 * @return
	 */
	@Override
	public InterviewSummary getInterviewInfo(String companyID, int interviewCate, List<String> listEmployeeID,
						boolean getSubInterview, boolean getDepartment, boolean getPosition, boolean getEmployment)	{
		
		//ドメインモデル [面談記録] を取得する (Lấy domain "InterviewRecord")
		OutputOfGetInterviewRecord output_GetInterviewRecord = this.getInterviewRecord(companyID, interviewCate, listEmployeeID);
				
		//メイン面談者社員IDリストの重複を削除する (Xóa mainInterviewerSid List/ danh sách ID nhân viên phỏng vấn chính bị trùng lặp)
		List<String> listMainInterview = this.getMainInterviewDistinct(output_GetInterviewRecord.getListSimpleInterview());
		
		//アルゴリズム [社員の情報を取得する] を実行する (thực hiện thuật toán [lấy thông tin employee]) --- 
		EmployeeInfoQueryImport paramInterview = EmployeeInfoQueryImport.builder()
				.employeeIds(listMainInterview)
				.referenceDate(GeneralDate.today()) 
				.toGetWorkplace(false)
				.toGetDepartment(getDepartment)
				.toGetPosition(getPosition)
				.toGetEmployment(getEmployment)
				.toGetClassification(false)
				.toGetEmploymentCls(false).build();
		List<EmployeeInformationImport> listInterviewEmployeeImport = employeeInforAdapter.find(paramInterview);
		
		listInterviewEmployeeImport.forEach(li -> {
			System.out.println(li.getDepartment() + " li.sid ->>> " + li.getEmployeeId());
		});
		
		//面談記録情報リストを生成する(Tạo  Main interviewer information list)
		List<InterviewRecordInfo> listMainInterviewInfo =  this.createInterviewRecordInfoList(
										output_GetInterviewRecord.getListSimpleInterview(),
										listInterviewEmployeeImport);
		
		if (!getSubInterview) {
			return new InterviewSummary(output_GetInterviewRecord.getListAvailable(),
					listMainInterviewInfo,
					new ArrayList<>());
		} else {
			//ドメインモデル [面談記録] を取得する(Get the domain model [Interview Record])
			List<SimpleSubInterview> listSimpleSub = new ArrayList<>();
			
			Set<String> subInterviewSID = new HashSet<>();
			output_GetInterviewRecord.getListSimpleInterview().forEach(s -> {
				listSimpleSub.addAll(s.getSubInterviewSids().stream().map(sub -> {
					return new SimpleSubInterview(s.getInterviewRecordId(), sub);
				}).collect(Collectors.toList()));
				
				//Cái Set này đảm bảo các item là duy nhất
				//サブ面談者社員IDリストの重複を削除する(Remove duplicates of sub interviewer employee ID list)
				subInterviewSID.addAll(s.getSubInterviewSids());
			});
			
			//アルゴリズム [社員の情報を取得する] を実行する (thực hiện thuật toán [lấy thông tin employee]) --- 
			EmployeeInfoQueryImport paramSubInterview = EmployeeInfoQueryImport.builder()
					.employeeIds(new ArrayList<String>(subInterviewSID))
					.referenceDate(GeneralDate.today()) 
					.toGetWorkplace(false)
					.toGetDepartment(getDepartment)
					.toGetPosition(getPosition)
					.toGetEmployment(getEmployment)
					.toGetClassification(false)
					.toGetEmploymentCls(false).build();
			List<EmployeeInformationImport> listSubInterviewEmployeeImport = employeeInforAdapter.find(paramSubInterview);
			
			//サブ面談者情報リストを生成する(Generate a sub interviewer information list)
			List<SubInterviewInfor> listSubInterviewInfo =  this.createSubInterviewRecordInfoList(
					 											listSimpleSub, listSubInterviewEmployeeImport);
			
			return new InterviewSummary(
					output_GetInterviewRecord.getListAvailable(),
					listMainInterviewInfo,
					listSubInterviewInfo);
		}
	}
	
	//** 面談記録情報リストを生成する(Tạo  Main interviewer information list) */
	private List<InterviewRecordInfo> createInterviewRecordInfoList(
											List<SimpleInterviewRecord> listSimpleInterview,
											List<EmployeeInformationImport> listMainInterview){
		List<InterviewRecordInfo> result = new ArrayList<>();
		
		listMainInterview.forEach(i -> {
			SimpleInterviewRecord simple = 
					listSimpleInterview.stream().filter(s -> i.getEmployeeId().equals(s.getMainInterviewerSid())).findFirst().get();
			/**
			 * 面談記録情報リスト {社員ID、面談記録ID、面談日、メイン面談者社員ID、社員コード、ビジネスネーム、ビジネスネームカナ、部門コード、部門表示名、職位コード、職位名称、雇用コード、雇用名称}
			 */
			InterviewRecordInfo info = new InterviewRecordInfo(simple.getEmployeeID(), simple.getInterviewRecordId(),
					simple.getInterviewDate(),
					simple.getMainInterviewerSid(), 
					i.getEmployeeCode(), 
					i.getBusinessName(),
					i.getBusinessNameKana(), 
					i.getDepartment() == null ? "" : i.getDepartment().getDepartmentCode(), 
				    i.getDepartment() == null ? "" : i.getDepartment().getDepartmentDisplayName(),
		    		i.getPosition() == null ? "" : i.getPosition().getPositionCode(),		
					i.getPosition() == null ? "" : i.getPosition().getPositionName(),
					i.getEmployment() == null ? "" : i.getEmployment().getEmploymentCode(),		
					i.getEmployment() == null ? "" : i.getEmployment().getEmploymentName());
			
			result.add(info);
		});
		
		return result;
	}
	
	//サブ面談者情報リストを生成する(Generate a sub interviewer information list)
	private List<SubInterviewInfor> createSubInterviewRecordInfoList(
						List<SimpleSubInterview> listSimpleSub,
						List<EmployeeInformationImport> listSubInterviewInfo){
		List<SubInterviewInfor> result = new ArrayList<>();
		
		listSubInterviewInfo.forEach(i -> {
			SimpleSubInterview simpleSub = 
					listSimpleSub.stream().filter(s -> i.getEmployeeId().equals(s.getSubInterviewSID())).findFirst().get();
			
			SubInterviewInfor info = new SubInterviewInfor(simpleSub.getInterviewRecordID(),
					simpleSub.getSubInterviewSID(),
					i.getEmployeeCode(), 
					i.getBusinessName(),
					i.getBusinessNameKana(), 
					i.getDepartment() == null ? "" : i.getDepartment().getDepartmentCode(), 
				    i.getDepartment() == null ? "" : i.getDepartment().getDepartmentDisplayName(),
		    		i.getPosition() == null ? "" : i.getPosition().getPositionCode(),		
					i.getPosition() == null ? "" : i.getPosition().getPositionName(),
					i.getEmployment() == null ? "" : i.getEmployment().getEmploymentCode(),		
					i.getEmployment() == null ? "" : i.getEmployment().getEmploymentName());
			
			result.add(info);
		});
		
		return result;
	}
	
		
	/**
	 * ドメインモデル [面談記録] を取得する (Lấy domain "InterviewRecord")
	 * @param companyID
	 * @param interviewCate
	 * @param listEmployeeID
	 * @return
	 */
	private OutputOfGetInterviewRecord getInterviewRecord(String companyID, int interviewCate, List<String> listEmployeeID) {
		OutputOfGetInterviewRecord output = new OutputOfGetInterviewRecord();
		
		List<InterviewRecord> interviewRecords = interviewRecordRepository.getInterviewRecords(companyID, interviewCate, listEmployeeID);
		
		List<InterviewRecordAvailability> listAvailable = new ArrayList<InterviewRecordAvailability>();
		for (String employeeId : listEmployeeID) {
			boolean has = interviewRecords.stream().filter(i -> i.getIntervieweeSid().v().equals(employeeId)).findFirst().isPresent();
			InterviewRecordAvailability interviewRecordAvailability = new InterviewRecordAvailability(employeeId, has);
			listAvailable.add(interviewRecordAvailability);
		}
		
		List<SimpleInterviewRecord> listSimpleInterview = interviewRecords.stream().map(c -> {
			return new SimpleInterviewRecord(
					c.getIntervieweeSid().v(),
					c.getInterviewRecordId(),
					c.getInterviewDate(),
					c.getMainInterviewerSid().v(),
					c.getListSubInterviewer().stream().map(s -> s.getSubInterviewerSid().v()).collect(Collectors.toList())
					);
		}).collect(Collectors.toList());
		
		output.setListAvailable(listAvailable);
		output.setListSimpleInterview(listSimpleInterview);
		
		return output;
	}

	/** メイン面談者社員IDリストの重複を削除する (Xóa mainInterviewerSid List/ danh sách ID nhân viên phỏng vấn chính bị trùng lặp) */
	private List<String> getMainInterviewDistinct(List<SimpleInterviewRecord> listSimpleInterview){
		return listSimpleInterview.stream().map(c -> c.getMainInterviewerSid()).distinct().collect(Collectors.toList());
	}
	
// 	private List<InterviewRecordInfo> createInterviewRecordInfo(
//			EmployeeInfoQueryImport param,
//			List<InterviewRecord> interviewRecords,
//			List<EmployeeInformationImport> listImport) {
//		List<InterviewRecordInfo> listData = new ArrayList<>();	
//		for(InterviewRecord itemData :interviewRecords){
//			for(EmployeeInformationImport itemInfo : listImport){
//				if(itemData.getMainInterviewerSid().v().equals(itemInfo.getEmployeeId())){
//					// 面談記録情報リストを生成する
//					String departmentCode = null;
//					String departmentName = null;
//					String positionCode = null;
//					String positionName = null;
//					String employmentCd = null;
//					String employmentName = null;
//					if (!param.isToGetDepartment()) {
//						departmentCode = itemInfo.getDepartment().getDepartmentCode();
//						departmentName = itemInfo.getDepartment().getDepartmentName();
//					}
//					if(!param.isToGetPosition()){
//						positionCode = itemInfo.getPosition().getPositionCode();
//						positionName = itemInfo.getPosition().getPositionName();
//					}
//					if(!param.isToGetEmployment()){
//						employmentCd = itemInfo.getEmployment().getEmploymentCode();
//						employmentName = itemInfo.getEmployment().getEmploymentName();		
//					}
//					listData.add(new InterviewRecordInfo(itemData.getIntervieweeSid().v(), itemData.getInterviewRecordId(), itemData.getInterviewDate(),
//							itemData.getMainInterviewerSid().v(), itemInfo.getEmployeeCode(), itemInfo.getBusinessName(), itemInfo.getBusinessNameKana(), departmentCode, departmentName,
//							positionCode, positionName, employmentCd, employmentName, itemData.getListSubInterviewer()));
//					break;
//				}
//			}
//		}
//		return listData;
//	}
	
//	private List<SubInterviewInfor> createSubInterviewInfor(
//			EmployeeInfoQueryImport param,
//			List<SubInterviewInfor> listSubInterviewInfor,
//			List<EmployeeInformationImport> listEmployeeInformationImport) {
//		List<SubInterviewInfor> listData = new ArrayList<>();	
//		for(SubInterviewInfor subInterviewInfor : listSubInterviewInfor){
//			for(EmployeeInformationImport itemInfo : listEmployeeInformationImport){
//				if(subInterviewInfor.getSubInterviewerId().equals(itemInfo.getEmployeeId())){
//					// 面談記録情報リストを生成する
//					String departmentCode = null;
//					String departmentName = null;
//					String positionCode = null;
//					String positionName = null;
//					String employmentCd = null;
//					String employmentName = null;
//					if (!param.isToGetDepartment()) {
//						departmentCode = itemInfo.getDepartment().getDepartmentCode();
//						departmentName = itemInfo.getDepartment().getDepartmentName();
//					}
//					if(!param.isToGetPosition()){
//						positionCode = itemInfo.getPosition().getPositionCode();
//						positionName = itemInfo.getPosition().getPositionName();
//					}
//					if(!param.isToGetEmployment()){
//						employmentCd = itemInfo.getEmployment().getEmploymentCode();
//						employmentName = itemInfo.getEmployment().getEmploymentName();		
//					}
//					subInterviewInfor.setEmployeeCd(subInterviewInfor.getSubInterviewerId());
//					subInterviewInfor.setBusinessName(itemInfo.getBusinessName());
//					subInterviewInfor.setBusinessnameKana(itemInfo.getBusinessNameKana());
//					subInterviewInfor.setDepartmentCd(departmentCode);
//					subInterviewInfor.setDepartmentName(departmentName);
//					subInterviewInfor.setPositionCd(positionCode);
//					subInterviewInfor.setPositionName(positionName);
//					subInterviewInfor.setEmploymentCd(employmentCd);
//					subInterviewInfor.setEmploymentName(employmentName);
//					listData.add(subInterviewInfor);
//					break;
//				}
//			}
//		}
//		return listData;
//	}
//
//	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
//	    Set<Object> seen = ConcurrentHashMap.newKeySet();
//	    return t -> seen.add(keyExtractor.apply(t));
//	}
	
}
