package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.wage;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
 *         NRWeb照会年間賃金XMLとHTMLに変換
 */
public class NRWebQueryAnnualWageXmlHtml {

	// 情報処理
	public static Response process(Require require, NRWebQuerySidDateParameter param) {
		// ＄NRWeb照会年間賃金
		NRWebMonthWageAndEmployeeId queryResult = GetNRWebQueryAnnualWage.process(require, param);
		// 目安金額設定
		List<EstimateAmountSettingImport> dataAmountSetting = require.getDataAmountSetting(param.getCid(),
				param.getSid(), queryResult.getMonthWage().getPeriod().end());
		List<EstimateAmountDetailImport> dataAmountSettingAnnual = dataAmountSetting.stream()
				.filter(x -> x.getAnnualAmountDetail().isPresent()).map(x -> x.getAnnualAmountDetail().get())
				.collect(Collectors.toList());

		// $目安金額
		long maxAmountSetting = dataAmountSettingAnnual.stream().map(x -> x.getAmount()).sorted((x, y) -> y - x)
				.findFirst().orElse(0);
		queryResult.getMonthWage().setMeasureAmount(maxAmountSetting);

		if (param.getNrWebQuery().getType().isPresent()
				&& param.getNrWebQuery().getType().get().equals(NRType.XML.value)) {
			// XMLを作る(＄NRWeb照会メニュー一覧)
			return Response.ok(
					queryResult.getMonthWage().createXml(NRWebQueryMenuName.ANNUAL_WAGE,
							param.getNrWebQuery().getYmFormat(), queryResult.getYear(), dataAmountSettingAnnual),
					MediaType.APPLICATION_XML).build();
		} else {
			// HTMLを作る(＄NRWeb照会メニュー一覧)
			return Response.ok(
					queryResult.getMonthWage().createHtml(NRWebQueryMenuName.ANNUAL_WAGE,
							param.getNrWebQuery().getYmFormat(), queryResult.getYear(), dataAmountSettingAnnual),
					MediaType.TEXT_HTML).build();
		}
	}

	public static interface Require extends GetNRWebQueryAnnualWage.Require {
		// 目安金額を取得
		// GetEstimateAmountAdapter
		public List<EstimateAmountSettingImport> getDataAmountSetting(String cid, String sid, GeneralDate date);
	}
}
