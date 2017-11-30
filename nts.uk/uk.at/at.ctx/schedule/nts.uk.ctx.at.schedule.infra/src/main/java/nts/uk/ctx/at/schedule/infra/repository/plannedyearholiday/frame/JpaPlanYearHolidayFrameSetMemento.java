/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.plannedyearholiday.frame;

import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameName;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameNo;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame.KscstPlanYearHdFrame;
import nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame.KscstPlanYearHdFramePK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaPlanYearHolidayFrameSetMemento.
 */
public class JpaPlanYearHolidayFrameSetMemento implements PlanYearHolidayFrameSetMemento{
	
	/**
	 * Sets the kscst plan year hd frame.
	 *
	 * @param kscstPlanYearHdFrame the new kscst plan year hd frame
	 */
	@Setter
	private KscstPlanYearHdFrame kscstPlanYearHdFrame;
	
	/**
	 * Instantiates a new jpa plan year holiday frame set memento.
	 *
	 * @param kscstPlanYearHdFrame the kscst plan year hd frame
	 */
	public JpaPlanYearHolidayFrameSetMemento(KscstPlanYearHdFrame kscstPlanYearHdFrame) {
		if(kscstPlanYearHdFrame.getKscstPlanYearHdFramePK() == null){
			kscstPlanYearHdFrame.setKscstPlanYearHdFramePK(new KscstPlanYearHdFramePK());
		}
		this.kscstPlanYearHdFrame = kscstPlanYearHdFrame;
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.classification.ClassificationSetMemento#setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.kscstPlanYearHdFrame.getKscstPlanYearHdFramePK().setCid(companyId.v());
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setPlanYearHolidayFrameNo(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameNo)
	 */
	@Override
	public void setPlanYearHolidayFrameNo(PlanYearHolidayFrameNo planYearHolidayFrNo) {
		this.kscstPlanYearHdFrame.getKscstPlanYearHdFramePK().setPlanYearHdNo(planYearHolidayFrNo.v().shortValue());
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setUseClassification(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr)
	 */
	@Override
	public void setUseClassification(NotUseAtr useAtr) {
		this.kscstPlanYearHdFrame.setUseAtr((short)useAtr.value);
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setPlanYearHolidayFrameName(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameName)
	 */
	@Override
	public void setPlanYearHolidayFrameName(PlanYearHolidayFrameName planYearHolidayFrName) {
		this.kscstPlanYearHdFrame.setPlanYearHdName(planYearHolidayFrName.v());
	}

}
