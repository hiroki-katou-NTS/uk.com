package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyActualResults;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyActualResultsDto {
	/**ID*/
	private String monthlyActualID;
	/**月次表示項目シート一覧*/
	private List<SheetCorrectedMonthlyDto> listSheetCorrectedMonthly;
	public MonthlyActualResultsDto(String monthlyActualID, List<SheetCorrectedMonthlyDto> listSheetCorrectedMonthly) {
		super();
		this.monthlyActualID = monthlyActualID;
		this.listSheetCorrectedMonthly = listSheetCorrectedMonthly;
	}
	
	public static MonthlyActualResultsDto fromDomain(MonthlyActualResults domain) {
		return new MonthlyActualResultsDto(
				domain.getMonthlyActualID(),
				domain.getListSheetCorrectedMonthly().stream().map(c->SheetCorrectedMonthlyDto.fromDomain(c)).collect(Collectors.toList())
				);
	}
}
