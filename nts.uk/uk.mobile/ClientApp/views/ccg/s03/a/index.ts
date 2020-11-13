import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import * as _ from 'lodash';
import * as moment from 'moment';

@component({
  route: '/ccg/s03/a',
  style: require('./style.scss'),
  template: require('./index.vue'),
  validations: {},
  name: 'ccgs03a'
})
export class Ccgs03AComponent extends Vue {

  private API = {
    // <<ScreenQuery>> 社員のお知らせの画面を表示する
    getEmployeeNotification: 'sys/portal/notice/getEmployeeNotification',
    // <<ScreenQuery>> 社員が宛先のお知らせの内容を取得する
    getContentOfDestinationNotification: 'sys/portal/notice/getContentOfDestinationNotification',
    // <<Command>> お知らせを閲覧する
    viewMessageNotice: 'sys/portal/notice/viewMessageNotice',
    // <<Command>> 個人の記念日を閲覧する
    updateAnnivesartNotice: 'ctx/bs/person/personal/anniversary/updateAnnivesartNotice'
  };

  private urlRegex = /(((https?:\/\/)|(www\.))[^\s]+)/g;

  public dateValue: DatePeriod = new DatePeriod(
    moment.utc().format('YYYY/MM/DD'),
    moment.utc().format('YYYY/MM/DD')
  );

  public anniversaries: AnniversaryNotices[] = [];
  public msgNotices: MsgNotices[] = [];
  public role: Role = new Role();
  public systemDate: string = '';
  public roleFlag: boolean = true;

  public created() {
    this.$mask('show');
    this.$http.post('com', this.API.getEmployeeNotification)
      .then((response: EmployeeNotification) => {
        this.anniversaries = response.anniversaryNotices;
        const msgNotices = _.map(response.msgNotices, (x: any) => {
          let msg = new MsgNotices();
          msg.creator = x.creator;
          msg.flag = x.flag;
          msg.message = x.message;
          msg.dateDisplay = x.message ? x.message.startDate + ' ' + this.$i18n('CCG003_15') + ' ' + x.message.endDate : '';
          msg.messageDisplay = this.replaceUrl(x.message.notificationMessage);

          return msg;
        });
        this.msgNotices = msgNotices;
        this.role = response.role;
        this.roleFlag = response.role.employeeReferenceRange !== 3;
        this.systemDate = moment.utc(response.systemDate).locale('ja').format('YYYY/M/D(dddd)');
        this.$mask('hide');
      })
      .catch((error: any) => {
        this.$modal.error(error);
        this.$mask('hide');
      });
  }

  private replaceUrl(text: string): string {

    return text.replace(this.urlRegex, (url: any, b: any, c: any) => {
      const url2 = (c == 'www.') ? 'http://' + url : url;

      return '<a href="' + url2 + '" target="_blank">' + url + '</a>';
    });
  }

  public onClickFilter(): void {
    this.$mask('show');
    const startDate = moment.utc(this.dateValue.startDate, 'YYYY/MM/DD');
    const endDate = moment.utc(this.dateValue.endDate, 'YYYY/MM/DD');
    const baseDate = moment.utc().format('YYYY/MM/DD');
    if (startDate.isAfter(baseDate) || endDate.isAfter(baseDate)) {
      this.$modal.error({ messageId: 'Msg_1833' });
      this.$mask('hide');

      return;
    }

    const param: DatePeriod = new DatePeriod(
      moment.utc(this.dateValue.startDate).toISOString(),
      moment.utc(this.dateValue.endDate).toISOString()
    );
    this.$http.post(this.API.getContentOfDestinationNotification, param)
      .then((response: DestinationNotification) => {
        if (response) {
          this.anniversaries = response.anniversaryNotices;
          const msgNotices = _.map(response.msgNotices, (x: any) => {
            let msg = new MsgNotices();
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
      .catch((error: any) => {
        this.$modal.error(error);
        this.$mask('hide');
      });
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
    const command = {
      personalId: vm.anniversaries[index].anniversaryNotice.personalId,
      anniversary: vm.anniversaries[index].anniversaryNotice.anniversary,
      referDate: moment.utc(vm.dateValue.endDate).toISOString(),
    };
    vm.$mask('show');
    vm.$http.post(vm.API.updateAnnivesartNotice, command)
      .then(() => {
        const anniversaries = vm.anniversaries;
        anniversaries[index].flag = false;
        vm.anniversaries = anniversaries;
        vm.$mask('hide');
      })
      .catch((error: any) => {
        vm.$modal.error(error);
        vm.$mask('hide');
      });
  }

  /**
   * A6、アコーディオンを広げて内容を表示する
   */
  public onClickMessageNotice(creator: string, inputDate: string, index: any): void {
    const vm = this;
    if (!vm.msgNotices[index].flag) {

      return;
    }
    let employeeId = '';
    vm.$auth.user.then((usr: any) => {
      employeeId = usr.employeeId;
    });
    const command: any = {
      'msgInfors': [{
        'creatorId': creator,
        'inputDate': inputDate
      }],
      'sid': employeeId
    };
    vm.$mask('show');
    vm.$http.post(this.API.viewMessageNotice, command)
      .then(() => {
        const msgNotices = vm.msgNotices;
        msgNotices[index].flag = false;
        vm.msgNotices = msgNotices;
        vm.$mask('hide');
      })
      .catch((error: any) => {
        vm.$modal.error(error);
        vm.$mask('hide');
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
  anniversary: string;
  anniversaryTitle: string;
  notificationMessage: string;
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