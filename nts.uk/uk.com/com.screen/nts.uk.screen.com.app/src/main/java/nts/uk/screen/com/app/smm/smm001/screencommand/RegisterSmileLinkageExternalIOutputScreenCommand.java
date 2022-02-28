package nts.uk.screen.com.app.smm.smm001.screencommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutputConditionCode;
import nts.uk.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedMonthSettingClassification;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversion;
import nts.uk.smile.dom.smilelinked.cooperationoutput.PaymentCategory;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileCooperationOutputClassification;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.arc.enums.EnumAdaptor;

@Getter
public class RegisterSmileLinkageExternalIOutputScreenCommand {
	private Integer paymentCode;
	private List<EmploymentAndLinkedMonthSettingDto> rightEmployments;
	private Integer salaryCooperationClassification;
	private Integer monthlyLockClassification;
	private Integer monthlyApprovalCategory;
	private String salaryCooperationConditions;

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

	public LinkedPaymentConversion convertScreenCommandToLinkedPaymentConversion() {
		LinkedPaymentConversion domain = null;
		List<EmploymentAndLinkedMonthSetting> selectiveEmploymentCodes = new ArrayList<>();
		if (!rightEmployments.isEmpty()) {
			rightEmployments.forEach(e -> {
				selectiveEmploymentCodes.add(new EmploymentAndLinkedMonthSetting(
						EnumAdaptor.valueOf(e.getInterlockingMonthAdjustment(), LinkedMonthSettingClassification.class),
						e.getScd()));
			});
		}
		domain = new LinkedPaymentConversion(EnumAdaptor.valueOf(paymentCode, PaymentCategory.class),
				selectiveEmploymentCodes);
		return domain;
	}

}
