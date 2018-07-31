module nts.uk.at.view.kdp003.c {
    __viewContext.ready(function() {
        let dataShare: any;
        this.transferred.ifPresent(data => {
            dataShare = data;
            let screenModel = new viewmodel.ScreenModel(dataShare);
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
                $("#kdp003-grid").igGrid({
                    columns: [
                        { headerText: nts.uk.resource.getText("KDP003_40"), key: "wkpCode", dataType: "string",width: 120 },
                        { headerText: nts.uk.resource.getText("KDP003_41"), key: "wkpName", dataType: "string",width: 160 },
                        { headerText: nts.uk.resource.getText("KDP003_42"), key: "empCode", dataType: "string" ,width: 110},
                        { headerText: nts.uk.resource.getText("KDP003_43"), key: "empName", dataType: "string" ,width: 120},
                        { headerText: nts.uk.resource.getText("KDP003_44"), key: "cardNo", dataType: "string",width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_45"), key: "date", dataType: "string" ,columnCssClass: "col-align-right",width: 90},
                        { headerText: nts.uk.resource.getText("KDP003_46"), key: "time", dataType: "string", columnCssClass: "col-align-right" ,width: 60},
                        { headerText: nts.uk.resource.getText("KDP003_47"), key: "atdType", dataType: "string", hidden: !screenModel.hiddentOutputEmbossMethod(),width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_48"), key: "workTimeZone", dataType: "string", hidden: !screenModel.hiddentOutputWorkHours(),width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_49"), key: "installPlace", dataType: "string", hidden: !screenModel.hiddentOutputSetLocation(),width: 110},
                        { headerText: nts.uk.resource.getText("KDP003_50"), key: "localInfor", dataType: "string", hidden: !screenModel.hiddentOutputPosInfor() ,width: 110},
                        { headerText: nts.uk.resource.getText("KDP003_51"), key: "otTime", dataType: "string", hidden: !screenModel.hiddentOutputOT(),width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_52"), key: "midnightTime", dataType: "string", hidden: !screenModel.hiddentOutputNightTime(),width: 110 },
                        { headerText: nts.uk.resource.getText("KDP003_53"), key: "supportCard", dataType: "string", hidden: !screenModel.hiddentOutputSupportCard(),width: 110},
                    ],
                    features: [{
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false,
                        activation: false,
                        rowVirtualization: true,
                        rowSelectionChanged: screenModel.selectionChanged.bind(screenModel)
                    },
                        { name: 'Sorting', type: 'local' },
                        {
                            name: 'Paging',
                            pageSize: 20,
                            currentPageIndex: 0
                        }
                       
                    ],
                    virtualization: true,
                    virtualizationMode: 'fixed',
                    autoGenerateColumns: false,
                    width: screenModel.widthGrid(),
                    height: "470px",
                   // primaryKey: "wkpCode",
                    dataSource: screenModel.dataSource
                });
                $("#kdp003-grid").closest('.ui-iggrid').addClass('nts-gridlist');
                $("#kdp003-grid").setupSearchScroll("igGrid", true);
                $("#kdp003-grid").igGrid("dataBind");

            });
            });
        });
    }
