package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 月次の初期表示フォーマット
 * @author tutk
 *
 */
@Getter
public class InitialDisplayMonthly extends AggregateRoot {
	/**会社ID*/
	private String companyID;
	/**コード*/
	private MonthlyPerformanceFormatCode monthlyPfmFormatCode;

	public InitialDisplayMonthly(String companyID, MonthlyPerformanceFormatCode monthlyPfmFormatCode) {
		super();
		this.companyID = companyID;
		this.monthlyPfmFormatCode = monthlyPfmFormatCode;
	}
	
	

}
