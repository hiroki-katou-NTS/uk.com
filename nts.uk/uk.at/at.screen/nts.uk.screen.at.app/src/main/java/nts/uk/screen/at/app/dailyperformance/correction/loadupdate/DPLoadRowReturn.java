package nts.uk.screen.at.app.dailyperformance.correction.loadupdate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCellStateDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DPLoadRowReturn {
	
	private List<DPDataDto> lstData;
	
	private List<DPCellStateDto> lstCellState;
	
	
}
