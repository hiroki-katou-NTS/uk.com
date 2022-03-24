package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.gul.location.GeoCoordinate;

/**
 * 携帯打刻可能範囲
 * @author tutk
 *
 */
@Value
public class StampMobilePossibleRange implements DomainValue {
	/**
	 * 半径
	 */
	private RadiusAtr radius;
	
	/**
	 * 地理座標
	 */
	private GeoCoordinate geoCoordinate;

	public StampMobilePossibleRange(RadiusAtr radius, GeoCoordinate geoCoordinate) {
		super();
		this.radius = radius;
		this.geoCoordinate = geoCoordinate; 
	}
	
	/**
	 * 打刻範囲内であるか
	 * @param geoCoordinateInput
	 * @return
	 */
	public boolean checkWithinStampRange(GeoCoordinate geoCoordinateInput) {
		int value = (int) this.geoCoordinate.getDistanceAsMeter(geoCoordinateInput);
		return !(value > this.radius.getValue());
	}
}
