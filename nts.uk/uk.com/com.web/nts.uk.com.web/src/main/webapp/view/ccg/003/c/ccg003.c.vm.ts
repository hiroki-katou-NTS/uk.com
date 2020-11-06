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
    deleteMessageNotice: 'sys/portal/notice/deleteMessageNotice',
    // <<Command>> お知らせを更新する
    updateMessageNotice: 'sys/portal/notice/updateMessageNotice'
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
    updateDate: KnockoutObservable<string> = ko.observable('');

    isNewMode: KnockoutObservable<boolean> = ko.observable(false);
    isActiveDelete: KnockoutComputed<boolean> = ko.computed(() => !this.isNewMode());

    // ※C1
    isVisibleAllEmployees: KnockoutComputed<boolean> = ko.computed(() => this.destination() === DestinationClassification.ALL);
    // ※C2
    isActiveWorkplaceBtn: KnockoutComputed<boolean> = ko.computed(() => this.destination() === DestinationClassification.WORKPLACE);
    // ※C3
    isActiveEmployeeBtn: KnockoutComputed<boolean> = ko.computed(() => this.destination() === DestinationClassification.DEPARTMENT);
    // ※C4, !※C5
    isVisibleWorkplaceList: KnockoutComputed<boolean> = ko.computed(() => this.employeeReferenceRange !== EmployeeReferenceRange.DEPARTMENT_ONLY);
    // ※C6
    isVisibleDestination: KnockoutComputed<boolean> = ko.computed(() =>
      this.isNewMode() || moment.utc(this.dateValue().startDate, 'YYYY/MM/DD').isBefore(moment.utc(new Date(), 'YYYY/MM/DD')));

    parentParam: ParentParam = new ParentParam();
    isStartUpdateMode: boolean = false;

    workPlaceIdList: KnockoutObservableArray<string> = ko.observableArray([]);
    workPlaceName: KnockoutObservableArray<string> = ko.observableArray([]);
    notificationCreated: KnockoutObservable<NotificationCreated> = ko.observable(null);
    workPlaceText: KnockoutComputed<string> = ko.computed(() => {
      return this.workPlaceName().join(COMMA);
    });
    workPlaceTxtRefer: KnockoutObservable<string> = ko.observable('');

    employeeInfoId: KnockoutObservableArray<string> = ko.observableArray([]);
    employeeName: KnockoutObservableArray<string> = ko.observableArray([]);
    employeeText: KnockoutComputed<string> = ko.computed(() => {
      return this.employeeName().join(COMMA);
    });
    employeeReferenceRange: number = 0;
    startDateOnUpdateMode: string = '';

    startDateOfMsgUpdate: string = '';

    created(parentParam: ParentParam) {
      const vm = this;
      vm.parentParam = parentParam;
      vm.isNewMode(vm.parentParam.isNewMode);
      vm.isStartUpdateMode = !vm.parentParam.isNewMode;
      vm.employeeReferenceRange = parentParam.employeeReferenceRange;
      vm.onStartScreen();
    }

    mounted() {
      const vm = this;
      $('#C1_2').focus();
      vm.destination.subscribe(() => {
        if (vm.employeeReferenceRange === EmployeeReferenceRange.DEPARTMENT_ONLY) {
          if (vm.destination() === DestinationClassification.WORKPLACE && vm.isStartUpdateMode) {
            vm.isStartUpdateMode = false;
            const wkpList = vm.notificationCreated().targetWkps;
            vm.workPlaceTxtRefer(_.map(wkpList, wkp => wkp.workplaceName).join(COMMA));
            return;
          }
          if (_.isNull(vm.notificationCreated().workplaceInfo)) {
            const workplaceInfo = this.notificationCreated().workplaceInfo;
            vm.workPlaceTxtRefer(workplaceInfo.workplaceCode + ' ' + workplaceInfo.workplaceName);
          }
          vm.isStartUpdateMode = false;
        }
      });
    }

    /**
     * 起動する
     */
    onStartScreen(): void {
      const vm = this;
      const msg: MessageNotice = vm.isNewMode() ? null : vm.parentParam.messageNotice;
      if (!vm.isNewMode() && msg !== null) {
        // C1_2
        vm.messageText(msg.notificationMessage);
        // C2_2
        vm.destination(msg.targetInformation.destination);
        // C3_2
        const period: DatePeriod = new DatePeriod({
          startDate: msg.datePeriod.startDate,
          endDate: msg.datePeriod.endDate
        });
        vm.dateValue(period);
        // C4
        const updateDate: string = moment.utc(msg.modifiedDate, 'YYYY/MM/DD HH:mm').toString();
        vm.updateDate(vm.$i18n('CCG003_27', [updateDate]));
        vm.startDateOfMsgUpdate = msg.datePeriod.startDate;
        msg.datePeriod.endDate = moment.utc(msg.datePeriod.endDate, 'YYYY/MM/DD').toISOString();
        msg.datePeriod.startDate = moment.utc(msg.datePeriod.startDate, 'YYYY/MM/DD').toISOString();
      }

      const params: NotificationParams = new NotificationParams({
        refeRange: vm.parentParam.employeeReferenceRange,
        msg: msg
      });
      vm.$blockui('show');
      this.$ajax('com', API.notificationCreatedByEmp, params).then((response: NotificationCreated) => {
        if (response) {
          vm.notificationCreated(response);
          if (this.employeeReferenceRange === EmployeeReferenceRange.DEPARTMENT_ONLY) {
            // ※新規モード又は更新モード(宛先区分≠職場選択)
            if (_.isNull(response.workplaceInfo) && this.destination() !== DestinationClassification.WORKPLACE) {
              vm.workPlaceIdList([response.workplaceInfo.workplaceId]);
              vm.workPlaceName([response.workplaceInfo.workplaceName]);
            }
          }
          // ※　ロール.参照範囲＝全社員　OR　部門・職場(配下含む）の場合
          if (vm.employeeReferenceRange === EmployeeReferenceRange.ALL_EMPLOYEE || vm.employeeReferenceRange === EmployeeReferenceRange.DEPARTMENT_AND_CHILD) {
            const targetWkps = response.targetWkps;
            const workPlaceIdList = _.map(targetWkps, wkp => wkp.workplaceId);
            const workPlaceName = _.map(targetWkps, wkp => wkp.workplaceName);
            vm.workPlaceIdList(workPlaceIdList);
            vm.workPlaceName(workPlaceName);
          }

          const targetEmps = response.targetEmps;
          const employeeInfoId = _.map(targetEmps, emp => emp.sid);
          const employeeName = _.map(targetEmps, emp => emp.bussinessName);
          vm.employeeName(employeeName);
          vm.employeeInfoId(employeeInfoId);
        }
      })
        .fail(error => vm.$dialog.error(error))
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
        const isCancel = getShared('CDL008Cancel');
        if (isCancel) {
          return;
        }
        vm.workPlaceIdList(getShared('outputCDL008'));
        vm.$ajax('com', API.getNameOfDestinationWkp, vm.workPlaceIdList()).then((response: WorkplaceInfo[]) => {
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
        target: DestinationClassification.WORKPLACE
      };
      setShared('CDL009Params', cdl009Params);
      modal("/view/cdl/009/a/index.xhtml").onClosed(() => {
        const isCancel = getShared('CDL009Cancel');
        if (isCancel) {
          return;
        }
        vm.employeeInfoId().push(getShared('CDL009Output'));
        vm.$blockui('show');
        vm.$ajax('com', API.acquireNameOfDestinationEmployee, vm.employeeInfoId()).then((response: EmployeeInfo[]) => {
          if (response) {
            const employeeInfoId = _.map(response, x => x.sid)
            const employeeName = _.map(response, x => x.bussinessName);
            vm.employeeInfoId(employeeInfoId);
            vm.employeeName(employeeName);
          }
        })
          .always(() => vm.$blockui('hide'));
      });
    }

    /**
     * C20_1：登録をクリックする
     */
    onClickRegister(): void {
      const vm = this;
      const error: string = vm.checkBeforeRegister();
      if (!_.isEmpty(error)) {
        vm.$dialog.error({ messageId: error });
        return;
      }
      if (vm.isNewMode()) {
        vm.registerOnNewMode();
      } else {
        vm.registerOnUpdateMode();
      }
    }

    /**
     * 登録前のチェックについて
     */
    checkBeforeRegister(): string {
      const vm = this;
      if (vm.destination() === DestinationClassification.WORKPLACE && _.isEmpty(vm.workPlaceIdList())) {
        return 'Msg_1813';
      }

      if (vm.destination() === DestinationClassification.DEPARTMENT && _.isEmpty(vm.employeeInfoId())) {
        return 'Msg_1814';
      }

      if (moment.utc(vm.dateValue().startDate).isBefore(moment.utc().format('YYYY/MM/DD'))) {
        if (vm.isNewMode()) {
          return 'Msg_1834';
        } else if (vm.startDateOfMsgUpdate !== '' && !moment.utc(vm.startDateOfMsgUpdate, 'YYYY/MM/DD').isSame(moment.utc(new Date(), 'YYYY/MM/DD'))) {
          return 'Msg_1834';
        }
      }
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
      vm.$ajax('com', API.registerMessageNotice, command)
        .then(() => {
          vm.$dialog.info({ messageId: 'Msg_15' })
            .then(() => this.$window.close({ isClose: true }))
        })
        .fail(error => vm.$dialog.error(error))
        .always(() => vm.$blockui('hide'));

    }

    /**
     * ※更新モードの場合
     */
    registerOnUpdateMode(): void {
      const vm = this;
      const oldMsg = vm.parentParam.messageNotice;

      const message: MessageNotice = new MessageNotice({
        creatorID: oldMsg.creatorID,
        inputDate: oldMsg.inputDate,
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
        })
      });

      const command = {
        creatorId: oldMsg.creatorID,
        inputDate: oldMsg.inputDate,
        messageNotice: message
      }
      vm.$blockui('show');
      vm.$ajax('com', API.updateMessageNotice, command)
        .then(() => {
          vm.$dialog.info({ messageId: 'Msg_15' })
            .then(() => this.$window.close({ isClose: true }))
        })
        .fail(error => vm.$dialog.error(error))
        .always(() => vm.$blockui('hide'));
    }

    /**
     * C20_2：削除をクリックする
     */
    onClickDelete(): void {
      const vm = this;
      vm.$dialog.confirm({ messageId: 'Msg_18' }).then((result: 'no' | 'yes' | 'cancel') => {
        if (result !== 'yes') {
          return;
        }
        const messageNotice = vm.parentParam.messageNotice;
        const command = {
          creatorID: messageNotice.creatorID,
          inputDate: messageNotice.inputDate
        };
        vm.$blockui('show');
        vm.$ajax('com', API.deleteMessageNotice, command)
          .then(() => {
            vm.$dialog.info({ messageId: 'Msg_16' })
              .then(() => this.$window.close({ isClose: false }))
          })
          .always(() => vm.$blockui('hide'));
      });

    }

    /**
     * Close dialog
     */
    closeWindow(): void {
      this.$window.close({ isClose: true });
    }
  }

  enum DestinationClassification {
    ALL = 0,
    WORKPLACE = 1,
    DEPARTMENT = 2
  }

  enum StartMode {
    WORKPLACE = 0,
    DEPARTMENT = 1
  }

  enum SystemType {
    COMMON = 0,
    EMPLOYMENT = 1,
    SALARY = 2,
    HUMAN_RESOURCE = 3
  }

  enum EmployeeReferenceRange {
    ALL_EMPLOYEE = 0,
    DEPARTMENT_AND_CHILD = 1,
    DEPARTMENT_ONLY = 2,
    ONLY_MYSELF = 3
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

  interface NotificationCreated {
    workplaceInfo: WorkplaceInfo;
    targetWkps: WorkplaceInfo[];
    targetEmps: EmployeeInfo[];
  }

}
