package nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.BreakOrRestTime;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.BusinessTripAppWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.TargetWorkTypeByApp;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * refactor4 refactor 4
 */
@Entity
@Table(name = "KRQMT_APP_APV_EMP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtAppApvEmp extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected KrqmtAppApvEmpPK pk;

    @Column(name = "HOLIDAY_TYPE_USE_FLG")
    private Integer holidayTypeUseFlg;

    @Column(name = "DISPLAY_FLAG")
    private Integer displayFlag;

    @OneToMany(targetEntity = KrqmtAppWorktypeEmp.class, cascade = CascadeType.ALL, mappedBy = "krqstAppEmploymentSet", orphanRemoval = true)
    @JoinTable(name = "KRQMT_APP_WORKTYPE_EMP")
    public List<KrqmtAppWorktypeEmp> krqdtAppEmployWorktype;

    String getCompanyId() {
        return pk.getCid();
    }

    public String getEmploymentCode() {
        return pk.getEmploymentCode();
    }

    @Override
    protected Object getKey() {
        return pk;
    }

    public static AppEmploymentSet toDomain(List<KrqmtAppApvEmp> entities) {
        if (CollectionUtil.isEmpty(entities))
            return null;
        String companyId = entities.get(0).getCompanyId();
        String employmentCode = entities.get(0).getEmploymentCode();
        List<TargetWorkTypeByApp> targetWorkTypeList = entities.stream().map(e -> {
            ApplicationType appType = EnumAdaptor.valueOf(e.getPk().getAppType(), ApplicationType.class);
            switch (appType) {
                case ABSENCE_APPLICATION:
                    return new TargetWorkTypeByApp(
                            appType,
                            BooleanUtils.toBoolean(e.getDisplayFlag()),
                            e.getKrqdtAppEmployWorktype().stream().map(KrqmtAppWorktypeEmp::getWorkTypeCode).collect(Collectors.toList()),
                            Optional.empty(),
                            Optional.of(BooleanUtils.toBoolean(e.getHolidayTypeUseFlg())),
                            Optional.of(EnumAdaptor.valueOf(e.getPk().getHolidayOrPauseType(), HolidayAppType.class)),
                            Optional.empty()
                    );
                case BUSINESS_TRIP_APPLICATION:
                    return new TargetWorkTypeByApp(
                            appType,
                            BooleanUtils.toBoolean(e.getDisplayFlag()),
                            e.getKrqdtAppEmployWorktype().stream().map(KrqmtAppWorktypeEmp::getWorkTypeCode).collect(Collectors.toList()),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of(EnumAdaptor.valueOf(e.getPk().getHolidayOrPauseType(), BusinessTripAppWorkType.class))
                    );
                case COMPLEMENT_LEAVE_APPLICATION:
                    return new TargetWorkTypeByApp(
                            appType,
                            BooleanUtils.toBoolean(e.getDisplayFlag()),
                            e.getKrqdtAppEmployWorktype().stream().map(KrqmtAppWorktypeEmp::getWorkTypeCode).collect(Collectors.toList()),
                            Optional.of(EnumAdaptor.valueOf(e.getPk().getHolidayOrPauseType(), BreakOrRestTime.class)),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty()
                    );
                default:
                    return new TargetWorkTypeByApp(
                            appType,
                            BooleanUtils.toBoolean(e.getDisplayFlag()),
                            e.getKrqdtAppEmployWorktype().stream().map(KrqmtAppWorktypeEmp::getWorkTypeCode).collect(Collectors.toList()),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty()
                    );
            }
        }).collect(Collectors.toList());
        return new AppEmploymentSet(companyId, employmentCode, targetWorkTypeList);
    }

    public static List<KrqmtAppApvEmp> fromDomain(AppEmploymentSet domain) {
        return domain.getTargetWorkTypeByAppLst().stream().map(target -> {
            String companyId = domain.getCompanyID();
            String employmentCode = domain.getEmploymentCD();
            switch (target.getAppType()) {
                case ABSENCE_APPLICATION:
                    return new KrqmtAppApvEmp(
                            new KrqmtAppApvEmpPK(companyId, employmentCode, target.getAppType().value, target.getOpHolidayAppType().get().value),
                            BooleanUtils.toInteger(target.getOpHolidayTypeUse().get()),
                            BooleanUtils.toInteger(target.isDisplayWorkType()),
                            target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(companyId, employmentCode, target.getAppType().value, target.getOpHolidayAppType().get().value, c)).collect(Collectors.toList())
                    );
                case BUSINESS_TRIP_APPLICATION:
                    return new KrqmtAppApvEmp(
                            new KrqmtAppApvEmpPK(companyId, employmentCode, target.getAppType().value, target.getOpBusinessTripAppWorkType().get().value),
                            null,
                            BooleanUtils.toInteger(target.isDisplayWorkType()),
                            target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(companyId, employmentCode, target.getAppType().value, target.getOpBusinessTripAppWorkType().get().value, c)).collect(Collectors.toList())
                    );
                case COMPLEMENT_LEAVE_APPLICATION:
                    return new KrqmtAppApvEmp(
                            new KrqmtAppApvEmpPK(companyId, employmentCode, target.getAppType().value, target.getOpBreakOrRestTime().get().value),
                            null,
                            BooleanUtils.toInteger(target.isDisplayWorkType()),
                            target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(companyId, employmentCode, target.getAppType().value, target.getOpBreakOrRestTime().get().value, c)).collect(Collectors.toList())
                    );
                default:
                    return new KrqmtAppApvEmp(
                            new KrqmtAppApvEmpPK(companyId, employmentCode, target.getAppType().value, 9),
                            null,
                            BooleanUtils.toInteger(target.isDisplayWorkType()),
                            target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(companyId, employmentCode, target.getAppType().value, 9, c)).collect(Collectors.toList())
                    );
            }
        }).collect(Collectors.toList());
    }

    public static KrqmtAppApvEmp fromDomain(String companyId, String employmentCode, TargetWorkTypeByApp target) {
        switch (target.getAppType()) {
            case ABSENCE_APPLICATION:
                return new KrqmtAppApvEmp(
                        new KrqmtAppApvEmpPK(companyId, employmentCode, target.getAppType().value, target.getOpHolidayAppType().get().value),
                        BooleanUtils.toInteger(target.getOpHolidayTypeUse().get()),
                        BooleanUtils.toInteger(target.isDisplayWorkType()),
                        target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(companyId, employmentCode, target.getAppType().value, target.getOpHolidayAppType().get().value, c)).collect(Collectors.toList())
                );
            case BUSINESS_TRIP_APPLICATION:
                return new KrqmtAppApvEmp(
                        new KrqmtAppApvEmpPK(companyId, employmentCode, target.getAppType().value, target.getOpBusinessTripAppWorkType().get().value),
                        null,
                        BooleanUtils.toInteger(target.isDisplayWorkType()),
                        target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(companyId, employmentCode, target.getAppType().value, target.getOpBusinessTripAppWorkType().get().value, c)).collect(Collectors.toList())
                );
            case COMPLEMENT_LEAVE_APPLICATION:
                return new KrqmtAppApvEmp(
                        new KrqmtAppApvEmpPK(companyId, employmentCode, target.getAppType().value, target.getOpBreakOrRestTime().get().value),
                        null,
                        BooleanUtils.toInteger(target.isDisplayWorkType()),
                        target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(companyId, employmentCode, target.getAppType().value, target.getOpBreakOrRestTime().get().value, c)).collect(Collectors.toList())
                );
            default:
                return new KrqmtAppApvEmp(
                        new KrqmtAppApvEmpPK(companyId, employmentCode, target.getAppType().value, 9),
                        null,
                        BooleanUtils.toInteger(target.isDisplayWorkType()),
                        target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(companyId, employmentCode, target.getAppType().value, 9, c)).collect(Collectors.toList())
                );
        }
    }

    public void update(TargetWorkTypeByApp target) {
        this.displayFlag = BooleanUtils.toInteger(target.isDisplayWorkType());
        this.holidayTypeUseFlg = target.getAppType() == ApplicationType.ABSENCE_APPLICATION ? target.getOpHolidayTypeUse().map(BooleanUtils::toInteger).orElse(null) : null;
        this.krqdtAppEmployWorktype.clear();
        this.krqdtAppEmployWorktype.addAll(target.getWorkTypeLst().stream().map(c -> new KrqmtAppWorktypeEmp(
                this.pk.getCid(),
                this.pk.getEmploymentCode(),
                this.pk.getAppType(),
                this.pk.getHolidayOrPauseType(),
                c
        )).collect(Collectors.toList()));
        this.krqdtAppEmployWorktype.forEach(t -> {t.setContractCd(this.contractCd);});
    }
}

