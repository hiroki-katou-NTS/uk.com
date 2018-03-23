package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class SelfConfirmDay {
	private GeneralDate date;
	private Boolean value;
}
