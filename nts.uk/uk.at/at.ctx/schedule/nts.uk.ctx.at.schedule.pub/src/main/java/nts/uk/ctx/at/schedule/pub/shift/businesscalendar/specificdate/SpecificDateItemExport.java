package nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificDateItemExport {
	
	/**
	 * companyId
	 */
	private String companyId;
	
	/**
	 * useAtr
	 */
	private int useAtr;
	
	/**
	 * specificDateItemNo
	 */
	private Integer specificDateItemNo;
	
	/**
	 * specificName
	 */
	private String specificName;
}
