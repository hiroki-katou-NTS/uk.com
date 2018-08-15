module nts.uk.at.view.ksm011.d.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    import viewmodel = nts.uk.com.view.ccg025.a.component.viewmodel;

    export class ScreenModel {
        component: ccg.component.viewmodel.ComponentModel;

        currentCodeItem: KnockoutObservable<any>;
        listDeadline: KnockoutObservableArray<any>;
        itemBaseAtr: KnockoutObservableArray<any>;
        outputAtr: KnockoutObservableArray<any>;
        itemCorrectDeadlineAtr: KnockoutObservableArray<any>;
        useCls: KnockoutObservable<number>;
        currentItem: KnockoutObservable<PermissonDto>;
        items: KnockoutObservableArray<PermissonDto>;
        correctDeadline: KnockoutObservable<number>;
        roleId: KnockoutObservable<any>;
        permisionCommon: KnockoutObservable<any> = ko.observableArray([]);
        listCommon: KnockoutObservable<any> = ko.observableArray([]);
        listWorkplace: KnockoutObservable<any> = ko.observableArray([]);
        listAuthority: KnockoutObservable<any> = ko.observableArray([]);
        listDate: KnockoutObservable<any> = ko.observableArray([]);
        listShift: KnockoutObservable<any> = ko.observableArray([]);
        listCommonDes: KnockoutObservable<any> = ko.observableArray([]);
        listWorkplaceDes: KnockoutObservable<any> = ko.observableArray([]);
        listAuthorityDes: KnockoutObservable<any> = ko.observableArray([]);
        listDateDes: KnockoutObservable<any> = ko.observableArray([]);
        listShiftDes: KnockoutObservable<any> = ko.observableArray([]);

        constructor() {
            let self = this;
            let dfd = $.Deferred();
            self.roleId = ko.observable();
            self.currentCodeItem = ko.observable();

            self.initComponent();
            self.listDeadline = ko.observableArray([]);
            self.outputAtr = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);
            let arr: any = [];
            for (let i = 0; i <= 31; i++) {
                arr.push({ code: i, name: i + '日' });
            }
            self.itemCorrectDeadlineAtr = ko.observableArray(arr);
            self.currentItem = ko.observable(new PermissonDto({}));
            self.items = ko.observableArray([

            ]);
            self.useCls = ko.observable(0);
            self.correctDeadline = ko.observable(0);
            self.component.currentCode.subscribe(function(codeChanged) {
                var data = _.find(self.component.listRole(), ['roleId', codeChanged]);
                self.findAll(data.roleId);
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
            $.when(self.getData()).done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }

        initComponent() {
            let self = this;
            service.findDes().done(function(data) {
                self.listCommon(_.map(data.common, (d: any) => ({
                    functionNo: d.functionNoCom,
                    functionName: d.displayNameCom,
                    available: d.initialValueCom,
                    description: d.descripptionCom
                })));

                self.listAuthority(_.map(data.authority, (sRes: any) => ({
                    functionNo: sRes.functionNoAuth,
                    functionName: sRes.displayNameAuth,
                    available: sRes.initialValueAuth,
                    description: sRes.descripptionAuth
                })));

                self.listDate(_.map(data.date, (sRes: any) => ({
                    functionNo: sRes.functionNoDate,
                    functionName: sRes.displayNameDate,
                    available: sRes.initialValueDate,
                    description: sRes.descripptionDate
                })));

                let listData = [];
                _.forEach(data.shift, function(sRes) {
                    var shiftData = {
                        functionNo: sRes.functionNoShift,
                        functionName: sRes.displayNameShift,
                        available: sRes.initialValueShift,
                        description: sRes.descripptionShift
                    }
                    listData.push(shiftData);
                })
                self.listShift(listData);

                listData = [];
                _.forEach(data.workplace, function(sRes) {
                    var workData = {
                        functionNo: sRes.functionNoWork,
                        functionName: sRes.displayNameWork,
                        available: sRes.initialValueWork,
                        description: sRes.descripptionWork
                    }
                    listData.push(workData);
                })
                self.listWorkplace(listData);

            })

            self.component = new ccg.component.viewmodel.ComponentModel({
                roleType: RoleType.Work,
                multiple: false
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
                _.forEach(self.listCommon(), function(sRes) {
                    commonAuthor.push({
                        roleId: data.roleId,
                        availableCommon: sRes.available ? 1 : 0,
                        functionNoCommon: sRes.functionNo
                    });
                })

                _.forEach(self.listWorkplace(), function(sRes) {
                    perWorkplace.push({
                        roleId: data.roleId,
                        availableWorkplace: sRes.available ? 1 : 0,
                        functionNoWorkplace: sRes.functionNo
                    });
                })

                _.forEach(self.listAuthority(), function(sRes) {
                    persAuthority.push({
                        roleId: data.roleId,
                        availablePers: sRes.available ? 1 : 0,
                        functionNoPers: sRes.functionNo
                    });

                })
                

                _.forEach(self.listDate(), function(sRes) {
                    dateAuthority.push({
                        roleId: data.roleId,
                        availableDate: sRes.available ? 1 : 0,
                        functionNoDate: sRes.functionNo
                    });
                })

                _.forEach(self.listShift(), function(sRes) {
                    shiftPermisson.push({
                        roleId: data.roleId,
                        availableShift: sRes.available ? 1 : 0,
                        functionNoShift: sRes.functionNo
                    });
                })

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
                    _.forEach(self.listCommon(), function(item) {
                        var author = _.find(permissonTotalArr.commonAuthor, function(a: any) { return a.functionNoCommon == item.functionNo });
                        if (author) {
                            item.available = author.availableCommon ? 1 : 0;

                        } else {
                            item.available = 0;
                        }
                        listCommon.push(item);
                        self.listCommon(listCommon);

                    });

                    var listWorkp = [];
                    _.forEach(self.listWorkplace(), function(item) {
                        var author = _.find(permissonTotalArr.perWorkplace, function(a: any) { return a.functionNoWorkplace == item.functionNo });
                        if (author) {
                            item.available = author.availableWorkplace ? 1 : 0;
                        } else {
                            item.available = 0;
                        }
                        listWorkp.push(item);
                        self.listWorkplace(listWorkp);


                    });

                    var listEmployee = [];
                    _.forEach(self.listAuthority(), function(item) {
                        var author = _.find(permissonTotalArr.persAuthority, function(a: any) { return a.functionNoPers == item.functionNo });
                        if (author) {
                            item.available = author.availablePers ? 1 : 0;
                        } else {
                            item.available = 0;
                        }
                        listEmployee.push(item);
                        self.listAuthority(listEmployee);

                    });

                    var listDate = [];
                    _.forEach(self.listDate(), function(item) {
                        var author = _.find(permissonTotalArr.dateAuthority, function(a: any) { return a.functionNoDate == item.functionNo });
                        if (author) {
                            item.available = author.availableDate ? 1 : 0;
                        } else {
                            item.available = 0;
                        }
                        listDate.push(item);
                        self.listDate(listDate);

                    });

                    var listShiftDes = [];
                    _.forEach(self.listShift(), function(item) {
                        var author = _.find(permissonTotalArr.shiftPermisson, function(a: any) { return a.functionNoShift == item.functionNo });
                        if (author) {
                            item.available = author.availableShift ? 1 : 0;
                        } else {
                            item.available = 0;
                        }
                        listShiftDes.push(item);
                        self.listShift(listShiftDes);

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
    export enum RoleType {
        //就業
        Work = 3
    }
}
