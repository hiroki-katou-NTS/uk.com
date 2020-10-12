module nts.uk.at.view.kmk008.h {
    import service = nts.uk.at.view.kmk008.h.service;
    
    export module viewmodel {        
        export class ScreenModel {
            useEmployment: KnockoutObservable<boolean>;
            useWorkPlace: KnockoutObservable<boolean>;
            useClasss: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.useEmployment = ko.observable(true);
                self.useWorkPlace = ko.observable(true);
                self.useClasss = ko.observable(true);
            }

            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.getData().done(function(item) {
                    if (item) {
                        self.useEmployment(item.employmentUseAtr);
                        self.useWorkPlace(item.workPlaceUseAtr);
                        self.useClasss(item.classificationUseAtr);
                    } else {
                        self.useEmployment(true);
                        self.useWorkPlace(true);
                        self.useClasss(true);
                    }
                    dfd.resolve();
                });


                return dfd.promise();
            }

            submitAndCloseDialog(): void {
                var self = this;
                
                service.getData().done(function(item) {                    
                    if (item) {
                        service.updateData({
                            employmentUseAtr: self.useEmployment() ? 1 : 0,// 雇用使用区分
                            workPlaceUseAtr: self.useWorkPlace() ? 1 : 0,// 職場使用区分
                            classificationUseAtr: self.useClasss() ? 1 : 0 // 分類使用区分
                        }).done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                self.closeDialog();
                            });    
                        });
                    } else {
                        service.insertData({
                            employmentUseAtr: self.useEmployment() ? 1 : 0,// 雇用使用区分
                            workPlaceUseAtr: self.useWorkPlace() ? 1 : 0,// 職場使用区分
                            classificationUseAtr: self.useClasss() ? 1 : 0// 分類使用区分
                        }).done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                self.closeDialog();
                            });    
                        });
                    }
                })
                //self.closeDialog();
            }

            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
    }

}
