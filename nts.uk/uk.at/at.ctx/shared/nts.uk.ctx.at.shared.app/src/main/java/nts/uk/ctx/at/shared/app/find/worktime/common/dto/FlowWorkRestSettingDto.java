/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingSetMemento;

/**
 * The Class FlowWorkRestSettingDto.
 */
@Getter
@Setter
public class FlowWorkRestSettingDto implements FlowWorkRestSettingSetMemento {

	/** The common rest setting. */
	private CommonRestSettingDto commonRestSetting;

	/** The flow rest setting. */
	private FlowWorkRestSettingDetailDto flowRestSetting;
	
	/**
	 * Instantiates a new flow work rest setting dto.
	 */
	public FlowWorkRestSettingDto() {
		this.commonRestSetting = new CommonRestSettingDto();
		this.flowRestSetting = new FlowWorkRestSettingDetailDto();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingSetMemento#
	 * setCommonRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * CommonRestSetting)
	 */
	@Override
	public void setCommonRestSetting(CommonRestSetting commonRest) {
		if (commonRest != null) {
			this.commonRestSetting = new CommonRestSettingDto();
			commonRest.saveToMemento(this.commonRestSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingSetMemento#
	 * setFlowRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetail)
	 */
	@Override
	public void setFlowRestSetting(FlowWorkRestSettingDetail flowRest) {
		if (flowRest != null) {
			this.flowRestSetting = new FlowWorkRestSettingDetailDto();
			flowRest.saveToMemento(this.flowRestSetting);
		}
	}

}
