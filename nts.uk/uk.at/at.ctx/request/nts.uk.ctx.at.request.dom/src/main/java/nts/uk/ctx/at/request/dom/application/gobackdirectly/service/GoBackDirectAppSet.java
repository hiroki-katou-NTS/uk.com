package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;

@Getter
@Setter
public class GoBackDirectAppSet {
	GoBackDirectly_Old goBackDirectly;
	int prePostAtr;
	String workLocationName1;
	String workLocationName2;
	String workTypeName;
	String workTimeName;
	String appReasonId;
	String appReason;
	String appDate;
	String employeeName;

	DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput;

	PrelaunchAppSetting prelaunchAppSetting;

	DetailScreenInitModeOutput detailScreenInitModeOutput;
	
}
