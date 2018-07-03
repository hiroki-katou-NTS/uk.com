module nts.uk.at.view.kaf022.s.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};
    
    export class ScreenModel {
        listReason: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedReason: KnockoutObservable<ApplicationReason> = ko.observable(null);
        columns: KnockoutObservableArray<any>;
        listAppType: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedAppType: KnockoutObservable<number> = ko.observable(0);
        selectedOrder: KnockoutObservable<number> = ko.observable(null); 
        
        constructor() {
            let self = this;
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KAF022_441"), key: 'displayOrder', width: 100 },
                { headerText: nts.uk.resource.getText("KAF022_443"), key: 'reasonTemp', width: 250 }
            ]);
            
            // list enum apptype get to combobox
            let listApplicationType = __viewContext.enums.ApplicationType;
            _.forEach(listApplicationType, (obj) => {
                if([0, 1, 2, 4, 6, 10].indexOf(obj.value) > -1){
                    self.listAppType.push(new ItemModel(obj.value, obj.name))
                }
            });
            
            // subscribe combobox for grid list change
            self.selectedAppType.subscribe((value) => {
                self.getData(value);
            });
            
            // subscribe a item in the list
            self.selectedOrder.subscribe((value) => {
                self.selectedReason(self.listReason()[value]);
            });
        }

        /** get data to list **/
        getData(appType: number): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getReason(appType).done((lstData: Array<IApplicationReason>) => {
                let listOrder = _.orderBy(lstData, ['displayOrder'], ['asc']);
                self.listReason(listOrder);
                self.selectedReason(self.listReason()[0]);
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            })
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.getData(self.selectedAppType()).done(function() {
                dfd.resolve();    
            });
            return dfd.promise();
        }
        
        checkUpdateMode() {
            let self = this;
        }

        /** update or insert data when click button register **/
        register() {
            let self = this;
//            let code = "";
//            $("#inpPattern").trigger("validate");
//            let updateOption = new BusinessType(self.selectedCode(), self.selectedName());
//            code = self.codeObject();
//            _.defer(() => {
//                if (nts.uk.ui.errors.hasError() === false) {
//                    // update item to list  
//                    if (self.checkUpdate() == true) {
//                        service.update(updateOption).done(function() {
//                            self.getData().done(function() {
//                                self.selectedCode(code);
//                                self.checkUpdateMode();
//                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
//                            });
//                        });
//                    }
//                    else {
//                        code = self.codeObject();
//                        self.selectedOption(null);
//                        let obj = new BusinessType(self.codeObject(), self.selectedName());
//                        // insert item to list
//                        service.insert(obj).done(function() {
//                            self.getData().done(function() {
//                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
//                                self.selectedCode(code);
//                                self.checkUpdateMode();
//                            });
//                        }).fail(function(res) {
//                            $('#inpCode').ntsError('set', res);
//                        });
//                    }
//                }
//            });
//            $("#inpPattern").focus();
        }
        //  new mode 
        newMode() {
            let self = this;
//            self.check(true);
//            self.checkUpdate(false);
//            self.selectedCode("");
//            self.codeObject("");
//            self.selectedName("");
//            $("#inpCode").focus();
//            self.checkUpdateMode();
        }

        /** remove item from list **/
        remove() {
            let self = this;
//            let count = 0;
//            for (let i = 0; i <= self.lstBusinessType().length; i++) {
//                if (self.lstBusinessType()[i].businessTypeCode == self.selectedCode()) {
//                    count = i;
//                    break;
//                }
//            }
//            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
//                service.remove(self.selectedOption()).done(function() {
//                    self.getData().done(function() {
//                        // if number of item from list after delete == 0 
//                        if (self.lstBusinessType().length == 0) {
//                            self.newMode();
//                            return;
//                        }
//                        // delete the last item
//                        if (count == ((self.lstBusinessType().length))) {
//                            self.selectedCode(self.lstBusinessType()[count - 1].businessTypeCode);
//                            return;
//                        }
//                        // delete the first item
//                        if (count == 0) {
//                            self.selectedCode(self.lstBusinessType()[0].businessTypeCode);
//                            return;
//                        }
//                        // delete item at mediate list 
//                        else if (count > 0 && count < self.lstBusinessType().length) {
//                            self.selectedCode(self.lstBusinessType()[count].businessTypeCode);
//                            return;
//                        }
//                    })
//                })
//                nts.uk.ui.dialog.info({ messageId: "Msg_16" });
//                self.checkUpdateMode();
//            }).ifNo(() => {
//            });
//            $("#inpPattern").focus();
        }
    }
    
    export interface IApplicationReason{
        /** 理由ID */
        reasonID: string;
        /** 表示順 */
        displayOrder: number;
        /** 定型理由*/
        reasonTemp: string;
        /** 既定*/
        defaultFlg: number;    
    }
    
    export class ApplicationReason {
        /** 理由ID */
        reasonID: string;
        /** 表示順 */
        displayOrder: KnockoutObservable<number>;
        /** 定型理由*/
        reasonTemp: KnockoutObservable<string>;
        /** 既定*/
        defaultFlg: KnockoutObservable<number>;
        constructor(param: IApplicationReason) {
            let self = this;
            self.reasonID = param.reasonID;
            self.displayOrder = ko.observable(param.displayOrder);
            self.reasonTemp = ko.observable(param.reasonTemp);
            self.defaultFlg = ko.observable(param.defaultFlg);
        }
    }
    
    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}




