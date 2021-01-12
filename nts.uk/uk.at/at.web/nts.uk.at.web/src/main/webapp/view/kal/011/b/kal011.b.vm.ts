module nts.uk.at.kal011.B {
    const API = {
        GET: "at/function/alarm-workplace/alarm-list/get-alarm-list",
        EXPORT: "at/function/alarm-workplace/alarm-list/export-alarm-list"
    }

    @bean()
    export class Kal011BViewModel extends ko.ViewModel {

        columns: Array<any>;
        currentSelectedRow: KnockoutObservable<any>;
        dataSource: Array<ExtractAlarmDto> = [];
        flgActive: KnockoutObservable<boolean>;
        processId: string;
        alarmPatternCode: string;
        alarmPatternName: string;

        constructor(props: any) {
            super();
            let vm = this;

            vm.currentSelectedRow = ko.observable(null);
            vm.flgActive = ko.observable(true);
            vm.columns = [
                { headerText: '', key: 'recordId', width: 1, hidden: true },
                /**B3_2**/
                { headerText: vm.$i18n('KAL011_13'), key: 'workplaceCode', width: 100 },
                /**B3_3**/
                { headerText: vm.$i18n('KAL011_14'), key: 'workplaceName', width: 100 },
                /**B3_4**/
                { headerText: vm.$i18n('KAL011_15'), key: 'alarmValueDate', width: 135 },
                /**B3_5**/
                { headerText: vm.$i18n('KAL011_16'), key: 'categoryName', width: 150 },
                /**B3_6**/
                { headerText: vm.$i18n('KAL011_17'), key: 'alarmValueMessage', width: 200 },
                /**B3_7**/
                { headerText: vm.$i18n('KAL011_18'), key: 'alarmItemName', width: 140 },
                /**B3_8**/
                { headerText: vm.$i18n('KAL011_19'), key: 'checkTargetValue', width: 200 },
                /**B3_9**/
                { headerText: vm.$i18n('KAL011_20'), key: 'comment', width: 200 }
            ];
        }

        created() {
            const vm = this;
            vm.$blockui("invisible");
            vm.$window.storage('KAL011BModalData').done((data: any) => {
                vm.processId = data.processId;
                vm.alarmPatternCode = data.alarmPatternCode;
                vm.alarmPatternName = data.alarmPatternName;
                $("#grid").igGrid({
                    height: '450px',
                    dataSource: vm.dataSource,
                    primaryKey: 'recordId',
                    columns: vm.columns,
                    tabIndex: 4,
                    features: [
                        { name: 'Paging', type: 'local', pageSize: 20 },
                        {
                            name: "Tooltips",
                            columnSettings: [
                                { columnKey: "workplaceName", allowTooltips: true }
                            ]
                        }
                    ],
                    enableTooltip: true
                });
                vm.$ajax(API.GET + "/" + vm.processId).done((res: Array<ExtractAlarmDto>) => {
                    vm.dataSource = res;
                    if (res && res.length == 0) {
                        vm.flgActive(false);
                    }
                    $("#grid").igGrid("option", "dataSource", vm.dataSource);
                }).fail((err: any) => vm.$dialog.error(err)).always(() => vm.$blockui("clear"));
            })
           
            _.extend(window, { vm });
        }

        exportExcel() {
            const vm = this;
            vm.$blockui("invisible");
            let parram: any = {
                processId: vm.processId,
                alarmPatternCode: vm.alarmPatternCode,
                alarmPatternName: vm.alarmPatternName
            };
            nts.uk.request.exportFile(API.EXPORT, parram).done(() => {
            }).fail((errExcel) => vm.$dialog.error(errExcel)).always(() => vm.$blockui("clear"));
        }

        sendEmail() {
            let vm = this;
            let modalData = { data: vm.dataSource, currentCode: vm.alarmPatternCode };
            vm.$window.storage('KAL011CModalData', modalData).done(() => {
                vm.$window.modal('/view/kal/011/c/index.xhtml')
                    .then((result: any) => {

                    });
            });
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }

    export interface ExtractAlarmDto {
        recordId: string;
        alarmValueMessage: string;
        alarmValueDate: string;
        alarmItemName: string;
        categoryName: string;
        checkTargetValue: string;
        category: number;
        startTime: any;
        comment: string;
        workplaceId: string;
        workplaceCode: string;
        workplaceName: string;
        hierarchyCode: string
    }
}