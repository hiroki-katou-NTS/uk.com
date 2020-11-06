package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCombinations;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftPalletDto {

	public int patternNo;
	public String patternName;
	public List<CombinationDto> workPairSet;
	
	public ShiftPalletDto(ShiftPaletteCombinations shiftPalletCombinations) {
		super();
		this.patternNo = shiftPalletCombinations.getPositionNumber();
		this.patternName = shiftPalletCombinations.getCombinationName().v();
		this.workPairSet = shiftPalletCombinations.getCombinations()
				.stream()
				.map( c-> new CombinationDto (c))
				.collect(Collectors.toList());
	}
}
