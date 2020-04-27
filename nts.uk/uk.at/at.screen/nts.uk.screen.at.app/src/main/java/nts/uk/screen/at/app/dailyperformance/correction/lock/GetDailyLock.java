package nts.uk.screen.at.app.dailyperformance.correction.lock;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.DailyLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.IGetDailyLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.StatusActualDay;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.StatusLock;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX 日の実績の状況を取得する
 */
@Stateless
public class GetDailyLock implements IGetDailyLock {

	@Inject
	private DPLock dpLock;

	@Override
	public DailyLock getDailyLock(StatusActualDay satusActual) {
		String comapnyId = AppContexts.user().companyId();
		DPLockDto dpLockDto = dpLock.checkLockAll(comapnyId, satusActual.getEmployeeId(), satusActual.getDate());
		boolean lockDay = dpLock.checkLockDay(dpLockDto.getLockDayAndWpl(), satusActual.getClosureId(),
				satusActual.getEmployeeId(), satusActual.getDate());
		boolean lockWpl = dpLock.checkLockWork(dpLockDto.getLockDayAndWpl(), satusActual.getWplId(),
				satusActual.getEmployeeId(), satusActual.getDate());
		boolean lockHist = dpLock.lockHist(dpLockDto.getLockHist(), satusActual.getEmployeeId(), satusActual.getDate());

		return new DailyLock(satusActual.getEmployeeId(), satusActual.getDate(),
				lockDay ? StatusLock.LOCK : StatusLock.UN_LOCK, lockWpl ? StatusLock.LOCK : StatusLock.UN_LOCK,
				StatusLock.UN_LOCK, StatusLock.UN_LOCK, StatusLock.UN_LOCK, StatusLock.UN_LOCK,
				lockHist ? StatusLock.LOCK : StatusLock.UN_LOCK);
	}

}
