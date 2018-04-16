package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParamIdentityConfirmDay {
	private String employeeId;
	private List<SelfConfirmDay> selfConfirmDay;
}
