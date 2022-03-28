package nts.uk.ctx.at.function.infra.repository.alarm.persistenceextractresult;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.database.DatabaseProduct;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult.KfndtAlarmExtracResult;
import nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult.KfndtAlarmExtracResultPK;
import nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult.KfndtPersisAlarmExt;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternName;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPersisAlarmListExtractResultRepository extends JpaRepository implements PersisAlarmListExtractResultRepository {

    @Override
    public Optional<PersistenceAlarmListExtractResult> getAlarmExtractResult(String runCode, String patternCode, List<String> empIds) {
        val sql1 = "select a from KfndtPersisAlarmExt a where a.pk.cid = :companyId and a.autoRunCode = :runCode and a.patternCode = :patternCode";
        val sql2 = "select a from KfndtAlarmExtracResult a where a.pk.processId = :processId and a.pk.sid in :empIds";

        Optional<KfndtPersisAlarmExt> data1 = this.queryProxy().query(sql1, KfndtPersisAlarmExt.class)
                .setParameter("companyId", AppContexts.user().companyId())
                .setParameter("runCode", runCode)
                .setParameter("patternCode", patternCode)
                .getSingle();
        if (!data1.isPresent()) return Optional.empty();

        List<KfndtAlarmExtracResult> data2 = this.queryProxy().query(sql2, KfndtAlarmExtracResult.class)
                .setParameter("processId", data1.get().pk.processId)
                .setParameter("empIds", empIds)
                .getList();

        return Optional.of(new PersistenceAlarmListExtractResult(
                new AlarmPatternCode(data1.get().patternCode),
                new AlarmPatternName(data1.get().patternName),
                KfndtAlarmExtracResult.toDomain(data2),
                data1.get().pk.cid,
                data1.get().autoRunCode
        ));
    }

    @Override
    public Optional<PersistenceAlarmListExtractResult> getAlarmExtractResult(String companyId, String patternCode, String runCode) {
        val sql1 = "select a from KfndtPersisAlarmExt a where a.pk.cid = :companyId and a.patternCode = :patternCode and a.autoRunCode = :runCode";
        val sql2 = "select a from KfndtAlarmExtracResult a where a.pk.cid = :companyId and a.pk.processId = :processId";

        Optional<KfndtPersisAlarmExt> data = this.queryProxy().query(sql1, KfndtPersisAlarmExt.class)
                .setParameter("companyId", companyId)
                .setParameter("patternCode", patternCode)
                .setParameter("runCode", runCode)
                .getSingle();
        if (!data.isPresent()) return Optional.empty();

        List<KfndtAlarmExtracResult> data2 = this.queryProxy().query(sql2, KfndtAlarmExtracResult.class)
                .setParameter("companyId", companyId)
                .setParameter("processId", data.get().pk.processId)
                .getList();

        return Optional.of(new PersistenceAlarmListExtractResult(
                new AlarmPatternCode(data.get().patternCode),
                new AlarmPatternName(data.get().patternName),
                KfndtAlarmExtracResult.toDomain(data2),
                companyId,
                data.get().autoRunCode
        ));
    }

    @Override
    public void insert(PersistenceAlarmListExtractResult domain) {
        if (domain == null || domain.getAlarmListExtractResults().isEmpty()) {
            return;
        }

        val data = this.getAlarmExtractResult(AppContexts.user().companyId(), domain.getAlarmPatternCode().v(), domain.getAutoRunCode());

        if (!data.isPresent()) {
            String processId = IdentifierUtil.randomUniqueId();
            KfndtPersisAlarmExt entity = KfndtPersisAlarmExt.of(domain, processId);
            this.commandProxy().insert(entity);
        } else {
            Optional<String> processId;
            StringBuilder builderString = new StringBuilder();
            builderString.append("a.PROCESS_ID FROM KFNDT_PERSIS_ALARM_EXT a");
            builderString.append(" INNER JOIN KFNDT_ALARM_EXTRAC_RESULT b ");
            builderString.append(" ON a.CID = b.CID AND a.PROCESS_ID = b.PROCESS_ID");
            builderString.append(" WHERE a.AUTORUN_CODE = ? AND a.PATTERN_CODE = ?");
            String stringQuery = builderString.toString();

            String sqlQuery;
            if (this.database().is(DatabaseProduct.POSTGRESQL)) {
                sqlQuery = "SELECT " + stringQuery + " LIMIT 1";
            } else {
                sqlQuery = "SELECT TOP 1 " + stringQuery;
            }

            try (PreparedStatement sql = this.connection().prepareStatement(sqlQuery)) {
                sql.setString(1, domain.getAutoRunCode());
                sql.setString(2, domain.getAlarmPatternCode().v());
                processId = new NtsResultSet(sql.executeQuery()).getSingle(rec -> rec.getString("PROCESS_ID"));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (processId.isPresent()) {
                KfndtPersisAlarmExt oldEntity = KfndtPersisAlarmExt.of(data.get(), processId.get());
                KfndtPersisAlarmExt updateEntity = KfndtPersisAlarmExt.of(domain, processId.get());
                oldEntity.extractResults.addAll(updateEntity.extractResults);
                this.commandProxy().update(oldEntity);
            }
        }
    }

    @Override
    public void delete(PersistenceAlarmListExtractResult domain) {
        if (domain == null || domain.getAlarmListExtractResults().isEmpty()) {
            return;
        }
        List<KfndtAlarmExtracResultPK> lstDelete = new ArrayList<>();
        String cid = domain.getCompanyID();
        for (AlarmEmployeeList alarmEmployeeList : domain.getAlarmListExtractResults()) {
            for (AlarmExtractInfoResult y : alarmEmployeeList.getAlarmExtractInfoResults()) {
                for (ExtractResultDetail z : y.getExtractionResultDetails()) {
                    lstDelete.add(new KfndtAlarmExtracResultPK(
                            cid,
                            "",
                            alarmEmployeeList.getEmployeeID(),
                            y.getAlarmCategory().value,
                            y.getAlarmCheckConditionCode().v(),
                            y.getAlarmListCheckType().value,
                            y.getAlarmCheckConditionNo(),
                            z.getPeriodDate().getStartDate().isPresent() ? String.valueOf(z.getPeriodDate().getStartDate().get()) : String.valueOf(GeneralDate.today())));
                }
            }
        }

        for (KfndtAlarmExtracResultPK x : lstDelete) {
            Optional<KfndtAlarmExtracResult> rs = this.queryProxy().query("select a from KfndtAlarmExtracResult a where a.pk.cid = :cid and a.pk.sid = :sid and a.pk.category = :category and a.pk.alarmCheckCode = :alarmCheckCode and a.pk.checkAtr = :checkAtr and a.pk.conditionNo = :conditionNo and a.pk.startDate = :startDate",
                    KfndtAlarmExtracResult.class)
                    .setParameter("cid", cid)
                    .setParameter("sid", x.sid)
                    .setParameter("category", x.category)
                    .setParameter("alarmCheckCode", x.alarmCheckCode)
                    .setParameter("checkAtr", x.checkAtr)
                    .setParameter("conditionNo", x.conditionNo)
                    .setParameter("startDate", x.startDate)
                    .getSingle();
            if (rs.isPresent()) {
                this.commandProxy().remove(rs.get());
            }
        }
    }

    @Override
    public List<PersistenceAlarmListExtractResult> getAlarmExtractResult(String companyId, List<String> employeeIds) {
        if (CollectionUtil.isEmpty(employeeIds)) return Collections.emptyList();

        List<PersistenceAlarmListExtractResult> results;
        List<KfndtAlarmExtracResult> extractResults = this.queryProxy()
                .query("select a from KfndtAlarmExtracResult a where a.pk.cid = :companyId and a.pk.sid in :employeeIds", KfndtAlarmExtracResult.class)
                .setParameter("companyId", companyId)
                .setParameter("employeeIds", employeeIds)
                .getList();

        List<String> processIds = extractResults.stream().map(i -> i.pk.processId).distinct().collect(Collectors.toList());
        if (processIds.isEmpty()) return Collections.emptyList();
        try (PreparedStatement statement = this.connection()
                .prepareStatement("select * from KFNDT_PERSIS_ALARM_EXT a where a.CID = ? and a.PROCESS_ID in ("
                        + processIds.stream().map(s -> "?").collect(Collectors.joining(",")) + ")")) {

            statement.setString(1, companyId);
            for (int i = 0; i < processIds.size(); i++) {
                statement.setString(i + 2, processIds.get(i));
            }

            results = new NtsResultSet(statement.executeQuery()).getList(rec -> {
                String processId = rec.getString("PROCESS_ID");
                List<AlarmEmployeeList> alarmListExtractResults = KfndtAlarmExtracResult.toDomain(extractResults.stream()
                        .filter(i -> i.pk.processId.equals(processId)).collect(Collectors.toList()));

                return new PersistenceAlarmListExtractResult(
                        new AlarmPatternCode(rec.getString("PATTERN_CODE")),
                        new AlarmPatternName(rec.getString("PATTERN_NAME")),
                        alarmListExtractResults,
                        rec.getString("CID"),
                        rec.getString("AUTORUN_CODE")
                );
            });

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return results;
    }

    @Override
    public void onlyDeleteParent(String cid, String patternCode, String runCode) {
        String query = "DELETE FROM KFNDT_PERSIS_ALARM_EXT WHERE CID = ? AND PATTERN_CODE = ? AND AUTORUN_CODE = ? ";
        PreparedStatement ps = null;
        try {
            ps = this.connection().prepareStatement(query);
            ps.setString(1, cid);
            ps.setString(2, patternCode);
            ps.setString(3, runCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
