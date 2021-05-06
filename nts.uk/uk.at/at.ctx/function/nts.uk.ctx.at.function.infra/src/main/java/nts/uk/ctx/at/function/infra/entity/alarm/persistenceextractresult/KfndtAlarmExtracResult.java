package nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult;

import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "KFNDT_ALARM_EXTRAC_RESULT")
public class KfndtAlarmExtracResult extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final JpaEntityMapper<KfndtAlarmExtracResult> MAPPER = new JpaEntityMapper<>(KfndtAlarmExtracResult.class);

    @EmbeddedId
    public KfndtAlarmExtracResultPK pk;

    /** アラーム値日付：終了日 */
    @Column(name = "END_DATE")
    public String endDate;

    /** アラームリストパターン名称 */
    @Column(name = "PATTERN_NAME")
    public String patternName;

    /** アラーム項目 */
    @Column(name = "ALARM_ITEM_NAME")
    public String alarmItemName;

    /**  アラーム内容 */
    @Column(name = "ALARM_CONTENT")
    public String alarmContent;

    /** 発生日時 */
    @Column(name = "RUN_TIME")
    public GeneralDateTime runTime;

    /** 職場ID*/
    @Column(name = "WORKPLACE_ID")
    public String workPlaceId;

    /** アラームメッセージ */
    @Column(name = "MESSAGE")
    public String message;

    /** チェック対象値 */
    @Column(name = "CHECK_VALUE")
    public String checkValue;

    @ManyToOne
    @JoinColumns(
            {@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
                    @JoinColumn(name = "PROCESS_ID", referencedColumnName = "PROCESS_ID", insertable = false, updatable = false)})
    public KfndtPersisAlarmExt persisAlarmExtract;

    @Override
    protected Object getKey() {
        return pk;
    }

    public KfndtAlarmExtracResult(KfndtAlarmExtracResultPK pk, String endDate, String patternName, String alarmItemName, String alarmContent, GeneralDateTime runTime, String workPlaceId, String message, String checkValue) {
        this.pk = pk;
        this.endDate = endDate;
        this.patternName = patternName;
        this.alarmItemName = alarmItemName;
        this.alarmContent = alarmContent;
        this.runTime = runTime;
        this.workPlaceId = workPlaceId;
        this.message = message;
        this.checkValue = checkValue;
    }
}
