package nts.uk.ctx.at.shared.app.command.worktype.specialholidayframe;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class SpecialHolidayFrameCommand {
	/*会社ID*/
	private String companyId;
	
	/*特別休暇枠ID*/
	private int specialHdFrameNo;
	
	/*枠名称*/
	private String specialHdFrameName;
	
	/*特別休暇枠の廃止区分*/
	private int deprecateSpecialHd;
	
	/* 時間管理する */
	private int timeMngAtr;
	
	/**
	 * Convert to domain object
	 * @return
	 */
	public SpecialHolidayFrame toDomain() {
		String companyId = AppContexts.user().companyId();
		
		return  SpecialHolidayFrame.createFromJavaType(companyId, specialHdFrameNo, specialHdFrameName, deprecateSpecialHd ,timeMngAtr);
	}
}
