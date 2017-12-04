/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingSetMemento;

/**
 * The Class FlowWorkRestSettingDto.
 */
@Getter
@Setter
public class FlowWorkRestSettingDto implements FlowWorkRestSettingSetMemento{
	
	/** The common rest setting. */
	private CommonRestSettingDto commonRestSetting;

	/** The flow rest setting. */
	private FlowWorkRestSettingDetailDto flowRestSetting;

	/**
	 * Sets the common rest setting.
	 *
	 * @param commonRest the new common rest setting
	 */
	@Override
	public void setCommonRestSetting(CommonRestSetting commonRest) {
		commonRest.saveToMemento(this.commonRestSetting);
	}

	/**
	 * Sets the flow rest setting.
	 *
	 * @param flowRest the new flow rest setting
	 */
	@Override
	public void setFlowRestSetting(FlowWorkRestSettingDetail flowRest) {
		flowRest.saveToMemento(this.flowRestSetting);
	}
	
	

}
