package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectframe;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect.ReflectEntranceAndExit;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectondomain.ReflectionInformation;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectStampOuput;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.CheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.OutputCheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.StampReflectRangeOutput;

/**
 * 枠反映する (new_2020) in 入退門反映する
 * 
 * @author tutk
 *
 */
@Stateless
public class ReflectFrameEntranceAndExit {

	@Inject
	private CheckRangeReflectAttd checkRangeReflectAttd;
	
	@Inject
	private ReflectEntranceAndExit reflectEntranceAndExit;
	/**
	 * 
	 * @param listReflectionInformation
	 * @param stamp
	 * @param stampReflectRangeOutput
	 * @param integrationOfDaily
	 */
	public ReflectStampOuput reflect(List<ReflectionInformation> listReflectionInformation, Stamp stamp,
			StampReflectRangeOutput stampReflectRangeOutput, IntegrationOfDaily integrationOfDaily) {
		// 反映範囲か確認する
		OutputCheckRangeReflectAttd outputCheckRangeReflectAttd = checkRangeReflectAttd.checkRangeReflectAttd(stamp,
				stampReflectRangeOutput, integrationOfDaily);
		
		switch (outputCheckRangeReflectAttd) {
		case FIRST_TIME:
			//反映情報（Temporary）を求める
			Optional<ReflectionInformation> reflectionInformation1 = listReflectionInformation.stream().filter(x->x.getFrameNo()== 1).findFirst();
			if(reflectionInformation1.isPresent()) {
				//反映する
				reflectEntranceAndExit.reflect(reflectionInformation1.get(), stamp, integrationOfDaily.getYmd());
			}
			break;

		case SECOND_TIME:
			//反映情報（Temporary）を求める
			Optional<ReflectionInformation> reflectionInformation2 = listReflectionInformation.stream().filter(x->x.getFrameNo()== 2).findFirst();
			if(reflectionInformation2.isPresent()) {
				//反映する
				reflectEntranceAndExit.reflect(reflectionInformation2.get(), stamp, integrationOfDaily.getYmd());
			}
			break;

		default:// OUT_OF_RANGE
			return ReflectStampOuput.NOT_REFLECT;
		}
		return ReflectStampOuput.REFLECT;
	}

}
