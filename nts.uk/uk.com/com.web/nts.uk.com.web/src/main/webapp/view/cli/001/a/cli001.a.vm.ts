var multiple = true;
var products = [
    { "ProductID": 1, "Name": "Adjustable Race", "ProductNumber": "AR-5381","code": 'a1'},
    { "ProductID": 2, "Name": "Bearing Ball", "ProductNumber": "BA-8327" ,"code": 'a2' },
    { "ProductID": 3, "Name": "BB Ball Bearing", "ProductNumber": "BE-2349" ,"code": 'a3'},
    { "ProductID": 4, "Name": "Headset Ball Bearings", "ProductNumber": "BE-2908" ,"code": 'a4'},
    { "ProductID": 316, "Name": "Blade", "ProductNumber": "BL-2036" ,"code": 'a5'},
    { "ProductID": 317, "Name": "LL Crankarm", "ProductNumber": "CA-5965" ,"code": 'a6'},
    { "ProductID": 318, "Name": "ML Crankarm", "ProductNumber": "CA-6738" ,"code": 'a7'},
    { "ProductID": 319, "Name": "HL Crankarm", "ProductNumber": "CA-7457" ,"code": 'a8'},
    { "ProductID": 320, "Name": "Chainring Bolts", "ProductNumber": "CB-2903" ,"code": 'a9'},
    { "ProductID": 5, "Name": "Adustable Race", "ProductNumber": "AR-5383" ,"code": 'a1'},
    { "ProductID": 6, "Name": "Adjusable Race", "ProductNumber": "AR-5389" ,"code": 'a1'},
    { "ProductID": 7, "Name": "djustable Race", "ProductNumber": "AR-5387" ,"code": 'a1'}
];
module nts.uk.com.view.cli001.a {


    export module viewmodel {

        export class ScreenModel {
            dataSource: any;
            selectedList: any;

            constructor() {
                this.items = ko.observableArray([]);
                self.dataSource = products;
                self.selectedList = ko.observableArray([]);
                var features = [];
                features.push({
                    name: 'Selection',
                    mode: 'row',
                    multipleSelection: multiple,
                    activation: false,
                    rowSelectionChanged: this.selectionChanged.bind(this)
                });
                features.push({ name: 'Sorting', type: 'local' });
                features.push({ name: 'RowSelectors', enableCheckBoxes: multiple, enableRowNumbering: true });

                $("#grid").igGrid({
                    columns: [
                        { headerText: nts.uk.resource.getText("CLI001_12"),key: "ProductID", dataType: "number" },
                        { headerText: nts.uk.resource.getText("CLI001_13"), key: "Name" ,dataType: "string"},
                        { headerText: nts.uk.resource.getText("CLI001_14"), key: "ProductNumber", dataType: "string"},
                        { headerText: nts.uk.resource.getText("CLI001_15"), key: "code", dataType: "string"},
                    ],
                    features: [{
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true,
                        activation: false,
                        rowSelectionChanged: this.selectionChanged.bind(this)
                    },
                        { name: 'Sorting', type: 'local' },
                        { name: 'RowSelectors', enableCheckBoxes: true, enableRowNumbering: true }
                    ],
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    width: "500px",
                    height: "240px",
                    primaryKey: "ProductID",
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



            /**
            * Save
            */
            public save() {

            }


        }
    }