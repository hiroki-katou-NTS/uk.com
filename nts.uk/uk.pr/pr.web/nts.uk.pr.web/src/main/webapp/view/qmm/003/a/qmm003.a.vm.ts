module nts.uk.pr.view.qmm003.a.viewmodel {
    
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import constants = qmm003.share.constants;

    export class ScreenModel {
        
        items2: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;
        headers: any;
        listRegions: Array<any> = constants.listRegions;
        listPrefectures: Array<any>;
        selectedResidentTaxPayee: KnockoutObservable<ResidentTaxPayee>;
        updateMode: KnockoutObservable<boolean> = ko.observable(false);
        totalRtp: KnockoutObservable<number> = ko.observable(0);
        dispTotalRtp: KnockoutObservable<string> = ko.computed(() => {
            return getText("QMM003_10", [this.totalRtp()]);
        });
        listRsdTaxPayees: Array<Node> = [];

        constructor() {
            let self = this;
            self.items2 = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.headers = ko.observableArray([getText("QMM003_9")]);
            self.listPrefectures = constants.listPrefectures;
            self.selectedResidentTaxPayee = ko.observable(new ResidentTaxPayee(null));
            self.selectedCode.subscribe(val => {
                if (_.isEmpty(val) || val.indexOf("_") == 0) { //select parent node
                    self.selectedResidentTaxPayee().setData(null);
                    self.updateMode(false);
                    $("#A3_2").focus();
                } else {
                    block.invisible();
                    service.getResidentTaxPayee(val).done(data => {
                        self.selectedResidentTaxPayee().setData(data);
                        self.updateMode(true);
                        $("#A3_3").focus();
                    }).fail(error => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
                _.defer(() => {
                    nts.uk.ui.errors.clearAll();
                });
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            self.listRsdTaxPayees = [];
            service.getAllResidentTaxPayee().done((data: Array<any>) => {
                self.totalRtp(data.length);
                let listNodes = [];
                self.listRegions.forEach(r => {
                    let regionNode = new Node(r.code, r.name, [], 0);
                    let prefectures = self.listPrefectures.filter(pr => {return pr.region == r.code});
                    let prefectureNodes = [];
                    prefectures.forEach(pr => {
                        let prefectureNode = new Node(pr.code < 10 ? "_0" + pr.code : "_" + pr.code, pr.name, [], 1);
                        if (data.length > 0) {
                            let residentTaxPayees = data.filter(d => {return d.prefectures == pr.code});
                            let residentNodes = _.map(residentTaxPayees, rs => {
                                let node = new Node(rs.code, rs.name, [], 2);
                                self.listRsdTaxPayees.push(node);
                                return node;
                            });
                            prefectureNode.children = residentNodes;
                        }
                        prefectureNodes.push(prefectureNode);
                    });
                    regionNode.children = prefectureNodes;
                    listNodes.push(regionNode);
                });
                self.items2(listNodes);
                dfd.resolve();
            }).fail(error => {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        createNew() {
            let self = this;
            self.selectedCode("");
        }

        register() {
            let self = this;
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                let command = ko.toJS(self.selectedResidentTaxPayee());
                ko.utils.extend(command, {
                    updateMode: self.updateMode()
                });
                nts.uk.pr.view.qmm003.a.service.register(command).done(() => {
                    self.startPage().done(() => {
                        info({ messageId: "Msg_15" }).then(() => {
                            self.setSelectedCode(command.code);
                        });
                    });
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }
        }

        remove() {
            let self = this;
            block.invisible();
            service.checkBeforeDelete(self.selectedCode()).done(() => {
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    let deletedIndex = _.findIndex(self.listRsdTaxPayees, r => {return r.code == self.selectedCode()});
                    let nextSelectCode = "";
                    if (self.listRsdTaxPayees.length > 1) {
                        if (deletedIndex == self.listRsdTaxPayees.length - 1) {
                            nextSelectCode = self.listRsdTaxPayees[deletedIndex - 1].code;
                        } else {
                            nextSelectCode = self.listRsdTaxPayees[deletedIndex + 1].code;
                        }
                    }
                    nts.uk.pr.view.qmm003.a.service.remove(self.selectedCode()).done(() => {
                        self.startPage().done(() => {
                            info({ messageId: "Msg_16" }).then(() => {
                                self.setSelectedCode(nextSelectCode);
                            });
                        });
                    }).fail(error => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }).ifNo(() => {
                });
            }).fail(error => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }
        
        openDialogQmm003d() {
            let self = this;
            modal("/view/qmm/003/d/index.xhtml").onClosed(() => {
                let cancel = getShared("QMM003DCancel");
                if (!cancel) {
                    self.startPage().done(() => {
                        if (self.listRsdTaxPayees.length > 0)
                            self.setSelectedCode(self.listRsdTaxPayees[0].code);
                        else
                            self.setSelectedCode("");
                    });
                }
            });
        }
        
        openDialogQmm003e() {
            let self = this;
            modal("/view/qmm/003/e/index.xhtml").onClosed(() => {
                self.selectedCode.valueHasMutated();
            });
        }
        
        setSelectedCode(value: string) {
            let self = this;
            if (self.selectedCode() == value)
                self.selectedCode.valueHasMutated();
            else 
                self.selectedCode(value);
        }
        // exportFilePdf
        exportFilePdf(){
            block.invisible();
            service.exportFilePdf()
                .done(function () {
                    block.clear();

                })
                .fail(function (error) {
                    dialog.alertError({messageId: error.messageId});
                    block.clear();
                })
                .always(function () {
                    block.clear();
                });
            ;
        }

    }

    class Node {
        id: string;
        code: string;
        name: string;
        nodeText: string;
        children: any;
        level: number; //0: region, 1: prefecture, 2: resident
        
        constructor(code: string, name: string, children: Array<Node>, level?: number) {
            let self = this;
            self.id = code;
            self.code = level == 2 ? code : "";
            self.name = level == 2 ? name : "";
            self.nodeText = level == 2 ? _.escape(code + ' ' + name) : _.escape(name);
            self.children = children;
            if (level != null) self.level = level;
        }
    }
    
    class ResidentTaxPayee {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        kanaName: KnockoutObservable<string>;
        compileStationName: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        subscriberName: KnockoutObservable<string>;
        accountNumber: KnockoutObservable<string>;
        reportCd: KnockoutObservable<string>;
        reportName: KnockoutObservable<string>;
        designationNum: KnockoutObservable<string>;
        prefectures: KnockoutObservable<number>;
        postCode: KnockoutObservable<string>;
        
        constructor(data: {code: string, name: string, kana: string, prefectures: number, reportCd: string, reportName: string, accountNumber: string, subscriberName: string, designationNum: string, postCode: string, compileStationName: string, memo: string}) {
            this.code = ko.observable(data == null ? null : data.code);
            this.name = ko.observable(data == null ? null : data.name);
            this.kanaName = ko.observable(data == null ? null : data.kana);
            this.compileStationName = ko.observable(data == null ? null : data.compileStationName);
            this.memo = ko.observable(data == null ? null : data.memo);
            this.subscriberName = ko.observable(data == null ? null : data.subscriberName);
            this.accountNumber = ko.observable(data == null ? null : data.accountNumber);
            this.reportCd = ko.observable(data == null ? null : data.reportCd); 
            this.designationNum = ko.observable(data == null ? null : data.designationNum);
            this.prefectures = ko.observable(data == null ? null : data.prefectures);
            this.postCode = ko.observable(data == null ? null : data.postCode);
            this.reportName = ko.observable(data == null ? null : data.reportName);
        }
        
        openDialogQmm003c() {
            let self = this;
            setShared("QMM003CParam", self.reportCd());
            modal("/view/qmm/003/c/index.xhtml").onClosed(() => {
                let selected = getShared("QMM003CResult");
                if (selected) {
                    self.reportCd(selected.code);
                    self.reportName(selected.name);
                }
            });
        }
        
        openDialogQmm003b() {
            let self = this;
            modal("/view/qmm/003/b/index.xhtml").onClosed(() => {
                let code: string = getShared("QMM003BResult");
                if (code) {
                    block.invisible()
                    nts.uk.pr.view.qmm003.a.service.getResidentTaxPayeeZero(code).done(data => {
                        self.setData(data);
                        let reportName = __viewContext.screenModel.listRsdTaxPayees.filter(d => {return d.code == self.reportCd()});
                        self.reportName(_.isEmpty(reportName) ? "" : reportName[0].name);
                        $("#A3_3").focus();
                        nts.uk.ui.errors.clearAll();
                    }).fail(error => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
            });
        }
        
        setData(data: any) {
            let self = this;
            self.code(data == null ? null : data.code);
            self.name(data == null ? null : data.name);
            self.kanaName(data == null ? null : data.kanaName);
            self.prefectures(data == null ? null : data.prefectures);
            self.reportCd(data == null ? null : data.reportCd);
            self.accountNumber(data == null ? null : data.accountNumber);
            self.subscriberName(data == null ? null : data.subscriberName);
            self.designationNum(data == null ? null : data.designationNum);
            self.postCode(data == null ? null : data.postCode);
            self.compileStationName(data == null ? null : data.compileStationName);
            self.memo(data == null ? null : data.memo);
            self.reportName(data == null ? null : data.reportName);
        }
        
    }
    
}

