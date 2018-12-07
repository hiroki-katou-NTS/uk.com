package nts.uk.file.com.infra.subscriberegis;

import nts.arc.layer.infra.data.DbConsts;
import nts.gul.collection.CollectionUtil;
import nts.uk.file.com.app.subscriberegis.SubscribeRegisColumn;
import nts.uk.file.com.app.subscriberegis.SubscribeRegisRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Stateless
public class SubscribeRegisImpl implements SubscribeRegisRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_EXPORT_EXCEL = "SELECT emp.SCD, p.BUSINESS_NAME, ag.START_DATE, ag.END_DATE, ag.BUSINESS_NAME as PersonName, ag.AGENT_APP_TYPE2, ag.AGENT_APP_TYPE3, ag.AGENT_APP_TYPE4" +
            " FROM BSYMT_EMP_DTA_MNG_INFO emp JOIN BPSMT_PERSON p ON emp.PID = p.PID " +
            "INNER JOIN " +
            "(Select cag.SID,  cag.START_DATE, cag.END_DATE, cag.AGENT_SID1, cag.AGENT_APP_TYPE1, cag.AGENT_SID2, cag.AGENT_APP_TYPE2, cag.AGENT_SID3, cag.AGENT_APP_TYPE3, cag.AGENT_SID4, cag.AGENT_APP_TYPE4,  empp.SCD, pp.BUSINESS_NAME " +
            "FROM CMMMT_AGENT cag  JOIN BSYMT_EMP_DTA_MNG_INFO empp ON cag.AGENT_SID1 = empp.SID " +
            "JOIN BPSMT_PERSON pp  ON empp.PID = pp.PID) ag ON emp.SID = ag.SID " +
            "WHERE emp.SID IN ('%s') AND emp.CID = ? ORDER BY emp.SCD , ag.END_DATE DESC";


    @Override
    public List<MasterData> getDataExport(String companyId, List<String>  employeeIds) {

        List<MasterData> datas = new ArrayList<>();
        CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,(sublist)-> {

            String params = String.join("','",sublist);
            String sql = String.format(GET_EXPORT_EXCEL,params);
            Query query = entityManager.createNativeQuery(sql).setParameter(1,companyId);

            List<Object[]> data = query.getResultList();
            for(Object[] objects : data){
                datas.add(new MasterData(dataContent(objects),null,""));
            }

        });

        return datas;
    }

    private Map<String, Object> dataContent(Object[] object){
        Map<String, Object> data = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        //employeeCode
        data.put(SubscribeRegisColumn.CMM044_42, object[0].toString());
        //employeeName
        data.put(SubscribeRegisColumn.CMM044_43, object[1].toString());
        //startDate
        data.put(SubscribeRegisColumn.CMM044_44, object[2] == null ? null : df.format(object[2]).toString());
        //endDate
        data.put(SubscribeRegisColumn.CMM044_45, object[3] == null ? null : df.format(object[3]).toString());
        //type
        data.put(SubscribeRegisColumn.CMM044_46, object[4] == null ? null : TextResource.localize("CMM044_16"));
        //personame
        data.put(SubscribeRegisColumn.CMM044_47, object[4] == null ? null : object[4].toString());
        return data;
    }

}
