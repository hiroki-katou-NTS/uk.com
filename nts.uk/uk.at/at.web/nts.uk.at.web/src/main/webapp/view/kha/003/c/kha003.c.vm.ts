module nts.uk.at.kha003.c {

    const API = {
        //TODO api path
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        // c2_4
        c24Items: KnockoutObservableArray<ItemModel>;
        c24CurrentCodeList: KnockoutObservableArray<any>;
        // c3_4
        c34Items: KnockoutObservableArray<ItemModel>;
        c34CurrentCodeList: KnockoutObservableArray<any>;
        // c4_4
        c44Items: KnockoutObservableArray<ItemModel>;
        c44CurrentCodeList: KnockoutObservableArray<any>;
        // c5_4
        c54Items: KnockoutObservableArray<ItemModel>;
        c54CurrentCodeList: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        c21Params: KnockoutObservable<any>;
        c21Text: KnockoutObservable<any>;
        c31Params: KnockoutObservable<any>;
        c31Text: KnockoutObservable<any>;
        c41Params: KnockoutObservable<any>;
        c41Text: KnockoutObservable<any>;
        c51Params: KnockoutObservable<any>;
        c51Text: KnockoutObservable<any>;

        c21isVisible: KnockoutObservable<boolean>;
        c31isVisible: KnockoutObservable<boolean>;
        c41isVisible: KnockoutObservable<boolean>;
        c51isVisible: KnockoutObservable<boolean>;
        dateRange: KnockoutObservable<any>;
        // gridRows: KnockoutObservable<any>;
        code: string;

        constructor() {
            super();
            const vm = this;
            // c2_4
            vm.c24Items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            vm.columns = ko.observableArray([
                {headerText: this.$i18n('KHA003_62'), prop: 'code', width: 80}, //c2_5,c3_5,c4_5,c5_5
                {headerText: this.$i18n('KHA003_63'), prop: 'name', width: 150}  //c2_6,c3_6,c4_6,c5_6
            ]);
            vm.c24CurrentCodeList = ko.observableArray([]);

            // c3_4
            vm.c34Items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            vm.c34CurrentCodeList = ko.observableArray([]);

            // c4_4
            vm.c44Items = ko.observableArray([]);
            vm.c44CurrentCodeList = ko.observableArray([]);

            // c5_4
            vm.c54Items = ko.observableArray([]);
            vm.c54CurrentCodeList = ko.observableArray([]);
            vm.c21Params = ko.observable();
            vm.c21Text = ko.observable();
            vm.c31Params = ko.observable();
            vm.c31Text = ko.observable();
            vm.c41Params = ko.observable();
            vm.c41Text = ko.observable();
            vm.c51Params = ko.observable();
            vm.c51Text = ko.observable();
            vm.c21isVisible = ko.observable(false);
            vm.c31isVisible = ko.observable(false);
            vm.c41isVisible = ko.observable(false);
            vm.c51isVisible = ko.observable(false);
            vm.dateRange = ko.observable();
            // vm.gridRows = ko.observable(12);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.$window.storage('kha003BShareData').done((data) => {
                vm.dateRange(data.dateRange);
            });
            vm.c24CurrentCodeList.subscribe((newValue: any) => {
                vm.$errors("clear");
            });
            vm.c34CurrentCodeList.subscribe((newValue: any) => {
                vm.$errors("clear");
            });
            vm.c44CurrentCodeList.subscribe((newValue: any) => {
                vm.$errors("clear");
            });
            vm.c54CurrentCodeList.subscribe((newValue: any) => {
                vm.$errors("clear");
            });
            vm.$window.storage('kha003AShareData').done((data: any) => {
                vm.code = data.code;
                if (!(data.c21.type === undefined || data.c21.type === null)) {
                    vm.c21isVisible(true)
                }
                if (!(data.c31.type === undefined || data.c31.type === null)) {
                    vm.c31isVisible(true)
                }
                if (!(data.c41.type === undefined || data.c41.type === null)) {
                    vm.c41isVisible(true)
                }
                if (!(data.c51.type === undefined || data.c51.type === null)) {
                    vm.c51isVisible(true)
                }
                vm.c21Params(data.c21);
                vm.c21Text(vm.$i18n('KHA003_61', [data.c21.name]));
                vm.c31Params(data.c31);
                vm.c31Text(vm.$i18n('KHA003_61', [vm.c31Params().name]));
                vm.c41Params(data.c41);
                vm.c41Text(vm.$i18n('KHA003_61', [vm.c41Params().name]));
                vm.c51Params(data.c51);
                vm.c51Text(vm.$i18n('KHA003_61', [vm.c51Params().name]));
                vm.$window.storage('kha003AShareData_' + data.code).done((oldData: any) => {
                    if ((oldData && oldData.code == data.code) && (data.c21.type != oldData.c21.type
                            || data.c31.type != oldData.c31.type
                            || data.c41.type != oldData.c41.type
                            || data.c51.type != oldData.c51.type)) {
                        vm.$dialog.error({messageId: "Msg_2168"}).then(() => {
                            vm.c24CurrentCodeList(_.map(vm.c24Items(), x => x.code));
                            vm.c34CurrentCodeList(_.map(vm.c34Items(), x => x.code));
                            vm.c44CurrentCodeList(_.map(vm.c44Items(), x => x.code));
                            vm.c54CurrentCodeList(_.map(vm.c54Items(), x => x.code));
                        });
                        vm.$window.storage('kha003CShareData_' + data.code, {});
                    } else {
                        vm.$window.storage('kha003CShareData_' + data.code).done(savedDataC => {
                            const dataC = savedDataC || {};
                            if (dataC.code == data.code) {
                                vm.c24CurrentCodeList(dataC.c24CurrentCodeList || []);
                                vm.c34CurrentCodeList(dataC.c34CurrentCodeList || []);
                                vm.c44CurrentCodeList(dataC.c44CurrentCodeList || []);
                                vm.c54CurrentCodeList(dataC.c54CurrentCodeList || []);
                            }
                        });
                    }
                    vm.$window.storage('kha003AShareData_' + data.code, data);
                });
            });

            vm.$window.storage('kha003BShareData').done((data: any) => {
                vm.c24Items(this.getItemData(vm.c21Params().type, data));
                vm.c34Items(this.getItemData(vm.c31Params().type, data));
                vm.c44Items(this.getItemData(vm.c41Params().type, data));
                vm.c54Items(this.getItemData(vm.c51Params().type, data));
                window.setTimeout(function () {
                    document.getElementById('multi-list-c2_4_container').focus();
                }, 0);
            });
            // $(document).ready(function () {
            //     vm.gridRows((12 * $(window).height()) / 768);
            // });
        }


        /**
         * function for get item data to map with UI
         * @param taskCode
         * @param data
         * @author rafiqul.islam
         */
        getItemData(taskCode: any, data: any) {
            let itemList: any = [];
            let task = data.masterNameInfo;
            switch (taskCode) {
                case 0:
                    task.affWorkplaceInfoList.forEach((data: any) => {
                        itemList.push(new ItemModel(data.workplaceCode, data.workplaceName))
                    });
                    break;
                case 1:
                    task.workPlaceInfoList.forEach((data: any) => {
                        itemList.push(new ItemModel(data.workplaceCode, data.workplaceName))
                    });
                    break;
                case 2:
                    task.employeeInfoList.forEach((data: any) => {
                        itemList.push(new ItemModel(data.employeeCode, data.employeeName))
                    });
                    break;
                case 3:
                    task.task1List.forEach((data: any) => {
                        itemList.push(new ItemModel(data.code, data.taskName))
                    });
                    break;
                case 4:
                    task.task2List.forEach((data: any) => {
                        itemList.push(new ItemModel(data.code, data.taskName))
                    });
                    break;
                case 5:
                    task.task3List.forEach((data: any) => {
                        itemList.push(new ItemModel(data.code, data.taskName))
                    });
                    break;
                case 6:
                    task.task4List.forEach((data: any) => {
                        itemList.push(new ItemModel(data.code, data.taskName))
                    });
                    break;
                case 7:
                    task.task5List.forEach((data: any) => {
                        itemList.push(new ItemModel(data.code, data.taskName))
                    });
                    break;
            }
            return itemList;
        }

        mounted() {
            const vm = this;
        }

        /**
         * Event on click cancel button.
         */
        public cancel(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * Event on click decide button.
         */
        public decide(): void {
            const vm = this;
            vm.$validate().then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                if (vm.ifAnyDialogNotSelected()) {
                    vm.$errors("#multi-list-c2_4", "Msg_2165");
                } else {
                    let shareData = {
                        code: vm.code,
                        c24CurrentCodeList: vm.c24CurrentCodeList(),
                        c34CurrentCodeList: vm.c34CurrentCodeList(),
                        c44CurrentCodeList: vm.c44CurrentCodeList(),
                        c54CurrentCodeList: vm.c54CurrentCodeList(),
                        dateRange: vm.dateRange()
                    };
                    vm.$window.storage('kha003CShareData_' + vm.code, shareData);
                    vm.$window.storage('kha003CShareData', shareData).then(() => {
                        vm.$jump('/view/kha/003/d/index.xhtml');
                        nts.uk.ui.windows.close();
                    });
                }
            });
        }

        /**
         * function for check if any selected dialog has empty selected list or not
         */
        public ifAnyDialogNotSelected(): boolean {
            const vm = this;
            if (vm.c21isVisible() && vm.c24CurrentCodeList().length <= 0) {
                return true;
            }
            if (vm.c31isVisible() && vm.c34CurrentCodeList().length <= 0) {
                return true;
            }
            if (vm.c41isVisible() && vm.c44CurrentCodeList().length <= 0) {
                return true;
            }
            if (vm.c51isVisible() && vm.c54CurrentCodeList().length <= 0) {
                return true;
            }
            return false;
        }
    }

    class ItemModel {
        code: string; //c2_8,c3_8,c4_8,c5_8,
        name: string; //c2_9,c3_9,c4_9,c5_9
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}


