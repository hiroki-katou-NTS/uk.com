package nts.uk.ctx.at.request.infra.repository.application.businesstrip;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.infra.entity.application.businesstrip.KrqdtAppTrip;
import nts.uk.ctx.at.request.infra.entity.application.businesstrip.KrqdtAppTripPK;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class JpaBusinessTripRepository extends JpaRepository implements BusinessTripRepository {

    public static final String FIND_BY_ID = "SELECT *  "
            + "FROM KRQDT_APP_TRIP as a INNER JOIN KRQDT_APPLICATION as b ON a.APP_ID = b.APP_ID"
            + " WHERE a.APP_ID = @appID AND a.CID = @companyId AND a.APP_DATE = @date";

    public static final String FIND_BY_APP_ID = "SELECT *  "
            + "FROM KRQDT_APP_TRIP as a INNER JOIN KRQDT_APPLICATION as b ON a.APP_ID = b.APP_ID"
            + " WHERE a.APP_ID = @appID AND a.CID = @companyId ORDER BY a.APP_DATE ASC";

    @Override
    public Optional<BusinessTrip> findById(String companyId, String appId, GeneralDate date) {
        return new NtsStatement(FIND_BY_ID, this.jdbcProxy())
                .paramString("appID", appId)
                .paramString("companyId", companyId)
                .paramDate("date", date)
                .getSingle(res -> toDomain(res));
    }

    @Override
    public Optional<BusinessTrip> findByAppId(String companyId, String appId) {
        List<BusinessTrip> businessTrips = new NtsStatement(FIND_BY_APP_ID, this.jdbcProxy())
                .paramString("appID", appId)
                .paramString("companyId", companyId)
                .getList(res -> toDomain(res));
        BusinessTrip result = new BusinessTrip();
        result.setDepartureTime(businessTrips.get(0).getDepartureTime());
        result.setReturnTime(businessTrips.get(0).getReturnTime());
        List<BusinessTripInfo> infos = businessTrips.stream().map(i -> i.getInfos().get(0)).collect(Collectors.toList());
        result.setInfos(infos);
        return Optional.of(result);
    }

    @Override
    public void add(BusinessTrip domain) {
        List<KrqdtAppTrip> entities = toEntity(domain);
        this.commandProxy().insertAll(entities);
    }

    @Override
    public void update(BusinessTrip domain) {
        List<KrqdtAppTrip> entities = toEntity(domain);
        this.commandProxy().updateAll(entities);
    }

    @Override
    public void remove(BusinessTrip domain) {

    }

    private BusinessTrip toDomain(NtsResultRecord res) {
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
            application.setOpReversionReason(Optional.ofNullable(null));
        }else {
            application.setOpReversionReason(Optional.ofNullable(new ReasonForReversion(res.getString("REASON_REVERSION"))));
        }
        application.setAppDate(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_DATE")), pattern2)));
        if (res.getInt("FIXED_REASON") == null) {
            application.setOpAppStandardReasonCD(Optional.ofNullable(null));
        }else {
            application.setOpAppStandardReasonCD(Optional.ofNullable(new AppStandardReasonCode(res.getInt("FIXED_REASON"))));
        }
        if (res.getString("APP_REASON") == null) {
            application.setOpAppReason(Optional.ofNullable(null));
        }else {
            application.setOpAppReason(Optional.ofNullable(new AppReason(res.getString("APP_REASON"))));
        }
        application.setAppType(EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class));
        application.setEmployeeID(res.getString("APPLICANTS_SID"));
        if (res.getDate("APP_START_DATE") == null) {
            application.setOpAppStartDate(Optional.ofNullable(null));
        }else {
            application.setOpAppStartDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_START_DATE")), pattern2))));
        }
        if (res.getDate("APP_END_DATE") == null) {
            application.setOpAppEndDate(Optional.ofNullable(null));
        } else {
            application.setOpAppEndDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_END_DATE")), pattern2))));
        }

        if (res.getInt("STAMP_OPTION_ATR") == null) {
            application.setOpStampRequestMode(Optional.ofNullable(null));
        }else {
            application.setOpStampRequestMode(Optional.of(EnumAdaptor.valueOf(res.getInt("STAMP_OPTION_ATR"), StampRequestMode.class)));
        }
        String wkTypeCd = res.getString("WORK_TYPE_CD");
        String wkTimeCd = res.getString("WORK_TIME_CD");
        Integer wkTimeStart = res.getInt("WORK_TIME_START");
        Integer wkTimeEnd = res.getInt("WORK_TIME_END");
        Integer startTime = res.getInt("START_TIME");
        Integer returnTime = res.getInt("ARRIVAL_TIME");
        String date = res.getString("APP_DATE");
        BusinessTrip businessTrip = new BusinessTrip(application);
        BusinessTripInfo businessTripInfo = new BusinessTripInfo(
                new WorkInformation(wkTimeCd, wkTypeCd),
                GeneralDate.fromString(date, "yyyy/MM/dd"),
                (wkTimeStart == null && wkTimeEnd == null) ? Optional.empty() : Optional.of(Arrays.asList(new TimeZoneWithWorkNo(1,wkTimeStart, wkTimeEnd)) )
        );
        businessTrip.setInfos(Arrays.asList(businessTripInfo));
        businessTrip.setDepartureTime(Optional.ofNullable(startTime));
        businessTrip.setReturnTime(Optional.ofNullable(returnTime));
        return businessTrip;
    }

    private List<KrqdtAppTrip> toEntity(BusinessTrip domain) {
        String cid = AppContexts.user().companyId();
        String contractCd = AppContexts.user().contractCode();
        List<KrqdtAppTrip> entities = new ArrayList<>();
        if(domain.getInfos().isEmpty()) {
            return null;
        }
        domain.getInfos().stream().forEach(i -> {
            KrqdtAppTrip entity = new KrqdtAppTrip();
            entity.setKrqdtAppTripPK(new KrqdtAppTripPK(cid, domain.getAppID(), i.getDate().toString()));
            entity.setContractCD(contractCd);
            entity.setWorkTypeCD(i.getWorkInformation().getWorkTypeCode().v());
            entity.setWorkTimeCD(i.getWorkInformation().getWorkTimeCode().v());
            entity.setStartTime(domain.getDepartureTime().get());
            entity.setArrivalTime(domain.getReturnTime().get());
            entity.setWorkTimeStart(i.getWorkingHours().get().get(0).getTimeZone().getStartTime().v());
            entity.setWorkTimeEnd(i.getWorkingHours().get().get(0).getTimeZone().getEndTime().v());
            entities.add(entity);
        });

        return entities;
    }
}
