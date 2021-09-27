/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.oem003.a {

  const API = {
    getEquipmentList: 'query/equipment/classificationmaster/getAll',
    insetEquipment: 'ctx/office/equipment/classificationmaster/insert',
    updateEquipment: 'ctx/office/equipment/classificationmaster/update',
    deleteEquipment: 'com/screen/oem004/delete',
  };

  @bean()
  export class ViewModel extends ko.ViewModel {
    equipmentList: KnockoutObservableArray<any> = ko.observableArray([]);
    checkAll: KnockoutObservable<boolean> = ko.observable(false);

    code: KnockoutObservable<string> = ko.observable('');
    name: KnockoutObservable<string> = ko.observable('');

    selectedItemNo: KnockoutObservable<number> = ko.observable(0);
    dataTables: KnockoutObservableArray<DataTable>;

    formTitle: KnockoutObservable<string> = ko.observable(this.$i18n('OEM003_25'));
    
    created() {
      const vm = this;
      vm.dataTables = ko.observableArray([
        new DataTable('', 1,1,1,12,'kg',true,'1', 1),
        new DataTable('', 1,1,1,12,'kg',true,'2', 2),
        new DataTable('', 1,1,1,12,'kg',true,'3', 3)
      ]);

      vm.checkAll.subscribe(value => {
        const checkData = _.forEach(vm.dataTables(), item => {
          item.selected(value);
        });
        vm.dataTables(checkData);
      })
    }

    clickAdd() {
      const vm = this;
      const dataLength = vm.dataTables().length;
      // Max length is 9
      if (dataLength >= 9) return;

      const order = dataLength + 1;
      vm.dataTables.push(vm.defaultDataTable(order));
    }

    removeData(delOrder: KnockoutObservable<number>) {
      const vm = this;
      // filter to remove data
      const filterArr = _.filter(vm.dataTables(), item => item.order() !== delOrder());

      // set new order for data
      filterArr.map((item, index) => {
        const order = index + 1;
        item.order(order);
        item.index = order;
      });

      vm.dataTables(filterArr);
    }

    toOrderBy(type: 'before' | 'after') {
      const vm = this;
      // Clone data list of table
      const data = _.cloneDeep(vm.dataTables());
      const move = (fromOrder: number, toOrder: number) => {
        const from = fromOrder - 1;
        const to = toOrder - 1;

        if (!data[to] || data[to].checked())
          return;

        // remove `from` item and store it
        const f = data.splice(from, 1)[0];
        // insert stored item into position `to`
        data.splice(to, 0, f);
      }

      // Change the order of data list
      _.map(
        // If type is before, map the data tables, ifnot map the reverse of data table
        type === 'before' ? vm.dataTables() : vm.dataTables().reverse(),
        item => {
          if (item.checked() && type === 'before') {
            item.toBefore();
            move(item.index, item.order())
          }

          if (item.checked() && type === 'after') {
            item.toAfter();
            move(item.index, item.order())
          }
        }
      );

      // Apply new order
      _.map(data, (item, index) => {
        item.order(index + 1);
        item.index = index + 1;
      });

      vm.dataTables(data);
    }

    clickRegister() {}
    clickExport() {}

    defaultDataTable(order: number): DataTable {
      return new DataTable('', 6, null, null, null, '', false, '', order);
    }

  }

  class DataTable extends ko.ViewModel {
    id: string;
    checked: KnockoutObservable<boolean> = ko.observable(false);
    itemName: KnockoutObservable<string>;
    displayWidth: KnockoutObservable<number>;
    itemType: KnockoutObservable<number>;
    min: KnockoutObservable<number>;
    max: KnockoutObservable<number>;
    unit: KnockoutObservable<string>;
    require: KnockoutObservable<boolean>;
    desc: KnockoutObservable<string>;
    order: KnockoutObservable<number>;
    index: number;

    itemTypes: KnockoutObservableArray<any> = ko.observableArray([
      { itemNo: 1, text: this.$i18n('OEM003_26') },
      { itemNo: 2, text: this.$i18n('OEM003_27') },
      { itemNo: 3, text: this.$i18n('OEM003_28') },
      { itemNo: 4, text: this.$i18n('OEM003_29') },
      { itemNo: 5, text: this.$i18n('OEM003_30') },
      { itemNo: 6, text: this.$i18n('OEM003_31') },
      { itemNo: 7, text: this.$i18n('OEM003_32') },
      { itemNo: 8, text: this.$i18n('OEM003_33') },
      { itemNo: 9, text: this.$i18n('OEM003_34') },
    ]);

    isCharacter: KnockoutComputed<boolean>;
    isNumeric: KnockoutComputed<boolean>;
    isTime: KnockoutComputed<boolean>;
    isCharOrNumber: KnockoutComputed<boolean>;
    isItemTypeNull: KnockoutComputed<boolean>;

    constructor(
      itemName: string, displayWidth: number,
      itemType: number, min: number, max: number,
      unit: string, require: boolean, desc: string, order: number
    ) {
      super();
      const vm = this;
      vm.id = nts.uk.util.randomId();
      vm.itemName = ko.observable(itemName);
      vm.displayWidth = ko.observable(displayWidth);
      vm.itemType = ko.observable(itemType);
      vm.min = ko.observable(min);
      vm.max = ko.observable(max);
      vm.unit = ko.observable(unit);
      vm.require = ko.observable(require);
      vm.desc = ko.observable(desc);
      vm.order = ko.observable(order);

      vm.isCharacter = ko.computed(() => 1 <= this.itemType() && this.itemType() <= 3);
      vm.isNumeric = ko.computed(() => 4 <= this.itemType() && this.itemType() <= 6);
      vm.isTime = ko.computed(() => 7 <= this.itemType() && this.itemType() <= 9);
      vm.isCharOrNumber = ko.computed(() => vm.isCharacter() || vm.isNumeric());
      vm.isItemTypeNull = ko.computed(() => _.isNull(vm.itemType()));
      vm.index = order;
    }

    toAfter() {
      const vm = this;
      if (vm.order() >= 9)
        return;

      vm.order(vm.order() + 1);
    }

    toBefore() {
      const vm = this;
      if (vm.order() <= 1)
        return;

      vm.order(vm.order() - 1);
    }

    selected(selected: boolean) {
      const vm = this;
      vm.checked(selected);
    }

  }
}
