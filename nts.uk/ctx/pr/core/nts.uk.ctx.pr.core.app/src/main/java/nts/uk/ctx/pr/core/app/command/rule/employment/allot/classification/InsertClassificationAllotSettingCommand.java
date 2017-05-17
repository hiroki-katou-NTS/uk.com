package nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSetting;

@AllArgsConstructor
@Getter
public class InsertClassificationAllotSettingCommand {
	String companyCode;
	String historyId;
	String classificationCode;
	String bonusDetailCode;
	String paymentDetailCode;
	
	public ClassificationAllotSetting toDomain(String companyCode){
		return ClassificationAllotSetting.createFromJavaType(getCompanyCode(), getHistoryId(), getClassificationCode(), getBonusDetailCode(), getPaymentDetailCode());
		
	} 

}
