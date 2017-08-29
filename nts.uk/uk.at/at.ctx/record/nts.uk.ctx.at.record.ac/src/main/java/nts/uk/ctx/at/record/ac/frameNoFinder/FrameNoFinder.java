package nts.uk.ctx.at.record.ac.frameNoFinder;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking.AttendanceItemLinkingPub;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapter;

@Stateless
public class FrameNoFinder implements FrameNoAdapter{
	
	@Inject
	private AttendanceItemLinkingPub attendanceItemLinkingPub;

	@Override
	public List<Integer> getFrameNo(String attendanceItemId) {
		return attendanceItemLinkingPub.getFrameNo(attendanceItemId);
	}
	
}
