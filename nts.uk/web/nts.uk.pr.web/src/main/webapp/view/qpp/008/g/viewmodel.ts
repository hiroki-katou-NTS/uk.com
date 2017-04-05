module qpp008.g.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        firstLoad: KnockoutObservable<boolean>;
        /*checkBox*/
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        /*switchButton*/
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);

            /*checkBox*/
            var self = this;
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
            self.selectedRuleCode = ko.observable(1);
            /*switchButton*/
            self.roundingRules = ko.observableArray([
                { code: '1', name: 'すべて' },
                { code: '2', name: '未確認' },
                { code: '3', name: '確認済み' }
            ]);
            /*iggrid*/
            self.firstLoad = ko.observable(true);
            var _isDataBound = false;
            var _checkAll = false;
            var _checkAllValue = false;

            /////////////event of iggrid
            $("#grid10").on("iggriddatabound", function(event, ui) {
                if (_checkAll) {
                    //                    return true;
                } else if (_isDataBound === false) {
                    _isDataBound = false;
                } else {
                    return;
                }
                let grid = ui.owner,
                    ds = grid.dataSource,
                    data = ds.data(),
                    dataLength = data.length;
                for (let i = 0; i < dataLength; i++) {
                    if (_checkAll) {
                        if (data[i]["Difference"] < 6 || data[i]["Difference"] > 20) {
                            data[i]["Confirm"] = _checkAllValue;
                        }
                    }
                    else if (self.firstLoad()) {
                        if (data[i]["Difference"] < 6 || data[i]["Difference"] > 20) {
                            data[i]["Confirm"] = true;
                        }
                    }
                }
                self.firstLoad = ko.observable(false);
            });
            // event editable cell
            $(document).delegate("#grid10", "iggridupdatingeditrowstarting", function(ui: any) {
                let $cell = $($("#grid10").igGrid("cellById", ui.rowID, "Difference"));
                if (!($cell.hasClass("red") || $cell.hasClass("yellow"))) {
                    return false;

                }

                // get reference to igGridUpdating widget
                ui.owner;

                // to get key or index of row
                ui.rowID;

                // check if that event is raised while new-row-adding
                ui.rowAdding;
            });

            //change color by delegate
                        $(document).delegate("#grid10", "iggriddatarendered", function(ui: any) {
                            _.forEach(ui.owner.dataSource.dataView(), function(item, index) {
                                if (item["Difference"] > 20) {
                                    let cell1 = $("#grid10").igGrid("cellById", item["EmpCode"], "Difference");
                                    $(cell1).addClass('red').attr('data-class', 'red');
                                    $(window).on('resize', () => { $(cell1).addClass('red'); });
                                    //item["IsPromotion"] = true;
                                } else if (item["Difference"] < 6) {
                                    let cell1 = $("#grid10").igGrid("cellById", item["EmpCode"], "Difference");
                                    $(cell1).addClass('yellow').attr('data-class', 'yellow');
                                    $(window).on('resize', () => { $(cell1).addClass('yellow'); });
                                    
                                   //item["IsPromotion"] = true;
                              } else {
                                   //item["IsPromotion"] = false;
                                }
                            });
                        });
            //instantiation

            let iggridData = new Array();
            let str = ['a0', 'b0', 'c0', 'd0', 'eo', 'f0', 'g0', 'h0', 'i0', 'j0', 'k0'];
            for (let j = 0; j < 4; j++) {
                for (let i = 1; i < 41; i++) {
                    let code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    let CompareDate1 = Math.floor((Math.random() * 5000) + 150);
                    let CompareDate2 = Math.floor((Math.random() * 5000) + 150);
                    let Difference = Math.floor((Math.random() * 20) + 5);
                    iggridData.push({
                        "EmpCode": code, "EmpName": code, "Classification": '未', "ItemName": '項目名', "CompareDate1": CompareDate1,
                        "CompareDate2": CompareDate2, "Difference": Difference, "Reason": 'diference', "RegisStatus1": 'aa', "RegisStatus2": 'bb', "Confirm": true
                    });
                }
            }
            $("#grid10").igGrid({
                primaryKey: "EmpCode",
                dataSource: iggridData,//Datasources
                width: "1260",
                autoGenerateColumns: false,
                //dataSourceType: "Json",
                //responseDataKey: "results",
                columns: [
                    { headerText: "％社員％コード", key: "EmpCode", dataType: "string" },
                    { headerText: "氏名", key: "EmpName", dataType: "string" },
                    { headerText: "区分", key: "Classification", dataType: "string", width: 30 },
                    { headerText: "項目名", key: "ItemName", dataType: "string" },
                    { headerText: "比較年月1", key: "CompareDate1", dataType: "number" },
                    { headerText: "比較年月2", key: "CompareDate2", dataType: "number" },
                    { headerText: "差額", key: "Difference", dataType: "number" },
                    { headerText: "差異理由", key: "Reason", dataType: "string" },
                    { headerText: "登録状況（比較年月１）", key: "RegisStatus1", dataType: "string" },
                    { headerText: "登録状況（比較年月2）", key: "RegisStatus2", dataType: "string" },
                    { headerText: "確認済  <br/><input type='checkbox' id='cb1'/>", key: "Confirm", dataType: "bool", unbound: true, format: "checkbox", width: 60 }
                ],
                features:
                [
                    {
                        name: "Paging",
                        type: "local",
                        pageIndexChanged: function(ui: any) {

                        },
                        pageSize: 20
                    },
                    {
                        name: "Updating",
                        editMode: "row",
                        enableAddRow: false,
                        enableDeleteRow: true,
                        columnSettings: [
                            {
                                columnKey: "EmpCode",
                                readOnly: true
                            },
                            {
                                columnKey: "EmpName",
                                readOnly: true
                            },
                            {
                                columnKey: "Classification",
                                readOnly: true
                            },
                            {
                                columnKey: "ItemName",
                                readOnly: true
                            },
                            {
                                columnKey: "CompareDate1",
                                readOnly: true
                            },
                            {
                                columnKey: "CompareDate2",
                                readOnly: true
                            },
                            {
                                columnKey: "Difference",
                                editorType: "numeric",
                                readOnly: true
                            },
                            {
                                columnKey: "Reason",
                                readOnly: false
                            },
                            {
                                columnKey: "RegisStatus1",
                                readOnly: true
                            },
                            {
                                columnKey: "RegisStatus2",
                                readOnly: true
                            },
                            {
                                columnKey: "IsPromotion",
                                editorType: "checkbox",
                                readOnly: false
                            }
                        ]
                    }
                ]
            });
            //checkBox
            $("#cb1").on("click", function(event, ui) {
                var value = $(this).is(":checked");
                _checkAll = true;
                _checkAllValue = value;
                $("#grid10").igGrid("dataBind");
            });

        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
    }
}