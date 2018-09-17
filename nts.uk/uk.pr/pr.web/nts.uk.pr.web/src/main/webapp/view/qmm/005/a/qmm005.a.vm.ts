module nts.uk.pr.view.qmm005.a.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import CurrentProcessDate = nts.uk.pr.view.qmm005.share.model.CurrentProcessDate;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        //A2_2
        itemTable:ItemTable;
        //A3_4 対象雇用
        targetEmployment:KnockoutObservableArray<number>;
        processCategoryNO:number;
        itemBinding:KnockoutObservableArray<ItemBinding>;



        constructor() {
            var self = this;
            $("#A2_2").ntsFixedTable({ height: 300, width: 1000 });
            $("#A3_1").ntsFixedTable({ height: 300, width:400  });
            //A3_4 対象雇用
            self.targetEmployment=ko.observable([]);

            self.itemBinding=ko.observableArray([]);


        }

        showDialogD_Create(processCateNo):void{
            setShared("QMM005_output_D_create", processCateNo);
            modal('/view/qmm/005/d/index.xhtml', {title: '',}).onClosed(function (): any {
            })
        }

        showDialogB_Update(processCateNo):void{
            let param={
                processCateNo:processCateNo,
                modeUpdate:true,
            }
            setShared("QMM005_output_D_create", param);
        }






        showDialogB(param): void {
            setShared("QMM005_output_B", param);
            modal('/view/qmm/005/b/index.xhtml', {title: '',}).onClosed(function (): any {
            })
        }

        showDialogF(param): void {
            let self = this;
            let employeeArr = new Array();
            employeeArr = self.itemTable.empCdNameImports;
            let paramEmployment = {
                processCateNo: param,
                employeeList: employeeArr
            }
            setShared("QMM005_output_F", paramEmployment);
            modal('/view/qmm/005/f/index.xhtml', {title: '',}).onClosed(function (): any {
            })

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.findDisplayRegister().done(data=>{
                self.itemTable=new ItemTable(data);
                console.log(self.itemTable);


                var sizetalbe=self.itemTable.processInfomations.length;
                for(let i:number=sizetalbe;i<5;i++){
                    self.itemTable.processInfomations.push(new model.ProcessInfomation({
                            processCateNo: i+1,
                            processDivisionName: '',
                            deprecatCate: 0
                        }
                    ));
                }

                for(let i:number=0;i<5;i++){
                    self.itemBinding.push(new ItemBinding(
                        self.itemTable.processInfomations[i],
                        _.sortBy(_.filter(self.itemTable.setDaySupports,function (o) {
                                return o.processCateNo==i+1;
                        }),function (o) {
                            return o.processDate;
                        }),
                    ));
                };

                console.log(self.itemBinding());
            });
            dfd.resolve();
            return dfd.promise();
        }

    }



    export class ItemBinding{

        processInfomation:model.ProcessInfomation;
        setDaySupports:KnockoutObservableArray<model.SetDaySupport>;
        setDaySupportsSelectedCode:KnockoutObservable<number>;
        constructor(
            processInfomation:model.ProcessInfomation,
            setDaySupports:Array<model.SetDaySupport>,

        ){
            this.processInfomation=processInfomation;
            this.setDaySupports=ko.observableArray(setDaySupports);
            this.setDaySupportsSelectedCode=ko.observable(0);
        }
    }


    export interface IitemTable{
        informationDto: Array<model.ProcessInfomation>,
        setDaySupportDto: Array<model.SetDaySupport>,
        currProcessDateDto: Array<model.CurrentProcessDate>,
        empTiedProYearDto: Array<model.EmpTiedProYear>,
        empCdNameImports: Array<model.EmpCdNameImport>
    }

    export class ItemTable{
        processInfomations:Array<model.ProcessInfomation>;
        setDaySupports:Array<model.SetDaySupport>;
        currentProcessDates:Array<model.CurrentProcessDate>;
        empCdNameImports:Array<model.EmpCdNameImport>;
        empTiedProYear:Array<model.EmpTiedProYear>;

        constructor(
            param:IitemTable
        ){
            this.processInfomations=param.informationDto;
            this.currentProcessDates=param.currProcessDateDto;
            this.setDaySupports=param.setDaySupportDto;
            this.empCdNameImports=param.empCdNameImports;
            this.empTiedProYear=param.empTiedProYearDto;
        }


    }









}
