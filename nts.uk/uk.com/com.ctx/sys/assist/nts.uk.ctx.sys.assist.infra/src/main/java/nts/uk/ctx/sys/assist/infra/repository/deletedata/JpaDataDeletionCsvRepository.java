/**
 * 
 */
package nts.uk.ctx.sys.assist.infra.repository.deletedata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionCsvRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.TableDeletionDataCsv;

/**
 * @author hiep.th
 *
 */
@Stateless
public class JpaDataDeletionCsvRepository extends JpaRepository implements DataDeletionCsvRepository {
	private static final StringBuilder SELECT_TABLE_DEL_DATA_SQL = new StringBuilder("SELECT a.sspdtManualSetDeletionPK.delId, a.supplementExplanation, ")
			.append("a.startDateOfDaily, a.endDateOfDaily, a.startMonthOfMonthly, a.endMonthOfMonthly, ")
			.append("a.startYearOfMonthly, a.endYearOfMonthly, a.companyID, ")
			.append("b.delType, b.delCode, b.delName, c.categoryId, c.categoryName, c.timeStore, c.recoveryStorageRange, ")
			.append("c.otherCompanyCls, e.tableJapanName, e.tableEnglishName, ")
			.append("e.historyCls, e.defaultCondKeyQuery, e.fieldKeyQuery1, e.fieldKeyQuery2, ")
			.append("e.fieldKeyQuery3, e.fieldKeyQuery4, e.fieldKeyQuery5, e.fieldKeyQuery6, ")
			.append("e.fieldKeyQuery7, e.fieldKeyQuery8, e.fieldKeyQuery9, e.fieldKeyQuery10, e.clsKeyQuery1,")
			.append("e.clsKeyQuery2, e.clsKeyQuery3, e.clsKeyQuery4, e.clsKeyQuery5, e.clsKeyQuery6, ")
			.append("e.clsKeyQuery7, e.clsKeyQuery8, e.clsKeyQuery9, e.clsKeyQuery10, e.filedKeyUpdate1, ")
			.append("e.filedKeyUpdate2, e.filedKeyUpdate3, e.filedKeyUpdate4, e.filedKeyUpdate5, ")
			.append("e.filedKeyUpdate6, e.filedKeyUpdate7, e.filedKeyUpdate8, e.filedKeyUpdate9, ")
			.append("e.filedKeyUpdate10, e.filedKeyUpdate11, e.filedKeyUpdate12, e.filedKeyUpdate13, ")
			.append("e.filedKeyUpdate14, e.filedKeyUpdate15, e.filedKeyUpdate16, e.filedKeyUpdate17, ")
			.append("e.filedKeyUpdate18, e.filedKeyUpdate19, e.filedKeyUpdate20, e.fieldDate1, e.fieldDate2, ")
			.append("e.fieldDate3, e.fieldDate4, e.fieldDate5, e.fieldDate6, e.fieldDate7, e.fieldDate8, ")
			.append("e.fieldDate9, e.fieldDate10, e.fieldDate11, e.fieldDate12, e.fieldDate13, e.fieldDate14, ")
			.append("e.fieldDate15, e.fieldDate16, e.fieldDate17, e.fieldDate18, e.fieldDate19, e.fieldDate20, ")
			.append("e.hasParentTblFlg, e.parentTblName, e.parentTblJapanName, e.fieldAcqDateTime, ")
			.append("e.fieldAcqEndDate, e.fieldAcqEmployeeId, e.fieldAcqStartDate, e.fieldAcqCid, e.fieldParent1, ")
			.append("e.fieldParent2, e.fieldParent3, e.fieldParent4, e.fieldParent5, e.fieldParent6, e.fieldParent7, ")
			.append("e.fieldParent8, e.fieldParent9, e.fieldParent10, e.fieldChild1, e.fieldChild2, e.fieldChild3, ")
			.append("e.fieldChild4, e.fieldChild5, e.fieldChild6, e.fieldChild7, e.fieldChild8, e.fieldChild9,e.fieldChild10 ")
			.append("FROM SspdtManualSetDeletion a, SspdtResultDeletion b, SspmtCategory c, SspdtCategoryDeletion d, SspmtCategoryFieldMt e ")
			.append("WHERE a.sspdtManualSetDeletionPK.delId = b.sspdtResultDeletionPK.delId AND a.sspdtManualSetDeletionPK.delId = d.sspdtCategoryDeletionPK.delId AND c.categoryId = e.categoryFieldMtPk.categoryId AND d.sspdtCategoryDeletionPK.categoryId = c.categoryId AND a.sspdtManualSetDeletionPK.delId = :delId ");
	
	private static final String SELECT_COLUMN_NAME_SQL = "select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS"
			+ " where TABLE_NAME = ?tableName";

	@Override
	public List<TableDeletionDataCsv> getTableDelDataCsvById(String delId) {
		List<Object[]> listTemp = this.queryProxy().query(SELECT_TABLE_DEL_DATA_SQL.toString(), Object[].class)
				.setParameter("delId", delId).getList();

		if (listTemp == null || listTemp.isEmpty()) {
			return Collections.emptyList();
		}
		return listTemp.stream().map(i -> createDomainFromEntity(i)).collect(Collectors.toList());
	}

	/**
	 * @param i
	 * @return
	 */
	private TableDeletionDataCsv createDomainFromEntity(Object[] i) {
		String delId = String.valueOf(i[0]);
		String supplementExplanation = String.valueOf(i[1]);
		String startDateOfDaily = String.valueOf(i[2]);
		String endDateOfDaily = String.valueOf(i[3]);
		String startMonthOfMonthly = String.valueOf(i[4]);
		String endMonthOfMonthly = String.valueOf(i[5]);
		String startYearOfMonthly = String.valueOf(i[6]);
		String endYearOfMonthly = String.valueOf(i[7]);
		String companyId = String.valueOf(i[8]);
		
		int delType = Integer.parseInt(String.valueOf(i[9]));
		String delCode = String.valueOf(i[10]);
		String delName = String.valueOf(i[11]);
//		int saveForInvest = Integer.parseInt(String.valueOf(i[12]));
		String categoryId = String.valueOf(i[12]);
		String categoryName = String.valueOf(i[13]);
		int timeStore = Integer.parseInt(String.valueOf(i[14]));
		int recoveryStorageRange = Integer.parseInt(String.valueOf(i[15]));
		int otherCompanyCls = Integer.parseInt(String.valueOf(i[16]));
		String tableJapanName = String.valueOf(i[17]);
		String tableEnglishName = String.valueOf(i[18]);
		int historyCls = Integer.parseInt(String.valueOf(i[19]));
		String defaultCondKeyQuery = String.valueOf(i[20]);
		String fieldKeyQuery1 = String.valueOf(i[21]);
		String fieldKeyQuery2 = String.valueOf(i[22]);
		String fieldKeyQuery3 = String.valueOf(i[23]);
		String fieldKeyQuery4 = String.valueOf(i[24]);
		String fieldKeyQuery5 = String.valueOf(i[25]);
		String fieldKeyQuery6 = String.valueOf(i[26]);
		String fieldKeyQuery7 = String.valueOf(i[27]);
		String fieldKeyQuery8 = String.valueOf(i[28]);
		String fieldKeyQuery9 = String.valueOf(i[29]);
		String fieldKeyQuery10 = String.valueOf(i[30]);
		String clsKeyQuery1 = String.valueOf(i[31]);
		String clsKeyQuery2 = String.valueOf(i[32]);
		String clsKeyQuery3 = String.valueOf(i[33]);
		String clsKeyQuery4 = String.valueOf(i[34]);
		String clsKeyQuery5 = String.valueOf(i[35]);
		String clsKeyQuery6 = String.valueOf(i[36]);
		String clsKeyQuery7 = String.valueOf(i[37]);
		String clsKeyQuery8 = String.valueOf(i[38]);
		String clsKeyQuery9 = String.valueOf(i[39]);
		String clsKeyQuery10 = String.valueOf(i[40]);
		String filedKeyUpdate1 = String.valueOf(i[41]);
		String filedKeyUpdate2 = String.valueOf(i[42]);
		String filedKeyUpdate3 = String.valueOf(i[43]);
		String filedKeyUpdate4 = String.valueOf(i[44]);
		String filedKeyUpdate5 = String.valueOf(i[45]);
		String filedKeyUpdate6 = String.valueOf(i[46]);
		String filedKeyUpdate7 = String.valueOf(i[47]);
		String filedKeyUpdate8 = String.valueOf(i[48]);
		String filedKeyUpdate9 = String.valueOf(i[49]);
		String filedKeyUpdate10 = String.valueOf(i[50]);
		String filedKeyUpdate11 = String.valueOf(i[51]);
		String filedKeyUpdate12 = String.valueOf(i[52]);
		String filedKeyUpdate13 = String.valueOf(i[53]);
		String filedKeyUpdate14 = String.valueOf(i[54]);
		String filedKeyUpdate15 = String.valueOf(i[55]);
		String filedKeyUpdate16 = String.valueOf(i[56]);
		String filedKeyUpdate17 = String.valueOf(i[57]);
		String filedKeyUpdate18 = String.valueOf(i[58]);
		String filedKeyUpdate19 = String.valueOf(i[59]);
		String filedKeyUpdate20 = String.valueOf(i[60]);
		String fieldDate1 = String.valueOf(i[61]);
		String fieldDate2 = String.valueOf(i[62]);
		String fieldDate3 = String.valueOf(i[63]);
		String fieldDate4 = String.valueOf(i[64]);
		String fieldDate5 = String.valueOf(i[65]);
		String fieldDate6 = String.valueOf(i[66]);
		String fieldDate7 = String.valueOf(i[67]);
		String fieldDate8 = String.valueOf(i[68]);
		String fieldDate9 = String.valueOf(i[69]);
		String fieldDate10 = String.valueOf(i[70]);
		String fieldDate11 = String.valueOf(i[71]);
		String fieldDate12 = String.valueOf(i[72]);
		String fieldDate13 = String.valueOf(i[73]);
		String fieldDate14 = String.valueOf(i[74]);
		String fieldDate15 = String.valueOf(i[75]);
		String fieldDate16 = String.valueOf(i[76]);
		String fieldDate17 = String.valueOf(i[77]);
		String fieldDate18 = String.valueOf(i[78]);
		String fieldDate19 = String.valueOf(i[79]);
		String fieldDate20 = String.valueOf(i[80]);
		int hasParentTblFlg = Integer.parseInt(String.valueOf(i[81]));
		String parentTblName = String.valueOf(i[82]);
		String parentTblJapanName = String.valueOf(i[83]);
		String fieldAcqDateTime = String.valueOf(i[84]);
		String fieldAcqEndDate = String.valueOf(i[85]);
		String fieldAcqEmployeeId = String.valueOf(i[86]);
		String fieldAcqStartDate = String.valueOf(i[87]);
		String fieldAcqCid = String.valueOf(i[88]);
		String fieldParent1 = String.valueOf(i[89]);
		String fieldParent2 = String.valueOf(i[90]);
		String fieldParent3 = String.valueOf(i[91]);
		String fieldParent4 = String.valueOf(i[92]);
		String fieldParent5 = String.valueOf(i[93]);
		String fieldParent6 = String.valueOf(i[94]);
		String fieldParent7 = String.valueOf(i[95]);
		String fieldParent8 = String.valueOf(i[96]);
		String fieldParent9 = String.valueOf(i[97]);
		String fieldParent10 = String.valueOf(i[98]);
		String fieldChild1 = String.valueOf(i[99]);
		String fieldChild2 = String.valueOf(i[100]);
		String fieldChild3 = String.valueOf(i[101]);
		String fieldChild4 = String.valueOf(i[102]);
		String fieldChild5 = String.valueOf(i[103]);
		String fieldChild6 = String.valueOf(i[104]);
		String fieldChild7 = String.valueOf(i[105]);
		String fieldChild8 = String.valueOf(i[106]);
		String fieldChild9 = String.valueOf(i[107]);
		String fieldChild10 = String.valueOf(i[108]);
		
		

		TableDeletionDataCsv dataDeletionCsv = new TableDeletionDataCsv(delId, delType, delCode, delName,
				supplementExplanation, categoryId, categoryName, timeStore, recoveryStorageRange, 
				otherCompanyCls, tableJapanName, tableEnglishName, historyCls, defaultCondKeyQuery, fieldKeyQuery1,
				fieldKeyQuery2, fieldKeyQuery3, fieldKeyQuery4, fieldKeyQuery5, fieldKeyQuery6, fieldKeyQuery7,
				fieldKeyQuery8, fieldKeyQuery9, fieldKeyQuery10, clsKeyQuery1, clsKeyQuery2, clsKeyQuery3, clsKeyQuery4,
				clsKeyQuery5, clsKeyQuery6, clsKeyQuery7, clsKeyQuery8, clsKeyQuery9, clsKeyQuery10, filedKeyUpdate1,
				filedKeyUpdate2, filedKeyUpdate3, filedKeyUpdate4, filedKeyUpdate5, filedKeyUpdate6, filedKeyUpdate7,
				filedKeyUpdate8, filedKeyUpdate9, filedKeyUpdate10, filedKeyUpdate11, filedKeyUpdate12,
				filedKeyUpdate13, filedKeyUpdate14, filedKeyUpdate15, filedKeyUpdate16, filedKeyUpdate17,
				filedKeyUpdate18, filedKeyUpdate19, filedKeyUpdate20, fieldDate1, fieldDate2, fieldDate3, fieldDate4,
				fieldDate5, fieldDate6, fieldDate7, fieldDate8, fieldDate9, fieldDate10, fieldDate11, fieldDate12,
				fieldDate13, fieldDate14, fieldDate15, fieldDate16, fieldDate17, fieldDate18, fieldDate19, fieldDate20,
				hasParentTblFlg, parentTblName, parentTblJapanName, fieldAcqDateTime, fieldAcqEndDate,
				fieldAcqEmployeeId, fieldAcqStartDate, fieldAcqCid, fieldParent1, fieldParent2, fieldParent3,
				fieldParent4, fieldParent5, fieldParent6, fieldParent7, fieldParent8, fieldParent9, fieldParent10,
				fieldChild1, fieldChild2, fieldChild3, fieldChild4, fieldChild5, fieldChild6, fieldChild7, fieldChild8,
				fieldChild9, fieldChild10, startDateOfDaily, endDateOfDaily, startMonthOfMonthly, endMonthOfMonthly,
				startYearOfMonthly, endYearOfMonthly, companyId);

		return dataDeletionCsv;
	}
	
	

	/**
	 * 
	 */
	@Override
	public List<List<String>> getDataForEachCaegory(TableDeletionDataCsv tableDelData, List<EmployeeDeletion> employeeDeletions) {
		Map<String, Object> parrams = new HashMap<>();
		String sqlStr = buildGetDataForEachCatSql(tableDelData, employeeDeletions, parrams);
		Query query = this.getEntityManager().createNativeQuery(sqlStr);
		for(Entry<String, Object> entry : parrams.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		List<Object[]> listTemp = (List<Object[]>)query.getResultList();
		return listTemp.stream().map(objects -> {
			List<String> record = new ArrayList<String>();
			for (Object field : objects) {
				record.add(String.valueOf(field));
			}
			return record;
        }).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param tableDelData
	 * @param employeeDeletions
	 * @param parrams
	 * @return
	 */
	private String buildGetDataForEachCatSql(TableDeletionDataCsv tableDelData, 
			List<EmployeeDeletion> employeeDeletions, Map<String, Object> parrams) {
		boolean hasParentTbl = tableDelData.hasParentTblFlg();
		StringBuffer buffer = new StringBuffer();
		// build select part
		buildSelectPart(buffer, tableDelData, hasParentTbl);
		//build form part
		buildFromPart(buffer, tableDelData.getTableEnglishName());
		//build inner joint
		if (hasParentTbl) {
			buildInnerJoint(buffer, tableDelData);
		}
		//build where part
		buildWherePart(buffer, tableDelData, employeeDeletions, parrams);
		return buffer.toString();
	}

	/**
	 * build the select part
	 * @param buffer
	 * @param tblName
	 * @param parentTblName
	 * @param acqCidField
	 * @param acqEmployeeField
	 * @param acqDateField
	 * @param acqStartDateField
	 * @param acqEndDateField
	 * @param hasParentTbl
	 */
	private void buildSelectPart(StringBuffer buffer, TableDeletionDataCsv tableDelData,
			boolean hasParentTbl) {
		String acqCidField = tableDelData.getFieldAcqCid();
		String acqEmployeeField = tableDelData.getFieldAcqEmployeeId();
		String acqDateField = tableDelData.getFieldAcqDateTime();
		String acqStartDateField = tableDelData.getFieldAcqStartDate();
		String acqEndDateField = tableDelData.getFieldAcqEndDate();
		String tblName = tableDelData.getTableEnglishName();
		String parentTblName = tableDelData.getParentTblName();
		
		String tblAcq = tblName;
		if (hasParentTbl) {
			tblAcq = parentTblName;
		}

		buffer.append("SELECT ");
		// acqCidField
		if (acqCidField != null && !"null".equals(acqCidField)) {
			buffer.append(tblAcq + "." + acqCidField + " AS H_CID, ");
		} else {
			buffer.append(" NULL AS H_CID, ");
		}
		
		// acqEmployeeField
		if (acqEmployeeField != null && !"null".equals(acqEmployeeField)) {
			buffer.append(tblAcq + "." + acqEmployeeField + " AS H_SID, ");
		} else {
			buffer.append(" NULL AS H_SID, ");
		}
		// acqDateField
		if (acqDateField != null && !"null".equals(acqDateField)) {
			buffer.append(tblAcq + "." + acqDateField + " AS H_DATE, ");
		} else {
			buffer.append(" NULL AS H_DATE, ");
		}
		// acqStartDateField
		if (acqStartDateField != null && !"null".equals(acqStartDateField)) {
			buffer.append(tblAcq + "." + acqStartDateField + " AS H_DATE_START, ");
		} else {
			buffer.append(" NULL AS H_DATE_START, ");
		}
		// acqEndDateField
		if (acqEndDateField != null && !"null".equals(acqEndDateField)) {
			buffer.append(tblAcq + "." + acqEndDateField + " AS H_DATE_END, ");
		} else {
			buffer.append(" NULL AS H_DATE_END, ");
		}
		
		if (hasParentTbl) {
			buffer.append(parentTblName + ".* ");
		} else {
			buffer.append(tblName + ".* ");
		}
	}
	
	/**
	 * 
	 * @param buffer
	 * @param tblName
	 */
	private void buildFromPart(StringBuffer buffer, String tblName) {
		buffer.append(" FROM " + tblName + " AS " + tblName);
	}
	
	/**
	 * build inner joint in case: the having parrent table
	 * @param buffer
	 * @param tableDelData
	 */
	private void buildInnerJoint(StringBuffer buffer, TableDeletionDataCsv tableDelData) {
		String tblName = tableDelData.getTableEnglishName();
		String parentTblName = tableDelData.getParentTblName();
		buffer.append(" INNER　JOIN " + parentTblName + " ON ");
		
		if (tableDelData.getFieldChild1() != null && !"null".equals(tableDelData.getFieldChild1()) 
				&& tableDelData.getFieldParent1() != null && !"null".equals(tableDelData.getFieldParent1())) {
			buffer.append(tblName + "." + tableDelData.getFieldChild1() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent1());
		}
		
		if (tableDelData.getFieldChild2()  != null && !"null".equals(tableDelData.getFieldChild2())
				&& tableDelData.getFieldParent2() != null && !"null".equals(tableDelData.getFieldParent2())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild2() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent2());
		}
		
		if (tableDelData.getFieldChild3() != null && !"null".equals(tableDelData.getFieldChild3())
				&& tableDelData.getFieldParent3() != null && !"null".equals(tableDelData.getFieldParent3())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild3() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent3());
		}
		
		if (tableDelData.getFieldChild4() != null && !"null".equals(tableDelData.getFieldChild4())
				&& tableDelData.getFieldParent4() != null && !"null".equals(tableDelData.getFieldChild4())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild4() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent4());
		}
		
		if (tableDelData.getFieldChild5() != null && !"null".equals(tableDelData.getFieldChild5())
				&& tableDelData.getFieldParent5() != null && !"null".equals(tableDelData.getFieldChild5())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild5() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent5());
		}
		
		if (tableDelData.getFieldChild6() != null && !"null".equals(tableDelData.getFieldChild6())
				&& tableDelData.getFieldParent6() != null && !"null".equals(tableDelData.getFieldChild6())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild6() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent6());
		}
		
		if (tableDelData.getFieldChild7() != null  && !"null".equals(tableDelData.getFieldChild7())
				&& tableDelData.getFieldParent7() != null && !"null".equals(tableDelData.getFieldChild7())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild7() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent7());
		}
		
		if (tableDelData.getFieldChild8() != null  && !"null".equals(tableDelData.getFieldChild8())
				&& tableDelData.getFieldParent8() != null && !"null".equals(tableDelData.getFieldChild8())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild8() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent8());
		}
		
		if (tableDelData.getFieldChild9() != null  && !"null".equals(tableDelData.getFieldChild9())
				&& tableDelData.getFieldParent9() != null && !"null".equals(tableDelData.getFieldChild9())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild9() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent9());
		}
		
		if (tableDelData.getFieldChild10() != null  && !"null".equals(tableDelData.getFieldChild10())
				&& tableDelData.getFieldParent10() != null && !"null".equals(tableDelData.getFieldChild10())) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild10() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent10());
		}
	}
	
	/**
	 * 
	 * @param buffer
	 * @param tableDelData
	 * @param employeeDeletions
	 * @param parrams
	 */
	private void buildWherePart(StringBuffer buffer, TableDeletionDataCsv tableDelData, 
			List<EmployeeDeletion> employeeDeletions, Map<String, Object> parrams) {
		int timeStore = tableDelData.getTimeStore();
		String tblAcq = tableDelData.getTableEnglishName();
		if (tableDelData.hasParentTblFlg()) {
			tblAcq = tableDelData.getParentTblName();
		}
		
		String lstEmployeeIds = "";
		if (employeeDeletions != null && employeeDeletions.size() > 0) {
			StringBuffer employeeIdsBuf = new StringBuffer();
			for (EmployeeDeletion employeeDeletion : employeeDeletions) {
				employeeIdsBuf.append(employeeDeletion.getEmployeeId() + ",");
			}
			lstEmployeeIds = employeeIdsBuf.toString();
			lstEmployeeIds = lstEmployeeIds.substring(0, lstEmployeeIds.length() - 1);
		}
		
		
		buffer.append(" WHERE 1 = 1 ");
		
		//company id
		if (tableDelData.getFieldAcqCid() != null && !"null".equals(tableDelData.getFieldAcqCid())) {
			buffer.append("AND " + tblAcq + "." + tableDelData.getFieldAcqCid() + " = ?cid ");
			parrams.put("cid", tableDelData.getCompanyId());
		}
		
		//employee id
		if (tableDelData.getFieldAcqEmployeeId() != null && !"null".equals(tableDelData.getFieldAcqEmployeeId())) {
			if (lstEmployeeIds != null && !lstEmployeeIds.isEmpty()) {
				buffer.append("AND " + tblAcq + "." + tableDelData.getFieldAcqEmployeeId() + " IN ( ?employeeIds ) ");
				parrams.put("employeeIds", lstEmployeeIds);
			}
		}
		
		//date
		String acqDateTimeField = tableDelData.getFieldAcqDateTime();
		if (acqDateTimeField != null && !"null".equals(acqDateTimeField)) {
			if (!isDateFieldInOracle(acqDateTimeField, tableDelData)) {
				buffer.append("AND " + tblAcq + "." + acqDateTimeField + " >= ?startDate ");
				buffer.append("AND " + tblAcq + "." + acqDateTimeField + " <= ?endDate ");
			} else {
				buffer.append("AND " + tblAcq + "." + acqDateTimeField + " >= " + toDateOracle(timeStore, " ?startDate "));
				buffer.append("AND " + tblAcq + "." + acqDateTimeField + " <= " + toDateOracle(timeStore, " ?endDate "));
			}
			setDateParrams(tableDelData, parrams);
		}
		
		//period date
		String acqStartDateField = tableDelData.getFieldAcqStartDate();
		String acqEndDateField = tableDelData.getFieldAcqEndDate();
		
		if (acqStartDateField != null && !"null".equals(acqStartDateField)
				&& acqEndDateField != null && !"null".equals(acqEndDateField)) {
			if (!isDateFieldInOracle(acqStartDateField, tableDelData)) {
				buffer.append("AND " + tblAcq + "." + acqStartDateField + " >= ?startDate ");
				buffer.append("AND " + tblAcq + "." + acqEndDateField + " <= ?endDate ");
			} else {
				buffer.append("AND " + tblAcq + "." + acqStartDateField + " >= " + toDateOracle(timeStore, " ?startDate "));
				buffer.append("AND " + tblAcq + "." + acqEndDateField + " <= " + toDateOracle(timeStore, " ?endDate "));
			}
			setDateParrams(tableDelData, parrams);
		}
		
		//condition default
		if (tableDelData.getDefaultCondKeyQuery() != null && !"null".equals(tableDelData.getDefaultCondKeyQuery())) {
			buffer.append("AND " + tableDelData.getDefaultCondKeyQuery());
		}
	}
	
	/**
	 * 
	 * @param nameField
	 * @param tableDelData
	 * @return
	 */
	private boolean isDateFieldInOracle(String nameField, TableDeletionDataCsv tableDelData) {
		if (nameField.equals(tableDelData.getFieldDate1()) || nameField.equals(tableDelData.getFieldDate2())
				|| nameField.equals(tableDelData.getFieldDate3()) || nameField.equals(tableDelData.getFieldDate4())
				|| nameField.equals(tableDelData.getFieldDate5()) || nameField.equals(tableDelData.getFieldDate6())
				|| nameField.equals(tableDelData.getFieldDate7()) || nameField.equals(tableDelData.getFieldDate8())
				|| nameField.equals(tableDelData.getFieldDate9()) || nameField.equals(tableDelData.getFieldDate10())
				|| nameField.equals(tableDelData.getFieldDate11()) || nameField.equals(tableDelData.getFieldDate12())
				|| nameField.equals(tableDelData.getFieldDate13()) || nameField.equals(tableDelData.getFieldDate14())
				|| nameField.equals(tableDelData.getFieldDate15()) || nameField.equals(tableDelData.getFieldDate16())
				|| nameField.equals(tableDelData.getFieldDate17()) || nameField.equals(tableDelData.getFieldDate18())
				|| nameField.equals(tableDelData.getFieldDate19()) || nameField.equals(tableDelData.getFieldDate20())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param timeStoreValue
	 * @param key
	 * @return
	 */
	private String toDateOracle(int timeStoreValue, String key) {
		TimeStore timeStore = TimeStore.valueOf(timeStoreValue);
		if (timeStore == TimeStore.DAILY) {
			return " TO_DATE(" + key +  ",YYYY-MM-DD') ";
		} else if (timeStore == TimeStore.MONTHLY) {
			return " TO_DATE(" + key +  ",YYYY-MM') ";
		} else if (timeStore == TimeStore.ANNUAL) {
			return " TO_DATE(" + key +  ",YYYY') ";
		}
		return null;
	}
	
	/**
	 * 
	 * @param tableDelData
	 * @param parrams
	 */
	private void setDateParrams(TableDeletionDataCsv tableDelData, Map<String, Object> parrams) {
		TimeStore timeStore = TimeStore.valueOf(tableDelData.getTimeStore());
		if (timeStore == TimeStore.DAILY) {
			parrams.put("startDate", tableDelData.getStartDateOfDaily());
			parrams.put("endDate", tableDelData.getEndDateOfDaily());
		} else if (timeStore == TimeStore.MONTHLY) {
			parrams.put("startDate", tableDelData.getStartMonthOfMonthly());
			parrams.put("endDate", tableDelData.getEndMonthOfMonthly());
		} else if (timeStore == TimeStore.ANNUAL) {
			parrams.put("startDate", tableDelData.getStartYearOfMonthly());
			parrams.put("endDate", tableDelData.getEndYearOfMonthly());
		}
	}

	/**
	 * 
	 */
	@Override
	public List<String> getColumnName(String nameTable) {
		List<String> listTemp = this.getEntityManager().createNativeQuery(SELECT_COLUMN_NAME_SQL)
				.setParameter("tableName", nameTable).getResultList();

		if (listTemp == null || listTemp.isEmpty()) {
			return Collections.emptyList();
		}
		return listTemp.stream().map(temp -> {
			return String.valueOf(temp);
		}).collect(Collectors.toList());
	}

	/**
	 * 
	 */
	@Override
	public int deleteData(TableDeletionDataCsv tableDelData, List<EmployeeDeletion> employeeDeletions) {
		Map<String, Object> parrams = new HashMap<>();
		String sqlStr = buildDelDataForEachCatSql(tableDelData, employeeDeletions, parrams);
		Query query = this.getEntityManager().createNativeQuery(sqlStr);
		for(Entry<String, Object> entry : parrams.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		int numberDel = query.executeUpdate();
		return numberDel;
	}
	
	/**
	 * 
	 * @param tableDelData
	 * @param employeeDeletions
	 * @param parrams
	 * @return
	 */
	private String buildDelDataForEachCatSql(TableDeletionDataCsv tableDelData, 
			List<EmployeeDeletion> employeeDeletions, Map<String, Object> parrams) {
		StringBuffer buffer = new StringBuffer();
		//build form part
		buildFromDelPart(buffer, tableDelData.getTableEnglishName());
		//build where part
		buildWhereDelPart(buffer, tableDelData, employeeDeletions, parrams);
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param buffer
	 * @param tblName
	 */
	private void buildFromDelPart(StringBuffer buffer, String tblName) {
		buffer.append(" DELETE FROM " + tblName );
	}
	
	/**
	 * 
	 * @param buffer
	 * @param tableDelData
	 * @param employeeDeletions
	 * @param parrams
	 */
	private void buildWhereDelPart(StringBuffer buffer, TableDeletionDataCsv tableDelData, 
			List<EmployeeDeletion> employeeDeletions, Map<String, Object> parrams) {
		if (!tableDelData.hasParentTblFlg()) {
			buildWherePart(buffer, tableDelData, employeeDeletions, parrams);
		}
		else {
			buildWhereExist(buffer, tableDelData, employeeDeletions, parrams);
		}
	}
	
	/**
	 * 
	 * @param buffer
	 * @param tableDelData
	 * @param employeeDeletions
	 * @param parrams
	 */
	private void buildWhereExist(StringBuffer buffer, TableDeletionDataCsv tableDelData, 
			List<EmployeeDeletion> employeeDeletions, Map<String, Object> parrams) {
		
		String tblName = tableDelData.getTableEnglishName();
		String parentTblName = tableDelData.getParentTblName();
		
		buffer.append(" where ");
		buffer.append(" exists ( ");
		buffer.append(" select ");
		
		if (tableDelData.getFieldParent1() != null) {
			buffer.append(tableDelData.getFieldParent1());
		}
		
		if (tableDelData.getFieldParent2() != null) {
			buffer.append(", " + tableDelData.getFieldParent2());
		}
		
		if (tableDelData.getFieldParent3() != null) {
			buffer.append(", " + tableDelData.getFieldParent3());
		}
		
		if (tableDelData.getFieldParent4() != null) {
			buffer.append(", " + tableDelData.getFieldParent4());
		}
		
		if (tableDelData.getFieldParent5() != null) {
			buffer.append(", " + tableDelData.getFieldParent5());
		}
		
		if (tableDelData.getFieldParent6() != null) {
			buffer.append(", " + tableDelData.getFieldParent6());
		}
		
		if (tableDelData.getFieldParent7() != null) {
			buffer.append(", " + tableDelData.getFieldParent7());
		}
		
		if (tableDelData.getFieldParent8() != null) {
			buffer.append(", " + tableDelData.getFieldParent8());
		}
		
		if (tableDelData.getFieldParent9() != null) {
			buffer.append(", " + tableDelData.getFieldParent9());
		}
		
		if (tableDelData.getFieldParent10() != null) {
			buffer.append(", " + tableDelData.getFieldParent10());
		}
		
		buffer.append(" from " + parentTblName);
		
		//where for select
		buildWherePart(buffer, tableDelData, employeeDeletions, parrams);
		
		//build 
		if (tableDelData.getFieldChild1() != null && tableDelData.getFieldParent1() != null) {
			buffer.append(" AND " + tblName+ tableDelData.getFieldChild1() 
				+ " = " + parentTblName + tableDelData.getFieldParent1());
		}
		
		if (tableDelData.getFieldChild2() != null && tableDelData.getFieldParent2() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild2() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent2());
		}
		
		if (tableDelData.getFieldChild3() != null && tableDelData.getFieldParent3() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild3() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent3());
		}
		
		if (tableDelData.getFieldChild4() != null && tableDelData.getFieldParent4() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild4() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent4());
		}
		
		if (tableDelData.getFieldChild5() != null && tableDelData.getFieldParent5() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild5() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent5());
		}
		
		if (tableDelData.getFieldChild6() != null && tableDelData.getFieldParent6() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild6() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent6());
		}
		
		if (tableDelData.getFieldChild7() != null && tableDelData.getFieldParent7() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild7() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent7());
		}
		
		if (tableDelData.getFieldChild8() != null && tableDelData.getFieldParent8() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild8() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent8());
		}
		
		if (tableDelData.getFieldChild9() != null && tableDelData.getFieldParent9() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild9() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent9());
		}
		
		if (tableDelData.getFieldChild10() != null && tableDelData.getFieldParent10() != null) {
			buffer.append(" AND " + tblName + "." + tableDelData.getFieldChild10() 
				+ " = " + parentTblName + "." + tableDelData.getFieldParent10());
		}
	}
}
