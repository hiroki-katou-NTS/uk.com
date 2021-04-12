package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.MonthlyAttendanceItemNameDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト.週次のアラームリストのチェック条件.アルゴリズム.週次の集計処理.週次の勤怠項目を取得する
 *
 */
@Stateless
public class WeeklyAttendanceItemService {
	@Inject
	private AttendanceItemNameAdapter attendanceAdap;
	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;
	
	/**
	 * 週次の勤怠項目を取得する
	 * @param cid 会社ID
	 * @return List＜勤怠項目ID、勤怠項目名称＞
	 */
	public Map<Integer, String> getAttendanceItem(String cid) {
		// 勤怠項目の種類　＝　週次
		int typeOfAttendanceItem = TypeOfItemImport.Weekly.value;
		
		// 画面で利用できる勤怠項目一覧を取得する TODO need QA confirm method
		// Input: 勤怠項目の種類　＝　3 週次
		// Output: 画面で利用できる勤怠項目一覧
		List<MonthlyAttendanceItemNameDto> attendanceItemNames = attendanceAdap.getMonthlyAttendanceItem(typeOfAttendanceItem);
		
		// 勤怠項目ID　＝　取得した「画面で利用できる勤怠項目一覧」．勤怠項目ID  (Attendance item ID)
		List<Integer> attendanceIds = attendanceItemNames.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList());
		
		// 勤怠項目に対応する名称を生成する  TODO need QA confirm method
		List<AttItemName> lstName = companyMonthlyItemService.getMonthlyItems(cid, Optional.empty(), attendanceIds, null);
		
		return lstName.stream().collect(Collectors.toMap(AttItemName::getAttendanceItemId, AttItemName::getAttendanceItemName));	
	}
}
