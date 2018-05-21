package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
@Getter
@Setter
@NoArgsConstructor
public class SheetCorrectedMonthlyDto {

	/**並び順*/
	private int sheetNo;
	/**名称*/
	private String sheetName;
	/**月次表示項目一覧*/
	private List<DisplayTimeItemDto> listDisplayTimeItem;
	public SheetCorrectedMonthlyDto( int sheetNo, String sheetName, List<DisplayTimeItemDto> listDisplayTimeItem) {
		super();
		this.sheetNo = sheetNo;
		this.sheetName = sheetName;
		this.listDisplayTimeItem = listDisplayTimeItem;
	}
	
	public static SheetCorrectedMonthlyDto fromDomain(SheetCorrectedMonthly domain) {
		return new SheetCorrectedMonthlyDto(
				domain.getSheetNo(),
				domain.getSheetName().v(),
				domain.getListDisplayTimeItem().stream().map(c->DisplayTimeItemDto.fromDomain(c)).collect(Collectors.toList())
				);
	}
	
}
