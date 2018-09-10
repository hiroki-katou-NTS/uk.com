module nts.uk.com.view.qmm005.a.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.com.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;

    export class ScreenModel {

        itemTable:ItemTable;





        value: KnockoutObservable<string>;
        itemListCbb1: KnockoutObservableArray<model.ItemModel>;
        itemListCbb1SelectedCode: KnockoutObservableArray<number>;
        processInfomations: KnockoutObservableArray<model.ProcessInfomation>;
        setDaySupports: KnockoutObservableArray<model.SetDaySupport>;

        currentProcessDates:KnockoutObservableArray<model.CurrentProcessDate>;
        employmentTiedProcessYears:KnockoutObservableArray<model.EmploymentTiedProcessYear>;
        employments:KnockoutObservableArray<model.Employment>;

        constructor() {
            var self = this;
            self.itemTable=new ItemTable();



            self.value = ko.observable('');
            self.itemListCbb1 = ko.observableArray([
                new model.ItemModel(1, '2016'),
                new model.ItemModel(2, '2017')
            ]);

            self.processInfomations = ko.observableArray([]);
            self.setDaySupports = ko.observableArray([]);
            self.currentProcessDates=ko.observableArray([]);
            self.employmentTiedProcessYears=ko.observableArray([]);
            self.employments=ko.observableArray([]);





            service.getProcessInfomations().done((process: Array<model.IProcessInfomation>) => {
                let processInformationTemp: Array<model.ProcessInfomation> = [];
                if (!_.isEmpty(process)) {
                    process.forEach(item=>{
                        processInformationTemp.push(new model.ProcessInfomation(item));
                    });
                    self.processInfomations.push.apply(processInformationTemp);
                }
            });

            service.getSetDaySupports().done((daysupport:Array<model.ISetDaySupport>)=>{
                let setDaySupportsTemp: Array<model.SetDaySupport> = [];
                if (!_.isEmpty(daysupport)) {
                    daysupport.forEach(item=>{
                        setDaySupportsTemp.push(new model.SetDaySupport(item));
                    });
                    self.setDaySupports.push.apply(setDaySupportsTemp);
                }

            });



            self.itemListCbb1SelectedCode = ko.observable(0);
        }


        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }

    }

    export class ItemTable{
        processInfomations:Array<model.ProcessInfomation>;
        currentProcessDates:Array<model.CurrentProcessDate>;
        setDaySupports:Array<model.SetDaySupport>;
        employmentTiedProcessYears:Array<model.EmploymentTiedProcessYear>;
        employments:Array<model.Employment>;
        constructor(){
            this.processInfomations=new Array(5);
            this.currentProcessDates=new Array(5);
            this.setDaySupports=new Array(5);
            this.employmentTiedProcessYears=new Array(5);
            this.employments=new Array(5);
        }


    }




}
