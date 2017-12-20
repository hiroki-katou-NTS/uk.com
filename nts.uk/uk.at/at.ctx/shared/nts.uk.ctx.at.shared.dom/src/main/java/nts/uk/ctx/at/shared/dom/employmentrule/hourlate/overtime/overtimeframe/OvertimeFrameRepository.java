package nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe;

import java.util.List;

public interface OvertimeFrameRepository {
	
	
	/**
	 * getOvertimeFrameByFrameNos
	 * @param companyID
	 * @param frameNo
	 * @return
	 */
	List<OvertimeFrame> getOvertimeFrameByFrameNos(String companyID,List<Integer> frameNos);
	/**
	 * getOvertimeFrameByCID
	 * @param companyID
	 * @param useAtr
	 * @return
	 */
	List<OvertimeFrame> getOvertimeFrameByCID(String companyID,int useAtr);
	
	
	
}
