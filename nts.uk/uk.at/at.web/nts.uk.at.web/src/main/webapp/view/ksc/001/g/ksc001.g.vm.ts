module nts.uk.at.view.ksc001.g {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            items:KnockoutObservableArray<GridItem>;
            constructor() {
                let self = this;
                self.enable = ko.observable(true);
                self.required = ko.observable(true);

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({});

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });
                let list:Array<GridItem> = [];
                    for (var i = 0; i < 50; i++) {
                        list.push(new GridItem(i));
                    }
                self.items = ko.observableArray(list);
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                $("#fixed-table").ntsFixedTable({ height: 430 });
                return dfd.promise();
            }
            /**
             * request to create creation screen
             */
            openDialog(): void {
                let self = this;
            blockUI.invisible();
            // the default value of categorySet = undefined
            //nts.uk.ui.windows.setShared('', );
            nts.uk.ui.windows.sub.modal("/view/ksc/001/h/index.xhtml", { dialogClass: "no-close" }).onClosed(() => { 
            });
            blockUI.clear();
            }
        }
        
        export class GridItem {
            id: number;
            exeDay: string;
            exeEmployeeCode: string;
            exeEmployeeName: string;
            targetPeriod: string;
            status: string;
            exeId:string;
            constructor(id: number) {
                this.id = id;
                this.exeDay = "2017/12/12";
                this.exeEmployeeCode = "A0000000"+id;
                this.exeEmployeeName = "日通システム　名"+id;
                this.targetPeriod = "2017/12/12 ~ 2017/12/12";
                this.status = "完了　（エラーあり）";
                this.exeId = id.toString();
                //TODO
            }
        }
    }
}