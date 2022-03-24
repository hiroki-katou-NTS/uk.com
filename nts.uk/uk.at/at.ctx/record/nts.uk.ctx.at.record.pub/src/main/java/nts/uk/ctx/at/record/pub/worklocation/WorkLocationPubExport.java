package nts.uk.ctx.at.record.pub.worklocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public class WorkLocationPubExport extends AggregateRoot {
	/**
	 * thay CID thành contractCode
	 */
	private String contractCode;
	/**
	 * 場所CD
	 */
	private String workLocationCD;
	/**
	 * 場所名
	 */
	private String workLocationName;
//	/**
//	 * 
//	 */
//	private String horiDistance;
//	/**
//	 * 
//	 */
//	private String vertiDistance;
	/**
	 * bỏ dài rộng, thay bằng bán kính (Enum Radius)
	 */
	private Integer radius;
	/**
	 * 
	 */
	private Double latitude;
	/**
	 * 
	 */
	private Double longitude;

	public static WorkLocationPubExport createSimpleFromJavaType(String contractCode, String workLocationCD,
			String workLocationName, Integer radius, Double latitude, Double longitude) {
		return new WorkLocationPubExport(contractCode, workLocationCD, workLocationName, radius,
				latitude, longitude);
	}
}
