/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.b {
  const NUM_ROWS = 10;

  const PATH = {
    getSettingListWorkStatus: 'at/function/kwr/003/a/listworkstatus',
    getSettingLitsWorkStatusDetails: 'at/function/kwr/003/b/detailworkstatus',
    checkDailyAuthor: 'at/function/kwr/003/a/checkdailyauthor',
    deleteSettingItemDetails: 'at/function/kwr/003/b/delete',
    createSettingItemDetails: 'at/function/kwr/003/b/create',
    updateSettingItemDetails: 'at/function/kwr/003/b/update',
    getFormInfo: 'at/screen/kwr/003/b/getinfor'
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    settingListItems: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    columns: KnockoutObservableArray<any>;
    currentCode: KnockoutObservable<any>;
    currentCodeList: KnockoutObservable<string> = ko.observable(null);
    currentSettingCodeList: KnockoutObservableArray<any>;
    settingRules: KnockoutObservableArray<any>;
    //current setting
    attendance: KnockoutObservable<any> = ko.observable(null);
    attendanceCode: KnockoutObservable<string> = ko.observable(null);
    attendanceName: KnockoutObservable<string> = ko.observable(null);
    settingCategory: KnockoutObservable<number> = ko.observable(0);
    settingId: KnockoutObservable<string> = ko.observable(null);
    settingListItemsDetails: KnockoutObservableArray<SettingForPrint> = ko.observableArray([]);
    //----------------------
    isSelectAll: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAddButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAttendanceCode: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDeleteButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDuplicateButton: KnockoutObservable<boolean> = ko.observable(false);
    isNewMode: KnockoutObservable<boolean> = ko.observable(false);
    someObservable: KnockoutObservable<any> = ko.observable(null);
    //KDL 047, 048
    shareParam = new SharedParams();

    position: KnockoutObservable<number> = ko.observable(1);
    attendanceItemName: KnockoutObservable<string> = ko.observable(null);
    columnIndex: KnockoutObservable<number> = ko.observable(1);
    isDisplayItemName: KnockoutObservable<boolean> = ko.observable(true);
    isDisplayTitle: KnockoutObservable<boolean> = ko.observable(true);
    isEnableComboBox: KnockoutObservable<number> = ko.observable(2);
    isEnableTextEditor: KnockoutObservable<number> = ko.observable(2);
    comboSelected: KnockoutObservable<any> = ko.observable(null);
    tableSelected: KnockoutObservable<any> = ko.observable(null);
    //isIE: KnockoutObservable<boolean> = ko.observable(false);

    workStatusTableOutputItem: KnockoutObservable<any> = ko.observable(null);
    diligenceProjects: KnockoutObservableArray<DiligenceProject> = ko.observableArray([]);
    diligenceProjectsDKL48: KnockoutObservableArray<DiligenceProject> = ko.observableArray([]);
    listDetail: KnockoutObservableArray<SettingForPrint> = ko.observableArray([]);
    constructor(params: any) {
      super();

      const vm = this;

      //get output info
      vm.getWorkStatusTableOutput().done(() => {

        vm.getSettingList(params);

        vm.currentCodeList.subscribe((newValue: any) => {
          nts.uk.ui.errors.clearAll();
          if (!newValue) return;
          vm.getSettingListForPrint(newValue);
          vm.getSettingListItemsDetails();
        });

        vm.settingRules = ko.observableArray([
          { code: 1, name: vm.$i18n('KWR003_217') },
          { code: 2, name: vm.$i18n('KWR003_218') }
        ]);

        vm.settingListItemsDetails.subscribe((newList) => {
          if (!newList || newList.length <= 0) {
            vm.isSelectAll(false);
            return;
          }
          //Check if all the values in the settingListItemsDetails array are true:
          let isSelectedAll: any = vm.settingListItemsDetails().every(item => item.isChecked() === true);
          //there is least one item which is not checked
          if (isSelectedAll === false) isSelectedAll = null;
          vm.isSelectAll(isSelectedAll);
        });

        // subscribe isSelectAll
        vm.isSelectAll.subscribe(newValue => {
          vm.selectAllChange(newValue);
        });

        //KDL 047, 048      
        vm.shareParam.titleLine.displayFlag = vm.isDisplayTitle();
        vm.shareParam.titleLine.layoutCode = vm.attendanceCode();
        vm.shareParam.titleLine.layoutName = vm.attendanceName();

        const positionText = vm.position() === 1 ? "???" : "???";
        vm.shareParam.titleLine.directText = "";
        vm.shareParam.itemNameLine.displayFlag = vm.isDisplayItemName();
        vm.shareParam.itemNameLine.displayInputCategory = 1;
        vm.shareParam.itemNameLine.name = vm.attendanceItemName();
        vm.shareParam.attribute.selectionCategory = 2;
        vm.shareParam.attribute.selected = vm.comboSelected();
        vm.shareParam.selectedTime = vm.tableSelected();
        vm.shareParam.attendanceItems = vm.diligenceProjects();
        vm.shareParam.diligenceProjectList = vm.diligenceProjectsDKL48();
      });
        // vm.settingRules.subscribe((value)=>{
        //     console.log("ABABABA" + value)
        // })
    }

    created(params: any) {
      const vm = this;
      /* const userAgent = window.navigator.userAgent;
      let msie = userAgent.match(/Trident.*rv\:11\./);
      if (!_.isNil(msie) && msie.index > -1) {
        vm.isIE(true);
      } */
    }


    mounted() {
      const vm = this;
      if (!!navigator.userAgent.match(/Trident.*rv\:11\./)) {
        $("#multiGridList").ntsFixedTable({ height: 'auto' });
        $('.kwr-003b').addClass('ie');
      } else
        $("#multiGridList").ntsFixedTable({ height: 370 });

    }

    addRowItem(newRow?: SettingForPrint) {
      let vm = this,
        row: SettingForPrint = newRow;

      if (!newRow) {
        let lastItem: any = _.last(vm.settingListItemsDetails());
        let id = lastItem ? lastItem.id : 0;
        row = new SettingForPrint(id, null, 0, null, false);
      }

      row.isChecked.subscribe((value: boolean) => {
        nts.uk.ui.errors.clearAll();
        vm.settingListItemsDetails.valueHasMutated();
      });

      row.setting.subscribe((value) => {
        row.selectedTimeList([]);
        row.selectionItem(null);
        row.selectedTime = -1;
        vm.settingListItemsDetails.valueHasMutated();
      });

      vm.settingListItemsDetails.push(row);
    }

    newSetting() {
      const vm = this;
      vm.clearModelToNew();
      $('#KWR003_B42').focus();
    }

    clearModelToNew() {
      const vm = this;

      nts.uk.ui.errors.clearAll();
      vm.currentCodeList(null);

      vm.createDefaultSettingDetails();

      vm.isEnableDuplicateButton(false);
      vm.isEnableDeleteButton(false);

      vm.attendanceCode(null);
      vm.attendanceName(null);
      vm.isEnableAttendanceCode(true);
      vm.isNewMode(true);
    }
    /**
     * Registers setting
     */
    registerSetting() {
      const vm = this;
      //register or update
      $(".attendance-input").trigger("validate");

      _.forEach(vm.settingListItemsDetails(), (item, index) => {
        if (
          (!item.isChecked() && !_.isEmpty(item.name()) && item.selectedTimeList().length === 0)
          || (!item.isChecked() && _.isEmpty(item.name()) && item.selectedTimeList().length > 0)
          || item.isChecked()
        ) {
          if (_.isEmpty(item.name())) {
            $('#textName' + item.id).ntsError('set', {
              messageId: 'MsgB_1', messageParams: [vm.$i18n('KWR003_213')]
            });
          }

          if (item.selectedTimeList().length === 0) {
            $('#btnRow-' + item.id).ntsError('set', {
              messageId: 'MsgB_1', messageParams: [vm.$i18n('KWR003_214')]
            });
          }
        }
      });

      if (nts.uk.ui.errors.hasError()) return;

      vm.saveOrUpdateSetting().done(() => {
        //vm.getSettingListItemsDetails();
      });
    }

    settingAttendance() {
      const vm = this;
      let returnAttendance: AttendanceItem = {
        code: vm.attendanceCode(),
        name: vm.attendanceName(),
        status: vm.isNewMode() ? 1 : 0, // 0: Update, 1: Addnew, 2: Remove
        settingCategory: vm.settingCategory()
      };
      vm.attendance(returnAttendance);
    }

    saveOrUpdateSetting(): JQueryPromise<any> {
      const vm = this;
      let dfd = $.Deferred<any>();

      let params: DataOutputDto = new DataOutputDto(),
        outputItemList: Array<OutputItemList> = [];

      _.forEach(vm.settingListItemsDetails(), (item, index) => {
        if (!_.isEmpty(item.name()) && item.selectedTimeList().length > 0) {
          let outputItem = new OutputItemList();
          outputItem.rank = item.id;
          outputItem.name = item.name();
          outputItem.printTargetFlag = item.isChecked();
          outputItem.independentCalClassic = item.setting();
          outputItem.itemDetailAtt = item.selected;
          outputItem.dailyMonthlyClassic = 1; //??????

          let outputItemDetails: Array<Attribute> = [];
          _.forEach(item.selectedTimeList(), (o) => {
            outputItemDetails.push({
              operator: (o.operator) ? (o.operator == '+' || o.operator ==1 ? 1 : 2) : null,
              attendanceItemId: o.itemId
            });
          });
          outputItem.selectedAttItemList = outputItemDetails;
          outputItemList.push(outputItem);
        }
      });

      params.code = vm.attendanceCode();
      params.name = vm.attendanceName();
      params.settingId = vm.isNewMode() ? null : vm.settingId();
      params.settingCategory = vm.settingCategory();
      params.outputItemList = outputItemList;

      let url = vm.isNewMode() ? PATH.createSettingItemDetails : PATH.updateSettingItemDetails;
      let reloadParams = { standOrFree: vm.settingCategory(), code: vm.attendanceCode(), msgId: null };

      vm.$blockui('show');
      vm.$ajax(url, params).done(() => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          vm.loadSettingList(reloadParams);
          vm.settingAttendance();
          vm.isNewMode(false);
          vm.$blockui('hide');
          $('#KWR003_B43').focus();
        });
        dfd.resolve();
      }).fail((error) => {
        switch (error.messageId) {
          case 'Msg_1903':
            vm.$dialog.error({ messageId: error.messageId }).then(() => {
              reloadParams.code = null;
              reloadParams.msgId = 'Msg_1903';
              vm.loadSettingList(reloadParams);
              //nts.uk.ui.errors.clearAll();
              //$('#btnB11').focus();
            });
            break;

          case 'Msg_1753':
            //$('#KWR003_B42').ntsError('set', { messageId: error.messageId });
            vm.$dialog.error({ messageId: error.messageId }).then(() => {
              $('#KWR003_B42').focus();
            });
            break;
        }
        vm.$blockui('hide');
        dfd.reject();
      });

      return dfd.promise();
    }

    /**
     * Orders list items by field
     * @param [listItemsDetails] 
     * @param [field] 
     * @param [sort_type] 
     * @returns  
     */
    orderListItemsByField(listItemsDetails?: Array<any>, field: string = 'name', sort_type: string = 'desc') {
      let newListItemsDetails: Array<any> = [];
      _.forEach(listItemsDetails, (row, index) => {
        let temp = {
          id: row.id,
          isChecked: row.isChecked(),
          name: row.name(),
          setting: row.setting(),
          selectionItem: row.selectionItem(),
          selectedItemList: row.selectedTimeList(),
          selected: row.selected,
          selectedTime: row.selectedTime
        };

        newListItemsDetails.push(temp);
      });

      return newListItemsDetails;
    }

    /**
     * Create list item after sorted
     * @param [listItemsDetails] 
     */
    createListItemAfterSorted(listItemsDetails?: Array<any>) {
      const vm = this;

      vm.settingListItemsDetails([]);
      let tempListItem: Array<SettingForPrint> = [];

      _.forEach(listItemsDetails, (x: any) => {
        if (!_.isEmpty(x.name)) {
          let newItem: SettingForPrint = new SettingForPrint(
            x.id,
            x.name,
            x.setting,
            x.selectionItem,
            x.isChecked,
            x.selectedItemList,
            x.selected,
            x.selectedTime
          );
          vm.addRowItem(newItem);
        }
      });

      let beginItems = vm.settingListItemsDetails().length;
      //if beginItems is less then NUM_ROWS | 10
      for (let i = beginItems; i < NUM_ROWS; i++) {
        let newItem: SettingForPrint = new SettingForPrint(i, null, 1, null, false);
        vm.addRowItem(newItem);
      }
    }

    /**
     * Delete setting
     */
    deleteSetting() {
      const vm = this;

      vm.$blockui('show');
      const params = { settingId: vm.settingId() }; //??????????????????ID
      vm.$dialog.confirm({ messageId: 'Msg_18' }).then((answer: string) => {
        if (answer === 'yes') {
          vm.$ajax(PATH.deleteSettingItemDetails, params)
            .done(() => {
              vm.$dialog.info({ messageId: 'Msg_16' }).then(() => {
                vm.getPositionBeforeDelete();  //keep position before remove              );
                vm.$blockui('hide');
              })
            })
            .fail((error) => { vm.$blockui('hide'); });
        } else {
          vm.$blockui('hide');
        }
      });
    }

    getPositionBeforeDelete() {
      const vm = this;
      let newSelectedCode = null;

      let index = _.findIndex(vm.settingListItems(), (x) => x.code === vm.currentCodeList());
      if (vm.settingListItems().length > 1) {
        if (index === vm.settingListItems().length - 1)
          index = index - 1;
        else
          index = index + 1;
        newSelectedCode = vm.settingListItems()[index].code;
      }

      let newSettingListItems = _.filter(vm.settingListItems(), (x) => x.code !== vm.currentCodeList());
      vm.settingListItems([]);
      if (newSettingListItems.length > 0) {
        vm.settingListItems(newSettingListItems);
        vm.currentCodeList(newSelectedCode);
      } else {
        vm.clearModelToNew();
        $('#KWR003_B42').focus();
      }
    }

    /**
     * Deletes setting item
     * @returns  
     */
    deleteSettingItem() {
      const vm = this;
      //get all items that will be remove
      let listCheckedItems: Array<any> = vm.settingListItemsDetails().filter((row) => row.isChecked() === true);
      if (listCheckedItems.length <= 0) return;

      //get all items that will be not remove
      let listNotCheckedItems: Array<any> = vm.settingListItemsDetails().filter((row) => row.isChecked() === false);
      vm.settingListItemsDetails(listNotCheckedItems);
    }
    /**
     * Duplicate Setting
     * */
    /**
     * Shows dialog C
     */
    showDialogC() {
      const vm = this;

      let selectedObj = _.find(vm.settingListItems(), (x) => x.code === vm.currentCodeList());
      let params = {
        code: selectedObj.code,
        name: selectedObj.name,
        settingCategory: vm.settingCategory(),
        settingId: selectedObj.id //??????????????????ID 
      }

      vm.$window.modal('/view/kwr/003/c/index.xhtml', params).then((result) => {
        if (_.isNil(result)) {
          return;
        }
        //reload the work status table
        params = { ...result, standOrFree: vm.settingCategory() };
        vm.loadSettingList(params)
      });

      $('#KWR003_B43').focus();
    }

    /**
     * Close dialog
     */
    closeDialog() {
      const vm = this;
      vm.$window.close(vm.attendance());
    }

    /**
     * Get setting list items details
     */
    getSettingListItemsDetails() {
      const vm = this;

      let selectedObj = _.find(vm.settingListItems(), (x) => x.code === vm.currentCodeList());
      //get details the work table status
      let beginItems = 0;
      let listDaily = vm.diligenceProjects();

      vm.$blockui('show');

      vm.$ajax(PATH.getSettingLitsWorkStatusDetails, { settingId: selectedObj.id }).done((data) => {

        vm.settingListItemsDetails([]);
        if (!_.isNil(data) && !data.settingId) {
          vm.$dialog.error({ messageId: 'Msg_1903' }).then(() => {
            vm.loadSettingList({ standOrFree: vm.settingCategory(), code: null });
          });
          return;
        }

        if (!_.isNil(data) && !_.isNil(data.outputItemList)) {
          //total output item which registered
          beginItems = data.outputItemList.length;
          //order by list
          let listItemsDetails: Array<SettingForPrint> = [];
          _.forEach(data.outputItemList, (x, index: number) => {
            let dataItemsWithOperation: Array<selectedItemList> = [];
            let selectedItemText = null, selectedTime: number = -1;
            //remove duplicate if yes
            if (x.attendanceItemList.length > 0) {
              let attendanceItemList = _.filter(x.attendanceItemList, (element: any, index, self) => {
                return index === _.findIndex(self, (d: any) => element.attendanceItemId === d.attendanceItemId);
              });
              //get selected items: ????????????
              if (x.independentCalClassic === 2) {
                _.forEach(attendanceItemList, (element: any) => {
                  let findObj = _.find(listDaily, (listItem: any) => listItem.attendanceItemId === element.attendanceItemId  
                                                    && x.itemDetailAtt === listItem.attributes);
                  if (!_.isNil(findObj)) {
                    dataItemsWithOperation.push({
                      itemId: element.attendanceItemId,
                      indicatesNumber: element.attendanceItemId,
                      name: findObj.name,
                      operator: (element.operatorName === 'ADDITION') ? '+' : '-'
                    });
                  }
                });

                selectedItemText = vm.createDataSelection(dataItemsWithOperation);
              } else {
                let findObj = _.find(listDaily, (listItem: any) => listItem.attendanceItemId === attendanceItemList[0].attendanceItemId);
                //if (!_.isNil(findObj)) selectedItemText = findObj.name;
                selectedItemText = !_.isNil(findObj) ? findObj.name : '';
                selectedTime = attendanceItemList[0].attendanceItemId;
                dataItemsWithOperation.push({
                  itemId: attendanceItemList[0].attendanceItemId,
                  indicatesNumber: attendanceItemList[0].attendanceItemId,
                  name: selectedItemText, //findObj.name
                  operator: 1
                });
              }
            }

            let newItem: SettingForPrint = new SettingForPrint(
              index,
              x.name,
              x.independentCalClassic,
              selectedItemText,
              x.printTargetFlag,
              dataItemsWithOperation,
              x.itemDetailAtt,
              selectedTime);

            listItemsDetails.push(newItem);
          });

          //re-order the list
          listItemsDetails = vm.orderListItemsByField(listItemsDetails);
          vm.createListItemAfterSorted(listItemsDetails);
          vm.listDetail(listItemsDetails);
        }

        vm.$blockui('hide');

      }).fail(() => vm.$blockui('hide'));
    }

    /**
     * Creatse default setting details
     */
    createDefaultSettingDetails() {
      const vm = this;
      //clear
      vm.settingListItemsDetails([])
      for (let i = 0; i < NUM_ROWS; i++) {
        let newItem = new SettingForPrint(i, null, 1, null, false);
        vm.addRowItem(newItem);
      }
    }
    /**
     * Creates data selection
     * @param selectedItemList 
     * @returns  
     */
    createDataSelection(selectedItemList: Array<any>) {
      let vm = this,
        dataSelection: string = '',
        selectionItem: Array<string> = [];

      _.forEach(selectedItemList, (item, index: number) => {
        if (index === 0 && item.operator.substring(0, 1) === '+') {
          selectionItem.push(item.name);
        } else {
          selectionItem.push(item.operator + ' ' + item.name);
        }
      });

      if (selectionItem.length > 0) {
        dataSelection = _.join(selectionItem, '');
      }

      return dataSelection;
    }

    getSettingListForPrint(code: string) {
      const vm = this;
      if (!_.isNil(code)) {
        let selectedObj = _.find(vm.settingListItems(), (x: any) => x.code === code);
        if (!_.isNil(selectedObj)) {

          vm.attendanceCode(selectedObj.code);
          vm.attendanceName(selectedObj.name);
          vm.settingId(selectedObj.id);

          //KDL 047, 048
          vm.shareParam.titleLine.layoutCode = vm.attendanceCode();
          vm.shareParam.titleLine.layoutName = vm.attendanceName();

          vm.isEnableAttendanceCode(false);
          vm.isEnableAddButton(true);
          vm.isEnableDeleteButton(true);
          vm.isEnableDuplicateButton(true);
          vm.isNewMode(false);
          vm.settingAttendance();
        }
      }

      $('#KWR003_B43').focus();
    }

    getWorkStatusTableOutput(): JQueryPromise<any> {
      const vm = this;
      let dfd = $.Deferred<any>();

      vm.$blockui('grayout');

      vm.workStatusTableOutputItem = ko.observable({ listDaily: [], listMonthly: [] });
      //?????????????????????
      vm.$ajax(PATH.getFormInfo, { formNumberDisplay: 6 }).done((result) => {
        if (result && result.listDaily) {
          _.forEach(result.listDaily, (item) => {
            let masterType = (item.masterType === 1 || item.masterType === 2 || item.masterType === null) ? item.masterType : null;
            let attendanceAtr: any = ((item.attributes === 1 && item.masterType === 1) || (item.attributes === 2 && item.masterType === 2))
              ? 0 : (item.attributes === 3 && item.masterType === null ? 6 : null);
            //KLD047
            vm.diligenceProjects.push(
              new DiligenceProject(
                item.attendanceItemId,
                item.attendanceItemName,
                item.attributes, //attendanceAtr
                item.attendanceItemDisplayNumber,
                masterType,
                attendanceAtr
              )
            );

            //KLD048 - export = 1
            attendanceAtr = item.attributes === 4
                            ? 5
                            : item.attributes === 5
                              ? 2
                              : item.attributes === 7
                                ? 3
                                : null;

            vm.diligenceProjectsDKL48.push(
              new DiligenceProject(
                item.attendanceItemId,
                item.attendanceItemName,
                item.attributes, //attendanceAtr
                item.attendanceItemDisplayNumber,
                item.masterType,
                attendanceAtr
              )
            );

          });
        }
        vm.$blockui('hide');
        dfd.resolve();
      }).always(() => vm.$blockui('hide'));

      return dfd.promise();
    }

    getSettingList(params: any) {
      const vm = this;

      if (!params) return;
      vm.settingCategory(params.standOrFree);
      vm.settingId(params.settingId);
      vm.loadSettingList(params);
    }

    loadSettingList(params: any) {
      const vm = this;
      let listWorkStatus: Array<any> = [];

      vm.$blockui('grayout');
      //sort by code with asc
      vm.settingListItems([]);

      vm.$ajax(PATH.getSettingListWorkStatus, { setting: params.standOrFree }).then((data) => {
        if (!_.isNil(data) && data.length > 0) {
          _.forEach(data, (item) => {
            let code = _.padStart(item.settingDisplayCode, 2, '0');
            listWorkStatus.push(new ItemModel(code, _.trim(item.settingName), item.settingId));
          });

          listWorkStatus = _.orderBy(listWorkStatus, ['code'], ['asc']);
          vm.settingListItems(listWorkStatus);

          let code = (!_.isNil(data) && !_.isNil(params.code)) ? _.padStart(params.code, 2, '0') : null;
          if (vm.settingListItems().length > 0) {
            let firstItem: any = _.head(vm.settingListItems());
            if (!code) code = firstItem.code;
          }

          vm.currentCodeList(code);
          vm.currentCodeList.valueHasMutated();
          if (!_.isNil(params.msgId) && params.msgId === 'Msg_1903') $('#btnB11').focus();

        } else {
          //create new the settings list
          vm.clearModelToNew();
          if (!_.isNil(params.msgId) && params.msgId === 'Msg_1903')
            $('#btnB11').focus();
          else
            $('#KWR003_B42').focus();
        }

        vm.$blockui('hide');
      });
    }
    /**
     * Opens dialog KDL
     * @param data 
     */
    openDialogKDL(data: SettingForPrint) {
      const vm = this;

      if (data.setting() === 2)
        vm.openDialogKDL048(data);
      else
        vm.openDialogKDL047(data);
    }
    /**
     * Opens dialog kdl047
     * @param row 
     */
    openDialogKDL047(row: any) {
      const vm = this;

      vm.shareParam.itemNameLine.name = row.name();
      vm.shareParam.itemNameLine.displayFlag = true;
      vm.shareParam.attribute.selected = row.selected; //setting Category
      //vm.shareParam.exportAtr = 1;
      //?????????????????? - ????????????????????? - ????????????		
      if (!_.isNil(row.selectedTimeList())) {
        vm.shareParam.selectedTimeList = row.selectedTimeList();
      }
      vm.shareParam.selectedTime = row.selectedTime;
      vm.shareParam.attribute.attributeList = [
        new AttendanceType(1, vm.$i18n('KWR002_141')),
        new AttendanceType(2, vm.$i18n('KWR002_142')),
        new AttendanceType(3, vm.$i18n('KWR002_143')),
      ];

      nts.uk.ui.windows.setShared('attendanceItem', vm.shareParam, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/047/a/index.xhtml').onClosed(() => {
        const attendanceItem = nts.uk.ui.windows.getShared('attendanceRecordExport');

        if (_.isNil(attendanceItem)) {
          return;
        }

        if (nts.uk.ui.errors.hasError()) nts.uk.ui.errors.clearAll();
        let index = _.findIndex(vm.settingListItemsDetails(), (o: any) => { return o.id === row.id; });
        if (attendanceItem.attendanceItemName) {
          vm.settingListItemsDetails()[index].name(attendanceItem.attendanceItemName);
          $('#textName' + vm.settingListItemsDetails()[index].id).ntsError('clear');
        }

        let findAttendanceName = _.find(vm.shareParam.attendanceItems, (x: any) => {
          return x.attendanceItemId === parseInt(attendanceItem.attendanceId);
        });

        //????????????
        if (!_.isNil(findAttendanceName)) {
          vm.settingListItemsDetails()[index].selectionItem(findAttendanceName.attendanceItemName);

          let listItem: selectedItemList = {};
          listItem.itemId = parseInt(attendanceItem.attendanceId);
          listItem.name = findAttendanceName.attendanceItemName;
          listItem.operator = '+'; //+
          vm.settingListItemsDetails()[index].selectedTimeList([listItem]);
          vm.settingListItemsDetails()[index].selected = attendanceItem.attribute;
          vm.settingListItemsDetails()[index].selectedTime = attendanceItem.attendanceId;

          if (vm.settingListItemsDetails()[index].isChecked()) $('#textName' + row.id).focus();
        } else {
          vm.settingListItemsDetails()[index].name(null);
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
          vm.settingListItemsDetails()[index].selectedTime = -1;
          vm.settingListItemsDetails()[index].selected = 0;
        }
      });
    }

    /**
     * Opens dialog kdl048
     * @param row 
     */
    openDialogKDL048(row: any) {
      let vm = this,
        selectionItem: Array<string> = [];
      vm.shareParam.exportAtr = 1; //???????????? - ???????????? - 7?????????		
      vm.shareParam.attribute.attributeList = [
        new AttendanceType(4, vm.$i18n('KWR002_180')),
        new AttendanceType(5, vm.$i18n('KWR002_181')),
        //new AttendanceType(6, vm.$i18n('KWR002_182')),
        new AttendanceType(7, vm.$i18n('KWR002_183'))
      ]

      vm.shareParam.itemNameLine.name = row.name();
      vm.shareParam.attribute.selected = row.selected; //setting Category      

      if (!_.isNil(row.selectedTimeList())) {
        vm.shareParam.selectedTimeList = row.selectedTimeList();
      }

      nts.uk.ui.windows.setShared('attendanceItem', vm.shareParam, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/048/index.xhtml').onClosed(() => {
        const attendanceItem = nts.uk.ui.windows.getShared('attendanceRecordExport');

        if (!attendanceItem) return;
        let index = _.findIndex(vm.settingListItemsDetails(), (o: any) => { return o.id === row.id; });
        if (attendanceItem.attendanceId.length > 0) {
          //clear error on input
          if (nts.uk.ui.errors.hasError()) nts.uk.ui.errors.clearAll();
          let dataSelection: string = vm.createDataSelection(attendanceItem.attendanceId);
          if (index > -1) {
            vm.settingListItemsDetails()[index].name(attendanceItem.attendanceItemName);
            vm.settingListItemsDetails()[index].selectionItem(dataSelection);
            vm.settingListItemsDetails()[index].selectedTimeList(attendanceItem.attendanceId);
            vm.settingListItemsDetails()[index].selected = attendanceItem.attribute;
            if (vm.settingListItemsDetails()[index].isChecked()) $('#textName' + row.id).focus();
          }
        } else {
          vm.settingListItemsDetails()[index].name(null);
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
          vm.settingListItemsDetails()[index].selectedTime = -1;
          vm.settingListItemsDetails()[index].selected = 0;
        }
      });
    }

    selectAllChange(newValue: boolean) {
      const vm = this;

      if (newValue === null) return;
      _.forEach(vm.settingListItemsDetails(), (row, index) => {
        row.isChecked(newValue);
      })
    }

    checkValidateInput() {
      const vm = this;
      if (!!vm.attendanceCode()) {
        $('#KWR003_B42').ntsError('set', { messageId: 'MsgB_1' });
        return false;
      }

      if (!!vm.attendanceName()) {
        $('#KWR003_B43').ntsError('set', { messageId: 'MsgB_1' });
        return false;
      }
    }
  }

  //=================================================================
  export interface AttendanceItem {
    code?: string;
    name?: string;
    status?: number;
    settingCategory?: number;
  }

  export class ItemModel {
    id: string;
    code: string;
    name: string;
    constructor(code?: string, name?: string, id?: string) {
      this.code = code;
      this.name = name;
      this.id = id;
    }
  }

  export class SettingForPrint {
    id: number;
    isChecked: KnockoutObservable<boolean> = ko.observable(false);
    name: KnockoutObservable<string> = ko.observable(null);
    setting: KnockoutObservable<number> = ko.observable(1); // 1 ?????? | 2	?????? 
    selected: number = 0;
    selectedTime: number = -1;
    selectionItem: KnockoutObservable<string> = ko.observable(null);
    selectedTimeList: KnockoutObservableArray<selectedItemList> = ko.observableArray([]);
    temp: number = null;
    check:boolean = false;
      independentCalcClassicProgrammaticChange: boolean = false;
    constructor(
      id?: number,
      name?: string,
      setting?: number,
      selectionItem?: string,
      checked?: boolean,
      selectedTimeList?: Array<selectedItemList>,
      selected: number = 0,
      selectedTime: number = -1) {
      this.name(name || '');
      this.setting(setting);
      this.isChecked(checked || false);
      this.selectionItem(selectionItem || ''); //display
      this.id = id;
      this.selectedTimeList(selectedTimeList || []);
      this.selected = selected;
      this.selectedTime = selectedTime;
      this.setting.subscribe((oldValue) => {
            if (!this.independentCalcClassicProgrammaticChange && !_.isEmpty(this.selectionItem())) {
                const oldSelectedTimeList = this.selectedTimeList();
                const oldSelectionItem = this.selectionItem();
                const oldSelectedTime = this.selectedTime;

                nts.uk.ui.dialog.confirm({ messageId: "Msg_2087" }).ifYes(()=>{
                    // if yes do nothing
                }).ifNo(()=>{
                    // if no reset value
                    this.independentCalcClassicProgrammaticChange = true;
                    this.setting(oldValue);
                    this.selectedTimeList(oldSelectedTimeList);
                    this.selectionItem(oldSelectionItem);
                    this.selectedTime = oldSelectedTime;
                });
            } else {
                this.independentCalcClassicProgrammaticChange = false;
            }
        }, null, "beforeChange");

    }
  }

  export class Model {
    code: string;
    name: string;
    settingForPrint: Array<SettingForPrint>;
    constructor(code?: string, name?: string, settings?: Array<SettingForPrint>) {
      this.code = code;
      this.name = name;
      this.settingForPrint = settings;
    }
  }

  export interface selectedItemList {
    itemId?: number;
    operator?: string | number;
    name?: string;
    indicatesNumber?: number
  }
  //KDL 047, 048
  // Display object mock
  export class SharedParams {
    // ???????????????
    titleLine: TitleLineObject = new TitleLineObject();
    // ????????????
    itemNameLine: ItemNameLineObject = new ItemNameLineObject();
    // ??????
    attribute: AttributeObject = new AttributeObject();
    // List<????????????>KDL 048
    diligenceProjectList: DiligenceProject[] = [];
    // List<????????????> KDL 047
    attendanceItems: Array<DiligenceProject> = [];
    // List<????????????????????????>
    selectedTimeList: Array<selectedItemListParam> = [];
    // ????????????????????????ID
    selectedTime: number;
    // ?????????????????????
    attendanceIds: Array<any>;
    // columnIndex
    columnIndex: number;
    // position
    position: number;
    // exportAtr
    exportAtr: number;
  }

  export class selectedItemListParam {
    // ??????ID
    itemId: any | null = null;
    // ?????????
    operator: String | null = null;
    name: string | null = null;
    indicatesNumber: any | null = null;
    constructor(itemId: any, operator: String, name?: string, indicatesNumber?: any) {
      this.itemId = itemId;
      this.operator = operator;
      this.name = name;
      this.indicatesNumber = indicatesNumber;
    }
  }

  export class TitleLineObject {
    // ???????????????
    displayFlag: boolean = false;
    // ?????????????????????
    layoutCode: String | null = null;
    // ???????????????
    layoutName: String | null = null;
    // ????????????
    directText: String | null = null;
  }

  export class ItemNameLineObject {
    // ???????????????
    displayFlag: boolean = false;
    // ??????????????????
    displayInputCategory: number = 1;
    // ??????
    name: String | null = null;
  }

  export class AttributeObject {
    // ????????????
    selectionCategory: number = 2;
    // List<??????>
    attributeList: AttendanceType[] = [];
    // ????????????
    selected: number = 1;

    constructor(init?: Partial<AttributeObject>) {
      $.extend(this, init);
    }
  }

  export class AttendanceType {
    attendanceTypeCode: number;
    attendanceTypeName: string;
    constructor(attendanceTypeCode: number, attendanceTypeName: string) {
      this.attendanceTypeCode = attendanceTypeCode;
      this.attendanceTypeName = attendanceTypeName;
    }
  }

  export class DiligenceProject {
    /** ????????????ID */
    attendanceItemId: any;
    /** ?????????????????? */
    attendanceItemName: any;
    attributes: any;
    /** ???????????? */
    displayNumbers: any;
    attendanceAtr: any;
    /** ?????????????????? */
    masterType: number | null;
    //48
    // ID
    id: any;
    // ??????
    name: any;
    // ??????
    //attributes: any;
    // ????????????
    indicatesNumber: any;
    constructor(id: any, name: any, attributes: any, indicatesNumber: any, masterType: number | null, attendanceAtr: number | null) {
      this.attendanceItemId = id;
      this.attendanceItemName = name;
      this.attributes = attributes;
      this.displayNumbers = indicatesNumber;
      //this.masterType = masterType;
      this.masterType = masterType;
      //48
      this.id = id;
      this.name = name;
      this.indicatesNumber = indicatesNumber;
      this.attendanceAtr = attendanceAtr;
    }
  }

  //save
  export class DataOutputDto {
    settingId?: string;
    code: string;
    name: string;
    settingCategory: number; // 0 ???????????? || 1 ????????????
    outputItemList: Array<any>;

    constructor(init?: Partial<DataOutputDto>) {
      $.extend(this, init);
    }
  }

  export class OutputItemList {
    rank: number; //???????????????
    name: string; //??????
    printTargetFlag: boolean; //?????????????????????
    independentCalClassic: number; //?????????????????? //1 	?????? || 2	??????
    dailyMonthlyClassic: number; //?????????????????? 1	?????? || //2 ??????
    itemDetailAtt: number; //?????????????????????
    selectedAttItemList: Array<any>; // ???????????????????????????

    constructor(init?: Partial<OutputItemList>) {
      $.extend(this, init);
    }
  }

  export class Attribute {
    operator: number | string; //????????? 1	?????? || 2	?????? || null
    attendanceItemId: number; //????????????ID

    constructor(init?: Partial<Attribute>) {
      $.extend(this, init);
    }
  }
}