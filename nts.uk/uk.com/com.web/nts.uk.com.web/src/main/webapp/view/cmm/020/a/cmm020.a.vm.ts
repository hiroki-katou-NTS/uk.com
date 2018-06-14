module nts.uk.com.view.cmm020.a {
    //    import MasterCopyDataCommand = nts.uk.com.view.cmm001.f.model.MasterCopyDataCommand;
    const LAST_INDEX_ERA_NAME_SYTEM: number = 3;
    export module viewmodel {
        export class ScreenModel {
            texteditor: any;
            simpleValue: KnockoutObservable<string>;

            columns: KnockoutObservableArray<any>;
            dataSource: KnockoutObservableArray<model.EraItem>;
            eraName: KnockoutObservable<string>;
            eraSymbol: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            inputData: KnockoutObservable<any>;
            currentName: KnockoutObservable<string>;
            currentSymbol: KnockoutObservable<string>;
            currentStartDate: KnockoutObservable<string>;
            currentCode: KnockoutObservable<string>;
            eraSelected: KnockoutObservable<model.EraItem>;
            activeUpdate: KnockoutObservable<boolean>;
            activeDelete: KnockoutObservable<boolean>;

            constructor() {
                let self = this;

                self.activeUpdate = ko.observable(false);
                self.activeDelete = ko.observable(false);
                self.simpleValue = ko.observable("123");
                self.texteditor = {
                    value: ko.observable(''),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "Placeholder for text editor",
                        width: "100px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
                self.columns = ko.observableArray([
                    { headerText: 'eraId', key: 'eraId', width: 100, hidden: true },
                    { headerText: nts.uk.resource.getText('CMM020_6'), key: 'eraName', width: 60, formatter: _.escape },
                    { headerText: nts.uk.resource.getText('CMM020_7'), key: 'eraSymbol', width: 45, formatter: _.escape },
                    { headerText: nts.uk.resource.getText('CMM020_8'), key: 'startDate', width: 70, formatter: _.escape }
                ]);

                self.eraSelected = ko.observable(null);
                self.dataSource = ko.observableArray([]);
                self.eraName = ko.observable('');
                self.eraSymbol = ko.observable('');
                self.startDate = ko.observable('');
                self.inputData = ko.observable();
                self.currentName = ko.observable('');
                self.currentSymbol = ko.observable('');
                self.currentStartDate = ko.observable('');
                self.currentCode = ko.observable('');
                self.currentCode.subscribe(function(codeChanged) {
                    self.clearError();
                    if (codeChanged == undefined || codeChanged == "") {
                        self.refreshEraShow();
                        self.activeUpdate(false);
                        self.activeDelete(false);
                    } else {
                        let currentEra = self.dataSource().filter(e => e.eraId === codeChanged)[0];
                        if (!_.isEmpty(currentEra)) {
                            self.eraSelected(currentEra);
                            self.setValueCurrentEraShow(currentEra);
                            //check era is system value, set active btn update and delete
                            var indexOfEraSelected = self.dataSource().indexOf(currentEra);
                            self.activeUpdate(indexOfEraSelected > LAST_INDEX_ERA_NAME_SYTEM);
                            self.activeDelete(indexOfEraSelected == self.dataSource().length - 1);

                        };
                    }
                });
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();

                //call ws get all era
                var listEraName: model.EraItem[] = [];
                for (let i = 0; i <= 3; i++) {
                    listEraName.push(new model.EraItem("code" + i, "Name" + i, "Sb" + i, "2018/06/1" + (5-i)));
                }
                listEraName.push(new model.EraItem("code5", "CusName", "CusSb", "2018/06/25"));
                listEraName.push(new model.EraItem("code6", "222", "222", "2018/06/23"));
                if (listEraName === undefined || listEraName.length == 0) {
                    self.dataSource();
                } else {
                    self.dataSource(listEraName);
                    let eraNameFirst = _.first(listEraName);
                    self.currentCode(eraNameFirst.eraId);
                    self.setValueCurrentEraShow(eraNameFirst);
                }
                console.log(!self.activeUpdate());
                dfd.resolve();
                return dfd.promise();
            }

            public newItem() {

            }

            /**
             * createEra
             */
            public createEra(): void {
                var self = this;
                service.createEraName(self.inputData).done(function(res: any) {

                }).fail(function(res: any) {
                    console.log(res);
                });
            }

            /**
             * deleteEra
             */
            public deleteEra(): void {
                var self = this;
                service.deleteEraName(self.inputData).done(function(res: any) {

                }).fail(function(res: any) {
                    console.log(res);
                });
            }
            private setValueCurrentEraShow(currentEra: model.EraItem) {
                var self = this;
                self.currentName(currentEra.eraName);
                self.currentSymbol(currentEra.eraSymbol);
                self.currentStartDate(currentEra.startDate);
            }
            private refreshEraShow() {
                var self = this;
                self.currentName('');
                self.currentSymbol('');
                self.currentStartDate('');
            }
            clearError(): void {
                if ($('.nts-editor').ntsError("hasError")) {
                    $('.nts-input').ntsError('clear');
                }
            }


        }
        export module model {

            export class EraItem {
                eraId: string;
                eraName: string;
                eraSymbol: string;
                startDate: string;
                endDate: string;
                systemType: boolean;

                constructor(eraId: string, eraName: string, eraSysbol: string, startDate: string) {
                    this.eraId = eraId;
                    this.eraName = eraName;
                    this.eraSymbol = eraSysbol;
                    this.startDate = startDate;
                }
            }
        }
    }
}