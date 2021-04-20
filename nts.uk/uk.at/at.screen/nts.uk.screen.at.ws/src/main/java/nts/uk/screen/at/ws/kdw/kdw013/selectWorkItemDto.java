package nts.uk.screen.at.ws.kdw.kdw013;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tutt
 *
 */
@Getter
public class selectWorkItemDto {
	
	//社員ID
	String sId;
	
	//基準日
	GeneralDate refDate;
	
	//作業枠NO
	int taskFrameNo;
	
	//上位枠作業コード
	String taskCode;
}