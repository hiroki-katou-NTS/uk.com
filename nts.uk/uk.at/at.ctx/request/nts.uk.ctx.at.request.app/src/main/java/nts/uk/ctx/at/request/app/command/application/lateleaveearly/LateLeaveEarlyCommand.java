package nts.uk.ctx.at.request.app.command.application.lateleaveearly;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
public class LateLeaveEarlyCommand {
	
	//	遅刻早退取消申請
	private int appType;
	
	//	申請
	private Application application;
	
	//	遅刻早退取消申請起動時の表示情報
	private ArrivedLateLeaveEarlyInfoOutput infoOutput;

}
