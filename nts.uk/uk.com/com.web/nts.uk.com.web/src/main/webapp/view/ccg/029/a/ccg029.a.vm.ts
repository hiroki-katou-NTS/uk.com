module nts.uk.hr.view.jhc002.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        processingDate: KnockoutObservable<string>;
        keySearch: KnockoutObservable<string>;
        employeeList: [];
        
        btnSelectionText : KnockoutObservable<string>;
        isShowfull : KnockoutObservable<boolean>;
        callback: () => any;
        input: Input;
        
        constructor(param: Input, callback: () => any) {
            var self = this;
            //param
            self.input = new Input(param);
            self.callback = callback;
            
            //control 
            self.processingDate = ko.observable(moment(new Date()).format("YYYY/MM/DD"));
            self.keySearch = ko.observable("");
            self.employeeList = [];
            self.isShowfull = ko.observable(false);
            self.btnSelectionText = getText('CCG029_A1_5','部門');
            $(".searchTips-area").ntsPopup({
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: "#searchTipsBtn"
                },
                showOnStart: false,
                dismissible: false
            });
            $("#searchTipsBtn").click(function() {
                $(".searchTips-area").ntsPopup("toggle");
            });
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
                        headerText: getText('CCG029_A1_24'), key: 'employeeCode', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('CCG029_A1_25'), key: 'employeeName', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('CCG029_A1_26'), key: 'katakanaName', dataType: 'string',  width: '100px'
                    },
                    {
                        headerText: self.input.systemType == 1 ? getText('CCG029_1') : getText('CCG029_2'), key: 'departmentCode', dataType: 'string',  width: '100px'
                    },
                    {
                        headerText: self.input.systemType == 1 ? getText('Com_Workplace') : getText('Com_Department'), key: 'department', dataType: 'string', width: '100px'
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
    
    class Input {
        systemType: number; //システム区分（0：共通、1：就業、2：給与、3：人事）
        includePreEmployee: boolean; //入社前社員を含める
        includeRetirement: boolean; //退職者を含める
        includeAbsence: boolean; //休職者を含める 
        includeClosed: boolean; //休業者を含める
        includeTransferEmployee: boolean; //出向社員を含める
        includeAcceptanceTransferEmployee: boolean; //受入出向社員を含める
        getPosition: boolean; //職位を取得する
        getEmployment: boolean; //雇用を取得する
        getPersonalFileManagert: boolean; //個人ファイル管理を取得する
        
        constructor(input: any) {
            this.systemType = input ? input.systemType || 3 : 3;
            this.includePreEmployee = input ? input.includePreEmployee || true: true;
            this.includeRetirement = input ? input.includeRetirement || true: true;
            this.includeAbsence = input ? input.includeAbsence || true: true;
            this.includeClosed = input ? input.includeClosed || true: true;
            this.includeTransferEmployee = input ? input.includeTransferEmployee || true: true;
            this.includeAcceptanceTransferEmployee = input ? input.includeAcceptanceTransferEmployee || true: true;
            this.getPosition = input ? input.getPosition || false: false;
            this.getEmployment = input ? input.getEmployment || false: false;
            this.getPersonalFileManagert = input ? input.getPersonalFileManagert || false: false;
        }
    }
    
    class CareerType {
        id: string;
        code: string;
        name: string;
        constructor(obj: any) {
            this.id = obj.id
            this.code = obj.code;
            this.name = obj.name;
        }
    }
}
