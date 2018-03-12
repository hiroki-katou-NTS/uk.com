/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingGetMemento;

/**
 * The Class FlowWorkRestSettingDto.
 */
@Getter
@Setter
public class FlowWorkRestSettingDto implements FlowWorkRestSettingGetMemento{
	
	/** The common rest setting. */
	private CommonRestSettingDto commonRestSetting;

	/** The flow rest setting. */
	private FlowWorkRestSettingDetailDto flowRestSetting;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingGetMemento#getCommonRestSetting()
	 */
	@Override
	public CommonRestSetting getCommonRestSetting() {
		return new CommonRestSetting(this.commonRestSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingGetMemento#getFlowRestSetting()
	 */
	@Override
	public FlowWorkRestSettingDetail getFlowRestSetting() {
		return new FlowWorkRestSettingDetail(this.flowRestSetting);
	}

	

}
