package nts.uk.ctx.at.aggregation.app.find.scheduletable.outputsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author quytb
 *UKDesign.UniversalK.就業.KSU_スケジュール.KSU005_個人スケジュール表. B：個人スケジュール表_出力項目設定(職場別).Ｂ：メニュー別OCD.Ｂ：画面表示
 */
@Stateless
public class ScheduleTableOutputSettingFinder {
	@Inject
	private ScheduleTableOutputSettingRepository repository;
	
	/**
	 *UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール表.APP.出力項目設定情報を取得する
	 */
	public ScheduleTableOutputSettingDto findByCidAndCode(String code) {
		Optional<ScheduleTableOutputSetting> optional = repository.get(AppContexts.user().companyId(), new OutputSettingCode(code));
		return optional.isPresent() ? ScheduleTableOutputSettingDto.setData(optional.get()): new ScheduleTableOutputSettingDto();
	}

	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール表.APP.出力項目設定一覧情報を取得する
	 */
	public List<ScheduleTableOutputSettingDto> findByCid() {
		List<ScheduleTableOutputSetting> scheduleTableOutputSettings = repository
				.getList(AppContexts.user().companyId());
		if (scheduleTableOutputSettings.isEmpty()) {
			List<ScheduleTableOutputSettingDto> results = new ArrayList<ScheduleTableOutputSettingDto>();
			results.add(ScheduleTableOutputSettingDto.builder()
					.isAttendance(AppContexts.user().roles().isInChargeAttendance())
					.hasAttendance(AppContexts.user().roles().isInChargeAttendance()).build());
			return results;			
		} else {
			return scheduleTableOutputSettings.stream().map(item -> ScheduleTableOutputSettingDto.setData(item))
					.collect(Collectors.toList());
		}
	}
}
