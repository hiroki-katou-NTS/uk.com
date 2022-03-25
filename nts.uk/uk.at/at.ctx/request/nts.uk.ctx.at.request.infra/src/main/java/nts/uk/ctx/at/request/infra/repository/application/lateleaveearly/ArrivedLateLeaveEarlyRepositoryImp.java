package nts.uk.ctx.at.request.infra.repository.application.lateleaveearly;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
import nts.uk.ctx.at.request.infra.entity.application.lateleaveearly.KrqdtAppLateEarly;
import nts.uk.ctx.at.request.infra.entity.application.lateleaveearly.KrqdtAppLateOrLeavePK;
import nts.uk.ctx.at.request.infra.repository.application.FindAppCommonForNR;

/**
 * @author anhnm
 *
 */
@Stateless
public class ArrivedLateLeaveEarlyRepositoryImp extends JpaRepository
		implements ArrivedLateLeaveEarlyRepository, FindAppCommonForNR<ArrivedLateLeaveEarly> {
	private final String SELECT_BY_CID_APPID = "SELECT * FROM KRQDT_APP_LATE_EARLY " + "WHERE CID = @companyId"
			+ " AND APP_ID = @appId";

	@Inject 
	private ApplicationRepository applicationRepo;
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyRepository
	 * #registerLateLeaveEarly(java.lang.String,
	 * nts.uk.ctx.at.request.dom.application.Application,
	 * nts.uk.ctx.at.request.dom.application.lateorleaveearly.
	 * ArrivedLateLeaveEarlyInfoOutput)
	 */
	@Override
	public void registerLateLeaveEarly(String cID, Application application,
			ArrivedLateLeaveEarlyInfoOutput infoOutput) {
		ArrivedLateLeaveEarly arrivedLateLeaveEarly = infoOutput.getArrivedLateLeaveEarly().get();
		// insert entity to table
		this.commandProxy().insert(toEntity(cID, application.getAppID(), arrivedLateLeaveEarly));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyRepository
	 * #getLateEarlyApp(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrivedLateLeaveEarly getLateEarlyApp(String companyId, String appId, Application app) {

		return new NtsStatement(SELECT_BY_CID_APPID, this.jdbcProxy()).paramString("companyId", companyId)
				.paramString("appId", appId).getSingle(x -> KrqdtAppLateEarly.MAPPER.toEntity(x).toDomain(app))
				.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyRepository
	 * #updateLateLeaveEarly(java.lang.String,
	 * nts.uk.ctx.at.request.dom.application.Application,
	 * nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarly)
	 */
	@Override
	public void updateLateLeaveEarly(String cID, Application application, ArrivedLateLeaveEarly infoOutput) {
		// initial value for entity value
		Integer lateCanAtr1 = null, earlyCanAtr1 = null, lateCanAtr2 = null, earlyCanAtr2 = null;
		Integer lateTime1 = null, earlyTime1 = null, lateTime2 = null, earlyTime2 = null;

		// get list cancel and list report time
		List<LateCancelation> listCancel = infoOutput.getLateCancelation();
		List<TimeReport> listTime = infoOutput.getLateOrLeaveEarlies();

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
					lateTime1 = report.getTimeWithDayAttr() == null ? null : report.getTimeWithDayAttr().v();
				} else {
					earlyTime1 = report.getTimeWithDayAttr() == null ? null : report.getTimeWithDayAttr().v();
				}
			} else {
				if (report.getLateOrEarlyClassification().value == 0) {
					lateTime2 = report.getTimeWithDayAttr() == null ? null : report.getTimeWithDayAttr().v();
				} else {
					earlyTime2 = report.getTimeWithDayAttr() == null ? null : report.getTimeWithDayAttr().v();
				}
			}
		}

		// create entity with value
		KrqdtAppLateEarly entity = new NtsStatement(SELECT_BY_CID_APPID, this.jdbcProxy())
				.paramString("companyId", cID).paramString("appId", application.getAppID())
				.getSingle(x -> KrqdtAppLateEarly.MAPPER.toEntity(x)).get();
		// entity.setKrqdtAppLateOrLeavePK(pk);

		// if(lateTime empty => lateCancelAtr1 = null)
			entity.setLateTime1(lateTime1);
			entity.setLateCancelAtr1(lateCanAtr1);

		// if(lateTime empty => lateCancelAtr1 = null)
			entity.setEarlyTime1(earlyTime1);
			entity.setEarlyCancelAtr1(earlyCanAtr1);

		// if(lateTime empty => lateCancelAtr1 = null)
			entity.setLateTime2(lateTime2);
			entity.setLateCancelAtr2(lateCanAtr2);

		// if(lateTime empty => lateCancelAtr1 = null)
			entity.setEarlyTime2(earlyTime2);
			entity.setEarlyCancelAtr2(earlyCanAtr2);

		this.commandProxy().update(entity);
	}

	@Override
	public void remove(String cID, String appId) {
		this.commandProxy().remove(KrqdtAppLateEarly.class, new KrqdtAppLateOrLeavePK(cID, appId));
	}

	private final String FIND_BY_APPID_IN = "SELECT * FROM KRQDT_APP_LATE_EARLY " + "WHERE CID = @companyId"
			+ " AND APP_ID IN @appId";
	@Override
	public List<ArrivedLateLeaveEarly> findWithSidDate(String companyId, String sid, GeneralDate date) {
		List<Application> lstApp = applicationRepo.findAppWithSidDate(companyId, sid, date, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value);
		 return mapToDom(companyId, lstApp);
	}

	@Override
	public List<ArrivedLateLeaveEarly> findWithSidDateApptype(String companyId, String sid, GeneralDate date,
			GeneralDateTime inputDate, PrePostAtr prePostAtr) {
		List<Application> lstApp = applicationRepo.findAppWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value);
		 return mapToDom(companyId, lstApp);
	}

	@Override
	public List<ArrivedLateLeaveEarly> findWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
		List<Application> lstApp = applicationRepo.findAppWithSidDatePeriod(companyId, sid, period, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value);
		 return mapToDom(companyId, lstApp);
	}
	
	private List<ArrivedLateLeaveEarly> mapToDom(String companyId, List<Application> lstApp) {
		if (lstApp.isEmpty())
			return new ArrayList<>();
		 return new NtsStatement(FIND_BY_APPID_IN, this.jdbcProxy()).paramString("companyId", companyId)
					.paramString("appId",  lstApp.stream().map(x -> x.getAppID()).collect(Collectors.toList())).
					getList(x -> {
						return KrqdtAppLateEarly.MAPPER.toEntity(x).toDomain(this.findAppId(lstApp, x.getString("APP_ID")).orElse(null));
					});
	}
	
	@Override
	public void add(String cid, ArrivedLateLeaveEarly lateOrLeaveEarly) {
		this.commandProxy().insert(toEntity(cid, lateOrLeaveEarly.getAppID(), lateOrLeaveEarly));
		this.getEntityManager().flush();
	}
	
	private KrqdtAppLateEarly toEntity(String cid, String appId, ArrivedLateLeaveEarly arrivedLateLeaveEarly) {
		// entity primary key
		KrqdtAppLateOrLeavePK pk = new KrqdtAppLateOrLeavePK(cid, appId);

		// initial value for entity value
		Integer lateCanAtr1 = null, earlyCanAtr1 = null, lateCanAtr2 = null, earlyCanAtr2 = null;
		Integer lateTime1 = null, earlyTime1 = null, lateTime2 = null, earlyTime2 = null;

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
					lateTime1 = report.getTimeWithDayAttr().v();
				} else {
					earlyTime1 = report.getTimeWithDayAttr().v();
				}
			} else {
				if (report.getLateOrEarlyClassification().value == 0) {
					lateTime2 = report.getTimeWithDayAttr().v();
				} else {
					earlyTime2 = report.getTimeWithDayAttr().v();
				}
			}
		}

		// create entity with value
		KrqdtAppLateEarly entity = new KrqdtAppLateEarly();
		entity.setKrqdtAppLateOrLeavePK(pk);

		// if(lateTime empty => lateCancelAtr1 = null)
		entity.setLateTime1(lateTime1);
		entity.setLateCancelAtr1(lateCanAtr1);

		// if(lateTime empty => lateCancelAtr1 = null)
		entity.setEarlyTime1(earlyTime1);
		entity.setEarlyCancelAtr1(earlyCanAtr1);

		// if(lateTime empty => lateCancelAtr1 = null)
		entity.setLateTime2(lateTime2);
		entity.setLateCancelAtr2(lateCanAtr2);

		// if(lateTime empty => lateCancelAtr1 = null)
		entity.setEarlyTime2(earlyTime2);
		entity.setEarlyCancelAtr2(earlyCanAtr2);
		
		return entity;
	}
}
