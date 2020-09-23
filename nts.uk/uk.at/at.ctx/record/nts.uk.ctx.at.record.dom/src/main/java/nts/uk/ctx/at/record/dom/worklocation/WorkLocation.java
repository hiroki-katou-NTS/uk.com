package nts.uk.ctx.at.record.dom.worklocation;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

@Value
@EqualsAndHashCode(callSuper= false)
/**
 * 
 * @author hieult
 *
 */
	/**	勤務場所 **/
public class WorkLocation extends AggregateRoot {
	
	/** Company ID */
	private final String companyID;
	
	/** Work Location CD */
	private WorkLocationCD workLocationCD;    

	/** Work Location Name */
	private WorkLocationName workLocationName; 
	
	/** Horizontal distance */
	private String horiDistance;
	
	/** Vertical  distance */
	private String vertiDistance;
	
	/**Latitude */
	private Latitude latitude;
	
		/**Longitude */
	private Longitude  longitude;
	
	public static WorkLocation createFromJavaType (String companyID, String workLocationCD, String workLocationName, String horiDistance, String vertiDistance, String latitude, String longitude){
		return new WorkLocation(companyID,
				new WorkLocationCD(workLocationCD),
				new WorkLocationName(workLocationName),
				horiDistance,
				vertiDistance,
				new Latitude(latitude), new Longitude(longitude));
	}
}
