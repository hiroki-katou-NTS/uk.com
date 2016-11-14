package nts.uk.ctx.pr.proto.app.paymentdata.find;

import lombok.Value;

/**
 * Finder DTO of PersonalPaymentSetting
 */
@Value
public class PersonalPaymentSettingDto {

	/** company code */
	private String companyCode;

	/** 個人Id */
	private String personId;

	/** 開始日 */
	private int startDate;

	/** 完了日 */
	private int endDate;

	/** 給与明細コード */
	private String paymentDetailCode;

	/** 賞与明細コード */
	private String bonusDetailCode;

	/**
	 * Create DTO from domain object.
	 * 
	 * @param domain
	 *            domain
	 * @return DTO
	 */
	// public static PersonalPaymentSettingDto fromDomain(Person domain) {
	// return new PersonalPaymentSettingDto(domain.getCode().v(),
	// domain.getName().v());
	// }
}
