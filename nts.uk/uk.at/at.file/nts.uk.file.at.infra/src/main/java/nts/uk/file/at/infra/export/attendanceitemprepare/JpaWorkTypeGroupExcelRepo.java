package nts.uk.file.at.infra.export.attendanceitemprepare;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.attendanceitemprepare.WorkTypeDtoExcel;
import nts.uk.file.at.app.export.attendanceitemprepare.WorkTypeGroupExcel;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaWorkTypeGroupExcelRepo extends JpaRepository implements WorkTypeGroupExcel{

	private static final String SELECT_ALL_WORKTYPE_EXCEL = "SELECT a.CODE, a.NAME, b.WORKTYPE_GROUP_NO, b.WORKTYPE_GROUP_NAME, c.NAME,b.WORKTYPE_CODE "
			+ "FROM BSYMT_EMPLOYMENT  a "
			+ "left join KRCMT_WORKTYPE_CHANGEABLE b on a.CODE = b.EMP_CODE and a.CID=b.CID "
			+ "left join KSHMT_WORKTYPE c on b.WORKTYPE_CODE = c.CD and b.CID=c.CID "
			+ "where a.CID=?companyId and a.CID=?companyId";
	private static final String SELECT_ALL_DEFAULT_WORKTPYE = "select a.WORKTYPE_GROUP_NO, a.WORKTYPE_GROUP_NAME, b.NAME "
			+ "from KRCMT_WORKTYPE_CHANGEABLE a "
			+ "left join KSHMT_WORKTYPE b on a.WORKTYPE_CODE = b.CD and b.CID=?companyId "
			+ "where a.CID='000000000000-0000' and a.EMP_CODE='0'  ";
	List<WorkTypeDtoExcel> resultDefault ;
	@Override
	public Map<String, Map<Integer, List<WorkTypeDtoExcel>>> getAllWorkType() {
		//get data default 
		List<?> dataDefault = this.getEntityManager().createNativeQuery(SELECT_ALL_DEFAULT_WORKTPYE)
				.setParameter("companyId", AppContexts.user().companyId()).getResultList();
		resultDefault = new ArrayList<>();
		dataDefault.stream().forEach(x -> {
			putRowToResultDefault(resultDefault, (Object[])x);
		});
		//get all data
		List<?> data = this.getEntityManager().createNativeQuery(SELECT_ALL_WORKTYPE_EXCEL)
				.setParameter("companyId", AppContexts.user().companyId()).getResultList();
		List<WorkTypeDtoExcel> result = new ArrayList<>();
		data.stream().forEach(x -> {
			putRowToResult(result, (Object[])x);
		});
		
		Map<String, Map<Integer, List<WorkTypeDtoExcel>>> map = result.stream()
			    .collect(Collectors.groupingBy(WorkTypeDtoExcel::getCodeEmp,
			        Collectors.groupingBy(WorkTypeDtoExcel::getGroupNo)));
		return map;
	}
	private void putRowToResultDefault(List<WorkTypeDtoExcel> resultDefault, Object[] x) {
		String codeEmp = null;
		String nameEmp = null;
		int groupNo = -1;
			if(x[0] !=null){
				groupNo = ((BigDecimal) x[0]).intValue();
			}
		String groupName = (String)x[1];
		String workTypeName = (String)x[2];
		WorkTypeDtoExcel workTypeDtoExcel = new WorkTypeDtoExcel(codeEmp, nameEmp, groupNo, groupName, workTypeName);
		resultDefault.add(workTypeDtoExcel);
		
	}
	private void putRowToResult(List<WorkTypeDtoExcel> result, Object[] x) {
		String codeEmp = (String)x[0];
		String nameEmp = (String)x[1];
		int groupNo = -1;
			if(x[2] !=null){
				groupNo = ((BigDecimal) x[2]).intValue();
			}
		String groupName = (String)x[3];
		String workTypeName = (String)x[4];
		if(x[2]==null && x[5]==null){
			resultDefault.forEach(f -> f.setCodeEmpAndName(codeEmp,nameEmp));
			for (WorkTypeDtoExcel workType : resultDefault) {
				WorkTypeDtoExcel a = new WorkTypeDtoExcel(codeEmp,nameEmp,workType.getGroupNo(),workType.getGroupName(),workType.getWorkTypeName());
				result.add(a);
			}
			
		}else {
			WorkTypeDtoExcel workTypeDtoExcel = new WorkTypeDtoExcel(codeEmp, nameEmp, groupNo, groupName, workTypeName);
			result.add(workTypeDtoExcel);
		}
		
	}

}
