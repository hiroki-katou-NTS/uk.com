package nts.uk.ctx.pr.proto.dom.layout.line;

import java.util.List;

public interface LayoutMasterLineRepository {

	/**
	 * add LayoutMasterLine
	 * 
	 * @param layoutMasterLine
	 */
	void add(LayoutMasterLine layoutMasterLine);

	/**
	 * update LayoutMasterLine
	 * 
	 * @param layoutMasterLine
	 */
	void update(LayoutMasterLine layoutMasterLine);

	/**
	 * delete a LayoutMasterLine
	 * 
	 * @param autoLineId
	 * @param categoryAttribute
	 */
	void remove(String autoLineId, String categoryAttribute);

	/**
	 * get Line
	 * 
	 * @param autoLineId
	 * @param categoryAttribute
	 * @return list Line
	 */
	List<LayoutMasterLine> getLine(String autoLineId, String categoryAttribute);
}
