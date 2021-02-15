package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.use;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.use.KshmtAutoUseUnitSet;

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
	public ApplyAtr getUseJobSet() {
		return  ApplyAtr.valueOf(this.entity.getJobCalSet());
	}

	@Override
	public ApplyAtr getUseWkpSet() {
		return  ApplyAtr.valueOf(this.entity.getWkpCalSet()) ;
	}

	@Override
	public ApplyAtr getUseJobwkpSet() {
		return ApplyAtr.valueOf(this.entity.getWkpJobCalSet());
	}

}
