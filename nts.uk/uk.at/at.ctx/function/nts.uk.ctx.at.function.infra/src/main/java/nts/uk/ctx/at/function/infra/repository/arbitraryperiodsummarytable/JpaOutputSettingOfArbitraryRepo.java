package nts.uk.ctx.at.function.infra.repository.arbitraryperiodsummarytable;


import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitraryRepo;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.infra.entity.arbitraryperiodsummarytable.KfnmtRptWkAnpOut;
import nts.uk.ctx.at.function.infra.entity.arbitraryperiodsummarytable.KfnmtRptWkAnpOutAtd;
import nts.uk.ctx.at.function.infra.entity.arbitraryperiodsummarytable.KfnmtRptWkAnpOutatdPk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaOutputSettingOfArbitraryRepo extends JpaRepository implements OutputSettingOfArbitraryRepo {
    private static final String FIND_ANP_OUT_ITEM;

    private static final String FIND_LIST_FREELY;

    private static final String FIND_ANP_OUT_ITEM_SETTING;

    private static final String FIND_ANP_OUT_ITEMS;

    private static final String FIND_DELETE_ANP_OUT_CONST;

    private static final String FIND_ANP_OUT_ITEM_SETTING_BY_CODE;

    private static final String FIND_ANP_OUT_ITEM_BY_CODE_EMPLOYEE;

    private static final String FIND_ANP_OUT_ITEM_SETTING_FOR_DUP;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkAnpOut a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.settingType  =:settingType ");
        builderString.append(" ORDER BY  a.exportCd ");
        FIND_ANP_OUT_ITEM = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkAnpOut a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.settingType  =:settingType ");
        builderString.append(" AND  a.sid  =:employeeId ");
        builderString.append(" ORDER BY  a.exportCd ");
        FIND_LIST_FREELY = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkAnpOutAtd a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.pk.layOutId  =:settingId ");
        //builderString.append(" ORDER BY  a.atdItemId");
        FIND_ANP_OUT_ITEMS = builderString.toString();


        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkAnpOut a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.layOutId  =:settingId ");
        builderString.append(" ORDER BY  a.exportCd ");
        FIND_ANP_OUT_ITEM_SETTING = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkAnpOut a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.layOutId  =:settingId ");
        builderString.append(" ORDER BY  a.exportCd ");
        FIND_ANP_OUT_ITEM_SETTING_FOR_DUP = builderString.toString();


        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkAnpOutAtd a ");
        builderString.append(" WHERE  a.pk.layOutId  =:settingId ");
        FIND_DELETE_ANP_OUT_CONST = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkAnpOut a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.exportCd  =:displayCode ");
        builderString.append(" AND  a.settingType  =:settingType ");
        FIND_ANP_OUT_ITEM_SETTING_BY_CODE = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT a ");
        builderString.append("FROM KfnmtRptWkAnpOut a ");
        builderString.append("WHERE a.companyId  =:cid ");
        builderString.append(" AND  a.sid  =:employeeId ");
        builderString.append(" AND  a.exportCd  =:displayCode ");
        builderString.append(" AND  a.settingType  =:settingType ");
        FIND_ANP_OUT_ITEM_BY_CODE_EMPLOYEE = builderString.toString();
    }

    @Override
    public List<OutputSettingOfArbitrary> getlistForStandard(String cid, SettingClassificationCommon settingClassic) {
        return this.queryProxy().query(FIND_ANP_OUT_ITEM, KfnmtRptWkAnpOut.class)
                .setParameter("cid", cid)
                .setParameter("settingType", settingClassic.value)
                .getList(JpaOutputSettingOfArbitraryRepo::toDomain);
    }

    @Override
    public List<OutputSettingOfArbitrary> getListOfFreely(String cid, SettingClassificationCommon settingClassification,
                                                          String employeeId) {
        return this.queryProxy().query(FIND_LIST_FREELY, KfnmtRptWkAnpOut.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", employeeId)
                .setParameter("settingType", settingClassification.value)
                .getList(JpaOutputSettingOfArbitraryRepo::toDomain);
    }

    @Override
    public OutputSettingOfArbitrary getOutputSettingOfArbitrary(String cid, String settingId) {

        val outputItem = this.queryProxy().query(FIND_ANP_OUT_ITEMS, KfnmtRptWkAnpOutAtd.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId)
                .getList(JpaOutputSettingOfArbitraryRepo::toDomain);

        val result = this.queryProxy().query(FIND_ANP_OUT_ITEM_SETTING, KfnmtRptWkAnpOut.class)
                .setParameter("cid", cid)
                .setParameter("settingId", settingId).getSingle(e -> JpaOutputSettingOfArbitraryRepo.toDomain(e, outputItem));
        return result.orElse(null);

    }

    @Override
    public void createNew(String cid, OutputSettingOfArbitrary outputSetting) {
        val entitySetting = KfnmtRptWkAnpOut.fromDomain(outputSetting, cid);
        this.commandProxy().insert(entitySetting);

        val listEntityCont = KfnmtRptWkAnpOutAtd.fromDomain(outputSetting);
        if (!listEntityCont.isEmpty()) {
            this.commandProxy().insertAll(listEntityCont);
        }

    }

    @Override
    public void update(String cid, String settingId, OutputSettingOfArbitrary outputSetting) {
        this.commandProxy().update(KfnmtRptWkAnpOut.fromDomain(outputSetting, cid));
        val entity = this.queryProxy().query(FIND_DELETE_ANP_OUT_CONST, KfnmtRptWkAnpOutAtd.class)
                .setParameter("settingId", settingId).getList();
        if (!CollectionUtil.isEmpty(entity)) {
            this.commandProxy().removeAll(entity);
            this.getEntityManager().flush();
        }
        this.commandProxy().insertAll(KfnmtRptWkAnpOutAtd.fromDomain(outputSetting));
    }

    @Override
    public void delete(String settingId) {
        this.commandProxy().remove(KfnmtRptWkAnpOut.class, settingId);
        val entityConst = this.queryProxy().query(FIND_DELETE_ANP_OUT_CONST, KfnmtRptWkAnpOutAtd.class)
                .setParameter("settingId", settingId).getList();
        if (!CollectionUtil.isEmpty(entityConst)) {
            this.commandProxy().removeAll(entityConst);
        }
    }

    @Override
    public void duplicateConfigDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId, OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName) {
        val optEntitySetting = this.queryProxy().query(FIND_ANP_OUT_ITEM_SETTING_FOR_DUP, KfnmtRptWkAnpOut.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingId).getSingle();
        if (optEntitySetting.isPresent()) {
            KfnmtRptWkAnpOut entitySetting = optEntitySetting.get();
            val entity = new KfnmtRptWkAnpOut(
                    replicationDestinationSettingId,
                    entitySetting.contractCd,
                    entitySetting.companyId,
                    Integer.parseInt(duplicateCode.v()),
                    copyDestinationName.v(),
                    entitySetting.sid,
                    entitySetting.settingType
            );
            this.commandProxy().insert(entity);
        }

        val optEntityConst = this.queryProxy().query(FIND_ANP_OUT_ITEMS, KfnmtRptWkAnpOutAtd.class)
                .setParameter("cid", cid)
                .setParameter("settingId", replicationSourceSettingId).getList();
        if (!optEntityConst.isEmpty()) {
            val listItem = optEntityConst.stream().map(e -> new KfnmtRptWkAnpOutAtd(
                    new KfnmtRptWkAnpOutatdPk(replicationDestinationSettingId, e.pk.printPosition),
                    e.getContractCd(),
                    e.getCompanyId(),
                    e.getAtdItemId()
            )).collect(Collectors.toList());
            this.commandProxy().insertAll(listItem);
        }
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid) {
        val displayCode = Integer.parseInt(code.v());
        val settingType = SettingClassificationCommon.STANDARD_SELECTION;
        val rs = this.queryProxy().query(FIND_ANP_OUT_ITEM_SETTING_BY_CODE, KfnmtRptWkAnpOut.class)
                .setParameter("cid", cid)
                .setParameter("displayCode", displayCode)
                .setParameter("settingType", settingType.value)
                .getList(JpaOutputSettingOfArbitraryRepo::toDomain);
        return rs != null && rs.size() != 0;
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid, String employeeId) {
        val displayCode = Integer.parseInt(code.v());
        val settingType = SettingClassificationCommon.FREE_SETTING;
        val rs = this.queryProxy().query(FIND_ANP_OUT_ITEM_BY_CODE_EMPLOYEE, KfnmtRptWkAnpOut.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", employeeId)
                .setParameter("displayCode", displayCode)
                .setParameter("settingType", settingType.value)
                .getList(JpaOutputSettingOfArbitraryRepo::toDomain);
        return rs != null && rs.size() != 0;
    }

    private static OutputSettingOfArbitrary toDomain(KfnmtRptWkAnpOut entity) {
        return new OutputSettingOfArbitrary(
                entity.layOutId,
                new OutputItemSettingCode(Integer.toString(entity.exportCd)),
                new OutputItemSettingName(entity.name),
                entity.sid!= null ? Optional.of( entity.sid) : Optional.empty(),
                EnumAdaptor.valueOf(entity.settingType, SettingClassificationCommon.class),
                Collections.emptyList()

        );
    }

    private static OutputSettingOfArbitrary toDomain(KfnmtRptWkAnpOut entity, List<AttendanceItemToPrint> outputItemList) {
        return new OutputSettingOfArbitrary(
                entity.layOutId,
                new OutputItemSettingCode(Integer.toString(entity.exportCd)),
                new OutputItemSettingName(entity.name),
                entity.sid!= null ? Optional.of( entity.sid) : Optional.empty(),
                EnumAdaptor.valueOf(entity.settingType, SettingClassificationCommon.class),
                outputItemList

        );
    }

    private static AttendanceItemToPrint toDomain(KfnmtRptWkAnpOutAtd entity) {

        return new AttendanceItemToPrint(
                entity.atdItemId,
                entity.pk.printPosition
        );
    }
}
