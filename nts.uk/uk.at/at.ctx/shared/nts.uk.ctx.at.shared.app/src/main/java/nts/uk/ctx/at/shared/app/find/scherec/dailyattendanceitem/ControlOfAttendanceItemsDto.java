package nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;

@Getter
@Setter
public class ControlOfAttendanceItemsDto {
	

	/**会社ID*/
	private String companyID;
	
	/**勤怠項目ID*/
	private int itemDailyID;
	
	/**日別実績のヘッダ背景色*/
	private String headerBgColorOfDailyPer;

	/**時間項目の入力単位*/
	private Integer inputUnitOfTimeItem;
	
	public ControlOfAttendanceItemsDto(String companyID, int itemDailyID, String headerBgColorOfDailyPer, Integer inputUnitOfTimeItem) {
		super();
		this.companyID = companyID;
		this.itemDailyID = itemDailyID;
		this.headerBgColorOfDailyPer = headerBgColorOfDailyPer;
		this.inputUnitOfTimeItem = inputUnitOfTimeItem;
	}
	
	public static ControlOfAttendanceItemsDto fromDomain(ControlOfAttendanceItems domain) {
		return new ControlOfAttendanceItemsDto(
				domain.getCompanyID(),
				domain.getItemDailyID(),
				!domain.getHeaderBgColorOfDailyPer().isPresent() ?null:domain.getHeaderBgColorOfDailyPer().get().v(),
				!domain.getInputUnitOfTimeItem().isPresent()?null: domain.getInputUnitOfTimeItem().get().value
				);
	}
}
