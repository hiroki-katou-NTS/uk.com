module nts.uk.at.view.ksu001.la {
    import blockUI = nts.uk.ui.block;    
    
    export module viewmodel {
        export class ScreenModel {
            listComponentOption: any;
            itemsLeft: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
            itemsRight: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
            listScheduleTeam: KnockoutObservableArray<ScheduleTeam> = ko.observableArray([]);
            columns: KnockoutObservableArray<any>;
            columnsLeft: KnockoutObservableArray<any>;
            columnsRight: KnockoutObservableArray<any>;
            currentCodeListLeft: KnockoutObservableArray<any> = ko.observableArray([]);
            currentCodeListRight: KnockoutObservableArray<any> = ko.observableArray([]);
            selectedCode: KnockoutObservable<string> = ko.observable("");
            workplaceGroupId: KnockoutObservable<string> = ko.observable("");
            workplaceGroupName: KnockoutObservable<string> = ko.observable("");
            enableDelete: KnockoutObservable<boolean> = ko.observable(true);
            isEditing: KnockoutObservable<boolean> = ko.observable(false);
            baseDate: KnockoutObservable<string> = ko.observable("");
            scheduleTeamModel: KnockoutObservable<ScheduleTeamModel> = ko.observable(new ScheduleTeamModel("", "", "", "",[]));

            constructor() {
                var self = this;

                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSU001_3208'), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText('KSU001_3209'), key: 'name', width: 150 }
                ]);

                self.columnsRight = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSU001_3208'), key: 'employeeCd', width: 100 },
                    { headerText: nts.uk.resource.getText('KSU001_3209'), key: 'businessName', width: 150 }
                ]);

                self.columnsLeft = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSU001_3208'), key: 'employeeCd', width: 100 },
                    { headerText: nts.uk.resource.getText('KSU001_3209'), key: 'businessName', width: 150 },
                    { headerText: nts.uk.resource.getText('KSU001_3215'), key: 'teamName', width: 90 }
                ]);

                self.selectedCode.subscribe((code: string) => {
                    let dfd = $.Deferred();
                    if (_.isEmpty(code)) {
                        self.clearData();
                        dfd.resolve();
                    } else {
                        service.findDetail(self.workplaceGroupId(), code).done((dataDetail: any) => {
                            if(!_.isNull(dataDetail) && !_.isEmpty(dataDetail)){
                                self.clearError();
                                self.enableDelete(true);
                                self.scheduleTeamModel().updateData(dataDetail);
                                self.scheduleTeamModel().isEnableCode(false);
                                self.scheduleTeamModel().workplaceGroupId(self.workplaceGroupId());
                                self.isEditing(true);
                                self.getEmpOrgInfo();
                            }                           
                            $('#scheduleTeamName').focus();
                            dfd.resolve();
                        })
                        
                    }
                    return dfd.promise();
                });

                // Initial listComponentOption
                self.listComponentOption = {
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedCode,
                    isDialog: false
                };
                
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                let baseDateTemp = nts.uk.ui.windows.getShared("baseDate").split('T');  
                let temp = baseDateTemp[0].split('-');
                let baseDate = temp[0]+'/'+temp[1]+'/'+temp[2];
                self.baseDate(baseDate);
                blockUI.invisible();
                let dateRequest: any = {baseDate: self.baseDate()};                
                service.findWorkplaceGroup(dateRequest).done((x: WorkplaceGroup) => {
                    let workplaceGroup = ko.toJS(x);
                    self.workplaceGroupName(workplaceGroup.workplaceGroupName);
                    self.workplaceGroupId(workplaceGroup.workplaceGroupId);
                    service.findAll(workplaceGroup.workplaceGroupId).done((listScheduleTeam: Array<ScheduleTeam>) => {
                        if (!_.isEmpty(listScheduleTeam) && !_.isNull(listScheduleTeam)) {                           
                            self.listScheduleTeam(listScheduleTeam);
                            self.selectedCode(listScheduleTeam[0].code);
                            self.getEmpOrgInfo();
                        } else {
                            self.isEditing(false);
                        }
                    });
                    
                    blockUI.clear();
                    dfd.resolve();
                });
                return dfd.promise();
            }

            private getEmpOrgInfo(): void {
                const self = this;
                let request:any = {};
                request.baseDate = self.baseDate();
                request.workplaceGroupId = self.workplaceGroupId(); 

                service.findEmpOrgInfo(request).done((dataAll: Array<ItemModel>)=>{
                    _.each(dataAll, x =>{
                        if(x.teamName === ""){
                            x.teamName = nts.uk.resource.getText('KSU001_3223');
                        } 
                    });
                    let itemLeft = _.filter(dataAll, x =>{
                        return x.teamCd != self.selectedCode();
                    });
                        self.itemsLeft(itemLeft);
                        self.itemsRight(_.difference(dataAll, itemLeft));
                });
            }
            public registerOrUpdate(): void {
                let self = this;
                if (self.validateAll()) {
                    return;
                }
                blockUI.invisible();
                if (!self.isEditing()) {                    
                    //register
                    service.register(ko.toJSON(self.scheduleTeamModel)).done(() => {
                        service.findAll(self.workplaceGroupId()).done((listScheduleTeam: Array<ScheduleTeam>) => {
                            self.listScheduleTeam(listScheduleTeam);
                            self.selectedCode(self.scheduleTeamModel().code());
                            blockUI.clear();
                            nts.uk.ui.dialog.info({messageId: "Msg_15"});
                            $('#scheduleTeamCd').focus();
                        });
                    }).fail((res) => {
                        blockUI.clear();
                        if(res.messageId == "Msg_3"){
                            self.selectedCode("");
                            $('#scheduleTeamCd').ntsError('set',{messageId: res.messageId});
                        }                        
                    });
                } else {
                    //update
                    let employeeIds = _.map(self.itemsRight(), item => {
                        return item.employeeId;
                    });
                    self.scheduleTeamModel().employeeIds(employeeIds);
                    service.update(ko.toJSON(self.scheduleTeamModel)).done(()=>{
                        self.listScheduleTeam(_.map(self.listScheduleTeam(), function(el: ScheduleTeam){
                            if(el.code == self.scheduleTeamModel().code()){
                                return new ScheduleTeam(self.scheduleTeamModel().code(), self.scheduleTeamModel().name());
                            }
                            return el;
                        }));
                                             
                        blockUI.clear();
                        nts.uk.ui.dialog.info({messageId: "Msg_15"});
                        $('#scheduleTeamName').focus();
                    });
                }
            }

            public remove(): void {
                let self = this;
                nts.uk.ui.dialog.confirm({messageId: "Msg_18"}).ifYes(function(){
                    let command: any = {};
                    command.code = self.scheduleTeamModel().code();
                    command.workplaceGroupId = self.workplaceGroupId();
                    blockUI.invisible();
                    service.remove(command).done(function(){
                        nts.uk.ui.dialog.info({messageId: "Msg_16"}).then(function(){
                            // _.each(self.itemsRight(), item =>{
                            //    item.teamCd = "";
                            // });

                            // _.each(self.itemsLeft(), item =>{
                            //     if(item.teamCd === self.selectedCode())
                            //         item.teamCd = "";
                            //  });

                            if(self.listScheduleTeam().length == 1){
                                self.listScheduleTeam([]);
                                self.selectedCode("");
                            } else {
                                let indexSelected: number;
                                for(let index:number = 0; index < self.listScheduleTeam().length; index++){
                                    if(self.listScheduleTeam()[index].code == self.selectedCode()){
                                        indexSelected = (index == self.listScheduleTeam().length - 1)? index -1: index;
                                        self.listScheduleTeam().splice(index,1);
                                        break;
                                    }
                                }                                
                                self.enableDelete(true);
                                self.selectedCode(self.listScheduleTeam()[indexSelected].code);
                            }
                        });

                    }).always(function(){
                        blockUI.clear();
                    });
                }).ifNo(function(){
                    blockUI.clear();
                    $('#scheduleTeamName').focus();
                });
            }

            public moveItemLeftToRight(): void {
                const vm = this;
                let empListLeft = ko.toJS(vm.itemsLeft);
                let empListRight = ko.toJS(vm.itemsRight);

                // get value and index from gridlist
                let idAs = $('#emp-list-left').ntsGridList("getSelected");
                let itemChosen = [];
                _.each(idAs, id => {
                    // get item by index from gridlist
                    itemChosen.push(empListLeft[id.index]);
                });

                var temp = _.difference(empListLeft, itemChosen);
                vm.itemsLeft(temp);
                vm.itemsRight(_.union(empListRight, itemChosen));
            }

            public moveItemRightToLeft(): void {
                const vm = this;
                let empListRight = ko.toJS(vm.itemsRight);
                let empListLeft = ko.toJS(vm.itemsLeft);

                // get value and index from gridlist
                let idBs = $('#emp-list-right').ntsGridList("getSelected");
                let itemChosen = [];
                _.each(idBs, id => {
                    itemChosen.push(empListRight[id.index]);
                });
                vm.itemsRight(_.difference(empListRight, itemChosen));
                vm.itemsLeft(_.sortBy(_.union(empListLeft, itemChosen), [function (o) { return o.employeeCd; }]));
            }

            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            public clearData(): void {
                let self = this;
                self.selectedCode("");
                self.enableDelete(false);
                self.isEditing(false);
                self.scheduleTeamModel().resetData(); 
                self.clearError();          
                $('#scheduleTeamCd').focus();              
            }

            private validateAll(): boolean {
                $('#scheduleTeamCd').ntsEditor('validate');
                $('#scheduleTeamName').ntsEditor('validate');
                $('#scheduleTeamRemarks').ntsEditor('validate');
                if (nts.uk.ui.errors.hasError()) {                    
                    return true;
                }
                return false;
            }

            private clearError(): void {
                $('#scheduleTeamCd').ntsError('clear');
                $('#scheduleTeamName').ntsError('clear');
                $('#scheduleTeamRemarks').ntsError('clear');
            }
        }

        class ScheduleTeamModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            note: KnockoutObservable<string>;
            workplaceGroupId: KnockoutObservable<string> = ko.observable("");
            employeeIds: KnockoutObservableArray<string> = ko.observableArray([]);
            isEnableCode: KnockoutObservable<boolean>;

            constructor(teamCode: string, teamName: string, teamRemarks: string, workplaceGroupId: string, employeeIds: string[]) {
                this.code = ko.observable(teamCode);
                this.name = ko.observable(teamName);
                this.note = ko.observable(teamRemarks);
                this.isEnableCode = ko.observable(false);
                this.workplaceGroupId = ko.observable(workplaceGroupId);  
                this.employeeIds = ko.observableArray(employeeIds);             
            }
            public resetData() {
                let self = this;
                self.code("");
                self.name("");
                self.note("");
                self.isEnableCode(true);                
            }
            public updateData(scheduleTeamDto: any) {
                let self = this;                
                self.code(scheduleTeamDto.code);
                self.name(scheduleTeamDto.name);
                self.note(scheduleTeamDto.note);
            }
        }
    }

    class WorkplaceGroup {
        workplaceGroupName: string;
        workplaceGroupId: string;
    }

    class ScheduleTeamDto {
        code: string;
        name: string;
        note: string;
        constructor(code: string, name: string, remarks: string) {
            this.code = code;
            this.name = name;
            this.note = remarks;
        }
    }
    class ScheduleTeam {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }



    /**
       * List Type
       */
    export class ListType {
        static EMPLOYMENT = 1;
        static CLASSIFICATION = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    /**
     * SelectType
     */
    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    class ItemModel {
        employeeId: string;
        employeeCd: string;
        businessName: string;
        teamCd: string;
        teamName: string;

        constructor(id: string, code: string, name: string, teamName: string, teamCd: string) {
            this.employeeId = id;
            this.employeeCd = code;
            this.businessName = name;
            this.teamName = teamName;
            this.teamCd = teamCd;
        }
    }    
}