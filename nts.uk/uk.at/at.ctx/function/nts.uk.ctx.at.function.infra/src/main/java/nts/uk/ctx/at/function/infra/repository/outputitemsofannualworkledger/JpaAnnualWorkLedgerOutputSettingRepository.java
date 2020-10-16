package nts.uk.ctx.at.function.infra.repository.outputitemsofannualworkledger;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSettingRepository;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DailyOutputItemsAnnualWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.FormOutputItemName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger.KfnmtRptYrRecDispCont;
import nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger.KfnmtRptYrRecItem;
import nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger.KfnmtRptYrRecSetting;
import nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger.KfnmtRptYrRecSettingPk;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.KfnmtRptWkRecDispCont;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.KfnmtRptWkRecItem;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.KfnmtRptWkRecSetting;
import nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable.KfnmtRptWkRecSettingPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaAnnualWorkLedgerOutputSettingRepository extends JpaRepository implements AnnualWorkLedgerOutputSettingRepository {
    private static final String FIND_LIST_OUT_PUT_STATUS;

    private static final String FIND_LIST_FREE_SETTING_ITEM;

    private static final String FIND_WORK_SETTING;

    private static final String FIND_WORK_ITEM;

    private static final String FIND_WORK_CONST;

    private static final String DELETE_WORK_CONST;

    private static final String DELETE_WORK_ITEM;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptYrRecSetting a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.settingType  =:settingType ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_OUT_PUT_STATUS = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptYrRecSetting a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.settingType  =:settingType ");
        builderString.append(" AND  a.employeeId  =:employeeId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_FREE_SETTING_ITEM = builderString.toString();
        
        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptYrRecSetting a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_WORK_SETTING = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptYrRecItem a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.pk.itemPos ");
        FIND_WORK_ITEM = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptYrRecDispCont a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY   a.pk.iD, a.pk.itemPos, a.pk.attendanceId ");
        FIND_WORK_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("DELETE a ");
        builderString.append("FROM KfnmtRptWkRecDispCont a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        DELETE_WORK_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("DELETE a ");
        builderString.append("FROM KfnmtRptWkRecItem a ");
        builderString.append("WHERE a.companyID  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        DELETE_WORK_ITEM = builderString.toString();
    }

    @Override
    public List<AnnualWorkLedgerOutputSetting> getAListOfOutputSettings(String cid, SettingClassificationCommon classificationCommon) {
        return this.queryProxy().query(FIND_LIST_OUT_PUT_STATUS, KfnmtRptYrRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingType", classificationCommon.toString())
                .getList(JpaAnnualWorkLedgerOutputSettingRepository::toDomain);
    }

    @Override
    public List<AnnualWorkLedgerOutputSetting> getTheFreeSettingOutputItemList(String cid, SettingClassificationCommon classificationCommon, String employeeId) {
        return this.queryProxy().query(FIND_LIST_FREE_SETTING_ITEM, KfnmtRptYrRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingType", classificationCommon.toString())
                .setParameter("employeeId", employeeId)
                .getList(JpaAnnualWorkLedgerOutputSettingRepository::toDomain);
    }

    @Override
    public AnnualWorkLedgerOutputSetting getDetailsOfTheOutputSettings(String cid, String settingId) {
        val itemList = this.queryProxy().query(FIND_WORK_CONST, KfnmtRptYrRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getList();

        val outputItem = this.queryProxy().query(FIND_WORK_ITEM, KfnmtRptYrRecItem.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getList(JpaAnnualWorkLedgerOutputSettingRepository::toDomain);
        outputItem.forEach(e -> {
            e.setSelectedAttendanceItemList(itemList.stream().filter(i -> i.pk.itemPos == e.getRank())
                    .map(JpaAnnualWorkLedgerOutputSettingRepository::toDomain).collect(Collectors.toList()));
        });

        val rs = this.queryProxy().query(FIND_WORK_SETTING, KfnmtRptYrRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getSingle(JpaAnnualWorkLedgerOutputSettingRepository::toDomain).get();
        rs.setOutputItemList(outputItem);
        return rs;
    }

    @Override
    public void createNewFixedSelection(String cid, AnnualWorkLedgerOutputSetting outputSetting, List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        val entitySetting = KfnmtRptYrRecSetting.fromDomain(cid, outputSetting);
        this.commandProxy().insert(entitySetting);

        val listEntityItems = KfnmtRptYrRecItem.fromDomain(outputSetting, outputItemsOfTheDayList, outputItemList);
        if (!listEntityItems.isEmpty()) {
            this.commandProxy().insertAll(listEntityItems);
        }
        val listEntityConst = KfnmtRptYrRecDispCont.fromDomain(outputSetting, outputItemsOfTheDayList, outputItemList, attendanceItemList);
        if (!listEntityConst.isEmpty()) {
            this.commandProxy().insertAll(listEntityConst);
        }
    }

    @Override
    public void createNewFreeSetting(String cid, String employeeId, AnnualWorkLedgerOutputSetting outputSetting, List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        val entitySetting = KfnmtRptYrRecSetting.fromDomain(cid, outputSetting);
        entitySetting.employeeId = employeeId;
        this.commandProxy().insert(entitySetting);

        val listEntityItems = KfnmtRptYrRecItem.fromDomain(outputSetting, outputItemsOfTheDayList, outputItemList);
        if (!listEntityItems.isEmpty()) {
            this.commandProxy().insertAll(listEntityItems);
        }
        val listEntityConst = KfnmtRptYrRecDispCont.fromDomain(outputSetting, outputItemsOfTheDayList, outputItemList, attendanceItemList);
        if (!listEntityConst.isEmpty()) {
            this.commandProxy().insertAll(listEntityConst);
        }
    }

    @Override
    public void updateBoilerpalteSection(String cid, String settingId, AnnualWorkLedgerOutputSetting outputSetting, List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        this.commandProxy().update(KfnmtRptYrRecSetting.fromDomain(cid, outputSetting));
        this.commandProxy().updateAll(KfnmtRptYrRecItem.fromDomain(outputSetting, outputItemsOfTheDayList, outputItemList));
        this.queryProxy().query(DELETE_WORK_CONST, KfnmtRptYrRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);
        this.getEntityManager().flush();
        this.commandProxy().insertAll(KfnmtRptYrRecDispCont.fromDomain(outputSetting, outputItemsOfTheDayList, outputItemList, attendanceItemList));
    }

    @Override
    public void updateFreeSetting(String cid, String settingId, AnnualWorkLedgerOutputSetting outputSetting, List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
        this.commandProxy().update(KfnmtRptYrRecSetting.fromDomain(cid, outputSetting));
        this.commandProxy().updateAll(KfnmtRptYrRecItem.fromDomain(outputSetting, outputItemsOfTheDayList, outputItemList));
        this.queryProxy().query(DELETE_WORK_CONST, KfnmtRptYrRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);
        this.getEntityManager().flush();
        this.commandProxy().insertAll(KfnmtRptYrRecDispCont.fromDomain(outputSetting, outputItemsOfTheDayList, outputItemList, attendanceItemList));
    }

    @Override
    public void deleteSettingDetail(String cid, String settingId) {
        this.commandProxy().remove(KfnmtRptYrRecSetting.class, new KfnmtRptYrRecSettingPk(settingId));

        this.queryProxy().query(DELETE_WORK_CONST, KfnmtRptYrRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);

        this.queryProxy().query(DELETE_WORK_ITEM, KfnmtRptYrRecItem.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);
    }

    @Override
    public void duplicateConfigurationDetail(String cid, String optionalEmployeeId, String replicationSourceSettingsId,
                                             String destinationSettingId, OutputItemSettingCode outputItemSettingCode,
                                             OutputItemSettingName outputItemSettingName) {
        val optEntitySetting = this.queryProxy().query(FIND_WORK_SETTING, KfnmtRptYrRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingsId).getSingle();
        if (optEntitySetting.isPresent()) {
            val entitySettingCopy = optEntitySetting.get();
            entitySettingCopy.pk.setID(destinationSettingId);
            entitySettingCopy.name = outputItemSettingName.v();
            this.commandProxy().insert(entitySettingCopy);
        }
        val optEntityItem = this.queryProxy().query(FIND_WORK_ITEM, KfnmtRptYrRecItem.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingsId).getSingle();
        if (optEntityItem.isPresent()) {
            val entityItem = optEntityItem.get();
            entityItem.pk.setID(Integer.parseInt(destinationSettingId));
            entityItem.itemName = outputItemSettingName.v();
            this.commandProxy().insert(entityItem);
        }
        val optEntityConst = this.queryProxy().query(FIND_WORK_CONST, KfnmtRptWkRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingsId).getSingle();
        if (optEntityConst.isPresent()) {
            val entityConst = optEntityConst.get();
            entityConst.pk.setID(destinationSettingId);
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

    private static AnnualWorkLedgerOutputSetting toDomain(KfnmtRptYrRecSetting entity) {

        return new AnnualWorkLedgerOutputSetting(
                entity.getPk().getID(),
                new OutputItemSettingCode(Integer.toString(entity.displayCode)),
                new OutputItemSettingName(entity.name),
                EnumAdaptor.valueOf(entity.settingType, SettingClassificationCommon.class),
                null,
                entity.employeeId,
                null
        );
    }

    private static OutputItem toDomain(KfnmtRptYrRecItem entity) {
        return new OutputItem(
                entity.pk.itemPos,
                new FormOutputItemName(entity.itemName),
                entity.itemIsPrintEd,
                EnumAdaptor.valueOf(entity.itemCalculatorType, IndependentCalculationClassification.class),
                EnumAdaptor.valueOf(entity.itemAttendanceType, DailyMonthlyClassification.class),
                EnumAdaptor.valueOf(entity.itemAttribute, CommonAttributesOfForms.class),
                null
        );
    }

    private static OutputItemDetailSelectionAttendanceItem toDomain(KfnmtRptYrRecDispCont entity) {
        return new OutputItemDetailSelectionAttendanceItem(
                EnumAdaptor.valueOf(entity.operator, OperatorsCommonToForms.class),
                entity.pk.attendanceId
        );

    }
}
