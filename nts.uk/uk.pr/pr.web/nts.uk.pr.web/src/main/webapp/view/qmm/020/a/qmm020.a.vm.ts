module nts.uk.pr.view.qmm020.a.viewmodel {
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm020.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;

    export class ScreenModel {
        masterUse: KnockoutObservable<number> = ko.observable();
        individualUse: KnockoutObservable<number> = ko.observable();
        usageMaster: KnockoutObservable<number> = ko.observable();
        selectedH: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {

        }

        startPage(): JQueryPromise<any> {
            block.invisible();
            let self = this;
            let dfd = $.Deferred();
            service.getStateUseUnitSettingById().done((data: any) => {
                if (data) {
                    self.masterUse(data.masterUse);
                    self.individualUse(data.individualUse);
                    self.usageMaster(data.usageMaster);
                    self.activeSideBar(data);
                } else {
                    self.activeSideBar(null);
                }
                dfd.resolve();
            }).fail((err) => {
                dfd.reject();
                if (err)
                    dialog.alertError(err);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        activeSideBar(setting) {
            if (!setting) {
                $("#sidebar").ntsSideBar("hide", 1);
                $("#sidebar").ntsSideBar("hide", 2);
                $("#sidebar").ntsSideBar("hide", 3);
                $("#sidebar").ntsSideBar("hide", 4);
                $("#sidebar").ntsSideBar("hide", 5);
                $("#sidebar").ntsSideBar("hide", 6);
                return;
            }

            if (setting.masterUse == 1) {
                if (setting.usageMaster == 0) {
                    $("#sidebar").ntsSideBar("show", 1);
                } else {
                    $("#sidebar").ntsSideBar("hide", 1);
                }
                if (setting.usageMaster == 1) {
                    $("#sidebar").ntsSideBar("show", 2);
                } else {
                    $("#sidebar").ntsSideBar("hide", 2);
                }
                if (setting.usageMaster == 2) {
                    $("#sidebar").ntsSideBar("show", 3);
                } else {
                    $("#sidebar").ntsSideBar("hide", 3);
                }
                if (setting.usageMaster == 3) {
                    $("#sidebar").ntsSideBar("show", 4);
                } else {
                    $("#sidebar").ntsSideBar("hide", 4);
                }
                if (setting.usageMaster == 4) {
                    $("#sidebar").ntsSideBar("show", 5);
                } else {
                    $("#sidebar").ntsSideBar("hide", 5);
                }
            } else {
                $("#sidebar").ntsSideBar("hide", 1);
                $("#sidebar").ntsSideBar("hide", 2);
                $("#sidebar").ntsSideBar("hide", 3);
                $("#sidebar").ntsSideBar("hide", 4);
                $("#sidebar").ntsSideBar("hide", 5);
            }
            if (setting.individualUse == 1) {
                $("#sidebar").ntsSideBar("show", 6);
            } else {
                $("#sidebar").ntsSideBar("hide", 6);
            }
        }

        openScreenL() {
            let self = __viewContext.viewModel.viewmodelA;
            modal("/view/qmm/020/l/index.xhtml").onClosed(() => {
                let params = getShared(model.PARAMETERS_SCREEN_L.OUTPUT);
                if (isNullOrUndefined(params)) return;
                if (!params.isSubmit) return;
                block.invisible();
                service.getStateUseUnitSettingById().done((data: any) => {
                    if (data) {
                        self.masterUse(data.masterUse);
                        self.individualUse(data.individualUse);
                        self.usageMaster(data.usageMaster);
                        self.activeSideBar(data);
                    }
                }).fail((err) => {
                    if (err)
                        dialog.alertError(err);
                }).always(() => {
                    block.clear();
                });
            });
        }

        onSelectTabB() {
            __viewContext.viewModel.viewmodelB.startPage().done(function () {
                nts.uk.ui.errors.clearAll();
                if (__viewContext.viewModel.viewmodelB.mode() === model.MODE.NO_REGIS) {
                    __viewContext.viewModel.viewmodelB.enableEditHisButton(false);
                    __viewContext.viewModel.viewmodelB.enableAddHisButton(true);
                    __viewContext.viewModel.viewmodelB.enableRegisterButton(false);
                    __viewContext.viewModel.viewmodelB.openScreenJ();
                } else {
                    __viewContext.viewModel.viewmodelB.enableEditHisButton(true);
                    __viewContext.viewModel.viewmodelB.enableAddHisButton(true);
                    __viewContext.viewModel.viewmodelB.enableRegisterButton(true);
                }
                __viewContext.viewModel.viewmodelB.newHistoryId(null);
                $("#B1_5_container").focus();
            });
        };

        onSelectTabC() {
            __viewContext.viewModel.viewmodelC.startPage().done(function () {
                nts.uk.ui.errors.clearAll();
                if (__viewContext.viewModel.viewmodelC.mode() === model.MODE.NO_REGIS) {
                    __viewContext.viewModel.viewmodelC.enableEditHisButton(false);
                    __viewContext.viewModel.viewmodelC.enableAddHisButton(true);
                    __viewContext.viewModel.viewmodelC.enableRegisterButton(false);
                } else {
                    __viewContext.viewModel.viewmodelC.enableEditHisButton(true);
                    __viewContext.viewModel.viewmodelC.enableAddHisButton(true);
                    __viewContext.viewModel.viewmodelC.enableRegisterButton(true);
                }
                __viewContext.viewModel.viewmodelC.newHistoryId(null);
                $("#C1_5_container").focus();
            });
        };

        onSelectTabD() {
            __viewContext.viewModel.viewmodelD.startPage().done(function () {
                nts.uk.ui.errors.clearAll();
                __viewContext.viewModel.viewmodelD.enableEditHisButton(true);
                __viewContext.viewModel.viewmodelD.enableAddHisButton(true);
                $("#D1_5_container").focus();
            });

        };

        onSelectTabE() {
            __viewContext.viewModel.viewmodelE.initScreen(null);
            $("#E1_5_container").focus();
        };

        onSelectTabI() {
            __viewContext.viewModel.viewmodelA.selectedH(false);
            __viewContext.viewModel.viewmodelI.initScreen().done(() => {
                __viewContext.viewModel.viewmodelI.loadCCg001();
                $("#I2_1_container").focus();
            });
        };

        onSelectTabF() {
            __viewContext.viewModel.viewmodelF.initScreen(null);
            $("#F1_5_container").focus();
        };

        onSelectTabG() {
            __viewContext.viewModel.viewmodelG.initScreen(null);
            $("#G1_5_container").focus();
        };

        onSelectTabH() {
            __viewContext.viewModel.viewmodelA.selectedH(true);
            __viewContext.viewModel.viewmodelH.initScreen();
            __viewContext.viewModel.viewmodelH.loadCCG001();
            $("#emp-component").focus();
        };
    }
}