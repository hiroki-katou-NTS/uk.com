package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeObjAppHolidayCommand {
//	申請別対象勤務種類
	List<String> workTypeList;
	
//	申請種類
	private int appType;
	
//	表示する勤務種類を設定する
	private Boolean workTypeSetDisplayFlg;
	
//	休暇申請種類
	private Integer holidayAppType;
	
//	休暇種類を利用しない
	private Integer holidayTypeUseFlg;
	
//	振休振出区分
	private Integer swingOutAtr;

	private Integer businessTripAtr;
}
