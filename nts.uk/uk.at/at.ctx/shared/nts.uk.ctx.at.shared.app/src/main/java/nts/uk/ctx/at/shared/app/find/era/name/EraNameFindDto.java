package nts.uk.ctx.at.shared.app.find.era.name;

import lombok.Data;

@Data
public class EraNameFindDto {
	
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
	 * Instantiates a new era name find dto.
	 *
	 * @param eraId the era id
	 * @param eraName the era name
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param eraSymbol the era symbol
	 * @param systemType the system type
	 */
	public EraNameFindDto(String eraId, String eraName, String startDate, String endDate, String eraSymbol,
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
	 * Instantiates a new era name find dto.
	 */
	public EraNameFindDto() {
		super();
	}
	
	
}