/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.dto.OperationStartSetDailyPerformDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OperationStartSetDailyPerformFinder.
 */
@Stateless
public class OperationStartSetDailyPerformFinder {
	
	/** The operation start set daily perform repository. */
	@Inject
	private OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepository;
	
	private static final String yyyyMMDD = "yyyy/MM/dd";
	
	/**
	 * Find by cid.
	 *
	 * @return the operation start set daily perform dto
	 */
	public OperationStartSetDailyPerformDto findByCid() {
		String companyId = AppContexts.user().companyId();
		OperationStartSetDailyPerformDto dto = new OperationStartSetDailyPerformDto();
		operationStartSetDailyPerformRepository.findByCid(new CompanyId(companyId))
					.ifPresent(domain -> {
						if (domain.getOperateStartDateDailyPerform().isPresent()) {
							dto.setOperateStartDateDailyPerform(domain.getOperateStartDateDailyPerform().get().toString(yyyyMMDD));
						} else {
							dto.setOperateStartDateDailyPerform(StringUtils.EMPTY);
						}
					});
		return dto;
	}
}

