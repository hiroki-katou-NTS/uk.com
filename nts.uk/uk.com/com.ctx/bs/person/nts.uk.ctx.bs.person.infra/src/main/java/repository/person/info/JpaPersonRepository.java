/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package repository.person.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import entity.person.info.BpsmtPerson;
import entity.person.info.BpsmtPersonPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.infra.entity.person.CcgmtPerson;
import nts.uk.ctx.basic.infra.entity.person.CcgmtPerson_;
import nts.uk.ctx.basic.infra.repository.person.JpaPersonGetMemento;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

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
		Person domain = Person.createFromJavaType(entity.bpsmtPersonPk.pId, entity.personName);
		return domain;
	}

	private BpsmtPerson toEntity(Person domain) {
		BpsmtPerson entity = new BpsmtPerson();
		entity.bpsmtPersonPk = new BpsmtPersonPk(domain.getPersonId());
		entity.birthday = domain.getBirthDate();
		entity.bloodType = domain.getBloodType() == null ? 0: domain.getBloodType().value;
		entity.businessEnglishName = domain.getPersonNameGroup().getBusinessEnglishName() == null ? "" : domain.getPersonNameGroup().getBusinessEnglishName().v(); 
		entity.businessOtherName = domain.getPersonNameGroup().getBusinessOtherName() == null ? "" :domain.getPersonNameGroup().getBusinessOtherName().v();
		entity.businessName = domain.getPersonNameGroup().getBusinessName()  == null ? "" : domain.getPersonNameGroup().getBusinessName().v();
		entity.gender = domain.getGender() == null ? 0 :  domain.getGender().value ;
		entity.hobby = domain.getHobBy() == null ? "" :domain.getHobBy().v();
		entity.nationality = domain.getCountryId() == null ? "" : domain.getCountryId().v();
		entity.oldName = domain.getPersonNameGroup().getOldName()  == null ? "" : domain.getPersonNameGroup().getOldName().toString();
		entity.personMailAddress = domain.getMailAddress() == null ? "" : domain.getMailAddress().v();
		entity.personMobile = domain.getPersonMobile() == null ? "" :domain.getPersonMobile().v() ;
		entity.personName = domain.getPersonNameGroup().getPersonName() == null ? "" : domain.getPersonNameGroup().getPersonName().v();
		entity.personNameKana = domain.getPersonNameGroup().getPersonNameKana() == null ? "" :domain.getPersonNameGroup().getPersonNameKana().v(); 
		entity.personRomanji = domain.getPersonNameGroup().getPersonRomanji() == null ? "" : domain.getPersonNameGroup().getPersonRomanji().toString();
		entity.taste = domain.getTaste() == null ? "" :domain.getTaste().v();
		entity.todokedeFullName = domain.getPersonNameGroup().getTodokedeFullName() == null ? "" : domain.getPersonNameGroup().getTodokedeFullName().toString();
		entity.todokedeOldFullName = domain.getPersonNameGroup().getTodokedeOldFullName() == null ? "" : domain.getPersonNameGroup().getTodokedeOldFullName().toString();
		return entity;
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

	@Override
	public void updatePerson(Person person) {
		this.commandProxy().update(toEntity(person));
	}
}
