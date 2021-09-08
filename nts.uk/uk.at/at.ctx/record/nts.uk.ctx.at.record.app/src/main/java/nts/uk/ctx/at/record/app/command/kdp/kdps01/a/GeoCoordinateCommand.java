package nts.uk.ctx.at.record.app.command.kdp.kdps01.a;

import java.math.BigDecimal;

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
	private BigDecimal latitude;

	/** 経度 */
	private BigDecimal longitude;

	public GeoCoordinate toDomainValue() {
		if (this.latitude == null && this.longitude == null) {
			return null;
		}
		return new GeoCoordinate(this.latitude.doubleValue(), this.longitude.doubleValue());
	}
}
