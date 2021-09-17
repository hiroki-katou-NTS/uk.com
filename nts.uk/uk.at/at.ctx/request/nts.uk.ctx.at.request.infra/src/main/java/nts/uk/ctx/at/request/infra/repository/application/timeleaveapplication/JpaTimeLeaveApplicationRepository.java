package nts.uk.ctx.at.request.infra.repository.application.timeleaveapplication;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.*;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHd;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHdInput;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHdInputPK;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHdPK;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.shr.com.context.AppContexts;

import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class JpaTimeLeaveApplicationRepository extends JpaRepository implements TimeLeaveApplicationRepository {

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
        
        Set<Integer> hdTypes = timeZoneEntities.stream().map(x -> x.pk.timeHdType).collect(Collectors.toSet());
        hdTypes.addAll(appTimeEntities.stream().map(x -> x.pk.timeHdType).collect(Collectors.toSet()));
        List<TimeLeaveApplicationDetail> details = new ArrayList<>();
        hdTypes.stream().forEach(hdType -> {
        	TimeLeaveApplicationDetail detail = new TimeLeaveApplicationDetail(
                    EnumAdaptor.valueOf(hdType, AppTimeType.class),
					timeZoneEntities.stream().filter(timeZone -> timeZone.pk.timeHdType == hdType)
							.map(i -> new TimeZoneWithWorkNo(i.pk.frameNo, i.workTimeStart, i.workTimeEnd))
							.collect(Collectors.toList()),
							 appTimeEntities
	                            .stream()
	                            .filter(i -> i.pk.timeHdType == hdType)
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
        	   details.add(detail);
        });
        TimeLeaveApplication result = new TimeLeaveApplication(application.get(), details);
        return Optional.of(result);

    }

    @Override
    public void add(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> entities = toEntity(domain);
        List<KrqdtAppTimeHdInput> details = toEntityDetail(domain);
        this.commandProxy().insertAll(entities);
        this.commandProxy().insertAll(details);
        this.getEntityManager().flush();
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

}
