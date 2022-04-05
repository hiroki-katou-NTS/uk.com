package nts.uk.screen.at.app.knr002.query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.DeclareHolidayWorkFrameDto;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.DeclareOvertimeFrameDto;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.DeclareSetDto;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.FilingSettingsDto;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.HdwkFrameEachHdAtrDto;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.OvertimeWorkFrameDto;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.WorkdayoffFrameDto;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.HdwkFrameEachHdAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;

/**
 * Screen Query - 申告設定を取得する
 * 
 * @author NWS
 *
 */
@Stateless
public class GetFilingSettingsScreenQuery {

	@Inject
	private OvertimeWorkFrameRepository overtimeWorkFrameRepository;

	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;

	@Inject
	private DeclareSetRepository declareSetRepository;

	public FilingSettingsDto get(String companyId) {
		// Step 1: get 残業枠枠 with 会社ID、使用区分＝する
		List<OvertimeWorkFrame> optionalOvertimeWorkFrames = overtimeWorkFrameRepository
				.getOvertimeWorkFrameByFrameByCom(companyId, 1);
		List<OvertimeWorkFrameDto> optionalOvertimeWorkFrameDto = this
				.toOvertimeWorkFrameDto(optionalOvertimeWorkFrames).stream()
				.sorted(Comparator.comparingInt(e -> e.getOvertimeWorkFrNo().intValue())).collect(Collectors.toList());

		// Step 2: get 休出枠 with ログイン会社ID、使用区分＝する
		List<WorkdayoffFrame> optionalWorkdayoffFrames = workdayoffFrameRepository.findByUseAtr(companyId, 1);
		List<WorkdayoffFrameDto> optionalWorkdayoffFrameDto = this.toWorkdayoffFrameDto(optionalWorkdayoffFrames)
				.stream().sorted(Comparator.comparingInt(e -> e.getWorkdayoffFrNo().intValue()))
				.collect(Collectors.toList());

		// Step 3: get 申告設定 with ログイン会社ID
		Optional<DeclareSet> optionalDeclareSet = declareSetRepository.find(companyId);
		DeclareSetDto optionalDeclareSetDto = this.toDeclareSetDto(optionalDeclareSet.orElse(null));

		FilingSettingsDto filingSettingsDto = new FilingSettingsDto(optionalOvertimeWorkFrameDto,
				optionalWorkdayoffFrameDto, optionalDeclareSetDto);
		return filingSettingsDto;
	}

	/**
	 * Convert AggregateRoot to dto
	 * 
	 * @param overtimeWorkFrame
	 * @return OvertimeWorkFrameDto
	 */
	private List<OvertimeWorkFrameDto> toOvertimeWorkFrameDto(List<OvertimeWorkFrame> overtimeWorkFrames) {
		List<OvertimeWorkFrameDto> optionalOvertimeWorkFrameDto = new ArrayList<OvertimeWorkFrameDto>();
		if (overtimeWorkFrames != null && overtimeWorkFrames.size() > 0) {// We need this because orElse passes null
			overtimeWorkFrames.stream().forEach(overtimeWorkFrame -> {
				// convert OvertimeWorkFrame to dto
				OvertimeWorkFrameDto overtimeWorkFrameDto = new OvertimeWorkFrameDto();
				overtimeWorkFrameDto.setCompanyId(overtimeWorkFrame.getCompanyId());
				overtimeWorkFrameDto.setOvertimeWorkFrName(overtimeWorkFrame.getOvertimeWorkFrName().v());
				overtimeWorkFrameDto.setOvertimeWorkFrNo(overtimeWorkFrame.getOvertimeWorkFrNo().v());
				overtimeWorkFrameDto.setRole(overtimeWorkFrame.getRole().value);
				overtimeWorkFrameDto.setTransferAtr(overtimeWorkFrame.getTransferAtr().value);
				overtimeWorkFrameDto.setTransferFrName(overtimeWorkFrame.getTransferFrName().v());
				overtimeWorkFrameDto.setUseClassification(overtimeWorkFrame.getUseClassification().value);
				optionalOvertimeWorkFrameDto.add(overtimeWorkFrameDto);
			});
			return optionalOvertimeWorkFrameDto;
		} else {
			return null;
		}
	}

	/**
	 * Convert AggregateRoot to dto
	 * 
	 * @param overtimeWorkFrame
	 * @return WorkdayoffFrameDto
	 */
	private List<WorkdayoffFrameDto> toWorkdayoffFrameDto(List<WorkdayoffFrame> workdayoffFrames) {
		List<WorkdayoffFrameDto> workdayoffFrameDtos = new ArrayList<WorkdayoffFrameDto>();

		if (workdayoffFrames != null && workdayoffFrames.size() > 0) {// We need this because orElse passes null
			workdayoffFrames.stream().forEach(workdayoffFrame -> {
				// convert WorkdayoffFrame to dto
				WorkdayoffFrameDto workdayoffFrameDto = new WorkdayoffFrameDto();
				workdayoffFrameDto.setCompanyId(workdayoffFrame.getCompanyId());
				workdayoffFrameDto.setRole(workdayoffFrame.getRole().value);
				workdayoffFrameDto.setTransferFrName(workdayoffFrame.getTransferFrName().v());
				workdayoffFrameDto.setUseClassification(workdayoffFrame.getUseClassification().value);
				workdayoffFrameDto.setWorkdayoffFrName(workdayoffFrame.getWorkdayoffFrName().v());
				workdayoffFrameDto.setWorkdayoffFrNo(workdayoffFrame.getWorkdayoffFrNo().v());
				workdayoffFrameDtos.add(workdayoffFrameDto);
			});
			return workdayoffFrameDtos;
		} else {
			return null;
		}
	}

	/**
	 * Convert AggregateRoot to dto
	 * 
	 * @param overtimeWorkFrame
	 * @return DeclareSetDto
	 */
	private DeclareSetDto toDeclareSetDto(DeclareSet declareSet) {
		if (declareSet != null) {// We need this because orElse passes null
			// convert DeclareSet to dto
			DeclareSetDto declareSetDto = new DeclareSetDto();
			declareSetDto.setCompanyId(declareSet.getCompanyId());
			declareSetDto.setFrameSet(declareSet.getFrameSet().value);
			declareSetDto.setMidnightAutoCalc(declareSet.getMidnightAutoCalc().value);
			declareSetDto.setUsageAtr(declareSet.getUsageAtr().value);
			DeclareHolidayWorkFrameDto holidayWorkFrameDto = new DeclareHolidayWorkFrameDto();
			if (declareSet.getHolidayWorkFrame().getHolidayWork().isPresent()) {
				HdwkFrameEachHdAtr hdwkFrameEachHdAtr = declareSet.getHolidayWorkFrame().getHolidayWork().get();
				HdwkFrameEachHdAtrDto hdwkFrameEachHdAtrDto = new HdwkFrameEachHdAtrDto(
						hdwkFrameEachHdAtr.getStatutory().v(), hdwkFrameEachHdAtr.getNotStatutory().v(),
						hdwkFrameEachHdAtr.getNotStatHoliday().v());
				holidayWorkFrameDto.setHolidayWork(hdwkFrameEachHdAtrDto);
			} else {
				holidayWorkFrameDto.setHolidayWork(new HdwkFrameEachHdAtrDto());
			}
			if (declareSet.getHolidayWorkFrame().getHolidayWorkMn().isPresent()) {
				HdwkFrameEachHdAtr hdwkFrameEachHdAtr = declareSet.getHolidayWorkFrame().getHolidayWorkMn().get();
				HdwkFrameEachHdAtrDto hdwkFrameEachHdAtrDto = new HdwkFrameEachHdAtrDto(
						hdwkFrameEachHdAtr.getStatutory().v(), hdwkFrameEachHdAtr.getNotStatutory().v(),
						hdwkFrameEachHdAtr.getNotStatHoliday().v());
				holidayWorkFrameDto.setHolidayWorkMn(hdwkFrameEachHdAtrDto);
			} else {
				holidayWorkFrameDto.setHolidayWorkMn(new HdwkFrameEachHdAtrDto());
			}
			declareSetDto.setHolidayWorkFrame(holidayWorkFrameDto);
			DeclareOvertimeFrameDto declareOvertimeFrameDto = new DeclareOvertimeFrameDto(
					declareSet.getOvertimeFrame().getEarlyOvertime().isPresent()
							? declareSet.getOvertimeFrame().getEarlyOvertime().get().v()
							: null,
					declareSet.getOvertimeFrame().getEarlyOvertimeMn().isPresent()
							? declareSet.getOvertimeFrame().getEarlyOvertimeMn().get().v()
							: null,
					declareSet.getOvertimeFrame().getOvertime().isPresent()
							? declareSet.getOvertimeFrame().getOvertime().get().v()
							: null,
					declareSet.getOvertimeFrame().getOvertimeMn().isPresent()
							? declareSet.getOvertimeFrame().getOvertimeMn().get().v()
							: null);
			declareSetDto.setOvertimeFrame(declareOvertimeFrameDto);
			return declareSetDto;
		} else {
			return null;
		}
	}
}
