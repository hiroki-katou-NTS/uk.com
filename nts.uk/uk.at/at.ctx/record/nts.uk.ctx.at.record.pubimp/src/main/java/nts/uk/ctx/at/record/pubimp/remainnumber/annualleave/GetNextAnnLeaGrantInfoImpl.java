package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.GetNextAnnLeaGrantInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.CalcNextAnnLeaGrantInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

/**
 * 実装：次回年休付与情報を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetNextAnnLeaGrantInfoImpl implements GetNextAnnLeaGrantInfo {

	/** 締め開始日と年休付与テーブルから次回年休付与を計算する */
	@Inject
	private CalcNextAnnLeaGrantInfo calcNextAnnLeaGrantInfo;
	
	/** 次回年休付与情報を取得する */
	@Override
	public Optional<NextAnnualLeaveGrant> algorithm(String companyId, GeneralDate closureStart, GeneralDate entryDate,
			GeneralDate criteriaDate, String grantTableCode, Optional<LimitedTimeHdTime> contractTime) {
		
		return this.calcNextAnnLeaGrantInfo.algorithm(
				companyId, closureStart, entryDate, criteriaDate, grantTableCode, contractTime);
	}
}
