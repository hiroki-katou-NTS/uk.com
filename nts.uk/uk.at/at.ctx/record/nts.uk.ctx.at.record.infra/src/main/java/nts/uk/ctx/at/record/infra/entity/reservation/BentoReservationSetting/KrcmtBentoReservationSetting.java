package nts.uk.ctx.at.record.infra.entity.reservation.BentoReservationSetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting.*;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.*;
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
                ContentChangeDeadline.valueOf(entity.contentChangeDeadline),
                ContentChangeDeadlineDay.valueOf(entity.contentChangeDeadlineDay),
                OrderedData.valueOf(entity.monthlyResults),
                OrderDeadline.valueOf(entity.dailyResults)
        );

        Achievements achievements = new Achievements(
                new ReferenceTime(entity.referenceTime),
                AchievementMethod.valueOf(entity.dailyResults),
                AchievementMethod.valueOf(entity.monthlyResults)
                );

        return new BentoReservationSetting(
                entity.companyID,
                OperationDistinction.valueOf(entity.operationDistinction),
                correctionContent,
                achievements
                );
    }
}
