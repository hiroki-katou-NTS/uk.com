package nts.uk.ctx.bs.employee.infra.repository.familyrelatedinformation.socialinsurance;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsurance;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsuranceRepository;
import nts.uk.ctx.bs.employee.infra.entity.familyrelatedinformation.socialinsurance.BsymtFamilySocialInsurance;

public class JpaFamilySocialInsurance extends JpaRepository implements FamilySocialInsuranceRepository{

	private static final String SELECT_FAMILY_SOCIAL_INS_BY_ID= "SELECT f FROM BsymtFamilySocialInsurance"
			 + " WHERE BsymtFamilySocialInsurance.bsymtFamilySocialInsurancePK.socialInsId = :socialInsId";
	
	private FamilySocialInsurance toDomainFamilySocialInsurance(BsymtFamilySocialInsurance entity){
		val domain = FamilySocialInsurance.createFromJavaType(entity.getFamilyMemberId(), entity.getSId(), 
				entity.getBsymtFamilySocialInsurancePK().getSocialInsId(), entity.getStrD(), entity.getEndD(), 
				entity.getNursingCare() == 1, entity.getHealthInsDep() == 1, entity.getNationalPenNo3() == 1, 
				entity.getBasicPerNumber());
		return domain;
	}
	@Override
	public Optional<FamilySocialInsurance> getFamilySocialInsById(String familySocialInsById) {
		return this.queryProxy().query(SELECT_FAMILY_SOCIAL_INS_BY_ID, BsymtFamilySocialInsurance.class)
				.setParameter("socialInsId", familySocialInsById).getSingle(x -> toDomainFamilySocialInsurance(x));
	}

}
