module nts.uk.at.view.kdp003.c {
    import getText = nts.uk.resource.getText;
    import StampOutputItemSetDto = nts.uk.at.view.kdp003.c.service.model.StampingOutputItemSetDto;
    export module viewmodel {

        export class ScreenModel {

            dataSource: any;
            stampCode: string;
            dataPram: any;
            selectedList: KnockoutObservableArray<any>;
            hiddentOutputEmbossMethod: KnockoutObservable<boolean>;
            hiddentOutputWorkHours: KnockoutObservable<boolean>;
            hiddentOutputSetLocation: KnockoutObservable<boolean>;
            hiddentOutputPosInfor: KnockoutObservable<boolean>;
            hiddentOutputOT: KnockoutObservable<boolean>;
            hiddentOutputNightTime: KnockoutObservable<boolean>;
            hiddentOutputSupportCard: KnockoutObservable<boolean>;
            widthGrid: KnockoutObservable<string>;
            numberHiddent: number;


            constructor(dataShare: any) {
                var self = this;
                self.stampCode = dataShare.outputSetCode
                self.dataPram = dataShare
                self.hiddentOutputEmbossMethod = ko.observable(false);
                self.hiddentOutputWorkHours = ko.observable(false);
                self.hiddentOutputSetLocation = ko.observable(false);
                self.hiddentOutputPosInfor = ko.observable(false);
                self.hiddentOutputOT = ko.observable(false);
                self.hiddentOutputNightTime = ko.observable(false);
                self.hiddentOutputSupportCard = ko.observable(false);
                self.widthGrid = ko.observable('810px');
                self.numberHiddent = 0;
                self.dataSource = [];
                self.selectedList = ko.observableArray([]);
                var features = [];
                features.push({
                    name: 'Selection',
                    allowFiltering: true,
                    mode: 'row',
                    activation: false,
                    rowSelectionChanged: this.selectionChanged.bind(this)
                });
                features.push({
                    name: "Filtering",
                    type: "local",
                    mode: "simple",
                    filterDialogContainment: "window",
                    columnSettings: [
                        {
                            columnKey: "time",
                            condition: "startsWith"
                        }

                    ]
                });
                features.push({ name: 'Sorting', type: 'local' });
                features.push({ name: 'RowSelectors', enableRowNumbering: true });

            }
            selectionChanged(evt, ui) {
                let self = this;
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
                let self = this;
                let dfd = $.Deferred<any>();
                var count: number = 0;
                service.findStampOutput(self.stampCode).done((data: StampOutputItemSetDto) => {
                    self.hiddentOutputEmbossMethod(data.outputEmbossMethod);
                    self.hiddentOutputWorkHours(data.outputWorkHours);
                    self.hiddentOutputSetLocation(data.outputSetLocation);
                    self.hiddentOutputPosInfor(data.outputPosInfor);
                    self.hiddentOutputOT(data.outputOT);
                    self.hiddentOutputNightTime(data.outputNightTime);
                    self.hiddentOutputSupportCard(data.outputSupportCard);
                    if (data.outputEmbossMethod) {
                        count++;
                    }
                    if (data.outputWorkHours) {
                        count++;
                    }
                    if (data.outputSetLocation) {
                        count++;
                    }
                    if (data.outputPosInfor) {
                        count++;
                    }
                    if (data.outputOT) {
                        count++;
                    }
                    if (data.outputNightTime) {
                        count++;
                    }
                    if (data.outputSupportCard) {
                        count++;
                    }
                    switch (count) {
                        case 0: {
                            self.widthGrid("810px");
                            break;
                        }
                        case 1: {
                            self.widthGrid("910px");
                            break;
                        }
                        case 2: {
                            self.widthGrid("1030px");
                            break;
                        }
                        case 3: {
                            self.widthGrid("1130px");
                            break;
                        }
                        case 4: {
                            self.widthGrid("1250px");
                            break;
                        }
                        case 5: {
                            self.widthGrid("1360px");
                            break;
                        }
                        case 6: {
                            self.widthGrid("1470px");
                            break;
                        }
                        default: {
                            self.widthGrid("1580px");
                            break;
                        }
                    }
                    service.exportExcel(self.dataPram).done((data) => {
                        data.forEach(item => {
                            item.date = moment.utc(item.date).format('YYYY/M/DD');
                        });
                        self.dataSource = data;
                        $("#kdp003-grid").igGrid("dataSourceObject", self.dataSource);
                        $("#kdp003-grid").igGrid("dataBind");
                    });
                    dfd.resolve();
                })
                    .fail((res: any) => {

                        dfd.reject();
                    });

                return dfd.promise();
            }
            
             /**
            * Set focus
            */
            public setInitialFocusAndTabindex(): void {
                let self = this;
                 $("#kdp003-grid").igGrid({
                    columns: [
                        { headerText: nts.uk.resource.getText("KDP003_40"), key: "wkpCode", dataType: "string",width: 120 },
                        { headerText: nts.uk.resource.getText("KDP003_41"), key: "wkpName", dataType: "string",width: 160 },
                        { headerText: nts.uk.resource.getText("KDP003_42"), key: "empCode", dataType: "string" ,width: 110},
                        { headerText: nts.uk.resource.getText("KDP003_43"), key: "empName", dataType: "string" ,width: 120},
                        { headerText: nts.uk.resource.getText("KDP003_44"), key: "cardNo", dataType: "string",width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_45"), key: "date", dataType: "string" ,columnCssClass: "col-align-right",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_46"), key: "time", dataType: "string", columnCssClass: "col-align-right" ,width: 80},
                        { headerText: nts.uk.resource.getText("KDP003_47"), key: "atdType", dataType: "string", hidden: !self.hiddentOutputEmbossMethod(),width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_48"), key: "workTimeZone", dataType: "string", hidden: !self.hiddentOutputWorkHours(),width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_49"), key: "installPlace", dataType: "string", hidden: !self.hiddentOutputSetLocation(),width: 120},
                        { headerText: nts.uk.resource.getText("KDP003_50"), key: "localInfor", dataType: "string", hidden: !self.hiddentOutputPosInfor() ,width: 110},
                        { headerText: nts.uk.resource.getText("KDP003_51"), key: "otTime", dataType: "string", hidden: !self.hiddentOutputOT(),width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_52"), key: "midnightTime", dataType: "string", hidden: !self.hiddentOutputNightTime(),width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_53"), key: "supportCard", dataType: "string", hidden: !self.hiddentOutputSupportCard(),width: 110},
                    ],
                    features: [{
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false,
                        activation: false,
                        rowVirtualization: true,
                        rowSelectionChanged: self.selectionChanged.bind(self)
                    },
                        { name: 'Sorting', type: 'local' },
                        {
                            name: 'Paging',
                            pageSize: 20,
                            currentPageIndex: 0,
                            pageSizeChanged: function (evt, ui) { 
                            let source = ko.toJS(self.dataSource);
                            $("#kdp003-grid").igGrid("option", "dataSource", source);
                            $("#kdp003-grid").igGrid("dataBind");
                            }
                        }
                    ],
                    virtualization: true,
                    virtualizationMode: 'fixed',
                    autoGenerateColumns: false,
                    width: self.widthGrid(),
                    height: "570px",
                    dataSource: self.dataSource,
                    dataBinding: function(evt, ui) {
                        $("#kdp003-grid_container *").attr('tabindex', -1);
                        $("#kdp003-grid_container").attr('tabindex', -1);
                        $("#kdp003-grid_virtualContainer").attr('tabindex', 3);
                        $("#kdp003-grid_virtualContainer").focus();
                    }
                });
                 $("#kdp003-grid").closest('.ui-iggrid').addClass('nts-gridlist');
                 $("#kdp003-grid").setupSearchScroll("igGrid", true);
            }



            /**
            * Export excel
            */
            public exportExcel(): void {
                let self = this,

                    data: any = self.dataPram;
                data.lstEmployeeId = [];
                service.outPutFileExcel(data).done((data1) => {
                    console.log(data1);
                })


            }

        }
    }


}