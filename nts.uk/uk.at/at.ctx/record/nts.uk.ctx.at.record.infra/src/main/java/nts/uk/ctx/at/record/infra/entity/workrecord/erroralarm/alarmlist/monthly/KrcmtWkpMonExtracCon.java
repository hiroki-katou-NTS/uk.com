package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    /* 月次抽出条件ID */
    @Id
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

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

    /* 平均時間 */
    @Column(name = "AVERAGE_TIME")
    public Integer averageTime;

    /* 平均比率 */
    @Column(name = "AVERAGE_RATE")
    public Integer averageRatio;

    /* チェック対象 */
    @Column(name = "CHK_TARGET")
    public String checkTarget;

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
        entity.contractCd = AppContexts.user().contractCode();
        entity.orderNumber = domain.getOrderNumber();
        entity.monExtracConName = domain.getMonExtracConName().v();
        entity.useAtr = domain.isUseAtr();
        entity.checkMonthlyItemsType = domain.getCheckMonthlyItemsType().value;
        entity.messageDisp = domain.getMessageDisp().v();

        val averageValueItem = domain.getAverageValueItem();
        if (averageValueItem != null) {
            entity.averageNumberOfDays = averageValueItem.getAverageNumberOfDays().isPresent() ? averageValueItem.getAverageNumberOfDays().get().value : null;

            entity.averageNumberOfTimes = averageValueItem.getAverageNumberOfTimes().isPresent() ? averageValueItem.getAverageNumberOfTimes().get().value : null;

            entity.averageRatio = averageValueItem.getAverageRatio().isPresent() ? averageValueItem.getAverageRatio().get().value : null;

            entity.averageTime = averageValueItem.getAverageTime().isPresent() ? averageValueItem.getAverageTime().get().value : null;

            entity.checkTarget = averageValueItem.getCheckTarget().isPresent() ? averageValueItem.getCheckTarget().get().v() : null;
        }

        entity.errorAlarmCheckID = domain.getErrorAlarmCheckID();

        return entity;
    }

    /**
     * @param checkConditions 勤務項目のチェック条件
     */
    public ExtractionMonthlyCon toDomain(CheckConditions checkConditions) {
        return ExtractionMonthlyCon.create(
                this.errorAlarmWorkplaceId,
                this.orderNumber,
                this.checkMonthlyItemsType,
                this.useAtr,
                this.errorAlarmCheckID,
                checkConditions,
                this.checkTarget,
                this.averageNumberOfDays,
                this.averageNumberOfTimes,
                this.averageTime,
                this.averageRatio,
                this.monExtracConName,
                this.messageDisp
        );
    }
}
