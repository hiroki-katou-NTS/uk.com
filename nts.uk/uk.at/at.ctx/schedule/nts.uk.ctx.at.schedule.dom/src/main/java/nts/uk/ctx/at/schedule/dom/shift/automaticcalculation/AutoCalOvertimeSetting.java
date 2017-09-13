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
 * The Class AutoCalOvertimeSetting.
 */
///残業時間の自動計算設定

/**
 * Gets the legal mid OT time.
 *
 * @return the legal mid OT time
 */
@Getter
public class AutoCalOvertimeSetting extends DomainObject{

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
	
	/** The Early OT time. */
	//早出残業時間
	private AutoCalSetting EarlyOTTime;
	
	/** The Early mid OT time. */
	//早出深夜残業時間
	private AutoCalSetting EarlyMidOTTime;
	
	/** The Normal OT time. */
	//普通残業時間
	private AutoCalSetting NormalOTTime;
	
	/** The Normal mid OT time. */
	//普通深夜残業時間
	private AutoCalSetting NormalMidOTTime;
	
	/** The Legal OT time. */
	//法定内残業時間
	private AutoCalSetting LegalOTTime;
	
	/** The Legal mid OT time. */
	//法定内深夜残業時間
	private AutoCalSetting LegalMidOTTime;

	/**
	 * Instantiates a new auto cal overtime setting.
	 *
	 * @param companyId the company id
	 * @param jobId the job id
	 * @param wkpId the wkp id
	 * @param autoCalAtr the auto cal atr
	 * @param earlyOTTime the early OT time
	 * @param earlyMidOTTime the early mid OT time
	 * @param normalOTTime the normal OT time
	 * @param normalMidOTTime the normal mid OT time
	 * @param legalOTTime the legal OT time
	 * @param legalMidOTTime the legal mid OT time
	 */
	public AutoCalOvertimeSetting(CompanyId companyId, PositionId jobId, WorkplaceId wkpId, Integer autoCalAtr,
			AutoCalSetting earlyOTTime, AutoCalSetting earlyMidOTTime, AutoCalSetting normalOTTime,
			AutoCalSetting normalMidOTTime, AutoCalSetting legalOTTime, AutoCalSetting legalMidOTTime) {
		super();
		this.companyId = companyId;
		this.jobId = jobId;
		this.wkpId = wkpId;
		this.autoCalAtr = autoCalAtr;
		EarlyOTTime = earlyOTTime;
		EarlyMidOTTime = earlyMidOTTime;
		NormalOTTime = normalOTTime;
		NormalMidOTTime = normalMidOTTime;
		LegalOTTime = legalOTTime;
		LegalMidOTTime = legalMidOTTime;
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
		AutoCalOvertimeSetting other = (AutoCalOvertimeSetting) obj;
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
