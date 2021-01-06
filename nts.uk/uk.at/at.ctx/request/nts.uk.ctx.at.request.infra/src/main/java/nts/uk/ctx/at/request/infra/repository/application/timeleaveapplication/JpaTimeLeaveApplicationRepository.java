package nts.uk.ctx.at.request.infra.repository.application.timeleaveapplication;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.*;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaTimeLeaveApplicationRepository extends JpaRepository implements TimeLeaveApplicationRepository {

    private static final String SELECT;

    private static final String SELECT_APP_TIME_HD = "SELECT a FROM KrqdtAppTimeHd a WHERE a.krqdtAppTimeHdPK.companyID = :companyId AND a.krqdtAppTimeHdPK.appID = :appId";
    private static final String SELECT_APP_TIME_HD_INPUT = "SELECT b FROM KrqdtAppTimeHdInput b WHERE a.krqdtAppTimeHdInputPK.companyID = :companyId AND a.krqdtAppTimeHdInputPK.appID = :appId";

    private static final String GET_BY_KEYS;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT * FROM KRQDT_APP_TIME_HD as a ");
        builderString.append(" INNER JOIN KRQDT_APPLICATION as b ON a.APP_ID = b.APP_ID");
        builderString.append(" JOIN KRQDT_APP_TIME_HD_INPUT as c ON a.CID = c.CID AND a.APP_ID = c.APP_ID AND a.TIME_HD_TYPE = c.TIME_HD_TYPE ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.CID = @companyId AND a.APP_ID = @appId ");
        GET_BY_KEYS = builderString.toString();
    }

    @Override
    public Optional<TimeLeaveApplication> findById(String companyId, String appId) {
        List<TimeLeaveApplication> timeLeaveApplications = new NtsStatement(GET_BY_KEYS, this.jdbcProxy())
            .paramString("companyId", companyId)
            .paramString("appId", appId)
            .getList(this::toDomain);
        if (timeLeaveApplications.size() == 0) return Optional.empty();
        TimeLeaveApplication result = timeLeaveApplications.get(0);
        result.setLeaveApplicationDetails(setDetail(timeLeaveApplications));
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

        this.commandProxy().removeAll(KrqdtAppTimeHd.class, entities.stream().map(i -> i.krqdtAppTimeHdPK).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrqdtAppTimeHdInput.class, details.stream().map(i -> i.krqdtAppTimeHdInputPK).collect(Collectors.toList()));

        this.commandProxy().insertAll(toEntity(domain));
        this.commandProxy().insertAll(toEntityDetail(domain));
    }

    @Override
    public void remove(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> entities = toEntity(domain);
        List<KrqdtAppTimeHdInput> details = toEntityDetail(domain);
        this.commandProxy().removeAll(KrqdtAppTimeHd.class, entities.stream().map(i -> i.krqdtAppTimeHdPK).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrqdtAppTimeHdInput.class, details.stream().map(i -> i.krqdtAppTimeHdInputPK).collect(Collectors.toList()));
    }

    public static List<TimeLeaveApplicationDetail> setDetail(List<TimeLeaveApplication> domains) {
        List<TimeLeaveApplicationDetail> result = new ArrayList<>();

        TimeLeaveApplicationDetail detailForPrivate = new TimeLeaveApplicationDetail();
        TimeLeaveApplicationDetail detailForUnion = new TimeLeaveApplicationDetail();

        List<TimeZoneWithWorkNo> lstNoForPrivate = new ArrayList<>();
        List<TimeZoneWithWorkNo> lstNoForUnion = new ArrayList<>();
        domains.forEach(x -> {
            TimeLeaveApplicationDetail detail = x.getLeaveApplicationDetails().get(0);
            AppTimeType appTimeType = detail.getAppTimeType();
            if (appTimeType == AppTimeType.PRIVATE) {
                detailForPrivate.setAppTimeType(appTimeType);
                detailForPrivate.setTimeDigestApplication(detail.getTimeDigestApplication());
                lstNoForPrivate.add(detail.getTimeZoneWithWorkNoLst().get(0));
            } else if (appTimeType == AppTimeType.UNION) {
                detailForUnion.setAppTimeType(appTimeType);
                detailForUnion.setTimeDigestApplication(detail.getTimeDigestApplication());
                lstNoForUnion.add(detail.getTimeZoneWithWorkNoLst().get(0));
            } else {
                result.add(new TimeLeaveApplicationDetail(
                    detail.getAppTimeType(),
                        Collections.singletonList(new TimeZoneWithWorkNo(
                            detail.getTimeZoneWithWorkNoLst().get(0).getWorkNo(),
                            detail.getTimeZoneWithWorkNoLst().get(0).getTimeZone()
                        )),
                    detail.getTimeDigestApplication()
                    )
                );
            }
        });
        detailForPrivate.setTimeZoneWithWorkNoLst(lstNoForPrivate);
        detailForUnion.setTimeZoneWithWorkNoLst(lstNoForUnion);
        result.add(detailForPrivate);
        result.add(detailForUnion);
        return result;
    }

    private TimeLeaveApplication toDomain(NtsResultRecord res) {
        Application application = new Application();
        String pattern = "yyyy/MM/dd HH:mm:ss";
        String pattern2 = "yyyy/MM/dd";
        DateFormat df = new SimpleDateFormat(pattern);
        DateFormat df2 = new SimpleDateFormat(pattern2);
        application.setAppID(res.getString("APP_ID"));
        application.setVersion(res.getInt("EXCLUS_VER"));
        application.setPrePostAtr(EnumAdaptor.valueOf(res.getInt("PRE_POST_ATR"), PrePostAtr.class));
        application.setInputDate(GeneralDateTime.fromString(df.format(res.getDate("INPUT_DATE")), pattern));
        application.setEnteredPersonID(res.getString("ENTERED_PERSON_SID"));
        if (res.getString("REASON_REVERSION") == null) {
            application.setOpReversionReason(Optional.empty());
        } else {
            application.setOpReversionReason(Optional.of(new ReasonForReversion(res.getString("REASON_REVERSION"))));
        }
        application.setAppDate(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_DATE")), pattern2)));
        if (res.getInt("FIXED_REASON") == null) {
            application.setOpAppStandardReasonCD(Optional.empty());
        } else {
            application.setOpAppStandardReasonCD(Optional.of(new AppStandardReasonCode(res.getInt("FIXED_REASON"))));
        }
        if (res.getString("APP_REASON") == null) {
            application.setOpAppReason(Optional.empty());
        } else {
            application.setOpAppReason(Optional.of(new AppReason(res.getString("APP_REASON"))));
        }
        application.setAppType(EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class));
        application.setEmployeeID(res.getString("APPLICANTS_SID"));
        if (res.getDate("APP_START_DATE") == null) {
            application.setOpAppStartDate(Optional.empty());
        } else {
            application.setOpAppStartDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_START_DATE")), pattern2))));
        }
        if (res.getDate("APP_END_DATE") == null) {
            application.setOpAppEndDate(Optional.empty());
        } else {
            application.setOpAppEndDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_END_DATE")), pattern2))));
        }

        if (res.getInt("STAMP_OPTION_ATR") == null) {
            application.setOpStampRequestMode(Optional.empty());
        } else {
            application.setOpStampRequestMode(Optional.of(EnumAdaptor.valueOf(res.getInt("STAMP_OPTION_ATR"), StampRequestMode.class)));
        }

        Integer frameNo = res.getInt("FRAME_NO");
        if (frameNo == null) return null;
        List<TimeLeaveApplicationDetail> details = new ArrayList<>();
        details.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(res.getInt("TIME_HD_TYPE"), AppTimeType.class),
                Collections.singletonList(new TimeZoneWithWorkNo(
                    (int) res.getInt("FRAME_NO"),
                    res.getInt("WORK_TIME_START"),
                    res.getInt("WORK_TIME_END")
                )),
                new TimeDigestApplication(
                    res.getInt("HOUR_OF_SIXTY_OVERTIME") == null ? new AttendanceTime(0) : new AttendanceTime(res.getInt("HOUR_OF_SIXTY_OVERTIME")),
                    res.getInt("HOUR_OF_CARE") == null ? new AttendanceTime(0) : new AttendanceTime(res.getInt("HOUR_OF_CARE")),
                    res.getInt("HOUR_OF_CHILD_CARE") == null ? new AttendanceTime(0) : new AttendanceTime(res.getInt("HOUR_OF_CHILD_CARE")),
                    res.getInt("HOUR_OF_HDCOM") == null ? new AttendanceTime(0) : new AttendanceTime(res.getInt("HOUR_OF_HDCOM")),
                    res.getInt("HOUR_OF_HDPAID") == null ? new AttendanceTime(0) : new AttendanceTime(res.getInt("HOUR_OF_HDPAID")),
                    res.getInt("HOUR_OF_HDSP") == null ? new AttendanceTime(0) : new AttendanceTime(res.getInt("HOUR_OF_HDSP")),
                    Optional.of(res.getInt("FRAME_NO_OF_HDSP"))

                )
            )
        );

        return new TimeLeaveApplication(application, details);
    }

    public static List<KrqdtAppTimeHd> toEntity(TimeLeaveApplication domain) {
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
                    x.getTimeZone().getEndTime().v(),
                    x.getTimeZone().getStartTime().v()
                );
                entity.contractCd = AppContexts.user().contractCode();
                result.add(entity);
            })
        );
        return result;
    }

    public static List<KrqdtAppTimeHdInput> toEntityDetail(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHdInput> result = new ArrayList<>();

        domain.getLeaveApplicationDetails().forEach(detail -> {
                KrqdtAppTimeHdInput entity = new KrqdtAppTimeHdInput(
                    new KrqdtAppTimeHdInputPK(
                        AppContexts.user().companyId(),
                        domain.getAppID(),
                        detail.getAppTimeType().value
                    ),
                    detail.getTimeDigestApplication().getOvertime60H().v() > 0 ? detail.getTimeDigestApplication().getOvertime60H().v() : null,
                    detail.getTimeDigestApplication().getNursingTime().v() > 0 ? detail.getTimeDigestApplication().getNursingTime().v() : null,
                    detail.getTimeDigestApplication().getChildTime().v() > 0 ? detail.getTimeDigestApplication().getChildTime().v() : null,
                    detail.getTimeDigestApplication().getTimeOff().v() > 0 ? detail.getTimeDigestApplication().getTimeOff().v() : null,
                    detail.getTimeDigestApplication().getTimeAnnualLeave().v() > 0 ? detail.getTimeDigestApplication().getTimeAnnualLeave().v() : null,
                    detail.getTimeDigestApplication().getTimeSpecialVacation().v() > 0 ? detail.getTimeDigestApplication().getTimeSpecialVacation().v() : null,
                    detail.getTimeDigestApplication().getSpecialVacationFrameNO().isPresent() ? detail.getTimeDigestApplication().getSpecialVacationFrameNO().get() : null
                );
                entity.contractCd = AppContexts.user().contractCode();
                result.add(entity);
            }
        );
        return result;
    }

}
