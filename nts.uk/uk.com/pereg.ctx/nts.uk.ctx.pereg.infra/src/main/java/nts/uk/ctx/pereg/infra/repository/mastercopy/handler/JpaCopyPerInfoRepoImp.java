/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.mastercopy.CopyPerInfoRepository;

/**
 * The Class JpaCopyPerInfoRepoImp.
 */
@Stateless
public class JpaCopyPerInfoRepoImp extends JpaRepository implements CopyPerInfoRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pereg.dom.mastercopy.CopyPerInfoRepository#personalInfoDefCopy
	 * (java.lang.String, int)
	 */
	@Override
	public void personalInfoDefCopy(String companyId, int copyMethod) {
		Map<String, String> transIdMap = new HashMap<>();
		transIdMap.putAll(new PersonalInfoDefCopyHandler(this, copyMethod, companyId).doCopy());
		transIdMap.putAll(new PerInfoSelectionItemCopyHandler(this, copyMethod, companyId).doCopy());
		transIdMap.putAll(new PpemtNewLayoutDataCopyHandler(copyMethod, companyId, getEntityManager()).doCopy());
		transIdMap.putAll(new PpemtPInfoItemGroupDataCopyHandler(copyMethod, companyId, getEntityManager(), transIdMap).doCopy());
	}
}
