package nts.uk.ctx.pr.proto.dom.layout.line;

import java.util.List;
import java.util.Optional;

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
	void add(LayoutMasterLine layoutMasterLine);

	/**
	 * update a layout master line
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param autoLineID
	 * @param categoryAtr
	 * @param stmtCode
	 */
	void update(LayoutMasterLine layoutMasterLine);

	/**
	 * delete a layout master line
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param autoLineID
	 * @param categoryAtr
	 * @param stmtCode
	 */
	void remove(String companyCode, int startYm, String autoLineID, int categoryAtr, String stmtCode);

	Optional<LayoutMasterLine> getLine(String companyCd, String stmtCd, int strYm, String autoLineId);

	List<LayoutMasterLine> getLines(String companyCd, String stmtCd, int strYm);



	/**
	 * get Line
	 * 
	 * @param companyCode
	 * @param startYM
	 * @param stmtCode
	 * @return list Line
	 */
	

}
