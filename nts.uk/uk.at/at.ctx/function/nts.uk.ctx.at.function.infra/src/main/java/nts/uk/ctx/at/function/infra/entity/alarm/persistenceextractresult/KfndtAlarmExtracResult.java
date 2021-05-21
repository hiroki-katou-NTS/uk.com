package nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult;

import lombok.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmCheckConditionCode;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmEmployeeList;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmExtractInfoResult;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.ExtractResultDetail;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "KFNDT_ALARM_EXTRAC_RESULT")
public class KfndtAlarmExtracResult extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @JoinColumns({
            @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
            @JoinColumn(name = "PROCESS_ID", referencedColumnName = "PROCESS_ID", insertable = false, updatable = false)
    })
    public KfndtPersisAlarmExt persisAlarmExtract;

    @Override
    protected Object getKey() {
        return pk;
    }

    public KfndtAlarmExtracResult(KfndtAlarmExtracResultPK pk, String endDate, String patternName, String alarmItemName, String alarmContent, GeneralDateTime runTime, String workPlaceId, String message, String checkValue) {
        super();
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

    public static List<AlarmEmployeeList> toDomain(List<KfndtAlarmExtracResult> entities) {
        List<AlarmEmployeeList> domains = new ArrayList<>();
        Map<String, List<KfndtAlarmExtracResult>> mapBySid = entities.stream().collect(Collectors.groupingBy(e -> e.pk.sid));
        mapBySid.forEach((sid, entitiesOfSid) -> {
            List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>();
            Map<GroupKey, List<KfndtAlarmExtracResult>> mapByGroupKey = entities.stream().collect(Collectors.groupingBy(e -> new GroupKey(e.pk.conditionNo, e.pk.alarmCheckCode, e.pk.category, e.pk.checkAtr)));
            mapByGroupKey.forEach((key, value) -> {
                value.sort(Comparator.comparing(i -> i.pk.startDate));
                AlarmExtractInfoResult infoResult = new AlarmExtractInfoResult(
                        key.conditionNo,
                        new AlarmCheckConditionCode(key.alarmCheckCode),
                        EnumAdaptor.valueOf(key.category, AlarmCategory.class),
                        EnumAdaptor.valueOf(key.checkAtr, AlarmListCheckType.class),
                        value.stream().map(e -> new ExtractResultDetail(
                                new ExtractionAlarmPeriodDate(
                                        Optional.of(GeneralDate.fromString(e.pk.startDate, "yyyy/MM/dd")),
                                        Optional.ofNullable(e.endDate == null ? null : GeneralDate.fromString(e.endDate, "yyyy/MM/dd"))
                                ),
                                e.alarmItemName,
                                e.alarmContent,
                                e.runTime,
                                Optional.ofNullable(e.workPlaceId),
                                Optional.ofNullable(e.message),
                                Optional.ofNullable(e.checkValue)
                        )).collect(Collectors.toList())
                );
                alarmExtractInfoResults.add(infoResult);
            });
            domains.add(new AlarmEmployeeList(alarmExtractInfoResults, sid));
        });
        return domains;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    static class GroupKey {
        public String conditionNo;
        public String alarmCheckCode;
        public int category;
        public int checkAtr;
    }
}