/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr004.b {

  const NUM_ROWS = 10;
  const KWR004_B_INPUT = 'KWR004_WORK_STATUS_DATA';
  const KWR004_B_OUTPUT = 'KWR004_WORK_STATUS_RETURN';
  const KWR004_C_INPUT = 'KWR004_C_DATA';
  const KWR004_C_OUTPUT = 'KWR004_C_RETURN';

  const PATH = {
    getInitDayMonth: 'at/screen/kwr004/b/getInitDayMonth',
    getSetting: 'at/screen/kwr004/b/getSetting',
    getSettingDetail: 'at/screen/kwr004/b/getDetail',
    deleteSetting: 'at/function/kwr004/delete',
    updateSetting: 'at/function/kwr004/update',
    createSetting: 'at/function/kwr004/create'
  };

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

    constructor(params: any) {
      super();

      const vm = this;

      //KDL047, 048
      vm.getSettingListKDL();
      //merge attributes, settings
      vm.switchAndDropBox();
      //get the left with code, settingCategory from A
      vm.getSettingList();

      vm.currentCodeList.subscribe((newCode: any) => {
        nts.uk.ui.errors.clearAll();
        if (_.isNil(newCode)) return;
        vm.getSettingListItemsDetails(newCode);
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
      vm.shareParam.titleLine.directText = vm.$i18n('KWR002_131') + vm.columnIndex() + vm.$i18n('KWR002_132') + positionText + vm.$i18n('KWR002_133');
      vm.shareParam.itemNameLine.displayFlag = vm.isDisplayItemName();
      vm.shareParam.itemNameLine.displayInputCategory = vm.isEnableTextEditor();
      vm.shareParam.itemNameLine.name = vm.attendanceItemName();
      vm.shareParam.attribute.selectionCategory = vm.isEnableComboBox();
      vm.shareParam.attribute.selected = vm.comboSelected();
      vm.shareParam.selectedTime = vm.tableSelected();

      vm.shareParam.attribute.attributeList = [
        new AttendanceType(1, vm.$i18n('KWR002_141')),
        new AttendanceType(2, vm.$i18n('KWR002_142')),
        new AttendanceType(3, vm.$i18n('KWR002_143'))
      ]

      vm.shareParam.attendanceItems = vm.diligenceProjects(); //KDL047
      vm.shareParam.diligenceProjectList = vm.diligenceProjects(); //KDL048
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      if (!!navigator.userAgent.match(/Trident.*rv\:11\./))
        $("#multiGridList").ntsFixedTable({ height: 370 });
      else
        $("#multiGridList").ntsFixedTable({ height: 372 });
    }

    addRowItem(newRow?: SettingForPrint) {
      let vm = this,
        row: SettingForPrint = newRow;

      row.isChecked.subscribe((value: boolean) => {
        vm.settingListItemsDetails.valueHasMutated();
      });

      row.independentCalcClassic.subscribe((value) => {
        row.dailyAttributes(value === 1 ? vm.dailyAloneAttributes() : vm.dailyCalcAttributes());
        vm.settingListItemsDetails.valueHasMutated();
      });

      vm.settingListItemsDetails.push(row);
    }

    addNewRow() {
      const vm = this;
      //vm.addRowItem();
      vm.createDefaultSettingDetails();

      vm.isEnableDuplicateButton(false);
      vm.isEnableDeleteButton(false);
      vm.attendanceCode(null);
      vm.attendanceName(null);
      vm.isEnableAttendanceCode(true);
      vm.isNewMode(true);
      vm.currentCodeList(null);
      $('#KWR004_B32').focus();
    }

    registerSetting() {
      const vm = this;

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
            vm.getSettingItemsLeft(null);
            $('#btnB11').focus();
          });
          break;

        case 'Msg_1859':
          $('#KWR004_B32').ntsError('set', { messageId: messageId });
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
          x.independentCalcClassic(2);
          vm.settingListItemsDetails.push(x);
        });
      }
    }


    getDailyOutputItems() {
      let dailyOutputItems = [];
      const vm = this;

      //get daily
      let twoItemList = _.dropRight(vm.settingListItemsDetails(), vm.settingListItemsDetails().length - 2);
      console.log(twoItemList);
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
      console.log(dailyOutputItems);
      return dailyOutputItems;
    }

    getMonthlyOutputItems() {
      let monthlyOutputItems = [];
      const vm = this;

      //get monthly
      let eightItemList = _.drop(vm.settingListItemsDetails(), 2);
      console.log(eightItemList);
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
      console.log(monthlyOutputItems);
      return monthlyOutputItems;
    }

    sortSettingListItemsDetails(settingList: Array<SettingForPrint>, max_row: number) {
      const vm = this,
        tempSettings: Array<SettingForPrint> = [];

      _.forEach(settingList, (item) => {
        if (!nts.uk.util.isNullOrEmpty(item.name())) {
          tempSettings.push(item);
        }
      });

      let from = tempSettings.length;
      for (let i = from; i < max_row; i++) {
        let dailyAttributes = vm.dailyOrMonthlyAttributes(i < 2 ? 1 : 2);
        let newItem = new SettingForPrint(i + 1, null, i < 2 ? 1 : 2, null, false, 0, [], dailyAttributes, i < 2);
        tempSettings.push(newItem);
      }

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
        $('#KWR004_B33').focus();
      }

      vm.$blockui('show');
      vm.settingListItemsDetails([]);
      vm.$ajax(PATH.getSettingDetail, { settingId: settingId })
        .done((result) => {
          //daily
          if (!_.isNil(result.dailyOutputItems)) {
            vm.makeSettingDetailsFromData(result.dailyOutputItems, 2);
          }

          if (!_.isNil(result.monthlyOutputItems)) {
            vm.makeSettingDetailsFromData(result.monthlyOutputItems, 10);
          }
          //vm.orderSettingListItemsDetails();
        })
        .fail()
        .always(() => vm.$blockui('hide'))
    }

    makeSettingDetailsFromData(outputItems: Array<any>, maxItems: number) {
      const vm = this;
      let step = maxItems === 2 ? 1 : 3;

      _.forEach(outputItems, (x, index: number) => {
        let dailyAttributes = vm.dailyOrMonthlyAttributes(x.independentCalcClassic);
        //maxItems < 8 -> true: daily else monthly
        let selectionItem = vm.getAttendanceAttributes(maxItems < 8, x.independentCalcClassic, x.selectedListItems);        
        let newItem = new SettingForPrint(
          index + step, //rank
          x.name, //b4_3_2 見出し名称
          x.independentCalcClassic, //b4_5_1 or b4_5_2
          vm.createDataSelection(selectionItem), //b4_3_4 - display 選択項目
          x.printTargetFlag, //b4_3_1
          x.attribute, //b4_3_3_2 - selected code
          x.selectedListItems, //B4_3_3_2 属性選択
          dailyAttributes, //b4_3_3_2
          maxItems < 8 //daily or monthly
        );
        
        vm.addRowItem(newItem);
      });

      let from = vm.settingListItemsDetails().length;
      if (from < maxItems) {
        for (let i = from; i < maxItems; i++) {
          let dailyAttributes = vm.dailyOrMonthlyAttributes(i < 2 ? 1 : 2);
          let newItem = new SettingForPrint(i + step, null, i < 2 ? 1 : 2, null, false, 0, [], dailyAttributes, i < 2);
          vm.addRowItem(newItem);
        }
      }
    }

    dailyOrMonthlyAttributes(type: number) {
      const vm = this;
      return type === 1 ? vm.dailyAloneAttributes() : vm.monthlyAttributes();
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
                vm.getSettingItemsLeft(null);
                vm.$blockui('hide');
              })
            })
            .always(() => {
              vm.$blockui('hide');
            })
            .fail((error) => {

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

      vm.$window.storage(KWR004_C_INPUT, params).then(() => {
        vm.$window.modal('/view/kwr/004/c/index.xhtml').then(() => {
          vm.$window.storage(KWR004_C_OUTPUT).then((data) => {
            if (_.isNil(data)) {
              return;
            }
            //reload screen B
            vm.getSettingItemsLeft(data.code);
          });
        });
      });
    }

    closeDialog() {
      const vm = this;
      //KWR004_B_OUTPUT
      vm.$window.storage(KWR004_B_OUTPUT, { code: vm.attendanceCode() }).then(() => {
        vm.$window.close();
      });
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
        if (index === 0 && item.operator.substring(0, 1) === '+') {
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

    getSettingList() {
      const vm = this;
      vm.$window.storage(KWR004_B_INPUT).then((data: any) => {
        if (!_.isNil(data)) {
          let code = !_.isNil(data.code) ? data.code : null;
          vm.itemSelection(data.itemSelection);
          vm.getSettingItemsLeft(code); //left list          
        }
      });
    }

    getSettingItemsLeft(currentCode: string) {
      const vm = this;

      let lisItems: Array<any> = [];

      vm.$ajax(PATH.getSetting, { settingClassification: vm.itemSelection() })
        .done((result) => {
          if (_.isNil(result) || result.length == 0) {
            vm.addNewRow();
          } else {
            //merge to settingListItems
            _.forEach(result, (x) => {
              let code = _.padStart(x.code, 2, '0');
              lisItems.push(new ItemModel(code, x.name, x.settingId));
            });

            //sort by code with asc
            lisItems = _.orderBy(lisItems, 'code', 'asc');
            vm.settingListItems([]);
            vm.settingListItems(lisItems);

            let firstItem: any = _.head(vm.settingListItems());
            if (_.isNil(currentCode)) currentCode = firstItem.code;
            vm.currentCodeList(currentCode);

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

      vm.shareParam.itemNameLine.name = row.name();
      vm.shareParam.attribute.selected = row.itemAttribute(); //setting Category
      vm.shareParam.selectedTime = row.selectedTime;
      vm.shareParam.attendanceItems = vm.diligenceProjects(); //KDL047

      vm.shareParam.attribute.attributeList = [
        new AttendanceType(1, vm.$i18n('KWR002_141')),
        new AttendanceType(2, vm.$i18n('KWR002_142')),
        new AttendanceType(3, vm.$i18n('KWR002_143'))
      ]

      nts.uk.ui.windows.setShared('attendanceItem', vm.shareParam, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/047/a/index.xhtml').onClosed(() => {
        const attendanceItem = nts.uk.ui.windows.getShared('attendanceRecordExport');
        //console.log(attendanceItem);
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
          listItem.indicatesNumber = 0;

          vm.settingListItemsDetails()[index].required(true);
          vm.settingListItemsDetails()[index].selectedTimeList([listItem]);
          vm.settingListItemsDetails()[index].itemAttribute(attendanceItem.attribute);
          vm.settingListItemsDetails()[index].selectedTime = attendanceItem.attendanceId;
          vm.settingListItemsDetails()[index].selectionItem(findAttendanceName.attendanceItemName);
          $('#textName' + row.id).focus();
        } else {
          vm.settingListItemsDetails()[index].name(null);
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
          vm.settingListItemsDetails()[index].selectedTime = -1;
          vm.settingListItemsDetails()[index].itemAttribute(-1);
          vm.settingListItemsDetails()[index].required(false);
        }
      });
    }

    openDialogKDL048(row: any) {
      let vm = this;

      vm.shareParam.attribute.attributeList = [
        new AttendanceType(4, vm.$i18n('KWR002_180')),
        new AttendanceType(5, vm.$i18n('KWR002_181')),
        new AttendanceType(7, vm.$i18n('KWR002_183'))
      ]

      if (!row.type) {
        vm.shareParam.attribute.attributeList.push(new AttendanceType(6, vm.$i18n('KWR002_182')));
        vm.shareParam.diligenceProjectList = vm.diligenceProjectsMonthly(); //KDL048
      } else
        vm.shareParam.diligenceProjectList = vm.diligenceProjects(); //KDL048

      vm.shareParam.itemNameLine.name = row.name();
      vm.shareParam.attribute.selected = row.itemAttribute(); //setting Category
      if (!_.isNil(row.selectedTimeList())) {
        vm.shareParam.selectedTimeList = row.selectedTimeList();
      }

      nts.uk.ui.windows.setShared('attendanceItem', vm.shareParam, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/048/index.xhtml').onClosed(() => {
        const attendanceItem = nts.uk.ui.windows.getShared('attendanceRecordExport');
        console.log(attendanceItem);
        if (!attendanceItem) return;
        let index = _.findIndex(vm.settingListItemsDetails(), (o: any) => { return o.id === row.id; });
        if (attendanceItem.selectedTimeList.length > 0) {
          //clear error on input
          if (nts.uk.ui.errors.hasError()) nts.uk.ui.errors.clearAll();
          let dataSelection: string = vm.createDataSelection(attendanceItem.selectedTimeList);
          if (index > -1) {
            vm.settingListItemsDetails()[index].name(attendanceItem.itemNameLine.name);
            vm.settingListItemsDetails()[index].required(true);
            vm.settingListItemsDetails()[index].selectionItem(dataSelection);
            vm.settingListItemsDetails()[index].selectedTimeList(attendanceItem.selectedTimeList);
            vm.settingListItemsDetails()[index].itemAttribute(attendanceItem.attribute.selected);
            $('#textName' + row.id).focus();
          }
        } else {
          vm.settingListItemsDetails()[index].name(null);
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
          vm.settingListItemsDetails()[index].selectedTime = -1;
          vm.settingListItemsDetails()[index].itemAttribute(-1);
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

    getSettingListKDL() {
      const vm = this;

      vm.$blockui('grayout');

      vm.workStatusTableOutputItem = ko.observable({ listDaily: [], listMonthly: [] });
      vm.$ajax(PATH.getInitDayMonth, { settingClassification: 1, formNumberDisplay: 7 }).done((result) => {
        if (result && result.listDaily) {
          _.forEach(result.listDaily, (item) => {
            vm.diligenceProjects.push(new DiligenceProject(
              item.attendanceItemId,
              item.attendanceItemName,
              item.attributes,
              item.attendanceItemDisplayNumber
            )
            );
          });
        }

        if (result && result.listMonthly) {
          _.forEach(result.listMonthly, (item) => {
            vm.diligenceProjectsMonthly.push(new DiligenceProject(
              item.attendanceItemId,
              item.attendanceItemName,
              item.attributes,
              item.attendanceItemDisplayNumber
            )
            );
          });
        }

        vm.$blockui('hide');
      }).always(() => vm.$blockui('hide'));
    }

    switchAndDropBox() {
      const vm = this;
      //帳票共通の単独計算区分 IndependentCalcClassic
      vm.enumIndependentCalculation = ko.observableArray([
        { code: 1, name: vm.$i18n('KWR004_68') },
        { code: 2, name: vm.$i18n('KWR004_69') }
      ]);

      vm.dailyAloneAttributes = ko.observableArray([]);
      let allowIndex = [1, 2, 3];
      _.forEach(__viewContext.enums.DailyAttendanceAtr, (x) => {
        if (_.includes(allowIndex, x.value)) {
          vm.dailyAloneAttributes.push({ code: x.value, name: vm.$i18n(x.name) });
        }
      });

      vm.dailyCalcAttributes = ko.observableArray([]);
      allowIndex = [4, 5, 7];
      _.forEach(__viewContext.enums.DailyAttendanceAtr, (x) => {
        if (_.includes(allowIndex, x.value)) {
          vm.dailyCalcAttributes.push({ code: x.value, name: vm.$i18n(x.name) });
        }
      });

      vm.monthlyAttributes = ko.observableArray();
      allowIndex = [4, 5, 6, 7];
      _.forEach(__viewContext.enums.MonthlyAttendanceItemAtr, (x) => {
        if (_.includes(allowIndex, x.value)) {
          vm.monthlyAttributes.push({ code: x.value, name: vm.$i18n(x.name) });
        }
      });
    }
    /**
     * Get attendance attributes
     * @param type 
     * @param icClassic 
     * @param selectedListItems 
     * @return Array
     */
    getAttendanceAttributes(type: boolean, icClassic: number, selectedListItems: Array<any>) {
      const vm = this;
      let temp = [],
        attributesItems = vm.diligenceProjects();
      //from 3rd - Monthly
      if (icClassic === 2 && !type) attributesItems = vm.diligenceProjectsMonthly();

      _.forEach(selectedListItems, (x) => {
        let findAttend = _.find(attributesItems, (e) => x.attendanceItemId === e.attendanceItemId);
        if (!_.isNil(findAttend))
          temp.push({
            operator: x.operator === 1 ? '+' : '-',
            name: findAttend.attendanceItemName
          });
      })

      return temp;
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

    constructor(
      id?: number,
      name?: string,
      indCalcClassic?: number,
      selectionItem?: string,
      checked?: boolean,
      itemAttribute?: number,
      selectedTimeList?: Array<any>,
      dailyAttributes?: Array<any>,
      type: boolean = false
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
    //48
    // ID
    id: any;
    // 名称
    name: any;
    // 属性
    //attributes: any;
    // 表示番号
    indicatesNumber: any;
    constructor(id: any, name: any, attributes: any, indicatesNumber: any) {
      this.attendanceItemId = id;
      this.attendanceItemName = name;
      this.attributes = attributes;
      this.displayNumbers = indicatesNumber;
      //48
      this.id = id;
      this.name = name;
      //this.attributes = attributes;
      this.indicatesNumber = indicatesNumber;
    }
  }
}