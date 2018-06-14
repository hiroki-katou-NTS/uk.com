var multiple = true;
module nts.uk.com.view.cli001.a {
import LockOutDataDto = nts.uk.com.view.cli001.a.service.model.LockOutDataDto;

    export module viewmodel {

        export class ScreenModel {
            
            dataSource: any;
            selectedList: any;

            constructor() {
                var self = this;
                self.dataSource = [];
                self.selectedList = ko.observableArray([]);
                var features = [];
                features.push({
                    name: 'Selection',
                    mode: 'row',
                    multipleSelection: multiple,
                    activation: false,
                    rowSelectionChanged: this.selectionChanged.bind(this)
                });
                features.push({ name: 'Sorting', type: 'local' });
                features.push({ name: 'RowSelectors', enableCheckBoxes: multiple, enableRowNumbering: true });

                $("#cli001-igGrid").igGrid({
                    columns: [
                        { headerText: nts.uk.resource.getText(""),key: "userId", dataType: "string",hidden: true  },
                        { headerText: nts.uk.resource.getText("CLI001_12"),key: "loginId", dataType: "string",width: 100 },
                        { headerText: nts.uk.resource.getText("CLI001_13"), key: "userName" ,dataType: "string",width: 170},
                        { headerText: nts.uk.resource.getText("CLI001_14"), key: "lockOutDateTime", dataType: "string",width: 200},
                        { headerText: nts.uk.resource.getText("CLI001_15"), key: "logType", dataType: "string",width: 300,
                            formatter: v => v == 1 ? '強制ロック' : ''},
                    ],
                    features: [{
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true,
                        activation: false,
                        rowSelectionChanged: this.selectionChanged.bind(this)
                    },
                        { name: 'Sorting', type: 'local' },
                        { name: 'RowSelectors', enableCheckBoxes: true, enableRowNumbering: true },
                        { name: 'Paging',
                            pageSize: 100,
                            currentPageIndex: 0
                        },
                    ],
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    width: "840px",
                    height: "240px",
                    primaryKey: "userId",
                    dataSource: self.dataSource
                });
                $("#cli001-igGrid").closest('.ui-iggrid').addClass('nts-gridlist');
                $("#cli001-igGrid").setupSearchScroll("igGrid", true);

            }

            selectionChanged(evt, ui) {
                //console.log(evt.type);
                var selectedRows = ui.selectedRows;
                var arr = [];
                for (var i = 0; i < selectedRows.length; i++) {
                    arr.push("" + selectedRows[i].id);
                }
                this.selectedList(arr);
            };

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                 service.findAll().done((data: Array<LockOutDataDto>) => {
                   _self.dataSource = data;
                     $("#cli001-igGrid").igGrid("dataSourceObject", _self.dataSource);
                       $("#cli001-igGrid").igGrid("dataBind"); 
                    dfd.resolve();
                }).fail((res: any) => {
                   
                    dfd.reject();
                });

                return dfd.promise();
            }
            
            /**
            * open dialog
            */
            public openDialogUserInfo(){
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
                 
                if (_.isEmpty(self.dataSource)) {
                    $('#add-Lock').focus();
               }else {
                     $('#tableGrid').focus();
               }
            }


            /**
            * Save
            */
            public save() {
                
                var self = this;
                if (_.isEmpty(self.selectedList())) {
                    $('#add-Lock').focus();
                    nts.uk.ui.dialog.error({ messageId: "Msg_218", messageParams: "CLI001_25" });
                }
                else {
                    $('#tableGrid').focus();
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_35" }).then(() => {

                            let command = {lstUserId:self.selectedList()};
                            service.removeLockOutData(command).done(() => {
                                nts.uk.ui.dialog.info({ messageId: 'Msg_221' }).then(function() {
                                    //Search again and display the screen
                                     $("#cli001-igGrid").igGrid("dataSourceObject", _self.dataSource);
                                     $("#cli001-igGrid").igGrid("dataBind"); 
                              
                                }).fail((res: any) => {
                                    return;
                                });

                            });
                        }).ifNo(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_36" }).then(() => {
                                return;
                            });
                        }).then(() => {

                        });
                    }
            }


            }
        }
        }}