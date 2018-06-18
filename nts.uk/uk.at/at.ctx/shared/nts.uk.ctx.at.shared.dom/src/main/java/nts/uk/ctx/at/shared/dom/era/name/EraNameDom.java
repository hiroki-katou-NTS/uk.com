package nts.uk.ctx.at.shared.dom.era.name;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * The Class EraNameDom.
 */
//元号
@Getter
@Setter
public class EraNameDom extends AggregateRoot {
	
	/** The era name id. */
	//元号ID
	private String eraNameId;

	/** The end date. */
	//終了年月日
	private GeneralDate endDate;

	/** The era name. */
	//元号
	private EraName eraName;

	/** The start date. */
	//開始年月日
	private GeneralDate startDate;

	/** The symbol. */
	//記号
	private SymbolName symbol;

	/** The system type. */
	//システム規定区分
	private SystemType systemType;

	/**
	 * Instantiates a new era name dom.
	 *
	 * @param memento the memento
	 */
	public EraNameDom(EraNameDomGetMemento memento) {
		this.eraNameId = memento.getEraNameId();		
		this.endDate = memento.getEndDate();
		this.eraName = memento.getEraName();
		this.startDate = memento.getStartDate();
		this.symbol = memento.getSymbol();
		this.systemType = memento.getSystemType();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EraNameDomSetMemento memento) {
		memento.setEraNameId(this.eraNameId);
		memento.setEndDate(this.endDate);
		memento.setEraName(this.eraName);
		memento.setStartDate(this.startDate);
		memento.setSymbol(this.symbol);
		memento.setSystemType(this.systemType);
	}
	
	public static EraNameDom createFromJavaType() {
		return new EraNameDom();
	}
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
	}

}
