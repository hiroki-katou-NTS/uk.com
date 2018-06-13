package nts.uk.ctx.at.shared.dom.era.name;

import nts.arc.time.GeneralDate;

public interface EraNameDomSetMemento {
	
	void setEraNameId(String eraNameId);

	void setEndDate(GeneralDate endDate);

	void setEraName(EraName eraName);

	void setStartDate(GeneralDate startDate);

	void setSymbol(String symbol);

	void setSystemType(SystemType systemType);

}
