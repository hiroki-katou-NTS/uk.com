package nts.uk.ctx.at.request.dom.applicationapproval.application.stamp.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.stamp.StampRequestSetting;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
public class AppStampSetOutput {
	private StampRequestSetting stampRequestSetting;
	private List<ApplicationReason> applicationReasons;
}
