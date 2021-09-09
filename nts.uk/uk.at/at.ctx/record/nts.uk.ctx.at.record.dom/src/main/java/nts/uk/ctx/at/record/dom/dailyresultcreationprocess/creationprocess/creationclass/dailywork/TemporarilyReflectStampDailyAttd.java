package nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.ReflectGoingOutAndReturn;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.entranceandexit.EntranceAndExit;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect.ReflectStampSupport;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectpclogoninfo.ReflectPcLogonInfo;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectleavingwork.ReflectLeavingWork;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflecttemporarystartend.ReflectTemporaryStartEnd;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.ReflectWork;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

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
	
	@Inject
	private ReflectStampSupport reflectStampSupport;
	/**
	 * 打刻を反映する
	 */
	public List<ErrorMessageInfo> reflectStamp(String companyId, Stamp stamp, StampReflectRangeOutput stampReflectRangeOutput,
			IntegrationOfDaily integrationOfDaily, ChangeDailyAttendance changeDailyAtt) {
		
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		
		switch(stamp.getType().getChangeClockArt()) {
		case GOING_TO_WORK: //出勤
			// 組み合わせ区分＝直行？
			if (stamp.getType().getSetPreClockArt() != SetPreClockArt.DIRECT) {
				// 出勤を反映する (Phản ánh 出勤)
				reflectWork.reflectWork(companyId, stamp, stampReflectRangeOutput, integrationOfDaily);
				if (!stamp.getImprintReflectionStatus().isReflectedCategory()) {
					return listErrorMessageInfo;
				}
			}else {
				/** 直行区分=ONにする */
				integrationOfDaily.getWorkInformation().setGoStraightAtr(NotUseAttribute.Use);
				//打刻。反映区分＝反映済み
				stamp.setReflectedCategory(true);
			}
			
			changeDailyAtt.setAttendance(true);
			
			break;
		case WORKING_OUT: // 退勤
			// 組み合わせ区分＝直帰？
			if (stamp.getType().getSetPreClockArt() != SetPreClockArt.BOUNCE) {
				// 退勤を反映する （Phản ánh 退勤）
				reflectLeavingWork.reflectLeaving(companyId, stamp, stampReflectRangeOutput, integrationOfDaily);
				if (!stamp.getImprintReflectionStatus().isReflectedCategory()) {
					return listErrorMessageInfo;
				}
			}else {
				/** 直帰区分=ONにする */
				integrationOfDaily.getWorkInformation().setBackStraightAtr(NotUseAttribute.Use);
				//打刻。反映区分＝反映済み
				stamp.setReflectedCategory(true);
			}

			changeDailyAtt.setAttendance(true);
			
			break;
		case GO_OUT://外出 or 戻り
		case RETURN:
			//外出・戻りを反映する (Phản ánh 外出・戻り)
			reflectGoingOutAndReturn.reflect(companyId, stamp, stampReflectRangeOutput, integrationOfDaily);
			if (!stamp.getImprintReflectionStatus().isReflectedCategory()) {
				return listErrorMessageInfo;
			}
			break;
		case TEMPORARY_WORK://臨時開始 or 臨時終了
		case TEMPORARY_LEAVING:
			reflectTemporaryStartEnd.reflect(companyId, stamp, stampReflectRangeOutput, integrationOfDaily);
			if (!stamp.getImprintReflectionStatus().isReflectedCategory()) {
				return listErrorMessageInfo;
			}
			break;
		case BRARK: //退門Or入門
		case OVER_TIME:
			entranceAndExit.entranceAndExit(companyId, stamp, stampReflectRangeOutput, integrationOfDaily);
			if (!stamp.getImprintReflectionStatus().isReflectedCategory()) {
				return listErrorMessageInfo;
			}
			break;
		case PC_LOG_ON: //PCログオフOrPcログオン
		case PC_LOG_OFF:
			//PCログオン情報反映す
			reflectPcLogonInfo.reflect(companyId, stamp, stampReflectRangeOutput, integrationOfDaily);
			if (!stamp.getImprintReflectionStatus().isReflectedCategory()) {
				return listErrorMessageInfo;
			}
			break;
		case START_OF_SUPPORT:
		case END_OF_SUPPORT:	
			//応援開始 OR 応援終了　OR　応援出勤　OR 臨時+応援出勤
		reflectStampSupport.reflect(companyId, stamp, integrationOfDaily, stampReflectRangeOutput);
			return listErrorMessageInfo;
		default :
			return listErrorMessageInfo;
		}
		
		// 勤務情報を反映する
		listErrorMessageInfo.addAll(reflectWorkInformation.reflect(true, true, stamp, integrationOfDaily, changeDailyAtt));
		
		return listErrorMessageInfo;
		
	}
}
