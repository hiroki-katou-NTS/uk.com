package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.dom.annualworkschedule.SettingOutputItemOfAnnualWorkSchedule;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.AnnualWorkSheetPrintingForm;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.TotalAverageDisplay;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemDto;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.FormCanUsedForTime;
import nts.uk.ctx.at.function.dom.employmentfunction.commonform.SettingClassification;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SettingOutputItemOfAnnualWorkScheduleFinder.
 *
 * @author LienPTK
 */
@Stateless
public class SettingOutputItemOfAnnualWorkScheduleFinder {

	/** The set output item of annual work sch repository. */
	@Inject
	private SetOutputItemOfAnnualWorkSchRepository setOutputItemOfAnnualWorkSchRepository;

	/** The attendance item name service. */
	@Inject
	private AttendanceItemNameService attendanceItemNameService;

	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.KWR008_年間勤務表(Lịch làm việc trong năm).B：出力項目設定.アルゴリズム.勤怠チェック用勤怠項目の取得.勤怠チェック用勤怠項目の取得
	 * 
	 * @return monthly attendance items
	 */
	public List<AttendanceItemDto> getMonthlyAtdItems() {
		String companyId = AppContexts.user().companyId();

		// 画面で使用可能な月次勤怠項目を取得する
		List<Integer> attendanceIdList = this.attendanceItemNameService.getMonthlyAttendanceItemsAvaiable(
				companyId
				, FormCanUsedForTime.ANNUAL_WORK_SCHEDULE
				, TypeOfItem.Monthly
		);

		// 月次勤怠項目に対応する名称、属性を取得する
		return this.attendanceItemNameService.getMonthlyAttendanceItemNameAndAttr(companyId, attendanceIdList);
	}

	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.KWR008_年間勤務表(Lịch làm việc trong năm).B：出力項目設定.アルゴリズム.３６協定チェック用勤怠項目の取得.３６協定チェック用勤怠項目の取得
	 *
	 * @return the 36 attendance item
	 */
	public List<AttendanceItemDto> getAttendanceItemsForAgreementCheck() {
		String companyId = AppContexts.user().companyId();

		// 画面で使用可能な月次勤怠項目を取得する
		List<Integer> attendanceIdList = this.attendanceItemNameService.getMonthlyAttendanceItemsAvaiable(
				companyId
				, FormCanUsedForTime.ANNUAL_ROSTER_36_AGREEMENT
				, TypeOfItem.Monthly
		);

		// 月次勤怠項目に対応する名称、属性を取得する
		return this.attendanceItemNameService.getMonthlyAttendanceItemNameAndAttr(companyId, attendanceIdList);
	}
	
	/**
	 * Gets the atd items by type.
	 *
	 * @param displayFormat 起動区分（36チェックリスト／年間勤務表）
	 * @return the atd items by type
	 */
	public List<AttendanceItemDto> getAtdItemsByType(int displayFormat) {
		// 36協定チェックリストの場合
		if (displayFormat == AnnualWorkSheetPrintingForm.AGREEMENT_CHECK_36.value) {
			return this.getAttendanceItemsForAgreementCheck();
		}
		// 勤怠チェックリストの場合
		return this.getMonthlyAtdItems();
	}

	/**
	 * ドメインモデル「年間勤務表の出力項目設定」をすべて取得する.
	 *
	 * @param settingType the setting type
	 * @return the all annual setting
	 */
	public List<SetOutputItemOfAnnualWorkSchDto> getAllAnnualSetting(int settingType, int printForm) {
		// 自由設定の場合のみ
		Optional<String> employeeId = settingType == SettingClassification.FREE_SETTING.value
										? Optional.of(AppContexts.user().employeeId())
										: Optional.empty();
		// Get all setting
		return this.setOutputItemOfAnnualWorkSchRepository
				.findAllSeting(AppContexts.user().companyId(), employeeId, printForm, settingType).stream()
				.map(t -> SetOutputItemOfAnnualWorkSchDto.toDto(t))
				.collect(Collectors.toList());
	}
	
	/**
	 * 「年間勤務表の出力項目設定」を取得する
	 *
	 * @param layoutId 項目設定ID
	 * @return the sets the output item of annual work sch dto
	 */
	public SetOutputItemOfAnnualWorkSchDto findByLayoutId(String layoutId) {
		return this.setOutputItemOfAnnualWorkSchRepository.findByLayoutId(layoutId)
				.map(t -> SetOutputItemOfAnnualWorkSchDto.toDto(t))
				.orElse(new SetOutputItemOfAnnualWorkSchDto());
	}
	
	public Boolean checkAverage(String layoutId) {
		Optional<SettingOutputItemOfAnnualWorkSchedule> outputItem = this.setOutputItemOfAnnualWorkSchRepository.findByLayoutId(layoutId);
		if (outputItem.isPresent() && outputItem.get().getTotalAverageDisplay().isPresent()) {
			return outputItem.get().isMultiMonthDisplay() && outputItem.get().getTotalAverageDisplay().get() == TotalAverageDisplay.AVERAGE;
		}
		return false;
	}

	/**
	 * アルゴリズム「レイアウト情報を複製する」を実行する
	 * 
	 * @param AnnualWorkScheduleDuplicateDto dto
	 */
	public void executeCopy(AnnualWorkScheduleDuplicateDto dto) {
		String companyId = AppContexts.user().companyId();
		
		Optional<String> employeeId = dto.getSelectedType() == 0
				? employeeId = Optional.empty()
				: Optional.of(AppContexts.user().employeeId());
		
		// ドメインモデル「年間勤務表の出力項目設定」で コード重複チェックを行う
		Optional<SettingOutputItemOfAnnualWorkSchedule> outputItem = this.setOutputItemOfAnnualWorkSchRepository.findByLayoutId(dto.getLayoutId());
		
		// 重複する場合
		if(outputItem.get().getCd().v().equals(dto.getDuplicateCode())) {
			throw new BusinessException("Msg_1776");
		}
		// 重複しない場合
		Optional<SettingOutputItemOfAnnualWorkSchedule> duplicateItem = this.setOutputItemOfAnnualWorkSchRepository
				.findByCode(dto.getDuplicateCode(), employeeId, companyId, dto.getSelectedType(), dto.getPrintFormat());
		//複製元の存在チェックを行う
		if(duplicateItem.isPresent()) {
			// 複製元出力項目が存在しない場合
			throw new BusinessException("Msg_1946");
		} else {
			String duplicateId = UUID.randomUUID().toString();
			outputItem.get().setCd(new OutItemsWoScCode(dto.getDuplicateCode()));
			outputItem.get().setName(new OutItemsWoScName(dto.getDuplicateName()));
			outputItem.get().setLayoutId(duplicateId);
			// 複製元出力項目が存在する場合
			this.setOutputItemOfAnnualWorkSchRepository.add(outputItem.get());
		}	
	}
	

}
