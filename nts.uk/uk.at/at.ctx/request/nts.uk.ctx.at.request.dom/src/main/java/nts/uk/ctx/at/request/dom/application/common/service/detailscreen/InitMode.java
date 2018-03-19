package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

/**
 * 14-3.詳細画面の初期モード
 * @author hieult
 *
 */
public interface InitMode {

	public DetailScreenInitModeOutput getDetailScreenInitMode(User user, Integer reflectPerState);

}
