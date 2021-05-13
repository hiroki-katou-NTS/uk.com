package nts.uk.ctx.at.function.infra.repository.alarm.persistenceextractresult;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult.KfndtPersisAlarmExt;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternName;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPersisAlarmListExtractResultRepository extends JpaRepository implements PersisAlarmListExtractResultRepository {
    @SneakyThrows
    @Override
    public Optional<PersistenceAlarmListExtractResult> getAlarmExtractResult(String runCode, String patternCode, List<String> empIds) {
        List<PersisAlarmExtractResultDto> data;
        List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>();
        try (PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KFNDT_PERSIS_ALARM_EXT a1"
                + " INNER JOIN KFNDT_ALARM_EXTRAC_RESULT a2 "
                + " ON a1.CID = a2.CID AND a1.PROCESS_ID = a2.PROCESS_ID"
                + " WHERE a1.AUTORUN_CODE = ?"
                + " AND a1.PATTERN_CODE = ?"
                + " AND a2.SID IN ("
                + NtsStatement.In.createParamsString(empIds) + ")")) {
            sql.setString(1, runCode);
            sql.setString(2, patternCode);
            for (int i = 0; i < empIds.size(); i++) {
                sql.setString(3 + i, empIds.get(i));
            }

            data = new NtsResultSet(sql.executeQuery()).getList(JpaPersisAlarmListExtractResultRepository::toDto);

            if (data.isEmpty()) {
                return Optional.empty();
            }

            Map<String, List<PersisAlarmExtractResultDto>> dataMap = data.parallelStream().collect(Collectors.groupingBy(PersisAlarmExtractResultDto::getEmployeeID));
            dataMap.entrySet().parallelStream().forEach(c -> {
                c.getValue().parallelStream().forEach(x -> {
                    List<ExtractResultDetail> extractDetails = Collections.singletonList(
                            new ExtractResultDetail(
                                    new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.fromString(x.getStartDate(), "yyyy/MM/dd")),
                                            Optional.ofNullable(StringUtils.isEmpty(x.getEndDate()) ? GeneralDate.fromString(x.getEndDate(), "yyyy/MM/dd") : null)),
                                    x.getAlarmPatternName(),
                                    x.getAlarmContent(),
                                    x.getRunTime(),
                                    Optional.ofNullable(x.getWpID()),
                                    Optional.ofNullable(x.getMessage()),
                                    Optional.ofNullable(x.getCheckValue())));

                    alarmExtractInfoResults.add(
                            new AlarmExtractInfoResult(x.getAlarmCheckConditionNo(),
                                    new AlarmCheckConditionCode(x.getAlarmCheckConditionCode()),
                                    EnumAdaptor.valueOf(x.getAlarmCategory(), AlarmCategory.class),
                                    EnumAdaptor.valueOf(x.getAlarmListCheckType(), AlarmListCheckType.class),
                                    extractDetails));
                });
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(new PersistenceAlarmListExtractResult(
                new AlarmPatternCode(data.get(0).getAlarmPatternCode()),
                new AlarmPatternName(data.get(0).getAlarmPatternName()),
                Collections.singletonList(new AlarmEmployeeList(
                        alarmExtractInfoResults,
                        data.get(0).getEmployeeID()
                )),
                data.get(0).getCompanyID(),
                data.get(0).getAutoRunCode()));
    }

    private static PersisAlarmExtractResultDto toDto(NtsResultSet.NtsResultRecord rec) {
        PersisAlarmExtractResultDto dto = new PersisAlarmExtractResultDto(
                rec.getString("PATTERN_CODE"),
                rec.getString("PATTERN_NAME"),
                rec.getString("CID"),
                rec.getString("AUTORUN_CODE"),
                rec.getString("SID"),
                rec.getString("CONDITION_NO"),
                rec.getString("ALARM_CHECK_CODE"),
                rec.getInt("CATEGORY"),
                rec.getInt("CHECK_ATR"),
                rec.getString("START_DATE"),
                rec.getString("END_DATE"),
                rec.getString("ALARM_CONTENT"),
                rec.getGeneralDateTime("RUN_TIME"),
                rec.getString("WORKPLACE_ID"),
                rec.getString("MESSAGE"),
                rec.getString("CHECK_VALUE")
        );
        return dto;
    }

    @Override
    public Optional<PersistenceAlarmListExtractResult> getAlarmExtractResult(String companyId, String patternCode, String runCode) {
        String sql = "SELECT a FROM KfndtPersisAlarmExt a WHERE a.pk.cid = :companyId AND a.patternCode = :patternCode AND a.autoRunCode = :runCode ";

        return this.queryProxy().query(sql, KfndtPersisAlarmExt.class)
                .setParameter("companyId", companyId)
                .setParameter("patternCode", patternCode)
                .setParameter("runCode", runCode)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public void insert(PersistenceAlarmListExtractResult domain) {
        if (domain == null || domain.getAlarmListExtractResults().isEmpty()) {
            return;
        }

        KfndtPersisAlarmExt entity = KfndtPersisAlarmExt.of(domain);
        this.commandProxy().insert(entity);
    }

    @Override
    public void delete(PersistenceAlarmListExtractResult domain) {
        if (domain == null || domain.getAlarmListExtractResults().isEmpty()) {
            return;
        }

        String cid = domain.getCompanyID();
        String sql = "DELETE FROM KfndtPersisAlarmExt a JOIN KfndtAlarmExtracResult b ON a.pk.cid = b.pk.cid AND a.pk.processId = b.pk.processId"
                + "WHERE a.pk.cid = :cid";

        this.getEntityManager().createQuery(sql).setParameter("cid", cid).executeUpdate();
        this.getEntityManager().flush();

//        KfndtPersisAlarmExtPk pk = new KfndtPersisAlarmExtPk(domain.getCompanyID(), "processId-TODO");
//        this.commandProxy().remove(pk);
    }

    @Override
    public List<PersistenceAlarmListExtractResult> getAlarmExtractResult(String companyId, List<String> employeeIds) {
        String sql = "SELECT * FROM KFNDT_PERSIS_ALARM_EXT kpae "
                + " JOIN KFNDT_ALARM_EXTRAC_RESULT kaer ON kpae.CID = kaer.CID AND kpae.PROCESS_ID = kaer.PROCESS_ID "
                + " WHERE kpae.CID = @companyId AND kaer.SID IN @empIds";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("empIds", employeeIds)
                .getList(x -> KfndtPersisAlarmExt.MAPPER.toEntity(x).toDomain());
    }
}
