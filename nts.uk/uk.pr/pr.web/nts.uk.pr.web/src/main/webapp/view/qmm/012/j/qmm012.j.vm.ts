module nts.uk.pr.view.qmm012.j.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import model = nts.uk.pr.view.qmm012.share.model;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        lstCustomes: KnockoutObservableArray<IDataScreen> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('');

        itemNameCd: KnockoutObservable<string> = ko.observable('aa');
        name: KnockoutObservable<string> = ko.observable('');
        shortName: KnockoutObservable<string> = ko.observable('');
        englishName: KnockoutObservable<string> = ko.observable('');
        otherLanguageName: KnockoutObservable<string> = ko.observable('');
        categoryAtr: KnockoutObservable<number> = ko.observable(0);
        columns: KnockoutObservableArray<any> = ko.observableArray<any>([
                { headerText: getText('QMM012_20'), key: 'itemNameCd', width: 50, formatter: _.escape },
                { headerText: getText('QMM012_21'), key: 'name', width: 200, formatter: _.escape },
                { headerText: getText('QMM012_25'), key: 'shortName', width: 150, formatter: _.escape },
                { headerText: getText('QMM012_96'), key: 'englishName', width: 150,
                     template: "<input type='text' value='${englishName}' />"          
                },
                { headerText: getText('QMM012_97'), key: 'otherLanguageName', width: 200, formatter: _.escape }
            ]);
        constructor() {
            let self = this;
            block.invisible();
            service.getStatementItemAndStatementItemName(self.categoryAtr()).done(function(data: Array<IDataScreen>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["itemNameCd"]);
                    self.lstCustomes(dataSort);
                    self.currentCode(self.lstCustomes()[0].itemNameCd);
                }
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }
        onSelectTabB() { };
        onSelectTabC() { };
        onSelectTabD() { };
        onSelectTabE() { };
        onSelectTabF() { };
        register() { };
        cancel() { };
    }
    
    export interface IDataScreen{
        itemNameCd: string,
        name: string,
        shortName: string,
        otherLanguageName: string, 
        englishName: string
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
                self.itemNameCd(params ? params.itemNameCd : '');
                self.name(params ? params.name : '');
                self.shortName(params ? params.shortName : '');
                self.englishName(params ? params.englishName : '');
                self.otherLanguageName(params ? params.otherLanguageName : '');
        }
    }
}