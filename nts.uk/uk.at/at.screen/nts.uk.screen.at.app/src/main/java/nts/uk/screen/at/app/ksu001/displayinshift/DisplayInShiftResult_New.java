package nts.uk.screen.at.app.ksu001.displayinshift;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ksu001.aggreratepersonaltotal.AggreratePersonalDto;
import nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal.AggrerateWorkplaceDto;
import nts.uk.screen.at.app.ksu001.getshiftpalette.PageInfo;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;
import nts.uk.screen.at.app.ksu001.getshiftpalette.TargetShiftPalette;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisplayInShiftResult_New {
	// data cua shift pallet
	public List<PageInfo> listPageInfo ;                      // List<ページ, 名称>
	public TargetShiftPalette targetShiftPalette ; // 対象のシフトパレット： Optional<ページ, シフトパレット>
//	public List<ShiftMasterMapWithWorkStyle> shiftMasterWithWorkStyleLst;  // 取得したシフト一覧： List<シフトマスタ, 出勤休日区分> ==> thằng này sẽ lưu vào localStorage
	 
	// data cua Grid 
	public List<ScheduleOfShiftDto> listWorkScheduleShift;  // List<勤務予定（シフト）dto> ==> data hiển thị trên grid
	
	
	// Map<シフトマスタ, Optional<出勤休日区分>>
	public Map<ShiftMasterDto, Integer> mapShiftMasterWithWorkStyle; 
	
	// 個人計集計結果　←集計内容によって情報が異なる
	public AggreratePersonalDto aggreratePersonal;
	
	// ・職場計集計結果　←集計内容によって情報が異なる
	public AggrerateWorkplaceDto aggrerateWorkplace;
	
}
