package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.dto.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.dto.User;

/**
 * 
 * @author hieult
 *
 */
public interface DetailScreenInitModeService {

	public DetailScreenInitModeOutput getDetailScreenInitMode(User user, int reflectPerState);

}
