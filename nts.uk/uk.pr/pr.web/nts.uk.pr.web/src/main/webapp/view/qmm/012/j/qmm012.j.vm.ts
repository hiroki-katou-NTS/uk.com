module nts.uk.pr.view.qmm012.j.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm012.share.model;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import validation = nts.uk.ui.validation;

    export class ScreenModel {
        statementItems: Array<IDataScreen> = [];
        currentCode: KnockoutObservable<string> = ko.observable('');

        itemNameCd: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        shortName: KnockoutObservable<string> = ko.observable('');
        englishName: KnockoutObservable<string> = ko.observable('');
        otherLanguageName: KnockoutObservable<string> = ko.observable('');
        categoryAtr: KnockoutObservable<number> = ko.observable(0);
        isNewMode: KnockoutObservable<boolean> = ko.observable(false);

        englishNameValidator = new validation.StringValidator(getText("QMM012_120"), "EnglishName", { required: false });
        otherLanguageNameValidator = new validation.StringValidator(getText("QMM012_121"), "OtherLanguageName", { required: false });
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
            self.loadGrid();
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
                self.statementItems = [];
                $("#gridStatement").ntsGrid("destroy");
                if (data && data.length > 0) {
                    self.statementItems = _.sortBy(data, ["itemNameCd"]);
                    self.currentCode(self.statementItems[0].itemNameCd);
                    self.isNewMode(false);
                }
                else{
                    self.currentCode(null);
                    self.isNewMode(true);
                }
                self.loadGrid();
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        updateStatelmentItemName() {
            let self = this;
            let statementItems: Array<IDataScreen> = $("#gridStatement").igGrid("option", "dataSource")
            // update
            _.forEach(statementItems, (item: IDataScreen) => {
                if (_.isEmpty(item.englishName)) {
                    item.englishName = null;
                }
                if (_.isEmpty(item.otherLanguageName)) {
                    item.otherLanguageName = null;
                }
            })
            self.validateForm(statementItems);
            if(nts.uk.ui.errors.hasError()) {
                return;
            }
            block.invisible();

            service.updateStatementItemName(statementItems).done(() => {
                self.getData();
                dialog.info({messageId: "Msg_15"});
            }).fail(function (error) {
                alertError(error);
            }).always(function () {
                block.clear();
            });
        }

        loadGrid(){
            let self = this;
            $("#gridStatement").ntsGrid({
                width: '807px',
                height: '459px',
                dataSource: self.statementItems,
                primaryKey: 'itemNameCd',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    {headerText: getText("QMM012_32"), key: 'itemNameCd', dataType: 'string', width: '50px'},
                    {headerText: getText("QMM012_33"), key: 'name', dataType: 'string', width: '220px'},
                    {headerText: getText("QMM012_35"), key: 'shortName', dataType: 'string', width: '140px'},
                    {
                        headerText: getText("QMM012_120"), key: 'englishName', dataType: 'string', width: '190px',
                        ntsControl: 'TextEditor'
                    },
                    {
                        headerText: getText("QMM012_121"), key: 'otherLanguageName', dataType: 'string', width: '207px',
                        ntsControl: 'TextEditor'
                    },
                ],
                features: [],
                ntsControls: [
                    {name: 'TextEditor', controlType: 'TextEditor', constraint: {valueType: 'String', required: false}}
                ],
            });
        }

        validateForm(statementItems: Array<IDataScreen>){
            let self = this,
                check: any;
            nts.uk.ui.errors.clearAll();
            _.each(statementItems, (item: IDataScreen) => {
                check = self.englishNameValidator.validate(item.englishName);
                if(!check.isValid) {
                    self.setErrorEnglishName(item.itemNameCd, check.errorCode, check.errorMessage)
                }
                check = self.otherLanguageNameValidator.validate(item.otherLanguageName);
                if(!check.isValid) {
                    self.setErrorOtherLanguageName(item.itemNameCd, check.errorCode, check.errorMessage)
                }
            })
        }

        setErrorEnglishName(id: string, messageId: any, message: any) {
            $("#gridStatement").find(".nts-grid-control-englishName-" + id + " input").ntsError('set', {
                messageId: messageId,
                message: message
            });
        }

        setErrorOtherLanguageName(id: string, messageId: any, message: any) {
            $("#gridStatement").find(".nts-grid-control-otherLanguageName-" + id + " input").ntsError('set', {
                messageId: messageId,
                message: message
            });
        }
    }

    export interface IDataScreen {
        categoryAtr: number;
        itemNameCd: string;
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
}