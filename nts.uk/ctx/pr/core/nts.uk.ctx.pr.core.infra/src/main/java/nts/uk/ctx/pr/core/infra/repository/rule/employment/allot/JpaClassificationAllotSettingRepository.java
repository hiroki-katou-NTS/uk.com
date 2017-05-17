package nts.uk.ctx.pr.core.infra.repository.rule.employment.allot;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSetting;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingRespository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotCl;

@Stateless
public class JpaClassificationAllotSettingRepository extends JpaRepository
		implements ClassificationAllotSettingRespository {

	private final String SEL_1 = "SELECT c FROM QstmtStmtAllotCl c WHERE c.qstmtStmtAllotClPK.companyCode = :companyCode "
			+ " AND ( c.paymentDetailCode != '00'" + " OR c.bonusDetailCode != '00')";;


	@Override
	public List<ClassificationAllotSetting> findAll(String companyCode) {
		return this.queryProxy().query(SEL_1, QstmtStmtAllotCl.class).setParameter("companyCode", companyCode)
				.getList(c -> toDomain(c));
	}


	private ClassificationAllotSetting toDomain(QstmtStmtAllotCl entity) {
		val domain = ClassificationAllotSetting.createFromJavaType(entity.qstmtStmtAllotClPK.companyCode,
				entity.qstmtStmtAllotClPK.historyId, entity.qstmtStmtAllotClPK.classificationCode, entity.bonusDetailCode,
				entity.paymentDetailCode);
		return domain;
	}

}
