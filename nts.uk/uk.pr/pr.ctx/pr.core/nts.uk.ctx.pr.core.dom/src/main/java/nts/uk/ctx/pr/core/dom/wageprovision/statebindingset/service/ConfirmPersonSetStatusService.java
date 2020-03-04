package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.adapter.query.classification.ClassificationImport;
import nts.uk.ctx.pr.core.dom.adapter.query.department.DepartmentImport;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInfoAverAdapter;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInformationImport;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.pr.core.dom.adapter.query.employement.EmploymentImport;
import nts.uk.ctx.pr.core.dom.adapter.query.position.PositionImport;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class ConfirmPersonSetStatusService {
    @Inject
    private StateUseUnitSetRepository stateUseUnitSetRepo;
    @Inject
    private StateCorreHisIndiviRepository stateCorreHistIndiviRepo;
    @Inject
    private EmployeeInfoAverAdapter employeeInfoAverAdapter;
    @Inject
    private StateCorreHisDeparRepository stateCorreHisDeparRepo;
    @Inject
    private StateCorreHisEmRepository stateCorreHisEmRepo;
    @Inject
    private StateCorreHisClsRepository stateCorreHisClsRepo;
    @Inject
    private StateCorreHisPoRepository stateCorreHisPoRepo;
    @Inject
    private StateCorreHisSalaRepository stateCorreHisSalaRepo;
    @Inject
    private StateCorreHisComRepository stateCorreHisComRepo;
    @Inject
    private StatementLayoutRepository statementLayoutRepo;

    /**
     * 個人紐付け明細書取得処理
     */
    public List<ConfirmPersonSetStatus> getStatementLinkPerson(List<String> empIds, GeneralDate baseDate) {
        String cid = AppContexts.user().companyId();
        YearMonth yearMonth = baseDate.yearMonth();
        List<ConfirmPersonSetStatus> result = new ArrayList();

        // ドメインモデル「明細書利用単位設定」を取得する
        Optional<StateUseUnitSet> stateUseUnitSetOtp = stateUseUnitSetRepo.getStateUseUnitSettingById(cid);
        if (!stateUseUnitSetOtp.isPresent()) {
            return Collections.emptyList();
        }
        StateUseUnitSet stateUseUnitSet = stateUseUnitSetOtp.get();

        StateCorreHisAndLinkSetIndivi stateHistAndLinkSet = stateCorreHistIndiviRepo.getStateCorreHisAndLinkSetIndivi(empIds, yearMonth);
        // ドメインモデル「明細書紐付け履歴（個人）」を取得する
        Map<String, List<YearMonthHistoryItem>> stateCorreHisIndiviMap = stateHistAndLinkSet.getHistorys()
                .stream().collect(Collectors.toMap(StateCorreHisIndivi::getEmpID, StateCorreHisIndivi::items));
        // ドメインモデル「明細書紐付け設定（個人）」を取得する
        Map<String, StateLinkSetIndivi> stateLinkSetIndiviMap = stateHistAndLinkSet.getSettings()
                .stream().collect(Collectors.toMap(StateLinkSetIndivi::getHistoryID, x -> x));

        // Imported(給与)「」を取得する
        Map<String, EmployeeInformationImport> empInfoMap = employeeInfoAverAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(empIds, baseDate, false, true, true,
                        true, true, false))
                .stream().collect(Collectors.toMap(EmployeeInformationImport::getEmployeeId, x -> x));

        // ドメインモデル「明細書紐付け履歴（部門）」を取得する
        // ドメインモデル「明細書紐付け設定（マスタ）」を取得する
        Map<String, StateLinkSetMaster> stateLinkSetMasterDepMap = stateCorreHisDeparRepo.getStateLinkSetMaster(cid, yearMonth)
                .stream().collect(Collectors.toMap(x -> x.getMasterCode().v(), x -> x));

        // ドメインモデル「明細書紐付け履歴（雇用）」を取得する
        // ドメインモデル「明細書紐付け設定（マスタ）」を取得する
        Map<String, StateLinkSetMaster> stateLinkSetMasterEmpMap = stateCorreHisEmRepo.getStateLinkSetMaster(cid, yearMonth)
                .stream().filter(x->x.getBonusCode().isPresent() || x.getSalaryCode().isPresent())
                .collect(Collectors.toMap(x -> x.getMasterCode().v(), x -> x));


        // ドメインモデル「明細書紐付け履歴（分類）」を取得する
        // ドメインモデル「明細書紐付け設定（マスタ）」を取得する
        Map<String, StateLinkSetMaster> stateLinkSetMasterClsMap = stateCorreHisClsRepo.getStateLinkSetMaster(cid, yearMonth)
                .stream().collect(Collectors.toMap(x -> x.getMasterCode().v(), x -> x));

        // ドメインモデル「明細書紐付け履歴（職位）」を取得する
        // ドメインモデル「明細書紐付け設定（マスタ）」を取得する
        Map<String, StateLinkSetMaster> stateLinkSetMasterPosMap = stateCorreHisPoRepo.getStateLinkSetMaster(cid, yearMonth)
                .stream().collect(Collectors.toMap(x -> x.getMasterCode().v(), x -> x));

        // ドメインモデル「明細書紐付け履歴（給与分類）」を取得する
        // ドメインモデル「明細書紐付け設定（マスタ）」を取得する
        Map<String, StateLinkSetMaster> stateLinkSetMasterSalMap = stateCorreHisSalaRepo.getStateLinkSetMaster(cid, yearMonth)
                .stream().collect(Collectors.toMap(x -> x.getMasterCode().v(), x -> x));

        // ドメインモデル「明細書紐付け履歴（会社）」を取得する
        Optional<StateCorreHisCom> stateCorreHisComOpt = stateCorreHisComRepo.getStateCorrelationHisCompanyByDate(cid, yearMonth);
        // ドメインモデル「明細書紐付け設定（会社）」を取得する
        Optional<StateLinkSetCom> stateLinkSetCom = Optional.empty();
        if (stateCorreHisComOpt.isPresent()) {
            List<YearMonthHistoryItem> histItemCom = stateCorreHisComOpt.get().getHistory();
            if (!histItemCom.isEmpty()) {
                stateLinkSetCom = stateCorreHisComRepo.getStateLinkSettingCompanyById(cid, histItemCom.get(0).identifier());
            }
        }

        // ドメインモデル「明細書レイアウト」を全て取得する
        Map<String, StatementLayout> statementLayoutMap = statementLayoutRepo.getAllStatementLayoutByCid(cid)
                .stream().collect(Collectors.toMap(x -> x.getStatementCode().v(), x -> x));

        for (String sid : empIds) {
            boolean existSalaryBonus;
            ConfirmPersonSetStatus personSet = new ConfirmPersonSetStatus();
            personSet.setSid(sid);

            // 取得したドメインモデル「明細書利用単位設定」をチェックする
            if (SettingUseCls.USE.equals(stateUseUnitSet.getIndividualUse())) {
                // ドメインモデル「明細書紐付け履歴（個人）」を取得する
                if (stateCorreHisIndiviMap.containsKey(sid)) {
                    String histId = stateCorreHisIndiviMap.get(sid).get(0).identifier();
                    // ドメインモデル「明細書紐付け設定（個人）」を取得する
                    StateLinkSetIndivi stateLinkSetIndivi = stateLinkSetIndiviMap.get(histId);
                    existSalaryBonus = this.setSalaryBonus(personSet, stateLinkSetIndivi.getSalaryCode(),
                            stateLinkSetIndivi.getBonusCode(), statementLayoutMap);
                    if (existSalaryBonus) {
                        personSet.setSettingCtg(SettingCls.PERSON.value);
                        result.add(personSet);
                        continue;
                    }
                }
            }
            if (SettingUseCls.USE.equals(stateUseUnitSet.getMasterUse())) {
                // マスタ紐付け明細書取得
                existSalaryBonus = this.setLinkMaster(personSet, stateUseUnitSet.getUsageMaster(), empInfoMap.get(sid),
                        stateLinkSetMasterDepMap, stateLinkSetMasterEmpMap, stateLinkSetMasterClsMap,
                        stateLinkSetMasterPosMap, stateLinkSetMasterSalMap, statementLayoutMap);
                if (existSalaryBonus) {
                    result.add(personSet);
                    continue;
                }
            }

            if (stateLinkSetCom.isPresent()) {
                StateLinkSetCom com = stateLinkSetCom.get();
                this.setSalaryBonus(personSet, com.getSalaryCode(), com.getBonusCode(), statementLayoutMap);
                personSet.setSettingCtg(SettingCls.COMPANY.value);
            }
            result.add(personSet);
        }
        return result;
    }

    private boolean setSalaryBonus(ConfirmPersonSetStatus personSet, Optional<StatementCode> salaryCode,
                                   Optional<StatementCode> bonusCode, Map<String, StatementLayout> statementLayoutMap) {
        // 取得したドメインモデル「明細書紐付け設定（個人）」をチェックする
        if (!salaryCode.isPresent() && !bonusCode.isPresent()) return false;
        if (salaryCode.isPresent()) {
            personSet.setSalaryCode(salaryCode.get().v());
            personSet.setSalaryName(this.getSttLayoutName(salaryCode.get().v(), statementLayoutMap));
        }
        if (bonusCode.isPresent()) {
            personSet.setBonusCode(bonusCode.get().v());
            personSet.setBonusName(this.getSttLayoutName(bonusCode.get().v(), statementLayoutMap));
        }
        return true;
    }

    private String getSttLayoutName(String code, Map<String, StatementLayout> statementLayoutMap) {
        if (statementLayoutMap.containsKey(code)) {
            return statementLayoutMap.get(code).getStatementName().v();
        }
        return "";
    }

    /**
     * マスタ紐付け明細書取得
     */
    private boolean setLinkMaster(ConfirmPersonSetStatus personSet, Optional<UsageMaster> type, EmployeeInformationImport empInfo,
                               Map<String, StateLinkSetMaster> masterDepMap, Map<String, StateLinkSetMaster> masterEmpMap,
                               Map<String, StateLinkSetMaster> masterClsMap, Map<String, StateLinkSetMaster> masterPosMap,
                               Map<String, StateLinkSetMaster> masterSalMap, Map<String, StatementLayout> statementLayoutMap) {
        if (!type.isPresent()) return false;
        String masterCode = null;
        String masterName = null;
        StateLinkSetMaster master = null;
        String tempMasterCode = null;
        switch (type.get()) {
            case DEPARMENT:
                // TODO 部門紐付け明細書取得
                DepartmentImport depImport = empInfo.getDepartment();
                if (depImport == null) break;
                tempMasterCode = depImport.getDepartmentCode();
                if (masterDepMap.containsKey(tempMasterCode)) {
                    master = masterDepMap.get(tempMasterCode);
                    masterCode = depImport.getDepartmentCode();
                    masterName = depImport.getDepartmentName();
                }
                personSet.setSettingCtg(SettingCls.DEPARMENT.value);
                break;
            case EMPLOYEE:
                EmploymentImport empImport = empInfo.getEmployment();
                if (empImport == null) break;
                tempMasterCode = empImport.getEmploymentCode();
                if (masterEmpMap.containsKey(tempMasterCode)) {
                    master = masterEmpMap.get(tempMasterCode);
                    masterCode = empImport.getEmploymentCode();
                    masterName = empImport.getEmploymentName();
                }
                personSet.setSettingCtg(SettingCls.EMPLOYEE.value);
                break;
            case CLASSIFICATION:
                ClassificationImport clsImport = empInfo.getClassification();
                if (clsImport == null) break;
                tempMasterCode = clsImport.getClassificationCode();
                if (masterClsMap.containsKey(tempMasterCode)) {
                    master = masterClsMap.get(tempMasterCode);
                    masterCode = clsImport.getClassificationCode();
                    masterName = clsImport.getClassificationName();
                }
                personSet.setSettingCtg(SettingCls.CLASSIFICATION.value);
                break;
            case POSITION:
                PositionImport posImport = empInfo.getPosition();
                if (posImport == null) break;
                tempMasterCode = posImport.getPositionCode();
                if (masterPosMap.containsKey(tempMasterCode)) {
                    master = masterPosMap.get(tempMasterCode);
                    masterCode = posImport.getPositionCode();
                    masterName = posImport.getPositionName();
                }
                personSet.setSettingCtg(SettingCls.POSITION.value);
                break;
            case SALARY:
                // TODO
                /*if (masterSalMap.containsKey(masterCode)) {
                    master = masterEmpMap.get(masterCode);
                }*/
                personSet.setSettingCtg(SettingCls.SALARY.value);
                break;
        }
        personSet.setMasterCode(masterCode);
        personSet.setMasterName(masterName);

        return master != null && this.setSalaryBonus(personSet, master.getSalaryCode(), master.getBonusCode(), statementLayoutMap);
    }
}
