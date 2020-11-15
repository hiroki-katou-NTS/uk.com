package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.ExtractionScheduleCon;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AggregateRoot: アラームリスト（職場別）スケジュール／日次の抽出条件
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_SCHEDAI_EXCON")
public class KrcmtWkpSchedaiExCon extends AggregateTableEntity {

    /* スケジュール／日次抽出条件ID */
    @Id
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* 契約コード */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /* No */
    @Column(name = "ORDER_NUMBER")
    public int orderNumber;

    /* 表示するメッセージ日次抽出条件名称 */
    @Column(name = "DAI_EXTRAC_CON_NAME")
    public String daiExtracConName;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    private boolean useAtr;

    /* チェック項目 */
    @Column(name = "CHECK_ITEM")
    public int checkDayItemsType;

    /* 表示するメッセージ */
    @Column(name = "MESSAGE_DISPLAY")
    public String messageDisp;

    /* 対比チェック対象 */
    @Column(name = "COMPARE_CHK_TARGET")
    public Integer contrastType;

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

    public static KrcmtWkpSchedaiExCon fromDomain(ExtractionScheduleCon domain) {
        KrcmtWkpSchedaiExCon entity = new KrcmtWkpSchedaiExCon();

        entity.errorAlarmWorkplaceId = domain.getErrorAlarmWorkplaceId();
        entity.contractCd = AppContexts.user().contractCode();
        entity.orderNumber = domain.getOrderNumber();
        entity.daiExtracConName = domain.getDaiExtracConName().v();
        entity.useAtr = domain.isUseAtr();
        entity.checkDayItemsType = domain.getCheckDayItemsType().value;
        entity.messageDisp = domain.getMessageDisp().v();

        val comparisonCheckItems = domain.getComparisonCheckItems();
        if (comparisonCheckItems != null) {
            entity.contrastType = comparisonCheckItems.getContrastType().isPresent() ? comparisonCheckItems.getContrastType().get().value : null;

            entity.checkTarget = comparisonCheckItems.getCheckTarget().isPresent() ? comparisonCheckItems.getCheckTarget().get().v() : null;
        }

        entity.errorAlarmCheckID = domain.getErrorAlarmCheckID();

        return entity;
    }

    /**
     * @param checkConditions 勤務項目のチェック条件
     */
    public ExtractionScheduleCon toDomain(CheckConditions checkConditions) {
        return ExtractionScheduleCon.create(
                this.errorAlarmWorkplaceId,
                this.orderNumber,
                this.checkDayItemsType,
                this.useAtr,
                this.errorAlarmCheckID,
                checkConditions,
                this.checkTarget,
                this.contrastType,
                this.daiExtracConName,
                this.messageDisp
        );
    }
}
