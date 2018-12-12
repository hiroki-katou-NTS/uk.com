package nts.uk.ctx.workflow.dom.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
@Value
@AllArgsConstructor
public class PersonImport {
	/** 社員ID*/
	private String sID;
	/** 社員コード*/
	private String employeeCode;
	/** 社員名*/
	private String employeeName;
	
	/** 会社メールアドレス*/
	private String companyMail;
}
