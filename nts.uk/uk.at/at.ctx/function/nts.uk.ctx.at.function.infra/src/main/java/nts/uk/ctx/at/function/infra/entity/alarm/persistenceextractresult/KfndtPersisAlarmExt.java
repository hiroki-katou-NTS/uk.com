package nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternName;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmEmployeeList;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersistenceAlarmListExtractResult;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<KfndtAlarmExtracResult> extractResults;

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
        String processId = IdentifierUtil.randomUniqueId();  // TODO: processId = randomUUID ???

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
                                            String.valueOf(z.getPeriodDate().getStartDate().get())
                                    ),
                                    z.getPeriodDate().getEndDate().isPresent() ? String.valueOf(z.getPeriodDate().getEndDate().get()) : null,
                                    domain.getAlarmPatternName().v(),
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
                extractResults
        );
    }

    /**
     * Convert entity to domain
     *
     * @return PersistenceAlarmListExtractResult
     */
    public PersistenceAlarmListExtractResult toDomain() {
        List<AlarmEmployeeList> alarmListExtractResults = this.extractResults.stream().map(KfndtAlarmExtracResult::toDomain).collect(Collectors.toList());

        return new PersistenceAlarmListExtractResult(
                new AlarmPatternCode(this.patternCode),
                new AlarmPatternName(this.patternName),
                alarmListExtractResults,
                this.pk.cid,
                this.autoRunCode);

//        Map<String, List<KfndtAlarmExtracResult>> mapEmpId = this.extractResults.stream()
//                .collect(Collectors.groupingBy(x -> x.pk.sid));
//        for (val item : mapEmpId.keySet()) {
//            val lstValue = mapEmpId.get(item)
//            List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>();
//            if (!lstValue.isEmpty()) {
//                List<ExtractResultDetail> details = new ArrayList<>();
//                lstValue.forEach(x -> {
//                    details.add(new ExtractResultDetail(
//                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.fromString(x.pk.startDate, "yyyy/MM/dd")),
//                                    Optional.ofNullable(StringUtils.isEmpty(x.endDate) ? GeneralDate.fromString(x.endDate, "yyyy/MM/dd") : null)),
//                            x.alarmItemName,
//                            x.alarmContent,
//                            x.runTime,
//                            Optional.ofNullable(x.workPlaceId),
//                            Optional.ofNullable(x.message),
//                            Optional.ofNullable(x.checkValue)));
//
//                    alarmExtractInfoResults.add(new AlarmExtractInfoResult(
//                            x.pk.alarmCheckCode,
//                            new AlarmCheckConditionCode(x.pk.alarmCheckCode),
//                            EnumAdaptor.valueOf(x.pk.category, AlarmCategory.class),
//                            EnumAdaptor.valueOf(x.pk.checkAtr, AlarmListCheckType.class),
//                            details));
//                });
//            }
//            alarmListExtractResults.add(new AlarmEmployeeList(
//                    alarmExtractInfoResults,
//                    item));
//        }
    }
}
