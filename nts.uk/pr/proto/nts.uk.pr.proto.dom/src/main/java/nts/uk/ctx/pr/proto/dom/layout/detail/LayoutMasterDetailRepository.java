package nts.uk.ctx.pr.proto.dom.layout.detail;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import nts.arc.time.YearMonth;

@RequestScoped
public interface LayoutMasterDetailRepository {

	/**
	 * add a layout master detail
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param stmtCode
	 */
	void add(String companyCode, int startYm, String stmtCode);

	/**
	 * update a layout master detail
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param stmtCode
	 * @param categoryAtr
	 * @param autoLineID
	 * @param itemCode
	 */
	void update(String companyCode, int startYm, String stmtCode, int categoryAtr, String autoLineID, String itemCode);

	/**
	 * delete a layout master detail
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @param categoryAtr
	 * @param itemCode
	 */
	void remove(String companyCode, int startYm, String stmtCode, int categoryAtr, String itemCode);

	/**
	 * get Detail
	 * 
	 * @param companyCode
	 * @param layout code
	 * @param start YM
	 * @return category type
	 */
	List<LayoutMasterDetail> getDetails(
			String companyCd, 
			String stmtCd, 
			YearMonth startYm, 
			int categoryAtr);

	/**
	 * get detail
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYM
	 * @param categoryAtr
	 * @param calculationMethod
	 * @param item code
	 * @return
	 */
	Optional<LayoutMasterDetail> getDetail(
			String companyCd, 
			String stmtCd, 
			YearMonth startYm, 
			int categoryAtr,
			String itemCd);
}
