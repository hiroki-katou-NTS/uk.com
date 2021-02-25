package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageRatio;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.ToCheckConditions;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtEralstCndexprange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Entity: アラームリスト（職場）月次の抽出条件
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKPMON_EXTRAC_CON")
public class KrcmtWkpMonExtracCon extends UkJpaEntity implements Serializable {

    /* 月次抽出条件ID */
    @Id
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* No */
    @Column(name = "ORDER_NUMBER")
    public int no;

    /* 月次抽出条件名称 */
    @Column(name = "MONTH_EXTR_CON_NAME")
    public String monExtracConName;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    private boolean useAtr;

    /* チェック項目 */
    @Column(name = "CHECK_ITEM")
    public int checkMonthlyItemsType;

    /* 表示するメッセージ */
    @Column(name = "MESSAGE_DISPLAY")
    public String messageDisp;

//    /* 平均日数 */
//    @Column(name = "AVERAGE_DAY")
//    public Integer averageNumberOfDays;
//
//    /* 平均回数 */
//    @Column(name = "AVERAGE_NUM_TIMES")
//    public Integer averageNumberOfTimes;
//
//    /* 平均時間 */
//    @Column(name = "AVERAGE_TIME")
//    public Integer averageTime;

    /* 平均比率 */
    @Column(name = "AVERAGE_RATE")
    public Integer averageRatio;

    /* 勤務実績のエラーアラームチェックID */
    @Column(name = "CONDITION_COMPARE_ID")
    public String errorAlarmCheckID;

    @Override
    protected Object getKey() {
        return errorAlarmWorkplaceId;
    }

    public static KrcmtWkpMonExtracCon fromDomain(ExtractionMonthlyCon domain) {
        KrcmtWkpMonExtracCon entity = new KrcmtWkpMonExtracCon();

        entity.errorAlarmWorkplaceId = domain.getErrorAlarmWorkplaceId();
        entity.no = domain.getNo();
        entity.monExtracConName = domain.getMonExtracConName().v();
        entity.useAtr = domain.isUseAtr();
        entity.checkMonthlyItemsType = domain.getCheckMonthlyItemsType().value;
        entity.messageDisp = domain.getMessageDisp().v();
        entity.averageRatio = domain.getAverageRatio().isPresent() ? ((AverageRatio) domain.getAverageRatio().get()).value : null;
        entity.errorAlarmCheckID = domain.getErrorAlarmCheckID();
        return entity;
    }

    /**
     * @param compareSingle
     * @param compareRange
     * @param singleFixed
     */
    public ExtractionMonthlyCon toDomain(Optional<KrcstErAlCompareSingle> compareSingle, Optional<KrcstErAlCompareRange> compareRange, Optional<KrcstErAlSingleFixed> singleFixed, List<KrcmtEralstCndexprange> targetItem) {
        List<Integer> additions = targetItem.stream().filter(ti -> ti.targetAtr == 0)
                .map(ti -> ti.krcstErAlAtdTargetPK.attendanceItemId).collect(Collectors.toList());
        List<Integer> substractions = targetItem.stream()
                .filter(ti -> ti.targetAtr == 1).map(ti -> ti.krcstErAlAtdTargetPK.attendanceItemId)
                .collect(Collectors.toList());

        return ExtractionMonthlyCon.create(
                this.errorAlarmWorkplaceId,
                this.no,
                this.checkMonthlyItemsType,
                this.useAtr,
                this.errorAlarmCheckID,
                ToCheckConditions.checkMonExtracCon(this.checkMonthlyItemsType, compareSingle, compareRange, singleFixed),
                additions,
                substractions,
                this.averageRatio,
                this.monExtracConName,
                this.messageDisp
        );
    }
}
