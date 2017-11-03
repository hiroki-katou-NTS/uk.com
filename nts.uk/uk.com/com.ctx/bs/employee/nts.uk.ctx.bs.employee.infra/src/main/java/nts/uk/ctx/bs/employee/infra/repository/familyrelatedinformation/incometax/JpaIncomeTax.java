package nts.uk.ctx.bs.employee.infra.repository.familyrelatedinformation.incometax;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTax;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTaxRepository;
import nts.uk.ctx.bs.employee.infra.entity.familyrelatedinformation.incometax.BsymtIncomeTax;
import nts.uk.ctx.bs.employee.infra.entity.familyrelatedinformation.incometax.BsymtIncomeTaxPK;

@Stateless
public class JpaIncomeTax extends JpaRepository implements IncomeTaxRepository{

	private static final String SELECT_INCOME_TAX_BY_ID = "SELECT i FROM BsymtIncomeTax i"
			+ " WHERE i.bsymtIncomeTaxPK.incomeTaxId = :incomeTaxId";
	
	private IncomeTax toDomainInComeTax(BsymtIncomeTax entity){
		val domain = IncomeTax.createFromJavaType(entity.getBsymtIncomeTaxPK().getIncomeTaxId(), entity.getFamilyMemberId(),
				entity.getSId(), entity.getStrD(),entity.getEndD(), entity.getSupporter() == 1, 
				entity.getDisabilityType(), entity.getDeductionTargetType());
		return domain;
	}
	@Override
	public Optional<IncomeTax> getIncomeTaxById(String incomeTaxId) {
		return this.queryProxy().query(SELECT_INCOME_TAX_BY_ID, BsymtIncomeTax.class)
				.setParameter("incomeTaxId", incomeTaxId).getSingle(x -> toDomainInComeTax(x));
	}
	private BsymtIncomeTax toEntity(IncomeTax domain){
		BsymtIncomeTaxPK key = new BsymtIncomeTaxPK(domain.getIncomeTaxID());
		return new BsymtIncomeTax(key, domain.getSid(), domain.getFamilyMemberId(), domain.getDisabilityType().value,
				domain.getDeductionTargetType().value, domain.isSupporter()?1:0, domain.getPeriod().start(), domain.getPeriod().end());
	}
	/**
	 * 取得した「家族所得税」を更新する
	 * @param domain
	 */
	@Override
	public void addIncomeTax(IncomeTax domain) {
		this.commandProxy().insert(toEntity(domain));
	}

}
