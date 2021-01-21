package nts.uk.file.com.infra.subscriberegis;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.file.com.app.subscriberegis.SubscribeRegisColumn;
import nts.uk.file.com.app.subscriberegis.SubscribeRegisRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.util.*;


@Stateless
public class SubscribeRegisImpl extends JpaRepository implements SubscribeRegisRepository {

    private static final String GET_EXPORT_EXCEL = "SELECT "
    		+"		CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.SCD"
    		+"		ELSE NULL"
    		+"		END SCD,"
    		+"			CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.BUSINESS_NAME"
    		+"		ELSE NULL"
    		+"		END BUSINESS_NAME, "
    		+"		TABLE_RESULT.START_DATE, TABLE_RESULT.END_DATE, TABLE_RESULT.PersonName, TABLE_RESULT.AGENT_APP_TYPE2, TABLE_RESULT.AGENT_APP_TYPE3, TABLE_RESULT.AGENT_APP_TYPE4 	"
    		+"FROM ("
    		+"			SELECT emp.SCD, p.BUSINESS_NAME, ag.START_DATE, ag.END_DATE, ag.BUSINESS_NAME as PersonName, ag.AGENT_APP_TYPE2, ag.AGENT_APP_TYPE3, ag.AGENT_APP_TYPE4 , ROW_NUMBER() OVER" +"(PARTITION BY emp.SCD ORDER BY emp.SCD) AS ROW_NUMBER"
    		+"									 FROM BSYMT_SYAIN emp JOIN BPSMT_PERSON p ON emp.PID = p.PID  "
    		+"									INNER JOIN  "
    		+"									(Select cag.SID,  cag.START_DATE, cag.END_DATE, cag.AGENT_SID1, cag.AGENT_APP_TYPE1, cag.AGENT_SID2, cag.AGENT_APP_TYPE2, cag.AGENT_SID3," +"cag.AGENT_APP_TYPE3, cag.AGENT_SID4, cag.AGENT_APP_TYPE4,  empp.SCD, pp.BUSINESS_NAME  "
    		+"									FROM WWFMT_AGENT cag  JOIN BSYMT_SYAIN empp ON cag.AGENT_SID1 = empp.SID  "
    		+"									JOIN BPSMT_PERSON pp  ON empp.PID = pp.PID) ag ON emp.SID = ag.SID  "
    		+"									WHERE emp.SID IN ('%s') AND emp.CID =  ? ) TABLE_RESULT"
    		+" ORDER BY TABLE_RESULT.SCD , TABLE_RESULT.END_DATE DESC ";



    @Override
    @SneakyThrows
    public List<MasterData> getDataExport(String companyId, List<String>  employeeIds) {

        List<MasterData> datas = new ArrayList<>();
        CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,(sublist)-> {
            String params = String.join("','",sublist);
            String sql = String.format(GET_EXPORT_EXCEL,params);
                    try(PreparedStatement stmt = this.connection().prepareStatement(sql)) {
                                stmt.setString(1,companyId);
                                datas.addAll(new NtsResultSet(stmt.executeQuery()).getList(x ->toMasterData(x)));

                    }catch(Exception e) {
                        throw new RuntimeException(e);
                    }
            });
        return datas;
    }

    private MasterData toMasterData(NtsResultSet.NtsResultRecord r){
        Map<String, Object> data = new HashMap<>();

        //employeeCode
        data.put(SubscribeRegisColumn.CMM044_42, r.getString("SCD"));
        //employeeName
        data.put(SubscribeRegisColumn.CMM044_43, r.getString("BUSINESS_NAME"));
        //startDate
        data.put(SubscribeRegisColumn.CMM044_44, GeneralDate.localDate(r.getDate("START_DATE").toLocalDate()));
        //endDate
        data.put(SubscribeRegisColumn.CMM044_45, GeneralDate.localDate(r.getDate("END_DATE").toLocalDate()));
        //type
        data.put(SubscribeRegisColumn.CMM044_46, TextResource.localize("CMM044_16"));
        //personame
        data.put(SubscribeRegisColumn.CMM044_47, r.getString("PersonName"));

        return new MasterData(data,null,"");
    }

}
