module nts.uk.at.view.kaf022.s.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};
    import isNullOrEmpty = nts.uk.text.isNullOrEmpty;
    import dialogInfo = nts.uk.ui.dialog.info;
    import dialogConfirm =  nts.uk.ui.dialog.confirm;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        listReason: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedReason: KnockoutObservable<ApplicationReason> = ko.observable(null);
        columns: KnockoutObservableArray<any>;
        listAppType: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedAppType: KnockoutObservable<number> = ko.observable(0);
        selectedOrder: KnockoutObservable<string> = ko.observable("");
        listAppEnum: Array<number> = [];
        // bien theo doi update mode hay new mode
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this;
            
            self.initData();
            
            // subscribe combobox for grid list change
            self.selectedAppType.subscribe((value) => {
                if(!_.isNil(value)){
                    nts.uk.ui.errors.clearAll();
                    self.getData(value);                    
                }
            });

            // subscribe a item in the list
            self.selectedOrder.subscribe((value) => {
                if (!isNullOrEmpty(value)) {
                    nts.uk.ui.errors.clearAll();
                    let reason = _.find(self.listReason(), (o) => { return o.keyToOrder == value });
                    if (!isNullOrEmpty(reason)) {
                        self.selectedReason(new ApplicationReason(reason));
                        self.isUpdate(true);
                    }
                }else{
                        self.createNew();
                    }
            });
        }
        
        initData(){
            let self = this;
            
            self.columns = ko.observableArray([
                { headerText: getText("KAF022_443"), key: 'keyToOrder', width: 250, hidden: true },
                { headerText: getText("KAF022_441"), key: 'defaultFlg', width: 100, formatter: makeIcon },
                { headerText: getText("KAF022_443"), key: 'reasonTemp', width: 250, formatter: _.escape }

            ]);
            self.listAppEnum.push(ApplicationType.OVER_TIME_APPLICATION.valueOf());
            self.listAppEnum.push(ApplicationType.ABSENCE_APPLICATION.valueOf());
            self.listAppEnum.push(ApplicationType.WORK_CHANGE_APPLICATION.valueOf());
            self.listAppEnum.push(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION.valueOf());
            self.listAppEnum.push(ApplicationType.BREAK_TIME_APPLICATION.valueOf());
            self.listAppEnum.push(ApplicationType.COMPLEMENT_LEAVE_APPLICATION.valueOf());
            // list enum apptype get to combobox
            let listApplicationType = __viewContext.enums.ApplicationType;
            _.forEach(listApplicationType, (obj) => {
                if (self.listAppEnum.indexOf(obj.value) > -1) {
                    self.listAppType.push(new ItemModel(obj.value, obj.name))
                }
            });    
        
        }

        /** get data to list **/
        getData(appType: number): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.grayout();
            service.getReason(appType).done((lstData: Array<IApplicationReason>) => {
                if (lstData.length > 0) {
                    self.listReason(lstData);
                    let listOrder = _.orderBy(_.map(lstData, (o) => {
                        o.keyToOrder = o.companyId + "-" + o.dispOrder + "-" + o.reasonID;
                        return o;
                    }), ['dispOrder'], ['asc']);
                    self.listReason(listOrder);
                    self.selectedOrder(listOrder[0].keyToOrder);
                    dfd.resolve();
                }else{
                    self.listReason([]);
                    self.createNew();
                    dfd.resolve();
                }
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            }).always(() => {
               nts.uk.ui.block.clear(); 
            });
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            nts.uk.ui.block.grayout();
            let self = this,
                dfd = $.Deferred();
            self.listReason.removeAll();
            self.getData(self.selectedAppType()).done(function() {
                if(_.size(self.listReason())){
                    self.isUpdate(true);
                    self.selectedOrder(self.listReason()[0].keyToOrder);
                }
                dfd.resolve();
            });
            nts.uk.ui.block.clear;
            return dfd.promise();
        }

        // new button
        createNew() {
            let self = this,
                data = {
                    /** 理由ID */
                    reasonID: " ",
                    /** 表示順 */
                    dispOrder: 0,
                    /** 定型理由*/
                    reasonTemp: '',
                    /** 既定*/
                    defaultFlg: 0,
                    keyToOrder : "0"
                }
            self.selectedReason(new ApplicationReason(data));
            self.selectedOrder(null);
            self.isUpdate(false);
            nts.uk.ui.errors.clearAll();
        }

        // close dialog
        closeDialog() {
            nts.uk.ui.windows.close();
        }

        update(){
            let self = this;
            let order = null;
            let dfd = $.Deferred();
            // sắp xếp các phần tử trong list
            for (let i = 0; i < self.listReason().length; i++) {
                self.listReason()[i].dispOrder = i;
            }
            // update item to list  
            // tìm item đang được chọn
            if (self.listReason().length > 0) {
                order = _.find(self.listReason(), function(d) {  
                    return d.reasonID === self.selectedReason().reasonID;
                });
            }
            let cmd = {
                appType: self.selectedAppType(),
                reasonID: self.selectedReason().reasonID,
                dispOrder: order ? order.dispOrder : 0,
                reasonTemp: self.selectedReason().reasonTemp(),
                defaultFlg: self.selectedReason().defaultFlg() ? 1 : 0,
                keyToOrder : self.selectedReason().keyToOrder
            };
            if(self.listReason().length > 1){
                // lấy list các phần tử ngoại trừ phần tử đang select
                let listUpdate = { listCommand: self.listReason() };
                listUpdate.listCommand = _.remove(listUpdate.listCommand, function(n) {
                    return n.dispOrder != order.dispOrder;
                });
                // lấy list các giá trị default của các phần tử ngoại trừ phần tử hiện tại
                let listDefault = _.map(listUpdate.listCommand, 'defaultFlg');
                // nếu phần tử hiện tại đã được check và trong list default cũng tồn tại 1 giá trị được check thì phải reset giá trị default của tất cả các phần tử trong list về 0
                if (cmd.defaultFlg == 1 && listDefault.indexOf(1) > -1) {
                    _.forEach(listUpdate.listCommand, (obj) => {
                        obj.defaultFlg = 0;
                    });
                }
                listUpdate.listCommand.push(cmd);
                let code = cmd.dispOrder;
                nts.uk.ui.block.grayout();
                if (nts.uk.ui.errors.hasError() === false) {
                    service.update(listUpdate).done(function() {
                        self.startPage().done(function() {
                            let reason = _.find(self.listReason(), (o) => { return o.reasonID === cmd.reasonID });
                            if (!isNullOrEmpty(reason)) {
                                self.selectedOrder(reason.companyId + "-" + reason.dispOrder + "-" + reason.reasonID);
                            }
                            dialogInfo({ messageId: "Msg_15" });
                        });
                    }).fail(function(res) {
                        nts.uk.ui.block.clear();
                        alert(res.message);
                        dfd.reject();
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }
            }else{
                let listUpdate = { listCommand:[] };
                listUpdate.listCommand.push(cmd);
                let code = cmd.dispOrder;
                nts.uk.ui.block.grayout();
                if (nts.uk.ui.errors.hasError() === false) {
                    service.update(listUpdate).done(function() {
                        self.startPage().done(function() {
                            let reason = _.find(self.listReason(), (o) => { return o.reasonID === cmd.reasonID });
                            if (!isNullOrEmpty(reason)) {
                                self.selectedOrder(reason.companyId + "-" + reason.dispOrder + "-" + reason.reasonID);
                            }
                            dialogInfo({ messageId: "Msg_15" });
                        });
                    }).fail(function(res) {
                        nts.uk.ui.block.clear();
                        alert(res.message);
                        dfd.reject();
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }
            }
        }
        
        /** update or insert data when click button register **/
        register() {
            let self = this;
            let dfd = $.Deferred();
            // sắp xếp các phần tử trong list
            for (let i = 0; i < self.listReason().length; i++) {
                self.listReason()[i].dispOrder = i;
            }
            _.defer(() => {
                $('#reason-temp').trigger("validate");
                if (nts.uk.ui.errors.hasError() === false) {
                    // update item to list  
                    // tìm item đang được chọn
                    let order = _.find(self.listReason(), function(d) {
                        return d.reasonID === self.selectedReason().reasonID;
                    });
                    let key = self.selectedOrder(); 
                    if (self.isUpdate() == true) {
                        self.update();
                        self.selectedOrder(key);
                    }
                    else {
                        let code = self.listReason().length;
                        let obj = {
                            appType: self.selectedAppType(),
                            reasonID: '',
                            dispOrder: code,
                            reasonTemp: self.selectedReason().reasonTemp(),
                            defaultFlg: self.selectedReason().defaultFlg() ? 1 : 0,
                        }
                        // Nếu item cần insert có default = 1
                        if(obj.defaultFlg == 1){
                            nts.uk.ui.block.grayout();
                            let listUpdate = { listCommand: self.listReason() };
                            _.each(listUpdate.listCommand, (obj) => {
                                obj.defaultFlg = 0;
                            });
                            // update list ban đầu
                            service.update(listUpdate).done(function() {
                                // insert item to list
                                service.insert(obj).done(function(result) {
                                    self.startPage().done(function() {
                                        dialogInfo({ messageId: "Msg_15" });
                                        let reason = _.find(self.listReason(), (o) => { return o.reasonId = obj.reasonID});
                                        if (!isNullOrEmpty(reason)) {
                                            self.selectedOrder(result);
                                        }
                                    });
                                }).fail(function(res) {
                                    dfd.reject();
                                    alert(error.message);
                                }).always(() => {
                                    nts.uk.ui.block.clear();
                                });
                            })
                        }else{
                            // insert item to list
                            let apptype = self.selectedAppType();
                            service.insert(obj).done(function(result) {
                                self.getData(apptype).done(function() {
                                    dialogInfo({ messageId: "Msg_15" });
                                    let reason = _.find(self.listReason(), (o) => { return o.reasonId = obj.reasonID });
                                    if (!isNullOrEmpty(result)) {
                                        self.selectedOrder(result);
                                    }
                                });
                            }).fail(function(res) {
                                dfd.reject();
                                alert(error.message);
                            }).always(() => {
                                nts.uk.ui.block.clear();
                            });
                        }
                    }
                }
            });
        }

        /** remove item from list **/
        remove() {
            let self = this;
            let count = 0;
            let appTypeNow = self.selectedAppType();
            // tìm vị trí của item định xóa
            for (let i = 0; i <= self.listReason().length; i++) {
                if (self.listReason()[i].keyToOrder == self.selectedOrder()) {
                    count = i;
                    break;
                }
            }
               dialogConfirm({ messageId: "Msg_18" }).ifYes(() => {
                let cmd = {
                    appType: self.selectedAppType(),
                    reasonID: self.selectedReason().reasonID,    
                }
                service.deleteReason(cmd).done(function() {
                    // lấy list các phần tử còn lại ngoại trừ phần tử bị delete
                    let listNotDel = _.remove(self.listReason(), function(n) {
                        return n.dispOrder != self.selectedReason().dispOrder();
                    });
                    
                    let listUpdate = { listCommand: listNotDel };
                    for(let a = 0; a<listUpdate.listCommand.length; a++){
                        listUpdate.listCommand[a].dispOrder = a;
                    }
                    
                    // update list ban đầu
                    service.update(listUpdate).done(function() {
                        // insert item to list
                        self.getData(appTypeNow).done(function() {
                            if (self.listReason().length > 0) {
                                // delete the last item
                                if (count == ((self.listReason().length))) {
                                    self.selectedOrder(self.listReason()[count - 1].keyToOrder);
                                    dialogInfo({ messageId: "Msg_16" });
                                    return;
                                }
                                // delete the first item
                                if (count == 0) {
                                    self.selectedOrder(self.listReason()[0].keyToOrder);
                                    dialogInfo({ messageId: "Msg_16" });
                                    return;
                                }
                                // delete item at mediate list 
                                else if (count > 0 && count < self.listReason().length) {
                                    self.selectedOrder(self.listReason()[count].keyToOrder);
                                    dialogInfo({ messageId: "Msg_16" });
                                    return;
                                }
                            }
                            else {
                                self.selectedOrder(undefined);
                            }
                            dialogInfo({ messageId: "Msg_16" });
                            self.selectedOrder(code);
                        }).fail(function(res) {
                            dfd.reject();
                            alert(res.message);
                        }).always(() => {
                            nts.uk.ui.block.clear();
                        });
                    }).always(() => {
                            nts.uk.ui.block.clear();
                        });
                });
            }).ifNo(() => {
            })
       }
            
    }

    export interface IApplicationReason {
        /** 理由ID */
        reasonID: string;
        /** 表示順 */
        dispOrder: number;
        /** 定型理由*/
        reasonTemp: string;
        /** 既定*/
        defaultFlg: number;
        
        appType : number;
        
        companyId: string;

        keyToOrder: string;
    }

    export class ApplicationReason {
        /** 理由ID */
        reasonID: string;
        /** 表示順 */
        dispOrder: KnockoutObservable<number>;
        /** 定型理由*/
        reasonTemp: KnockoutObservable<string>;
        /** 既定*/
        defaultFlg: KnockoutObservable<number>;
        
        keyToOrder: KnockoutObservable<string>;
        
        icon: string;
        constructor(param: IApplicationReason) {
            let self = this;
            self.reasonID = param.reasonID;
            self.dispOrder = ko.observable(param.dispOrder);
            self.reasonTemp = ko.observable(param.reasonTemp);
            self.defaultFlg = ko.observable(param.defaultFlg);
            self.keyToOrder = ko.observable(param.keyToOrder);

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

    export enum ApplicationType {
        /** 残業申請*/
        OVER_TIME_APPLICATION = 0,
        /** 休暇申請*/
        ABSENCE_APPLICATION = 1,
        /** 勤務変更申請*/
        WORK_CHANGE_APPLICATION = 2,
        /** 直行直帰申請*/
        GO_RETURN_DIRECTLY_APPLICATION = 4,
        /** 休出時間申請*/
        BREAK_TIME_APPLICATION = 6,
        /** 振休振出申請*/
        COMPLEMENT_LEAVE_APPLICATION = 10,
    }

    function makeIcon(value) {
        if (value == 1) {
            return '<i class="icon icon-dot"></i>';
        }
        return '';
    }
}




