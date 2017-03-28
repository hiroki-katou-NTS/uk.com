/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.reference;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRefItem;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository;

/**
 * The Class JpaWtReferenceRepository.
 */
@Stateless
public class JpaWtReferenceRepository extends JpaRepository implements WtReferenceRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository#
	 * getCodeRefItem(nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRef)
	 */
	@Override
	public List<WtCodeRefItem> getCodeRefItem(WtCodeRef codeRef) {
		return this.queryProxy().query(codeRef.getWagePersonQuery(), WtCodeRefItem.class)
				// .setParameter("companyCode", companyCode)
				// .setParameter("categoryAtr", categoryAtr)
				// .setParameter("itemCodeList", itemCode)
				.getList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository#
	 * getMasterRefItem(nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRef)
	 */
	@Override
	public List<WtCodeRefItem> getMasterRefItem(WtMasterRef masterRef) {
		return this.queryProxy().query(masterRef.getWagePersonQuery(), WtCodeRefItem.class)
				// .setParameter("companyCode", companyCode)
				// .setParameter("categoryAtr", categoryAtr)
				// .setParameter("itemCodeList", itemCode)
				.getList();
	}

}
