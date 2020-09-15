package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutPutShiftMasterDto {

	private List<ShiftMastersDto> shiftMastersDto;
	
	private WorkInfoTimeZoneTempo tempo;
}
