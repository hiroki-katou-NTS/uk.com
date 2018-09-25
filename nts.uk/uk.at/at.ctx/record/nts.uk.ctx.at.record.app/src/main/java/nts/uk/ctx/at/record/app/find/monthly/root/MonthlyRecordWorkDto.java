package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdRemain;
import nts.uk.ctx.at.record.dom.monthly.information.childnursing.MonChildHdRemain;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;

@Data
/** 月別実績（WORK） */
@AttendanceItemRoot(isContainer = true, itemType = AttendanceItemType.MONTHLY_ITEM)
public class MonthlyRecordWorkDto extends MonthlyItemCommon {

	/** 年月: 年月 */
	private YearMonth yearMonth;

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 締めID: 締めID */
	private int closureID;

	/** 締め日: 日付 */
	private ClosureDateDto closureDate;

	/** 月別実績の所属情報: 月別実績の所属情報 */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_AFFILIATION_INFO_NAME, layout = MONTHLY_AFFILIATION_INFO_CODE)
	private AffiliationInfoOfMonthlyDto affiliation;

	/** 月別実績の勤怠時間: 月別実績の勤怠時間 */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_ATTENDANCE_TIME_NAME, layout = MONTHLY_ATTENDANCE_TIME_CODE)
	private AttendanceTimeOfMonthlyDto attendanceTime;

	/** 月別実績の任意項目 */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_OPTIONAL_ITEM_NAME, layout = MONTHLY_OPTIONAL_ITEM_CODE)
	private AnyItemOfMonthlyDto anyItem;
	
	/** 年休月別残数データ: 年休月別残数データ */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_ANNUAL_LEAVING_REMAIN_NAME, layout = MONTHLY_ANNUAL_LEAVING_REMAIN_CODE)
	private AnnLeaRemNumEachMonthDto annLeave;

	/** 積立年休月別残数データ: 積立年休月別残数データ */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_RESERVE_LEAVING_REMAIN_NAME, layout = MONTHLY_RESERVE_LEAVING_REMAIN_CODE)
	private RsvLeaRemNumEachMonthDto rsvLeave;

	/** 特別休暇月別残数データ: 特別休暇残数月別データ */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_SPECIAL_HOLIDAY_REMAIN_NAME, layout = MONTHLY_SPECIAL_HOLIDAY_REMAIN_CODE, 
			listMaxLength = 20, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<SpecialHolidayRemainDataDto> specialHoliday = new ArrayList<>();

	/** 代休月別残数データ: 代休月別残数データ */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_OFF_REMAIN_NAME, layout = MONTHLY_OFF_REMAIN_CODE)
	private MonthlyDayoffRemainDataDto dayOff;

	/** 振休月別残数データ: 振休月別残数データ */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_ABSENCE_LEAVE_REMAIN_NAME, layout = MONTHLY_ABSENCE_LEAVE_REMAIN_CODE)
	private AbsenceLeaveRemainDataDto absenceLeave;
	
	/** 月別実績の備考: 月別実績の備考 */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_REMARKS_NAME, layout = MONTHLY_REMARKS_CODE, 
			listMaxLength = 5, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<MonthlyRemarksDto> remarks = new ArrayList<>();
	
	/** 介護休暇月別残数データ: 介護休暇月別残数データ */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_CARE_HD_REMAIN_NAME, layout = MONTHLY_CARE_HD_REMAIN_CODE)
	private MonthlyCareHdRemainDto care;
	
	/** 子の看護月別残数データ: 子の看護月別残数データ */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_CHILD_CARE_HD_REMAIN_NAME, layout = MONTHLY_CHILD_CARE_HD_REMAIN_CODE)
	private MonthlyChildCareHdRemainDto childCare;
	
	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public YearMonth yearMonth() {
		return this.yearMonth;
	}

	public MonthlyRecordWorkDto employeeId(String employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public MonthlyRecordWorkDto yearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
		return this;
	}

	public MonthlyRecordWorkDto closureID(int closureID) {
		this.closureID = closureID;
		return this;
	}

	public MonthlyRecordWorkDto closureDate(ClosureDateDto closureDate) {
		this.closureDate = closureDate;
		return this;
	}
	
	public static MonthlyRecordWorkDto builder(){
		return new MonthlyRecordWorkDto();
	}
	
	public MonthlyRecordWorkDto withAffiliation(AffiliationInfoOfMonthlyDto affiliation){
		this.affiliation = affiliation;
		return this;
	}
	
	public MonthlyRecordWorkDto withAttendanceTime(AttendanceTimeOfMonthlyDto attendanceTime){
		this.attendanceTime = attendanceTime;
		return this;
	}
	
	public MonthlyRecordWorkDto withAnyItem(AnyItemOfMonthlyDto anyItem){
		this.anyItem = anyItem;
		return this;
	}
	
	public MonthlyRecordWorkDto withAnnLeave(AnnLeaRemNumEachMonthDto annLeave){
		this.annLeave = annLeave;
		return this;
	}
	
	public MonthlyRecordWorkDto withRsvLeave(RsvLeaRemNumEachMonthDto rsvLeave){
		this.rsvLeave = rsvLeave;
		return this;
	}
	
	public MonthlyRecordWorkDto withSpecialHoliday(List<SpecialHolidayRemainDataDto> specialHoliday){
		this.specialHoliday = new ArrayList<>(specialHoliday);
		return this;
	}
	
	public MonthlyRecordWorkDto withDayOff(MonthlyDayoffRemainDataDto dayOff){
		this.dayOff = dayOff;
		return this;
	}
	
	public MonthlyRecordWorkDto withAbsenceLeave(AbsenceLeaveRemainDataDto absenceLeave){
		this.absenceLeave = absenceLeave;
		return this;
	}
	
	public MonthlyRecordWorkDto withRemarks(List<MonthlyRemarksDto> remarks){
		this.remarks = remarks;
		return this;
	}
	
	public MonthlyRecordWorkDto withCare(MonthlyCareHdRemainDto care){
		this.care = care;
		return this;
	}
	
	public MonthlyRecordWorkDto withChildCare(MonthlyChildCareHdRemainDto childCare){
		this.childCare = childCare;
		return this;
	}
	
	public AffiliationInfoOfMonthly toAffiliation(){
		return this.affiliation == null ? null : this.affiliation.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}
	
	public AttendanceTimeOfMonthly toAttendanceTime(){
		return this.attendanceTime == null ? null : this.attendanceTime.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}
	
	public List<AnyItemOfMonthly> toAnyItems(){
		return this.anyItem.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}
	
	public AnnLeaRemNumEachMonth toAnnLeave(){
		return this.annLeave == null ? null : this.annLeave.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}
	
	public RsvLeaRemNumEachMonth toRsvLeave(){
		return this.rsvLeave == null ? null : this.rsvLeave.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}
	
	public List<SpecialHolidayRemainData> toSpecialHoliday(){
		return this.specialHoliday.stream().map(s -> s.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate())).collect(Collectors.toList());
	}
	
	public MonthlyDayoffRemainData toDayOff(){
		return this.dayOff == null ? null : this.dayOff.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}
	
	public AbsenceLeaveRemainData toAbsenceLeave(){
		return this.absenceLeave == null ? null :  this.absenceLeave.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}
	
	public List<RemarksMonthlyRecord> toRemarks(){
		return this.remarks == null ? null :  this.remarks.stream().map(r -> r.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate())).collect(Collectors.toList());
	}
	
	public MonCareHdRemain toMonCare(){
		return this.care == null ? null :  this.care.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}
	
	public MonChildHdRemain toMonChildCare(){
		return this.childCare == null ? null :  this.childCare.toDomain(getEmployeeId(), getYearMonth(), getClosureID(), getClosureDate());
	}

	@Override
	public IntegrationOfMonthly toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		if (this.attendanceTime == null) return new IntegrationOfMonthly();
		return new IntegrationOfMonthly(
				Optional.ofNullable(this.attendanceTime == null ? null : this.attendanceTime.toDomain(employeeId, ym, closureID, closureDate)),
				Optional.ofNullable(this.affiliation == null ? null : this.affiliation.toDomain(employeeId, ym, closureID, closureDate)),
				this.anyItem.toDomain(employeeId, ym, closureID, closureDate),
				Optional.empty(),
				Optional.ofNullable(this.annLeave == null ? null : this.annLeave.toDomain(employeeId, ym, closureID, closureDate)),
				Optional.ofNullable(this.rsvLeave == null ? null : this.rsvLeave.toDomain(employeeId, ym, closureID, closureDate)),
				Optional.ofNullable(this.absenceLeave == null ? null : this.absenceLeave.toDomain(employeeId, ym, closureID, closureDate)),
				Optional.ofNullable(this.dayOff == null ? null : this.dayOff.toDomain(employeeId, ym, closureID, closureDate)),
				this.specialHoliday.stream().map(s -> s.toDomain(employeeId, ym, closureID, closureDate)).collect(Collectors.toList()),
				this.remarks.stream().map(s -> s.toDomain(employeeId, ym, closureID, closureDate)).collect(Collectors.toList()),
				Optional.ofNullable(this.care == null ? null : this.care.toDomain(employeeId, ym, closureID, closureDate)),
				Optional.ofNullable(this.childCare == null ? null : this.childCare.toDomain(employeeId, ym, closureID, closureDate)));
	}
}
