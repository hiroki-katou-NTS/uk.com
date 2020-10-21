module nts.uk.at.view.ksu001.g {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;

    const Paths = {
        GET_WORK_AVAILABILITY_OF_ONE_DAY: 'screen/at/shift/management/workavailability/getAll'
    };
    const method = [
        { id: '', Name: "なし" },
        { id: '休日', Name: "休日" },
        { id: 'シフト', Name: "シフト" },
        { id: '時間帯', Name: "時間帯" },
    ]
    @bean()
    class Ksu001GViewModel extends ko.ViewModel {
        listWorkAvailabilitys: KnockoutObservableArray<IWorkAvailabilityOfOneDay> = ko.observableArray([]);
        period: string = "";
        constructor(params: any) {
            super();
            const self = this;           
            self.loadWorkAvailabilityOfOneDay();           
        }

        loadWorkAvailabilityOfOneDay(): void {
            const self = this;
            let request :any = getShared('dataShareDialogG');
            // let request: any = {
            //     "startDate": "2020/10/01",
            //     "endDate": "2020/10/30",
            //     "employeeIDs": ["7A8B2864-7093-4539-BEA9-418177C7F746", "7A8D0AE3-89F7-427F-BE66-768B7713FCA0", "7A919929-22F8-436A-875A-D8FBF40D4922"]
            // }
            self.period = request.startDate + "～" + request.endDate;
            self.$ajax(Paths.GET_WORK_AVAILABILITY_OF_ONE_DAY, request).then((data: Array<IWorkAvailabilityOfOneDay>) => {
                self.$blockui("show");
                if (data) {                   
                    let dataAll = _.sortBy(data, item => item.desireDay);
                    dataAll.map( item => {
                        if(item.shift.length > 1){
                            
                        }

                        if(item.timezone.length > 1){
                            
                        }
                    })
                    self.listWorkAvailabilitys(dataAll);                   
                    $("#grid").igGrid({
                        width: "800px",
                        height: "420px",
                        dataSource: dataAll,
                        dataSourceType: "json",
                        // primaryKey: "date",
                        autoGenerateColumns: false,
                        responseDatakey: "results",
                        columns: [
                            { headerText: getText('KSU001_4032'), key: "desireDay", dataType: "string" },
                            { headerText: getText('KSU001_4033'), key: "employeeCdName", dataType: "string", width: "30%" },
                            { headerText: getText('KSU001_4034'), key: "method", dataType: "string" },
                            { headerText: getText('KSU001_4035'), key: "shift" },
                            { headerText: getText('KSU001_4036'), key: "timezone" },
                            { headerText: getText('KSU001_4037'), key: "remarks" }
                        ],
                    
                        features: [
                            {
                                name: "CellMerging",
                                mergeOn: "always",
                                mergeType: "physical",
                                mergeStrategy: function (prevRec, curRec, columnKey) {
                                    if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["employeeCdName"] === curRec["employeeCdName"]){                                        
                                        return prevRec[columnKey].toLowerCase() === curRec[columnKey].toLowerCase();
                                    } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["employeeCdName"] === curRec["employeeCdName"] && prevRec["method"] === curRec["method"]) {
                                        return prevRec[columnKey] === curRec[columnKey];
                                    } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["shift"] === curRec["shift"]) {
                                        return prevRec[columnKey] === curRec[columnKey];
                                    } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["timezone"] === curRec["timezone"]) {
                                        return prevRec[columnKey] === curRec[columnKey];
                                    } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["remarks"] === curRec["remarks"]) {
                                        return prevRec[columnKey] === curRec[columnKey];
                                    }                                   
                                    return false;
                                }
                            },
                            {
                                name: "Filtering",
                                type: "local",
                                mode: "simple",
                                filterDialogContainment: "window",
                                filterSummaryAlwaysVisible: false,
                                columnSettings: [
                                    { columnKey: 'desireDay', condition: "equals" },
                                    { columnKey: 'employeeID', condition: "startsWith" },
                                    { columnKey: "shift", allowFiltering: false },
                                    { columnKey: "timezone", allowFiltering: false },
                                    { columnKey: "remarks", allowFiltering: false },
                                    {
                                        columnKey: "method", editorType: 'combo',
                                        conditionList: [
                                            "equals"
                                        ],
                                        editorOptions: {
                                            mode: "dropdown",
                                            dataSource: method,
                                            textKey: "Name",
                                            valueKey: "id",
                                            selectionChanged: function (e, args) {
                                                //TODO sử dụng khi thay đổi data của combobox
                                            }
                                        }
                                    }
                                ]
                            }
                        ]
                    });
                }
            }).fail((res) => {
                self.$dialog.error({ messageId: res.messageId });
            }).always(() => {
                self.$blockui('hide');
            });
        }

        clearFilter() {
            $("#grid").igGridFiltering("filter", [], true);
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }
    }

    interface IWorkAvailabilityOfOneDay {
        desireDay: string,
        employeeID: string,
        method: string,
        shift: string,
        timezone: string,
        remarks: string
    }
}