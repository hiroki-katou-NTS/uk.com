package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.CheckDayItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.ComparisonCheckItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.ContrastType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.ExtractionScheduleCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

import javax.persistence.*;
import java.util.Optional;

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
    @EmbeddedId
    public KrcmtWkpSchedaiExConPK pk;

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
        return pk;
    }

    public static KrcmtWkpSchedaiExCon fromDomain(ExtractionScheduleCon domain) {
        KrcmtWkpSchedaiExCon entity = new KrcmtWkpSchedaiExCon();

        entity.pk = KrcmtWkpSchedaiExConPK.fromDomain(domain);
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
        val checkTarget = Optional.of(new BonusPaySettingCode(this.checkTarget));

        val contrastType = this.contrastType != null ? Optional.of(EnumAdaptor.valueOf(this.contrastType, ContrastType.class)) : Optional.empty();

        return ExtractionScheduleCon.create(
                this.pk.errorAlarmWorkplaceId,
                this.orderNumber,
                EnumAdaptor.valueOf(this.checkDayItemsType, CheckDayItemsType.class),
                this.useAtr,
                this.errorAlarmCheckID,
                checkConditions,
                ComparisonCheckItems.create(checkTarget, contrastType),
                new NameAlarmExtractionCondition(this.daiExtracConName),
                new DisplayMessage(this.messageDisp)
        );
    }
}
