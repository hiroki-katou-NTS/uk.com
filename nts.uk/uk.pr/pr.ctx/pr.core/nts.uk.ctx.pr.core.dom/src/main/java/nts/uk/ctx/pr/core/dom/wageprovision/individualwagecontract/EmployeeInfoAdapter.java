package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import javax.ejb.Stateless;
import java.util.List;

public interface EmployeeInfoAdapter {
    List<EmployeeInfoImport> getByListSid(List<String> sIds);
}