package nts.uk.ctx.at.record.app.find.monthly.week;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtilRes;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter.WeeklyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.shr.com.context.AppContexts;

public class WeeklyRecordToAttendanceItemConverterImpl implements WeeklyRecordToAttendanceItemConverter {

	private AttendanceItemConvertFactory convertFactory;
	private AttendanceTimeOfWeeklyDto weeklyAttendance;
	private Map<Integer, OptionalItem> master;
	
	public WeeklyRecordToAttendanceItemConverterImpl(AttendanceItemConvertFactory convertFactory) {
		this.convertFactory = convertFactory;
		this.master = getOptionalItemRepo().findAll(AppContexts.user().companyId())
				.stream().collect(Collectors.toMap(c -> c.getOptionalItemNo().v(), c -> c));
	}
	
	@Override
	public OptionalItemRepository getOptionalItemRepo() {
		return this.convertFactory.getOptionalItemRepo();
	}

	@Override
	public Optional<ItemValue> convert(int attendanceItemId) {
		return convert(Arrays.asList(attendanceItemId)).stream().findFirst();
	}

	@Override
	public List<ItemValue> convert(Collection<Integer> attendanceItemIds) {
		
		return AttendanceItemUtilRes.collect(weeklyAttendance, attendanceItemIds, AttendanceItemType.WEEKLY_ITEM);
	}

	@Override
	public void merge(ItemValue value) {
		merge(Arrays.asList(value));
	}

	@Override
	public void merge(Collection<ItemValue> values) {
		
		AttendanceItemUtilRes.merge(weeklyAttendance, values, AttendanceItemType.WEEKLY_ITEM);
	}

	@Override
	public WeeklyRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfWeekly domain) {
		
		this.weeklyAttendance = AttendanceTimeOfWeeklyDto.from(domain, this.master);
		return this;
	}

	@Override
	public Optional<AttendanceTimeOfWeekly> toAttendanceTime() {
		
		this.weeklyAttendance.getAnyItem().correctItems(this.master);
		
		return this.weeklyAttendance.toDomain();
	}

}
