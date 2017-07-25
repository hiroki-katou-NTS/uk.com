package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class AttendanceItemFinder {
	
	public AttendanceItemDto find(){
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();	
		
		//乖離時間 KMKMT_DIVERGENCE_TIME - hoatt
		
		//加給時間項目 KBPST_BP_TIME_ITEM - hungnm
		
		//割増項目 KMNMT_PREMIUM_ITEM - hungdd
		
		//特定加給時間項目 - KBPST_BP_TIME_ITEM - dũng
		
		//任意項目
		
		//回数集計
		
		return null;
	}

}
