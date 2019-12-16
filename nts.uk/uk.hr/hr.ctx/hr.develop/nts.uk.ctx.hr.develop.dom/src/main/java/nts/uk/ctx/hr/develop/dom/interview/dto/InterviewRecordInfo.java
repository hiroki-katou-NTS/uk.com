package nts.uk.ctx.hr.develop.dom.interview.dto;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class InterviewRecordInfo {
	/** 社員ID **/
	private String employeeID; 
	/** 面談記録ID **/
	private String interviewRecordId;
	/** 面談日**/
	private GeneralDate interviewDate;
	/** メイン面談者社員ID **/
	private String mainInterviewerEmployeeID;
	/** 社員コード **/
	private String employeeCD ; 
	/** ビジネスネーム **/
	private String businessName;
	/** ビジネスネームカナ **/
	private String businessNameKana;
	/** 部門コード **/
	private Optional<String> departmentCd;
	/** 部門表示名 **/
	private Optional<String> departmentDisplayName;
	/** 職位コード **/
	private Optional<String> positionCd;
	/** 職位名称 **/
	private Optional<String> positionName;
	/** 雇用コード  **/
	private Optional<String> employmentCd;
	/** 雇用名称 **/
	private Optional<String> employmentName;

	public void setDataByDepartment(boolean hasDepartment){
		if(!hasDepartment){
		 this.departmentCd = Optional.empty();
		 this.departmentDisplayName = Optional.empty();
		}
	} 
	public void setDataByPosition(boolean hasPosition){
		if(!hasPosition){
			 this.positionCd = Optional.empty();
			 this.positionName = Optional.empty();
		}
	} 
	public void setDataByEmployment(boolean employment){
		if(!employment){
			 this.employmentCd = Optional.empty();
			 this.employmentName = Optional.empty();
		}
	}
	
	public InterviewRecordInfo(String employeeID, String interviewRecordId, GeneralDate interviewDate,
			String mainInterviewerEmployeeID, String employeeCD, String businessName,
			String businessNameKana, String departmentCd, String departmentDisplayName,
			String positionCd, String positionName,String employmentCd,
			String employmentName) {
		super();
		this.employeeID = employeeID;
		this.interviewRecordId = interviewRecordId;
		this.interviewDate = interviewDate;
		this.mainInterviewerEmployeeID = mainInterviewerEmployeeID;
		this.employeeCD = employeeCD;
		this.businessName = businessName;
		this.businessNameKana = businessNameKana;
		this.departmentCd =  Optional.ofNullable(departmentCd);
		this.departmentDisplayName =  Optional.ofNullable(departmentDisplayName);
		this.positionCd =  Optional.ofNullable(positionCd);
		this.positionName =  Optional.ofNullable(positionName);
		this.employmentCd =  Optional.ofNullable(employmentCd);
		this.employmentName =  Optional.ofNullable(employmentName);
	}
	
	
	
}
