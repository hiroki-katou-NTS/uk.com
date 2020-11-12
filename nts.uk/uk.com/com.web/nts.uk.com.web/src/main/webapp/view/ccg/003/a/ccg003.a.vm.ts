/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.a {

  const API = {
    // <<ScreenQuery>> 社員のお知らせの画面を表示する
    getEmployeeNotification: 'sys/portal/notice/getEmployeeNotification',
    // <<ScreenQuery>> 社員が宛先のお知らせの内容を取得する
    getContentOfDestinationNotification: 'sys/portal/notice/getContentOfDestinationNotification',
    // <<Command>> お知らせを閲覧する
    viewMessageNotice: 'sys/portal/notice/viewMessageNotice',
    // <<Command>> 個人の記念日を閲覧する
    updateAnnivesartNotice: 'ctx/bs/person/personal/anniversary/updateAnnivesartNotice'
  }

  const urlRegex = /(((https?:\/\/)|(www\.))[^\s]+)/g;

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
      vm.$blockui('grayout');
      vm.initPopup();
      vm.$ajax('com', API.getEmployeeNotification).then((response: EmployeeNotification) => {
        if (response) {
          vm.anniversaries(response.anniversaryNotices);
          const msgNotices = _.map(response.msgNotices, x => {
            let msg = new MsgNotices();
            msg.creator = x.creator;
            msg.flag = x.flag;
            msg.message = x.message;
            msg.dateDisplay = x.message ? x.message.startDate + ' ' + vm.$i18n('CCG003_15') + ' ' + x.message.endDate : '';
            msg.messageDisplay = vm.replaceUrl(x.message.notificationMessage);
            return msg;
          });
          vm.msgNotices(msgNotices);
          vm.role(response.role);
          vm.roleFlag(response.role.employeeReferenceRange !== 3);
          vm.systemDate(moment.utc(response.systemDate).locale('ja').format('YYYY/M/D(dddd)'));
        }
      })
        .fail(error => vm.$dialog.error(error))
        .always(() => vm.$blockui('hide'));
    }

    initPopup(): void {
      $('#A0').ntsPopup({
        trigger: '#show-ccg003',
        position: {
          my: 'left top',
          at: 'left bottom',
          of: '#show-ccg003'
        },
        showOnStart: false,
        dismissible: true
      });

      $('#show-ccg003').click(() => {
        $('#A0').ntsPopup('show');
      });
    }

    /**
     * A4_3:絞込をクリックする
     */
    onClickFilter(): void {
      const vm = this;
      vm.$blockui('grayout');
      const startDate = moment.utc(vm.dateValue().startDate, 'YYYY/MM/DD');
      const endDate = moment.utc(vm.dateValue().endDate, 'YYYY/MM/DD');
      const baseDate = moment.utc(new Date(), 'YYYY/MM/DD');
      if (startDate.isAfter(baseDate) || endDate.isAfter(baseDate)) {
        vm.$dialog.error({ messageId: 'Msg_1833' });
        vm.$blockui('hide');
        return;
      }
      vm.anniversaries([]);
      vm.msgNotices([]);

      const param: DatePeriod = new DatePeriod({
        startDate: startDate.toISOString(),
        endDate: endDate.toISOString()
      });
      vm.$ajax('com', API.getContentOfDestinationNotification, param)
        .then((response: DestinationNotification) => {
          if (response) {
            vm.anniversaries(response.anniversaryNotices);
            const msgNotices = _.map(response.msgNotices, x => {
              let msg = new MsgNotices();
              msg.creator = x.creator;
              msg.flag = x.flag;
              msg.message = x.message;
              msg.dateDisplay = x.message ? x.message.startDate + ' ' + vm.$i18n('CCG003_15') + ' ' + x.message.endDate : '';
              msg.messageDisplay = vm.replaceUrl(x.message.notificationMessage);
              return msg;
            });
            vm.msgNotices(msgNotices);
          }
        })
        .fail(error => vm.$dialog.error(error))
        .always(() => vm.$blockui('hide'));
    }

    replaceUrl(text: string): string {
      return text.replace(urlRegex, (url, b, c) => {
        const url2 = (c == 'www.') ? 'http://' + url : url;
        return '<a href="' + url2 + '" target="_blank">' + url + '</a>';
      });
    }

    /**
     * A5、アコーディオンを広げて内容を表示する
     */
    onClickAnniversary(index: any): void {
      const vm = this;
      if (_.isNil(vm.anniversaries()[index()])) {
        return;
      }
      if (!vm.anniversaries()[index()].flag) {
        return;
      }
      const command = {
        personalId: vm.anniversaries()[index()].anniversaryNotice.personalId,
        anniversary: vm.anniversaries()[index()].anniversaryNotice.anniversary,
        referDate: moment.utc(vm.dateValue().endDate).toISOString(),
      }
      vm.$blockui('show');
      vm.$ajax('com', API.updateAnnivesartNotice, command)
        .then(() => {
          const anniversaries = vm.anniversaries();
          anniversaries[index()].flag = false;
          vm.anniversaries(anniversaries);
        })
        .fail(error => vm.$dialog.error(error))
        .always(() => vm.$blockui('hide'));
    }

    /**
     * A6、アコーディオンを広げて内容を表示する
     */
    onClickMessageNotice(creator: string, inputDate: string, index: any): void {
      const vm = this;
      if (!vm.msgNotices()[index()].flag) {
        return;
      }
      const command: any = {
        msgInfors: [{
          creatorId: creator,
          inputDate: inputDate
        }],
        sid: __viewContext.user.employeeId
      }
      vm.$blockui('show');
      vm.$ajax('com', API.viewMessageNotice, command)
        .then(() => {
          const msgNotices = vm.msgNotices();
          msgNotices[index()].flag = false;
          vm.msgNotices(msgNotices);
        })
        .fail(error => vm.$dialog.error(error))
        .always(() => vm.$blockui('hide'));
    }

    /**
     * A2:メッセージ入力のリンクをクリックする
     */
    openScreenB(): void {
      const vm = this;
      vm.$window.modal('/view/ccg/003/b/index.xhtml', vm.role().employeeReferenceRange)
        .then(() => vm.onClickFilter());
    }

    closeWindow(): void {
      $('#A0').ntsPopup('hide');
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
    messageDisplay: string;
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
    startDate: any;
    endDate: any;
    employeeIdSeen: string[];
    notificationMessage: string;
  }

}
