package nts.uk.ctx.at.record.dom.workrecord.closurestatus.export;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;

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
}
