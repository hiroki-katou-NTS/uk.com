package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import java.util.Optional;

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
	private Optional<GeoCoordinate> geoCoordinate;

	public StampMobilePossibleRange(RadiusAtr radius, GeoCoordinate geoCoordinate) {
		super();
		this.radius = radius;
		this.geoCoordinate = Optional.ofNullable(geoCoordinate); 
	}
	
	/**
	 * 打刻範囲内であるか
	 * @param geoCoordinateInput
	 * @return
	 */
	public boolean checkWithinStampRange(GeoCoordinate geoCoordinateInput) {
		if (!this.geoCoordinate.isPresent()) {
			return true;
		}
		int value = (int) this.geoCoordinate.get().getDistanceAsMeter(geoCoordinateInput);
		return !(value > this.radius.getValue());
	}
}
