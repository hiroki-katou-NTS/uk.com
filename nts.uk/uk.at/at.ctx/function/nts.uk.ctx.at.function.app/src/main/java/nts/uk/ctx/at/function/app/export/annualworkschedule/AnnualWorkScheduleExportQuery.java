package nts.uk.ctx.at.function.app.export.annualworkschedule;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.function.app.find.annualworkschedule.EmployeeDto;

@Value
public class AnnualWorkScheduleExportQuery {
	/** */
	private List<EmployeeDto> employees;
	private int printFormat;
	private String fiscalYear;
	/** A3_2 期間: 開始年月 */
	private String startYearMonth;
	/** A3_4 期間: 終了年月 */
	private String endYearMonth;
	/** A4_2 定型選択 */
	private String setItemsOutputCd;
	/** A6_2 改頁選択 */
	private int breakPage;
	/** A10_2 36協定対象外者選択肢 */
	private int excludeEmp;
}
