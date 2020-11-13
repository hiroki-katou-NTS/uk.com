package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval.CheckItemAppapv;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval.FixedExtractionAppapvItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

/**
 * Entity: アラームリスト（職場）申請承認の固定抽出項目
 *
 * @author Thanh.LNP
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKPFXEX_APPAPV_ITM")
public class KrcmtWkpfxexAppapvItm extends AggregateTableEntity {

    @EmbeddedId
    public KrcmtWkpfxexAppapvItmPK pk;

    /* 契約コード */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /* 申請承認チェック名称 */
    @Column(name = "APPLI_APPRO_CHKNAME")
    public String appapvCheckName;

    /* アラームチェック区分 */
    @Column(name = "ALARM_CHK_ATR")
    public int alarmCheckCls;

    /* 最初表示するメッセージ */
    @Column(name = "FIRST_MESSAGE_DIS")
    public String firstMessageDisp;

    /* メッセージを太字にする */
    @Column(name = "MESSAGE_BOLD")
    public boolean boldAtr;

    /* メッセージの色 */
    @Column(name = "MESSAGE_COLOR")
    public String messageColor;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static KrcmtWkpfxexAppapvItm fromDomain(FixedExtractionAppapvItems domain) {
        KrcmtWkpfxexAppapvItm entity = new KrcmtWkpfxexAppapvItm();

        entity.pk = KrcmtWkpfxexAppapvItmPK.fromDomain(domain);
        entity.contractCd = AppContexts.user().contractCode();
        entity.appapvCheckName = domain.getAppapvCheckName();
        entity.alarmCheckCls = domain.getAlarmCheckCls().value;
        entity.firstMessageDisp = domain.getFirstMessageDisp().v();
        entity.boldAtr = domain.isBoldAtr();

        entity.messageColor = domain.getMessageColor().isPresent() ? domain.getMessageColor().get().v() : null;

        return entity;
    }

    public FixedExtractionAppapvItems toDomain() {
        return FixedExtractionAppapvItems.create(
                EnumAdaptor.valueOf(this.pk.fixedCheckDayItems, CheckItemAppapv.class),
                EnumAdaptor.valueOf(this.alarmCheckCls, AlarmCheckClassification.class),
                this.boldAtr,
                this.appapvCheckName,
                new DisplayMessage(this.firstMessageDisp),
                Optional.of(new ColorCode(this.messageColor))
        );
    }
}
