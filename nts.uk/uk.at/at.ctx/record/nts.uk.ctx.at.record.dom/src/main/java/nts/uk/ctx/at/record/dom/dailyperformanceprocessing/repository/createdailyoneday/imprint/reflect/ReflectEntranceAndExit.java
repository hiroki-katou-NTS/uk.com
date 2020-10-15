package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectondomain.ReflectionInformation;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectone.ReflectOneEntranceAndExit;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;

/**
 * 反映する (new_2020) for : 入退門反映する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.アルゴリズム.一日の日別実績の作成処理（New）.打刻反映.入退門・PC反映する.反映する.反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectEntranceAndExit {
	@Inject
	private ReflectOneEntranceAndExit reflectOneEntranceAndExit;
	
	/**
	 * 
	 * @param reflectionInformation
	 * @param stamp
	 * @param ymd  処理中の年月日
	 */
	public Optional<WorkStamp> reflect(Optional<ReflectionInformation> reflectionInformation,Stamp stamp,GeneralDate ymd) {
		//打刻区分を確認する
		if(stamp.getType().getChangeClockArt() == ChangeClockArt.BRARK ||   //退門ORPCログオフの場合
				   stamp.getType().getChangeClockArt() == ChangeClockArt.PC_LOG_OFF ) {
			//片方反映する
			return reflectOneEntranceAndExit.reflect(reflectionInformation.isPresent() ? reflectionInformation.get().getEnd():Optional.empty(), stamp, ymd);
		
		}else if(stamp.getType().getChangeClockArt() == ChangeClockArt.OVER_TIME ||  //入門ORPCログオンの場合 
				   stamp.getType().getChangeClockArt() == ChangeClockArt.PC_LOG_ON ) {
			//片方反映する
			return reflectOneEntranceAndExit.reflect(reflectionInformation.isPresent() ? reflectionInformation.get().getStart():Optional.empty(), stamp, ymd);
		}
		return Optional.empty();
		
	}
}
