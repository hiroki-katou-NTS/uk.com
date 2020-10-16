package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.dailyperform.remark.dto.RemarkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remarks.RecordRemarks;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/** 月別実績の備考 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_REMARKS_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class MonthlyRemarksDto extends MonthlyItemCommon {

	/***/
	private static final long serialVersionUID = 1L;
	
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

	@AttendanceItemLayout(jpPropertyName = FAKED, layout = LAYOUT_A, 
			listMaxLength = 5, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<RemarkDto> remarks;
	
	@Override
	public String employeeId() {
		return employeeId;
	}
	@Override
	public List<RemarksMonthlyRecord> toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		return ConvertHelper.mapTo(remarks, c -> new RemarksMonthlyRecord(employeeId, ConvertHelper.getEnum(closureID, ClosureId.class), 
										c.getNo(), ym, closureDate == null ? null : closureDate.toDomain(),
										c.getRemark() == null ? null : new RecordRemarks(c.getRemark())));
	}
	@Override
	public YearMonth yearMonth() {
		return ym;
	}
	
	public static MonthlyRemarksDto from(List<RemarksMonthlyRecord> domain){
		MonthlyRemarksDto dto = new MonthlyRemarksDto();
		if (domain != null && !domain.isEmpty()) {
			dto.setEmployeeId(domain.get(0).getEmployeeId());
			dto.setYm(domain.get(0).getRemarksYM());
			dto.setClosureID(domain.get(0).getClosureId().value);
			dto.setClosureDate(ClosureDateDto.from(domain.get(0).getClosureDate()));
			dto.setRemarks(ConvertHelper.mapTo(domain, c -> from(c)));
			
			dto.exsistData();
		}
		return dto;
	}
	
	public static RemarkDto from(RemarksMonthlyRecord domain){
		RemarkDto dto = new RemarkDto();
		
		dto.setRemark(domain.getRecordRemarks() == null ? null : domain.getRecordRemarks().v());
		dto.setNo(domain.getRemarksNo());
		
		return dto;
	}
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (FAKED.equals(path)) {
			return new RemarkDto();
		}
		return super.newInstanceOf(path);
	}
	@Override
	public int size(String path) {
		if (FAKED.equals(path)) {
			return 5;
		}
		return super.size(path);
	}
	@Override
	public PropType typeOf(String path) {
		if (FAKED.equals(path)) {
			return PropType.VALUE;
		}
		return super.typeOf(path);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (FAKED.equals(path)) {
			return (List<T>) remarks;
		}
		return super.gets(path);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (FAKED.equals(path)) {
			remarks = (List<RemarkDto>) value;
		}
	}
	@Override
	public boolean isRoot() {
		return true;
	}
	@Override
	public String rootName() {
		return MONTHLY_REMARKS_NAME;
	}


}
