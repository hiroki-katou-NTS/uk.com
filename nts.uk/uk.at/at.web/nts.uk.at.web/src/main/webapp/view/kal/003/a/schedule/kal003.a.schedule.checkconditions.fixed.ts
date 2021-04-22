/// <reference path='../../../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kal003.a.schedule.condition.viewmodel {

  @component({
    name: 'kal003-a-schedule-daily-fixed',
    template: `<div class="table">
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
                          <td class="col-message" data-bind="text: classification"></td>
                          <td class="col-message" data-bind="text: name">fsdf</td>
                          <td class="col-message" data-bind="text: message">fsd</td>                         
                        </tr>
                    </tbody>
                </table>
                </div>
              </div>`
  })

  class ScheduleDailyFixedTab extends ko.ViewModel {

    checkConditionList: KnockoutObservableArray<ClassificationItem> = ko.observableArray([]);
    userAtrCheck: KnockoutObservableArray<any> = ko.observableArray([]);
    dataGetShare: KnockoutObservable<any> = ko.observable({});

    created(params: any) {
      const vm = this;

      //get value from parent screen
      vm.dataGetShare().categoryId = params.tabScheduleFixedCheckConditions.categoryId;
      vm.dataGetShare().tabScheduleFixedCheckConditions = params.tabScheduleFixedCheckConditions;  

      vm.userAtrCheck = ko.observableArray([
        { code: '1', name: vm.$i18n('KAL003_189'), width: 60 },
        { code: '0', name: vm.$i18n('KAL003_190'), width: 60 }
      ]);

      //setting here
      for (let i = 0; i < vm.dataGetShare().categoryId + 1; i++) {
        let listItem: ClassificationItem = new ClassificationItem(false, i + 1, '区分 ' + vm.dataGetShare().categoryId, 'スケジュール月次 ' + i,null);
        listItem.checked.subscribe((value) => {
          vm.updateDataToSave();
        });
        vm.checkConditionList.push(listItem);
      }
      vm.updateDataToSave();
    }

    updateDataToSave() {
      const vm = this;
      vm.dataGetShare().tabScheduleFixedCheckConditions.dataToSave(vm.checkConditionList());
    }

    OpenSettingCheckCondition(rowId: number) {
      const vm = this;
      alert(rowId);
    }
  }

  export class ClassificationItem {
    checked: KnockoutObservable<boolean> = ko.observable(false);
    rowId: number;
    name: string;
    classification: string;
    message: string;

    constructor(checked: boolean, rowId: number, classification: string, name: string, message: string) {
      this.checked(checked);
      this.rowId = rowId;
      this.name = name;
      this.classification = classification;
      this.message = message;
    }
  }
}