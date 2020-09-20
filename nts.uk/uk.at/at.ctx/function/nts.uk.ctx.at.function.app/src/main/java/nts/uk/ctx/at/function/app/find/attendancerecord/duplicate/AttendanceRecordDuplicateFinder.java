package nts.uk.ctx.at.function.app.find.attendancerecord.duplicate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyworkschedule.DataReturnDto;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSettingRepository;
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
	private AttendanceRecordFreeSettingRepository outputItemRepo;
	
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
//			Optional<AttendanceRecordFreeSetting> freeSetting = this.outputItemRepo.getOutputItemsByCompnayAndEmployee(companyId, employeeId);
//			if (freeSetting.isPresent()) {
//				throw new BusinessException("Msg_3");
//			}
//		}
	}

	public DataReturnDto executeCopy(AttendanceRecordDuplicateDto dto) {
		String companyId = AppContexts.user().companyId();
		Optional<String> employeeId = dto.getSelectedType() == ItemSelectionType.FREE_SETTING.value
						? Optional.of(AppContexts.user().employeeId())
						: Optional.empty();

		// INPUT．項目選択種類をチェック Check INPUT.Item selection type
		Optional<AttendanceRecordExportSetting> setting = this.attendanceRecExpSetRepo.findByCode(ItemSelectionType.valueOf(dto.getSelectedType())
				, companyId
				, employeeId
				, dto.getCode());
		
		
		return null;
	}
	
	
}
