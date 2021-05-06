package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;

import lombok.Setter;
import nts.uk.screen.at.app.kdw013.a.OvertimeLeaveTime;

/**
 * 
 * @author tutt
 *
 */
@Setter
public class RegisterWorkContentDto {

	// エラー一覧
	private List<ErrorMessageInfoDto> lstErrorMessageInfo;
	
	// List<残業休出時間>
	private List<OvertimeLeaveTime> lstOvertimeLeaveTime;
	
}
