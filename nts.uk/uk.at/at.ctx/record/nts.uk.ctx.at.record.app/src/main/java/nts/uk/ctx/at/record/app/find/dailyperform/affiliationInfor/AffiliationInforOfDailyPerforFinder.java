package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績の所属情報 Finder*/
@Stateless
public class AffiliationInforOfDailyPerforFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public AffiliationInforOfDailyPerforDto find() {
		// TODO Auto-generated method stub
		return new AffiliationInforOfDailyPerforDto();
	}

}
