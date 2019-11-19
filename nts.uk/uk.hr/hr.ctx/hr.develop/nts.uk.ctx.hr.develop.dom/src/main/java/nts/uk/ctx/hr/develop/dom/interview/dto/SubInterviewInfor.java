package nts.uk.ctx.hr.develop.dom.interview.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.interview.SubInterviewer;
@Getter
@Setter
public class SubInterviewInfor {
	
	/** 面談記録ID --Interview record ID**/
	private String interviewRecordId;
	/** サブ面談者社員ID Sub Interviewer employee ID **/
	private  SubInterviewer listSubInterviewer;
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
}
