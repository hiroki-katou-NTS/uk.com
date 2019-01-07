package nts.uk.file.at.infra.export.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.roledaily.BusinessDailyExcel;
import nts.uk.file.at.app.export.roledaily.BusinessDailyRepo;

@Stateless
public class JpaBusinessDailyRepo extends JpaRepository implements BusinessDailyRepo {

	private static final String GET_ALL_BY_COMPANY = "select a.BUSINESS_TYPE_CD, a.ATTENDANCE_ITEM_ID, a.SHEET_NO, b.SHEET_NAME "
			+ "from KRCMT_BUS_DAILY_ITEM a "
			+ "join KRCMT_BUS_FORM_SHEET b "
			+ "on a.BUSINESS_TYPE_CD=b.BUSINESS_TYPE_CD and a.SHEET_NO=b.SHEET_NO where a.CID=?companyId "
			+ "";
	String tempCode = "";
//	List<BusinessDailyExcel> list = new ArrayList<>();
	@Override
	public Map<String, Map<Integer, List<BusinessDailyExcel>>> getAllByComp(String companyId) {
		List<?> data = this.getEntityManager().createNativeQuery(GET_ALL_BY_COMPANY)
				.setParameter("companyId", companyId).getResultList();
		Map<String, List<BusinessDailyExcel>> listBusiness = new HashMap<>();
		List<BusinessDailyExcel> list = new ArrayList<>();
		data.stream().forEach(x -> {
			putRowToResult(list, (Object[])x);
		});
		listBusiness = list.stream().collect(Collectors.groupingBy(BusinessDailyExcel::getCode));
		Map<String, Map<Integer, List<BusinessDailyExcel>>> result = new HashMap<>();
		List<String> listkeyBzDailyByCode = new ArrayList<String>(listBusiness.keySet());
//		Collections.sort(listkeyBzDailyByCode);
		for (String string : listkeyBzDailyByCode) {
			List<BusinessDailyExcel> listBzDailyBySheetNo = listBusiness.get(string);
			Map<Integer, List<BusinessDailyExcel>> mapListBzDailyBySheetNo = listBzDailyBySheetNo.stream().collect(Collectors.groupingBy(BusinessDailyExcel::getSheetNo));
//			List<Integer> listkeyBzDailyBySheetNo = new ArrayList<Integer>(mapListBzDailyBySheetNo.keySet());
			result.put(string, mapListBzDailyBySheetNo);
		}
		return result;
		
	}

	private void putRowToResult(List<BusinessDailyExcel> list, Object[] x) {
		Integer attItemId = 0;
		Integer sheetNo = 0;
		String code = (String) x[0];
		String sheetName = (String) x[3];
		if(x[1] !=null){
			sheetNo = ((BigDecimal) x[1]).intValue();
		}
		if(x[2] !=null){
			attItemId =((BigDecimal) x[2]).intValue();
		}
		BusinessDailyExcel businessDailyExcel = 
				new BusinessDailyExcel(code, sheetNo,attItemId,sheetName);
		list.add(businessDailyExcel);
	}

}
