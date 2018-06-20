package nts.uk.ctx.at.record.app.find.monthly;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.app.find.monthly.root.AffiliationInfoOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AnnLeaRemNumEachMonthDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AnyItemOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AttendanceTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.RsvLeaRemNumEachMonthDto;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

public class MonthlyRecordToAttendanceItemConverterImpl implements MonthlyRecordToAttendanceItemConverter {

	private final MonthlyRecordWorkDto monthlyRecord;
	
	private MonthlyRecordToAttendanceItemConverterImpl(){
		this.monthlyRecord = new MonthlyRecordWorkDto();
	}
	
	public static MonthlyRecordToAttendanceItemConverter builder() {
		return new MonthlyRecordToAttendanceItemConverterImpl();
	}
	
	@Override
	public Optional<ItemValue> convert(int attendanceItemId) {
		List<ItemValue> items = AttendanceItemUtil.toItemValues(
				this.monthlyRecord, Arrays.asList(attendanceItemId), AttendanceItemType.MONTHLY_ITEM);
		return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
	}
	
	@Override
	public List<ItemValue> convert(Collection<Integer> attendanceItemIds) {
		return AttendanceItemUtil.toItemValues(this.monthlyRecord, attendanceItemIds, AttendanceItemType.MONTHLY_ITEM);
	}
	
	@Override
	public void merge(ItemValue value) {
		AttendanceItemUtil.fromItemValues(this.monthlyRecord, Arrays.asList(value), AttendanceItemType.MONTHLY_ITEM);
	}
	
	@Override
	public void merge(Collection<ItemValue> values) {
		AttendanceItemUtil.fromItemValues(this.monthlyRecord, values, AttendanceItemType.MONTHLY_ITEM);
	}
	
	@Override
	public MonthlyRecordToAttendanceItemConverter withAffiliation(AffiliationInfoOfMonthly domain) {
		this.monthlyRecord.withAffiliation(AffiliationInfoOfMonthlyDto.from(domain));
		return this;
	}
	
	@Override
	public MonthlyRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfMonthly domain) {
		this.monthlyRecord.withAttendanceTime(AttendanceTimeOfMonthlyDto.from(domain));
		return this;
	}
	
	@Override
	public MonthlyRecordToAttendanceItemConverter withAnyItem(List<AnyItemOfMonthly> domains) {
		this.monthlyRecord.withAnyItem(AnyItemOfMonthlyDto.from(domains));
		return this;
	}
	
	@Override
	public MonthlyRecordToAttendanceItemConverter withAnnLeave(AnnLeaRemNumEachMonth domain) {
		this.monthlyRecord.withAnnLeave(AnnLeaRemNumEachMonthDto.from(domain));
		return this;
	}
	
	@Override
	public MonthlyRecordToAttendanceItemConverter withRsvLeave(RsvLeaRemNumEachMonth domain) {
		this.monthlyRecord.withRsvLeave(RsvLeaRemNumEachMonthDto.from(domain));
		return this;
	}
	
	@Override
	public MonthlyRecordToAttendanceItemConverter completed() {
		return this;
	}
	
	@Override
	public Optional<AffiliationInfoOfMonthly> toAffiliation() {
		return Optional.ofNullable(this.monthlyRecord.toAffiliation());
	}
	
	@Override
	public Optional<AttendanceTimeOfMonthly> toAttendanceTime() {
		return Optional.ofNullable(this.monthlyRecord.toAttendanceTime());
	}
	
	@Override
	public List<AnyItemOfMonthly> toAnyItems() {
		return this.monthlyRecord.toAnyItems();
	}
	
	@Override
	public Optional<AnnLeaRemNumEachMonth> toAnnLeave() {
		return Optional.ofNullable(this.monthlyRecord.toAnnLeave());
	}
	
	@Override
	public Optional<RsvLeaRemNumEachMonth> toRsvLeave() {
		return Optional.ofNullable(this.monthlyRecord.toRsvLeave());
	}
}
