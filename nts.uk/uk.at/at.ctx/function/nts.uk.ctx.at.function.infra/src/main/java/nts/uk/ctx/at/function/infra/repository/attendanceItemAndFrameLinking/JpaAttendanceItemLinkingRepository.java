package nts.uk.ctx.at.function.infra.repository.attendanceItemAndFrameLinking;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.repository.AttendanceItemLinkingRepository;
import nts.uk.ctx.at.function.infra.enity.attendanceItemAndFrameLinking.KfnmtAttendanceLink;

@Stateless
public class JpaAttendanceItemLinkingRepository extends JpaRepository implements AttendanceItemLinkingRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAttendanceLink a ");
		builderString.append("WHERE a.kfnmtAttendanceLinkPK.attendanceItemId IN :attendanceItemIds ");
		FIND = builderString.toString();
	}

	@Override
	public List<AttendanceItemLinking> getByAttendanceId(List<Integer> attendanceItemIds) {
		return this.queryProxy().query(FIND, KfnmtAttendanceLink.class)
				.setParameter("attendanceItemIds", attendanceItemIds).getList(f -> toDomain(f));
	}

	private static AttendanceItemLinking toDomain(KfnmtAttendanceLink kfnmtAttendanceLink) {
		AttendanceItemLinking attendanceItemLinking = AttendanceItemLinking.createFromJavaType(
				kfnmtAttendanceLink.kfnmtAttendanceLinkPK.attendanceItemId,
				kfnmtAttendanceLink.kfnmtAttendanceLinkPK.frameNo.intValue(),
				kfnmtAttendanceLink.kfnmtAttendanceLinkPK.typeOfItem.intValue(),
				kfnmtAttendanceLink.kfnmtAttendanceLinkPK.frameCategory.intValue());
		return attendanceItemLinking;
	}

}
