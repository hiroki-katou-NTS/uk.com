package nts.uk.ctx.at.function.ac.estimateamount;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.pub.estimateamount.GetEstimateAmountPub;
import nts.uk.ctx.at.function.dom.adapter.estimateamount.EstimateAmountDetailImport;
import nts.uk.ctx.at.function.dom.adapter.estimateamount.EstimateAmountSettingImport;
import nts.uk.ctx.at.function.dom.adapter.estimateamount.GetEstimateAmountAdapter;

@Stateless
public class GetEstimateAmountAdapterImpl implements GetEstimateAmountAdapter {

	@Inject
	private GetEstimateAmountPub pub;

	@Override
	public List<EstimateAmountSettingImport> getData(String cid, String sid, GeneralDate date) {
		return pub
				.getData(cid, sid,
						date)
				.stream().map(
						x -> new EstimateAmountSettingImport(x.getAnnualAmountDetail()
								.map(y -> new EstimateAmountDetailImport(y.getAmountFrameNo(), y.getAmount(),
										y.getTreatmentByFrameColor())),
								x.getMonthlyAmountDetail().map(y -> new EstimateAmountDetailImport(y.getAmountFrameNo(),
										y.getAmount(), y.getTreatmentByFrameColor()))))
				.collect(Collectors.toList());
	}

}
