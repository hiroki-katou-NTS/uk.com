/* module nts.uk.com.view.cmf003.c {
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;
    export module viewmodel {
        export class ScreenModel {

            // category
            categoriesDefault: KnockoutObservableArray<ItemCategory>;
            categoriesSelected: KnockoutObservableArray<ItemCategory>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCateSelected: KnockoutObservableArray<ItemSystemType>;

            // systemType
            systemTypes: KnockoutObservableArray<number>;
            selectedCode: KnockoutObservable<string> = ko.observable(null);
            currentItem: KnockoutObservable<ItemSystemType>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            headerCodeCategories: string = getText("CMF003_65");
            headerNameCategories: string = getText("CMF003_66");

            systemtypeFromB: KnockoutObservable<ItemSystemType>;
            categoriesFromB: KnockoutObservable<any>;
            constructor() {

                var self = this;
                let categoriesFB = getShared('CMF003_B_CATEGORIES');
                let systemtypeFB = getShared('CMF003_B_SYSTEMTYPE');
                self.currentCateSelected = ko.observableArray([]);
                var systemIdSelected;
                self.systemTypes = ko.observableArray([]);
                service.getSysTypes().done(function(data: Array<any>) {
                    if (data && data.length) {
                        _.forOwn(data, function(index) {
                            self.systemTypes.push(new ItemSystemType(index.type + '', index.name));
                        });

                        if (systemtypeFB != undefined) { 
                            systemIdSelected = systemtypeFB.code; 
                        } else { 
                            systemIdSelected = self.systemTypes()[0].code; 
                        }
                        self.selectedCode(systemIdSelected);
                    } else {

                    }

                }).fail(function(error) {
                    alertError(error);

                }).always(() => {

                });

                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                self.categoriesDefault = ko.observableArray([]);
                self.selectedCode.subscribe(value => {
                    if (value && value.length > 0) {
                    self.currentItem = _.find(self.systemTypes(), a => a.code === value);
                        service.getConditionList(parseInt(self.selectedCode())).done(function(data: Array<any>) {
                            
                            data = _.sortBy(data, ["categoryId"]);
                            if (systemtypeFB != undefined) {
                                _.forOwn(categoriesFB, function(index) {
                                     _.remove(data, function (e) {
                                            return e.categoryId == index.categoryId;
                                        });
                                });
                                self.currentCateSelected(categoriesFB);
                           }
                            self.categoriesDefault(data);
                            $("#swap-list-grid1 tr:first-child").focus();
                            
                        }).fail(function(error) {
                            alertError(error);
                        }).always(() => {
                            _.defer(() => {
                                $("#grd_Condition tr:first-child").focus();
                            });
                        });
                    }
                });

                self.columns = ko.observableArray([
                    { headerText: self.headerCodeCategories, key: 'categoryId', width: 70 },
                    { headerText: self.headerNameCategories, key: 'categoryName', width: 250 }
                ]);


                self.categoriesSelected = self.currentCateSelected;

            }

            remove() {
                let self = this;
                self.categoriesDefault.shift();
            }

            closeUp() {
                close();
            }

            submit() {
                let self = this;
                if (self.currentCateSelected().length == 0) {
                    alertError({ messageId: "Msg_577" });
                } else {
                    setShared("CMF003_C_CATEGORIES", JSON.stringify(self.currentCateSelected()));
                    setShared("CMF003_C_SYSTEMTYPE", self.currentItem);
                    close();
                }
            }

        }
    }

    class ItemCategory {
        schelperSystem: number;
        categoryId: string;
        categoryName: string;
        possibilitySystem: number;
        storedProcedureSpecified: number;
        timeStore: number;
        otherCompanyCls: number;
        attendanceSystem: number;
        recoveryStorageRange: number;
        paymentAvailability: number;
        storageRangeSaved: number;
        constructor(schelperSystem: number, categoryId: string, categoryName: string, possibilitySystem: number,
            storedProcedureSpecified: number, timeStore: number, otherCompanyCls: number, attendanceSystem: number,
            recoveryStorageRange: number, paymentAvailability: number, storageRangeSaved: number) {
            this.schelperSystem = schelperSystem;
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.possibilitySystem = possibilitySystem;
            this.storedProcedureSpecified = storedProcedureSpecified;
            this.timeStore = timeStore;
            this.otherCompanyCls = otherCompanyCls;
            this.attendanceSystem = attendanceSystem;
            this.recoveryStorageRange = recoveryStorageRange;
            this.paymentAvailability = paymentAvailability;
            this.storageRangeSaved = storageRangeSaved;
        }
    }

    class ItemSystemType {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }



} */

module nts.uk.com.view.cmf003.c {
  import getText = nts.uk.resource.getText;
  import TextEditorOption = nts.uk.ui.option.TextEditorOption;
  import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;

  @bean()
  export class ViewModel extends ko.ViewModel {
    screenMode: KnockoutObservable<number> = ko.observable(ScreenMode.NEW);

      //Pattern list
      patternList: KnockoutObservableArray<Pattern> = ko.observableArray([
        { code: '00001', patternName: 'name1' },
        { code: '00002', patternName: 'name2' },
        { code: '00003', patternName: 'name3' },
        { code: '00004', patternName: 'name4' },
        { code: '00005', patternName: 'name5' },
        { code: '00006', patternName: 'name6' },
        { code: '00011', patternName: 'name1' },
        { code: '00012', patternName: 'name2' },
        { code: '00013', patternName: 'name3' },
        { code: '00014', patternName: 'name4' },
        { code: '00015', patternName: 'name5' },
        { code: '00016', patternName: 'name6' },
        { code: '00021', patternName: 'name1' },
        { code: '00022', patternName: 'name2' },
        { code: '00023', patternName: 'name3' },
        { code: '00024', patternName: 'name4' },
        { code: '00025', patternName: 'name5' },
        { code: '00026', patternName: 'name6' },
        { code: '00031', patternName: 'name1' },
        { code: '00032', patternName: 'name2' },
        { code: '00033', patternName: 'name3' },
        { code: '00034', patternName: 'name4' },
        { code: '00035', patternName: 'name5' },
        { code: '00036', patternName: 'name6' },
        { code: '00041', patternName: 'name1' },
        { code: '00042', patternName: 'name2' },
        { code: '00043', patternName: 'name3' },
        { code: '00044', patternName: 'name4' },
        { code: '00045', patternName: 'name5' },
        { code: '00046', patternName: 'name6' },
      ]);
    selectedPattern: KnockoutObservable<Pattern> = ko.observable(null);
    patternColumns: KnockoutObservableArray<any> = ko.observableArray([
      { headerText: getText('CMF003_23'), key: 'code', width: 75 },
      { headerText: getText('CMF003_632'), key: 'patternName', width: 250 }
    ]);

    //Pattern
    codeValue: KnockoutObservable<string> = ko.observable('');
    nameValue: KnockoutObservable<string> = ko.observable('');

    options: TextEditorOption = new TextEditorOption({
      width: '150px'
    });

    //Category list
    categoriesDefault: KnockoutObservableArray<Category> = ko.observableArray([]);
    categoriesSelected: KnockoutObservableArray<Category> = ko.observableArray([]);
    leftColumns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
      { headerText: getText('CMF003_65'), key: 'categoryId', width: 70 },
      { headerText: getText('CMF003_66'), key: 'categoryName', width: 250 }
    ]);
    rightColumns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
      { headerText: getText('CMF003_65'), key: 'categoryId', width: 70 },
      { headerText: getText('CMF003_66'), key: 'categoryName', width: 180 },
      { headerText: getText('CMF003_636'), key: 'retentionPeriod', width: 100 }
    ]);
    currentCateSelected: KnockoutObservableArray<ItemSystemType> = ko.observableArray([]);
    systemTypes: KnockoutObservableArray<ItemModel> = ko.observableArray([
      new ItemModel(0, ' '),
      new ItemModel(1, getText('CMF003_400')),
      new ItemModel(2, getText('CMF003_401')),
      new ItemModel(3, getText('CMF003_402')),
      new ItemModel(4, getText('CMF003_403')),
    ]);
    selectedSystemType: KnockoutObservable<number> = ko.observable();

    //Auto execution
    saveFormatChecked: KnockoutObservable<boolean> = ko.observable(false);
    usePasswordChecked: KnockoutObservable<boolean> = ko.observable(false);
    targetYearDD: KnockoutObservableArray<ItemModel> = ko.observableArray([
      new ItemModel(1, '参照年'),
      new ItemModel(2, getText('CMF003_405')),
      new ItemModel(3, getText('CMF003_406')),
      new ItemModel(4, getText('CMF003_407'))
    ]);
    targetMonthDD: KnockoutObservableArray<ItemModel> = ko.observableArray([
      new ItemModel(1, '参照月'),
      new ItemModel(2, getText('CMF003_408')),
      new ItemModel(3, getText('CMF003_409')),
      new ItemModel(4, getText('CMF003_410')),
      new ItemModel(5, getText('CMF003_411')),
      new ItemModel(6, getText('CMF003_412')),
      new ItemModel(7, getText('CMF003_413')),
      new ItemModel(8, getText('CMF003_414')),
      new ItemModel(9, getText('CMF003_415')),
      new ItemModel(10, getText('CMF003_416')),
      new ItemModel(11, getText('CMF003_417')),
      new ItemModel(12, getText('CMF003_418')),
      new ItemModel(13, getText('CMF003_419'))
    ]);
    selectedDailyTargetYear: KnockoutObservable<string> = ko.observable('');
    selectedDailyTargetMonth: KnockoutObservable<string> = ko.observable('');
    selectedMonthlyTargetYear: KnockoutObservable<string> = ko.observable('');
    selectedMonthlyTargetMonth: KnockoutObservable<string> = ko.observable('');
    selectedAnnualTargetYear: KnockoutObservable<string> = ko.observable('');
    password: KnockoutObservable<string> = ko.observable('');
    confirmPassword: KnockoutObservable<string> = ko.observable('');
    isCheckboxActive: KnockoutObservable<boolean> = ko.observable(false);
    explanation: KnockoutObservable<string> = ko.observable('');

    mounted() {
      const vm = this;
      vm.usePasswordChecked.subscribe(value => {
        if (value) {
          $(".password-input").trigger("validate");
        } else {
          $('.password-input').ntsError('clear');
        }
      });

      vm.initDisplay();
    }

    private initDisplay() {
      const vm = this;
      vm.$blockui("grayout");
      service.initDisplay().done((res) => {
        console.log(res);
        _.map(res.categories, (x: any) => {
          let c = new Category();
          c.categoryId = x.categoryId;
          c.categoryName = x.categoryName;
          c.retentionPeriod = getText(x.retentionPeriod);
          vm.categoriesDefault.push(c);
        })
      }).always(() => {
        vm.$blockui("clear");
      });
    }

    public refreshNew() {
      const vm = this;
      vm.screenMode(ScreenMode.NEW);
      vm.selectedAnnualTargetYear(vm.getDefaultItem(vm.targetYearDD()).name);
      vm.selectedDailyTargetYear(vm.getDefaultItem(vm.targetYearDD()).name);
      vm.selectedMonthlyTargetYear(vm.getDefaultItem(vm.targetYearDD()).name);
      vm.selectedDailyTargetMonth(vm.getDefaultItem(vm.targetMonthDD()).name);
      vm.selectedMonthlyTargetMonth(vm.getDefaultItem(vm.targetMonthDD()).name);
      vm.codeValue('');
      vm.nameValue('');
      vm.selectedSystemType(0);
      vm.saveFormatChecked(false);
    }

    private getDefaultItem(arr: ItemModel[]): ItemModel {
      return _.filter(arr, item => item.code === '0').pop();
    }
  }

  export class Pattern {
    code: string;
    patternName: string;
  }

  export class ItemCategory {
    schelperSystem: number;
    categoryId: string;
    categoryName: string;
    possibilitySystem: number;
    storedProcedureSpecified: number;
    timeStore: number;
    otherCompanyCls: number;
    attendanceSystem: number;
    recoveryStorageRange: number;
    paymentAvailability: number;
    storageRangeSaved: number;
    constructor(schelperSystem: number, categoryId: string, categoryName: string, possibilitySystem: number,
      storedProcedureSpecified: number, timeStore: number, otherCompanyCls: number, attendanceSystem: number,
      recoveryStorageRange: number, paymentAvailability: number, storageRangeSaved: number) {
      this.schelperSystem = schelperSystem;
      this.categoryId = categoryId;
      this.categoryName = categoryName;
      this.possibilitySystem = possibilitySystem;
      this.storedProcedureSpecified = storedProcedureSpecified;
      this.timeStore = timeStore;
      this.otherCompanyCls = otherCompanyCls;
      this.attendanceSystem = attendanceSystem;
      this.recoveryStorageRange = recoveryStorageRange;
      this.paymentAvailability = paymentAvailability;
      this.storageRangeSaved = storageRangeSaved;
    }
  }

  export class Category {
    categoryId: string;
    categoryName: string;
    retentionPeriod?: string;
  }

  export class ItemSystemType {
    code: string;
    name: string;
    period: string;
    constructor(code: string, name: string, period: string) {
      this.code = code;
      this.name = name;
      this.period = period;
    }
  }

  export class ItemModel {
    code: string;
    name: string;

    constructor(code: number, name: string) {
      this.code = code.toString();
      this.name = name;
    }
  }
  
  export class ScreenMode {
    static NEW = 0;
    static UPDATE = 1;
  }
}