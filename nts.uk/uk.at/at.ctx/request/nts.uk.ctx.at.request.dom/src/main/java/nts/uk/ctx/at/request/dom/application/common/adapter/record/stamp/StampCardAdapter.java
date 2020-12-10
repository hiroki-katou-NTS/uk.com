package nts.uk.ctx.at.request.dom.application.common.adapter.record.stamp;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.StampCard;

public interface StampCardAdapter {

	public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber);
	
}
