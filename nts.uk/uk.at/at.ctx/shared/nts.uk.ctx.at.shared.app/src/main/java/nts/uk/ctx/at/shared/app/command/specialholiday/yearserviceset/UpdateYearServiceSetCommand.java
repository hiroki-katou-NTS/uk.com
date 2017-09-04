package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceset;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * update year month date
 * @author yennth
 *
 */
@Getter
@Setter
public class UpdateYearServiceSetCommand{
	private List<YearServiceSetCommand> yearServiceSets;
}
