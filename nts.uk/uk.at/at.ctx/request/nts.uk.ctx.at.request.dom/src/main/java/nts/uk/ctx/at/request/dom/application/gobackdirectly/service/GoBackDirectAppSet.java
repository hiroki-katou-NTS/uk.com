package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;

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
}
