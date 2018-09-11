module ccg018.a.viewmodel {

    export class ScreenModel {
        title: KnockoutObservable<string> = ko.observable('');
        tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
            new TabModel({ id: 'a1', name: nts.uk.resource.getText('CCG018_1'), active: true, display: true, templateUrl: "jobtitle-template" }),
            new TabModel({ id: 'b', name: nts.uk.resource.getText('CCG018_2'), display: true, templateUrl: "person-template" }),
        ]);
        currentTab: KnockoutObservable<TabModel>;
        baseModel: base.result.BaseResultModel;

        constructor() {
            let self = this;
            self.currentTab = ko.observable(self.tabs()[0]);
            self.baseModel = new base.result.BaseResultModel();
            self.tabs().map((t) => {
                    // set title for tab

                    if (t.active() == true) {
                        self.title(t.name);
                        self.changeTab(t);
                    }
                });
        };

        /**
         * start page
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            $.when(self.findBySystemMenuCls(), self.findDataForAfterLoginDis(), self.findByCId()).done(function() {
                dfd.resolve();
            }).fail(function(err) {
                dfd.reject();
            });

            return dfd.promise();
        }

        changeTab(tab: TabModel): any {
            let self = this,
                oldtab: TabModel = _.find(self.tabs(), t => t.active());
            tab.active(true);
            self.currentTab(tab);
            self.title(tab.name());
            self.tabs().map(t => (t.id() != tab.id()) && t.active(false));

            // Clean binding area.
            var resultArea = $(".screen-content");
            resultArea.html("");
            //TODO: cai nay co ve hok can anh a,
            //ko.cleanNode(resultArea.get(0));

            // call start function on view at here
            switch (tab.id()) {
                case 'a1':
                    self.findByCId().done(function(){
                        var viewmodelA1 = new ccg018.a1.viewmodel.ScreenModel(self.baseModel);
                        $(resultArea).load(viewmodelA1.screenTemplateUrl(), function() {
                            viewmodelA1.searchByDate().done(function() {
                                ko.applyBindings(viewmodelA1, resultArea.children().get(0));
                                ko.applyBindings(viewmodelA1, resultArea.children().get(1));
                                if (viewmodelA1.categorySet() == 0) {
                                    $("#width-tbody").addClass("width-tbody");
                                } else {
                                    $("#width-tbody").removeClass("width-tbody");
                                }
                                $('#A2-2').focus();
                            });
                        });
                    });
                    break;
                case 'b':
                    self.findByCId().done(function(){
                        var viewmodelB = new ccg018.b.viewmodel.ScreenModel(self.baseModel);
                        $(resultArea).load(viewmodelB.screenTemplateUrl(), function() {
                            viewmodelB.start().done(function() {
                                ko.applyBindings(viewmodelB, resultArea.children().get(0));
                                ko.applyBindings(viewmodelB, resultArea.children().get(1));
                                _.defer(() => {
                                    viewmodelB.bindGrid();
                                    viewmodelB.initCCG001();
                                });

                            });
                        });
                    });
                    break;
            }
            //TODO: cai nay co ve hok can anh a,
            //$('.screen-' + tab.id().toLowerCase()).trigger('click');
        }

        /**
         * find data in table STANDARD_MENU with companyId and 
         * afterLoginDisplay = 1 (display)  or System = 0(common) and MenuClassification = 8(top page)
         */
        findDataForAfterLoginDis(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.baseModel.comboItemsAfterLogin = [];
            service.findDataForAfterLoginDis()
                .done(function(data) {
                    self.baseModel.comboItemsAfterLogin.push(new ComboBox({
                        code: '',
                        name: '未設定',
                        system: 0,
                        menuCls: 0
                    }));
                    _.forEach(data, function(x) {
                        self.baseModel.comboItemsAfterLogin.push(new ComboBox({
                            code: x.code,
                            name: x.displayName,
                            system: x.system,
                            menuCls: x.classification
                        }));
                    });
                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });
            return dfd.promise();
        }

        /**
         * Find data in table STANDARD_MENU base on CompanyId and System = 0(common) and MenuClassification = 8(top page)
         * Return array comboItemsAsTopPage 
         */
        findBySystemMenuCls(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.baseModel.comboItemsAsTopPage = [];
            service.findBySystemMenuCls()
                .done(function(data) {
                    if (data.length >= 0) {
                        self.baseModel.comboItemsAsTopPage.push(new ComboBox({
                            code: '',
                            name: '未設定',
                            system: 0,
                            menuCls: 0
                        }));

                        _.forEach(data, function(x) {
                            self.baseModel.comboItemsAsTopPage.push(new ComboBox({
                                code: x.code,
                                name: x.displayName,
                                system: x.system,
                                menuCls: x.classification
                            }));
                        });
                    }
                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });
            return dfd.promise();
        }

        /**
         * get categorySet in DB TOPPAGE_SET base on companyId
         */
        findByCId(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.findByCId()
                .done(function(data) {
                    if (!(!!data)) {
                        //self.openDialogC();
                        self.baseModel.categorySet = null;
                    } else {
                        //self.categorySet(data.ctgSet);
                        self.baseModel.categorySet = data.ctgSet;
                    }
                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });
            return dfd.promise();
        }
    }

    class TabModel {
        id: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        active: KnockoutObservable<boolean>;
        display: KnockoutObservable<boolean>;
        templateUrl: KnockoutObservable<string>;

        constructor(param: any) {
            this.id = ko.observable(param.id);
            this.name = ko.observable(param.name);
            this.active = ko.observable(param.active || false);
            this.display = ko.observable(param.display || false);
            this.templateUrl = ko.observable(param.templateUrl);
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