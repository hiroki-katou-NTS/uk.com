package nts.uk.ctx.at.function.app.find.attendancerecord.duplicate;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.DataReturnDto;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordOuputItems;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordOuputItemsRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.shr.com.context.AppContexts;

/**
 * Screen F KWR002
 * The Class AttendanceRecordDuplicateFinder.
 */
@Stateless
public class AttendanceRecordDuplicateFinder {

	
	@Inject
	private AttendanceRecordOuputItemsRepository outputItemRepo;
	
	@Inject
	private AttendanceRecordStandardSettingRepository standardRepo;
	
	@Inject
	AttendanceRecordExportSettingRepository attendanceRecExpSetRepo;
	
	//	アルゴリズム「複製登録処理」を実行する
	public void copyRegistionProcess() {
		String roleId = AppContexts.user().roles().forAttendance();
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
//		if (dto.getSelectedType() == ItemSelectionType.STANDARD_SETTING.value) {
//			Optional<AttendanceRecordStandardSetting> standardSetting = this.standardRepo.getStandardByCompanyId(companyId);
//			if (standardSetting.isPresent()) {
//				throw new BusinessException("Msg_3");
//			}
//		} else {
//			Optional<AttendanceRecordOuputItems> freeSetting = this.outputItemRepo.getOutputItemsByCompnayAndEmployee(companyId, employeeId);
//			if (freeSetting.isPresent()) {
//				throw new BusinessException("Msg_3");
//			}
//		}
	}

	public DataReturnDto executeCopy(AttendanceRecordDuplicateDto dto) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		// INPUT．項目選択種類をチェック Check INPUT.Item selection type
		if (dto.getSelectedType() == ItemSelectionType.STANDARD_SETTING.value) {
			//	ドメインモデル「出勤簿の出力項目定型設定」を取得する Nhận domain model 「出勤簿の出力項目定型設定」	
			Optional<AttendanceRecordStandardSetting> standardSetting = this.standardRepo.findByCompanyIdAndCode(companyId, dto.getCode());
			if (standardSetting.isPresent()) {
				throw new BusinessException("Msg_3");
			}
		} else {
			// 	ドメインモデル「出勤簿の出力項目自由設定」を取得する  Nhận domain model 「...」
			Optional<AttendanceRecordOuputItems> freeSetting = this.outputItemRepo.findByCompanyEmployeeAndCode(companyId, employeeId, dto.getCode());
			if (freeSetting.isPresent()) {
				throw new BusinessException("Msg_3");
			}
		}
		
		if (dto.getSelectedType() == ItemSelectionType.STANDARD_SETTING.value) {
			Optional<AttendanceRecordStandardSetting> standardSetting = this.standardRepo.findByCompanyCodeLayoutId(companyId, dto.getCode(), dto.getLayoutId());
//			standardSetting.map(i -> {
//				AttendanceRecordStandardSetting domainCopy = new AttendanceRecordStandardSetting();
//				JpaAttendanceRecordOuputItemsSetMemsento
//			})
			
		} else {
			Optional<AttendanceRecordOuputItems> freeSetting = this.outputItemRepo.findByCompanyEmployeeCodeAndLayoutId(companyId, employeeId, dto.getCode(), dto.getLayoutId());
			
		}
		return null;
	}
	
	
}
