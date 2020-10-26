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
            let dataAll: Array<any>;
            // let listData: Array<any>;
            let request :any = getShared('dataShareDialogG');         
            self.period = request.startDate + "～" + request.endDate;
            self.$ajax(Paths.GET_WORK_AVAILABILITY_OF_ONE_DAY, request).then((data: Array<IWorkAvailabilityOfOneDay>) => {
                self.$blockui("show");
                if (data) {
                    if (data.length  === 0) {
                        self.$dialog.error({ messageId: "Msg_37" });
                    }
                    //sort list by timezone
                    let dataTmp = _.sortBy(data, item => item.desireDay); 
                    let listDate = _.uniqBy(_.map(dataTmp, x => x.desireDay), y => y);
                    _.forEach(listDate, date =>{                        
                        let arr = _.filter(dataTmp, data => {
                            return data.desireDay === date; 
                        })
                        dataAll = _.union(dataAll,_.sortBy(arr, e => e.timezone));
                    });                    
                    // listData = _.map(dataAll, x => x.desireDay = new Date(x.desireDay));
                    // let temp = _.forEach(dataAll,x => x.desireDay = moment(x.desireDay).format("YYYY/MM/DD"));
                    self.listWorkAvailabilitys(dataAll);
                    $("#grid").igGrid({
                        width: "800px",
                        height: "420px",
                        dataSource: dataAll,
                        dataSourceType: "json",
                        primaryKey: "desireDay",
                        autoGenerateColumns: false,
                        responseDatakey: "results",
                        columns: [
                            { headerText: getText('KSU001_4032'), key: "desireDay", dataType: "string" },                           
                            // { headerText: getText('KSU001_4032'), key: "desireDay", dataType: "date" },  
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
                                    // if (prevRec["desireDay"] === curRec["desireDay"]){                                        
                                    //     // return prevRec[columnKey] === curRec[columnKey];
                                    //     return 
                                    // } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["employeeCdName"] === curRec["employeeCdName"]) {
                                    //     // return prevRec[columnKey] === curRec[columnKey];
                                    //     return true;
                                    // } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["employeeCdName"] === curRec["employeeCdName"] && prevRec["method"] != "" && prevRec["method"] === curRec["method"]) {
                                    //     // return prevRec[columnKey] === curRec[columnKey];
                                    //     return true;
                                    // } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["employeeCdName"] === curRec["employeeCdName"] && prevRec["shift"] != ""  && prevRec["shift"] === curRec["shift"]) {
                                    //     // return prevRec[columnKey] === curRec[columnKey];
                                    //     return true;
                                    // } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["employeeCdName"] === curRec["employeeCdName"] && prevRec["timezone"] != ""  && prevRec["timezone"] === curRec["timezone"]) {
                                    //     // return prevRec[columnKey] === curRec[columnKey];
                                    //     return true;
                                    // } else if (prevRec["desireDay"] === curRec["desireDay"] && prevRec["employeeCdName"] === curRec["employeeCdName"] && prevRec["remarks"] != ""  && prevRec["remarks"] === curRec["remarks"]) {
                                    //     // return prevRec[columnKey] === curRec[columnKey];
                                    //     return true;
                                    // }                            
                                    // return false;

                                    if (prevRec["desireDay"] === curRec["desireDay"]) {
                                        if (prevRec["employeeCdName"] === curRec["employeeCdName"]) {
                                            if (prevRec["method"] === curRec["method"] && curRec["method"] != "") {
                                                if(prevRec["shift"] === curRec["shift"] && curRec["shift"] != ""){
                                                    // if (prevRec["timezone"] === curRec["timezone"] && curRec["timezone"] != "") { 
                                                    //     return prevRec[columnKey] === curRec[columnKey];                                   
                                                    // }
                                                    // return prevRec["timezone"] === curRec["timezone"] && curRec["timezone"] != "";     
                                                    return prevRec[columnKey] === curRec[columnKey];
                                                } 
                                                return prevRec[columnKey] === curRec[columnKey];                                                                                     
                                            }
                                            // return prevRec["method"] === curRec["method"] && curRec["method"] != "";
                                            return prevRec[columnKey] === curRec[columnKey];
                                        }
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
                                caseSensitive: false,
                                columnSettings: [                                    
                                    { columnKey: 'desireDay', 
                                        conditionList:["same", "beforeAndEqual", "afterAndEqual"],
                                        customConditions: {
                                        same: {
                                                labelText: "= ～に等しい",
                                                expressionText: "～に等しい",
                                                requireExpr: true,
                                                filterFunc: self.equal
                                            },
                                        afterAndEqual: {
                                                labelText: ">= 以上",
                                                expressionText: "以上",
                                                requireExpr: true,
                                                filterFunc: self.afterAndEqual
                                            },
                                        beforeAndEqual: {
                                            labelText: "<= 以下",
                                            expressionText: "以下",
                                            requireExpr: true,
                                            filterFunc: self.beforeAndEqual
                                        }
                                    }
                                },
                                 
                                    { columnKey: 'employeeCdName', conditionList: ["contains", "doesNotContain"] },
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
                    $("#grid").focus();   
                } 
              
            }).fail(() => {
                self.$dialog.error({ messageId: "Msg_37" });
            }).always(() => {
                self.$blockui('hide');
            });
        }

        equal(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if(isNaN(parseInt(expression))) {
                return parseInt(value.replaceAll('/','')) == 99999999;
            }
            return parseInt(value.replaceAll('/','')) == parseInt(expression.replaceAll('/',''));
        }
        beforeAndEqual(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if(isNaN(parseInt(expression))) {
                return parseInt(value.replaceAll('/','')) == 99999999;
            }
            return parseInt(value.replaceAll('/','')) <= parseInt(expression.replaceAll('/',''));
        }
        afterAndEqual(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if(isNaN(parseInt(expression))) {
                return parseInt(value.replaceAll('/','')) == 99999999;
            }
            return parseInt(value.replaceAll('/','')) >= parseInt(expression.replaceAll('/',''));
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