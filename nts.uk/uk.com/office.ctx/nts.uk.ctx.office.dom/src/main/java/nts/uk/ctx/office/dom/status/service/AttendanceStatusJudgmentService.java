package nts.uk.ctx.office.dom.status.service;

import java.util.Optional;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.status.ActivityStatus;
import nts.uk.ctx.office.dom.status.StatusClassfication;
import nts.uk.ctx.office.dom.status.adapter.AttendanceStateImport;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.在席のステータスの判断.在席のステータスの判断
 *
 */
public class AttendanceStatusJudgmentService {

	private AttendanceStatusJudgmentService() {}

	public static AttendanceAccordActualData getActivityStatus(Required rq, String sid) {

		Optional<GoOutEmployeeInformation> goout = rq.getGoOutEmployeeInformation(sid, GeneralDate.today());
		Optional<ActivityStatus> status = rq.getActivityStatus(sid, GeneralDate.today());

		// 在席状態を取得する
		AttendanceStateImport attendanceState = rq.getAttendace(sid);

		// ステータス分類を判断する
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		if (goout.isPresent() && goout.get().getGoOutTime().lessThanOrEqualTo(now)
				&& goout.get().getComebackTime().greaterThanOrEqualTo(now)) {
			// case 1
			return AttendanceAccordActualData.builder()
					.attendanceState(StatusClassfication.GO_OUT)
					.workingNow(attendanceState.isWorkingNow())
					.build();
		}

		if (attendanceState.getAttendanceState() == StatusClassfication.GO_HOME) {
			// case 2
			return AttendanceAccordActualData.builder()
					.attendanceState(attendanceState.getAttendanceState())
					.workingNow(attendanceState.isWorkingNow())
					.build();
		}

		if (status.isPresent()) {
			if(status.get().getActivity()  == StatusClassfication.GO_OUT) {
				// case 3
				return AttendanceAccordActualData.builder()
						.attendanceState(attendanceState.getAttendanceState())
						.workingNow(attendanceState.isWorkingNow())
						.build();
			}
			// case 4
			return AttendanceAccordActualData.builder()
					.attendanceState(status.get().getActivity())
					.workingNow(attendanceState.isWorkingNow())
					.build();
		}
		// case 5
		return AttendanceAccordActualData.builder()
				.attendanceState(attendanceState.getAttendanceState())
				.workingNow(attendanceState.isWorkingNow())
				.build();
	}
	
	public interface Required {
	
	/**
	 * [R-1] 社員の外出情報を取得する
	 * 
	 * 社員の外出情報Repository.取得する(社員ID,年月日)
	 * 
	 * @param sid  社員ID
	 * @param date 年月日
	 * @return Optional<社員の外出情報>
	 */
	public Optional<GoOutEmployeeInformation> getGoOutEmployeeInformation(String sid, GeneralDate date);
	
	/**
	 * [R-2] 在席のステータスを取得する
	 * 
	 * 在席のステータスRepository.取得する(社員ID,年月日)
	 * 
	 * @param sid 社員ID
	 * @param date 年月日>:
	 * @return Optional<ActivityStatus> Optional<在席のステータス>
	 */
	public Optional<ActivityStatus> getActivityStatus(String sid, GeneralDate date);
	
	/**
	 * [R-3] 在席状態を取得する
	 * 
	 * 在席状態を取得するAdapter.取得する(社員ID)
	 * 
	 * @param sid 社員ID
	 * @return 在席状態
	 */
	public AttendanceStateImport getAttendace(String sid);
	}
}
