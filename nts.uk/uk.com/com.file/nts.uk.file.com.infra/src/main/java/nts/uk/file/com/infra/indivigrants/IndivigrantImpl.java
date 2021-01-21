package nts.uk.file.com.infra.indivigrants;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.file.com.app.indivigrants.IndivigrantColumn;
import nts.uk.file.com.app.indivigrants.IndivigrantRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class IndivigrantImpl  extends JpaRepository implements IndivigrantRepository {

	private static final String GET_EXPORT_EXCEL = 
			"SELECT" 						
			+" CASE WHEN TBL.ROW_NUMBER2 = 1 THEN TBL.ROLE_TYPE	"					
			+" ELSE NULL"					
			+" END ROLE_TYPE," 					
			+" CASE WHEN TBL.ROW_NUMBER1 = 1 THEN TBL.ROLE_CD" 						
			+" ELSE NULL"					
			+" END ROLE_CD,"					
			+" CASE WHEN TBL.ROW_NUMBER1 = 1 THEN TBL.ROLE_NAME"						
			+" ELSE NULL"					
			+" END ROLE_NAME,"					
			+" TBL.LOGIN_ID,"						
			+" TBL.BUSINESS_NAME," 						
			+" TBL.STR_D," 						
			+" TBL.END_D"						
			+" FROM "						
				+" (SELECT gr.ROLE_TYPE, role.ROLE_CD, role.ROLE_NAME,"						
							+" us.LOGIN_ID,"
							+" IIF(per.BUSINESS_NAME IS NOT NULL, per.BUSINESS_NAME, us.USER_NAME) AS BUSINESS_NAME,"
							+" gr.STR_D, gr.END_D, "		
							+" ROW_NUMBER() OVER (PARTITION BY role.ROLE_TYPE, role.ROLE_CD" 		
							+" ORDER BY  role.ROLE_TYPE ASC, role.ROLE_CD ASC, us.LOGIN_ID ASC) AS ROW_NUMBER1,"
							+" ROW_NUMBER() OVER (PARTITION BY role.ROLE_TYPE" 		
							+" ORDER BY  role.ROLE_TYPE ASC, role.ROLE_CD ASC, us.LOGIN_ID ASC) AS ROW_NUMBER2"	
				+" FROM SACMT_ROLE_INDIVI_GRANT gr"						
				+" INNER JOIN SACMT_USER us ON gr.USER_ID = us.USER_ID"						
				+" LEFT JOIN BPSMT_PERSON per ON per.PID = us.ASSO_PID"						
				+" INNER JOIN SACMT_ROLE role ON gr.ROLE_ID = role.ROLE_ID "
				+" AND role.ROLE_TYPE BETWEEN "+ RoleType.EMPLOYMENT.value +" AND "+ RoleType.PERSONAL_INFO.value +""					
				+" WHERE gr.CID = ? "
			    +		 " AND gr.STR_D <= CONVERT(DATETIME, ?, 102) "
			    +		 " AND gr.END_D >= CONVERT(DATETIME, ?, 102)"
				+ ") TBL";

	@Override
	public List<MasterData> getDataExport(String baseDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		try (PreparedStatement stmt = this.connection().prepareStatement(GET_EXPORT_EXCEL.toString())){
			stmt.setString(1, cid);
			stmt.setString(2, baseDate);
			stmt.setString(3, baseDate);
			datas.addAll(new NtsResultSet(stmt.executeQuery()).getList(i->buildMasterListData(i)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;
	}

	private MasterData buildMasterListData(NtsResultRecord r){
		Map<String,MasterCellData> data = new HashMap<>();
			data.put(IndivigrantColumn.CAS013_40, MasterCellData.builder()
                .columnId(IndivigrantColumn.CAS013_40)
                .value(r.getInt("ROLE_TYPE") != null ? getTypeName(r.getInt("ROLE_TYPE")) : null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(IndivigrantColumn.CAS013_41, MasterCellData.builder()
                .columnId(IndivigrantColumn.CAS013_41)
                .value(r.getString("ROLE_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(IndivigrantColumn.CAS013_42, MasterCellData.builder()
                .columnId(IndivigrantColumn.CAS013_42)
                .value(r.getString("ROLE_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(IndivigrantColumn.CAS013_43, MasterCellData.builder()
                .columnId(IndivigrantColumn.CAS013_43)
                .value(r.getString("LOGIN_ID"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(IndivigrantColumn.CAS013_44, MasterCellData.builder()
                .columnId(IndivigrantColumn.CAS013_44)
                .value(r.getString("BUSINESS_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(IndivigrantColumn.CAS013_45, MasterCellData.builder()
                .columnId(IndivigrantColumn.CAS013_45)
                .value(GeneralDate.localDate(((Date) (r.getDate("STR_D"))).toLocalDate()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(IndivigrantColumn.CAS013_46, MasterCellData.builder()
                .columnId(IndivigrantColumn.CAS013_46)
                .value(GeneralDate.localDate(((Date) (r.getDate("END_D"))).toLocalDate()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	private String getTypeName(int roleType) {
		String nameType = null;
		RoleType type = EnumAdaptor.valueOf(roleType, RoleType.class);
		switch (type) {
			case SYSTEM_MANAGER:
				nameType = I18NText.getText("Enum_RoleType_systemManager");
				break;
			case COMPANY_MANAGER:
				nameType = I18NText.getText("Enum_RoleType_companyManager");
				break;
			case GROUP_COMAPNY_MANAGER:
				nameType = I18NText.getText("Enum_RoleType_groupCompanyManager");
				break;
			case EMPLOYMENT:
				nameType = I18NText.getText("Enum_RoleType_employment");
				break;
			case SALARY:
				nameType = I18NText.getText("Enum_RoleType_salary");
				break;
			case HUMAN_RESOURCE:
				nameType = I18NText.getText("Enum_RoleType_humanResource");
				break;
			case OFFICE_HELPER:
				nameType = I18NText.getText("Enum_RoleType_officeHelper");
				break;
			case MY_NUMBER:
				nameType = I18NText.getText("Enum_RoleType_myNumber");
				break;
			case PERSONAL_INFO:
				nameType = I18NText.getText("Enum_RoleType_personalInfo");
				break;
			default:
				break;
			}
		return nameType;
	}
	
}
