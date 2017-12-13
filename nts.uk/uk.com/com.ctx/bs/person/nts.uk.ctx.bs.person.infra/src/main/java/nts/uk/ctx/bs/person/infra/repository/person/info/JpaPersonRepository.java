/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.infra.repository.person.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.infra.entity.person.info.BpsmtPerson;
import nts.uk.ctx.bs.person.infra.entity.person.info.BpsmtPersonPk;

/**
 * The Class JpaPersonRepository.
 */
@Stateless
public class JpaPersonRepository extends JpaRepository implements PersonRepository {
	public final String SELECT_NO_WHERE = "SELECT c FROM BpsmtPerson c";

	public final String SELECT_BY_PERSON_IDS = SELECT_NO_WHERE + " WHERE c.bpsmtPersonPk.pId IN :pids";

	public final String GET_LAST_CARD_NO = "SELECT c.cardNumberLetter FROM BpsstUserSetting c "

			+ " WHERE c.companyId = :companyId AND c.cardNumberLetter LIKE CONCAT(:cardNo, '%')"
			+ " ORDER BY  c.cardNumberLetter DESC";

	private static Person toDomain(BpsmtPerson entity) {
		if(entity.gender == 0) {
			entity.gender = 1;
		}
		
		Person domain = Person.createFromJavaType(entity.bpsmtPersonPk.pId, entity.birthday, entity.bloodType,
				entity.gender, entity.businessName, entity.personName, entity.personNameKana);
		return domain;
	}

	private BpsmtPerson toEntity(Person domain) {
		BpsmtPerson entity = new BpsmtPerson();
		entity.bpsmtPersonPk = new BpsmtPersonPk(domain.getPersonId());
		entity.birthday = domain.getBirthDate();
		entity.bloodType = domain.getBloodType() == null ? 0 : domain.getBloodType().value;
		entity.gender = domain.getGender() == null? 0: domain.getGender().value;
		
		entity.personName = domain.getPersonNameGroup().getPersonName() == null ? ""
				: domain.getPersonNameGroup().getPersonName().getFullName().v();
		
		entity.personNameKana = domain.getPersonNameGroup().getPersonName() == null ? ""
				: domain.getPersonNameGroup().getPersonName().getFullNameKana().v();
		
		entity.businessEnglishName = domain.getPersonNameGroup().getBusinessEnglishName() == null ? ""
				: domain.getPersonNameGroup().getBusinessEnglishName().v();
		entity.businessOtherName = domain.getPersonNameGroup().getBusinessOtherName() == null ? ""
				: domain.getPersonNameGroup().getBusinessOtherName().v();
		
		entity.businessName = domain.getPersonNameGroup().getBusinessName() == null ? ""
				: domain.getPersonNameGroup().getBusinessName().v();
		
		entity.businessNameKana = domain.getPersonNameGroup().getBusinessNameKana() == null ? ""
				: domain.getPersonNameGroup().getBusinessNameKana().v();
		
		entity.oldName = domain.getPersonNameGroup().getOldName() == null ? ""
				: domain.getPersonNameGroup().getOldName().getFullName().v();
		
		entity.oldNameKana = domain.getPersonNameGroup().getOldName() == null ? ""
				: domain.getPersonNameGroup().getOldName().getFullNameKana().v();
		
		entity.personRomanji = domain.getPersonNameGroup().getPersonRomanji() == null ? ""
				: domain.getPersonNameGroup().getPersonRomanji().getFullName().v();
		
		entity.personRomanjiKana = domain.getPersonNameGroup().getPersonRomanji() == null ? ""
				: domain.getPersonNameGroup().getPersonRomanji().getFullNameKana().v();
		
		
		entity.todokedeFullName = domain.getPersonNameGroup().getTodokedeFullName() == null ? ""
				: domain.getPersonNameGroup().getTodokedeFullName().getFullName().v();
		entity.todokedeFullNameKana = domain.getPersonNameGroup().getTodokedeFullName() == null ? ""
				: domain.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v();
		
		entity.perNameMultilLang = domain.getPersonNameGroup().getPersonalNameMultilingual() == null ? ""
				: domain.getPersonNameGroup().getPersonalNameMultilingual().getFullName().v();
		
		entity.perNameMultilLangKana = domain.getPersonNameGroup().getPersonalNameMultilingual() == null ? ""
				: domain.getPersonNameGroup().getPersonalNameMultilingual().getFullNameKana().v();
		return entity;
	}

	/**
	 * update entity
	 * 
	 * @param domain
	 * @param entity
	 * @return
	 */
	private void updateEntity(Person domain, BpsmtPerson entity) {
		if (domain.getBirthDate() != null){
			entity.birthday = domain.getBirthDate();
		}
		if (domain.getBloodType() != null){
			entity.bloodType = domain.getBloodType().value;
		}
		if (domain.getGender()!=null){
			entity.gender = domain.getGender().value;
		}
		if (domain.getPersonNameGroup().getPersonName() != null && domain.getPersonNameGroup().getPersonName().getFullName()!= null){
			entity.personName = domain.getPersonNameGroup().getPersonName().getFullName().v();
		}
		if (domain.getPersonNameGroup().getPersonName() != null && domain.getPersonNameGroup().getPersonName().getFullNameKana()!= null){
			entity.personNameKana = domain.getPersonNameGroup().getPersonName().getFullNameKana().v();
		}
		if (domain.getPersonNameGroup().getBusinessEnglishName() != null){
			entity.businessEnglishName = domain.getPersonNameGroup().getBusinessEnglishName().v();
		}
		if (domain.getPersonNameGroup().getBusinessOtherName() != null){
			entity.businessOtherName = domain.getPersonNameGroup().getBusinessOtherName().v();
		}
		if (domain.getPersonNameGroup().getBusinessName() != null){
			entity.businessName = domain.getPersonNameGroup().getBusinessName().v();
		}
		if (domain.getPersonNameGroup().getBusinessNameKana() != null){
			entity.businessNameKana = domain.getPersonNameGroup().getBusinessNameKana().v();
		}
		if (domain.getPersonNameGroup().getOldName() != null && domain.getPersonNameGroup().getOldName().getFullName() != null){
			entity.oldName = domain.getPersonNameGroup().getOldName().getFullName().v();
		}
		if (domain.getPersonNameGroup().getOldName() != null && domain.getPersonNameGroup().getOldName().getFullNameKana() != null){
			entity.oldNameKana = domain.getPersonNameGroup().getOldName().getFullNameKana().v();
		}
		if (domain.getPersonNameGroup().getPersonRomanji() != null && domain.getPersonNameGroup().getPersonRomanji().getFullName() != null){
			entity.personRomanji = domain.getPersonNameGroup().getPersonRomanji().getFullName().v();
		}
		if (domain.getPersonNameGroup().getPersonRomanji() != null && domain.getPersonNameGroup().getPersonRomanji().getFullNameKana() != null){
			entity.personRomanjiKana = domain.getPersonNameGroup().getPersonRomanji().getFullNameKana().v();
		}
		if (domain.getPersonNameGroup().getTodokedeFullName() != null && domain.getPersonNameGroup().getTodokedeFullName().getFullName() != null){
			entity.todokedeFullName = domain.getPersonNameGroup().getTodokedeFullName().getFullName().v();
		}
		if (domain.getPersonNameGroup().getTodokedeFullName() != null && domain.getPersonNameGroup().getTodokedeFullName().getFullNameKana() != null){
			entity.todokedeFullNameKana = domain.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v();
		}
		if (domain.getPersonNameGroup().getPersonalNameMultilingual() != null && domain.getPersonNameGroup().getPersonalNameMultilingual().getFullName() != null){
			entity.perNameMultilLang = domain.getPersonNameGroup().getPersonalNameMultilingual() == null ? ""
					: domain.getPersonNameGroup().getPersonalNameMultilingual().getFullName().v();
		}
		if (domain.getPersonNameGroup().getPersonalNameMultilingual() != null && domain.getPersonNameGroup().getPersonalNameMultilingual().getFullNameKana() != null){
			entity.perNameMultilLangKana = domain.getPersonNameGroup().getPersonalNameMultilingual().getFullNameKana().v();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.person.PersonRepository#getPersonByPersonId(java.
	 * util.List)
	 */
	@Override
	public List<Person> getPersonByPersonIds(List<String> personIds) {

		// check exist input
		if (CollectionUtil.isEmpty(personIds)) {
			return new ArrayList<>();
		}

		List<Person> lstPerson = this.queryProxy().query(SELECT_BY_PERSON_IDS, BpsmtPerson.class)
				.setParameter("pids", personIds).getList(c -> toDomain(c));

		return lstPerson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.person.PersonRepository#getByPersonId(java.lang.String)
	 */
	@Override
	public Optional<Person> getByPersonId(String personId) {
		Optional<BpsmtPerson> person = this.queryProxy().find(new BpsmtPersonPk(personId), BpsmtPerson.class);
		if (person.isPresent()) {
			return Optional.of(toDomain(person.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public String getLastCardNo(String companyId, String startCardNoLetter) {
		if (startCardNoLetter == null)
			startCardNoLetter = "";
		List<Object[]> lst = this.queryProxy().query(GET_LAST_CARD_NO).setParameter("companyId", companyId)
				.setParameter("cardNo", startCardNoLetter).getList();

		String returnStr = "";
		if (lst.size() > 0) {
			Object obj = lst.get(0);
			returnStr = obj.toString();
		}

		return returnStr;
	}

	/**
	 * Update person 取得した「個人」を更新する
	 * 
	 * @param person
	 */
	@Override
	public void update(Person person) {
		// Get entity
		Optional<BpsmtPerson> existItem = this.queryProxy().find(new BpsmtPersonPk(person.getPersonId()),
				BpsmtPerson.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("invalid Person");
		}
		// Update entity
		updateEntity(person, existItem.get());
		// Update person table
		this.commandProxy().update(existItem.get());
	}

	//sonnlb code start
	@Override
	public void addNewPerson(Person domain) {
		this.commandProxy().insert(toEntity(domain));
		
	}
	//sonnlb code end
}
