package workplace;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;

@Value
@EqualsAndHashCode(callSuper= false)
/**
 * 
 * @author hieult
 *
 */
public class WorkPlace extends AggregateRoot {
	
	/** Company ID */
	private final String companyID;
	
	/** Work Location CD */
	private String workLocationCD;    

	/** Work Location Name */
	private String workLocationName; 
	
	/** Horizontal distance */
	private String horiDistance;
	
	/** Vertical  distance */
	private String vertiDistance;
	
	/**Latitude */
	private Latitude latitude;
	
		/**Longitude */
	private Longitude  longitude;
	
	public static WorkPlace createFromJavaType (String companyID, String workLocationCD, String workLocationName, String horiDistance, String vertiDistance, String latitude, String longitude){
		return new WorkPlace(companyID,
				workLocationCD,
				workLocationName,
				horiDistance,
				vertiDistance,
				new Latitude(latitude), new Longitude(longitude));
	}
}
