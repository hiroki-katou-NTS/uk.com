module nts.uk.pr.view.qmm012.j.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm012.share.model;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        lstCustomes: KnockoutObservableArray<DataScreen> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('');

        itemNameCd: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        shortName: KnockoutObservable<string> = ko.observable('');
        englishName: KnockoutObservable<string> = ko.observable('');
        otherLanguageName: KnockoutObservable<string> = ko.observable('');
        categoryAtr: KnockoutObservable<number> = ko.observable(0);
        isNewMode: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            $("#J2_1").focus();
            //Fixed table
            if (/Edge/.test(navigator.userAgent)) {
                $("#fixed-table").ntsFixedTable({ height: 499, width: 825 });
            } else if (/Chrome/.test(navigator.userAgent)) {
                $("#fixed-table").ntsFixedTable({ height: 505, width: 825 });
            } else {
                $("#fixed-table").ntsFixedTable({ height: 499, width: 825 });
            }
            let categoryAtrScreenB = getShared('QMM012_B');
            if (categoryAtrScreenB != null) {
                self.categoryAtr(categoryAtrScreenB);
            }
            if (self.categoryAtr(0)) {
                self.onSelectTabB();
            } else if (self.categoryAtr(1)) {
                self.onSelectTabC();
            }
            else if (self.categoryAtr(2)) {
                self.onSelectTabD();
            }
            else if (self.categoryAtr(3)) {
                self.onSelectTabE();
            }
            else if (self.categoryAtr(4)) {
                self.onSelectTabF();
            }
        }
        onSelectTabB() {
            let self = this;
            self.categoryAtr(0);
            self.getData();
        };
        onSelectTabC() {
            let self = this;
            self.categoryAtr(1);
            self.getData();
        };
        onSelectTabD() {
            let self = this;
            self.categoryAtr(2);
            self.getData();
        };
        onSelectTabE() {
            let self = this;
            self.categoryAtr(3);
            self.getData();
        };
        onSelectTabF() {
            let self = this;
            self.categoryAtr(4);
            self.getData();
        };
        cancel() { 
            nts.uk.ui.windows.close();
        };

        getData(): JQueryPromise<any> {
            let self = this;
            block.invisible();
            service.getStatementItemAndStatementItemName(self.categoryAtr()).done(function(data: Array<IDataScreen>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["itemNameCd"]);
                    self.lstCustomes(dataSort.map(x => new DataScreen(x)));
                    self.currentCode(dataSort[0].itemNameCd);
                    self.isNewMode(false);

                    setTimeout(function () {
                        if(data.length > 15) {
                            $('.nts-fixed-body-container').addClass('has-padding');
                            $('.nts-fixed-table .scroll-header').addClass('scroll_header');
                        } else {
                            $('.nts-fixed-body-container').removeClass('has-padding');
                            $('.nts-fixed-table .scroll-header').removeClass('scroll_header');
                        }
                    }, 100);
                }
                else{
                    self.lstCustomes([]);
                    self.isNewMode(true);
                }
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        updateStatelmentItemName() {
            let self = this;

            if(!nts.uk.ui.errors.hasError()) {
                block.invisible();
                // update
                let data: Array<DataScreen> = ([]);
                _.forEach(self.lstCustomes(), item => {
                    if (_.isEmpty(item.englishName())) {
                        item.englishName = null;
                    }
                    if (_.isEmpty(item.otherLanguageName())) {
                        item.otherLanguageName = null;
                    }
                    data.push(item);
                })
                self.lstCustomes(data);
                service.updateStatementItemName(ko.toJS(self.lstCustomes())).done(() => {
                    self.getData();
                    dialog.info({messageId: "Msg_15"});
                }).fail(function (error) {
                    alertError(error);
                }).always(function () {
                    block.clear();
                });
            }
        }
    }

    export interface IDataScreen {
        categoryAtr: number;
        itemNameCd: string;
        salaryItemId: string;
        defaultAtr: number;
        valueAtr: number;
        deprecatedAtr: number;
        socialInsuaEditableAtr: number;
        intergrateCd: string;
        name: string;
        shortName: string;
        otherLanguageName: string;
        englishName: string;
    }

    export class DataScreen {
        categoryAtr: number;
        itemNameCd: KnockoutObservable<string> = ko.observable('');
        salaryItemId: KnockoutObservable<string> = ko.observable('');
        defaultAtr: KnockoutObservable<number> = ko.observable(0);
        valueAtr: KnockoutObservable<number> = ko.observable(0);
        deprecatedAtr: KnockoutObservable<number> = ko.observable(0);
        socialInsuaEditableAtr: KnockoutObservable<number> = ko.observable(0);
        intergrateCd: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        shortName: KnockoutObservable<string> = ko.observable('');
        otherLanguageName: KnockoutObservable<string> = ko.observable('');
        englishName: KnockoutObservable<string> = ko.observable('')
        constructor(params: IDataScreen) {
            let self = this;
            self.salaryItemId(params ? params.salaryItemId : '');
            self.itemNameCd(params ? params.itemNameCd : '');
            self.name(params ? params.name : '');
            self.shortName(params ? params.shortName : '');
            self.englishName(params ? params.englishName : '');
            self.otherLanguageName(params ? params.otherLanguageName : '');
        }
    }
}