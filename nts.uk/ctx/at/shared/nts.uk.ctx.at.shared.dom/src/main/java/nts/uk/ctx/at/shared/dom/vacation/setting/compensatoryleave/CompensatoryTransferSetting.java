/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CompensatoryTransferSetting.
 */
@Setter
public class CompensatoryTransferSetting extends DomainObject {

	/** The certain time. */
	private OneDayTime certainTime;
	
	/** The use division. */
	private boolean useDivision;
	
	/** The design time. */
	private DesignTime designTime;
	
	/** The transfer division. */
	private TransferSettingDivision transferDivision; 
}
