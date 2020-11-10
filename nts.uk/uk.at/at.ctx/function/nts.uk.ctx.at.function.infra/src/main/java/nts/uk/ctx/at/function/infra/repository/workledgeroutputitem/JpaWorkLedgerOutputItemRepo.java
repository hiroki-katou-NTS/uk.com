package nts.uk.ctx.at.function.infra.repository.workledgeroutputitem;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;
import nts.uk.ctx.at.function.infra.entity.outputitemofworkledger.KfnmtRptRecDispCont;
import nts.uk.ctx.at.function.infra.entity.outputitemofworkledger.KfnmtRptRecDispContPk;
import nts.uk.ctx.at.function.infra.entity.outputitemofworkledger.KfnmtRptRecSetting;
import nts.uk.ctx.at.function.infra.entity.outputitemofworkledger.KfnmtRptRecSettingPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaWorkLedgerOutputItemRepo extends JpaRepository implements WorkLedgerOutputItemRepo {

    private static final String FIND_LIST_WORK_LEDGER;

    private static final String FIND_LIST_FREELY;

    private static final String FIND_WORK_LEDGER_SETTING;

    private static final String FIND_WORK_LEDGER_CONST;

    private static final String FIND_DELETE_WORK_LEDGER_CONST;


    private static final String FIND_WORK_LEDGER_ITEM_BY_CODE;

    private static final String FIND_WORK_LEDGER_ITEM_BY_CODE_EMPLOYEE;

    private static final String DELETE_WORK_LEDGER_CONST_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.settingType  =:settingType ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_WORK_LEDGER = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.settingType  =:settingType ");
        builderString.append(" AND  a.employeeId  =:employeeId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_LIST_FREELY = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.displayCode ");
        FIND_WORK_LEDGER_SETTING = builderString.toString();


        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptRecDispCont a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        builderString.append(" ORDER BY  a.printPosition");
        FIND_WORK_LEDGER_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("DELETE  ");
        builderString.append("FROM KfnmtRptRecDispCont a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.pk.iD  =:settingId ");
        DELETE_WORK_LEDGER_CONST_CID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptRecDispCont a ");
        builderString.append(" WHERE  a.pk.iD  =:settingId ");
        FIND_DELETE_WORK_LEDGER_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.displayCode  =:displayCode ");
        FIND_WORK_LEDGER_ITEM_BY_CODE = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptRecSetting a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.employeeId  =:employeeId ");
        builderString.append(" AND  a.displayCode  =:displayCode ");
        FIND_WORK_LEDGER_ITEM_BY_CODE_EMPLOYEE = builderString.toString();
    }
    @Override
    public List<WorkLedgerOutputItem> getlistForStandard(String cid, SettingClassificationCommon settingClassic) {
        return this.queryProxy().query(FIND_LIST_WORK_LEDGER, KfnmtRptRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingType", settingClassic.value)
                .getList(JpaWorkLedgerOutputItemRepo::toDomain);
    }

    @Override
    public List<WorkLedgerOutputItem> getListOfFreely(String cid, SettingClassificationCommon settingClassification, String employeeId) {
        return this.queryProxy().query(FIND_LIST_FREELY, KfnmtRptRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", employeeId)
                .setParameter("settingType", settingClassification.value)
                .getList(JpaWorkLedgerOutputItemRepo::toDomain);
    }

    @Override
    public WorkLedgerOutputItem getWorkStatusOutputSettings(String cid, String settingId) {
        val result = this.queryProxy().query(FIND_WORK_LEDGER_SETTING, KfnmtRptRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getSingle(JpaWorkLedgerOutputItemRepo::toDomain);
        if (!result.isPresent()) {
            return null;
        }
        WorkLedgerOutputItem rs = result.get();
        // todo
        return rs;
    }

    @Override
    public void createNew(String cid, WorkLedgerOutputItem outputSetting, List<AttendanceItemToPrint> outputItemList) {
        val entitySetting = KfnmtRptRecSetting.fromDomain(outputSetting, cid);
        this.commandProxy().insert(entitySetting);

        val listEntityCont = KfnmtRptRecDispCont.fromDomain(cid,outputSetting,outputItemList);
        if (!listEntityCont.isEmpty()) {
            this.commandProxy().insertAll(listEntityCont);
        }

    }

    @Override
    public void update(String cid, String settingId, WorkLedgerOutputItem outputSetting, List<AttendanceItemToPrint> outputItemList) {
        this.commandProxy().update(KfnmtRptRecSetting.fromDomain(outputSetting, cid));
        this.queryProxy().query(DELETE_WORK_LEDGER_CONST_CID, KfnmtRptRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId);
        this.getEntityManager().flush();
        this.commandProxy().insertAll(KfnmtRptRecDispCont.fromDomain(cid, outputSetting, outputItemList));

    }

    @Override
    public void delete(String settingId) {
        this.commandProxy().remove(KfnmtRptRecSetting.class, new KfnmtRptRecSettingPk(settingId));

        val entityConst =  this.queryProxy().query(FIND_DELETE_WORK_LEDGER_CONST, KfnmtRptRecDispCont.class)
                .setParameter("settingId", settingId).getList();
        if(!CollectionUtil.isEmpty(entityConst)){
            this.commandProxy().removeAll(entityConst);
        }
        this.getEntityManager().flush();
    }

    @Override
    public void duplicateConfigDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId, OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName) {
        val optEntitySetting = this.queryProxy().query(FIND_WORK_LEDGER_SETTING, KfnmtRptRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingId).getSingle();
        if (optEntitySetting.isPresent()) {
            KfnmtRptRecSetting entitySetting = optEntitySetting.get();
            val entity = new KfnmtRptRecSetting(
                    new KfnmtRptRecSettingPk(replicationDestinationSettingId),
                    entitySetting.contractCode,
                    entitySetting.companyId,
                    Integer.parseInt(duplicateCode.v()),
                    copyDestinationName.v(),
                    entitySetting.employeeId,
                    entitySetting.settingType
            );
            this.commandProxy().insert(entity);
        }


        val optEntityConst = this.queryProxy().query(FIND_WORK_LEDGER_CONST, KfnmtRptRecDispCont.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingId).getList();
        if (!optEntityConst.isEmpty()) {
            val listItem = optEntityConst.stream().map(e->new KfnmtRptRecDispCont(
                    new KfnmtRptRecDispContPk(replicationDestinationSettingId,e.pk.attendanceItemId),
                    e.contractCode,
                    e.companyId,
                    e.printPosition
            ) ).collect(Collectors.toList());
            this.commandProxy().insertAll(listItem);
        }
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid) {
        val displayCode = Integer.parseInt(code.v());
        val rs = this.queryProxy().query(FIND_WORK_LEDGER_ITEM_BY_CODE, KfnmtRptRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("displayCode", displayCode)
                .getSingleOrNull(JpaWorkLedgerOutputItemRepo::toDomain);
        return rs != null;
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid, String employeeId) {
        val displayCode = Integer.parseInt(code.v());
        val rs = this.queryProxy().query(FIND_WORK_LEDGER_ITEM_BY_CODE_EMPLOYEE, KfnmtRptRecSetting.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", employeeId)
                .setParameter("displayCode", displayCode)
                .getSingleOrNull(JpaWorkLedgerOutputItemRepo::toDomain);
        return rs != null;
    }

    private static WorkLedgerOutputItem toDomain(KfnmtRptRecSetting entity) {

        return new WorkLedgerOutputItem(
                entity.pk.getID(),
                new OutputItemSettingCode(Integer.toString(entity.displayCode)),
				null,
                new OutputItemSettingName(entity.name),
                EnumAdaptor.valueOf(entity.settingType, SettingClassificationCommon.class),
                entity.employeeId
        );
    }
}
