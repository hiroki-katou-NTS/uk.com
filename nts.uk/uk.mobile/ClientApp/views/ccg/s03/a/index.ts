import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import * as _ from 'lodash';
import * as moment from 'moment';

const API = {
  // <<ScreenQuery>> 社員のお知らせの画面を表示する
  getEmployeeNotification: 'sys/portal/notice/getEmployeeNotification',
  // <<ScreenQuery>> 社員が宛先のお知らせの内容を取得する
  getContentOfDestinationNotification: 'sys/portal/notice/getContentOfDestinationNotification',
  // <<Command>> お知らせを閲覧する
  viewMessageNotice: 'sys/portal/notice/viewMessageNotice',
  // <<Command>> 個人の記念日を閲覧する
  updateAnnivesaryNotice: 'ctx/bs/person/personal/anniversary/updateAnnivesaryNotice'
};

const URL_REGX = /(((https?:\/\/)|(www\.))[^\s]+)/g;

@component({
  name: 'ccgs03a',
  route: '/ccg/s03/a',
  style: require('./style.scss'),
  template: require('./index.vue'),
  validations: {}
})
export class Ccgs03AComponent extends Vue {

  public iconNew = 'data:image/gif;base64,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'
    + '6baxkJ0vZyvogAgA7';

  public dateValue: { start?: Date; end?: Date } = {
    start: moment.utc().toDate(),
    end: moment.utc().toDate()
  };

  public anniversaries: AnniversaryNotices[] = [];
  public msgNotices: MsgNotices[] = [];
  public role: Role = new Role();
  public systemDate: string = '';
  public roleFlag: boolean = true;

  public created() {
    const vm = this;

    vm.$mask('show');

    vm.$auth
      .token
      .then((tk: string | null) => {
        if (!tk) {
          vm.$close();
          
          return null;
        }

        return vm.$http.post('com', API.getEmployeeNotification);
      })
      .then((response: any) => {
        if (response && response.data) {
          const employeeNotification: EmployeeNotification = response.data;
          this.anniversaries = employeeNotification.anniversaryNotices;
          const msgNotices = _.map(employeeNotification.msgNotices, (x: any) => {
            const msg = new MsgNotices();
            msg.creator = x.creator;
            msg.flag = x.flag;
            msg.message = x.message;
            msg.dateDisplay = x.message ? x.message.startDate + ' ' + this.$i18n('CCG003_15') + ' ' + x.message.endDate : '';
            msg.messageDisplay = this.replaceUrl(x.message.notificationMessage);

            return msg;
          });

          this.msgNotices = msgNotices;
          this.role = employeeNotification.role;
          this.roleFlag = !!this.role && employeeNotification.role.employeeReferenceRange !== 3;
          this.systemDate = moment.utc(employeeNotification.systemDate).locale('ja').format('YYYY/M/D(dd)');
          this.$mask('hide');
        }
      })
      .catch((error: any) => {
        this.$modal.error(error);
        this.$mask('hide');
      });
  }

  public clickTitle(type: string, index: number) {
    let element = this.$refs[type][index];
    const elementClass: DOMTokenList = element.classList;
    if (elementClass.contains('show')) {
      element.classList.remove('show');

      return;
    }
    element.classList.add('show');
  }

  private replaceUrl(text: string): string {
    return text.replace(URL_REGX, (url: any, b: any, c: any) => {
      const url2 = (c == 'www.') ? 'http://' + url : url;

      return '<a href="' + url2 + '" target="_blank">' + url + '</a>';
    });
  }

  public onClickFilter(): void {
    this.$mask('show');

    this.msgNotices = [];
    this.anniversaries = [];
    const startDate = moment.utc(this.dateValue.start, 'YYYY/MM/DD');
    const endDate = moment.utc(this.dateValue.end, 'YYYY/MM/DD');
    const baseDate = moment.utc(new Date(), 'YYYY/MM/DD');

    if (startDate.isAfter(baseDate) || endDate.isAfter(baseDate)) {
      this.$modal.error({
        messageId: 'Msg_1833'
      })
        .then(() => this.$mask('hide'));

      return;
    }

    if (startDate.isAfter(endDate)) {
      this.$modal.error({
        messageId: 'FND_E_SPAN_REVERSED',
        messageParams: [this.$i18n('CCGS03_6').toString()]
      })
        .then(() => this.$mask('hide'));

      return;
    }

    if (startDate.add(1, 'M').isBefore(endDate)) {
      this.$modal.error({
        messageId: 'FND_E_SPAN_OVER_MONTH',
        messageParams: [this.$i18n('CCGS03_6').toString()]
      })
        .then(() => this.$mask('hide'));

      return;
    }

    const param: DatePeriod = new DatePeriod(
      moment.utc(this.dateValue.start).toISOString(),
      moment.utc(this.dateValue.end).toISOString()
    );

    this.$http
      .post(API.getContentOfDestinationNotification, param)
      .then((response: any) => {
        if (response && response.data) {
          const destinationNotification: DestinationNotification = response.data;
          this.anniversaries = destinationNotification.anniversaryNotices;
          const msgNotices = _.map(destinationNotification.msgNotices, (x: any) => {
            const msg = new MsgNotices();
            msg.creator = x.creator;
            msg.flag = x.flag;
            msg.message = x.message;
            msg.dateDisplay = x.message ? x.message.startDate + ' ' + this.$i18n('CCG003_15') + ' ' + x.message.endDate : '';
            msg.messageDisplay = this.replaceUrl(x.message.notificationMessage);

            return msg;
          });
          this.msgNotices = msgNotices;
          this.$mask('hide');
        }
      })
      .catch((error: any) => this.$modal.error(error).then(() => this.$mask('hide')));
  }

  /**
   * A5、アコーディオンを広げて内容を表示する
   */
  public onClickAnniversary(index: any): void {
    const vm = this;
    if (_.isNil(vm.anniversaries[index])) {

      return;
    }

    if (!vm.anniversaries[index].flag) {

      return;
    }
    const anniversary = vm.anniversaries[index].anniversaryNotice.displayDate;
    const command = {
      personalId: vm.anniversaries[index].anniversaryNotice.personalId,
      anniversary: moment.utc(anniversary, 'MM-DD').format('MMDD'),
      referDate: moment.utc(vm.dateValue.end).toISOString(),
    };
    vm.$http.post(API.updateAnnivesaryNotice, command)
      .then(() => {
        vm.anniversaries[index].flag = false;
      })
      .catch((error: any) => {
        vm.$modal.error(error);
      });
  }

  /**
   * A6、アコーディオンを広げて内容を表示する
   */
  public async onClickMessageNotice(creator: string, date: string, index: any): Promise<void> {
    const vm = this;
    if (!vm.msgNotices[index].flag) {

      return;
    }
    let employeeId = '';
    await vm.$auth.user.then((usr: any) => {
      employeeId = usr.employeeId;
    });
    const command = {
      msgInfors: [{
        creatorId: creator,
        inputDate: date
      }],
      sid: employeeId
    };
    vm.$http.post(API.viewMessageNotice, command)
      .then(() => {
        vm.msgNotices[index].flag = false;
      })
      .catch((error: any) => {
        vm.$modal.error(error);
      });
  }

}

interface EmployeeNotification {
  msgNotices: MsgNotices[];
  anniversaryNotices: AnniversaryNotices[];
  role: Role;
  systemDate: string;
}

interface DestinationNotification {
  msgNotices: MsgNotices[];
  anniversaryNotices: AnniversaryNotices[];
}

interface AnniversaryNotices {
  anniversaryNotice: AnniversaryNotice;
  flag: boolean;
}

interface AnniversaryNotice {
  personalId: string;
  noticeDay: number;
  seenDate: string;
  anniversaryMonth: number;
  anniversaryDay: number;
  anniversaryTitle: string;
  notificationMessage: string;
  displayDate: string;
}

class MsgNotices {
  public message: MessageNotice;
  public creator: string;
  public dateDisplay: string;
  public flag: boolean;
  public messageDisplay: string;
}

class MessageNotice {
  public creatorID: string;
  public inputDate: string;
  public modifiedDate: string;
  public targetInformation: any;
  public startDate: any;
  public endDate: any;
  public employeeIdSeen: string[];
  public notificationMessage: string;
}

class Role {
  public companyId: string;
  public roleId: string;
  public roleCode: string;
  public roleName: string;
  public assignAtr: number;
  public employeeReferenceRange: number;
}

class DatePeriod {
  public startDate: string;
  public endDate: string;

  constructor(startDate: string, endDate: string) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

}