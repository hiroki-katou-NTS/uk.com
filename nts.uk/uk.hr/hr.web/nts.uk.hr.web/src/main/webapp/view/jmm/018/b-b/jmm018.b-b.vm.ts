module nts.uk.com.view.jmm018.tabb.viewmodel {
    import getText = nts.uk.resource.getText;
    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModel {
        screenMode: KnockoutObservable<any> = ko.observable(1);
        masterId: KnockoutObservable<string> = ko.observable("");
        histList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedHistId: KnockoutObservable<any> = ko.observable('');
        itemListB422_15: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedIdB422_15: KnockoutObservable<number> = ko.observable(0);
        releaseDate: KnockoutObservableArray<any> = ko.observableArray([]);
        releaseDateSelected: KnockoutObservable<number> = ko.observable(1);
        textEditorB422_15_6: KnockoutObservable<number> = ko.observable(0);
        textDate: KnockoutObservable<string> = ko.observable('');
        releaseType: KnockoutObservableArray<any> = ko.observableArray([]);
        releaseTypeSelected: KnockoutObservable<number> = ko.observable(1);
        retireDate: KnockoutObservableArray<any> = ko.observableArray([]);
        retireDateSelected: KnockoutObservable<number> = ko.observable(1);
        referenceValueLs: KnockoutObservableArray<any> = ko.observableArray([]);
        numberDispLs: KnockoutObservableArray<any> = ko.observableArray([]);
        
        constructor() {
            let self = this;
            
            // radio button
            let ageCondition = __viewContext.enums.UseAtr;
            _.forEach(ageCondition, (obj) => {
                self.itemListB422_15.push(new ItemModel(obj.value, obj.name));
            });
            
            // combobox
            self.releaseDate = ko.observableArray([
                    new ItemModel(1, getText('JMM018_B422_15_5_1')),
                    new ItemModel(2, getText('JMM018_B422_15_5_2')),
                    new ItemModel(3, getText('JMM018_B422_15_5_3')),
                    new ItemModel(6, getText('JMM018_B422_15_5_6')),
                ]);
            
            self.releaseType = ko.observableArray([
                    new ItemModel(1, getText('JMM018_B422_15_5_1')),
                    new ItemModel(2, getText('JMM018_B422_15_5_2')),
                    new ItemModel(3, getText('JMM018_B422_15_5_3')),
                    new ItemModel(6, getText('JMM018_B422_15_5_6')),
                ]);
            
            self.retireDate = ko.observableArray([
                    new ItemModel(1, getText('JMM018_B422_15_5_1')),
                    new ItemModel(2, getText('JMM018_B422_15_5_2')),
                    new ItemModel(3, getText('JMM018_B422_15_5_3')),
                    new ItemModel(6, getText('JMM018_B422_15_5_6')),
                ]);
            
            self.numberDispLs = ko.observableArray([
                    new ItemModel(1, getText('JMM018_B422_15_5_1')),
                    new ItemModel(2, getText('JMM018_B422_15_5_2')),
                    new ItemModel(3, getText('JMM018_B422_15_5_3')),
                    new ItemModel(6, getText('JMM018_B422_15_5_6')),
                ]);
            
            self.delVisible = ko.observable(true);
            
            self.delChecked = ko.observable();
            
            self.pathGet = ko.observable(`careermgmt/careerpath/getDateHistoryItem`);
            self.pathAdd = ko.observable(`careermgmt/careerpath/saveDateHistoryItem`);
            self.pathUpdate = ko.observable(`careermgmt/careerpath/updateDateHistoryItem`);
            self.pathDelete = ko.observable(`careermgmt/careerpath/removeDateHistoryItem`);
            
            self.commandAdd = (masterId, histId, startDate, endDate) => {
                return { startDate: moment(startDate).format("YYYY/MM/DD") }
            };
            
            self.getQueryResult = (res) => {
                return _.map(res, h => {
                    return { histId: h.historyId, startDate: h.startDate, endDate: h.endDate, displayText: `${h.startDate} ～ ${h.endDate}` };
                });
            };
            
            self.commandUpdate = (masterId, histId, startDate, endDate) => {
                return {
                    historyId: histId,
                    startDate: moment(startDate).format("YYYY/MM/DD")
                }
            };
            
            self.commandDelete = (masterId, histId) => {
                return {
                    historyId: histId
                };
            };
            
            self.getSelectedStartDate = () => {
                let selectedHist = _.find(self.histList(), h => h.histId === self.selectedHistId());
                if (selectedHist) return selectedHist.startDate;
            };
            
            self.histList.subscribe(function(newValue) {
                if(self.histList().length == 0){
                    self.selectedHistId('');        
                }
            });
            
            self.selectedHistId.subscribe(function(newValue) {
                console.log(newValue);
            });
            
            self.afterRender = () => {
            };
            
            self.afterAdd = () => {
                new service.getLatestCareerPathHist().done(function(data: any) {
                    self.latestCareerPathHist(data);
                });
            };
            
            self.afterUpdate = () => {
                alert("Updated");
            };
            
            self.afterDelete = () => {
                alert("delete");
            };
            
            self.releaseDateSelected.subscribe(function(val){
                if(val == 2){
                    self.textDate(getText('JMM018_B422_17_7_1'));   
                }else if(val == 3){
                    self.textDate(getText('JMM018_B422_17_7_2'));   
                }
            });
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.grayout();
            let a = {
                        index: 1,
                        // B422_15_22
                        valueItem: "a",
                        // B422_15_23
                        display: true,
                        // B422_15_24
                        numberDisplay: 2,
                        // B422_15_25
                        valueCriteria: "a",
                        // B422_15_26
                        continuationCategory: "a",
                    }
                let b = {
                    index: 2,
                    // B422_15_22
                    valueItem: "a",
                    // B422_15_23
                    display: false,
                    // B422_15_24
                    numberDisplay: 3,
                    // B422_15_25
                    valueCriteria: "a",
                    // B422_15_26
                    continuationCategory: "a",
                }

            self.referenceValueLs().push(new ReferenceValue(a));
            self.referenceValueLs().push(new ReferenceValue(b));
               dfd.resolve();

                nts.uk.ui.block.clear();

            return dfd.promise();
        }

    }
    
    export interface IReferenceValue {
        index: number;
        // B422_15_22
        valueItem: string;
        // B422_15_23
        display: boolean;
        // B422_15_24
        numberDisplay: number;
        // B422_15_25
        valueCriteria: string;
        // B422_15_26
        continuationCategory: string;
    }
    
    class ReferenceValue {
        index: number;
        // B422_15_22
        valueItem: KnockoutObservable<string>;
        // B422_15_23
        display: KnockoutObservable<boolean>;
        // B422_15_24
        numberDisplay: KnockoutObservable<number>;
        // B422_15_25
        valueCriteria: KnockoutObservable<string>;
        // B422_15_26
        continuationCategory: KnockoutObservable<string>;
        constructor(param: IReferenceValue) {
            let self = this;
            self.index = param.index;
            self.valueItem = ko.observable(param.valueItem);
            self.display = ko.observable(param.display);
            self.numberDisplay = ko.observable(param.numberDisplay);
            self.valueCriteria = ko.observable(param.valueCriteria);
            self.continuationCategory = ko.observable(param.continuationCategory);
        }
    }

    class ItemModel {
        value: number;
        name: string;

        constructor(value: number, name: string) {
            this.value = value;
            this.name = name;
        }
    }
}
