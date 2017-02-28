package nts.uk.ctx.basic.app.command.system.era;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.system.era.Era;

@Getter
@Setter
public class DeleteEraCommand {
	//private String eraName;
	//private String eraMark;
	private GeneralDate startDate;
	//private Date endDate;
	//private int fixAttribute;
	
	public Era toDomain(){
		Era domain = Era.createFromDataType1(
				this.startDate);
		return domain;
	}
	
}
