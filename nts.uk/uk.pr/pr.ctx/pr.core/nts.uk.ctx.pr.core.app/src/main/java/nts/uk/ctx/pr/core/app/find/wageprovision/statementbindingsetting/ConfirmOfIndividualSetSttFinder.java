package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHisExportAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHistoryExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.AffDepartHistory;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.AffDepartHistoryAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.Department;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.EmploymentHisExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.IEmploymentHistoryAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
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
    private SyJobTitleAdapter mJobTitleInfoAdapter;

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


    public void indiTiedStatAcquiProcess(int type,String empID, String hisId, GeneralDate baseDate,EmployeeInformationQueryDtoImport param) {
        String cid = AppContexts.user().companyId();
        Optional<StateUseUnitSetting> mStateUseUnitSetting = mStateUseUnitSettingRepository.getStateUseUnitSettingById(cid);
        if (mStateUseUnitSetting.get().getIndividualUse().value == SettingUseClassification.USE.value) {
            Optional<StateCorrelationHisIndividual> mStateCorrelationHisIndividual = mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById(empID, hisId);
            if (mStateCorrelationHisIndividual.isPresent()) {
                Optional<StateLinkSettingIndividual> mStateLinkSettingIndividual = mStateCorrelationHisIndividualRepository.getStateLinkSettingIndividualById(empID,mStateCorrelationHisIndividual.get().getHistory().get(0).identifier());
                if (mStateLinkSettingIndividual.isPresent() && mStateLinkSettingIndividual.get().getBonusCode().isPresent() && mStateLinkSettingIndividual.get().getSalaryCode().isPresent()) {
                    return;
                }
            }
            getAcquireMasterLinkedStatement(type, empID,baseDate, hisId,param);
        }
        Optional<StateCorrelationHisCompany> mSStateCorrelationHisCompany = mStateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyByDate(cid, baseDate);
        Optional<StateLinkSettingCompany> mStateLinkSettingCompany = mStateCorrelationHisCompanyRepository.getStateLinkSettingCompanyById(cid,mSStateCorrelationHisCompany.get().getHistory().get(0).identifier());
        Optional<StatementLayout> mOptionalStatementLayout = mStatementLayoutRepository.getStatementLayoutById(cid, mStateLinkSettingCompany.get().getSalaryCode().get().v());



    }

    /*マスタ紐付け明細書取得*/ /*return for I2_14 */
    private Optional<StateLinkSettingMaster> getAcquireMasterLinkedStatement(int type, String employeeId, GeneralDate baseDate, String hisId,EmployeeInformationQueryDtoImport param) {
        String cid = AppContexts.user().companyId();
        switch (type) {
            case DEPARMENT: {
                AffDepartHistory mAffDepartHistory = mAffDepartHistoryAdapter.getDepartmentByBaseDate(employeeId, baseDate).get();
                mStateCorrelationHisDeparmentRepository.getStateCorrelationHisDeparmentByDate(cid, baseDate);
                Optional<StateLinkSettingMaster> mStateLinkSettingMaster = mStateCorrelationHisDeparmentRepository.getStateLinkSettingMasterById(cid,hisId, mAffDepartHistory.getDepartmentCode());
                if (!mStateLinkSettingMaster.isPresent()) {
                    return Optional.empty();
                }
                Optional<Department> mDepartment = mDepartmentAdapter.getDepartmentByBaseDate(employeeId, baseDate);
                if (!mDepartment.isPresent()) {
                    return Optional.empty();
                }
                editDepartmentCode(mDepartment.get().getHierarchyCd());
                Optional<Department> mDepartmentUpper = mDepartmentAdapter.getDepartmentByDepartmentId(mDepartment.get().getDepartmentId());
                if (!mDepartmentUpper.isPresent()) {
                    return Optional.empty();
                }
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisDeparmentRepository.getStateLinkSettingMasterById(cid,hisId, mDepartmentUpper.get().getDepartmentId());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;

            }
            case EMPLOYEE: {
                Optional<EmploymentHisExport> mEmploymentHisExport = mIEmploymentHistoryAdapter.getEmploymentHistory(employeeId, hisId);
                Optional<StateCorrelationHisEmployee> mStateCorrelationHisEmployee = mStateCorrelationHisEmployeeRepository.getStateCorrelationHisEmployeeById(cid, hisId);
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisEmployeeRepository.getStateLinkSettingMasterById(cid,hisId, mEmploymentHisExport.get().getEmploymentCode());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;

            }
            case CLASSIFICATION: {
                Optional<ClassificationHistoryExport> mEmploymentHisExport = mClassificationHisExportAdapter.getClassificationHisByBaseDate(employeeId);
                Optional<StateCorrelationHisClassification> mStateCorrelationHisEmployee = mStateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationById(cid, hisId);
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisClassificationRepository.getStateLinkSettingMasterById(cid,hisId, mEmploymentHisExport.get().getClassificationCode());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;

            }
            case POSITION: {
                List<JobTitle> mJobTitleInfo = mJobTitleInfoAdapter.findAll(cid, baseDate);
                Optional<StateCorrelationHisPosition> mStateCorrelationHisPosition = mStateCorrelationHisPositionRepository.getStateCorrelationHisPositionById(cid, hisId);
                Optional<StateLinkSettingMaster> mStateLinkSettingMasterVer2 = mStateCorrelationHisPositionRepository.getStateLinkSettingMasterById(cid,hisId, mJobTitleInfo.get(0).getJobTitleCode());
                if(!mStateLinkSettingMasterVer2.isPresent()){
                    return Optional.empty();
                }
                return mStateLinkSettingMasterVer2;

            }
            case SALARY: {
                Optional<EmploySalaryClassHistory> mEmploySalaryClassHistory = mEmploySalaryClassHistoryRepository.getEmploySalaryClassHistoryById(employeeId, hisId);
                Optional<EmploySalaryCategory> mEmploySalaryCategory = mEmploySalaryCategoryRepository.getEmploySalaryClassHistoryById(mEmploySalaryClassHistory.get().getHistory().get(0).identifier());
                Optional<StateCorrelationHisSalary> mStateCorrelationHisSalary = mStateCorrelationHisSalaryRepository.getStateCorrelationHisSalaryByKey(cid, hisId);
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
