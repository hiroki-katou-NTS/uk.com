package nts.uk.ctx.pr.core.app.find.rule.employment.allot;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.ClassificationAllotSetting;

@Data
@AllArgsConstructor
public class ClassificationAllotSettingDto {

	String companyCode;
	String historyId;
	String classificationCode;
	String bonusDetailCode;
	String paymentDetailCode;

	public static ClassificationAllotSettingDto fromDomain(ClassificationAllotSetting classificationAllotSetting) {
		return new ClassificationAllotSettingDto(classificationAllotSetting.getCompanyCode().v(),
				classificationAllotSetting.getHistotyId(), classificationAllotSetting.getClassificationCode().v(),
				classificationAllotSetting.getBonusDetailCode().v(),
				classificationAllotSetting.getPaymentDetailCode().v());
	}

}
