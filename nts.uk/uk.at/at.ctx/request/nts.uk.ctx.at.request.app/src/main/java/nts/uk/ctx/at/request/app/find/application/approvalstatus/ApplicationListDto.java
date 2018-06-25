package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;

/**
 * 
 * @author Anh.BD
 *
 */

@Value
public class ApplicationListDto {
	private List<ApplicationDetailDto> listAppDetail;
	private List<AppCompltLeaveSync> lstAppCompltLeaveSync;
	boolean displayPrePostFlg;
}
