package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.ReflectStampDailyAttdOutput;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;

/**
 * DS : 打刻データがいつの日別実績に反映するか
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻データがいつの日別実績に反映するか
 * 
 * @author tutk
 *
 */
public class ReflectDataStampDailyService {
	/**
	 * [1] 判断する
	 * 
	 * @param require
	 * @param employeeId
	 * @param stamp
	 * @return 反映対象日
	 */
	public static Optional<ReflectDateAndEmpID> getJudgment(Require require, String cid, ContractCode contractCode,
			Stamp stamp) {
		Stamp stampCheck = stamp.clone();
		Optional<String> employeeId = require.getByCardNoAndContractCode(contractCode, stampCheck.getCardNumber())
				.map(x -> x.getEmployeeId());
		if (!employeeId.isPresent())
			return Optional.empty();
		GeneralDate date = stampCheck.getStampDateTime().toDate();
		DatePeriod period = new DatePeriod(date.addDays(-2), date.addDays(1));
		Optional<GeneralDate> reflectDate =  period.stream().filter(c -> require.createDailyDomAndReflectStamp(cid, employeeId.get(), c, stampCheck)
				.map(x -> stampCheck.getImprintReflectionStatus().isReflectedCategory()).orElse(false)).findFirst();
		return reflectDate.map(dateProcess -> new ReflectDateAndEmpID(dateProcess, employeeId.get()));
	}

	public static interface Require {
		// [R-1]打刻カードを取得する
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber);
		//TemporarilyReflectStampDailyAttd
		public Optional<ReflectStampDailyAttdOutput> createDailyDomAndReflectStamp(String cid, String employeeId, GeneralDate date,
				Stamp stamp);
	}

}
