package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.gul.location.GeoCoordinate;

/**
 * VO : 打刻場所情報
 * @author tutk
 *
 */
@Value
public class StampLocationInfor implements DomainValue {
	/**
	 * エリア外の打刻区分
	 */
	private final boolean outsideAreaAtr;
	
	/**
	 * 打刻位置情報
	 */
	private final GeoCoordinate positionInfor;

	public StampLocationInfor(boolean outsideAreaAtr, GeoCoordinate positionInfor) {
		super();
		this.outsideAreaAtr = outsideAreaAtr;
		this.positionInfor = positionInfor;
	}

	
	
}
