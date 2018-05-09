package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyPerformanceFormatCode;

@Getter
@Setter
@NoArgsConstructor
public class InitialDisplayMonthlyCmd {


	/**会社ID*/
	private String companyID;
	/**コード*/
	private String monthlyPfmFormatCode;
	
	public InitialDisplayMonthlyCmd(String companyID, String monthlyPfmFormatCode) {
		super();
		this.companyID = companyID;
		this.monthlyPfmFormatCode = monthlyPfmFormatCode;
	}
	
	public static InitialDisplayMonthly fromCommand(InitialDisplayMonthlyCmd command) {
		return new InitialDisplayMonthly(
				command.getCompanyID(),
				new MonthlyPerformanceFormatCode(command.getMonthlyPfmFormatCode())
				);
	}
}
