package nts.uk.screen.at.ws.kdw.kdw013.bch;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class StartParamDto {
	
	public GeneralDate refDate;
	
	public List<Integer> itemIds;
}
