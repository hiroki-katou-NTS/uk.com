/**
 * 
 */
package nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

/**
 * @author laitv
 */
@Data
@NoArgsConstructor
/** 作業内容 */
public class WorkContentDto implements  ItemConst, AttendanceItemDataGate {
	
	
	/** 勤務先: 応援別勤務の勤務先 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = WORKPLACE_BYSUPPORT)
	private WorkplaceOfWorkEachOuenDto workplace;
	
	/** 作業: 作業グループ */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = WORKGROUP)
	private WorkGroupDto work;
	
	/** 備考: 作業入力備考 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = WORKREMARKS)
	private String workRemarks;
	
	/** 作業: 作業グループ */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = WORKGROUP)
	private Optional<WorkGroupDto> workOpt;
	
	
	@Override
	public WorkContentDto clone() {
		WorkContentDto result = new WorkContentDto();
		result.setWorkplace(workplace == null ? null : workplace.clone());
		result.setWork(work == null ? null : work.clone());
		result.setWorkRemarks(workRemarks);
		return result;
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case WORKPLACE_BYSUPPORT:
			return new WorkplaceOfWorkEachOuenDto();
		case WORKGROUP:
			return new WorkGroupDto();
		default:
			return null;
		}
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case WORKREMARKS :
			return Optional.of(ItemValue.builder().value(workRemarks).valueType(ValueType.CODE));
		default:
			return Optional.empty();
		}
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case WORKPLACE_BYSUPPORT:
			workplace = (WorkplaceOfWorkEachOuenDto) value;
			break;
		case WORKGROUP:
			work = (WorkGroupDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case WORKREMARKS:
			this.workRemarks = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case WORKPLACE_BYSUPPORT:
			return Optional.ofNullable(workplace);
		case WORKGROUP:
			return Optional.ofNullable(work);
		default:
			return Optional.empty();
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case WORKPLACE_BYSUPPORT:
		case WORKGROUP:
			return PropType.OBJECT;
		case WORKREMARKS:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	public WorkContentDto(WorkplaceOfWorkEachOuenDto workplace, WorkGroupDto work, String workRemarks) {
		super();
		this.workplace = workplace;
		this.work = work;
		this.workRemarks = workRemarks;
	}

	public WorkContentDto(WorkplaceOfWorkEachOuenDto workplace, Optional<WorkGroupDto> workOpt, String workRemarks) {
		super();
		this.workplace = workplace;
		this.workOpt = workOpt;
		this.workRemarks = workRemarks;
	}

}
