package nts.uk.ctx.bs.person.infra.repository.person.family;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMember;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMemberRepository;
import nts.uk.ctx.bs.person.infra.entity.person.family.BpsmtFamily;
import nts.uk.ctx.bs.person.infra.entity.person.family.BpsmtFamilyPk;
import nts.uk.ctx.bs.person.infra.entity.person.info.widowhistory.BpsmtWidowHis;

@Stateless
public class JpaFamilyMember extends JpaRepository implements FamilyMemberRepository {

	public static final String GET_ALL_BY_PID = "SELECT c FROM BpsmtFamily c WHERE c.pid = :pid";
	
	private static final String SELECT_FAMILY_BY_ID = "SELECT c FROM BpsmtFamily c WHERE c.ppsmtFamilyPk.familyId = :familyId";

	private List<FamilyMember> toListFamily(List<BpsmtFamily> listEntity) {
		List<FamilyMember> lstFamily = new ArrayList<>();
		if (!listEntity.isEmpty()) {
			listEntity.stream().forEach(c -> {
				FamilyMember family = toDomainFamily(c);

				lstFamily.add(family);
			});
		}
		return lstFamily;
	}

	private FamilyMember toDomainFamily(BpsmtFamily entity) {
		val domain = FamilyMember.createFromJavaType(
				entity.birthday,
				entity.deathDate, 
				entity.entryDate, 
				entity.expDate, 
				entity.ppsmtFamilyPk.familyId, 
				entity.name,
				entity.NameKana,
				entity.nameMultiLang,
				entity.nameMultiLangKana,
				entity.nameRomaji,
				entity.nameRomajiKana, 
				entity.nationality,
				entity.occupationName, 
				entity.pid, 
				entity.relationShip, 
				entity.SupportCareType,
				entity.todukedeName,
				entity.TogSepDivType, 
				entity.workStudentType);
		return domain;
	}
	
	@Override
	public FamilyMember getFamilyById(String familyId) {
		Optional<FamilyMember> family = this.queryProxy().query(SELECT_FAMILY_BY_ID, BpsmtFamily.class)
				.setParameter("familyId", familyId).getSingle(x -> toDomainFamily(x));
		return family.isPresent()?family.get():null;
	}

	@Override
	public List<FamilyMember> getListByPid(String pid) {
		List<BpsmtFamily> listEntity = this.queryProxy().query(GET_ALL_BY_PID, BpsmtFamily.class)
				.setParameter("pid", pid).getList();
		return toListFamily(listEntity);
	}
	/**
	 * toEntity
	 * @param domain
	 * @return
	 */
	private BpsmtFamily toEntity(FamilyMember domain){
		BpsmtFamilyPk key = new BpsmtFamilyPk(domain.getFamilyMemberId());
		return new BpsmtFamily(key, domain.getWorkStudentType().value, domain.getTogSepDivisionType().value,
				domain.getTokodekeName().v(), domain.getSupportCareType().value, domain.getRelationship().v(), 
				domain.getPersonId(), domain.getOccupationName().v(), domain.getNationalityId().v(), domain.getNameRomajiFull().v(), 
				domain.getNameRomajiFullKana().v(), domain.getNameMultiLangFull().v(), domain.getNameMultiLangFullKana().v(),
				domain.getFullName().v(), domain.getFullNameKana().v(), domain.getExpelledDate(), domain.getEntryDate(), 
				domain.getDeadDay(), domain.getBirthday());
	}
	/**
	 * updateEntity
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(FamilyMember domain, BpsmtFamily entity){
		entity.workStudentType = domain.getWorkStudentType().value;
		entity.TogSepDivType = domain.getTogSepDivisionType().value;
		entity.todukedeName = domain.getTokodekeName().v();
		entity.SupportCareType = domain.getSupportCareType().value;
		entity.relationShip = domain.getRelationship().v();
		entity.pid = domain.getPersonId();
		entity.occupationName = domain.getOccupationName().v();
		entity.nationality = domain.getNationalityId().v();
		entity.nameRomaji = domain.getNameRomajiFull().v();
		entity.nameRomajiKana =	domain.getNameRomajiFullKana().v();
		entity.nameMultiLang = domain.getNameMultiLangFull().v();
	 	entity.nameMultiLangKana = domain.getNameMultiLangFullKana().v();
		entity.name	= domain.getFullName().v();
		entity.NameKana = domain.getFullNameKana().v();
		entity.expDate = domain.getExpelledDate();
		entity.entryDate = domain.getEntryDate(); 
		entity.deathDate = domain.getDeadDay();
		entity.birthday = domain.getBirthday();
	}
	/**
	 * Add family ドメインモデル「家族」を新規登録する
	 * @param family
	 */
	@Override
	public void addFamily(FamilyMember family) {
		this.commandProxy().insert(toEntity(family));
	}
	/**
	 * Update family 取得した「家族」を更新する
	 * @param family
	 */
	@Override
	public void updateFamily(FamilyMember family) {
		// Get exist entity
		BpsmtFamilyPk pk = new BpsmtFamilyPk(family.getFamilyMemberId());
		Optional<BpsmtFamily> existItem = this.queryProxy().find(pk, BpsmtFamily.class);
		if(!existItem.isPresent()){
			throw new RuntimeException("invalid Family");
		}
		// Update entity
		updateEntity(family,existItem.get());
		// Update family
		this.commandProxy().update(existItem.get());
	
		
	}

}
