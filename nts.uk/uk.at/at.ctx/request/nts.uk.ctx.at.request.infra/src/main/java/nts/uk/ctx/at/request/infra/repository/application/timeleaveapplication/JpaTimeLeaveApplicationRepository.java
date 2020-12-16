package nts.uk.ctx.at.request.infra.repository.application.timeleaveapplication;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHd;

import javax.ejb.Stateless;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication.KrqdtAppTimeHd.toEntity;

@Stateless
public class JpaTimeLeaveApplicationRepository extends JpaRepository implements TimeLeaveApplicationRepository {

    private static final String SELECT;

    private static final String GET_BY_KEYS;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT * FROM KRQDT_APP_TIME_HD as a INNER JOIN KRQDT_APPLICATION as b ON a.APP_ID = b.APP_ID");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.CID = @companyId AND a.APP_ID = @appId AND a.TIME_HD_TYPE = @appTimeType AND a.FRAME_NO = @frameNo ");
        GET_BY_KEYS = builderString.toString();

    }


    @Override
    public Optional<TimeLeaveApplication> findByKeys(String companyId, String appId, int appTimeType, int frameNo) {
        return new NtsStatement(GET_BY_KEYS, this.jdbcProxy())
            .paramString("companyId", companyId)
            .paramString("appId", appId)
            .paramInt("appTimeType", appTimeType)
            .paramInt("frameNo", frameNo)
            .getSingle(this::toDomain);
    }

    @Override
    public void add(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> entities = toEntity(domain);
        this.commandProxy().insertAll(entities);
    }

    @Override
    public void update(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> entities = toEntity(domain);
        this.commandProxy().updateAll(entities);
    }

    @Override
    public void remove(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> entities = toEntity(domain);
        this.commandProxy().removeAll(KrqdtAppTimeHd.class, entities.stream().map(i -> i.krqdtAppTimeHdPKPK).collect(Collectors.toList()));
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
        }else {
            application.setOpReversionReason(Optional.of(new ReasonForReversion(res.getString("REASON_REVERSION"))));
        }
        application.setAppDate(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_DATE")), pattern2)));
        if (res.getInt("FIXED_REASON") == null) {
            application.setOpAppStandardReasonCD(Optional.empty());
        }else {
            application.setOpAppStandardReasonCD(Optional.of(new AppStandardReasonCode(res.getInt("FIXED_REASON"))));
        }
        if (res.getString("APP_REASON") == null) {
            application.setOpAppReason(Optional.empty());
        }else {
            application.setOpAppReason(Optional.of(new AppReason(res.getString("APP_REASON"))));
        }
        application.setAppType(EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class));
        application.setEmployeeID(res.getString("APPLICANTS_SID"));
        if (res.getDate("APP_START_DATE") == null) {
            application.setOpAppStartDate(Optional.empty());
        }else {
            application.setOpAppStartDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_START_DATE")), pattern2))));
        }
        if (res.getDate("APP_END_DATE") == null) {
            application.setOpAppEndDate(Optional.empty());
        } else {
            application.setOpAppEndDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_END_DATE")), pattern2))));
        }

        if (res.getInt("STAMP_OPTION_ATR") == null) {
            application.setOpStampRequestMode(Optional.empty());
        }else {
            application.setOpStampRequestMode(Optional.of(EnumAdaptor.valueOf(res.getInt("STAMP_OPTION_ATR"), StampRequestMode.class)));
        }

        //TODO QA 38361
        String wkTypeCd = res.getString("TIME_HD_TYPE");
        String wkTimeCd = res.getString("FRAME_NO");
        Integer wkTimeEnd = res.getInt("WORK_TIME_END");
        Integer wkTimeStart = res.getInt("WORK_TIME_START");

        return new TimeLeaveApplication();
    }

}
