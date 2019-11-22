package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;

@Getter
@AllArgsConstructor
public class ApplicationDataOutput {

	private Long version;
	//申請ID
	private String applicationID;
	//事前事後区分
	private Integer prePostAtr; 
	//入力日
	private String inputDate; 
	//入力者
	private String enteredPersonSID;
	//申請日
	private String applicationDate; 
	//申請種類
	private Integer applicationType;
	//申請者
	private String  applicantSID;
	//反映状態
	private String reflectStatus;
	
	private String startDate;
	
	private String endDate;
	
	private Integer reflectPerState;
	
	public static ApplicationDataOutput convert(ApplicationDto_New app, String reflectStatus) {
		return new ApplicationDataOutput(app.getVersion(), app.getApplicationID(), app.getPrePostAtr(),
				app.getInputDate(), app.getEnteredPersonSID(), app.getApplicationDate(), app.getApplicationType(),
				app.getApplicantSID(), reflectStatus, app.getStartDate(), app.getEndDate(), app.getReflectPerState());
	}
}
