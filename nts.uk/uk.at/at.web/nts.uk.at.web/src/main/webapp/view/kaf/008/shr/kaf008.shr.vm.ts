/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kaf008_ref.shr.viewmodel {
    import BusinessTripInfoOutput = nts.uk.at.view.kaf008_ref.a.viewmodel.BusinessTripInfoOutput;

    @component({
        name: 'kaf008-share',
        template: `<div id="kaf008-share">
                        <div class="flex valign-center A5">
                            <div id="A5_1" data-bind="ntsFormLabel: {text: $i18n('KAF008_20')}"></div>
                            <div id="A5_2">
                                <span  data-bind="i18n : 'KAF008_21'"></span>
                            </div>
                            <input tab-index="4" id="A5_3" data-bind="ntsTimeWithDayEditor: {name: '#[KAF008_21]',
                                            value: departureTime, enable: enableInput, required: false, option: {timeWithDay: true}}"/>
                            <div id="A5_4">
                                <span  data-bind="i18n : 'KAF008_22'"></span>
                            </div>
                            <input tab-index="5" id="A5_5" data-bind="ntsTimeWithDayEditor: {name: '#[KAF008_22]',
                                            value: returnTime, enable: enableInput, required: false, option: {timeWithDay: true}}"/>
                        </div>
                        <div id="A6_1">
                            <div class="label" data-bind="if: comment()">
                                <span data-bind="text: comment().comment, style: { color: comment().colorCode, 'font-weight': comment().bold ? 'bold' : 'normal' }"></span>
                            </div>
                        </div>
                        <div id="A10" tab-index="6">
                            <table id="fixed-table">
                                <colgroup>
                                    <col width="120px"/>
                                    <col width="60px"/>
                                    <col width="130px"/>
                                    <col width="60px"/>
                                    <col width="130px"/>
                                    <col width="100px"/>
                                    <col width="100px"/>
                                </colgroup>
                                <THEAD>
                                <tr>
                                    <th class="ui-widget-header" data-bind="i18n : 'KAF008_24'"></th>
                                    <th class="ui-widget-header" data-bind="i18n : 'KAF008_25'"></th>
                                    <th class="ui-widget-header" data-bind="i18n : 'KAF008_26'"></th>
                                    <th class="ui-widget-header" data-bind="i18n : 'KAF008_27'"></th>
                                    <th class="ui-widget-header" data-bind="i18n : 'KAF008_28'"></th>
                                    <th class="ui-widget-header" data-bind="i18n : 'KAF008_29'"></th>
                                    <th class="ui-widget-header" data-bind="i18n : 'KAF008_30'"></th>
                                </tr>
                                </THEAD>
                    
                                <TBODY data-bind="foreach: items">
                                    <tr>
                                        <td>
                                            <div class="div_line" >
                                                <span data-bind="text: dateDisp, style: {color: dateColor}"></span>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="div_line code" id="A10_D2" tab-index="7">
                                                <input data-bind="ntsTextEditor: {
                                                name: '#[KAF008_31]',
                                                value: wkTypeCd,
                                                enable: $parent.enableInput(),
                                                required: true,
                                                constraint: 'WorkTypeCode'
                                            }, attr: {id: id + '-wkCode'}"/>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="div_line A10_D3" data-bind="if: $parent.enableInput()">
                                                <a tab-index="8" class="hyperlink" data-bind="text: wkTypeName, click: $parent.openDialogKdl003.bind($parent, $data)"></a>
                                            </div>
                                            <div class="div_line A10_D3" data-bind="if: !$parent.enableInput()">
                                                <span tab-index="8" data-bind="text: wkTypeName"></span>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="div_line code" id="A10_D4">
                                                <input tab-index="9" data-bind="ntsTextEditor: {
                                                name: '#[KAF008_33]',
                                                value: wkTimeCd,
                                                enable: $parent.enableInput(),
                                                constraint: 'WorkTimeCode'
                                            }, attr: {id: id + '-tmCode'}"/>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="div_line A10_D5" data-bind="if: $parent.enableInput()">
                                                <a tab-index="10" class="hyperlink" data-bind="text: wkTimeName, click: $parent.openDialogKdl003.bind($parent, $data)"></a>
                                            </div>
                                            <div class="div_line A10_D5" data-bind="if: !$parent.enableInput()">
                                                <span tab-index="10" data-bind="text: wkTimeName"></span>
                                            </div>
                                        </td>
                                        <td>
                                            <div id="A10_D6" class="div_line time" >
                                                <input data-bind="ntsTimeWithDayEditor: {
                                                            name: '#[KAF008_35]',
                                                            constraint:'TimeWithDayAttr',
                                                            value: start,
                                                            enable: $parent.enableInput(),
                                                            readonly: false,
                                                            required: false
                                                            },
                                                            attr: {id: id}"/>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="div_line time" id="A10_D7">
                                                <input data-bind="ntsTimeWithDayEditor: {
                                                            name: '#[KAF008_36]',
                                                            constraint:'TimeWithDayAttr',
                                                            value: end,
                                                            enable: $parent.enableInput(),
                                                            readonly: false,
                                                            required: false
                                                            }"/>
                                            </div>
                                        </td>
                                    </tr>
                                </TBODY>
                            </table>
                        </div>
                    </div>`
    })

    class Kaf008ShareViewModel extends ko.ViewModel {

        departureTime: KnockoutObservable<number> = ko.observable(null);
        returnTime: KnockoutObservable<number> = ko.observable(null);
        comment: KnockoutObservable<Comment> = ko.observable(null);
        items: KnockoutObservableArray<TripContentDisp> = ko.observableArray([]);
        workTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);
        holidayTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);
        businessTripContent: KnockoutObservable<any> = ko.observable(null);
        businessTripOutput: KnockoutObservable<BusinessTripInfoOutput> = ko.observable(null);
        dataFetch: KnockoutObservable<any> = ko.observable(null);
        mode: number = Mode.New;
        enableInput: KnockoutObservable<boolean> = ko.observable(true);

        created(params: any) {
            const vm = this;
            vm.dataFetch = params.dataFetch;
            vm.mode = params.mode;

            vm.departureTime = vm.dataFetch().businessTripContent.departureTime;
            vm.returnTime = vm.dataFetch().businessTripContent.returnTime;

            if (params.mode == Mode.New) {
                vm.startNewMode();
            } else {
                vm.startEditMode();
            }
        }

        startNewMode() {
            const vm = this;
            vm.dataFetch.subscribe(value => {
                if (value) {

                    const tripOutput = value.businessTripOutput;
                    const tripContent = value.businessTripContent;
                    const {setting} = tripOutput;

                    vm.businessTripOutput(tripOutput);
                    vm.workTypeCds(tripOutput.workdays);
                    vm.holidayTypeCds(tripOutput.holidays);

                    if (setting && setting.appCommentSet) {
                        vm.comment({
                            comment: setting.appCommentSet.comment,
                            colorCode: setting.appCommentSet.colorCode,
                            bold: setting.appCommentSet.bold == 1 ? true : false
                        });
                    }

                    let checkContent = _.filter(tripOutput.businessTripActualContent, i => i.opAchievementDetail == null);
                    if (_.isEmpty(checkContent)) {
                        let lstContent = _.map(tripOutput.businessTripActualContent, function (content, index) {
                            let eachContent = new TripContentDisp(
                                content.date,
                                content.opAchievementDetail.workTypeCD,
                                content.opAchievementDetail.opWorkTypeName || "マスタ未登録",
                                content.opAchievementDetail.workTimeCD,
                                content.opAchievementDetail.opWorkTimeName,
                                content.opAchievementDetail.opWorkTime,
                                content.opAchievementDetail.opLeaveTime
                            );
                            eachContent.wkTypeCd.subscribe(code => {
                                vm.changeWorkTypeCode(tripOutput, content, code, index);
                            });
                            eachContent.wkTimeCd.subscribe(code => {
                                vm.changeWorkTimeCode(tripOutput, content, code, index);
                            });
                            eachContent.start.subscribe(startValue => {
                                content.opAchievementDetail.opWorkTime = startValue;
                            });
                            eachContent.end.subscribe(endValue => {
                                content.opAchievementDetail.opLeaveTime = endValue;
                            });
                            return eachContent;
                        });
                        vm.items(lstContent);
                    }
                };
            });
        }

        startEditMode() {
            const vm = this;
            vm.dataFetch.subscribe(value => {
                if (value) {

                    const tripOutput = value.businessTripOutput;
                    const tripContent = value.businessTripContent;
                    const setting = tripOutput.setting;

                    value.businessTripOutput.appDispInfoStartup.appDetailScreenInfo.outputMode == 1 ? vm.enableInput(true) : vm.enableInput(false);

                    vm.businessTripOutput(tripOutput);
                    vm.workTypeCds(tripOutput.workdays);
                    vm.holidayTypeCds(tripOutput.holidays);
                    vm.departureTime(tripContent.departureTime());
                    vm.returnTime(tripContent.returnTime());

                    if (setting && setting.appCommentSet) {
                        vm.comment({
                            comment: setting.appCommentSet.comment,
                            colorCode: setting.appCommentSet.colorCode,
                            bold: setting.appCommentSet.bold == 1 ? true : false
                        });
                    }

                    const contentDisp = _.map(tripContent.tripInfos, function (data, index) {

                        let contentTrip = new TripContentDisp(
                            data.date,
                            data.wkTypeCd,
                            data.wkTypeName,
                            data.wkTimeCd,
                            data.wkTimeName,
                            data.startWorkTime,
                            data.endWorkTime
                        );

                        contentTrip.wkTypeCd.subscribe(code => {
                            vm.changeTypeCodeScreenB(tripOutput, data, code, index);
                        });
                        contentTrip.wkTimeCd.subscribe(code => {
                            vm.changeWorkTimeCodeScreenB(tripOutput, data, code, index);
                        });
                        contentTrip.start.subscribe(startValue => {
                            data.startWorkTime = startValue;
                        });
                        contentTrip.end.subscribe(endValue => {
                            data.endWorkTime = endValue;
                        });
                        return contentTrip;
                    });
                    vm.items(contentDisp);
                }
            });
        }

        mounted() {
            const vm = this;

            $("#fixed-table").ntsFixedTable({});

        }

        // 勤務種類コードを入力する
        changeWorkTypeCode(data: BusinessTripOutput, currentContent: any, wkCode: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);

            let command = {
                date: currentContent.date,
                businessTripInfoOutputDto: businessTripInfoOutputDto,
                typeCode: wkCode
            };
            let currentRow = vm.dataFetch().businessTripOutput.businessTripActualContent[index].opAchievementDetail;

            vm.clearWorkTypeErrorByDate(currentContent.date);
            vm.$blockui("show");
            vm.$validate([
                //'#kaf008-share #A10_D2',
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changeWorkTypeCode, command);
                }
            }).done(res => {
                if (res) {
                    let workTypeAfterChange = res.infoAfterChange;
                    let InfoChanged = _.findIndex(workTypeAfterChange, {date: currentContent.date});
                    let workCodeChanged = workTypeAfterChange[InfoChanged].workTypeDto.workTypeCode;
                    let workNameChanged = workTypeAfterChange[InfoChanged].workTypeDto.name;

                    currentRow.workTypeCD = workCodeChanged;
                    currentRow.opWorkTypeName = workNameChanged;
                    vm.items()[index].wkTypeCd(workCodeChanged);
                    vm.items()[index].wkTypeName(workNameChanged);

                    // vm.dataFetch.valueHasMutated();
                    vm.focusWorkTimeByDate(currentContent.date);

                }
            }).fail(err => {
                currentRow.workTypeCD = command.typeCode;
                currentRow.opWorkTypeName = "なし";
                vm.items()[index].wkTypeCd(command.typeCode);
                vm.items()[index].wkTypeName("なし");

                vm.handleError(err);

            }).always(() => vm.$blockui("hide"));
        }

        // 就業時間帯コードを入力する
        changeWorkTimeCode(data: BusinessTripOutput, currentContent: any, timeCode: string, index: number) {
            const vm = this;

            let typeCode = currentContent.opAchievementDetail.workTypeCD;
            let startWorkTime = currentContent.opAchievementDetail.opWorkTime;
            let endWorkTime = currentContent.opAchievementDetail.opLeaveTime;
            let date = currentContent.date;

            let businessTripInfoOutputDto = ko.toJS(data);
            let currentRow = vm.dataFetch().businessTripOutput.businessTripActualContent[index].opAchievementDetail;
            let command = {
                date,
                businessTripInfoOutputDto,
                typeCode,
                timeCode,
                startWorkTime,
                endWorkTime
            };

            vm.clearWorkTimeErrorByDate(date);
            vm.$blockui("show");
            vm.$validate([
                // '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changWorkTimeCode, command);
                }
            }).done(res => {
                if (res && res.name) {
                    currentRow.workTimeCD = timeCode;
                    currentRow.opWorkTimeName = res.name;
                    vm.items()[index].wkTimeCd(timeCode);
                    vm.items()[index].wkTimeName(res.name);
                } else {
                    currentRow.workTimeCD = "";
                    currentRow.opWorkTimeName = "なし";
                    vm.items()[index].wkTimeCd("");
                    vm.items()[index].wkTimeName("なし");
                }

                if (res.msg) {
                    vm.$dialog.error({messageId: res.msg}).then(() => $('#' + date.replace(/\//g, "")).focus());
                } else {
                    $('#' + date.replace(/\//g, "")).focus();
                }

            }).fail(err => {
                currentRow.workTimeCD = timeCode;
                currentRow.opWorkTimeName = "なし";
                vm.items()[index].wkTimeCd(timeCode);
                vm.items()[index].wkTimeName("なし");

                vm.handleError(err);

            }).always(() => vm.$blockui("hide"));
        }

        // 勤務種類コードを入力する
        changeTypeCodeScreenB(data: BusinessTripOutput, content: any, codeChanged: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let currentRow = vm.dataFetch().businessTripContent.tripInfos[index];
            let command = {
                date: content.date,
                businessTripInfoOutputDto: businessTripInfoOutputDto,
                typeCode: codeChanged
            };

            vm.clearWorkTypeErrorByDate(command.date);
            vm.$blockui("show");
            vm.$validate([
                // '#kaf008-share #A10_D2'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changeWorkTypeCode, command);
                }
            }).done(res => {
                if (res) {
                    let workTypeAfterChange = res.infoAfterChange;
                    let InfoChanged = _.findIndex(workTypeAfterChange, {date: content.date});
                    let workCodeChanged = workTypeAfterChange[InfoChanged].workTypeDto.workTypeCode;
                    let workNameChanged = workTypeAfterChange[InfoChanged].workTypeDto.name;

                    currentRow.wkTypeCd = workCodeChanged;
                    currentRow.wkTypeName = workNameChanged;
                    vm.items()[index].wkTypeCd(workCodeChanged);
                    vm.items()[index].wkTypeName(workNameChanged);

                }
            }).fail(err => {
                let param;

                currentRow.wkTypeCd = "";
                currentRow.wkTypeName = "なし";
                vm.items()[index].wkTypeCd(command.typeCode);
                vm.items()[index].wkTypeName("なし");

                vm.handleError(err);

            }).always(() => vm.$blockui("hide"));
        }

        // 就業時間帯コードを入力する
        changeWorkTimeCodeScreenB(output: BusinessTripOutput, data: any, codeChanged: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(output);
            let contentChanged = vm.dataFetch().businessTripContent.tripInfos[index];
            let startWorkTime = data.startWorkTime;
            let endWorkTime = data.endWorkTime;
            let command = {
                date: data.date,
                businessTripInfoOutputDto: businessTripInfoOutputDto,
                typeCode: data.wkTypeCd,
                timeCode: codeChanged,
                startWorkTime,
                endWorkTime
            };

            vm.clearWorkTimeErrorByDate(command.date);
            vm.$blockui("show");
            vm.$validate([
                // '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changWorkTimeCode, command);
                }
            }).done(res => {

                if (res && res.name) {
                    contentChanged.wkTimeCd = codeChanged;
                    contentChanged.wkTimeName = res.name;
                    vm.items()[index].wkTimeCd(codeChanged);
                    vm.items()[index].wkTimeName(res.name);
                } else {
                    contentChanged.wkTimeCd = "";
                    contentChanged.wkTimeName = "なし";
                }

                if (res.msg) {
                    vm.$dialog.error({messageId: res.msg}).then(() => $('#' + command.date.replace(/\//g, "")).focus());
                } else {
                    $('#' + command.date.replace(/\//g, "")).focus();
                }

            }).fail(err => {
                let param;

                contentChanged.wkTimeCd = "";
                contentChanged.wkTimeName = "なし";
                vm.items()[index].wkTimeCd(codeChanged);
                vm.items()[index].wkTimeName("なし");

                vm.handleError(err);

            }).always(() => vm.$blockui("hide"));
        }

        // 勤務就業の選択を実行する
        openDialogKdl003(data: TripContentDisp) {
            const vm = this;
            let dispFlag: boolean = true;
            let selectedDate = data.date;
            let selectedWorkTypeCode = data.wkTypeCd();
            let selectedWorkTimeCode = data.wkTimeCd();
            let listWorkCode = [];

            let selectedIndex = _.findIndex(ko.toJS(vm.items), {date: data.date});

            let listWkTime = vm.businessTripOutput().appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
            let listWkTimeCd = _.map(listWkTime, function (obj) {
                return obj.worktimeCode
            });


            let command = {
                selectedDate: selectedDate,
                businessTripInfoOutputDto: ko.toJS(vm.businessTripOutput)
            };

            vm.$ajax(API.startKDL003, command).done(res => {
                console.log(res);
                dispFlag = res;
            }).then(() => {
                if (dispFlag) {
                    listWorkCode = _.map(vm.workTypeCds(), function (obj) {
                        return obj.workTypeCode
                    });
                } else {
                    listWorkCode = _.map(vm.holidayTypeCds(), function (obj) {
                        return obj.workTypeCode
                    });
                }

                vm.$window.storage('parentCodes', {
                    workTypeCodes: listWorkCode,
                    selectedWorkTypeCode: selectedWorkTypeCode,
                    workTimeCodes: listWkTimeCd,
                    selectedWorkTimeCode: selectedWorkTimeCode,
                    showNone: !dispFlag
                });

                nts.uk.ui.windows.setShared('parentCodes', {
                    workTypeCodes: listWorkCode,
                    selectedWorkTypeCode: selectedWorkTypeCode,
                    workTimeCodes: listWkTimeCd,
                    selectedWorkTimeCode: selectedWorkTimeCode,
                    showNone: !dispFlag
                }, true);

                vm.$errors("clear");

                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function (): any {
                    //view all code of selected item
                    let rs = nts.uk.ui.windows.getShared('childData');
                    if (rs) {
                        let currentRow;
                        if (vm.mode == Mode.New) {
                            currentRow = vm.dataFetch().businessTripOutput.businessTripActualContent[selectedIndex].opAchievementDetail;
                            currentRow.workTypeCD = rs.selectedWorkTypeCode;
                            currentRow.opWorkTypeName = rs.selectedWorkTypeName;
                            currentRow.workTimeCD = rs.selectedWorkTimeCode;
                            currentRow.opWorkTimeName = rs.selectedWorkTimeName;
                            currentRow.opWorkTime = rs.first.start;
                            currentRow.opLeaveTime = rs.first.end;

                        } else {
                            currentRow = vm.dataFetch().businessTripContent.tripInfos[selectedIndex];
                            currentRow.wkTypeCd = rs.selectedWorkTypeCode;
                            currentRow.wkTypeName = rs.selectedWorkTypeName;
                            currentRow.wkTimeCd = rs.selectedWorkTimeCode;
                            currentRow.wkTimeName = rs.selectedWorkTimeName;
                            currentRow.startWorkTime = rs.first.start;
                            currentRow.endWorkTime = rs.first.end;
                        }

                        vm.items()[selectedIndex].wkTypeCd(rs.selectedWorkTypeCode);
                        vm.items()[selectedIndex].wkTypeName(rs.selectedWorkTypeName);
                        vm.items()[selectedIndex].wkTimeCd(rs.selectedWorkTimeCode);
                        vm.items()[selectedIndex].wkTimeName(rs.selectedWorkTimeName);
                        vm.items()[selectedIndex].start(rs.first.start);
                        vm.items()[selectedIndex].end(rs.first.end);
                    }

                    setTimeout(() => {
                        return $('#' + data.id).focus();
                    }, 50);

                });
            });

        }

        handleError(err: any) {
            const vm = this;
            let param;

            switch (err.messageId) {
                case "Msg_457":
                case "Msg_1329": {
                    let id = '#' + err.parameterIds[0].replace(/\//g, "") + '-wkCode';
                    vm.$errors({
                        [id]: err
                    });
                    break;
                }

                case "Msg_1685":
                case "Msg_23":
                case "Msg_24": {
                    let id: string = '#' + err.parameterIds[0].replace(/\//g, "") + '-tmCode';
                    vm.$errors({
                        [id]: err
                    });
                    break;
                }

                default: {
                    let messageId, messageParams;
                    if(err.errors) {
                        let errors = err.errors;
                        messageId = errors[0].messageId;
                    } else {
                        messageId = err.messageId;
                        messageParams = [err.parameterIds.join('、')];
                    }
                    vm.$dialog.error({ messageId: messageId, messageParams: messageParams }).then(() => {
                        if (err.messageId == 'Msg_197') {
                            location.reload();
                        }
                    });;
                    break;
                }
            }
        }

        clearWorkTypeErrorByDate(date: any) {
            $('#' + date.replace(/\//g, "") + '-wkCode').ntsError('clear');
        }

        clearWorkTimeErrorByDate(date: any) {
            $('#' + date.replace(/\//g, "") + '-tmCode').ntsError('clear');
        }

        focusWorkTypeByDate(date: any) {
            $('#' + date.replace(/\//g, "") + '-wkCode').focus();
        }

        focusWorkTimeByDate(date: any) {
            $('#' + date.replace(/\//g, "") + '-tmCode').focus();
        }

    }

    interface Comment {
        comment: string;
        colorCode: string;
        bold: boolean;
    }

    export interface BusinessTripOutput {
        setting: Comment;
        appDispInfoStartup: any;
        holidays: any;
        workdays: any;
        businessTripActualContent: any;
        infoBeforeChange: any;
        infoAfterChange: any;
    }

    export interface BusinessTripContent {
        departureTime: KnockoutObservable<number>;
        returnTime: KnockoutObservable<number>;
        tripInfos: Array<any>;
    }

    export interface TripInfoDetail {
        date: string;
        wkTimeCd: string;
        wkTimeName: string;
        wkTypeCd: string;
        wkTypeName: string;
        startWorkTime: number;
        endWorkTime: number;
    }

    interface TripDetail {
        date: KnockoutObservable<number>;
        wkTypeCd: KnockoutObservable<string>;
        wkTimeCd: KnockoutObservable<string>;
        startWorkTime: KnockoutObservable<number>;
        endWorkTime: KnockoutObservable<number>;
        wkTypeName: KnockoutObservable<string>;
        wkTimeName: KnockoutObservable<string>
    }

    export class TripContentDisp {
        id: string;
        date: string;
        dateDisp: string;
        dateColor: string = "initial";
        wkTypeCd: KnockoutObservable<string>;
        wkTypeName: KnockoutObservable<string>;
        wkTimeCd: KnockoutObservable<string>;
        wkTimeName: KnockoutObservable<string>;
        start: KnockoutObservable<number>;
        end: KnockoutObservable<number>;

        constructor(date: string, wkTypeCd: string, wkTypeName: string, wkTimeCd: string, wkTimeName: string, start: number, end: number) {
            this.id = date.replace(/\//g, "");
            this.date = date;
            //  moment(date, "YYYY/MM/DD").format('YYYY-MM-DD(ddd)');
            this.dateDisp = nts.uk.time.applyFormat("Short_YMDW", [date]);
            let day = moment(moment.utc(date, "YYYY/MM/DD").toISOString()).format('dddd');
            if (day == "土曜日") {
                this.dateColor = "#0000FF";
            }
            if (day == "日曜日") {
                this.dateColor = "#FF0000";
            }
            this.wkTypeCd = ko.observable(wkTypeCd || "");
            this.wkTypeName = ko.observable(wkTypeName || "なし");
            this.wkTimeCd = ko.observable(wkTimeCd || "");
            this.wkTimeName = ko.observable(wkTimeName || "なし");
            this.start = ko.observable(start);
            this.end = ko.observable(end);
        }
    }

    export interface BusinessTripInfoDetail {
        date: string;
        wkTypeCd: string;
        wkTimeCd: string;
        startWorkTime: number;
        endWorkTime: number;
    }

    const API = {
        changeWorkTypeCode: "at/request/application/businesstrip/changeWorkTypeCode",
        changWorkTimeCode: "at/request/application/businesstrip/changeWorkTimeCode",
        startKDL003: "at/request/application/businesstrip/startKDL003"
    }

    export const Mode = {
        New: 1,
        Edit: 2,
        View: 3
    };

}