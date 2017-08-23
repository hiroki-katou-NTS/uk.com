module nts.uk.at.view.kdw002.a {
    export module viewmodel {
        import href = nts.uk.request.jump;
        import getText = nts.uk.resource.getText;
        import infor = nts.uk.ui.dialog.info;
        export class ScreenModel {
            headerColorValue: KnockoutObservable<string>;
            unitRoundings: KnockoutObservableArray<any>;
            selectedOption: KnockoutObservable<any>;
            attendanceItems: KnockoutObservableArray<any>;
            aICurrentCode: KnockoutObservable<any>;
            attendanceItemColumn: KnockoutObservableArray<any>;
            txtItemId: KnockoutObservable<any>;
            txtItemName: KnockoutObservable<any>;
            timeInputCurrentCode: KnockoutObservable<any>;
            linebreak: KnockoutObservable<any>;
            constructor() {
                var self = this;
                self.headerColorValue = ko.observable('');
                self.linebreak = ko.observable(0);
                self.unitRoundings = ko.observableArray([{ timeInputValue: -1, timeInputName: '' },
                    { timeInputValue: 0, timeInputName: '1分' }, { timeInputValue: 1, timeInputName: '5分' }, { timeInputValue: 2, timeInputName: '10分' },
                    { timeInputValue: 3, timeInputName: '15分' }, { timeInputValue: 4, timeInputName: '30分' }
                    , { timeInputValue: 5, timeInputName: '60分' }]),
                    self.timeInputCurrentCode = ko.observable(-1);
                self.txtItemId = ko.observable(null);
                self.txtItemName = ko.observable('');
                self.attendanceItems = ko.observableArray([]);
                self.aICurrentCode = ko.observable(null);
                self.aICurrentCode.subscribe(attendanceItemId => {
                    service.getControlOfAttendanceItem(attendanceItemId).done(cAttendanceItem => {
                        self.txtItemId(cAttendanceItem.attandanceTimeId);
                        self.txtItemName(cAttendanceItem.attandanceName);
                        self.linebreak(cAttendanceItem.nameLineFeedPosition);
                        self.headerColorValue(cAttendanceItem.headerBackgroundColorOfDailyPerformance);
                        self.timeInputCurrentCode(cAttendanceItem.inputUnitOfTimeItem);
                    });

                });

                self.attendanceItemColumn = ko.observableArray([
                    { headerText: 'コード', key: 'attendanceItemId', width: 100, dataType: "number" },
                    { headerText: '名称', key: 'attendanceItemName', width: 230, dataType: "string" }
                ]);
                $(".clear-btn").hide();

                service.getAttendanceItems().done(atItems => {
                    if (!nts.uk.util.isNullOrUndefined(atItems)) {
                         self.aICurrentCode(atItems[0].attendanceItemId);
                       let attendanceItems =[];
                        atItems.forEach(attendanceItem => {
                           attendanceItems.push({ attendanceItemId: attendanceItem.attendanceItemId, attendanceItemName: attendanceItem.attendanceItemName });
                        });
                        self.attendanceItems(attendanceItems);
                    }

                });

            }
            
            navigateView(): void {
                var self = this;
                var path = "/view/kdw/006/index.xhtml";
               href(path);
            }

            submitData(): void {
                var self = this;
                var AtItems = { attandanceTimeId: self.txtItemId(), inputUnitOfTimeItem: self.timeInputCurrentCode(), headerBackgroundColorOfDailyPerformance: self.headerColorValue(), nameLineFeedPosition: self.linebreak() };
                service.updateControlOfAttendanceItem(AtItems).done(x=>{
                     infor(nts.uk.resource.getMessage("Msg_15", []));
                });

            }
             




        }

        interface IAttendanceItem {
            attendanceItemId: number;
            attendanceItemName: string;
        }


        class AttendanceItem {
            attendanceItemId: number;
            attendanceItemName: string;

            constructor(params: IAttendanceItem) {
                var self = this;
                self.attendanceItemId = params.attendanceItemId;
                self.attendanceItemName = params.attendanceItemName;
            }
        }
    }
}
