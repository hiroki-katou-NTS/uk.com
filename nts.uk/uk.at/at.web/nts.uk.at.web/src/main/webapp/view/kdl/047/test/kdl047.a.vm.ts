/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl047.test.screenModel {

  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {
    itemList: KnockoutObservableArray<any>;

    position: KnockoutObservable<number> = ko.observable(1);
    attendanceItemName: KnockoutObservable<string> = ko.observable('勤務種類');
    attendanceCode: KnockoutObservable<string> = ko.observable('02');
    attendanceName: KnockoutObservable<string> = ko.observable('出勤簿');
    columnIndex: KnockoutObservable<number> = ko.observable(1);
    tab: KnockoutObservable<number> = ko.observable(1);
    tabs: KnockoutObservableArray<any>;
    isDisplayItemName: KnockoutObservable<boolean> = ko.observable(true);
    isDisplayTitle: KnockoutObservable<boolean> = ko.observable(true);
    isEnableComboBox: KnockoutObservable<number> = ko.observable(1);
    isEnableTextEditor: KnockoutObservable<number> = ko.observable(1);
    comboSelected: KnockoutObservable<any> = ko.observable(null);
    tableSelected: KnockoutObservable<any> = ko.observable(null);

    created() {
      const vm = this;
      vm.itemList = ko.observableArray([
        {
          id: 1,
          name: 'UPPER'
        },
        {
          id: 2,
          name: 'LOWER'
        }
      ])
      vm.tabs = ko.observableArray([
        {
          id: 1,
          name: 'Tab-1'
        },
        {
          id: 2,
          name: 'Tab-2'
        }
      ]);

    }

    open(): void {
      const vm = this;
      let shareParam: AttendanceItemShare = new AttendanceItemShare();
      shareParam.titleLine.displayFlag = vm.isDisplayTitle();
      shareParam.titleLine.layoutCode = vm.attendanceCode();
      shareParam.titleLine.layoutName = vm.attendanceName();
      const positionText = vm.position() === 1 ? "上" : "下";
      shareParam.titleLine.directText = vm.$i18n('KWR002_131') + vm.columnIndex() + vm.$i18n('KWR002_132') + positionText + vm.$i18n('KWR002_133');
      shareParam.itemNameLine.displayFlag = vm.isDisplayItemName();
      shareParam.itemNameLine.displayInputCategory = vm.isEnableTextEditor();
      shareParam.itemNameLine.name = vm.attendanceItemName();
      shareParam.attribute.selectionCategory = vm.isEnableComboBox();
      shareParam.attribute.selected = vm.comboSelected();
      shareParam.selectedTime = vm.tableSelected();
      shareParam.attribute.attributeList = [
        new AttendaceType(1, vm.$i18n('KWR002_141')),
        new AttendaceType(2, vm.$i18n('KWR002_142')),
        new AttendaceType(3, vm.$i18n('KWR002_143')),
        new AttendaceType(4, vm.$i18n('KWR002_180')),
        new AttendaceType(5, vm.$i18n('KWR002_181')),
        new AttendaceType(6, vm.$i18n('KWR002_182')),
        new AttendaceType(7, vm.$i18n('KWR002_183'))
      ]

      shareParam.attendanceItems = [
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

      setShared('attendanceItem', shareParam, true);

      nts.uk.ui.windows.sub.modal('/view/kdl/047/a/index.xhtml').onClosed(() => {
        const attendanceItem = getShared('attendanceRecordExport');
        if (!attendanceItem) {
          return;
        }
        vm.comboSelected = ko.observable(attendanceItem.attribute);
        vm.tableSelected = ko.observable(attendanceItem.attendanceId);
        vm.attendanceItemName(attendanceItem.attendanceItemName);
        alert('Code selection in combo: ' + vm.comboSelected() +' and '+'Code selection in table ' + vm.tableSelected())
      });
    }

  }


  export class AttendaceType {
    attendanceTypeCode: number;
    attendanceTypeName: string;
    constructor(attendanceTypeCode: number, attendanceTypeName: string) {
      this.attendanceTypeCode = attendanceTypeCode;
      this.attendanceTypeName = attendanceTypeName;
    }
  }

  export class AttendanceItemShare {
    // タイトル行
    titleLine: any = {};
    // 項目名行
    itemNameLine: any = {};
    // 属性
    attribute: any = {};
    // List<勤怠項目>
    attendanceItems: Array<any> = [];
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

    constructor(init?: Partial<AttendanceItemShare>) {
      $.extend(this, init);
    }
  }

  export class DiligenceProject {
    // ID
    attendanceItemId: any;
    // 名称
    attendanceItemName: any;
    // 属性
    attributes: any;
    // 表示番号
    displayNumbers: any;

    constructor(id: any, name: any, attributes: any, indicatesNumber: any) {
      this.attendanceItemId = id;
      this.attendanceItemName = name;
      this.attributes = attributes;
      this.displayNumbers = indicatesNumber;
    }
  }
}