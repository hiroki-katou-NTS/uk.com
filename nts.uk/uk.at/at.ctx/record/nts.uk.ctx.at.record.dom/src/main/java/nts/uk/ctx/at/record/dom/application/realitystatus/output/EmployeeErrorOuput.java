package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 *
 */
@AllArgsConstructor
@Value
public class EmployeeErrorOuput {
	/** 処理年月日: 年月日*/
	private GeneralDate date;
	
	private Boolean hasError;
}
