package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * VO : 打刻場所情報
 * @author tutk
 *
 */
@Value
public class StampLocationInfor implements DomainObject {
	/**
	 * エリア外の打刻区分
	 */
	private final boolean outsideAreaAtr;
	
	/**
	 * 打刻位置情報
	 */
	private final int positionInfor; //TODO:

	public StampLocationInfor(boolean outsideAreaAtr, int positionInfor) {
		super();
		this.outsideAreaAtr = outsideAreaAtr;
		this.positionInfor = positionInfor;
	}
	
}
