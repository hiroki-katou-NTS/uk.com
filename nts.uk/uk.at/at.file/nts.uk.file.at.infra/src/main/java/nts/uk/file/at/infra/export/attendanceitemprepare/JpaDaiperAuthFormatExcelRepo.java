package nts.uk.file.at.infra.export.attendanceitemprepare;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.attendanceitemprepare.PerAuthFormatExport;
import nts.uk.file.at.app.export.attendanceitemprepare.PerAuthFormatItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaDaiperAuthFormatExcelRepo extends JpaRepository implements PerAuthFormatExport{

	private static final String SELECT_ALL_DAI = 
			"select a.DAILY_PERFORMANCE_FORMAT_CD,a.DAILY_PERFORMANCE_FORMAT_NAME , "
			+ " b.DAILY_PERFORMANCE_FORMAT_CD as availability, c.SHEET_NO, d.SHEET_NAME, "
			+ "c.ATTENDANCE_ITEM_ID,c.DISPLAY_ORDER from KFNMT_DAY_FORM a  "
			+ "left join KFNMT_DAY_FORM_DEFAULT b "
			+ "on a.CID = b.CID and a.DAILY_PERFORMANCE_FORMAT_CD = b.DAILY_PERFORMANCE_FORMAT_CD "
			+ "left join KFNMT_DAY_FORM_DAY_ITEM c "
			+ "on a.CID=c.CID and a.DAILY_PERFORMANCE_FORMAT_CD=c.DAILY_PERFORMANCE_FORMAT_CD "
			+ "left join KFNMT_DAY_FORM_SHEET d "
			+ "on c.DAILY_PERFORMANCE_FORMAT_CD = d.DAILY_PERFORMANCE_FORMAT_CD "
			+ "and c.SHEET_NO=d.SHEET_NO and c.CID=d.CID where a.CID=?companyId "
			+ "order by a.DAILY_PERFORMANCE_FORMAT_CD,c.SHEET_NO,c.DISPLAY_ORDER ";
	private static final String SELECT_ALL_DAI_MON = "select a.DAILY_PERFORMANCE_FORMAT_CD,a.DAILY_PERFORMANCE_FORMAT_NAME , "
			+ " c.ATTENDANCE_ITEM_ID,c.DISPLAY_ORDER from KFNMT_DAY_FORM a "
			+ " left join KFNMT_DAY_FORM_MON_ITEM c "
			+ " on a.CID=c.CID and a.DAILY_PERFORMANCE_FORMAT_CD=c.DAILY_PERFORMANCE_FORMAT_CD "
			+ "where a.CID=?companyId ";
	private static final String SELECT_ALL_MON = "select a.MON_FORMAT_CODE,a.MON_FORMAT_NAME ,"
			+ " b.MON_PFM_FORMAT_CODE as availability, c.SHEET_NO, d.SHEET_NAME,"
			+ " c.ATTENDANCE_ITEM_ID,c.DISPLAY_ORDER "
			+ "from KRCMT_MON_FORM a  "
			+ "left join KFNMT_MON_FORM_DEFAULT b "
			+ "on a.CID = b.CID and a.MON_FORMAT_CODE = b.MON_PFM_FORMAT_CODE "
			+ "left join KFNMT_MON_FORM_ITEM c on a.CID=c.CID and a.MON_FORMAT_CODE=c.MON_FORMAT_CODE  "
			+ "left join KFNMT_MON_FORM_SHEET d "
			+ "on c.MON_FORMAT_CODE = d.MON_FORMAT_CODE and c.SHEET_NO=d.SHEET_NO and c.CID=d.CID "
			+ "where a.CID=?companyId "
			+ "order by a.MON_FORMAT_CODE,c.SHEET_NO,c.DISPLAY_ORDER ";
	
	
	@Override
	public Map<String, Map<Integer, List<PerAuthFormatItem>>> getAllDailyByComp(String companyId) {
		List<?> dataDefault = this.getEntityManager().createNativeQuery(SELECT_ALL_DAI)
				.setParameter("companyId", AppContexts.user().companyId()).getResultList();
		List<PerAuthFormatItem> result = new ArrayList<>();
		dataDefault.stream().forEach(x -> {
			putRowToResultDefault(result, (Object[])x);
		});
		
		
		Map<String, Map<Integer, List<PerAuthFormatItem>>> map = result.stream()
			    .collect(Collectors.groupingBy(PerAuthFormatItem::getDailyCode,
			        Collectors.groupingBy(PerAuthFormatItem::getSheetNo)));
		return map;
	}
	@Override
	public Map<String, List<PerAuthFormatItem>> getAllDaiMonthlyByComp(String companyId) {
		List<?> dataDefault = this.getEntityManager().createNativeQuery(SELECT_ALL_DAI_MON)
				.setParameter("companyId", AppContexts.user().companyId()).getResultList();
		List<PerAuthFormatItem> result = new ArrayList<>();
		dataDefault.stream().forEach(x -> {
			putRowToResultMon(result, (Object[])x);
		});
		
		
		Map<String, List<PerAuthFormatItem>> map = result.stream()
			    .collect(Collectors.groupingBy(PerAuthFormatItem::getDailyCode));
		return map;
	}
	
	private void putRowToResultMon(List<PerAuthFormatItem> result, Object[] x) {
		 String dailyCode = (String)x[0];
		 String dailyName = (String)x[1];
		 int attId = x[2]!=null?((BigDecimal) x[2]).intValue():-1;
		 int displayOder = x[3]!=null?((BigDecimal) x[3]).intValue():-1;

		 PerAuthFormatItem daiPerAuthFormatItem = 
				 new PerAuthFormatItem(dailyCode, dailyName, attId, displayOder);
		result.add(daiPerAuthFormatItem);
		
	}
	private void putRowToResultDefault(List<PerAuthFormatItem> result, Object[] x) {
		 String dailyCode = (String)x[0];
		 String dailyName = (String)x[1];
		 int availability = x[2]!=null?1:0;
		 int sheetNo = x[3]!=null?((BigDecimal) x[3]).intValue():-1;
		 String sheetName = (String)x[4];
		 int attId = x[5]!=null?((BigDecimal) x[5]).intValue():-1;
		 int displayOder = x[6]!=null?((BigDecimal) x[6]).intValue():-1;

		 PerAuthFormatItem daiPerAuthFormatItem = 
				 new PerAuthFormatItem(dailyCode, dailyName, availability, sheetNo, sheetName, attId, displayOder);
		result.add(daiPerAuthFormatItem);
		
	}
	@Override
	public Map<String, Map<Integer, List<PerAuthFormatItem>>> getAllMonByComp(String companyId) {
		List<?> dataDefault = this.getEntityManager().createNativeQuery(SELECT_ALL_MON)
				.setParameter("companyId", AppContexts.user().companyId()).getResultList();
		List<PerAuthFormatItem> result = new ArrayList<>();
		dataDefault.stream().forEach(x -> {
			putRowToResultDefault(result, (Object[])x);
		});
		
		
		Map<String, Map<Integer, List<PerAuthFormatItem>>> map = result.stream()
			    .collect(Collectors.groupingBy(PerAuthFormatItem::getDailyCode,
			        Collectors.groupingBy(PerAuthFormatItem::getSheetNo)));
		return map;
	}
		
}
