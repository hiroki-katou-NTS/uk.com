package nts.uk.ctx.at.record.pub.worklocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public class WorkLocationPubExport extends AggregateRoot {
	/**
	 * 会社CD
	 */
	private String companyID;
	/**
	 * 場所CD
	 */
	private String workLocationCD;
	/**
	 * 場所名
	 */
	private String workLocationName;
	/**
	 * 
	 */
	private String horiDistance;
	/**
	 * 
	 */
	private String vertiDistance;
	/**
	 * 
	 */
	private String latitude;
	/**
	 * 
	 */
	private String longitude;

	public static WorkLocationPubExport createSimpleFromJavaType(String companyID, String workLocationCD,
			String workLocationName, String horiDistance, String vertiDistance, String latitude, String longitude) {
		return new WorkLocationPubExport(companyID, workLocationCD, workLocationName, horiDistance, vertiDistance,
				latitude, longitude);
	}
}
