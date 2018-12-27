module nts.uk.at.view.kmk013.j {
    
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            transAttendMethod: KnockoutObservableArray<ItemModel>;
            selectedItem: KnockoutObservable<number>; // J2_4
            
            lstWorkTypeGivenDays: KnockoutObservableArray<WorkType>;
            lstWorkTypeAttendanceDays: KnockoutObservableArray<WorkType>;
            
            workTypeList: KnockoutObservableArray<any>;
            currentItem: KnockoutObservable<WorktypeDisplayDto>;
            currentItemAttendance: KnockoutObservable<WorktypeDisplayDto>;
            workTypeNames: KnockoutObservable<string>;
            workTypeNamesAttendance: KnockoutObservable<string>;
            items: KnockoutObservableArray<WorktypeDisplayDto>;
            useAtr: KnockoutObservable<number>;
            
            constructor() {
                var self = this;
                self.transAttendMethod = ko.observableArray<ItemModel> ([
                    new ItemModel(0, nts.uk.resource.getText("KMK013_311")),
                    new ItemModel(1, nts.uk.resource.getText("KMK013_312"))
                ]);
                self.selectedItem = ko.observable(0);
                self.lstWorkTypeGivenDays = ko.observableArray([]);
                self.lstWorkTypeAttendanceDays = ko.observableArray([]);
                self.items = ko.observableArray([]);
                self.useAtr = ko.observable(0);
                self.workTypeList = ko.observableArray([]);
                self.currentItem = ko.observable(new WorktypeDisplayDto({}));
                self.currentItemAttendance = ko.observable(new WorktypeDisplayDto({}));
                self.workTypeNames = ko.observable("");
                self.workTypeNamesAttendance = ko.observable("");
                self.workTypeNames.subscribe(function(data){
                    // Set tooltip
                    $('#itemname_absenceDay').text(data);
                });
                self.workTypeNamesAttendance.subscribe(function(data) {
                    // Set tooltip
                    $('#itemname_attendanceDay').text(data);
                });
                
            }
            
            // Start Page
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                self.getWorkTypeList().done(function() {
                    self.findAll();
                    service.loadAllSetting().done(function(data) {
                        if (data) {
                            self.selectedItem(data.attendanceItemCountingMethod);
                        }
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                    
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                
                return dfd.promise();
            }
            
            openKDL002_givenDays(): void {
                var self = this;
                var workTypeCodes = _.map(self.workTypeList(), function(item: IWorkTypeModal) { return item.workTypeCode });
                nts.uk.ui.windows.setShared('KDL002_Multiple', true);
                nts.uk.ui.windows.setShared('KDL002_AllItemObj', workTypeCodes);
                nts.uk.ui.windows.setShared('KDL002_SelectedItemId', self.currentItem().workTypeList(), true);
                
                nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.block.clear();
                    var data = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                    var name = [];
                    _.forEach(data, function(item: IWorkTypeModal) {
                        name.push(item.name);
                    });
                    self.workTypeNames(name.join(" + "));
    
                    var workTypeCodes = _.map(data, function(item: any) { return item.code; });
                    self.currentItem().workTypeList(workTypeCodes);
                });
            }
            
            openKDL002_attendanceDays(): void {
                var self = this;
                var workTypeCodes = _.map(self.workTypeList(), function(item: IWorkTypeModal) { return item.workTypeCode });
                nts.uk.ui.windows.setShared('KDL002_Multiple', true);
                nts.uk.ui.windows.setShared('KDL002_AllItemObj', workTypeCodes);
                nts.uk.ui.windows.setShared('KDL002_SelectedItemId', self.currentItemAttendance().workTypeList(), true);
                
                nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.block.clear();
                    var data = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                    var name = [];
                    _.forEach(data, function(item: IWorkTypeModal) {
                        name.push(item.name);
                    });
                    self.workTypeNamesAttendance(name.join(" + "));
    
                    var workTypeCodes = _.map(data, function(item: any) { return item.code; });
                    self.currentItemAttendance().workTypeList(workTypeCodes);     
                });
            }
            
            getWorkTypeList() {
                var self = this;
                var dfd = $.Deferred();
                service.findWorkType().done(function(res) {
                    self.workTypeList.removeAll();
                    _.forEach(res, function(item) {
                        self.workTypeList.push({
                            workTypeCode: item.workTypeCode,
                            name: item.name,
                            memo: item.memo
                        });
                    });
                    dfd.resolve();
                }).fail(function(error) {
                    alert(error.message);
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            
            getNames(data: Array<IWorkTypeModal>, workTypeCodesSelected: Array<string>, control: KnockoutObservable<string>) {
                var name = [];
                var self = this;
                if (workTypeCodesSelected && workTypeCodesSelected.length > 0) {
                    _.forEach(data, function(item: IWorkTypeModal) {
                        _.forEach(workTypeCodesSelected, function(items: any) {
                            if (_.includes(items, item)) {
                                service.findWorkTypeName(items).done(function(workType) {
                                    name.push(workType.workTypeName);
                                    control(name.join(" + "));
                                });
                            }
                        });
                    });
                }
            }
            
            findAll(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.items.removeAll();
                
                service.findPayItem().done(function(data) {
                    var attPayItems = data.payAttendanceDays;
                    var absPayItems = data.payAbsenceDays;
                    _.each(absPayItems, (items: any) => {
                        self.currentItem().workTypeList().push(items);
                    });
                    self.getNames(self.currentItem().workTypeList(), absPayItems, self.workTypeNames);
                    _.each(attPayItems, (items: any) => {
                        self.currentItemAttendance().workTypeList().push(items);
                    });
                    self.getNames(self.currentItemAttendance().workTypeList(), attPayItems, self.workTypeNamesAttendance);
                });
                
                return dfd.promise();
            }
            
            // Save data
            saveData(): void {
                let self = this,
                    data: any = {};
                blockUI.grayout();
                data.attendanceItemCountingMethod = self.selectedItem();
                var workType = ko.toJS(self.currentItem());
                var workTypeAttendance = ko.toJS(self.currentItemAttendance());
                var workTypeData: any = {
                    payAttendanceDays: workTypeAttendance.workTypeList,
                    payAbsenceDays: workType.workTypeList
                };
                $.when(service.registerVertical(data),service.registerPayItem(workTypeData)).done(()=>{
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                $('#trans-attend').focus();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(() => {
                    blockUI.clear();
                });
            }
        }
        
        
        // Class ItemModel
        class ItemModel {
            id: number;
            name: string;
            constructor(id: number, name: string) {
                this.id = id;
                this.name = name;
            }
        }
        
        // Class work type
        class WorkType {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
    
    export class WorkTypeModal {
        workTypeCode: string;
        name: string;
        memo: string;
        constructor(param: IWorkTypeModal) {
            this.workTypeCode = param.workTypeCode;
            this.name = param.name;
            this.memo = param.memo;
        }
    }

    export interface IWorkTypeModal {
        workTypeCode: string;
        name: string;
        memo: string;
    }
    
    export class WorktypeDisplayDto {
        useAtr: KnockoutObservable<number>;
        workTypeList: KnockoutObservableArray<WorktypeDisplaySetDto>;
        constructor(param: IWorktypeDisplayDto) {
            this.useAtr = ko.observable(param.useAtr || 0);
            this.workTypeList = ko.observableArray(param.workTypeList || null);
        }
    }

    export interface IWorktypeDisplayDto {
        useAtr?: number;
        workTypeList?: Array<WorktypeDisplaySetDto>;
    }


    export class WorktypeDisplaySetDto {
        workTypeCode: string;
        constructor(param: IWorktypeDisplaySetDto) {
            this.workTypeCode = param.workTypeCode;
        }
    }

    export interface IWorktypeDisplaySetDto {
        workTypeCode?: string;
    }
}