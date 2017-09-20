/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.workplace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfo;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfoPK;

/**
 * The Class JpaWorkplaceConfigInfoRepository.
 */
@Stateless
public class JpaWorkplaceConfigInfoRepository extends JpaRepository implements WorkplaceConfigInfoRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WorkplaceConfigInfo> find(String companyId, String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository#add(nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo)
	 */
	@Override
	public void add(WorkplaceConfigInfo workplaceConfigInfo) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository#addList(nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo)
	 */
	@Override
	public void addList(WorkplaceConfigInfo wkpConfigInfo) {
		this.commandProxy().insertAll(this.convertToListEntity(wkpConfigInfo));
	}
	
	/**
	 * Convert to list entity.
	 *
	 * @param wkpConfigInfo the wkp config info
	 * @return the list
	 */
	private List<BsymtWkpConfigInfo> convertToListEntity(WorkplaceConfigInfo wkpConfigInfo) {
		return wkpConfigInfo.getWkpHierarchy().stream().map(item -> {
			BsymtWkpConfigInfo entity = new BsymtWkpConfigInfo();
			BsymtWkpConfigInfoPK pk = new BsymtWkpConfigInfoPK();
			pk.setCid(wkpConfigInfo.getCompanyId());
			pk.setHistoryId(wkpConfigInfo.getHistoryId().v());
			entity.setBsymtWkpConfigInfoPK(pk);
			entity.setWkpid(item.getWorkplaceId().v());
			entity.setHierarchyCd(item.getHierarchyCode().v());
			return entity;
		}).collect(Collectors.toList());
	}

}
