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
import nts.uk.shr.com.company.CompanyId;
import nts.uk.shr.com.constants.DefaultSettingKeys;
import nts.uk.shr.com.context.AppContexts;

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
		
		val copyContract = CopyContext.ContractCode.same(AppContexts.user().contractCode());
		
		val copyCompanyId = new CopyContext.CompanyId(
				AppContexts.user().zeroCompanyIdInContract(),
				companyId);
		
		val copyContext = createCopyContext(copyContract, copyCompanyId, copyMethod);
		
		copyCompanyTables(copyContext);
	}

	@Override
	public void copyOnTenantCreated(String newTenantCode) {

		val copyContract = new CopyContext.ContractCode(
				DefaultSettingKeys.CONTRACT_CODE,
				newTenantCode);
		
		val copyCompanyId = new CopyContext.CompanyId(
				DefaultSettingKeys.COMPANY_ID,
				CompanyId.zeroCompanyInTenant(newTenantCode));

		// copyMethodは、新規テナント作成時なのでどれでもいいはず
		val copyContext = createCopyContext(copyContract, copyCompanyId, 1);
		
		CopyBases.execute(copyContext);
		copyCompanyTables(copyContext);
	}

	private CopyContext createCopyContext(
			CopyContext.ContractCode copyContract,
			CopyContext.CompanyId copyCompanyId,
			int copyMethod) {
		
		return new CopyContext(
				jdbcProxy(),
				commandProxy(),
				queryProxy(),
				copyContract,
				copyCompanyId,
				CopyMethodOnConflict.valueOf(copyMethod));
	}

	private static void copyCompanyTables(CopyContext copyContext) {
		
		val categoryIds = CopyCategory.execute(copyContext);
		val idContainer = CopyItem.execute(copyContext, categoryIds);
		CopyLayout.execute(copyContext, idContainer);
		CopyGroupItem.execute(copyContext, idContainer);
	}
}
