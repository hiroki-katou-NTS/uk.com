package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.Value;
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
			
			/** 実績取消区分 */
			private int actualCancelAtr;
			
			/** 早退1 */
			private int early1;
			
			/** 早退時刻1 */
			private String earlyTime1;
			
			/** 遅刻1 */
			private int late1;
			
			/** 遅刻時刻1 */
			private String lateTime1;
			
			/** 早退2 */
			private int early2;
			
			/** 早退時刻2 */
			private String earlyTime2;
			
			/** 遅刻2 */
			private int late2;
			
			/** 遅刻時刻2 */
			private String lateTime2;
			
		public static	LateOrLeaveEarlyDto fromDomain ( LateOrLeaveEarly domain){
			return new LateOrLeaveEarlyDto(
					domain.getCompanyID(),
					domain.getAppID(),
					domain.getActualCancelAtr().value,
					domain.getEarly1().value,
					domain.getEarlyTime1().toString(),
					domain.getLate1().value,
					domain.getLateTime1().toString(),
					domain.getEarly2().value,
					domain.getEarlyTime2().toString(),
					domain.getLate2().value,
					domain.getLateTime2().toString()
					);
		}
}
