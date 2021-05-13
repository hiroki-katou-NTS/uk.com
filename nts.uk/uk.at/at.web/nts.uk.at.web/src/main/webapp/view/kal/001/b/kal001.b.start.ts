module nts.uk.at.view.kal001.b {  
    __viewContext.ready(function() {
        let param =  nts.uk.ui.windows.getShared("extractedAlarmData");
        if (_.isEmpty(param.listAlarmExtraValueWkReDto)) param.listAlarmExtraValueWkReDto = [
            {
                guid: "0000000001",
                workplaceID: "0000000001",
                hierarchyCd: "0000000001",
                workplaceName: "WKP0000000001",
                employeeID: "0000000001",
                employeeCode: "0000000001",
                employeeName: "EMP0000000001",
                alarmValueDate: "2021/12/30",
                category: 1,
                categoryName: "CTG1",
                alarmItem: "0000000001",
                alarmValueMessage: "0000000001",
                comment: "0000000001",
                checkedValue: "0000000001",
                menuName: "Application",
                menuItems: [
                    {programId: 'KAF001', menuName: 'Application', url: '/view/kaf/001/a/index.xhtml'}
                ]
            },
            {
                guid: "0000000002",
                workplaceID: "0000000002",
                hierarchyCd: "0000000002",
                workplaceName: "WKP0000000002",
                employeeID: "0000000002",
                employeeCode: "0000000002",
                employeeName: "EMP0000000002",
                alarmValueDate: "2021/12/30",
                category: 1,
                categoryName: "CTG1",
                alarmItem: "0000000002",
                alarmValueMessage: "0000000002",
                comment: "0000000002",
                checkedValue: "0000000002",
                menuName: "...",
                menuItems: [
                    {programId: 'KAF001', menuName: 'Application', url: '/view/kaf/001/a/index.xhtml'},
                    {programId: 'KAF022', menuName: 'Application Setting', url: '/view/kaf/022/a/index.xhtml'}
                ]
            },
            {
                guid: "0000000003",
                workplaceID: "0000000003",
                hierarchyCd: "0000000003",
                workplaceName: "WKP0000000003",
                employeeID: "0000000003",
                employeeCode: "0000000003",
                employeeName: "EMP0000000003",
                alarmValueDate: "2021/12/30",
                category: 1,
                categoryName: "CTG1",
                alarmItem: "0000000003",
                alarmValueMessage: "0000000003",
                comment: "0000000003",
                checkedValue: "0000000003",
                menuName: null,
                menuItems: []
            }
        ];
        let extractedAlarmData : Array<model.ValueExtractAlarmDto> = param.listAlarmExtraValueWkReDto;
        let screenModel = new viewmodel.ScreenModel(param);

        if (!extractedAlarmData || extractedAlarmData.length <= 0) {// same condiditon dataExtractAlarm.nullData
            nts.uk.ui.dialog.info({ messageId: "Msg_835" });
            screenModel.flgActive(false);
        }

        screenModel.startPage().done(function() {
            $(".popup-area1").ntsPopup('init');
            __viewContext.bind(screenModel);            
            // $("#grid").igGrid("option", "dataSource", extractedAlarmData);
//            screenModel.dataSource= extractedAlarmData;
        });
    });
}