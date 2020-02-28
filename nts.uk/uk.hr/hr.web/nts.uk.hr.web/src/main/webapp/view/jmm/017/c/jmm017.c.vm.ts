module nts.uk.com.view.jmm017.c.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import error = nts.uk.ui.dialog.error;
    import getText = nts.uk.resource.getText;
    
    export class ScreenModel {
        
        screenName: string;
        usageFlgByScreen: KnockoutObservable<boolean>;
        guideMsgText: KnockoutObservable<string>;
        guideMsg: any;
        
        constructor() {
            let self = this;
            self.guideMsg = getShared("guideMsg");
            self.screenName = self.guideMsg.categoryName + ' > ' + self.guideMsg.eventName + ' > ' + self.guideMsg.programName + ' > '+ self.guideMsg.screenName;
            self.usageFlgByScreen = ko.observable(self.guideMsg.usageFlgByScreen === '使用する');
            self.guideMsgText = ko.observable(self.guideMsg.guideMsg);
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            block.grayout();
            var dfd = $.Deferred();
            new service.getGuidance().done(function(data: any) {
                __viewContext.primitiveValueConstraints.String_Any_100.maxLength = (data.guideMsgMaxNum) * 2;
            }).always(function() {
                dfd.resolve();
                block.clear();
            });
            return dfd.promise();
        }

        decide() {
            block.grayout();
            let self = this;
            self.guideMsg.usageFlgByScreen = self.usageFlgByScreen()?'使用する':'使用しない';
            self.guideMsg.guideMsg = self.guideMsgText();
            new service.updateGuideMsg(self.guideMsg).done(function() {
                nts.uk.ui.windows.setShared("guideMsgB", self.guideMsg);
                nts.uk.ui.windows.close();
            }).always(function() {
                block.clear();
            });
            
        }

        cancel(): any {
            nts.uk.ui.windows.close();
        }

    }
}