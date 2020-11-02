/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.a {

  const API = {
    // <<ScreenQuery>> 社員のお知らせの画面を表示する
    getEmployeeNotification: 'sys/portal/notice/getEmployeeNotification',
    // <<ScreenQuery>> 社員が宛先のお知らせの内容を取得する
    getContentOfDestinationNotification: 'sys/portal/notice/getContentOfDestinationNotification',
    // <<Command>> お知らせを閲覧する
    viewMessageNotice: 'sys/portal/notice/viewMessageNotice'
  }

  @bean()
  export class ViewModel extends ko.ViewModel {
    isStartScreen: boolean = true;
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: moment.utc().format('YYYY/MM/DD'),
      endDate: moment.utc().format('YYYY/MM/DD')
    }));
    systemDate: KnockoutObservable<string> = ko.observable('');

    // Map<お知らせメッセージ、作成者> (List)
    msgNotices: KnockoutObservableArray<MsgNotices> = ko.observableArray([]);
    // Map<個人の記念日情報、新記念日Flag> (List)
    anniversaries: KnockoutObservableArray<AnniversaryNotices> = ko.observableArray([]);
    // ロール
    roleFlag: KnockoutObservable<boolean> = ko.observable(true);
    role: KnockoutObservable<Role> = ko.observable(new Role());

    created() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax(API.getEmployeeNotification).then((response: EmployeeNotification) => {
        if (response) {
          vm.anniversaries(response.anniversaryNotices);
          const msgNotices = _.map(response.msgNotices, x => {
            let msg = new MsgNotices();
            msg.creator = x.creator;
            msg.flag = x.flag;
            msg.message = x.message;
            msg.dateDisplay = vm.getDisplayDate(x.message.datePeriod)
            return msg;
          });
          vm.msgNotices(msgNotices);
          vm.role(response.role);
          vm.roleFlag(response.role.employeeReferenceRange !== 3);
          vm.systemDate(moment.utc(response.systemDate).format('YYYY/M/D(W)'))
        }
      })
      .fail(error => vm.$dialog.error(error))
      .always(() => vm.$blockui('hide'));
    }

    getDisplayDate(datePeriod: DatePeriod): string {
      const vm = this;
      return datePeriod ? datePeriod.startDate + vm.$i18n('CCG003_15') + datePeriod.endDate : '';
    }

    /**
     * A4_3:絞込をクリックする
     */
    onClickFilter(): void {
      const vm = this;
      const startDate = moment.utc(vm.dateValue().startDate, 'YYYY/MM/DD');
      const endDate = moment.utc(vm.dateValue().endDate, 'YYYY/MM/DD');
      const baseDate = moment.utc(new Date(), 'YYYY/MM/DD');
      if (startDate.isAfter(baseDate) || endDate.isAfter(baseDate)) {
        vm.$dialog.error('Msg_1833');
        return;
      }

      const param: DatePeriod = new DatePeriod({
        startDate: moment.utc(vm.dateValue().startDate).toISOString(),
        endDate: moment.utc(vm.dateValue().endDate).toISOString()
      });
      
      vm.$ajax(API.getContentOfDestinationNotification, param).then((response: DestinationNotification) => {
        if (response) {
          vm.anniversaries(response.anniversaryNotices);
          const msgNotices = _.map(response.msgNotices, x => {
            let msg = new MsgNotices();
            msg.creator = x.creator;
            msg.flag = x.flag;
            msg.message = x.message;
            msg.dateDisplay = vm.getDisplayDate(x.message.datePeriod)
            return msg;
          });
          vm.msgNotices(msgNotices);
        }
      })
      .fail(error => vm.$dialog.error(error))
      .always(() => vm.$blockui('hide'));
    }

    /**
     * A5、アコーディオンを広げて内容を表示する
     */
    onClickAnniversary(index: any): void {
      this.anniversaries()[index()].flag = false;
    }

    /**
     * A6、アコーディオンを広げて内容を表示する
     */
    onClickMessageNotice(creator: string, inputDate: string, flag: boolean, index: any): void {
      const vm = this;
      if (!flag) {
        return;
      }
      const command: any = {
        msgInfors: [{
          creatorID: creator,
          inputDate: inputDate
        }],
        sid: __viewContext.user.employeeId
      }
      vm.$blockui('show');
      vm.$ajax(API.viewMessageNotice, command).then(() => {
        vm.msgNotices()[index()].flag = false;
      })
      .fail(error => vm.$dialog.error(error))
      .always(() => vm.$blockui('hide'));
    }

    /**
     * A2:メッセージ入力のリンクをクリックする
     */
    openScreenB(): void {
      const vm = this;
      vm.$window.modal('/view/ccg/003/b/index.xhtml', vm.role().employeeReferenceRange);
    }

    closeWindow(): void {
      // ?????
    }
  }

  class DestinationNotification {
    msgNotices: MsgNotices[];
    anniversaryNotices: AnniversaryNotices[];
  }

  class DatePeriod {
    startDate: string;
    endDate: string;

    constructor(init?: Partial<DatePeriod>) {
      $.extend(this, init);
    }
  }

  class EmployeeNotification {
    msgNotices: MsgNotices[];
    anniversaryNotices: AnniversaryNotices[];
    role: Role;
    systemDate: string;
  }

  class MsgNotices {
    message: MessageNotice;
    creator: string;
    dateDisplay: string;
    flag: boolean;
  }

  class AnniversaryNotices {
    anniversaryNotice: AnniversaryNoticeImport;
    flag: boolean;
  }

  class AnniversaryNoticeImport {
	  personalId: string;
    noticeDay: number;
    seenDate: string;
    anniversary: string;
    anniversaryTitle: string;
    notificationMessage: string;
  }

  class Role {
    companyId: string;
    roleId: string;
    roleCode: string;
    roleName: string;
    assignAtr: number;
    employeeReferenceRange: number;
  }

  class MessageNotice {
    creatorID: string;
    inputDate: string;
    modifiedDate: string;
    targetInformation: any;
    datePeriod: any;
    employeeIdSeen: string[];
    notificationMessage: string;
  }

}