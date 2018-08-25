package nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Stateless
public class SpecialHolidayFrameFinder {
	@Inject
	private SpecialHolidayFrameRepository specialHolidayFrameRepository;
	
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	@Inject
	private SpecialHolidayEventRepository specialHolidayEventRepository;
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<SpecialHolidayFrameDto> findAll() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		return this.specialHolidayFrameRepository.findAll(companyId).stream().map(c -> SpecialHolidayFrameDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by company id. and 使用区分　=　true
	 *
	 * @return the list
	 */
	public List<SpecialHolidayFrameDto> findSpecialHolidayFrame() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		return this.specialHolidayFrameRepository.findSpecialHolidayFrame(companyId).stream().map(c -> SpecialHolidayFrameDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by frame no.
	 *
	 * @return the data
	 */
	public SpecialHolidayFrameDto findHolidayFrameByCode(int frameNo) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		Optional<SpecialHolidayFrame> data = this.specialHolidayFrameRepository.findHolidayFrameByCode(companyId, frameNo);
		
		if(data.isPresent()){
			return SpecialHolidayFrameDto.fromDomain(data.get());
		}
		
		return null;
	}
	
	public List<SpecialHolidayFrameDto> findByCompanyIdWithoutEventAndSpecialHoliday() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		// ドメインモデル「事象に対する特別休暇」.対象項目を取得する
		List<SpecialHolidayEvent> lstSpecialHolidayEvent = specialHolidayEventRepository.findByCompany(companyId);
		
		// 「事象別に対する特別休暇」.対象項目
		List<Integer> lstSpecialHolidayEventNo = lstSpecialHolidayEvent.stream().map(x -> x.getSpecialHolidayEventNo()).collect(Collectors.toList());
		
		// ドメインモデル「特別休暇」.対象項目を取得する
		List<SpecialHoliday> lstSpecialHoliday = specialHolidayRepository.findByCompanyId(companyId);
		
		// 取得した「特別休暇」.対象項目
		List<Integer> lstSpecialHolidayFrameNo = new ArrayList<>();
		lstSpecialHoliday.stream().forEach(x -> {
			Optional<SpecialHoliday> optSpecialHoliday = specialHolidayRepository.findBySingleCD(companyId, x.getSpecialHolidayCode().v());
			optSpecialHoliday.ifPresent(specialHoliday -> {
				if (specialHoliday.getTargetItem().getFrameNo() != null)
					lstSpecialHolidayFrameNo.addAll(specialHoliday.getTargetItem().getFrameNo());
			});
		});
		
		// ドメインモデル「特別休暇枠」．枠名称を取得し、画面に設定する
		/** 
		 *  取得条件：
			会社ID = ログイン会社ID
			NO ≠ 取得した「事象別に対する特別休暇」.対象項目 + 取得した「特別休暇」.対象項目.対象の特別休暇枠
		 */
		return this.specialHolidayFrameRepository.findAll(companyId).stream()
				.filter(x -> !lstSpecialHolidayEventNo.contains(x.getSpecialHdFrameNo()) && !lstSpecialHolidayFrameNo.contains(x.getSpecialHdFrameNo()))
				.map(c -> SpecialHolidayFrameDto.fromDomain(c))
				.collect(Collectors.toList());
	}
}
