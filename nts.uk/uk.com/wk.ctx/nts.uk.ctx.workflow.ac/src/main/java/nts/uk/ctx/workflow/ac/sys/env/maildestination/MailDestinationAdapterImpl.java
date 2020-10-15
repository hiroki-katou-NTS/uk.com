package nts.uk.ctx.workflow.ac.sys.env.maildestination;

import nts.uk.ctx.workflow.dom.adapter.sys.env.maildestination.MailDestinationAdapter;
import nts.uk.ctx.workflow.dom.adapter.sys.env.maildestination.MailDestinationImport;
//import nts.uk.ctx.sys.env.pub.maildestination.IMailDestinationPub;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MailDestinationAdapterImpl implements MailDestinationAdapter {
//    @Inject
//    private IMailDestinationPub iMailDestinationPub;

    @Override
    public List<MailDestinationImport> getEmpEmailAddress(String cID, List<String> sIDs, Integer functionID) {
//        List<MailDestinationImport> listEmpMail = iMailDestinationPub.getEmpEmailAddress(cID, sIDs, functionID).stream()
//                .map(x -> new MailDestinationImport(x.getEmployeeID(), mapGoingMail(x.getOutGoingMails())))
//                .collect(Collectors.toList());
//        return listEmpMail;
        return Collections.emptyList();
    }
}
