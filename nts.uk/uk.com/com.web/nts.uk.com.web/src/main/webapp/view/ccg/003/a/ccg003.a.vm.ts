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
    updateAnnivesaryNotice: 'ctx/bs/person/personal/anniversary/updateAnnivesaryNotice'
  };

  const urlRegex = /(((https?:\/\/)|(www\.))[^\s]+)/g;

  @component({
    name: 'ccg003-component',
    template: `<div class="contents">
    <!-- A0 お知らせ表示 -->
    <div id="A0-CCG003" class="panel panel-frame panel-ccg003">
      <div class="top-content">
        <!-- A1 対象日 -->
        <div id="A1"><span data-bind="text: systemDate" style="color: black !important;"></span>
        </div>
        <div class="fw-right">
          <div data-bind="if: roleFlag">
            <!-- A2 メッセージ入力 -->
            <a id="A2" class="ccg003-a2" class="mr-5" href="#" data-bind="click: openScreenB, text: $component.$i18n('CCG003_4')"></a>
          </div>
          <div>
            <!-- A3 ☓アイコン -->
            <i id="A3-CCG003" data-bind="ntsIcon: { no: 174, width: 25, height: 15 }, click: closeWindow"></i>
          </div>
        </div>
      </div>
      <!-- A4 絞り込み -->
      <div id="A4" class="w-490" data-bind="ntsAccordion: {}">
        <div id="top-title" class="bg-schedule-focus bg-accordion-1">
          <!-- ヘッダテキスト -->
          <h3 data-bind="text: $component.$i18n('CCG003_5')" class="inline"></h3>
        </div>
        <div id="body-title" class="bg-accordion-1 no-border-radius pl-10">
          <div class="row-inline" style="align-items: center;">
            <!-- A4_1 表示期間(ラベル) -->
            <span id="A4_1" class="auto-margin" data-bind="text: $component.$i18n('CCG003_6')"></span>
            <!-- A4_2 表示期間 -->
            <div id="daterangepicker" tabindex="1" class="ml-10" data-bind="ntsDateRangePicker: {
              required: false,
              enable: true,
              showNextPrevious: false,
              value: dateValue,
              maxRange: 'oneMonth'}"
            />
            <!-- A4_3 絞込 -->
            <button id="A4_3" tabindex="2" class="small pl-10 pr-10 ml-90" data-bind="click: onClickFilter, text: $component.$i18n('CCG003_7')"></button>
          </div>
        </div>
      </div>
      <div class="auto-overflow">
        <div data-bind="foreach: anniversaries">
          <!-- A5 記念日 -->
          <div class="w-490" data-bind="ntsAccordion: {}, click: $component.onClickAnniversary.bind($component, $index)">
            <div class="bg-schedule-focus">
              <!-- ヘッダテキスト -->
              <span class="limited-label-custom  mw-400" data-bind="text: anniversaryNotice.anniversaryTitle"></span>
              <!-- A5_1 NEWアイコン -->
              <span data-bind="if: $component.anniversaries()[$index()].flag">
                <i data-bind="ntsIcon: { no: 175, width: 25, height: 15 }"></i>
              </span>
            </div>
            <div class="mr-data no-border-radius">
              <!-- A5_2 記念日内容 -->
              <div>
                <span class="break-space" data-bind="text: anniversaryNotice.notificationMessage"></span>
                <span class="block-5" data-bind="text: $component.$i18n('CCG003_16', [anniversaryNotice.displayDate])"></span>
              </div>
            </div>
          </div>
        </div>
        <div data-bind="foreach: msgNotices">
          <!-- A6 メッセージ -->
          <div class="w-490" data-bind="ntsAccordion: {activate: $component.onClickMessageNotice.bind($component, message.creatorID, message.inputDate, $index)}">
            <h3 class="bg-schedule-focus">
              <!-- ヘッダテキスト -->
              <span class="limited-label-custom  mw-400" data-bind="text: message.notificationMessage"></span>
              <!-- A6_1 NEWアイコン -->
              <span data-bind="if: $component.msgNotices()[$index()].flag">
                <i data-bind="ntsIcon: { no: 175, width: 25, height: 15 }"></i>
              </span>
            </h3>
            <!-- A6_2 メッセージ内容 -->
            <div class="mr-data no-border-radius">
              <div>
                <span class="break-space" data-bind="html: messageDisplay"></span>
                <span class="block-5" data-bind="text: $component.$i18n('CCG003_8', [creator])"></span>
                <span class="block-5" data-bind="text: $component.$i18n('CCG003_9', [dateDisplay])"></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <style>
    #A0-CCG003 {
      min-height: 150px;
    }
    #A3-CCG003 {
      cursor: pointer;
    }
    #body-title {
      border-bottom: 0;
    }
    .ccg003-a2 {
      color: blue !important;
      text-decoration: underline;
    }
    .datepicker-container {
      z-index: 10000000 !important;
    }
    .mt-5 {
      margin-top: 5px;
    }
    .w-490 {
      width: 490px;
    }
    .img-close {
      cursor: pointer;
      width: 15px;
      height: 15px;
    }
    .top-content {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 10px;
    }
    .fw-right {
      display: flex;
      float: right;
      justify-content: end;
    }
    .panel-ccg003 {
      position: absolute;
      max-height: 460px;
      background-color: #f2f8ee !important;
      box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
      width: 510px;
      border-radius: 0px !important;
    }
    .row-inline {
      display: inline-flex;
    }
    .ml-20 {
      margin-left: 20px;
    }
    .bg-accordion-1 {
      background-color: #e1eed7 !important;
    }
    .ui-accordion > .ui-accordion-header {
      margin: 0 0 0 0 !important;
      border-radius: 0px !important;
    }
    .bg-accordion-data {
      background-color:#ffffcc !important;
    }
    .no-border-radius {
      border-radius: 0px !important;
    }
    .pl-10 {
      padding-left: 10px !important;
    }
    .pr-10 {
      padding-right: 10px !important;
    }
    .auto-margin {
      margin: auto;
    }
    .ml-10 {
      margin-left: 10px;
    }
    .ml-90 {
      margin-left: 90px;
    }
    .no-border-bottom {
      border-bottom: none;
    }
    .new-img {
      width: 20px;
      height: 10px;
      float: right;
    }
    .mw-400 {
      max-width: 400px;
    }
    .mr-5 {
      margin-right: 5px;
    }
    .auto-overflow {
      overflow-y: auto;
      max-height: 385px;
      -moz-transition: 0.5s;
      -ms-transition: 0.5s;
      -o-transition: 0.5s;
      -webkit-transition: 0.5s;
      transition: 0.5s;
    }
    .block-5 {
      display: block;
      margin-top: 5px;
    }
    .break-space {
      white-space: pre-wrap;
    }
    .limited-label-custom {
      width: 99%;
      display: inline-block;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .inline {
      display: inline;
    }
  </style>`
  })
  @bean()
  export class ViewModel extends ko.ViewModel {
    isStartScreen = true;
    formatType = 'YYYY/MM/DD';
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: moment.utc().format(this.formatType),
      endDate: moment.utc().format(this.formatType)
    }));
    systemDate: KnockoutObservable<string> = ko.observable('');

    // Map<お知らせメッセージ、作成者> (List)
    msgNotices: KnockoutObservableArray<MsgNotices> = ko.observableArray([]);
    // Map<個人の記念日情報、新記念日Flag> (List)
    anniversaries: KnockoutObservableArray<AnniversaryNotices> = ko.observableArray([]);
    // ロール
    roleFlag: KnockoutObservable<boolean> = ko.observable(false);
    role: KnockoutObservable<Role> = ko.observable(new Role());
    isShow: KnockoutObservable<boolean> = ko.observable(true);
    isEmployee: KnockoutComputed<boolean> = ko.computed(() => __viewContext.user.isEmployee);

    created() {
      const vm = this;
      if (!vm.isEmployee()) {
        return;
      }
      vm.$blockui('show');
      vm.$ajax('com', API.getEmployeeNotification)
        .then((response: EmployeeNotification) => {
          if (response) {
            vm.anniversaries(response.anniversaryNotices);
            const msgNotices = vm.listMsgNotice(response.msgNotices);
            vm.msgNotices(msgNotices);
            if (response.role) {
              vm.role(response.role);
              vm.roleFlag(response.role.employeeReferenceRange !== 3);
            }
            vm.systemDate(moment.utc(response.systemDate).locale('ja').format('YYYY/M/D(dd)'));
          }
        })
        .fail(error => vm.$dialog.error(error))
        .always(() => vm.$blockui('hide'));
    }

    mounted() {
      const vm = this;
      const elementId ='#notice-msg';
      const marginTop = $('#user').height() - $('#notice-msg').height();
      $('#A0-CCG003').ntsPopup({
        trigger: elementId,
        position: {
          my: 'right top',
          at: `right bottom-${marginTop}`,
          of: $('#user')
        },
        showOnStart: false,
        dismissible: false
      });

      $(elementId).click(() => {
        if (vm.isShow()) {
          $('#A0-CCG003').ntsPopup('show');
        } else {
          $('#A0-CCG003').ntsPopup('hide');
        }
        vm.isShow(!vm.isShow());
      });
      $('#top-title').dblclick(e => e.preventDefault());
      $('#top-title').click(() => {
        $('#top-title').css('border-bottom', 'none');
        const maxHeight = $('.auto-overflow').css('max-height');
        if (maxHeight === '320px') {
          $('.auto-overflow').css('max-height', '385px');
        } else {
          $('.auto-overflow').css('max-height', '320px');
        }

        if (!_.isEmpty(vm.anniversaries()) || !_.isEmpty(vm.msgNotices())) {
          $('#A4').css('border-bottom', 'unset');
        } else {
          $('#A4').css('border-bottom', '1px groove');
        }
      });
      $('#A0-CCG003').css('z-index', '9999999999');
    }

    /**
     * A4_3:絞込をクリックする
     */
    onClickFilter(): void {
      const vm = this;
      vm.$blockui('show');
      const startDate = moment.utc(vm.dateValue().startDate, vm.formatType);
      const endDate = moment.utc(vm.dateValue().endDate, vm.formatType);
      const baseDate = moment.utc(new Date(), vm.formatType);
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
            const msgNotices = vm.listMsgNotice(response.msgNotices);
            vm.msgNotices(msgNotices);

            if (!_.isEmpty(response.anniversaryNotices || !_.isEmpty(response.msgNotices))) {
              $('#A4').css('border-bottom', 'unset');
            }
          }
        })
        .fail(error => vm.$dialog.error(error))
        .always(() => vm.$blockui('hide'));
    }

    listMsgNotice(messages: any[]): MsgNotices[] {
      const vm = this;
      if (_.isEmpty(messages)) {
        return [];
      }
      return _.map(messages, (item: any) => {
        const msg = new MsgNotices();
        msg.creator = item.creator;
        msg.flag = item.flag;
        msg.message = item.message;
        msg.dateDisplay = item.message ? `${item.message.startDate} ${vm.$i18n('CCG003_15')} ${item.message.endDate}` : '';
        msg.messageDisplay = vm.replaceUrl(item.message.notificationMessage);
        return msg;
      });
    }

    replaceUrl(text: string): string {
      return text.replace(urlRegex, (url, b, c) => {
        const url2 = (c == 'www.') ? 'http://' + url : url;
        return '<a style="color: blue !important;" href="' + url2 + '" target="_blank">' + url + '</a>';
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
      const anniversary = vm.anniversaries()[index()].anniversaryNotice.displayDate;
      const command = {
        personalId: vm.anniversaries()[index()].anniversaryNotice.personalId,
        anniversary: moment.utc(anniversary, 'MM-DD').format('MMDD'),
        referDate: moment.utc(vm.dateValue().endDate).toISOString(),
      }
      vm.$blockui('show');
      vm.$ajax('com', API.updateAnnivesaryNotice, command)
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
      vm.$window.modal('com', '/view/ccg/003/b/index.xhtml', vm.role())
        .then(() => vm.onClickFilter());
    }

    closeWindow(): void {
      const vm = this;
      vm.isShow(!vm.isShow());
      $('#A0-CCG003').ntsPopup('hide');
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
    displayDate: string;
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
