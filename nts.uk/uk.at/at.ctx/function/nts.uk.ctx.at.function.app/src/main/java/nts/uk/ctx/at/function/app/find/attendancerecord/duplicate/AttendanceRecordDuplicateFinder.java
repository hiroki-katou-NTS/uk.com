package nts.uk.ctx.at.function.app.find.attendancerecord.duplicate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Screen F KWR002
 * The Class AttendanceRecordDuplicateFinder.
 */
@Stateless
public class AttendanceRecordDuplicateFinder {

	
	@Inject
	private AttendanceRecordFreeSettingRepository freeSettingRepo;
	
	@Inject
	private AttendanceRecordStandardSettingRepository standardRepo;
	
	@Inject
	AttendanceRecordExportSettingRepository attendanceRecExpSetRepo;
	
	@Inject
	SingleAttendanceRecordRepository singleAttendanceRecordRepository;
	
	@Inject
	CalculateAttendanceRecordRepositoty calculateAttendanceRecordRepositoty;
	
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

	public String executeCopy(AttendanceRecordDuplicateDto dto) {
		String companyId = AppContexts.user().companyId();
		Optional<String> employeeId = dto.getSelectedType() == ItemSelectionType.FREE_SETTING.value
						? Optional.of(AppContexts.user().employeeId())
						: Optional.empty();
		
		// INPUT．項目選択種類をチェック Check INPUT.Item selection type
		// ドメインモデル「出勤簿の出力項目定型設定」を取得する Nhận domain model 「出勤簿の出力項目定型設定」
		// ドメインモデル「出勤簿の出力項目自由設定」を取得する  Nhận domain model 「...」
		Optional<AttendanceRecordExportSetting> setting = this.attendanceRecExpSetRepo.findByCode(ItemSelectionType.valueOf(dto.getSelectedType())
				, companyId
				, employeeId
				, dto.getDuplicateCode());
		
		// 複製先のドメインモデルが存在しているかチェックする Check xem có tồn tại domain model duplicate destination không
		if (setting.isPresent()) {
			return "Msg_3";
		}
		return null;
	}
	
}
