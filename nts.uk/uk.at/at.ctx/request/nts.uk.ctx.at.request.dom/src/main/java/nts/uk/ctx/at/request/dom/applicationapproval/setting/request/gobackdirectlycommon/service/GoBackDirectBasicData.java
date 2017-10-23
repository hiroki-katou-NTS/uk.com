package nts.uk.ctx.at.request.dom.applicationapproval.setting.request.gobackdirectlycommon.service;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;

/**
 * 直行直帰基本データ
 * @author ducpm
 *
 */
@Getter
@Setter
public class GoBackDirectBasicData {
	Optional<GoBackDirectlyCommonSetting> goBackDirectSet;
	String employeeName;
	String sID;
	List<ApplicationReason> listAppReason;
	AppCommonSettingOutput appCommonSettingOutput;
	boolean isDutiesMulti;
}
