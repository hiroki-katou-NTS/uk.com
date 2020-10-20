package nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.ReflectGoingOutAndReturn;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.entranceandexit.EntranceAndExit;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectpclogoninfo.ReflectPcLogonInfo;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectleavingwork.ReflectLeavingWork;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflecttemporarystartend.ReflectTemporaryStartEnd;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.ReflectWork;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * «DomainService» 打刻データを日別勤怠に仮反映する
 * 打刻を反映する (new_2020)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.作成用クラス.日別作成WORK.打刻データを日別勤怠に仮反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TemporarilyReflectStampDailyAttd {
	@Inject
	private ReflectWork reflectWork;
	
	@Inject
	private ReflectWorkInformation reflectWorkInformation;
	
	@Inject
	private ReflectLeavingWork reflectLeavingWork;
	
	@Inject
	private ReflectGoingOutAndReturn reflectGoingOutAndReturn;
	
	@Inject
	private ReflectTemporaryStartEnd reflectTemporaryStartEnd;
	
	@Inject
	private EntranceAndExit entranceAndExit;
	
	@Inject
	private ReflectPcLogonInfo reflectPcLogonInfo;
	/**
	 * 打刻を反映する
	 */
	public List<ErrorMessageInfo> reflectStamp(Stamp stamp,StampReflectRangeOutput stampReflectRangeOutput,IntegrationOfDaily integrationOfDaily) {
		String companyId = AppContexts.user().companyId();
		ChangeClockArt type = stamp.getType().getChangeClockArt();
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		switch( type) {
		case GOING_TO_WORK: //出勤
			// 組み合わせ区分＝直行？
			if (stamp.getType().getSetPreClockArt() != SetPreClockArt.DIRECT) {
				// 出勤を反映する (Phản ánh 出勤)
				reflectWork.reflectWork(stamp, stampReflectRangeOutput,
						integrationOfDaily);
				if (!stamp.isReflectedCategory()) {
					return listErrorMessageInfo;
				}
			}else {
				//打刻。反映区分＝反映済み
				stamp.setReflectedCategory(true);
			}
			// 勤務情報を反映する
			listErrorMessageInfo.addAll(reflectWorkInformation.reflect(true, true, companyId, integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), stamp, integrationOfDaily));

			break;
		case WORKING_OUT: // 退勤
			// 組み合わせ区分＝直帰？
			if (stamp.getType().getSetPreClockArt() != SetPreClockArt.BOUNCE) {
				// 退勤を反映する （Phản ánh 退勤）
				reflectLeavingWork.reflectLeaving(stamp,
						stampReflectRangeOutput, integrationOfDaily);
				if (!stamp.isReflectedCategory()) {
					return listErrorMessageInfo;
				}
			}else {
				//打刻。反映区分＝反映済み
				stamp.setReflectedCategory(true);
			}
			// 勤務情報を反映する
			listErrorMessageInfo.addAll(reflectWorkInformation.reflect(false, true, companyId, integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), stamp, integrationOfDaily));
			break;
		case GO_OUT://外出 or 戻り
		case RETURN:
			//外出・戻りを反映する (Phản ánh 外出・戻り)
			reflectGoingOutAndReturn.reflect(stamp, stampReflectRangeOutput, integrationOfDaily);
			if (!stamp.isReflectedCategory()) {
				return listErrorMessageInfo;
			}
			// 勤務情報を反映する
			listErrorMessageInfo.addAll(reflectWorkInformation.reflect(false, false, companyId, integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), stamp, integrationOfDaily));
			break;
		case TEMPORARY_WORK://臨時開始 or 臨時終了
		case TEMPORARY_LEAVING:
			reflectTemporaryStartEnd.reflect(stamp, stampReflectRangeOutput, integrationOfDaily);
			if (!stamp.isReflectedCategory()) {
				return listErrorMessageInfo;
			}
			// 勤務情報を反映する
			listErrorMessageInfo.addAll(reflectWorkInformation.reflect(false, false, companyId, integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), stamp, integrationOfDaily));
			break;
		case BRARK: //退門Or入門
		case OVER_TIME:
			entranceAndExit.entranceAndExit(stamp, stampReflectRangeOutput, integrationOfDaily);
			if (!stamp.isReflectedCategory()) {
				return listErrorMessageInfo;
			}
			// 勤務情報を反映する
			listErrorMessageInfo.addAll(reflectWorkInformation.reflect(false, false, companyId, integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), stamp, integrationOfDaily));
			break;
		case PC_LOG_ON: //PCログオフOrPcログオン
		case PC_LOG_OFF:
			//PCログオン情報反映す
			reflectPcLogonInfo.reflect(stamp, stampReflectRangeOutput, integrationOfDaily);
			if (!stamp.isReflectedCategory()) {
				return listErrorMessageInfo;
			}
			// 勤務情報を反映する
			listErrorMessageInfo.addAll(reflectWorkInformation.reflect(false, false, companyId, integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), stamp, integrationOfDaily));
			break;
		default :
			//応援開始 OR 応援終了　OR　応援出勤　OR 臨時+応援出勤
			//tạm thời không làm
			break;
		}
		return listErrorMessageInfo;
		
	}
}
