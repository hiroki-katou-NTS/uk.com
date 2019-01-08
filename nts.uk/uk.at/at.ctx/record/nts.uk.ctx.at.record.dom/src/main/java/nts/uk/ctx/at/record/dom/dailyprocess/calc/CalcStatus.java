package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;

/**
 * 計算結果のステータスを管理するためのクラス
 * @author keisuke_hoshina
 *
 */
@Getter
public class CalcStatus {
	//中断、正常終了のステータス
	private ProcessState processState;
	
	//実績
	private List<IntegrationOfDaily> integrationOfDailyList;

	/**
	 * Constructor 
	 */
	public CalcStatus(ProcessState processState, List<IntegrationOfDaily> integrationOfDailyList) {
		super();
		this.processState = processState;
		this.integrationOfDailyList = integrationOfDailyList;
	}
}
