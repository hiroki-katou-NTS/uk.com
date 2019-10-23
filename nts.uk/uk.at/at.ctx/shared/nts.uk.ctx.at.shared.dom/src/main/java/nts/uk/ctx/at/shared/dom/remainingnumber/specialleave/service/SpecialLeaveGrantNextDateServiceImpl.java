package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class SpecialLeaveGrantNextDateServiceImpl implements SpecialLeaveGrantNextDateService{
	@Inject
	private NotDepentSpecialLeaveOfEmployee notDepentSpeService;
	@Override
	public Optional<GrantDaysInfor> getNumberOfGrantDays(SpecialLeaveGrantNextDateInput param) {
		//次回特休付与計算開始日を計算する
		GeneralDate startDate = this.getStartDateOfSpecialNextDay(param.getClosureDate(), param.getInputDate());
		//アルゴリズム「次回特別休暇付与日を取得」を実行
		SpeGrantNextDateByGetInput inputParam = new SpeGrantNextDateByGetInput(param.getCid(),
				param.getSpecialCode(),
				new DatePeriod(startDate, GeneralDate.max()),
				param.getSpecialDate(),
				param.getSpecialSetting(),
				param.getSpeGrantDataCode(),
				param.getGranDays(),
				param.getInputDate(),
				param.getAnnualHolidayDate());
		return this.getGrantDataOfNextDay(inputParam);
	}

	@Override
	public GeneralDate getStartDateOfSpecialNextDay(GeneralDate closureDate, GeneralDate inputDate) {
		GeneralDate outputDate = GeneralDate.today();
		//パラメータ「入社年月日」とパラメータ「締め開始日」を比較
		if(closureDate.afterOrEquals(inputDate)) {
			//次回特休付与計算開始日　←　パラメータ「締め開始日」
			outputDate = closureDate;
		} else {
			//次回特休付与計算開始日　←　パラメータ「入社年月日」
			outputDate = inputDate;
		}
		//次回特休付与計算開始日 = 次回特休付与計算開始日.AddDays(1)
		outputDate = outputDate.addDays(1);
		return outputDate;
	}

	@Override
	public Optional<GrantDaysInfor> getGrantDataOfNextDay(SpeGrantNextDateByGetInput param) {
		//社員に依存しない特別休暇情報を取得する
		NotDepentSpecialLeaveOfEmployeeInput inputData = new NotDepentSpecialLeaveOfEmployeeInput(param.getCid(), 
				param.getDatePeriod(), 
				param.getSpecialCode(), 
				param.getSpecialDate(),
				param.getAnnualHolidayDate(),
				param.getInputDate(), 
				true, 
				param.getSpecialSetting(), 
				param.getGranDays(),
				param.getSpeGrantDataCode());
		InforSpecialLeaveOfEmployee speInfor = notDepentSpeService.getNotDepentInfoSpecialLeave(inputData);
		//Output「付与日数一覧」をチェックする
		if(speInfor.getSpeHolidayInfor().isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(speInfor.getSpeHolidayInfor().get(0).getGrantDaysInfor());
	}

}
