__viewContext.ready(function() {
	debugger;
	var paramSPR = JSON.parse(window.sessionStorage.getItem("paramSPR"));
	window.sessionStorage.removeItem("paramSPR");
    var menuCD = parseInt(paramSPR.menu);
    var loginemployeeCD = paramSPR.loginemployeeCode;
    var employeeCode = paramSPR.employeeCode;
    var starttime = parseInt(paramSPR.starttime);
    var endtime = parseInt(paramSPR.endtime);
    var date = paramSPR.date;
    var selecttype = parseInt(paramSPR.selecttype);
    var applicationID = paramSPR.applicationID;
    var reason = paramSPR.reason;
    var userID = paramSPR.userID;
    var contractCD = paramSPR.contractCD;
    var companyID = paramSPR.companyID;
    var companyCD = paramSPR.companyCD;
    var personID = paramSPR.personID;
    var loginEmployeeID = paramSPR.loginEmployeeID;
    var roleID = paramSPR.roleID;
    var employeeID = paramSPR.employeeID;
	switch(menuCD) {
	case 1:
		break;
	case 2:
		break;
	case 3:
		var initParam = {
			//画面モード
			screenMode: 0,
			//社員一覧
			lstEmployee: [employeeID],
			//エラー参照を起動する
			errorRefStartAtr: true,
			//期間を変更する
			changePeriodAtr: true,
			//処理締め
			targetClosue: null,
			//Optional
			//打刻初期値
			initClock: {
				dateSpr: date,
				canEdit: false,
				employeeId: employeeID, 
				liveTime: endtime,	//退勤打刻
				goOut: starttime,  //出勤打刻
			},
			//遷移先の画面
			transitionDesScreen: null,
		}

		var extractionParam = {
			//表示形式
			displayFormat: 0, // DPCorrectionDisplayFormat.INDIVIDUAl(個人別)
			//期間
			startDate: date,
			endDate: date,
			//抽出した社員一覧
			lstExtractedEmployee: employeeID,
			//Optional
			//日付別で起動
			dateTarget: date,
			individualTarget: employeeID,
		}
		nts.uk.request.jump("at", "/view/kdw/003/a/index.xhtml", {initParam: initParam, extractionParam: extractionParam});
		break;
	case 4:
		break;
	case 5:
		break;
	case 6:
		break;
	default:
        nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
	}
})