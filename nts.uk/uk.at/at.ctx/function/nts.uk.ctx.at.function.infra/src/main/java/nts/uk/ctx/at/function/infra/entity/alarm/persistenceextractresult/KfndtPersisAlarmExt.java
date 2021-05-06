package nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternName;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.AlarmEmployeeList;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.AlarmExtractInfoResult;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.ExtractResultDetail;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.PersistenceAlarmListExtractResult;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KFNDT_PERSIS_ALARM_EXT")
public class KfndtPersisAlarmExt extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final JpaEntityMapper<KfndtPersisAlarmExt> MAPPER = new JpaEntityMapper<>(KfndtPersisAlarmExt.class);

    @EmbeddedId
    public KfndtPersisAlarmExtPk pk;

    /**
     * 自動実行コード
     */
    @Column(name = "AUTORUN_CODE")
    public String autoRunCode;

    /**
     * アラームリストパターンコード
     */
    @Column(name = "PATTERN_CODE")
    public String patternCode;

    /**
     * アラームリストパターン名称
     */
    @Column(name = "PATTERN_NAME")
    public String patternName;

    @OneToMany(mappedBy = "persisAlarmExtract", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "KFNDT_ALARM_EXTRAC_RESULT")
    List<KfndtAlarmExtracResult> extractResults;

    @Override
    protected Object getKey() {
        return pk;
    }

    /**
     * Convert domain to entity
     *
     * @param domain PersistenceAlarmListExtractResult
     * @return KfndtPersisAlarmExt
     */
    public static KfndtPersisAlarmExt of(PersistenceAlarmListExtractResult domain) {
        String cid = domain.getCompanyID();
        String patternName = domain.getAlarmPatternName().v();
        String processId = IdentifierUtil.randomUniqueId();  // TODO: processId == runCode ????

        List<KfndtAlarmExtracResult> extractResults = new ArrayList<>();
        domain.getAlarmListExtractResults().forEach(x ->
                x.getAlarmExtractInfoResults().forEach(y -> {
                    y.getExtractionResultDetails().forEach(z ->
                            extractResults.add(new KfndtAlarmExtracResult(
                                    new KfndtAlarmExtracResultPK(
                                            cid,
                                            processId,
                                            x.getEmployeeID(),
                                            y.getAlarmCategory().value,
                                            y.getAlarmCheckConditionCode().v(),
                                            y.getAlarmListCheckType().value,
                                            y.getAlarmCheckConditionNo(),
                                            z.getPeriodDate().getStartDate().isPresent() ? String.valueOf(z.getPeriodDate().getStartDate()) : null
                                    ),
                                    z.getPeriodDate().getEndDate().isPresent() ? String.valueOf(z.getPeriodDate().getEndDate()) : null,
                                    patternName,
                                    z.getAlarmName(),
                                    z.getAlarmContent(),
                                    z.getRunTime(),
                                    z.getWpID().isPresent() ? z.getWpID().get() : null,
                                    z.getMessage().isPresent() ? z.getMessage().get() : null,
                                    z.getCheckValue().isPresent() ? z.getCheckValue().get() : null
                            )));
                }));

        return new KfndtPersisAlarmExt(
                new KfndtPersisAlarmExtPk(
                        cid,
                        processId
                ),
                domain.getAutoRunCode(),
                domain.getAlarmPatternCode().v(),
                domain.getAlarmPatternName().v(),
                extractResults);
    }

    /**
     * Convert entity to domain
     *
     * @return PersistenceAlarmListExtractResult
     */
    public PersistenceAlarmListExtractResult toDomain() {
        List<ExtractResultDetail> details = new ArrayList<>();
        this.extractResults.forEach(e -> details.add(new ExtractResultDetail(
                new ExtractionAlarmPeriodDate(Optional.ofNullable(StringUtils.isEmpty(e.pk.startDate) ? GeneralDate.fromString(e.pk.startDate, "yyyy/MM/dd") : null),
                        Optional.ofNullable(StringUtils.isEmpty(e.endDate) ? GeneralDate.fromString(e.endDate, "yyyy/MM/dd") : null)),
                e.alarmItemName,
                e.alarmContent,
                e.runTime,
                Optional.ofNullable(e.workPlaceId),
                Optional.ofNullable(e.message),
                Optional.ofNullable(e.checkValue))));

        List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>();
        List<AlarmEmployeeList> alarmListExtractResults = new ArrayList<>();

        Optional<KfndtAlarmExtracResult> firstDetail = this.extractResults.stream().findFirst();
        if (firstDetail.isPresent()) {
            alarmExtractInfoResults.add(new AlarmExtractInfoResult(
                    firstDetail.get().pk.alarmCheckCode,
                    new AlarmCheckConditionCode(firstDetail.get().pk.alarmCheckCode),
                    EnumAdaptor.valueOf(firstDetail.get().pk.category, AlarmCategory.class),
                    EnumAdaptor.valueOf(firstDetail.get().pk.checkAtr, AlarmListCheckType.class),
                    details));

            alarmListExtractResults.add(new AlarmEmployeeList(
                    alarmExtractInfoResults,
                    firstDetail.get().pk.sid));
        }

        return new PersistenceAlarmListExtractResult(
                new AlarmPatternCode(this.patternCode),
                new AlarmPatternName(this.patternName),
                alarmListExtractResults,
                this.pk.cid,
                this.autoRunCode);
    }
}
