package nts.uk.ctx.at.function.infra.repository.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.*;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.IndependentCalculationClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.KfnmtRptWkRecDispCont;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.KfnmtRptWkRecItem;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.KfnmtRptWkRecSetting;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.KfnmtRptWkRecSettingPk;

import javax.ejb.Stateless;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        builderString.append("FROM KfnmtRptWkRecSetting a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.employeeCode  =:employeeCode ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_WORK_STATUS = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecSetting a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.employeeCode  =:employeeCode ");
        builderString.append(" AND  a.employeeId  =:employeeId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_FREELY = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecSetting a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_WORK_STATUS_SETTING = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecItem a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.pk.itemPos ");
        FIND_WORK_STATUS_ITEM = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecDispCont a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.pk.itemPos, a.pk.iD, a.pk.attendanceId ");
        FIND_WORK_STATUS_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("DELETE a ");
        builderString.append("FROM KfnmtRptWkRecDispCont a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        DELETE_WORK_STATUS_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("DELETE a ");
        builderString.append("FROM KfnmtRptWkRecItem a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        DELETE_WORK_STATUS_ITEM = builderString.toString();
    }

    @Override
    public List<WorkStatusOutputSettings> getListWorkStatusOutputSettings(String cid, SettingClassificationCommon settingClassification) {
        return this.queryProxy().query(FIND_LIST_WORK_STATUS, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("employeeCode", settingClassification.toString())
                .getList(JpaWorkStatusOutputSettingsRepository::toDomain);
    }

    @Override
    public List<WorkStatusOutputSettings> getListOfFreelySetOutputItems(String cid, SettingClassificationCommon settingClassification, String employeeId) {
        return this.queryProxy().query(FIND_LIST_FREELY, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", employeeId)
                .setParameter("employeeCode", settingClassification.toString())
                .getList(JpaWorkStatusOutputSettingsRepository::toDomain);
    }

    @Override
    public WorkStatusOutputSettings getWorkStatusOutputSettings(String cid, String settingId) {
        val itemList = this.queryProxy().query(FIND_WORK_STATUS_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getList();

        val outputItem = this.queryProxy().query(FIND_WORK_STATUS_ITEM, KfnmtRptWkRecItem.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getList(JpaWorkStatusOutputSettingsRepository::toDomain);
        outputItem.forEach(e->{
            e.setSelectedAttendanceItemList(itemList.stream().filter(i->i.pk.itemPos==e.getRank())
                    .map(JpaWorkStatusOutputSettingsRepository::toDomain).collect(Collectors.toList()));
        });

        val rs = this.queryProxy().query(FIND_WORK_STATUS_SETTING, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getSingle(JpaWorkStatusOutputSettingsRepository::toDomain).get();
        rs.setOutputItem(outputItem);
        return rs;
    }

    @Override
    public void createNewFixedPhrase(String cid, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        val entitySetting = KfnmtRptWkRecSetting.fromDomain(outputSettings, cid);
        this.commandProxy().insert(entitySetting);

        val listEntityItems = KfnmtRptWkRecItem.fromDomain(cid, outputSettings, outputItemList);
        if (!listEntityItems.isEmpty()) {
            this.commandProxy().insertAll(listEntityItems);
        }
        val listEntityConst = KfnmtRptWkRecDispCont.fromDomain(cid, outputSettings, outputItemList, attendanceItemList);
        if (!listEntityConst.isEmpty()) {
            this.commandProxy().insertAll(listEntityConst);
        }
    }

    @Override
    public void createNewFreeSetting(String cid, String employeeId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        val entitySetting = KfnmtRptWkRecSetting.fromDomain(outputSettings, cid);
        entitySetting.employeeId = employeeId;
        this.commandProxy().insert(entitySetting);

        val listEntityItems = KfnmtRptWkRecItem.fromDomain(cid, outputSettings, outputItemList);
        if (!listEntityItems.isEmpty()) {
            this.commandProxy().insertAll(listEntityItems);
        }
        val listEntityConst = KfnmtRptWkRecDispCont.fromDomain(cid, outputSettings, outputItemList, attendanceItemList);
        if (!listEntityConst.isEmpty()) {
            this.commandProxy().insertAll(listEntityConst);
        }
    }

    @Override
    public void updateBoilerplateSelection(String cid, String settingId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        this.commandProxy().update(KfnmtRptWkRecSetting.fromDomain(outputSettings, cid));
        this.commandProxy().updateAll(KfnmtRptWkRecItem.fromDomain(cid, outputSettings, outputItemList));
        this.queryProxy().query(DELETE_WORK_STATUS_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);
        this.getEntityManager().flush();
        this.commandProxy().insertAll(KfnmtRptWkRecDispCont.fromDomain(cid, outputSettings, outputItemList, attendanceItemList));

    }

    @Override
    public void updateFreeSettings(String cid, String settingId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        this.commandProxy().update(KfnmtRptWkRecSetting.fromDomain(outputSettings, cid));
        this.commandProxy().updateAll(KfnmtRptWkRecItem.fromDomain(cid, outputSettings, outputItemList));
        this.queryProxy().query(DELETE_WORK_STATUS_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);
        this.getEntityManager().flush();
        this.commandProxy().insertAll(KfnmtRptWkRecDispCont.fromDomain(cid, outputSettings, outputItemList, attendanceItemList));

    }

    @Override
    public void deleteTheSettingDetails(String cid, String settingId) {
        this.commandProxy().remove(KfnmtRptWkRecSetting.class, new KfnmtRptWkRecSettingPk(settingId));

        this.queryProxy().query(DELETE_WORK_STATUS_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);

        this.queryProxy().query(DELETE_WORK_STATUS_ITEM, KfnmtRptWkRecItem.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);
    }

    @Override
    public void duplicateConfigurationDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId, OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName) {
        val optEntitySetting = this.queryProxy().query(FIND_WORK_STATUS_SETTING, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationDestinationSettingId).getSingle();
        if (optEntitySetting.isPresent()) {
            val entitySettingCopy = optEntitySetting.get();
            entitySettingCopy.pk.setID(duplicateCode.v());
            entitySettingCopy.name = copyDestinationName.v();
            this.commandProxy().insert(entitySettingCopy);
        }
        val optEntityItem = this.queryProxy().query(FIND_WORK_STATUS_ITEM, KfnmtRptWkRecItem.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationDestinationSettingId).getSingle();
        if (optEntityItem.isPresent()) {
            val entityItem = optEntityItem.get();
            entityItem.pk.setID(duplicateCode.v());
            entityItem.itemName = copyDestinationName.v();
            this.commandProxy().insert(entityItem);
        }
        val optEntityConst = this.queryProxy().query(FIND_WORK_STATUS_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationDestinationSettingId).getSingle();
        if (optEntityConst.isPresent()) {
            val entityConst = optEntityConst.get();
            entityConst.pk.setID(duplicateCode.v());
            this.commandProxy().insert(entityConst);
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

    private static WorkStatusOutputSettings toDomain(KfnmtRptWkRecSetting entity) {

        return new WorkStatusOutputSettings(
                entity.pk.getID(),
                new OutputItemSettingCode(Integer.toString(entity.displayCode)),
                new OutputItemSettingName(entity.name),
                entity.employeeId,
                EnumAdaptor.valueOf(entity.employeeCode, SettingClassificationCommon.class),
                null
        );
    }

    private static OutputItem toDomain(KfnmtRptWkRecItem entity) {
        return new OutputItem(
                entity.pk.itemPos,
                new FormOutputItemName(entity.itemName),
                entity.itemIsPrintEd,
                EnumAdaptor.valueOf(entity.itemType, IndependentCalculationClassification.class),
                null,
                EnumAdaptor.valueOf(entity.itemAtribute, CommonAttributesOfForms.class),
                null
        );
    }

    private static OutputItemDetailSelectionAttendanceItem toDomain(KfnmtRptWkRecDispCont entity) {
        return new OutputItemDetailSelectionAttendanceItem(
                EnumAdaptor.valueOf(entity.operator, OperatorsCommonToForms.class),
                entity.pk.attendanceId
        );

    }
}
