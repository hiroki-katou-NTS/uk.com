module nts.uk.at.view.ksu001.g {
    import getText = nts.uk.resource.getText;
    @bean()
    class Ksu001GViewModel extends ko.ViewModel {
        constructor(params: any) {
            var productData2 = [
                { date: "2020/10/15", codename: "000000000", desirse: "休日", shift:"CA 1", timezone: "8:00~15:00", remark: "Data Import"},
                { date: "2020/10/16", codename: "0000000002", desirse: "時間帯", shift:"CA 1", timezone: "16:00~29:00", remark: "Code"},
                { date: "2020/10/16", codename: "0000000003", desirse: "休日", shift:"CA 1", timezone: "8:00~15:00", remark: "QA"},
                { date: "2020/10/17", codename: "0000000004", desirse: "シフト", shift:"CA 1", timezone: "8:00~19:00", remark: "Test"},
                { date: "2020/10/17", codename: "0000000005", desirse: "時間帯", shift:"CA 1", timezone: "8:00~15:00", remark: "Code"},
                { date: "2020/10/17", codename: "0000000006", desirse: "休日", shift:"CA 1", timezone: "16:00~29:00", remark: "Design"},
                { date: "2020/10/18", codename: "0000000007", desirse: "時間帯", shift:"CA 1", timezone: "8:00~15:00", remark: "QA"},
 				{ date: "2020/10/17", codename: "0000000005", desirse: "時間帯", shift:"CA 1", timezone: "8:00~15:00", remark: "Code"},
                { date: "2020/10/17", codename: "0000000006", desirse: "休日", shift:"CA 1", timezone: "16:00~29:00", remark: "Design"},
                { date: "2020/10/18", codename: "0000000007", desirse: "時間帯", shift:"CA 1", timezone: "8:00~15:00", remark: "QA"},
{ date: "2020/10/18", codename: "0000000007", desirse: "時間帯", shift:"CA 1", timezone: "8:00~15:00", remark: "QA"}
            ]

            var productData1 = [
                { id: '', Name: "なし" },
                { id: '休日', Name: "休日" },
                { id: 'シフト', Name: "シフト" },
                { id: '時間帯', Name: "時間帯" },
            ]

            super();
            $("#grid").igGrid({
                width: "800px",
                height: "420px",
                dataSource: productData2,
                dataSourceType: "json",
                // primaryKey: "date",
                autoGenerateColumns: false,      
                responseDatakey: "results" ,         
                columns: [
                    { headerText: getText('KSU001_4032'), key: "date", dataType: "string" },
                    { headerText: getText('KSU001_4033'), key: "codename", dataType: "string", width: "30%" },
                    { headerText: getText('KSU001_4034'), key: "desirse", dataType: "string"  },
                    { headerText: getText('KSU001_4035'), key: "shift" },
                    { headerText: getText('KSU001_4036'), key: "timezone" },
                    { headerText: getText('KSU001_4037'), key: "remark" }
                ],
                // autoCommit: true,
                features: [
                    {
                        name: "CellMerging",
                        mergeOn: "always",
                        mergeType: "physical",
                        mergeStrategy: function (prevRec, curRec, columnKey) {
                            if ($.type(prevRec[columnKey]) === "string" &&
                                $.type(curRec[columnKey]) === "string" &&
                                prevRec["date"] === curRec["date"]) {
                                return prevRec[columnKey].toLowerCase() === curRec[columnKey].toLowerCase();
                            } else if (prevRec["date"] === curRec["date"]) {
                                return prevRec[columnKey] === curRec[columnKey];
                            } else if (prevRec["timezone"] === curRec["timezone"]) {
                                return prevRec[columnKey] === curRec[columnKey];
                            } else if (prevRec["desirse"] === curRec["desirse"]) {
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
                            {  columnKey: 'date', condition: "equals" },
                            {columnKey: 'codename', condition: "startsWith" },
                            { columnKey: "shift", allowFiltering: false },
                            { columnKey: "timezone", allowFiltering: false },
                            { columnKey: "remark", allowFiltering: false },
                            {columnKey: "desirse", editorType: 'combo',
                                conditionList: [
                                                "equals"
                                            ],
                                editorOptions: {
                                    mode: "dropdown",
                                    dataSource: productData1,
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

 		clearFilter(){
            $("#grid").igGridFiltering("filter", [], true);
        }

 		closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }
    }
}