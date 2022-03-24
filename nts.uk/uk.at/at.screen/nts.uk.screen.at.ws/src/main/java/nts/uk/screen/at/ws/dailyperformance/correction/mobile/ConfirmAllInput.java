package nts.uk.screen.at.ws.dailyperformance.correction.mobile;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemCheckBox;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmAllInput {
	
	private List<DPItemCheckBox> listDPItemCheckBox;
	
	private List<DailyRecordDto> dailyRecordDtos;
}
