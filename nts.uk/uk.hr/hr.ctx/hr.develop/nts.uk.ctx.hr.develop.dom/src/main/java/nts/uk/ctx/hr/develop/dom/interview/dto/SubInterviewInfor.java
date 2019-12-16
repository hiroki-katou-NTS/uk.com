package nts.uk.ctx.hr.develop.dom.interview.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SubInterviewInfor {
	
	/** 面談記録ID --Interview record ID**/
	private String interviewRecordId;
	/** サブ面談者社員ID Sub Interviewer employee ID **/
	private String subInterviewerId;
	/** 社員コード **/
	private String employeeCd;
	/**ビジネスネーム **/
	private String businessName;
	/** ビジネスネームカナ**/
	private String businessnameKana;
	/** 部門コード **/
	private String departmentCd;
	/** 部門表示名 **/
	private String departmentName;
	/** 職位コード**/
	private String positionCd;
	/** 職位名称 **/
	private String positionName;
	/** 雇用コード **/
	private String employmentCd;
	/** 雇用名称 **/
	private String employmentName;
	
	public SubInterviewInfor(String interviewRecordId, String subInterviewerId)
	{
		super();
		this.interviewRecordId = interviewRecordId;
		this.subInterviewerId = subInterviewerId;
	}
	
	public SubInterviewInfor(String interviewRecordId, String subInterviewerId, String employeeCd, String businessName,
			String businessnameKana, String departmentCd, String departmentName, String positionCd, String positionName,
			String employmentCd, String employmentName) {
		super();
		this.interviewRecordId = interviewRecordId;
		this.subInterviewerId = subInterviewerId;
		this.employeeCd = employeeCd;
		this.businessName = businessName;
		this.businessnameKana = businessnameKana;
		this.departmentCd = departmentCd;
		this.departmentName = departmentName;
		this.positionCd = positionCd;
		this.positionName = positionName;
		this.employmentCd = employmentCd;
		this.employmentName = employmentName;
	}
	
}
