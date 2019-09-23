module nts.uk.hr.view.jhc002.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        processingDate: KnockoutObservable<string>;
        keySearch: KnockoutObservable<string>;
        employeeList: [];
        
        btnSelectionText : KnockoutObservable<string>;
        isShowfull : KnockoutObservable<boolean>;
        
        
        constructor() {
            var self = this;
            self.processingDate = ko.observable("");
            self.keySearch = ko.observable("");
            self.employeeList = [];
            self.isShowfull = ko.observable(false);
            self.btnSelectionText = getText('CCG029_A1_5','部門');
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            for (var i = 0; i < 20; i++) {
              self.employeeList.push({id: i, employeeCode: 'employeeCode', employeeName: 'employeeName', katakanaName: 'katakana', departmentCode: 'departmentCode', department: 'department'});
            }
            self.bindData();
            dfd.resolve();
            block.clear();
            return dfd.promise();
        }
        
        public expandDipslay(): void {
            var self = this;
            if(self.isShowfull()){
                $("#gridListEmployees").igGridHiding("hideColumn", "katakanaName");
                $("#gridListEmployees").igGridHiding("hideColumn", "departmentCode");
                $( "#gridListEmployeesContent" ).removeClass( "showFullColumn" );
                self.isShowfull(false);
            }else{
                $("#gridListEmployees").igGridHiding("showColumn", "katakanaName");
                $("#gridListEmployees").igGridHiding("showColumn", "departmentCode");
                $( "#gridListEmployeesContent" ).addClass( "showFullColumn" );
                self.isShowfull(true);
            }
        }
        
        public bindData(): void {
            var self = this;
            $("#gridListEmployees").igGrid({
                autoGenerateColumns: false,
                primaryKey: 'id',
                columns: [
                    {   
                        headerText: 'id', key: 'id', hidden: true
                    },
                    {
                        headerText: getText('CCG029_A1_25'), key: 'employeeCode', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('CCG029_A1_26'), key: 'employeeName', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('CCG029_A1_27'), key: 'katakanaName', dataType: 'string',  width: '100px'
                    },
                    {
                        headerText: getText('CCG029_A1_28'), key: 'departmentCode', dataType: 'string',  width: '100px'
                    },
                    {
                        headerText: getText('CCG029_A1_29'), key: 'department', dataType: 'string', width: '100px'
                    }
                ],
                dataSource: self.employeeList,
                dataSourceType: 'json',
                responseDataKey: 'results',
                height: '100%',
                width: '100%',
                tabIndex: 7,
                features: [
                    {
                        name: "Selection",
                        mode: "row",
                        multipleSelection: false
                    },
                    {
                        name: 'Filtering', 
                        type: 'local',
                        mode: 'simple'
                    },
                    {
                        name: 'Sorting', 
                        type: 'local'
                    },
                    {
                        name: 'Resizing' 
                    },
                    {
                        name: "Hiding",
                        columnSettings: [
                            { columnKey: "employeeCode", allowHiding: false},
                            { columnKey: "employeeName", allowHiding: false},
                            { columnKey: "katakanaName", allowHiding: false, hidden: true },
                            { columnKey: "departmentCode", allowHiding: false, hidden: true },
                            { columnKey: "department", allowHiding: false}
                        ]
                    }
                ]
            });
        }
    }

    class CareerType {
        id: string;
        code: string;
        name: string;
        enable: KnockoutObservable<boolean> = ko.observable(false);
        constructor(obj: any) {
            this.id = obj.id
            this.code = obj.code;
            this.name = obj.name;
        }
    }
}
