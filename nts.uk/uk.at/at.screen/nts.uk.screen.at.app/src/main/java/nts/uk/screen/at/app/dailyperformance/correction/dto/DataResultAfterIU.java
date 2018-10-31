package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.checkdata.dto.FlexShortageRCDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResultAfterIU {
	
	Map<Integer, List<DPItemValue>> errorMap;
	
	FlexShortageRCDto flexShortage;
	
	Boolean showErrorDialog;
}
