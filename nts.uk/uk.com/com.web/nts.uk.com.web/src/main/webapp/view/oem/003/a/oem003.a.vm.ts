/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.oem003.a {

  const API = {
    register: 'ctx/office/equipment/achivement/register',
    findSettings: 'com/screen/oem003/findSettings',
  };

  @bean()
  export class ViewModel extends ko.ViewModel {
    equipmentList: KnockoutObservableArray<any> = ko.observableArray([]);
    checkAll: KnockoutObservable<boolean> = ko.observable(false);

    dataTables: KnockoutObservableArray<DataTable> = ko.observableArray([]);

    formTitle: KnockoutObservable<string> = ko.observable(this.$i18n('OEM003_25'));
    
    created() {
      const vm = this;
      vm.checkAll.subscribe(value => {
        const checkData = _.forEach(vm.dataTables(), item => {
          item.selected(value);
        });
        vm.dataTables(checkData);
      });
    }

    mounted() {
      const vm = this;
      vm.startPage();
    }

    startPage(): JQueryPromise<any> {
      const vm = this;
      const dfd = $.Deferred();
      vm
        .$ajax('com', API.findSettings)
        .then((response : EquipmentUsageSetting) => {

          dfd.resolve();
        })
        .fail(error => {
          dfd.reject();
        });
      
      return dfd.promise();
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

    clickRegister() {
      const vm = this;
      const command: EquipmentUsageSetting = new EquipmentUsageSetting(
        __viewContext.user.companyId,
        vm.formTitle(),
        vm.dataTables()
      )

      vm.$ajax('com', API.register, command)
    }
    clickExport() {}

    defaultDataTable(order: number): DataTable {
      return new DataTable('', 6, null, null, null, null, '', false, '', order);
    }

  }

  class DataTable extends ko.ViewModel {
    id: string;
    checked: KnockoutObservable<boolean> = ko.observable(false);
    itemName: KnockoutObservable<string>;
    displayWidth: KnockoutObservable<number>;
    itemNo: KnockoutObservable<number>;
    min: KnockoutObservable<number>;
    max: KnockoutObservable<number>;
    digitsNo: KnockoutObservable<number>;
    unit: KnockoutObservable<string>;
    require: KnockoutObservable<boolean>;
    memo: KnockoutObservable<string>;
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
      itemNo: number, min: number, max: number, digitsNo: number,
      unit: string, require: boolean, memo: string, order: number
    ) {
      super();
      const vm = this;
      vm.id = nts.uk.util.randomId();
      vm.itemName = ko.observable(itemName);
      vm.displayWidth = ko.observable(displayWidth);
      vm.itemNo = ko.observable(itemNo);
      vm.min = ko.observable(min);
      vm.max = ko.observable(max);
      vm.digitsNo = ko.observable(digitsNo);
      vm.unit = ko.observable(unit);
      vm.require = ko.observable(require);
      vm.memo = ko.observable(memo);
      vm.order = ko.observable(order);

      vm.isCharacter = ko.computed(() => 1 <= this.itemNo() && this.itemNo() <= 3);
      vm.isNumeric = ko.computed(() => 4 <= this.itemNo() && this.itemNo() <= 6);
      vm.isTime = ko.computed(() => 7 <= this.itemNo() && this.itemNo() <= 9);
      vm.isCharOrNumber = ko.computed(() => vm.isCharacter() || vm.isNumeric());
      vm.isItemTypeNull = ko.computed(() => _.isNull(vm.itemNo()));
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

  enum ItemClassification {
    TEXT = 0,
    NUMBER = 1,
    TIME = 2,
  }

  class EquipmentUsageSetting {
    // 設備帳票設定
    formSetting: {
      cid: string,
      title: string
    };

    // 設備利用実績の項目設定
    itemSettings: {
      cid: string, itemNo: string,
      inputControl: {
        itemCls: number,
        require: boolean,
        digitsNo: number,
        maximum: number,
        minimum: number,
      }, 
      items: {
        itemName: string,
        unit: string,
        memo: string,
      },
    }[];

    // 設備の実績入力フォーマット設定
    formatSetting: {
      cid: string,
      itemDisplaySettings: {
        displayWidth: number,
        displayOrder: number,
        itemNo: string
      }[],
    };

    constructor(cid: string, title: string, dataTables: DataTable[]) {
      const vm = this;
      vm.formSetting = { cid, title };
      const itemSettings: any[] = [];
      const itemDisplaySettings: any[] = [];
      _.forEach(dataTables, item => {
        itemDisplaySettings.push({
          displayWidth: item.displayWidth(),
          displayOrder: item.order(),
          itemNo: item.itemNo(),
        });

        itemSettings.push({
          cid, itemNo: item.itemNo(),
          inputControl: {
            itemCls: item.isCharacter()
              ? ItemClassification.TEXT
              : item.isNumeric()
                ? ItemClassification.NUMBER
                : ItemClassification.TIME,
            require: item.require(),
            digitsNo: item.digitsNo(),
            maximum: item.max(),
            minimum: item.min(),
          }, 
          items: {
            itemName: item.itemName(),
            unit: item.unit(),
            memo: item.memo(),
          },
        })
      });
      
      vm.itemSettings = itemSettings;
      vm.formatSetting = { cid, itemDisplaySettings };
    }

    toDataTables() {
      
    }
  }
}
