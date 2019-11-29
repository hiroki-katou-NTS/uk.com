package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.*;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaEmpAddChangeInfoFileGenerator extends JpaRepository implements EmpAddChangeInfoExReposity {
    @Override
    public List<EmpFamilySocialInsCtgInfo> getEmpFamilySocialInsCtgInfoList(List<String> empIds, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	CID,");
            exportSQL.append("	START_DATE,");
            exportSQL.append("	END_DATE,");
            exportSQL.append("	DEPENDENT_ATR,");
            exportSQL.append("	FM_BS_PEN_NUM, ");
            exportSQL.append("	FAMILY_ID ");
            exportSQL.append("FROM");
            exportSQL.append("	QQSMT_EMP_FAMILY_INS_HIS ");
            exportSQL.append("WHERE");
            exportSQL.append("	CID = ?cid");
            exportSQL.append("	AND SID IN ( '%s' )");
            String emp = empIds.stream()
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
            return resultQuery.stream().map(i -> EmpFamilySocialInsCtgInfo.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .cid(i[1] == null ? "" : i[1].toString())
                    .startDate(i[2] == null ? null : convertToGDate(i[2].toString()))
                    .endDate(i[3] == null ? null : convertToGDate(i[3].toString()))
                    .dependent(i[4] == null ? null : Integer.parseInt(i[4].toString()))
                    .fmBsPenNum(i[5] == null ? "" : i[5].toString())
                    .familyId(i[6] == null ? null : Integer.parseInt(i[6].toString()))
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<EmpHealInsurQInfo> getEmpHealInsurQInfoList(List<String> empIds, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	CID,");
            exportSQL.append("	START_DATE,");
            exportSQL.append("	END_DATE,");
            exportSQL.append("	KENHO_NUM ");
            exportSQL.append("FROM");
            exportSQL.append("	QQSDT_KENHO_INFO ");
            exportSQL.append("WHERE");
            exportSQL.append("	CID = ?cid");
            exportSQL.append("	AND SID IN ( '%s' )");
            String emp = empIds.stream()
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
                return resultQuery.stream().map(i -> EmpHealInsurQInfo.builder()
                        .empId(i[0] == null ? "" : i[0].toString())
                        .cid(i[1] == null ? "" : i[1].toString())
                        .startDate(i[2] == null ? null : convertToGDate(i[2].toString()))
                        .endDate(i[3] == null ? null : convertToGDate(i[3].toString()))
                        .healInsurNumber(i[4] == null ? "" : i[4].toString())
                        .build()
                ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<EmpWelfarePenInsQualiInfo> getEmpWelfarePenInsQualiInfoList(List<String> empIds, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	CID,");
            exportSQL.append("	START_DATE,");
            exportSQL.append("	END_DATE,");
            exportSQL.append("	KOUHO_NU ");
            exportSQL.append("FROM");
            exportSQL.append("	QQSDT_KOUHO_INFO ");
            exportSQL.append("WHERE");
            exportSQL.append("	CID = ?cid");
            exportSQL.append("	AND SID IN ( '%s' )");
            String emp = empIds.stream()
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
            return resultQuery.stream().map(i -> EmpWelfarePenInsQualiInfo.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .cid(i[1] == null ? "" : i[1].toString())
                    .startDate(i[2] == null ? null : convertToGDate(i[2].toString()))
                    .endDate(i[3] == null ? null : convertToGDate(i[3].toString()))
                    .welPenNumber(i[4] == null ? null : i[4].toString())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<EmpCorpOffHisInfo> getEmpCorpOffHisInfo(List<String> empIds, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	CID,");
            exportSQL.append("	START_DATE,");
            exportSQL.append("	END_DATE,");
            exportSQL.append("	SYAHO_OFFICE_CD ");
            exportSQL.append("FROM");
            exportSQL.append("	QQSDT_SYAHO_OFFICE_INFO ");
            exportSQL.append("WHERE");
            exportSQL.append("	CID = ?cid");
            exportSQL.append("	AND SID IN ( '%s' )");
            String emp = empIds.stream()
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
            return resultQuery.stream().map(i -> EmpCorpOffHisInfo.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .cid(i[1] == null ? "" : i[1].toString())
                    .startDate(i[2] == null ? null : convertToGDate(i[2].toString()))
                    .endDate(i[3] == null ? null : convertToGDate(i[3].toString()))
                    .socialInsurOfficeCode(i[4] == null ? null : i[4].toString())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<EmPensionFundPartiPeriodInfo> getEmPensionFundPartiPeriodInfo(List<String> empIds, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	CID,");
            exportSQL.append("	START_DATE,");
            exportSQL.append("	END_DATE,");
            exportSQL.append("	KIKIN_NUM ");
            exportSQL.append("FROM");
            exportSQL.append("	QQSDT_KIKIN_INFO ");
            exportSQL.append("WHERE");
            exportSQL.append("	CID = ?cid");
            exportSQL.append("	AND SID IN ( '%s' )");
            String emp = empIds.stream()
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
            return resultQuery.stream().map(i -> EmPensionFundPartiPeriodInfo.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .cid(i[1] == null ? "" : i[1].toString())
                    .startDate(i[2] == null ? null : convertToGDate(i[2].toString()))
                    .endDate(i[3] == null ? null : convertToGDate(i[3].toString()))
                    .membersNumber(i[4] == null ? null : i[4].toString())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<HealInsurPortPerIntellInfo> getHealInsurPortPerIntellInfo(List<String> empIds, String cid) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("SELECT");
            exportSQL.append("	SID,");
            exportSQL.append("	CID,");
            exportSQL.append("	START_DATE,");
            exportSQL.append("	END_DATE,");
            exportSQL.append("	KNKUM_NUM ");
            exportSQL.append("FROM");
            exportSQL.append("	QQSDT_KNKUM_INFO ");
            exportSQL.append("WHERE");
            exportSQL.append("	CID = ?cid");
            exportSQL.append("	AND SID IN ( '%s' )");
            String emp = empIds.stream()
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
            return resultQuery.stream().map(i -> HealInsurPortPerIntellInfo.builder()
                    .empId(i[0] == null ? "" : i[0].toString())
                    .cid(i[1] == null ? "" : i[1].toString())
                    .startDate(i[2] == null ? null : convertToGDate(i[2].toString()))
                    .endDate(i[3] == null ? null : convertToGDate(i[3].toString()))
                    .healInsurUnionNumber(i[4] == null ? null : i[4].toString())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private GeneralDate convertToGDate(String Date) {
        if(Date.length() > 18) {
            return GeneralDate.fromString(Date.substring(0,19), "yyyy-MM-dd hh:mm:ss");
        }

        return GeneralDate.fromString(Date, "yyyy-MM-dd");
    }
}
