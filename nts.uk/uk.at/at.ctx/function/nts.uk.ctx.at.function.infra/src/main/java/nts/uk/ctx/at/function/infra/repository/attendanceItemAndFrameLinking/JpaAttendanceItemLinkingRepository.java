/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.attendanceItemAndFrameLinking;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.repository.AttendanceItemLinkingRepository;
import nts.uk.ctx.at.function.infra.entity.attendanceItemAndFrameLinking.KfnmtAttendanceLink;

@Stateless
public class JpaAttendanceItemLinkingRepository extends JpaRepository implements AttendanceItemLinkingRepository {

	private static final String FIND;
	private static final String FIND_BY_ANY_ITEM;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAttendanceLink a ");
		builderString.append("WHERE a.kfnmtAttendanceLinkPK.attendanceItemId IN :attendanceItemIds ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAttendanceLink a ");
		builderString.append("WHERE a.kfnmtAttendanceLinkPK.typeOfItem = :typeOfItem ");
		builderString.append("AND a.kfnmtAttendanceLinkPK.frameCategory = 8"); //任意項目
		FIND_BY_ANY_ITEM = builderString.toString();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.repository.
	 * AttendanceItemLinkingRepository#getByAnyItemCategory(nts.uk.ctx.at.
	 * function.dom.attendanceItemAndFrameLinking.enums.TypeOfItem)
	 */
	@Override
	public List<AttendanceItemLinking> getByAnyItemCategory(TypeOfItem type) {
		return this.queryProxy().query(FIND_BY_ANY_ITEM, KfnmtAttendanceLink.class)
				.setParameter("typeOfItem", type.value).getList(f -> toDomain(f));
	}

}
