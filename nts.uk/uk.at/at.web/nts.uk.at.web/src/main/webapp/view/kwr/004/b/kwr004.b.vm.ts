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
    currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
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
    isEnableAttendanceCode: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDeleteButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDuplicateButton: KnockoutObservable<boolean> = ko.observable(false);

    //KDL 047, 048
    shareParam = new SharedParams();

    position: KnockoutObservable<number> = ko.observable(1);
    attendanceItemName: KnockoutObservable<string> = ko.observable('勤務種類');
    //attendanceCode: KnockoutObservable<string> = ko.observable('02');
    //attendanceName: KnockoutObservable<string> = ko.observable('出勤簿');
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

      vm.switchAndDropBox();

      //get the left with code, settingCategory from A
      vm.getSettingList();

      vm.currentCodeList.subscribe((newCode: any) => {
        nts.uk.ui.errors.clearAll();
        if (_.isNil(newCode)) return;
        //vm.getSettingListItemsDetails();
        //vm.getSettingListForPrint(newCode);
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
        new AttendaceType(1, vm.$i18n('KWR002_141')),
        new AttendaceType(2, vm.$i18n('KWR002_142')),
        new AttendaceType(3, vm.$i18n('KWR002_143')),
        //new AttendaceType(4, vm.$i18n('KWR002_180')),
        //new AttendaceType(5, vm.$i18n('KWR002_181')),
        //new AttendaceType(6, vm.$i18n('KWR002_182')),
        //new AttendaceType(7, vm.$i18n('KWR002_183'))
      ]

      vm.shareParam.attendanceItems = vm.diligenceProjects(); //KDL047
      vm.shareParam.diligenceProjectList = vm.diligenceProjects(); //KDL048
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      //$("#multiGridList").ntsFixedTable({ 'max-width': 700 });
      if (!!navigator.userAgent.match(/Trident.*rv\:11\./))
        $("#multiGridList").ntsFixedTable({ height: 'auto' });
      else
        $("#multiGridList").ntsFixedTable({ height: 372 });

      $('#KWR004_B33').focus();
    }

    addRowItem(newRow?: SettingForPrint) {
      let vm = this,
        row: SettingForPrint = newRow;

      row.isChecked.subscribe((value: boolean) => {
        vm.settingListItemsDetails.valueHasMutated();
      });

      row.independentCalcClassic.subscribe((value) => {
        if (value === 1)
          row.dailyAttributes(vm.dailyAloneAttributes());
        else
          row.dailyAttributes(vm.dailyCalcAttributes());
      });

      vm.settingListItemsDetails.push(row);
    }

    addNewRow() {
      const vm = this;
      //vm.addRowItem();
      vm.creatDefaultSettingDetails();

      vm.isEnableDuplicateButton(false);
      vm.isEnableDeleteButton(false);

      vm.attendanceCode(null);
      vm.attendanceName(null);
      vm.isEnableAttendanceCode(true);
      vm.isNewMode(true);
      $('#KWR004_B32').focus();
    }

    registerSetting() {
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

      let params = {
        id: null,
        code: null,
        name: null,
        settingCategory: vm.itemSelection(),
        employeeI: vm.$user.employeeId,
        dailyOutputItems: vm.getDailyOutputItems(),
        monthlyOutputItems: vm.getMonthlyOutputItems()
      };

      console.log(params);

      const path_url = (vm.isNewMode()) ? PATH.createSetting : PATH.updateSetting;

      //vm.$blockui('show');

      /* vm.$ajax(path_url, params)
        .done((result) => {
          vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
            vm.getSettingList();
            $('#btnB15').focus();
          });
        })
        .fail((err) => {         
          switch (error.messageId) {
            case 'Msg_1898':
              vm.$dialog.error({  messageId: error.messageId }).then(() => {
                reloadParams.code = null;
                vm.loadSettingList(reloadParams);
                $('#btnB11').focus();
              });             
              break;

            case 'Msg_1859':
              $('#KWR003_B42').ntsError('set', { messageId: error.messageId });
              break;
          }
        }).always(() => vm.$blockui('hide'))

      let returnAttendance: AttendanceItem = {
        code: vm.attendanceCode(),
        name: vm.attendanceName(),
        status: vm.isNewMode() ? 1 : 0 // 0: Update, 1: Addnew, 2: Remove
      };
      vm.attendance(returnAttendance); */

      //change to update status
      vm.isNewMode(false);
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

      //get daily
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
        tempSettings: Array<SettingForPrint> = [];

      _.forEach(settingList, (item) => {
        if (!nts.uk.util.isNullOrEmpty(item.name())) {
          tempSettings.push(item);
        }
      });

      let from = tempSettings.length;
      for (let i = from; i < max_row; i++) {
        let dailyAttributes = i < 2 ? vm.dailyAloneAttributes() : vm.monthlyAttributes();
        let newItem = new SettingForPrint(
          i + 1, //id
          null, //name
          i < 2 ? 1 : 2, //indCalcClassic
          null, //selectionItem
          false, //checked
          0, //itemAttribute
          [], //selectedTimeList
          dailyAttributes, //dailyAttributes,
          -1, //selectedCategory
          i < 2 //type
        );
        tempSettings.push(newItem);
      }

      return tempSettings;
    }


    deleteSetting() {
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

    showDialogC() {
      const vm = this;
      let lastItem = _.last(vm.settingListItems());

      let params = {
        code: vm.attendanceCode(), //複製元の設定ID
        name: vm.attendanceName(),
        lastCode: !_.isNil(lastItem) ? lastItem.code : null,
        settingListItems: vm.settingListItemsDetails() //設定区分
      }

      vm.$window.storage(KWR004_C_INPUT, params).then(() => {
        vm.$window.modal('/view/kwr/004/c/index.xhtml').then(() => {
          vm.$window.storage(KWR004_C_OUTPUT).then((data) => {
            console.log(data);
            if (_.isNil(data)) {
              return;
            }

            /* let duplicateItem = _.find(vm.settingListItems(), (x) => x.code === data.code);
            if (!_.isNil(duplicateItem)) {
              vm.$dialog.error({ messageId: 'Msg_1903' }).then(() => { });
              return;
            }

            vm.settingListItems.push(data);
            vm.currentCodeList(data.code);    */
          });
        });
      });
    }

    closeDialog() {
      const vm = this;
      //KWR004_B_OUTPUT
      vm.$window.storage(KWR004_B_OUTPUT, vm.attendance());
      vm.$window.close();
    }

    /**
     *
    */
    creatDefaultSettingDetails(from: number = 0) {
      const vm = this;
      //clear
      vm.settingListItemsDetails([]);
      for (let i = from; i < NUM_ROWS; i++) {
        let dailyAttributes = i < 2 ? vm.dailyAloneAttributes() : vm.monthlyAttributes();
        let newItem = new SettingForPrint(
          i + 1, //id
          null, //name
          i < 2 ? 1 : 2, //indCalcClassic
          null, //selectionItem
          false, //checked
          0, //itemAttribute
          [], //selectedTimeList
          dailyAttributes, //dailyAttributes
          -1, //selectedCategory
          i < 2 //type: daily or monthly
        );
        vm.addRowItem(newItem);
      }
      console.log(vm.settingListItemsDetails());
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
        /* if (dataSelection.length > 20) {
          dataSelection = dataSelection.substring(0, 19) + vm.$i18n('KWR003_219');
        } */
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

          //KDL 047, 048
          vm.shareParam.titleLine.layoutCode = vm.attendanceCode();
          vm.shareParam.titleLine.layoutName = vm.attendanceName();

          vm.isEnableAttendanceCode(false);
          vm.isEnableAddButton(true);
          vm.isEnableDeleteButton(true);
          vm.isEnableDuplicateButton(true);
        }
      }
    }

    getSettingList() {
      const vm = this;
      vm.$window.storage(KWR004_B_INPUT).then((data: any) => {
        if (!_.isNil(data)) {
          let code = !_.isNil(data.code) ? data.code : null;

          vm.itemSelection(data.itemSelection);
          vm.attendanceCode(code);
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
              lisItems.push(new ItemModel(x.settingCode, x.settingName, x.settingId));
            });

            //sort by code with asc
            lisItems = _.orderBy(lisItems, ['code'], ['asc']);
            vm.settingListItems(lisItems);

            let firstItem: any = _.head(vm.settingListItems());
            if (!currentCode) currentCode = firstItem.code;
            vm.currentCodeList.push(currentCode);
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
      vm.shareParam.attribute.selected = row.selectedCategory(); //setting Category
      vm.shareParam.selectedTime = row.selectedTime;

      nts.uk.ui.windows.setShared('attendanceItem', vm.shareParam, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/047/a/index.xhtml').onClosed(() => {
        const attendanceItem = nts.uk.ui.windows.getShared('attendanceRecordExport');
        console.log(attendanceItem);
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

        //選択項目
        if (!_.isNil(findAttendanceName)) {

          let listItem = { itemId: -1, name: null, operator: null };
          listItem.itemId = parseInt(attendanceItem.attendanceId);
          listItem.name = findAttendanceName.attendanceItemName;
          listItem.operator = '+'; //+

          vm.settingListItemsDetails()[index].name(attendanceItem.itemNameLine.name);
          vm.settingListItemsDetails()[index].required(true);
          vm.settingListItemsDetails()[index].selectedTimeList.push(listItem);
          vm.settingListItemsDetails()[index].selectedCategory(attendanceItem.attribute);
          vm.settingListItemsDetails()[index].selectedTime = attendanceItem.attendanceId;
          vm.settingListItemsDetails()[index].selectionItem(findAttendanceName.attendanceItemName);
          $('#textName' + row.id).focus();
        } else {
          vm.settingListItemsDetails()[index].name(null);
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
          vm.settingListItemsDetails()[index].selectedTime = -1;
          vm.settingListItemsDetails()[index].selectedCategory(-1);
          vm.settingListItemsDetails()[index].required(false);
        }
      });
    }

    openDialogKDL048(row: any) {
      let vm = this,
        selectionItem: Array<string> = [];

      vm.shareParam.attribute.attributeList = [
        new AttendaceType(4, vm.$i18n('KWR002_180')),
        new AttendaceType(5, vm.$i18n('KWR002_181')),
        new AttendaceType(7, vm.$i18n('KWR002_183'))
      ]

      if (row.type === 1) {
        vm.shareParam.attribute.attributeList.push(new AttendaceType(6, vm.$i18n('KWR002_182')));
        vm.shareParam.diligenceProjectList = vm.diligenceProjectsMonthly(); //KDL048
      }
      vm.shareParam.itemNameLine.name = row.name();
      vm.shareParam.attribute.selected = row.selectedCategory(); //setting Category
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
            vm.settingListItemsDetails()[index].selectedCategory(attendanceItem.itemNameLine.displayInputCategory);
            $('#textName' + row.id).focus();
          }
        } else {
          vm.settingListItemsDetails()[index].name(null);
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
          vm.settingListItemsDetails()[index].selectedTime = -1;
          vm.settingListItemsDetails()[index].selectedCategory(-1);
          vm.settingListItemsDetails()[index].required(false);
        }
      });
    }

    checkItem(data: SettingForPrint) {
      console.log(data);
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
    selectedCategory: KnockoutObservable<number> = ko.observable(-1);
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
      selectedCategory?: number,
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
      this.selectedCategory(selectedCategory || -1)
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
    attributeList: AttendaceType[] = [];
    // 選択済み
    selected: number = 1;
  }

  export class AttendaceType {
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