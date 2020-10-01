package nts.uk.screen.at.app.schedule.personal.correction;

import javax.ejb.Stateless;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author anhdt
 * 	送る期間を取得する
 */
@Stateless
public class GetSendingPeriodQuery {
	
    public DatePeriod getSendingPeriod(DatePeriod currentPeriod, boolean willBeAdvanced, boolean cycle28Day) {
    	DatePeriod result = null;
    	
    	if(cycle28Day) {
    		// 28日周期か == true <call> 年月日 現在の期間．終了日+1
    		result = currentPeriod.newSpan(currentPeriod.start(), currentPeriod.end().addDays(1));
    	} else {
    		// 期間の開始日、終了日にそれぞれ「月を足す」を実行する
    		if(willBeAdvanced) {
    			result = currentPeriod.newSpan(currentPeriod.start().addMonths(1), currentPeriod.end().addMonths(1));
    		} else {
    			result = currentPeriod.newSpan(currentPeriod.start().addMonths(-1), currentPeriod.end().addMonths(-1));
    		}
    	}
    	
		return result;
	}
	
}
