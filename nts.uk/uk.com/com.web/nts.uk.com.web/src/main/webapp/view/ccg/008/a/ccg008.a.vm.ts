module nts.uk.com.view.ccg008.a {
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            
            constructor() {
                var self = this;
                var title1 = nts.uk.resource.getText("CCG008_1");
                var title2 = nts.uk.resource.getText("CCG008_4");
                
                self.tabs = ko.observableArray([
                    {id: 'tab-1', title: title1, content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-2', title: title2, content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
                ]);
                self.selectedTab = ko.observable('tab-2');
                self.changePreviewIframe("8dc3b29c-e75b-4cd0-8263-bb4564aa4d4a");
            }
            
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
            
            //for frame review layout
            private changePreviewIframe(layoutID: string): void {
                $("#preview-iframe-1").attr("src", "/nts.uk.com.web/view/ccg/common/previewWidget/index.xhtml?layoutid=" + layoutID);
                $("#preview-iframe-2").attr("src", "/nts.uk.com.web/view/ccg/common/previewWidget/index.xhtml?layoutid=" + layoutID);
            }
            
            //for setting dialog
            openDialog(): void{
                var dialogTitle = nts.uk.resource.getText("CCG008_2");
                nts.uk.ui.windows.sub.modeless("/view/ccg/008/b/index.xhtml", {title: dialogTitle});
            }
        }   
    }
}