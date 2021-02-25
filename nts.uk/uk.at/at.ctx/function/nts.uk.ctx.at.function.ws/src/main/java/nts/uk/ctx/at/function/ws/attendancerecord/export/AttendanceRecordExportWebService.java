package nts.uk.ctx.at.function.ws.attendancerecord.export;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceIdItemDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceIdItemFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceRecordExportDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceRecordExportFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.item.ApprovalProcessingUseSettingDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.item.ApprovalProcessingUseSettingFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.item.AttendanceRecordKeyDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.item.CalculateAttendanceRecordDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.item.CalculateAttendanceRecordFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.item.SingleAttendanceRecordDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.item.SingleAttendanceRecordFinder;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceRecordExportWebService.
 */
@Path("com/function/attendancerecord/export")
@Produces("application/json")
public class AttendanceRecordExportWebService {

	/** The attendance rec exp finder. */
	@Inject
	AttendanceRecordExportFinder attendanceRecExpFinder;

	/** The attendance item finder. */
	@Inject
	AttendanceIdItemFinder attendanceItemFinder;
	
	/** The approval processing use setting repository */
	@Inject
	private ApprovalProcessingUseSettingFinder approvalProcessingUseSettingFinder;
	
	/** The single attendance record finder. */
	@Inject
	SingleAttendanceRecordFinder singleAttendanceRecordFinder;

	/** The calculate attendance record finder */
	@Inject
	CalculateAttendanceRecordFinder calculateAttendanceRecordFinder;

	/**
	 * Gets the all attendance record export daily.
	 *
	 * @param code
	 *            the code
	 * @return the all attendance record export daily
	 */
	@POST
	@Path("getAllAttendanceRecordDailyExport/{layoutId}")
	public List<AttendanceRecordExportDto> getAllAttendanceRecordExportDaily(@PathParam("layoutId") String layoutId) {
		return this.attendanceRecExpFinder.getAllAttendanceRecordExportDaily(layoutId);
	}

	/**
	 * Gets the all attendance record export monthly.
	 *
	 * @param code
	 *            the code
	 * @return the all attendance record export monthly
	 */
	@POST
	@Path("getAllAttendanceRecordExportMonthly/{layoutId}")
	public List<AttendanceRecordExportDto> getAllAttendanceRecordExportMonthly(@PathParam("layoutId") String layoutId) {
		return this.attendanceRecExpFinder.getAllAttendanceRecordExportMonthly(layoutId);
	}

	/**
	 * Gets the attendance single.
	 *
	 * @return the attendance single
	 */
	@POST
	@Path("getAttendanceListSingle")
	public List<AttendanceIdItemDto> getAttendanceSingle() {
		List<Integer> screenUse = new ArrayList<Integer>();
		screenUse.add(13);
		screenUse.add(14);
		screenUse.add(15);

		return attendanceItemFinder.getAttendanceItem(screenUse,1);

	}
	
	/**
	 * Gets the attendance calculate.
	 *
	 * @return the attendance calculate
	 */
	@POST
	@Path("getAttendanceListCalculate/{type}")
	public List<AttendanceIdItemDto> getAttendanceCalculate(@PathParam("type") int attendanceType) {
		List<Integer> screenUse = new ArrayList<Integer>();
		screenUse.add(16);
		screenUse.add(17);
		screenUse.add(18);

		return attendanceItemFinder.getAttendanceItem(screenUse, attendanceType);

	}
	
	/**
	 * #3803 アルゴリズム「承認処理の利用設定を取得する」を実行する Xử lý thuật toán "Nhận cài đặt sử dụng của quy trình phê duyệt"
	 *
	 * @return 承認処理の利用設定
	 */
	@POST
	@Path("getApprovalProcessingUseSetting")
	public ApprovalProcessingUseSettingDto getApprovalProcessingUseSetting() {
		String companyId = AppContexts.user().companyId();
		return this.approvalProcessingUseSettingFinder.getApprovalProcessingUseSettingDto(companyId);
	}

	/**
	 * Gets the single attendance record info.
	 *
	 * @param attendanceRecordKey
	 *            the attendance record key
	 * @return the single attendance record info
	 */
	@POST
	@Path("getSingleAttendanceRecord")
	public SingleAttendanceRecordDto getSingleAttendanceRecordInfo(AttendanceRecordKeyDto attendanceRecordKey) {
		return this.singleAttendanceRecordFinder.getSingleAttendanceRecord(attendanceRecordKey);
	}
	
	/**
	 * Gets the calculate attendance record dto.
	 *
	 * @param attendanceRecordKey
	 *            the attendance record key
	 * @return the calculate attendance record dto
	 */
	@POST
	@Path("getCalculateAttendanceRecordDto")
	public CalculateAttendanceRecordDto getCalculateAttendanceRecordDto(AttendanceRecordKeyDto attendanceRecordKey) {
		return this.calculateAttendanceRecordFinder.getCalculateAttendanceRecordDto(attendanceRecordKey);
	}
	
	/**
	 * #3803 get daily attendance items
	 * 
	 * @return List＜勤怠項目ID、名称、属性、マスタの種類、表示番号＞
	 */
	@POST
	@Path("getDailyAttendanceItems")
	public List<AttendanceItemDto> getDailyAttendanceItems() {
		return this.attendanceItemFinder.getDailyAttendanceItemAtrs();
	}
	
	/**
	 * #3803 get monthly attendance items
	 * 
	 * @return List＜勤怠項目ID、名称、属性、表示番号＞
	 */
	@POST
	@Path("getMonthlyAttendanceItems")
	public List<AttendanceItemDto> getMonthlyAttendanceItems() {
		return this.attendanceItemFinder.getMonthlyAttendanceItemAtrs();
	}
}
