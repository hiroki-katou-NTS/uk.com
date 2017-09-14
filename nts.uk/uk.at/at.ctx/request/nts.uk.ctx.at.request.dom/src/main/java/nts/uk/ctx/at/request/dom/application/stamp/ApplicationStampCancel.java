package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author Doan Duy Hung
 *
 */
/**
 * 
 * 打刻取消申請
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ApplicationStampCancel extends DomainObject {
	private StampAtr stampAtr;
	
	/**
	 * 打刻枠No
	 */
	private Integer stampFrameNo;
	
	/**
	 * 実績取消
	 */
	private Integer cancelAtr;

	public ApplicationStampCancel(StampAtr stampAtr, Integer stampFrameNo, Integer cancelAtr) {
		super();
		this.stampAtr = stampAtr;
		this.stampFrameNo = stampFrameNo;
		this.cancelAtr = cancelAtr;
	}
}
