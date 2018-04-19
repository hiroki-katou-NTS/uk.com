package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

// 日別勤務表の出力項目
@Getter
public class OutputItemDailyWorkSchedule extends AggregateRoot{
	// コード
	private OutputItemSettingCode itemCode;
	
	// 名称
	private OutputItemSettingName itemName;
	
	// 表示する勤怠項目
	private List<TimeItemTobeDisplay> lstDisplayedAttendance;
	
	// 備考内容
	private List<PrintRemarksContent> lstRemarkContent;
	
	// 勤務種類・就業時間帯の名称
	private NameWorkTypeOrHourZone zoneName;
	
	public OutputItemDailyWorkSchedule(OutputItemDailyWorkScheduleGetMemento memento) {
		this.itemCode = memento.getItemCode();
		this.itemName = memento.getItemName();
		this.lstDisplayedAttendance = memento.getLstDisplayedAttendance();
		this.lstRemarkContent = memento.getLstRemarkContent();
	}
	
	public void saveToMemento(OutputItemDailyWorkScheduleSetMemento memento) {
		memento.setItemCode(this.itemCode);
		memento.setItemName(this.itemName);
		memento.setLstDisplayedAttendance(this.lstDisplayedAttendance);
		memento.setLstRemarkContent(this.lstRemarkContent);
	}
}
