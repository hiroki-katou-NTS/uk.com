/// <reference path='../../../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kal003.a.schedule.condition.viewmodel {

  @component({
    name: 'kal003-a-schedule-daily-fixed',
    template: `<div class="table">
                <div class="cell">
                  <button data-bind="text: $vm.$i18n('KAL003_21')"></button>
                </div>
                <div class="cell">
                  <button data-bind="text: $vm.$i18n('KAL003_22')"></button>
                </div>
              </div>
              <div class="table">
                <div class="cell ui-iggrid">     
                  <table id="fixed-table" class="fix-table">     
                    <thead>
                      <tr>
                        <th class="ui-widget-header col-checkbox"data-bind="text: $vm.$i18n('KAL003_153')"></th>
                        <th class="ui-widget-header col-num" data-bind="text: $vm.$i18n('KAL003_24')"></th>
                        <th class="ui-widget-header col-num" data-bind="text: $vm.$i18n('KAL003_53')"></th>
                        <th class="ui-widget-header" data-bind="text: $vm.$i18n('KAL003_54')"></th>
                        <th class="ui-widget-header col-message" data-bind="text: $vm.$i18n('KAL003_155')"></th>
                      </tr>
                    </thead>
                    <tbody data-bind="foreach: checkConditionList">          
                        <tr>
                          <td class="col-checkbox halign-center"><div data-bind="ntsCheckBox: {checked: checked}"></div></td>
                          <td class="col-num" data-bind="text: ko.observable(rowId)"></td>
                          <td class="col-num" data-bind="text: name"></td>
                          <td class="col-message" data-bind="text: name">fsdf</td>
                          <td class="col-message" data-bind="text: name">fsd</td>                         
                        </tr>
                    </tbody>
                </table>
                </div>
              </div>`
  })

  class ScheduleDailyFixedTab extends ko.ViewModel {

    checkConditionList: KnockoutObservableArray<ListItem> = ko.observableArray([]);
    userAtrCheck: KnockoutObservableArray<any> = ko.observableArray([]);

    created(params: any) {
      const vm = this;
      vm.userAtrCheck = ko.observableArray([
        { code: '1', name: vm.$i18n('KAL003_189'), width: 60 },
        { code: '0', name: vm.$i18n('KAL003_190'), width: 60 }
      ]);

      //setting here
      for (let i = 0; i < 10; i++) {
        vm.checkConditionList.push(new ListItem(false, i + 1, 'Test ' + i, [], 'Message ' + i, false));
      }
    
    }

    OpenSettingCheckCondition(rowId: number) {
      const vm = this;
      alert(rowId);
    }
  }

  export class ListItem {
    checked: KnockoutObservable<boolean> = ko.observable(false);
    rowId: number;
    name: KnockoutObservable<string> = ko.observable(null);
    attributes: KnockoutObservable<any> = ko.observable(null);
    message: KnockoutObservable<string> = ko.observable(null);
    useAtr: KnockoutObservable<boolean> = ko.observable(false);

    constructor(checked: boolean, rowId: number, name: string, attributes: Array<any>, message: string, use: boolean) {
      this.checked(checked);
      this.rowId = rowId;
      this.name(name);
      this.attributes(attributes);
      this.message(message);
      this.useAtr(use);
    }

  }
}