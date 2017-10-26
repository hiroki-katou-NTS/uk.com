package nts.uk.ctx.at.record.pubimp.dailyattendanceitem;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.record.pub.dailyattendanceitem.DailyAttendanceItemRecPub;
import nts.uk.ctx.at.record.pub.dailyattendanceitem.DailyAttendanceItemRecPubDto;

@Stateless
public class DailyAttendanceItemRecPubImpl implements DailyAttendanceItemRecPub {

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	@Override
	public List<DailyAttendanceItemRecPubDto> getDailyAttendanceItem(String companyId,
			List<Integer> dailyAttendanceItemIds) {
		List<DailyAttendanceItem> dailyAttendanceItemList = this.dailyAttendanceItemRepository.getListById(companyId,
				dailyAttendanceItemIds);

		List<DailyAttendanceItemRecPubDto> attendanceItemPubDtos = dailyAttendanceItemList.stream().map(f -> {
			return new DailyAttendanceItemRecPubDto(f.getCompanyId(), f.getAttendanceItemId(),
					f.getAttendanceName().v(), f.getDisplayNumber(), f.getUserCanUpdateAtr().value,
					f.getDailyAttendanceAtr().value, f.getNameLineFeedPosition());
		}).collect(Collectors.toList());

		return attendanceItemPubDtos;
	}

	@Override
	public List<DailyAttendanceItemRecPubDto> getDailyAttendanceItemList(String companyId) {
		List<DailyAttendanceItem> attendanceItems = this.dailyAttendanceItemRepository.getList(companyId);
		
		List<DailyAttendanceItemRecPubDto> dailyAttendanceItemRecPubDtos = attendanceItems.stream().map(f -> {
			return new DailyAttendanceItemRecPubDto(f.getCompanyId(), f.getAttendanceItemId(), f.getAttendanceName().v(), f.getDisplayNumber(), f.getUserCanUpdateAtr().value, f.getDailyAttendanceAtr().value, f.getNameLineFeedPosition());
		}).collect(Collectors.toList());
		return dailyAttendanceItemRecPubDtos;
	}

	@Override
	public List<DailyAttendanceItemRecPubDto> getDailyAttendanceItemList(String companyId, List<Integer> dailyAttendanceAtrs) {
		if (CollectionUtil.isEmpty(dailyAttendanceAtrs)) {
			return Collections.emptyList();
		}
		
		return this.dailyAttendanceItemRepository.findByAtr(companyId, dailyAttendanceAtrs)
				.stream().map(x -> DailyAttendanceItemRecPubDto.builder()
							.attendanceItemId(x.getAttendanceItemId())
							.attendanceName(x.getAttendanceName().v())
							.dailyAttendanceAtr(x.getDailyAttendanceAtr().value)
							.displayNumber(x.getDisplayNumber())
							.nameLineFeedPosition(x.getNameLineFeedPosition())
							.userCanUpdateAtr(x.getUserCanUpdateAtr().value)
							.companyId(companyId).build()).collect(Collectors.toList());
	}
}
