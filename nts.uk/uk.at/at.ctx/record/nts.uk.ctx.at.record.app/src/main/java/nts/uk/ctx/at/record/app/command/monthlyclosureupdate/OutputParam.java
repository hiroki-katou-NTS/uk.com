package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class OutputParam {

	private int status;
	private Optional<MonthlyClosureUpdateLog> outputLog; 
}
