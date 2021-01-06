module nts.uk.at.view.kdl053 {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;

    const Paths = {
        GET_WORK_AVAILABILITY_OF_ONE_DAY: 'screen/at/shift/management/workavailability/getAll',
        GET_ATENDANCENAME_BY_IDS:"at/record/attendanceitem/daily/getattendnamebyids",
        EXPORT_CSV:"screen/at/kdl053/exportCsv"
    };
    
    @bean()
    class Kdl053ViewModel extends ko.ViewModel {		
        period: string = "";
        isRegistered: KnockoutObservable<boolean> = ko.observable(true);
        registrationErrorList: KnockoutObservable<any> = ko.observable();
        constructor(params: any) {
            super();
            const self = this;
            self.loadScheduleRegisterErr();            
            // $('#grid').focus();
            // $('#grid_scroll').focus();
        }
        loadScheduleRegisterErr(): void {
            const self = this;

            let dataAll: Array<any> = [];
            dataAll = [{employeeCdName:'000001 0110の社員1', date:'2020/07/07', errId: 163, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000002 新規　ログ', date:'2020/08/07', errId: 165, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000003 承認　者', date:'2020/07/09', errId: 195, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000004 別　確認11', date:'2020/08/08', errId: 201, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000005 承認者　管理本部', date:'2020/09/07', errId: 205, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000006 承認者', date:'2020/09/09', errId: 537, errMsg: '登録に誤りがありました'},                    
                    {employeeCdName:'000007 申請　１', date:'2020/07/17', errId: 165, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000008 日別実績　確認', date:'2020/07/22', errId: 195, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000009 日別実績　確認6', date:'2020/07/14', errId: 201, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000010 日別実績　テスト', date:'2020/07/15', errId: 205, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000011 日別実績　確認', date:'2020/07/19', errId: 537, errMsg: '登録に誤りがありました'},
                    {employeeCdName:'000012 承認者　課長', date:'2020/07/11', errId: 538, errMsg: '登録に誤りがありました'}
            ]
            let listIds: Array<any> = _.map(dataAll, item => { return item.errId });
            // self.isRegistered(false);
            self.registrationErrorList(dataAll);
            
            self.$ajax(Paths.GET_ATENDANCENAME_BY_IDS,listIds).done((data: Array<any>)=>{
                if(data && data.length > 0){
                    let index = 0;
                    _.each(dataAll, item => {
                        _.each(data, itemName =>{
                            if(item.errId == itemName.attendanceItemId ){
                                dataAll[index].errName = itemName.attendanceItemName;
                                index++;
                            }                           
                        })
                    })                   
                }                
                $("#grid").igGrid({
                    width: "780px",
                    height: "330px",
                    dataSource: dataAll,
                    dataSourceType: "json",
                    primaryKey: "employeeCdName",
                    autoGenerateColumns: false,                        
                    responseDatakey: "results",
                    columns: [
                        { headerText: getText('KDL053_5'), key: "employeeCdName", dataType: "string", width: "25%" },                          
                        { headerText: getText('KDL053_6'), key: "date", dataType: "string", width: "15%" },
                        { headerText: getText('KDL053_7'), key: "errName", dataType: "string", width: "20%" },
                        { headerText: getText('KDL053_8'), key: "errMsg" }                                   
                    ],
                    features: [
                        {
                            name : 'Paging',
                            type: "local",
                            pageSize : 10
                        }
                    ]
                });
            })
            .fail(() => {

            })
            .always(() => {

            });
        }
        exportCsv(): void {
            const self = this;
            self.$blockui("invisible"); 
            nts.uk.request.exportFile(Paths.EXPORT_CSV, self.registrationErrorList()).always(() => {
                self.$blockui("clear");
            });
        }

        closeDialog(): void {
             const vm = this;
             vm.$window.close();
        }

        getDayfromDate(fromDate: string): number {
            let date = new Date(fromDate);
            return date.getDay();
        }
    }

    interface IScheduleRegisterErr {
        /** コード／名称*/
        employeeCdName: string;

        date: string;

        errName: string;

        errMsg: string;
       
    }

    class ScheduleRegisterErr{
        employeeCdName: string;
        date: string;
        errName: string;
        errMsg: string;
        // constructor(scheduleRegisterErr: IScheduleRegisterErr) {
        //     this.employeeCdName = scheduleRegisterErr.employeeCdName;
        //     this.date = scheduleRegisterErr.date;
        //     this.errName = scheduleRegisterErr.errName;
        //     this.errMsg = scheduleRegisterErr.errMsg;
        // }

        constructor(employeeCdName: string, date: string, errName: string, errMsg: string) {
            this.employeeCdName = employeeCdName;
            this.date = date;
            this.errName = errName;
            this.errMsg = errMsg;
        }
    }
}