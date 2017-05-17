package nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteClassificationAllotSettingCommand {
	String companyCode;
	String historyId;
	String classificationCode;
	String bonusDetailCode;
	String paymentDetailCode;
}
