package nts.uk.ctx.at.request.infra.repository.application.lateleaveearly;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
import nts.uk.ctx.at.request.infra.entity.application.lateleaveearly.KrqdtAppLateOrLeavePK_New;
import nts.uk.ctx.at.request.infra.entity.application.lateleaveearly.KrqdtAppLateOrLeave_New;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyRepositoryImp extends JpaRepository implements LateLeaveEarlyRepository {

	/**
	 * @param application
	 * @param infoOutput
	 */
	@Override
	public void registerLateLeaveEarly(String cID, Application application,
			ArrivedLateLeaveEarlyInfoOutput infoOutput) {
		ArrivedLateLeaveEarly arrivedLateLeaveEarly = infoOutput.getArrivedLateLeaveEarly().get();

		// entity primary key
		KrqdtAppLateOrLeavePK_New pk = new KrqdtAppLateOrLeavePK_New(cID, application.getAppID());

		// initial value for entity value
		int lateCanAtr1 = 0, earlyCanAtr1 = 0, lateCanAtr2 = 0, earlyCanAtr2 = 0;
		int lateTime1 = 0, earlyTime1 = 0, lateTime2 = 0, earlyTime2 = 0;

		// get list cancel and list report time
		List<LateCancelation> listCancel = arrivedLateLeaveEarly.getLateCancelation();
		List<TimeReport> listTime = arrivedLateLeaveEarly.getLateOrLeaveEarlies();

		// check and set cancel value by workNo and late or early
		for (LateCancelation cancel : listCancel) {
			if (cancel.getWorkNo() == 1) {
				if (cancel.getLateOrEarlyClassification().value == 0) {
					lateCanAtr1 = 1;
				} else {
					earlyCanAtr1 = 1;
				}
			} else {
				if (cancel.getLateOrEarlyClassification().value == 0) {
					lateCanAtr2 = 1;
				} else {
					earlyCanAtr2 = 1;
				}
			}
		}

		// check and set time report value by workNo and late or early
		for (TimeReport report : listTime) {
			if (report.getWorkNo() == 1) {
				if (report.getLateOrEarlyClassification().value == 0) {
					lateTime1 = report.getTimeWithDayAttr().rawHour();
				} else {
					earlyTime1 = report.getTimeWithDayAttr().rawHour();
				}
			} else {
				if (report.getLateOrEarlyClassification().value == 0) {
					lateTime2 = report.getTimeWithDayAttr().rawHour();
				} else {
					earlyTime2 = report.getTimeWithDayAttr().rawHour();
				}
			}
		}

		// create entity with value
		KrqdtAppLateOrLeave_New entity = new KrqdtAppLateOrLeave_New();
		entity.setKrqdtAppLateOrLeavePK(pk);

		// if(lateTime empty => lateCancelAtr1 = null)
		if (lateTime1 != 0) {
			entity.setLateTime1(lateTime1);
			entity.setLateCancelAtr1(lateCanAtr1);
		}

		// if(lateTime empty => lateCancelAtr1 = null)
		if (earlyTime1 != 0) {
			entity.setEarlyTime1(earlyTime1);
			entity.setEarlyCancelAtr1(earlyCanAtr1);
		}

		// if(lateTime empty => lateCancelAtr1 = null)
		if (lateTime2 != 0) {
			entity.setLateTime2(lateTime2);
			entity.setLateCancelAtr2(lateCanAtr2);
		}

		// if(lateTime empty => lateCancelAtr1 = null)
		if (earlyTime2 != 0) {
			entity.setEarlyTime2(earlyTime2);
			entity.setEarlyCancelAtr2(earlyCanAtr2);
		}

		// insert entity to table
		this.commandProxy().insert(entity);
	}
}
