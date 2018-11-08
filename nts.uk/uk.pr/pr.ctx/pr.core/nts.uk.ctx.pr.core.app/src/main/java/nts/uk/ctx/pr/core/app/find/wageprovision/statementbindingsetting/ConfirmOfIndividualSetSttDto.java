package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;

@AllArgsConstructor
@Value
public class ConfirmOfIndividualSetSttDto {
    private StateLinkSettingCompany mStateLinkSettingCompany;
    private StatementLayout mStatementLayout;
    private StateLinkSettingMaster mStateLinkSettingMaster;

}
