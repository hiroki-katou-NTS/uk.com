/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KrcmtDailyAttendanceItem;

@Stateless
public class JpaDailyAttendanceItemRepository extends JpaRepository implements DailyAttendanceItemRepository {

	private static final String FIND;
	private static final String FIND_ALL;

	private static final String FIND_SINGLE;

	private static final String FIND_BY_ID;

	private static final String FIND_BY_ATR;
	
	private static final String FIND_BY_ATRS;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtDailyAttendanceItem a ");
		builderString.append("WHERE a.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		FIND = builderString.toString();
		
		
		StringBuilder b = new StringBuilder();
		b.append("SELECT a ");
		b.append("FROM KrcmtDailyAttendanceItem a ");
		b.append("WHERE a.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		FIND_ALL = b.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtDailyAttendanceItem a ");
		builderString.append("WHERE a.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		builderString.append("AND  a.krcmtDailyAttendanceItemPK.attendanceItemId = :attendanceItemId ");
		FIND_SINGLE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtDailyAttendanceItem a ");
		builderString.append("WHERE a.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		builderString.append("AND a.krcmtDailyAttendanceItemPK.attendanceItemId IN :dailyAttendanceItemIds ");
		FIND_BY_ID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtDailyAttendanceItem a ");
		builderString.append("WHERE a.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		builderString.append("AND a.dailyAttendanceAtr = :dailyAttendanceAtr ");
		builderString.append("ORDER BY a.displayNumber ASC");
		FIND_BY_ATR = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtDailyAttendanceItem a ");
		builderString.append("WHERE a.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		builderString.append("AND a.dailyAttendanceAtr IN :dailyAttendanceAtrs ");
		FIND_BY_ATRS = builderString.toString();
	}

	@Override
	public List<DailyAttendanceItem> getListTobeUsed(String companyId, int userCanUpdateAtr) {
		return this.queryProxy().query(FIND, KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}
	
	@Override
	public List<DailyAttendanceItem> getListById(String companyId, List<Integer> dailyAttendanceItemIds) {
		if(dailyAttendanceItemIds.isEmpty())
			return Collections.emptyList();
		return this.queryProxy().query(FIND_BY_ID, KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId)
				.setParameter("dailyAttendanceItemIds", dailyAttendanceItemIds).getList(f -> toDomain(f));
	}

	@Override
	public List<DailyAttendanceItem> getList(String companyId) {
		return this.queryProxy().query(FIND_ALL, KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
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
	public Optional<DailyAttendanceItem> getDailyAttendanceItem(String companyId, int attendanceItemId) {
		return this.queryProxy().query(FIND_SINGLE, KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId)
				.setParameter("attendanceItemId", attendanceItemId).getSingle(f -> toDomain(f));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.
	 * DailyAttendanceItemRepository#findByAtr(java.lang.String, int)
	 */
	@Override
	public List<DailyAttendanceItem> findByAtr(String companyId, DailyAttendanceAtr itemAtr) {
		return this.queryProxy().query(FIND_BY_ATR, KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId)
				.setParameter("dailyAttendanceAtr", itemAtr.value).getList(f -> toDomain(f));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.
	 * DailyAttendanceItemRepository#findByAtr(java.lang.String, List<Integer>)
	 */
	@Override
	public List<DailyAttendanceItem> findByAtr(String companyId, List<Integer> dailyAttendanceAtrs) {
		return this.queryProxy().query(FIND_BY_ATRS, KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId)
				.setParameter("dailyAttendanceAtrs", dailyAttendanceAtrs).getList(f -> toDomain(f));
	}

	@Override
	public List<DailyAttendanceItem> findByAtrsAndAttItemIds(String companyId, List<Integer> itemAtrs,
			List<Integer> attendanceItemIds) {
		boolean hasItemAtrs = itemAtrs != null && !itemAtrs.isEmpty();
		boolean hasAttendanceAtrs = attendanceItemIds != null && !attendanceItemIds.isEmpty();

		StringBuilder builderString = new StringBuilder();
		builderString.append(FIND_ALL);
		if (hasItemAtrs) {
			builderString.append(" AND a.dailyAttendanceAtr IN :dailyAttendanceAtrs");
		}
		if (hasAttendanceAtrs) {
			builderString.append(" AND a.krcmtDailyAttendanceItemPK.attendanceItemId IN :attendanceItemIds");
		}

		TypedQueryWrapper<KrcmtDailyAttendanceItem> query = this.queryProxy()
				.query(builderString.toString(), KrcmtDailyAttendanceItem.class).setParameter("companyId", companyId);

		if (hasItemAtrs) {
			query.setParameter("dailyAttendanceAtrs", itemAtrs);
		}
		if (hasAttendanceAtrs) {
			query.setParameter("attendanceItemIds", attendanceItemIds);
		}

		return query.getList(f -> toDomain(f));
	}
}
