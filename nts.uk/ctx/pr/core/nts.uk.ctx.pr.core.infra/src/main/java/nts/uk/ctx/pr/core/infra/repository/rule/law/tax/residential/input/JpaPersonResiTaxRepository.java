package nts.uk.ctx.pr.core.infra.repository.rule.law.tax.residential.input;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.input.PersonResiTax;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.input.PersonResiTaxRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.residential.input.PprmtPersonResiTax;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.residential.input.PprmtPersonResiTaxPK;

@Stateless
public class JpaPersonResiTaxRepository extends JpaRepository implements PersonResiTaxRepository {
	private final String SEL_1 = "SELECT c FROM PprmtPersonResiTax c "
			+ "WHERE c.PprmtPersonResiTaxPK.companyCode = :CCD" + "AND c.PprmtPersonResiTaxPK.personId = :PID";

	private PprmtPersonResiTax toEntity(PersonResiTax domain) {
		val entity = new PprmtPersonResiTax();

		entity.pprmtPersonResiTaxPK = new PprmtPersonResiTaxPK();
		entity.pprmtPersonResiTaxPK.companyCode = domain.getCompanyCode().v();
		entity.pprmtPersonResiTaxPK.personID = domain.getPersonId().v();
		entity.residenceTaxAve = domain.getResidenceTaxAve().v();
		entity.residenceTaxBn = domain.getResidenceTaxBn().v();
		entity.residenceTaxLevyAtr = domain.getResidenceTaxLevyAtr().value;
		entity.residenceTaxLumpAtr = domain.getResidenceTaxLumpAtr().value;
		entity.residenceTaxLumpYm = domain.getResidenceTaxLumpYm().v();
		entity.residenceCode = domain.getResidenceCode().v();
		entity.yearKey = domain.getYearKey().v();
		entity.residenceTax01 = domain.getResidenceTax().get(0).getValue().v();
		entity.residenceTax02 = domain.getResidenceTax().get(1).getValue().v();
		entity.residenceTax03 = domain.getResidenceTax().get(2).getValue().v();
		entity.residenceTax04 = domain.getResidenceTax().get(3).getValue().v();
		entity.residenceTax05 = domain.getResidenceTax().get(4).getValue().v();
		entity.residenceTax06 = domain.getResidenceTax().get(5).getValue().v();
		entity.residenceTax07 = domain.getResidenceTax().get(6).getValue().v();
		entity.residenceTax08 = domain.getResidenceTax().get(7).getValue().v();
		entity.residenceTax09 = domain.getResidenceTax().get(8).getValue().v();
		entity.residenceTax10 = domain.getResidenceTax().get(9).getValue().v();
		entity.residenceTax11 = domain.getResidenceTax().get(10).getValue().v();
		entity.residenceTax12 = domain.getResidenceTax().get(11).getValue().v();
		return entity;
	}

	public static PersonResiTax toDomain(PprmtPersonResiTax entity) {
		PersonResiTax domain = PersonResiTax.createFromJavaType(entity.pprmtPersonResiTaxPK.companyCode,
				entity.pprmtPersonResiTaxPK.personID, entity.yearKey, entity.residenceCode, entity.residenceTaxBn,
				entity.residenceTaxAve, entity.residenceTaxLumpAtr, entity.residenceTaxLumpYm,
				entity.residenceTaxLevyAtr);
		domain.createResidenceTaxFromJavaType(1, entity.residenceTax01, 2, entity.residenceTax02, 3,
				entity.residenceTax03, 4, entity.residenceTax04, 5, entity.residenceTax05, 6, entity.residenceTax06, 7,
				entity.residenceTax07, 8, entity.residenceTax08, 9, entity.residenceTax09, 10, entity.residenceTax10,
				11, entity.residenceTax11, 12, entity.residenceTax12);
		return domain;
	}

	@Override
	public List<PersonResiTax> findAll(String companyCode, String personId) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SEL_1, PprmtPersonResiTax.class).setParameter("CCD", companyCode)
				.setParameter("PID", personId).getList(c -> toDomain(c));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
