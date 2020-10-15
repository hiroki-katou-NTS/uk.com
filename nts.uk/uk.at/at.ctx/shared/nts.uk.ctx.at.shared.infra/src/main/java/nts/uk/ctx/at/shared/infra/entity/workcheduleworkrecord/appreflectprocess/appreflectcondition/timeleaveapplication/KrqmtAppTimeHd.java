package nts.uk.ctx.at.shared.infra.entity.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.*;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveDestination;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KRQMT_APP_TIME_HD")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KrqmtAppTimeHd extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    private String companyId;

    @Column(name = "SIXTY_OVERTIME_REFLECT_ATR")
    private int sixtyOvertimeReflectAtr;

    @Column(name = "CARE_REFLECT_ATR")
    private int careReflectAtr;

    @Column(name = "CHILD_CARE_REFLECT_ATR")
    private int childCareRelfectAtr;

    @Column(name = "HDCOM_REFLECT_ATR")
    private int substituteHolidayReflectAtr;

    @Column(name = "HDSP_REFLECT_ATR")
    private int specialHolidayReflectAtr;

    @Column(name = "HDPAID_REFLECT_ATR")
    private int annualHolidayReflectAtr;

    @Column(name = "BEF_WORK_REFLECT_ATR1")
    private int beforeWorkReflectAtr1;

    @Column(name = "AFT_WORK_REFLECT_ATR1")
    private int afterWorkRelfectAtr1;

    @Column(name = "BEF_WORK_REFLECT_ATR2")
    private int beforeWorkReflectAtr2;

    @Column(name = "AFT_WORK_REFLECT_ATR2")
    private int afterWorkReflectAtr2;

    @Column(name = "OUT_PRI_REFLECT_ATR")
    private int outPrivateReflectAtr;

    @Column(name = "OUT_UNION_REFLECT_ATR")
    private int outUnionReflectAtr;

    @Column(name = "WORK_TIME_REFLECT_ATR")
    private int workTimeReflectAtr;

    @Override
    protected Object getKey() {
        return companyId;
    }

    public TimeLeaveApplicationReflect toDomain() {
        return new TimeLeaveApplicationReflect(
                companyId,
                EnumAdaptor.valueOf(workTimeReflectAtr, NotUseAtr.class),
                new TimeLeaveDestination(
                        EnumAdaptor.valueOf(beforeWorkReflectAtr1, NotUseAtr.class),
                        EnumAdaptor.valueOf(beforeWorkReflectAtr2, NotUseAtr.class),
                        EnumAdaptor.valueOf(outPrivateReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(outUnionReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(afterWorkRelfectAtr1, NotUseAtr.class),
                        EnumAdaptor.valueOf(afterWorkReflectAtr2, NotUseAtr.class)
                ),
                new TimeLeaveAppReflectCondition(
                        EnumAdaptor.valueOf(sixtyOvertimeReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(careReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(childCareRelfectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(substituteHolidayReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(annualHolidayReflectAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(specialHolidayReflectAtr, NotUseAtr.class)
                )
        );
    }

    public static KrqmtAppTimeHd fromDomain(TimeLeaveApplicationReflect domain) {
        return new KrqmtAppTimeHd(
                domain.getCompanyId(),
                domain.getCondition().getSuperHoliday60H().value,
                domain.getCondition().getNursing().value,
                domain.getCondition().getChildNursing().value,
                domain.getCondition().getSubstituteLeaveTime().value,
                domain.getCondition().getAnnualVacationTime().value,
                domain.getCondition().getSpecialVacationTime().value,
                domain.getDestination().getFirstBeforeWork().value,
                domain.getDestination().getFirstAfterWork().value,
                domain.getDestination().getSecondBeforeWork().value,
                domain.getDestination().getSecondAfterWork().value,
                domain.getDestination().getPrivateGoingOut().value,
                domain.getDestination().getUnionGoingOut().value,
                domain.getReflectActualTimeZone().value
        );
    }

    public void update(TimeLeaveApplicationReflect domain) {
        this.sixtyOvertimeReflectAtr = domain.getCondition().getSuperHoliday60H().value;
        this.careReflectAtr = domain.getCondition().getNursing().value;
        this.childCareRelfectAtr = domain.getCondition().getChildNursing().value;
        this.substituteHolidayReflectAtr = domain.getCondition().getSubstituteLeaveTime().value;
        this.annualHolidayReflectAtr = domain.getCondition().getAnnualVacationTime().value;
        this.specialHolidayReflectAtr = domain.getCondition().getSpecialVacationTime().value;
        this.beforeWorkReflectAtr1 = domain.getDestination().getFirstBeforeWork().value;
        this.afterWorkRelfectAtr1 = domain.getDestination().getFirstAfterWork().value;
        this.beforeWorkReflectAtr2 = domain.getDestination().getSecondBeforeWork().value;
        this.afterWorkReflectAtr2 = domain.getDestination().getSecondAfterWork().value;
        this.outPrivateReflectAtr = domain.getDestination().getPrivateGoingOut().value;
        this.outUnionReflectAtr = domain.getDestination().getUnionGoingOut().value;
        this.workTimeReflectAtr = domain.getReflectActualTimeZone().value;
    }
}
