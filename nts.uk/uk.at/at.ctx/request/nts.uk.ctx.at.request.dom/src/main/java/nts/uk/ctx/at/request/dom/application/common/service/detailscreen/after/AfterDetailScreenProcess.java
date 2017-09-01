package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.dto.DetailScreenProcessAfterOutput;

/**
 * 
 * @author hieult
 *
 */
/**10-2.詳細画面解除後の処理 */
public interface AfterDetailScreenProcess {
	
	public DetailScreenProcessAfterOutput getDetailScreenProcessAfter(Application application);


}
