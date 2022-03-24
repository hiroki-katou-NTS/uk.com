package nts.uk.ctx.link.smile.app.smilelink;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author yen_nth
 *
 */
@Data
@NoArgsConstructor
public class SmileOutLinkSetDto {

	// 給与連携区分 
	private Integer salaryCooperationClassification;
	
	// 月次ロック区分 
	private Integer monthlyLockClassification;
	
	// 月次承認区分
	private Integer monthlyApprovalCategory;
	
	// 給与連携条件 
	private String salaryCooperationConditions;
	
	// 連動支払変換
	private List<LinkedPaymentDto> linkedPaymentConversion;

	public SmileOutLinkSetDto(Integer salaryCooperationClassification, Integer monthlyLockClassification,
			Integer monthlyApprovalCategory, Optional<String> salaryCooperationConditions,
			List<LinkedPaymentDto> linkedPaymentConversion) {
		super();
		this.salaryCooperationClassification = salaryCooperationClassification;
		this.monthlyLockClassification = monthlyLockClassification;
		this.monthlyApprovalCategory = monthlyApprovalCategory;
		this.salaryCooperationConditions = salaryCooperationConditions.isPresent() ? salaryCooperationConditions.get() : null;
		this.linkedPaymentConversion = linkedPaymentConversion;
	}
	
}
