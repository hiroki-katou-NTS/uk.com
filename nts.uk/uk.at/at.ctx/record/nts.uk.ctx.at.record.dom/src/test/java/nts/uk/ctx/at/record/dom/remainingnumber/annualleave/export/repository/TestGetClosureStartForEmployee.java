package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

/**
 * 社員に対応する締め開始日を取得する
 * @author masaaki_jinno
 *
 */
public class TestGetClosureStartForEmployee implements GetClosureStartForEmployee{

	// 実行時に集計期間開始日をセットする
	public GeneralDate startDate;
	
	/** 社員に対応する締め開始日を取得する */
	@Override
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<GeneralDate> algorithm(String employeeId) {
		return Optional.ofNullable(startDate);
		
	}
	
}
