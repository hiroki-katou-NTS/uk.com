package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto.PCLogOnInforOfDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績のPCログオン情報 Finder */
@Stateless
public class PCLogOnInforOfDailyPerformFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public PCLogOnInforOfDailyPerformDto find() {
		// TODO Auto-generated method stub
		return new PCLogOnInforOfDailyPerformDto();
	}
}
