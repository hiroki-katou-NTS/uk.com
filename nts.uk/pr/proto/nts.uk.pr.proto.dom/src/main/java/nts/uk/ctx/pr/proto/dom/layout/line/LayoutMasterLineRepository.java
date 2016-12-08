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
	 * add list lines
	 * @param lines
	 */
	void add(List<LayoutMasterLine> lines);
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
	 * update list lines
	 * @param lines
	 */
	void update(List<LayoutMasterLine> lines);
	/**
	 * delete a layout master line
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param autoLineID
	 * @param categoryAtr
	 * @param stmtCode
	 */
	void remove(String companyCode, String historyId, String autoLineID, int categoryAtr, String stmtCode);
	
	void remove(List<LayoutMasterLine> lines);
	
	/**
	 * Find layout master line
	 * @param companyCd
	 * @param stmtCd
	 * @param strYm
	 * @param categoryAtr
	 * @param autoLineId
	 * @return
	 */
	Optional<LayoutMasterLine> find(String companyCode, String stmtCode, String historyId, int categoryAtr, String autoLineId);

	Optional<LayoutMasterLine> getLine(String companyCd, String stmtCd, int strYm, String autoLineId);

	List<LayoutMasterLine> getLines(String companyCd, String stmtCd, int strYm);
	
	List<LayoutMasterLine> getLinesBefore(String companyCd, String stmtCd, int endYm);
	
	List<LayoutMasterLine> getLines(String companyCd, String stmtCd, int strYm, int categoryAtr);
}
