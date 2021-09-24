package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
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

	@Inject
	private RecordDomRequireService requireService;

	/** 次回年休付与情報を取得する */
	@Override
	public Optional<NextAnnualLeaveGrant> algorithm(String companyId, String employeeId, GeneralDate closureStart, GeneralDate entryDate,
			GeneralDate criteriaDate, String grantTableCode, Optional<LimitedTimeHdTime> contractTime) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		return CalcNextAnnLeaGrantInfo.algorithm(require, cacheCarrier,
				companyId, employeeId, closureStart, entryDate, criteriaDate, grantTableCode, contractTime);
	}
}
