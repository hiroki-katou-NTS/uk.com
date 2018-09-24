package nts.uk.ctx.at.record.app.find.monthly.root;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.dom.monthly.remarks.RecordRemarks;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/** 月別実績の備考 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_REMARKS_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class MonthlyRemarksDto extends MonthlyItemCommon {
	
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月: 年月 */
	private YearMonth ym;

	/** 締めID: 締めID */
	// @AttendanceItemValue
	// @AttendanceItemLayout(jpPropertyName = "締めID", layout = "A")
	private int closureID = 1;

	/** 締め日: 日付 */
	// @AttendanceItemLayout(jpPropertyName = "締め日", layout = "B")
	private ClosureDateDto closureDate;

	/** 締め期間: 期間 */
	@AttendanceItemLayout(jpPropertyName = PERIOD, layout = LAYOUT_A)
	private DatePeriodDto datePeriod;
	
	/**	備考 */
	@AttendanceItemLayout(jpPropertyName = REMARK, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TEXT)
	private String remarks;	
	
	private int no;
	
	@Override
	public String employeeId() {
		return employeeId;
	}
	@Override
	public RemarksMonthlyRecord toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		return new RemarksMonthlyRecord(employeeId, ConvertHelper.getEnum(closureID, ClosureId.class), no, ym, 
										datePeriod == null ? null : datePeriod.toDomain(), 
										remarks == null ? null : new RecordRemarks(remarks), 
										closureDate == null ? null : closureDate.toDomain());
	}
	@Override
	public YearMonth yearMonth() {
		return ym;
	}
	
	public static MonthlyRemarksDto from(RemarksMonthlyRecord domain){
		MonthlyRemarksDto dto = new MonthlyRemarksDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYm(domain.getRemarksYM());
			dto.setClosureID(domain.getClosuteId().value);
			dto.setClosureDate(ClosureDateDto.from(domain.getClosureDate()));
			dto.setDatePeriod(DatePeriodDto.from(domain.getRemarksPeriod()));
			dto.setRemarks(domain.getRecordRemarks() == null ? null : domain.getRecordRemarks().v());
			dto.setNo(domain.getRemarksNo());
			dto.exsistData();
		}
		return dto;
	}
}