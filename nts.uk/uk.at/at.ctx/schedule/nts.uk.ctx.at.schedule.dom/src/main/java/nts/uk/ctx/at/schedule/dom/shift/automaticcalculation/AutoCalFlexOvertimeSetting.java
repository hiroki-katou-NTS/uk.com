/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.automaticcalculation;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class AutoCalFlexOvertimeSetting.
 */
//フレックス超過時間の自動計算設定

@Getter
public class AutoCalFlexOvertimeSetting extends DomainObject {
	
	/** The company id. */
	//会社ID
	private CompanyId companyId;
	
	/** The job id. */
	//職位ID
	private PositionId jobId;
	
	/** The wkp id. */
	//職場ID
	private WorkplaceId wkpId;
	
	/** The auto cal atr. */
	private Integer autoCalAtr;

	/** The Flex OT time. */
	//フレックス超過時間
	private AutoCalSetting FlexOTTime;

	/** The Flex OT night time. */
	//フレックス超過深夜時間
	private AutoCalSetting FlexOTNightTime;

	/**
	 * Instantiates a new auto cal flex overtime setting.
	 *
	 * @param companyId the company id
	 * @param jobId the job id
	 * @param wkpId the wkp id
	 * @param autoCalAtr the auto cal atr
	 * @param flexOTTime the flex OT time
	 * @param flexOTNightTime the flex OT night time
	 */
	public AutoCalFlexOvertimeSetting(CompanyId companyId, PositionId jobId, WorkplaceId wkpId, Integer autoCalAtr,
			AutoCalSetting flexOTTime, AutoCalSetting flexOTNightTime) {
		super();
		this.companyId = companyId;
		this.jobId = jobId;
		this.wkpId = wkpId;
		this.autoCalAtr = autoCalAtr;
		FlexOTTime = flexOTTime;
		FlexOTNightTime = flexOTNightTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autoCalAtr == null) ? 0 : autoCalAtr.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		result = prime * result + ((wkpId == null) ? 0 : wkpId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutoCalFlexOvertimeSetting other = (AutoCalFlexOvertimeSetting) obj;
		if (autoCalAtr == null) {
			if (other.autoCalAtr != null)
				return false;
		} else if (!autoCalAtr.equals(other.autoCalAtr))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		if (wkpId == null) {
			if (other.wkpId != null)
				return false;
		} else if (!wkpId.equals(other.wkpId))
			return false;
		return true;
	}
}
