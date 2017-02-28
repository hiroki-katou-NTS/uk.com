package nts.uk.ctx.pr.core.infra.repository.rule.law.tax.commutelimit;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit.CommuteNoTaxLimit;
import nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit.CommuteNoTaxLimitRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.commutelimit.QtxmtCommuNotaxLimit;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.commutelimit.QtxmtCommuNotaxLimitPK;

@Stateless
public class JpaCommuteNoTaxLimitRepository extends JpaRepository implements CommuteNoTaxLimitRepository {

	private final String SELECT_BY_COMPANYCODE = "SELECT c FROM QtxmtCommuNotaxLimit c WHERE c.qtxmtCommuNotaxLimitPK.companyCode = :ccd";

	@Override
	public List<CommuteNoTaxLimit> getCommuteNoTaxLimitByCompanyCode(String companyCode) {
		return this.queryProxy().query(SELECT_BY_COMPANYCODE, QtxmtCommuNotaxLimit.class)
				.setParameter("ccd", companyCode).getList(c -> convertToDomainObject(c));
	}

	@Override
	public void add(CommuteNoTaxLimit commuteNoTaxLimit) {
		this.commandProxy().insert(convertToInfaEnty(commuteNoTaxLimit));
	}

	@Override
	public void update(CommuteNoTaxLimit commuteNoTaxLimit) {
		this.commandProxy().update(convertToInfaEnty(commuteNoTaxLimit));
	}

	@Override
	public void delele(String companyCode, String commuNoTaxLimitCode) {
		QtxmtCommuNotaxLimitPK pk = new QtxmtCommuNotaxLimitPK(companyCode, commuNoTaxLimitCode);
		this.commandProxy().remove(QtxmtCommuNotaxLimit.class, pk);
	}

	private static CommuteNoTaxLimit convertToDomainObject(QtxmtCommuNotaxLimit entity) {
		return CommuteNoTaxLimit.createFromJavaType(entity.qtxmtCommuNotaxLimitPK.companyCode,
				entity.qtxmtCommuNotaxLimitPK.commuNotaxLimitCd, entity.commuNotaxLimitName,
				entity.commuNotaxLimitValue);
	}

	private static QtxmtCommuNotaxLimit convertToInfaEnty(CommuteNoTaxLimit entity) {
		val qtxmtCommuNotaxLimit = new QtxmtCommuNotaxLimit();
		qtxmtCommuNotaxLimit.qtxmtCommuNotaxLimitPK = new QtxmtCommuNotaxLimitPK();
		qtxmtCommuNotaxLimit.qtxmtCommuNotaxLimitPK.companyCode = entity.getCompanyCode();
		qtxmtCommuNotaxLimit.qtxmtCommuNotaxLimitPK.commuNotaxLimitCd = entity.getCommuNoTaxLimitCode().v();
		qtxmtCommuNotaxLimit.commuNotaxLimitName = entity.getCommuNoTaxLimitName().v();
		qtxmtCommuNotaxLimit.commuNotaxLimitValue = entity.getCommuNoTaxLimitValue().v();
		return qtxmtCommuNotaxLimit;
	}

}
