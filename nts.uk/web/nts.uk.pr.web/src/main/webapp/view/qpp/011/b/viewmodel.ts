// TreeGrid Node
module qpp011.b {

    export class ScreenModel {
        RadioItemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        //combobox
        ComboBoxItemList: KnockoutObservableArray<ComboboxItemModel>;
        itemName: KnockoutObservable<string>;
        ComboBoxCurrentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //
        selectedValue_B_LST_001: any;
        selectedValue_C_LST_001: any;
        //gridlist
        files: any;
        //number editter
        numbereditor: any;
        constructor() {
            var self = this;
            //start radiogroup data
            self.RadioItemList = ko.observableArray([
                new BoxModel(1, '譛ｬ遉ｾ'),
                new BoxModel(2, '豕募ｮ夊ｪｿ譖ｸ蜃ｺ蜉帷畑莨夂､ｾ')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            //end radiogroup data
            //start combobox data
            self.ComboBoxItemList = ko.observableArray([
                new ComboboxItemModel('0001', 'Item1'),
                new ComboboxItemModel('0002', 'Item2'),
                new ComboboxItemModel('0003', 'Item3')
            ]);
            self.ComboBoxCurrentCode = ko.observable(1);
            self.selectedCode = ko.observable('0001')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //end combobox data
            // start gridlist
            //B_LST_001
            //end gridlist
            //start number editer
            self.numbereditor = {
                value: ko.observable(),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 0,
                    decimallength: 0,
                    placeholder: "",
                    width: "",
                    textalign: "right"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            }
            //end number editer
            self.files = [
                {
                    "id": 1, "name": "Documents", "dateModified": "9/12/2013", "type": "File Folder", "size": 4480, "files": [
                        { "id": 2, "name": "To do list.txt", "dateModified": "11/5/2013", "type": "TXT File", "size": 4448 },
                        { "id": 3, "name": "Work.txt", "dateModified": "9/12/2013", "type": "TXT File", "size": 32 }
                    ]
                },
                {
                    "id": 4, "name": "Music", "dateModified": "6/10/2014", "type": "File Folder", "size": 5594, "files": [
                        {
                            "id": 5, "name": "AC/DC", "dateModified": "6/10/2014", "type": "File Folder", "size": 2726, "files": [
                                { "id": 6, "name": "Stand Up.mp3", "dateModified": "6/10/2014", "type": "MP3 File", "size": 456 },
                                { "id": 7, "name": "T.N.T.mp3", "dateModified": "6/10/2014", "type": "MP3 File", "size": 1155 },
                                { "id": 8, "name": "The Jack.mp3", "dateModified": "6/10/2014", "type": "MP3 File", "size": 1115 }
                            ]
                        },
                        {
                            "id": 9, "name": "WhiteSnake", "dateModified": "6/11/2014", "type": "File Folder", "size": 2868, "files": [
                                { "id": 10, "name": "Trouble.mp3", "dateModified": "6/11/2014", "type": "MP3 File", "size": 1234 },
                                { "id": 11, "name": "Bad Boys.mp3", "dateModified": "6/11/2014", "type": "MP3 File", "size": 522 },
                                { "id": 12, "name": "Is This Love.mp3", "dateModified": "6/11/2014", "type": "MP3 File", "size": 1112 },
                            ]
                        }
                    ]
                },
                {
                    "id": 13, "name": "Pictures", "dateModified": "1/9/2014", "type": "File Folder", "size": 1825, "files": [
                        {
                            "id": 14, "name": "Jacks Birthday", "dateModified": "6/9/2014", "type": "File Folder", "size": 631, "files": [
                                { "id": 15, "name": "Picture1.png", "dateModified": "6/9/2014", "type": "PNG image", "size": 493 },
                                { "id": 16, "name": "Picture2.png", "dateModified": "6/9/2014", "type": "PNG image", "size": 88 },
                                { "id": 17, "name": "Picture3.gif", "dateModified": "6/9/2014", "type": "GIF File", "size": 50 },
                            ]
                        },
                        {
                            "id": 18, "name": "Trip to London", "dateModified": "3/10/2014", "type": "File Folder", "size": 1194, "files": [
                                { "id": 19, "name": "Picture1.png", "dateModified": "3/10/2014", "type": "PNG image", "size": 974 },
                                { "id": 20, "name": "Picture2.png", "dateModified": "3/10/2014", "type": "PNG image", "size": 142 },
                                { "id": 21, "name": "Picture3.png", "dateModified": "3/10/2014", "type": "PNG image", "size": 41 },
                                { "id": 22, "name": "Picture4.png", "dateModified": "3/10/2014", "type": "PNG image", "size": 25 },
                                { "id": 23, "name": "Picture5.png", "dateModified": "3/10/2014", "type": "PNG image", "size": 12 },
                            ]
                        }
                    ]
                },
                {
                    "id": 24, "name": "Videos", "dateModified": "1/4/2014", "type": "File Folder", "size": 0

                },
                {
                    "id": 25, "name": "Books", "dateModified": "1/4/2014", "type": "File Folder", "size": 99376, "files": [
                        {
                            "id": 26, "name": "James Rollins", "dateModified": "6/2/2014", "type": "File Folder", "size": 34228, "files": [
                                { "id": 27, "name": "Alter of Eden.pdf", "dateModified": "6/5/2014", "type": "Adobe Acrobat Document", "size": 8894 },
                                { "id": 28, "name": "Amazonia.pdf", "dateModified": "3/2/2014", "type": "Adobe Acrobat Document", "size": 6212 },
                                { "id": 29, "name": "Subterranean.pdf", "dateModified": "1/4/2014", "type": "Adobe Acrobat Document", "size": 4820 },
                                { "id": 30, "name": "Sandstorm.pdf", "dateModified": "2/2/2014", "type": "Adobe Acrobat Document", "size": 14302 }
                            ]
                        },
                        {
                            "id": 31, "name": "Stephen King", "dateModified": "3/10/2014", "type": "File Folder", "size": 65148, "files": [
                                { "id": 32, "name": "It.pdf", "dateModified": "3/10/2014", "type": "Adobe Acrobat Document", "size": 9987 },
                                { "id": 33, "name": "Misery.pdf", "dateModified": "3/10/2014", "type": "Adobe Acrobat Document", "size": 32313 },
                                { "id": 34, "name": "Pet Sematary.pdf", "dateModified": "3/10/2014", "type": "Adobe Acrobat Document", "size": 22848 }
                            ]
                        }
                    ]
                },
                { "id": 35, "name": "Games", "dateModified": "8/8/2014", "type": "File Folder", "size": 0 },
                {
                    "id": 36, "name": "Projects", "dateModified": "7/4/2014", "type": "File Folder", "size": 4, "files": [
                        {
                            "id": 37, "name": "Visual Studio 2012", "dateModified": "7/4/2014", "type": "File Folder", "size": 1, "files": [
                                { "id": 38, "name": "Project10.sln", "dateModified": "7/4/2014", "type": "Microsoft Visual Studio Solution", "size": 1 }
                            ]
                        },
                        {
                            "id": 39, "name": "Visual Studio 2013", "dateModified": "9/6/2014", "type": "File Folder", "size": 3, "files": [
                                { "id": 40, "name": "Project1.sln", "dateModified": "9/6/2014", "type": "Microsoft Visual Studio Solution", "size": 1 },
                                { "id": 41, "name": "Project2.sln", "dateModified": "9/6/2014", "type": "Microsoft Visual Studio Solution", "size": 1 },
                                { "id": 42, "name": "Project3.sln", "dateModified": "9/6/2014", "type": "Microsoft Visual Studio Solution", "size": 1 }
                            ]
                        }
                    ]
                }
            ];
            BindGrid("#B_LST_001");
            BindGrid("#C_LST_001");
            function BindGrid(gridID) {
                $(gridID).igTreeGrid({
                    width: "480px",
                    height: "500px",
                    dataSource: self.files,
                    autoGenerateColumns: false,
                    primaryKey: "id",
                    columns: [
                        { headerText: "ID", key: "id", width: "250px", dataType: "number", hidden: true },
                        { headerText: "Name", key: "name", width: "300px", dataType: "string" },
                        { headerText: "Date Modified", key: "dateModified", width: "130px", dataType: "date", hidden: true },
                        { headerText: "Type", key: "type", width: "230px", dataType: "string", hidden: true },
                        { headerText: "Size in KB", key: "size", width: "130px", dataType: "number", hidden: true }
                    ],
                    childDataKey: "files",
                    initialExpandDepth: 2,
                    features: [
                        {
                            name: "Selection",
                            multipleSelection: true
                        },
                        {
                            name: "RowSelectors",
                            enableCheckBoxes: true,
                            checkBoxMode: "biState",
                            enableSelectAllForPaging: true,
                            enableRowNumbering: false
                        }]
                });
            }

            self.selectedValue_B_LST_001 = ko.observableArray([]);
            let $B_LST_001 = $("#B_LST_001");
            self.selectedValue_B_LST_001.subscribe(function(newValue) {
                let selectedRows = _.map($B_LST_001.igTreeGridSelection("selectedRows"), function(row) {
                    return row.id;
                });
                if (!_.isEqual(selectedRows, newValue)) {
                    $B_LST_001.igTreeGridSelection("clearSelection");
                    newValue.forEach(function(id) {
                        $B_LST_001.igTreeGridSelection("selectRowById", id);
                    });
                }
            });
            self.selectedValue_C_LST_001 = ko.observableArray([]);
            let $C_LST_001 = $("#B_LST_001");
            self.selectedValue_C_LST_001.subscribe(function(newValue) {
                let selectedRows = _.map($C_LST_001.igTreeGridSelection("selectedRows"), function(row) {
                    return row.id;
                });
                if (!_.isEqual(selectedRows, newValue)) {
                    $C_LST_001.igTreeGridSelection("clearSelection");
                    newValue.forEach(function(id) {
                        $C_LST_001.igTreeGridSelection("selectRowById", id);
                    });
                }
            });
        }

        add() {
            var self = this;
            var obj = {
                "resimentTaxCode": resimentTaxCode,

                "yearMonth": yearMonth,

                "taxBonusMoney": taxBonusMoney,

                "taxOverDueMoney": taxOverDueMoney,

                "taxDemandChargeMoyney": taxDemandChargeMoyney,

                "address": address,

                "dueDate": dueDate,

                "headcount": headcount,

                "retirementBonusAmout": retirementBonusAmout,

                "cityTaxMoney": cityTaxMoney,

                "prefectureTaxMoney": prefectureTaxMoney
            };

            service.add(obj).done(function() {
                //do something    
            }).fail(function(response) {
                alert(response.message);
            })
        }
        update() {
            var self = this;
            var obj = new service.model.residentialTax(
            
                
                );

            service.update().done(function() {
                //do something    
            }).fail(function(response) {
                alert(response.message);
            })
        }

        openFDialog() {
            nts.uk.ui.windows.sub.modal('/view/qpp/011/f/index.xhtml', { height: 550, width: 740, dialogClass: 'no-close' }).onClosed(function(): any {
            });
        }
        openDDialog() {
            nts.uk.ui.windows.sub.modal('/view/qpp/011/d/index.xhtml', { height: 550, width: 1000, dialogClass: 'no-close' }).onClosed(function(): any {
            });
        }
    }
    
    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    export class ComboboxItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class GridItemModel_C_LST_001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class GridItemModel_B_LST_001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }

    }


};
