module nts.uk.pr.view.ccg015.c {
    export module viewmodel {
        import aservice = nts.uk.com.view.ccg015.a.service;
        import aserviceDto = nts.uk.com.view.ccg015.a.service.model;
        import PlacementDto = service.PlacementDto;
        import StringDto = service.StringDto;
        export class ScreenModel {
            parentTopPageCode: KnockoutObservable<string>;
            parentTopPageName: KnockoutObservable<string>;
            parentLayoutId: KnockoutObservable<string>;
            newTopPageCode: KnockoutObservable<string>;
            newTopPageName: KnockoutObservable<string>;
            isDuplicateCode: KnockoutObservable<boolean>;
            check: KnockoutObservable<boolean>;
            constructor(topPageCode: string, topPageName: string, layoutId: string) {
                var self = this;
                self.parentTopPageCode = ko.observable(topPageCode);
                self.parentTopPageName = ko.observable(topPageName);
                self.parentLayoutId = ko.observable(layoutId);
                self.newTopPageCode = ko.observable("");
                self.newTopPageName = ko.observable("");
                self.isDuplicateCode = ko.observable(false);
                self.check = ko.observable(true);
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();

                dfd.resolve();
                return dfd.promise();
            }
            private copyTopPage() {
                var self = this;
                $('.nts-input').ntsEditor('validate');
                if ($('.nts-input').ntsError('hasError')) {

                }
                else {
                    nts.uk.ui.windows.setShared("codeOfNewTopPage", self.newTopPageCode());
                    aservice.loadTopPage().done(function(data: Array<aserviceDto.TopPageItemDto>) {
                        //TODO check input code exist
                        data.forEach(function(item, index) {
                            if (item.topPageCode == self.newTopPageCode()) {
                                self.isDuplicateCode(true);
                            }
                        });
                        //
                        if (self.isDuplicateCode()) {
                            //check overwrite data
                            if (self.check()) {
                                var topPageOverWrite: service.TopPageDto = {
                                    topPageCode: self.newTopPageCode(),
                                    topPageName: self.newTopPageName(),
                                    layoutId: self.parentLayoutId(),
                                    languageNumber: 0
                                };
                                service.copyTopPage(topPageOverWrite).done(function() {
                                    nts.uk.ui.windows.close();
                                    //                                nts.uk.ui.windows.setShared("codeOfNewTopPage", self.newTopPageCode());
                                });
                            }
                            else {
                                nts.uk.ui.dialog.alert("入力したコードは、既に登録されています。");    
                            }
                        }
                        else {
                            //check neu tp dc copy da dk layout thi cp layout
                            if (self.parentLayoutId()) {
                                service.copyLayout(self.parentLayoutId(), self.parentTopPageCode()).done(function(data: StringDto) {
                                    var topPage: aservice.model.TopPageDto = {
                                        topPageCode: self.newTopPageCode(),
                                        topPageName: self.newTopPageName(),
                                        languageNumber: 0,//jp
                                        layoutId: data.newLayoutId//get from parent
                                    };
                                    aservice.registerTopPage(topPage).done(function() {
                                        nts.uk.ui.windows.close();
                                        //                                    nts.uk.ui.windows.setShared("codeOfNewTopPage", self.newTopPageCode());
                                        nts.uk.ui.dialog.alert("コピーが完了しました。");
                                    });
                                });
                            }
                            else {
                                var topPage: aservice.model.TopPageDto = {
                                    topPageCode: self.newTopPageCode(),
                                    topPageName: self.newTopPageName(),
                                    languageNumber: 0,//jp
                                    layoutId: ""
                                };
                                aservice.registerTopPage(topPage).done(function() {
                                    nts.uk.ui.windows.close();
                                    //                                nts.uk.ui.windows.setShared("codeOfNewTopPage", self.newTopPageCode());
                                    nts.uk.ui.dialog.alert("コピーが完了しました。");
                                });
                            }
                        }
                    });
                }

            }
        }
    }
}