package nts.uk.ctx.at.record.infra.entity.reservation.setting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting.*;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KRCMT_PREPARATION")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBentoReservationSetting extends UkJpaEntity {

    @Id
    @Column(name = "CID")
    public String companyID;

    @Column(name = "OPE_CAT")
    public int operationDistinction;

    @Column(name = "REF_TIME")
    public int referenceTime;

    @Column(name = "ORDERED_DEA")
    public int orderDeadline;

    @Column(name = "MON_RES")
    public int monthlyResults;

    @Column(name = "DAY_RES")
    public int dailyResults;

    @Column(name = "ORDERED_CHA")
    public int contentChangeDeadline;

    @Column(name = "ORDERED_CHA_DAY")
    public int contentChangeDeadlineDay;

    @Override
    protected Object getKey() {
        return companyID;
    }

    public static BentoReservationSetting toDomain(KrcmtBentoReservationSetting entity) {

        CorrectionContent correctionContent = new CorrectionContent(
                EnumAdaptor.valueOf(entity.contentChangeDeadline, ContentChangeDeadline.class),
                EnumAdaptor.valueOf(entity.contentChangeDeadlineDay, ContentChangeDeadlineDay.class),
                EnumAdaptor.valueOf(entity.monthlyResults, OrderedData.class),
                EnumAdaptor.valueOf(entity.dailyResults, OrderDeadline.class)
        );

        Achievements achievements = new Achievements(
                new ReferenceTime(entity.referenceTime),
                EnumAdaptor.valueOf(entity.dailyResults, AchievementMethod.class),
                EnumAdaptor.valueOf(entity.monthlyResults, AchievementMethod.class)
        );

        return new BentoReservationSetting(
                new CompanyId(entity.companyID),
                EnumAdaptor.valueOf(entity.operationDistinction,OperationDistinction.class),
                correctionContent,
                achievements
        );
    }
}