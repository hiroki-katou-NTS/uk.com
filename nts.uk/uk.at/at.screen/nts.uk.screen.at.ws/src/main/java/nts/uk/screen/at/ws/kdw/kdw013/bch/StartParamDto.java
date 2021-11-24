package nts.uk.screen.at.ws.kdw.kdw013.bch;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class StartParamDto {
	//社員ID
	public String employeeId; 
	//基準日
	public GeneralDate refDate;
	//List<工数実績項目ID>
	public List<Integer> itemIds;
	//Optional<作業コード1>
	public String workCode1; 
	//Optional<作業コード2>
	public String workCode2;
	//Optional<作業コード3>
	public String workCode3;
	//Optional<作業コード4>
	public String workCode4;
	//Optional<作業コード5>
	public String workCode5;
}
