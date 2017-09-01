package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 
 * @author Doan Duy Hung
 *
 */
/**
 * 
 * 出退勤申請
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ApplicationStampWork {
	private StampAtr stampAtr;
	
	/**
	 * 打刻枠No
	 */
	private Integer stampFrameNo;
	
	private StampGoOutReason stampGoOutReason;
	
	/**
	 * 応援カード
	 */
	private String supportCard;
		
	/**
	 * 応援場所
	 */
	private String supportLocationCD;
	
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

	public ApplicationStampWork(StampAtr stampAtr, Integer stampFrameNo, StampGoOutReason stampGoOutReason, String supportCard, 
			String supportLocationCD, Integer startTime, String startLocation, Integer endTime, String endLocation) {
		super();
		this.stampAtr = stampAtr;
		this.stampFrameNo = stampFrameNo;
		this.stampGoOutReason = stampGoOutReason;
		this.supportCard = supportCard;
		this.supportLocationCD = supportLocationCD;
		this.startTime = startTime;
		this.startLocation = startLocation;
		this.endTime = endTime;
		this.endLocation = endLocation;
	}
}
