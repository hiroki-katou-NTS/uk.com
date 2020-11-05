/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kbt002.i {
  //     export module viewmodel {
  //         import modal = nts.uk.ui.windows.sub.modal;
  //         import setShared = nts.uk.ui.windows.setShared;
  //         import getShared = nts.uk.ui.windows.getShared;
  //         import windows = nts.uk.ui.windows;
  //         import block = nts.uk.ui.block;
  //         import dialog = nts.uk.ui.dialog;
  //         import getText = nts.uk.resource.getText;
  //         import modelkbt002b = nts.uk.at.view.kbt002.b.viewmodel;
  //         export class ScreenModel {
  //             executionId: KnockoutObservable<string>;
  //             execItemCd: KnockoutObservable<string>;
  //             isDaily: KnockoutObservable<boolean>;
  //             execution : KnockoutObservable<modelkbt002b.ExecutionItem>;
  //             dateString: KnockoutObservable<string>;
  //             dataSource : KnockoutObservableArray<any>;
  //             nameObj : KnockoutObservable<string>;
  //             numberPerson : KnockoutObservable<number>;

  //             columns: KnockoutObservableArray<NtsGridListColumn>;
  //             currentCode : KnockoutObservable<any>;

  //             constructor() {
  //                 let self = this;
  //                 let sharedData = nts.uk.ui.windows.getShared('inputDialogI').sharedObj;
  //                 self.executionId = ko.observable(sharedData.executionId);
  //                 self.execItemCd = ko.observable(sharedData.execItemCd)
  //                 self.nameObj = ko.observable(sharedData.nameObj);
  //                 self.isDaily= ko.observable(sharedData.isDaily);
  //                 self.execution = ko.observable(null);
  //                 self.dateString = ko.observable("2018/08/28");
  //                 self.dataSource  = ko.observableArray([]);
  //                 self.numberPerson = ko.observable(0);
  //                 self.currentCode  = ko.observable(null);

  //                 this.columns = ko.observableArray([
  //                     { headerText: 'id', key: 'employeeId', width: 100, hidden: true },
  //                     { headerText: getText('KBT002_184'), key: 'employeeCode', width: 100 },
  //                     { headerText: getText('KBT002_185'), key: 'pname', width: 130,formatter: _.escape },
  //                     { headerText: getText('KBT002_186'), key: 'date', width: 170,formatter: _.escape }, 
  //                     { headerText: getText('KBT002_187'), key: 'errorMessage', width: 300,
  //                             formatter: function (errorMessage, record) {
  //                                     return "<label class='limited-label'> " + errorMessage + " </label>";       
  //                             }

  //                     }
  //                 ]);
  //             }

  //             // Start page
  //             start() : JQueryPromise<any> { 
  //                 let self = this;
  //                 var dfd = $.Deferred();
  //                 let dfdGetDataByExecution = self.getDataByExecution(self.executionId());
  // //                self.getDataByExecution(self.executionId()).done(function(){
  // //                    dfd.resolve();
  // //                });
  //                 let dfdGetLogHis = self.getLogHistory(self.execItemCd(),self.executionId());
  //                 $.when(dfdGetDataByExecution,dfdGetLogHis).done((dfdGetDataByExecutionData,dfdGetLogHisData) => {
  //                     for(let i = 0;i<self.dataSource().length;i++){
  //                         self.dataSource()[i].date =self.dateString();
  //                     }
  //                     console.log(self.dataSource());
  //                     dfd.resolve();
  //                 });
  //                 return dfd.promise();
  //             }  

  //             getDataByExecution(executionId : string ){ 
  //                 let self = this;
  //                 let dfd = $.Deferred();
  //                 if(self.isDaily()){
  //                     service.findAppDataInfoDailyByExeID(executionId).done(function(data){
  //                         self.dataSource(data);
  //                         self.numberPerson(data.length);
  //                         dfd.resolve();
  //                     }).fail(function(res: any) { 
  //                         dfd.reject();
  //                         nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
  //                     });
  //                 }else{
  //                     service.findAppDataInfoMonthlyByExeID(executionId).done(function(data){
  //                         self.dataSource(data);  
  //                         self.numberPerson(data.length);
  //                         dfd.resolve();
  //                     }).fail(function(res: any) {
  //                         dfd.reject();
  //                         nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
  //                     });
  //                 }
  //                 return dfd.promise();  
  //             }

  //             getLogHistory(execItemCd :string, execId :string){
  //                 let self = this;
  //                 let dfd = $.Deferred();
  //                 service.getLogHistory(execItemCd,execId).done(function(data){
  //                     self.dateString(data.lastExecDateTime);
  //                     dfd.resolve();
  //                 }).fail(function(res: any) { 
  //                     dfd.reject();
  //                     nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
  //                 });

  //                 return dfd.promise();
  //             }

  //             // 閉じる button
  //             closeDialog() {
  //                 windows.close();
  //             }
  //         }
  //     }

  import getText = nts.uk.resource.getText;

  const API = {
    init: "ctx/at/record/approveDataError/init"
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    // Data from dialog G
    executionId: string = null;
    isDaily: boolean = false;

    content: KnockoutObservable<string> = ko.observable(null);
    targetNumber: KnockoutObservable<string> = ko.observable(null);

    // Error list
    errorList: KnockoutObservableArray<AppDataInfoDto> = ko.observableArray([]);
    errorColumns: any[] = [
      { headerText: '', key: 'id', hidden: true },
      { headerText: getText('KBT002_184'), key: 'employeeCode', width: '100px' },
      { headerText: getText('KBT002_185'), key: 'employeeName', width: '100px' },
      { headerText: getText('KBT002_186'), key: 'date', width: '100px' },
      { headerText: getText('KBT002_187'), key: 'errorMessage', width: '400px' }
    ];
    selectedError: KnockoutObservable<AppDataInfoDto> = ko.observable(null);

    // Init dialog
    public created(params: any) {
      const vm = this;
      vm.executionId = params.executionId;
      vm.isDaily = params.isDaily;
      vm.content(params.nameObj);
    }

    mounted() {
      const vm = this;
      vm.init();
    }

    public closeDialog(): void {
      const vm = this;
      vm.$window.close();
    }

    private init(): void {
      const vm = this;
      vm.$blockui("grayout");
      vm.$ajax(API.init, { executionId: vm.executionId, isDaily: vm.isDaily })
        .then((res: any) => {
          let data: any[];
          if (vm.isDaily) {
            data = res.dailyDtos;
          } else {
            data = res.monthlyDtos;
          }
          vm.targetNumber(nts.uk.text.format(getText("KBT002_182"), data.length));
          vm.errorList(_.chain(data)
            .map(item => {
              let dto: AppDataInfoDto = {
                id: nts.uk.util.randomId(),
                employeeId: item.employeeId,
                errorMessage: item.errorMessage,
                date: moment.utc(item.date).format("YYYY/MM/DD")
              };
              const emp: any = _.find(res.employees, { sid: dto.employeeId });
              dto.employeeCode = emp.scd;
              dto.employeeName = emp.bussinessName;
              return dto;
            })
            .orderBy('employeeCode', 'asc')
            .orderBy('date', 'asc')
            .value());
        })
        .always(() => vm.$blockui("clear"));
    }
  }

  export class AppDataInfoDto {
    id: string;
    employeeId: string;
    employeeCode?: string;
    employeeName?: string;
    errorMessage: string;
    date: string;
  }

}
