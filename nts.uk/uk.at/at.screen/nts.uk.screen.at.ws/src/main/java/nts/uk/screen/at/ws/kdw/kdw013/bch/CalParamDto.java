package nts.uk.screen.at.ws.kdw.kdw013.bch;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.screen.at.app.kdw013.command.TimeSpanForCalcCommand;

/**
 * 
 * @author Sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalParamDto {

	TimeSpanForCalcCommand refTimezone;

	List<TimeSpanForCalcCommand> goOutBreakTimeLst;

	public TimeSpanForCalc getRefTimezone() {

		return TimeSpanForCalcCommand.toDomain(this.refTimezone);
	}
	
	
	public List<TimeSpanForCalc> getGoOutBreakTimeLst() {

		return this.goOutBreakTimeLst.stream().map(x -> TimeSpanForCalcCommand.toDomain(x))
				.collect(Collectors.toList());
	}
	
	
	
}
