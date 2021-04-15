package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.GetSupportDataJudgedSameDS;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudCriteriaSameStampOfSupportRepo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudgmentCriteriaSameStampOfSupport;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SWkpHistImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.enu.DailyDomainGroup;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 応援作業反映
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績処理.作成処理.一日の日別実績の作成処理（New）.打刻反映.打刻応援反映する.応援作業反映.応援作業反映
 * 
 * @author phongtq
 *
 */
@Stateless
public class SupportWorkReflection {

	@Inject
	private SysWorkplaceAdapter sysWorkplaceAdapter;

	@Inject
	private JudCriteriaSameStampOfSupportRepo ofSupportRepo;

	public ReflectionAtr supportWorkReflect(SupportParam param, IntegrationOfDaily integrationOfDaily,
			StampReflectRangeOutput stampReflectRangeOutput) {
		String cid = AppContexts.user().companyId();

		// 打刻データが応援開始・終了反映時間内かの確認を行う
		boolean startAtr = this.checkStarEndSupport(param.getTimeDay(), stampReflectRangeOutput);
		if (!startAtr) {
			// 反映状態＝反映失敗を返す
			return ReflectionAtr.REFLECT_FAIL;
		}

		// 「応援の同一打刻の判断基準」を取得する TODO - còn phần lấy từ cache ra
		JudgmentCriteriaSameStampOfSupport judgmentSupport = ofSupportRepo.get(cid);

		// 職場、場所を取得する - lấy workplace và nơi làm việc
		WorkInformationWork informationWork = this.getWorkplaceWorkCDData(integrationOfDaily.getEmployeeId(),
				integrationOfDaily.getAffiliationInfor(), param.getStartAtr(), param.getTimeDay(),
				param.getLocationCode(), param.getWorkplaceId());

		// 日別勤怠（Work）から応援時間帯を取得する - 応援データ一覧 - lấy dữ liệu support từ 日別勤怠（Work）
		List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime = integrationOfDaily.getOuenTimeSheet();
		// 反映前状態の応援データ一覧を作る TODO - 反映前の応援データ一覧 - tạo ra list data support trước khi phản ánh
		List<OuenWorkTimeSheetOfDailyAttendance> lstOuenBefore = new ArrayList<>();
		lstOuenBefore.addAll(lstOuenWorkTime);
		// 勤怠打刻から応援データを作る - 打刻応援データ - tạo dữ liệu daily support từ check tay (stamp) lấy được
		OuenWorkTimeSheetOfDailyAttendance ouenStamp = this.convertStampingSupportData(informationWork,
				param.getTimeDay(), informationWork.getStartAtr());

		// 自動セットの応援データをクリアして、応援データをフラットする - clear dữ liệu support tự động được tạo
		List<OuenWorkTimeSheetOfDailyAttendance> lstClearOuen = this.clearSupportDataAutoFlatData(lstOuenWorkTime);

		// 打刻応援データを応援データ一覧にいれる - 打刻セット済み応援データ一覧 - cho data support check tay vào list data support
		List<OuenWorkTimeSheetOfDailyAttendance> lstStampedSupport = this.getStampedSupportData(
				param.isTimePriorityFlag(), judgmentSupport, lstClearOuen, ouenStamp, param.getStartAtr());

		// 出退勤で応援データを補正する - 補正済みの応援データ一覧 & 勤務Temporary - chỉnh sửa data support lúc đến lúc về
		CorrectSupportData correctSupportData = this.correctSupportDataFromWork(integrationOfDaily.getAttendanceLeave(),
				lstStampedSupport, informationWork, judgmentSupport);

		// 応援データを自動セットしてマージする - セット済み応援データ - set tự động data support rồi merge dữ liệu - qua buoc nay la chi con lai 2 thang dung data
		List<OuenWorkTimeSheetOfDailyAttendance> dataAutoSet = this
				.supportDataAutoSetMerged(correctSupportData.getInformationWork(), correctSupportData.getAttendance());

		// 最大応援回数を補正する - 補正済みの応援データ一覧 - chỉnh sửa số lần support tối đa
		List<OuenWorkTimeSheetOfDailyAttendance> lstCorrectMaximum = this.correctMaximumCheering(judgmentSupport,
				dataAutoSet);

		// 応援項目の編集状態補正する - chỉnh sửa trạng thái edit của support
		this.correctEditStatusSupportItem(integrationOfDaily, lstCorrectMaximum, lstOuenBefore);

		// 日別勤怠（Work）にデータ入れる
		integrationOfDaily.setOuenTimeSheet(dataAutoSet);

		// 反映状態＝反映済みを返す
		return ReflectionAtr.REFLECTED;
	}

	/**
	 * 打刻データが応援開始・終了反映時間内かの確認を行う
	 * 
	 * @param timeDay
	 * @param stampReflectRangeOutput
	 * @return
	 */
	public boolean checkStarEndSupport(WorkTimeInformation timeDay, StampReflectRangeOutput stampReflectRangeOutput) {
		// パラメータ。勤怠打刻を反映範囲ないか確認する
		// 打刻反映範囲。外出。開始＜＝勤怠打刻。時刻。時刻＜＝打刻反映範囲。外出。終了
		if (stampReflectRangeOutput.getGoOut().getStart().v() <= timeDay.getTimeWithDay().get().v()
				&& timeDay.getTimeWithDay().get().v() <= stampReflectRangeOutput.getGoOut().getEnd().v()) {
			return true;
		}
		return false;
	}

	/**
	 * 職場、作業CDデータを取得する
	 * 
	 * @param sid
	 * @param affiliationInfor
	 * @param startAtr
	 * @param timeDay
	 * @param locationCode
	 * @param workplaceId
	 * @return
	 */
	public WorkInformationWork getWorkplaceWorkCDData(String sid, AffiliationInforOfDailyAttd affiliationInfor,
			StartAtr startAtr, WorkTimeInformation timeDay, Optional<WorkLocationCD> locationCode,
			Optional<String> workplaceId) {
		// 勤務先情報Workを作成する
		WorkInformationWork informationWork = new WorkInformationWork(null, null, startAtr);

		// 個人情報を取得する
		WorkInformationWork informationWork2 = this.getPersonalInformation(affiliationInfor.getWplID(), informationWork);

		// パラメータ。場所コードを確認する
		if (locationCode.isPresent()) {
			// Emptyじゃない場合
			informationWork2.setLocationCD(new WorkLocationCD(locationCode.get().v()));
		}

		// パラメータ。職場IDを確認する
		if (workplaceId.isPresent()) {
			// Emptyじゃない
			informationWork2.setWorkplaceId(new WorkplaceId(workplaceId.get()));
		}

		return informationWork2;
	}

	/**
	 * 個人情報を取得する
	 * 
	 * @param sid
	 * @param informationWork
	 * @return
	 */
	public WorkInformationWork getPersonalInformation(String wplId, WorkInformationWork informationWork) {

		if (wplId != null) {
			// 勤務先情報Workを上書きする
			// 勤務先情報Work。職場ID＝取得した「社員所属職場履歴」。職場ID
			informationWork.setWorkplaceId(new WorkplaceId(wplId));
		}

		return informationWork;
	}

	/**
	 * 打刻応援データに変換する
	 * 
	 * @param informationWork
	 * @param information
	 * @param startAtr
	 * @return
	 */
	public OuenWorkTimeSheetOfDailyAttendance convertStampingSupportData(WorkInformationWork informationWork,
			WorkTimeInformation information, StartAtr startAtr) {
		// 新しい「日別実績の応援作業別勤怠時間帯」を作成する
		String companyId = AppContexts.user().companyId();
		OuenWorkTimeSheetOfDailyAttendance sheetOfDaily = null;

		// 作業Temporarｙから「日別実績の応援作業別勤怠時間帯」にデータ入れる
		WorkplaceOfWorkEachOuen eachOuen = null;
		if (informationWork.getLocationCD() != null) {
			eachOuen = WorkplaceOfWorkEachOuen.create(informationWork.getWorkplaceId().v(),
					new WorkLocationCD(informationWork.getLocationCD().v()));
		} else {
			eachOuen = WorkplaceOfWorkEachOuen.create(informationWork.getWorkplaceId().v(), null);
		}

		WorkContent workContent = WorkContent.create(companyId, eachOuen, Optional.empty());

		TimeSheetOfAttendanceEachOuenSheet timeSheet = null;

		// パラメータ。開始区分を確認する
		if (startAtr == StartAtr.START_OF_SUPPORT) {
			// 応援開始の場合
			timeSheet = TimeSheetOfAttendanceEachOuenSheet.create(new WorkNo(0), Optional.ofNullable(information),
					Optional.empty());
		}

		if (startAtr == StartAtr.END_OF_SUPPORT) {
			// 応援終了の場合
			timeSheet = TimeSheetOfAttendanceEachOuenSheet.create(new WorkNo(0), Optional.empty(),
					Optional.ofNullable(information));
		}
		sheetOfDaily = OuenWorkTimeSheetOfDailyAttendance.create(0, workContent, timeSheet);

		return sheetOfDaily;
	}

	/**
	 * 自動セットの応援データをクリアして、応援データをフラットする
	 * 
	 * @param lstOuenWorkTime
	 * @return
	 */
	public List<OuenWorkTimeSheetOfDailyAttendance> clearSupportDataAutoFlatData(
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime) {
		// 空の応援データ一覧を作成する
		List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWork = new ArrayList<>();

		// 応援データ一覧をループする
		for (OuenWorkTimeSheetOfDailyAttendance ouen : lstOuenWorkTime) {
			// 「開始の応援データ」が自動セットかを確認する
			// 応援データ。時間帯。開始。実打刻。時刻。時刻変更理由。時刻変更手段
			if (ouen.getTimeSheet().getStart().isPresent() && ouen.getTimeSheet().getStart().get().getReasonTimeChange()
					.getTimeChangeMeans() == TimeChangeMeans.AUTOMATIC_SET) {
				// 「応援データ。時間帯。開始」をクリアする
				ouen.getTimeSheet().setStart(null);
			}
			// 「終了の応援データ」が自動セットかを確認する
			// 応援データ。時間帯。終了。実打刻。時刻。時刻変更理由。時刻変更手段
			if (ouen.getTimeSheet().getEnd().isPresent() && ouen.getTimeSheet().getEnd().get().getReasonTimeChange()
					.getTimeChangeMeans() == TimeChangeMeans.AUTOMATIC_SET) {
				// 「応援データ。時間帯。終了」をクリアする
				ouen.getTimeSheet().setEnd(null);
			}

			// 開始と終了を確認する
			// どっちか一方ある場合 - start or end
			if ((ouen.getTimeSheet().getStart().isPresent() && !ouen.getTimeSheet().getEnd().isPresent())
					|| (!ouen.getTimeSheet().getStart().isPresent() && ouen.getTimeSheet().getEnd().isPresent())) {
				lstOuenWork.add(ouen);
			}

			// 両方がある場合 - both start and end
			if (ouen.getTimeSheet().getStart().isPresent() && ouen.getTimeSheet().getEnd().isPresent()) {
				TimeSheetOfAttendanceEachOuenSheet eachOuenSheet = TimeSheetOfAttendanceEachOuenSheet
						.create(new WorkNo(ouen.getWorkNo()), Optional.empty(), ouen.getTimeSheet().getEnd());
				OuenWorkTimeSheetOfDailyAttendance dailyAttendance = OuenWorkTimeSheetOfDailyAttendance
						.create(ouen.getWorkNo(), ouen.getWorkContent(), eachOuenSheet);

				TimeSheetOfAttendanceEachOuenSheet eachOuenSheet2 = TimeSheetOfAttendanceEachOuenSheet
						.create(new WorkNo(ouen.getWorkNo()), ouen.getTimeSheet().getStart(), Optional.empty());
				OuenWorkTimeSheetOfDailyAttendance dailyAttendance2 = OuenWorkTimeSheetOfDailyAttendance
						.create(ouen.getWorkNo(), ouen.getWorkContent(), eachOuenSheet2);

				lstOuenWork.add(dailyAttendance);
				lstOuenWork.add(dailyAttendance2);
			}
		}

		return lstOuenWork;
	}

	/**
	 * 打刻応援データを応援データ一覧にいれる
	 * 
	 * @param timePriorityFlag
	 * @param judgmentSupport
	 * @param lstClearOuen
	 * @param ouenStamp
	 * @param startAtr
	 * @return
	 */
	public List<OuenWorkTimeSheetOfDailyAttendance> getStampedSupportData(boolean timePriorityFlag,
			JudgmentCriteriaSameStampOfSupport judgmentSupport,
			List<OuenWorkTimeSheetOfDailyAttendance> lstClearOuen, OuenWorkTimeSheetOfDailyAttendance ouenStamp,
			StartAtr startAtr) {
		GetSupportDataJudgedSameDS.Required required = new GetSupportDataJudgedSameImpl(ofSupportRepo);
		// 同一と判断された応援データを取得する - 応援データ
		Optional<OuenWorkTimeSheetOfDailyAttendance> ouenStampNew = GetSupportDataJudgedSameDS
				.getSupportDataJudgedSame(required, lstClearOuen, ouenStamp, startAtr.value == 0 ? true : false);
		if (!ouenStampNew.isPresent()) {
			// 打刻応援データを応援データ一覧に入れる
			lstClearOuen.add(ouenStamp);
			return lstClearOuen;
		}
		// 上書き対象の時刻を取得する - 勤怠時刻
		Optional<TimeWithDayAttr> timeOverWritten = this.getTimeOverwritten(timePriorityFlag, ouenStampNew.get(), startAtr,
				ouenStamp);
		if (timeOverWritten.isPresent()) {
			// 取得した勤怠打刻＜＞Empty
			// 時刻上書きする
			this.overwriteTime(ouenStampNew.get(), startAtr, timeOverWritten);
		} else {
			if (ouenStampNew.get().getWorkContent().getWorkplace().getWorkplaceId() != null)
				return lstClearOuen;
		}
		// 応援・作業を補正する
		this.correctSupportWork(ouenStamp, ouenStampNew.get());
		return lstClearOuen;
	}

	/**
	 * 応援・作業を補正する
	 * 
	 * @param ouenConvert
	 * @param ouenStamp
	 */
	public void correctSupportWork(OuenWorkTimeSheetOfDailyAttendance ouenStamp,
			OuenWorkTimeSheetOfDailyAttendance ouenStampNew) {
		// 作業データを上書きする
		ouenStampNew.getWorkContent().setWork(ouenStamp.getWorkContent().getWork());
		// 勤務先を上書きする
		ouenStampNew.getWorkContent().setWorkplace(ouenStamp.getWorkContent().getWorkplace());
	}

	/**
	 * 時刻上書きする
	 * 
	 * @param ouenStamp
	 * @param startAtr
	 * @param timeOverWritten
	 */
	public void overwriteTime(OuenWorkTimeSheetOfDailyAttendance ouenStamp, StartAtr startAtr,
			Optional<TimeWithDayAttr> timeOverWritten) {
		// 開始区分を確認する
		if (startAtr == StartAtr.START_OF_SUPPORT) {
			ouenStamp.getTimeSheet().getStart().get().setTimeWithDay(timeOverWritten);
		}

		if (startAtr == StartAtr.END_OF_SUPPORT) {
			ouenStamp.getTimeSheet().getEnd().get().setTimeWithDay(timeOverWritten);
		}
	}

	/**
	 * 上書き対象の時刻を取得する
	 * 
	 * @param timePriorityFlag
	 * @param ouenStampNew
	 * @param startAtr
	 * @param ouenStamp
	 * @return
	 */
	public Optional<TimeWithDayAttr> getTimeOverwritten(boolean timePriorityFlag,
			OuenWorkTimeSheetOfDailyAttendance ouenStampNew, StartAtr startAtr,
			OuenWorkTimeSheetOfDailyAttendance ouenStamp) {
		if (startAtr == StartAtr.START_OF_SUPPORT) {
			// 応援開始の場合
			// 時刻上書きか確認する
			// 「打刻応援データ。開始。時刻 ＞ 応援データ。開始。時刻」 OR 時刻優先フラグ＝True
			if ((ouenStamp.getTimeSheet().getStart().get().getTimeWithDay().isPresent()
					&& ouenStampNew.getTimeSheet().getStart().get().getTimeWithDay().isPresent()
					&& ouenStamp.getTimeSheet().getStart().get().getTimeWithDay().get().v() > ouenStampNew.getTimeSheet()
							.getStart().get().getTimeWithDay().get().v())
					|| timePriorityFlag == true) {
				return ouenStampNew.getTimeSheet().getStart().get().getTimeWithDay();
			}
		}
		if (startAtr == StartAtr.END_OF_SUPPORT) {
			// 上書き対象の時刻を取得する
			// 「打刻応援データ。終了。時刻 ＞ 応援データ。終了。時刻」 OR 時刻優先フラグ＝True
			if ((ouenStamp.getTimeSheet().getStart().get().getTimeWithDay().isPresent()
					&& ouenStampNew.getTimeSheet().getStart().get().getTimeWithDay().isPresent()
					&& ouenStamp.getTimeSheet().getEnd().get().getTimeWithDay().get().v() > ouenStampNew.getTimeSheet()
							.getEnd().get().getTimeWithDay().get().v())
					|| timePriorityFlag == true) {
				return ouenStampNew.getTimeSheet().getEnd().get().getTimeWithDay();
			}
		}
		return Optional.empty();
	}

	/**
	 * 出退勤で応援データを補正する
	 * 
	 * @param attendanceLeave
	 * @param lstOuenWorkTime
	 * @param informationWork
	 * @param judgmentSupport
	 * @return
	 */
	public CorrectSupportData correctSupportDataFromWork(Optional<TimeLeavingOfDailyAttd> attendanceLeave,
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime, WorkInformationWork informationWork,
			JudgmentCriteriaSameStampOfSupport judgmentSupport) {

		// 最初の出勤と最後の退勤を検出する
		WorkTemporary detectAttendance = this.detectAttendance(attendanceLeave);

		// 最初の出勤の応援データと最後の退勤の応援データを取得する
		SupportAttendanceDepartureTempo suportDataFirtAndLast = this.getSupportFataFirstAttendanceLastDeparture(
				lstOuenWorkTime, detectAttendance, judgmentSupport, informationWork);

		// ほかの出退勤を補正する
		this.correctOtherAttendance(informationWork, detectAttendance, lstOuenWorkTime, judgmentSupport);

		// 応援データを並びかえる （時刻の昇順） - ソート済み応援データ
		List<OuenWorkTimeSheetOfDailyAttendance> lstOuenSort = this.rearrangeSupportData(lstOuenWorkTime);

		// 最初の出勤と最後の退勤の応援データを応援データに入れる
		List<OuenWorkTimeSheetOfDailyAttendance> lstCompensate = this.compensateFirstLastAttendance(lstOuenSort,
				suportDataFirtAndLast);

		// 補正済みの応援データ一覧を返す
		CorrectSupportData data = new CorrectSupportData(lstCompensate, detectAttendance);
		return data;
	}

	/**
	 * 応援データを自動セットしてマージする
	 * 
	 * @param informationWork
	 * @param lstOuenWorkTime
	 * @return
	 */
	public List<OuenWorkTimeSheetOfDailyAttendance> supportDataAutoSetMerged(WorkTemporary informationWork,
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime) {
		// セット済み応援データ一覧＝Empty
		List<OuenWorkTimeSheetOfDailyAttendance> lstSetSupportData = new ArrayList<>();
		for (int i = 0; i < lstOuenWorkTime.size(); i++) {
			// 処理中の応援データの開始データを確認する
			if (lstOuenWorkTime.get(i).getTimeSheet().getStart().isPresent()) {
				OuenWorkTimeSheetOfDailyAttendance attendance = null;
				if (lstOuenWorkTime.size() > (i + 1)) {
					// 後ろの応援データを取得する
					attendance = lstOuenWorkTime.get(i + 1);
				}
				// 取得できない
				// ・勤務Temporary＝パラメータ。勤務Temporary ・応援データ一覧＝補正済み応援データ一覧 ・処理中の応援データ＝処理中の応援データ
				if (attendance == null) {
					this.correctSupportDataPutInList(informationWork, lstSetSupportData, lstOuenWorkTime.get(i));
					return lstSetSupportData;
				} else {
					// 後ろの応援データの開始データを確認する
					if (attendance.getTimeSheet().getStart().isPresent()) {
						// 存在する場合
						// 処理中の応援データを自動セットする
						OuenWorkTimeSheetOfDailyAttendance ouenWorkTime = this
								.automaticallySetSupportDataBeingProcessed(StartAtr.END_OF_SUPPORT,
										lstOuenWorkTime.get(i), attendance, informationWork);
						// 応援データを補正して一覧に入れる
						this.correctSupportDataPutInList(informationWork, lstSetSupportData, ouenWorkTime);
					} else {
						// 処理中の応援データに終了データをセットする
						if(attendance.getTimeSheet().getEnd().isPresent())
						lstOuenWorkTime.get(i).getTimeSheet().setEnd(attendance.getTimeSheet().getEnd().get());
						// 応援データを補正して一覧に入れる
						this.correctSupportDataPutInList(informationWork, lstSetSupportData, lstOuenWorkTime.get(i));
						i += 1;
					}
				}
			} else {
				// 前のセット済みの応援データを取得する
				OuenWorkTimeSheetOfDailyAttendance attendance = null;
				if (lstOuenWorkTime.size() > 0) {
					// 後ろの応援データを取得する
					attendance = lstSetSupportData.get(lstSetSupportData.size() - 1);
					if (attendance == null) {
						// 応援データを補正して一覧に入れる
						this.correctSupportDataPutInList(informationWork, lstSetSupportData, lstOuenWorkTime.get(i));
					} else {
						// 取得できます
						// 処理中の応援データを自動セットする
						OuenWorkTimeSheetOfDailyAttendance ouenWorkTime = this
								.automaticallySetSupportDataBeingProcessed(StartAtr.START_OF_SUPPORT,
										lstOuenWorkTime.get(i), attendance, informationWork);
						// 応援データを補正して一覧に入れる
						this.correctSupportDataPutInList(informationWork, lstSetSupportData, ouenWorkTime);
					}
				}
			}
		}
		return lstSetSupportData;
	}

	/**
	 * 処理中の応援データを自動セットする
	 * 
	 * @param startAtr
	 * @param ouenWorkTime
	 * @param attendance
	 * @param informationWork
	 * @return
	 */
	public OuenWorkTimeSheetOfDailyAttendance automaticallySetSupportDataBeingProcessed(StartAtr startAtr,
			OuenWorkTimeSheetOfDailyAttendance ouenWorkTime, OuenWorkTimeSheetOfDailyAttendance attendance,
			WorkTemporary informationWork) {
		if (startAtr == StartAtr.START_OF_SUPPORT) {
			if (attendance.getTimeSheet().getEnd().isPresent()) { // check để không lỗi
				ReasonTimeChange reasonTimeChange = new ReasonTimeChange(TimeChangeMeans.valueOf(attendance.getTimeSheet().getEnd().get().getReasonTimeChange().getTimeChangeMeans().value), 
						Optional.ofNullable(attendance.getTimeSheet().getEnd().get().getReasonTimeChange().getEngravingMethod().isPresent() ? 
								attendance.getTimeSheet().getEnd().get().getReasonTimeChange().getEngravingMethod().get() : null));
				Optional<TimeWithDayAttr> timeWithDay = attendance.getTimeSheet().getEnd().get().getTimeWithDay().isPresent() ? 
						Optional.of(new TimeWithDayAttr(attendance.getTimeSheet().getEnd().get().getTimeWithDay().get().v())) : Optional.empty();
				WorkTimeInformation end = new WorkTimeInformation(reasonTimeChange, timeWithDay.isPresent() ? timeWithDay.get() : null);
				
				ouenWorkTime.getTimeSheet().setStart(end);
				ouenWorkTime.getTimeSheet().getStart().get().getReasonTimeChange()
						.setTimeChangeMeans(TimeChangeMeans.AUTOMATIC_SET);
			} else {
				ouenWorkTime.getTimeSheet().setStart(WorkTimeInformation.createByAutomaticSet(null));
			}
		} else {
			if (attendance.getTimeSheet().getStart().isPresent()) { // check để không lỗi
				ReasonTimeChange reasonTimeChange = new ReasonTimeChange(TimeChangeMeans.valueOf(attendance.getTimeSheet().getStart().get().getReasonTimeChange().getTimeChangeMeans().value), 
						Optional.ofNullable(attendance.getTimeSheet().getStart().get().getReasonTimeChange().getEngravingMethod().isPresent() ? 
								attendance.getTimeSheet().getStart().get().getReasonTimeChange().getEngravingMethod().get() : null));
				Optional<TimeWithDayAttr> timeWithDay = attendance.getTimeSheet().getStart().get().getTimeWithDay().isPresent() ? 
						Optional.of(new TimeWithDayAttr(attendance.getTimeSheet().getStart().get().getTimeWithDay().get().v())) : Optional.empty();
				WorkTimeInformation start = new WorkTimeInformation(reasonTimeChange, timeWithDay.isPresent() ? timeWithDay.get() : null);
				
				ouenWorkTime.getTimeSheet().setEnd(start);
				ouenWorkTime.getTimeSheet().getEnd().get().getReasonTimeChange()
						.setTimeChangeMeans(TimeChangeMeans.AUTOMATIC_SET);
			} else {
				ouenWorkTime.getTimeSheet().setEnd(WorkTimeInformation.createByAutomaticSet(null));
			}
		}
		return ouenWorkTime;
	}

	/**
	 * 応援データを補正して一覧に入れる
	 * 
	 * @param informationWork
	 * @param lstSetSupportData
	 * @param ouenWorkTime
	 */
	public void correctSupportDataPutInList(WorkTemporary informationWork,
			List<OuenWorkTimeSheetOfDailyAttendance> lstSetSupportData,
			OuenWorkTimeSheetOfDailyAttendance ouenWorkTime) {
		this.correctWorkFrameNumber(informationWork, ouenWorkTime);
		ouenWorkTime.setWorkNo(lstSetSupportData.size());
		lstSetSupportData.add(ouenWorkTime);
	}

	/**
	 * 勤務枠Noを補正する
	 * 
	 * @param informationWork
	 * @param ouenWorkTime
	 */
	public void correctWorkFrameNumber(WorkTemporary informationWork, OuenWorkTimeSheetOfDailyAttendance ouenWorkTime) {
		// 勤務Temporary。出勤２時刻のデータがあるか確認する
		if (informationWork.getTwoHoursWork().isPresent()) {
			// Nullじゃない場合
			// 応援データは出勤２時刻以内か確認する - Kiểm tra nếu có start thì dùng start ko thì dùng end để
			// so sánh
			// 出勤２時刻より大きい場合 - larger
			boolean checkTimeLarger = false;
			if (ouenWorkTime.getTimeSheet().getStart().isPresent()) {
				if (ouenWorkTime.getTimeSheet().getStart().get().getTimeWithDay().get().v() > informationWork
						.getTwoHoursWork().get().getStamp().get().getTimeDay().getTimeWithDay().get().v()) {
					checkTimeLarger = true;
				}
			} else {
				if (ouenWorkTime.getTimeSheet().getEnd().get().getTimeWithDay().get().v() > informationWork
						.getTwoHoursWork().get().getStamp().get().getTimeDay().getTimeWithDay().get().v()) {
					checkTimeLarger = true;
				}
			}

			if (checkTimeLarger == true) {
				// 応援データの勤務枠Noをセットする
				ouenWorkTime.getTimeSheet().setWorkNo(new WorkNo(2));
				return;
			}
		}
		ouenWorkTime.getTimeSheet().setWorkNo(new WorkNo(1));
	}

	/**
	 * 最大応援回数を補正する
	 * 
	 * @param judgmentSupport
	 * @param dataAutoSet
	 * @return
	 */
	public List<OuenWorkTimeSheetOfDailyAttendance> correctMaximumCheering(
			JudgmentCriteriaSameStampOfSupport judgmentSupport, List<OuenWorkTimeSheetOfDailyAttendance> dataAutoSet) {
		List<OuenWorkTimeSheetOfDailyAttendance> dataAutoSetNew = new ArrayList<>();
		// パラメータ。応援データ一覧のsizeを確認する
		if (dataAutoSet.size() <= 1) {
			// １以下の場合 - パラメータ。応援データ一覧を返す
			return dataAutoSet;
		} else {
			// ２以上の場合
			// パラメータ。応援データ一覧の末尾の応援データを抜き出す
			OuenWorkTimeSheetOfDailyAttendance lastData = dataAutoSet.get(dataAutoSet.size() - 1);
			// パラメータ。応援データ一覧の先頭の応援データを取得する
			OuenWorkTimeSheetOfDailyAttendance firstData = dataAutoSet.get(0);

			// 最大応援回数で補正する
			dataAutoSetNew = this.correctWithMaxNumberCheers(
					judgmentSupport.getSupportMaxFrame() == null ? judgmentSupport.getSupportMaxFrame().v() - 1 : null,
					dataAutoSet);
			if (dataAutoSetNew.size() > 1) {
				// 最後の退勤の応援データを補正する
				lastData.getTimeSheet()
						.setStart(firstData.getTimeSheet().getStart().isPresent()
								? firstData.getTimeSheet().getStart().get()
								: null);
				// 最後の退勤の応援データを補正済みの応援データ一覧に入れる
				dataAutoSetNew.add(lastData);
			} else {
				// 補正済みの応援データ一覧の末尾の応援データを取得する
				OuenWorkTimeSheetOfDailyAttendance lastDataProcess = dataAutoSetNew.isEmpty() ? null
						: dataAutoSetNew.get(dataAutoSetNew.size() - 1); // check để không lỗi
				// 最後の退勤の応援データを補正する
				if (lastDataProcess != null) { // check để không lỗi
					lastData.getTimeSheet()
							.setStart(lastDataProcess.getTimeSheet().getEnd().isPresent()
									? lastDataProcess.getTimeSheet().getEnd().get()
									: null);
				}

				// 最後の退勤の応援データを補正済みの応援データ一覧の末尾に入れる
				dataAutoSetNew.add(dataAutoSetNew.size(), lastData);
			}
		}
		return dataAutoSetNew;
	}

	/**
	 * 最大応援回数で補正する
	 * 
	 * @param support
	 * @param dataAutoSet
	 * @return
	 */
	public List<OuenWorkTimeSheetOfDailyAttendance> correctWithMaxNumberCheers(Integer support,
			List<OuenWorkTimeSheetOfDailyAttendance> dataAutoSet) {
		List<OuenWorkTimeSheetOfDailyAttendance> dataAutoSetNew = new ArrayList<>();
		if (support != null && dataAutoSet.size() > support) {
			dataAutoSetNew = dataAutoSet.subList(0, support);
		}
		return dataAutoSetNew;
	}

	/**
	 * 編集状態を補正する
	 * 
	 * @param integrationOfDaily
	 * @param lstCorrectMaximum
	 * @param lstOuenBefore
	 */
	public void correctEditStatusSupportItem(IntegrationOfDaily integrationOfDaily,
			List<OuenWorkTimeSheetOfDailyAttendance> lstCorrectMaximum,
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenBefore) {
		// 応援開始時刻、応援終了時刻以外の編集状態補正する
		Map<Integer, List<ItemValue>> mapItemValue = new HashMap<>();
		this.correctEditStatusOtherCheering(integrationOfDaily, lstCorrectMaximum, lstOuenBefore, mapItemValue);

		// 応援開始時刻、応援終了時刻の編集状態を補正する
		this.correctEditStatusCheeringStarEndYime(integrationOfDaily, lstCorrectMaximum, mapItemValue);
	}

	// 応援開始時刻、応援終了時刻以外の編集状態補正する
	public void correctEditStatusOtherCheering(IntegrationOfDaily integrationOfDaily,
			List<OuenWorkTimeSheetOfDailyAttendance> lstCorrectMaximum,
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenBefore, Map<Integer, List<ItemValue>> mapItemValue) {

		// パラメータ。日別勤怠（Work）。編集状態から応援時間帯の編集状態一覧を取得する
		List<Integer> lstIdState = AttendanceItemIdContainer.getItemIdByDailyDomains(DailyDomainGroup.SUPPORT_TIME,
				DailyDomainGroup.SUPPORT_TIMESHEET);
		List<EditStateOfDailyAttd> lstEditState = integrationOfDaily.getEditState().stream()
				.filter(x -> lstIdState.contains(x.getAttendanceItemId())).collect(Collectors.toList());

		// 取得できない
		if (lstEditState.isEmpty()) {
			return;
		}

		// Emptyの補正済みの編集状態一覧を作る - 補正済みの編集状態：日別実績の編集状態＜List＞
		List<EditStateOfDailyAttd> lstEditStated = new ArrayList<>();

		// 取得できた編集状態一覧は応援勤務枠Noでグループする
		Map<Integer, List<EditStateOfDailyAttd>> mapGroupEdits = new HashMap<>();
		List<ItemValue> lstItemValue = AttendanceItemIdContainer.getIds(lstIdState, AttendanceItemType.DAILY_ITEM);
		mapItemValue = AttendanceItemIdContainer.mapWorkNoItemsValue(lstItemValue);

		mapItemValue.entrySet().stream().forEach(x -> {
			List<Integer> ids = x.getValue().stream().map(i -> i.getItemId()).collect(Collectors.toList());
			List<EditStateOfDailyAttd> lstEditStatefilter = lstEditState.stream()
					.filter(z -> ids.contains(z.getAttendanceItemId())).collect(Collectors.toList());

			mapGroupEdits.put(x.getKey(), lstEditStatefilter);
		});

		// グループした編集状態グループをループする
		mapGroupEdits.entrySet().stream().forEach(x -> {
			// 反映後の応援勤務枠Noを取得する
			int frameNoAfterRelect = this.getSupportWorkFrameNoAfterRelect(x.getKey(), lstOuenBefore,
					lstCorrectMaximum);
			// －１じゃない場合
			if (frameNoAfterRelect != -1) {
				// 反映後の応援勤務枠Noを取得する
				if (frameNoAfterRelect != x.getKey()) {
					// 処理中のグループの編集状態をベースして取得した応援勤務枠Noの項目の編集状態を作る
					List<EditStateOfDailyAttd> dailyAttd = mapGroupEdits.get(x.getKey() + 1);
					// 作った編集状態一覧を補正済みの編集状態一覧に入れる
					lstEditStated.addAll(dailyAttd);
				} else {
					// 処理中のグループの編集状態を補正済みの編集状態一覧に入れる
					lstEditStated.addAll(x.getValue());
				}
			}
		});

		// パラメータ。日別勤怠（Work）。編集状態から応援項目の編集状態を消す
		List<Integer> lstId = lstEditState.stream().map(i -> i.getAttendanceItemId()).collect(Collectors.toList());
		List<EditStateOfDailyAttd> editStateOfDailyAttds = integrationOfDaily.getEditState().stream()
				.filter(del -> !lstId.contains(del.getAttendanceItemId())).collect(Collectors.toList());

		integrationOfDaily.setEditState(editStateOfDailyAttds);
		// パラメータ。日別勤怠（Work）。編集状態に補正済みの編集状態を追加する
		integrationOfDaily.getEditState().addAll(lstEditStated);
	}

	/**
	 * 反映後の応援勤務枠Noを取得する
	 * 
	 * @param workFrameNo
	 * @param lstOuenBefore
	 * @param lstCorrectMaximum
	 * @return
	 */
	public int getSupportWorkFrameNoAfterRelect(int workFrameNo, List<OuenWorkTimeSheetOfDailyAttendance> lstOuenBefore,
			List<OuenWorkTimeSheetOfDailyAttendance> lstCorrectMaximum) {
		// 反映前の応援データを取得する
		Optional<OuenWorkTimeSheetOfDailyAttendance> ouenBeforeNew = lstOuenBefore.stream()
				.filter(x -> x.getWorkNo() == workFrameNo).findFirst();
		Optional<OuenWorkTimeSheetOfDailyAttendance> correctMaximumNew = Optional.empty();
		// 取得できる応援データの開始データが自動セットかどうか確認する
		if (ouenBeforeNew.get().getTimeSheet().getStart().get().getReasonTimeChange()
				.getTimeChangeMeans() == TimeChangeMeans.AUTOMATIC_SET) {
			// 自動セットの場合
			// 反映前の応援時刻を取得する
			Optional<WorkTimeInformation> end = ouenBeforeNew.get().getTimeSheet().getEnd();

			correctMaximumNew = lstCorrectMaximum.stream().filter(x -> x.getTimeSheet().getEnd().get() == end.get())
					.findFirst();
		} else {
			Optional<WorkTimeInformation> start = ouenBeforeNew.get().getTimeSheet().getStart();
			correctMaximumNew = lstCorrectMaximum.stream().filter(x -> x.getTimeSheet().getStart().get() == start.get())
					.findFirst();
		}

		if (correctMaximumNew.isPresent()) {
			// 応援データの応援勤務枠Noを返す
			return ouenBeforeNew.get().getWorkNo();
		}
		// －１を返す
		return -1;
	}

	/**
	 * 応援開始時刻、応援終了時刻の編集状態を補正する
	 * 
	 * @param integrationOfDaily
	 * @param lstCorrectMaximum
	 * @param mapItemValue
	 */
	public void correctEditStatusCheeringStarEndYime(IntegrationOfDaily integrationOfDaily,
			List<OuenWorkTimeSheetOfDailyAttendance> lstCorrectMaximum, Map<Integer, List<ItemValue>> mapItemValue) {
		for (OuenWorkTimeSheetOfDailyAttendance attendance : lstCorrectMaximum) {
			// 処理中の応援データの開始の変更手段を確認する
			TimeChangeMeans changeMeansStart = attendance.getTimeSheet().getStart().get().getReasonTimeChange()
					.getTimeChangeMeans();

			if ((changeMeansStart == TimeChangeMeans.HAND_CORRECTION_PERSON)
					|| (changeMeansStart == TimeChangeMeans.HAND_CORRECTION_OTHERS)
					|| (changeMeansStart == TimeChangeMeans.APPLICATION)) {
				// 編集状態追加する
				Optional<ItemValue> itemStart = mapItemValue.get(attendance.getWorkNo()).stream()
						.filter(x -> x.getPathLink().toString().contains(ItemConst.START)).findFirst();
				this.addEditStatus(itemStart.get().getItemId(), integrationOfDaily, changeMeansStart);
			}
			TimeChangeMeans changeMeansEnd = attendance.getTimeSheet().getEnd().isPresent()
					? attendance.getTimeSheet().getEnd().get().getReasonTimeChange().getTimeChangeMeans()
					: null; // check để không lỗi
			if ((changeMeansEnd == TimeChangeMeans.HAND_CORRECTION_PERSON)
					|| (changeMeansEnd == TimeChangeMeans.HAND_CORRECTION_OTHERS)
					|| (changeMeansEnd == TimeChangeMeans.APPLICATION)) {
				// 編集状態追加する
				Optional<ItemValue> itemEnd = mapItemValue.get(attendance.getWorkNo()).stream()
						.filter(x -> x.getPathLink().toString().contains(ItemConst.END)).findFirst();
				this.addEditStatus(itemEnd.get().getItemId(), integrationOfDaily, changeMeansEnd);
			}
		}
	}

	/**
	 * 編集状態追加する
	 * 
	 * @param itemId
	 * @param integrationOfDaily
	 * @param changeMeans
	 */
	public void addEditStatus(int itemId, IntegrationOfDaily integrationOfDaily, TimeChangeMeans changeMeans) {
		EditStateSetting stateSetting = null;
		if (changeMeans == TimeChangeMeans.APPLICATION || changeMeans == TimeChangeMeans.DIRECT_BOUNCE_APPLICATION) {
			stateSetting = EditStateSetting.REFLECT_APPLICATION;
		}
		// 応援開始時刻の編集状態を作る
		EditStateOfDailyAttd attd = new EditStateOfDailyAttd(itemId, stateSetting);
		// パラメータ。日別勤怠（Work）。編集状態に作った編集状態を入れる
		integrationOfDaily.getEditState().add(attd);
	}

	/**
	 * 最初の出勤と最後の退勤を検出する
	 * 
	 * @param attendanceLeave
	 * @return
	 */
	public WorkTemporary detectAttendance(Optional<TimeLeavingOfDailyAttd> attendanceLeave) {
		// Emptyの勤務Temporaryを作成する
		WorkTemporary workTemporary = new WorkTemporary(Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty());

		// 出退勤１を取得する
		Optional<TimeLeavingWork> leavingWork = Optional.empty();
		Optional<TimeLeavingWork> leavingWork2 = Optional.empty();
		if (attendanceLeave.isPresent()) {
			leavingWork = attendanceLeave.get().getTimeLeavingWorks().stream().filter(x -> x.getWorkNo().v() == 1)
					.findFirst();
			leavingWork2 = attendanceLeave.get().getTimeLeavingWorks().stream().filter(x -> x.getWorkNo().v() == 2)
					.findFirst();
		}

		if (leavingWork.isPresent()) {
			// 最初の出勤をセットする - 勤務Temporary。最初の出勤＝取得できる出退勤．出勤
			workTemporary.setFirstAttendance(leavingWork.get().getAttendanceStamp());

			// 最後の退勤をセットする
			// 勤務Temporary。最後の退勤＝取得できる出退勤．退勤
			workTemporary.setLastLeave(leavingWork.get().getLeaveStamp());
			// 勤務Temporary。退勤1時刻＝取得できる出退勤．退勤
			workTemporary.setOneHourLeavingWork(leavingWork.get().getLeaveStamp());
		}
		if (leavingWork2.isPresent()) {
			// 勤務Temporary。最後の退勤＝取得できる出退勤．退勤
			workTemporary.setLastLeave(leavingWork2.get().getLeaveStamp());
			// 勤務Temporary。出勤２時刻＝取得できる出退勤．出勤
			workTemporary.setTwoHoursWork(leavingWork2.get().getLeaveStamp());
		}

		return workTemporary;
	}

	/**
	 * 最初の出勤の応援データと最後の退勤の応援データを補正する
	 * 
	 * @param lstOuenWorkTime
	 * @param detectAttendance
	 * @param judgmentSupport
	 * @param informationWork
	 * @return
	 */
	public SupportAttendanceDepartureTempo getSupportFataFirstAttendanceLastDeparture(
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime, WorkTemporary detectAttendance,
			JudgmentCriteriaSameStampOfSupport judgmentSupport, WorkInformationWork informationWork) {

		// Emptyの出退勤の応援を作成する
		SupportAttendanceDepartureTempo departureTempo = new SupportAttendanceDepartureTempo(Optional.empty(),
				Optional.empty());

		// 最初の出勤を確認する
		if (detectAttendance.getFirstAttendance().isPresent()) {
			// Nullじゃない場合
			// 最初の出勤の時刻と同じ時刻もつ応援データを応援データ一覧から検索して抜き出し
			// 最初の出勤。打刻。時刻。時刻
			Integer time1 = detectAttendance.getFirstAttendance().get().getStamp().get().getTimeDay().getTimeWithDay()
					.get().v();
			// 同一打刻の判断基準。同一打刻とみなす範囲
			Integer time2 = judgmentSupport.getSameStampRanceInMinutes().v();
			Optional<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeAfter = lstOuenWorkTime.stream().filter(x ->{
					val time = x.getTimeSheet().getStart().flatMap(c -> c.getTimeWithDay()).map(c -> c.v()).orElse(null);
					if(time == null)
					return false;
					return time1 - time2 < time &&  time< time1 + time2;}).findFirst();
				
				if (ouenWorkTimeAfter.isPresent()) {
					lstOuenWorkTime.remove(ouenWorkTimeAfter.get());
					// 検索できる
					// 出退勤の応援。最初の出勤をセットする
					departureTempo.setFirstAttendance(ouenWorkTimeAfter);

					// 出退勤の応援。最初の出勤。時間帯。開始。時刻。時刻＝最初の出勤。打刻。時刻。時刻
					departureTempo.getFirstAttendance().get().getTimeSheet().getStart().get().setTimeWithDay(
							detectAttendance.getFirstAttendance().get().getStamp().get().getTimeDay().getTimeWithDay());
				} else {
					// 検索できない
					// 打刻応援データに変換する
					OuenWorkTimeSheetOfDailyAttendance convertStamp = this.convertStampingSupport(informationWork,
							detectAttendance.getFirstAttendance().get().getStamp().isPresent() ? detectAttendance.getFirstAttendance().get().getStamp().get().getTimeDay() : null,
							StartAtr.START_OF_SUPPORT);

					// 出退勤の応援。最初の出勤をセットする
					departureTempo.setFirstAttendance(Optional.ofNullable(convertStamp));
				}
		} else {
			// 最初の応援終了を自動セットする
			this.automaticSetFirstSupport(lstOuenWorkTime);
		}
		// 最後の退勤を確認する
		if (detectAttendance.getLastLeave().isPresent()) {
			
			// if Nullじゃない場合
			// 最後の退勤の時刻と同じ時刻の応援データを応援データ一覧から検索して抜き出し
			// 最後の退勤。打刻。時刻。時刻
			Integer time1 = detectAttendance.getLastLeave().get().getStamp().isPresent() ? detectAttendance.getLastLeave().get().getStamp().get().getTimeDay().getTimeWithDay().get().v() : null;
			// 同一打刻の判断基準。同一打刻とみなす範囲
			Integer time2 = judgmentSupport.getSameStampRanceInMinutes().v();
			
				// 最後の退勤の応援データ
			Optional<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeAfter = Optional.empty();
				if(time1 != null) {
					ouenWorkTimeAfter = lstOuenWorkTime.stream().filter(x ->{
						val time = x.getTimeSheet().getEnd().flatMap(c -> c.getTimeWithDay()).map(c -> c.v()).orElse(null);
						if(time == null)
						return false;
						return time1 - time2 < time &&  time< time1 + time2;}).findFirst();
				}
				if (ouenWorkTimeAfter.isPresent()) {
					
					lstOuenWorkTime.remove(ouenWorkTimeAfter.get());
					// 検索できる
					// 出退勤の応援。最後の退勤をセットする
					departureTempo.setLastLeave(ouenWorkTimeAfter);

					// 出退勤の応援。最後の退勤。時間帯。開始。時刻。時刻＝最後の退勤。打刻。時刻。時刻
					if (departureTempo.getLastLeave().get().getTimeSheet().getStart().isPresent()) {
						departureTempo.getLastLeave().get().getTimeSheet().getStart().get().setTimeWithDay(
								detectAttendance.getLastLeave().get().getStamp().get().getTimeDay().getTimeWithDay());
					}
				} else {
					// 検索できない
					// 打刻応援データに変換する
					OuenWorkTimeSheetOfDailyAttendance convertStamp = this.convertStampingSupport(informationWork,
							detectAttendance.getLastLeave().get().getStamp().isPresent() ? detectAttendance.getLastLeave().get().getStamp().get().getTimeDay() : null,
							StartAtr.END_OF_SUPPORT);

					// 出退勤の応援。最初の出勤をセットする
					departureTempo.setLastLeave(Optional.ofNullable(convertStamp));
				}
		} else {
			// 最後の応援開始を自動セットする
			this.automaticSetLastSupport(lstOuenWorkTime);
		}
		// 出退勤の応援を返す
		return departureTempo;
	}

	/**
	 * 打刻応援データに変換する
	 * 
	 * @param informationWork
	 * @param timeDay
	 * @param startAtr
	 * @return
	 */
	public OuenWorkTimeSheetOfDailyAttendance convertStampingSupport(WorkInformationWork informationWork,
			WorkTimeInformation timeDay, StartAtr startAtr) {
		// 新しい「日別実績の応援作業別勤怠時間帯」を作成する
		String cid = AppContexts.user().companyId();

		OuenWorkTimeSheetOfDailyAttendance attendance = null;
		WorkplaceOfWorkEachOuen eachOuen = WorkplaceOfWorkEachOuen.create(informationWork.getWorkplaceId().v(),
				informationWork.getLocationCD() == null ? null
						: new WorkLocationCD(informationWork.getLocationCD().v()));

		WorkContent workContent = WorkContent.create(cid, eachOuen, Optional.empty());
		if (startAtr == StartAtr.START_OF_SUPPORT) {
			TimeSheetOfAttendanceEachOuenSheet timeSheet = TimeSheetOfAttendanceEachOuenSheet.create(new WorkNo(0),
					Optional.ofNullable(timeDay), Optional.empty());
			attendance = OuenWorkTimeSheetOfDailyAttendance.create(0, workContent, timeSheet);
			return attendance;
		}
		TimeSheetOfAttendanceEachOuenSheet timeSheet = TimeSheetOfAttendanceEachOuenSheet.create(new WorkNo(0),
				Optional.empty(), Optional.ofNullable(timeDay));
		attendance = OuenWorkTimeSheetOfDailyAttendance.create(0, workContent, timeSheet);
		return attendance;
	}

	/**
	 * 最初の出勤を自動セットする
	 * 
	 * @param lstOuenWorkTime
	 */
	public void automaticSetFirstSupport(List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime) {
		// 応援データ一覧の先頭の応援データを取得する
		if (lstOuenWorkTime.isEmpty()) {
			return;
		}
		// 開始応援かを確認する
		// 開始の場合
		if (lstOuenWorkTime.get(0).getTimeSheet().getStart().isPresent()) {
			WorkContent workContent = lstOuenWorkTime.get(0).getWorkContent();
			TimeSheetOfAttendanceEachOuenSheet timeSheet = TimeSheetOfAttendanceEachOuenSheet.create(new WorkNo(0),
					Optional.empty(), lstOuenWorkTime.get(0).getTimeSheet().getStart());
			timeSheet.getEnd().get().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.AUTOMATIC_SET);
			// 取得した応援データをベースして終了の応援データ作る
			OuenWorkTimeSheetOfDailyAttendance dailyAttendance = OuenWorkTimeSheetOfDailyAttendance.create(0,
					workContent, timeSheet);
			// 作成した応援データを応援データ一覧の先頭に入れる
			lstOuenWorkTime.add(0, dailyAttendance);
		}
	}

	/**
	 * 最後の退勤を自動セットする
	 * 
	 * @param lstOuenWorkTime
	 */
	public void automaticSetLastSupport(List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime) {
		// 応援データ一覧の末尾の応援データを取得する
		if (lstOuenWorkTime.isEmpty()) {
			return;
		}
		// 終了応援かを確認する
		// 終了の場合
		if (lstOuenWorkTime.get(0).getTimeSheet().getEnd().isPresent()) {
			WorkContent workContent = lstOuenWorkTime.get(0).getWorkContent();
			TimeSheetOfAttendanceEachOuenSheet timeSheet = TimeSheetOfAttendanceEachOuenSheet.create(new WorkNo(0),
					lstOuenWorkTime.get(0).getTimeSheet().getEnd(), Optional.empty());
			timeSheet.getStart().get().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.AUTOMATIC_SET);
			// 取得した応援データをベースして終了の応援データ作る
			OuenWorkTimeSheetOfDailyAttendance dailyAttendance = OuenWorkTimeSheetOfDailyAttendance.create(0,
					workContent, timeSheet);
			// 作成した応援データを応援データ一覧の先頭に入れる
			lstOuenWorkTime.add(0, dailyAttendance);
		}
	}

	/**
	 * ほかの出退勤を補正する
	 * 
	 * @param informationWork
	 * @param workTemporary
	 * @param lstOuenWorkTime
	 * @param judgmentSupport
	 */
	public void correctOtherAttendance(WorkInformationWork informationWork, WorkTemporary workTemporary,
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime,
			JudgmentCriteriaSameStampOfSupport judgmentSupport) {
		// 勤務Temporary。退勤１時刻を確認する
		if (workTemporary.getOneHourLeavingWork().isPresent()) {
			// 応援データ一覧から退勤1時刻と同じ時刻持つ応援データを探す - 応援データ一覧
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenFilter = new ArrayList<>();
			
			if(workTemporary.getOneHourLeavingWork().get().getStamp().isPresent()) {
				lstOuenFilter = lstOuenWorkTime.stream().filter(x -> x
						.getTimeSheet().getEnd().isPresent()
						&& x.getTimeSheet().getEnd().get().getTimeWithDay().get().v() == workTemporary
								.getOneHourLeavingWork().get().getStamp().get().getTimeDay().getTimeWithDay().get().v())
						.collect(Collectors.toList());
			}

			// if 検索できない
			if (lstOuenFilter.isEmpty()) {
				// 打刻応援データに変換する - 打刻応援データ
				OuenWorkTimeSheetOfDailyAttendance convertStamp = this.convertStampingSupport(informationWork,
						workTemporary.getOneHourLeavingWork().get().getStamp().isPresent() ? workTemporary.getOneHourLeavingWork().get().getStamp().get().getTimeDay() : null,
						StartAtr.END_OF_SUPPORT);
				// 打刻応援データを応援データ一覧にいれる - 応援データ一覧
				lstOuenWorkTime = this.getStampedSupportData(true, judgmentSupport, lstOuenFilter, convertStamp,
						StartAtr.END_OF_SUPPORT);
			}
		}
		// 勤務Temporary。出勤２時刻を確認する
		if (workTemporary.getTwoHoursWork().isPresent()) {
			// 応援データ一覧から出勤２時刻と同じ時刻持つ応援データを探す
			if (workTemporary.getTwoHoursWork().isPresent()) {
				List<OuenWorkTimeSheetOfDailyAttendance> lstOuenFilter = lstOuenWorkTime.stream()
						.filter(x -> x.getTimeSheet().getEnd().get().getTimeWithDay().get().v() == workTemporary
								.getTwoHoursWork().get().getStamp().get().getTimeDay().getTimeWithDay().get().v())
						.collect(Collectors.toList());

				// if 検索できない
				if (lstOuenFilter.isEmpty()) {
					// 打刻応援データに変換する - 打刻応援データ
					OuenWorkTimeSheetOfDailyAttendance convertStamp = this.convertStampingSupport(informationWork,
							workTemporary.getTwoHoursWork().get().getStamp().isPresent() ? workTemporary.getTwoHoursWork().get().getStamp().get().getTimeDay() : null,
							StartAtr.START_OF_SUPPORT);
					// 打刻応援データを応援データ一覧にいれる - 応援データ一覧
					lstOuenWorkTime = this.getStampedSupportData(true, judgmentSupport, lstOuenFilter, convertStamp,
							StartAtr.START_OF_SUPPORT);
				}
			}
		}
	}

	/**
	 * 応援データを並びかえる （時刻の昇順）
	 * 
	 * @param lstOuenWorkTime
	 * @return
	 */
	public List<OuenWorkTimeSheetOfDailyAttendance> rearrangeSupportData(
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenWorkTime) {
		Comparator<OuenWorkTimeSheetOfDailyAttendance> comparator = new Comparator<OuenWorkTimeSheetOfDailyAttendance>() {

			@Override
			public int compare(OuenWorkTimeSheetOfDailyAttendance arg0, OuenWorkTimeSheetOfDailyAttendance arg1) {
				// TODO Auto-generated method stub
				Optional<TimeWithDayAttr> start1 = arg0.getTimeSheet().getStart().isPresent()
						? arg0.getTimeSheet().getStart().get().getTimeWithDay()
						: Optional.empty();
				Optional<TimeWithDayAttr> end1 = arg0.getTimeSheet().getEnd().isPresent()
						? arg0.getTimeSheet().getEnd().get().getTimeWithDay()
						: Optional.empty();

				Optional<TimeWithDayAttr> start2 = arg1.getTimeSheet().getStart().isPresent()
						? arg1.getTimeSheet().getStart().get().getTimeWithDay()
						: Optional.empty();
				Optional<TimeWithDayAttr> end2 = arg1.getTimeSheet().getEnd().isPresent()
						? arg1.getTimeSheet().getEnd().get().getTimeWithDay()
						: Optional.empty();
				// 両方が開始の場合開始使う
				if (start1.isPresent() && start2.isPresent()) {
					return start1.get().v().compareTo(start2.get().v());
				}
				// 一方が開始、一方が終了の場合、開始と終了を使う ・同じ時刻の場合、終了は前にする
				if (start1.isPresent() && end2.isPresent() && !start2.isPresent()) {
					if (start1.get().v() != end2.get().v())
						return start1.get().v().compareTo(end2.get().v());
					else
						return end2.get().v() - start1.get().v();
				}

				if (start2.isPresent() && end1.isPresent() && !start1.isPresent()) {
					if (start2.get().v() != end1.get().v())
						return start2.get().v().compareTo(end1.get().v());
					else
						return end1.get().v() - start2.get().v();
				}
				// 両方が終了の場合、終了使う
				if (!start1.isPresent() && !start2.isPresent() && end1.isPresent() && end2.isPresent()) {
					return end1.get().v().compareTo(end2.get().v());
				}
				return 0;
			}
		};

		Collections.sort(lstOuenWorkTime, comparator);
		return lstOuenWorkTime;
	}

	/**
	 * 最初の出勤と最後の退勤を補正する
	 * 
	 * @param lstOuenSort
	 * @param suportDataFirtAndLast
	 * @return
	 */
	public List<OuenWorkTimeSheetOfDailyAttendance> compensateFirstLastAttendance(
			List<OuenWorkTimeSheetOfDailyAttendance> lstOuenSort,
			SupportAttendanceDepartureTempo suportDataFirtAndLast) {
		// 出退勤の応援。最初の出勤を確認する
		if (suportDataFirtAndLast.getFirstAttendance().isPresent()) {
			lstOuenSort.add(0, suportDataFirtAndLast.getFirstAttendance().get());
		}
		// 出退勤の応援。最後の退勤を確認する
		if (suportDataFirtAndLast.getLastLeave().isPresent()) {
			lstOuenSort.add(suportDataFirtAndLast.getLastLeave().get());
		}
		return lstOuenSort;
	}

	@AllArgsConstructor
	private static class GetSupportDataJudgedSameImpl implements GetSupportDataJudgedSameDS.Required {

		@Inject
		private JudCriteriaSameStampOfSupportRepo repo;

		@Override
		public JudgmentCriteriaSameStampOfSupport getCriteriaSameStampOfSupport() {
			String cid = AppContexts.user().companyId();
			return repo.get(cid);
		}
	}
}
