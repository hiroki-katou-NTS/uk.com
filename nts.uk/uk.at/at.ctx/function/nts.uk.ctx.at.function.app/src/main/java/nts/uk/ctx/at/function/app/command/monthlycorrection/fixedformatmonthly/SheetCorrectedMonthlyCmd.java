package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

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
public class SheetCorrectedMonthlyCmd {
	/**並び順*/
	private int sheetNo;
	/**名称*/
	private String sheetName;
	/**月次表示項目一覧*/
	private List<DisplayTimeItemCmd> listDisplayTimeItem;
	public SheetCorrectedMonthlyCmd(int sheetNo, String sheetName, List<DisplayTimeItemCmd> listDisplayTimeItem) {
		super();
		this.sheetNo = sheetNo;
		this.sheetName = sheetName;
		this.listDisplayTimeItem = listDisplayTimeItem;
	}
	
	public static SheetCorrectedMonthly fromCommand(SheetCorrectedMonthlyCmd command) {
		return new SheetCorrectedMonthly(
				command.getSheetNo(),
				new DailyPerformanceFormatName(command.getSheetName()),
				command.getListDisplayTimeItem().stream().map(c->DisplayTimeItemCmd.fromCommand(c)).collect(Collectors.toList())
				);
	}
	
}
