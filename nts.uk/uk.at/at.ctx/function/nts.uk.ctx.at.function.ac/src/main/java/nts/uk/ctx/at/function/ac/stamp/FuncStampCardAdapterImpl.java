package nts.uk.ctx.at.function.ac.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.stamp.FuncStampCardAdapter;
import nts.uk.ctx.at.function.dom.adapter.stamp.StampCard;
import nts.uk.ctx.at.record.pub.stamp.StampCardPub;

/**
 * @author thanh_nx
 *
 *         打刻カード番号を指定して社員IDを取得する
 */
@Stateless
public class FuncStampCardAdapterImpl implements FuncStampCardAdapter {

	@Inject
	private StampCardPub stampCardPub;

	@Override
	public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber) {
		return stampCardPub.getByCardNoAndContractCode(contractCode, stampNumber)
				.map(x -> new StampCard(x.getStampCardId(), x.getEmployeeId()));
	}

	@Override
	public Optional<String> getSidByCardNoAndContractCode(String contractCode, String stampNumber) {
		return stampCardPub.getSidByCardNoAndContractCode(contractCode, stampNumber);
	}

}
