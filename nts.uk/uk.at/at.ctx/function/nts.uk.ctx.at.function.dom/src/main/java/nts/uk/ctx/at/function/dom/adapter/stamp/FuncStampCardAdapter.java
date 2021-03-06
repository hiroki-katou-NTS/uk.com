package nts.uk.ctx.at.function.dom.adapter.stamp;

import java.util.Optional;

public interface FuncStampCardAdapter {

	public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber);
	
	//取得
	public Optional<String> getSidByCardNoAndContractCode(String contractCode, String stampNumber);
	
}
