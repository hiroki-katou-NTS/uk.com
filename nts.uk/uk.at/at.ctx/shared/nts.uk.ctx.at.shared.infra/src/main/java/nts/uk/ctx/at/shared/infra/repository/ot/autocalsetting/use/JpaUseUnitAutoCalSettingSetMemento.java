package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.use;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.UseClassification;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.use.UseUnitAutoCalSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.use.KshmtAutoUseUnitSet;

public class JpaUseUnitAutoCalSettingSetMemento implements UseUnitAutoCalSettingSetMemento {
	
	/** The entity. */
	private KshmtAutoUseUnitSet entity;

	/**
	 * Instantiates a new jpa wkp auto cal setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaUseUnitAutoCalSettingSetMemento(KshmtAutoUseUnitSet entity) {
		this.entity = entity;
	}


	@Override
	public void setUseJobSet(UseClassification useJobSet) {
		this.entity.setJobCalSet(useJobSet.value);
		
		
	}

	@Override
	public void setUseWkpSet(UseClassification useWkpSet) {
		this.entity.setWkpCalSet(useWkpSet.value);
		
	}

	@Override
	public void setUseJobwkpSet(UseClassification useJobwkpSet) {
		this.entity.setWkpJobCalSet(useJobwkpSet.value);
	}

	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

}
