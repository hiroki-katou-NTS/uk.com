package nts.uk.ctx.at.shared.infra.repository.era.name;

import java.math.BigDecimal;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.era.name.EraName;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomSetMemento;
import nts.uk.ctx.at.shared.dom.era.name.SystemType;
import nts.uk.ctx.at.shared.infra.entity.era.name.CisdtEraName;

public class JpaEraNameSetMemento implements EraNameDomSetMemento {
	
	private CisdtEraName entity;
	
	public JpaEraNameSetMemento() {
		super();
	}

	public JpaEraNameSetMemento(CisdtEraName entity) {
		this.entity = entity;
	}

	@Override
	public void setEraNameId(String eraNameId) {
		this.entity.setEraNameId(eraNameId);
	}

	@Override
	public void setEndDate(GeneralDate endDate) {
		this.entity.setEndDate(endDate);
	}

	@Override
	public void setEraName(EraName eraName) {
		this.entity.setEraName(eraName.toString());
	}

	@Override
	public void setStartDate(GeneralDate startDate) {
		this.entity.setStartDate(startDate);
	}

	@Override
	public void setSymbol(String symbol) {
		this.entity.setSymbol(symbol);
	}

	@Override
	public void setSystemType(SystemType systemType) {
		this.entity.setSystemType(BigDecimal.valueOf(systemType.value));
	}
}
