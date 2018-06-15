var multiple = true;
module nts.uk.com.view.cli001.a {
    import LockOutDataDto = nts.uk.com.view.cli001.a.service.model.LockOutDataDto;

    export module viewmodel {

        export class ScreenModel {
            items: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;
            currentCodeList: KnockoutObservableArray<any>

            constructor() {
                var self = this;
                self.items = ko.observableArray([]);
                this.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText(""), key: "userId", dataType: "string", hidden: true },
                    { headerText: nts.uk.resource.getText("CLI001_12"), key: "loginId", dataType: "string", width: 100 },
                    { headerText: nts.uk.resource.getText("CLI001_13"), key: "userName", dataType: "string", width: 170 },
                    { headerText: nts.uk.resource.getText("CLI001_14"), key: "lockOutDateTime", dataType: "string", width: 200 },
                    {
                        headerText: nts.uk.resource.getText("CLI001_15"), key: "logType", dataType: "string", width: 300,
                        formatter: v => v == 1 ? '強制ロック' : ''
                    },
                ]);
                this.currentCodeList = ko.observableArray([]);
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                service.findAll().done((data: Array<LockOutDataDto>) => {
                    _self.items(data);

                    dfd.resolve();
                }).fail((res: any) => {

                    dfd.reject();
                });

                return dfd.promise();
            }

            /**
            * open dialog
            */
            public openDialogUserInfo() {
                nts.uk.ui.windows.setShared("CLI_DIALOG_B_INPUT_DATA");
                nts.uk.ui.windows.sub.modal("/view/cli/001/b/index.xhtml").onClosed(() => {
                    nts.uk.ui.block.clear();
                });
            }

            /**
            * Set focus
            */
            public setInitialFocus(): void {
                let self = this;

                if (_.isEmpty(self.items)) {
                    $('#add-Lock').focus();
                } else {
                   $('#tableGrid').focus();
                }
            }


            /**
            * Save
            */
            public save() {

                var self = this;
                if (_.isEmpty(self.currentCodeList())) {
                    $('#add-Lock').focus();
                    nts.uk.ui.dialog.error({ messageId: "Msg_218", messageParams: "CLI001_25" });
                }
                else {
                    $('#tableGrid').focus();
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                        .ifYes(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_35" }).then(() => {
                                let command = { lstUserId: self.currentCodeList() };

                                service.removeLockOutData(command).done(() => {
                                    nts.uk.ui.dialog.info({ messageId: 'Msg_221' }).then(() => {
                                        //Search again and display the screen
                                        service.findAll().done((data: Array<LockOutDataDto>) => {
                                            self.items(data);
                                            self.currentCodeList([]);
                                        });
                                    });
                                }).fail((res: any) => {
                                    return;    
                                });

                            });
                        }).ifNo(() => nts.uk.ui.dialog.info({ messageId: "Msg_36" }));
                }
            }

        }
    }
}
