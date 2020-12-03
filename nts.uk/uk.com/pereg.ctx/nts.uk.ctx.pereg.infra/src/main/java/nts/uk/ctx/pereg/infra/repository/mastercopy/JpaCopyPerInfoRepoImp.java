/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pereg.infra.repository.mastercopy;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.mastercopy.CopyPerInfoRepository;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.CopyContext;

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
		
		val copyContext = new CopyContext(
				jdbcProxy(),
				commandProxy(),
				queryProxy(),
				CopyMethodOnConflict.valueOf(copyMethod));
		
		val categoryIds = CopyCategory.execute(copyContext);
		val idContainer = CopyItem.execute(copyContext, categoryIds);
		CopyLayout.execute(copyContext, idContainer);
		CopyGroupItem.execute(copyContext, idContainer);
	}
}
