module ccg018.a1.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        items: KnockoutObservableArray<TopPageJobSet> = ko.observableArray([]);
        isVisible: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<number>;
        listJobTitle: KnockoutObservableArray<any>;
        comboItemsAfterLogin: KnockoutObservableArray<ComboBox>;
        comboItemsAsTopPage: KnockoutObservableArray<ComboBox>;
        //appear/disappear header of scroll on UI
        isHeaderScroll: KnockoutObservable<boolean>;

        roundingRules: KnockoutObservableArray<any>;

        constructor() {
            let self = this;
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
                $('#A2-2').focus();
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
                        self.comboItemsAsTopPage.push(new ComboBox({
                            code: '',
                            name: '未設定',
                            system: 0,
                            menuCls: 0
                        }));

                        _.forEach(data, function(x) {
                            self.comboItemsAsTopPage.push(new ComboBox({
                                code: x.code,
                                name: x.displayName,
                                system: x.system,
                                menuCls: x.classification
                            }));
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
                    self.comboItemsAfterLogin.push(new ComboBox({
                        code: '',
                        name: '未設定',
                        system: 0,
                        menuCls: 0
                    }));
                    _.forEach(data, function(x) {
                        self.comboItemsAfterLogin.push(new ComboBox({
                            code: x.code,
                            name: x.displayName,
                            system: x.system,
                            menuCls: x.classification
                        }));
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
            self.items.removeAll();
            ccg018.a1.service.findDataOfTopPageJobSet(listJobId)
                .done(function(data) {
                    if (data.length > 0) {
                        _.forEach(listJobId, function(x) {
                            let dataObj: any = _.find(data, ['jobId', x]),
                                jobTitle = _.find(self.listJobTitle(), ['id', x]);

                            if (dataObj) {
                                self.items.push(new TopPageJobSet({
                                    code: jobTitle.code,
                                    name: jobTitle.name,
                                    loginMenuCd: dataObj.loginMenuCode,
                                    topMenuCd: dataObj.topMenuCode,
                                    personPermissionSet: dataObj.personPermissionSet,
                                    jobId: x,
                                    system: dataObj.loginSystem,
                                    menuClassification: dataObj.menuClassification
                                }));
                            } else {
                                self.items.push(new TopPageJobSet({
                                    code: jobTitle.code,
                                    name: jobTitle.name,
                                    loginMenuCd: '',
                                    topMenuCd: '',
                                    personPermissionSet: 0,
                                    jobId: x,
                                    system: 0,
                                    menuClassification: 0
                                }));
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

        /**
         * Jump to screen CCG015
         */
        jumpToCcg015(): void {
            nts.uk.request.jump("/view/ccg/015/a/index.xhtml");
        }
    }

    interface ITopPageJobSet {
        code: string,
        name: string,
        loginMenuCd: string,
        topMenuCd: string,
        personPermissionSet: number,
        jobId: string,
        system: number,
        menuClassification: number
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
        //beacause there can exist same code, so create uniqueCode = loginMenuCd+ system+ menuClassification
        uniqueCode: KnockoutObservable<string> = ko.observable('');

        constructor(param: ITopPageJobSet) {
            let self = this;

            self.code = ko.observable(param.code);
            self.name = ko.observable(param.name);
            self.loginMenuCd = ko.observable(param.loginMenuCd);
            self.topMenuCd = ko.observable(param.topMenuCd);
            self.personPermissionSet = ko.observable(param.personPermissionSet);
            self.jobId = ko.observable(param.jobId);
            self.system = ko.observable(param.system);
            self.menuClassification = ko.observable(param.menuClassification);

            self.uniqueCode(nts.uk.text.format("{0}{1}{2}", param.loginMenuCd, param.system, param.menuClassification));

            self.uniqueCode.subscribe(function() {
                //if uniqueCode = '00' return loginMenuCd = ''
                self.loginMenuCd(self.uniqueCode().length > 2 ? self.uniqueCode().slice(0, 4) : '');
                self.system(+(self.uniqueCode().slice(-2, -1)));
                self.menuClassification(+(self.uniqueCode().slice(-1)));
            });
        }
    }

    interface IComboBox {
        code: string;
        name: string;
        system: number;
        menuCls: number;
        uniqueCode?: string;
    }

    class ComboBox {
        code: string;
        name: string;
        system: number;
        menuCls: number;
        uniqueCode: string;

        constructor(param: IComboBox) {
            this.code = param.code;
            this.name = param.name;
            this.system = param.system;
            this.menuCls = param.menuCls;
            this.uniqueCode = nts.uk.text.format("{0}{1}{2}", param.code, param.system, param.menuCls);
        }
    }
}