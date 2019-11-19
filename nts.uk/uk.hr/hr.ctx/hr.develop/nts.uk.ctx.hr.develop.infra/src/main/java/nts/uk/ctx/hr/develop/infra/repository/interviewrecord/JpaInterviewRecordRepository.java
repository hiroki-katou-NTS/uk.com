package nts.uk.ctx.hr.develop.infra.repository.interviewrecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.interview.EmployeeId;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecord;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecordRepository;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewRecordInfo;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInforAdapter;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
@Stateless
public class JpaInterviewRecordRepository extends JpaRepository implements InterviewRecordRepository{
	
	@Inject
	private InterviewRecordRepository interviewRecordRepository;
 
	@Inject
	private EmployeeInforAdapter employeeInforAdapter;
	
	@Override
	public List<InterviewRecord> getInterviewContents(String companyID, int interviewCate, List<String> listEmployeeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InterviewRecord> getByCategoryCIDAndListEmpID(String companyID, int interviewCategory,
			List<String> listEmployeeID, boolean department, boolean jobtitle, boolean employment) {
		//面談記録概要の取得
		
		//ドメインモデル [面談記録] を取得する - ドメインモデル [面談記録] を取得する ---Get the domain model [Interview Record]
		List<InterviewRecord> listInterView  = interviewRecordRepository.getInterviewContents(companyID, interviewCategory, listEmployeeID);
		//メイン面談者社員IDリストの重複を削除する  --- Remove duplicate main interviewer employee ID list 
		List<EmployeeId> listSid = listInterView.stream().map(c -> c.getIntervieweeSid()).collect(Collectors.toList());
		List<String> filterListSid = listSid.stream().map(c -> c.v()).distinct().collect(Collectors.toList());
		//var listID = listEmployeeID.stream().map(c -> c.Value()).collect(Collectors.toList());
		EmployeeInfoQueryImport param = EmployeeInfoQueryImport.builder()
				.employeeIds(filterListSid)
				.referenceDate(GeneralDate.today())               //Xem lai date
				.toGetWorkplace(false)
				.toGetDepartment(department)
				.toGetPosition(jobtitle)
				.toGetEmployment(employment)
				.toGetClassification(false)
				.toGetEmploymentCls(false).build();

		
		//アルゴリズム [社員の情報を取得する] を実行する(thực hiện thuật toán [lấy thông tin employee]) --- 
		List<EmployeeInformationImport> listImport = employeeInforAdapter.find(param);
		//[Output] ---メイン面談者情報リスト = List<社員情報> 
		//面談記録情報リストを生成する --- Tạo danh sách thông tin hồ sơ phỏng vấn
		List<InterviewRecordInfo> listRecordInfo = createInterviewRecordInfo(param, listInterView, listImport);
		if (listRecordInfo.isEmpty()) {
			
		} else {
			
		}
		
		
		
		
		return null;
	}

	

	private List<InterviewRecordInfo> createInterviewRecordInfo(EmployeeInfoQueryImport param, List<InterviewRecord> listInterView  , List<EmployeeInformationImport> listImport ){
		List<InterviewRecordInfo> listData = new ArrayList<>();	
		for(InterviewRecord itemData :listInterView){
			for(EmployeeInformationImport itemInfo : listImport){
				if(itemData.getMainInterviewerSid().v().equals(itemInfo.getEmployeeId())){
					// 面談記録情報リストを生成する
					String departmentCode = null;
					String departmentName = null;
					String positionCode = null;
					String positionName = null;
					String employmentCd = null;
					String employmentName =null;
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
							positionCode, positionName, employmentCd, employmentName,itemData.getListSubInterviewer()));
					break;
				}
			}
		}
		return listData;
	}
}
