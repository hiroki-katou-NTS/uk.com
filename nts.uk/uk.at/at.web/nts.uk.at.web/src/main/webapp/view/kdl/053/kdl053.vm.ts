module nts.uk.at.view.kdl053 {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;

    const Paths = {
        GET_ATENDANCENAME_BY_IDS:"at/record/attendanceitem/daily/getattendnamebyids",
        EXPORT_CSV:"screen/at/kdl053/exportCsv"
    };
    
    @bean()
    class Kdl053ViewModel extends ko.ViewModel {
        hasError: KnockoutObservable<boolean> = ko.observable(true);
        registrationErrorList: KnockoutObservable<any> = ko.observable([]);
        registrationErrorListCsv: KnockoutObservable<any> = ko.observable([]);
        constructor(params: any) {
            super();
            const self = this;
            self.loadScheduleRegisterErr(); 
        }
        mounted(){
            const self = this;
            if(self.hasError()){
                self.$window.size(540, 820);
            } else {
                self.$window.size(500, 820);
            }
        }

        loadScheduleRegisterErr(): void {
            const self = this;
            let data = getShared('dataShareDialogKDL053');           
            let errorRegistrationListTmp = data.errorRegistrationList;
            let employeeIds = data.employeeIds;
            let errorRegistrationList: any = [], errorRegistrationListCsv: any =[];

            _.each(employeeIds, id =>{      
                let temp: any = [];
                _.each(errorRegistrationListTmp, err => {
                    if(err.sid == id) {
                        err.employeeCdName = err.scd + " " + err.empName;
                        temp.push(err);
                    }
                })               
                errorRegistrationList = _.union(errorRegistrationList, _.sortBy(temp, item => item.date));
            });

            self.hasError(data.isRegistered == 1); 

            _.each(errorRegistrationList, errorLog => {
                switch (self.getDayfromDate(errorLog.date)) {
                    case 0:
                        errorLog.dateCss = '<span>' + errorLog.date + '<span style="color:red">' + " (" + moment.weekdaysShort(0) + ')</span></span>';
                        break;
                    case 1:
                        errorLog.dateCss = '<span>' + errorLog.date + " (" + moment.weekdaysShort(1) + ')</span>';
                        break;
                    case 2:
                        errorLog.dateCss = '<span>' + errorLog.date + " (" + moment.weekdaysShort(2) + ')</span>';
                        break;
                    case 3:
                        errorLog.dateCss = '<span>' + errorLog.date + " (" + moment.weekdaysShort(3) + ')</span>';
                        break;
                    case 4:
                        errorLog.dateCss = '<span>' + errorLog.date + " (" + moment.weekdaysShort(4) + ')</span>';
                        break;
                    case 5:
                        errorLog.dateCss = '<span>' + errorLog.date + " (" + moment.weekdaysShort(5) + ')</span>';
                        break;
                    case 6:
                        errorLog.dateCss = '<span>' + errorLog.date + '<span style="color:blue">'+ " (" + moment.weekdaysShort(6) + ')</span></span>';
                        break;
                }
            })

            let listIds: Array<any> = _.map(errorRegistrationList, item => { return item.attendanceItemId }); 

            this.$blockui("invisible");
            self.$ajax(Paths.GET_ATENDANCENAME_BY_IDS, listIds).done((data: Array<any>) => {
                if (data && data.length > 0) {
                    let index = 0, idx = 0;
                    _.each(errorRegistrationList, item => {                        
                        idx++;
                        _.each(data, itemName => {
                            if (item.attendanceItemId == itemName.attendanceItemId) {
                                errorRegistrationList[index].errName = itemName.attendanceItemName;
                                index++;
                            }
                        })
                    })
                    self.registrationErrorListCsv(errorRegistrationList);
                    self.registrationErrorList(errorRegistrationList);
                } else {
                    _.each(errorRegistrationList, item => {
                        item.errName = "";
                    })
                    self.registrationErrorListCsv(errorRegistrationList);
                    self.registrationErrorList(errorRegistrationList);
                }

                $("#grid").igGrid({
                    width: "780px",
                    height: "330px",
                    dataSource: self.registrationErrorList(),
                    dataSourceType: "json",
                    primaryKey: "id",
                    autoGenerateColumns: false,
                    responseDatakey: "results",
                    columns: [                        
                        { headerText: getText('KDL053_5'), key: "employeeCdName", dataType: "string", width: "30%" },
                        { headerText: getText('KDL053_6'), key: "dateCss", dataType: "string", width: "16%" },
                        { headerText: getText('KDL053_7'), key: "errName", dataType: "string", width: "18%" },
                        { headerText: getText('KDL053_8'), key: "errorMessage", width: "34%" }
                    ],
                    features: [
                        {
                            name: 'Paging',
                            type: "local",
                            pageSize: 10
                        },
                        {
                            name: 'Resizing',
                            columnSettings: [
                                { columnKey: "employeeCdName", allowResizing: true },
                                { columnKey: "dateCss", allowResizing: true },
                                { columnKey: "errName", allowResizing: true },
                                { columnKey: "errorMessage", allowResizing: true }
                            ],
                        }
                    ]
                });
                $('#btnClose').focus();
                self.$blockui("hide");
            })
                .always(() => {
                    self.$blockui("hide");
                });
        }  
        exportCsv(): void {
            const self = this;
            self.$blockui("invisible"); 
            nts.uk.request.exportFile(Paths.EXPORT_CSV, self.registrationErrorListCsv()).always(() => {
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
}