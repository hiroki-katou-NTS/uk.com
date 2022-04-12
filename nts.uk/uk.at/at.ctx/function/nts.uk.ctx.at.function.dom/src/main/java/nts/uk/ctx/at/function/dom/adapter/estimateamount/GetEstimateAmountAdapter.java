package nts.uk.ctx.at.function.dom.adapter.estimateamount;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * @author thanh_nx
 *
 *         目安金額を取得Adapter
 */
public interface GetEstimateAmountAdapter {
	public List<EstimateAmountSettingImport> getData(String cid, String sid, GeneralDate date);
}
