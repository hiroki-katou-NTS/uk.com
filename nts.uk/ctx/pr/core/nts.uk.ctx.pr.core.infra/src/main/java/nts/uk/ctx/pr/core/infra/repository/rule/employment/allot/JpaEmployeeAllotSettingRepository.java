package nts.uk.ctx.pr.core.infra.repository.rule.employment.allot;

import java.util.List;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSetting;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotEm;
@Stateless
public class JpaEmployeeAllotSettingRepository extends JpaRepository implements EmployeeAllotSettingRepository{
	private final String SEL_1 = "SELECT c FROM QstmtStmtAllotEm c"
			+ " WHERE c.qstmtStmtAllotEmPK.companyCode = :companyCode"
			+ " AND c.qstmtStmtAllotEmPK.employeeCd = :employeeCd"
			+ " AND c.qstmtStmtAllotEmPK.histId = :histId"
			+ " AND ( c.paymentDetailCode != '00'"
			+ " OR c.bonusDetailCode != '00')";
	
	private final String SEL_2 = "SELECT c FROM QstmtStmtAllotEm c "
			+ " WHERE c.qstmtStmtAllotEmPK.companyCode = :companyCode"
			+ " AND c.qstmtStmtAllotEmPK.histId = :histId";

	@Override
	public Optional<EmployeeAllotSetting> find(String companyCode, String historyID, String employeeCd) {
		Optional<QstmtStmtAllotEm> empAllot = this.queryProxy().query(SEL_1, QstmtStmtAllotEm.class)
				.setParameter("companyCode", companyCode)
				.setParameter("employeeCd", employeeCd)
				.setParameter("histId", historyID).getSingle();

		if (!empAllot.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(toDomain(empAllot.get()));
	}

	private EmployeeAllotSetting toDomain(QstmtStmtAllotEm empAllot) {
		val domain = EmployeeAllotSetting.createFromJavaType(empAllot.QstmtStmtAllotEmPK.companyCode, 
				empAllot.QstmtStmtAllotEmPK.histId, 
				empAllot.QstmtStmtAllotEmPK.employeeCd, 
				empAllot.paymentDetailCode, 
				empAllot.bonusDetailCode);
		return domain;
	}

	@Override
	public List<EmployeeAllotSetting> findAll(String companyCode, String historyID) {
		return this.queryProxy().query(SEL_2, QstmtStmtAllotEm.class)
					.setParameter("companyCode", companyCode)
					.setParameter("histId", historyID)
				.getList(c -> toDomain(c));
	}


}
