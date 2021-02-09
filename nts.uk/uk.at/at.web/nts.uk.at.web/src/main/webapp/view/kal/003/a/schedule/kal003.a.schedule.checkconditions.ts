/// <reference path='../../../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kal003.a.schedule.condition.viewmodel {

  import windows = nts.uk.ui.windows;

  @component({
    name: 'kal003-a-schedule-daily',
    template: `<div class="table table-buttons">
                <div class="cell">
                  <button data-bind="text: $vm.$i18n('KAL003_21'), click: AddNewConditionItem"></button>
                </div>
                <div class="cell">
                  <button data-bind="text: $vm.$i18n('KAL003_22'), enable: isAllowRemove,,click: DeleteConditionItem"></button>
                </div>
              </div>
              <div class="table">
                <div class="cell ui-iggrid">     
                  <table id="fixed-table" class="fix-table">     
                    <thead>
                      <tr>
                        <th class="ui-widget-header col-checkbox halign-center"><div data-bind="ntsCheckBox: { checked: checkAll}"></div></th>
                        <th class="ui-widget-header col-num halign-center" data-bind="text: $vm.$i18n('KAL003_24')"></th>
                        <th class="ui-widget-header col-name" data-bind="text: $vm.$i18n('KAL003_12')"></th>
                        <th class="ui-widget-header" data-bind="text: $vm.$i18n('KAL003_23')"></th>
                        <th class="ui-widget-header col-message" data-bind="text: $vm.$i18n('KAL003_25')"></th>
                        <th class="ui-widget-header col-use" data-bind="text: $vm.$i18n('KAL003_188')"></th>
                      </tr>
                    </thead>
                    <tbody data-bind="foreach: checkConditionList">          
                        <tr>
                          <td class="col-checkbox halign-center"><div data-bind="ntsCheckBox: {checked: checked}"></div></td>
                          <td class="col-num halign-center" data-bind="text: ko.observable(rowId)"></td>
                          <td class="col-name"><input data-bind="ntsTextEditor: {value: name, 
                            option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                              width: '160px'
                            }))
                          }" /></td>
                          <td class="halign-center"><button data-bind="text: $vm.$i18n('KAL003_14'), 
                                click: function(){ $parent.OpenSettingCheckCondition(rowId) }"></button></td>
                          <td class="col-message" data-bind="text: message"></td>
                          <td class="col-use">
                            <div class="cf" data-bind="ntsSwitchButton: {
                              name: '#[KAL003_188]',
                              options: $parent.userAtrCheck,
                              optionsValue: 'code',
                              optionsText: 'name',
                              value: useAtr,
                              required: true,
                              enable: true }"></div>
                          </td>
                        </tr>
                    </tbody>
                </table>
                </div>
              </div>`
  })

  class ScheduleDailyTab extends ko.ViewModel {
    isNew: ko.KnockoutObservable<boolean> = ko.observable(true);
    checkAll: ko.KnockoutObservable<boolean> = ko.observable(false);
    isAllowRemove: ko.KnockoutObservable<boolean> = ko.observable(false);
    checkConditionList: KnockoutObservableArray<ListItem> = ko.observableArray([]);
    userAtrCheck: KnockoutObservableArray<any> = ko.observableArray([]);

    dataGetShare: KnockoutObservable<any> = ko.observable(null);

    created(params: any) {
      const vm = this;

      vm.userAtrCheck = ko.observableArray([
        { code: '1', name: vm.$i18n('KAL003_189'), width: 60 },
        { code: '0', name: vm.$i18n('KAL003_190'), width: 60 }
      ]);

      //get value from parent screen
      vm.dataGetShare = params.dataSetShare;

      vm.checkConditionList.subscribe((newList) => {           
        if (!newList || newList.length <= 0) {
          vm.checkAll(false);
          return;
        }

        let isSelectedAll: any = vm.checkConditionList().every(item => item.checked() === true);
        //there is least one item which is not checked
        if (isSelectedAll === false) isSelectedAll = null;
        vm.checkAll(isSelectedAll);

      });

      vm.checkAll.subscribe((value) => {
        if (value === null) return;
        vm.SelectedCheckAll(value);
      });
    }

    OpenSettingCheckCondition(rowId: number) {
      const vm = this;

      vm.dataGetShare().dataToSave(vm.checkConditionList());
      
     /*
      let sendData = {
        category: vm.dataGetShare.category,
        data: []
      };
      windows.setShared('inputKal003b', sendData);
      windows.sub.modal('/view/kal/003/b/index.xhtml', { height: 600, width: 1020 }).onClosed(function (): any {
        // get data from share window    
        let data = windows.getShared('outputKal003b');
        if (data != null && data != undefined) {
        }
      });
      */
    }

    AddNewConditionItem() {
      const vm = this;

      nts.uk.ui.errors.clearAll();

      let order = vm.checkConditionList().length + 1;
      let conditionItem = new ListItem(false, order, null, [], null, false);
      conditionItem.checked.subscribe((value) => {
        vm.isAllowRemove(_.some(vm.checkConditionList(), (x) => x.checked() === true));  
        if( value === false) vm.checkAll(null); 
        else vm.checkConditionList.valueHasMutated();
      });
      vm.checkConditionList.push(conditionItem);
    }

    DeleteConditionItem() {
      const vm = this;
      vm.$dialog.confirm({ messageId: 'Msg_16' }).then((result: string) => {
        if (result === 'yes') {
          vm.RemoveConditionItems();
        }
      })
    }

    RemoveConditionItems() {
      const vm = this;

      let conditionItems = _.filter(vm.checkConditionList(), (x) => x.checked() !== true);
      //reorder
      _.forEach(conditionItems, (x, index) => {
        conditionItems[index].rowId = index + 1;
      });
      vm.checkConditionList.removeAll();
      vm.checkConditionList(conditionItems);
    }

    SelectedCheckAll(value: boolean) {
      const vm = this;
      _.forEach(vm.checkConditionList(), (item, index) => { item.checked(value); });
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