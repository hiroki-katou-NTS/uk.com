package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveAppSetting;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;

@Stateless
public class NotDepentSpecialLeaveOfEmployeeImpl implements NotDepentSpecialLeaveOfEmployee{
	@Inject
	private SpecialHolidayRepository speHolidayRepos;
	@Inject
	private GrantDateTblRepository grantTblRepos;
	@Inject
	private InforSpecialLeaveOfEmployeeSevice inforSpeEmpService;
	@Override
	public InforSpecialLeaveOfEmployee getNotDepentInfoSpecialLeave(NotDepentSpecialLeaveOfEmployeeInput param) {
		InforSpecialLeaveOfEmployee outputData = new InforSpecialLeaveOfEmployee(InforStatus.NOTUSE, Optional.empty(), new ArrayList<>(), false);
		//ドメインモデル「特別休暇」を取得する
		Optional<SpecialHoliday> optSpeHolidayInfor = speHolidayRepos.findByCode(param.getCid(), param.getSpecialLeaveCode());
		if(!optSpeHolidayInfor.isPresent()) {
			return outputData;
		}
		SpecialHoliday speHoliday = optSpeHolidayInfor.get();
		//付与日数情報を取得する
		GrantDaysInforByDates grantDaysInfor = this.getGrantDays(param, speHoliday);
		//Output「付与日数一覧」の件数をチェックする
		if(grantDaysInfor == null 
				|| grantDaysInfor.getLstGrantDaysInfor().isEmpty()) {
			outputData.setStatus(InforStatus.NOTGRANT);
			return outputData;
		}
		//期限を取得する
		List<SpecialHolidayInfor> getDeadlineInfo = this.getPeriodGrantDate(grantDaysInfor, speHoliday);
		outputData.setStatus(InforStatus.GRANTED);
		outputData.setSpeHolidayInfor(getDeadlineInfo);
		//・端数消滅：ドメインモデル「特別休暇．付与情報．取得できなかった端数は消滅する」
		outputData.setChkDisappear(speHoliday.getGrantRegular().isAllowDisappear());
		//・蓄積上限日数：ドメインモデル「特別休暇．期限情報．繰越上限日数」
		outputData.setUpLimiDays(speHoliday.getGrantPeriodic().getLimitCarryoverDays() == null ? Optional.empty() :
				Optional.of(speHoliday.getGrantPeriodic().getLimitCarryoverDays().v()));
		return outputData;
	}
	@Override
	public GrantDaysInforByDates getGrantDays(NotDepentSpecialLeaveOfEmployeeInput param, SpecialHoliday speHoliday) {
		//取得しているドメインモデル「特別休暇．付与情報．付与基準日」をチェックする
		GrantDate grantDateInfor = speHoliday.getGrantRegular().getGrantDate();
		//付与基準日
		GeneralDate baseDate = GeneralDate.today();
		if(grantDateInfor == GrantDate.EMP_GRANT_DATE) {
			//パラメータ「入社年月日」をパラメータ「付与基準日」にセットする
			baseDate = param.getInputDate();
		} else if (grantDateInfor == GrantDate.GRANT_BASE_HOLIDAY) {
			//パラメータ「年休付与基準日」をパラメータ「付与基準日」にセットする
			baseDate = param.getAnnGrantDate();
		} else {
			//パラメータ「特別休暇付与基準日」をパラメータ「付与基準日」にセットする
			baseDate = param.getSpeGrantDate();
		}
		//取得している「特別休暇．付与情報．付与するタイミングの種類」をチェックする
		TypeTime typeTime = speHoliday.getGrantRegular().getTypeTime();
		if(typeTime == TypeTime.GRANT_START_DATE_SPECIFY) {
			//固定の付与日一覧を求める
			RequestGrantData paraFixed = new RequestGrantData(param.getDatePeriod(),
					baseDate, param.isSignFlg(),
					param.getSpecialSetting(),
					param.getGrantDays(),
					param.getCid(),
					Optional.empty(), 0);
			return this.getGrantDaysOfFixed(paraFixed, speHoliday);
		} else {
			//テーブルに基づいた付与日数一覧を求める
			RequestGrantData paraTbl = new RequestGrantData(param.getDatePeriod(),
					baseDate,
					param.isSignFlg(),
					param.getSpecialSetting(),
					Optional.empty(),
					param.getCid(),
					param.getGrantTableCd(),
					param.getSpecialLeaveCode());
			return this.getGrantDaysOfTable(paraTbl, speHoliday);
		}
	}
	@Override
	public GrantDaysInforByDates getGrantDaysOfFixed(RequestGrantData param, SpecialHoliday speHoliday) {
		//パラメータ「付与基準日」をパラメータ「比較年月日」にセットする
		GeneralDate startLoopDate = param.getGrantDate();
		GrantDaysInforByDates outputData = new GrantDaysInforByDates(startLoopDate, new ArrayList<>());
		int inteval = speHoliday.getGrantRegular().getGrantTime().getFixGrantDate().getInterval().v();
		if(inteval == 0) {
			return outputData;
		}
		//パラメータ「比較年月日」に取得している「特別休暇．付与情報．固定付与日．周期」を加算する
		List<GrantDaysInfor> lstGrantDays = new ArrayList<>();
		for(GeneralDate loopDate = startLoopDate; loopDate.beforeOrEquals(param.getDatePeriod().end());) {
			if(param.getDatePeriod().start().beforeOrEquals(loopDate)
					&& loopDate.beforeOrEquals(param.getDatePeriod().end())) {
				double grantDays = 0;
				if(param.getSpecialSetting() != SpecialLeaveAppSetting.PRESCRIBED) {
					grantDays = param.getFixedGrantDays().isPresent() ? param.getFixedGrantDays().get() : 0;
				} else {
					grantDays = speHoliday.getGrantRegular().getGrantTime().getFixGrantDate().getGrantDays().v();
				}
				//パラメータ「付与日数一覧」を追加する
				GrantDaysInfor infor = new GrantDaysInfor(loopDate, Optional.empty(), grantDays);
				lstGrantDays.add(infor);
				if(param.isSignFlg()) {
					break;
				}				
			}
			if(param.getDatePeriod().end().afterOrEquals(loopDate)) {
				loopDate = loopDate.addYears(inteval);
				outputData.setGrantDate(loopDate);	
			}			
		}
		
		if(!lstGrantDays.isEmpty()) {
			lstGrantDays = lstGrantDays.stream().sorted((a, b) -> a.getYmd().compareTo(b.getYmd()))
					.collect(Collectors.toList());
		}
		outputData.setLstGrantDaysInfor(lstGrantDays);
		return outputData;
	}
	@Override
	public GrantDaysInforByDates getGrantDaysOfTable(RequestGrantData param, SpecialHoliday speHoliday) {
		GrantDaysInforByDates outputData = new GrantDaysInforByDates(param.getGrantDate(), new ArrayList<>());
		//ドメインモデル「特別休暇付与テーブル」を取得する
		List<ElapseYear> elapseYear = new ArrayList<>();
		//パラメータ「特別休暇適用設定」≠所定の条件を適用する　の場合
		//パラメータ「付与テーブルコード」		
		if(param.getSpecialSetting() != SpecialLeaveAppSetting.PRESCRIBED) {
			if(!param.getGrantTblCd().isPresent()) {
				return outputData;
			}
			elapseYear = grantTblRepos.findElapseByGrantDateCd(param.getCid(),
					speHoliday.getSpecialHolidayCode().v(),
					param.getGrantTblCd().get());
		}
		//パラメータ「特別休暇適用設定」＝所定の条件を適用する　の場合
		//　規定のテーブルとする＝TRUE
		else {
			Optional<GrantDateTbl> optGranDateTbl = grantTblRepos.findByCodeAndIsSpecified(param.getCid(),
					speHoliday.getSpecialHolidayCode().v());
			if(optGranDateTbl.isPresent()) {
				elapseYear = grantTblRepos.findElapseByGrantDateCd(param.getCid(), speHoliday.getSpecialHolidayCode().v(), optGranDateTbl.get().getGrantDateCode().v());
			}
		}
		if(elapseYear.isEmpty()) {
			return outputData;
		}
		//パラメータ「付与基準日」をパラメータ「比較年月日」にセットする
		GeneralDate baseDate = param.getGrantDate();
		List<GrantDaysInfor> lstGrantDays = new ArrayList<>();
		for (ElapseYear yearData : elapseYear) {
			//パラメータ「比較年月日」に取得したドメインモデル「特別休暇付与テーブル．経過年数に対する付与日数．経過年数」を加算する
			GeneralDate loopDate = baseDate.addYears(yearData.getYears().v());
			loopDate = loopDate.addMonths(yearData.getMonths().v());
			outputData.setGrantDate(loopDate);
			//パラメータ「比較年月日」とパラメータ「期間」を比較する
			if(loopDate.before(param.getDatePeriod().start())) {
				continue;
			}
			if(param.getDatePeriod().end().before(loopDate)) {
				break;
			}
			if(param.getDatePeriod().start().beforeOrEquals(loopDate)
					&& loopDate.beforeOrEquals(param.getDatePeriod().end())) {
				GrantDaysInfor infor = new GrantDaysInfor(loopDate, Optional.empty(), yearData.getGrantedDays().v());
				lstGrantDays.add(infor);
				if(param.isSignFlg()) {
					break;
				}
			}
		}
		if(!lstGrantDays.isEmpty()) {
			lstGrantDays = lstGrantDays.stream().sorted((a, b) -> a.getYmd().compareTo(b.getYmd()))
					.collect(Collectors.toList());
		}
		outputData.setLstGrantDaysInfor(lstGrantDays);
		return outputData;
	}
	@Override
	public List<SpecialHolidayInfor> getPeriodGrantDate(GrantDaysInforByDates param, SpecialHoliday speHoliday) {
		/*List<SpecialHolidayInfor> lstOutput = new ArrayList<>();
		//取得している「特別休暇．期限情報．期限指定方法」をチェックする
		TimeLimitSpecification timeSpecifyMethod = speHoliday.getGrantPeriodic().getTimeSpecifyMethod();
		if(timeSpecifyMethod == TimeLimitSpecification.INDEFINITE_PERIOD) {
			//パラメータ「付与日数一覧」を元にパラメータ「特別休暇情報一覧」を生成する
			for (GrantDaysInfor grantInfor : param.getLstGrantDaysInfor()) {
				SpecialHolidayInfor infor = new SpecialHolidayInfor(new GrantDaysInfor(grantInfor.getYmd(), grantInfor.getErrorFlg(), grantInfor.getGrantDays()), Optional.empty());
				lstOutput.add(infor);
			}
		} else if (timeSpecifyMethod == TimeLimitSpecification.AVAILABLE_GRANT_DATE_DESIGNATE) {
			//パラメータ「付与日数一覧」を元にパラメータ「特別休暇情報一覧」を生成する
			for (GrantDaysInfor grantInfor : param.getLstGrantDaysInfor()) {
				//パラメータ「付与日数一覧．年月日」＋取得している「定期付与．付与日（定期）．特別休暇の期限」の「年数」「月数」
				SpecialVacationDeadline speDeadline = speHoliday.getGrantPeriodic().getExpirationDate();
				GeneralDate dealineDate = grantInfor.getYmd().addYears(speDeadline.getYears().v());
				dealineDate.addMonths(speDeadline.getMonths().v());
				SpecialHolidayInfor infor = new SpecialHolidayInfor(new GrantDaysInfor(grantInfor.getYmd(), grantInfor.getErrorFlg(), grantInfor.getGrantDays()), Optional.of(dealineDate));
				lstOutput.add(infor);
			}			
		} else if (timeSpecifyMethod == TimeLimitSpecification.AVAILABLE_UNTIL_NEXT_GRANT_DATE) {
			//期限日　←　パラメータ「付与日数一覧．年月日」の次の「付与日数一覧．年月日」
			　//※最後の処理で次の「付与日数一覧．年月日」が存在しない場合
			　　　//→パラメータ「期間外次回付与日」をセット
			int i = 1;
			for (GrantDaysInfor daysInfor : grantDaysInfor.getLstGrantDaysInfor()) {
				SpecialHolidayInfor output = new SpecialHolidayInfor();
				if(i == grantDaysInfor.getLstGrantDaysInfor().size()) {
					output = new SpecialHolidayInfor(daysInfor, grantDaysInfor.getGrantDate() != null 
							? Optional.of(grantDaysInfor.getGrantDate()) : Optional.empty());
				} else {
					GrantDaysInfor nextInfor = grantDaysInfor.getLstGrantDaysInfor().get(i);
					output = new SpecialHolidayInfor(daysInfor, Optional.of(nextInfor.getYmd()));
				}
				lstOutput.add(output);
				i += 1;
			}
		}
		return null;*/
		return inforSpeEmpService.getDeadlineInfo(param, speHoliday);
	}

}
