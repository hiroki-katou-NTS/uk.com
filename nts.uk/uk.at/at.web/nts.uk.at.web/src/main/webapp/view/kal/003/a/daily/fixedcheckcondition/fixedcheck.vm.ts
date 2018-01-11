module nts.uk.at.view.kal003.a.daily.fixedcheckcondition {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            /** functiton start page */
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            //data
            listFixedConditionWorkRecord: KnockoutObservableArray<model.FixedConditionWorkRecord>;
            listWorkRecordExtraCon: KnockoutObservableArray<model.WorkRecordExtraCon>;
            //table
            columns: Array<any>;
            items: KnockoutObservableArray<model.Item>;
            currentItem: KnockoutObservable<model.Item>;
            //enum 
            enumWorkRecordFixedCheckItem: KnockoutObservableArray<any>;
            constructor() {
                let self = this;
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');
                //data
                self.listFixedConditionWorkRecord = ko.observableArray([]);
                self.listWorkRecordExtraCon = ko.observableArray([]);
                //table 
                self.items = ko.observableArray([]);
                self.currentItem = ko.observable(null);
                //enum
                self.enumWorkRecordFixedCheckItem = ko.observableArray(__viewContext.enums.WorkRecordFixedCheckItem);
                _.each(self.enumWorkRecordFixedCheckItem(), (value) => {
                    let temp = new model.Item(value.value, value.name, 'message '+value.value);
                    self.items.push(temp);

                });
                self.columns = [
                    { headerText: getText('KAL003_24'), key: 'no', width: 100 },
                    { headerText: getText('KAL003_25'), key: 'name', width: 120 },
                    {
                        headerText: getText('KAL003_26'), key: 'message', width: 100,
                        //                        template: "<input >" + '' + "</input>"
                        //                    }
                        formatter: function(message, record) {
                            return "<input data-bind=\"ntsTextEditor: {value: " + message + "}\" />"

                        }
                    }
                ];


            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                let dfdAllFixedConditionWorkRecord = self.getAllFixedConditionWorkRecord();
                let dfdAllWorkRecordExtraCon = self.getAllWorkRecordExtraCon();
                $.when(dfdAllFixedConditionWorkRecord, dfdAllWorkRecordExtraCon).done((dfdAllFixedConditionWorkRecordData, dfdAllWorkRecordExtraConData) => {
                    dfd.resolve();
                });
                return dfd.promise();
            }//end start page

            getAllFixedConditionWorkRecord() {
                let self = this;
                let dfd = $.Deferred();
                service.getAllFixedConditionWorkRecord().done((data) => {
                    self.listFixedConditionWorkRecord(data);
                    dfd.resolve();
                });

                return dfd.promise();

            }
            getAllWorkRecordExtraCon() {
                let self = this;
                let dfd = $.Deferred();
                service.getAllWorkRecordExtraCon().done((data) => {
                    self.listWorkRecordExtraCon(data);
                    dfd.resolve();
                });

                return dfd.promise();
            }


        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        //interface WorkRecordExtraCon
        export interface IWorkRecordExtraCon {
            errorAlarmCheckID: string;
            checkItem: number;
            messageBold: boolean;
            messageColor: string;
            sortOrderBy: number;
            useAtr: boolean;
            nameWKRecord: string;
        }
        //class WorkRecordExtraCon
        export class WorkRecordExtraCon {
            errorAlarmCheckID: string;
            checkItem: number;
            messageBold: boolean;
            messageColor: string;
            sortOrderBy: number;
            useAtr: boolean;
            nameWKRecord: string;
            constructor(data: IWorkRecordExtraCon) {
                this.errorAlarmCheckID = data.errorAlarmCheckID;
                this.checkItem = data.checkItem;
                this.messageBold = data.messageBold;
                this.messageColor = data.messageColor;
                this.sortOrderBy = data.sortOrderBy;
                this.useAtr = data.useAtr;
                this.nameWKRecord = data.nameWKRecord;
            }
        }//end class WorkRecordExtraCon

        export interface IFixedConditionWorkRecord {
            errorAlarmCode: string;
            fixConWorkRecordNo: number;
            message: string;
            useAtr: boolean;
        }
        //class FixedConditionWorkRecord
        export class FixedConditionWorkRecord {
            errorAlarmCode: string;
            fixConWorkRecordNo: number;
            message: string;
            useAtr: boolean;
            constructor(data: IFixedConditionWorkRecord) {
                this.errorAlarmCode = data.errorAlarmCode;
                this.fixConWorkRecordNo = data.fixConWorkRecordNo;
                this.message = data.message;
                this.useAtr = data.useAtr;
            }
        }
        export class Item {
            no: number;
            name: string;
            message: string;
            constructor(no: number,
                name: string,
                message: string) {
                this.no = no;
                this.name = name;
                this.message = message;
            }

        }


    }//end module model

}//end module