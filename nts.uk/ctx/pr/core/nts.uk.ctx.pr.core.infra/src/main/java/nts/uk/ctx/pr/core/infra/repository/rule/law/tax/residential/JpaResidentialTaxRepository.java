package nts.uk.ctx.pr.core.infra.repository.rule.law.tax.residential;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTax;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTaxRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.residential.QtxmtResidentialTax;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.residential.QtxmtResidentialTaxPk;

/**
 * 
 * @author lanlt
 *
 */
@Stateless
public class JpaResidentialTaxRepository extends JpaRepository implements ResidentialTaxRepository {
	private final String SEL_1 = "SELECT c FROM QtxmtResidentialTax c WHERE c.qtxmtResidentialTaxPk.companyCd =:companyCd";
	private final String SEL_2 = "SELECT c FROM QtxmtResidentialTax c WHERE c.qtxmtResidentialTaxPk.companyCd =:companyCd && c.qtxmtResidentialTaxPk.resiTaxCode =:resiTaxCode && c.resiTaxReportCode :=resiTaxReportCode";
	// key CCD RESI_TAX_CD

	private static ResidentialTax toDomain(QtxmtResidentialTax entity) {
		val domain = ResidentialTax.createFromJavaType(entity.qtxmtResidentialTaxPk.companyCd, entity.companyAccountNo,
				entity.companySpecifiedNo, entity.cordinatePostOffice, entity.cordinatePostalCode, entity.memo,
				entity.prefectureCode, entity.registeredName, entity.resiTaxAutonomy,
				entity.qtxmtResidentialTaxPk.resiTaxCode, entity.resiTaxReportCode);
		return domain;
	}

	private static QtxmtResidentialTax toEntity(ResidentialTax domain) {
		QtxmtResidentialTax entity = new QtxmtResidentialTax();
		entity.qtxmtResidentialTaxPk = new QtxmtResidentialTaxPk(domain.getCompanyCode().v(),
				domain.getResiTaxCode().v());
		entity.companyAccountNo = domain.getCompanyAccountNo().v();
		entity.companySpecifiedNo = domain.getCompanySpecifiedNo().v();
		entity.cordinatePostalCode = domain.getCordinatePostalCode().v();
		entity.cordinatePostOffice = domain.getCordinatePostOffice().v();
		entity.memo = domain.getMemo().v();
		entity.prefectureCode = domain.getPrefectureCode().v();
		entity.registeredName = domain.getRegisteredName().v();
		entity.resiTaxReportCode = domain.getResiTaxReportCode().v();
		entity.resiTaxAutonomy = domain.getResiTaxAutonomy().v();
		return entity;
	}

	// by companyCode
	@Override
	public List<ResidentialTax> getAllResidentialTax(String companyCode) {
		List<ResidentialTax> list= this.queryProxy().query(SEL_1, QtxmtResidentialTax.class).setParameter("companyCd", companyCode)
				.getList(c -> toDomain(c));
		return list;
	}

	@Override
	public void add(ResidentialTax residentalTax) {
		this.commandProxy().insert(toEntity(residentalTax));

	}

	@Override
	public void update(ResidentialTax residentalTax) {
		try {
			this.commandProxy().update(toEntity(residentalTax));
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void delele(String companyCode, String resiTaxCode) {
		val objectKey = new QtxmtResidentialTaxPk();
		objectKey.companyCd = companyCode;
		objectKey.resiTaxCode = resiTaxCode;
		this.commandProxy().remove(QtxmtResidentialTax.class, objectKey);

	}

	// @Override
	// public void update(String companyCode, String resiTaxCode) {
	// // TODO Auto-generated method stub
	//
	// }

	@Override
	public Optional<ResidentialTax> getAllResidialTax(String companyCode, String resiTaxCode,
			String resiTaxReportCode) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SEL_2, QtxmtResidentialTax.class).setParameter("companyCd", companyCode)
				.setParameter("resiTaxCode", resiTaxCode).setParameter("resiTaxReportCode", resiTaxReportCode)
				.getSingle(c -> toDomain(c));

	}
	// @Override
	// public void delete(String resiTaxCode, String resiTaxReportCode) {
	// // TODO Auto-generated method stub
	//
	// }

}
