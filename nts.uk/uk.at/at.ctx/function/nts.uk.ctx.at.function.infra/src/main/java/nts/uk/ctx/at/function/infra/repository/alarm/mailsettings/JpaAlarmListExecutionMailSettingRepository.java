package nts.uk.ctx.at.function.infra.repository.alarm.mailsettings;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSetting;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSettingRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.mailsettings.KfnmtAlstExeMailSetting;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaAlarmListExecutionMailSettingRepository extends JpaRepository implements AlarmListExecutionMailSettingRepository {
    private static final String SELECT;
    private static final String SELECT_ALL_BY_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a FROM KfnmtAlstExeMailSetting a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE a.pk.companyID = :cid AND a.pk.personWkpAtr = :personalManagerClassify");
        SELECT_ALL_BY_CID = builderString.toString();
    }

    @Override
    public List<AlarmListExecutionMailSetting> findAll(String cid, int personalManagerClassify) {
        return this.queryProxy().query(SELECT_ALL_BY_CID, KfnmtAlstExeMailSetting.class)
                .setParameter("cid", cid)
                .setParameter("personalManagerClassify", personalManagerClassify)
                .getList(KfnmtAlstExeMailSetting::toDomain);
    }

    @Override
    public Optional<AlarmListExecutionMailSetting> find(String cid) {
        return Optional.empty();
    }


    @Override
    public void insert(AlarmListExecutionMailSetting alarmExecMailSetting) {
        if (alarmExecMailSetting == null) return;
        this.commandProxy().insert(KfnmtAlstExeMailSetting.of(alarmExecMailSetting));
    }

    @Override
    public void update(AlarmListExecutionMailSetting alarmExecMailSetting) {
        if (alarmExecMailSetting == null) return;
        val newEntity = KfnmtAlstExeMailSetting.of(alarmExecMailSetting);
        val updateEntity = this.queryProxy().find(newEntity.pk, KfnmtAlstExeMailSetting.class).orElse(null);
        if (updateEntity != null) {
            updateEntity.fromEntity(newEntity);
            this.commandProxy().update(updateEntity);
        }
    }
}
