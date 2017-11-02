package repository.person.family;

import javax.ejb.Stateless;

import entity.person.family.BpsmtFamily;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;

@Stateless
public class JpaFamily extends JpaRepository implements FamilyRepository{

//	private Family toDomainFamily(BpsmtFamily entity){
//		return Family.createFromJavaType(entity.birthday, entity.deathDate, entity.entryDate, entity.expDate,
//				entity.ppsmtFamilyPk.familyId, entity.name, entity.NameKana, entity.nameMultiLang, entity.nameMultiLangKana, 
//				entity.nameRomaji, entity.nameRomajiKana, entity.nationality, entity.occupationName, 
//				entity.PId, entity.relationShip, entity.SupportCareType, entity.notify(), 
//				entity.TogSepDivType, entity.workStudentType);
//	}
	
	
	@Override
	public Family getFamilyById(String familyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
