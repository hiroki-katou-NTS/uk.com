/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr004.b {

  const NUM_ROWS = 10;

  const PATH = {
    getInitDayMonth: 'at/screen/kwr004/b/getInitDayMonth',
    getSetting: 'at/screen/kwr004/b/getSetting',
    getSettingDetail: 'at/screen/kwr004/b/getDetail',
    deleteSetting: 'at/function/kwr004/delete',
    updateSetting: 'at/function/kwr004/update',
    createSetting: 'at/function/kwr004/create'
  };

  const maxDailyRows = 2;
  const maxMonthlyRows = 8;

  @bean()
  class ViewModel extends ko.ViewModel {

    settingListItems: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    columns: KnockoutObservableArray<any>;
    currentCode: KnockoutObservable<any>;
    currentCodeList: KnockoutObservable<any> = ko.observable([]);
    enumIndependentCalculation: KnockoutObservableArray<any>;
    //dailyAttributes: KnockoutObservableArray<any> = ko.observableArray([]);
    dailyAloneAttributes: KnockoutObservableArray<any>;
    dailyCalcAttributes: KnockoutObservableArray<any>;
    monthlyAttributes: KnockoutObservableArray<any>;
    attendance: KnockoutObservable<any> = ko.observable(null);
    attendanceCode: KnockoutObservable<string> = ko.observable(null);
    attendanceName: KnockoutObservable<string> = ko.observable(null);
    settingId: KnockoutObservable<string> = ko.observable(null);
    settingRuleCode: KnockoutObservable<number> = ko.observable(0);
    settingListItemsDetails: KnockoutObservableArray<SettingForPrint> = ko.observableArray([]);
    model: KnockoutObservable<Model> = ko.observable(new Model());

    isSelectAll: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAddButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAttendanceCode: KnockoutObservable<boolean> = ko.observable(true);
    isEnableDeleteButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDuplicateButton: KnockoutObservable<boolean> = ko.observable(false);

    //KDL 047, 048
    shareParam = new SharedParams();

    position: KnockoutObservable<number> = ko.observable(1);
    attendanceItemName: KnockoutObservable<string> = ko.observable('勤務種類');
    columnIndex: KnockoutObservable<number> = ko.observable(1);
    isDisplayItemName: KnockoutObservable<boolean> = ko.observable(true);
    isDisplayTitle: KnockoutObservable<boolean> = ko.observable(true);
    isEnableComboBox: KnockoutObservable<number> = ko.observable(2);
    isEnableTextEditor: KnockoutObservable<number> = ko.observable(2);
    comboSelected: KnockoutObservable<any> = ko.observable(null);
    tableSelected: KnockoutObservable<any> = ko.observable(null);

    isNewMode: KnockoutObservable<boolean> = ko.observable(false);
    itemSelection: KnockoutObservable<number> = ko.observable(null);

    workStatusTableOutputItem: KnockoutObservable<any> = ko.observable(null);
    diligenceProjects: KnockoutObservableArray<DiligenceProject> = ko.observableArray([]);
    diligenceProjectsMonthly: KnockoutObservableArray<DiligenceProject> = ko.observableArray([]);
    diligenceProjectsDaily: KnockoutObservableArray<DiligenceProject> = ko.observableArray([]);
    attributeList: KnockoutObservableArray<any> = ko.observableArray([]);
    isItemRemoved: KnockoutObservable<boolean> = ko.observable(false);
    currentSelected:  KnockoutObservable<any> = ko.observable(null);
    constructor(params: any) {
      super();

      const vm = this;

      //merge attributes, settings
      vm.switchAndDropBox();

      //get the left with code, settingCategory from A
      vm.getSettingListKDL().done(() => {
        vm.getSettingList(params);

        vm.currentCodeList.subscribe((newCode: any) => {
          nts.uk.ui.errors.clearAll();
          if (_.isNil(newCode)) return;
          vm.getSettingListItemsDetails(newCode);
            //KDL 047, 048
            vm.shareParam.titleLine.layoutCode = vm.attendanceCode();
            vm.shareParam.titleLine.layoutName = vm.attendanceName();
        });

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

        const positionText = vm.position() === 1 ? "上" : "下";
        vm.shareParam.titleLine.directText = "";
        vm.shareParam.itemNameLine.displayFlag = vm.isDisplayItemName();
        vm.shareParam.itemNameLine.displayInputCategory = 1;
        vm.shareParam.itemNameLine.name = vm.attendanceItemName();
        vm.shareParam.attribute.selectionCategory = 2;
        vm.shareParam.attribute.selected = vm.comboSelected();
        vm.shareParam.selectedTime = vm.tableSelected();

        vm.shareParam.attribute.attributeList = [
          new AttendanceType(1, vm.$i18n('KWR002_141')),
          new AttendanceType(2, vm.$i18n('KWR002_142')),
          new AttendanceType(3, vm.$i18n('KWR002_143'))
        ]

        vm.shareParam.attendanceItems = vm.diligenceProjects(); //KDL047 - Daily
        vm.shareParam.diligenceProjectList = vm.diligenceProjects(); //KDL048 - Daily
      });
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      let _height: number = 372,
        _dialogHeight = $(window).height();

      _height = (_dialogHeight > 592) ? 448 : 372;

      if (!!navigator.userAgent.match(/Trident.*rv\:11\./)) {
        _height = _dialogHeight > 592 ? 446 : 370;
      }
      $("#multiGridList").ntsFixedTable({ height: _height });
    }

    addRowItem(newRow?: SettingForPrint) {
      let vm = this,
        row: SettingForPrint = newRow;

      row.isChecked.subscribe((value: boolean) => {
        nts.uk.ui.errors.clearAll();
        vm.settingListItemsDetails.valueHasMutated();
      });

      row.independentCalcClassic.subscribe((value) => {
        row.selectedTimeList([]);
        row.selectionItem(null);
        row.selectedTime = -1;
        row.dailyAttributes(value === 1 ? vm.dailyAloneAttributes() : vm.dailyCalcAttributes());
        vm.settingListItemsDetails.valueHasMutated();
      });

      row.itemAttribute.subscribe((value) => {
        row.selectedTimeList([]);
        row.selectionItem(null);
        row.selectedTime = -1;
        vm.settingListItemsDetails.valueHasMutated();
      });

      vm.settingListItemsDetails.push(row);
    }

    clearSetting() {

    }

    addNewRow() {
      const vm = this;
      vm.clearModelToNew();
      $('#KWR004_B32').focus();
    }

    clearModelToNew() {
      const vm = this;
      vm.createDefaultSettingDetails();
      vm.isEnableDuplicateButton(false);
      vm.isEnableDeleteButton(false);
      vm.attendanceCode(null);
      vm.attendanceName(null);
      vm.isEnableAttendanceCode(true);
      vm.isNewMode(true);
      vm.currentCodeList(null);
    }

    registerSetting() {
      const vm = this;

      $('.attendance-code-name').trigger('validate');
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

      //order before save to database
      vm.orderSettingListItemsDetails();

      let params = {
        id: vm.isNewMode() ? null : vm.settingId(),
        code: vm.attendanceCode(),
        name: vm.attendanceName(),
        settingCategory: vm.itemSelection(),
        employeeI: vm.$user.employeeId,
        dailyOutputItems: vm.getDailyOutputItems(),
        monthlyOutputItems: vm.getMonthlyOutputItems()
      };

      const path_url = (vm.isNewMode()) ? PATH.createSetting : PATH.updateSetting;

      vm.$blockui('show');
      vm.$ajax(path_url, params)
        .done((result) => {
          vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
            vm.getSettingItemsLeft(vm.attendanceCode());
            vm.$blockui('hide');
            $('#KWR004_B33').focus();
          });
        })
        .fail((error) => {
          vm.showError(error.messageId);
          vm.$blockui('hide');
        }).always(() => vm.$blockui('hide'));
    }

    showError(messageId: string) {
      const vm = this;
      switch (messageId) {
        case 'Msg_1898':
          vm.$dialog.error({ messageId: messageId }).then(() => {
            vm.isItemRemoved(true);
            vm.getSettingItemsLeft(null);
          });
          break;

        case 'Msg_1859':
          //$('#KWR004_B32').ntsError('set', { messageId: messageId });
          vm.$dialog.error({ messageId: messageId }).then(() => {
            $('#KWR004_B32').focus();
          });
          break;
      }
    }

    orderSettingListItemsDetails() {
      const vm = this;
      //sort before get info and save
      let templateSetting = vm.settingListItemsDetails();
      //set reset
      vm.settingListItemsDetails([])
      //sort daily
      let twoItemList: Array<SettingForPrint> = _.dropRight(templateSetting, templateSetting.length - 2);
      twoItemList = vm.sortSettingListItemsDetails(twoItemList, 2);
      if (!_.isNil(twoItemList)) {
        _.forEach(twoItemList, (x) => {
          vm.settingListItemsDetails.push(x);
        });
      }
      //sort monthly
      let eightItemList: Array<SettingForPrint> = _.drop(templateSetting, 2);
      eightItemList = vm.sortSettingListItemsDetails(eightItemList, 8);
      if (!_.isNil(eightItemList)) {
        _.forEach(eightItemList, (x) => {
          vm.settingListItemsDetails.push(x);
        });
      }
    }


    getDailyOutputItems() {
      let dailyOutputItems = [];
      const vm = this;

      //get daily
      let twoItemList = _.dropRight(vm.settingListItemsDetails(), vm.settingListItemsDetails().length - 2);

      _.forEach(twoItemList, (x, index) => {
        let dailyItem = {
          rank: index + 1, // 1 -> 2
          name: x.name(),
          printTargetFlag: x.isChecked(),
          independentCalcClassic: x.independentCalcClassic(),
          dailyMonthlyClassification: 1, //（固定）日次 ＝ 1
          itemDetailAttributes: x.itemAttribute(),
          selectedAttendanceItemList: []
        };

        let outputItemDetails = [];
        _.forEach(x.selectedTimeList(), (o) => {
          outputItemDetails.push({
            operator: (o.operator) ? (o.operator == '+' ? 1 : 2) : null,
            attendanceItemId: o.itemId
          });
        });
        dailyItem.selectedAttendanceItemList = outputItemDetails;
        dailyOutputItems.push(dailyItem);
      });

      return dailyOutputItems;
    }

    getMonthlyOutputItems() {
      let monthlyOutputItems = [];
      const vm = this;

      //get monthly
      let eightItemList = _.drop(vm.settingListItemsDetails(), 2);

      _.forEach(eightItemList, (x, index) => {
        let monthlyItem = {
          rank: index + 3,  // 3 -> 10
          name: x.name(),
          printTargetFlag: x.isChecked(),
          independentCalcClassic: 2, //（固定）計算
          dailyMonthlyClassification: 2, //（固定）月次 ＝ ２
          itemDetailAttributes: x.itemAttribute(),
          selectedAttendanceItemList: []
        };

        let outputItemDetails = [];
        _.forEach(x.selectedTimeList(), (o) => {
          outputItemDetails.push({
            operator: (o.operator) ? (o.operator == '+' ? 1 : 2) : null,
            attendanceItemId: o.itemId
          });
        });
        monthlyItem.selectedAttendanceItemList = outputItemDetails;

        monthlyOutputItems.push(monthlyItem);
      });

      return monthlyOutputItems;
    }

    sortSettingListItemsDetails(settingList: Array<SettingForPrint>, max_row: number) {
      const vm = this,
        tempSettings: Array<SettingForPrint> = [],
        tempSettings1: Array<SettingForPrint> = [];

      _.forEach(settingList, (item) => {
        if (!nts.uk.util.isNullOrEmpty(item.name())) {
          tempSettings.push(item);
        } else {
          tempSettings1.push(item);
        }
      });

      _.forEach(tempSettings1, (item) => {
        tempSettings.push(item);
      });

      return tempSettings;
    }

    /**
     * Show output details
    */
    getSettingListItemsDetails(newCode: string) {
      const vm = this;

      let findObj = _.find(vm.settingListItems(), (x) => x.code === newCode);
      let settingId = null;

      if (!_.isNil(findObj)) {
        settingId = findObj.settingId;
        vm.attendanceCode(findObj.code);
        vm.attendanceName(findObj.name);
        vm.settingId(settingId);
        vm.isEnableAttendanceCode(false);
        vm.isEnableAddButton(true);
        vm.isEnableDuplicateButton(true);
        vm.isEnableDeleteButton(true);
        vm.isNewMode(false);

        if (!vm.isItemRemoved())
          $('#KWR004_B33').focus();
        else {
          $('#btnB11').focus();
          vm.isItemRemoved(false);
        }
      }

      vm.$blockui('show');
      vm.settingListItemsDetails([]);
      vm.$ajax(PATH.getSettingDetail, { settingId: settingId })
        .done((result) => {
          if (_.isNil(result)) {
            vm.$dialog.error({ messageId: 'Msg_1898' }).then(() => {
              vm.getSettingItemsLeft(null); //left list    
              vm.$blockui('hide');
            });
          } else {
            //daily
            if (!_.isNil(result.dailyOutputItems)) {
              vm.makeSettingDetailsFromData(result.dailyOutputItems, 2);
            }

            if (!_.isNil(result.monthlyOutputItems)) {
              vm.makeSettingDetailsFromData(result.monthlyOutputItems, 10);
            }
            vm.$blockui('hide');
          }
        })
        .fail()
        .always(() => vm.$blockui('hide'))
    }

    makeSettingDetailsFromData(outputItems: Array<any>, maxItems: number) {
      const vm = this;

      let dailyAttributes: any = [],
        independentCalc: number = 0,
        newItem: SettingForPrint = null;

      let step = maxItems === maxDailyRows ? 1 : 3; // 2 = d
      let selectionItem = null,
        selectedListItems = [];
      const dailyOrMonthly = maxItems < maxMonthlyRows;  //true: daily else monthly

      _.forEach(outputItems, (x, index: number) => {
        independentCalc = (!_.isNil(x.independentCalcClassic)) ? x.independentCalcClassic : 2;
        independentCalc = (maxItems === 2 && independentCalc === 2) ? 3 : independentCalc;
        dailyAttributes = vm.dailyOrMonthlyAttributes(independentCalc);
        selectedListItems = []; //clear
        _.forEach(x.selectedListItems, (o) => {
          selectedListItems.push({
            ...o, itemId: o.attendanceItemId,
            operator: o.operator == 2 ? '-' : '+'
          });
        });

        //create label to display
        selectionItem = vm.getAttendanceAttributes(dailyOrMonthly, independentCalc, selectedListItems, x.attribute);
        if (_.isEmpty(selectionItem)) selectedListItems = [];
        //create new row
        let selectedTime: number = selectedListItems.length > 0 ? selectedListItems[0].attendanceItemId : -1;

        let newItem = new SettingForPrint(
          x.rank, //rank
          x.name, //b4_3_2 見出し名称
          x.independentCalcClassic, //b4_5_1 or b4_5_2
          vm.createDataSelection(selectionItem), //b4_3_4 - display 選択項目
          x.printTargetFlag, //b4_3_1
          x.attribute, //b4_3_3_2 - selected code
          selectedListItems, //B4_3_3_2 属性選択
          dailyAttributes, //b4_3_3_2
          dailyOrMonthly, //daily or monthly
          selectedTime
        );

        vm.addRowItem(newItem);
      });

      let from = vm.settingListItemsDetails().length;
      if (from < maxItems) {
        for (let i = from; i < maxItems; i++) {
          dailyAttributes = vm.dailyOrMonthlyAttributes(i < maxDailyRows ? 1 : 2);
          newItem = new SettingForPrint(i + step, null, i < maxDailyRows ? 1 : 2, null,
            false, 0, [], dailyAttributes, i < maxDailyRows);
          vm.addRowItem(newItem);
        }
      }
    }

    dailyOrMonthlyAttributes(type: number) {
      const vm = this;
      if (type === 1) return vm.dailyAloneAttributes()
      else if (type === 2) return vm.monthlyAttributes();
      else return vm.dailyCalcAttributes();
      //return type === 1 ? vm.dailyAloneAttributes() : vm.monthlyAttributes();
    }

    deleteSetting() {
      const vm = this;

      vm.$blockui('show');
      const params = {
        settingId: vm.settingId() //該当する設定ID
      };

      vm.$dialog.confirm({ messageId: 'Msg_18' }).then((answer: string) => {
        if (answer === 'yes') {
          vm.$ajax(PATH.deleteSetting, params)
            .done(() => {
              vm.$dialog.info({ messageId: 'Msg_16' }).then(() => {
                vm.getPositionBeforeDelete();  //keep position before remove     
                vm.$blockui('hide');
              })
            })
            .always(() => {
              vm.$blockui('hide');
            })
            .fail((error) => {
              vm.$blockui('hide');
            });
        } else {
          vm.$blockui('hide');
        }
      });
    }

    /**
     * Duplicate Setting
     * */

    showDialogC() {
      const vm = this;
      let lastItem = _.last(vm.settingListItems());

      let params = {
        code: vm.attendanceCode(), //複製元の設定ID
        name: vm.attendanceName(),
        settingId: vm.settingId(),
        settingCategory: vm.itemSelection()
      }

      vm.$window.modal('/view/kwr/004/c/index.xhtml', params).then((data) => {
        if (_.isNil(data)) {
          return;
        }
        //reload screen B
        vm.getSettingItemsLeft(data.code);
      });
    }

    closeDialog() {
      const vm = this;
      vm.$window.close({ code: vm.attendanceCode() });
    }

    /**
     *
    */
    createDefaultSettingDetails(from: number = 0) {
      const vm = this;
      //clear
      vm.settingListItemsDetails([]);
      for (let i = from; i < NUM_ROWS; i++) {
        let dailyAttributes = i < 2 ? vm.dailyAloneAttributes() : vm.monthlyAttributes();
        let newItem = new SettingForPrint(i + 1, null, i < 2 ? 1 : 2, null, false, 0, [], dailyAttributes, i < 2);
        vm.addRowItem(newItem);
      }
    }

    createDataSelection(selectedTimeList: Array<any>) {
      let vm = this,
        dataSelection: string = '',
        selectionItem: Array<string> = [];

      _.forEach(selectedTimeList, (item, index: number) => {
        if (index === 0 && item.operator === '+') {
          selectionItem.push(item.name);
        } else {
          selectionItem.push(item.operator + ' ' + item.name);
        }
      });

      if (selectionItem.length > 0) {
        dataSelection = _.join(selectionItem, ' ');
      }

      return dataSelection;
    }

    getSettingList(params: any) {
      const vm = this;
      if (!_.isNil(params)) {
        let code = !_.isNil(params.code) ? params.code : null;
        vm.itemSelection(params.itemSelection);
        vm.getSettingItemsLeft(code); //left list          
      }
    }

    getSettingItemsLeft(currentCode: string) {
      const vm = this;

      let lisItems: Array<any> = [];

      vm.settingListItems.removeAll();
      vm.$ajax(PATH.getSetting, { settingClassification: vm.itemSelection() })
        .done((result) => {
          if (_.isNil(result) || result.length == 0) {
            vm.clearModelToNew();
            if (vm.isItemRemoved()) {
              $('#btnB11').focus();
              vm.isItemRemoved(false);
            } else
              $('#KWR004_B32').focus();
          } else {
            //merge to settingListItems
            _.forEach(result, (x) => {
              let code = _.padStart(x.code, 2, '0');
              lisItems.push(new ItemModel(code, x.name, x.settingId));
            });

            //sort by code with asc
            lisItems = _.orderBy(lisItems, 'code', 'asc');
            vm.settingListItems(lisItems);

            let firstItem: any = _.head(vm.settingListItems());
            if (_.isNil(currentCode)) currentCode = firstItem.code;
            vm.currentCodeList(currentCode);
            //vm.currentCodeList.valueHasMutated();
          }
        }).fail(() => { });
    }

    openDialogKDL(data: SettingForPrint) {
      const vm = this;

      if (data.independentCalcClassic() === 1)
        vm.openDialogKDL047(data);
      else
        vm.openDialogKDL048(data);
    }

    openDialogKDL047(row: any) {
      const vm = this;
      //１：勤務種類 - ２：就業時間帯 - ３：時刻			
      vm.shareParam.itemNameLine.name = row.name();
      vm.shareParam.attribute.selected = row.itemAttribute(); //setting Category      
      vm.shareParam.attendanceItems = vm.diligenceProjects(); //KDL047
      vm.shareParam.selectedTime = row.selectedTime;
      if (!_.isNil(row.selectedTimeList())) {
        vm.shareParam.selectedTimeList = row.selectedTimeList();
      }

      vm.shareParam.attribute.attributeList = [
        new AttendanceType(1, vm.$i18n('KWR002_141')),
        new AttendanceType(2, vm.$i18n('KWR002_142')),
        new AttendanceType(3, vm.$i18n('KWR002_143'))
      ]
      //vm.shareParam.exportAtr = 1;

      nts.uk.ui.windows.setShared('attendanceItem', vm.shareParam, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/047/a/index.xhtml').onClosed(() => {
        const attendanceItem = nts.uk.ui.windows.getShared('attendanceRecordExport');

        if (_.isNil(attendanceItem)) return;
        if (nts.uk.ui.errors.hasError()) nts.uk.ui.errors.clearAll();
        let index = _.findIndex(vm.settingListItemsDetails(), (o: any) => { return o.id === row.id; });
        if (attendanceItem.attendanceItemName) {
          vm.settingListItemsDetails()[index].name(attendanceItem.attendanceItemName);
          $('#textName' + vm.settingListItemsDetails()[index].id).ntsError('clear');
        }

        let findAttendanceName = _.find(vm.shareParam.attendanceItems, (x: any) => {
          return parseInt(x.attendanceItemId) === parseInt(attendanceItem.attendanceId);
        });

        //選択項目
        if (!_.isNil(findAttendanceName)) {
          let listItem: selectedTimeList = {};
          listItem.itemId = parseInt(attendanceItem.attendanceId);
          listItem.name = findAttendanceName.attendanceItemName;
          listItem.operator = '+'; //+
          listItem.indicatesNumber = findAttendanceName.indicatesNumber;
          //vm.settingListItemsDetails()[index].required(true);   
          vm.settingListItemsDetails()[index].itemAttribute(attendanceItem.attribute);
          vm.settingListItemsDetails()[index].selectedTimeList([listItem]);
          vm.settingListItemsDetails()[index].selectedTime = attendanceItem.attendanceId;
          vm.settingListItemsDetails()[index].selectionItem(findAttendanceName.attendanceItemName);
          if (row.isChecked()) $('#textName' + row.id).focus();
        } else {
          vm.settingListItemsDetails()[index].itemAttribute(-1);
          vm.settingListItemsDetails()[index].name(null);
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
          vm.settingListItemsDetails()[index].selectedTime = -1;
          vm.settingListItemsDetails()[index].required(false);
        }
      });
    }

    openDialogKDL048(row: any) {
      let vm = this;
      //４：時間 - ５：回数 - ６：日数 - 7：金額		
      vm.shareParam.exportAtr = 1;
      vm.shareParam.attribute.attributeList = [
        new AttendanceType(4, vm.$i18n('KWR002_180')),
        new AttendanceType(5, vm.$i18n('KWR002_181')),
        new AttendanceType(7, vm.$i18n('KWR002_183'))
      ]

      if (!row.type) {
        vm.shareParam.exportAtr = 2;
        vm.shareParam.attribute.attributeList.push(new AttendanceType(6, vm.$i18n('KWR002_182')));
        vm.shareParam.diligenceProjectList = vm.diligenceProjectsMonthly(); //KDL048
      } else
        vm.shareParam.diligenceProjectList = vm.diligenceProjectsDaily(); //KDL048

      vm.shareParam.itemNameLine.name = row.name();
      vm.shareParam.attribute.selected = row.itemAttribute(); //setting Category
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
            //vm.settingListItemsDetails()[index].required(true);
            vm.settingListItemsDetails()[index].itemAttribute(attendanceItem.attribute);
            vm.settingListItemsDetails()[index].selectionItem(dataSelection);
            vm.settingListItemsDetails()[index].selectedTimeList(attendanceItem.attendanceId);
            vm.settingListItemsDetails()[index].name(attendanceItem.attendanceItemName);
            if (row.isChecked()) $('#textName' + row.id).focus();
          }
        } else {
          vm.settingListItemsDetails()[index].itemAttribute(-1);
          vm.settingListItemsDetails()[index].name(null);
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
          vm.settingListItemsDetails()[index].selectedTime = -1;
          vm.settingListItemsDetails()[index].required(false);
        }
      });
    }

    checkItem(data: SettingForPrint) {
      return true
    }

    selectAllChange(newValue: boolean) {
      const vm = this;

      if (newValue === null) return;

      _.forEach(vm.settingListItemsDetails(), (row, index) => {
        row.isChecked(newValue);
      })
    }

    getSettingListKDL(): JQueryPromise<any> {
      const vm = this;
      let dfd = $.Deferred<any>();

      vm.$blockui('grayout');

      vm.workStatusTableOutputItem = ko.observable({ listDaily: [], listMonthly: [] });
      //7：年間勤務台帳
      vm.$ajax(PATH.getInitDayMonth, { settingClassification: 1, formNumberDisplay: 7 }).done((result) => {
        if (result && result.listDaily) {
          _.forEach(result.listDaily, (item) => {
            let masterType = (item.masterType === 1 || item.masterType === 2 || item.masterType === null) ? item.masterType : null;
            let attendanceAtr: any = ((item.attributes === 1 && item.masterType === 1) || (item.attributes === 2 && item.masterType === 2))
              ? 0 : (item.attributes === 3 && item.masterType === null ? 6 : null);
            vm.diligenceProjects.push(
              new DiligenceProject(
                item.attendanceItemId,
                item.attendanceItemName,
                item.attributes,//attendanceAtr
                item.attendanceItemDisplayNumber,
                masterType,
                attendanceAtr
              )
            );
            //export 1    
            attendanceAtr = item.attributes === 4
              ? 5
              : item.attributes === 5
                ? 2
                : item.attributes === 7
                  ? 3
                  : null;
            vm.diligenceProjectsDaily.push(
              new DiligenceProject(
                item.attendanceItemId,
                item.attendanceItemName,
                item.attributes,//attendanceAtr
                item.attendanceItemDisplayNumber,
                masterType,
                attendanceAtr
              )
            );
          });
        }

        if (result && result.listMonthly) {
          _.forEach(result.listMonthly, (item) => {
            //export 3
            let attendanceAtr = item.attributes === 4
              ? 1
              : item.attributes === 5
                ? 2 : item.attributes === 6
                  ? 3 : item.attributes === 7
                    ? 4 : null;
            vm.diligenceProjectsMonthly.push(
              new DiligenceProject(
                item.attendanceItemId,
                item.attendanceItemName,
                item.attributes,//attendanceAtr
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

    switchAndDropBox() {
      const vm = this;
      //帳票共通の単独計算区分 IndependentCalcClassic
      vm.enumIndependentCalculation = ko.observableArray([
        { code: 1, name: vm.$i18n('KWR004_68') },
        { code: 2, name: vm.$i18n('KWR004_69') }
      ]);

      //1, 2, 3
      vm.dailyAloneAttributes = ko.observableArray([]);
      vm.dailyAloneAttributes.push({ code: 1, name: vm.$i18n('KWR002_141') });
      vm.dailyAloneAttributes.push({ code: 2, name: vm.$i18n('KWR002_142') });
      vm.dailyAloneAttributes.push({ code: 3, name: vm.$i18n('KWR002_143') });

      //4, 5, 7
      vm.dailyCalcAttributes = ko.observableArray([]);
      vm.dailyCalcAttributes.push({ code: 4, name: vm.$i18n('KWR002_180') });
      vm.dailyCalcAttributes.push({ code: 5, name: vm.$i18n('KWR002_181') });
      vm.dailyCalcAttributes.push({ code: 7, name: vm.$i18n('KWR002_183') });

      //4, 5, 6, 7
      vm.monthlyAttributes = ko.observableArray();
      vm.monthlyAttributes.push({ code: 4, name: vm.$i18n('KWR002_180') });
      vm.monthlyAttributes.push({ code: 5, name: vm.$i18n('KWR002_181') });
      vm.monthlyAttributes.push({ code: 6, name: vm.$i18n('KWR002_182') });
      vm.monthlyAttributes.push({ code: 7, name: vm.$i18n('KWR002_183') });
    }
    /**
     * Get attendance attributes
     * @param type 
     * @param icClassic 
     * @param selectedListItems 
     * @return Array
     */
    getAttendanceAttributes(type: boolean, icClassic: number, selectedListItems: Array<any>, attribute: any) {
      const vm = this;
      let temp = [],
        attributesItems = vm.diligenceProjects();
      //from 3rd - Monthly
      if (icClassic === 2 && !type)
        attributesItems = vm.diligenceProjectsMonthly();
      else if (icClassic === 2 && type)
        attributesItems = vm.diligenceProjectsDaily();

      _.forEach(selectedListItems, (x) => {
        let findAttend = _.find(attributesItems, (e) => x.attendanceItemId === e.attendanceItemId && attribute === e.attributes);
        if (!_.isNil(findAttend)) {
          temp.push({ operator: x.operator, name: findAttend.attendanceItemName });
        }
      })

      return temp;
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
        $('#KWR004_B32').focus();
      }
      //vm.addNewRow();
    }
  }

  //=================================================================
  export interface AttendanceItem {
    code?: string;
    name?: string;
    status?: number;
  }

  export class ItemModel {
    code: string;
    name: string;
    settingId?: string;
    constructor(code?: string, name?: string, settingId?: string) {
      this.code = code;
      this.name = name;
      this.settingId = settingId
    }
  }

  export class SettingForPrint {
    id: number;
    isChecked: KnockoutObservable<boolean> = ko.observable(false);
    name: KnockoutObservable<string> = ko.observable(null);
    required: KnockoutObservable<boolean> = ko.observable(false);
    independentCalcClassic: KnockoutObservable<number> = ko.observable(1);
    selectedTime: number = -1;
    selectionItem: KnockoutObservable<string> = ko.observable(null);
    itemAttribute: KnockoutObservable<number> = ko.observable(0);
    selectedTimeList: KnockoutObservableArray<selectedTimeList> = ko.observableArray([]);
    dailyAttributes: KnockoutObservableArray<any> = ko.observableArray([]);
    type: boolean = false;
    independentCalcClassicProgrammaticChange: boolean = false;
    itemAttributeProgrammaticChange: boolean = false;

    constructor(
      id?: number,
      name?: string,
      indCalcClassic?: number,
      selectionItem?: string,
      checked?: boolean,
      itemAttribute?: number,
      selectedTimeList?: Array<any>,
      dailyAttributes?: Array<any>,
      type: boolean = false,
      selectedTime: number = -1
    ) {
      this.name(name || '');
      this.independentCalcClassic(indCalcClassic);
      this.isChecked(checked || false);
      this.selectionItem(selectionItem || '');
      this.id = id;
      this.itemAttribute(itemAttribute);
      this.selectedTimeList(selectedTimeList || []);
      this.dailyAttributes(dailyAttributes || []);
      this.type = type;
      this.selectedTime = selectedTime;

      this.independentCalcClassic.subscribe((oldValue) => {
        if (!this.independentCalcClassicProgrammaticChange && !_.isEmpty(this.selectionItem())) {
            const oldSelectedTimeList = this.selectedTimeList();
            const oldSelectionItem = this.selectionItem();
            const oldSelectedTime = this.selectedTime;
            const oldDailyAttributes = this.dailyAttributes();
            nts.uk.ui.dialog.confirm({ messageId: "Msg_2087" }).ifYes(()=>{
                // if yes do nothing
            }).ifNo(()=>{
                // if no reset value
                this.independentCalcClassicProgrammaticChange = true;
                this.independentCalcClassic(oldValue);
                this.selectedTimeList(oldSelectedTimeList);
                this.selectionItem(oldSelectionItem);
                this.selectedTime = oldSelectedTime;
                this.dailyAttributes(oldDailyAttributes);
            });
        } else {
          this.independentCalcClassicProgrammaticChange = false;
        }
      }, null, "beforeChange");
      this.itemAttribute.subscribe((oldValue) => {
          if (!this.itemAttributeProgrammaticChange && !_.isEmpty(this.selectionItem())) {
              const oldSelectedTimeList = this.selectedTimeList();
              const oldSelectionItem = this.selectionItem();
              const oldSelectedTime = this.selectedTime;
              nts.uk.ui.dialog.confirm({ messageId: "Msg_2087" }).ifYes(()=>{
                  // if yes do nothing
              }).ifNo(()=>{
                  // if no reset value
                  this.itemAttributeProgrammaticChange = true;
                  this.itemAttribute(oldValue);
                  this.selectedTimeList(oldSelectedTimeList);
                  this.selectionItem(oldSelectionItem);
                  this.selectedTime = oldSelectedTime;
              });
          } else {
              this.itemAttributeProgrammaticChange = false;
          }
      }, null, "beforeChange");
    }
  }

  export interface selectedTimeList {
    itemId?: number;
    operator?: string;
    name?: string;
    indicatesNumber?: number
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

  //KDL 047, 048
  // Display object mock
  export class SharedParams {
    // タイトル行
    titleLine: TitleLineObject = new TitleLineObject();
    // 項目名行
    itemNameLine: ItemNameLineObject = new ItemNameLineObject();
    // 属性
    attribute: AttributeObject = new AttributeObject();
    // List<勤怠項目>KDL 048
    diligenceProjectList: DiligenceProject[] = [];
    // List<勤怠項目> KDL 047
    attendanceItems: DiligenceProject[] = [];
    // List<選択済み勤怠項目>
    selectedTimeList: SelectedTimeListParam[] = [];
    // 選択済み勤怠項目ID
    selectedTime: number;
    // 加減算する項目
    attendanceIds: Array<any>;
    // columnIndex
    columnIndex: number;
    // position
    position: number;
    // exportAtr
    exportAtr: number;
  }
  export class SelectedTimeListParam {
    // 項目ID
    itemId: any | null = null;
    // 演算子
    operator: String | null = null;

    constructor(itemId: any, operator: String) {
      this.itemId = itemId;
      this.operator = operator;
    }
  }

  export class TitleLineObject {
    // 表示フラグ
    displayFlag: boolean = false;
    // 出力項目コード
    layoutCode: String | null = null;
    // 出力項目名
    layoutName: String | null = null;
    // コメント
    directText: String | null = null;
  }

  export class ItemNameLineObject {
    // 表示フラグ
    displayFlag: boolean = false;
    // 表示入力区分
    displayInputCategory: number = 1;
    // 名称
    name: String | null = null;
  }

  export class AttributeObject {
    // 選択区分
    selectionCategory: number = 2;
    // List<属性>
    attributeList: AttendanceType[] = [];
    // 選択済み
    selected: number = 1;
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
    attendanceItemId: any;
    attendanceItemName: any;
    attributes: any;
    displayNumbers: any;
    attendanceAtr: any;
    /** マスタの種類 */
    masterType: number | null;
    //48
    // ID
    id: any;
    // 名称
    name: any;
    // 属性
    //attributes: any;
    // 表示番号
    indicatesNumber: any;
    constructor(id: any, name: any, attributes: any, indicatesNumber: any, masterType: number | null, attendanceAtr: number | null) {
      this.attendanceItemId = id;
      this.attendanceItemName = name;
      this.attributes = attributes;
      this.displayNumbers = indicatesNumber;
      this.masterType = masterType;
      //48
      this.id = id;
      this.name = name;
      this.indicatesNumber = indicatesNumber;
      this.attendanceAtr = attendanceAtr;
    }
  }
}