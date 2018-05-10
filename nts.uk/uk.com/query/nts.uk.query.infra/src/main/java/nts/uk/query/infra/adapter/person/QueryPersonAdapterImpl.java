/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.adapter.person;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.infra.entity.person.info.BpsmtPerson;
import nts.uk.query.model.person.QueryPersonAdapter;

/**
 * The Class QueryPersonAdapterImpl.
 */
public class QueryPersonAdapterImpl extends JpaRepository implements QueryPersonAdapter {

	/** The find by name. */
	private final String FIND_BY_NAME = "SELECT c FROM BpsmtPerson c"
			+ " WHERE c.personName LIKE :name"
			+ " OR c.personName LIKE :name"
			+ " OR c.personNameKana LIKE :name"
			+ " OR c.businessName LIKE :name"
			+ " OR c.businessNameKana LIKE :name"
			+ " OR c.businessEnglishName LIKE :name"
			+ " OR c.businessOtherName LIKE :name"
			+ " OR c.personRomanji LIKE :name"
			+ " OR c.personRomanjiKana LIKE :name"
			+ " OR c.todokedeFullName LIKE :name"
			+ " OR c.todokedeFullNameKana LIKE :name"
			+ " OR c.oldName LIKE :name"
			+ " OR c.oldNameKana LIKE :name"
			+ " OR c.perNameMultilLang LIKE :name"
			+ " OR c.perNameMultilLangKana LIKE :name";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.model.person.QueryPersonAdapter#findByName(java.lang.String)
	 */
	@Override
	public List<String> findPersonIdsByName(String name) {
		return this.queryProxy().query(FIND_BY_NAME, BpsmtPerson.class).setParameter("name", name)
				.getList(e -> e.bpsmtPersonPk.pId);
	}

}
