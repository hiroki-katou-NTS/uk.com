package nts.uk.ctx.bs.employee.infra.repository.familyrelatedinformation.familycare;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCare;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCareRepository;
import nts.uk.ctx.bs.employee.infra.entity.familyrelatedinformation.care.BsymtFamilyCare;

@Stateless
public class JpaFamilyCare extends JpaRepository implements FamilyCareRepository{

	private static final String SELECT_FAMILY_CARE_BY_ID = "SELECT f FROM BsymtFamilyCare f"
			+ " WHERE f.bsymtFamilyCarePK.familyCareId = :familyCareId";
	
	private FamilyCare toDomailFamilyCare(BsymtFamilyCare entity){
		val domain = FamilyCare.createFromJavaType(entity.getBsymtFamilyCarePK().getFamilyCareId(), 
				entity.getFamilyMemberId(), entity.getSId(), entity.getStrD(), 
				entity.getEndD(), entity.getSupportDistinction());
		return domain;
	}
	
	@Override
	public Optional<FamilyCare> getFamilyCareById(String familyCareId) {
		return this.queryProxy().query(SELECT_FAMILY_CARE_BY_ID, BsymtFamilyCare.class)
				.setParameter("familyCareId", familyCareId).getSingle(x -> toDomailFamilyCare(x));
	}

	
}
