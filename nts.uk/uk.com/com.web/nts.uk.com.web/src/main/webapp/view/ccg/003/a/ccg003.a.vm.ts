/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.a {

  const API = {
    getEmployeeNotification: 'sys/portal/notice/getEmployeeNotification'
  }

  @bean()
  export class ViewModel extends ko.ViewModel {
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: '',
      endDate: ''
    }));

    // Map<お知らせメッセージ、作成者> (List)
    msgNotices: KnockoutObservableArray<MsgNotices> = ko.observableArray([]);
    // Map<個人の記念日情報、新記念日Flag> (List)
    anniversaries: KnockoutObservableArray<AnniversaryNotices> = ko.observableArray([]);
    // ロール
    roleFlag: KnockoutObservable<boolean> = ko.observable(true);
    role: KnockoutObservable<Role> = ko.observable(new Role());

    created() {
      this.onStartScreen();
    }

    onStartScreen(): void {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax(API.getEmployeeNotification).then((response: EmployeeNotification) => {
        if (response) {
          vm.anniversaries(response.anniversaryNotices);
          vm.msgNotices(response.msgNotices);
          vm.role(response.role);
          vm.roleFlag(response.role.employeeReferenceRange !== 3);
        }
      })
      .always(() => vm.$blockui('hide'));
    }

    openScreenB(): void {
      const vm = this;
      vm.$window.modal('/view/ccg/003/b/index.xhtml', vm.role().employeeReferenceRange);
    }

    seenMessage(index: number): void {
      const vm = this;
      const msg = vm.anniversaries()[index];
      if (msg && msg.flag()) {
        vm.msgNotices()[index].flag (false);
      }
    }

    closeWindow(): void {
      $('.contents').closest('iframe').attr('id');
    }
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
  }

  class MsgNotices {
    message: MessageNotice;
    creator: string;
    flag: KnockoutObservable<boolean>;
  }

  class AnniversaryNotices {
    anniversaryNotice: AnniversaryNoticeImport;
    flag: KnockoutObservable<boolean>;
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