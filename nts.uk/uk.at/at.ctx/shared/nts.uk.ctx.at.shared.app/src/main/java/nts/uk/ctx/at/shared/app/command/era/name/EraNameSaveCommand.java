package nts.uk.ctx.at.shared.app.command.era.name;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.era.name.EraName;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDom;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomGetMemento;
import nts.uk.ctx.at.shared.dom.era.name.SymbolName;
import nts.uk.ctx.at.shared.dom.era.name.SystemType;

@Data
public class EraNameSaveCommand {
	
	/** The era id. */
	private String eraId;
	
	/** The era name. */
	private String eraName;
	
	/** The start date. */
	private String startDate;
	
	/** The end date. */
	private String endDate;
	
	/** The era symbol. */
	private String eraSymbol;
	
	/** The system type. */
	private Integer systemType;

	/**
	 * Instantiates a new era name command.
	 *
	 * @param eraId the era id
	 * @param eraName the era name
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param eraSymbol the era symbol
	 * @param systemType the system type
	 */
	public EraNameSaveCommand(String eraId, String eraName, String startDate, String endDate, String eraSymbol,
			Integer systemType) {
		super();
		this.eraId = eraId;
		this.eraName = eraName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.eraSymbol = eraSymbol;
		this.systemType = systemType;
	}

	/**
	 * Instantiates a new era name command.
	 */
	public EraNameSaveCommand() {
		super();
	}
	
	public EraNameDom toDomain() {
		return new EraNameDom(new EraNameGetMementoIml(this));
	}
	
	class EraNameGetMementoIml implements EraNameDomGetMemento {
		
		private EraNameSaveCommand command;

		public EraNameGetMementoIml(EraNameSaveCommand command) {
			super();
			this.command = command;
		}

		@Override
		public String getEraNameId() {
			return command.getEraId();
		}

		@Override
		public GeneralDate getEndDate() {
			return null;
		}

		@Override
		public EraName getEraName() {
			return new EraName(command.getEraName());
		}

		@Override
		public GeneralDate getStartDate() {
			return GeneralDate.fromString(command.getStartDate(),"yyyy/MM/dd");
		}

		@Override
		public SymbolName getSymbol() {
			return new SymbolName(command.getEraSymbol());
		}

		@Override
		public SystemType getSystemType() {
			return SystemType.valueOf(command.getSystemType());
		}
		
	}
}

