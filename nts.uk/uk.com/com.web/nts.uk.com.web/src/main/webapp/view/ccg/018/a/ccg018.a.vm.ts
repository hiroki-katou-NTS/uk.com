module ccg018.a.viewmodel {
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

            self.start();
        }

        start(): any {
            let self = this;
            if (self.categorySet() == undefined) {
                self.findByCId();
            } else {
                self.categorySet(__viewContext.viewModel.viewmodelB.categorySet());
            }
            $.when(self.findBySystemMenuCls(), self.findDataForAfterLoginDis()).done(function() {
                self.searchByDate();
            });
        }

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
         * Find data in table STANDARD_MENU base on CompanyId and System = 0(common) and MenuClassification = 8(top page)
         * Return 2 array comboItemsAsTopPage and comboItemsAfterLogin
         */
        findBySystemMenuCls(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.comboItemsAsTopPage.removeAll();
            ccg018.a.service.findBySystemMenuCls()
                .done(function(data) {
                    if (data.length >= 0) {
                        self.comboItemsAsTopPage.push(new ItemModel('', '未設定'));

                        _.forEach(data, function(x) {
                            self.comboItemsAsTopPage.push(new ItemModel(x.code, x.displayName));
                        });
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * find data in talbel STANDARD_MENU with companyId and 
         * afterLoginDisplay = 1 (display)  or System = 0(common) and MenuClassification = 8(top page)
         */
        findDataForAfterLoginDis(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.comboItemsAfterLogin.removeAll();
            ccg018.a.service.findDataForAfterLoginDis()
                .done(function(data) {
                    self.comboItemsAfterLogin.push(new ItemModel('', '未設定'));
                    _.forEach(data, function(x) {
                        self.comboItemsAfterLogin.push(new ItemModel(x.code, x.displayName));
                    });
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }


        /**
         * Find data in DB TOPPAGE_JOB_SET
         */
        findDataOfTopPageJobSet(listJobId: any): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            ccg018.a.service.findDataOfTopPageJobSet(listJobId)
                .done(function(data) {
                    if (data.length > 0) {
                        _.forEach(data, function(x) {
                            var jobTitle = _.find(self.listJobTitle(), ['id', x.jobId]);
                            self.items.push(new TopPageJobSet(jobTitle.code, jobTitle.name, x.loginMenuCode, x.topMenuCode, x.personPermissionSet, x.jobId, x.loginSystem, x.menuClassification));
                        });
                        dfd.resolve();
                    }
                }).fail();
            return dfd.promise();
        }

        /**
         * get JobCode and JobName in table CJTMT_JOB_TITLE
         */
        searchByDate(): any {
            var self = this;
            self.items.removeAll();
            ccg018.a.service.findDataOfJobTitle(self.date())
                .done(function(data) {
                    if (data.length > 0) {
                        self.listJobTitle(data);
                        var listJobId = [];
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
            var self = this;
            var command = {
                listTopPageJobSet: ko.mapping.toJS(self.items()),
                ctgSet: self.categorySet()
            };
            ccg018.a.service.update(command)
                .done(function() {
                    self.searchByDate();
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
        constructor(code: string, name: string, loginMenuCd: string, topMenuCd: string, personPermissionSet: number, jobId: string, system: number, menuClassification: number) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.loginMenuCd = ko.observable(loginMenuCd);
            this.topMenuCd = ko.observable(topMenuCd);
            this.personPermissionSet = ko.observable(personPermissionSet);
            this.jobId = ko.observable(jobId);
            this.system = ko.observable(system);
            this.menuClassification = ko.observable(menuClassification);
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