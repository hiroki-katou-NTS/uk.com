/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.insurance.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.pr.app.export.insurance.data.InsuranceOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceQuery;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceRepository;
import nts.uk.pr.file.infra.entity.ReportPismtPersonInsuSocial;
import nts.uk.pr.file.infra.insurance.entity.ReportQismtSocialInsuOffice;

/**
 * The Class JpaSalarySocialInsuranceRepository.
 *
 * @author duongnd
 */

@Stateless
public class JpaSalarySocialInsuranceRepository extends JpaRepository implements SalarySocialInsuranceRepository {
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.file.pr.app.export.insurance.salary.
     * SalarySocailInsuranceRepository#fincReportData(nts.uk.file.pr.app.export.
     * insurance.salary.SalarySocialInsuranceQuery)
     */
    @Override
    public SalarySocialInsuranceReportData findReportData(String companyCode, String loginPersonId,
            SalarySocialInsuranceQuery salaryQuery) {
        List<InsuranceOfficeDto> officeItems  = new ArrayList<>();
        List<Object[]> objects = findPersonOffices(companyCode, loginPersonId, salaryQuery);
        for (Object[] obs : objects) {
            ReportQismtSocialInsuOffice officeEntity = (ReportQismtSocialInsuOffice) obs[0];
            InsuranceOfficeDto officeDto = new InsuranceOfficeDto();
            officeDto.setCode(officeEntity.getQismtSocialInsuOfficePK().getSiOfficeCd());
            officeDto.setName(officeEntity.getSiOfficeName());
            List<ReportPismtPersonInsuSocial> personsEntity = (List<ReportPismtPersonInsuSocial>) obs[3];
            // TODO: find information of employee in office.
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private List<Object[]> findPersonOffices(String companyCode, String loginPersonId, SalarySocialInsuranceQuery salaryQuery) {
        EntityManager em = this.getEntityManager();
        String script = "SELECT so, pp, mp, ps "
                + "FROM ReportQismtSocialInsuOffice so, "
                + "ReportQpdptPayday pp, "
                + "ReportQpdmtPayday mp, "
                + "ReportPismtPersonInsuSocial ps "
                + "WHERE so.qismtSocialInsuOfficePK.ccd = :companyCode "
                + "AND so.qismtSocialInsuOfficePK.siOfficeCd IN :officeCodes "
                + "AND pp.qpdptPaydayPK.ccd = so.qismtSocialInsuOfficePK.ccd "
                + "AND pp.qpdptPaydayPK.pid = :loginPid "
                + "AND mp.qpdmtPaydayPK.ccd = pp.qpdptPaydayPK.ccd "
                + "AND mp.qpdmtPaydayPK.processingNo = pp.processingNo "
                + "AND mp.qpdmtPaydayPK.payBonusAtr = 0 "
                + "AND mp.qpdmtPaydayPK.processingYm = :yearMonth "
                + "AND mp.qpdmtPaydayPK.sparePayAtr = 0 "
                + "AND ps.pismtPersonInsuSocialPK.ccd = mp.qpdmtPaydayPK.ccd "
                + "AND ps.strD <= :yearMonth "
                + "AND ps.endD >= :yearMonth "
                + "AND ps.siOfficeCd IN :officeCodes ";
        Query query = em.createQuery(script);
        query.setParameter("companyCode", companyCode);
        List<String> officeCodes = salaryQuery.getInsuranceOffices().stream()
                .map(p -> p.getCode())
                .collect(Collectors.toList());
        query.setParameter("officeCodes", officeCodes);
        query.setParameter("loginPid", loginPersonId);
        query.setParameter("yearMonth", salaryQuery.getYearMonth());
        return query.getResultList();
    }
}
