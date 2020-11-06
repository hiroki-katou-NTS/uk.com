package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.AverageValueItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

/**
 * Entity: アラームリスト（職場）月次の抽出条件
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKPMON_EXTRAC_CON")
public class KrcmtWkpMonExtracCon extends AggregateTableEntity {
    @EmbeddedId
    public KrcmtWkpMonExtracConPK pk;

    /* 契約コード */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /* No */
    @Column(name = "ORDER_NUMBER")
    public int orderNumber;

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

    /* 平均日数 */
    @Column(name = "AVERAGE_DAY")
    public Integer averageNumberOfDays;

    /* 平均回数 */
    @Column(name = "AVERAGE_NUM_TIMES")
    public Integer averageNumberOfTimes;

    /* 平均比率 */
    @Column(name = "AVERAGE_RATE")
    public Integer averageRatio;

    /* 平均時間 */
    @Column(name = "AVERAGE_TIME")
    public Integer averageTime;

    /* チェック対象 */
    @Column(name = "CHK_TARGET")
    public String checkTarget;

    /*  */
    @Column(name = "CONDITION_COMPARE_ID")
    public String conditionCompareId;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static KrcmtWkpMonExtracCon fromDomain(ExtractionMonthlyCon domain) {
        KrcmtWkpMonExtracCon entity = new KrcmtWkpMonExtracCon();

        entity.pk = KrcmtWkpMonExtracConPK.fromDomain(domain);
        entity.contractCd = AppContexts.user().contractCode();
        entity.orderNumber = domain.getOrderNumber();
        entity.monExtracConName = domain.getMonExtracConName().v();
        entity.useAtr = domain.isUseAtr();
        entity.checkMonthlyItemsType = domain.getCheckMonthlyItemsType().value;
        entity.messageDisp = domain.getMessageDisp().v();

        if (domain.getAverageValueItem() != null) {
            if (domain.getAverageValueItem().getAverageNumberOfDays().isPresent()) {
                entity.averageNumberOfDays = domain.getAverageValueItem().getAverageNumberOfDays().get().value;
            }

            if (domain.getAverageValueItem().getAverageNumberOfTimes().isPresent()) {
                entity.averageNumberOfTimes = domain.getAverageValueItem().getAverageNumberOfTimes().get().value;
            }

            if (domain.getAverageValueItem().getAverageRatio().isPresent()) {
                entity.averageRatio = domain.getAverageValueItem().getAverageRatio().get().value;
            }

            if (domain.getAverageValueItem().getAverageTime().isPresent()) {
                entity.averageTime = domain.getAverageValueItem().getAverageTime().get().value;
            }

            if (domain.getAverageValueItem().getCheckTarget().isPresent()) {
                entity.checkTarget = domain.getAverageValueItem().getCheckTarget().get().v();
            }
        }

//        entity.conditionCompareId = domain.getCompareRange().

        return entity;
    }

    /**
     * @param compareRange       範囲との比較
     * @param compareSingleValue 単一値との比較
     */
    public ExtractionMonthlyCon toDomain(CompareRange compareRange, CompareSingleValue compareSingleValue) {
        Optional<BonusPaySettingCode> checkTarget = Optional.empty();
        if (!this.checkTarget.isEmpty()) {
            checkTarget = Optional.of(new BonusPaySettingCode(this.checkTarget));
        }

        Optional<AverageNumberOfTimes> averageNumberOfDays = Optional.empty();
        if (this.averageNumberOfDays != null) {
            averageNumberOfDays = Optional.of(EnumAdaptor.valueOf(this.averageNumberOfDays, AverageNumberOfTimes.class));
        }

        Optional<AverageNumberOfDays> averageNumberOfTimes = Optional.empty();
        if (this.averageNumberOfTimes != null) {
            averageNumberOfTimes = Optional.of(EnumAdaptor.valueOf(this.averageNumberOfTimes, AverageNumberOfDays.class));
        }

        Optional<AverageTime> averageRatio = Optional.empty();
        if (this.averageRatio != null) {
            averageRatio = Optional.of(EnumAdaptor.valueOf(this.averageRatio, AverageTime.class));
        }

        Optional<AverageRatio> averageTime = Optional.empty();
        if (this.averageTime != null) {
            averageTime = Optional.of(EnumAdaptor.valueOf(this.averageTime, AverageRatio.class));
        }

        ExtractionMonthlyCon domain = ExtractionMonthlyCon.create(
                this.pk.errorAlarmWorkplaceId,
                this.orderNumber,
                EnumAdaptor.valueOf(this.checkMonthlyItemsType, CheckMonthlyItemsType.class),
                this.useAtr,
                AverageValueItem.create(checkTarget, averageNumberOfDays, averageNumberOfTimes, averageRatio, averageTime),
                new NameAlarmExtractionCondition(this.monExtracConName),
                new DisplayMessage(this.messageDisp),
                compareRange,
                compareSingleValue
        );

        return domain;
    }
}
