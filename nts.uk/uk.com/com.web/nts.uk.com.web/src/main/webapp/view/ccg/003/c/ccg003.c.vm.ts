/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.c {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;
  import modal = nts.uk.ui.windows.sub.modal;

  const API = {
    // <<ScreenQuery>> 社員が作成するお知らせ登録の画面を表示する
    notificationCreatedByEmp: 'sys/portal/notice/notificationCreatedByEmp',
    // <<ScreenQuery>> お知らせ宛先の職場の名称を取得する
    getNameOfDestinationWkp: 'sys/portal/notice/getNameOfDestinationWkp',
    // <<ScreenQuery>> お知らせ宛先の社員の名称を取得する
    acquireNameOfDestinationEmployee: 'sys/portal/notice/acquireNameOfDestinationEmployee',
    // <<Command>> お知らせを登録する
    registerMessageNotice: 'sys/portal/notice/registerMessageNotice',
    // <<Command>> お知らせを削除する
    deleteMessageNotice: 'sys/portal/notice/deleteMessageNotice'
  };

  const COMMA = '、';

  @bean()
  export class ViewModel extends ko.ViewModel {
    messageText: KnockoutObservable<string> = ko.observable('');
    destination: KnockoutObservable<number> = ko.observable(2);
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: '',
      endDate: ''
    }));
    
    isNewMode: KnockoutObservable<boolean> = ko.observable(false);
    isActiveDelete: KnockoutComputed<boolean> = ko.computed(() => !this.isNewMode());

    // ※C1
    isVisibleAllEmployees: KnockoutObservable<boolean> = ko.observable(true);
    // ※C2
    isActiveWorkplaceBtn: KnockoutComputed<boolean> = ko.computed(() => this.destination() === 2);
    // ※C3
    isActiveEmployeeBtn: KnockoutComputed<boolean> = ko.computed(() => this.destination() === 3);
    // ※C4, !※C5
    isVisibleWorkplaceList: KnockoutObservable<boolean> = ko.observable(true);
    // ※C6
    isVisibleDestination: KnockoutComputed<boolean> = ko.computed(() => this.isNewMode() || this.destination() === 3);

    parentParam: ParentParam = new ParentParam();

    workPlaceIdList: KnockoutObservableArray<string> = ko.observableArray([]);
    workPlaceName: KnockoutObservableArray<string> = ko.observableArray([]);
    workPlaceText: KnockoutComputed<string> = ko.computed(() => {
      return this.workPlaceName().join(COMMA);
    });

    employeeInfoId: KnockoutObservableArray<string> = ko.observableArray([]);
    employeeName: KnockoutObservableArray<string> = ko.observableArray([]);
    employeeText: KnockoutComputed<string> = ko.computed(() => {
      return this.employeeName().join(COMMA);
    });
    
    created(parentParam: ParentParam) {
      const vm = this;
      vm.parentParam = parentParam;
      vm.isNewMode(vm.parentParam.isNewMode);
      vm.onStartScreen();
    }

    /**
     * 起動する
     */
    onStartScreen(): void {
      const vm = this;
      const msg: MessageNotice = vm.isNewMode() ? null : null;
      const params: NotificationParams = new NotificationParams({
        refeRange: vm.parentParam.employeeReferenceRange,
        msg: msg
      });
      vm.$blockui('show');
      this.$ajax(API.notificationCreatedByEmp, params).then(response => {

      })
      .always(() => vm.$blockui('hide'));
    }

    /**
     * C2_3_2：職場選択クリック時
     */
    openCDL008Dialog(): void {
      const vm = this;
      const inputCDL008: any = {
        startMode: StartMode.WORKPLACE,
        isMultiple: true,
        showNoSelection: false,
        selectedCodes: vm.workPlaceIdList(),
        isShowBaseDate: true,
        baseDate: moment.utc().toISOString(),
        selectedSystemType: SystemType.EMPLOYMENT,
        isrestrictionOfReferenceRange: true
      };
      setShared('inputCDL008', inputCDL008);
      modal('/view/cdl/008/a/index.xhtml').onClosed(() => {
        vm.workPlaceIdList(getShared('outputCDL008'));
        vm.$ajax(API.getNameOfDestinationWkp, vm.workPlaceIdList()).then((response: WorkplaceInfo[]) => {
          if (response) {
            const workPlaceIdList = _.map(response, x => x.workplaceId);
            const workPlaceName = _.map(response, x => x.workplaceName);
            vm.workPlaceIdList(workPlaceIdList);
            vm.workPlaceName(workPlaceName);
          }
        });
      });
    }

    /**
     * C2_3_3：社員選択クリック時
     */
    openCDL009Dialog(): void {
      const vm = this;
      const cdl009Params: any = {
        isMultiSelect: true,
        baseDate: moment.utc().toISOString(),
        target: TargetClassification.WORKPLACE
      };
      setShared('CDL009Params', cdl009Params);
      modal("/view/cdl/009/a/index.xhtml").onClosed(() => {
        const isCancel = getShared('CDL009Cancel');
        if (isCancel) {
            return;
        }
        vm.employeeInfoId(getShared('CDL009Output'));
        vm.$blockui('show');
        vm.$ajax(API.acquireNameOfDestinationEmployee, vm.employeeInfoId()).then((response: EmployeeInfo[]) => {
          if (response) {
            const employeeInfoId = _.map(response, x => x.sid)
            const employeeName = _.map(response, x => x.bussinessName);
            vm.employeeInfoId(employeeInfoId);
            vm.employeeName(employeeName);
          }
        });
      });
    }

    /**
     * C20_1：登録をクリックする
     */
    onClickRegister(): void {
      const vm = this;
      vm.registerOnNewMode();
    }

    /**
     * ※新規モードの場合
     */
    registerOnNewMode(): void {
      const vm = this;
      const message: MessageNotice = new MessageNotice({
        creatorID: __viewContext.user.employeeId,
        inputDate: moment.utc().toISOString(),
        modifiedDate: moment.utc().toISOString(),
        notificationMessage: vm.messageText(),
        targetInformation: new TargetInformation({
          destination: vm.destination(),
          targetWpids: vm.isActiveWorkplaceBtn() ? vm.workPlaceIdList() : [],
          targetSIDs: vm.isActiveEmployeeBtn() ? vm.employeeInfoId() : []
        }),
        datePeriod: new DatePeriod({
          startDate: moment.utc(vm.dateValue().startDate).toISOString(),
          endDate: moment.utc(vm.dateValue().endDate).toISOString()
        }),
        employeeIdSeen: null
      });

      const command = {
        creatorID: __viewContext.user.employeeId,
        messageNotice: message
      }
      vm.$blockui('show');
      vm.$ajax(API.registerMessageNotice, command).always(() => vm.$blockui('hide'));

    }

    /**
     * ※更新モードの場合
     */
    registerOnUpdateMode(): void {

    }

    /**
     * C20_2：削除をクリックする
     */
    onClickDelete(): void {
      const vm = this;
      const command = {
        creatorID: '',
        inputDate: ''
      };
      vm.$blockui('show');
      vm.$ajax(API.deleteMessageNotice, command).then(() => {})
      .always(() => vm.$blockui('hide'));
    }

    /**
     * Close dialog
     */
    closeWindow(): void {
      this.$window.close();
    }
  }

  class TargetClassification {
    static WORKPLACE = 1;
    static DEPARTMENT = 2;
  }

  class StartMode {
    static WORKPLACE = 0;
    static DEPARTMENT = 1;
  }

  class SystemType {
    static COMMON = 0;
    static EMPLOYMENT = 1;
    static SALARY = 2;
    static HUMAN_RESOURCE = 3;
  }

  class ParentParam {
    isNewMode: boolean;
    employeeReferenceRange: number;
    messageNotice: MessageNotice
  }

  class DatePeriod {
    startDate: string;
    endDate: string;

    constructor(init?: Partial<DatePeriod>) {
      $.extend(this, init);
    }
  }

  class NotificationParams {
    refeRange: number;
    msg: MessageNotice;
    constructor(init?: Partial<NotificationParams>) {
      $.extend(this, init);
    }
  }

  class MessageNotice {
    creatorID: string;
    inputDate: string;
    modifiedDate: string;
    targetInformation: TargetInformation;
    datePeriod: any;
    employeeIdSeen: string[];
    notificationMessage: string;
    constructor(init?: Partial<MessageNotice>) {
      $.extend(this, init);
    }
  }

  class WorkplaceInfo {
    workplaceId: string;
    workplaceCode: string;
    workplaceName: string;
  }

  class EmployeeInfo {
    sid: string;
    scd: string;
    bussinessName: string;
  }

  class TargetInformation {
	  targetSIDs: string[];
	  targetWpids: string[];
    destination: number;
    constructor(init?: Partial<TargetInformation>) {
      $.extend(this, init);
    }
  }
  
}