module nts.uk.at.view.kdp003.c {
    import getText = nts.uk.resource.getText;
    export module viewmodel {



        export class ScreenModel {

            dataSource: any;
            selectedList: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.dataSource = [
                    { "userId": "1", "loginId": "1", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "2", "loginId": "2", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "3", "loginId": "3", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "4", "loginId": "4", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "5", "loginId": "5", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "6", "loginId": "6", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "7", "loginId": "7", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "8", "loginId": "8", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "9", "loginId": "9", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "10", "loginId": "10", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "11", "loginId": "11", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "12", "loginId": "12", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "13", "loginId": "13", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "14", "loginId": "14", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "15", "loginId": "15", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "16", "loginId": "16", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "17", "loginId": "17", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "18", "loginId": "18", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "19", "loginId": "19", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "20", "loginId": "20", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "21", "loginId": "21", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "22", "loginId": "22", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "23", "loginId": "23", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "24", "loginId": "24", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }
                ];
                self.selectedList = ko.observableArray([]);
                var features = [];
                features.push({
                    name: 'Selection',
                    mode: 'row',
                    activation: false,
                    rowSelectionChanged: this.selectionChanged.bind(this)
                });
                features.push({ name: 'Sorting', type: 'local' });
                features.push({ name: 'RowSelectors', enableRowNumbering: true });

                $("#grid").igGrid({
                    columns: [
                        { headerText: nts.uk.resource.getText("KDP003_40"), key: "userId", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_41"), key: "loginId", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_42"), key: "userName", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_43"), key: "lockOutDateTime", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_44"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_45"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_46"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_47"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_48"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_49"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_50"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_51"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_52"), key: "logType", dataType: "string", width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_53"), key: "logType", dataType: "string", width: 100 },
                    ],
                    features: [{
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false,
                        activation: false,
                        rowSelectionChanged: this.selectionChanged.bind(this)
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
                    dataSource: self.dataSource
                });
                $("#grid").closest('.ui-iggrid').addClass('nts-gridlist');
                $("#grid").setupSearchScroll("igGrid", true);

            }

            selectionChanged(evt, ui) {
                //console.log(evt.type);
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
                let _self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();

                return dfd.promise();
            }

        }
    }


}