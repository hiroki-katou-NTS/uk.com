module nts.uk.pr.view.qpp018.c {
    export module viewmodel {
        export class ScreenModel {
            
            checklistPrintSettingModel: KnockoutObservable<ChecklistPrintSettingModel>;
            dirty: nts.uk.ui.DirtyChecker;

            constructor() {
                let self = this;
                self.checklistPrintSettingModel = ko.observable(new ChecklistPrintSettingModel());
                self.dirty = new nts.uk.ui.DirtyChecker(self.checklistPrintSettingModel);
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                self.loadCheckListPrintSetting().done(function() {
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            
            loadCheckListPrintSetting(): JQueryPromise<service.model.CheckListPrintSettingDto> {
                let self = this;
                let dfd = $.Deferred<service.model.CheckListPrintSettingDto>();
                service.findCheckListPrintSetting().done(function (data) {
                    self.initUI(data);
                    self.dirty.reset();
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                })
                return dfd.promise();
            }
            
            saveConfigSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                if (self.validate()) {
                    return;
                }
                let command = self.toJSObject();
                service.saveCheckListPrintSetting(command).done(function(res: any) {
                    dfd.resolve(res);
                }).fail(function(res) {
                    dfd.reject(res);
                }).always(function() {
                    self.closeDialog();
                });
                return dfd.promise();
            }
            
            validate(): boolean {
                let self = this;
                let isError: boolean = false;
                self.clearError();
                let checklistSetting = self.checklistPrintSettingModel();
                if (!checklistSetting.showDetail() && !checklistSetting.showOffice() && !checklistSetting.showTotal() 
                    && !checklistSetting.showDeliveryNoticeAmount()) {
                    isError = true;
                    $('#require-least-item').ntsError('set', 'You must choose at least check box item.');
                }
                return isError;
            }
            
            clearError(): void {
                $('#require-least-item').ntsError('clear');
            }
            
            closeDialog() {
                nts.uk.ui.windows.close();
            }
            
            initUI(res: service.model.CheckListPrintSettingDto): void {
                let self = this;
                let checklistSetting = self.checklistPrintSettingModel();
                if (res) {
                    checklistSetting.setData(res);
                } else {
                    checklistSetting.defaultValue();
                }
            }
            
            toJSObject(): any {
                let self = this;
                let checklistSetting = self.checklistPrintSettingModel();
                let command: any = {};
                let checkListPrintSetting: any = {};
                checkListPrintSetting.showCategoryInsuranceItem = checklistSetting.showCategoryInsuranceItem(); 
                checkListPrintSetting.showDetail = checklistSetting.showDetail();
                checkListPrintSetting.showOffice = checklistSetting.showOffice();
                checkListPrintSetting.showTotal = checklistSetting.showTotal();
                checkListPrintSetting.showDeliveryNoticeAmount = checklistSetting.showDeliveryNoticeAmount();
                command.checkListPrintSettingDto = checkListPrintSetting;
                return command;
            }
        }
        
        /**
         * The ChecklistPrintSettingModel
         */
        export class ChecklistPrintSettingModel {
            
            healthInsuranceItems: KnockoutObservableArray<any>;
            selectedHealthInsuranceItem: KnockoutObservable<string>;
            showCategoryInsuranceItem: KnockoutObservable<boolean>;
            showDeliveryNoticeAmount: KnockoutObservable<boolean>;
            showDetail: KnockoutObservable<boolean>;
            showOffice: KnockoutObservable<boolean>;
            showTotal: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.selectedHealthInsuranceItem = ko.observable("indicate");
                self.showCategoryInsuranceItem = ko.computed(function () {
                    if (self.selectedHealthInsuranceItem() == 'indicate') {
                        return true;
                    }
                    return false;
                }, self);
                self.showDetail = ko.observable(false);
                self.showOffice = ko.observable(false);
                self.showTotal = ko.observable(false);
                self.showDeliveryNoticeAmount = ko.observable(false);
                self.healthInsuranceItems = ko.observableArray([
                    {code: "indicate", name: "表示する"},
                    {code: "hide", name: "表示しない"}
                ]);
            }
            
            setData(dto: service.model.CheckListPrintSettingDto): void {
                let self = this;
                if (dto.showCategoryInsuranceItem) {
                    self.selectedHealthInsuranceItem("indicate");
                } else {
                    self.selectedHealthInsuranceItem("hide");
                }
                self.showDetail(dto.showDetail);
                self.showOffice(dto.showOffice);
                self.showTotal(dto.showTotal);
                self.showDeliveryNoticeAmount(dto.showDeliveryNoticeAmount);
            }
            
            defaultValue(): void {
                let self = this;
                self.selectedHealthInsuranceItem("indicate");
                self.showOffice(false);
                self.showTotal(false);
                self.showDeliveryNoticeAmount(false);
            }
        }
    }
}