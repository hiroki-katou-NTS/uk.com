package nts.uk.ctx.at.function.infra.repository.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.*;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaWorkStatusOutputSettingsRepository extends JpaRepository implements WorkStatusOutputSettingsRepository {
    private static final String FIND_LIST_WORK_STATUS;

    private static final String FIND_LIST_FREELY;

    private static final String FIND_WORK_STATUS_SETTING;

    private static final String FIND_WORK_STATUS_ITEM;

    private static final String FIND_WORK_STATUS_CONST;

    private static final String FIND_DELETE_WORK_STATUS_CONST;

    private static final String FIND_DELETE_WORK_STATUS_ITEM;

    private static final String FIND_WORK_STATUS_ITEM_BY_CODE;

    private static final String FIND_WORK_STATUS_ITEM_BY_CODE_EMPLOYEE;

    private static final String DELETE_WORK_STATUS_CONST_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.settingType  =:settingType ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_WORK_STATUS = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.settingType  =:settingType ");
        builderString.append(" AND  a.employeeId  =:employeeId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_FREELY = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.iD  =:settingId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_WORK_STATUS_SETTING = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecItem a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.pk.itemPos ");
        FIND_WORK_STATUS_ITEM = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecDispCont a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.pk.itemPos, a.pk.iD, a.pk.attendanceId ");
        FIND_WORK_STATUS_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("DELETE  ");
        builderString.append("FROM KfnmtRptWkRecDispCont a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        DELETE_WORK_STATUS_CONST_CID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecDispCont a ");
        builderString.append(" WHERE  a.pk.iD  =:settingId ");
        FIND_DELETE_WORK_STATUS_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a  ");
        builderString.append("FROM KfnmtRptWkRecItem a ");
        builderString.append(" WHERE  a.pk.iD  =:settingId ");
        FIND_DELETE_WORK_STATUS_ITEM = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.displayCode  =:displayCode ");
        FIND_WORK_STATUS_ITEM_BY_CODE = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.employeeId  =:employeeId ");
        builderString.append(" AND  a.displayCode  =:displayCode ");
        FIND_WORK_STATUS_ITEM_BY_CODE_EMPLOYEE = builderString.toString();
    }

    @Override
    public List<WorkStatusOutputSettings> getListWorkStatusOutputSettings(String cid, SettingClassificationCommon settingClassification) {
        return this.queryProxy().query(FIND_LIST_WORK_STATUS, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingType", settingClassification.value)
                .getList(JpaWorkStatusOutputSettingsRepository::toDomain);
    }

    @Override
    public List<WorkStatusOutputSettings> getListOfFreelySetOutputItems(String cid, SettingClassificationCommon settingClassification, String employeeId) {
        return this.queryProxy().query(FIND_LIST_FREELY, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", employeeId)
                .setParameter("settingType", settingClassification.value)
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
        outputItem.forEach(e -> {
            e.setSelectedAttendanceItemList(itemList.stream().filter(i -> i.pk.itemPos == e.getRank())
                    .map(JpaWorkStatusOutputSettingsRepository::toDomain).collect(Collectors.toList()));
        });

        val result = this.queryProxy().query(FIND_WORK_STATUS_SETTING, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getSingle(e -> JpaWorkStatusOutputSettingsRepository.toDomain(e, outputItem));
        return result.orElse(null);

    }

    @Override
    public void createNew(String cid, WorkStatusOutputSettings outputSettings) {
        val entitySetting = KfnmtRptWkRecSetting.fromDomain(outputSettings, cid);
        this.commandProxy().insert(entitySetting);

        val listEntityItems = KfnmtRptWkRecItem.fromDomain(cid, outputSettings);
        if (!listEntityItems.isEmpty()) {
            this.commandProxy().insertAll(listEntityItems);
        }
        val listEntityConst = KfnmtRptWkRecDispCont.fromDomain(cid, outputSettings);
        if (!listEntityConst.isEmpty()) {
            this.commandProxy().insertAll(listEntityConst);
        }
    }

    @Override
    public void update(String cid, WorkStatusOutputSettings outputSettings) {
        val settingId = outputSettings.getSettingId();
        this.commandProxy().update(KfnmtRptWkRecSetting.fromDomain(outputSettings, cid));
        val entityItem = this.queryProxy().query(FIND_DELETE_WORK_STATUS_ITEM, KfnmtRptWkRecItem.class)
                .setParameter("settingId", settingId).getList();
        if (!CollectionUtil.isEmpty(entityItem)) {
            this.commandProxy().removeAll(entityItem);
            this.getEntityManager().flush();
            this.commandProxy().insertAll(KfnmtRptWkRecItem.fromDomain(cid, outputSettings));
        }
        val entityConst = this.queryProxy().query(FIND_DELETE_WORK_STATUS_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("settingId", settingId)
                .getList();
        if (!CollectionUtil.isEmpty(entityConst)) {
            this.commandProxy().removeAll(entityConst);
            this.getEntityManager().flush();
            this.commandProxy().insertAll(KfnmtRptWkRecDispCont.fromDomain(cid, outputSettings));
        }

    }

    @Override
    public void delete(String settingId) {
        this.commandProxy().remove(KfnmtRptWkRecSetting.class, settingId);
        val entityConst = this.queryProxy().query(FIND_DELETE_WORK_STATUS_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("settingId", settingId)
                .getList();
        if (!CollectionUtil.isEmpty(entityConst)) {
            this.commandProxy().removeAll(entityConst);
        }
        val entityItem = this.queryProxy().query(FIND_DELETE_WORK_STATUS_ITEM, KfnmtRptWkRecItem.class)
                .setParameter("settingId", settingId)
                .getList();
        if (!CollectionUtil.isEmpty(entityItem)) {
            this.commandProxy().removeAll(entityItem);
        }
    }

    @Override
    public void duplicateConfigurationDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId, OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName) {
        val optEntitySetting = this.queryProxy().query(FIND_WORK_STATUS_SETTING, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingId).getSingle();
        if (optEntitySetting.isPresent()) {
            KfnmtRptWkRecSetting entitySetting = optEntitySetting.get();
            val entity = new KfnmtRptWkRecSetting(
                    replicationDestinationSettingId,
                    entitySetting.contractCode,
                    entitySetting.companyId,
                    Integer.parseInt(duplicateCode.v()),
                    copyDestinationName.v(),
                    entitySetting.employeeId,
                    entitySetting.settingType
            );
            this.commandProxy().insert(entity);
        }
        val optEntityItem = this.queryProxy().query(FIND_WORK_STATUS_ITEM, KfnmtRptWkRecItem.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingId).getList();
        if (!optEntityItem.isEmpty()) {
            val listItem = optEntityItem.stream().map(e -> new KfnmtRptWkRecItem(
                    new KfnmtRptWkRecItemPk(replicationDestinationSettingId, e.pk.itemPos),
                    e.contractCode,
                    e.companyId,
                    e.itemName,
                    e.itemIsPrintEd,
                    e.itemType,
                    e.itemAtribute
            )).collect(Collectors.toList());
            this.commandProxy().insertAll(listItem);
        }
        val optEntityConst = this.queryProxy().query(FIND_WORK_STATUS_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingId).getList();
        if (!optEntityConst.isEmpty()) {
            val listItem = optEntityConst.stream().map(e -> new KfnmtRptWkRecDispCont(
                    new KfnmtRptWkRecDispContPk(replicationDestinationSettingId, e.pk.itemPos, e.pk.attendanceId),
                    e.contractCode,
                    e.companyId,
                    e.operator
            )).collect(Collectors.toList());
            this.commandProxy().insertAll(listItem);
        }
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid) {
        val displayCode = Integer.parseInt(code.v());
        val rs = this.queryProxy().query(FIND_WORK_STATUS_ITEM_BY_CODE, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("displayCode", displayCode)
                .getSingleOrNull(JpaWorkStatusOutputSettingsRepository::toDomain);
        return rs != null;
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid, String employeeId) {
        val displayCode = Integer.parseInt(code.v());
        val rs = this.queryProxy().query(FIND_WORK_STATUS_ITEM_BY_CODE_EMPLOYEE, KfnmtRptWkRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", employeeId)
                .setParameter("displayCode", displayCode)
                .getSingleOrNull(JpaWorkStatusOutputSettingsRepository::toDomain);
        return rs != null;
    }

    private static WorkStatusOutputSettings toDomain(KfnmtRptWkRecSetting entity) {

        return new WorkStatusOutputSettings(
                entity.iD,
                new OutputItemSettingCode(Integer.toString(entity.displayCode)),
                new OutputItemSettingName(entity.name),
                entity.employeeId,
                EnumAdaptor.valueOf(entity.settingType, SettingClassificationCommon.class),
                null
        );
    }

    private static WorkStatusOutputSettings toDomain(KfnmtRptWkRecSetting entity, List<OutputItem> listItems) {

        return new WorkStatusOutputSettings(
                entity.iD,
                new OutputItemSettingCode(Integer.toString(entity.displayCode)),
                new OutputItemSettingName(entity.name),
                entity.employeeId,
                EnumAdaptor.valueOf(entity.settingType, SettingClassificationCommon.class),
                listItems
        );
    }

    private static OutputItem toDomain(KfnmtRptWkRecItem entity) {
        return new OutputItem(
                entity.pk.itemPos,
                new FormOutputItemName(entity.itemName),
                entity.itemIsPrintEd,
                EnumAdaptor.valueOf(entity.itemType, IndependentCalcClassic.class),
                DailyMonthlyClassification.DAILY,
                EnumAdaptor.valueOf(entity.itemAtribute, CommonAttributesOfForms.class),
                null
        );
    }

    private static OutputItemDetailAttItem toDomain(KfnmtRptWkRecDispCont entity) {
        return new OutputItemDetailAttItem(
                EnumAdaptor.valueOf(entity.operator, OperatorsCommonToForms.class),
                entity.pk.attendanceId
        );

    }
}
