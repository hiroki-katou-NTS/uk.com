package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApplicationMetaOutput {
	private String appID;
	private ApplicationType appType;
	private GeneralDate appDate;
}
