/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestTimePK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSetPK;

/**
 * The Class KshmtFlexSetGroub.
 */
@Getter
@Setter
public class KshmtFlexSetGroup {

	/** The entity. */
	private KshmtFlexOdRestTime entityOffday;

	/** The entity. */
	private KshmtFlexWorkSet entitySetting;
	
	/** The entity flow. */
	private KshmtFlexRestSet entityRest;

	/**
	 * Instantiates a new kshmt flex set groub.
	 *
	 * @param entityOffday the entity offday
	 * @param entitySetting the entity setting
	 * @param entityRest the entity rest
	 */
	public KshmtFlexSetGroup(KshmtFlexOdRestTime entityOffday, KshmtFlexWorkSet entitySetting,
			KshmtFlexRestSet entityRest) {
		super();
		if(entityOffday.getKshmtFlexOdRestTimePK() == null){
			entityOffday.setKshmtFlexOdRestTimePK(new KshmtFlexOdRestTimePK());
		}
		if(entitySetting.getKshmtFlexWorkSetPK() == null){
			entitySetting.setKshmtFlexWorkSetPK(new KshmtFlexWorkSetPK());
		}
		if(entityRest.getKshmtFlexRestSetPK() == null){
			entityRest.setKshmtFlexRestSetPK(new KshmtFlexRestSetPK());
		}
		this.entityOffday = entityOffday;
		this.entitySetting = entitySetting;
		this.entityRest = entityRest;
	}
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId){
		this.entityOffday.getKshmtFlexOdRestTimePK().setCid(companyId);
		this.entityRest.getKshmtFlexRestSetPK().setCid(companyId);
		this.entitySetting.getKshmtFlexWorkSetPK().setCid(companyId);
	}
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId(){
		return this.entitySetting.getKshmtFlexWorkSetPK().getCid();
	}
	
	/**
	 * Sets the worktime code.
	 *
	 * @param worktimeCode the new worktime code
	 */
	public void setWorktimeCode(String worktimeCode){
		this.entityOffday.getKshmtFlexOdRestTimePK().setWorktimeCd(worktimeCode);
		this.entityRest.getKshmtFlexRestSetPK().setWorktimeCd(worktimeCode);
		this.entitySetting.getKshmtFlexWorkSetPK().setWorktimeCd(worktimeCode);
	}
	
	/**
	 * Gets the worktime code.
	 *
	 * @return the worktime code
	 */
	public String getWorktimeCode(){
		return this.entitySetting.getKshmtFlexWorkSetPK().getWorktimeCd();
	}
	
}
