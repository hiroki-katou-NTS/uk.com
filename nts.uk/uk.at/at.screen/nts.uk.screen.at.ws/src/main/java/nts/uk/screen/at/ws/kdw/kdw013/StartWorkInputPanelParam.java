package nts.uk.screen.at.ws.kdw.kdw013;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tutt
 *
 */
@Getter
public class StartWorkInputPanelParam {

	// 社員ID
	private String sId;

	// 基準日
	private GeneralDate refDate;

	// 作業グループ
	private WorkGroupDto workGroupDto;

}