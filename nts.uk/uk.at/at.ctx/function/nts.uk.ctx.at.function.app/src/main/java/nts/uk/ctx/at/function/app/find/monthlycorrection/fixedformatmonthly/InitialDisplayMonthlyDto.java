package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;

@Getter
@Setter
@NoArgsConstructor
public class InitialDisplayMonthlyDto {
	/**会社ID*/
	private String companyID;
	/**コード*/
	private String monthlyPfmFormatCode;
	public InitialDisplayMonthlyDto(String companyID, String monthlyPfmFormatCode) {
		super();
		this.companyID = companyID;
		this.monthlyPfmFormatCode = monthlyPfmFormatCode;
	}
	
	public static InitialDisplayMonthlyDto fromDomain(InitialDisplayMonthly domain) {
		return new InitialDisplayMonthlyDto(
			domain.getCompanyID(),
			domain.getMonthlyPfmFormatCode().v()
				);
	}
}
