package nts.uk.ctx.sys.portal.ac.notice;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.portal.dom.notice.adapter.MessageNoticeAdapter;

@Stateless
public class MessageNoticeAdapterImpl implements MessageNoticeAdapter {
	
	@Inject
	private WorkplacePub workplacePub;

	@Override
	public Optional<String> getWpId(String sid, GeneralDate baseDate) {
		Optional<SWkpHistExport> sWkpHistExport = workplacePub.findBySidNew("", sid, baseDate);
		if (!sWkpHistExport.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(sWkpHistExport.get().getWorkplaceId());
	}
	
	
}
