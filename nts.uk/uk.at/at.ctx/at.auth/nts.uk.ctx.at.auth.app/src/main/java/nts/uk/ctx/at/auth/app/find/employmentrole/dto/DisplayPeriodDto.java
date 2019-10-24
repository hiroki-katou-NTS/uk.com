package nts.uk.ctx.at.auth.app.find.employmentrole.dto;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DisplayPeriodDto {
	public int currentOrNextMonth;
	public List<DateProcessedDto> listDateProcessed;
}
