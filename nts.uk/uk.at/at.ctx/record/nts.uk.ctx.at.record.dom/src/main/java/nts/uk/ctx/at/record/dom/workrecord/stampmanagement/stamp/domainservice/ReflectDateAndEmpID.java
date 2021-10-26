package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author thanh_nx
 *
 *         反映する日と社員ID
 */
@AllArgsConstructor
@Getter
public class ReflectDateAndEmpID {
	
	//年月日
	private GeneralDate date;
	
	//社員ID
	private String sid;

}
