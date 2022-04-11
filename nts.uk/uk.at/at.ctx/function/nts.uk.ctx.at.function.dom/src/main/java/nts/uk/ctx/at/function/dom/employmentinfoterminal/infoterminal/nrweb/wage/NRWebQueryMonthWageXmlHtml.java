package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.wage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageAndEmployeeId;
import nts.uk.ctx.at.function.dom.adapter.estimateamount.EstimateAmountDetailImport;
import nts.uk.ctx.at.function.dom.adapter.estimateamount.EstimateAmountSettingImport;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRType;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;

/**
 * @author thanh_nx
 *
 *         NRWeb照会月間賃金XMLとHTMLに変換
 */
public class NRWebQueryMonthWageXmlHtml {

	// 情報処理
	public static String process(Require require, NRWebQuerySidDateParameter param) {
		// ＄NRWeb照会月間賃金
		NRWebMonthWageAndEmployeeId queryResult = GetNRWebQueryMonthWage.process(require, param);
		// 目安金額設定
		List<EstimateAmountSettingImport> dataAmountSetting = require.getDataAmountSetting(param.getCid(),
				param.getSid(), queryResult.getMonthWage().getPeriod().end());
		List<EstimateAmountDetailImport> dataAmountSettingMonth = dataAmountSetting.stream()
				.filter(x -> x.getMonthlyAmountDetail().isPresent()).map(x -> x.getMonthlyAmountDetail().get())
				.collect(Collectors.toList());

		// $目安金額
		long maxAmountSetting = dataAmountSettingMonth.stream().map(x -> x.getAmount()).sorted((x, y) -> y - x)
				.findFirst().orElse(0);
		queryResult.getMonthWage().setMeasureAmount(maxAmountSetting);

		if (param.getNrWebQuery().getType().isPresent()
				&& param.getNrWebQuery().getType().get().equals(NRType.XML.value)) {
			// XMLを作る(＄NRWeb照会メニュー一覧)
			return queryResult.getMonthWage().createXml(NRWebQueryMenuName.MONTH_WAGE,
							param.getNrWebQuery().getYmFormat(), Optional.empty(), dataAmountSettingMonth);
		} else {
			// HTMLを作る(＄NRWeb照会メニュー一覧)
			return queryResult.getMonthWage().createHtml(NRWebQueryMenuName.MONTH_WAGE,
					param.getNrWebQuery().getYmFormat(),  Optional.empty(), dataAmountSettingMonth);
		}
	}

	public static interface Require extends GetNRWebQueryMonthWage.Require {

		// 目安金額を取得
		// GetEstimateAmountAdapter
		public List<EstimateAmountSettingImport> getDataAmountSetting(String cid, String sid, GeneralDate date);
	}
}
