package nts.uk.ctx.at.shared.dom.employmentrule.hourlate.breaktime.breaktimeframe;

import java.util.List;

public interface BreaktimeFrameRepository {
	/**
	 * @param breaktimeFrameNo
	 * @return
	 */
	List<BreaktimeFrame> getBreaktimeFrameByFrameNo(List<Integer> breaktimeFrameNo);
	/**
	 * @param companyID
	 * @param useAtr
	 * @return
	 */
	List<BreaktimeFrame> getBreaktimeFrameByCID(String companyID,int useAtr);
}
