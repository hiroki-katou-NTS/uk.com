/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinshift;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.screen.at.app.ksu001.getshiftpalette.PageInfo;
import nts.uk.screen.at.app.ksu001.getshiftpalette.TargetShiftPalette;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftDto;

/**
 * @author laitv
 *
 */
@Value
public class DisplayInShiftResult {
	
	public List<PageInfo> listPageInfo ;                      // List<ページ, 名称>
	public Optional<TargetShiftPalette> targetShiftPalette ; // 対象のシフトパレット： Optional<ページ, シフトパレット>
	public List<WorkScheduleShiftDto> listWorkScheduleShift;  // List<勤務予定（シフト）dto>
	public List<PageShift> listOfPageShift;                   // 取得したシフト一覧： List<シフトマスタ, 出勤休日区分>
	
}
