package nts.uk.ctx.pr.core.infra.repository.rule.employment.allot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeader;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeaderRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.employee.EmployeeAllotSettingHeader;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotHCl;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotHClPK;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotHEm;

@Stateless
public class JpaClassificationAllotSettingHeaderRepository extends JpaRepository
		implements ClassificationAllotSettingHeaderRepository {

	private final String SEL_01 = " SELECT c FROM QstmtStmtAllotHCl c WHERE c.qstmtStmtAllotHClPK.companyCode = :companyCode ";

	@Override
	public void insert(ClassificationAllotSettingHeader domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void delete(ClassificationAllotSettingHeader domain) {
		this.commandProxy().remove(toEntity(domain));
	}
	
	

	@Override
	public List<ClassificationAllotSettingHeader> findAll(String companyCode) {
		return this.queryProxy().query(SEL_01, QstmtStmtAllotHCl.class).setParameter("companyCode", companyCode)
				.getList(c -> toDomain(c));
	}

	@Override
	public void update(ClassificationAllotSettingHeader domain) {
		this.commandProxy().update(toEntity(domain));
	}

	private ClassificationAllotSettingHeader toDomain(QstmtStmtAllotHCl entity) {
		return ClassificationAllotSettingHeader.createFromJavaType(entity.qstmtStmtAllotHClPK.companyCode,
				entity.qstmtStmtAllotHClPK.historyId, entity.startDateYM, entity.endDateYM);
	}
	private QstmtStmtAllotHCl toEntity(ClassificationAllotSettingHeader domain) {
		QstmtStmtAllotHClPK key = new QstmtStmtAllotHClPK(domain.getCompanyCode().v(), domain.getHistoryId());
		QstmtStmtAllotHCl entity = new QstmtStmtAllotHCl(key, domain.getStartDateYM(),domain.getEndDateYM());
		return entity;
	}


}
