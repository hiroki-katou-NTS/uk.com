module nts.uk.at.view.ksm011.d.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    import viewmodel = nts.uk.com.view.ccg025.a.component.viewmodel;
    import ccg026 = nts.uk.com.view.ccg026;
    import viewmodel026 = nts.uk.com.view.ccg026.component.viewmodel;

    export class ScreenModel {
        component: ccg.component.viewmodel.ComponentModel;
        component026: ccg026.component.viewmodel.ComponentModel;
        componentWorkplace: ccg026.component.viewmodel.ComponentModel;
        componentEmployee: ccg026.component.viewmodel.ComponentModel;
        componentDate: ccg026.component.viewmodel.ComponentModel;
        componentShift: ccg026.component.viewmodel.ComponentModel;
        currentCodeItem: KnockoutObservable<any>;
        listPermissionCommon: KnockoutObservableArray<any>;
        listPermissionWorkplace: KnockoutObservableArray<any>;
        listPermissionEmployee: KnockoutObservableArray<any>;
        listPermissionDate: KnockoutObservableArray<any>;
        listPermissionShift: KnockoutObservableArray<any>;
        listDeadline: KnockoutObservableArray<any>;
        itemBaseAtr: KnockoutObservableArray<any>;
        outputAtr: KnockoutObservableArray<any>;
        itemCorrectDeadlineAtr: KnockoutObservableArray<any>;
        useCls: KnockoutObservable<number>;
        currentItem: KnockoutObservable<PermissonDto>;
        items: KnockoutObservableArray<PermissonDto>;
        correctDeadline: KnockoutObservable<number>;
        roleId: KnockoutObservable<any>;
        constructor() {
            let self = this;
            let dfd = $.Deferred();
            self.roleId = ko.observable();
            self.currentCodeItem = ko.observable();

            self.initComponent();

            self.listPermissionCommon = ko.observableArray([]);
            self.listPermissionWorkplace = ko.observableArray([]);
            self.listPermissionEmployee = ko.observableArray([]);
            self.listPermissionDate = ko.observableArray([]);
            self.listPermissionShift = ko.observableArray([]);
            self.listDeadline = ko.observableArray([]);
            self.outputAtr = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);
            let arr: any = [];
            for (let i = 1; i <= 31; i++) {
                arr.push({ code: i, name: i + '日' });
            }
            self.itemCorrectDeadlineAtr = ko.observableArray(arr);
            self.currentItem = ko.observable(new PermissonDto({}));
            self.items = ko.observableArray([

            ]);
            self.useCls = ko.observable(0);
            self.correctDeadline = ko.observable(0);
            self.component.currentCode.subscribe(function(codeChanged) {
                self.findAll(codeChanged);
                self.useCls(0);
                self.correctDeadline(0);
            });
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.getListWorkplace();
            $.when(self.getData()).done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }

        initComponent() {
            let self = this;
            self.component = new ccg.component.viewmodel.ComponentModel({
                roleType: 1,
                multiple: false
            });

            self.component026 = new ccg026.component.viewmodel.ComponentModel({
                classification: 1,
                maxRow: 3
            });

            self.componentWorkplace = new ccg026.component.viewmodel.ComponentModel({
                classification: 1,
                maxRow: 3
            });
            self.componentEmployee = new ccg026.component.viewmodel.ComponentModel({
                classification: 1,
                maxRow: 3
            });

            self.componentDate = new ccg026.component.viewmodel.ComponentModel({
                classification: 1,
                maxRow: 3
            });

            self.componentShift = new ccg026.component.viewmodel.ComponentModel({
                classification: 1,
                maxRow: 3
            });

            self.component026.startPage().done(function() {
                self.listPermissionCommon(self.component026.listPermissions());
            }).then(() => {
                self.componentWorkplace.startPage().done(function() {
                    self.listPermissionWorkplace(self.componentWorkplace.listPermissions());
                });
            }).then(() => {
                self.componentEmployee.startPage().done(function() {
                    self.listPermissionEmployee(self.componentEmployee.listPermissions());
                });
            }).then(() => {
                self.componentDate.startPage().done(function() {
                    self.listPermissionDate(self.componentDate.listPermissions());
                });
            }).then(() => {
                self.componentShift.startPage().done(function() {
                    self.listPermissionShift(self.componentShift.listPermissions());
                });
            });
        }

        registration() {
            var self = this;
            var dfd = $.Deferred();
            nts.uk.ui.block.invisible();

            var data = _.find(self.component.listRole(), ['roleId', self.component.currentCode()]);
            var commonAuthor = [];
            var perWorkplace = [];
            var persAuthority = [];
            var dateAuthority = [];
            var shiftPermisson = [];
            _.forEach(self.items(), function(itemPer: IPermissonDto) {

                _.forEach(self.component026.listPermissions(), function(itemCom) {
                    commonAuthor.push({
                        roleId: data.roleId,
                        availableCommon: itemCom.availability() ? 1 : 0,
                        functionNoCommon: itemCom.functionNo
                    });
                });

                _.forEach(self.componentWorkplace.listPermissions(), function(itemWork) {
                    perWorkplace.push({
                        roleId: data.roleId,
                        availableWorkplace: itemWork.availability() ? 1 : 0,
                        functionNoWorkplace: itemWork.functionNo

                    });
                });

                _.forEach(self.componentEmployee.listPermissions(), function(itemPers) {
                    persAuthority.push({
                        roleId: data.roleId,
                        availablePers: itemPers.availability() ? 1 : 0,
                        functionNoPers: itemPers.functionNo
                    });
                });

                _.forEach(self.componentDate.listPermissions(), function(itemDate) {
                    dateAuthority.push({
                        roleId: data.roleId,
                        availableDate: itemDate.availability() ? 1 : 0,
                        functionNoDate: itemDate.functionNo
                    });
                });

                _.forEach(self.componentShift.listPermissions(), function(itemShift) {
                    shiftPermisson.push({
                        roleId: data.roleId,
                        availableShift: itemShift.availability() ? 1 : 0,
                        functionNoShift: itemShift.functionNo
                    });
                });
            })
            var schemodifyDeadline: IModifyDeadlineDto = {
                roleId: data.roleId,
                useCls: self.useCls(),
                correctDeadline: self.correctDeadline()
            };

            var permissonData = new PermissonDto({
                roleId: data.roleId,
                commonAuthor: commonAuthor,
                perWorkplace: perWorkplace,
                persAuthority: persAuthority,
                dateAuthority: dateAuthority,
                shiftPermisson: shiftPermisson,
                schemodifyDeadline: schemodifyDeadline
            });
            service.add(ko.toJS(permissonData)).done(function() {
                nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_15"));
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            }).always(function() {
                nts.uk.ui.block.clear();
            })
        }

        private getData() {
            let self = this;
            let dfd = $.Deferred();
            self.component.startPage().done(function() {
                self.component.currentCode.valueHasMutated();
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * get list workplace
         */
        private getListWorkplace() {
            let self = this;
            let dfd = $.Deferred();

            self.componentWorkplace.roleId(self.component.currentCode());
            self.component026.roleId(self.component.currentCode());
            self.componentEmployee.roleId(self.component.currentCode());
            self.componentDate.roleId(self.component.currentCode());
            self.componentShift.roleId(self.component.currentCode());
        }


        findAll(roleId: string): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.findAll(roleId).done(function(permissonTotalArr: any) {
                if (permissonTotalArr != null) {
                    var totalTime: IPermissonDto = {
                        commonAuthor: permissonTotalArr.commonAuthor,
                        dateAuthority: permissonTotalArr.dateAuthority,
                        persAuthority: permissonTotalArr.persAuthority,
                        perWorkplace: permissonTotalArr.perWorkplace,
                        shiftPermisson: permissonTotalArr.shiftPermisson,
                        schemodifyDeadline: permissonTotalArr.schemodifyDeadline
                    };
                    self.items.removeAll();
                    self.items.push(new PermissonDto(totalTime));

                    var listCommon = [];
                    _.forEach(self.listPermissionCommon(), function(item) {
                        var author = _.find(permissonTotalArr.commonAuthor, function(a: any) { return a.functionNoCommon == item.functionNo });
                        if (author) {
                            item.availability(!!author.availableCommon);

                        } else {
                            item.availability(!!item.availability()) ? 1 : 0;
                        }
                        listCommon.push(item);
                        self.listPermissionCommon(listCommon);

                        if (author) {
                            self.component026.listPermissions(self.listPermissionCommon());
                        } else {
                            self.component026.roleId(roleId);
                        }
                    });

                    var listWorkplace = [];
                    _.forEach(self.listPermissionWorkplace(), function(item) {
                        var author = _.find(permissonTotalArr.perWorkplace, function(a: any) { return a.functionNoWorkplace == item.functionNo });
                        if (author) {
                            item.availability(!!author.availableWorkplace);
                        } else {
                            item.availability(!!item.availability()) ? 1 : 0;
                        }
                        listWorkplace.push(item);
                        self.listPermissionWorkplace(listWorkplace);

                        if (author) {
                            self.componentWorkplace.listPermissions(self.listPermissionWorkplace());
                        } else {
                            self.componentWorkplace.roleId(roleId);
                        }
                    });

                    var listEmployee = [];
                    _.forEach(self.listPermissionEmployee(), function(item) {
                        var author = _.find(permissonTotalArr.persAuthority, function(a: any) { return a.functionNoPers == item.functionNo });
                        if (author) {
                            item.availability(!!author.availablePers);
                        } else {
                            item.availability(!!item.availability()) ? 1 : 0;
                        }
                        listEmployee.push(item);
                        self.listPermissionEmployee(listEmployee);

                        if (author) {
                            self.componentEmployee.listPermissions(self.listPermissionEmployee());
                        } else {
                            self.componentEmployee.roleId(roleId);
                        }
                    });

                    var listDate = [];
                    _.forEach(self.listPermissionDate(), function(item) {
                        var author = _.find(permissonTotalArr.dateAuthority, function(a: any) { return a.functionNoDate == item.functionNo });
                        if (author) {
                            item.availability(!!author.availableDate);
                        } else {
                            item.availability(!!item.availability()) ? 1 : 0;
                        }
                        listDate.push(item);
                        self.listPermissionDate(listDate);

                        if (author) {
                            self.componentDate.listPermissions(self.listPermissionDate());
                        } else {
                            self.componentDate.roleId(roleId);
                        }
                    });

                    var listShift = [];
                    _.forEach(self.listPermissionShift(), function(item) {
                        var author = _.find(permissonTotalArr.shiftPermisson, function(a: any) { return a.functionNoShift == item.functionNo });
                        if (author) {
                            item.availability(!!author.availableShift);
                        } else {
                            item.availability(!!item.availability()) ? 1 : 0;
                        }
                        listShift.push(item);
                        self.listPermissionShift(listShift);

                        if (author) {
                            self.componentShift.listPermissions(self.listPermissionShift());
                        } else {
                            self.componentShift.roleId(roleId);
                        }
                    });

                    _.forEach(permissonTotalArr.schemodifyDeadline, function(item) {
                        if (permissonTotalArr.schemodifyDeadline.length = 0) {
                            self.useCls(0);
                            self.correctDeadline(0);


                        } else {
                            self.useCls(item.useCls);
                            self.correctDeadline(item.correctDeadline);
                        }

                    })
                }
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }

    }

    export interface IPermissonDto {
        roleId?: string;
        commonAuthor?: Array<ICommonAuthorDto>;
        dateAuthority?: Array<IDateAuthorityDto>;
        persAuthority?: Array<IPersAuthorityDto>;
        perWorkplace?: Array<IPerWorkplaceDto>;
        shiftPermisson?: Array<IShiftPermissonDto>;
        schemodifyDeadline?: IModifyDeadlineDto;
    }

    export class PermissonDto {
        roleId: KnockoutObservable<string>;
        commonAuthor: KnockoutObservableArray<CommonAuthorDto> = ko.observableArray([]);
        dateAuthority: KnockoutObservableArray<DateAuthorityDto> = ko.observableArray([]);
        persAuthority: KnockoutObservableArray<PersAuthorityDto> = ko.observableArray([]);
        perWorkplace: KnockoutObservableArray<PerWorkplaceDto> = ko.observableArray([]);
        shiftPermisson: KnockoutObservableArray<ShiftPermissonDto> = ko.observableArray([]);
        schemodifyDeadline: KnockoutObservable<ModifyDeadlineDto>;
        constructor(param: IPermissonDto) {
            this.roleId = ko.observable(param.roleId || null);
            this.commonAuthor(param.commonAuthor ? _.map(param.commonAuthor, item => { return new CommonAuthorDto(item) }) : []);
            this.dateAuthority(param.dateAuthority ? _.map(param.dateAuthority, item => { return new DateAuthorityDto(item) }) : []);
            this.persAuthority(param.persAuthority ? _.map(param.persAuthority, item => { return new PersAuthorityDto(item) }) : []);
            this.perWorkplace(param.perWorkplace ? _.map(param.perWorkplace, item => { return new PerWorkplaceDto(item) }) : []);
            this.shiftPermisson(param.shiftPermisson ? _.map(param.shiftPermisson, item => { return new ShiftPermissonDto(item) }) : []);
            this.schemodifyDeadline = ko.observable(param.schemodifyDeadline ? new ModifyDeadlineDto(param.schemodifyDeadline) : new ModifyDeadlineDto({}));
        }
    }


    export class ModifyDeadlineDto {
        roleId: KnockoutObservable<string>;
        useCls: KnockoutObservable<number>;
        correctDeadline: KnockoutObservable<number>;
        constructor(param: IModifyDeadlineDto) {
            this.roleId = ko.observable(param.roleId || null);
            this.useCls = ko.observable(param.useCls || 0);
            this.correctDeadline = ko.observable(param.correctDeadline || 0);
        }
    }

    export interface IModifyDeadlineDto {
        roleId?: string;
        useCls?: number;
        correctDeadline?: number;
    }

    export class CommonAuthorDto {
        roleId: KnockoutObservable<string>;
        availableCommon: KnockoutObservable<number>;
        functionNoCommon: KnockoutObservable<number>;
        constructor(param: ICommonAuthorDto) {
            this.roleId = ko.observable(param.roleId || null);
            this.availableCommon = ko.observable(param.availableCommon || 0);
            this.functionNoCommon = ko.observable(param.functionNoCommon || null);
        }
    }

    export interface ICommonAuthorDto {
        roleId?: string;
        availableCommon?: number;
        functionNoCommon?: number;
    }


    export class DateAuthorityDto {
        roleId: KnockoutObservable<string>;
        availableDate: KnockoutObservable<number>;
        functionNoDate: KnockoutObservable<number>;
        constructor(param: IDateAuthorityDto) {
            this.roleId = ko.observable(param.roleId || null);
            this.availableDate = ko.observable(param.availableDate || 0);
            this.functionNoDate = ko.observable(param.functionNoDate || null);
        }
    }

    export interface IDateAuthorityDto {
        roleId?: string;
        availableDate?: number;
        functionNoDate?: number;
    }


    export class PersAuthorityDto {
        roleId: KnockoutObservable<string>;
        availablePers: KnockoutObservable<number>;
        functionNoPers: KnockoutObservable<number>;
        constructor(param: IPersAuthorityDto) {
            this.roleId = ko.observable(param.roleId || null);
            this.availablePers = ko.observable(param.availablePers || 0);
            this.functionNoPers = ko.observable(param.functionNoPers || null);
        }
    }

    export interface IPersAuthorityDto {
        roleId?: string;
        availablePers?: number;
        functionNoPers?: number;
    }


    export class PerWorkplaceDto {
        roleId: KnockoutObservable<string>;
        availableWorkplace: KnockoutObservable<number>;
        functionNoWorkplace: KnockoutObservable<number>;
        constructor(param: IPerWorkplaceDto) {
            this.roleId = ko.observable(param.roleId || null);
            this.availableWorkplace = ko.observable(param.availableWorkplace || 0);
            this.functionNoWorkplace = ko.observable(param.functionNoWorkplace || null);
        }
    }

    export interface IPerWorkplaceDto {
        roleId?: string;
        availableWorkplace?: number;
        functionNoWorkplace?: number;
    }


    export class ShiftPermissonDto {
        roleId: KnockoutObservable<string>;
        availableShift: KnockoutObservable<number>;
        functionNoShift: KnockoutObservable<number>;
        constructor(param: IShiftPermissonDto) {
            this.roleId = ko.observable(param.roleId || null);
            this.availableShift = ko.observable(param.availableShift || 0);
            this.functionNoShift = ko.observable(param.functionNoShift || null);
        }
    }

    export interface IShiftPermissonDto {
        roleId?: string;
        availableShift?: number;
        functionNoShift?: number;
    }
}
