package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaGuaByTheInsurExportRepository extends JpaRepository implements GuaByTheInsurExportRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtComStatutoryWrite f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.comStatutoryWritePk.cid =:cid order by f.comStatutoryWritePk.code ASC";

    @Override
    public Object[] getInforCompanyByCid(String cid) {
        Object[] resultQuery = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT");
        exportSQL.append("  BC.NAME,");
        exportSQL.append("  BA.ADDRESS_1,");
        exportSQL.append("  BA.ADDRESS_2,");
        exportSQL.append("  BA.PHONE_NUMBER,");
        exportSQL.append("  BA.POSTAL_CODE,");
        exportSQL.append("  BC.REPRESENTATIVE_NAME ");
        exportSQL.append(" FROM");
        exportSQL.append("  BCMMT_COMPANY BC");
        exportSQL.append("  INNER JOIN BCMMT_ADDRESS BA ON BC.CID = BA.CID ");
        exportSQL.append("  AND BC.CID =:cid");
        resultQuery = (Object[]) this.getEntityManager().createNativeQuery(exportSQL.toString())
                .setParameter("cid",cid)
                .getSingleResult();
        return resultQuery;
    }

    @Override
    public List<Object[]> getDataExport(List<String> empIds, String cid,String userId, GeneralDate startDate, GeneralDate endDate) {

        List<Object[]> resultQuery ;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT");
        exportSQL.append("  QSINS.OFFICE_INFORMATION,");
        exportSQL.append("  BC.NAME,");
        exportSQL.append("  QSIO.NAME,");
        exportSQL.append("  BA.ADDRESS_1,");
        exportSQL.append("  QSIO.ADDRESS_1,");
        exportSQL.append("  BA.ADDRESS_2,");
        exportSQL.append("  QSIO.ADDRESS_2,");
        exportSQL.append("  BA.PHONE_NUMBER,");
        exportSQL.append("  QSIO.PHONE_NUMBER,");
        exportSQL.append("  BA.POSTAL_CODE,");
        exportSQL.append("  QSIO.POSTAL_CODE,");
        exportSQL.append("  BC.REPRESENTATIVE_NAME,");
        exportSQL.append("  QSIO.REPRESENTATIVE_NAME,");
        exportSQL.append("  QSINS.SUBMITTED_NAME,");
        exportSQL.append("  QSINS.BUSSINESS_ARR_SYMBOL,");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER_1,");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER_2,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("  BP.PERSON_NAME,");
        exportSQL.append("  BP.TODOKEDE_FNAME,");
        exportSQL.append("  BP.PERSON_NAME,");
        exportSQL.append("  BP.TODOKEDE_FNAME,");
        exportSQL.append("  BP.PERSON_NAME_KANA,");
        exportSQL.append("  BP.TODOKEDE_FNAME_KANA,");
        exportSQL.append("  BP.PERSON_NAME_KANA,");
        exportSQL.append("  BP.TODOKEDE_FNAME_KANA,");
        exportSQL.append("  BP.BIRTHDAY,");
        exportSQL.append("  BP.BIRTHDAY,");
        exportSQL.append("  BP.GENDER,");
        exportSQL.append("  QTPPI.HISTORY_ID,");
        exportSQL.append("  QEPI.UNDERGOUND_DIVISION,");
        exportSQL.append("  QSII.QUALIFI_DISTIN,");
        exportSQL.append("  QEBPN.BASIC_PEN_NUMBER,");
        exportSQL.append("  QEHIQ.START_DATE,");
        exportSQL.append("  QEWIQI.START_DATE,");
        exportSQL.append("  QEHIQ.START_DATE,");
        exportSQL.append("  QEWIQI.START_DATE,");
        exportSQL.append("  QSII.DEPEN_APPOINT,");
        exportSQL.append("  QSII.REMUN_MONTHLY_AMOUNT,");
        exportSQL.append("  QSII.REMUN_MONTHLY_AMOUNT_KIND,");
        exportSQL.append("  QSII.TOTAL_MONTHLY_REMUN,");
        exportSQL.append("  QSII.PERCENT_OR_MORE,");
        exportSQL.append("  QMEWI.IS_MORE_EMP,");
        exportSQL.append("  QSII.SHORT_TIME_WORKES,");
        exportSQL.append("  QSII.CONTIN_REEM_AFTER_RETIREMENT,");
        exportSQL.append("  QSII.REMARKS_OTHER,");
        exportSQL.append("  QSII.REMARKS_AND_OTHER_CONTENTS,");
        exportSQL.append("  QSII.LIVING_ABROAD,");
        exportSQL.append("  QSII.SHORT_STAY,");
        exportSQL.append("  QSII.REASON_OTHER,");
        exportSQL.append("  QSII.REASON_AND_OTHER_CONTENTS, ");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER ");
        exportSQL.append(" FROM ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_CORP_OFF_HIS QECOH ");
        exportSQL.append("    WHERE EMPLOYEE_ID IN ('%s') ");
        exportSQL.append("      AND QECOH.END_DATE <= ?endDate ");
        exportSQL.append("      AND QECOH.END_DATE >= ?startDate  ) AS ROOT ");
        exportSQL.append(" LEFT JOIN QQSMT_SOC_INSU_NOTI_SET QSINS ON QSINS.CID = ?cid  ");
        exportSQL.append(" AND QSINS.USER_ID = ?userId ");
        exportSQL.append(" LEFT JOIN QPBMT_SOCIAL_INS_OFFICE QSIO ");
        exportSQL.append(" ON QSIO.CID = ?cid AND QSIO.CODE = ROOT.SOCIAL_INSURANCE_OFFICE_CD ");
        exportSQL.append(" LEFT JOIN BCMMT_COMPANY BC ON BC.CID = QSIO.CID ");
        exportSQL.append(" LEFT JOIN BCMMT_ADDRESS BA ON BA.CID = QSIO.CID ");
        exportSQL.append(" LEFT JOIN BSYMT_EMP_DTA_MNG_INFO BEDMI ON BEDMI.SID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN BPSMT_PERSON BP ON BEDMI.PID = BP.PID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_TEM_PEN_PART_INFO QTPPITEM ");
        exportSQL.append("    WHERE ");
        exportSQL.append("       QTPPITEM.END_DATE <= ?endDate ");
        exportSQL.append("      AND QTPPITEM.END_DATE >= ?startDate ) AS QTPPI ");
        exportSQL.append(" ON QTPPI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_PEN_INS QEPITEM ");
        exportSQL.append("    WHERE QEPITEM.END_DATE <= ?endDate ");
        exportSQL.append("      AND QEPITEM.END_DATE >= ?startDate ) AS QEPI ");
        exportSQL.append(" ON QEPI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN QQSMT_SOC_ISACQUISI_INFO QSII ");
        exportSQL.append(" ON QSII.EMPLOYEE_ID = ROOT.EMPLOYEE_ID AND QSII.CID =  QSIO.CID ");
        exportSQL.append(" LEFT JOIN QQSMT_EMP_BA_PEN_NUM QEBPN ");
        exportSQL.append(" ON QEBPN.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_HEAL_INSUR_QI QEHIQTEM ");
        exportSQL.append("    WHERE QEHIQTEM.END_DATE <= ?endDate ");
        exportSQL.append("      AND QEHIQTEM.END_DATE >= ?startDate ) AS QEHIQ ");
        exportSQL.append(" ON QEHIQ.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN QQSMT_MULTI_EMP_WORK_IF QMEWI ");
        exportSQL.append(" ON QMEWI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_WELF_INS_QC_IF QEWIQITEMP ");
        exportSQL.append("    WHERE QEWIQITEMP.END_DATE <= ?endDate ");
        exportSQL.append("      AND QEWIQITEMP.END_DATE >= ?startDate ) AS QEWIQI ");
        exportSQL.append(" ON QEWIQI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        String sql = String.format(exportSQL.toString(), empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','")));
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql)
                    .setParameter("startDate", convertDate(startDate))
                    .setParameter("endDate", convertDate(endDate))
                    .setParameter("cid", cid)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;
    }
    private java.sql.Date convertDate(GeneralDate baseDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.sql.Date sqlDate = null;
        try {
            date = format.parse(baseDate.toString("yyyy-MM-dd"));
            sqlDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            return null;
        }
        return sqlDate;
    }

    @Override
    public CompanyInfor getCompanyInfor(String cid) {
        Object[] result = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("  SELECT");
        exportSQL.append("    CONTRACT_CD,");
        exportSQL.append("    NAME,");
        exportSQL.append("    MONTH_STR,");
        exportSQL.append("    ABOLITION_ATR,");
        exportSQL.append("    REPRESENTATIVE_NAME,");
        exportSQL.append("    REPRESENTATIVE_JOB,");
        exportSQL.append("    KNNAME,");
        exportSQL.append("    ABNAME,");
        exportSQL.append("    TAX_NO,");
        exportSQL.append("    FAX_NUM,");
        exportSQL.append("    ADDRESS_1,");
        exportSQL.append("    ADDRESS_2,");
        exportSQL.append("    KNNAME_1,");
        exportSQL.append("    KNNAME_2,");
        exportSQL.append("    POSTAL_CODE,");
        exportSQL.append("    PHONE_NUMBER");
        exportSQL.append("  FROM  (SELECT * FROM BCMMT_COMPANY ");
        exportSQL.append("        WHERE CID = ?cid) c ");
        exportSQL.append("  INNER JOIN BCMMT_ADDRESS i ON i.CID = c.CID");
        try {
            result = (Object[]) this.getEntityManager().createNativeQuery(exportSQL.toString()).setParameter("cid", cid)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return CompanyInfor.builder().companyId(cid)
                .companyCode(result[0].toString())
                .companyName(result[2].toString())
                .startMonth(((BigDecimal)result[3]).intValue())
                .isAbolition(((BigDecimal)result[4]).intValue())
                .repname(result[5] != null ? result[5].toString() : "")
                .repost(result[6] != null ? result[6].toString() : "")
                .comNameKana(result[7] != null ? result[7].toString() : "")
                .shortComName(result[8] != null ? result[8].toString() : "")
                .taxNo(result[9] != null ? result[9].toString() : "")
                .faxNum(result[10] != null ? result[10].toString() : "")
                .add_1(result[11] != null ? result[11].toString() : "")
                .add_2(result[12] != null ? result[12].toString() : "")
                .addKana_1(result[13] != null ? result[13].toString() : "")
                .addKana_2(result[14] != null ? result[14].toString() : "")
                .postCd(result[15] != null ? result[15].toString() : "")
                .phoneNum(result[16] != null ? result[16].toString() : "")
                .build()
                ;
    }
}
