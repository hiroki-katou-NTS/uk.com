/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.oew001.b {
  import model = oew001.share.model;

  const API = {
    getEmployeeList: "com/screen/oew001/getEmployeeList",
    insert: "ctx/office/equipment/data/insert",
    update: "ctx/office/equipment/data/update",
    delete: "ctx/office/equipment/data/delete",
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {

    data: KnockoutObservable<model.Oew001BData> = ko.observable(null);
    employeeList: KnockoutObservableArray<model.EmployeeInfoDto> = ko.observableArray([]);

    created(param: any) {
      const vm = this;
      vm.data(new model.Oew001BData(param));
      vm.data().employeeName = ko.observable(param.employeeName);
      vm.data().useDate = ko.observable(param.useDate);
      _.forEach(param.optionalItems, data => data.value = ko.observable(data.value));
      vm.data().optionalItems = ko.observableArray(param.optionalItems);
      vm.data.valueHasMutated();
    }

    mounted() {
      const vm = this;
      vm.$blockui("grayout");
      vm.getEmployeeList().then(() => {
        vm.data().employeeName(_.find(vm.employeeList(), { "employeeId": vm.data().sid }).businessName);
        vm.data.valueHasMutated();
      })
      .always(() => vm.$blockui("clear"));
    }

    private getEmployeeList(): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(API.getEmployeeList).then(result => vm.employeeList(result));
    }

    /**
     * Ｂ：利用実績の新規登録をする
     */
    private insert(param: model.EquipmentDataDto): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(API.insert, param)
        .then(() => {
          return vm.$dialog.info({ messageId: "Msg_15" });
        })
        .fail(err => {
          vm.$dialog.error(err.messageId);
        });
    }

    /**
     * Ｂ：利用実績の更新をする
     */
    private update(param: model.EquipmentDataDto): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(API.update, param)
      .then(() => {
        return vm.$dialog.info({ messageId: "Msg_15" });
      })
      .fail(err => vm.$dialog.error(err.messageId));
    }

    /**
     * Ｂ：利用実績の削除をする
     */
    private delete(param: any): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(API.delete, param)
      .then(() => {
        return vm.$dialog.info({ messageId: "Msg_16" });
      })
      .fail(err => vm.$dialog.error(err.messageId));
    }

    public processSave() {
      const vm = this;
      vm.$blockui("grayout");
      const input = vm.data();
      const itemDatas = _.map(input.optionalItems(), data => new model.ItemDataDto({
        itemNo: data.itemNo,
        itemClassification: data.itemCls,
        actualValue: data.value()
      }));
      const param = new model.EquipmentDataDto({
        equipmentClassificationCode: input.equipmentClsCode,
        equipmentCode: input.equipmentInfoCode,
        inputDate: moment.utc().toISOString(),
        useDate: moment.utc(input.useDate(), model.constants.YYYY_MM_DD).toISOString(),
        sid: input.sid,
        itemDatas: itemDatas
      });
      let call = vm.data().isNewMode ? vm.insert(param) : vm.update(param);
      call.then(() => vm.$window.close({
        isSaveSuccess: true
      })).always(() => vm.$blockui("clear"));
    }

    public processDelete() {
      const vm = this;
      vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: "yes" | "no") => {
        if (result === "yes") {
          vm.$blockui("grayout");
          const input = vm.data();
          const param = {
            equipmentCode: input.equipmentInfoCode,
            inputDate: moment.utc(input.inputDate, model.constants.YYYY_MM_DD).toISOString(),
            useDate: moment.utc(input.useDate(), model.constants.YYYY_MM_DD).toISOString(),
          };
          vm.delete(param).then(() => vm.$window.close({
            isDeleteSuccess: true
          })).always(() => vm.$blockui("clear"));
        }
      })
    }

    public processCancel() {
      const vm = this;
      vm.$window.close();
    }
  }
}