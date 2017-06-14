package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemType;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfWorkPlace {

	private String workplaceId;

	private String basicSettingId;
	
	private LaborSystemType laborSystemType;
	
}
