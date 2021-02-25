package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;

@Getter
/** 作業集計枠明細 */
public class OuenWorkAggregateFrameDetail implements DomainObject {

	/**　集計枠No: int　*/
	private int frameNo;

	/**　集計明細: 応援作業集計明細　*/
	private OuenWorkAggregateDetail detail;

	private OuenWorkAggregateFrameDetail(int frameNo, OuenWorkAggregateDetail detail) {
		super();
		this.frameNo = frameNo;
		this.detail = detail;
	}
	
	public static OuenWorkAggregateFrameDetail create(int frameNo, OuenWorkAggregateDetail detail) {

		return new OuenWorkAggregateFrameDetail(frameNo, detail);
	}
}
