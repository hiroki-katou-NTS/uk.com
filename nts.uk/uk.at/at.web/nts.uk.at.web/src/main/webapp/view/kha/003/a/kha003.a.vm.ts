module nts.uk.at.kha003.a {

    const API = {
        INIT: 'at/screen/kha003/a/init',
        REGISTER: 'at/screen/kha003/a/register',
        FIND: 'at/screen/kha003/a/find',
        UPDATE: 'at/screen/kha003/a/update',
        DELETE: 'at/screen/kha003/a/delete'
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        items: KnockoutObservableArray<ItemModel>;
        summaryItems: KnockoutObservableArray<any>;
        taskFrameSettingA56: TaskFrameSetting = new TaskFrameSetting(0, '', 0);
        taskFrameSettingA57: TaskFrameSetting = new TaskFrameSetting(0, '', 0);
        taskFrameSettingA58: TaskFrameSetting = new TaskFrameSetting(0, '', 0);
        taskFrameSettingA59: TaskFrameSetting = new TaskFrameSetting(0, '', 0);
        taskFrameSettingA510: TaskFrameSetting = new TaskFrameSetting(0, '', 0);
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        manHour: CodeName = new CodeName('', '');
        // A6_1_1 radio button
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        // A6_2_2 radio button
        itemListA622: KnockoutObservableArray<any>;
        selectedIdA622: KnockoutObservable<number>;
        enableA622: KnockoutObservable<boolean>;
        //A7_1
        isA71Checked: KnockoutObservable<boolean>;
        isA71Enable: KnockoutObservable<boolean>;
        //A7_2
        isA72Enable: KnockoutObservable<boolean>;

        layoutSettings: KnockoutObservableArray<any>;
        isUpdateMode: KnockoutObservable<boolean>;
        isInit: boolean = true;
        isOutPutAll: KnockoutObservable<boolean>;

        constructor() {
            super();
            const vm = this;
            vm.currentCode = ko.observable();
            vm.currentCodeList = ko.observableArray([]);
            vm.items = ko.observableArray([]);
            vm.summaryItems = ko.observableArray([]);
            vm.layoutSettings = ko.observableArray([]);
            vm.itemList = ko.observableArray([
                new BoxModel(0, this.$i18n('KHA003_28')),
                new BoxModel(1, this.$i18n('KHA003_29')),
            ]);
            vm.selectedId = ko.observable(0);
            vm.enable = ko.observable(true);
            vm.itemListA622 = ko.observableArray([
                new BoxModel(0, this.$i18n('KHA003_32') + this.$i18n('KHA003_35')),
                new BoxModel(1, this.$i18n('KHA003_33') + this.$i18n('KHA003_36')),
                new BoxModel(2, this.$i18n('KHA003_34') + this.$i18n('KHA003_37')),
            ]);
            vm.selectedIdA622 = ko.observable(0);
            vm.enableA622 = ko.observable(true);
            vm.isA71Checked = ko.observable(true);
            vm.isA71Enable = ko.observable(true);
            vm.isA72Enable = ko.observable(true);
            vm.isUpdateMode = ko.observable(false);
            vm.isOutPutAll = ko.observable(false);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.currentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getScreenDetails(newValue).done(() => {
                        this.isUpdateMode(true);
                        $('#A4_3').focus();
                    });
                }
            });

            vm.summaryItems.subscribe((newValue: any) => {
                console.log("data:" + newValue);
                if (newValue.length == 4) {
                    $('#append_note').hide();
                } else {
                    $('#append_note').show();
                }
                $('.panel_common')
                    .removeClass('bacg-inactive')
                    .addClass('bacg-active')
                    .parent()
                    .css({'pointer-events': 'auto'});
                newValue.forEach((item: any) => {
                    $('#item_type_' + item.summaryItemType)
                        .removeClass('bacg-active')
                        .addClass('bacg-inactive')
                        .parent()
                        .css({'pointer-events': 'none'});
                })
            });

            vm.loadScreenListData();
            var itemId = '';
            var itemtype = '';
            var itemCount = 0;
            $("#appen-area-one .pannel_padding").draggable({
                helper: function (e) {
                    var html = e.target
                    itemId = html.id;
                    itemtype = $(html).data('itemtype');
                    itemCount = $('#append_area .cell').length;
                    if (itemCount <= 3) {
                        return '<div class="panel panel-gray-bg item_a_6_4_to_67">\n' +
                            '                                            <button class="button_top_right_corner"><i class="icon icon-close"></i></button>\n' +
                            '                                            <span class="label" style="display: table;margin: 71px auto;">' + html.innerText + '</span>\n' +
                            '                                        </div>';
                    }
                },
                stop: function (e, ui) {
                    var html = ui.helper;
                    if (itemCount <= 3) {
                        $(this).css({'pointer-events': 'none'});
                        $(this).children().removeClass('bacg-active').addClass('bacg-inactive');
                        $('#append_area').append('<div class="cell valign-center">\n' +
                            '                                        <div style="background-color: #e7d3193b" class="panel  item_a_6_4_to_67">\n' +
                            '                                            <button id="' + itemId + '" class="button_top_right_corner"><i class="icon icon-close"></i></button>\n' +
                            '                                            <span data-itemtype="' + itemtype + '"  class="label layout-setting summary-item" style="display: table;margin: 71px auto;">' + $(this).children().html() + '</span>\n' +
                            '                                        </div>\n' +
                            '                                    </div>');
                    }
                    if (itemCount >= 3) {
                        $('#append_note').hide();
                    }
                    matchWidth();
                }
            });

            function matchWidth() {
                $('#free_area').css("width", $('#appen_parent').width());
            }

            $(document).ready(function () {
                $('#append_area').on('click', ".button_top_right_corner", function (e) {
                    var id = $(this).attr('id');
                    $('#' + id).removeClass('bacg-inactive').addClass('bacg-active');
                    $('#' + id).parent().css({'pointer-events': 'auto'});
                    $(this).parent().parent().remove();
                    if ($('#append_area .cell').length <= 3) {
                        $('#append_note').show();
                    }
                    matchWidth();
                });
            });
        }

        mounted() {
            const vm = this;
        }


        /**
         * get screen details
         *
         * @param code
         * @author rafiqul.islam
         * */
        getScreenDetails(code: string): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            if (code != "") {
                //  $("#I6_3").focus();
                vm.$blockui("invisible");
                vm.$ajax(API.FIND + "/" + code).then(data => {
                    vm.manHour.code(data.code);
                    vm.manHour.name(data.name);
                    vm.selectedId(data.totalUnit);
                    vm.selectedIdA622(data.displayFormat);
                    vm.isA71Checked(data.dispHierarchy);
                    let type0Name = vm.$i18n('KHA003_24');
                    let type1Name = vm.$i18n('KHA003_25');
                    let type2Name = vm.$i18n('KHA003_26');
                    vm.summaryItems([]);
                    vm.summaryItems(_.map(data.summaryItems, function (item: any) {
                        let itemTypeName = '';
                        if (item.itemType === 0) {
                            itemTypeName = type0Name;
                        } else if (item.itemType === 1) {
                            itemTypeName = type1Name;
                        } else if (item.itemType === 2) {
                            itemTypeName = type2Name;
                        } else {
                            itemTypeName = item.itemTypeName
                        }
                        return {
                            hierarchicalOrder: item.hierarchicalOrder,
                            summaryItemType: item.itemType,
                            itemTypeName: itemTypeName
                        }
                    }));
                    dfd.resolve();
                }).fail(err => {
                    dfd.reject();
                }).always(() => vm.$blockui("clear"));
            }
            return dfd.promise();
        }

        /**
         * get I screen items list
         *
         * @author rafiqul.islam
         * */
        loadScreenListData(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$blockui("invisible");
            vm.$ajax(API.INIT).then(data => {
                vm.items(_.map(data.manHoursSummaryTables, function (item: any) {
                    return new ItemModel(item.code, item.name)
                }));
                if (data.manHoursSummaryTables.length > 0 && vm.isInit) {
                    vm.isUpdateMode(true);
                    vm.currentCode("");
                    vm.currentCode(data.manHoursSummaryTables[0].code);
                    // $("#I6_3").focus();
                }
                if (data.manHoursSummaryTables.length <= 0) {
                    vm.isUpdateMode(false)
                }
                data.taskFrameSettings.forEach(function (value: any) {
                    if (value.taskFrameNo == 1) {
                        vm.taskFrameSettingA56.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA56.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA56.useAtr(value.useAtr);
                    }
                    if (value.taskFrameNo == 2) {
                        vm.taskFrameSettingA57.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA57.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA57.useAtr(value.useAtr);
                    }
                    if (value.taskFrameNo == 3) {
                        vm.taskFrameSettingA58.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA58.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA58.useAtr(value.useAtr);
                    }
                    if (value.taskFrameNo == 4) {
                        vm.taskFrameSettingA59.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA59.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA59.useAtr(value.useAtr);
                    }
                    if (value.taskFrameNo == 5) {
                        vm.taskFrameSettingA510.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA510.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA510.useAtr(value.useAtr);
                    }
                });
            }).fail(err => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui("clear"));
            return dfd.promise();
        }

        /**
         * create new button
         *
         * @author rafiqul.islam
         * */
        clickNewButton() {
            const vm = this;
            vm.isUpdateMode(false);
            vm.currentCode('');
            vm.manHour.code('');
            vm.manHour.name('')
            vm.summaryItems([]);
        }

        /**
         * registration
         *
         * @author rafiqul.islam
         * */
        clickRegistrationButton() {
            const vm = this;
            let summaryItemsParams: any = [];
            $('#append_area .summary-item').each(function (index, object) {
                let item = {
                    hierarchicalOrder: index + 1,
                    summaryItemType: $(object).data('itemtype')
                }
                summaryItemsParams.push(item);
                console.log(summaryItemsParams);
            })
            vm.$validate('#A4_2', '#A4_3').then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                else {
                    let command = {
                        code: vm.manHour.code(),
                        name: vm.manHour.name(),
                        totalUnit: vm.selectedId(),
                        displayFormat: vm.selectedIdA622(),
                        dispHierarchy: vm.isA71Checked() ? 1 : 0,
                        summaryItems: summaryItemsParams
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(vm.isUpdateMode() ? API.UPDATE : API.REGISTER, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_15"})
                            .then(() => {
                                vm.isInit = false;
                                vm.loadScreenListData();
                                vm.currentCode(command.code);
                                vm.getScreenDetails(command.code);
                                $("#A4_3").focus();
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                    });
                }
            });
        }

        /**
         * duplicate
         *
         * @author rafiqul.islam
         * */
        clickDuplicateButton() {
            const vm = this;
            let shareData = {
                code: vm.manHour.code(),
                name: vm.manHour.name()
            }
            vm.$window.storage('kha003ERequiredData', shareData).then(() => {
                vm.$window.modal("/view/kha/003/e/index.xhtml").then(() => {
                    vm.loadScreenListData()
                });
            });
        }

        /**
         * get selectable code
         *
         * @param items
         * @param code
         * @return string
         * @author rafiqul.islam         *
         * */
        getSelectableCode(items: any, code: string): string {
            var currentIndex = 0;
            var selectableCode = "";
            for (var i = 0; i < items.length; i++) {
                if (items[i].code == code) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex === items.length - 1 && items.length != 1) {
                selectableCode = items[currentIndex - 1].code;
            } else if (currentIndex === 0 && items.length > 1) {
                selectableCode = items[1].code;
            }
            else {
                if (items.length != 1) {
                    selectableCode = items[currentIndex + 1].code;
                }
            }
            return selectableCode;
        }

        /**
         * click delete
         *
         * @author rafiqul.islam
         * */
        clickDeleteButton() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'yes' | 'cancel') => {
                if (result === 'yes') {
                    var selectableCode = vm.getSelectableCode(vm.items(), vm.manHour.code());
                    let command = {
                        code: vm.manHour.code()
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(API.DELETE, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.isInit = false;
                                vm.loadScreenListData();
                                vm.currentCode(selectableCode);
                                vm.getScreenDetails(selectableCode);
                                if (vm.items().length === 0) {
                                    $("#A4_2").focus();
                                }
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                        vm.$errors("clear");
                    });
                }
                $("#J3_3").focus();
            });
        }

        /**
         * output all
         *
         * @author rafiqul.islam
         * */
        clickOutputAllButton() {
            const vm = this;
            let shareData = {
                isCsvOutPut: true,
                totalUnit: vm.selectedId()
            }
            vm.isOutPutAll(true);
            vm.currentCode('');
            vm.manHour.code('');
            vm.manHour.name('')
            vm.summaryItems([]);
            vm.selectedIdA622(0);
            vm.selectedId(0);
            vm.$window.storage('kha003AShareData', shareData).then(() => {
                vm.$window.modal("/view/kha/003/b/index.xhtml").then(() => {

                });
            });
        }

        /**
         * click run button
         *
         * @author rafiqul.islam
         * */
        clickRunButton() {
            const vm = this;
            vm.isOutPutAll(false);
            vm.selectedId(0);
            var c21 = {};
            var c31 = {};
            var c41 = {};
            var c51 = {};
            $('.layout-setting').each(function (i, obj) {
                let object = $(obj);
                if (i == 0) {
                    c21 = {
                        type: object.data('itemtype'),
                        name: obj.innerHTML
                    };
                }
                if (i == 1) {
                    c31 = {
                        type: object.data('itemtype'),
                        name: obj.innerHTML
                    };
                    ;
                }
                if (i == 2) {
                    c41 = {
                        type: object.data('itemtype'),
                        name: obj.innerHTML
                    };
                    ;
                }
                if (i == 3) {
                    c51 = {
                        type: object.data('itemtype'),
                        name: obj.innerHTML
                    };
                    ;
                }
            });
            let shareData = {
                c21: c21,
                c31: c31,
                c41: c41,
                c51: c51,
                totalUnit: vm.selectedId(),
                isCsvOutPut: false,
            }
            vm.$window.storage('kha003AShareData', shareData).then(() => {
                vm.$window.modal("/view/kha/003/b/index.xhtml").then(() => {

                });
            });
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id: any, name: any) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class CodeName {
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class ItemModel {
        code: any;
        name: any;

        constructor(code: any, name: any) {
            this.code = code;
            this.name = name;
        }
    }

    class TaskFrameSetting {
        taskFrameNo: KnockoutObservable<any>;
        taskFrameName: KnockoutObservable<any>;
        useAtr: KnockoutObservable<any>;

        constructor(taskFrameNo: any, taskFrameName: any, useAtr: any) {
            this.taskFrameNo = ko.observable(taskFrameNo);
            this.taskFrameName = ko.observable(taskFrameName);
            this.useAtr = ko.observable(useAtr);
        }
    }
}


