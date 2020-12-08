package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

import java.util.List;

/**
 * 36協定特別条項の適用申請の承認/否認を行う
 *
 * @author Le Huu Dat
 */
@Value
public class ApproveDenialAppSpecialProvisionCommand {
    private List<ApproveDenialAppSpecialProvisionApproverCommand> approvers;
    private List<ApproveDenialAppSpecialProvisionConfirmerCommand> confirmers;
}
