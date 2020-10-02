module nts.uk.at.view.kbt002.l {
  import getShared = nts.uk.ui.windows.getShared;
  const getTextResource = nts.uk.resource.getText;

  @bean()
  export class KBT002LViewModel extends ko.ViewModel {

    items: KnockoutObservableArray<ProcExecIndexResult> = ko.observableArray([]);
    columns: KnockoutObservableArray<NtsGridListColumn>;
    currentCode: KnockoutObservable<any>;
    currentCodeList: KnockoutObservableArray<any>;
    count: number = 100;
    switchOptions: KnockoutObservableArray<any>;
    execId: KnockoutObservable<string> = ko.observable('');
    tableOfGoals: KnockoutObservable<string> = ko.observable('');
    numberTable : KnockoutObservableArray<string> = ko.observableArray();

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

      const sharedData = nts.uk.ui.windows.getShared('inputDialogL');
      if(sharedData){
        vm.execId = sharedData.execId;
      }
      vm.getProExecIndex();
    }

    getProExecIndex(){
    const vm = this;
    service.indexResconstruction("b08d2311-d51f-4e1b-96e7-f7ebcbfa29f3").done((data : ProExecIndexAndNumberTarget) => {
      if(data.indexReconstructionResult){
        vm.items(data.indexReconstructionResult);
      }
      const numberT : string[] = [String(data.numberOfTargetTable)]
      // vm.numberTable(numberT);
      // console.log(vm.numberTable())

      vm.tableOfGoals(getTextResource("KDL009_25", [numberT]))
      })
    }

    closeDialog(){
      const vm = this;
      vm.$window.close();
    }
  }
  export class ProExecIndexAndNumberTarget {
    numberOfTargetTable :  number;
    executionId: string;
    indexReconstructionResult: ProcExecIndexResult[];

    constructor(init?: Partial<ProExecIndexAndNumberTarget>) {
      $.extend(this, init);
    }
  }

 export class ProcExecIndexResult {
    indexName: string;
    tablePhysicalName: string;
    fragmentationRate: number;
    fragmentationRateAfterProcessing: number;
    constructor(init?: Partial<ProcExecIndexResult>) {
      $.extend(this, init);
    }
  }
}