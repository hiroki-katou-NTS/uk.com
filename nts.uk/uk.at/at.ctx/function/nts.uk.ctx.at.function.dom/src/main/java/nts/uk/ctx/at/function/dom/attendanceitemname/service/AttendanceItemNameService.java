package nts.uk.ctx.at.function.dom.attendanceitemname.service;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendanceitemframelinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.FormCanUsedForTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;

public interface AttendanceItemNameService {
	/**
	 *  勤怠項目に対応する名称を生成する
	 * @param dailyAttendanceItemIds 勤怠項目ID
	 * @param type 勤怠項目の種類
	 * @return
	 */
	List<AttItemName> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItem type);
	
	List<AttItemName> getNameOfAttendanceItem(String cid, List<Integer> attendanceItemIds, TypeOfItem type);
	
	List<AttItemName> getNameOfAttendanceItem(TypeOfItem type);

	List<AttItemName> getNameOfAttendanceItem(TypeOfItem type, List<AttItemName> attendanceItems);

	List<AttItemName> getNameOfAttendanceItem(List<AttItemName> attendanceItems,
			List<AttendanceItemLinking> attendanceItemAndFrameNos);
	
	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.使用不可の勤怠項目を除く.使用不可の勤怠項目を除く  Xóa attendance item không thể sử dụng
	 *
	 * @param companyId : 会社ID
	 * @param type: 勤怠項目の種類（0:スケジュール、1:日次、2:月次、3:週次、4:任意期間）
	 * @param attendanceItemIds List<勤怠項目ID>
	 * @return List＜使用可能な勤怠項目ID＞
	 */
	List<Integer> getAvaiableAttendanceItem(String companyId, TypeOfItem type, List<Integer> attendanceItemIds);
	
	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.画面で使用可能な月次勤怠項目を取得する.画面で使用可能な月次勤怠項目を取得する
	 * 
	 * @param companyId 会社ID
	 * @param formId 帳票ID
	 * @param type 勤怠項目の種類
	 * @return the monthly attendance items avaiable
	 */
	public List<Integer> getMonthlyAttendanceItemsAvaiable(String companyId, FormCanUsedForTime formId, TypeOfItem type);
	
	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.画面で使用可能な日次勤怠項目を取得する.画面で使用可能な日次勤怠項目を取得する
	 *
	 * @param companyId 会社ID
	 * @param formId 帳票ID
	 * @param type 勤怠項目の種類
	 * @return the daily attendance items avaiable
	 */
	public List<Integer> getDailyAttendanceItemsAvaiable(String companyId, FormCanUsedForTime formId, TypeOfItem type);

	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.日次勤怠項目に対応する名称、属性を取得する.日次勤怠項目に対応する名称、属性を取得する
	 * @param companyId
	 * @param attendanceIdList
	 * @return the daily attendance item name and attribute
	 */
	public List<AttendanceItemDto> getDailyAttendanceItemNameAndAttr(String companyId, List<Integer> attendanceIdList);

	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.月次勤怠項目に対応する名称、属性を取得する.月次勤怠項目に対応する名称、属性を取得する
	 * @param companyId
	 * @param attendanceIdList
	 * @return list attribute of attendance item
	 */
	public List<AttendanceItemDto> getMonthlyAttendanceItemNameAndAttr(String companyId, List<Integer> attendanceIdList);
}
