package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DisplayAndInputControl;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstDailyServiceTypeControl;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaDailyAttdItemAuthRepository extends JpaRepository implements DailyAttdItemAuthRepository {

	private static final String SELECT_BY_AUTHORITY_DAILY_ID = "SELECT c FROM KshstDailyServiceTypeControl c"
			+ " WHERE c.kshstDailyServiceTypeControlPK.companyID = :companyID"
			+ " AND c.kshstDailyServiceTypeControlPK.authorityDailyID = :authorityDailyID"
			+ " ORDER BY c.kshstDailyServiceTypeControlPK.itemDailyID";

	private static final String SELECT_BY_KEY = "SELECT c FROM KshstDailyServiceTypeControl c"
			+ " WHERE c.kshstDailyServiceTypeControlPK.companyID = :companyID"
			+ " AND c.kshstDailyServiceTypeControlPK.authorityDailyID = :authorityDailyID"
			+ " AND c.toUse = :toUse";

	private static final String SELECT_BY_KEY_ATT_ITEM_ID = SELECT_BY_KEY
			+ " AND c.kshstDailyServiceTypeControlPK.itemDailyID IN :itemDailyIDs";

	@Override
	public Optional<DailyAttendanceItemAuthority> getDailyAttdItem(String companyID, String authorityDailyId) {
		List<DisplayAndInputControl> data = this.queryProxy()
				.query(SELECT_BY_AUTHORITY_DAILY_ID, KshstDailyServiceTypeControl.class)
				.setParameter("companyID", companyID).setParameter("authorityDailyID", authorityDailyId)
				.getList(c -> c.toDomain());
		if (data.isEmpty())
			return Optional.empty();
		DailyAttendanceItemAuthority dailyAttendanceItemAuthority = new DailyAttendanceItemAuthority(companyID,
				authorityDailyId, data);
		return Optional.of(dailyAttendanceItemAuthority);
	}

	@Override
	public void updateDailyAttdItemAuth(DailyAttendanceItemAuthority dailyAttendanceItemAuthority) {
		List<KshstDailyServiceTypeControl> newEntity = dailyAttendanceItemAuthority.getListDisplayAndInputControl()
				.stream().map(c -> KshstDailyServiceTypeControl.toEntity(dailyAttendanceItemAuthority.getCompanyID(),
						dailyAttendanceItemAuthority.getAuthorityDailyId(), c))
				.collect(Collectors.toList());
		List<KshstDailyServiceTypeControl> updateEntity = this.queryProxy()
				.query(SELECT_BY_AUTHORITY_DAILY_ID, KshstDailyServiceTypeControl.class)
				.setParameter("companyID", dailyAttendanceItemAuthority.getCompanyID())
				.setParameter("authorityDailyID", dailyAttendanceItemAuthority.getAuthorityDailyId()).getList();
		 int minCount = Math.min(updateEntity.size(), newEntity.size());
		for (int i = 0; i < minCount; i++) {
			updateEntity.get(i).toUse = newEntity.get(i).toUse;
			if (newEntity.get(i).toUse == 1) {
				updateEntity.get(i).canBeChangedByOthers = newEntity.get(i).canBeChangedByOthers;
				updateEntity.get(i).youCanChangeIt = newEntity.get(i).youCanChangeIt;
			}
			this.commandProxy().update(updateEntity.get(i));
		}
	}

	@Override
	public void addDailyAttdItemAuth(DailyAttendanceItemAuthority dailyAttendanceItemAuthority) {
		List<KshstDailyServiceTypeControl> newEntity = dailyAttendanceItemAuthority.getListDisplayAndInputControl()
				.stream().map(c -> KshstDailyServiceTypeControl.toEntity(dailyAttendanceItemAuthority.getCompanyID(),
						dailyAttendanceItemAuthority.getAuthorityDailyId(), c))
				.collect(Collectors.toList());
		for (KshstDailyServiceTypeControl dailyServiceTypeControl : newEntity) {
			this.commandProxy().insert(dailyServiceTypeControl);
		}

	}

	@Override
	public Optional<DailyAttendanceItemAuthority> getDailyAttdItemByAttItemId(String companyID, String authorityDailyId,
			List<Integer> attendanceItemIds) {
		List<DisplayAndInputControl> data = new ArrayList<>();
		if (attendanceItemIds == null || attendanceItemIds.isEmpty()) {
			data = this.queryProxy().query(SELECT_BY_KEY, KshstDailyServiceTypeControl.class)
					.setParameter("companyID", companyID).setParameter("authorityDailyID", authorityDailyId)
					.setParameter("toUse", NotUseAtr.USE.value)
					.getList(c -> c.toDomain());
		} else {
			data = this.queryProxy().query(SELECT_BY_KEY_ATT_ITEM_ID, KshstDailyServiceTypeControl.class)
					.setParameter("companyID", companyID).setParameter("authorityDailyID", authorityDailyId)
					.setParameter("toUse", NotUseAtr.USE.value).setParameter("itemDailyIDs", attendanceItemIds)
					.getList(c -> c.toDomain());
		}
		if (data.isEmpty())
			return Optional.empty();
		DailyAttendanceItemAuthority dailyAttendanceItemAuthority = new DailyAttendanceItemAuthority(companyID,
				authorityDailyId, data);
		return Optional.of(dailyAttendanceItemAuthority);
	}

}
