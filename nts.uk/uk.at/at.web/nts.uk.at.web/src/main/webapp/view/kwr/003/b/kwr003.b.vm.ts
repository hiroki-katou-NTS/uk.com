/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.b {
  const NUM_ROWS = 10;
  const KWR003_B_INPUT = 'KWR003_WORK_STATUS_DATA';
  const KWR003_B_OUTPUT = 'KWR003WORK_STATUS_RETURN';
  const KWR003_C_INPUT = 'KWR003_C_DATA';
  const KWR003_C_OUTPUT = 'KWR003_C_RETURN';

  const PATH = {
    getSettingListWorkStatus: 'at/function/kwr/003/a/listworkstatus',
    getSettingLitsWorkStatusDetails: 'at/function/kwr/003/b/detailworkstatus',
    checkDailyAuthor: 'at/function/kwr/003/a/checkdailyauthor',
    deleteSettingItemDetails: '',
    createSettingItemDetails: '',
    updateSettingItemDetails: '',
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
    attendance: KnockoutObservable<any> = ko.observable(null);
    attendanceCode: KnockoutObservable<string> = ko.observable(null);
    attendanceName: KnockoutObservable<string> = ko.observable(null);
    settingRuleCode: KnockoutObservable<number> = ko.observable(0);
    settingListItemsDetails: KnockoutObservableArray<SettingForPrint> = ko.observableArray([]);

    isSelectAll: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAddButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAttendanceCode: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDeleteButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDuplicateButton: KnockoutObservable<boolean> = ko.observable(false);
    isNewMode: KnockoutObservable<boolean> = ko.observable(false);

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


    workStatusTableOutputItem: KnockoutObservableArray<any> = ko.observableArray([]);

    constructor(params: any) {
      super();

      const vm = this;

      //get output info
      vm.getWorkStatusTableOutput();

      vm.getSettingList();

      vm.currentCodeList.subscribe((code: any) => {
        nts.uk.ui.errors.clearAll();
        vm.getSettingListForPrint(code);
      });

      vm.settingRules = ko.observableArray([
        { code: 0, name: vm.$i18n('KWR003_217') },
        { code: 1, name: vm.$i18n('KWR003_218') }
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
        /* new AttendaceType(4, vm.$i18n('KWR002_180')),
        new AttendaceType(5, vm.$i18n('KWR002_181')),
        new AttendaceType(6, vm.$i18n('KWR002_182')),
        new AttendaceType(7, vm.$i18n('KWR002_183')) */
      ]

      vm.shareParam.attendanceItems = vm.getDiligenceProject();
      vm.shareParam.diligenceProjectList = vm.getDiligenceProject();

    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      if (!!navigator.userAgent.match(/Trident.*rv\:11\./))
        $("#multiGridList").ntsFixedTable({ height: 368 });
      else
        $("#multiGridList").ntsFixedTable({ height: 370 });
    }

    addRowItem(newRow?: SettingForPrint) {
      let vm = this,
        row: SettingForPrint = newRow;

      if (!newRow) {
        let lastItem: any = _.last(vm.settingListItemsDetails());
        let id = lastItem ? lastItem.id : 1;
        row = new SettingForPrint(id, null, 0, null, false);
      }

      row.isChecked.subscribe((value: boolean) => {
        vm.settingListItemsDetails.valueHasMutated();
      });

      vm.settingListItemsDetails.push(row);
    }

    addNewRow() {
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
      $('#KWR003_B42').focus();
    }

    /**
     * Registers setting
     */
    registerSetting() {
      const vm = this;

      let params: any = {
        code: '',
        name: '',
        settingListItemsDetails: vm.settingListItemsDetails()
      };

      /* 
      if (vm.isNewMode()) {
        //コードが重複しているため、登録できません。 Msg_1753        
        let checkExist = _.find(vm.settingListItems(), ['code', _.trim(vm.attendanceCode())]);        
        if( !_.isNil(checkExist) ) {
          vm.$dialog.error({messageId: 'Msg_1753'}).then(() => {
            $('#KWR003_B42').focus();           
          });
          return;
        }
      } else {
        //出力項目が削除されています。 ＃Msg_1903
        let temp = vm.settingListItemsDetails();
        temp=  _.filter(temp, (x) => x.id !== 1);
        if( temp.length !== vm.settingListItemsDetails().length ) {
          vm.$dialog.error({messageId: 'Msg_1903'}).then(() => {
            $('#btnB11').focus();
          });
          return;
        }
      } */

      //sort by name with desc
      let listItemsDetails: Array<any> = [];
      listItemsDetails = vm.orderListItemsByField(vm.settingListItemsDetails());
      vm.createListItemAfterSorted(listItemsDetails);

      let returnAttendance: AttendanceItem = {
        code: vm.attendanceCode(),
        name: vm.attendanceName(),
        status: vm.isNewMode() ? 1 : 0 // 0: Update, 1: Addnew, 2: Remove
      };
      vm.attendance(returnAttendance);

      //change to update status
      vm.isNewMode(false);

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
          selectedTimeList: row.selectedTimeList()
        };

        newListItemsDetails.push(temp);
      });

      newListItemsDetails = _.orderBy(newListItemsDetails, [field], [sort_type]);

      return newListItemsDetails;
    }

    /**
     * Create list item after sorted
     * @param [listItemsDetails] 
     */
    createListItemAfterSorted(listItemsDetails?: Array<any>) {
      const vm = this;

      vm.settingListItemsDetails([]);
      _.forEach(listItemsDetails, (x: any) => {
        let newIitem: SettingForPrint = new SettingForPrint(
          x.id, x.name, x.setting,
          x.selectionItem, x.isChecked,
          x.selectedTimeList);
        vm.addRowItem(newIitem);
      });
    }

    /**
     * Detele setting
     */
    deteleSetting() {
      const vm = this;

      let returnAttendance: AttendanceItem = {};
      returnAttendance.code = vm.attendanceCode();
      returnAttendance.status = 2; //Deleted
      vm.attendance(returnAttendance);

      /*       
      vm.$blockui('show');

      const params = {
        code: vm.attendanceCode() //該当する設定ID
      };

      vm.$dialog.confirm({ messageId: 'Msg_18' }).then((answer: string) => {
        if (answer === 'yes') {
          vm.$ajax(PATH.deleteSettingItemDetails, params)
            .done(() => {
              vm.$dialog.info({ messageId: 'Msg_16' }).then(() => {
                let returnAttendance: AttendanceItem= {};

                returnAttendance.code = vm.attendanceCode();
                returnAttendance.status = 2; //Deleted

                vm.attendance(returnAttendance);
                vm.$blockui('hide');
              })
            })
            .always(() => {
              vm.$blockui('hide');
            })
            .fail((error) => {

            });
        }
      }); */
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
      let lastItem = _.last(vm.settingListItems());

      let params = {
        code: vm.attendanceCode(), //複製元の設定ID
        name: vm.attendanceName(),
        lastCode: !_.isNil(lastItem) ? lastItem.code : null,
        settingListItemDetails: vm.settingListItemsDetails() //設定区分
      }

      vm.$window.storage(KWR003_C_INPUT, ko.toJS(params)).then(() => {
        vm.$window.modal('/view/kwr/003/c/index.xhtml').then(() => {
          vm.$window.storage(KWR003_C_OUTPUT).then((data) => {
            if (_.isNil(data)) {
              return;
            }

            let duplicateItem = _.find(vm.settingListItems(), (x) => x.code === data.code);
            if (!_.isNil(duplicateItem)) {
              vm.$dialog.error({ messageId: 'Msg_1903' }).then(() => { });
              return;
            }

            vm.settingListItems.push(data);
            vm.currentCodeList(data.code);
          });
        });
      });

      $('#KWR003_B43').focus();
    }

    /**
     * Close dialog
     */
    closeDialog() {
      const vm = this;
      vm.$window.storage(KWR003_B_OUTPUT, vm.attendance());
      vm.$window.close();
    }

    /**
     * Get setting list items details
     */
    getSettingListItemsDetails() {
      const vm = this;

      for (let i = 0; i < NUM_ROWS; i++) {
        let newIitem: SettingForPrint = new SettingForPrint(i + 1, '予定勤務種類', 0, '予定勤務種類', false);
        vm.addRowItem(newIitem);
      }

      let selectedObj = _.find(vm.settingListItems(), (x) => x.code === vm.currentCodeList());
      //get details the work table status
      let totalItems = 10;
      let listDaily = vm.workStatusTableOutputItem().listDaily;

      vm.$ajax(PATH.getSettingLitsWorkStatusDetails, { settingId: selectedObj.id }).done((data) => {
        if (!_.isNil(data) && data.outputItemList.length > 0) {
          /*  id?: number,
           name?: string,
           setting?: number,
           selectionItem?: string,
           checked?: boolean,
           selectedTimeList?: Array<selectedTimeList> */

          totalItems = totalItems - data.outputItemList.length;
          _.forEach(data.outputItemList, (x, index: number) => {
            let selectedTimeList: Array<selectedTimeList> = [];
            //get selected items: 選択項目
            _.forEach(x.attendanceItemList, (element: any) => {
              /* itemId?: number;
              operator?: string;
              name?: string;
              indicatesNumber?: number */
              let findObj = _.find(listDaily, (listItem: any) => listItem.attendanceItemId === element.attendanceItemId);
            });

            let newIitem: SettingForPrint = new SettingForPrint(
              index + 1,
              x.name,
              x.printTargetFlag,
              vm.createDataSelection(selectedTimeList),
              x.printTargetFlag,
              selectedTimeList);
            vm.addRowItem(newIitem);
          });
        }
      });

      //order by list
      //let listItemsDetails: Array<any> = [];
      //listItemsDetails = vm.orderListItemsByField(vm.settingListItemsDetails());
      //vm.createListItemAfterSorted(listItemsDetails);
    }

    /**
     * Creatsedefault setting details
     */
    createDefaultSettingDetails() {
      const vm = this;
      //clear
      vm.settingListItemsDetails([])
      for (let i = 0; i < NUM_ROWS; i++) {
        let newIitem = new SettingForPrint(i + 1, null, 0, null, false);
        vm.addRowItem(newIitem);
      }
    }
    /**
     * Creates data selection
     * @param selectedTimeList 
     * @returns  
     */
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
        if (dataSelection.length > 20) {
          dataSelection = dataSelection.substring(0, 19) + vm.$i18n('KWR003_219');
        }
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
          vm.isNewMode(false);
        }
      }

      $('#KWR003_B43').focus();
    }

    getWorkStatusTableOutput() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax(PATH.getFormInfo, { formNumberDisplay: 6 }).done((result) => {
        vm.workStatusTableOutputItem(result);
        vm.$blockui('hide');
        console.log(vm.workStatusTableOutputItem());
      }).always(() => vm.$blockui('hide'));
    }

    /**
     * Gets setting listwork status
     * @param type 
     * 定型選択	: 0
     * 自由設定 : 1
     */
    getSettingListWorkStatus(type: number): any {
      const vm = this;
      let listWorkStatus: Array<ItemModel> = [];
      //定型選択		
      vm.$ajax(PATH.getSettingListWorkStatus, { setting: type }).then((data) => {
        console.log(data);
        if (!_.isNil(data)) {
          _.forEach(data, (item) => {
            listWorkStatus.push(new ItemModel(item.settingDisplayCode, item.settingName, item.settingId));
          });
        }
        console.log(listWorkStatus);
        return listWorkStatus;
      });
    }

    getSettingList() {
      const vm = this;
      let listWorkStatus: Array<any> = [];

      vm.$window.storage(KWR003_B_INPUT).then((data: any) => {

        if (!data) return;

        vm.$ajax(PATH.getSettingListWorkStatus, { setting: data.standOrFree }).then((data) => {
          console.log(data);
          if (!_.isNil(data)) {
            _.forEach(data, (item) => {
              listWorkStatus.push(new ItemModel(item.settingDisplayCode, item.settingName, item.settingId));
            });
          }

          //sort by code with asc
          listWorkStatus = _.orderBy(listWorkStatus, ['code'], ['asc']);
          vm.settingListItems(listWorkStatus);

          let code = !_.isNil(data) ? data.code : null;
          if (vm.settingListItems().length > 0) {
            let firstItem: any = _.head(vm.settingListItems());
            if (!code) code = firstItem.code;
          }

          vm.currentCodeList(code);
          vm.getSettingListForPrint(code);

          vm.getSettingListItemsDetails();
          vm.currentSettingCodeList = ko.observableArray([]);
        });
      });
    }

    /**
     * Opens dialog KDL
     * @param data 
     */
    openDialogKDL(data: SettingForPrint) {
      const vm = this;

      if (data.setting())
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
      nts.uk.ui.windows.setShared('attendanceItem', vm.shareParam, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/047/a/index.xhtml').onClosed(() => {
        const attendanceItem = nts.uk.ui.windows.getShared('attendanceRecordExport');
        if (_.isNil(attendanceItem)) {
          return;
        }

        let index = _.findIndex(vm.settingListItemsDetails(), (o: any) => { return o.id === row.id; });
        if (attendanceItem.attendanceItemName) {
          vm.settingListItemsDetails()[index].name(attendanceItem.attendanceItemName);
          $('#textName' + vm.settingListItemsDetails()[index].id).ntsError('clear');
        }

        let findAttedenceName = _.find(vm.shareParam.attendanceItems, (x: any) => { return x.attendanceItemId === parseInt(attendanceItem.attendanceId); });
        if (!_.isNil(findAttedenceName)) {
          vm.settingListItemsDetails()[index].selectionItem(findAttedenceName.attendanceItemName);
          let listItem: selectedTimeList = {};
          listItem.itemId = attendanceItem.attendanceId;
          listItem.name = findAttedenceName.attendanceItemName;
          vm.settingListItemsDetails()[index].selectedTimeList.push(listItem);
        } else {
          vm.settingListItemsDetails()[index].selectionItem(null);
          vm.settingListItemsDetails()[index].selectedTimeList([]);
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

      vm.shareParam.attribute.attributeList = [
        new AttendaceType(4, vm.$i18n('KWR002_180')),
        new AttendaceType(5, vm.$i18n('KWR002_181')),
        //new AttendaceType(6, vm.$i18n('KWR002_182')),
        new AttendaceType(7, vm.$i18n('KWR002_183'))
      ]

      vm.shareParam.itemNameLine.name = row.name();

      nts.uk.ui.windows.setShared('attendanceItem', vm.shareParam, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/048/index.xhtml').onClosed(() => {
        const attendanceItem = nts.uk.ui.windows.getShared('attendanceRecordExport');

        if (!attendanceItem) {
          return;
        }

        if (attendanceItem.selectedTimeList.length > 0) {
          let index = _.findIndex(vm.settingListItemsDetails(), (o: any) => { return o.id === row.id; });
          let dataSelection: string = vm.createDataSelection(attendanceItem.selectedTimeList);
          if (index > -1) {
            vm.settingListItemsDetails()[index].name(attendanceItem.itemNameLine.name);
            vm.settingListItemsDetails()[index].selectionItem(dataSelection);
            vm.settingListItemsDetails()[index].selectedTimeList(attendanceItem.selectedTimeList);
          }
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

    getDiligenceProject() {
      let DiligenceProjects = [
        new DiligenceProject(1, '予定勤務種類', '', 0),
        new DiligenceProject(28, '勤務種類', '勤務種類', 28),
        new DiligenceProject(2, '予定就業時間帯', '予定就業時間帯', 2),
        new DiligenceProject(3, '予定出勤時刻1', '予定出勤時刻1', 3),
        new DiligenceProject(5, '予定出勤時刻5', '予定出勤時刻5', 5),
        new DiligenceProject(6, '予定出勤時刻6', '予定出勤時刻6', 6),
        new DiligenceProject(8, '予定出勤時刻8', '予定出勤時刻8', 8),
        new DiligenceProject(9, '予定出勤時刻9', '予定出勤時刻9', 9),
        new DiligenceProject(10, '予定出勤時刻10', '予定出勤時刻10', 10),
        new DiligenceProject(11, '予定出勤時111', '予定出勤時刻11', 11),
        new DiligenceProject(12, '予定出勤時刻12', '予定出勤時刻12', 12),
        new DiligenceProject(13, '予定出勤時刻13', '予定出勤時刻13', 13),
        new DiligenceProject(14, '予定出勤時刻14', '予定出勤時刻14', 14),
        new DiligenceProject(15, '予定出勤時刻15', '予定出勤時刻15', 15),
        new DiligenceProject(16, '予定出勤時刻16', '予定出勤時刻16', 16),
        new DiligenceProject(4, '予定退勤時刻1', '予定退勤時刻1', 4),
        new DiligenceProject(7, '予定休憩開始時刻1', '予定休憩開始時刻1', 7),
        new DiligenceProject(8, '予定休憩終了時刻1', '予定休憩終了時刻1', 8),
        new DiligenceProject(27, '予定時間', '予定時間', 27),
        new DiligenceProject(216, '残業１', '残業１', 216),
        new DiligenceProject(461, '勤務回数', '勤務回数', 461),
        new DiligenceProject(534, '休憩回数', '休憩回数', 534),
        new DiligenceProject(641, 'aaaaaaaaa回数', 'aaaaaaaaa回数', 641),
        new DiligenceProject(642, 'tukijikan回数', 'tukijikan回数', 642),
        new DiligenceProject(643, 'tukikin', 'tukikin', 643),
        new DiligenceProject(644, '出有ｵﾝ無ｵﾌ有ｶｳﾝﾄ（日次ﾄﾘｶﾞ）ｄ', '出有ｵﾝ無ｵﾌ有ｶｳﾝﾄ（日次ﾄﾘｶﾞ）ｄ', 644),
        new DiligenceProject(645, '出有ｵﾝ有ｵﾌ無ｶｳﾝﾄ（日次ﾄﾘｶﾞ）（bb）', '出有ｵﾝ有ｵﾌ無ｶｳﾝﾄ（日次ﾄﾘｶﾞ）（bb）', 645),
        new DiligenceProject(680, '任意項目４０', '任意項目４０', 680),
        new DiligenceProject(681, '任意項目４１', '任意項目４１', 681),
        new DiligenceProject(682, '任意項目４２月別', '任意項目４２月別', 682),
        new DiligenceProject(683, '任意項目４３', '任意項目４３', 683),
        new DiligenceProject(267, '振替休日１', '振替休日１', 267),
        new DiligenceProject(268, '計算休日出勤１', '計算休日出勤１', 268),
        new DiligenceProject(269, '計算振替休日１', '計算振替休日１', 269),
        new DiligenceProject(270, '事前休日出勤１', '事前休日出勤１', 270)
      ];

      return DiligenceProjects;
    }
  }

  //=================================================================
  export interface AttendanceItem {
    code?: string;
    name?: string;
    status?: number;
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
    setting: KnockoutObservable<number> = ko.observable(0); //  0 / 1
    selectionItem: KnockoutObservable<string> = ko.observable(null);
    selectedTimeList: KnockoutObservableArray<selectedTimeList> = ko.observableArray([]);

    constructor(
      id?: number,
      name?: string,
      setting?: number,
      selectionItem?: string,
      checked?: boolean,
      selectedTimeList?: Array<selectedTimeList>) {
      this.name(name || '');
      this.setting(setting);
      this.isChecked(checked || false);
      this.selectionItem(selectionItem || '');
      this.id = id;
      this.selectedTimeList(selectedTimeList || []);
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

  export interface selectedTimeList {
    itemId?: number;
    operator?: string;
    name?: string;
    indicatesNumber?: number
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