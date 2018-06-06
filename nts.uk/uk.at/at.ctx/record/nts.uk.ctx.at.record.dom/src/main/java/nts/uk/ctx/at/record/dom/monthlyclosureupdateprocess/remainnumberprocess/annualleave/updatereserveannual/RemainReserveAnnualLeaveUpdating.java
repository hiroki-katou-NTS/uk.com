package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.updatereserveannual;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantTimeRemainHistRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 積立年休残数更新
 *
 */

@Stateless
public class RemainReserveAnnualLeaveUpdating {

	@Inject
	private RervLeaGrantRemDataRepository reserveLeaveRemainRepo;

	@Inject
	private RsvLeaveGrantRemainHistRepository reserveLeaveRemainHistRepo;

	@Inject
	private RsvLeaveGrantTimeRemainHistRepository rsvLeaveTimeRemainHistRepo;

	/**
	 * 積立年休残数更新
	 * 
	 * @param output
	 * @param period
	 * @param empId
	 */
	public void updateReservedAnnualLeaveRemainNumber(AggrResultOfReserveLeave output,
			AggrPeriodEachActualClosure period, String empId) {
		updateNumberOfRemainingLeaveData(output, period, empId);
	}

	/**
	 * 積休付与残数データの更新
	 * 
	 * @param output
	 * @param period
	 * @param empId
	 */
	private void updateNumberOfRemainingLeaveData(AggrResultOfReserveLeave output, AggrPeriodEachActualClosure period,
			String empId) {
		String cid = AppContexts.user().companyId();
		List<ReserveLeaveGrantRemainingData> listData = reserveLeaveRemainRepo.findNotExp(empId, cid);
		if (!listData.isEmpty()) {
			for (ReserveLeaveGrantRemainingData data : listData) {
				ReserveLeaveGrantRemainHistoryData hist = new ReserveLeaveGrantRemainHistoryData(data,
						period.getYearMonth(), period.getClosureId(), period.getClosureDate());
				reserveLeaveRemainHistRepo.addOrUpdate(hist, cid);
			}
		}
		updateProcess(output.getAsOfPeriodEnd());
		updateRsvLeaveTimeRemainHistProcess(
				output.getAsOfGrant().isPresent() ? output.getAsOfGrant().get() : Collections.emptyList());
	}

	/**
	 * 積立年休付与残数データ更新処理
	 * 
	 * @param ReserveLeaveInfo
	 */
	private void updateProcess(ReserveLeaveInfo info) {
		String cId = AppContexts.user().companyId();
		List<ReserveLeaveGrantRemainingData> listData = info.getGrantRemainingNumberList();
		for (ReserveLeaveGrantRemainingData data : listData) {
			Optional<ReserveLeaveGrantRemainingData> optDomain = reserveLeaveRemainRepo.find(data.getEmployeeId(),
					data.getGrantDate(), data.getDeadline());
			if (optDomain.isPresent()) {
				ReserveLeaveGrantRemainingData domain = optDomain.get();
				ReserveLeaveGrantRemainingData updateDomain = ReserveLeaveGrantRemainingData.createFromJavaType(
						domain.getRsvLeaID(), domain.getEmployeeId(), domain.getGrantDate(), domain.getDeadline(),
						data.getExpirationStatus().value, data.getRegisterType().value,
						data.getDetails().getGrantNumber().v(), data.getDetails().getUsedNumber().getDays().v(),
						data.getDetails().getUsedNumber().getOverLimitDays().isPresent()
								? data.getDetails().getUsedNumber().getOverLimitDays().get().v() : null,
						data.getDetails().getRemainingNumber().v());
				reserveLeaveRemainRepo.update(updateDomain);
			} else {
				ReserveLeaveGrantRemainingData addDomain = ReserveLeaveGrantRemainingData.createFromJavaType(
						IdentifierUtil.randomUniqueId(), data.getEmployeeId(), data.getGrantDate(), data.getDeadline(),
						data.getExpirationStatus().value, data.getRegisterType().value,
						data.getDetails().getGrantNumber().v(), data.getDetails().getUsedNumber().getDays().v(),
						data.getDetails().getUsedNumber().getOverLimitDays().isPresent()
								? data.getDetails().getUsedNumber().getOverLimitDays().get().v() : null,
						data.getDetails().getRemainingNumber().v());
				reserveLeaveRemainRepo.add(addDomain, cId);
			}
		}
	}

	/**
	 * 積休付与時点残数履歴データ更新処理
	 * 
	 * @param listInfo
	 */
	private void updateRsvLeaveTimeRemainHistProcess(List<ReserveLeaveInfo> listInfo) {
		String cid = AppContexts.user().companyId();
		for (ReserveLeaveInfo info : listInfo) {
			List<ReserveLeaveGrantRemainingData> listData = info.getGrantRemainingNumberList();
			for (ReserveLeaveGrantRemainingData data : listData) {
				ReserveLeaveGrantTimeRemainHistoryData hist = new ReserveLeaveGrantTimeRemainHistoryData(data,
						info.getYmd());
				rsvLeaveTimeRemainHistRepo.add(hist, cid);
			}

		}
	}

}
