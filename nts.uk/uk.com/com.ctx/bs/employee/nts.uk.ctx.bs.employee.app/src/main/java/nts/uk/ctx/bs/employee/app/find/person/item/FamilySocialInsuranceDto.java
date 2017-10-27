package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class FamilySocialInsuranceDto extends CtgItemFixDto{
	/**
	 * 家族社会保険
	 */
	/** 家族メンバーID */
	private String familyMemberId;
	/**  社員ID*/
	private String sid;
	/** 家族社会保険ID */
	private String socailInsuaranceId;
	/** 開始日 */
	private GeneralDate startDate;
	/** 終了日 */
	private GeneralDate endDate;
	/** 介護社会保険適用 */
	private boolean nursingCare;
	/** 健康保険被扶養者区分 */
	private boolean healthInsuranceDependent;
	/** 国民年金第3号資格者*/
	private boolean nationalPensionNo3;
	/** 基礎年金番号*/
	private String basicPensionNumber;

	private FamilySocialInsuranceDto(String familyMemberId, String sid, String socailInsuaranceId,
			GeneralDate startDate, GeneralDate endDate, boolean nursingCare, boolean healthInsuranceDependent,
			boolean nationalPensionNo3, String basicPensionNumber){
		super();
		this.ctgItemType = CtgItemType.FAMILY_SOCIAL_INSURANCE;
		this.familyMemberId = familyMemberId;
		this.sid = sid;
		this.socailInsuaranceId = socailInsuaranceId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nursingCare = nursingCare;
		this.healthInsuranceDependent = healthInsuranceDependent;
		this.nationalPensionNo3 = nationalPensionNo3;
		this.basicPensionNumber = basicPensionNumber;
	}
	public static FamilySocialInsuranceDto createFromJavaType(String familyMemberId, String sid, String socailInsuaranceId,
			GeneralDate startDate, GeneralDate endDate, boolean nursingCare, boolean healthInsuranceDependent,
			boolean nationalPensionNo3, String basicPensionNumber) {
		return new FamilySocialInsuranceDto(familyMemberId, sid, socailInsuaranceId, startDate, endDate, nursingCare,
				healthInsuranceDependent, nationalPensionNo3, basicPensionNumber);
	}
}
