package nts.uk.ctx.pr.proto.dom.personalinformation.holiday;

import java.util.List;

/**
 * @author hungnm
 *
 */
public interface HolidayPaidRepository {
	
	/**
	 * 
	 * @param companyCode
	 * @param personIdList
	 * @return List HolidayPaid of list person
	 */
	List<HolidayPaid> find(String companyCode, List<String> personIdList);
	
}
