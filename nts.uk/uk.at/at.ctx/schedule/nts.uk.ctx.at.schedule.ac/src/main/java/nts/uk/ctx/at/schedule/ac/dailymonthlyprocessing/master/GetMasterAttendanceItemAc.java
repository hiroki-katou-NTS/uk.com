package nts.uk.ctx.at.schedule.ac.dailymonthlyprocessing.master;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyperform.master.GetMasterAttendanceItemPub;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master.AllMasterAttItemImport;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master.AllMasterAttItemImport.MasterAttItemDetailmport;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master.GetMasterAttendanceItemAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;

@Stateless
public class GetMasterAttendanceItemAc implements GetMasterAttendanceItemAdapter {

	@Inject
	private GetMasterAttendanceItemPub pub;

	@Override
	public List<AllMasterAttItemImport> getAllMaster(String companyId, List<Integer> lstAtt, GeneralDate baseDate) {
		return pub.getAllMaster(companyId, lstAtt, baseDate).stream().map(x -> {
			return new AllMasterAttItemImport(
					EnumAdaptor.valueOf(x.getType(), TypesMasterRelatedDailyAttendanceItem.class),
					x.getLstDetail().stream()
							.map(y -> new MasterAttItemDetailmport(y.getId(), y.getCode(), y.getName()))
							.collect(Collectors.toList()));
		}).collect(Collectors.toList());
	}

}
