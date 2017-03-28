package nts.uk.ctx.basic.app.command.system.era;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.system.era.Era;

@Data
@NoArgsConstructor
public class AddEraCommand {
	private String eraName;
	private String eraMark;
	private Date startDate;
	private Date endDate;
	private int fixAttribute;

	public Era toDomain() {
		Era domain = Era.createFromDataType(this.eraName, this.eraMark,
				this.startDate == null ? null : GeneralDate.legacyDate(this.startDate),
				this.endDate == null ? null : GeneralDate.legacyDate(this.endDate), this.fixAttribute);
		return domain;
	}
}
