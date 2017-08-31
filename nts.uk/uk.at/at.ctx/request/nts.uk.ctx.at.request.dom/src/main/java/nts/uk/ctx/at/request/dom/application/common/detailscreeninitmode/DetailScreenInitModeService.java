package nts.uk.ctx.at.request.dom.application.common.detailscreeninitmode;

import nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode.User;

/**
 * 
 * @author hieult
 *
 */
public interface DetailScreenInitModeService {

	public DetailScreenInitModeOutput getDetailScreenInitMode(User user, int reflectPerState);

}
