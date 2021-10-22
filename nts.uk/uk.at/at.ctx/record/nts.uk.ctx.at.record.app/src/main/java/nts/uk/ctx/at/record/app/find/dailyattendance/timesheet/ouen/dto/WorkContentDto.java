/**
 * 
 */
package nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;

/**
 * @author laitv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/** 作業内容 */
public class WorkContentDto implements  ItemConst, AttendanceItemDataGate {
	
		/** 勤務先: 応援別勤務の勤務先 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = WORKPLACE_BYSUPPORT)
	private WorkplaceOfWorkEachOuenDto workplace;
	
	
	/** 作業: 作業グループ */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = WORKGROUP)
	private Optional<WorkGroupDto> workOpt;

	/** 作業補足情報 */
	private Optional<WorkSuppInfoDto> workSuppInfo;
	
	public static WorkContentDto from(WorkContent domain) {
		if (domain == null) return null;
	
		return new WorkContentDto(
					WorkplaceOfWorkEachOuenDto.from(domain.getWorkplace()),
					WorkGroupDto.from(domain.getWork().orElse(null)),
					WorkSuppInfoDto.from(domain.getWorkSuppInfo().orElse(null)));
	}
	
	public WorkContent domain() {
		return WorkContent.create(
				workplace == null ? WorkplaceOfWorkEachOuen.create(new WorkplaceId(""), null) : workplace.domain(), 
				workOpt,
				Optional.empty(),
				workSuppInfo); 
	} 
	
	@Override
	public WorkContentDto clone() {
		WorkContentDto result = new WorkContentDto();
		result.setWorkplace(workplace == null ? null : workplace.clone());
		result.setWork(!workOpt.isPresent() ? null : workOpt.get().clone());
		result.setWorkSuppInfo(!workSuppInfo.isPresent() ? null : workSuppInfo.get().clone());
		return result;
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case WORKPLACE_BYSUPPORT:
			return new WorkplaceOfWorkEachOuenDto();
		case WORKGROUP:
			return new WorkGroupDto();
		case SUPP:
			return new WorkSuppInfoDto();
		default:
			return null;
		}
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case WORKPLACE_BYSUPPORT:
			workplace = (WorkplaceOfWorkEachOuenDto) value;
			break;
		case WORKGROUP:
			workOpt = Optional.ofNullable((WorkGroupDto) value);
			break;
		case SUPP:
			workSuppInfo = Optional.ofNullable((WorkSuppInfoDto) value);
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
			return workOpt;
		case SUPP:
			return workSuppInfo;
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
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

}
