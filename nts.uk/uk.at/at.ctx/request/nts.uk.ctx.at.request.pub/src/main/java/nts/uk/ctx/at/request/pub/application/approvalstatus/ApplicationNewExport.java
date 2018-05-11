package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Data
public class ApplicationNewExport {

	//申請ID
	private String applicationID;
	// 事前事後区分
	private int prePostAtr;
	// 入力日
	private GeneralDateTime inputDate;
	// 入力者
	private String enteredPersonID;
	// 反映情報
	private ReflectionInformation_NewDto reflectionInformation;
	// 差戻し理由
	private String reversionReason;
	// 申請日
	private GeneralDate appDate;
	//申請理由
	private String appReason;
	// 申請種類
	private int appType;
	// 申請者
	private String employeeID;
	// 申請終了日
	private Optional<GeneralDate> startDate;
	// 申請開始日
	private Optional<GeneralDate> endDate;
	
	public ApplicationNewExport(String applicationID, int prePostAtr, GeneralDateTime inputDate, String enteredPersonID, ReflectionInformation_NewDto reflectionInformation, String reversionReason, GeneralDate appDate, String appReason,
			int appType, String employeeID, Optional<GeneralDate> startDate, Optional<GeneralDate> endDate) {
		super();
		this.applicationID = applicationID;
		this.prePostAtr = prePostAtr;
		this.inputDate = inputDate;
		this.enteredPersonID = enteredPersonID;
		this.reflectionInformation = reflectionInformation;
		this.reversionReason = reversionReason;
		this.appDate = appDate;
		this.appReason = appReason;
		this.appType = appType;
		this.employeeID = employeeID;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}
