package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.absenceleaveapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.KrqdtAppRecAbsPK;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.absenceleaveapp.KrqdtAppHdSub;
import nts.uk.ctx.at.request.infra.repository.application.FindAppCommonForNR;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhPV
 */
@Stateless
public class JpaAbsenceLeaveAppRepository extends JpaRepository implements AbsenceLeaveAppRepository, FindAppCommonForNR<AbsenceLeaveApp>{

	@Inject 
	private ApplicationRepository applicationRepo;

	@Override
	public void insert(AbsenceLeaveApp domain) {
		this.commandProxy().insert(new KrqdtAppHdSub(domain));
	}

	@Override
	public Optional<AbsenceLeaveApp> findByID(String applicationID) {
		String companyId = AppContexts.user().companyId();
		Optional<Application> application = applicationRepo.findByID(applicationID);
		if(application.isPresent()) {
			return this.queryProxy().find(new KrqdtAppRecAbsPK(companyId, applicationID), KrqdtAppHdSub.class).map(c->c.toDomain(application.get()));
		}
		return Optional.empty();
	}

	@Override
	public void update(AbsenceLeaveApp domain) {
		this.commandProxy().update(new KrqdtAppHdSub(domain));
	}

	@Override
	public void remove(String appID) {
		String companyId = AppContexts.user().companyId();
		this.commandProxy().remove(KrqdtAppHdSub.class, new KrqdtAppRecAbsPK(companyId, appID));
	}

	@Override
	public Optional<AbsenceLeaveApp> findByAppId(String applicationID) {
		return this.findByID(applicationID);
	}
	
	private final static String FIND_BY_IDS = "select a from KrqdtAppHdSub a where a.pK.cId = :cid and a.pK.appID IN :appIds";
	@Override
	public List<AbsenceLeaveApp> findWithSidDate(String companyId, String sid, GeneralDate date) {
		List<Application> lstApp = applicationRepo.findAppWithSidDate(companyId, sid, date, ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}

	@Override
	public List<AbsenceLeaveApp> findWithSidDateApptype(String companyId, String sid, GeneralDate date,
			GeneralDateTime inputDate, PrePostAtr prePostAtr) {
		List<Application> lstApp = applicationRepo.findAppWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr, ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}

	@Override
	public List<AbsenceLeaveApp> findWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
		List<Application> lstApp = applicationRepo.findAppWithSidDatePeriod(companyId, sid, period, ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}
	
	private List<AbsenceLeaveApp> mapToDom(String companyId, List<Application> lstApp) {
		if (lstApp.isEmpty())
			return new ArrayList<>();
		return this.queryProxy().query(FIND_BY_IDS, KrqdtAppHdSub.class).setParameter("cid", companyId)
				.setParameter("appIds", lstApp.stream().map(x -> x.getAppID()).collect(Collectors.toList())).getList(x -> {
					return x.toDomain(this.findAppId(lstApp, x.pK.getAppID()).orElse(null));
				});
	}
}
