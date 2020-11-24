/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ktg031.a {

  const API = {
    findBySystem: "sys/portal/pginfomation/findBySystem",
    updateLogSetting: "sys/portal/logsettings/update",
    // ...
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
                    <span class="limited-label" data-bind="text: message"></span>
                  </td>
                  <td class="column-action">
                    <div data-bind="ntsCheckBox: { checked: isReaded }"></div>
                  </td>
                  <td class="column-action">
                    <i class="img-icon" data-bind="ntsIcon: {no: 178, width: 20, height: 20}, click: function() { $component.openUrl(url); }"></i>
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
    selectedAlarmType: KnockoutObservable<number> = ko.observable(0);
    listAlarmType: KnockoutObservableArray<{ code: number, name: string }> = ko.observableArray([]);
    listAlarm: KnockoutObservableArray<AlarmDto> = ko.observableArray();
    $grid: JQuery;

    created(params: any) {
      const vm = this;
      vm.listAlarmType([
        { code: 0, name: vm.$i18n("未読のみ") },
        { code: 1, name: vm.$i18n("全て表示") },
      ]);
      vm.listAlarm([
        new AlarmDto({
          dateMonth: '7/21',
          message: 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttest',
          isReaded: true,
          url: 'https://www.google.com.vn/'
        }),
        new AlarmDto({
          dateMonth: '7/22',
          message: 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttest',
          isReaded: false,
          url: 'https://www.google.com.vn/'
        }),
        new AlarmDto({
          dateMonth: '7/21',
          message: 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttest',
          isReaded: true,
          url: 'https://www.google.com.vn/'
        }),
        new AlarmDto({
          dateMonth: '7/22',
          message: 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttest',
          isReaded: false,
          url: 'https://www.google.com.vn/'
        }),
        new AlarmDto({
          dateMonth: '7/21',
          message: 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttest',
          isReaded: true,
          url: 'https://www.google.com.vn/'
        }),
        new AlarmDto({
          dateMonth: '7/22',
          message: 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttest',
          isReaded: false,
          url: 'https://www.google.com.vn/'
        }),
        new AlarmDto({
          dateMonth: '7/21',
          message: 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttest',
          isReaded: true,
          url: 'https://www.google.com.vn/'
        }),
        new AlarmDto({
          dateMonth: '7/22',
          message: 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttest',
          isReaded: false,
          url: 'https://www.google.com.vn/'
        }),
      ]);
    }

    mounted() {
      const vm = this;
      vm.$grid = $('#ktg031-grid');
      $("#ktg031-grid tr:nth-child(even)").addClass("even");
      $("#ktg031-grid tr:nth-child(odd)").addClass("odd");
    }

    openDialogSetting() {
      // TODO
      console.log('setting');
    }

    openUrl(url: string) {
      // TODO
      console.log(url);
    }

  }

  class AlarmDto {
    id: string;
    dateMonth: string;
    message: string;
    isReaded: boolean;
    url: string;

    constructor(init?: Partial<AlarmDto>) {
      $.extend(this, init);
    }
  }
}