module ccg018.a.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        items: KnockoutObservableArray<TopPageJobSet>;
        switchOptions: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        isHidden: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<number>;
        comboItemsAfterLogin: KnockoutObservableArray<any>;
        comboItemsAsTopPage: KnockoutObservableArray<any>;

        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode1: KnockoutObservable<string>;
        selectedCode2: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.date = ko.observable(new Date().toISOString());
            self.items = ko.observableArray([]);
            self.comboItemsAfterLogin = ko.observableArray([]);
            self.comboItemsAsTopPage = ko.observableArray([]);
            self.isHidden = ko.observable(false);
            self.categorySet = ko.observable(undefined);
            self.switchOptions = ko.observableArray([new ItemModel('0', nts.uk.resource.getText("CCG018_13")),
                new ItemModel('1', nts.uk.resource.getText("CCG018_14"))]);

            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給')
            ]);
            self.selectedCode1 = ko.observable('1');
            self.selectedCode2 = ko.observable('3');

            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("CCG018_13") },
                { code: '2', name: nts.uk.resource.getText("CCG018_14") }
            ]);
            self.selectedRuleCode = ko.observable(1);

            $.when(self.findByCId()).done(function() {
                $.when(self.findByAfterLoginDisplay()).done(function() {
                    $.when(self.findByAfterLgDisSysMenuCls()).done(function() {
                        $.when(self.findDataOfTopPageJobSet()).done(function() {
                            //                           self.bindGrid('785px');
                        }).fail();
                    }).fail();
                }).fail();
            }).fail();

            for (let i = 1; i < 50; i++) {
                self.items.push(new TopPageJobSet('00' + i, 'アルバイト', 'A001', 'A002', 0, 'A0000000', 1));
            }
            self.currentCode = ko.observable();
        }

        //        bindGrid(widthGrid: string) {
        //            var self = this;
        //            var comboColumns = [
        //                { prop: 'name', length: 8 }];
        //            $("#grid").ntsGrid({
        //                width: widthGrid,
        //                height: '565px',
        //                dataSource: self.items(),
        //                autoCommit: true,
        //                primaryKey: 'code',
        //                virtualization: true,
        //                virtualizationMode: 'continuous',
        //                columns: [
        //                    { headerText: nts.uk.resource.getText("CCG018_8"), key: 'code', dataType: 'number', width: '50px' },
        //                    { headerText: nts.uk.resource.getText("CCG018_9"), key: 'name', dataType: 'string', width: '120px' },
        //                    { headerText: nts.uk.resource.getText("CCG018_10"), key: 'afterLogin', dataType: 'string', width: '205px', ntsControl: 'Combobox1', hidden: self.isHidden() },
        //                    { headerText: nts.uk.resource.getText("CCG018_11"), key: 'asTopPage', dataType: 'string', width: '205px', ntsControl: 'Combobox2' },
        //                    { headerText: nts.uk.resource.getText("CCG018_12"), key: 'personPermissionSet', dataType: 'string', width: '185px', ntsControl: 'SwitchButtons' }
        //                ],
        //                features: [{ name: 'Sorting', type: 'local' }],
        //                ntsControls: [
        //                    { name: 'Combobox1', options: self.comboItemsAfterLogin(), optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
        //                    { name: 'Combobox2', options: self.comboItemsAsTopPage(), optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
        //                    { name: 'SwitchButtons', options: self.switchOptions(), optionsValue: 'code', optionsText: 'name', controlType: 'SwitchButtons', enable: true },
        //            });
        //        }

        /**
         * Find data in DB TOPPAGE_SET base on companyId
         */
        findByCId(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            ccg018.a.service.findByCId()
                .done(function(data) {
                    if (!(!!data)) {
                        self.openDialogC();
                    } else {
                        self.categorySet(data.ctgSet);
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * Find By After Login Display (asTopPage) 
         */
        findByAfterLoginDisplay(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            ccg018.a.service.findByAfterLoginDisplay()
                .done(function(data) {
                    if (data.length > 0) {
                        self.comboItemsAsTopPage().push(new ItemModel('', '未設定'));
                        for (var i = 0; i < data.length; i++) {
                            self.comboItemsAsTopPage().push(new ItemModel(data[i].code, data[i].displayName));
                        }
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
        * Find By After_Login_Display and System and Menu_Classification (after login display)
        */
        findByAfterLgDisSysMenuCls(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            ccg018.a.service.findByAfterLgDisSysMenuCls()
                .done(function(data) {
                    if (data.length > 0) {
                        self.comboItemsAfterLogin().push(new ItemModel('', '未設定'));
                        for (var i = 0; i < data.length; i++) {
                            self.comboItemsAfterLogin().push(new ItemModel(data[i].code, data[i].displayName));
                        }
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * Find data in DB TOPPAGE_JOB_SET
         */
        findDataOfTopPageJobSet(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            ccg018.a.service.findDataOfTopPageJobSet()
                .done(function(data) {
                    if (data.length > 0) {
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }


        /**
         * Update/insert data in TOPPAGE_JOB_SET
         */
        update(): void {
            var self = this;
            var command = {
                listTopPageJobSet: self.items(),
                ctgSet: self.categorySet()
            };
            ccg018.a.service.update(command)
                .done(function() {
                }).fail();
        }

        /**
         * Open dialog C
         */
        openDialogC(): void {
            var self = this;
            // the default value of categorySet = undefined
            nts.uk.ui.windows.setShared('categorySet', self.categorySet());
            nts.uk.ui.windows.sub.modal("/view/ccg/018/c/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                if (nts.uk.ui.windows.getShared('divideOrNot') != undefined) {
                    if (self.categorySet() != nts.uk.ui.windows.getShared('divideOrNot')) {
                        self.categorySet(nts.uk.ui.windows.getShared('divideOrNot'));
                        if (self.categorySet() === 1) {
                            self.isHidden(true);
                        } else {
                            self.isHidden(false);
                        }
                    }
                }
            });
        }
    }

    class TopPageJobSet {
        code: string;
        name: string;
        afterLogin: string;
        asTopPage: string;
        personPermissionSet: number;
        jobId: string;
        system: number;
        constructor(code: string, name: string, afterLogin: string, asTopPage: string, personPermissionSet: number, jobId: string, system: number) {
            this.code = code;
            this.name = name;
            this.afterLogin = afterLogin;
            this.asTopPage = asTopPage;
            this.personPermissionSet = personPermissionSet;
            this.jobId = jobId;
            this.system = system;
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
}