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
	void remove(String companyCode, int startYm, String autoLineID, int categoryAtr, String stmtCode);
	
	void remove(List<LayoutMasterLine> lines);

	Optional<LayoutMasterLine> getLine(String companyCd, String stmtCd, int strYm, String autoLineId);

	List<LayoutMasterLine> getLines(String companyCd, String stmtCd, int strYm);
	
	List<LayoutMasterLine> getLines(String companyCd, String stmtCd, int strYm, int categoryAtr);
	/**
	 * 明細書の履歴の行を全て削除する
	 * @param companyCd
	 * @param stmtCd
	 * @param strYm
	 */
	void removeAllLineOfHistory(String companyCd, String stmtCd, int strYm);
}
