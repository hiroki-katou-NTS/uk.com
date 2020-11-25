/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ktg031.a {

  const API = {
    findAlarmData: "sys/portal/toppageAlarm/findAlarmData",
    changeToRead: 'sys/portal/toppageAlarm/changeAlarmToReaded',
    changeToUnread: 'sys/portal/toppageAlarm/changeAlarmToUnread',
  }

  @component({
    name: 'ktg031-component',
    template: `
      <div id="ktg031-container">
        <div class="header-line"></div>
        <div class="body">
          <div class="body-top-row">
            <div data-bind="ntsComboBox: {
              name: '#[KTG031_10]',
              width: 150,
              value: $component.selectedAlarmType,
              options: $component.listAlarmType,
              optionsValue: 'code',
              optionsText: 'name',
              visibleItemsCount: 5,
              required: true,
              selectFirstIfNull: true,
              columns: [
                { prop: 'name', length: 10 },
              ]}"></div>
            <div class="body-top-label">
              <span class="label" data-bind="text: $component.$i18n('KTG031_11')"></span>
              <div data-bind="if: $component.isEmployee">
                <i class="img-icon" data-bind="ntsIcon: {no: 5, width: 30, height: 30}, click: $component.openDialogSetting"></i>
              </div>
            </div>
          </div>
          <div class="table-container">
            <table id="ktg031-grid">
              <tbody data-bind="foreach: $component.listAlarm">
                <tr>
                  <td class="column-date">
                    <span data-bind="text: dateMonth"></span>
                    <span data-bind="text: $component.$i18n('KTG031_13')"></span>
                  </td>
                  <td>
                    <span class="limited-label" data-bind="text: displayMessage"></span>
                  </td>
                  <td class="column-action">
                    <div data-bind="ntsCheckBox: { checked: isReaded }"></div>
                  </td>
                  <td class="column-action">
                    <i class="img-icon" data-bind="ntsIcon: {no: 178, width: 20, height: 20}, click: function() { $component.openUrl(linkUrl); }"></i>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <style type="text/css" rel="stylesheet">
        #ktg031-container {
          width: 100%;
          height: 290px;
          display: flex;
          flex-direction: column;
        }
        #ktg031-container .header-line {
          width: 100%;
          height: 5px;
          background-color: #689f38;
        }
        #ktg031-container .body {
          padding: 5px;
          box-sizing: border-box;
          width: 100%;
          height: calc(100% - 5px);
          display: flex;
          flex-direction: column;
        }
        #ktg031-container .body .body-top-row {
          display: flex;
          flex-direction: row;
          align-items: center;
        }
        #ktg031-container .body .body-top-row .body-top-label {
          width: 100%;
          padding-left: 10px;
          display: flex;
          flex-direction: row;
          align-items: center;
          justify-content: space-between;
        }
        #ktg031-container .body .body-top-row .body-top-label .label {
          font-size: 1.2rem;
        }
        #ktg031-container .body .body-top-row .body-top-label .img-icon:hover {
          cursor: pointer;
        }
        #ktg031-container .body .table-container {
          width: 100%;
          height: 100%;
          margin-top: 5px;
          overflow-y: auto;
        }
        #ktg031-container .body .table-container #ktg031-grid {
          width: 100%;
          table-layout: fixed;
        }
        #ktg031-container .body .table-container #ktg031-grid tr.even {
          background: #F2F2F2;
        }
        #ktg031-container .body .table-container #ktg031-grid td {
          padding: 3px;
          box-sizing: border-box;
        }
        #ktg031-container .body .table-container #ktg031-grid .column-date {
          width: 80px;
        }
        #ktg031-container .body .table-container #ktg031-grid .column-action {
          width: 50px;
          text-align: center;
        }
        #ktg031-container .body .table-container #ktg031-grid .img-icon:hover {
          cursor: pointer;
        }
      </style>
    `
  })
  export class Ktg031ComponentViewModel extends ko.ViewModel {
    isEmployee: KnockoutObservable<boolean> = ko.observable(false);
    selectedAlarmType: KnockoutObservable<number> = ko.observable(null);
    listAlarmType: KnockoutObservableArray<{ code: number, name: string }> = ko.observableArray([]);
    listAlarm: KnockoutObservableArray<AlarmDisplayDataDto> = ko.observableArray();
    $grid: JQuery;

    created(params: any) {
      const vm = this;
      vm.listAlarmType([
        { code: 0, name: vm.$i18n('Enum_ToppageAlarmDisplay_UNREAD') },
        { code: 1, name: vm.$i18n('Enum_ToppageAlarmDisplay_ALL') },
      ]);
      vm.selectedAlarmType.subscribe(value => vm.loadAlarmData(value));
      vm.selectedAlarmType(0);
    }

    loadAlarmData(displayType: number) {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(`${API.findAlarmData}/${displayType}`)
        .then((res: any[]) => {
          vm.listAlarm(_.map(res, (item) => new AlarmDisplayDataDto(item)));
          // Render row backgournd color
          vm.$nextTick(() => {
            vm.$grid = $('#ktg031-grid');
            $("#ktg031-grid tr:nth-child(even)").addClass("even");
            $("#ktg031-grid tr:nth-child(odd)").addClass("odd");
          });
        })
        .always(() => vm.$blockui('clear'));
    }

    changeToRead(companyId: string, sId: string, displayAtr: number, alarmClassification: number, identificationKey: string) {
      const vm = this;
      const command = new ToppageAlarmDataReadCommand({
        companyId: companyId,
        sId: sId,
        displayAtr: displayAtr,
        alarmClassification: alarmClassification,
        identificationKey: identificationKey,
      });
      vm.$blockui('grayout');
      vm.$ajax(API.changeToRead, command)
        .then((res) => {
          console.log(res);
        })
        .always(() => vm.$blockui('clear'));
    }

    changeToUnread(companyId: string, sId: string, displayAtr: number, alarmClassification: number, identificationKey: string) {
      const vm = this;
      const command = new ToppageAlarmDataUnreadCommand({
        companyId: companyId,
        sId: sId,
        displayAtr: displayAtr,
        alarmClassification: alarmClassification,
        identificationKey: identificationKey,
      });
      vm.$blockui('grayout');
      vm.$ajax(API.changeToUnread, command)
        .then((res) => {
          console.log(res);
        })
        .always(() => vm.$blockui('clear'));
    }

    openDialogSetting() {
      // TODO
      console.log('setting');
    }

    openUrl(url: string) {
      if (url) {
        window.open(url, "_blank");
      }
    }

  }

  class AlarmDisplayDataDto {
    alarmClassification: number;
    occurrenceDateTime: string;
    displayMessage: string;
    companyId: string;
    sId: string;
    displayAtr: number;
    identificationKey: string;
    linkUrl: string;
    alreadyDatetime: string;
    // Client info
    dateMonth: string;
    isReaded: boolean;

    constructor(init?: Partial<AlarmDisplayDataDto>) {
      $.extend(this, init);
      const occurrenceDateTime = moment.utc(this.occurrenceDateTime);
      this.dateMonth = occurrenceDateTime.format('MM/DD');
      let isReaded = false;
      if (this.alreadyDatetime) {
        const alreadyDatetime = moment.utc(this.alreadyDatetime);
        isReaded = occurrenceDateTime.isBefore(alreadyDatetime);
      }
      this.isReaded = isReaded;
    }
  }

  class ToppageAlarmDataReadCommand {
    companyId: string;
    sId: string;
    displayAtr: number;
    alarmClassification: number;
    identificationKey: string;

    constructor(init?: Partial<ToppageAlarmDataReadCommand>) {
      $.extend(this, init);
    }
  }

  class ToppageAlarmDataUnreadCommand {
    companyId: string;
    sId: string;
    displayAtr: number;
    alarmClassification: number;
    identificationKey: string;

    constructor(init?: Partial<ToppageAlarmDataUnreadCommand>) {
      $.extend(this, init);
    }
  }
}