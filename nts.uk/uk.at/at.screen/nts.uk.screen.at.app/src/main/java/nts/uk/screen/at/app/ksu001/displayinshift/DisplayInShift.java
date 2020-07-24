/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinshift;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.ksu001.getschedulesbyshift.SchedulesbyShiftParam;
import nts.uk.screen.at.app.ksu001.getshiftpalette.GetShiftPalette;
import nts.uk.screen.at.app.ksu001.getshiftpalette.GetShiftPaletteParam;
import nts.uk.screen.at.app.ksu001.getshiftpalette.GetShiftPaletteResult;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.GetSchedulesAndAchievementsByShift;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.SchedulesbyShiftDataResult;

/**
 * @author laitv 
 * <<ScreenQuery>> シフトで表示する 
 * 
 * 【Input】 Excelの項目移送表参照
 * 【Output】 
 * ・List<ページ, 名称> 
 * ※シフトパレット
 * ・対象ページのシフトパレット
 * ・List<シフトマスタ, 出勤休日区分>
 * ・List<勤務予定（シフト）dto>
 *
 * 
 */
@Stateless
public class DisplayInShift {
	
	@Inject
	private GetShiftPalette getShiftPalette;
	@Inject
	private GetSchedulesAndAchievementsByShift getScheduleByShift;
	
	public DisplayInShiftResult getData(DisplayInShiftParam param) {
		
		// khởi tạo param để truyền vào ScreenQuery シフトパレットを取得する
		GetShiftPaletteParam paramOfGetShiftPalette = new GetShiftPaletteParam(param.listShiftMasterNotNeedGetNew, param.shiftPaletteWantGet, param.workplaceId, param.workplaceGroupId);
		// gọi ScreenQuery シフトパレットを取得する
		GetShiftPaletteResult shiftPaletteResult = getShiftPalette.getData(paramOfGetShiftPalette); 
		
		// khởi tạo param để truyền vào ScreenQuery 予定・実績をシフトで取得する
		List<ShiftMasterDto> listShiftMasterAfterMerge = param.listShiftMasterNotNeedGetNew; // list này sẽ bao gồm cả ShiftMaster đã lưu dưới localStorage và ShiftMaster mới lấy.
		List<ShiftMasterDto> listShiftMasterGetNew = shiftPaletteResult.getListOfShift().stream().map(i -> i.shiftMaster).collect(Collectors.toList()); // list ShiftMaster mới lấy.
		List<ShiftMasterDto> listShiftMasterFromUI = param.listShiftMasterNotNeedGetNew; // list ShiftMaster mới lấy.
		for (ShiftMasterDto shiftMasterDtoNew : listShiftMasterGetNew) {
			if (!listShiftMasterFromUI.contains(shiftMasterDtoNew)) {
				listShiftMasterAfterMerge.add(shiftMasterDtoNew); // chỉ thêm vào những ShiftMaster chưa tồn tại trong list ShiftMaster dưới localStorage
			}
		}
		SchedulesbyShiftParam paramOfSchedulesbyshift = new SchedulesbyShiftParam(param.listSid, param.startDate, param.endDate, listShiftMasterAfterMerge, param.getActualData);
		// gọi ScreenQuery 勤務予定（シフト）を取得する
		SchedulesbyShiftDataResult schedulesbyShiftData = getScheduleByShift.getData(paramOfSchedulesbyshift);
		
		
		return null;
		
		
	}
}
