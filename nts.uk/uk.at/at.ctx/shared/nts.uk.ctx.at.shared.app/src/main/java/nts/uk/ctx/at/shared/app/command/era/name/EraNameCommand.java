package nts.uk.ctx.at.shared.app.command.era.name;

import java.util.Date;

import lombok.Data;

@Data
public class EraNameCommand{
	private String eraId;
	private String eraName;
	private Date startDate;
	private Date endDate;
	private String eraSymbol;
//	private boolean 
}

