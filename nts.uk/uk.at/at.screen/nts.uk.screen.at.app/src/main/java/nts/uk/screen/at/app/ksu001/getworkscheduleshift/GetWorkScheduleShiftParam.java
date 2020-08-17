/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;

/**
 * @author laitv
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetWorkScheduleShiftParam {
	
	public List<ShiftMasterDto> listShiftMasterNotNeedGetNew; // ・新たに取得する必要のないシフト一覧：List<シフトマスタ>
	public List<String> listSid;             // ・社員IDリスト：List<社員ID>
	public GeneralDate startDate;            // ・期間
	public GeneralDate endDate;    	         // ・期間
	
} 
