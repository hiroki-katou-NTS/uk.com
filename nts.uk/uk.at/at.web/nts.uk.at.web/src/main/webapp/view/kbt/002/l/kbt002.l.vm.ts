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
        { headerText: '', key: 'id', hidden: true },
        { headerText: vm.$i18n('KBT002_328'), key: 'tablePhysicalName', width: 250 },
        { headerText: vm.$i18n('KBT002_329'), key: 'indexName', width: 250 },
        { headerText: vm.$i18n('KBT002_330'), key: 'fragmentationRate', width: 150, formatter: vm.formatNumber },
        { headerText: vm.$i18n('KBT002_331'), key: 'fragmentationRateAfterProcessing', width: 150, formatter: vm.formatNumber }
      ]);
      if (params) {
        vm.execId(params.executionId);
        vm.getProExecIndex(vm.execId());
      } else {
        // FAKE DATA
        vm.getProExecIndex('ad686761-6807-467b-b9ab-9836a2047f07');
      }
    }

    mounted() {
      $("#L4_1").focus();
    }

    private getProExecIndex(execId: string) {
      const vm = this;
      vm.$blockui('grayout')
      .then(() => vm.$ajax(`${API.getExecItemInfoList}/${execId}`))
      .then((data: ProExecIndexAndNumberTargetDto) => {
        if (data.indexReconstructionResult) {
          _.forEach(data.indexReconstructionResult, item => item.id = nts.uk.util.randomId());
          vm.items(data.indexReconstructionResult);
        }
        vm.tableOfGoals(vm.$i18n("KBT002_327", [String(data.numberOfTargetTable)]));
      })
      .always(() => vm.$blockui("clear"));  
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    private formatNumber(value: string): string {
        return `<div style="float: right">${value}</div>`;
    }
  }
  export interface ProExecIndexAndNumberTargetDto {
    numberOfTargetTable: number;
    executionId: string;
    indexReconstructionResult: ProcExecIndexResultDto[];
  }

  export interface ProcExecIndexResultDto {
    id?: string;
    indexName: string;
    tablePhysicalName: string;
    fragmentationRate: number;
    fragmentationRateAfterProcessing: number;
  }
}
