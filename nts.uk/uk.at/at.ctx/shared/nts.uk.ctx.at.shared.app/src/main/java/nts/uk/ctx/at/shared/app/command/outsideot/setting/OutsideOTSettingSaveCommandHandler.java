/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.setting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeSettingSaveCommandHandler.
 */
@Stateless
public class OutsideOTSettingSaveCommandHandler extends CommandHandler<OutsideOTSettingSaveCommand>{
	
	/** The outside OT setting repository. */
	@Inject
	private OutsideOTSettingRepository outsideOTSettingRepository;
	
	/** The super HD 60 H con med repository. */
	@Inject
	private SuperHD60HConMedRepository superHD60HConMedRepository;
	
	@Inject
	private Com60HourVacationRepository com60HourVacationRepository;
	
	/** The Constant BEGIN_RATE_ZERO. */
	public static final int BEGIN_RATE_ZERO = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OutsideOTSettingSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		OutsideOTSettingSaveCommand command = context.getCommand();
		
		// to domain
		OutsideOTSetting domainSetting = command.toDomainSetting(companyId);
		SuperHD60HConMed domainSupper = command.toDomainSuper(companyId);
		
		Optional<Com60HourVacation> optionalCom60Hour = this.com60HourVacationRepository.findById(companyId);
		if(optionalCom60Hour.isPresent()){
			if(optionalCom60Hour.get().getSetting().getIsManage().equals(ManageDistinct.YES)){
				// check domain setting and supper
				this.checkDomainSettingAndSuper(domainSetting, domainSupper);
			}
		}
		
		// save domain
		this.outsideOTSettingRepository.save(domainSetting);
		this.superHD60HConMedRepository.save(domainSupper);
	}
	
	/**
	 * Check domain setting and super.
	 *
	 * @param domainSetting the domain setting
	 * @param domainSupper the domain supper
	 */
	private void checkDomainSettingAndSuper(OutsideOTSetting domainSetting,
			SuperHD60HConMed domainSupper) {
		domainSetting.getBreakdownItems().stream().forEach(bd -> {
			
			bd.getPremiumExtra60HRates().stream().forEach(per -> {
				
				val ot = domainSetting.getOvertimes().stream().filter(c -> c.getOvertimeNo() == per.getOvertimeNo()).findFirst();
				if (ot.get().isSuperHoliday60HOccurs() && per.getPremiumRate().v() <= BEGIN_RATE_ZERO) {
					throw new BusinessException("Msg_491");
				}
			});
		});
	}

}
