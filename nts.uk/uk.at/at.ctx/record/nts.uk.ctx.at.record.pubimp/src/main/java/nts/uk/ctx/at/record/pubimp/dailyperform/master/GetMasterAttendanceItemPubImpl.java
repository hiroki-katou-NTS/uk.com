package nts.uk.ctx.at.record.pubimp.dailyperform.master;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.GetMasterAttendanceItem;
import nts.uk.ctx.at.record.pub.dailyperform.master.AllMasterAttItemExport;
import nts.uk.ctx.at.record.pub.dailyperform.master.AllMasterAttItemExport.MasterAttItemDetailExport;
import nts.uk.ctx.at.record.pub.dailyperform.master.GetMasterAttendanceItemPub;

@Stateless
public class GetMasterAttendanceItemPubImpl implements GetMasterAttendanceItemPub {

	@Inject
	private GetMasterAttendanceItem getMasterAttendanceItem;

	@Override
	public List<AllMasterAttItemExport> getAllMaster(String companyId, List<Integer> lstAtt, GeneralDate baseDate) {
		return getMasterAttendanceItem.getAllMaster(companyId, lstAtt, baseDate).stream().map(x -> {
			return new AllMasterAttItemExport(x.getType().value,
					x.getLstDetail().stream()
							.map(y -> new MasterAttItemDetailExport(y.getId(), y.getCode(), y.getName()))
							.collect(Collectors.toList()));
		}).collect(Collectors.toList());
	}

}
