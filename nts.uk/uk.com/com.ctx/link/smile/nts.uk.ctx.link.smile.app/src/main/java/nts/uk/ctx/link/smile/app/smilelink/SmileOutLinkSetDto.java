package nts.uk.ctx.link.smile.app.smilelink;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.LinkedMonthSettingClassification;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversion;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.PaymentCategory;

/**
 * 
 * @author yen_nth
 *
 */
@Data
@NoArgsConstructor
public class SmileOutLinkSetDto {

	// 給与連携区分 
	private int salaryCooperationClassification;
	
	// 月次ロック区分 
	private int monthlyLockClassification;
	
	// 月次承認区分
	private int monthlyApprovalCategory;
	
	// 給与連携条件 
	private Optional<String> salaryCooperationConditions;
	
	// 連動支払変換
	private List<LinkedPaymentDto> linkedPaymentConversion;

	public SmileOutLinkSetDto(int salaryCooperationClassification, int monthlyLockClassification,
			int monthlyApprovalCategory, Optional<String> salaryCooperationConditions,
			List<LinkedPaymentDto> linkedPaymentConversion) {
		super();
		this.salaryCooperationClassification = salaryCooperationClassification;
		this.monthlyLockClassification = monthlyLockClassification;
		this.monthlyApprovalCategory = monthlyApprovalCategory;
		this.salaryCooperationConditions = salaryCooperationConditions;
		this.linkedPaymentConversion = linkedPaymentConversion;
	}
	
}
