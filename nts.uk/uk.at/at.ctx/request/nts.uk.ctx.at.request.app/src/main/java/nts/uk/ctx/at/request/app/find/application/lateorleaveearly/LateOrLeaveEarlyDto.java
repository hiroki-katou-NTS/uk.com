package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
/**
 * 
 * @author hieult
 *
 */
@Value
public class LateOrLeaveEarlyDto {

		/** 会社ID */
		private String companyID;
		
		/** 申請ID */
		private String appID;
		
		private Long version;
		
		private int postAtr;
		
        /** 申請日*/
		private GeneralDate applicationDate;
		
		/** 実績取消区分 */
		
		private int actualCancelAtr;
		
		/** 早退1 */
		private int early1;
		
		/** 早退時刻1 */
		private int earlyTime1;
		
		/** 遅刻1 */
		private int late1;
		
		/** 遅刻時刻1 */
		private int lateTime1;
		
		/** 早退2 */
		private int early2;
		
		/** 早退時刻2 */
		private int earlyTime2;
		
		/** 遅刻2 */
		private int late2;
		
		/** 遅刻時刻2 */
		private int lateTime2;
				
		/** 申請理由 appReason */
		private String appReason;
				
		public static LateOrLeaveEarlyDto fromDomain(LateOrLeaveEarly domain){
			return new LateOrLeaveEarlyDto(
					domain.getApplication().getCompanyID(),
					domain.getApplication().getAppID(),
					domain.getVersion(),
					domain.getApplication().getPrePostAtr().value,
					domain.getApplication().getAppDate(),
					domain.getActualCancelAtr(),
					domain.getEarly1().value,
					domain.getEarlyTime1().v(),
					domain.getLate1().value,
			    	domain.getLateTime1().v(),			    
			     	domain.getEarly2().value,
					domain.getEarlyTime2().v(),
			    	domain.getLate2().value,
			    	domain.getLateTime2().v(),
			    	domain.getApplication().getAppReason().v());
		}
}
