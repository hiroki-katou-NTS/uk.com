package nts.uk.ctx.at.shared.dom.worktype.specialholidayframe;

import java.util.UUID;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

@Getter
public class SpecialHolidayFrame {
	
	/*会社ID*/
	private String companyId;
	/*特別休暇枠ID*/
	private UUID SpecialHdFrameId;
	/*枠名称*/
	private WorkTypeName SpecialHdFrameName;
	/*特別休暇枠の廃止区分*/
	private DeprecateClassification deprecateSpecialHd;
	
	
	public SpecialHolidayFrame(String companyId, UUID specialHdFrameId, WorkTypeName specialHdFrameName,
			DeprecateClassification deprecateSpecialHd) {
		super();
		this.companyId = companyId;
		SpecialHdFrameId = specialHdFrameId;
		SpecialHdFrameName = specialHdFrameName;
		this.deprecateSpecialHd = deprecateSpecialHd;
	}
	
	public static SpecialHolidayFrame createSimpleFromJavaType(String companyId, String specialHdFrameId, String specialHdFrameName,
			int deprecateSpecialHd) {
		return new SpecialHolidayFrame(companyId, 
				UUID.fromString(specialHdFrameId), 
				new WorkTypeName(specialHdFrameName), 
				EnumAdaptor.valueOf(deprecateSpecialHd, DeprecateClassification.class));
	}
}
