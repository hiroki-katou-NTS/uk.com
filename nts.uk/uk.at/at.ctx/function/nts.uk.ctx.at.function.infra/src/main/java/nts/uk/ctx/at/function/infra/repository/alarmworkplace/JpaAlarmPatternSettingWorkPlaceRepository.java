package nts.uk.ctx.at.function.infra.repository.alarmworkplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace.KfnmtALstWkpPtn;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace.KfnmtALstWkpPtnPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaAlarmPatternSettingWorkPlaceRepository extends JpaRepository implements AlarmPatternSettingWorkPlaceRepository {
    private static final String SELECT;

    private static final String SELECT_BY_ALARM_PATTERN_CD;
    private static final String SELECT_BY_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a FROM KfnmtALstWkpPtn a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE a.pk.companyID = :companyId AND a.pk.alarmPatternCD = :alarmPatternCode ");
        SELECT_BY_ALARM_PATTERN_CD = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE a.pk.companyID = :companyId ");
        SELECT_BY_CID = builderString.toString();

    }

    @Override
    public void create(AlarmPatternSettingWorkPlace domain) {
        this.commandProxy().insert(KfnmtALstWkpPtn.toEntity(domain));
    }

    @Override
    public void update(AlarmPatternSettingWorkPlace domain) {
        KfnmtALstWkpPtn newEntity = KfnmtALstWkpPtn.toEntity(domain);
        KfnmtALstWkpPtn updateEntity = this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtALstWkpPtn.class)
            .setParameter("companyId", domain.getCompanyID())
            .setParameter("alarmPatternCode", domain.getAlarmPatternCD().v()).getSingle().get();

        updateEntity.fromEntity(newEntity);
        this.commandProxy().update(updateEntity);
    }

    @Override
    public void delete(String companyId, String alarmPatternCode) {
        KfnmtALstWkpPtnPk pk = new KfnmtALstWkpPtnPk(companyId, alarmPatternCode);
        this.commandProxy().remove(KfnmtALstWkpPtn.class, pk);
    }

    @Override
    public List<AlarmPatternSettingWorkPlace> findByCompanyId(String cid) {
        return this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtALstWkpPtn.class)
            .setParameter("companyId", cid).getList(KfnmtALstWkpPtn::toDomain);
    }

    @Override
    public Optional<AlarmPatternSettingWorkPlace> getCheckCondition(String cid, AlarmPatternCode alarmPatternCode) {
        return this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtALstWkpPtn.class)
            .setParameter("companyId", cid).setParameter("alarmPatternCode", alarmPatternCode).getSingle(KfnmtALstWkpPtn::toDomain);
    }
}
