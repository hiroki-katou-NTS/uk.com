package nts.uk.ctx.office.ac.status;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.pub.dailyresult.AttendanceStateExport;
import nts.uk.ctx.at.record.pub.dailyresult.AttendanceStatePub;
import nts.uk.ctx.office.dom.status.StatusClassfication;
import nts.uk.ctx.office.dom.status.adapter.AttendanceAdapter;
import nts.uk.ctx.office.dom.status.adapter.AttendanceStateImport;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AttendanceAdapterImpl implements AttendanceAdapter {

	@Inject
	private AttendanceStatePub pub;
	
	@Override
	public AttendanceStateImport getAttendace(String sid) {
		AttendanceStateExport data = pub.getAttendanceState(sid);
		return AttendanceStateImport.builder()
				.attendanceState(EnumAdaptor.valueOf(data.getAttendanceState().value,StatusClassfication.class))
				.workingNow(data.isWorkingNow())
				.build();
	}

}
