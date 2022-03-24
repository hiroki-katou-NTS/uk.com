package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaLimit;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

@NoArgsConstructor
@Data
public class StampingAreaRestrictionCommand {

	private int useLocationInformation;

	/** 制限方法 */
	private int stampingAreaLimit;

	public StampingAreaRestriction toDomain() {

		return new StampingAreaRestriction(EnumAdaptor.valueOf(useLocationInformation, NotUseAtr.class),
				EnumAdaptor.valueOf(stampingAreaLimit, StampingAreaLimit.class));
	}

}
