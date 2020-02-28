package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.*;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaRomajiNameNotiCreSetExReposity extends JpaRepository implements RomajiNameNotiCreSetExReposity {

    @Override
    public List<RomajiNameNotiCreSetExport> getEmpNameReportList(List<String> empList, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT ");
            exportSQL.append("	SID, ");
            exportSQL.append("	OTHER_ATR,");
            exportSQL.append("	SET_LISTED_ATR,");
            exportSQL.append("	RESIDENT_CARD_ATR,");
            exportSQL.append("	ADDRESS_OVERSEAS_ATR,");
            exportSQL.append("	SHORT_RESIDENT_ATR,");
            exportSQL.append("	OTHER_REASON,");
            exportSQL.append("	SPOUSE_OTHER_ATR,");
            exportSQL.append("	SPOUSE_SET_LISTED_ATR,");
            exportSQL.append("	SPOUSE_RESIDENT_CARD_ATR,");
            exportSQL.append("	SPOUSE_SHORT_RESIDENT_ATR,");
            exportSQL.append("	SPOUSE_OTHER_REASON, ");
            exportSQL.append("	SPOUSE_ADDRESS_OVERSEAS_ATR ");
            exportSQL.append("FROM");
            exportSQL.append("	QRSMT_EMP_ROMANNM_REPORT ");
            exportSQL.append("WHERE");
            exportSQL.append("	CID = ?cid");
            exportSQL.append("	AND SID IN ( '%s')");
            String emp = empList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("','"));
            String sql = String.format(exportSQL.toString(), emp);
            try {
                resultQuery = this.getEntityManager().createNativeQuery(sql)
                        .setParameter("cid", cid)
                        .getResultList();
            } catch (NoResultException e) {
                return Collections.emptyList();
            }
            return resultQuery.stream().map(i -> RomajiNameNotiCreSetExport.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .personalSetOther(i[1] == null ? 0 : ((BigDecimal) i[1]).intValue())
                    .personalSetListed(i[2] == null ? 0 : ((BigDecimal) i[2]).intValue())
                    .personalResidentCard(i[3] == null ? 0 : ((BigDecimal) i[3]).intValue())
                    .personalAddressOverseas(i[4] == null ? 0 : ((BigDecimal) i[4]).intValue())
                    .personalShortResident(i[5] == null ? 0 : ((BigDecimal) i[5]).intValue())
                    .personalOtherReason(i[6] == null ? "" : i[6].toString())
                    .spouseSetOther(i[7] == null ? 0 : ((BigDecimal) i[7]).intValue())
                    .spouseSetListed(i[8] == null ? 0 : ((BigDecimal) i[8]).intValue())
                    .spouseResidentCard(i[9] == null ? 0 : ((BigDecimal) i[9]).intValue())
                    .spouseShortResident(i[10] == null ? 0 : ((BigDecimal) i[10]).intValue())
                    .spouseOtherReason(i[11] == null ? "" : i[11].toString())
                    .spouseAddressOverseas(i[12] == null ? 0 : ((BigDecimal) i[12]).intValue())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RomajiNameNotiCreSetExport> getEmpBasicPenNumInforList(List<String> empList, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	KISONEN_NUM ");
            exportSQL.append("FROM");
            exportSQL.append("	QQSDT_SYAHO_KNEN_NUM ");
            exportSQL.append("WHERE");
            exportSQL.append("	CID = ?cid ");
            exportSQL.append("	AND SID IN ('%s')");
            String emp = empList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("','"));
            String sql = String.format(exportSQL.toString(), emp);
            try {
                resultQuery = this.getEntityManager().createNativeQuery(sql)
                        .setParameter("cid", cid)
                        .getResultList();
            } catch (NoResultException e) {
                return Collections.emptyList();
            }
            return resultQuery.stream().map(i -> RomajiNameNotiCreSetExport.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .basicPenNumber(i[1] == null ? "" : i[1].toString())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RomajiNameNotiCreSetExport> getEmpFamilySocialInsList(List<String> empList, String cid, int familyId, GeneralDate baseDate) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	FM_BS_PEN_NUM ");
            exportSQL.append("FROM");
            exportSQL.append("	QQSMT_EMP_FAMILY_INS_HIS ");
            exportSQL.append("WHERE");
            exportSQL.append("	FAMILY_ID = ?familyId");
            exportSQL.append("	AND SID IN ( '%s' ) ");
            exportSQL.append("	AND CID = ?cid");
            exportSQL.append("	AND START_DATE <= ?baseDate");
            exportSQL.append("	AND END_DATE >=  ?baseDate");
            String emp = empList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("','"));
            String sql = String.format(exportSQL.toString(), emp);
            try {
                resultQuery = this.getEntityManager().createNativeQuery(sql)
                        .setParameter("cid", cid)
                        .setParameter("familyId", familyId)
                        .setParameter("baseDate", baseDate.date())
                        .getResultList();
            } catch (NoResultException e) {
                return Collections.emptyList();
            }
            return resultQuery.stream().map(i -> RomajiNameNotiCreSetExport.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .fmBsPenNum(i[1] == null ? "" : i[1].toString())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RomajiNameNotiCreSetExport> getSocialInsuranceOfficeList(List<String> empList, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	NAME,");
            exportSQL.append("	REPRESENTATIVE_NAME,");
            exportSQL.append("	ADDRESS_1,");
            exportSQL.append("	ADDRESS_2,");
            exportSQL.append("	PHONE_NUMBER,");
            exportSQL.append("	POSTAL_CODE ");
            exportSQL.append("FROM");
            exportSQL.append("	QPBMT_SOCIAL_INS_OFFICE");
            exportSQL.append("	INNER JOIN QQSDT_SYAHO_OFFICE_INFO ");
            exportSQL.append("	ON CODE = SYAHO_OFFICE_CD ");
            exportSQL.append("	AND QQSDT_SYAHO_OFFICE_INFO.CID = QPBMT_SOCIAL_INS_OFFICE.CID ");
            exportSQL.append("WHERE");
            exportSQL.append("	QPBMT_SOCIAL_INS_OFFICE.CID = ?cid ");
            exportSQL.append("	AND SID IN ( '%s' )");
            String emp = empList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("','"));
            String sql = String.format(exportSQL.toString(), emp);
            try {
                resultQuery = this.getEntityManager().createNativeQuery(sql)
                        .setParameter("cid", cid)
                        .getResultList();
            } catch (NoResultException e) {
                return Collections.emptyList();
            }
            return resultQuery.stream().map(i -> RomajiNameNotiCreSetExport.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .name(i[1] == null ? "" : i[1].toString())
                    .representativeName(i[2] == null ? "" : i[2].toString())
                    .address1(i[3] == null ? "" : i[3].toString())
                    .address2(i[4] == null ? "" : i[4].toString())
                    .phoneNumber(i[5] == null ? "" : i[5].toString())
                    .postalCode(i[6] == null ? "" : i[6].toString())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
