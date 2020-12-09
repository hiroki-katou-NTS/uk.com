package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.use;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.use.KrcmtCalcSetUnitSet;

public class JpaUseUnitAutoCalSettingSetMemento implements UseUnitAutoCalSettingSetMemento {

	/** The entity. */
	private KrcmtCalcSetUnitSet entity;

	/**
	 * Instantiates a new jpa wkp auto cal setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaUseUnitAutoCalSettingSetMemento(KrcmtCalcSetUnitSet entity) {
		this.entity = entity;
	}

	@Override
	public void setUseJobSet(ApplyAtr useJobSet) {
		this.entity.setJobCalSet(useJobSet.value);

	}

	@Override
	public void setUseWkpSet(ApplyAtr useWkpSet) {
		this.entity.setWkpCalSet(useWkpSet.value);

	}

	@Override
	public void setUseJobwkpSet(ApplyAtr useJobwkpSet) {
		this.entity.setWkpJobCalSet(useJobwkpSet.value);
	}

	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

}
