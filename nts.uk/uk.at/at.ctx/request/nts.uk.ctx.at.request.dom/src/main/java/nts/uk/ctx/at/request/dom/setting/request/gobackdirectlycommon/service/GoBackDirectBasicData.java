package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;

@Getter
@Setter
public class GoBackDirectBasicData {
	Optional<GoBackDirectlyCommonSetting> goBackDirectSet;
	List<ApplicationReason> listAppReason;
	String employeeName;
}
