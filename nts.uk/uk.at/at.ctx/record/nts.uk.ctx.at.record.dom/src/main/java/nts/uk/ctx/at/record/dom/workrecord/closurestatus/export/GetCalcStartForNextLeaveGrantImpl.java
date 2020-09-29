package nts.uk.ctx.at.record.dom.workrecord.closurestatus.export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;

/**
 * 次回年休付与を計算する開始日を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetCalcStartForNextLeaveGrantImpl implements GetCalcStartForNextLeaveGrant {

	/** 締め状態管理 */
	@Inject
	private ClosureStatusManagementRepository closureStatusMngRepo;
	
	/** 次回年休付与を計算する開始日を取得する */
	@Override
	public GeneralDate algorithm(String employeeId) {
		
		// 「締め状態管理」を取得する
		val closureStatusMngOpt = this.closureStatusMngRepo.getLatestByEmpId(employeeId);
		if (closureStatusMngOpt.isPresent()){
			val closureStatusMng = closureStatusMngOpt.get();
			
			// 期間‥終了日+1日を返す
			GeneralDate result = closureStatusMng.getPeriod().end();
			if (result.before(GeneralDate.max())) result = result.addDays(1);
			return result;
		}

		// 「システム日付」を返す
		return GeneralDate.today();
	}

	/** 次回年休付与を計算する開始日を取得する  - đối ứng cps003*/
	@Override
	public Map<String, GeneralDate> algorithm(List<String> sids) {
		Map<String, GeneralDate> result = new HashMap<>();
		// 「締め状態管理」を取得する
		Map<String, ClosureStatusManagement> closureStatusMngMap = this.closureStatusMngRepo.getLatestBySids(sids);
		sids.stream().forEach(sid -> {
			ClosureStatusManagement closureStatusMng = closureStatusMngMap.get(sid);
			if (closureStatusMng != null) {
				// 期間‥終了日+1日を返す
				GeneralDate date = closureStatusMng.getPeriod().end();
				if (date.before(GeneralDate.max()))
					date = date.addDays(1);
				result.put(sid, date);
			} else {
				// 「システム日付」を返す
				result.put(sid, GeneralDate.today());
			}
		});
		return result;
	}
}
