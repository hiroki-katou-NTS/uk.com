package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.UseClassification;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.UseUnitAutoCalSettingGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoUseUnitSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

public class JpaUseUnitAutoCalSettingGetMemento implements UseUnitAutoCalSettingGetMemento{


	/** The entity. */
	private KshmtAutoUseUnitSet entity;

	/**
	 * Instantiates a new jpa wkp auto cal setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaUseUnitAutoCalSettingGetMemento(KshmtAutoUseUnitSet entity) {
		this.entity = entity;
	}

	
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getCid());
	}

	@Override
	public UseClassification getUseJobSet() {
		return  UseClassification.valueOf(this.entity.getJobCalSet());
	}

	@Override
	public UseClassification getUseWkpSet() {
		return  UseClassification.valueOf(this.entity.getWkpCalSet()) ;
	}

	@Override
	public UseClassification getUseJobwkpSet() {
		return UseClassification.valueOf(this.entity.getWkpJobCalSet());
	}

}
