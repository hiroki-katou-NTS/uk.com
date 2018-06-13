package nts.uk.ctx.at.shared.dom.era.name;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class EraNameDom extends AggregateRoot {
	
	/** The era name id. */
	private String eraNameId;

	/** The end date. */
	private GeneralDate endDate;

	/** The era name. */
	private EraName eraName;

	/** The start date. */
	private GeneralDate startDate;

	/** The symbol. */
	private String symbol;

	/** The system type. */
	private SystemType systemType;

	public EraNameDom(EraNameDomGetMemento memento) {
		this.eraNameId = memento.getEraNameId();		
		this.endDate = memento.getEndDate();
		this.eraName = memento.getEraName();
		this.startDate = memento.getStartDate();
		this.symbol = memento.getSymbol();
		this.systemType = memento.getSystemType();
	}
	
	public void saveToMemento(EraNameDomSetMemento memento) {
		memento.setEraNameId(this.eraNameId);
		memento.setEndDate(this.endDate);
		memento.setEraName(this.eraName);
		memento.setStartDate(this.startDate);
		memento.setSymbol(this.symbol);
		memento.setSystemType(this.systemType);
	}
	
	@Override
	public void validate() {
		super.validate();
	}

}
