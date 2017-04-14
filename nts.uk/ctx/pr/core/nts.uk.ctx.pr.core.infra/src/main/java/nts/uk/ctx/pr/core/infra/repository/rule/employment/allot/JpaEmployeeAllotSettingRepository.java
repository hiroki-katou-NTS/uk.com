package nts.uk.ctx.pr.core.infra.repository.rule.employment.allot;

import java.util.List;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllSetting;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSetting;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotEm;

@Stateless
public class JpaEmployeeAllotSettingRepository extends JpaRepository implements EmployeeAllotSettingRepository {
	private final String SEL_1 = "SELECT c FROM QstmtStmtAllotEm c"
			+ " WHERE c.qstmtStmtAllotEmPK.companyCode = :companyCode"
			+ " AND c.qstmtStmtAllotEmPK.employeeCd = :employeeCd" + " AND c.qstmtStmtAllotEmPK.histId = :histId"
			+ " AND ( c.paymentDetailCode != '00'" + " OR c.bonusDetailCode != '00')";

	private final String SEL_2 = "SELECT c FROM QstmtStmtAllotEm c "
			+ " WHERE c.qstmtStmtAllotEmPK.companyCode = :companyCode" + " AND c.qstmtStmtAllotEmPK.histId = :histId";

	private final String ALL_EMPLOYEE_SETTING = "SELECT e.cmnmtEmpPk.companyCode, e.cmnmtEmpPk.employmentCode, e.employmentName, q.stmtName"
			+ " FROM CmnmtEmp e LEFT JOIN QstmtStmtAllotEm a "
//			+ " ON e.cmnmtEmpPk.companyCode = a.qstmtStmtAllotEmPK.companyCode AND e.cmnmtEmpPk.employmentCode = a.qstmtStmtAllotEmPK.employeeCd"
			+ " LEFT JOIN QstmtStmtLayoutHead q"
//			+ " ON a.QstmtStmtAllotEmPK.companyCode = q.qstmtStmtLayoutHeadPK.companyCd AND"
//			+ " a.paymentDetailCode = q.qstmtStmtLayoutHeadPK.stmtCd AND a.bonusDetailCode = q.qstmtStmtLayoutHeadPK.stmtCd"
			+ " WHERE a.QstmtStmtAllotEmPK.companyCode = :companyCode AND a.QstmtStmtAllotEmPK.histId = :histId";
	
	// private final String ALL_EMP_SETIING = "SELECT l FROM (SELECT ALL FROM
	// CmnmtEmp e LEFT JOIN QstmtStmtAllotEm a ON ON e.cmnmtEmpPk.companyCode =
	// a.qstmtStmtAllotEmPK.companyCode AND e.cmnmtEmpPk.employmentCode =
	// a.qstmtStmtAllotEmPK.employeeCd) l";

	@Override
	public Optional<EmployeeAllotSetting> find(String companyCode, String historyID, String employeeCd) {
		Optional<QstmtStmtAllotEm> empAllot = this.queryProxy().query(SEL_1, QstmtStmtAllotEm.class)
				.setParameter("companyCode", companyCode).setParameter("employeeCd", employeeCd)
				.setParameter("histId", historyID).getSingle();

		if (!empAllot.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(toDomain(empAllot.get()));
	}

	private EmployeeAllotSetting toDomain(QstmtStmtAllotEm empAllot) {
		val domain = EmployeeAllotSetting.createFromJavaType(empAllot.QstmtStmtAllotEmPK.companyCode,
				empAllot.QstmtStmtAllotEmPK.histId, empAllot.QstmtStmtAllotEmPK.employeeCd, empAllot.paymentDetailCode,
				empAllot.bonusDetailCode);
		return domain;
	}

	@Override
	public List<EmployeeAllotSetting> findAll(String companyCode, String historyID) {
		return this.queryProxy().query(SEL_2, QstmtStmtAllotEm.class).setParameter("companyCode", companyCode)
				.setParameter("histId", historyID).getList(c -> toDomain(c));
	}

	@Override
	public List<EmployeeAllSetting> findAllEm(String companyCode, String historyID) {
		return this.queryProxy().query(ALL_EMPLOYEE_SETTING, Object[].class).setParameter("companyCode", companyCode)
				.setParameter("histId", historyID)
				.getList(s -> EmployeeAllSetting.createFromJavaType(s[0].toString(), s[1].toString(), s[2].toString(),
						s[3].toString(), s[4].toString(), s[5].toString(), s[6].toString(), s[7].toString()));
	}

}
