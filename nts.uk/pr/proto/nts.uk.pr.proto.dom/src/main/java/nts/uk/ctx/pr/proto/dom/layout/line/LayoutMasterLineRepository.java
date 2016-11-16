package nts.uk.ctx.pr.proto.dom.layout.line;

import java.util.List;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public interface LayoutMasterLineRepository {

	/**
	 * add a layout master line
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param stmtCode
	 */
	void add(String companyCode, int startYm, String stmtCode);

	/**
	 * update a layout master line
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param autoLineID
	 * @param categoryAtr
	 * @param stmtCode
	 */
	void update(String companyCode, int startYm, String autoLineID, String categoryAtr, String stmtCode);

	/**
	 * delete a layout master line
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param autoLineID
	 * @param categoryAtr
	 * @param stmtCode
	 */
	void remove(String companyCode, int startYm, String autoLineID, String categoryAtr, String stmtCode);

	/**
	 * get Line
	 * 
	 * @param companyCode
	 * @param startYM
	 * @param stmtCode
	 * @return list Line
	 */
	List<LayoutMasterLine> getLines(String companyCode, int startYM, String stmtCode);
}
