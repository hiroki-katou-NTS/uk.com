package nts.uk.ctx.at.request.dom.applicationapproval.application.gobackdirectly.service;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.applicationapproval.application.Application;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.other.output.OutputAllDataApp;
import nts.uk.ctx.at.request.dom.applicationapproval.application.gobackdirectly.GoBackDirectly;

@Getter
@Setter
public class GoBackDirectAppSet {
	GoBackDirectly goBackDirectly;
	int prePostAtr;
	String workLocationName1;
	String workLocationName2;
	String workTypeName;
	String workTimeName;
	String appReasonId;
	String appReason;
	String appDate;

	DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput;

	PrelaunchAppSetting prelaunchAppSetting;

	DetailScreenInitModeOutput detailScreenInitModeOutput;
	
}
