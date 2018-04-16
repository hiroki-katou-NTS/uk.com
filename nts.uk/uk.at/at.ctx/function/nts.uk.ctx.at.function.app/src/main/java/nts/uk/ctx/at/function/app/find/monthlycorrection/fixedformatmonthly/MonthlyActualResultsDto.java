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
	/**月次表示項目シート一覧*/
	private List<SheetCorrectedMonthlyDto> listSheetCorrectedMonthly;
	public MonthlyActualResultsDto( List<SheetCorrectedMonthlyDto> listSheetCorrectedMonthly) {
		super();
		this.listSheetCorrectedMonthly = listSheetCorrectedMonthly;
	}
	
	public static MonthlyActualResultsDto fromDomain(MonthlyActualResults domain) {
		return new MonthlyActualResultsDto(
				domain.getListSheetCorrectedMonthly().stream().map(c->SheetCorrectedMonthlyDto.fromDomain(c)).collect(Collectors.toList())
				);
	}
}
