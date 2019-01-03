/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.adapter.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.infra.entity.employee.mngdata.BsymtEmployeeDataMngInfo;
import nts.uk.query.model.employee.mgndata.EmpDataMngInfoAdapter;

/**
 * The Class EmpDataMngInfoAdapterImpl.
 */
@Stateless
public class EmpDataMngInfoAdapterImpl extends JpaRepository implements EmpDataMngInfoAdapter {

	/** The Constant FIND_NOT_DELETED_BY_SCODE. */
	private static final String FIND_NOT_DELETED_BY_SCODE = "SELECT e FROM BsymtEmployeeDataMngInfo e"
			+ " WHERE e.companyId = :cId"
			+ " AND e.employeeCode LIKE CONCAT('%', :sCd, '%')"
			+ " AND e.delStatus = 0";

	/** The Constant FIND_NOT_DELETED_BY_PIDS. */
	private static final String FIND_NOT_DELETED_BY_PIDS = "SELECT e FROM BsymtEmployeeDataMngInfo e"
			+ " WHERE e.companyId = :cId"
			+ " AND e.bsymtEmployeeDataMngInfoPk.pId IN :pIds"
			+ " AND e.delStatus = 0";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.employee.mgndata.EmpDataMngInfoAdapter#
	 * getEmployeeNotDeleteInCompany(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> findNotDeletedBySCode(String cId, String sCd) {
		return queryProxy().query(FIND_NOT_DELETED_BY_SCODE, BsymtEmployeeDataMngInfo.class).setParameter("cId", cId)
				.setParameter("sCd", sCd).getList().stream().map(x -> x.bsymtEmployeeDataMngInfoPk.sId)
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.employee.mgndata.EmpDataMngInfoAdapter#
	 * findByListPersonId(java.lang.String, java.util.List)
	 */
	@Override
	public List<String> findByListPersonId(String comId, List<String> pIds) {
		if (CollectionUtil.isEmpty(pIds)) {
			return Collections.emptyList();
		}

		List<BsymtEmployeeDataMngInfo> resultList = new ArrayList<>();

		CollectionUtil.split(pIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			resultList.addAll(this.queryProxy().query(FIND_NOT_DELETED_BY_PIDS, BsymtEmployeeDataMngInfo.class)
					.setParameter("cId", comId).setParameter("pIds", subList).getList());
		});

		return resultList.stream().map(e -> e.bsymtEmployeeDataMngInfoPk.sId).collect(Collectors.toList());
	}

}
