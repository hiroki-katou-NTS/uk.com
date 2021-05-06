package nts.uk.ctx.at.function.infra.repository.alarm.persistenceextractresult;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.PersisAlarmListExtractResultRepository;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.PersistenceAlarmListExtractResult;
import nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult.KfndtPersisAlarmExt;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPersisAlarmListExtractResultRepository extends JpaRepository implements PersisAlarmListExtractResultRepository {
    @Override
    public Optional<PersistenceAlarmListExtractResult> getAlarmExtractResult(String runCode, String patternCode, List<String> empIds) {
        String sql = "SELECT * FROM KFNDT_PERSIS_ALARM_EXT kpae "
                + " JOIN KFNDT_ALARM_EXTRAC_RESULT kaer ON kpae.CID = kaer.CID AND kpae.PROCESS_ID = kaer.PROCESS_ID "
                + " WHERE kpae.AUTORUN_CODE = @runCode AND kpae.PATTERN_CODE = @patternCode AND kaer.SID IN @empIds";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("runCode", runCode)
                .paramString("patternCode", patternCode)
                .paramString("empIds", empIds)
                .getSingle(x -> KfndtPersisAlarmExt.MAPPER.toEntity(x).toDomain());
    }

    @Override
    public Optional<PersistenceAlarmListExtractResult> getAlarmExtractResult(String companyId, String patternCode, String runCode) {
        String sql = "SELECT * FROM KFNDT_PERSIS_ALARM_EXT kpae "
                + " WHERE kpae.AUTORUN_CODE = @runCode AND kpae.PATTERN_CODE = @patternCode AND kpae.CID = @companyId";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("runCode", runCode)
                .paramString("patternCode", patternCode)
                .paramString("companyId", companyId)
                .getSingle(x -> KfndtPersisAlarmExt.MAPPER.toEntity(x).toDomain());
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

        KfndtPersisAlarmExt entity = KfndtPersisAlarmExt.of(domain);
        this.commandProxy().remove(entity);
    }
}
