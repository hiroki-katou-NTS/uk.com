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
	        letterColors: KnockoutObservable<string> = ko.observable("#7F7F7F");
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
					$(document).ready(function() {
	                    $('#correc-input').focus();
	                });
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
	                    self.stampValue(totalTimeArr.resultDisplayTime);
	                }
	                
	            }).fail(function(err: any) {
	                error({ messageId: err.messageId });
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
	            }).fail(function(err: any) {
	                error({ messageId: err.messageId });
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
	            let data = {
	                buttonEmphasisArt: self.selectedHighlight(),
	                historyDisplayMethod: self.selectedStamping(),
					stampingScreenSet: {
						serverCorrectionInterval: self.correcValue(),
						settingDateTimeColor: {
							textColor: self.letterColors()
						},
						resultDisplayTime: self.stampValue()
					}
	            };
	            ajax(paths.saveStampSetting, data).done(function() {
	                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
	            }).fail(function(res: any) {
	                error({ messageId: res.messageId });
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
	    
	}
	__viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}