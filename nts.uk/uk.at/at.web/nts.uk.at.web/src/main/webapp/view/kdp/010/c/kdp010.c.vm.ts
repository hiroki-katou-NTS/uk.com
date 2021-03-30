module nts.uk.at.view.kdp010.c {
    import getText = nts.uk.resource.getText;
	import error = nts.uk.ui.dialog.error;
    import block = nts.uk.ui.block;
	import ajax = nts.uk.request.ajax;

	module viewmodel {
		const paths: any = {
	        saveStampSetting: "at/record/stamp/management/saveStampSetting",
	        getStampSetting: "at/record/stamp/management/getStampSetting",
	        getStampPage: "at/record/stamp/management/getStampPageByCid"
	    }
	    export class ScreenModel {
	        // B4_2 - 打刻画面のサーバー時刻補正間隔 
	        correcValue: KnockoutObservable<number> = ko.observable(10);
	        // B5_2 - 打刻履歴表示方法
	        optionStamping: KnockoutObservableArray<any> = ko.observableArray([
	            { id: 0, name: getText("KDP010_19") },
	            { id: 1, name: getText("KDP010_20") },
	            { id: 2, name: getText("KDP010_21") }
	        ]);
	        selectedStamping: KnockoutObservable<number> = ko.observable(0);
	        // B6_3
	        letterColors: KnockoutObservable<string> = ko.observable("#ffffff");
	        // B6_5
	        backgroundColors: KnockoutObservable<string> = ko.observable("#0033cc");
	        // B10_2
	        optionHighlight: KnockoutObservableArray<any> = ko.observableArray([
	            { id: 1, name: getText("KDP010_39") },
	            { id: 0, name: getText("KDP010_40") }
	        ]);
	        selectedHighlight: KnockoutObservable<number> = ko.observable(0);
	        // B7_2
	        stampValue: KnockoutObservable<number> = ko.observable(3);
	        // List StampPageLayout (ページレイアウト設定)
	        lstStampPage: KnockoutObservable<any> = ko.observable({});
	        checkInUp: KnockoutObservable<boolean> = ko.observable(false);
	
	        start(): JQueryPromise<any> {
	            let self = this,dfd = $.Deferred();
	            block.grayout();
	            $.when(self.getStamp(), self.getData()).done(function() {
	                dfd.resolve();
	                block.clear();
	            });
	            return dfd.promise();
	        }
	
	        getData(): JQueryPromise<any> {
	            let self = this,dfd = $.Deferred();
	            ajax("at", paths.getStampSetting).done(function(totalTimeArr: any) {
	                if (totalTimeArr) {
	                    self.selectedHighlight(totalTimeArr.buttonEmphasisArt ? 1 : 0);
	                    self.selectedStamping(totalTimeArr.historyDisplayMethod);
	                    self.correcValue(totalTimeArr.correctionInterval);
	                    self.letterColors(totalTimeArr.textColor);
	                    self.backgroundColors(totalTimeArr.backGroundColor);
	                    self.stampValue(totalTimeArr.resultDisplayTime);
	                }
	                $('#correc-input').focus();
	            }).fail(function(error: any) {
	                error(error.message);
	            }).always(function () {
	                dfd.resolve();
	            });
	            return dfd.promise();
	        }
	
	        getStamp(): JQueryPromise<any> {
	            let self = this,dfd = $.Deferred();
	            ajax("at", paths.getStampPage).done(function(stampPage: any) {
	                if (stampPage && stampPage.length > 0)
	                    self.checkInUp(true);
	                else
	                    self.checkInUp(false);
	            }).fail(function(error: any) {
	                error(error.message);
	            }).always(function () {
	                dfd.resolve();
	            });
	            return dfd.promise();
	        }
	
	        save() {
	            let self = this;
	            if (nts.uk.ui.errors.hasError()) {
	                return;
	            }
	            block.invisible();
	            // Data from Screen 
	            let data = new StampSettingPersonDto({
	                buttonEmphasisArt: self.selectedHighlight(),
	                historyDisplayMethod: self.selectedStamping(),
	                correctionInterval: self.correcValue(),
	                textColor: self.letterColors(),
	                backGroundColor: self.backgroundColors(),
	                resultDisplayTime: self.stampValue()
	            });
	            ajax(paths.saveStampSetting, data).done(function() {
	                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
	            }).fail(function(res: any) {
	                error(res.message);
	            }).always(() => {
	                block.clear();
	            });
	        }
	
	        /**
	         * Open G dialog to set condition list.
	         */
	        openHDialog() {
	            let self = this;
	            nts.uk.ui.windows.setShared('STAMP_MEANS', 1);
	            nts.uk.ui.windows.sub.modal("/view/kdp/010/h/index.xhtml").onClosed(() => {
	                self.getStamp();   
	            });
	        }
	    }
	
	    export class StampSettingPersonDto {
	
	        /** 個人利用の打刻設定.打刻ボタンを抑制する */
	        buttonEmphasisArt: boolean;
	        /** 個人利用の打刻設定.打刻画面の表示設定.打刻履歴表示方法 */
	        historyDisplayMethod: number;
	        /** 個人利用の打刻設定.打刻画面の表示設定.打刻画面のサーバー時刻補正間隔 */
	        correctionInterval: number;
	        /** 個人利用の打刻設定.打刻画面の表示設定.打刻画面の日時の色設定.文字色 */
	        textColor: string;
	        /** 個人利用の打刻設定.打刻画面の表示設定.打刻画面の日時の色設定.背景色  */
	        backGroundColor: string;
	        /** 個人利用の打刻設定.打刻画面の表示設定.打刻結果自動閉じる時間 */
	        resultDisplayTime: number;
	
	        constructor(param: IStampSettingPersonDto) {
	            this.buttonEmphasisArt = param.buttonEmphasisArt;
	            this.historyDisplayMethod = param.historyDisplayMethod;
	            this.correctionInterval = param.correctionInterval;
	            this.textColor = param.textColor;
	            this.backGroundColor = param.backGroundColor;
	            this.resultDisplayTime = param.resultDisplayTime;
	        }
	    }
	
	    interface IStampSettingPersonDto {
	        buttonEmphasisArt: boolean;
	        historyDisplayMethod: number;
	        correctionInterval: number;
	        textColor: string;
	        backGroundColor: string;
	        resultDisplayTime: number;
	    }
	}
	__viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}