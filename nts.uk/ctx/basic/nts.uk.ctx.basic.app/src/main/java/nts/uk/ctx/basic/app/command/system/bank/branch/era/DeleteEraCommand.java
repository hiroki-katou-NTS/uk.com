package nts.uk.ctx.basic.app.command.system.bank.branch.era;

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
	private LocalDate startDate;
	//private Date endDate;
	//private int fixAttribute;
	
	public Era toDomain(){
		Era domain = Era.createFromDataType1(
				this.startDate == null ? null : GeneralDate.localDate(this.startDate));
		return domain;
	}
	
}
