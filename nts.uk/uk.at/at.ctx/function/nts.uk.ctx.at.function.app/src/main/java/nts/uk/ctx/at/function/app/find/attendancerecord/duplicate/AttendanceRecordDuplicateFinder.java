package nts.uk.ctx.at.function.app.find.attendancerecord.duplicate;

import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordRepositoty;
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
	
	@Inject
	AttendanceRecordRepositoty attdRecRepo;

	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.KWR002₌出勤簿 (Phiếu chấm công)
	 * .F:出勤簿レイアウト情報の複製(Duplicate thông tin layout phiếu chấm công)
	 * .F:アルゴリズム (Thuật toán).起動処理 (Xử lý khởi động).起動処理
	 * 
	 * @param param
	 * @return code and name
	 */
	public DataInfoReturnDto findCopyAttendance(AttendanceRecordDuplicateDto param) {
		AttendanceRecordExportSetting ares = new AttendanceRecordExportSetting();
		String companyId = AppContexts.user().companyId();
		
		if (param.getSelectedType() == ItemSelectionType.STANDARD_SETTING.value) {
			// ドメインモデル「出勤簿の出力項目定型設定」を取得する (Get domain model 「出勤簿の出力項目定型設定」)
			Optional<AttendanceRecordStandardSetting> standardSetting = standardRepo.getStandardWithLayoutId(companyId,
					param.getLayoutId(), param.getCode());
			
			if (standardSetting.isPresent() && !standardSetting.get().getAttendanceRecordExportSettings().isEmpty()) {
				ares = standardSetting.get().getAttendanceRecordExportSettings().get(0);
			}
		}
		
		if (param.getSelectedType() == ItemSelectionType.FREE_SETTING.value) {
			String employeeId = AppContexts.user().employeeId();
			// ドメインモデル「出勤簿の出力項目自由設定」を取得する (Get domain model 「出勤簿の出力項目自由設定」)
			Optional<AttendanceRecordFreeSetting> freeSetting = freeSettingRepo.getFreeWithLayoutId(companyId, employeeId,
					param.getLayoutId(), param.getCode());
			
			if (freeSetting.isPresent() && !freeSetting.get().getAttendanceRecordExportSettings().isEmpty()) {
				ares = freeSetting.get().getAttendanceRecordExportSettings().get(0);
			}
		}
		
		DataInfoReturnDto result = new DataInfoReturnDto();
		result.setCode(ares.getCode().v());
		result.setName(ares.getName().v());
		
		return result;
	}

	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.KWR002₌出勤簿 (Phiếu chấm công)
	 * .F:出勤簿レイアウト情報の複製(Duplicate thông tin layout phiếu chấm công)
	 * .F:アルゴリズム (Thuật toán).複製登録処理 (Xử lý đăng ký Duplicate).複製登録処理
	 * 
	 * @param param
	 * @return
	 */
	public void executeCopy(AttendanceRecordDuplicateDto param) {
		// 複製元コードと複製先コードが同じかチェックする Check mã nguồn và mã đích duplicate có giống nhau không
		if (param.duplicateCode.equals(param.code)) { // 同じ場合
			throw new BusinessException("Msg_355");
		}
		
		String companyId = AppContexts.user().companyId();
		Optional<String> employeeId = param.getSelectedType() == ItemSelectionType.FREE_SETTING.value
						? Optional.of(AppContexts.user().employeeId())
						: Optional.empty();

		// INPUT．項目選択種類をチェック Check INPUT.Item selection type
		// ドメインモデル「出勤簿の出力項目定型設定」を取得する Nhận domain model 「出勤簿の出力項目定型設定」
		// ドメインモデル「出勤簿の出力項目自由設定」を取得する  Nhận domain model 「...」
		Optional<AttendanceRecordExportSetting> setting = this.attendanceRecExpSetRepo.findByCode(ItemSelectionType.valueOf(param.getSelectedType())
				, companyId
				, employeeId
				, param.getDuplicateCode());
		
		// 複製先のドメインモデルが存在しているかチェックする Check xem có tồn tại domain model duplicate destination không
		if (setting.isPresent()) { // 存在している場合 Tồn tại
			throw new BusinessException("Msg_3");
		}
		
		String layoutId = param.getLayoutId();
		String duplicateId = UUID.randomUUID().toString();
		
		// INPUT．項目選択種類をチェック Check INPUT.Item selection type
		if (param.getSelectedType() == ItemSelectionType.STANDARD_SETTING.value) {
			
			// ドメインモデル「出勤簿の出力項目定型設定」を取得する (Get domain model 「出勤簿の出力項目定型設定」)
			Optional<AttendanceRecordStandardSetting> standardSetting = standardRepo.getStandardWithLayoutId(companyId,
					layoutId, param.getCode());
			
			if (standardSetting.isPresent() && !standardSetting.get().getAttendanceRecordExportSettings().isEmpty()) {
				standardSetting.get().getAttendanceRecordExportSettings().get(0).setCode(new ExportSettingCode(param.getDuplicateCode()));
				standardSetting.get().getAttendanceRecordExportSettings().get(0).setName(new ExportSettingName(param.getDuplicateName()));
				standardSetting.get().getAttendanceRecordExportSettings().get(0).setLayoutId(duplicateId);
				
				standardRepo.save(standardSetting.get());
				attdRecRepo.duplicateAttendanceRecord(layoutId, duplicateId);
			}
		}
		
		if (param.getSelectedType() == ItemSelectionType.FREE_SETTING.value) {
			// ドメインモデル「出勤簿の出力項目自由設定」を取得する (Get domain model 「出勤簿の出力項目自由設定」)
			Optional<AttendanceRecordFreeSetting> freeSetting = freeSettingRepo.getFreeWithLayoutId(companyId, employeeId.get(),
					layoutId, param.getCode());
			
			if (freeSetting.isPresent() && !freeSetting.get().getAttendanceRecordExportSettings().isEmpty()) {
				freeSetting.get().getAttendanceRecordExportSettings().get(0).setCode(new ExportSettingCode(param.getDuplicateCode()));
				freeSetting.get().getAttendanceRecordExportSettings().get(0).setName(new ExportSettingName(param.getDuplicateName()));
				freeSetting.get().getAttendanceRecordExportSettings().get(0).setLayoutId(duplicateId);
				
				freeSettingRepo.save(freeSetting.get());
				attdRecRepo.duplicateAttendanceRecord(layoutId, duplicateId);
			}
		}
	}

}
