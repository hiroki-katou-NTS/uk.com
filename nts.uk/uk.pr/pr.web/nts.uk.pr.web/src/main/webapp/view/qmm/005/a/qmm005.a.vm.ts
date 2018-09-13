module nts.uk.pr.view.qmm005.a.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import CurrentProcessDate = nts.uk.pr.view.qmm005.share.model.CurrentProcessDate;

    export class ScreenModel {
        //A2_2
        itemTable:Array<ItemTable>;

        //A3_4 対象雇用
        targetEmployment:KnockoutObservableArray<number>;

        processCategoryNO:number;

        myItems:KnockoutObservableArray<string>;

        setDaySupports:KnockoutObservableArray<model.SetDaySupport>;






        processInfomations:KnockoutObservableArray<model.ProcessInfomation>;
        currentProcessDates:Array<model.CurrentProcessDate>;
        setDaySupports:Array<model.SetDaySupport>;
        employmentTiedProcessYears:Array<model.EmploymentTiedProcessYear>;
        employments:Array<model.Employment>;




        value: KnockoutObservable<string>;
        itemListCbb1: KnockoutObservableArray<model.ItemModel>;
        selectedCode: KnockoutObservable<number>;
        itemListCbb11SelectedCode: KnockoutObservable<string>;
        processInfomations: KnockoutObservableArray<model.ProcessInfomation>;
        setDaySupports: KnockoutObservableArray<model.SetDaySupport>;




        currentProcessDates:KnockoutObservableArray<model.CurrentProcessDate>;
        employmentTiedProcessYears:KnockoutObservableArray<model.EmploymentTiedProcessYear>;
        employments:KnockoutObservableArray<model.Employment>;

        constructor() {
            var self = this;


            self.selectedCode=ko.observable('');
            self.itemListCbb1 = ko.observableArray([
                new model.ItemModel(1, '2016'),
                new model.ItemModel(2, '2017')
            ]);
            self.itemTable=new Array<ItemTable>(5);
            //A3_4 対象雇用
            self.targetEmployment=ko.observable([]);

            self.itemTable=new Array<ItemTable>(5);
            let paramProcessInfomation1={
                cid: '1',
                processCategoryNO: 1,
                processingName: 'name1',
                deprecatCategory: 1
            }
            let paramProcessInfomation2={
                cid: '1',
                processCategoryNO: 2,
                processingName: 'name2',
                deprecatCategory: model.Abolition.Not_Abolition
            }
            let paramSetDaySupport1={
                    cid:'1',
                    processCategoryNO:0,
                    socialInsurCollecMonth:5,
                    processDate:201810,
                    incomeTaxDate:'',
                    closeDateTime:'',
                    empExtraRefeDate:'20181011',
                    closureAccountingDate:'',
                    socialInsurStanDate:'',
                    empInsurdStanDate:'',
                    payMentDate:'20180506',
                    numberWorkDay:20
            }
            let paramSetDaySupport2={
                cid:'1',
                processCategoryNO:1,
                socialInsurCollecMonth:5,
                processDate:201810,
                incomeTaxDate:'',
                closeDateTime:'',
                empExtraRefeDate:'20181011',
                closureAccountingDate:'',
                socialInsurStanDate:'',
                empInsurdStanDate:'',
                payMentDate:'20180506',
                numberWorkDay:20
            }

            self.setDaySupports=ko.observableArray([
                new model.SetDaySupport(paramSetDaySupport1),
                new model.SetDaySupport(paramSetDaySupport2)

            ]);

            self.itemTable.push(
               // new ItemTable(1,new model.ProcessInfomation(paramProcessInfomation1),self.setDaySupports )
            );




            self.processCategoryNO=ko.observable(0);





            self.processInfomations = ko.observableArray([]);
            self.setDaySupports = ko.observableArray([]);
            self.currentProcessDates=ko.observableArray([]);
            self.employmentTiedProcessYears=ko.observableArray([]);
            self.employments=ko.observableArray([]);





            // service.getProcessInfomations().done((process: Array<model.IProcessInfomation>) => {
            //     let processInformationTemp: Array<model.ProcessInfomation> = [];
            //     if (!_.isEmpty(process)) {
            //         process.forEach(item=>{
            //             processInformationTemp.push(new model.ProcessInfomation(item));
            //         });
            //         self.processInfomations.push.apply(processInformationTemp);
            //     }
            // });
            //
            // service.getSetDaySupports().done((daysupport:Array<model.ISetDaySupport>)=>{
            //     let setDaySupportsTemp: Array<model.SetDaySupport> = [];
            //     if (!_.isEmpty(daysupport)) {
            //         daysupport.forEach(item=>{
            //             setDaySupportsTemp.push(new model.SetDaySupport(item));
            //         });
            //         self.setDaySupports.push.apply(setDaySupportsTemp);
            //     }
            //
            // });





            let data:Array<any>= [];

        }


        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }

    }

    export class ItemTable{
        code:KnockoutObservable<number>;
        processInfomations:model.ProcessInfomation;
        currentProcessDates:KnockoutObservableArray<model.CurrentProcessDate>;
        setDaySupports:KnockoutObservableArray<model.SetDaySupport>;

        constructor(
            processInfomations:model.ProcessInfomation,
            currentProcessDates:KnockoutObservableArray<model.CurrentProcessDate>,
            setDaySupports:KnockoutObservableArray<model.SetDaySupport>,

        ){
            this.processInfomations=processInfomations;
            this.currentProcessDates=currentProcessDates;
            this.setDaySupports=setDaySupports;

        }


    }




}
