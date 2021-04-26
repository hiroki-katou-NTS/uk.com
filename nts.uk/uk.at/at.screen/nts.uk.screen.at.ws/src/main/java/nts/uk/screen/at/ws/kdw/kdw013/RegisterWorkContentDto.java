package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeLeaveTime;

/**
 * 
 * @author tutt
 *
 */
public class RegisterWorkContentDto {

	// エラー一覧
	private List<ErrorMessageInfoDto> lstErrorMessageInfo;
	
	// List<残業休出時間>
	private List<OvertimeLeaveTime> lstOvertimeLeaveTime;
	
}
