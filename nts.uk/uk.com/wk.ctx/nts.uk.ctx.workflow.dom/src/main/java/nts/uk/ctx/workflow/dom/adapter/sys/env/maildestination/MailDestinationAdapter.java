package nts.uk.ctx.workflow.dom.adapter.sys.env.maildestination;

import java.util.List;

public interface MailDestinationAdapter {
    List<MailDestinationImport> getEmpEmailAddress(String cID, List<String> sIDs, Integer functionID);
}
