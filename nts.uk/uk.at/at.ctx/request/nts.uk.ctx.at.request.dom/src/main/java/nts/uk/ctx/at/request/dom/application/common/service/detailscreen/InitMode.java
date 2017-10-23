package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

/**
 * 
 * @author hieult
 *
 */
public interface InitMode {

	public DetailScreenInitModeOutput getDetailScreenInitMode(User user, int reflectPerState);

}
