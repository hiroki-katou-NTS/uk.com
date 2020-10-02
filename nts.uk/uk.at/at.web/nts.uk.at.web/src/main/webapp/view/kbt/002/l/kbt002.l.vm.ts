module nts.uk.at.view.kbt002.l {
  import getShared = nts.uk.ui.windows.getShared;
  const getTextResource = nts.uk.resource.getText;

  @bean()
  export class KBT002LViewModel extends ko.ViewModel {

    items: KnockoutObservableArray<ProcExecIndexResultDto> = ko.observableArray([]);
    columns: KnockoutObservableArray<NtsGridListColumn>;
    currentCode: KnockoutObservable<any>;
    currentCodeList: KnockoutObservableArray<any>;
    count: number = 100;
    switchOptions: KnockoutObservableArray<any>;
    execId: KnockoutObservable<string> = ko.observable('');
    tableOfGoals: KnockoutObservable<string> = ko.observable('');

    created(){
      const vm = this;
      
      vm.columns = ko.observableArray([
        { headerText: getTextResource('KBT002_328'), key: 'tablePhysicalName', width:250},
        { headerText: getTextResource('KBT002_329'), key: 'indexName', width:250},
        { headerText: getTextResource('KBT002_330'), key: 'fragmentationRate', width:150},
        { headerText: getTextResource('KBT002_331'), key: 'fragmentationRateAfterProcessing', width:150}
      ]);
      
    
      this.currentCode = ko.observable();
      this.currentCodeList = ko.observableArray([]);

      const sharedData = getShared('inputDialogL');
      if(sharedData){
        vm.execId = sharedData.executionId;
      }
      vm.getProExecIndex();
    }

    getProExecIndex(){
    const vm = this;
    vm.$blockui('grayout');
    service.indexResconstruction(vm.execId()).then((data : ProExecIndexAndNumberTargetDto) => {
      vm.$blockui('clear');
      if(data.indexReconstructionResult){
        vm.items(data.indexReconstructionResult);
      }
      vm.tableOfGoals(vm.$i18n("KBT002_327", [data.numberOfTargetTable+'']))
      })
      .always(() => vm.$blockui("clear"));
   }

    closeDialog(){
      const vm = this;
      vm.$window.close();
    }
  }
  export interface ProExecIndexAndNumberTargetDto {
    numberOfTargetTable :  number;
    executionId: string;
    indexReconstructionResult: ProcExecIndexResultDto[];
  }

 export interface ProcExecIndexResultDto {
    indexName: string;
    tablePhysicalName: string;
    fragmentationRate: number;
    fragmentationRateAfterProcessing: number;
  }
}