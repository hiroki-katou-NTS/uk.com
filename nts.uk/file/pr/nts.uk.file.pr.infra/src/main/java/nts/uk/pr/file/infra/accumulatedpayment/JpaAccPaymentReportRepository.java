/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.accumulatedpayment;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.file.pr.app.export.accumulatedpayment.AccPaymentRepository;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentItemData;
import nts.uk.file.pr.app.export.accumulatedpayment.query.AccPaymentReportQuery;



@Stateless
public class JpaAccPaymentReportRepository extends JpaRepository implements AccPaymentRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.screen.app.report.qet002.AccPaymentRepository
	 * #getItems(java.lang.String, nts.uk.ctx.pr.screen.app.report.qet002.query.AccPaymentReportQuery)
	 */
	@Override
	public List<AccPaymentItemData> getItems(String companyCode, AccPaymentReportQuery query) {
		// TODO Auto-generated method stub
		EntityManager em = this.getEntityManager();
		
		// Create Year Month.
		YearMonth startYearMonth = YearMonth.of(query.getTargetYear(), 1);
		YearMonth endYearMonth = YearMonth.of(query.getTargetYear(), 12);
		String queryString = "SELECT ec.empCd, e.employmentName"
				+ "FROM ReportPbsmtPersonBase p, "
				+ "ReportPclmtPersonEmpContract ec"
				+ "ReportCmnmtEmp e"
				+ "ReportQpdmtPayday pd"
				+ "WHERE p.pid = :PID"
				+ "AND ec.pclmtPersonEmpContractPK.ccd = :CCD"
				+ "AND p.pid = ec.pclmtPersonEmpContractPK.pId"
				+ "AND ec.pclmtPersonEmpContractPK.strD <= :BASE_YMD"
				+ "AND ec.pclmtPersonEmpContractPK.strD >= :BASE_YMD"
				+ "AND e.cmnmtEmpPk.companyCode = :CCD"
				+ "AND e.cmnmtEmpPk.employmentCode = ec.empCd"
				+ "AND pd.qpdmtPaydayPK.ccd = :CCD"
				+ "AND pd.qpdmtPaydayPK.payBonusAtr = :PAY_BONUS_ATR" //1
				+ "AND pd.qpdmtPaydayPK.processingNo = e.processingNo";
		
		return null;
	}
}
