package nts.uk.ctx.at.record.infra.repository.dailyattendanceitem;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.record.infra.entity.dailyattendanceitem.KrcmtDailyAttendanceItem;

@Stateless
public class JpaDailyAttendanceItemRepository extends JpaRepository implements DailyAttendanceItemRepository {

	private static final String FIND;
	private static final String FIND_ALL;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtDailyAttendanceItem a ");
		builderString.append("WHERE a.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		builderString.append("AND a.userCanSet = :userCanSet ");
		FIND = builderString.toString();
		
		
		StringBuilder b = new StringBuilder();
		b.append("SELECT a ");
		b.append("FROM KrcmtDailyAttendanceItem a ");
		b.append("WHERE a.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		FIND_ALL = b.toString();
		
	}

	@Override
	public List<DailyAttendanceItem> getListTobeUsed(String companyId, int userCanUpdateAtr) {
		return this.queryProxy().query(FIND, KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId)
				.setParameter("userCanSet", userCanUpdateAtr).getList(f -> toDomain(f));
	}

	private static DailyAttendanceItem toDomain(KrcmtDailyAttendanceItem krcmtDailyAttendanceItem) {
		DailyAttendanceItem dailyAttendanceItem = DailyAttendanceItem.createFromJavaType(
				krcmtDailyAttendanceItem.krcmtDailyAttendanceItemPK.companyId,
				krcmtDailyAttendanceItem.krcmtDailyAttendanceItemPK.attendanceItemId,
				krcmtDailyAttendanceItem.attendanceItemName,
				krcmtDailyAttendanceItem.displayNumber.intValue(),
				krcmtDailyAttendanceItem.userCanSet.intValue(),
				krcmtDailyAttendanceItem.dailyAttendanceAtr.intValue(),
				krcmtDailyAttendanceItem.nameLineFeedPosition.intValue());
		return dailyAttendanceItem;
	}

	@Override
	public List<DailyAttendanceItem> getList(String companyId) {
		return this.queryProxy().query(FIND_ALL, KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}
}
