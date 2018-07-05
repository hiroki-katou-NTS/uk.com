package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCreateDataInputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class InterimRemainOfMonthProccessImpl implements InterimRemainOfMonthProccess{
	@Inject
	private RemainCreateInforByRecordData recordDataService;
	@Inject
	private InterimRemainOffPeriodCreateData periodCreateDataService;
	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainDataMng(String cid, String sid,
			DatePeriod dateData) {
		//(Imported)「残数作成元情報(実績)」を取得する
		List<RecordRemainCreateInfor> lstRecordData = recordDataService.lstRecordRemainData(cid, sid, dateData);
		//アルゴリズム「指定期間の暫定残数管理データを作成する」を実行する
		InterimRemainCreateDataInputPara inputPara = new InterimRemainCreateDataInputPara(cid, sid, dateData, lstRecordData, Collections.emptyList(), Collections.emptyList(), false);
		return periodCreateDataService.createInterimRemainDataMng(inputPara);
	}
}
