/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.pubimp.attendanceItemAndFrameLinking;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.repository.AttendanceItemLinkingRepository;
import nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking.AttendanceItemLinkingDto;
import nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking.AttendanceItemLinkingPub;

@Stateless
public class AttendanceItemPubImp implements AttendanceItemLinkingPub {
	
	@Inject
	private AttendanceItemLinkingRepository attendanceItemLinkingRepository;

	@Override
	public List<AttendanceItemLinkingDto> getFrameNo(List<Integer> attendanceItemIds) {
		return attendanceItemLinkingRepository.getByAttendanceId(attendanceItemIds).stream().map(f -> {
			return new AttendanceItemLinkingDto(f.getAttendanceItemId(), f.getFrameNo().v(), f.getFrameCategory().value);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking.
	 * AttendanceItemLinkingPub#getByAnyItemCategory(int)
	 */
	@Override
	public List<AttendanceItemLinkingDto> getByAnyItemCategory(int typeOfItem) {
		return this.attendanceItemLinkingRepository
				.getByAnyItemCategory(EnumAdaptor.valueOf(typeOfItem, TypeOfItem.class)).stream()
				.map(item -> new AttendanceItemLinkingDto(item.getAttendanceItemId(), item.getFrameNo().v(),
						item.getFrameCategory().value))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking.
	 * AttendanceItemLinkingPub#findByFrameNos(java.util.List)
	 */
	@Override
	public List<AttendanceItemLinkingDto> findByFrameNos(List<Integer> frameNos, int typeOfItem, int frameCategory) {
		return this.attendanceItemLinkingRepository.findByFrameNos(frameNos, typeOfItem, frameCategory).stream()
				.map(item -> new AttendanceItemLinkingDto(item.getAttendanceItemId(), item.getFrameNo().v(),
						item.getFrameCategory().value))
				.collect(Collectors.toList());
	}

}
