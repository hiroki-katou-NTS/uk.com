package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class AgreementTimeOfMonthlyImport.
 *
 * @author LienPTK
 */
@Data
@AllArgsConstructor
public class AgreTimeOfMonthlyImport {
	/** 対象時間 */
	private int agreementTime;
	/** 閾値 */
	private OneTimeImport threshold;
}

