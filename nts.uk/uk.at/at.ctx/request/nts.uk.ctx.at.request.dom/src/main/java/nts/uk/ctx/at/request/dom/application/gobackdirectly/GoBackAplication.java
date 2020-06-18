package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;

@Getter
@Setter
public class GoBackAplication extends Application_New {
	public GoBackAplication(Long version, String companyID, String appID, PrePostAtr prePostAtr,
			GeneralDateTime inputDate, String enteredPersonID, AppReason reversionReason, GeneralDate appDate,
			AppReason appReason, ApplicationType appType, String employeeID, Optional<GeneralDate> startDate,
			Optional<GeneralDate> endDate, ReflectionInformation_New reflectionInformation) {
		super(version, companyID, appID, prePostAtr, inputDate, enteredPersonID, reversionReason, appDate, appReason, appType,
				employeeID, startDate, endDate, reflectionInformation);
	}
//	直帰区分
	private EnumConstant straightDistinction;
//	直行区分
	private EnumConstant straightLine;
//	勤務を変更する
	private Optional<EnumConstant> isChangedWork;
	
	private Optional<DataWork> dataWork;
	
//	申請内容
	public String getContent() {
		return null;
	}
	

}
