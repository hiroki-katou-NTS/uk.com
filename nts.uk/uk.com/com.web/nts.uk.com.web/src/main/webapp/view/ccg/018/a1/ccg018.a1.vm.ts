module ccg018.a1.viewmodel {
    import blockUI = nts.uk.ui.block;

    export class ScreenModel extends base.viewModel.ScreenModelBase {
        date: KnockoutObservable<string>;
        items: KnockoutObservableArray<TopPageJobSet> = ko.observableArray([]);
        isVisible: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<number>;
        listJobTitle: KnockoutObservableArray<any>;
        roundingRules: KnockoutObservableArray<any>;
        isEmpty: KnockoutObservable<boolean>;
        referenceDate: string = nts.uk.resource.getText("CCG018_6");

        constructor(baseModel: base.result.BaseResultModel) {
            super(baseModel);
            let self = this;
            self.screenTemplateUrl("../a1/index.xhtml");
            self.categorySet(baseModel.categorySet);
            self.listJobTitle = ko.observableArray([]);
            self.date = ko.observable(new Date().toISOString());
            self.isVisible = ko.computed(function() {
                return !!self.categorySet();
            });
            self.comboItemsAfterLogin(baseModel.comboItemsAfterLogin);
            self.comboItemsAsTopPage(baseModel.comboItemsAsTopPage);
            self.roundingRules = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("CCG018_13") },
                { code: 0, name: nts.uk.resource.getText("CCG018_14") }
            ]);

            self.isEmpty = ko.computed(function() {
                return !nts.uk.ui.errors.hasError();
            });

            self.categorySet.subscribe((newValue) => {
                if (newValue == 0) {
                    $("#width-tbody").addClass("width-tbody");
                } else {
                    $("#width-tbody").removeClass("width-tbody");
                }
            });

            self.checkCategorySet();
            $('#A2-2').focus();
        }

        start(): any {
            let self = this;
            self.searchByDate();
        }

        checkCategorySet(): void {
            let self = this;
            if (self.categorySet() == null) {
                self.categorySet(1);
                self.openDialogC();
            }
        }

        /**
         * Find data in DB TOPPAGE_JOB_SET
         */
        findDataOfTopPageJobSet(listJobId: any): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.items.removeAll();
            service.findDataOfTopPageJobSet(listJobId)
                .done(function(data) {
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
                }).fail(function() {
                    dfd.reject();
                });
            return dfd.promise();
        }

        /**
         * get JobId, JobCode and JobName in table CJTMT_JOB_TITLE
         * then call function findDataOfTopPageJobSet()
         */
        searchByDate(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            blockUI.invisible();
            self.items([]);
            nts.uk.ui.errors.clearAll();
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
                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                }).always(function() {
                    blockUI.clear();
                });
            return dfd.promise();
        }


        /**
         * Update/insert data in TOPPAGE_JOB_SET
         */
        save(): void {
            let self = this;
            if (self.items().length == 0) {
                return;
            }
            let dfd = $.Deferred();
            blockUI.invisible();
            let command = {
                listTopPageJobSet: ko.mapping.toJS(self.items()),
                ctgSet: self.categorySet()
            };
            ccg018.a1.service.update(command)
                .done(function() {
                    self.searchByDate();
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error.message);
                }).always(function() {
                    blockUI.clear();
                });
        }

        /**
         * Open dialog C
         */
        openDialogC(): void {
            let self = this;
            blockUI.invisible();
            // the default value of categorySet = undefined
            nts.uk.ui.windows.setShared('categorySet', self.categorySet());
            nts.uk.ui.windows.sub.modal("/view/ccg/018/c/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                if (nts.uk.ui.windows.getShared('categorySetC') != undefined) {
                    if (self.categorySet() != nts.uk.ui.windows.getShared('categorySetC')) {
                        self.categorySet(nts.uk.ui.windows.getShared('categorySetC'));
                    }
                }
            });
            blockUI.clear();
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


}