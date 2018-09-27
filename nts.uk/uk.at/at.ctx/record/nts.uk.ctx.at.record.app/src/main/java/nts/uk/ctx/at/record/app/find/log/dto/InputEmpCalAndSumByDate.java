package nts.uk.ctx.at.record.app.find.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputEmpCalAndSumByDate {
	
	GeneralDateTime startDate;
	
	GeneralDateTime endDate;

}
