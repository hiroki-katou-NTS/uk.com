package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHisExportAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHistoryExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.AffDepartHistoryAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.Department;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInformationAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInformationImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.EmploymentHisExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.IEmploymentHistoryAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryCategory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryCategoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryClassHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryClassHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class ConfirmOfIndividualSetSttFinder {
    @Inject
    private StateUseUnitSettingRepository mStateUseUnitSettingRepository;

    @Inject
    private StateCorrelationHisIndividualRepository mStateCorrelationHisIndividualRepository;

    @Inject
    private AffDepartHistoryAdapter mAffDepartHistoryAdapter;

    @Inject
    private StateCorrelationHisDeparmentRepository mStateCorrelationHisDeparmentRepository;


    @Inject
    private DepartmentAdapter mDepartmentAdapter;

    @Inject
    private StateCorrelationHisCompanyRepository mStateCorrelationHisCompanyRepository;

    @Inject
    private StatementLayoutRepository mStatementLayoutRepository;

    @Inject
    private IEmploymentHistoryAdapter mIEmploymentHistoryAdapter;

    @Inject
    private StateCorrelationHisEmployeeRepository mStateCorrelationHisEmployeeRepository;

    @Inject
    private ClassificationHisExportAdapter mClassificationHisExportAdapter;

    @Inject
    private StateCorrelationHisClassificationRepository mStateCorrelationHisClassificationRepository;

    @Inject
    private StateCorrelationHisPositionRepository mStateCorrelationHisPositionRepository;

    @Inject
    private JobTitleInfoAdapter mJobTitleInfoAdapter;

    @Inject
    private EmploySalaryClassHistoryRepository mEmploySalaryClassHistoryRepository;

    @Inject
    private StateCorrelationHisSalaryRepository mStateCorrelationHisSalaryRepository;

    @Inject
    private EmploySalaryCategoryRepository mEmploySalaryCategoryRepository;

    @Inject
    private EmployeeInformationAdapter employeeInformationAdapter;

    @Inject
    private SalaryClassificationInformationRepository mSalaryClassificationInformationRepository;

    private static final int FIRST_HISTORY = 0;


    public List<ConfirmOfIndividualSetSttDto> indiTiedStatAcquiProcess(int type, List<String> employeeIds, String hisId, GeneralDate baseDate ) {
        String cid = AppContexts.user().companyId();
        List<ConfirmOfIndividualSetSttDto> resulf = new ArrayList<ConfirmOfIndividualSetSttDto>();
        // <<Public>> 社員の情報を取得する
        List<EmployeeInformationImport> listEmployeeInformationImport = employeeInformationAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds, null, true, false, true,
                        true, false, false));

        Optional<StateUseUnitSetting> mStateUseUnitSetting = mStateUseUnitSettingRepository.getStateUseUnitSettingById(cid);
        List<SalaryClassificationInformation> salaryClassificationInformation = mSalaryClassificationInformationRepository.getAllSalaryClassificationInformation(cid);

        employeeIds.stream().forEach(itemEmployeeId -> {
                    Optional<EmployeeInformationImport> employeeInformationImport = Optional.empty();
                    Optional<StateLinkSettingMaster> stateLinkSettingMaster = Optional.empty();
                    Optional<StateLinkSettingIndividual> stateCorrelationHisIndividual = Optional.empty();
                    if (mStateUseUnitSetting.get().getIndividualUse().value == SettingUseClassification.USE.value) {
                        Optional<StateCorrelationHisIndividual> mStateCorrelationHisIndividual = mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualByDate(itemEmployeeId, baseDate);
                        if (!mStateCorrelationHisIndividual.isPresent()) {
                            stateCorrelationHisIndividual = mStateCorrelationHisIndividualRepository.getStateLinkSettingIndividualById(cid, mStateCorrelationHisIndividual.get().getHistory().get(FIRST_HISTORY).identifier());
                            if (stateCorrelationHisIndividual.isPresent() && stateCorrelationHisIndividual.get().getBonusCode().isPresent() && stateCorrelationHisIndividual.get().getSalaryCode().isPresent()) {
                                Optional<StateCorrelationHisCompany> mSStateCorrelationHisCompany = mStateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyByDate(cid, baseDate);
                                /*return for I2_9 and I2_11*/
                                Optional<StateLinkSettingCompany> mStateLinkSettingCompany = mStateCorrelationHisCompanyRepository.getStateLinkSettingCompanyById(cid,mSStateCorrelationHisCompany.get().getHistory().get(0).identifier());
                                /*return for I2_10*/
                                Optional<StatementLayout> mOptionalStatementLayout = mStatementLayoutRepository.getStatementLayoutById(cid, mStateLinkSettingCompany.get().getSalaryCode().get().v());
                                return;
                            }

                        }
                        employeeInformationImport = listEmployeeInformationImport.stream().filter(x -> x.getEmployeeId().equals(itemEmployeeId)).findFirst();
                        stateLinkSettingMaster = getAcquireMasterLinkedStatement(EnumAdaptor.valueOf(type, UsageMaster.class), itemEmployeeId, hisId, baseDate, employeeInformationImport.get());
                    }
                    Optional<StateCorrelationHisCompany> mSStateCorrelationHisCompany = mStateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyByDate(cid, baseDate);
                    /*return for I2_9 and I2_11*/
                    Optional<StateLinkSettingCompany> mStateLinkSettingCompany = mStateCorrelationHisCompanyRepository.getStateLinkSettingCompanyById(cid,mSStateCorrelationHisCompany.get().getHistory().get(0).identifier());
                    /*return for I2_10*/
                    Optional<StatementLayout> mOptionalStatementLayout = mStatementLayoutRepository.getStatementLayoutById(cid, mStateLinkSettingCompany.get().getSalaryCode().get().v());
                    Optional<StateLinkSettingMaster> finalStateLinkSettingMaster = stateLinkSettingMaster;
                    resulf.add(new ConfirmOfIndividualSetSttDto(mStateLinkSettingCompany.get().getSalaryCode().get().v(),
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
    private Optional<StateLinkSettingMaster> getAcquireMasterLinkedStatement(UsageMaster type, String employeeId, String hisId, GeneralDate baseDate, EmployeeInformationImport dataRequestList) {
        String cid = AppContexts.user().companyId();
        switch (type) {
            case DEPARMENT: {
                /*Imported(給与)「所属部門履歴」を取得する*/

                /*ドメインモデル「明細書紐付け履歴（部門）」を取得する*/
                Optional<StateCorrelationHisDeparment> mStateCorrelationHisDeparment = mStateCorrelationHisDeparmentRepository.getStateCorrelationHisDeparmentByDate(cid, baseDate);
                String historyId = mStateCorrelationHisDeparment.get().getHistory().get(FIRST_HISTORY).identifier();
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/ /*return for I2_9 and I2_11 */
                Optional<StateLinkSettingMaster> mStateLinkSettingMaster = mStateCorrelationHisDeparmentRepository.getStateLinkSettingMasterById(cid,historyId, dataRequestList.getDepartment().getDepartmentCode());
                if (!mStateLinkSettingMaster.isPresent()) {
                    return mStateLinkSettingMaster;
                }
                /*Imported(給与)「部門」を取得する */
                Optional<Department> mDepartment = mDepartmentAdapter.getDepartmentByBaseDate(employeeId, baseDate);
                if (!mDepartment.isPresent()) {
                    return Optional.empty();
                }
                /*階層コードを編集する*/
                editDepartmentCode(mDepartment.get().getHierarchyCd());

                /*Imported(給与)「部門」を取得する */
                Optional<Department> mDepartmentUpper = mDepartmentAdapter.getDepartmentByDepartmentId(mDepartment.get().getDepartmentId());
                if (!mDepartmentUpper.isPresent()) {
                    return Optional.empty();
                }
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisDeparmentRepository.getStateLinkSettingMasterById(cid,hisId, mDepartmentUpper.get().getDepartmentId());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }
            case EMPLOYEE: {
                /*Imported(給与)「雇用履歴」を取得する */
                Optional<EmploymentHisExport> mEmploymentHisExport = mIEmploymentHistoryAdapter.getEmploymentHistory(employeeId, hisId);
                /*ドメインモデル「明細書紐付け履歴（雇用）」を取得する*/
                Optional<StateCorrelationHisEmployee> mStateCorrelationHisEmployee = mStateCorrelationHisEmployeeRepository.getStateCorrelationHisEmployeeById(cid, hisId);
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisEmployeeRepository.getStateLinkSettingMasterById(cid,hisId, mEmploymentHisExport.get().lstEmpCodeandPeriod.stream().filter(x -> x.getHistoryID().equals(hisId)).findFirst().get().getEmploymentCode());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }
            case CLASSIFICATION: {
                /*Imported(給与)「分類履歴」を取得する */
                Optional<ClassificationHistoryExport> mEmploymentHisExport = mClassificationHisExportAdapter.getClassificationHisByBaseDate(cid, employeeId, baseDate);
                /*ドメインモデル「明細書紐付け履歴（分類）」を取得する*/
                Optional<StateCorrelationHisClassification> mStateCorrelationHisEmployee = mStateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationById(cid, hisId);
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisClassificationRepository.getStateLinkSettingMasterById(cid,hisId, mEmploymentHisExport.get().getClassificationCode());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }
            case POSITION: {
                /*Imported(給与)「職位履歴」を取得する */
                Optional<EmployeeJobHistExport> employeeJobHistExport = mJobTitleInfoAdapter.findSJobHistBySId(employeeId, baseDate);

                List<JobTitle> mJobTitleInfo = mJobTitleInfoAdapter.getJobTitleInfoByBaseDate(cid, baseDate);

                /*ドメインモデル「明細書紐付け履歴（職位）」を取得する*/
                Optional<StateCorrelationHisPosition> mStateCorrelationHisPosition = mStateCorrelationHisPositionRepository.getStateCorrelationHisPositionById(cid, hisId);

                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisPositionRepository.getStateLinkSettingMasterById(cid,hisId, mJobTitleInfo.get(0).getJobTitleCode());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;
            }
            case SALARY: {
                /*Imported(給与)「給与分類履歴」を取得する */
                Optional<EmploySalaryClassHistory> mEmploySalaryClassHistory = mEmploySalaryClassHistoryRepository.getEmploySalaryClassHistoryById(employeeId, hisId);
                Optional<EmploySalaryCategory> mEmploySalaryCategory = mEmploySalaryCategoryRepository.getEmploySalaryClassHistoryById(mEmploySalaryClassHistory.get().getHistory().get(0).identifier());
                /*ドメインモデル「明細書紐付け履歴（給与分類）」を取得する*/
                 Optional<StateCorrelationHisSalary> mStateCorrelationHisSalary = mStateCorrelationHisSalaryRepository.getStateCorrelationHisSalaryByKey(cid, hisId);
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisSalaryRepository.getStateLinkSettingMasterById(cid, hisId, mEmploySalaryCategory.get().getSalaryClassCode());
                if(!mStateLinkSettingMasterVer2.isPresent()){
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
