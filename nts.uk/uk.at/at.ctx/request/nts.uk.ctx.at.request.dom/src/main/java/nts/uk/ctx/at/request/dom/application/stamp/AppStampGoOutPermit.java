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
 * 外出許可申請
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class AppStampGoOutPermit extends DomainObject {
	
	private StampAtr stampAtr;
	
	/**
	 * 打刻枠No
	 */
	private Integer stampFrameNo;
	
	private StampGoOutAtr stampGoOutAtr;
	
	/**
	 * 開始時刻
	 */
	private Integer startTime;
	
	/**
	 * 開始場所
	 */
	private String startLocation;
	
	/**
	 * 終了時刻
	 */
	private Integer endTime;
	
	/**
	 * 終了場所
	 */
	private String endLocation;
	
	public AppStampGoOutPermit(StampAtr stampAtr, Integer stampFrameNo, StampGoOutAtr stampGoOutAtr,
			Integer startTime, String startLocation, Integer endTime, String endLocation) {
		super();
		this.stampAtr = stampAtr;
		this.stampFrameNo = stampFrameNo;
		this.stampGoOutAtr = stampGoOutAtr;
		this.startTime = startTime;
		this.startLocation = startLocation;
		this.endTime = endTime;
		this.endLocation = endLocation;
	}
}
