package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * インターフェイス：テスト用バイナリデータ出力
 * @author shuichi_ishida
 */
public interface OutputBinaryForTestInterface {

	void init1(String cid);

	void init2(String empId, DatePeriod period);

	void init3(String empId, YearMonth yearMonth);
}
