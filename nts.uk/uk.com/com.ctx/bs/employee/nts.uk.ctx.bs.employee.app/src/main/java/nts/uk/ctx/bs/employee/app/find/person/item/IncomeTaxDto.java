package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class IncomeTaxDto extends CtgItemFixDto{
	/**
	 * domain: 家族所得税
	 */
	/** 所得税ID */
	private String incomeTaxID;
	/** 家族メンバーID */
	private String familyMemberId;
	/** 社員ID */
	private String sid;
	/** Start date */
	private GeneralDate strD;
	/** End date */
	private GeneralDate endD;
	/** 扶養者区分 */
	private boolean supporter;
	/** 障害区分*/
	private int disabilityType;
	/** 控除対象区分*/
	private int deductionTargetType;
	
	private IncomeTaxDto(String incomeTaxID, String familyMemberId, String sid,
			GeneralDate startDate, GeneralDate endDate, boolean supporter, int disabilityType,
			int deductionTargetType) {
		super();
		this.ctgItemType = CtgItemType.INCOME_TAX;
		this.incomeTaxID = incomeTaxID;
		this.familyMemberId = familyMemberId;
		this.sid = sid;
		this.strD = startDate;
		this.endD = endDate;
		this.supporter = supporter;
		this.disabilityType = disabilityType;
		this.deductionTargetType = deductionTargetType;
	}
	
	public static IncomeTaxDto createFromJavaType(String incomeTaxID, String familyMemberId, String sid,
			GeneralDate startDate, GeneralDate endDate, boolean supporter, int disabilityType,
			int deductionTargetType){
		return new IncomeTaxDto(incomeTaxID, familyMemberId, sid, startDate, endDate, supporter, disabilityType, deductionTargetType);
	}
}
