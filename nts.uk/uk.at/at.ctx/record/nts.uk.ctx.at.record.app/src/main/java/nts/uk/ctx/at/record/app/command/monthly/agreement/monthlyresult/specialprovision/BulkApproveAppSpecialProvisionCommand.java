package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

import java.util.List;

/**
 * 36協定特別条項の適用申請の一括承認を行う
 *
 * @author Le Huu Dat
 */
@Value
public class BulkApproveAppSpecialProvisionCommand {
    private List<BulkApproveAppSpecialProvisionApproverCommand> approvers;
    private List<BulkApproveAppSpecialProvisionConfirmerCommand> confirmers;
}
