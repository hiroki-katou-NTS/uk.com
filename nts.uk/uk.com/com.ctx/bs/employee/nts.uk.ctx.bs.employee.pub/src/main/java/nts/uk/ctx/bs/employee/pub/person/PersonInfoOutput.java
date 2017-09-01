package nts.uk.ctx.bs.employee.pub.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class PersonInfoOutput {
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
