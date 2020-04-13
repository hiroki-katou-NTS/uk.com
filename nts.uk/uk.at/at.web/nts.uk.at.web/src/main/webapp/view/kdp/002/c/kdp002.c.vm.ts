module nts.uk.at.view.kdp002.c {
    export module viewmodel {
        export class ScreenModel {

            // B2_2
            employeeCodeName: KnockoutObservable<string> = ko.observable("基本給");
            // B3_2
            dayName: KnockoutObservable<string> = ko.observable("基本給");
            // B3_3
            timeName: KnockoutObservable<string> = ko.observable("基本給");
            // G4_2
            checkHandName: KnockoutObservable<string> = ko.observable("基本給");
            // G5_2
            numberName: KnockoutObservable<string> = ko.observable("基本給");
            // G6_2
            laceName: KnockoutObservable<string> = ko.observable("基本給");

            workName1: KnockoutObservable<string> = ko.observable("基本給");

            workName2: KnockoutObservable<string> = ko.observable("基本給");


            items: KnockoutObservableArray<model.ItemModels> = ko.observableArray([]);
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any> = ko.observable();
            currentCodeList: KnockoutObservableArray<any>;

            constructor() {
                let self = this;
                for (let i = 1; i < 6; i++) {
                    self.items.push(new model.ItemModels('00' + i, '基本給'));
                }

                self.columns2 = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KDP002_59"), key: 'code', width: 200 },
                    { headerText: nts.uk.resource.getText("KDP002_60"), key: 'name', width: 150 }
                ]);
            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                let itemIds = nts.uk.ui.windows.getShared("KDP010_2C");
                let data = {
                    //stampDate: moment().format("YYYY/MM/DD"),
                    attendanceItems: itemIds
                }

                service.startScreen(data).done((res) => {
                    console.log(res);
                    if (res) {
                        self.dayName(res.stampRecords[0].stampDate);
                        self.timeName(res.stampRecords[0].stampTime);
                        self.checkHandName();
                        self.numberName();
                        self.laceName();
                        self.workName1(res.workTypes[0].name);
                        self.workName2(res.workTimeTypes[0].name);
                    }
                });

                dfd.resolve();
                return dfd.promise();
            }
            getEmpInfo(): JQueryPromise<any> { 
                let self = this;
                let dfd = $.Deferred();
                let employeeId = __viewContext.user.employeeId;
                service.getEmpInfo(employeeId).done(function(data) {
                    self.employeeCodeName(data.employeeCode +" "+ data.personalName);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Close dialog
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
    }
    export module model {

        export class ItemModels {
            code: string;
            name: string;
            description: string;
            constructor(code: string, name: string, description: string) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
        }
    }
}