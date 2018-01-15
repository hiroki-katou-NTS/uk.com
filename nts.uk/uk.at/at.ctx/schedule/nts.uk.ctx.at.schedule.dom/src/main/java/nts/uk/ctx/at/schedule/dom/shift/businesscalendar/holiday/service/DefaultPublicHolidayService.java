package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class DefaultPublicHolidayService implements PublicHolidayService {

	
	@Inject
	private PublicHolidayRepository publicHolidayRepository;
	

	@Override
	public boolean isExist(String companyID, GeneralDate date) {
		Optional<PublicHoliday> publicHoliday = publicHolidayRepository.getHolidaysByDate(companyID, date);
		return publicHoliday.isPresent();

	}

	@Override
	public void deleteHolidayInfo(String companyID, GeneralDate date) {
		if (isExist(companyID , date)){
			publicHolidayRepository.remove(companyID, date);
		}
	}

	@Override
	public void createHolidayInfo(PublicHoliday publicHoliday) {
		
		if (isExist(publicHoliday.getCompanyId(), publicHoliday.getDate())){
			throw new BusinessException("Msg_132");
		}
		if (StringUtil.isNullOrEmpty(publicHoliday.getDate().toString(), true) || StringUtil.isNullOrEmpty(publicHoliday.getHolidayName().toString(), true)) 
		{ throw new BusinessException("");}
		publicHolidayRepository.add(publicHoliday);		
		
	}

	@Override
	public void updateHolidayInfo(PublicHoliday publicHoliday) {
		publicHolidayRepository.update(publicHoliday);
	
	}

}
