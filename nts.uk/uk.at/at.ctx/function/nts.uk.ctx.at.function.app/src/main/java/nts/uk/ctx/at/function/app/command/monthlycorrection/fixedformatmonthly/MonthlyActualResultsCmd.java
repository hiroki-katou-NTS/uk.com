package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyActualResults;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyActualResultsCmd {
	/**月次表示項目シート一覧*/
	private List<SheetCorrectedMonthlyCmd> listSheetCorrectedMonthly;
	public MonthlyActualResultsCmd(List<SheetCorrectedMonthlyCmd> listSheetCorrectedMonthly) {
		super();
		this.listSheetCorrectedMonthly = listSheetCorrectedMonthly;
	}
	
	public static MonthlyActualResults fromCommand(MonthlyActualResultsCmd command) {
		return new MonthlyActualResults(
				command.getListSheetCorrectedMonthly().stream().map(c->SheetCorrectedMonthlyCmd.fromCommand(c)).collect(Collectors.toList())
				);
	}
}
