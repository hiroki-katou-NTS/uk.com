package nts.uk.ctx.at.record.pubimp.workrecord.closurestatus;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.export.GetCalcStartForNextLeaveGrant;
import nts.uk.ctx.at.record.pub.workrecord.closurestatus.GetCalcStartForNextLeaveGrantPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 次回年休付与を計算する開始日を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetCalcStartForNextLeaveGrantPubImpl implements GetCalcStartForNextLeaveGrantPub {

	/** 次回年休付与を計算する開始日を取得する */
	@Inject
	private GetCalcStartForNextLeaveGrant getCalcStart;
	@Inject
	private ClosureStatusManagementRepository closureStatusRepo;
	/** 次回年休付与を計算する開始日を取得する */
	@Override
	public GeneralDate algorithm(String employeeId) {
		return this.getCalcStart.algorithm(employeeId);
	}

	@Override
	public Optional<DatePeriod> closureDatePeriod(String sid) {
		Optional<ClosureStatusManagement> optClosureStatus = closureStatusRepo.getLatestByEmpId(sid);
		if(!optClosureStatus.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(optClosureStatus.get().getPeriod());
	}
}
