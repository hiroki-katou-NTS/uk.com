package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class FamilyCareDto extends CtgItemFixDto{
	/**
	 *  家族介護
	 */
	/** 家族介護ID*/
	private String familyCareId;
	/** 家族ID */
	private String familyId;
	/** 社員ID */
	private String sid;
	/** 開始日 */
	private GeneralDate startDate;
	/** 終了日 */
	private GeneralDate endDate;
	/** 支援介護区分*/
	private int careClassifi;

	private FamilyCareDto(String familyCareId, String familyId, String sid, GeneralDate startDate,
			GeneralDate endDate, int careClassifi){
		super();
		this.ctgItemType = CtgItemType.FAMILY_CARE;
		this.familyCareId = familyCareId;
		this.familyId = familyId;
		this.sid = sid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.careClassifi = careClassifi;
	}
	public static FamilyCareDto createFromJavaType(String familyCareId, String familyId, String sid, GeneralDate startDate,
			GeneralDate endDate, int careClassifi) {
		return new FamilyCareDto(familyCareId, familyId, sid, startDate, endDate,
				careClassifi);
	}
}
