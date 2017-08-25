package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 
 * @author Doan Duy Hung
 *
 */
/**
 * 
 * 打刻申請
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ApplicationStamp extends AggregateRoot{
	/**
	 * 打刻申請モード
	 */
	private StampRequestMode stampRequestMode;
	
	private List<ApplicationStampGoOutPermit> applicationStampGoOutPermits;
	
	private List<ApplicationStampWork> applicationStampWorks;
	
	private List<ApplicationStampCancel> applicationStampCancels;
	
	private ApplicationStampOnlineRecord applicationStampOnlineRecords;

	public ApplicationStamp(StampRequestMode stampRequestMode,
			List<ApplicationStampGoOutPermit> applicationStampGoOutPermits,
			List<ApplicationStampWork> applicationStampWorks, List<ApplicationStampCancel> applicationStampCancels,
			ApplicationStampOnlineRecord applicationStampOnlineRecords) {
		super();
		this.stampRequestMode = stampRequestMode;
		this.applicationStampGoOutPermits = applicationStampGoOutPermits;
		this.applicationStampWorks = applicationStampWorks;
		this.applicationStampCancels = applicationStampCancels;
		this.applicationStampOnlineRecords = applicationStampOnlineRecords;
	}
}
