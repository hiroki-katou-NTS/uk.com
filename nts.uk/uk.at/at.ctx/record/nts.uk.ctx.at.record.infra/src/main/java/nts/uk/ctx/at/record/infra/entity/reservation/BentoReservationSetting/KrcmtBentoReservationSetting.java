package nts.uk.ctx.at.record.infra.entity.reservation.BentoReservationSetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KRCMT_PREPARATION")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBentoReservationSetting extends ContractUkJpaEntity {

    @Id
    @Column(name = "CID")
    public String companyID;

    @Column(name = "OPE_CAT")
    public int operationDistinction;

    @Column(name = "REF_TIME")
    public int referenceTime;

    @Column(name = "ORDERED_DEA")
    public int orderDeadline;

    @Column(name = "ORDERED_MODIFY")
    public int orderData;

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
                OrderedData.valueOf(entity.orderData),
                OrderDeadline.valueOf(entity.orderDeadline)
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

    public static KrcmtBentoReservationSetting fromDomain(BentoReservationSetting bentoReservationSetting) {
        return new KrcmtBentoReservationSetting(
                bentoReservationSetting.getCompanyId(),
                bentoReservationSetting.getOperationDistinction().value,
                bentoReservationSetting.getAchievements().getReferenceTime().v(),
                bentoReservationSetting.getCorrectionContent().getOrderDeadline().value,
                bentoReservationSetting.getCorrectionContent().getOrderedData().value,
                bentoReservationSetting.getAchievements().getMonthlyResults().value,
                bentoReservationSetting.getAchievements().getDailyResults().value,
                bentoReservationSetting.getCorrectionContent().getContentChangeDeadline().value,
                bentoReservationSetting.getCorrectionContent().getContentChangeDeadlineDay().value);
    }
}
