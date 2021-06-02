//ScreenQueryGetInforOfInitStartup
module nts.uk.at.view.ksu003.a {
	export module service {
        /**
         *  Service paths
         */
		var servicePath: any = {
			getDataStartScreen: "screen/at/schedule/getinfo-initstart",
			getFixedWorkInformation: "screen/at/schedule/getfixedworkinfo",
			displayDataKsu003: "screen/at/schedule/displayDataKsu003",
			sortEmployee: "screen/at/schedule/sortEmployee",
			getEmpWorkFixedWorkInfo: "screen/at/schedule/getEmpWorkFixedWorkInfo",
			changeWorkType: "screen/at/schedule/changeWorkType",
			checkWorkType: "screen/at/schedule/checkWorkType",
            checkTimeIsIncorrect: "ctx/at/shared/workrule/workinghours/checkTimeIsIncorrect",
        	regWorkSchedule: "screen/at/schedule/registerKSU003",
			getTaskInfo : "screen/at/schedule/getTaskInfo",
			getTaskPallet : "screen/at/schedule/getTaskPallet",
			getTaskPaletteDisplay : "screen/at/schedule/getTaskPaletteDisplay",
			checkEmpAttendance : "screen/at/schedule/checkEmpAttendance",
			getWorkScheduleInfor : "screen/at/schedule/getWorkScheduleInfor",
			addTaskWorkSchedule : "screen/at/schedule/addTaskWorkSchedule",
			addScheduleByDisplaySet : "screen/at/schedule/addScheduleByDisplaySet"
			
		};
		
		export function checkTimeIsIncorrect(command : any): JQueryPromise<any> {
            return nts.uk.request.ajax( "at", servicePath.checkTimeIsIncorrect, command);
        }

        /**
         * ①<<ScreenQuery>> 初期起動の情報取得
         */
		export function getDataStartScreen(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.getDataStartScreen, command);
		}

        /**
         * ①<<ScreenQuery>> 勤務固定情報を取得する
         */
		export function getFixedWorkInformation(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.getFixedWorkInformation, command);
		}

        /**
         * ①<<ScreenQuery>> 日付別勤務情報で表示する
         */
		export function displayDataKsu003(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.displayDataKsu003, command);
		}
		
		/**
         * ①<<ScreenQuery>> 社員を並び替える
         */
		export function sortEmployee(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.sortEmployee, command);
		}
		
		/**
         * ①<<ScreenQuery>> 社員勤務予定と勤務固定情報を取得する
         */
		export function getEmpWorkFixedWorkInfo(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.getEmpWorkFixedWorkInfo, command);
		}
		
		/**
         * ①<<ScreenQuery>> 勤務種類を変更する
         */
		export function changeWorkType(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.changeWorkType, command);
		}
		
		/**
         * ①<<ScreenQuery>> 勤務種類を変更する
         */
		export function checkWorkType(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.checkWorkType, command);
		}
		
		/**
         * ①<<ScreenQuery>> 勤務予定を登録する
         */
		export function regWorkSchedule(command: any): JQueryPromise<any> {
        	return nts.uk.request.ajax(servicePath.regWorkSchedule, command);
   		}

		// ver 4
		/**
         * ①<<ScreenQuery>> 作業選択準備情報を取得する
         */
		export function getTaskInfo(command: any): JQueryPromise<any> {
        	return nts.uk.request.ajax(servicePath.getTaskInfo, command);
   		}

		/**
         * ①<<ScreenQuery>> 作業パレットを取得する
         */
		export function getTaskPallet(command: any): JQueryPromise<any> {
        	return nts.uk.request.ajax(servicePath.getTaskPallet, command);
   		}

		/**
         * ①<<Query>> 作業パレット表示情報を取得する
         */
		export function getTaskPaletteDisplay(command: any): JQueryPromise<any> {
        	return nts.uk.request.ajax(servicePath.getTaskPaletteDisplay, command);
   		}
	
		/**
         * ①<<ScreenQuery>> 社員の出勤系をチェックする
         */
		export function checkEmpAttendance(command: any): JQueryPromise<any> {
        	return nts.uk.request.ajax(servicePath.checkEmpAttendance, command);
   		}

		/**
         * ①<<ScreenQuery>> 作業予定情報を取得する
         */
		export function getWorkScheduleInfor(command: any): JQueryPromise<any> {
        	return nts.uk.request.ajax(servicePath.getWorkScheduleInfor, command);
   		}

		/**
         * ①<<ScreenQuery>> 作業予定を登録する
         */
		export function addTaskWorkSchedule(command: any): JQueryPromise<any> {
        	return nts.uk.request.ajax(servicePath.addTaskWorkSchedule, command);
   		}

		/**
         * ①<<ScreenQuery>> 組織別スケジュール修正日付別の表示設定を登録する
         */
		export function addScheduleByDisplaySet(command: any): JQueryPromise<any> {
        	return nts.uk.request.ajax(servicePath.addScheduleByDisplaySet, command);
   		}
	}
}