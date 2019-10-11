module nts.uk.hr.view.ccg029.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        processingDate: KnockoutObservable<string>;
        keySearch: KnockoutObservable<string>;
        employeeList: [];
        
        btnSelectionText : KnockoutObservable<string>;
        isShowfull : KnockoutObservable<boolean>;
        input: Input;
        callback: any;
        
        constructor(param: Input, callback) {
            var self = this;
            //param
            self.input = new Input(param);
            self.callback = callback
            //control 
            self.processingDate = ko.observable('');
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
            
            $('#form').on('submit', function (e) {
                e.preventDefault();
                self.search();
            });
            
            //Delegate
            $(document).delegate("#gridListEmployees", "iggridcellclick", function (evt, ui) {
                var value = _.find(self.employeeList, ['personalId', ui.rowKey]);
                console.log(value);
                self.callback(value);
            });
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            self.processingDate(moment(new Date()).format("YYYY/MM/DD"));
            self.bindData();
            dfd.resolve();
            block.clear();
            return dfd.promise();
        }
        
        public search(): void{
            var self = this;
            let param = self.input;
            param.keyword = self.keySearch();
            param.baseDate = moment(self.processingDate()).format("YYYY/MM/DD");
            self.findEmployee(param);
        }
        
        public findEmployee(param: any): void {
            var self = this;
            if(self.keySearch() == ''){
                nts.uk.ui.dialog.info({ messageId: "Msg_1571" });
                return;
            }
            block.grayout();
            nts.uk.request.ajax("com", "query/ccg029employee/find", param).done(function(data){
                self.employeeList = [];
                self.bindData();
                if(data.length == 0){
                    nts.uk.ui.dialog.info({ messageId: "Msg_1572" });
                }
                self.employeeList = data;
                self.bindData();
            }).fail((error) => {
                nts.uk.ui.dialog.info(error);
            }).always(() => {
                block.clear();
            });
        }
        
        public expandDipslay(): void {
            var self = this;
            if(self.isShowfull()){
                $("#gridListEmployees").igGridHiding("hideColumn", "businessNameKana");
                $("#gridListEmployees").igGridHiding("hideColumn", "workplaceCode");
                $( "#gridListEmployeesContent" ).removeClass( "showFullColumn" );
                self.isShowfull(false);
            }else{
                $("#gridListEmployees").igGridHiding("showColumn", "businessNameKana");
                $("#gridListEmployees").igGridHiding("showColumn", "workplaceCode");
                $( "#gridListEmployeesContent" ).addClass( "showFullColumn" );
                self.isShowfull(true);
            }
        }
        
        
        public bindData(): void {
            var self = this;
            $("#gridListEmployees").igGrid({
                autoGenerateColumns: false,
                primaryKey: 'personalId',
                columns: [
                    {   
                        headerText: 'personalId', key: 'personalId', hidden: true
                    },
                    {
                        headerText: getText('CCG029_A1_24'), key: 'employeeCode', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('CCG029_A1_25'), key: 'businessName', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('CCG029_A1_26'), key: 'businessNameKana', dataType: 'string',  width: '100px'
                    },
                    {
                        headerText: self.input.systemType == 1 ? getText('CCG029_1') : getText('CCG029_2'), key: 'workplaceCode', dataType: 'string',  width: '100px' 
                    },
                    {
                        headerText: self.input.systemType == 1 ? getText('Com_Workplace') : getText('Com_Department'), key: 'workplaceName', dataType: 'string', width: '100px'
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
                        multipleSelection: false,
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
                            { columnKey: "businessName", allowHiding: false},
                            { columnKey: "businessNameKana", allowHiding: false, hidden: self.isShowfull()? '' : true },
                            { columnKey: "workplaceCode", allowHiding: false, hidden: self.isShowfull()? '' : true },
                            { columnKey: "workplaceName", allowHiding: false}
                        ]
                    },
                    {
                        name: "Tooltips"
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
            this.getPosition = input ? input.getPosition || true: true;
            this.getEmployment = input ? input.getEmployment || true: true;
            this.getPersonalFileManagert = input ? input.getPersonalFileManagert || true: true;
        }
    }
}
