package nts.uk.ctx.at.function.infra.repository.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettingsRepository;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.DisplayContentsOfWorkStatus;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.ItemsInTheWorkStatus;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.WorkStatusTableSettingPk;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.WorkStatusTableSettings;

import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import java.util.List;

@Stateless
public class JpaWorkStatusOutputSettingsRepository extends JpaRepository implements WorkStatusOutputSettingsRepository {
    private static final String FIND_LIST_WORK_STATUS;

    private static final String FIND_LIST_FREELY;

    private static final String FIND_WORK_STATUS_SETTING;

    private static final String FIND_WORK_STATUS_ITEM;

    private static final String FIND_WORK_STATUS_CONST;

    private static final String DELETE_WORK_STATUS_CONST;

    private static final String DELETE_WORK_STATUS_ITEM;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM WorkStatusTableSettings a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.employeeCode  =:employeeCode ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_WORK_STATUS = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM WorkStatusTableSettings a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.employeeCode  =:employeeCode ");
        builderString.append(" AND  a.employeeId  =:employeeId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_FREELY = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM WorkStatusTableSettings a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_WORK_STATUS_SETTING = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM ItemsInTheWorkStatus a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.pk.itemPos ");
        FIND_WORK_STATUS_ITEM = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM DisplayContentsOfWorkStatus a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.pk.itemPos, a.pk.iD, a.pk.attendanceId ");
        FIND_WORK_STATUS_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("DELETE a ");
        builderString.append("FROM DisplayContentsOfWorkStatus a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        DELETE_WORK_STATUS_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("DELETE a ");
        builderString.append("FROM ItemsInTheWorkStatus a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        DELETE_WORK_STATUS_ITEM = builderString.toString();
    }

    @Override
    public List<WorkStatusOutputSettings> getListWorkStatusOutputSettings(String cid, SettingClassificationCommon settingClassification) {
        return this.queryProxy().query(FIND_LIST_WORK_STATUS, WorkStatusTableSettings.class)
                .setParameter("cid", cid)
                .setParameter("employeeCode", settingClassification.toString())
                .getList(JpaWorkStatusOutputSettingsRepository::toDomain);
    }

    @Override
    public List<WorkStatusOutputSettings> getListOfFreelySetOutputItems(String cid, SettingClassificationCommon settingClassification, String employeeId) {
        return this.queryProxy().query(FIND_LIST_FREELY, WorkStatusTableSettings.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", employeeId)
                .setParameter("employeeCode", settingClassification.toString())
                .getList(JpaWorkStatusOutputSettingsRepository::toDomain);
    }

    @Override
    public WorkStatusOutputSettings getWorkStatusOutputSettings(String cid, String settingId) {
        // TODO
        this.queryProxy().query(FIND_WORK_STATUS_CONST, DisplayContentsOfWorkStatus.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getSingle(JpaWorkStatusOutputSettingsRepository::toDomain).get();

        this.queryProxy().query(FIND_WORK_STATUS_ITEM, ItemsInTheWorkStatus.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getSingle(JpaWorkStatusOutputSettingsRepository::toDomain).get();

        return this.queryProxy().query(FIND_WORK_STATUS_SETTING, WorkStatusTableSettings.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getSingle(JpaWorkStatusOutputSettingsRepository::toDomain).get();
    }

    @Override
    public void createNewFixedPhrase(String cid, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        val entitySetting = WorkStatusTableSettings.fromDomain(outputSettings, cid);
        this.commandProxy().insert(entitySetting);

        val listEntityItems = ItemsInTheWorkStatus.fromDomain(cid, outputSettings, outputItemList);
        if (!listEntityItems.isEmpty()) {
            this.commandProxy().insertAll(listEntityItems);
        }
        val listEntityConst = DisplayContentsOfWorkStatus.fromDomain(cid, outputSettings, outputItemList, attendanceItemList);
        if (!listEntityConst.isEmpty()) {
            this.commandProxy().insertAll(listEntityConst);
        }
    }

    @Override
    public void createNewFreeSetting(String cid, String employeeId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        val entitySetting = WorkStatusTableSettings.fromDomain(outputSettings, cid);
        entitySetting.employeeId = employeeId;
        this.commandProxy().insert(entitySetting);

        val listEntityItems = ItemsInTheWorkStatus.fromDomain(cid, outputSettings, outputItemList);
        if (!listEntityItems.isEmpty()) {
            this.commandProxy().insertAll(listEntityItems);
        }
        val listEntityConst = DisplayContentsOfWorkStatus.fromDomain(cid, outputSettings, outputItemList, attendanceItemList);
        if (!listEntityConst.isEmpty()) {
            this.commandProxy().insertAll(listEntityConst);
        }
    }

    @Override
    public void updateBoilerplateSelection(String cid, String settingId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
            this.commandProxy().update(WorkStatusTableSettings.fromDomain(outputSettings, cid));
            this.commandProxy().updateAll(ItemsInTheWorkStatus.fromDomain(cid, outputSettings, outputItemList));
            this.queryProxy().query(DELETE_WORK_STATUS_CONST,DisplayContentsOfWorkStatus.class)
                    .setParameter("cid",cid)
                    .setParameter("settingId",settingId);
            this.getEntityManager().flush();
            this.commandProxy().insertAll(DisplayContentsOfWorkStatus.fromDomain(cid, outputSettings, outputItemList, attendanceItemList));

    }

    @Override
    public void updateFreeSettings(String cid, String settingId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        this.commandProxy().update(WorkStatusTableSettings.fromDomain(outputSettings, cid));
        this.commandProxy().updateAll(ItemsInTheWorkStatus.fromDomain(cid, outputSettings, outputItemList));
        this.queryProxy().query(DELETE_WORK_STATUS_CONST,DisplayContentsOfWorkStatus.class)
                .setParameter("cid",cid)
                .setParameter("settingId",settingId);
        this.getEntityManager().flush();
        this.commandProxy().insertAll(DisplayContentsOfWorkStatus.fromDomain(cid, outputSettings, outputItemList, attendanceItemList));

    }

    @Override
    public void deleteTheSettingDetails(String cid, String settingId) {
        this.commandProxy().remove(WorkStatusTableSettings.class,new WorkStatusTableSettingPk(settingId));

        this.queryProxy().query(DELETE_WORK_STATUS_CONST,DisplayContentsOfWorkStatus.class)
                .setParameter("cid",cid)
                .setParameter("settingId",settingId);

        this.queryProxy().query(DELETE_WORK_STATUS_ITEM,ItemsInTheWorkStatus.class)
                .setParameter("cid",cid)
                .setParameter("settingId",settingId);
    }

    @Override
    public void duplicateConfigurationDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId, OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName) {
        val optEntity = this.queryProxy().query(FIND_WORK_STATUS_SETTING, WorkStatusTableSettings.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationDestinationSettingId).getSingle(JpaWorkStatusOutputSettingsRepository::toDomain);
        if (optEntity.isPresent()){
            val entitySetting = optEntity.get();
        }
        
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid) {
        return false;
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid, String employeeId) {
        return false;
    }

    private static WorkStatusOutputSettings toDomain(WorkStatusTableSettings entity) {

        return new WorkStatusOutputSettings();
    }

    private static WorkStatusOutputSettings toDomain(ItemsInTheWorkStatus entity) {

        return new WorkStatusOutputSettings();
    }

    private static WorkStatusOutputSettings toDomain(DisplayContentsOfWorkStatus entity) {

        return new WorkStatusOutputSettings();
    }
}
