package nts.uk.ctx.at.record.app.command.kdp.kdps01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.location.GeoCoordinate;

/**
 * 
 * @author sonnlb
 *
 *         地理座標
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoCoordinateCommand {
	/** 緯度 */
	private double latitude;

	/** 経度 */
	private double longitude;

	public GeoCoordinate toDomainValue() {

		return new GeoCoordinate(this.latitude, this.longitude);
	}
}
