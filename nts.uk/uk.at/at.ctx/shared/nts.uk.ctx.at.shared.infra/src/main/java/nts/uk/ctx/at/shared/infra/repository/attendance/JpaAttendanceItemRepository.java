package nts.uk.ctx.at.shared.infra.repository.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItemRepository;
import nts.uk.ctx.at.shared.infra.entity.attendance.KmnmtAttendanceItem;

@Stateless
public class JpaAttendanceItemRepository extends JpaRepository implements AttendanceItemRepository {

	private static final String SELECT_NO_WHERE = "SELECT c FROM KmnmtAttendanceItem c";
	private static final String SELECT_POSSIBLE_ITEM = SELECT_NO_WHERE
			+ " WHERE c.kmnmtAttendanceItemPK.companyId = :companyId"
			+ " AND c.kmnmtAttendanceItemPK.attendanceItemId IN :listPossibleItem";
	private static final String SELECT_ITEM = SELECT_NO_WHERE + " WHERE c.kmnmtAttendanceItemPK.companyId = :companyId ORDER BY c.kmnmtAttendanceItemPK.attendanceItemId ASC";
	private static final String SELECT_ATTENDANCE_ITEM = SELECT_NO_WHERE
			+ " WHERE c.kmnmtAttendanceItemPK.companyId = :companyId " + " AND c.useAtr = :useAtr ";
	private static final String SELECT_ATTENDANCE_ITEM_DETAIL = SELECT_NO_WHERE
			+ " WHERE c.kmnmtAttendanceItemPK.companyId = :companyId "
			+ " AND c.kmnmtAttendanceItemPK.attendanceItemId = :attendanceItemId ";

	private static AttendanceItem toDomain(KmnmtAttendanceItem entity) {
		val domain = AttendanceItem.createSimpleFromJavaType(entity.kmnmtAttendanceItemPK.companyId,
				entity.kmnmtAttendanceItemPK.attendanceItemId, entity.attendanceItemName, entity.displayNumber,
				entity.useAtr, entity.attendanceAtr, entity.nameLineFeedPosition);
		return domain;
	}

	@Override
	public List<AttendanceItem> getAttendanceItems(String companyId, int useAtr) {
		return this.queryProxy().query(SELECT_ATTENDANCE_ITEM, KmnmtAttendanceItem.class)
				.setParameter("companyId", companyId).setParameter("useAtr", useAtr).getList(f -> toDomain(f));
	}

	@Override
	public List<AttendanceItem> getPossibleAttendanceItems(String companyId, List<Integer> lstPossible) {
		List<KmnmtAttendanceItem> entities = new ArrayList<>();
		CollectionUtil.split(lstPossible, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy().query(SELECT_POSSIBLE_ITEM, KmnmtAttendanceItem.class)
				.setParameter("companyId", companyId).setParameter("listPossibleItem", subList)
				.getList());
		});
		return entities.stream().map(c -> toDomain(c)).collect(Collectors.toList());
	}

	@Override
	public List<AttendanceItem> getAttendanceItems(String companyId) {
		return this.queryProxy().query(SELECT_ITEM, KmnmtAttendanceItem.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<AttendanceItem> getAttendanceItemDetail(String companyId, int attendanceItemId) {
		return this.queryProxy().query(SELECT_ATTENDANCE_ITEM_DETAIL, KmnmtAttendanceItem.class)
				.setParameter("companyId", companyId).setParameter("attendanceItemId", attendanceItemId)
				.getSingle(f -> toDomain(f));
	}
}
