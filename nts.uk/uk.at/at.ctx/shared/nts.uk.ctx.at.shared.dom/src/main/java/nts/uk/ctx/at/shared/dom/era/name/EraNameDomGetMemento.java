package nts.uk.ctx.at.shared.dom.era.name;

import nts.arc.time.GeneralDate;

public interface EraNameDomGetMemento {

	String getEraNameId();

	GeneralDate getEndDate();

	EraName getEraName();

	GeneralDate getStartDate();

	String getSymbol();

	SystemType getSystemType();

}
