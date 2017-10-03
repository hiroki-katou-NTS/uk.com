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
		
		 /** 申請理由ID */
			private String appReasonID;
		
		/** 申請者*/
		private String applicantName;
		
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
			String appReasonID = domain.getApplicationReason().v().split(":")[0];
			String appReasonString = domain.getApplicationReason().v().substring(appReasonID.length() + 1);
			return new LateOrLeaveEarlyDto(
					domain.getCompanyID(),
					domain.getAppID(),
					appReasonID,
					//Get ApplicationName
					domain.getApplicantSID(),
					domain.getApplicationDate(),
					domain.getActualCancelAtr(),
					domain.getEarly1().value,
					domain.getEarlyTime1().minute(),
					domain.getLate1().value,
			    	domain.getLateTime1().minute(),
			     	domain.getEarly2().value,
					domain.getEarlyTime2().minute(),
			    	domain.getLate2().value,
			    	domain.getLateTime2().minute(),
			    	appReasonString);
			
	
		
		}
}
