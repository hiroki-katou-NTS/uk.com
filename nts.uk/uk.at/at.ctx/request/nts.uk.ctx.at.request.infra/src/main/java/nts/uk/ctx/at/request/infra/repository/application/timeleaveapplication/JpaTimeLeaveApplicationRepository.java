package nts.uk.ctx.at.request.infra.repository.application.timeleaveapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHd;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHdInput;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHdInputPK;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHdPK;
import nts.uk.ctx.at.request.infra.repository.application.FindAppCommonForNR;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaTimeLeaveApplicationRepository extends JpaRepository implements TimeLeaveApplicationRepository, FindAppCommonForNR<TimeLeaveApplication>  {

    @Inject
    private ApplicationRepository applicationRepo;

    private static final String SELECT_APP_TIME_HD = "SELECT a FROM KrqdtAppTimeHd a WHERE a.pk.companyID = :companyId AND a.pk.appID = :appId";
    private static final String SELECT_APP_TIME_HD_INPUT = "SELECT b FROM KrqdtAppTimeHdInput b WHERE b.pk.companyID = :companyId AND b.pk.appID = :appId";

    @Override
    public Optional<TimeLeaveApplication> findById(String companyId, String appId) {
    	   Optional<Application> application = applicationRepo.findByID(companyId, appId);
           if (!application.isPresent()) return Optional.empty();

           List<KrqdtAppTimeHd> timeZoneEntities = this.queryProxy().query(SELECT_APP_TIME_HD, KrqdtAppTimeHd.class)
                   .setParameter("companyId", companyId)
                   .setParameter("appId", appId).getList();
           List<KrqdtAppTimeHdInput> appTimeEntities = this.queryProxy().query(SELECT_APP_TIME_HD_INPUT, KrqdtAppTimeHdInput.class)
                   .setParameter("companyId", companyId)
                   .setParameter("appId", appId).getList();
           return toDom(application, timeZoneEntities, appTimeEntities);
    }
    
	public Optional<TimeLeaveApplication> toDom(Optional<Application> application,
			List<KrqdtAppTimeHd> timeZoneEntities, List<KrqdtAppTimeHdInput> appTimeEntities) {
		if(!application.isPresent()) {
			return Optional.empty();
		}
        List<TimeLeaveApplicationDetail> details = new ArrayList<>();
        Map<Integer, List<KrqdtAppTimeHd>> mapTimeZone = timeZoneEntities.stream().collect(Collectors.groupingBy(i -> i.pk.timeHdType));
        Map<Integer, List<KrqdtAppTimeHdInput>> mapAppTime = appTimeEntities.stream().collect(Collectors.groupingBy(i -> i.pk.timeHdType));
        
        Map<Integer, List<TimeZoneWithWorkNo>> mapTimeZoneDomain = new HashMap<Integer, List<TimeZoneWithWorkNo>>();
        Map<Integer, TimeDigestApplication> mapAppTimeDomain = new HashMap<Integer, TimeDigestApplication>();
                
        mapTimeZone.forEach((timeHdType, frames) -> {
            List<TimeZoneWithWorkNo> list = frames.stream()
                    .map(i -> new TimeZoneWithWorkNo(i.pk.frameNo, i.workTimeStart, i.workTimeEnd))
                    .collect(Collectors.toList());
            mapTimeZoneDomain.put(timeHdType, list);
        });
        mapAppTime.forEach((timeHdType, items) -> {
            mapAppTimeDomain.put(timeHdType, items.stream()
                    .filter(x -> x.pk.timeHdType == timeHdType)
                    .findFirst()
                    .map(i -> new TimeDigestApplication(
                          new AttendanceTime(i.sixtyHOvertime),
                          new AttendanceTime(i.nursingTime),
                          new AttendanceTime(i.childNursingTime),
                          new AttendanceTime(i.hoursOfSubHoliday),
                          new AttendanceTime(i.timeSpecialVacation),
                          new AttendanceTime(i.hoursOfHoliday),
                          Optional.ofNullable(i.specialHdFrameNo)
                  ))
                  .orElse(new TimeDigestApplication(
                          new AttendanceTime(0),
                          new AttendanceTime(0),
                          new AttendanceTime(0),
                          new AttendanceTime(0),
                          new AttendanceTime(0),
                          new AttendanceTime(0),
                          Optional.empty()
                  )));
        });
        
        mapTimeZoneDomain.forEach((timeHdType, frames) -> {
            TimeLeaveApplicationDetail detail = new TimeLeaveApplicationDetail(
                  EnumAdaptor.valueOf(timeHdType, AppTimeType.class),
                  frames,
                  mapAppTimeDomain.containsKey(timeHdType) ? mapAppTimeDomain.get(timeHdType) : 
                      new TimeDigestApplication(
                            new AttendanceTime(0),
                            new AttendanceTime(0),
                            new AttendanceTime(0),
                            new AttendanceTime(0),
                            new AttendanceTime(0),
                            new AttendanceTime(0),
                            Optional.empty())
          );
          details.add(detail);
        });
        
        mapAppTimeDomain.forEach((timeHdType, frames) -> {
            TimeLeaveApplicationDetail detail = new TimeLeaveApplicationDetail(
                    EnumAdaptor.valueOf(timeHdType, AppTimeType.class),
                    mapTimeZoneDomain.containsKey(timeHdType) ? mapTimeZoneDomain.get(timeHdType) : new ArrayList<TimeZoneWithWorkNo>(),
                    frames
            );
            
            if (!details.stream().filter(x -> x.getAppTimeType().value == timeHdType).findFirst().isPresent()) {
                details.add(detail);
            }
        });
        
//        mapTimeZone.forEach((timeHdType, frames) -> {
//            TimeLeaveApplicationDetail detail = new TimeLeaveApplicationDetail(
//                    EnumAdaptor.valueOf(timeHdType, AppTimeType.class),
//                    frames.stream().map(i -> new TimeZoneWithWorkNo(i.pk.frameNo, i.workTimeStart, i.workTimeEnd)).collect(Collectors.toList()),
//                    appTimeEntities
//                            .stream()
//                            .filter(i -> i.pk.timeHdType == timeHdType)
//                            .findFirst()
//                            .map(i -> new TimeDigestApplication(
//                                    new AttendanceTime(i.sixtyHOvertime),
//                                    new AttendanceTime(i.nursingTime),
//                                    new AttendanceTime(i.childNursingTime),
//                                    new AttendanceTime(i.hoursOfSubHoliday),
//                                    new AttendanceTime(i.timeSpecialVacation),
//                                    new AttendanceTime(i.hoursOfHoliday),
//                                    Optional.ofNullable(i.specialHdFrameNo)
//                            ))
//                            .orElse(new TimeDigestApplication(
//                                    new AttendanceTime(0),
//                                    new AttendanceTime(0),
//                                    new AttendanceTime(0),
//                                    new AttendanceTime(0),
//                                    new AttendanceTime(0),
//                                    new AttendanceTime(0),
//                                    Optional.empty()
//                            ))
//            );
//            details.add(detail);
//        });
        
        TimeLeaveApplication result = new TimeLeaveApplication(application.get(), details);
        return Optional.of(result);

    }

    @Override
    public void add(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> entities = toEntity(domain);
        List<KrqdtAppTimeHdInput> details = toEntityDetail(domain);
        this.commandProxy().insertAll(entities);
        this.commandProxy().insertAll(details);
    }

    @Override
    public void update(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> entities = this.queryProxy().query(SELECT_APP_TIME_HD, KrqdtAppTimeHd.class)
            .setParameter("companyId", AppContexts.user().companyId())
            .setParameter("appId", domain.getAppID()).getList();

        List<KrqdtAppTimeHdInput> details = this.queryProxy().query(SELECT_APP_TIME_HD_INPUT, KrqdtAppTimeHdInput.class)
            .setParameter("companyId", AppContexts.user().companyId())
            .setParameter("appId", domain.getAppID()).getList();

        this.commandProxy().removeAll(KrqdtAppTimeHd.class, entities.stream().map(i -> i.pk).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrqdtAppTimeHdInput.class, details.stream().map(i -> i.pk).collect(Collectors.toList()));
        this.getEntityManager().flush();
        this.commandProxy().insertAll(toEntity(domain));
        this.commandProxy().insertAll(toEntityDetail(domain));
    }

    @Override
    public void remove(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> entities = toEntity(domain);
        List<KrqdtAppTimeHdInput> details = toEntityDetail(domain);
        this.commandProxy().removeAll(KrqdtAppTimeHd.class, entities.stream().map(i -> i.pk).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrqdtAppTimeHdInput.class, details.stream().map(i -> i.pk).collect(Collectors.toList()));
    }

    private List<KrqdtAppTimeHd> toEntity(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> result = new ArrayList<>();
        domain.getLeaveApplicationDetails().forEach(detail ->
                detail.getTimeZoneWithWorkNoLst().forEach(x -> {
                    KrqdtAppTimeHd entity = new KrqdtAppTimeHd(
                            new KrqdtAppTimeHdPK(
                                    AppContexts.user().companyId(),
                                    domain.getAppID(),
                                    detail.getAppTimeType().value,
                                    x.getWorkNo().v()
                            ),
                            x.getTimeZone().getEndTime() != null ? x.getTimeZone().getEndTime().v() : null,
                            x.getTimeZone().getStartTime() != null ? x.getTimeZone().getStartTime().v() : null
                    );
                    entity.contractCd = AppContexts.user().contractCode();
                    result.add(entity);
                })
        );
        return result;
    }

    private List<KrqdtAppTimeHdInput> toEntityDetail(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHdInput> result = new ArrayList<>();

        domain.getLeaveApplicationDetails().forEach(detail -> {
                KrqdtAppTimeHdInput entity = new KrqdtAppTimeHdInput(
                    new KrqdtAppTimeHdInputPK(
                        AppContexts.user().companyId(),
                        domain.getAppID(),
                        detail.getAppTimeType().value
                    ),
                    detail.getTimeDigestApplication().getOvertime60H().v(),
                    detail.getTimeDigestApplication().getNursingTime().v(),
                    detail.getTimeDigestApplication().getChildTime().v(),
                    detail.getTimeDigestApplication().getTimeOff().v(),
                    detail.getTimeDigestApplication().getTimeAnnualLeave().v(),
                    detail.getTimeDigestApplication().getTimeSpecialVacation().v(),
                    detail.getTimeDigestApplication().getSpecialVacationFrameNO().orElse(null)
                );
                entity.contractCd = AppContexts.user().contractCode();
                result.add(entity);
            }
        );
        return result;
    }

    @Override
	public List<TimeLeaveApplication> findWithSidDate(String companyId, String sid, GeneralDate date) {
		List<Application> lstApp = applicationRepo.findAppWithSidDate(companyId, sid, date, ApplicationType.ANNUAL_HOLIDAY_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}

	@Override
	public List<TimeLeaveApplication> findWithSidDateApptype(String companyId, String sid, GeneralDate date,
			GeneralDateTime inputDate, PrePostAtr prePostAtr) {
		List<Application> lstApp = applicationRepo.findAppWithSidDateApptype(companyId, sid, date, inputDate,
				prePostAtr, ApplicationType.ANNUAL_HOLIDAY_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}

	@Override
	public List<TimeLeaveApplication> findWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
		List<Application> lstApp = applicationRepo.findAppWithSidDatePeriod(companyId, sid, period, ApplicationType.ANNUAL_HOLIDAY_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}
	
	private static final String SELECT_APP_TIME_HD_IDS = "SELECT a FROM KrqdtAppTimeHd a WHERE a.pk.companyID = :companyId AND a.pk.appID IN :appId";
    private static final String SELECT_APP_TIME_HD_INPUT_IDS = "SELECT b FROM KrqdtAppTimeHdInput b WHERE b.pk.companyID = :companyId AND b.pk.appID IN :appId";

	private List<TimeLeaveApplication> mapToDom(String companyId, List<Application> lstApp) {

		if (lstApp.isEmpty())
			return new ArrayList<TimeLeaveApplication>();

		List<KrqdtAppTimeHd> timeZoneEntities = this.queryProxy().query(SELECT_APP_TIME_HD_IDS, KrqdtAppTimeHd.class)
				.setParameter("companyId", companyId)
				.setParameter("appId", lstApp.stream().map(x -> x.getAppID()).collect(Collectors.toList())).getList();
		List<KrqdtAppTimeHdInput> appTimeEntities = this.queryProxy()
				.query(SELECT_APP_TIME_HD_INPUT_IDS, KrqdtAppTimeHdInput.class).setParameter("companyId", companyId)
				.setParameter("appId", lstApp.stream().map(x -> x.getAppID()).collect(Collectors.toList())).getList();

		return lstApp.stream().map(x -> {
			return toDom(Optional.of(x),
					timeZoneEntities.stream().filter(y -> y.pk.getAppID().equals(x.getAppID()))
							.collect(Collectors.toList()),
					appTimeEntities.stream().filter(y -> y.pk.getAppID().equals(x.getAppID()))
							.collect(Collectors.toList())).orElse(null);
		}).filter(x -> x != null).collect(Collectors.toList());
	}
}
