package nts.uk.screen.com.app.smm.smm001.screencommand;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutputConditionCode;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileCooperationOutputClassification;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.arc.enums.EnumAdaptor;

@Getter
public class RegisterSmileLinkageExternalIOutputScreenCommand {
	private Integer paymentCode;

	// Start: Variable at b screen
	private Integer salaryCooperationClassification;
	private Integer monthlyLockClassification;
	private Integer monthlyApprovalCategory;
	private String salaryCooperationConditions;
	// End: Variable at b screen

	public SmileLinkageOutputSetting convertScreenCommandToEntity() {
		ExternalOutputConditionCode externalOutputConditionCode = null;
		if (salaryCooperationConditions != null) {
			externalOutputConditionCode = new ExternalOutputConditionCode(salaryCooperationConditions);
		}
		SmileLinkageOutputSetting domain = new SmileLinkageOutputSetting(
				EnumAdaptor.valueOf(salaryCooperationClassification, SmileCooperationOutputClassification.class),
				EnumAdaptor.valueOf(monthlyLockClassification, SmileCooperationOutputClassification.class),
				EnumAdaptor.valueOf(monthlyApprovalCategory, SmileCooperationOutputClassification.class),
				Optional.ofNullable(externalOutputConditionCode));
		return domain;
	}

}
