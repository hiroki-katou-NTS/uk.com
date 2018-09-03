package nts.uk.ctx.at.shared.app.find.worktype.absenceframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Stateless
public class AbsenceFrameFinder {
	@Inject
	private AbsenceFrameRepository absenceFrameRepository;
	
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<AbsenceFrameDto> findAll() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		return this.absenceFrameRepository.findAll(companyId).stream().map(c -> AbsenceFrameDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by frame no.
	 *
	 * @return the data
	 */
	public AbsenceFrameDto findAbsenceFrameByCode(int frameNo) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		Optional<AbsenceFrame> data = this.absenceFrameRepository.findAbsenceFrameByCode(companyId, frameNo);
		
		if(data.isPresent()){
			return AbsenceFrameDto.fromDomain(data.get());
		}
		
		return null;
	}
	
	/**
	 * Find by company id without event and special holiday.
	 *
	 * @return the list
	 */
	public List<AbsenceFrameDto> findByCompanyIdWithoutEventAndSpecialHoliday() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		// ドメインモデル「特別休暇」.対象項目を取得する
		List<SpecialHoliday> lstSpecialHoliday = specialHolidayRepository.findByCompanyId(companyId);
		
		// 「特別休暇」.対象項目.欠勤枠
		List<Integer> lstSpecialHolidayFrameNo = new ArrayList<>();
		lstSpecialHoliday.stream().forEach(x -> {
			Optional<SpecialHoliday> optSpecialHoliday = specialHolidayRepository.findBySingleCD(companyId, x.getSpecialHolidayCode().v());
			optSpecialHoliday.ifPresent(specialHoliday -> {
				if (specialHoliday.getTargetItem().getAbsenceFrameNo() != null)
					lstSpecialHolidayFrameNo.addAll(specialHoliday.getTargetItem().getAbsenceFrameNo());
			});
		});
		
		// ドメインモデル「特別休暇枠」．枠名称を取得し、画面に設定する
		/** 
		 *  取得条件：
			会社ID = ログイン会社ID
			NO ≠ 取得した「事象別に対する特別休暇」.対象項目 + 取得した「特別休暇」.対象項目.対象の特別休暇枠
		 */
		return this.absenceFrameRepository.findAll(companyId).stream()
				.filter(x -> !lstSpecialHolidayFrameNo.contains(x.getAbsenceFrameNo()))
				.map(c -> AbsenceFrameDto.fromDomain(c))
				.collect(Collectors.toList());
	}
}
