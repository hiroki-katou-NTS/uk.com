module ccg018.a1.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        items: KnockoutObservableArray<TopPageJobSet>;
        isVisible: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<number>;
        listJobTitle: KnockoutObservableArray<any>;
        comboItemsAfterLogin: KnockoutObservableArray<ItemModel>;
        comboItemsAsTopPage: KnockoutObservableArray<ItemModel>;
        //appear/disappear header of scroll on UI
        isHeaderScroll: KnockoutObservable<boolean>;

        roundingRules: KnockoutObservableArray<any>;

        constructor() {
            let self = this;
            self.items = ko.observableArray([]);
            self.listJobTitle = ko.observableArray([]);
            self.date = ko.observable(new Date().toISOString());
            self.comboItemsAfterLogin = ko.observableArray([]);
            self.comboItemsAsTopPage = ko.observableArray([]);
            self.categorySet = ko.observable(undefined);
            self.isVisible = ko.computed(function() {
                return !!self.categorySet();
            });

            self.isHeaderScroll = ko.computed(function() {
                return self.items().length > 15 ? true : false;
            });

            self.roundingRules = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("CCG018_13") },
                { code: 0, name: nts.uk.resource.getText("CCG018_14") }
            ]);

            self.findByCId();
            $.when(self.findBySystemMenuCls(), self.findDataForAfterLoginDis()).done(function() {
                self.searchByDate();
            });
        }

        start(): any {
            let self = this;
            self.categorySet(__viewContext.viewModel.viewmodelB.categorySet());
        }

        /**
         * get categorySet in DB TOPPAGE_SET base on companyId
         */
        findByCId(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            ccg018.a1.service.findByCId()
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
         * Find data in table STANDARD_MENU base on CompanyId and System = 0(common) and MenuClassification = 8(top page)
         * Return 2 array comboItemsAsTopPage and comboItemsAfterLogin
         */
        findBySystemMenuCls(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.comboItemsAsTopPage([]);
            ccg018.a1.service.findBySystemMenuCls()
                .done(function(data) {
                    if (data.length >= 0) {
                        self.comboItemsAsTopPage.push(new ItemModel('', '未設定', 0, 0));

                        _.forEach(data, function(x) {
                            self.comboItemsAsTopPage.push(new ItemModel(x.code, x.displayName, x.system, x.classification));
                        });
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * find data in table STANDARD_MENU with companyId and 
         * afterLoginDisplay = 1 (display)  or System = 0(common) and MenuClassification = 8(top page)
         */
        findDataForAfterLoginDis(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.comboItemsAfterLogin([]);
            ccg018.a1.service.findDataForAfterLoginDis()
                .done(function(data) {
                    self.comboItemsAfterLogin.push(new ItemModel('', '未設定', 0, 0));
                    _.forEach(data, function(x) {
                        self.comboItemsAfterLogin.push(new ItemModel(x.code, x.displayName, x.system, x.classification));
                    });
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }


        /**
         * Find data in DB TOPPAGE_JOB_SET
         */
        findDataOfTopPageJobSet(listJobId: any): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            ccg018.a1.service.findDataOfTopPageJobSet(listJobId)
                .done(function(data) {
                    if (data.length > 0) {
                        _.forEach(listJobId, function(x) {
                            let dataObj: any = _.find(data, ['jobId', x]);
                            let jobTitle = _.find(self.listJobTitle(), ['id', x]);
                            if (!!dataObj) {
                                self.items.push(new TopPageJobSet(jobTitle.code, jobTitle.name, dataObj.loginMenuCode, dataObj.topMenuCode, dataObj.personPermissionSet, x, dataObj.loginSystem, dataObj.menuClassification));
                            } else {
                                self.items.push(new TopPageJobSet(jobTitle.code, jobTitle.name, '', '', 0, x, 0, 0));
                            }
                        });

                        dfd.resolve();
                    }
                }).fail();
            return dfd.promise();
        }

        /**
         * get JobId, JobCode and JobName in table CJTMT_JOB_TITLE
         * then call function findDataOfTopPageJobSet()
         */
        searchByDate(): any {
            let self = this;
            self.items([]);
            ccg018.a1.service.findDataOfJobTitle(self.date())
                .done(function(data) {
                    if (data.length > 0) {
                        self.listJobTitle(data);
                        let listJobId = [];
                        _.forEach(data, function(x) {
                            listJobId.push(x.id);
                        });
                        self.findDataOfTopPageJobSet(listJobId);
                    }
                }).fail();
        }


        /**
         * Update/insert data in TOPPAGE_JOB_SET
         */
        update(): void {
            let self = this;
            let command = {
                listTopPageJobSet: ko.mapping.toJS(self.items()),
                ctgSet: self.categorySet()
            };
            ccg018.a1.service.update(command)
                .done(function() {
                    self.searchByDate();
                    nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_15"));
                }).fail();
        }

        /**
         * Open dialog C
         */
        openDialogC(): void {
            let self = this;
            // the default value of categorySet = undefined
            nts.uk.ui.windows.setShared('categorySet', self.categorySet());
            nts.uk.ui.windows.sub.modal("/view/ccg/018/c/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                if (nts.uk.ui.windows.getShared('categorySetC') != undefined) {
                    if (self.categorySet() != nts.uk.ui.windows.getShared('categorySetC')) {
                        self.categorySet(nts.uk.ui.windows.getShared('categorySetC'));
                    }
                }
            });
        }
    }

    class TopPageJobSet {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        loginMenuCd: KnockoutObservable<string>;
        topMenuCd: KnockoutObservable<string>;
        personPermissionSet: KnockoutObservable<number>;
        jobId: KnockoutObservable<string>;
        system: KnockoutObservable<number>;
        menuClassification: KnockoutObservable<number>;
        uniqueCode: KnockoutObservable<string>;
        constructor(code: string, name: string, loginMenuCd: string, topMenuCd: string, personPermissionSet: number, jobId: string, system: number, menuClassification: number) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.loginMenuCd = ko.observable(loginMenuCd);
            this.topMenuCd = ko.observable(topMenuCd);
            this.personPermissionSet = ko.observable(personPermissionSet);
            this.jobId = ko.observable(jobId);
            this.system = ko.observable(system);
            this.menuClassification = ko.observable(menuClassification);
            this.uniqueCode = ko.observable(loginMenuCd + system + menuClassification);
        }
    }

    class ItemModel {
        code: string;
        name: string;
        system: number;
        menuCls: number;
        uniqueCode: string;

        constructor(code: string, name: string, system: number, menuCls: number) {
            this.code = code;
            this.name = name;
            this.system = system;
            this.menuCls = menuCls;
            this.uniqueCode = code + system + menuCls;
        }
    }
}