/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.adapter.person;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.infra.entity.person.info.BpsmtPerson;
import nts.uk.query.model.person.QueryPersonAdapter;

/**
 * The Class QueryPersonAdapterImpl.
 */
@Stateless
public class QueryPersonAdapterImpl extends JpaRepository implements QueryPersonAdapter {

	/** The find by name. */
	private static final String FIND_BY_NAME = "SELECT c FROM BpsmtPerson c"
			+ " WHERE c.personName LIKE CONCAT('%', :name, '%')"
			+ " OR c.personName LIKE CONCAT('%', :name, '%')"
			+ " OR c.personNameKana LIKE CONCAT('%', :name, '%')"
			+ " OR c.businessName LIKE CONCAT('%', :name, '%')"
			+ " OR c.businessNameKana LIKE CONCAT('%', :name, '%')"
			+ " OR c.businessEnglishName LIKE CONCAT('%', :name, '%')"
			+ " OR c.businessOtherName LIKE CONCAT('%', :name, '%')"
			+ " OR c.personRomanji LIKE CONCAT('%', :name, '%')"
			+ " OR c.personRomanjiKana LIKE CONCAT('%', :name, '%')"
			+ " OR c.todokedeFullName LIKE CONCAT('%', :name, '%')"
			+ " OR c.todokedeFullNameKana LIKE CONCAT('%', :name, '%')"
			+ " OR c.oldName LIKE CONCAT('%', :name, '%')"
			+ " OR c.oldNameKana LIKE CONCAT('%', :name, '%')"
			+ " OR c.perNameMultilLang LIKE CONCAT('%', :name, '%')"
			+ " OR c.perNameMultilLangKana LIKE CONCAT('%', :name, '%')";

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
