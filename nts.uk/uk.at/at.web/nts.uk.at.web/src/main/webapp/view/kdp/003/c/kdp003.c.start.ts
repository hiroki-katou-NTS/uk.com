module nts.uk.at.view.kdp003.c {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);
            $("#kdp003-grid").igGrid({
                    columns: [
                        { headerText: nts.uk.resource.getText("KDP003_40"), key: "userId", dataType: "string"},
                        { headerText: nts.uk.resource.getText("KDP003_41"), key: "loginId", dataType: "string"},
                        { headerText: nts.uk.resource.getText("KDP003_42"), key: "userName", dataType: "string"},
                        { headerText: nts.uk.resource.getText("KDP003_43"), key: "lockOutDateTime",dataType: "string" },
                        { headerText: nts.uk.resource.getText("KDP003_44"), key: "logType", dataType: "string" },
                        { headerText: nts.uk.resource.getText("KDP003_45"), key: "logType", dataType: "string"},
                        { headerText: nts.uk.resource.getText("KDP003_46"), key: "logType", dataType: "string" },
                        { headerText: nts.uk.resource.getText("KDP003_47"), key: "logType", dataType: "string", hidden: !screenModel.hiddentOutputEmbossMethod()},
                        { headerText: nts.uk.resource.getText("KDP003_48"), key: "logType", dataType: "string", hidden: !screenModel.hiddentOutputWorkHours()},
                        { headerText: nts.uk.resource.getText("KDP003_49"), key: "logType", dataType: "string", hidden: !screenModel.hiddentOutputSetLocation()},
                        { headerText: nts.uk.resource.getText("KDP003_50"), key: "logType", dataType: "string", hidden: !screenModel.hiddentOutputPosInfor()},
                        { headerText: nts.uk.resource.getText("KDP003_51"), key: "logType", dataType: "string", hidden: !screenModel.hiddentOutputOT()},
                        { headerText: nts.uk.resource.getText("KDP003_52"), key: "logType", dataType: "string", hidden: !screenModel.hiddentOutputNightTime()},
                        { headerText: nts.uk.resource.getText("KDP003_53"), key: "logType", dataType: "string", hidden: !screenModel.hiddentOutputSupportCard()},
                    ],
                    features: [{
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false,
                        activation: false,
                        rowSelectionChanged: screenModel.selectionChanged.bind(screenModel)
                    },
                        { name: 'Sorting', type: 'local' },
                        //{ name: 'RowSelectors', enableCheckBoxes: true, enableRowNumbering: false },
                        {
                            name: 'Paging',
                            pageSize: 20,
                            currentPageIndex: 0
                        },
                    ],
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    width: "1450px",
                    height: "550px",
                    primaryKey: "userId",
                    dataSource: screenModel.dataSource
                });
                $("#kdp003-grid").closest('.ui-iggrid').addClass('nts-gridlist');
                $("#kdp003-grid").setupSearchScroll("igGrid", true);
                 

        });
    });
}
