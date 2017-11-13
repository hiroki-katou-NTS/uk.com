package nts.uk.ctx.at.record.dom.workrecord.identificationstatus;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 本人確認 - root
 *
 */
@Getter
public class Identification extends AggregateRoot {
	
	private String employeeId;
	
	private GeneralDate processingYmd;
	
	private GeneralDate indentificationYmd;

}
