package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

/**
 * 月別実績と勤怠項目の相互変換
 * @author shuichu_ishida
 */
public interface MonthlyRecordToAttendanceItemConverter {

	Optional<ItemValue> convert(int attendanceItemId);

	List<ItemValue> convert(Collection<Integer> attendanceItemIds);
	
	void merge(ItemValue value);
	
	void merge(Collection<ItemValue> values);

	IntegrationOfMonthly toDomain();
	
	MonthlyRecordToAttendanceItemConverter setData(IntegrationOfMonthly domain);
	
	MonthlyRecordToAttendanceItemConverter withAffiliation(AffiliationInfoOfMonthly domain);

	MonthlyRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfMonthly domain);

	MonthlyRecordToAttendanceItemConverter withAnyItem(List<AnyItemOfMonthly> domains);

	MonthlyRecordToAttendanceItemConverter withAnnLeave(AnnLeaRemNumEachMonth domain);

	MonthlyRecordToAttendanceItemConverter withRsvLeave(RsvLeaRemNumEachMonth domain);

	MonthlyRecordToAttendanceItemConverter completed();
	
	Optional<AffiliationInfoOfMonthly> toAffiliation();
	
	Optional<AttendanceTimeOfMonthly> toAttendanceTime();
	
	List<AnyItemOfMonthly> toAnyItems();
	
	Optional<AnnLeaRemNumEachMonth> toAnnLeave();
	
	Optional<RsvLeaRemNumEachMonth> toRsvLeave();
}
