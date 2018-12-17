package nts.uk.ctx.at.schedule.infra.repository.shift.schedulehorizontal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.app.export.horitotalcategory.HoriTotalCategoryExcelRepo;
import nts.uk.ctx.at.schedule.app.export.horitotalcategory.HoriTotalExel;
import nts.uk.ctx.at.schedule.app.export.horitotalcategory.ItemCNTSetExcel;
import nts.uk.ctx.at.schedule.app.export.horitotalcategory.ItemTotalExcel;
@Stateless
public class JpaHoriTotalCateExcel extends JpaRepository implements HoriTotalCategoryExcelRepo {
	// hori total category
		// total eval order
		// total eval item
		// hori cal days set
		// hori total cnt set
		//EXPORT EXCEL
//		private static final String SELECT_CATE_EXCEL = SELECT_CATE_ITEM + "JOIN " + SELECT_ORDER_ITEM + "ON ";
		
//		private static final String SELECT_CATE_EXCEL = "SELECT c.kscmtHoriTotalCategoryPK.categoryCode, c.categoryName, c.memo, d.kscmtTotalEvalOrderPK.totalItemNo as totalItemNo,"+
//										"(SELECT e.totalItemName FROM KscmtTotalEvalItem e "+
//											"WHERE e.kscmtTotalEvalItemPK.totalItemNo = d.kscmtTotalEvalOrderPK.totalItemNo and e.kscmtTotalEvalItemPK.companyId = :companyId) as itemName, "+
//										"f.kscstHoriTotalCntSetPK.totalItemNo as totalTimeNo "+
//								" FROM KscmtHoriTotalCategoryItem c "+
//								" JOIN KscmtTotalEvalOrderItem d on d.kscmtTotalEvalOrderPK.companyId =:companyId and d.kscmtTotalEvalOrderPK.categoryCode = c.kscmtHoriTotalCategoryPK.categoryCode "+
//								" LEFT JOIN KscstHoriTotalCntSetItem f "+
//								" on  f.kscstHoriTotalCntSetPK.totalItemNo = d.kscmtTotalEvalOrderPK.totalTimeNo and f.kscstHoriTotalCntSetPK.companyId = :companyId and f.kscstHoriTotalCntSetPK.categoryCode = d.kscmtTotalEvalOrderPK.categoryCode;";
		
		private static final String SELECT_CATE_EXCEL = "SELECT c.CATEGORY_CD, c.CATEGORY_NAME, c.MEMO, d.TOTAL_ITEM_NO as totalItemNo, "
				+ "(SELECT e.TOTAL_ITEM_NAME FROM KSCMT_TOTAL_EVAL_ITEM e 	"
				+ "WHERE e.TOTAL_ITEM_NO = d.TOTAL_ITEM_NO and e.CID = ?companyId) "
				+ "as itemName, f.TOTAL_TIME_NO as totalTimeNo FROM KSCMT_HORI_TOTAL_CATEGORY c "
				+ "JOIN KSCMT_TOTAL_EVAL_ORDER d on d.CID = ?companyId "
				+ "and d.CATEGORY_CD = c.CATEGORY_CD LEFT JOIN KSCST_HORI_TOTAL_CNT_SET f "
				+ "on  f.TOTAL_ITEM_NO = d.TOTAL_ITEM_NO and f.CID = ?companyId"
				+ " and f.CATEGORY_CD = d.CATEGORY_CD;";
				
		@Override
		public List<HoriTotalExel> getAll(String companyId) {
			List<?> data = this.getEntityManager().createNativeQuery(SELECT_CATE_EXCEL)
					.setParameter("companyId", companyId).getResultList();
			Map<String, HoriTotalExel> result = new HashMap<>();
			data.stream().forEach(x -> {
				putRowToResult(result, (Object[])x);
			});
			
			return new ArrayList<HoriTotalExel>(result.values());
			
		}

		private void putRowToResult(Map<String, HoriTotalExel> result, Object[] x) {
			String categoryCode = (String)x[0];
			String categoryName = (String) x[1];
			String categoryMemo = (String) x[2];
			Integer itemNo = ((BigDecimal) x[3]).intValue();
			String totalItemName = (String) x[4];
			String totalTimeNo = String.valueOf(((BigDecimal) x[5]).intValue());
			
			HoriTotalExel horiTotalExel = result.get(categoryCode);
			if (horiTotalExel == null) {
				horiTotalExel = new HoriTotalExel(categoryCode, categoryName, categoryMemo);
				ItemTotalExcel newItemTotalExcel = new ItemTotalExcel(totalItemName, itemNo);
				newItemTotalExcel.putItemTotalExcel(new ItemCNTSetExcel(totalTimeNo, totalTimeNo));
				horiTotalExel.putItemTotalExcel(newItemTotalExcel);
				result.put(categoryCode, horiTotalExel);
			} else {
				Optional<ItemTotalExcel> itemTotalExcel = horiTotalExel.getItemTotalExcelByItemNo(itemNo);
				if (!itemTotalExcel.isPresent()) {
					ItemTotalExcel newItemTotalExcel = new ItemTotalExcel(totalItemName, itemNo);
					newItemTotalExcel.putItemTotalExcel(new ItemCNTSetExcel(totalTimeNo, totalTimeNo));
					horiTotalExel.putItemTotalExcel(newItemTotalExcel);
				}
				else {
					itemTotalExcel.get().putItemTotalExcel(new ItemCNTSetExcel(totalTimeNo, totalTimeNo));
				}
			}
		}


}
