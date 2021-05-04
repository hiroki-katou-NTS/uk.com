package nts.uk.screen.at.ws.kdw.kdw013;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tutt
 *
 */
@Getter
public class SelectWorkItemParam {
	
	//社員ID
	private String sId;
	
	//基準日
	private GeneralDate refDate;
	
	//作業枠NO
	private int taskFrameNo;
	
	//上位枠作業コード
	private String taskCode;
}