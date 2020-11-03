package nts.uk.screen.at.app.kdl045.query;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * «ScreenQuery» 詳細情報を取得する
 * @author tutk
 *
 */
@Stateless
public class GetMoreInformation {
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject 
	private RecordDomRequireService requireService;
	
	public GetMoreInformationOutput getMoreInformation(String employeeId,String workType,String workTimeCode) {
		
		String companyId = AppContexts.user().companyId();
		GetMoreInformationOutput data =new GetMoreInformationOutput();
		val require = requireService.createRequire();
		
		//1 : 共通設定の取得
		Optional<WorkTimezoneCommonSet> optWorktimezone = GetCommonSet.workTimezoneCommonSet(require, companyId, workTimeCode);
		WorkTimezoneCommonSetDto workTimezoneCommonSetDto = new WorkTimezoneCommonSetDto(); 
		if(!optWorktimezone.isPresent()) {
			data.setWorkTimezoneCommonSet(null);
		}else {
			optWorktimezone.get().saveToMemento(workTimezoneCommonSetDto);
			data.setWorkTimezoneCommonSet(workTimezoneCommonSetDto);
		}
		
		//TODO: 2 :休憩時間帯を取得する (require: Require): Optional<休憩時間>
		data.setBreakTime(null);

		//3:就業時間帯の設定を取得する
		//emptyの運用はあり得ないため - QA : 112354
		Optional<WorkTimeSetting> workTimeSetting = workTimeSettingRepository.findByCode(companyId, workTimeCode);
		data.setWorkTimeForm(workTimeSetting.get().getWorkTimeDivision().getWorkTimeForm().value);
		
		return data;
		
	}
}
