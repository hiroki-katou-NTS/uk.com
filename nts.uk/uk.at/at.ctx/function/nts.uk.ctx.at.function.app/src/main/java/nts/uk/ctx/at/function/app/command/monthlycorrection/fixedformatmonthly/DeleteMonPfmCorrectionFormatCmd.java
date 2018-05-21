package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMonPfmCorrectionFormatCmd {
	/**会社ID*/
	private String companyID;
	/**コード*/
	private String monthlyPfmFormatCode;
	
}
