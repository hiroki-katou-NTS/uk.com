package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：締め開始日と年休付与テーブルから次回年休付与を計算する
 * @author shuichu_ishida
 */
@Stateless
public class CalcNextAnnLeaGrantInfoImpl implements CalcNextAnnLeaGrantInfo {

	/** 次回年休付与を取得する */
	@Inject
	private GetNextAnnualLeaveGrant getNextAnnualLeaveGrant;
	
	/** 締め開始日と年休付与テーブルから次回年休付与を計算する */
	@Override
	public Optional<NextAnnualLeaveGrant> algorithm(String companyId, GeneralDate closureStart, GeneralDate entryDate,
			GeneralDate criteriaDate, String grantTableCode, Optional<LimitedTimeHdTime> contractTime) {
		
		// 「入社年月日」と「締め開始日」を比較　→　次回年休付与計算開始日
		GeneralDate nextGrant = closureStart;
		if (entryDate.after(closureStart)) nextGrant = entryDate;
		
		// 次回年休付与計算開始日+1日
		if (nextGrant.before(GeneralDate.max())) nextGrant = nextGrant.addDays(1);
		
		// 次回年休付与を取得する
		val results = this.getNextAnnualLeaveGrant.algorithm(companyId, grantTableCode, entryDate, criteriaDate,
				new DatePeriod(nextGrant, GeneralDate.max()), true);
		
		// 次回年休付与を返す
		if (results.size() == 0) return Optional.empty();
		return Optional.of(results.get(0));
	}
}
