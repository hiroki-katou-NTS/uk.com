package nts.uk.ctx.at.shared.infra.repository.era.name;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.era.name.EraName;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomGetMemento;
import nts.uk.ctx.at.shared.dom.era.name.SystemType;
import nts.uk.ctx.at.shared.infra.entity.era.name.CisdtEraName;

public class JpaEraNameGetMemento implements EraNameDomGetMemento {
	
	private CisdtEraName entity;
	
	public JpaEraNameGetMemento(CisdtEraName entity) {
		super();
		this.entity = entity;
	}

	public JpaEraNameGetMemento() {
		super();
	}

	@Override
	public String getEraNameId(){
		return this.entity.getEraNameId();
	};

	@Override
	public GeneralDate getEndDate(){
		return this.entity.getEndDate();
	};

	@Override
	public EraName getEraName(){
		return new EraName(this.entity.getEraName());
	};
	
	@Override
	public GeneralDate getStartDate(){
		return this.entity.getStartDate();
	};
	
	@Override
	public String getSymbol(){
		return this.entity.getSymbol();
	};
	
	@Override
	public SystemType getSystemType(){
		return SystemType.valueOf(this.entity.getSystemType().intValue());
	};

}
