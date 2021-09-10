package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone.ConfirmSetSpecifiResult;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * @author ThanhNX
 * 
 *         矛盾した時刻をクリアする
 * 
 */
@Stateless
public class ClearConflictTimeWithDay {

	@Inject
	private ConfirmSetSpecifiTimeZone confirmSetSpecifiTimeZone;

	@Inject
	private CreateResultClearAutoStamp createResultClearAutoStamp;
	
	@Inject
	private WorkTypeRepository workTypeRepo;

	// 矛盾した時刻をクリアする
	public void clear(String companyId, WorkingConditionItem workCondItem,
			IntegrationOfDaily domainDaily, ScheduleRecordClassifi classification) {

		Pair<Optional<TimeLeavingOfDailyAttd>, List<EditStateOfDailyAttd>> result;
		/** input.予定実績区分を確認 */
		if (classification == ScheduleRecordClassifi.RECORD) {
			result = getCorrectForRecord(companyId, workCondItem, domainDaily);
		} else {
			
			result = getCorrectForSche(companyId, domainDaily);
		}
		
		/** 日別実績の出退勤、編集状態を変更する */
		domainDaily.setAttendanceLeave(result.getLeft());
		domainDaily.setEditState(result.getRight());
	}
	
	/** 予定の時刻の矛盾を補正した時間帯を取得する */
	private Pair<Optional<TimeLeavingOfDailyAttd>, List<EditStateOfDailyAttd>> getCorrectForSche(String cid, IntegrationOfDaily domainDaily) {
		
		/** 勤務種類を取得する */
		val workType = workTypeRepo.findByPK(cid, domainDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v());
		
		return workType.map(c -> {

			/** 1日半日出勤・1日休日系の判定 */
			if (c.getAttendanceHolidayAttr() == AttendanceHolidayAttr.HOLIDAY) {
				
				/** 時刻をクリアした結果を作成する */
				return clearTime(domainDaily.getAttendanceLeave(), domainDaily.getEditState());
			}
			
			/** inputの情報をそのまま帰す */
			return Pair.of(domainDaily.getAttendanceLeave(), domainDaily.getEditState());
		}).orElseGet(() -> Pair.of(domainDaily.getAttendanceLeave(), domainDaily.getEditState()));
	} 
	
	/** 時刻をクリアした結果を作成する */
	private Pair<Optional<TimeLeavingOfDailyAttd>, List<EditStateOfDailyAttd>> clearTime(Optional<TimeLeavingOfDailyAttd> tl, List<EditStateOfDailyAttd> es) {
		
		return tl.map(c -> {

			/** 出退勤の件数分ループ */
			c.getTimeLeavingWorks().stream().forEach(tlw -> {
			
				/** 打刻のクリア */
				tlw.getAttendanceStamp().ifPresent(as -> {
					
					if (as.getStamp().isPresent()) {
						as.setStamp(Optional.empty());
						
						/** 編集状態をクリア */
						val removeId = tlw.getWorkNo().v() == 1 ? 31 : 41;
						es.removeIf(e -> e.getAttendanceItemId() == removeId);
					}
				});

				/** 打刻のクリア */
				tlw.getLeaveStamp().ifPresent(ls -> {
					
					if (ls.getStamp().isPresent()) {
						ls.setStamp(Optional.empty());
						
						/** 編集状態をクリア */
						val removeId = tlw.getWorkNo().v() == 1 ? 34 : 44;
						es.removeIf(e -> e.getAttendanceItemId() == removeId);
					}
				});
			});
			
			return Pair.of(tl, es);
		}).orElseGet(() -> Pair.of(tl, es));
	} 

	/** 実績の時刻の矛盾を補正した時間帯を取得する */
	private Pair<Optional<TimeLeavingOfDailyAttd>, List<EditStateOfDailyAttd>> getCorrectForRecord(String cid, 
			WorkingConditionItem workCondItem, IntegrationOfDaily domainDaily) {
		
		// 所定時間帯をセットするか確認する
		ConfirmSetSpecifiResult confirmSetSpecifi = confirmSetSpecifiTimeZone.confirmset(cid, workCondItem,
				domainDaily.getWorkInformation(), domainDaily.getAttendanceLeave(), 
				domainDaily.getYmd(), domainDaily.getEditState());

		// 自動打刻をクリアした結果を作成する ---mapping design domain
		if (confirmSetSpecifi.getAutoStampSetClassifi().isPresent()) {
			// 日別実績の出退勤を変更する
			// 返ってきた「日別実績の出退勤、編集状態を返す
			createResultClearAutoStamp.create(confirmSetSpecifi.getAutoStampSetClassifi().get(),
					domainDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v(), domainDaily.getAttendanceLeave());
		}
		
		return Pair.of(domainDaily.getAttendanceLeave(), domainDaily.getEditState());
	} 
	
}
