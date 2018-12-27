package nts.uk.screen.at.app.dailyperformance.correction.checkdata.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;

@AllArgsConstructor
@Data
public class ErrorAfterCalcDaily {
	
	private Boolean hasError;
	
	private Map<Integer, List<DPItemValue>> resultError;
	
	private FlexShortageRCDto flexShortage;

}
