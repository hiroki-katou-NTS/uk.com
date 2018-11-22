package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryCategory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryCategoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryClassHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryClassHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
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


    /*雇用*/
    private static final int EMPLOYEE = 0;
    /*部門*/
    private static final int DEPARMENT = 1;
    /*分類*/
    private static final int CLASSIFICATION = 2;
    /*職位*/
    private static final int POSITION = 3;
    /*給与分類*/
    private static final int SALARY = 4;


    public void indiTiedStatAcquiProcess(int type,String empID, String hisId, GeneralDate baseDate) {
        String cid = AppContexts.user().companyId();
        /*return for I2_15*/
        Optional<StateUseUnitSetting> mStateUseUnitSetting = mStateUseUnitSettingRepository.getStateUseUnitSettingById(cid);
        if (mStateUseUnitSetting.get().getIndividualUse().value == SettingUseClassification.USE.value) {
            Optional<StateCorrelationHisIndividual> mStateCorrelationHisIndividual = mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById(empID, hisId);
            if (mStateCorrelationHisIndividual.isPresent()) {
                /*return for I2_9 and I2_11*/
                Optional<StateLinkSettingIndividual> mStateLinkSettingIndividual = mStateCorrelationHisIndividualRepository.getStateLinkSettingIndividualById(empID,mStateCorrelationHisIndividual.get().getHistory().get(0).identifier());
                if (mStateLinkSettingIndividual.isPresent() && mStateLinkSettingIndividual.get().getBonusCode().isPresent() && mStateLinkSettingIndividual.get().getSalaryCode().isPresent()) {
                    return;
                }
            }
            getAcquireMasterLinkedStatement(type, empID,baseDate, hisId);
        }
        Optional<StateCorrelationHisCompany> mSStateCorrelationHisCompany = mStateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyByDate(cid, baseDate);
        /*return for I2_9 and I2_11*/
        Optional<StateLinkSettingCompany> mStateLinkSettingCompany = mStateCorrelationHisCompanyRepository.getStateLinkSettingCompanyById(cid,mSStateCorrelationHisCompany.get().getHistory().get(0).identifier());
        /*return for I2_10*/
        Optional<StatementLayout> mOptionalStatementLayout = mStatementLayoutRepository.getStatementLayoutById(cid, mStateLinkSettingCompany.get().getSalaryCode().get().v());



    }

    /*マスタ紐付け明細書取得*/ /*return for I2_14 */
    private Optional<StateLinkSettingMaster> getAcquireMasterLinkedStatement(int type, String employeeId, GeneralDate baseDate, String hisId) {
        String cid = AppContexts.user().companyId();
        switch (type) {
            case DEPARMENT: {
                /*Imported(給与)「所属部門履歴」を取得する*/
                AffDepartHistory mAffDepartHistory = mAffDepartHistoryAdapter.getDepartmentByBaseDate(employeeId, baseDate).get();
                /*ドメインモデル「明細書紐付け履歴（部門）」を取得する*/
                mStateCorrelationHisDeparmentRepository.getStateCorrelationHisDeparmentByDate(cid, baseDate);
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/ /*return for I2_9 and I2_11 */
                Optional<StateLinkSettingMaster> mStateLinkSettingMaster = mStateCorrelationHisDeparmentRepository.getStateLinkSettingMasterById(cid,hisId, mAffDepartHistory.getDepartmentCode());
                if (!mStateLinkSettingMaster.isPresent()) {
                    return Optional.empty();
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
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisEmployeeRepository.getStateLinkSettingMasterById(cid,hisId, mEmploymentHisExport.get().getEmploymentCode());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;

            }
            case CLASSIFICATION: {
                /*Imported(給与)「分類履歴」を取得する */
                Optional<ClassificationHistoryExport> mEmploymentHisExport = mClassificationHisExportAdapter.getClassificationHisByBaseDate(employeeId);
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
                Optional<JobTitleInfo> mJobTitleInfo = mJobTitleInfoAdapter.getJobTitleInfoByBaseDate(cid, baseDate);
                /*ドメインモデル「明細書紐付け履歴（職位）」を取得する*/
                Optional<StateCorrelationHisPosition> mStateCorrelationHisPosition = mStateCorrelationHisPositionRepository.getStateCorrelationHisPositionById(cid, hisId);
                /*ドメインモデル「明細書紐付け設定（マスタ）」を取得する*/
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisPositionRepository.getStateLinkSettingMasterById(cid,hisId, mJobTitleInfo.get().getJobTitleCode());
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
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisSalaryRepository.getStateLinkSettingMasterById(cid,hisId, mEmploySalaryCategory.get().getSalaryClassCode());
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
