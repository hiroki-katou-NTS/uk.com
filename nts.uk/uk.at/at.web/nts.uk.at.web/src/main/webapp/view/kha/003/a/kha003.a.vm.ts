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
        isExecutionMode: KnockoutObservable<boolean>;

        isOutPutAll: KnockoutObservable<boolean>;

        constructor() {
            super();
            const vm = this;
            vm.currentCode = ko.observable("");
            vm.currentCodeList = ko.observableArray([]);
            vm.items = ko.observableArray([]);
            vm.summaryItems = ko.observableArray([]);
            vm.layoutSettings = ko.observableArray([]);
            vm.itemList = ko.observableArray([
                new BoxModel(0, vm.$i18n('KHA003_28')),
                new BoxModel(1, vm.$i18n('KHA003_29')),
            ]);
            vm.selectedId = ko.observable(0);
            vm.enable = ko.observable(true);
            vm.itemListA622 = ko.observableArray([
                new BoxModel(0, vm.$i18n('KHA003_32') + "<br/>" + vm.$i18n('KHA003_35')),
                new BoxModel(1, vm.$i18n('KHA003_33') + "<br/>" + vm.$i18n('KHA003_36')),
                new BoxModel(2, vm.$i18n('KHA003_34') + "<br/>" + vm.$i18n('KHA003_37')),
            ]);
            vm.selectedIdA622 = ko.observable(0);
            vm.enableA622 = ko.observable(true);
            vm.isA71Checked = ko.observable(true);
            vm.isA71Enable = ko.observable(true);
            vm.isA72Enable = ko.observable(true);
            vm.isUpdateMode = ko.observable(false);
            vm.isExecutionMode = ko.observable(false);
            vm.isOutPutAll = ko.observable(false);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.currentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getScreenDetails(newValue).done(() => {
                        vm.isUpdateMode(false);
                        vm.isExecutionMode(true);
                        $('#A4_3').focus();
                    });
                } else {
                    vm.manHour.code('');
                    vm.manHour.name('');
                    vm.summaryItems([]);
                    vm.matchWidth();
                    vm.selectedIdA622(0);
                    vm.selectedId(0);
                    vm.isExecutionMode(false);
                    vm.isUpdateMode(false);
                    $('#A4_2').focus();
                }
            });

            vm.manHour.name.subscribe((newValue: any) => {
                vm.excutionModeToUpDateMode();
            });

            vm.selectedIdA622.subscribe((newValue: any) => {
                vm.excutionModeToUpDateMode();
            });

            vm.selectedId.subscribe((newValue: any) => {
                vm.excutionModeToUpDateMode();
            });
            vm.isA71Checked.subscribe((newValue: any) => {
                vm.excutionModeToUpDateMode();
            });

            vm.summaryItems.subscribe((newValue: any) => {
                vm.$errors("clear", "#append_area");
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
                });
            });

            vm.loadScreenListData();

            $(document).ready(function () {
                $('#append_area').on('click', ".button_top_right_corner", function (e) {
                    var id = $(this).attr('id');
                    $('#' + id).removeClass('bacg-inactive').addClass('bacg-active');
                    $('#' + id).parent().css({'pointer-events': 'auto'});
                    $(this).parent().parent().remove();
                    if ($('#append_area .cell').length <= 3) {
                        $('#append_note').show();
                    }
                    vm.excutionModeToUpDateMode();
                    vm.matchWidth();
                });
            });
        }

        mounted() {
            const vm = this;
        }

        /**
         * function for match with
         * */
        matchWidth() {
            $('#free_area').css("width", $('#appen_parent').width());
        }

        /**
         * Function for making execution mode to update mode
         * */
        excutionModeToUpDateMode() {
            const vm = this;
            if (vm.isExecutionMode()) {
                vm.isExecutionMode(false);
                vm.isUpdateMode(true);
            }
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
                vm.$blockui("invisible");
                vm.$ajax(API.FIND + "/" + code).then(data => {
                    if (data) {
                        vm.manHour.code(data.code);
                        vm.manHour.name(data.name);
                        vm.selectedId(data.totalUnit);
                        vm.selectedIdA622(data.displayFormat);
                        vm.isA71Checked(data.dispHierarchy);
                        let type0Name = vm.$i18n('KHA003_24');
                        let type1Name = vm.$i18n('KHA003_25');
                        let type2Name = vm.$i18n('KHA003_26');
                        $('#append_area').empty();
                        vm.summaryItems(_.sortBy(_.map(data.summaryItems || [], function (item: any) {
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
                        }), ["hierarchicalOrder"]));
                        vm.matchWidth();
                    } else {
                        vm.currentCode("");
                    }
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
        loadScreenListData(codeToSelect?: string): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$ajax(API.INIT).then(data => {
                vm.items(_.map(data.manHoursSummaryTables, function (item: any) {
                    return new ItemModel(item.code, item.name)
                }));
                if (data.manHoursSummaryTables.length > 0) {
                    if (codeToSelect)
                        vm.currentCode() == codeToSelect ? vm.currentCode.valueHasMutated() : vm.currentCode(codeToSelect);
                    else
                        vm.currentCode() == data.manHoursSummaryTables[0].code ? vm.currentCode.valueHasMutated() : vm.currentCode(data.manHoursSummaryTables[0].code);
                } else {
                    vm.currentCode() == "" ? vm.currentCode.valueHasMutated() : vm.currentCode("");
                }
                data.taskFrameSettings.forEach(function (value: any) {
                    if (value.taskFrameNo == 1 && value.useAtr == 1) {
                        vm.taskFrameSettingA56.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA56.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA56.useAtr(value.useAtr);
                    }
                    if (value.taskFrameNo == 2 && value.useAtr == 1) {
                        vm.taskFrameSettingA57.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA57.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA57.useAtr(value.useAtr);
                    }
                    if (value.taskFrameNo == 3 && value.useAtr == 1) {
                        vm.taskFrameSettingA58.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA58.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA58.useAtr(value.useAtr);
                    }
                    if (value.taskFrameNo == 4 && value.useAtr == 1) {
                        vm.taskFrameSettingA59.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA59.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA59.useAtr(value.useAtr);
                    }
                    if (value.taskFrameNo == 5 && value.useAtr == 1) {
                        vm.taskFrameSettingA510.taskFrameNo(value.taskFrameNo);
                        vm.taskFrameSettingA510.taskFrameName(value.taskFrameName);
                        vm.taskFrameSettingA510.useAtr(value.useAtr);
                    }
                });

                $("#appen-area-one .pannel_padding").draggable({
                    helper: function (e: any) {
                        var html = e.target
                        const itemId = html.id;
                        const itemType = $(html).data('itemtype');
                        return `<div class="panel panel-gray-bg item_a_6_4_to_67" data-itemtype="${itemType}" data-id="${itemId}">
                                    <button class="button_top_right_corner"><i class="icon icon-close"></i></button>
                                    <span class="label" style="display: table;margin: 71px auto;">${html.innerText}</span>
                                </div>`;
                    },
                    stop: function (e, ui) {
                        var html = ui.helper;
                        const itemId = $(html).data('id');
                        const itemType = $(html).data('itemtype');
                        const itemCount = $('#append_area .cell').length;

                        // check helper dragged into append area
                        let isHelperInAppendArea = false;
                        const aT1 = $("#append_area").parent().offset().top,
                            aT2 = $("#append_area").parent().offset().top + $("#append_area").parent().height(),
                            aL1 = $("#append_area").parent().offset().left,
                            aL2 = $("#append_area").parent().offset().left + $("#append_area").parent().width(),
                            hT1 = ui.offset.top - 20, // 20px padding
                            hT2 = ui.offset.top + $(html).height() + 20,
                            hL1 = ui.offset.left - 20,
                            hL2 = ui.offset.left + $(html).width() + 20;
                        if (aT1 <= hT1 && hT1 <= aT2 && aL1 <= hL1 && hL1 <= aL2) isHelperInAppendArea = true;
                        if (aT1 <= hT2 && hT2 <= aT2 && aL1 <= hL1 && hL1 <= aL2) isHelperInAppendArea = true;
                        if (aT1 <= hT1 && hT1 <= aT2 && aL1 <= hL2 && hL2 <= aL2) isHelperInAppendArea = true;
                        if (aT1 <= hT2 && hT2 <= aT2 && aL1 <= hL2 && hL2 <= aL2) isHelperInAppendArea = true;

                        if (isHelperInAppendArea) {
                            vm.$errors("clear", "#append_area");
                            if (itemCount <= 3) {
                                $(this).css({'pointer-events': 'none'});
                                $(this).children().removeClass('bacg-active').addClass('bacg-inactive');
                                $('#append_area').append(`<div class="cell valign-center">
                                                                    <div class="panel  item_a_6_4_to_67 bacg-active">
                                                                        <button tabindex="-1" id="${itemId}" class="button_top_right_corner"><i class="icon icon-close"></i></button>
                                                                        <span data-itemtype="${itemType}"  class="label layout-setting summary-item" style="display: table;margin: 71px auto;">${$(this).children().html()}</span>
                                                                    </div>
                                                                </div>`);
                                vm.excutionModeToUpDateMode();
                            }
                            if (itemCount >= 3) {
                                $('#append_note').hide();
                            }
                            vm.matchWidth();
                        }
                    }
                });
                dfd.resolve();
            }).fail(err => {
                dfd.reject();
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
            vm.currentCode('');
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
                if (summaryItemsParams.length === 0) {
                    vm.$errors("#append_area", "Msg_2174");
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
                                vm.loadScreenListData(command.code);
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
            };
            vm.isExecutionMode(true);
            vm.$window.storage('kha003ERequiredData', shareData).then(() => {
                vm.$window.modal("/view/kha/003/e/index.xhtml").then(() => {
                    vm.$window.storage('kha003EShareData').done((data) => {
                        if (data) vm.loadScreenListData(data.duplicatedCode);
                    });
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
            vm.isExecutionMode(true);
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: any) => {
                if (result === 'yes') {
                    var selectableCode = vm.getSelectableCode(vm.items(), vm.manHour.code());
                    let command = {
                        code: vm.manHour.code()
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(API.DELETE, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.loadScreenListData(selectableCode);
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                        vm.$errors("clear");
                        vm.isExecutionMode(true);
                    });
                }
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
            };
            vm.isOutPutAll(true);
            vm.currentCode('');
            vm.manHour.code('');
            vm.manHour.name('');
            vm.summaryItems([]);
            vm.selectedIdA622(0);
            vm.selectedId(0);
            vm.$window.storage('kha003AShareData', shareData).then(() => {
                vm.$window.modal("/view/kha/003/b/index.xhtml").then(() => {
                    vm.$window.storage('kha003BShareData').done((data: any) => {
                        if (!data.isCancel) {
                            var rows: any = [];
                            rows.push(['職場別作業集計表']);
                            rows.push([data.datePriod]);
                            var masterNameInfo = data.masterNameInfo;
                            rows.push(
                                [
                                    '社員コード',
                                    '社員名',
                                    '日付',
                                    '所属職場コード',
                                    '所属職場名称',
                                    '勤務職場コード',
                                    '勤務職場名称',
                                    '応援勤務枠NO',
                                    vm.taskFrameSettingA56.taskFrameName() + vm.$i18n('KHA003_112'),
                                    vm.taskFrameSettingA56.taskFrameName() + vm.$i18n('KHA003_113'),
                                    vm.taskFrameSettingA57.taskFrameName() + vm.$i18n('KHA003_112'),
                                    vm.taskFrameSettingA57.taskFrameName() + vm.$i18n('KHA003_113'),
                                    vm.taskFrameSettingA58.taskFrameName() + vm.$i18n('KHA003_112'),
                                    vm.taskFrameSettingA58.taskFrameName() + vm.$i18n('KHA003_113'),
                                    vm.taskFrameSettingA59.taskFrameName() + vm.$i18n('KHA003_112'),
                                    vm.taskFrameSettingA59.taskFrameName() + vm.$i18n('KHA003_113'),
                                    vm.taskFrameSettingA510.taskFrameName() + vm.$i18n('KHA003_112'),
                                    vm.taskFrameSettingA510.taskFrameName() + vm.$i18n('KHA003_113'),
                                    '総労働時間'
                                ]
                            );
                            data.workDetailDataList.forEach((data: any) => {
                                let affWorkPlace = this.getcodeAndName(data.affWorkplaceId, masterNameInfo.affWorkplaceInfoList, 0);
                                let workPlace = this.getcodeAndName(data.workplaceId, masterNameInfo.affWorkplaceInfoList, 1);
                                let empInfo = this.getcodeAndName(data.employeeId, masterNameInfo.employeeInfoList, 2);
                                let task1 = this.getcodeAndName(data.workCode1, masterNameInfo.task1List, null);
                                let task2 = this.getcodeAndName(data.workCode2, masterNameInfo.task2List, null);
                                let task3 = this.getcodeAndName(data.workCode3, masterNameInfo.task3List, null);
                                let task4 = this.getcodeAndName(data.workCode4, masterNameInfo.task4List, null);
                                let task5 = this.getcodeAndName(data.workCode5, masterNameInfo.task5List, null);
                                rows.push([
                                    empInfo ? empInfo.employeeCode : '',
                                    empInfo ? empInfo.employeeName : '',
                                    data.date,
                                    affWorkPlace ? affWorkPlace.workplaceCode : '',
                                    affWorkPlace ? affWorkPlace.workplaceName : '',
                                    workPlace ? workPlace.workplaceCode : '',
                                    workPlace ? workPlace.workplaceName : '',
                                    data.supportWorkFrameNo,
                                    task1 ? task1.code : '',
                                    task1 ? task1.taskName : '',
                                    task2 ? task2.code : '',
                                    task2 ? task2.taskName : '',
                                    task3 ? task3.code : '',
                                    task3 ? task3.taskName : '',
                                    task4 ? task4.code : '',
                                    task4 ? task4.taskName : '',
                                    task5 ? task5.code : '',
                                    task5 ? task5.taskName : '',
                                    data.totalWorkingHours
                                ])
                            });
                            var csvContent = rows.join("\n");
                            var link = window.document.createElement("a");
                            link.setAttribute("href", "data:text/csv;charset=utf-8,%EF%BB%BF" + encodeURI(csvContent));
                            var currentdate = new Date();
                            var pathSuffix = currentdate.getFullYear() + ""
                                + (currentdate.getMonth() + 1) + ""
                                + currentdate.getDate() + "  "
                                + currentdate.getHours() + ""
                                + currentdate.getMinutes() + ""
                                + currentdate.getSeconds()
                            link.setAttribute("download", "全て出力_" + pathSuffix + ".csv");
                            link.click();
                        }
                    })
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
                code:vm.currentCode()
            };
            vm.$window.storage('kha003AShareData', shareData).then(() => {
                vm.$window.modal("/view/kha/003/b/index.xhtml").then(() => {

                });
            });
        }

        /**
         * function for get code and name from the last list
         *
         * @param identifier
         * @param taskList
         * @param type         *
         * @author rafiqul.islam
         * */
        private getcodeAndName(identifier: any, taskList: any, type: any) {
            let info = null;
            identifier = identifier.trim();
            for (let element of taskList) {
                //check aff work place
                if (type == 0 && element.workplaceId === identifier) {
                    info = element;
                    break;
                }
                // check work place
                if (type == 1 && element.workplaceId === identifier) {
                    info = element;
                    break;
                }
                // check employee
                if (type == 2 && element.sid === identifier) {
                    info = element;
                    break;
                }
                // check task1~task5
                if (type == null && element.code === identifier) {
                    info = element;
                    break;
                }
            }
            return info;
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


