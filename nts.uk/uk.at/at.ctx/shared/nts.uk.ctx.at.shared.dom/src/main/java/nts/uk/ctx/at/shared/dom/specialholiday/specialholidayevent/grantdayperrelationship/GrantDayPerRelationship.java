package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHolidayCode;

@NoArgsConstructor
@AllArgsConstructor
@Data
/* 続柄毎の上限日数 */
public class GrantDayPerRelationship extends AggregateRoot {

	/* 会社ID */
	private String companyId;
	
	/* 続柄毎の上限日数ID */
	private String grantDayPerRelpId;

	/* 特別休暇枠NO */
	private int specialHolidayEventNo;

	/* 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/* 忌引とする */
	private UseAtr makeInvitation;

	/* 続柄に対する上限日数 */
	List<GrantDayRelationship> grantDayRelps;

}
