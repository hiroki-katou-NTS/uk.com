module nts.uk.at.kal011.c {


    const PATH_API = {
        //TODO wrote api path
        sendEmail: "",
        getWorkPlace: "",
    }

    @bean()
    export class Kal011CViewModel extends ko.ViewModel {

        columns: Array<any>;//nts.uk.ui.NtsGridListColumn
        //data
        workPlaceList: Array<WorkPlace> = [];
        processId: string;

        constructor(props: any) {
            super();
            const vm = this;
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.$window.storage('KAL011CModalData').done((data) => {
                vm.processId = data.processId;
                vm.getWorkPlaceList();
            });
            //mock data
            for (let i = 1; i < 100; i++) {
                vm.workPlaceList.push(new WorkPlace(false, 'id' + i, 'code' + i, 'name' + i));
            }
            vm.startPage();
        }

        /**
         * This function is responsible to close the modal         *
         * @return type void         *
         */
        closeDialog(): any {
            nts.uk.ui.windows.close();
        }

        /**
         *
         * function to get table data
         * @return JQueryPromise
         */
        getWorkPlaceList(): JQueryPromise<any> {
            let vm = this;
            let dfd = $.Deferred();
            vm.$blockui("invisible");
            let command = {
                processId: vm.processId,
            };
            vm.$ajax(PATH_API.getWorkPlace, command).then((data) => {
                //TODO map table with vm.workPlaceList
            }).fail(function (error) {
                vm.$dialog.error({messageId: error.messageId});
            }).always(() => {
                vm.$blockui("clear");
            });
            return dfd.promise();
        }

        /**
         *
         * functiton start pagea
         * @return JQueryPromise
         */
        startPage(): JQueryPromise<any> {
            let vm = this;
            let dfd = $.Deferred();
            vm.columns = [
                {headerText: '', dataType: 'string', key: 'GUID'},
                {
                    headerText: vm.$i18n('KAL011_24'),
                    dataType: 'boolean',
                    key: 'isSendTo',
                    showHeaderCheckbox: true,
                    width: 150,
                    ntsControl: 'isSendTo'
                },
                {headerText: vm.$i18n('KAL011_25'), key: 'workplaceCode', width: 150},
                {headerText: vm.$i18n('KAL011_26'), key: 'workplaceName', width: 260}
            ];
            $("#grid").ntsGrid({
                height: '300px',
                width: 450,
                dataSource: vm.workPlaceList,
                hidePrimaryKey: true,
                primaryKey: 'GUID',
                virtualization: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: vm.columns,
                features: [],
                ntsControls: [
                    {
                        name: 'isSendTo',
                        options: {value: 1, text: ''},
                        optionsValue: 'value',
                        optionsText: 'text',
                        controlType: 'CheckBox',
                        enable: true
                    }
                ]
            });
            return dfd.promise();
        }

        /**
         *
         * function to send mail
         * @return JQueryPromise
         */
        doSendEmail() {
            const vm = this;
            vm.$blockui("invisible");
            let command = {
                //TODO write params matching with server side logic
            };
            vm.$ajax(PATH_API.sendEmail, command).done((data) => {
                vm.$dialog.info({messageId: "Msg_207"})
                    .then(() => {
                        //TODO write business logic
                        vm.closeDialog();
                    });
            }).fail(function (error) {
                vm.$dialog.error({messageId: "Msg_1169"});
            }).always(() => {
                vm.closeDialog();
                vm.$blockui("clear");
            });
        }
    }

    class WorkPlace {
        GUID: string;
        isSendTo: boolean;
        workplaceId: string;
        workplaceCode: string;
        workplaceName: string;

        constructor(isSendTo: boolean, workplaceId: string, workplaceCode: string, workplaceName: string) {
            this.GUID = nts.uk.util.randomId().replace(/-/g, "_");
            this.isSendTo = isSendTo;
            this.workplaceId = workplaceId;
            /* 職場コード*/
            this.workplaceCode = workplaceCode;
            /* 職場名*/
            this.workplaceName = workplaceName;
        }
    }
}