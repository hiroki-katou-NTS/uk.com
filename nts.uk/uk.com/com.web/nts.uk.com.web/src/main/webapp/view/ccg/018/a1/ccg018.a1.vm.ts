module ccg018.a1.viewmodel {
    import blockUI = nts.uk.ui.block;

    export class ScreenModel extends base.viewModel.ScreenModelBase {
        date: KnockoutObservable<string>;
        items: KnockoutObservableArray<TopPageJobSet> = ko.observableArray([]);
        isVisible: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<number>;
        listJobTitle: KnockoutObservableArray<any>;
        isEmpty: KnockoutObservable<boolean>;
        referenceDate: string = nts.uk.resource.getText("CCG018_6");
        listSwitchDate: KnockoutObservableArray<number> = ko.observableArray();
        listRoleSet: KnockoutObservableArray<RoleSet> = ko.observableArray([]);
        lisTopPageRoleSet: KnockoutObservableArray<TopPageRoleSet> = ko.observableArray([]);
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
            self.listSwitchDate(self.getSwitchDateLists());
            $('#A2-2').focus();
            const vm = this;
            vm.getRoleSet();
            vm.getAllTopPageRoleSet();
        }

        //ドメインモデル「ロールセット」を取得する
        private getRoleSet() {
            const vm = this;
            blockUI.invisible();
            service.findAllRoleSet()
            .then((data: any) => {
                vm.listRoleSet(data);
            })
            .always(() => blockUI.clear());
        }

        // ドメインモデル「権限別トップページ設定」を取得
        private getAllTopPageRoleSet() {
            const vm = this;
            blockUI.invisible();
            service.findAllTopPageRoleSet()
            .then((data) => {
                _.forEach(vm.listRoleSet(), (x: RoleSet) => {
                    const dataObj: any = _.find(data, ['roleSetCode', x.roleSetCd]);
                    if (dataObj) {
                        vm.lisTopPageRoleSet().push(new TopPageRoleSet({
                            roleSetCode: x.roleSetCd,
                            name: x.roleSetName,
                            loginMenuCode: dataObj.loginMenuCode,
                            topMenuCode: dataObj.topMenuCode,
                            switchingDate: dataObj.switchingDate,
                            system: dataObj.system,
                            menuClassification: dataObj.menuClassification
                        }));
                    } else {
                        vm.lisTopPageRoleSet().push(new TopPageRoleSet({
                            roleSetCode: x.roleSetCd,
                            name: x.roleSetName,
                            loginMenuCode: '',
                            topMenuCode: '',
                            switchingDate: 0,
                            system: 0,
                            menuClassification: 0
                        }));
                    }
                });
            })
            .always(() => blockUI.clear());
        }

        checkCategorySet(): void {
            let self = this;
            if (self.categorySet() == null) {
                self.categorySet(1);
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
                                switchingDate: dataObj.switchingDate,
                                jobId: x,
                                system: dataObj.system,
                                menuClassification: dataObj.menuClassification
                            }));
                        } else {
                            self.items.push(new TopPageJobSet({
                                code: jobTitle.code,
                                name: jobTitle.name,
                                loginMenuCd: '',
                                topMenuCd: '',
                                personPermissionSet: 0,
                                switchingDate: 0,
                                jobId: x,
                                system: 0,
                                menuClassification: 0
                            }));
                        }
                    });
                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                }).always(function() {
                    blockUI.clear();
                });
            return dfd.promise();
        }

        /**
         * Update/insert data in TOPPAGE_ROLE_SET
         */
        save(): void {
            const vm = this;
            if (vm.lisTopPageRoleSet().length === 0) {
                return;
            }
            let dfd = $.Deferred();
            blockUI.invisible();
            const command = ko.mapping.toJS(vm.lisTopPageRoleSet());
            ccg018.a1.service.update(command)
                .done(() => {
                    blockUI.clear();
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail((error) => {
                    nts.uk.ui.dialog.alertError(error.message);
                }).always(() => blockUI.clear());
        }

        showNote() {
            $('#popup-show-note').remove();
            const $table1 = $('#A2-4');
            $('<div/>')
                .attr('id', 'popup-show-note')
                .appendTo($table1);
            const $popUpShowNote = $('#popup-show-note');
            $popUpShowNote.ntsPopup({
                showOnStart: false,
                dismissible: true,
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: '#A3_1'
                }
            });
            $('<div/>')
                .text(nts.uk.resource.getText('CCG018_52'))
                .appendTo($popUpShowNote);
            $popUpShowNote.ntsPopup('show');
        }

        private getSwitchDateLists() {
          const list: any = [];
          list.push({value: 0, text: nts.uk.resource.getText('CCG018_44')});
          _.range(1, 31).forEach(current => {
						list.push({value: current, text: current});
          });
          return list;
        }
    }

    interface ITopPageJobSet {
        code: string,
        name: string,
        loginMenuCd: string,
        topMenuCd: string,
        personPermissionSet: number,
        switchingDate: number,
        jobId: string,
        system: number,
        menuClassification: number
    }

    class TopPageJobSet {
        roleSetCode: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        loginMenuCd: KnockoutObservable<string>;
        topMenuCd: KnockoutObservable<string>;
        personPermissionSet: KnockoutObservable<number>;
        jobId: KnockoutObservable<string>;
        system: KnockoutObservable<number>;
        switchingDate: KnockoutObservable<number> = ko.observable(0);
        menuClassification: KnockoutObservable<number>;
        //beacause there can exist same code, so create uniqueCode = loginMenuCd+ system+ menuClassification
        uniqueCode: KnockoutObservable<string> = ko.observable('');
        constructor(param: ITopPageJobSet) {
            const self = this;
            self.roleSetCode = ko.observable(param.code);
            self.name = ko.observable(param.name);
            self.loginMenuCd = ko.observable(param.loginMenuCd);
            self.topMenuCd = ko.observable(param.topMenuCd);
            self.personPermissionSet = ko.observable(param.personPermissionSet);
            self.jobId = ko.observable(param.jobId);
            self.switchingDate = ko.observable(param.switchingDate);
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
    export class RoleSet {
        roleSetCd: string;
        roleSetName: string;
        constructor(roleSetCd: string, roleSetName: string) {
          this.roleSetCd = roleSetCd;
          this.roleSetName = roleSetName;
        }
      }
      export class ITopPageRoleSet {
        roleSetCode: string;
        switchingDate: number;
        loginMenuCode: string;
        topMenuCode: string;
        menuClassification: number;
        system: number;
        name: string;
      }
      class TopPageRoleSet {
        roleSetCode: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        loginMenuCode: KnockoutObservable<string>;
        topMenuCode: KnockoutObservable<string>;
        system: KnockoutObservable<number>;
        switchingDate: KnockoutObservable<number> = ko.observable(0);
        menuClassification: KnockoutObservable<number>;
        //beacause there can exist same code, so create uniqueCode = loginMenuCd+ system+ menuClassification
        uniqueCode: KnockoutObservable<string> = ko.observable('');
        constructor(param: ITopPageRoleSet) {
          const vm = this;
          vm.roleSetCode = ko.observable(param.roleSetCode);
          vm.name = ko.observable(param.name);
          vm.loginMenuCode = ko.observable(param.loginMenuCode);
          vm.topMenuCode = ko.observable(param.topMenuCode);
          vm.switchingDate = ko.observable(param.switchingDate);
          vm.system = ko.observable(param.system);
          vm.menuClassification = ko.observable(param.menuClassification);
          vm.uniqueCode(nts.uk.text.format("{0}{1}{2}", param.loginMenuCode, param.system, param.menuClassification));
          vm.uniqueCode.subscribe(function() {
            vm.loginMenuCode(vm.uniqueCode().length > 2 ? vm.uniqueCode().slice(0, 4) : '');
            vm.system(+(vm.uniqueCode().slice(-2, -1)));
            vm.menuClassification(+(vm.uniqueCode().slice(-1)));
          });
        }
      }
      export class IStandardMenu {
        code: string;
        menuClassification: number;
        displayName: string;
        system: number;
        uniqueCode: string;
      }
      class StandardMenu {
        code: KnockoutObservable<string>;
        menuClassification: KnockoutObservable<number>;
        displayName: KnockoutObservable<string>;
        system: KnockoutObservable<number>;
        uniqueCode: KnockoutObservable<string>;
        constructor(param: IStandardMenu) {
          const vm = this;
          vm.code = ko.observable(param.code);
          vm.menuClassification = ko.observable(param.menuClassification);
          vm.displayName = ko.observable(param.displayName);
          vm.system = ko.observable(param.system);
          vm.uniqueCode(nts.uk.text.format("{0}{1}{2}", param.code, param.system, param.menuClassification));
          vm.uniqueCode.subscribe(function() {
              //if uniqueCode = '00' return loginMenuCd = ''
              vm.code(vm.uniqueCode().length > 2 ? vm.uniqueCode().slice(0, 4) : '');
              vm.system(+(vm.uniqueCode().slice(-2, -1)));
              vm.menuClassification(+(vm.uniqueCode().slice(-1)));
          });
        }
      }
}