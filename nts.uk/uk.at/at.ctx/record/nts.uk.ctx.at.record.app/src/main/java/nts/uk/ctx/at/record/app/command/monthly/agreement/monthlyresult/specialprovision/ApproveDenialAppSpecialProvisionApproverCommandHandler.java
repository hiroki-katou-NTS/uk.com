package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AppApproval;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.AgreementApprovalComments;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請の承認/否認を行う（36承認者）
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApproveDenialAppSpecialProvisionApproverCommandHandler extends CommandHandler<List<ApproveDenialAppSpecialProvisionApproverCommand>> {
    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
    @Inject
    private AgreementMonthSettingRepository agreementMonthSettingRepo;
    @Inject
    private AgreementYearSettingRepository agreementYearSettingRepo;

    @Override
    protected void handle(CommandHandlerContext<List<ApproveDenialAppSpecialProvisionApproverCommand>> context) {
        String sid = AppContexts.user().employeeId();
        RequireImpl require = new RequireImpl(specialProvisionsOfAgreementRepo, agreementMonthSettingRepo, agreementYearSettingRepo);
        List<ApproveDenialAppSpecialProvisionApproverCommand> commands = context.getCommand();
        for (ApproveDenialAppSpecialProvisionApproverCommand command : commands) {
            Optional<AgreementApprovalComments> approvalComment = Optional.empty();
            if (command.getApprovalComment() != null) {
                approvalComment = Optional.of(new AgreementApprovalComments(command.getApprovalComment()));
            }
            AtomTask persist = AppApproval.change(require, command.getApplicantId(), sid,
                    EnumAdaptor.valueOf(command.getApprovalStatus(), ApprovalStatus.class), approvalComment);
            transaction.execute(persist);
        }

    }

    @AllArgsConstructor
    private class RequireImpl implements AppApproval.Require {

        private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
        private AgreementMonthSettingRepository agreementMonthSettingRepo;
        private AgreementYearSettingRepository agreementYearSettingRepo;

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return specialProvisionsOfAgreementRepo.getByAppId(applicantId);
        }

        @Override
        public Optional<AgreementMonthSetting> getYearMonthSetting(String empId, YearMonth yearMonth) {
            return agreementMonthSettingRepo.findByKey(empId, yearMonth);
        }

        @Override
        public Optional<AgreementYearSetting> getYearSetting(String empId, Year year) {
            return agreementYearSettingRepo.findBySidAndYear(empId, year.v());
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {
            specialProvisionsOfAgreementRepo.update(app);
        }

        @Override
        public void addYearMonthSetting(AgreementMonthSetting setting) {
            agreementMonthSettingRepo.add(setting);
        }

        @Override
        public void updateYearMonthSetting(AgreementMonthSetting setting) {
            agreementMonthSettingRepo.update(setting);
        }

        @Override
        public void addYearSetting(AgreementYearSetting setting) {
            agreementYearSettingRepo.add(setting);
        }

        @Override
        public void updateYearSetting(AgreementYearSetting setting) {
            agreementYearSettingRepo.update(setting);
        }
    }
}
