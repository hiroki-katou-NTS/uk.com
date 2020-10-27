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
import nts.uk.shr.com.i18n.TextResource;
/**
 * 
 * @author hoidd
 *
 */
@Stateless
public class JpaHoriTotalCateExcel extends JpaRepository implements HoriTotalCategoryExcelRepo {

	private static final String SELECT_CATE_EXCEL = "SELECT c.CATEGORY_CD, c.CATEGORY_NAME, c.MEMO, d.TOTAL_ITEM_NO as totalItemNo, "
				+ "(SELECT e.TOTAL_ITEM_NAME FROM KSCMT_HORIZONTAL_ITEM e 	"
				+ "WHERE e.TOTAL_ITEM_NO = d.TOTAL_ITEM_NO and e.CID = ?companyId) "
				+ "as itemName, g.TOTAL_TIMES_NAME as totalTimesName , h.YEAR_HD_ATR,h.HAVY_HD_ATR, h.SPHD_ATR, h.HALF_DAY_ATR as halfDay "
				+ " ,g.TOTAL_TIMES_NO as timesNo FROM KSCMT_HORIZONTAL_CATEGORY c "
				+ "JOIN KSCMT_HORIZONTAL_SORT d on d.CID = ?companyId  and c.CID=?companyId "
				+ "and d.CATEGORY_CD = c.CATEGORY_CD LEFT JOIN KSCMT_HORIZONTAL_CNT_AGG f "
				+ "on  f.TOTAL_ITEM_NO = d.TOTAL_ITEM_NO and f.CID = ?companyId"
				+ " and f.CATEGORY_CD = d.CATEGORY_CD "
				+ "LEFT JOIN KSHMT_TOTAL_TIMES g on g.TOTAL_TIMES_NO = f.TOTAL_TIME_NO and g.CID=?companyId "
				+ "LEFT JOIN KSCST_HORI_CAL_DAYS_SET h on h.TOTAL_ITEM_NO = d.TOTAL_ITEM_NO and h.CID = ?companyId  and h.CATEGORY_CD = d.CATEGORY_CD"
				+ " ORDER BY c.CATEGORY_CD,d.DISPORDER, f.TOTAL_TIME_NO"
				
				;
				
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
			
			Integer itemNo = -1;
			Integer timesNo = -1;
			if(x[10]!=null){
				timesNo = ((BigDecimal) x[10]).intValue();
			}
			if(x[3] !=null){
				itemNo = ((BigDecimal) x[3]).intValue();
			}
			String totalItemName = (String) x[4];
			String totalTimeName = "";
			if(x[5] !=null){
				totalTimeName = (String) x[5];
			}
			HoriTotalExel horiTotalExel = result.get(categoryCode);
			if (horiTotalExel == null) {
				horiTotalExel = new HoriTotalExel(categoryCode, categoryName, categoryMemo);
				ItemTotalExcel newItemTotalExcel = new ItemTotalExcel(totalItemName, itemNo);
				if(newItemTotalExcel.getItemNo()==3){
					newItemTotalExcel.setItemDaySets(genListItemDaySet(x[6],x[7],x[8],x[9]));
				}
				newItemTotalExcel.putItemTotalExcel(new ItemCNTSetExcel(totalTimeName,String.valueOf(timesNo)));
				horiTotalExel.putItemTotalExcel(newItemTotalExcel);
				result.put(categoryCode, horiTotalExel);
			} else {
				Optional<ItemTotalExcel> itemTotalExcel = horiTotalExel.getItemTotalExcelByItemNo(itemNo);
				if (!itemTotalExcel.isPresent()) {
					ItemTotalExcel newItemTotalExcel = new ItemTotalExcel(totalItemName, itemNo);
					if(newItemTotalExcel.getItemNo()==3){
						newItemTotalExcel.setItemDaySets(genListItemDaySet(x[6],x[7],x[8],x[9]));
					}
					newItemTotalExcel.putItemTotalExcel(new ItemCNTSetExcel(totalTimeName,String.valueOf(timesNo)));
					horiTotalExel.putItemTotalExcel(newItemTotalExcel);
				}
				else {
					itemTotalExcel.get().putItemTotalExcel(new ItemCNTSetExcel(totalTimeName,String.valueOf(timesNo)));
					if(itemTotalExcel.get().getItemNo()==3){
						itemTotalExcel.get().setItemDaySets(genListItemDaySet(x[6],x[7],x[8],x[9]));
					}
				}
			}
		}

		private List<String> genListItemDaySet(Object x6, Object x7, Object x8, Object x9) {
			int yeardHd = -1,yeardHavy =-1,specialHoliday =-1,halfDay =-1;
			List<String> listItemDaySet = new ArrayList<>();
			String yeardHdStr = TextResource.localize("KML004_20");
			String yeardHavyStr =  TextResource.localize("KML004_21");
			String specialHolidayStr =  TextResource.localize("KML004_22");
			String halfDayStr =  TextResource.localize("KML004_24");
			if(x6!=null){
				yeardHd = ((BigDecimal) x6).intValue();
				if(yeardHd==1){
					listItemDaySet.add(yeardHdStr);
				}
			}
			if(x7!=null){
				yeardHavy = ((BigDecimal) x7).intValue();
				if(yeardHavy==1){
					listItemDaySet.add(yeardHavyStr);
				}
			}
			if(x8!=null){
				specialHoliday = ((BigDecimal) x8).intValue();
				if(specialHoliday==1){
					listItemDaySet.add(specialHolidayStr);
				}
			}
			if(x9!=null){
				halfDay = ((BigDecimal) x9).intValue();
				if(halfDay==1){
					listItemDaySet.add(halfDayStr);
				}
			}
			
			return listItemDaySet;
		}


}
