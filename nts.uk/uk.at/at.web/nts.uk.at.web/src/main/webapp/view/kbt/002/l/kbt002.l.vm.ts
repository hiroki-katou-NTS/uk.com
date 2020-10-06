/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kbt002.l {

  const API = {
    getExecItemInfoList: "at/function/indexreconstruction",
  };

  @bean()
  export class KBT002LViewModel extends ko.ViewModel {

    items: KnockoutObservableArray<ProcExecIndexResultDto> = ko.observableArray([]);
    columns: KnockoutObservableArray<any> = ko.observableArray([]);
    currentCode: KnockoutObservable<any> = ko.observable();
    execId: KnockoutObservable<string> = ko.observable('');
    tableOfGoals: KnockoutObservable<string> = ko.observable('');

    created(params: any) {
      const vm = this;

      vm.columns = ko.observableArray([
        { headerText: vm.$i18n('KBT002_328'), key: 'tablePhysicalName', width: 250 },
        { headerText: vm.$i18n('KBT002_329'), key: 'indexName', width: 250 },
        { headerText: vm.$i18n('KBT002_330'), key: 'fragmentationRate', width: 150 },
        { headerText: vm.$i18n('KBT002_331'), key: 'fragmentationRateAfterProcessing', width: 150 }
      ]);

      if (params) {
        vm.execId = params.executionId;
        vm.getProExecIndex(vm.execId());
      }
    }

    getProExecIndex(execId: string) {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(API.getExecItemInfoList, execId).then((data: ProExecIndexAndNumberTargetDto) => {
        if (data.indexReconstructionResult) {
          vm.items(data.indexReconstructionResult);
        }
        vm.tableOfGoals(vm.$i18n("KBT002_327", [data.numberOfTargetTable + '']))
      }).always(() => vm.$blockui("clear"));
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
  export interface ProExecIndexAndNumberTargetDto {
    numberOfTargetTable: number;
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