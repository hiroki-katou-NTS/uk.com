package nts.uk.ctx.at.record.app.command.dailyperform.correctevent;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.dailyperform.checkdata.DailyModifyRCResult;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtilRes;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.enu.DailyDomainGroup;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

@NoArgsConstructor
@Getter
public class EventCorrectResult {

	private DailyRecordDto base;
	
	@Setter
	private DailyRecordDto corrected;
	
	private DailyModifyRCResult updated;
	
	private List<DailyDomainGroup> correctedType;
	
	public List<ItemValue> getCanBeCorrectedItems(){
		List<Integer> canBeUpdated = AttendanceItemIdContainer.getItemIdByDailyDomains(correctedType);
		
		return AttendanceItemUtilRes.collect(corrected, canBeUpdated, AttendanceItemType.DAILY_ITEM);
	}
	
	public List<ItemValue> getCorrectedItemsWithStrict(){
		List<Integer> canBeUpdated = AttendanceItemIdContainer.getItemIdByDailyDomains(correctedType, (type, path) -> {
			if(type == DailyDomainGroup.BREAK_TIME) {
				return path.contains(ItemConst.E_WORK_REF);
			}
			
			return true;
		});
		
		List<ItemValue> canBeCorrected = AttendanceItemUtilRes.collect(corrected, canBeUpdated, AttendanceItemType.DAILY_ITEM);
		List<ItemValue> beforeCorrect = AttendanceItemUtilRes.collect(base, canBeUpdated, AttendanceItemType.DAILY_ITEM);
		
		canBeCorrected.removeAll(beforeCorrect);
		
		return canBeCorrected;
	}

	public EventCorrectResult(DailyRecordDto base, DailyRecordDto corrected, DailyModifyRCResult updated,
			List<DailyDomainGroup> correctedType) {
		super();
		this.base = base;
		this.corrected = corrected;
		this.updated = updated;
		this.correctedType = correctedType;
	}
	
	public void removeEditStatesForCorrectedItem() {
		List<Integer> canbeCorrected = getCorrectedItemsWithStrict().stream().map(bc -> bc.itemId()).collect(Collectors.toList());
		
		this.corrected.getEditStates().removeIf(es -> canbeCorrected.contains(es.getAttendanceItemId()));
	}
}
