/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kbt002.j {

  const API = {
    findAll: "at/function/resultsperiod/findAll",
    saveAggrPeriod: "at/function/resultsperiod/save",
    removeAggrPeriod: "at/function/resultsperiod/removeAggrPeriod"
  };

  @bean()
  export class KBT002JViewModel extends ko.ViewModel {
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    aggrFrameCode: KnockoutObservable<string> = ko.observable(null);
    aggrName: KnockoutObservable<string> = ko.observable(null);
    dateValue: KnockoutObservable<DateValue> = ko.observable(new DateValue());

    aggrPeriodList: KnockoutObservableArray<AggrPeriodDto> = ko.observableArray([]);
    selectedAggrFrameCd: KnockoutObservable<string> = ko.observable('');
    refDate: KnockoutObservable<string> = ko.observable('');
    execScopeCls: KnockoutObservable<number> = ko.observable(null);
    aggrColumns: any[] = [];

    created(params: any) {
      const vm = this;
      vm.aggrColumns = [
        { headerText: vm.$i18n("KBT002_7"), key: 'aggrFrameCode', width: 50, formatter: _.escape },
        { headerText: vm.$i18n("KBT002_8"), key: 'optionalAggrName', width: 185, formatter: _.escape }
      ];
      if (params.aggrFrameCode) {
        vm.aggrFrameCode(params.aggrFrameCode);
      }
    }

    mounted() {
      const vm = this;
      vm.findAll(vm.aggrFrameCode());
      vm.selectedAggrFrameCd.subscribe(selectedCode => {
        // set update mode
        const selectedItem: AggrPeriodDto = _.find(vm.aggrPeriodList(), { aggrFrameCode: selectedCode });
        if (selectedItem && vm.aggrPeriodList().length > 0) {
          vm.bindingData(selectedItem);
          vm.isNewMode(false);
        } else {
          vm.isNewMode(true);
          vm.aggrFrameCode(null);
        }
        vm.$nextTick(() => vm.focusInput(vm.isNewMode()));
      });
      vm.isNewMode.subscribe(value => {
        vm.focusInput(value);
        if (!value) {
          nts.uk.ui.errors.clearAll();
        }
      });
    }

    bindingData(param: AggrPeriodDto) {
      const vm = this;
      vm.aggrFrameCode(param.aggrFrameCode || '');
      vm.aggrName(param.optionalAggrName || '');
      vm.dateValue().startDate = param.startDate;
      vm.dateValue().endDate = param.endDate;
      vm.dateValue.valueHasMutated();
    }

    private focusInput(isNewMode: boolean) {
      if (isNewMode) {
        $('#J3_2').focus();
      } else {
        $('#J1_1').focus();
      }
    }

    /**
     * ???????????????????????????
     * @param aggrFrameCode 
     */
    private processPostDelete(aggrFrameCode: string) {
      const vm = this;
      const index = _.findIndex(vm.aggrPeriodList(), { aggrFrameCode: aggrFrameCode });
      // ?????????????????????????????????????????????
      vm.processFindAll()
        .then(() => {
          // ????????????????????????
          if (vm.aggrPeriodList().length === 0) {
            vm.initProcExec();
          } else if (vm.aggrPeriodList().length === index) {
            // ??????????????????????????????
            vm.selectedAggrFrameCd(vm.aggrPeriodList()[index - 1].aggrFrameCode);
          } else {
            // ?????????????????????????????????????????????
            vm.selectedAggrFrameCd(vm.aggrPeriodList()[index].aggrFrameCode);
          }
        });
    }

    public initProcExec() {
      const vm = this;
      nts.uk.ui.errors.clearAll();
      vm.aggrName('');
      vm.aggrFrameCode('');
      vm.dateValue().startDate = null;
      vm.dateValue().endDate = null;
      vm.dateValue.valueHasMutated();
      vm.selectedAggrFrameCd(null);
      vm.isNewMode(true);
    }

    /**
     * ????????????????????????????????????????????????????????????
     * Find all AggrPeriodDto by companyId
     */
    public findAll(aggrFrameCode?: string) {
      const vm = this;
      vm.$blockui('grayout');
      vm.processFindAll()
        .then(() => {
          if (vm.aggrPeriodList().length > 0) {
            vm.selectedAggrFrameCd(aggrFrameCode || vm.aggrPeriodList()[0].aggrFrameCode);
          } else {
            vm.selectedAggrFrameCd(null);
          }
        })
        .always(() => vm.$blockui('clear'));
    }

    private processFindAll(): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(API.findAll)
        .then((res: AggrPeriodDto[]) => {
          vm.aggrPeriodList(res);
        });
    }

    /**
     * ?????????????????????????????????
     * Create AggrPeriodDto
    */
    public save() {
      const vm = this;
      const command: AggrPeriodCommand = new AggrPeriodCommand({
        aggrFrameCode: vm.aggrFrameCode(),
        optionalAggrName: vm.aggrName(),
        startDate: vm.dateValue().startDate ? moment.utc(vm.dateValue().startDate, 'YYYY/MM/DD').toISOString() : null,
        endDate: vm.dateValue().endDate ? moment.utc(vm.dateValue().endDate, 'YYYY/MM/DD').toISOString() : null,
      });
      vm.$blockui('grayout');
      vm.$validate()
        .then((valid: boolean) => {
          if (!valid) {
            return $.Deferred().reject();
          }
          //????????????????????????????????????????????????????????????
          return vm.$ajax(API.saveAggrPeriod, command)
            // ???????????????????????????????????????????????? #Msg_3
            .fail(() => vm.$dialog.error({ messageId: "Msg_3" }))
            // ????????????????????????ID???Msg_15??????????????????
            .then(() => vm.$dialog.info({ messageId: "Msg_15" }))
            .then(() => vm.findAll(vm.aggrFrameCode()));
        })
        .always(() => vm.$blockui('clear'));
    }

    /**
     * ?????????????????????????????????
     * Remove AggrPeriodDto
    */
    public remove() {
      const vm = this;
      vm.$dialog.confirm({ messageId: "Msg_18" })
        .then((result: 'no' | 'yes' | 'cancel') => {
          if (result === 'yes') {
            vm.$blockui('grayout');
            vm.$ajax(`${API.removeAggrPeriod}/${vm.aggrFrameCode()}`)
              .then(() => {
                vm.$blockui('clear');
                vm.$dialog.info({ messageId: "Msg_16" }).then(() => vm.processPostDelete(vm.aggrFrameCode()));
              })
              .fail((res) => {
                vm.$blockui('clear');
                vm.$dialog.alert({ messageId: res.messageId });
              });
          }
        });
    }

    /**
     * ???????????????????????????????????????
     * Close dialog
    */
    public closeDialog() {
      const vm = this;
      const param = {
        aggrFrameCode: vm.aggrFrameCode(),
        aggrPeriodList: vm.aggrPeriodList()
      };
      // ??????????????????
      vm.$window.close(param);
    }
  }

  /**
  * The Class AggrPeriodDto.
  * 	??????????????????
  */
  export interface AggrPeriodDto {
    companyId: string,
    aggrFrameCode: string,
    optionalAggrName: string,
    startDate: string,
    endDate: string
  }

  export class AggrPeriodCommand {
    /**  ??????ID. */
    companyId: string;
    /**  ????????????????????????. */
    aggrFrameCode: string;
    /**  ??????????????????. */
    optionalAggrName: string;
    /**  ????????????. */
    startDate: string;
    /**  ????????????. */
    endDate: string;

    constructor(init?: Partial<AggrPeriodDto>) {
      $.extend(this, init);
    }
  }

  export class DateValue {
    startDate: string = null;
    endDate: string = null;

    constructor(init?: Partial<DateValue>) {
      $.extend(this, init);
    }
  }
}