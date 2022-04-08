package nts.uk.ctx.at.aggregation.pub.estimateamount;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.pub.estimateamount.export.EstimateAmountSettingExport;

/**
 * @author thanh_nx
 *
 *         目安金額を取得Publish
 */
public interface GetEstimateAmountPub {
	public List<EstimateAmountSettingExport> getData(String cid, String sid, GeneralDate date);
}
