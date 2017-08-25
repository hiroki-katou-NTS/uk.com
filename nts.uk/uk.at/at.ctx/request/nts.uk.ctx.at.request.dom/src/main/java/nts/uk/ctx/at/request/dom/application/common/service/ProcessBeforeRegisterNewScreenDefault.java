package nts.uk.ctx.at.request.dom.application.common.service;

public class ProcessBeforeRegisterNewScreenDefault {
	
	public void processBeforeRegisterNewScreen(String companyID, String employeeID, String startDate, String endDate, int postAtr, int routeAtr, String targetApp){
		/*
		retirementCheckBeforeJoinCompany(companyID, employeeID, startDate);
		OtherCommonAlgorithm a = new OtherCommonAlgorithm();
		period = a.employeePeriodCurrentMonthCalculate(companyID, employeeID, startDate);
		periodStartDate = period.startDate; 
		periodEndDate = period.endDate;
		if(Integer.parseInt(endDate)-Integer.parseInt(startDate)>31) throw new BusinessException("Msg_277");
		if(periodStartDate.addYear(1) <= periodEndDate) throw new BusinessException("Msg_276");
		if(startDate < periodStartDate) throw new BusinessException("Msg_236");
		confirmRoot = from cache;
		if(confirmRoot.ApprovedRouteErrorFlag==undefined) throw new BusinessException("Msg_238");
		if(confirmRoot.ApprovedRouteErrorFlag.count==0) throw new BusinessException("Msg_324");
		if(confirmRoot.ApprovedRouteErrorFlag.count>=10) throw new BusinessException("Msg_237");
		if(passwordLevel!=0) deadlineApplicationCheck();
		if(passwordLevel!=0) applicationAcceptanceRestrictionsCheck();
		confirmationCheck();
		*/
	}
	
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, String date){
		/*
		Employee e = Employee.find(employeeID); 
		beforeData = Check Before Data;
		if((beforeData.count >=1)&&(date<e.entryDate)) throw new BusinessException(Msg_235);
		afterData = Check After Data;
		if((afterData.count >=1)&&(date<e.retirementDate)) throw new BusinessException(Msg_391); 
		*/
	}
	
	public void deadlineApplicationCheck(String appID, String appStartDate, String appEndDate, String startDate, String endDate){
		/*
		ApplicationDeadlineSetting obj = find(ApplicationApprovalFunctionSetting.ApplicationDeadlineSetting.TighteningID = appID);
		if(obj.useAtr == false) return;
		Loop(startDate -> endDate) {
			if(loopDate > appEndDate) return;
			if(obj.deadline = workingDay) {
				// Waiting for algorithm
				// input: appEndDate, obj.deadline, 
			} else {
				// obj.deadline = calendarDay ???
				deadline = appEndDate.addDay(obj.deadline);
			}
			if(systemDate > deadline) throw new BusinessException("Msg_327"); 
		}	
		 */
	}
	
	public void applicationAcceptanceRestrictionsCheck(int postAtr, String startDate, String endDate){
		/*
		if(postAtr == afterApplication){ // 事前事後区分 == 事後(Xin sau)
			if(AdvanceAcceptanceRestriction.doNotAllowFutureDays == false) {  //  「事前の受付制限」．未来日許可しない
				return;
			} else { 
				// 今日までの事後申請を登録する制限
				
					例：システム日付:2017/7/14
					2017/7/13の事後申請を登録。。。ＯＫ
					2017/7/14の事後申請を登録。。。ＯＫ
					2017/7/15の事後申請を登録。。。ＮＧ
						
				if(startDate>systemDate||endDate>systemDate) { //  systemDate <=> システム日付 
					throw new BusinessException("Msg_328");
				} else {
					return;
				}
			}
		} else { 
			// postAtr == beforeApplication, 事前事後区分 == 事前(Xin trước)
			if(AdvanceAcceptanceRestriction.useAtr == false) {  //  事前の受付制限」．利用区分
				return;
			} else {
				Loop(startDate -> endDate) {
					if(AdvanceAcceptanceRestriction.method == dayCheck){ // 「事前の受付制限」．チェック方法 == 日数
						limitDay = systemDate + AdvanceAcceptanceRestriction.day 
						
						// 受付制限日 = システム日付 + 「事前の受付制限」．日数

						例）「事前の受付制限」．日数 == 2　システム日付 == 2017/7/14 
						受付制限日 = 2017/7/16
						2017/7/15の事前申請を登録。。。ＮＧ
						2017/7/16の事前申請を登録。。。ＯＫ
						2017/7/17の事前申請を登録。。。ＯＫ
						
						
						例）「事前の受付制限」．日数 == 0　システム日付 == 2017/7/14 
						受付制限日 = 2017/7/14
						2017/7/13の事前申請を登録。。。ＮＧ
						2017/7/14の事前申請を登録。。。ＯＫ
						2017/7/15の事前申請を登録。。。ＯＫ
																			
						if(loopDay < limitDay) throw new BusinessException("Msg_327");
					} else {
						// 「事前の受付制限」．チェック方法 == 時刻
						if(loopDay < systemDate) {
							throw new BusinessException("Msg_327");
						} else if(loopDay == systemDate){
							limitDay = AdvanceAcceptanceRestriction.hourAndMinutes 
							
							// 受付制限日時 = 「事前の受付制限」．時分
								システム日時 = System.DateTime.Nowのhour * 60 + System.DateTime.Nowのminute;
								
								例）「事前の受付制限」．時分 = 1080(18:00)
								
								システム日時 = 1079(17:59)今日の事前申請を登録。。。ＯＫ
								システム日時 = 1080(18:00)今日の事前申請を登録。。。ＯＫ
								システム日時 = 1081(18:01)今日の事前申請を登録。。。ＮＧ 
								
							if(systemDate > limitDay) throw new BusinessException("Msg_327");
						}
					}
				}
			}
		}
		*/
	}
	
	public void confirmationCheck(String companyID, String employeeID, String appDate){
		/*
		obj = "Imported(ApplicationApproval)[ActualResultConfirmedState]  // 「Imported(申請承認)「実績確定状態」 
		if(obj.isPresent()){
			applicationRestrictionSetting = from cache; // 申請制限設定
			if(applicationRestrictionSetting.canNotApplyIfTheResultsByDayAreConfirmed == true){
				obj1 = Imported(ApplicationApproval)[ActualResultConfirmedState].dailyPerformanceConfirmed; // 「Imported(申請承認)「実績確定状態」．日別実績が確認済
				if(obj1 = true) throw new BusinessException(Msg_448);
			}
			if(applicationRestrictionSetting.canNotApplyIfTheResultsByMonthAreConfirmed == true){
				obj2 = Imported(ApplicationApproval)[ActualResultConfirmedState].monthlyPerformanceConfirmed; // 「Imported(申請承認)「実績確定状態」．月別実績が確認済
				if(obj2 = true) throw new BusinessException(Msg_449);
			}
			if(applicationRestrictionSetting.canNotApplyIfWorkHasBeenFixed == true){
				obj3 = Imported(ApplicationApproval)[ActualResultConfirmedState].classificationOfEmploymentOfBelongingWorkplace; // 「Imported(申請承認)「実績確定状態」．所属職場の就業確定区分
				if(obj3 = true) throw new BusinessException(Msg_450);
			}
			if(applicationRestrictionSetting.canNotApplyIfActualResultIsLockedState == true){
				date = from cache;
				アルゴリズム「社員の当月の期間を算出する」を実行する(thực hiện xử lý 「社員の当月の期間を算出する」)
				obj4 = OtherCommonAlgorithm.employeePeriodCurrentMonthCalculate(companyID, employeeID, date); // obj4 <=> 締め期間(開始年月日,終了年月日)
				if(obj4.startDate <= appDate <= obj4.endDate){ 
					obj5 = Imported(ApplicationApproval)[ActualResultConfirmedState].lockOfDailyPerformance; // 「Imported(申請承認)「実績確定状態」．日別実績のロック
					obj6 = Imported(ApplicationApproval)[ActualResultConfirmedState].lockOfMonthlyPerformance; // 「Imported(申請承認)「実績確定状態」．月別実績のロック
					if((obj5==true)||(obj6==true)) throw new BusinessException(Msg_451);
				} 
			}
		}
		 */
	}
}
