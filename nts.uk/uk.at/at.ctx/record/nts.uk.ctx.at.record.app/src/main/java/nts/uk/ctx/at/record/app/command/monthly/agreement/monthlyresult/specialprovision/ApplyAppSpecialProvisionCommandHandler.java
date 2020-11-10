package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.EmployeeInfor;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.*;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYM;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYear;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApproverRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 36協定特別条項の適用申請を更新登録する
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplyAppSpecialProvisionCommandHandler
        extends CommandHandlerWithResult<List<ApplyAppSpecialProvisionCommand>, List<ErrorResultDto>> {

    @Inject
    private RecordDomRequireService requireService;
    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
    @Inject
    private Approver36AgrByCompanyRepo approver36AgrByCompanyRepo;
    @Inject
    private UnitOfApproverRepo unitOfApproverRepo;
    @Inject
    private SyWorkplaceAdapter syWorkplaceAdapter;
    @Inject
    private Approver36AgrByWorkplaceRepo approver36AgrByWorkplaceRepo;
    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;
    @Inject
    private PersonEmpBasicInfoAdapter personEmpBasicInfoAdapter;

    @Override
    protected List<ErrorResultDto> handle(CommandHandlerContext<List<ApplyAppSpecialProvisionCommand>> context) {
        String cid = AppContexts.user().companyId();
        OneMonthAppUpdateRequireImpl requireMonth = new OneMonthAppUpdateRequireImpl(cid, requireService.createRequire(),
                specialProvisionsOfAgreementRepo, approver36AgrByCompanyRepo, unitOfApproverRepo,
                syWorkplaceAdapter, approver36AgrByWorkplaceRepo, affWorkplaceAdapter);
        AnnualAppUpdateRequireImpl requireAnual = new AnnualAppUpdateRequireImpl(requireService.createRequire(), specialProvisionsOfAgreementRepo);
        List<ApplyAppSpecialProvisionCommand> commands = context.getCommand();
        List<ErrorResultDto> errorResults = new ArrayList<>();
        List<AppCreationResult> results = new ArrayList<>();
        for (ApplyAppSpecialProvisionCommand command : commands) {
            if (TypeAgreementApplication.ONE_MONTH.value == command.getTypeAgreement()) {
                // 1ヶ月申請を更新する
                AppCreationResult result = OneMonthAppUpdate.update(requireMonth, cid, command.getApplicantId(),
                        new AgreementOneMonthTime(command.getTime()),
                        new ReasonsForAgreement(command.getReason()));
                results.add(result);
            } else if (TypeAgreementApplication.ONE_YEAR.value == command.getTypeAgreement()) {
                // 年間申請を更新する
                AppCreationResult result = AnnualAppUpdate.update(requireAnual, cid, command.getApplicantId(),
                        new AgreementOneYearTime(command.getTime()),
                        new ReasonsForAgreement(command.getReason()));
                results.add(result);
            }
        }

        // get errors
        for (AppCreationResult result : results) {
            List<ExcessErrorContentDto> errors = result.getErrorInfo().stream().map(ExcessErrorContentDto::new).collect(Collectors.toList());
            if (!CollectionUtil.isEmpty(errors)) {
                errorResults.add(new ErrorResultDto(result.getEmpId(),
                        null, null, errors));
            }
        }

        // execute
        if (CollectionUtil.isEmpty(errorResults)) {
            for (AppCreationResult result : results) {
                if (result.getAtomTask().isPresent()) {
                    transaction.execute(result.getAtomTask().get());
                }
            }
        }

        // 社員IDから個人社員基本情報を取得
        List<String> employeeIds = errorResults.stream().map(ErrorResultDto::getEmployeeId)
                .distinct().collect(Collectors.toList());
        Map<String, EmployeeInfor> empInfo = personEmpBasicInfoAdapter.getPerEmpBasicInfo(employeeIds)
                .stream().collect(Collectors.toMap(EmployeeInfor::getEmployeeId, c -> c));
        errorResults.forEach(x -> x.mappingEmpInfo(empInfo));

        return errorResults;
    }

    @AllArgsConstructor
    private class OneMonthAppUpdateRequireImpl implements OneMonthAppUpdate.Require {

        private String cid;
        private RecordDomRequireService.Require require;
        private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
        private Approver36AgrByCompanyRepo approver36AgrByCompanyRepo;
        private UnitOfApproverRepo unitOfApproverRepo;
        private SyWorkplaceAdapter syWorkplaceAdapter;
        private Approver36AgrByWorkplaceRepo approver36AgrByWorkplaceRepo;
        private AffWorkplaceAdapter affWorkplaceAdapter;

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return specialProvisionsOfAgreementRepo.getByAppId(applicantId);
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {
            specialProvisionsOfAgreementRepo.update(app);
        }

        @Override
        public Optional<Approver36AgrByCompany> getApproverHistoryItem(GeneralDate baseDate) {
            return approver36AgrByCompanyRepo.getByCompanyIdAndDate(cid, baseDate);
        }

        @Override
        public UnitOfApprover getUsageSetting() {
            return unitOfApproverRepo.getByCompanyId(cid);
        }

        @Override
        public Optional<SWkpHistRcImported> getYourWorkplace(String employeeId, GeneralDate baseDate) {
            return syWorkplaceAdapter.findBySid(employeeId, baseDate);
        }

        @Override
        public Optional<Approver36AgrByWorkplace> getApproveHistoryItem(String workplaceId, GeneralDate baseDate) {
            return approver36AgrByWorkplaceRepo.getByWorkplaceIdAndDate(workplaceId, baseDate);
        }

        @Override
        public List<String> getUpperWorkplace(String workplaceID, GeneralDate date) {
            return affWorkplaceAdapter.getUpperWorkplace(cid, workplaceID, date);
        }

        @Override
        public AgreMaxAverageTimeMulti getMaxAverageMulti(String sid, GeneralDate baseDate, YearMonth ym, Map<YearMonth, AttendanceTimeMonth> agreementTimes) {
            return AggregateAgreementTimeByYM.aggregate(this.require, sid, baseDate, ym, agreementTimes);
        }

        @Override
        public AgreementTimeYear timeYear(String sid, GeneralDate baseDate, Year year, Map<YearMonth, AttendanceTimeMonth> agreementTimes) {
            return AggregateAgreementTimeByYear.aggregate(createRequireY(), sid, baseDate, year, agreementTimes);
        }

        @Override
        public AgreementExcessInfo algorithm(CheckErrorApplicationMonthService.Require require, String employeeId, Year year) {
            return GetExcessTimesYear.get(this.require, employeeId, year);
        }

        @Override
        public Optional<AgreementOperationSetting> agreementOperationSetting(String cid) {
            return this.require.agreementOperationSetting(cid);
        }

        @Override
        public List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> sids, List<YearMonth> yearMonths) {
            return this.require.agreementTimeOfManagePeriod(sids, yearMonths);
        }

        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
            return this.require.workingConditionItem(employeeId, baseDate);
        }

        @Override
        public Optional<AgreementUnitSetting> agreementUnitSetting(String companyId) {
            return this.require.agreementUnitSetting(companyId);
        }

        @Override
        public Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.affEmployeeClassification(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode) {
            return this.require.agreementTimeOfClassification(companyId, laborSystemAtr, classificationCode);
        }

        @Override
        public List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.getCanUseWorkplaceForEmp(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfWorkPlace(workplaceId, laborSystemAtr);
        }

        @Override
        public Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.employment(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfEmployment(companyId, employmentCategoryCode, laborSystemAtr);
        }

        @Override
        public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfCompany(companyId, laborSystemAtr);
        }

        private AggregateAgreementTimeByYear.RequireM1 createRequireY() {

            return new AggregateAgreementTimeByYear.RequireM1() {

                @Override
                public Optional<AgreementOperationSetting> agreementOperationSetting(String cid) {
                    return require.agreementOperationSetting(cid);
                }

                @Override
                public BasicAgreementSetting basicAgreementSetting(String cid, String sid, GeneralDate baseDate, Year year) {
                    return require.basicAgreementSetting(cid, sid, baseDate, year);
                }

                @Override
                public Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String sid, YearMonth ym) {
                    return require.agreementTimeOfManagePeriod(sid, ym);
                }
            };
        }
    }

    @AllArgsConstructor
    private class AnnualAppUpdateRequireImpl implements AnnualAppUpdate.Require {

        private RecordDomRequireService.Require require;
        private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return specialProvisionsOfAgreementRepo.getByAppId(applicantId);
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {
            specialProvisionsOfAgreementRepo.update(app);
        }

        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
            return this.require.workingConditionItem(employeeId, baseDate);
        }

        @Override
        public Optional<AgreementUnitSetting> agreementUnitSetting(String companyId) {
            return this.require.agreementUnitSetting(companyId);
        }

        @Override
        public Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.affEmployeeClassification(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode) {
            return this.require.agreementTimeOfClassification(companyId, laborSystemAtr, classificationCode);
        }

        @Override
        public List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.getCanUseWorkplaceForEmp(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfWorkPlace(workplaceId, laborSystemAtr);
        }

        @Override
        public Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.employment(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfEmployment(companyId, employmentCategoryCode, laborSystemAtr);
        }

        @Override
        public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfCompany(companyId, laborSystemAtr);
        }
    }
}