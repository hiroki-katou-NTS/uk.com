/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kbt002.b {
  const API = {
    getEnumDataList: 'at/function/processexec/getEnum',
    getProcExecList: 'at/function/processexec/getProcExecList',
    saveProcExec: 'at/function/processexec/saveProcExec',
    deleteProcExec: 'at/function/processexec/removeProcExec',
    getMasterInfo: 'screen/at/processexec/getMasterInfo',
    getAlarmByUser: 'at/function/alarm/kal/001/pattern/setting',
    findWorkplaceTree: "bs/employee/workplace/config/info/findAll",
    findWkpTreeNew: 'bs/employee/wkpdep/get-wkpdepinfo-kcp004'
  };

  const getTextResource = nts.uk.resource.getText;
  const textUtil = nts.uk.text;
  const errors = nts.uk.ui.errors;

  @bean()
  export class KBT002BViewModel extends ko.ViewModel {
    // Set new mode or update mode
    isNewMode: KnockoutObservable<boolean> = ko.observable(null);

    execItemList: KnockoutObservableArray<any> = ko.observableArray([]);
    workplaceList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedExecCode: KnockoutObservable<string> = ko.observable('');
    currentExecItem: KnockoutObservable<ExecutionItem> = ko.observable(new ExecutionItem(null));

    // Display text
    workplaceListText: KnockoutObservable<string> = ko.observable('');
    targetDateText: KnockoutObservable<string> = ko.observable('');

    // Enumeration list
    targetMonthList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);

    targetDailyPerfItemList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
    repeatContentItemList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
    monthDayList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
    designatedYearList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);

    // List get alarm by user
    alarmByUserList: KnockoutObservableArray<any> = ko.observableArray([]);

    // B5_2 タスク有効設定
    taskEnableSettingList: KnockoutObservableArray<any> = ko.observableArray([
      { code: TaskEnableSettingClassificationCode.ENABLED, name: getTextResource('KBT002_123') },
      { code: TaskEnableSettingClassificationCode.DISABLED, name: getTextResource('KBT002_124') },
    ]);
    selectedTaskEnableSetting: KnockoutObservable<any> = ko.observable(null);

    tabs: KnockoutObservableArray<any> = ko.observableArray([
      { id: TabPanelId.TAB_1, title: getTextResource('KBT002_279'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
      { id: TabPanelId.TAB_2, title: getTextResource('KBT002_40'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
      { id: TabPanelId.TAB_3, title: getTextResource('KBT002_293'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
      { id: TabPanelId.TAB_4, title: getTextResource('KBT002_280'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
    ]);
    selectedTab: KnockoutObservable<string> = ko.observable('');

    itemsSwap: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    columns: KnockoutObservableArray<any> = ko.observableArray([
      { headerText: getTextResource('KBT002_7'), key: 'code', width: 55 },
      { headerText: getTextResource('KBT002_8'), key: 'name', width: 150 }
    ]);
    currentCodeListSwap: KnockoutObservableArray<any> = ko.observableArray([]);
    test: KnockoutObservableArray<any> = ko.observableArray([]);

    created() {
      const vm = this;
      vm.selectedTaskEnableSetting(TaskEnableSettingClassificationCode.ENABLED);
      vm.selectedTab(TabPanelId.TAB_1);
      vm.$ajax(API.getMasterInfo)
        .then((response: any) => {
          console.log(response)
        })
        .fail(err => {errors.clearAll();});

      vm.selectedExecCode.subscribe(execItemCode => {
        errors.clearAll();
        // Init process execution
        vm.currentExecItem(new ExecutionItem(null));
        vm.workplaceListText('');
        vm.targetDateText('');
        if (textUtil.isNullOrEmpty(execItemCode)) {
          vm.isNewMode(true);
        } else {
          // set update mode
          const data = _.filter(vm.execItemList(), (item) => { return item.execItemCode === execItemCode; });
          if (data[0]) {
            vm.currentExecItem().createData(data[0]);
            vm.buildWorkplaceStr(vm.currentExecItem().workplaceList());
            if (vm.currentExecItem().perScheduleCls()) {
              // vm.targetDateText(vm.buildTargetDateStr(vm.currentExecItem()) || '');
              vm.targetDateText('targetDateText');
            }
          }
          vm.isNewMode(false);
        }

        vm.$nextTick(() => {
          vm.focusInput();
        });
        errors.clearAll();
      });
    }

    mounted() {
      const vm = this;
      vm.$blockui('grayout')
      $.when(vm.getEnumDataList(), vm.getAlarmByUser())
        .then((response) => console.log(vm.targetMonthList()))
        .always(() => vm.$blockui('clear'));
    }

    /**
     * Get enum data list.
     */
    private getEnumDataList(): JQueryPromise<any> {
      const vm = this;
      const dfd = $.Deferred<void>();

      vm.$ajax(API.getEnumDataList)
        .then((response: ExecItemEnumDto) => {
          vm.targetMonthList(response.targetMonthList);
          vm.targetDailyPerfItemList(response.dailyPerfItemList);
          vm.repeatContentItemList(response.repeatContentItemList);
          vm.monthDayList(response.monthDayList);
          vm.designatedYearList(response.designatedYearList);

          const param: any = {
            startMode: 0,
            baseDate: moment.utc().toISOString(),
            systemType: 2,
            restrictionOfReferenceRange: true
          }
          vm.$ajax("com", API.findWkpTreeNew, param)
            .then((response: WorkplaceSearchData[]) => {
              vm.workplaceList(vm.convertTreeToArray(response));
              $.when(vm.getProcExecList())
                .then(() => {
                  dfd.resolve();
                });
            })
            .fail((error) => {
              vm.$dialog.alert({ messageId: "Msg_7" });
            });
        });
      // set ntsFixedTable style
      return dfd.promise();
    }

    /**
     * Get alarm by user.
     */
    private getAlarmByUser(): JQueryPromise<any> {
      const vm = this;
      const dfd = $.Deferred<void>();
      vm.$ajax(API.getAlarmByUser)
        .then((response: any) => {
          vm.alarmByUserList(response);
          dfd.resolve();
        })
        .fail((error) => {
          dfd.reject();
          vm.$dialog.error(error)
            .then(() => { vm.$blockui('clear'); });
        });
      return dfd.promise();
    }

    /**
     * Create process execution.
     * 新規 button
     */
    public createProcExec() {
      const vm = this;
      errors.clearAll();
      vm.isNewMode(true);
      vm.selectedExecCode('');
    }

    /**
     * Save process execution.
     * 登録 button
     */
    public saveProcExec() {
      const vm = this;
      // Validate
      if (vm.validate() || errors.hasError()) {
        return;
      }
      if ((vm.currentExecItem().execScopeCls() === 1) && (vm.currentExecItem().workplaceList().length === 0)) {
        vm.$dialog.error({ messageId: "Msg_1294" });
      } else {
        // Get json object
        const command: SaveProcessExecutionCommand = vm.toJsonObject();
        vm.$blockui('grayout');

        // Insert or update process execution
        vm.$ajax(API.saveProcExec, command)
          .then((response) => {
            vm.$blockui('clear');
            // Notice success
            vm.$dialog.info({ messageId: "Msg_15" })
              .then(() => {
                // Get process execution list
                vm.getProcExecList(response);
                vm.$nextTick(() => {
                  vm.focusInput();
                });
              });
          })
          .fail((error: any) => {
            vm.showMessageError(error);
          })
          .always(() => vm.$blockui('clear'));
      }
    }

    /**
     * Delete process execution.
     * 削除 button
     */
    public delProcExec() {
      const vm = this;
      const currentItem = vm.currentExecItem();
      let oldIndex = _.findIndex(vm.execItemList(), (x) => { x.execItemCode === currentItem.execItemCode() });
      const lastIndex = vm.execItemList().length - 1;

      vm.$dialog.confirm({ messageId: "Msg_18" })
        .then((result: 'no' | 'yes' | 'cancel') => {
          if (result === 'yes') {
            let command: RemoveProcessExecutionCommand = new RemoveProcessExecutionCommand();
            command.execItemCode = currentItem.execItemCode();

            vm.$blockui('grayout');
            // Get process exection list
            vm.$ajax(API.deleteProcExec, command)
              .then(() => {
                // Show info
                this.$dialog.info({ messageId: "Msg_16" })
                  .then(() => {
                    // Bussiness logic after info show
                    $.when(vm.getProcExecList())
                      .done(() => {
                        if (vm.execItemList().length > 0) {
                          if (oldIndex === lastIndex) {
                            oldIndex--;
                          }
                          vm.selectedExecCode(vm.execItemList()[oldIndex].execItemCode);
                        }
                      });
                  });
              })
              .fail((res: any) => {
                vm.showMessageError(res);
              })
              .always(() => vm.$blockui('clear'));
          }
        });
    }

    /**
     * Open dialog C
     * 実行タスク設定 button
     */
    public openDialogC() {
      const vm = this;
      vm.$blockui('grayout');
      const canSelected = vm.currentExecItem().workplaceList() ? vm.currentExecItem().workplaceList() : [];
      // Data send to dialog C
      const data: any = {
        execItemCode: vm.currentExecItem().execItemCode(),
        execItemName: vm.currentExecItem().execItemName(),
      };
      vm.$window.modal('/view/kbt/002/c/index.xhtml', data)
        .then(() => {
          vm.$blockui('clear');
        });
    }

    public openDialogK(){
      const vm = this;
      vm.$window.modal('/view/kbt/002/k/index.xhtml')
        .then(() => {
          vm.$blockui('clear');
        });
    }

    public openDialogL(){
      const vm = this;
      vm.$window.modal('/view/kbt/002/l/index.xhtml')
        .then(() => {
          vm.$blockui('clear');
        });
    }

    /**
     * Open dialog CDL008
     * 職場選択 button
     */
    private openDialogCDL008() {
      const vm = this;
      vm.$blockui('grayout');
      const canSelected = vm.currentExecItem().workplaceList() ? vm.currentExecItem().workplaceList() : [];
      // Data send to dialog CDL008
      const data: any = {
        baseDate: moment().utc().toDate(),
        isMultiple: true,
        selectedCodes: canSelected,
        selectedSystemType: 2,
        isrestrictionOfReferenceRange: true,
        showNoSelection: false,
        isShowBaseDate: false
      }
      vm.$window.modal('com', '/view/cdl/008/a/index.xhtml', data)
        .then((result: any) => {
          vm.$blockui('clear');
          if (result) {
            vm.currentExecItem().workplaceList(result);
            vm.buildWorkplaceStr(result);
          }
        });
    }

    /**
     * Get process execution list.
     * @param savedExecItemCode the saved exec item code
     */
    private getProcExecList(savedExecItemCode?: string): JQueryPromise<void> {
      const vm = this;
      const dfd = $.Deferred<void>();

      vm.execItemList([]);
      vm.$ajax(API.getProcExecList)
        .then((response) => {
          if (response && response.length > 0) {
            vm.execItemList(response);
            if (textUtil.isNullOrEmpty(savedExecItemCode)) {
              vm.selectedExecCode(response[0].execItemCode);
            } else {
              vm.selectedExecCode(savedExecItemCode);
            }
          } else {
            vm.createProcExec();
          }
          dfd.resolve();
        });
      return dfd.promise();
    }

    /**
     * Build workplace string.
     * @param wkpIdList workplace id list
     */
    private buildWorkplaceStr(wkpIdList: any) {
      const vm = this;
      let wkpText = '';
      if (wkpIdList) {
        if (wkpIdList.length === 0) {
          wkpText = '';
        } else if (wkpIdList.length === 1) {
          const wkpId = wkpIdList[0];
          const wkp = _.find(vm.workplaceList(), (workplace) => { return workplace.workplaceId === wkpId; });
          if (_.isNil(wkp)) {
            wkpText = getTextResource('KBT002_193');
          } else {
            wkpText = wkp.hierarchyCode + ' ' + wkp.name;
          }
        } else {
          let workplaceList: any[] = [];
          _.each(wkpIdList, wkpId => {
            const workplace = _.find(vm.workplaceList(), (workplace) => { return workplace.workplaceId === wkpId; });
            if (!_.isNil(workplace)) {
              workplaceList.push(workplace);
            }
          });
          if (workplaceList.length > 1) {
            workplaceList = _.sortBy(workplaceList, (wkp) => {
              return parseInt(wkp.hierarchyCode);
            });
            const firstWkp = workplaceList[0];
            const lastWkp = workplaceList[workplaceList.length - 1];
            wkpText = firstWkp.hierarchyCode + ' ' + firstWkp.name + ' ～ ' + lastWkp.hierarchyCode + ' ' + lastWkp.name;
          } else if (workplaceList.length === 1) {
            wkpText = workplaceList[0].hierarchyCode + ' ' + workplaceList[0].name + ' ～ ' + getTextResource('KBT002_193');
          } else {
            wkpText = getTextResource('KBT002_193') + ' ～ ' + getTextResource('KBT002_193');
          }
        }
      }
      vm.workplaceListText(wkpText);
    }

    /**
     * Build target date string
     * @param execItem the exec item
     */
    private buildTargetDateStr(execItem: any) {
      const vm = this;
      let startTargetDate;
      let endTargetDate;
      let today = moment(vm.$date.today())
      let targetDateStr = getTextResource('KBT002_25') + today.format("YYYY/MM/DD") + getTextResource('KBT002_26');

      // Calculate start target date
      if (execItem.targetMonth() === 0) {
        // If target date < current date then set date by current date
        startTargetDate = moment([today.year(), today.month(), execItem.targetDate()]);
      } else if (execItem.targetMonth() === 1) {
        startTargetDate = moment([today.year(), today.month(), execItem.targetDate()]).add(1, 'months');
      } else if (execItem.targetMonth() === 2) {
        startTargetDate = moment([today.year(), today.month(), execItem.targetDate()]).add(2, 'months');
      } else if (execItem.targetMonth() === 3) {
        startTargetDate = moment([today.year(), today.month(), execItem.targetDate()]);
      }
      if (startTargetDate.isValid()) {
        targetDateStr += startTargetDate.format("YYYY/MM/DD");
      }

      // Calculate end target date
      if (vm.currentExecItem().targetDate() === 1) {
        if (execItem.creationPeriod() === 1) {
          endTargetDate = startTargetDate.endOf('month');
        } else {
          endTargetDate = startTargetDate.add(execItem.creationPeriod() - 1, 'months').endOf('month');
        }
      } else {
        endTargetDate = startTargetDate.add(execItem.creationPeriod(), 'months');
      }
      targetDateStr += '～';
      if (startTargetDate.isValid()) {
        targetDateStr += endTargetDate.format("YYYY/MM/DD");
      }
      targetDateStr += 'です';

      return targetDateStr;
    }

    /**
     * Validate.
     * @returns true if there is no error, otherwise false
     */
    private validate(): boolean {
      const vm = this;
      // Clear error
      errors.clearAll();
      // Validate
      $("#B3_2").ntsEditor('validate');
      $("#B3_3").ntsEditor('validate');
      $(".ntsDatepicker").ntsEditor('validate');
      if (vm.currentExecItem().targetMonth() !== 3 && vm.currentExecItem().perScheduleClsNormal() && vm.currentExecItem().processExecType() === 0) {
        $("#B7_7").trigger("validate");
        $("#B7_9").trigger("validate");
      }
      if (vm.currentExecItem().perScheduleCls()) {
        $("#B7_7").ntsEditor('validate');
        $("#B7_9").ntsEditor('validate');
      }
      return $('.nts-input').ntsError('hasError');
    }

    /**
     * To json object.
     * @returns The save process execution command
     */
    private toJsonObject(): SaveProcessExecutionCommand {
      const vm = this;
      // To JsObject
      let command: SaveProcessExecutionCommand = new SaveProcessExecutionCommand();
      command.newMode = vm.isNewMode();
      if (vm.currentExecItem().processExecType() === 0) { //通常実行
        vm.currentExecItem().creationTarget(0);
        command.companyId = vm.currentExecItem().companyId();
        command.execItemCode = vm.currentExecItem().execItemCode();
        command.execItemName = vm.currentExecItem().execItemName();
        command.perScheduleCls = vm.currentExecItem().perScheduleClsNormal();
        if (!vm.currentExecItem().perScheduleClsNormal() || vm.currentExecItem().targetMonth() === 3) {
          command.targetDate = 1;
          command.creationPeriod = 1;
        } else {
          command.creationPeriod = vm.currentExecItem().creationPeriod();
          command.targetDate = vm.currentExecItem().targetDate();
        }
        command.targetMonth = vm.currentExecItem().targetMonth();
        command.creationTarget = vm.currentExecItem().creationTarget();
        command.recreateWorkType = false; //B15_3
        command.manualCorrection = false; //B15_4
        command.createEmployee = vm.currentExecItem().createEmployee();
        command.recreateTransfer = false; //B15_2(1)
        command.dailyPerfCls = vm.currentExecItem().dailyPerfClsNormal(); //B8_1
        command.dailyPerfItem = vm.currentExecItem().dailyPerfItem();
        command.midJoinEmployee = vm.currentExecItem().midJoinEmployee();
        command.reflectResultCls = vm.currentExecItem().reflectResultCls();
        command.monthlyAggCls = vm.currentExecItem().monthlyAggCls();
        command.execScopeCls = vm.currentExecItem().execScopeCls();
        command.refDate = textUtil.isNullOrEmpty(vm.currentExecItem().refDate()) ? null : new Date(vm.currentExecItem().refDate());
        command.workplaceList = vm.currentExecItem().workplaceList();
        command.recreateTypeChangePerson = false;
        command.recreateTransfers = false; //B15_2(2)
        command.appRouteUpdateAtr = vm.currentExecItem().appRouteUpdateAtrNormal()
        command.createNewEmp = vm.currentExecItem().createNewEmp();
        command.appRouteUpdateMonthly = vm.currentExecItem().appRouteUpdateMonthly();
        command.processExecType = vm.currentExecItem().processExecType();
        command.alarmCode = _.isNil(vm.currentExecItem().alarmCode()) ? null : vm.currentExecItem().alarmCode();
        command.alarmAtr = vm.currentExecItem().alarmAtr();
        command.mailPrincipal = vm.currentExecItem().mailPrincipal();
        command.mailAdministrator = vm.currentExecItem().mailAdministrator();
        command.designatedYear = vm.currentExecItem().designatedYear();
        command.startMonthDay = vm.currentExecItem().startMonthDay();
        command.endMonthDay = vm.currentExecItem().endMonthDay();
      } else { //再作成
        vm.alarmByUserList()[0] = undefined;
        vm.currentExecItem().creationTarget(1);
        command.companyId = vm.currentExecItem().companyId();
        command.execItemCode = vm.currentExecItem().execItemCode();
        command.execItemName = vm.currentExecItem().execItemName();
        command.perScheduleCls = vm.currentExecItem().perScheduleClsReCreate(); //B7_1
        command.targetMonth = 0;
        command.targetDate = 1;
        command.creationPeriod = 1;
        command.creationTarget = 1;
        command.recreateWorkType = vm.currentExecItem().recreateWorkType(); //B15_3
        command.manualCorrection = vm.currentExecItem().manualCorrection(); //B15_4
        command.createEmployee = false;
        command.recreateTransfer = vm.currentExecItem().recreateTransfer(); //B15_2(1)
        command.dailyPerfCls = vm.currentExecItem().dailyPerfClsReCreate(); //B14_3
        command.dailyPerfItem = 0;
        command.midJoinEmployee = false;
        command.reflectResultCls = vm.currentExecItem().dailyPerfClsReCreate();
        command.monthlyAggCls = vm.currentExecItem().dailyPerfClsReCreate();
        command.execScopeCls = vm.currentExecItem().execScopeCls();
        command.refDate = textUtil.isNullOrEmpty(vm.currentExecItem().refDate()) ? null : new Date(vm.currentExecItem().refDate());
        command.workplaceList = vm.currentExecItem().workplaceList();
        command.recreateTypeChangePerson = vm.currentExecItem().recreateTypeChangePerson(); //B14_2
        command.recreateTransfers = vm.currentExecItem().recreateTransfer(); //B15_2(2)
        command.appRouteUpdateAtr = vm.currentExecItem().appRouteUpdateAtrReCreate();
        command.createNewEmp = false;
        command.appRouteUpdateMonthly = false;
        command.processExecType = vm.currentExecItem().processExecType();
        command.alarmCode = _.isNil(vm.alarmByUserList()[0]) ? null : vm.alarmByUserList()[0].alarmCode;
        command.alarmAtr = false;
        command.mailPrincipal = false;
        command.mailAdministrator = false;
        command.designatedYear = 0;
        command.startMonthDay = 101;
        command.endMonthDay = 101;
      }
      return command;
    }

    /**
     * Show message error.
     * @param res the response
     */
    private showMessageError(res: any) {
      if (res.businessException) {
        const vm = this;
        vm.$dialog.error({ messageId: res.messageId, messageParams: res.parameterIds })
      }
    }

    /**
     * Focus input.
     */
    private focusInput() {
      const vm = this;
      if (vm.isNewMode()) {
        $('#B3_2').focus();
      } else {
        $('#B3_3').focus();
      }
    }

    /**
     * Convert tree data to array.
     * @param dataList the data list
     * @returns the any array
     */
    private convertTreeToArray(dataList: any[]): any[] {
      const vm = this;
      let res: any[] = [];
      _.forEach(dataList, (item) => {
        if (item.children && item.children.length > 0) {
          res = res.concat(vm.convertTreeToArray(item.children));
        }
        res.push({ workplaceId: item.id, hierarchyCode: item.code, name: item.name });
      })
      return res;
    }

    /**
     * Open screen J.
     */
    public openScreenJ() {
      const vm = this;
      vm.$blockui('grayout');
      vm.$window.modal('/view/kbt/002/j/index.xhtml')
        .then(() => {
          vm.$blockui('clear');
        });
    }
  }

  /** The interface IExecution item */
  export interface IExecutionItem {
    companyId: string;
    execItemCode: string;
    execItemName: string;
    perScheduleCls: boolean;
    perScheduleClsNormal: boolean;
    perScheduleClsReCreate: boolean;
    targetMonth: number;
    targetDate: number;
    creationPeriod: number;
    creationTarget: number;
    recreateWorkType: boolean;
    manualCorrection: boolean;
    createEmployee: boolean;
    recreateTransfer: boolean;
    dailyPerfCls: boolean;
    dailyPerfItem: number;
    //lastProcDate: string;
    midJoinEmployee: boolean;
    reflectResultCls: boolean;
    monthlyAggCls: boolean;
    execScopeCls: number;
    refDate: string;
    workplaceList: Array<string>;
    recreateTypeChangePerson: boolean;
    recreateTransfers: boolean;
    appRouteUpdateAtr: boolean;
    createNewEmp: boolean;
    appRouteUpdateMonthly: boolean;
    processExecType: number;
    appRouteUpdateAtrNormal: boolean;
    appRouteUpdateAtrReCreate: boolean;
    //daily perf cls
    dailyPerfClsNormal: boolean;
    dailyPerfClsReCreate: boolean;
    //alarm code
    alarmCode: string;
    alarmAtr: boolean;
    mailPrincipal: boolean;
    mailAdministrator: boolean;
    //ver29
    designatedYear: number;
    startMonthDay: number;
    endMonthDay: number;
  }

  /**
   * The class Execution item.
   */
  export class ExecutionItem {
    companyId: KnockoutObservable<string> = ko.observable('');
    execItemCode: KnockoutObservable<string> = ko.observable('');
    execItemName: KnockoutObservable<string> = ko.observable('');
    perScheduleCls: KnockoutObservable<boolean> = ko.observable(false);
    perScheduleClsNormal: KnockoutObservable<boolean> = ko.observable(false);
    perScheduleClsReCreate: KnockoutObservable<boolean> = ko.observable(false);
    targetMonth: KnockoutObservable<number> = ko.observable(null);
    targetDate: KnockoutObservable<number> = ko.observable(null);
    creationPeriod: KnockoutObservable<number> = ko.observable(null);
    creationTarget: KnockoutObservable<number> = ko.observable(null);
    recreateWorkType: KnockoutObservable<boolean> = ko.observable(false);
    manualCorrection: KnockoutObservable<boolean> = ko.observable(false);
    createEmployee: KnockoutObservable<boolean> = ko.observable(false);
    recreateTransfer: KnockoutObservable<boolean> = ko.observable(false);
    dailyPerfCls: KnockoutObservable<boolean> = ko.observable(false);
    dailyPerfItem: KnockoutObservable<number> = ko.observable(null);
    midJoinEmployee: KnockoutObservable<boolean> = ko.observable(false);
    reflectResultCls: KnockoutObservable<boolean> = ko.observable(false);
    monthlyAggCls: KnockoutObservable<boolean> = ko.observable(false);
    execScopeCls: KnockoutObservable<number> = ko.observable(null);
    refDate: KnockoutObservable<string> = ko.observable('');
    workplaceList: KnockoutObservableArray<any> = ko.observableArray([]);
    recreateTypeChangePerson: KnockoutObservable<boolean> = ko.observable(false);
    recreateTransfers: KnockoutObservable<boolean> = ko.observable(false);
    appRouteUpdateAtr: KnockoutObservable<boolean> = ko.observable(false);
    createNewEmp: KnockoutObservable<boolean> = ko.observable(false);
    appRouteUpdateMonthly: KnockoutObservable<boolean> = ko.observable(false);
    checkCreateNewEmp: KnockoutObservable<boolean> = ko.observable(false);
    processExecType: KnockoutObservable<number> = ko.observable(null);
    appRouteUpdateAtrNormal: KnockoutObservable<boolean> = ko.observable(false);
    appRouteUpdateAtrReCreate: KnockoutObservable<boolean> = ko.observable(false);
    dailyPerfClsNormal: KnockoutObservable<boolean> = ko.observable(false);
    dailyPerfClsReCreate: KnockoutObservable<boolean> = ko.observable(false);
    alarmCode: KnockoutObservable<string> = ko.observable('');
    alarmAtr: KnockoutObservable<boolean> = ko.observable(false);
    mailPrincipal: KnockoutObservable<boolean> = ko.observable(false);
    mailAdministrator: KnockoutObservable<boolean> = ko.observable(false);
    //ver29
    designatedYear: KnockoutObservable<number> = ko.observable(null);
    startMonthDay: KnockoutObservable<number> = ko.observable(101);
    endMonthDay: KnockoutObservable<number> = ko.observable(101);
    disableYearMonthDate: KnockoutObservable<boolean> = ko.observable(false);
    enableMonthDay: KnockoutObservable<boolean> = ko.observable(false);

    constructor(param: IExecutionItem) {
      const self = this;
      if (param && param !== null) {
        self.companyId(param.companyId);
        self.execItemCode(param.execItemCode || '');
        self.execItemName(param.execItemName || '');
        self.perScheduleCls(param.perScheduleCls || false);
        self.perScheduleClsNormal(param.perScheduleCls || false);
        self.perScheduleClsReCreate(param.perScheduleCls || false);
        self.targetMonth(param.targetMonth);
        self.targetDate(param.targetDate);
        self.creationPeriod(param.creationPeriod);
        self.creationTarget(param.creationTarget);
        self.recreateWorkType(param.recreateWorkType || false);
        self.manualCorrection(param.manualCorrection || false);
        self.createEmployee(param.createEmployee || false);
        self.recreateTransfer(param.recreateTransfer || false);
        self.dailyPerfCls(param.dailyPerfCls || false);
        self.dailyPerfItem(param.dailyPerfItem);
        self.midJoinEmployee(param.midJoinEmployee || false);
        self.reflectResultCls(param.reflectResultCls || false);
        self.monthlyAggCls(param.monthlyAggCls || false);
        self.execScopeCls(param.execScopeCls);
        self.refDate(param.refDate || moment().format("YYYY/MM/DD"));
        self.workplaceList(param.workplaceList || []);
        self.recreateTypeChangePerson(param.recreateTypeChangePerson || false);
        self.recreateTransfers(param.recreateTransfers || false);
        self.appRouteUpdateAtr(param.appRouteUpdateAtr || false);
        self.createNewEmp(param.createNewEmp || false);
        self.appRouteUpdateMonthly(param.appRouteUpdateMonthly || false);
        self.checkCreateNewEmp((param.appRouteUpdateAtr === true && param.appRouteUpdateAtr === true) ? true : false);
        self.processExecType(param.processExecType);
        self.appRouteUpdateAtrNormal(param.appRouteUpdateAtr || false);
        self.appRouteUpdateAtrReCreate(param.appRouteUpdateAtr || false);
        self.dailyPerfClsNormal(param.dailyPerfCls || false);
        self.dailyPerfClsReCreate(param.dailyPerfCls || false);
        self.alarmCode(param.alarmCode || '');
        self.alarmAtr(param.alarmAtr || false);
        self.mailPrincipal(param.mailPrincipal || false);
        self.mailAdministrator(param.mailAdministrator || false);
        self.designatedYear(param.designatedYear);
        self.startMonthDay(param.startMonthDay === null ? 101 : param.startMonthDay);
        self.endMonthDay(param.endMonthDay === null ? 101 : param.endMonthDay);
        if (self.targetMonth() === 3) {
          self.disableYearMonthDate(true);
        } else {
          self.disableYearMonthDate(false);
        }
        if (self.processExecType() === 0) {
          self.creationTarget(0);
          self.appRouteUpdateAtrNormal(self.appRouteUpdateAtr());
          self.appRouteUpdateAtrReCreate(false);

          self.dailyPerfClsNormal(self.dailyPerfCls());
          self.dailyPerfClsReCreate(false);

          self.perScheduleClsNormal(self.perScheduleCls());
          self.perScheduleClsReCreate(false);

          if (self.perScheduleClsNormal()) {
            self.enableMonthDay(true);
          }
        } else {
          self.creationTarget(1);
          self.appRouteUpdateAtrNormal(false);
          self.appRouteUpdateAtrReCreate(self.appRouteUpdateAtr());

          self.dailyPerfClsNormal(false);
          self.dailyPerfClsReCreate(self.dailyPerfCls());

          self.perScheduleClsNormal(false);
          self.perScheduleClsReCreate(self.perScheduleCls());

          if (self.perScheduleClsNormal()) {
            self.enableMonthDay(false);
          }
        }
      } else {
        self.companyId('');
        self.execItemCode('');
        self.execItemName('');
        self.perScheduleCls(false);
        self.targetMonth(0);
        self.targetDate(null);
        self.creationPeriod(null);
        self.creationTarget(0);
        self.recreateWorkType(false);
        self.manualCorrection(false);
        self.createEmployee(false);
        self.recreateTransfer(false);
        self.dailyPerfCls(false);
        self.dailyPerfItem(0);
        self.midJoinEmployee(false);
        self.reflectResultCls(false);
        self.monthlyAggCls(false);
        self.execScopeCls(0);
        self.refDate(moment().format("YYYY/MM/DD"));
        self.workplaceList([]);
        self.recreateTypeChangePerson(false);
        self.recreateTransfers(false);
        self.appRouteUpdateAtr(false);
        self.createNewEmp(false);
        self.appRouteUpdateMonthly(false);
        self.checkCreateNewEmp(false);
        self.processExecType(0);
        self.appRouteUpdateAtrNormal(false);
        self.appRouteUpdateAtrReCreate(false);
        self.dailyPerfClsNormal(false);
        self.dailyPerfClsReCreate(false);
        self.alarmCode('');
        self.alarmAtr(false);
        self.perScheduleClsNormal(false);
        self.perScheduleClsReCreate(false);
        self.mailPrincipal(false);
        self.mailAdministrator(false);
        self.processExecType(0);
        self.designatedYear(0);
        self.startMonthDay(101);
        self.endMonthDay(101);
      }
      /**
       * vm.targetMonth(): 
       * 0: システム日付の月
       * 1: システム日付の翌月
       * 2: システム日付の翌々月
       * 3: 開始月を指定する
       */
      self.targetMonth.subscribe(x => {
        errors.clearAll();
        if (x !== 3) {
          self.designatedYear(0);
          self.startMonthDay(101);
          self.endMonthDay(101);
          self.disableYearMonthDate(false);
        } else {
          self.disableYearMonthDate(true);
        }
      });

      self.perScheduleClsNormal.subscribe(x => {
        if (x === true && self.perScheduleClsNormal() === true) {
          self.enableMonthDay(true);
        } else {
          self.enableMonthDay(false);
        }
      });

      self.appRouteUpdateAtr.subscribe(x => {
        if (x === true && self.perScheduleCls() === true) {
          self.checkCreateNewEmp(true);
        } else {
          self.checkCreateNewEmp(false);
        }
      });

      self.perScheduleCls.subscribe(x => {
        if (x === true && self.appRouteUpdateAtr() === true) {
          self.checkCreateNewEmp(true);
        } else {
          self.checkCreateNewEmp(false);
        }
      });

      self.processExecType.subscribe(x => {
        if (x === 0) {
          self.creationTarget(0);
          self.appRouteUpdateAtrNormal(self.appRouteUpdateAtr());
          self.appRouteUpdateAtrReCreate(false);

          self.dailyPerfClsNormal(self.dailyPerfCls());
          self.dailyPerfClsReCreate(false);
          if (self.perScheduleClsNormal()) {
            self.enableMonthDay(true);
          }
        } else {
          self.creationTarget(1);
          self.appRouteUpdateAtrNormal(false);
          self.appRouteUpdateAtrReCreate(self.appRouteUpdateAtr());

          self.dailyPerfClsNormal(false);
          self.dailyPerfClsReCreate(self.dailyPerfCls());

          if (self.perScheduleClsNormal()) {
            self.enableMonthDay(false);
          }
        }
      });

      // self.targetDate.subscribe(x => {
      //   const data = this;
      //   (__viewContext as any).viewModel.targetDateText((__viewContext as any).viewModel.buildTargetDateStr(data) || '');
      // });
      // self.creationPeriod.subscribe(x => {
      //   const data = this;
      //   (__viewContext as any).viewModel.targetDateText((__viewContext as any).viewModel.buildTargetDateStr(data) || '');
      // });
      // self.targetMonth.subscribe(x => {
      //   const data = this;
      //   (__viewContext as any).viewModel.targetDateText((__viewContext as any).viewModel.buildTargetDateStr(data) || '');
      // });
    }

    createData(param: IExecutionItem) {
      const vm = this;

      vm.companyId(param.companyId);
      vm.execItemCode(param.execItemCode || '');
      vm.execItemName(param.execItemName || '');
      vm.perScheduleCls(param.perScheduleCls || false);
      vm.perScheduleClsNormal(param.perScheduleCls || false);
      vm.perScheduleClsReCreate(param.perScheduleCls || false);
      vm.targetMonth(param.targetMonth);
      vm.targetDate(param.targetDate);
      vm.creationPeriod(param.creationPeriod);
      vm.creationTarget(param.creationTarget);
      vm.recreateWorkType(param.recreateWorkType || false);
      vm.manualCorrection(param.manualCorrection || false);
      vm.createEmployee(param.createEmployee || false);
      vm.recreateTransfer(param.recreateTransfer || false);
      vm.dailyPerfCls(param.dailyPerfCls || false);
      vm.dailyPerfItem(param.dailyPerfItem);
      vm.midJoinEmployee(param.midJoinEmployee || false);
      vm.reflectResultCls(param.reflectResultCls || false);
      vm.monthlyAggCls(param.monthlyAggCls || false);
      vm.execScopeCls(param.execScopeCls);
      vm.refDate(param.refDate || moment().format("YYYY/MM/DD"));
      vm.workplaceList(param.workplaceList || []);
      vm.recreateTypeChangePerson(param.recreateTypeChangePerson || false);
      vm.recreateTransfers(param.recreateTransfers || false);
      vm.appRouteUpdateAtr(param.appRouteUpdateAtr || false);
      vm.createNewEmp(param.createNewEmp || false);
      vm.appRouteUpdateMonthly(param.appRouteUpdateMonthly || false);
      vm.checkCreateNewEmp((param.appRouteUpdateAtr === true && param.appRouteUpdateAtr === true) ? true : false);
      vm.processExecType(param.processExecType);
      vm.appRouteUpdateAtrNormal(param.appRouteUpdateAtr || false);
      vm.appRouteUpdateAtrReCreate(param.appRouteUpdateAtr || false);
      vm.dailyPerfClsNormal(param.dailyPerfCls || false);
      vm.dailyPerfClsReCreate(param.dailyPerfCls || false);
      vm.alarmCode(param.alarmCode || '');
      vm.alarmAtr(param.alarmAtr || false);
      vm.mailPrincipal(param.mailPrincipal || false);
      vm.mailAdministrator(param.mailAdministrator || false);
      vm.designatedYear(param.designatedYear);
      vm.startMonthDay(param.startMonthDay === null ? 101 : param.startMonthDay);
      vm.endMonthDay(param.endMonthDay === null ? 101 : param.endMonthDay);
      if (vm.targetMonth() === 3) {
        vm.disableYearMonthDate(true);
      } else {
        vm.disableYearMonthDate(false);
      }

      if (vm.processExecType() === 0) {
        vm.creationTarget(0);
        vm.appRouteUpdateAtrNormal(vm.appRouteUpdateAtr());
        vm.appRouteUpdateAtrReCreate(false);

        vm.dailyPerfClsNormal(vm.dailyPerfCls());
        vm.dailyPerfClsReCreate(false);

        vm.perScheduleClsNormal(vm.perScheduleCls());
        vm.perScheduleClsReCreate(false);

        if (vm.perScheduleClsNormal()) {
          vm.enableMonthDay(true);
        }
      } else {
        vm.creationTarget(1);
        vm.appRouteUpdateAtrNormal(false);
        vm.appRouteUpdateAtrReCreate(vm.appRouteUpdateAtr());

        vm.dailyPerfClsNormal(false);
        vm.dailyPerfClsReCreate(vm.dailyPerfCls());

        vm.perScheduleClsNormal(false);
        vm.perScheduleClsReCreate(vm.perScheduleCls());

        if (vm.perScheduleClsNormal()) {
          vm.enableMonthDay(false);
        }
      }
    }
  }

  /**
   * The class Task enable setting classification code.
   */
  export class TaskEnableSettingClassificationCode {
    static ENABLED = 1;
    static DISABLED = 2;
  }

  /**
   * The class Tab panel id.
   */
  export class TabPanelId {
    static TAB_1 = 'tab-1';
    static TAB_2 = 'tab-2';
    static TAB_3 = 'tab-3';
    static TAB_4 = 'tab-4';
  }

  /**
   * The interface Exec item enum dto.
   */
  export interface ExecItemEnumDto {
    /** The target month list */
    targetMonthList: EnumConstantDto[];
    /** The daily perf item list */
    dailyPerfItemList: EnumConstantDto[];
    /** The repeat content item list */
    repeatContentItemList: EnumConstantDto[];
    /** The month day list */
    monthDayList: EnumConstantDto[];
    /** The designated year list */
    designatedYearList: EnumConstantDto[];
  }

  /**
   * The interface Enum constant dto.
   */
  export interface EnumConstantDto {
    /** The value */
    value: number;
    /** The field name */
    fieldName: string;
    /** The localized name */
    localizedName: string;
  }

  /**
   * The class Workplace search data.
   */
  export class WorkplaceSearchData {
    /** The workplace id */
    workplaceId: string;
    /** The code */
    code: string;
    /** The name */
    name: string;
  }

  /**
   * The class Target group class.
   */
  export class TargetGroupClass {
    /** The target id */
    targetId: number;
    /** The target name */
    targetName: string;

    constructor(init?: Partial<TargetGroupClass>) {
      $.extend(this, init);
    }
  }

  export class ItemModel {
    code: number;
    name: string;

    constructor(init?: Partial<ItemModel>) {
      $.extend(this, init);
    }
  }

  /**
   * The class Save process execution command.
   */
  export class SaveProcessExecutionCommand {
    newMode: boolean;

    /** 会社ID */
    companyId: string;

    /**コード */
    execItemCode: string;

    /**名称 */
    execItemName: string;

    /**個人スケジュール作成 */
    perScheduleCls: boolean;

    /**対象月 */
    targetMonth: number;

    /**対象日 */
    targetDate: number;

    /**作成期間 */
    creationPeriod: number;

    /**作成対象 */
    creationTarget: number;

    /**勤務種別変更者を再作成 */
    recreateWorkType: boolean;

    /**手修正を保護する */
    manualCorrection: boolean;

    /**新入社員を作成する */
    createEmployee: boolean;

    /**異動者を再作成する */
    recreateTransfer: boolean;

    /**日別実績の作成・計算 */
    dailyPerfCls: boolean;

    /**作成・計算項目 */
    dailyPerfItem: number;

    /**前回処理日 */
    lastProcDate: any;

    /**途中入社は入社日からにする */
    midJoinEmployee: boolean;

    /**承認結果反映 */
    reflectResultCls: boolean;

    /**月別集計 */
    monthlyAggCls: boolean;

    execScopeCls: number;

    refDate: any;

    workplaceList: string[];

    /**更新処理の日別処理対象者区分.勤務種別変更者を再作成 */
    recreateTypeChangePerson: boolean;

    /**更新処理の日別処理対象者区分.異動者を再作成する */
    recreateTransfers: boolean;

    /** 承認ルート更新区分 */
    appRouteUpdateAtr: boolean;

    /** 新入社員を作成する */
    createNewEmp: boolean;

    /**承認ルート更新（月次） */
    appRouteUpdateMonthly: boolean;

    /**実行種別*/
    processExecType: number;

    alarmAtr: boolean;

    alarmCode: string;

    mailPrincipal: boolean;

    mailAdministrator: boolean;

    /**指定年 */
    designatedYear: number;

    /**指定開始月日 */
    startMonthDay: number;

    /**指定終了月日*/
    endMonthDay: number;

    constructor(init?: Partial<SaveProcessExecutionCommand>) {
      $.extend(this, init);
    }
  }

  /**
   * The class Remove process execution command.
   */
  export class RemoveProcessExecutionCommand {
    /** The exec item code */
    execItemCode: string;

    constructor(init?: Partial<RemoveProcessExecutionCommand>) {
      $.extend(this, init);
    }
  }

}