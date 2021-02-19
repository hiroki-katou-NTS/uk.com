package nts.uk.ctx.at.record.pub.stamp;

import java.util.Optional;

public interface StampCardPub {

	public Optional<StampCardExport> getByCardNoAndContractCode(String contractCode, String stampNumber);
	
}
