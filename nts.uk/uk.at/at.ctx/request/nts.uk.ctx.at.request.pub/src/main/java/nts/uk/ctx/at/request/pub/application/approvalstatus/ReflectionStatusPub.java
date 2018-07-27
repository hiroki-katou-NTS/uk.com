package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.List;
import nts.arc.time.GeneralDate;
public interface ReflectionStatusPub {

	public List<ApplicationNewExport> getByListRefStatus(String employeeID ,GeneralDate startDate, GeneralDate endDate , List<Integer> listReflecInfor);
}
