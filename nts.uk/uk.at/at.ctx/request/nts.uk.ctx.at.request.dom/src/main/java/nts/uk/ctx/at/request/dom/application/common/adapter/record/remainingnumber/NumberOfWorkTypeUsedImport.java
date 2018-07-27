package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class NumberOfWorkTypeUsedImport {
	private String workTypeCode;
	
	private Double attendanceDaysMonth;
}
