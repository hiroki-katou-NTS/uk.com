package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.ApproverItem;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.OneMonthAppUpdate;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請を更新登録する（1ヶ月）
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplyAppSpecialProvisionMonthCommandHandler extends CommandHandler<List<ApplyAppSpecialProvisionMonthCommand>> {
    @Override
    protected void handle(CommandHandlerContext<List<ApplyAppSpecialProvisionMonthCommand>> context) {
        String cid = AppContexts.user().companyId();
        RequireImpl require = new RequireImpl();
        List<ApplyAppSpecialProvisionMonthCommand> commands = context.getCommand();
        for(ApplyAppSpecialProvisionMonthCommand command : commands){
            OneMonthAppUpdate.update(require, cid, command.getApplicantId(),
                    new AgreementOneMonthTime(command.getOneMonthTime()),
                    new ReasonsForAgreement(command.getReason()));
        }
    }

    @AllArgsConstructor
    private class RequireImpl implements OneMonthAppUpdate.Require{

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return Optional.empty();
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {

        }

        @Override
        public Optional<ApproverItem> getApproverHistoryItem(GeneralDate baseDate) {
            return Optional.empty();
        }

        @Override
        public UnitOfApprover getUsageSetting() {
            return null;
        }

        @Override
        public Optional<SWkpHistRcImported> getYourWorkplace(String employeeId, GeneralDate baseDate) {
            return Optional.empty();
        }

        @Override
        public Optional<Approver36AgrByWorkplace> getApproveHistoryItem(String workplaceId, GeneralDate baseDate) {
            return Optional.empty();
        }

        @Override
        public List<String> getUpperWorkplace(String workplaceID, GeneralDate date) {
            return null;
        }

        @Override
        public AgreMaxAverageTimeMulti getMaxAverageMulti(String sid, GeneralDate baseDate, YearMonth ym, Map<YearMonth, AgreementOneMonthTime> agreementTimes) {
            return null;
        }

        @Override
        public AgreementTimeYear timeYear(String sid, GeneralDate baseDate, Year year, Map<YearMonth, AgreementOneMonthTime> agreementTimes) {
            return null;
        }

        @Override
        public AgreementExcessInfo algorithm(String employeeId, Year year) {
            return null;
        }

        @Override
        public Optional<AgreementOperationSetting> agreementOperationSetting(String cid) {
            return Optional.empty();
        }

        @Override
        public List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> sids, List<YearMonth> yearMonths) {
            return null;
        }

        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
            return Optional.empty();
        }

        @Override
        public Optional<AgreementUnitSetting> agreementUnitSetting(String companyId) {
            return Optional.empty();
        }

        @Override
        public Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId, GeneralDate baseDate) {
            return Optional.empty();
        }

        @Override
        public Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode) {
            return Optional.empty();
        }

        @Override
        public List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate) {
            return null;
        }

        @Override
        public Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId, LaborSystemtAtr laborSystemAtr) {
            return Optional.empty();
        }

        @Override
        public Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate) {
            return Optional.empty();
        }

        @Override
        public Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
            return Optional.empty();
        }

        @Override
        public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr) {
            return Optional.empty();
        }
    }
}
