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
	 * @param companyCode
	 * @param layoutCode
	 * @param autoLineId
	 * @param startYm
	 * @param categoryAttribute
	 */
	void remove(String companyCode, String layoutCode, int startYm, String autoLineId, String categoryAttribute);

	/**
	 * get Line
	 * 
	 * @param companyCode
	 * @param startYM
	 * @param stmtCode
	 * @param endYM
	 * @param layoutAtr
	 * @param stmtName
	 * @return list Line
	 */
	List<LayoutMasterLine> getLines(String companyCode, int startYM, String stmtCode, int endYM, int layoutAtr,
			String stmtName);
}
