package nts.uk.screen.at.app.query.kdp.kdp001.a;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;

@AllArgsConstructor
public class StampLocationInforDto {
	/**
	 * 打刻位置情報
	 */
	@Getter
	private final GeoCoordinate positionInfor;

	/**
	 * エリア外の打刻区分
	 */
	@Getter
	private final boolean outsideAreaAtr;

	public static StampLocationInforDto fromDomain(Optional<StampLocationInfor> locationInfor) {
		return locationInfor.map(x -> new StampLocationInforDto(x.getPositionInfor(), x.isOutsideAreaAtr()))
				.orElse(null);
	}
}
