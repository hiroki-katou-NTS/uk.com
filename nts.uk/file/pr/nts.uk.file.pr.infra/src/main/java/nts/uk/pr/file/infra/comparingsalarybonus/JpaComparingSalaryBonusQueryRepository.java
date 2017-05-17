package nts.uk.pr.file.infra.comparingsalarybonus;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsdtPaycompConfirm;
import nts.uk.file.pr.app.export.comparingsalarybonus.ComparingSalaryBonusQueryRepository;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.SalaryBonusDetail;

/**
 * The class JpaComparingSalaryBonusQueryRepository
 * 
 * @author lanlt
 *
 */
@Stateless
public class JpaComparingSalaryBonusQueryRepository extends JpaRepository
		implements ComparingSalaryBonusQueryRepository {

	private String SELECT_1 = "SELECT DISTINCT i.itemAbName, h.qstdtPaymentHeaderPK.processingYM, h.employeeName, h.specificationCode, h.makeMethodFlag, d.qstdtPaymentDetailPK.personId, "
			+ " d.qstdtPaymentDetailPK.categoryATR,  d.qstdtPaymentDetailPK.itemCode ,d.value, pb.nameB, pc.scd, dep.depName, dep.cmnmtDepPK.departmentCode, dep.hierarchyId"
			+ "  FROM QlsptPaycompFormDetail c" + " INNER JOIN QcamtItem_v1 i"
			+ " ON i.qcamtItemPK.ctgAtr = c.paycompFormDetailPK.categoryATR  AND i.qcamtItemPK.itemCd = c.paycompFormDetailPK.itemCode "
			+ " AND i.qcamtItemPK.ccd  = c.paycompFormDetailPK.companyCode  " + " INNER JOIN  QstdtPaymentHeader h"
			+ " ON  h.qstdtPaymentHeaderPK.companyCode = c.paycompFormDetailPK.companyCode"
			+ " INNER JOIN QstdtPaymentDetail d"
			+ " ON d.qstdtPaymentDetailPK.companyCode = c.paycompFormDetailPK.companyCode AND  d.qstdtPaymentDetailPK.processingNo = h.qstdtPaymentHeaderPK.processingNo"
			+ " AND d.qstdtPaymentDetailPK.sparePayAttribute = h.qstdtPaymentHeaderPK.sparePayAtr "
			+ " AND d.qstdtPaymentDetailPK.personId = h.qstdtPaymentHeaderPK.personId"
			+ " AND d.qstdtPaymentDetailPK.categoryATR = i.qcamtItemPK.ctgAtr "
			+ " AND d.qstdtPaymentDetailPK.itemCode = i.qcamtItemPK.itemCd " + " INNER JOIN PbsmtPersonBase pb"
			+ " ON pb.pid = d.qstdtPaymentDetailPK.personId"
			+ " INNER JOIN PcpmtPersonCom pc ON pc.pcpmtPersonComPK.pid = d.qstdtPaymentDetailPK.personId AND pc.pcpmtPersonComPK.ccd = d.qstdtPaymentDetailPK.companyCode"
			+ " INNER JOIN  CmnmtDep dep ON dep.cmnmtDepPK.companyCode = pc.pcpmtPersonComPK.ccd AND dep.startDate = h.standardDate "
			+ " WHERE c.paycompFormDetailPK.formCode = :formCode AND  h.qstdtPaymentHeaderPK.personId IN :personId"
			+ " AND h.qstdtPaymentHeaderPK.payBonusAtr = 0" + " AND h.qstdtPaymentHeaderPK.processingYM =:processingYM"
			+ " AND   c.paycompFormDetailPK.companyCode =:companyCode ORDER BY dep.cmnmtDepPK.departmentCode";
	private final String SELECT_DEPCODE = "SELECT DISTINCT dep.cmnmtDepPK.departmentCode"
			+ "  FROM QlsptPaycompFormDetail c" + " INNER JOIN QcamtItem_v1 i"
			+ " ON i.qcamtItemPK.ctgAtr = c.paycompFormDetailPK.categoryATR  AND i.qcamtItemPK.itemCd = c.paycompFormDetailPK.itemCode "
			+ " AND i.qcamtItemPK.ccd  = c.paycompFormDetailPK.companyCode  " + " INNER JOIN  QstdtPaymentHeader h"
			+ " ON  h.qstdtPaymentHeaderPK.companyCode = c.paycompFormDetailPK.companyCode"
			+ " INNER JOIN QstdtPaymentDetail d"
			+ " ON d.qstdtPaymentDetailPK.companyCode = c.paycompFormDetailPK.companyCode AND  d.qstdtPaymentDetailPK.processingNo = h.qstdtPaymentHeaderPK.processingNo"
			+ " AND d.qstdtPaymentDetailPK.sparePayAttribute = h.qstdtPaymentHeaderPK.sparePayAtr "
			+ " AND d.qstdtPaymentDetailPK.personId = h.qstdtPaymentHeaderPK.personId"
			+ " AND d.qstdtPaymentDetailPK.categoryATR = i.qcamtItemPK.ctgAtr "
			+ " AND d.qstdtPaymentDetailPK.itemCode = i.qcamtItemPK.itemCd " + " INNER JOIN PbsmtPersonBase pb"
			+ " ON pb.pid = d.qstdtPaymentDetailPK.personId"
			+ " INNER JOIN PcpmtPersonCom pc ON pc.pcpmtPersonComPK.pid = d.qstdtPaymentDetailPK.personId AND pc.pcpmtPersonComPK.ccd = d.qstdtPaymentDetailPK.companyCode"
			+ " INNER JOIN  CmnmtDep dep ON dep.cmnmtDepPK.companyCode = pc.pcpmtPersonComPK.ccd AND dep.startDate = h.standardDate "
			+ " WHERE c.paycompFormDetailPK.formCode = :formCode AND  h.qstdtPaymentHeaderPK.personId IN :personId"
			+ " AND h.qstdtPaymentHeaderPK.payBonusAtr = 0" + " AND h.qstdtPaymentHeaderPK.processingYM =:processingYM"
			+ " AND   c.paycompFormDetailPK.companyCode =:companyCode ORDER BY dep.cmnmtDepPK.departmentCode";
	private final String SELECT_PAYCOMP_COMFIRM = "SELECT c FROM QlsdtPaycompConfirm as c WHERE "
			+ "c.paycompConfirmPK.companyCode = :companyCode AND c.paycompConfirmPK.personId IN :personId "
			+ "AND c.paycompConfirmPK.processYMEarly = :processYMEarly AND c.paycompConfirmPK.processYMLater = :processYMLater";

	private final SalaryBonusDetail toDomain(Object[] salaryBonusDetail) {
		val salary = new SalaryBonusDetail();
		salary.setItemAbName(salaryBonusDetail[0].toString());
		salary.setProcessingYM(salaryBonusDetail[1].hashCode());
		salary.setEmployeeName(salaryBonusDetail[2].toString());
		salary.setSpecificationCode(salaryBonusDetail[3].toString());
		salary.setMakeMethodFlag(salaryBonusDetail[4].toString());
		salary.setPersonId(salaryBonusDetail[5].toString());
		salary.setCategoryATR(salaryBonusDetail[6].toString());
		salary.setItemCode(salaryBonusDetail[7].toString());
		salary.setValue(new BigDecimal(salaryBonusDetail[8].toString()));
		salary.setNameB(salaryBonusDetail[9].toString());
		salary.setScd(salaryBonusDetail[10].toString());
		salary.setDepartmentCode(salaryBonusDetail[12].toString());
		salary.setDepartmentName(salaryBonusDetail[11].toString());
		salary.setHierarchyId(salaryBonusDetail[13].toString());
		return salary;
	}
	
	private final String toDomainString(Object[] obj) {
		
		return obj[0].toString();
		
	}

	private static PaycompConfirm convertToDomainPaycompConfirm(QlsdtPaycompConfirm entity) {

		return PaycompConfirm.createFromJavaType(entity.paycompConfirmPK.companyCode, entity.paycompConfirmPK.personId,
				entity.paycompConfirmPK.itemCD, entity.paycompConfirmPK.processYMEarly,
				entity.paycompConfirmPK.processYMLater, entity.paycompConfirmPK.categoryAtr, entity.diffAmount,
				entity.diffReason, entity.confirmSTS);
	}

	@Override
	public List<SalaryBonusDetail> getContentReportEarlyMonth(String companyCode, List<String> PIDs, int yearMonth1,
			String formCode) {

		return this.queryProxy().query(SELECT_1, Object[].class).setParameter("formCode", formCode)
				.setParameter("personId", PIDs).setParameter("processingYM", yearMonth1)
				.setParameter("companyCode", companyCode).getList(c -> toDomain(c));
	}

	@Override
	public List<SalaryBonusDetail> getContentReportLaterMonth(String companyCode, List<String> PIDs, int yearMonth1,
			String formCode) {
		return this.queryProxy().query(SELECT_1, Object[].class).setParameter("formCode", formCode)
				.setParameter("personId", PIDs).setParameter("processingYM", yearMonth1)
				.setParameter("companyCode", companyCode).getList(c -> toDomain(c));
	}

	@Override
	public List<PaycompConfirm> getPayCompComfirm(String companyCode, List<String> personIDs, int processYMEarly,
			int processYMLater) {
		return this.queryProxy().query(SELECT_PAYCOMP_COMFIRM, QlsdtPaycompConfirm.class)
				.setParameter("companyCode", companyCode).setParameter("personId", personIDs)
				.setParameter("processYMEarly", processYMEarly).setParameter("processYMLater", processYMLater)
				.getList(s -> convertToDomainPaycompConfirm(s));
	}

	@Override
	public List<?> getDepartmentCodeList(String companyCode, List<String> PIDs, int yearMonth1, String formCode) {
		return this.queryProxy().query(SELECT_DEPCODE, Object[].class).setParameter("formCode", formCode)
				.setParameter("personId", PIDs).setParameter("processingYM", yearMonth1)
				.setParameter("companyCode", companyCode).getList();
	}

}
