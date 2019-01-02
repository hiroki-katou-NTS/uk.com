package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHisExportAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHistoryExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.AffDepartHistoryAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.WkpConfigAtTimeExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.EmploymentHisExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.IEmploymentHistoryAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInfoAverAdapter;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInformationImport;
import nts.uk.ctx.pr.core.dom.adapter.query.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryCategory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryCategoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ConOfIndiviSetSttFinder {
    @Inject
    private StateUseUnitSetRepository mStateUseUnitSetRepository;

    @Inject
    private StateCorreHisIndiviRepository mStateCorreHisIndiviRepository;

    @Inject
    private AffDepartHistoryAdapter mAffDepartHistoryAdapter;

    @Inject
    private StateCorreHisDeparRepository mStateCorreHisDeparRepository;


    @Inject
    private DepartmentAdapter mDepartmentAdapter;

    @Inject
    private StateCorreHisComRepository mStateCorreHisComRepository;

    @Inject
    private StatementLayoutRepository mStatementLayoutRepository;

    @Inject
    private IEmploymentHistoryAdapter mIEmploymentHistoryAdapter;

    @Inject
    private StateCorreHisEmRepository mStateCorreHisEmRepository;

    @Inject
    private ClassificationHisExportAdapter mClassificationHisExportAdapter;

    @Inject
    private StateCorreHisClsRepository mStateCorreHisClsRepository;

    @Inject
    private StateCorreHisPoRepository mStateCorreHisPoRepository;

    @Inject
    private nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.JobTitleInfoAdapter mJobTitleInfoAdapter;

    @Inject
    private EmploySalaryClassHistoryRepository mEmploySalaryClassHistoryRepository;

    @Inject
    private StateCorreHisSalaRepository mStateCorreHisSalaRepository;

    @Inject
    private EmploySalaryCategoryRepository mEmploySalaryCategoryRepository;

    @Inject
    private EmployeeInfoAverAdapter employeeInformationAdapter;

    @Inject
    private SalaryClassificationInformationRepository mSalaryClassificationInformationRepository;

    private List<WkpConfigAtTimeExport> mDepartment;

    private static final int FIRST_HISTORY = 0;


    public List<ConOfIndiviSetSttDto> indiTiedStatAcquiProcess(int type, List<InformationEmployeeDto> employees, String hisId, GeneralDate baseDate) {
        String cid = AppContexts.user().companyId();
        List<ConOfIndiviSetSttDto> resulf = new ArrayList<ConOfIndiviSetSttDto>();
        List<String> employeeIds = employees.stream().map(x -> new String(x.getEmployeeId())).collect((Collectors.toList()));
        // <<Public>> 社員の情報を取得する
        List<EmployeeInformationImport> listEmployeeInformationImport = employeeInformationAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds, null, true, false, true,
                        true, false, false));

        Optional<StateUseUnitSet> mStateUseUnitSetting = mStateUseUnitSetRepository.getStateUseUnitSettingById(cid);
        List<SalaryClassificationInformation> salaryClassificationInformation = mSalaryClassificationInformationRepository.getAllSalaryClassificationInformation(cid);
        mDepartment = mDepartmentAdapter.getDepartmentByBaseDate(cid, baseDate, employeeIds);

        employeeIds.stream().forEach(itemEmployeeId -> {
                    Optional<EmployeeInformationImport> employeeInformationImport = Optional.empty();
                    Optional<StateLinkSetMaster> stateLinkSettingMaster = Optional.empty();
                    Optional<StateLinkSetIndivi> stateCorrelationHisIndividual = Optional.empty();
                    if (mStateUseUnitSetting.get().getIndividualUse().value == SettingUseCls.USE.value) {
                        Optional<StateCorreHisIndivi> mStateCorrelationHisIndividual = mStateCorreHisIndiviRepository.getStateCorrelationHisIndividualByDate(itemEmployeeId, baseDate);
                        if (mStateCorrelationHisIndividual.isPresent()) {
                            stateCorrelationHisIndividual = mStateCorreHisIndiviRepository.getStateLinkSettingIndividualById(cid, mStateCorrelationHisIndividual.get().getHistory().get(FIRST_HISTORY).identifier());
                            if (stateCorrelationHisIndividual.isPresent() && stateCorrelationHisIndividual.get().getBonusCode().isPresent() && stateCorrelationHisIndividual.get().getSalaryCode().isPresent()) {
                                Optional<StateCorreHisCom> mSStateCorrelationHisCompany = mStateCorreHisComRepository.getStateCorrelationHisCompanyByDate(cid, baseDate);
                                /*return for I2_9 and I2_11*/
                                Optional<StateLinkSetCom> mStateLinkSettingCompany = mStateCorreHisComRepository.getStateLinkSettingCompanyById(cid, mSStateCorrelationHisCompany.get().getHistory().get(0).identifier());
                                /*return for I2_10*/
                                Optional<StatementLayout> mOptionalStatementLayout = mStatementLayoutRepository.getStatementLayoutById(cid, mStateLinkSettingCompany.get().getSalaryCode().get().v());
                                return;
                            }

                        }
                        if (mStateUseUnitSetting.get().getMasterUse().value == SettingUseCls.USE.value) {
                            employeeInformationImport = listEmployeeInformationImport.stream().filter(x -> x.getEmployeeId().equals(itemEmployeeId)).findFirst();
                            stateLinkSettingMaster = getAcquireMasterLinkedStatement(EnumAdaptor.valueOf(type, nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.UsageMaster.class), itemEmployeeId, hisId, baseDate, employeeInformationImport.get(), mDepartment.stream().filter(x -> x.getWorkplaceId().equals(employees.stream().filter(emId -> emId.getEmployeeId().equals(itemEmployeeId)).findFirst().get().getWorkplaceId())).findFirst().toString());

                        }
                    }
                    Optional<StateCorreHisCom> mSStateCorrelationHisCompany = mStateCorreHisComRepository.getStateCorrelationHisCompanyByDate(cid, baseDate);
                    if (!mSStateCorrelationHisCompany.isPresent()) {
                        return;
                    }
                    /*return for I2_9 and I2_11*/
                    Optional<StateLinkSetCom> mStateLinkSettingCompany = mStateCorreHisComRepository.getStateLinkSettingCompanyById(cid, mSStateCorrelationHisCompany.get().getHistory().get(0).identifier());
                    if (!mStateLinkSettingCompany.isPresent()) {
                        return;
                    }
                    /*return for I2_10*/
                    Optional<StatementLayout> mOptionalStatementLayout = mStatementLayoutRepository.getStatementLayoutById(cid, mStateLinkSettingCompany.get().getSalaryCode().get().v());
                    Optional<StateLinkSetMaster> finalStateLinkSettingMaster = stateLinkSettingMaster;
                    resulf.add(new ConOfIndiviSetSttDto(mStateLinkSettingCompany.get().getSalaryCode().get().v(),
                            stateLinkSettingMaster.get().getSalaryCode().get().v(),
                            stateCorrelationHisIndividual.get().getSalaryCode().get().v(),
                            mStateLinkSettingCompany.get().getBonusCode().get().v(),
                            stateLinkSettingMaster.get().getBonusCode().get().v(),
                            stateCorrelationHisIndividual.get().getBonusCode().get().v(),
                            mOptionalStatementLayout.get().getStatementName().v(),
                            stateLinkSettingMaster.get().getMasterCode().v(),
                            employeeInformationImport.get().getEmployment().getEmploymentName(),
                            employeeInformationImport.get().getDepartment().getDepartmentName(),
                            employeeInformationImport.get().getClassification().getClassificationName(),
                            employeeInformationImport.get().getPosition().getPositionName(),
                            salaryClassificationInformation.stream().filter(i -> i.getSalaryClassificationCode().v().equals(finalStateLinkSettingMaster.get().getMasterCode().v())).findFirst().get().getSalaryClassificationName().v()
                    ));

                }//end foreach
        );
        return resulf;
    }

    /*マスタ紐付け明細書取得*/ /*return for I2_14 */
    private Optional<StateLinkSetMaster> getAcquireMasterLinkedStatement(nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.UsageMaster type, String employeeId, String hisId, GeneralDate baseDate, EmployeeInformationImport dataRequestList, String hierarchyCd) {
        String cid = AppContexts.user().companyId();
        switch (type) {
            case DEPARMENT: {
                /*Imported(給与)「所属部門履歴」を取得する*/

                /*ドメインモデル「明細書紐付け履歴（部門）」を取得する*/
                Optional<StateCorreHisDepar> mStateCorrelationHisDeparment = mStateCorreHisDeparRepository.getStateCorrelationHisDeparmentByDate(cid, baseDate);
                String historyId = mStateCorrelationHisDeparment.get().getHistory().get(FIRST_HISTORY).identifier();
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/ /*return for I2_9 and I2_11 */
                Optional<StateLinkSetMaster> mStateLinkSettingMaster = mStateCorreHisDeparRepository.getStateLinkSettingMasterById(cid, historyId, dataRequestList.getDepartment().getDepartmentCode());
                if (!mStateLinkSettingMaster.isPresent()) {
                    return mStateLinkSettingMaster;
                }
                /*Imported(給与)「部門」を取得する */
                if (!hierarchyCd.isEmpty()) {
                    return Optional.empty();
                }
                /*階層コードを編集する*/
                editDepartmentCode(hierarchyCd);

                /*Imported(給与)「部門」を取得する */
                if (!mDepartment.stream().filter(x -> x.getHierarchyCd().equals(hierarchyCd)).findFirst().isPresent()) {
                    return Optional.empty();
                }
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSetMaster> mStateLinkSettingMasterVer2 = mStateCorreHisDeparRepository.getStateLinkSettingMasterById(cid, hisId,/* comment vì đợi request list mDepartmentUpper.get().getDepartmentId()*/null);
                if (!mStateLinkSettingMasterVer2.isPresent()) {
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }
            case EMPLOYEE: {
                /*Imported(給与)「雇用履歴」を取得する */
                Optional<EmploymentHisExport> mEmploymentHisExport = mIEmploymentHistoryAdapter.getEmploymentHistory(employeeId, hisId);
                if (!mEmploymentHisExport.isPresent()) {
                    return Optional.empty();
                }
                /*ドメインモデル「明細書紐付け履歴（雇用）」を取得する*/
                Optional<StateCorreHisEm> mStateCorrelationHisEmployee = mStateCorreHisEmRepository.getStateCorrelationHisEmployeeById(cid, hisId);
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSetMaster> mStateLinkSettingMasterVer2 = mStateCorreHisEmRepository.getStateLinkSettingMasterById(cid, hisId, mEmploymentHisExport.get().lstEmpCodeandPeriod.stream().filter(x -> x.getHistoryID().equals(hisId)).findFirst().get().getEmploymentCode());
                if (!mStateLinkSettingMasterVer2.isPresent()) {
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }
            case CLASSIFICATION: {
                /*Imported(給与)「分類履歴」を取得する */
                Optional<ClassificationHistoryExport> mEmploymentHisExport = mClassificationHisExportAdapter.getClassificationHisByBaseDate(cid, employeeId, baseDate);
                /*ドメインモデル「明細書紐付け履歴（分類）」を取得する*/
                Optional<StateCorreHisCls> mStateCorrelationHisEmployee = mStateCorreHisClsRepository.getStateCorrelationHisClassificationById(cid, hisId);
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSetMaster> mStateLinkSettingMasterVer2 = mStateCorreHisClsRepository.getStateLinkSettingMasterById(cid, hisId, mEmploymentHisExport.get().getClassificationCode());
                if (!mStateLinkSettingMasterVer2.isPresent()) {
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }
            case POSITION: {
                /*Imported(給与)「職位履歴」を取得する */
                Optional<EmployeeJobHistExport> employeeJobHistExport = mJobTitleInfoAdapter.findSJobHistBySId(employeeId, baseDate);

                List<JobTitle> mJobTitleInfo = mJobTitleInfoAdapter.getJobTitleInfoByBaseDate(cid, baseDate);

                /*ドメインモデル「明細書紐付け履歴（職位）」を取得する*/
                Optional<StateCorreHisPo> mStateCorrelationHisPosition = mStateCorreHisPoRepository.getStateCorrelationHisPositionById(cid, hisId);

                Optional<StateLinkSetMaster> mStateLinkSettingMasterVer2 = mStateCorreHisPoRepository.getStateLinkSettingMasterById(cid, hisId, mJobTitleInfo.get(0).getJobTitleCode());
                if (!mStateLinkSettingMasterVer2.isPresent()) {
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }
            case SALARY: {
                /*Imported(給与)「給与分類履歴」を取得する */
                Optional<EmploySalaryClassHistory> mEmploySalaryClassHistory = mEmploySalaryClassHistoryRepository.getEmploySalaryClassHistoryById(employeeId, hisId);
                Optional<EmploySalaryCategory> mEmploySalaryCategory = mEmploySalaryCategoryRepository.getEmploySalaryClassHistoryById(mEmploySalaryClassHistory.get().getHistory().get(0).identifier());
                /*ドメインモデル「明細書紐付け履歴（給与分類）」を取得する*/
                Optional<StateCorreHisSala> mStateCorrelationHisSalary = mStateCorreHisSalaRepository.getStateCorrelationHisSalaryByKey(cid, hisId);
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSetMaster> mStateLinkSettingMasterVer2 = mStateCorreHisSalaRepository.getStateLinkSettingMasterById(cid, hisId, mEmploySalaryCategory.get().getSalaryClassCode());
                if (!mStateLinkSettingMasterVer2.isPresent()) {
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }

        }
        return Optional.empty();
    }

    private String editDepartmentCode(String hierarchyCd) {
        int checkIndex = hierarchyCd.length();
        StringBuilder mStringBuilder = new StringBuilder(hierarchyCd);
        for (int i = hierarchyCd.length() - 3; i >= 0; i = i - 3) {
            if (!mStringBuilder.substring(i, checkIndex).equals("000")) {
                mStringBuilder.replace(i, checkIndex, "000");
                return mStringBuilder.toString();
            }
            checkIndex = i;
        }
        return mStringBuilder.toString();
    }


}
