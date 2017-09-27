package nts.uk.ctx.workflow.dom.approvermanagement.workroot.person;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
@Value
@AllArgsConstructor
public class PersonInforExportDto {
	/** 社員ID*/
	private String sID;
	/** 社員コード*/
	private String employeeCode;
	/** 社員名*/
	private String employeeName;
	/** 入社年月日*/
	private GeneralDate entryDate;
	/** 退職年月日*/
	private GeneralDate retireDate;
	/** 会社メールアドレス*/
	private String companyMail;
}
