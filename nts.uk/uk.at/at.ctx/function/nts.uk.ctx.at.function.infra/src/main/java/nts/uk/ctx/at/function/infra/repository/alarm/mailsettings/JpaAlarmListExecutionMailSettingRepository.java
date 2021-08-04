package nts.uk.ctx.at.function.infra.repository.alarm.mailsettings;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSetting;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSettingRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.mailsettings.KfnmtAlstExeMailSetting;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaAlarmListExecutionMailSettingRepository extends JpaRepository implements AlarmListExecutionMailSettingRepository {
    private static final String SELECT;
    private static final String SELECT_ALL_BY_CID_PM;
    private static final String SELECT_ALL_BY_CID_PM_IM;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a FROM KfnmtAlstExeMailSetting a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE a.pk.companyID = :cid AND a.pk.personWkpAtr = :individualWkpClassify");
        SELECT_ALL_BY_CID_PM = builderString.toString();
        SELECT_ALL_BY_CID_PM_IM = SELECT_ALL_BY_CID_PM + " AND a.pk.personalManagerAtr = :personalManagerClassify";
    }

    @Override
    public List<AlarmListExecutionMailSetting> getByCId(String cid, int individualWkpClassify) {
        return this.queryProxy().query(SELECT_ALL_BY_CID_PM, KfnmtAlstExeMailSetting.class)
                .setParameter("cid", cid)
                .setParameter("individualWkpClassify", individualWkpClassify)
                .getList(KfnmtAlstExeMailSetting::toDomain);
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

    @Override
    public void insertAll(List<AlarmListExecutionMailSetting> alarmExecMailSettings) {
        if (alarmExecMailSettings.isEmpty()) return;
        alarmExecMailSettings.forEach(entity -> {
            this.commandProxy().insert(KfnmtAlstExeMailSetting.of(entity));
        });
    }

    @Override
    public void updateAll(List<AlarmListExecutionMailSetting> alarmExecMailSettings) {
        if (alarmExecMailSettings.isEmpty()) return;
        alarmExecMailSettings.forEach(entity -> {
            val newEntity = KfnmtAlstExeMailSetting.of(entity);
            val updateEntity = this.queryProxy().find(newEntity.pk, KfnmtAlstExeMailSetting.class).orElse(null);
            if (updateEntity != null) {
                updateEntity.fromEntity(newEntity);
                this.commandProxy().update(updateEntity);
            }
        });
    }

    @Override
    public List<AlarmListExecutionMailSetting> getByCompanyId(String cid, int personalManagerClassify, int individualWRClassification) {
        return this.queryProxy().query(SELECT_ALL_BY_CID_PM_IM, KfnmtAlstExeMailSetting.class)
                .setParameter("cid", cid)
                .setParameter("individualWkpClassify", individualWRClassification)
                .setParameter("personalManagerClassify", personalManagerClassify)
                .getList(KfnmtAlstExeMailSetting::toDomain);
    }
}
