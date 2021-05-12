module nts.uk.at.view.kal001.b {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    export module viewmodel {
        export class ScreenModel {

            columns: Array<any>;
            currentSelectedRow: KnockoutObservable<any>;
            eralRecord: KnockoutObservable<number>;
            eralRecordText: KnockoutObservable<string>;
            dataSource: Array<model.ValueExtractAlarmDto> = [];
            flgActive: KnockoutObservable<boolean>;
            processId: string;

            menuItems: KnockoutObservableArray<any> = ko.observableArray([]);

            constructor(param) {
                let self = this;
                self.processId = param.processId;
                self.eralRecord = ko.observable(param.totalErAlRecord);
                self.eralRecordText = ko.observable(self.getEralRecordText(param));
                self.currentSelectedRow = ko.observable(null);
                self.flgActive = ko.observable(true);
                self.columns = [
                    {headerText: '', key: 'guid', width: 1, hidden: true},
                    {headerText: getText('KAL001_20'), key: 'workplaceName', width: 100},
                    {headerText: getText('KAL001_13'), key: 'employeeCode', width: 85},
                    {headerText: getText('KAL001_14'), key: 'employeeName', width: 130},
                    {headerText: getText('KAL001_15'), key: 'alarmValueDate', width: 125},
                    {headerText: getText('KAL001_16'), key: 'categoryName', width: 60},
                    {headerText: getText('KAL001_17'), key: 'alarmItem', width: 115},
                    {headerText: getText('KAL001_18'), key: 'alarmValueMessage', width: 245},
                    {headerText: getText('KAL001_41'), key: 'checkedValue', width: 100},
                    {headerText: getText('KAL001_19'), key: 'comment', width: 260},
                    {headerText: getText('KAL001_162'), key: 'menuName', width: 200, ntsControl: 'Link'}
                ];
                self.dataSource = param.listAlarmExtraValueWkReDto || [];
            }

            getEralRecordText(param: any): string {
                if (_.isNil(param.listAlarmExtraValueWkReDto) || param.totalErAlRecord <= 1000) {
                    return "";
                }
                return nts.uk.resource.getMessage("Msg_1524", [1000]);
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                $("#grid").ntsGrid({
                    height: '450px',
                    width: '1222px',
                    dataSource: self.dataSource,
                    primaryKey: 'guid',
                    columns: self.columns,
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    features: [
                        {name: 'Paging', type: 'local', pageSize: 20},
                        {
                            name: "Tooltips",
                            columnSettings: [
                                {columnKey: "workplaceName", allowTooltips: true}
                            ]
                        }
                    ],
                    ntsControls: [
                        { name: 'Link', click: (rowId: any) => {self.handleClickLinkButton(rowId);}, controlType: 'LinkLabel' },
                    ],
                    enableTooltip: true
                });
                dfd.resolve();
                return dfd.promise();
            }

            handleClickLinkButton(rowId: string) {
                const self = this;
                const selectedRow: model.ValueExtractAlarmDto = _.find(self.dataSource, r => r.guid == rowId);
                if (selectedRow) {
                    if (selectedRow.menuItems.length == 1) {
                        $(".popup-area1").ntsPopup("init");
                        self.menuItems([]);
                        nts.uk.request.jumpFromDialogOrFrame(selectedRow.menuItems[0].url);
                    } else {
                        $(".popup-area1").ntsPopup({
                            trigger: ".nts-grid-control-menuName-" + rowId,
                            position: {
                                my: "left top",
                                at: "left bottom",
                                of: ".nts-grid-control-menuName-" + rowId
                            },
                            showOnStart: false,
                            dismissible: true
                        });
                        self.menuItems(selectedRow.menuItems);
                        $(".popup-area1").ntsPopup("show");
                    }
                }
            }

            exportExcel(): void {
                let self = this;
                block.invisible();
//                let params = {
//                    data: self.dataSource
//                };
                service.exportAlarmData(self.processId, nts.uk.ui.windows.getShared("extractedAlarmData").currentAlarmCode).done(() => {

                }).fail((errExcel) => {
                    alertError(errExcel);
                }).always(() => {
                    block.clear();
                });
            }

            sendEmail(): void {
                let self = this;
                //nts.uk.ui.windows.setShared("employeeList", _.uniqWith(_.filter(shareEmployee,function(x){return x.workplaceID !=null;} ), _.isEqual));
//                let shareEmployee = _.map(self.dataSource, (item) =>{
//                   return {employeeID: item.employeeID, workplaceID: item.workplaceID}; 
//                });
//                nts.uk.ui.windows.setShared("employeeList", _.uniqWith(shareEmployee, _.isEqual));
                nts.uk.ui.windows.setShared("processId", self.processId);
                modal("/view/kal/001/c/index.xhtml").onClosed(() => {

                });

            }

            closeDialog() {
                nts.uk.ui.windows.close();
            }


        }
    }


    export module model {

        export interface ValueExtractAlarmDto {
            guid: string;
            workplaceID: string;
            hierarchyCd: string;
            workplaceName: string;
            employeeID: string;
            employeeCode: string;
            employeeName: string;
            alarmValueDate: string;
            category: number;
            categoryName: string;
            alarmItem: string;
            alarmValueMessage: string;
            comment: string;
            checkedValue: string;
            menuName: string;
            menuItems: Array<any>;
        }
    }


}