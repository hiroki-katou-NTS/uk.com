package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelfConfirm {
	private String employeeID;
	private boolean selfConfirm;
}
