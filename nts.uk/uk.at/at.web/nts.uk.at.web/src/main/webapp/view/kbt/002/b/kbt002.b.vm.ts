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
    findWkpTreeNew: 'bs/employee/wkpdep/get-wkpdepinfo-kcp004',
    selectProcExec: 'at/function/processexec/findProcessExecution/{0}'
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
    currentExecItem: KnockoutObservable<ExecutionItem> = ko.observable(new ExecutionItem({}));

    // Display text
    workplaceListText: KnockoutObservable<string> = ko.observable('');
    targetDateText: KnockoutObservable<string> = ko.observable('');

    // Enumeration list
    targetMonthList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);

    targetDailyPerfItemList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
    repeatContentItemList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
    monthDayList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
    designatedYearList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
    
    // List aggregate period
    aggrPeriodList: KnockoutObservableArray<any> = ko.observableArray([]);

    // List get alarm by user
    alarmByUserList: KnockoutObservableArray<any> = ko.observableArray([]);

    // B5_2 タスク有効設定
    taskEnableSettingList: KnockoutObservableArray<any> = ko.observableArray([
      { code: TaskEnableSettingClassificationCode.ENABLED, name: getTextResource('KBT002_123') },
      { code: TaskEnableSettingClassificationCode.DISABLED, name: getTextResource('KBT002_124') },
    ]);
    selectedTaskEnableSetting: KnockoutObservable<any> = ko.observable(null);
    executionTaskWarning: KnockoutObservable<string> = ko.observable('');

    tabs: KnockoutObservableArray<any> = ko.observableArray([
      { id: TabPanelId.TAB_1, title: getTextResource('KBT002_279'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
      { id: TabPanelId.TAB_2, title: getTextResource('KBT002_40'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
      { id: TabPanelId.TAB_3, title: getTextResource('KBT002_293'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
      { id: TabPanelId.TAB_4, title: getTextResource('KBT002_280'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
    ]);
    selectedTab: KnockoutObservable<string> = ko.observable('');

    columns: KnockoutObservableArray<any> = ko.observableArray([
      { headerText: getTextResource('KBT002_7'), key: 'conditionSetCode', width: 55 },
      { headerText: getTextResource('KBT002_8'), key: 'conditionSetName', width: 150 }
    ]);
    stdOutputList: KnockoutObservableArray<any> = ko.observableArray([]);
    currentStdOutputList: KnockoutObservableArray<any> = ko.observableArray([]);
    stdAcceptList: KnockoutObservableArray<any> = ko.observableArray([]);
    currentStdAcceptList: KnockoutObservableArray<any> = ko.observableArray([]);
    indexReconList: KnockoutObservableArray<any> = ko.observableArray([]);
    currentIndexReconList: KnockoutObservableArray<any> = ko.observableArray([]);
    indexReconColumns: KnockoutObservableArray<any> = ko.observableArray([
      { headerText: getTextResource('KBT002_7'), key: 'categoryNo', width: 55 },
      { headerText: getTextResource('KBT002_8'), key: 'categoryName', width: 150 }
    ]);
    
    storagePatternList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    storagePattern: KnockoutObservable<string> = ko.observable('');
    deletionPatternList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    deletionPattern: KnockoutObservable<string> = ko.observable('');

    created() {
      const vm = this;
      vm.selectedTaskEnableSetting(TaskEnableSettingClassificationCode.ENABLED);
      vm.selectedTab(TabPanelId.TAB_1);
      vm.$ajax(API.getMasterInfo)
        .then((response: any) => {
          console.log(response);
          vm.aggrPeriodList(_.map(response.aggrPeriodList, (item: any) => new ItemModel({ code: item.aggrFrameCode, name: item.optionalAggrName })));
          vm.stdAcceptList(response.stdAcceptCondSetList);
          vm.stdOutputList(response.stdOutputCondSetList);
          vm.storagePatternList(_.map(response.dataStoragePatternSetList, (item: any) => new ItemModel({ code: item.patternCode, name: item.patternName })));
          vm.deletionPatternList(_.map(response.dataDelPatternSetList, (item: any) => new ItemModel({ code: item.patternCode, name: item.patternName })));
        })
        .fail(err => { errors.clearAll(); });

      vm.selectedExecCode.subscribe(execItemCode => {
        errors.clearAll();
        // Init process execution
        vm.currentExecItem(new ExecutionItem({}));
        vm.workplaceListText('');
        vm.targetDateText('');
        if (textUtil.isNullOrEmpty(execItemCode)) {
          vm.isNewMode(true);
        } else {
          // set update mode
          vm.$blockui("grayout");
          vm.$ajax(textUtil.format(API.selectProcExec, execItemCode))
            .then(res => {
              console.log(res);
              vm.selectedTaskEnableSetting((res.taskSetting && res.taskSetting.enabledSetting) ? TaskEnableSettingClassificationCode.ENABLED : TaskEnableSettingClassificationCode.DISABLED); // B5_2
              vm.currentExecItem().createData(res);
              vm.currentExecItem().workplaceList(res.workplaceInfos);
              vm.buildWorkplaceStr(vm.currentExecItem().workplaceList()); // B4_6, 7, 9, 10
              vm.buildExecutionTaskWarningStr(); // B5_6
              if (vm.currentExecItem().perScheduleCls()) {
                // vm.targetDateText('targetDateText');
                vm.targetDateText(vm.buildTargetDateStr(vm.currentExecItem()));
              }
              vm.isNewMode(false);
            })
            .always(() => vm.$blockui("clear"));

          // const data = _.filter(vm.execItemList(), (item) => { return item.execItemCode === execItemCode; });
          // if (data[0]) {
          //   vm.currentExecItem().createData(data[0]);
          //   vm.buildWorkplaceStr(vm.currentExecItem().workplaceList());
          //   if (vm.currentExecItem().perScheduleCls()) {
          //     // vm.targetDateText(vm.buildTargetDateStr(vm.currentExecItem()) || '');
          //     vm.targetDateText('targetDateText');
          //   }
          // }
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

      vm.currentExecItem().aggrFrameCode.subscribe(data => console.log(data));
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
        //TODO
        cloudCreationFlag: false
      };
      vm.$window.modal('/view/kbt/002/c/index.xhtml', data)
        .then(() => {
          vm.$blockui('clear');
        });
    }

    public openDialogK() {
      const vm = this;
      vm.$window.modal('/view/kbt/002/k/index.xhtml')
        .then(() => {
          vm.$blockui('clear');
        });
    }

    public openDialogL() {
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
    public openDialogCDL008() {
      const vm = this;
      vm.$blockui('grayout');
      const canSelected = vm.currentExecItem().workplaceList() ? vm.currentExecItem().workplaceList() : [];
      // Data send to dialog CDL008
      nts.uk.ui.windows.setShared('inputCDL008', {
        baseDate: moment.utc().toDate(),
        isMultiple: true,
        selectedCodes: canSelected,
        selectedSystemType: 2,
        isrestrictionOfReferenceRange: true,
        showNoSelection: false,
        isShowBaseDate: false
      });
      nts.uk.ui.windows.sub.modal("com", "/view/cdl/008/a/index.xhtml").onClosed(function () {
        vm.$blockui("clear")
        const data = nts.uk.ui.windows.getShared('outputCDL008');
        if (data) {
          vm.currentExecItem().workplaceList(data);
          vm.buildWorkplaceStr(data);
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

    private buildExecutionTaskWarningStr() {
      const vm = this;
      // TODO
      vm.executionTaskWarning("TODO");
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
        // vm.currentExecItem().creationTarget(0);
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
        // command.creationTarget = vm.currentExecItem().creationTarget();
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
        // vm.currentExecItem().creationTarget(1);
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
        // command.recreateTypeChangePerson = vm.currentExecItem().recreateTypeChangePerson(); //B14_2
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
      vm.$window.modal('/view/kbt/002/j/index.xhtml', { aggrFrameCode: vm.currentExecItem().aggrFrameCode() })
        .then((res: any) => {
          vm.$blockui('clear');
          vm.aggrPeriodList(_.map(res.aggrPeriodList, (item: any) => new ItemModel({ code: item.aggrFrameCode, name: item.optionalAggrName })));
          vm.currentExecItem().alarmCode(res.aggrFrameCode);
        });
    }
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
    // creationTarget: KnockoutObservable<number> = ko.observable(null);
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
    // recreateTypeChangePerson: KnockoutObservable<boolean> = ko.observable(false);
    // recreateTransfers: KnockoutObservable<boolean> = ko.observable(false);
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

    // Ver34
    aggrFrameCode: KnockoutObservable<string> = ko.observable('');
    aggrPeriodCls: KnockoutObservable<boolean> = ko.observable(false);
    stdOutputCls: KnockoutObservable<boolean> = ko.observable(false);
    stdAcceptCls: KnockoutObservable<boolean> = ko.observable(false);
    displayAdministrator: KnockoutObservable<boolean> = ko.observable(false);
    displayPrincipal: KnockoutObservable<boolean> = ko.observable(false);
    storageCls: KnockoutObservable<boolean> = ko.observable(false);
    storagePattern: KnockoutObservable<string> = ko.observable('');
    deletionCls: KnockoutObservable<boolean> = ko.observable(false);
    deletionPattern: KnockoutObservable<string> = ko.observable('');
    indexReconCls: KnockoutObservable<boolean> = ko.observable(false);
    indexReconUpdateStats: KnockoutObservable<number> = ko.observable(null);

    constructor(init?: Partial<ExecutionItem>) {
      $.extend(this, init);
    }

    createData(param: any) {
      const vm = this;
      const taskSetting = param.taskSetting;
      const processExecution = param.processExecution;
      const workplaceInfos = param.workplaceInfos;

      vm.companyId(processExecution.companyId);
      vm.execItemCode(processExecution.execItemCode || ''); // B3_3
      vm.execItemName(processExecution.execItemName || ''); // B3_3
      
      vm.execScopeCls(processExecution.execScope.execScopeCls); // B4_2
      vm.refDate(processExecution.execScope.refDate || moment().format("YYYY/MM/DD")); // B4_12

      vm.processExecType(processExecution.executionType); // B16_1

      vm.perScheduleCls(processExecution.execSetting.perSchedule.perScheduleCls || false); 
      vm.perScheduleClsNormal(processExecution.execSetting.perSchedule.perScheduleCls || false); // B7_1
      vm.targetMonth(processExecution.execSetting.perSchedule.perSchedulePeriod.targetMonth); // B7_4
      vm.targetDate(processExecution.execSetting.perSchedule.perSchedulePeriod.targetDate); // B7_7
      vm.creationPeriod(processExecution.execSetting.perSchedule.perSchedulePeriod.creationPeriod);  // B7_9
      vm.designatedYear(processExecution.execSetting.perSchedule.perSchedulePeriod.designatedYear); // B7_12
      vm.startMonthDay(processExecution.execSetting.perSchedule.perSchedulePeriod.startMonthDay === null ? 101 : processExecution.execSetting.perSchedule.perSchedulePeriod.startMonthDay); // B7_14
      vm.endMonthDay(processExecution.execSetting.perSchedule.perSchedulePeriod.endMonthDay === null ? 101 : processExecution.execSetting.perSchedule.perSchedulePeriod.endMonthDay); // B7_16
      vm.createEmployee(processExecution.execSetting.perSchedule.createNewEmpSched || false); // B7_22

      vm.dailyPerfCls(processExecution.execSetting.dailyPerf.dailyPerfCls || false);
      vm.dailyPerfClsNormal(vm.dailyPerfCls() || false); // B8_1
      vm.dailyPerfItem(processExecution.execSetting.dailyPerf.dailyPerfItem); // B8_3
      vm.midJoinEmployee(processExecution.execSetting.dailyPerf.createNewEmpDailyPerf || false); // B8_5

      vm.reflectResultCls(processExecution.execSetting.reflectResultCls || false); // B9_1

      vm.monthlyAggCls(processExecution.execSetting.monthlyAggCls); // B10_1

      vm.aggrPeriodCls(processExecution.execSetting.aggregationOfArbitraryPeriod.classificationOfUse === 1 ? true : false); // B9_1
      vm.aggrFrameCode(processExecution.execSetting.aggregationOfArbitraryPeriod.aggrFrameCode || getTextResource("KBT002_193")); // B11_2

      vm.appRouteUpdateAtr(processExecution.execSetting.appRouteUpdateDaily.appRouteUpdateAtr || false); 
      vm.appRouteUpdateAtrNormal(vm.appRouteUpdateAtr() || false); // B12_1
      vm.checkCreateNewEmp((vm.appRouteUpdateAtr() === true && param.appRouteUpdateAtr() === true) ? true : false);
      vm.createNewEmp((processExecution.execSetting.appRouteUpdateDaily.createNewEmp == 0 ? false : true) || false); // B12_2

      vm.appRouteUpdateMonthly(processExecution.execSetting.appRouteUpdateMonthly || false); // B12_3

      // vm.creationTarget(param.creationTarget);
      if (vm.processExecType() === 1) {
        vm.perScheduleClsReCreate(processExecution.execSetting.perSchedule.perScheduleCls || false); // B14_2
        vm.dailyPerfClsReCreate(vm.dailyPerfCls() || false); // B14_3
        vm.appRouteUpdateAtrReCreate(vm.appRouteUpdateAtr() || false); // B14_4
        vm.recreateWorkType(processExecution.reExecCondition.recreatePerson || false); // B15_2
        vm.recreateTransfer(processExecution.reExecCondition.recreateTransfer || false); // B15_3
        vm.manualCorrection(processExecution.reExecCondition.recreateLeave || false); // B15_4
      }
      
      vm.alarmAtr(processExecution.execSetting.alarmExtraction.alarmAtr || false); // B17_1
      vm.alarmCode(processExecution.execSetting.alarmExtraction.alarmCode || ''); // B17_4
      vm.mailAdministrator(processExecution.execSetting.alarmExtraction.mailAdministrator || false); // B17_7
      vm.mailPrincipal(processExecution.execSetting.alarmExtraction.mailPrincipal || false); // B17_8
      vm.displayAdministrator(processExecution.execSetting.alarmExtraction.displayOnTopPageAdministrator || false); // B17_10
      vm.displayPrincipal(processExecution.execSetting.alarmExtraction.displayOnTopPagePrincipal || false); // B17_11

      vm.stdOutputCls(processExecution.execSetting.externalOutput.externalOutputClassification === 1 ? true : false); // B19_1
      
      vm.stdAcceptCls(processExecution.execSetting.externalAcceptance.externalAcceptanceClassification === 1 ? true : false); // B20_1

      vm.storageCls(processExecution.execSetting.saveData.saveDataClassification === 1 ? true : false); // B21_1
      vm.storagePattern(processExecution.execSetting.saveData.patternCode); // B21_4

      vm.deletionCls(processExecution.execSetting.deleteData.dataDeletionClassification === 1 ? true : false); // B22_1
      vm.deletionPattern(processExecution.execSetting.deleteData.patternCode); // B22_4

      vm.indexReconCls(processExecution.execSetting.indexReconstruction.classificationOfUse); // B23_1
      vm.indexReconUpdateStats(processExecution.execSetting.indexReconstruction.updateStats); // B26_1
      
      vm.workplaceList(workplaceInfos || []);
      // vm.recreateTypeChangePerson(param.recreateTypeChangePerson || false);
      // vm.recreateTransfers(param.recreateTransfers || false);
      
      if (vm.targetMonth() === 3) {
        vm.disableYearMonthDate(true);
      } else {
        vm.disableYearMonthDate(false);
      }

      if (vm.processExecType() === 0) {
        // vm.creationTarget(0);
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
        // vm.creationTarget(1);
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