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
		private Integer earlyTime1;
		
		/** 遅刻1 */
		private int late1;
		
		/** 遅刻時刻1 */
		private Integer lateTime1;
		
		/** 早退2 */
		private int early2;
		
		/** 早退時刻2 */
		private Integer earlyTime2;
		
		/** 遅刻2 */
		private int late2;
		
		/** 遅刻時刻2 */
		private Integer lateTime2;
				
		/** 申請理由 appReason */
		private String appReason;
				
		public static LateOrLeaveEarlyDto fromDomain(LateOrLeaveEarly domain, Long version){
//			return new LateOrLeaveEarlyDto(
//					domain.getApplication().getCompanyID(),
//					domain.getApplication().getAppID(),
//					version,
//					domain.getApplication().getPrePostAtr().value,
//					domain.getApplication().getAppDate(),
//					domain.getActualCancelAtr(),
//					domain.getEarly1().value,
//					domain.getEarlyTime1AsMinutes(),
//					domain.getLate1().value,
//			    	domain.getLateTime1AsMinutes(),			    
//			     	domain.getEarly2().value,
//					domain.getEarlyTime2AsMinutes(),
//			    	domain.getLate2().value,
//			    	domain.getLateTime2AsMinutes(),
//			    	domain.getApplication().getAppReason().v());
			return null;
		}
}
