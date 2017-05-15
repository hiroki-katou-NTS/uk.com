package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateClassificationAllotSettingCommand {
	String companyCode;
	String historyId;
	String classificationCode;
	String bonusDetailCode;
	String paymentDetailCode;
}
