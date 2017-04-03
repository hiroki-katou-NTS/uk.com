package nts.uk.ctx.pr.core.infra.repository.rule.employment.allot;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingHeader;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingHeaderRespository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotHEm;

@Stateless
public class JpaEmployeeAllotSettingHeaderRespository extends JpaRepository implements EmployeeAllotSettingHeaderRespository{
	
	private final String SEL_1 = "SELECT c FROM QstmtStmtAllotHEm c WHERE c.QstmtStmtAllotHEmPK.companyCode = :companyCode ";

	@Override
	public Optional<EmployeeAllotSettingHeader> find(String companyCode) {
		// TODO Auto-generated method stub
		List<QstmtStmtAllotHEm> hemsettings = this.queryProxy().query(SEL_1, QstmtStmtAllotHEm.class)
		.setParameter("companyCode", companyCode).getList();
			if (hemsettings.isEmpty()) {
				return Optional.empty();
			}
		return Optional.of(toDomain(hemsettings.get(0)));
	}

	private static EmployeeAllotSettingHeader toDomain(QstmtStmtAllotHEm entity) {
		//return null;
		val domain = EmployeeAllotSettingHeader.createFromJavaType(entity.QstmtStmtAllotHEmPK.companyCode,
					entity.startDate,
					entity.endDate,
					entity.QstmtStmtAllotHEmPK.histId);
		return domain;
	}

	@Override
	public List<EmployeeAllotSettingHeader> findAll(String companyCode) {
		return this.queryProxy().query(SEL_1, QstmtStmtAllotHEm.class).setParameter("companyCode", companyCode)
				.getList(c -> toDomain(c));
	}

}
