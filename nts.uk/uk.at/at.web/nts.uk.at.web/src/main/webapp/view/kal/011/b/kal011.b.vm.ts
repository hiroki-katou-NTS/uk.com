module nts.uk.at.kal011.B {

    const PATH_API = {}
    const PATH_EXPORT_EXCELL = {
        exportAlarmData: "at/function/alarm/kal/011/export-alarm-data/"
    }

    @bean()
    export class Kal011BViewModel extends ko.ViewModel {
        columns: Array<any>;
        currentSelectedRow: KnockoutObservable<any>;
        eralRecord: KnockoutObservable<number>;
        eralRecordText: KnockoutObservable<string>;
        dataSource: Array<ValueExtractAlarmDto> = [];
        flgActive: KnockoutObservable<boolean>;
        processId: string;
        parentData: any;

        constructor(props: any) {
            super();
            let vm = this;
            vm.parentData = nts.uk.ui.windows.getShared("KAL011BModalData");
            vm.processId = vm.parentData.processId;
            vm.eralRecord = ko.observable(100);//TODO eralRecord
            vm.eralRecordText = ko.observable("text");//TODO eralRecordText
            vm.currentSelectedRow = ko.observable(null);
            vm.flgActive = ko.observable(true);
            vm.columns = [
                {headerText: '', key: 'guid', width: 1, hidden: true},
                {headerText: vm.$i18n('KAL011_13'), key: 'workplaceCode', width: 85},
                {headerText: vm.$i18n('KAL011_14'), key: 'workplaceName', width: 245},
                {headerText: vm.$i18n('KAL011_15'), key: 'date', width: 130},
                {headerText: vm.$i18n('KAL011_16'), key: 'categoryName', width: 150},
                {headerText: vm.$i18n('KAL011_17'), key: 'alarmValue', width: 60},
                {headerText: vm.$i18n('KAL011_18'), key: 'alarmItem', width: 115},
                {headerText: vm.$i18n('KAL011_19'), key: 'checkTargetValue', width: 100},
                {headerText: vm.$i18n('KAL011_120'), key: 'comment', width: 260}
            ];
            // mock data
            for (let i = 1; i < 1000; i++) {
                vm.dataSource.push(new ValueExtractAlarmDto('guid' + i, '' + i, 'co' + i, 'wpname' + i, new Date().toDateString(), i, 'catageory' + i, 'alarm' + i, 'item' + i, 'checkvalue' + i, 'commetes test' + i));
            }
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.startPage().done(() => {
                $('.ui-igedit-input').attr("tabindex", "-1");
                $('.ui-iggrid-paging > *').attr("tabindex", "-1");
            });
        }

        /**
         * save file excel
         * @param processId
         * @param alarmCode
         * @return JQueryPromise
         */
        exportAlarmData(processId: string, alarmCode: string): JQueryPromise<any> {
            return nts.uk.request.exportFile(PATH_EXPORT_EXCELL.exportAlarmData + processId + '/' + alarmCode);
        }

        /**
         * get RecordTex
         * @param param
         * @return string
         */
        getEralRecordText(param: any): string {
            if (_.isNil(param.listAlarmExtraValueWkReDto) || param.totalErAlRecord <= 1000) {
                return "";
            }
            return nts.uk.resource.getMessage("Msg_1524", [1000]);
        }

        /**
         * export file excel
         * @return void
         */
        exportExcel(): void {
            const vm = this;
            vm.$blockui("invisible");
            vm.exportAlarmData(vm.processId, vm.parentData.selectedCode).done(() => {
            }).fail((errExcel) => {
                vm.$dialog.error(errExcel);
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        /**
         * send mail to destination
         * @return void
         */
        sendEmail(): void {
            let vm = this;
            let modalData = {processId: vm.processId};
            vm.$window.storage('KAL011CModalData', modalData).done(() => {
                vm.$window.modal('/view/kal/011/c/index.xhtml')
                    .then((result: any) => {

                    });
            });
        }

        /**
         * close modal
         * @return void
         */
        closeDialog() {
            nts.uk.ui.windows.close();
        }

        /**
         * action perform for igGrid start
         * @return JQueryPromise
         */
        startPage(): JQueryPromise<any> {
            let vm = this;
            let dfd = $.Deferred();
            $("#grid").igGrid({
                height: '450px',
                dataSource: vm.dataSource,
                primaryKey: 'guid',
                columns: vm.columns,
                tabIndex: -1,
                features: [
                    {name: 'Paging', type: 'local', pageSize: 20},
                    {
                        name: "Tooltips",
                        columnSettings: [
                            {columnKey: "workplaceName", allowTooltips: true}
                        ]
                    }
                ],
                enableTooltip: true
            });
            dfd.resolve();
            return dfd.promise();
        }
    }

    class ValueExtractAlarmDto {
        guid: string;
        workplaceId: string;
        /* 職場コード*/
        workplaceCode: string;
        /* 職場名*/
        workplaceName: string;
        /* 日付*/
        date: string;
        categoryId: number;
        /* カテゴリ*/
        categoryName: string;
        /* アラーム値*/
        alarmValue: string;
        /* アラーム項目名*/
        alarmItem: string;
        /* チェック対象値*/
        checkTargetValue: string;
        /* コメント*/
        comment: string;

        constructor(guid: string,
                    workplaceId: string,
                    workplaceCode: string,
                    workplaceName: string,
                    date: string,
                    categoryId: number,
                    categoryName: string,
                    alarmValue: string,
                    alarmItem: string,
                    checkTargetValue: string,
                    comment: string) {
            this.guid = guid;
            this.workplaceId = workplaceId;
            this.workplaceCode = workplaceCode;
            this.workplaceName = workplaceName;
            this.date = date;
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.alarmValue = alarmValue;
            this.alarmItem = alarmItem;
            this.checkTargetValue = checkTargetValue;
            this.comment = comment;
        }
    }
}