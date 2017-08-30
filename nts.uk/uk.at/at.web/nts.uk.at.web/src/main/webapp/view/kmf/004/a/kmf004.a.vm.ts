module nts.uk.at.view.kmf004.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<model.SpecialHolidayDto>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;

        currentItem: KnockoutObservable<model.SpecialHolidayDto>
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        selectedValue: KnockoutObservable<any>;
        enable: KnockoutObservable<boolean>;
        
        //Tab
        option1: KnockoutObservable<any>;
        option2: KnockoutObservable<any>;
        date: KnockoutObservable<string>;
        roundingRules1: KnockoutObservableArray<any>;
        roundingRules2: KnockoutObservableArray<any>;
        roundingRules3: KnockoutObservableArray<any>;
        roundingRules4: KnockoutObservableArray<any>;
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        selectedRuleCode: any;
        
        //Input
        inp_specialHolidayCode: KnockoutObservable<string>;
        inp_specialHolidayName: KnockoutObservable<string>;
        sel_grantPeriodicCls: KnockoutObservable<number>;
        roundingRules: KnockoutObservableArray<any>;
        inp_memo: KnockoutObservable<string>;
        inp_workTypeList: KnockoutObservable<string>;
        workTypeList: KnockoutObservableArray<any>;
        workTypeNames: KnockoutObservable<string>;
        isEnableCode: KnockoutObservable<boolean>;
        constructor() {
            var self = this;

            self.items = ko.observableArray([]);
            self.enable = ko.observable(true);


            self.date = ko.observable('');
            self.selectedCode = ko.observable('0002')
            self.isEnable = ko.observable(true);
            self.isEnableCode = ko.observable(false);
            self.isEditable = ko.observable(true);
            self.inp_specialHolidayCode = ko.observable(null);
            self.inp_specialHolidayName = ko.observable(null);
            self.sel_grantPeriodicCls = ko.observable(1);
            self.inp_memo = ko.observable(null);
            self.currentItem = ko.observable(null);
            self.workTypeList = ko.observableArray([]);
            self.inp_workTypeList = ko.observable(null);
            self.workTypeNames = ko.observable("");
            self.itemList = ko.observableArray([
                new ItemModel('基本給1', '基本給'),
                new ItemModel('基本給2', '役職手当'),
                new ItemModel('0003', '基本給')
            ]);

            self.roundingRules = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KMF004_15') },
                { code: '1', name: nts.uk.resource.getText('KMF004_14') },
            ]);
            self.selectedRuleCode = ko.observable(0);
            self.roundingRules.subscribe(function(newValue) {
                if (self.selectedRuleCode(0)) {
                    $('.tab-content').hide();
             
                }
            });
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'specialHolidayCode', width: 100 },
                { headerText: '名称', key: 'specialHolidayName', width: 150 }
            ]);

            self.roundingRules1 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_38') },
                { code: '2', name: nts.uk.resource.getText('KMF004_39') }
            ]);

            self.roundingRules2 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_51') },
                { code: '2', name: nts.uk.resource.getText('KMF004_52') }
            ]);

            self.roundingRules3 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_125') },
                { code: '2', name: nts.uk.resource.getText('KMF004_126') }
            ]);

            self.roundingRules4 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_61') },
                { code: '2', name: nts.uk.resource.getText('KMF004_62') }
            ]);

            //Tab1
            self.selectedValue = ko.observable(false);
            self.option1 = ko.observable({ value: 0, text: nts.uk.resource.getText('KMF004_23') });

            //Tab2
            self.option2 = ko.observable({ value: 1, text: nts.uk.resource.getText('KMF004_24') });

            self.currentCode = ko.observable();
            self.currentCode.subscribe(function(codeChanged) {
                if (codeChanged !== null && codeChanged !== undefined) {
                    self.changedCode(codeChanged);
                    self.isEnableCode(false);
                }
            });

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText('KMF004_17'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText('KMF004_18'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: nts.uk.resource.getText('KMF004_19'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: nts.uk.resource.getText('KMF004_20'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-5', title: nts.uk.resource.getText('KMF004_21'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            $.when(self.getAllSpecialHoliday(), self.getWorkTypeList()).done(function() {
                self.currentCode(self.items()[0].specialHolidayCode);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });

            return dfd.promise();
        }

        getAllSpecialHoliday(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.items.removeAll();
            service.findAllSpecialHoliday().done(function(specialHoliday_arr: Array<model.SpecialHolidayDto>) {
                for (var i = 0; i < specialHoliday_arr.length; i++) {
                    var specialHoliday: model.ISpecialHolidayDto = {
                        specialHolidayCode: specialHoliday_arr[i].specialHolidayCode,
                        specialHolidayName: specialHoliday_arr[i].specialHolidayName,
                        grantPeriodicCls: specialHoliday_arr[i].grantPeriodicCls,
                        memo: specialHoliday_arr[i].memo,
                        workTypeList: specialHoliday_arr[i].workTypeList
                    };
                    self.items.push(new model.SpecialHolidayDto(specialHoliday));
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });

            return dfd.promise();
        }

        addSpecialHoliday(): JQueryPromise<any> {
            var self = this;
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            var workTypeCodes = _.map(self.inp_workTypeList(), function(item: any) { return item.code });
            var specialholiday = new model.SpecialHolidayDto({
                specialHolidayCode: self.inp_specialHolidayCode(),
                specialHolidayName: self.inp_specialHolidayName(),
                grantPeriodicCls: self.sel_grantPeriodicCls(),
                memo: self.inp_memo(),
                workTypeList: workTypeCodes
            });

            if (self.isEnableCode()) {
                service.addSpecialHoliday(specialholiday).done(function(res) {
                    var resObj = ko.toJS(res);
                    if (self.currentCode) {
                        self.getAllSpecialHoliday();

                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_15"));
                        self.currentCode(specialholiday.specialHolidayCode);
                        self.isEnableCode(false);
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                })
            }
            else {
                service.updateSpecialHoliday(specialholiday).done(function(res) {
                    self.getAllSpecialHoliday();
                    self.currentCode(specialholiday.specialHolidayCode);
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_15"));
                    $("#daterangepicker").find(".ntsStartDatePicker").focus();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                })
            }
        }

        getWorkTypeList() {
            var self = this;
            var dfd = $.Deferred();
            service.findWorkType().done(function(res) {
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
        deleteSpecialHoliday() {
            let self = this;
            var index_of_itemDelete = _.findIndex(self.items(), ['specialHolidayCode', self.currentCode()]);
            nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                var specialholiday = {
                    specialHolidayCode: self.currentItem().specialHolidayCode
                };
                service.deleteSpecialHoliday(specialholiday).done(function() {
                    $.when(self.getAllSpecialHoliday()).done(function() {
                        var holidayId = "";
                        if (self.items().length == 0) {
                            self.initSpecialHoliday();
                        } else if (self.items().length == 1) {
                            holidayId = self.items()[0].specialHolidayCode;
                        } else if (index_of_itemDelete == self.items().length) {
                            holidayId = self.items()[index_of_itemDelete - 1].specialHolidayCode;
                        } else {
                            holidayId = self.items()[index_of_itemDelete].specialHolidayCode;
                        }
                        self.currentCode(holidayId);
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16"));
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                })
            }).ifNo(function() {
            });
        }
        initSpecialHoliday(): void {
            let self = this;
            self.inp_specialHolidayCode("");
            self.inp_specialHolidayName("");
            self.sel_grantPeriodicCls(0);
            self.inp_memo("");
            self.currentCode("");
            self.workTypeNames("");
            nts.uk.ui.errors.clearAll();
            self.isEnableCode(true);
        }

        changedCode(value) {
            var self = this;
            self.currentItem(self.findSpecialHoliday(value));

            if (self.currentItem() != null) {
                self.inp_specialHolidayCode(self.currentItem().specialHolidayCode);
                self.inp_specialHolidayName(self.currentItem().specialHolidayName);
                self.sel_grantPeriodicCls(self.currentItem().grantPeriodicCls);
                self.inp_memo(self.currentItem().memo);

                var names = self.getNames(self.workTypeList(), self.currentItem().workTypeList);
                self.workTypeNames(names);
            }
        }

        findSpecialHoliday(value: string): any {
            let self = this;
            var result = _.find(self.items(), function(obj: model.SpecialHolidayDto) {
                return obj.specialHolidayCode === value;
            });
            return (result) ? result : null;
        }

        openKDL002Dialog() {
            let self = this;
            nts.uk.ui.block.invisible();

            var workTypeCodes = _.map(self.workTypeList(), function(item: model.IWorkTypeModal) { return item.workTypeCode });
            nts.uk.ui.windows.setShared('KDL002_Multiple', true);
            nts.uk.ui.windows.setShared('KDL002_AllItemObj', workTypeCodes);
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId', []);

            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
                var data = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                var name = [];
                _.forEach(data, function(item: model.IWorkTypeModal) {
                    name.push(item.name);
                });
                self.workTypeNames(name.join(" + "));
                self.inp_workTypeList(data);
            });

        }

        openBDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/b/index.xhtml', { title: '代行リスト', height: 600, width: 1000, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }
        openDDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/b/index.xhtml', { title: '代行リスト', height: 600, width: 1100, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }

        openGDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/g/index.xhtml', { title: '続柄に対する付与日数', dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }

        openHDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/h/index.xhtml', { title: '続柄の登録', dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }

        getNames(data: Array<model.IWorkTypeModal>, workTypeCodesSelected: Array<string>) {
            var name = [];
            if (workTypeCodesSelected && workTypeCodesSelected.length > 0) {
                _.forEach(data, function(item: model.IWorkTypeModal) {
                    if (_.includes(workTypeCodesSelected, item.workTypeCode)) {
                        name.push(item.name);
                    }
                });
            }
            return name.join(" + ");
        }
    }

    class ItemModelSpecialHoliday {
        specialHolidayCode: string;
        specialHolidayName: string;
        constructor(specialHolidayCode: string, specialHolidayName: string) {
            this.specialHolidayCode = specialHolidayCode;
            this.specialHolidayName = specialHolidayName;
        }
    }

    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export module model {
        export interface ISpecialHolidayDto {
            specialHolidayCode: string;
            specialHolidayName: string;
            grantPeriodicCls: number;
            memo: string;
            workTypeList: Array<string>;
        }
        export class SpecialHolidayDto {
            specialHolidayCode: string;
            specialHolidayName: string;
            grantPeriodicCls: number;
            memo: string;
            workTypeList: Array<string>;
            constructor(param: ISpecialHolidayDto) {
                this.specialHolidayCode = param.specialHolidayCode;
                this.specialHolidayName = param.specialHolidayName;
                this.grantPeriodicCls = param.grantPeriodicCls;
                this.memo = param.memo;
                this.workTypeList = param.workTypeList;
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
    }

}