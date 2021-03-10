package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * スケジュール月次のアラームチェック条件
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KfndtScheCondMonthLinkPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CID")
    public String cid;

    /* チェック条件コード */
    @Column(name = "AL_CHECK_COND_CATE_CD")
    public String ctgCd;

    /* カテゴリ */
    @Column(name = "CATEGORY")
    public int ctg;

    @Column(name = "ALARM_TYPE")
    public int alarmType;
}
