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
        // listWorkAvailabilitys: KnockoutObservableArray<IWorkAvailabilityOfOneDay> = ko.observableArray([]);
        period: string = "";
        constructor(params: any) {
            super();
            const self = this;
            self.loadWorkAvailabilityOfOneDay();
            $('#grid').focus();
            $('#grid_scroll').focus();
        }

        loadWorkAvailabilityOfOneDay(): void {
            const self = this;
            let listData: Array<any> = [];    
            let dataAll: Array<any> = [];            
            let arrOneDay: Array<any> = [];

            let timezoneStart: string;
            let timezoneEnd: string;
            let request: any = getShared('dataShareDialogG');
            self.period = request.startDate + "～" + request.endDate;
            self.$ajax(Paths.GET_WORK_AVAILABILITY_OF_ONE_DAY, request).done((data: Array<IWorkAvailabilityOfOneDay>) => {
                self.$blockui("show");
                if (data && data.length > 0) {
                    let count: number = 0;
                    let convertedData: Array<IWorkAvailabilityOfOneDay> = [];
                    // format lại timezone hh:mm
                    listData = _.forEach(data, e =>{
                        if(e.timezone.trim()!=""){
                            let timezoneSplit = e.timezone.trim().split('～');
                            if(timezoneSplit[0].length < 5){
                               timezoneStart = "0".concat(timezoneSplit[0]);
                            } else {
                                timezoneStart = timezoneSplit[0];
                            }

                            if(timezoneSplit[1].length < 5){
                                timezoneEnd = "0".concat(timezoneSplit[1]);
                             } else {
                                 timezoneEnd = timezoneSplit[1];
                             }
                            
                           return e.timezone = timezoneStart + '～'+ timezoneEnd;
                        } else {
                            return e;
                        }
                    });
                    let grouped_data = _.groupBy(listData, 'employeeCdName');
                    _.forEach(grouped_data,function(el,index,arr){
                        let addSpace: string = "";
                        for (let i = 0; i < count; i++) {
                            addSpace += " ";
                        }
                        count += 1;
                        _.forEach(el,function(el1,index1,arr1){
                            convertedData.push({ 
                                desireDay : el1.desireDay,
                                employeeCdName : el1.employeeCdName,
                                method : el1.method + addSpace,
                                remarks : el1.remarks + addSpace,
                                shift : el1.shift + addSpace,
                                timezone : el1.timezone + addSpace});
                        })
                     });
                    
                    let dataAllTmp = _.sortBy(convertedData, item => item.desireDay);
                    //sort list by date
                    let dataTmp = _.sortBy(listData, item => item.desireDay);
                    let listDate = _.uniqBy(_.map(dataTmp, x => x.desireDay), y => y);
                    _.forEach(listDate, date => {
                        // tách mảng giá trị theo ngày
                        let arrByDay = _.filter(dataAllTmp, data => {                            
                            return data.desireDay === date;                                                
                        });

                        _.forEach(arrByDay, e =>{
                            if(e.timezone.trim() != ""){

                            }
                        });
                        // tách mảng theo timezone từ mảng đã tách theo ngày
                        let arrTimezone = _.filter(arrByDay, e => {
                            return e.timezone.trim() != "";
                        } );

                        
                        // tách mảng không có timezone từ mảng đã tách theo ngày
                        let arrNoTimezone = _.difference(arrByDay, arrTimezone);

                        // tạo lại mảng đã tách theo ngày với timezone đã được sort
                        arrOneDay = _.union(arrNoTimezone, _.sortBy(arrTimezone, e => e.timezone ))      
                        // tạo mảng dữ liệu đã được sort theo timezone, codename                        
                        dataAll = _.union(dataAll, _.sortBy(arrOneDay, e => e.employeeCdName));
                       
                    });
                   
                    // self.listWorkAvailabilitys(dataAll);
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

                                    if (prevRec["desireDay"] === curRec["desireDay"]) {
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
                                    {
                                        columnKey: 'desireDay',                                          
                                        conditionList: ["same", "beforeAndEqual", "afterAndEqual"],                                       
                                        customConditions: {                                           
                                            same: {
                                                labelText: "= ～に等しい",
                                                expressionText: "～に等しい",
                                                requireExpr: true,
                                                filterFunc: self.equal
                                            },
                                            afterAndEqual: {
                                                labelText: "<= 以下",
                                                expressionText: "以下",
                                                requireExpr: true,
                                                filterFunc: self.beforeAndEqual
                                            },
                                            beforeAndEqual: {
                                                labelText: ">= 以上",
                                                expressionText: "以上",                                                
                                                requireExpr: true,
                                                filterFunc: self.afterAndEqual
                                            },
                                            contains:{                                                
                                                expressionText: "～に等しい",
                                                requireExpr: true,
                                                filterFunc: self.equal
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
                    self.$blockui('hide');
                    $('#grid_scroll').focus();
                    $('#grid').focus();
                    // $('table').focus();
                    // $('input:first').removeAttr('placeholder');
                    $('input:first').attr('placeholder',"= ");                   
                    $("table thead tr td:nth-child(3)").css('padding',"0px !important");
                    $("table thead tr td:nth-child(2)").css('padding',"0px !important");
                    $("td").eq(2).css('padding',"0px !important");

                } else {
                    self.$dialog.error({ messageId: "Msg_37" });
                    // $('#grid').focus();

                }
                
            }).fail(() => {
                self.$dialog.error({ messageId: "Msg_37" });
            }).always(() => {
                self.$blockui('hide');              
            });         
        }

        equal(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.replaceAll('/', '')) == 99999999;
            }
            return parseInt(value.replaceAll('/', '')) == parseInt(expression.replaceAll('/', ''));
        }
        beforeAndEqual(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.replaceAll('/', '')) == 99999999;
            }
            return parseInt(value.replaceAll('/', '')) <= parseInt(expression.replaceAll('/', ''));
        }
        afterAndEqual(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.replaceAll('/', '')) == 99999999;
            }
            return parseInt(value.replaceAll('/', '')) >= parseInt(expression.replaceAll('/', ''));
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
        /** 年月日 */
        desireDay: string;

        /** コード／名称*/
        employeeCdName: string;

        /** コード／名称*/
        method: string;

        /** 表示情報.名称リスト */
        shift: string;

        /** 表示情報.時間帯リスト */
        timezone: string;

        /** 勤務希望のメモ */
        remarks: string;
    }
}