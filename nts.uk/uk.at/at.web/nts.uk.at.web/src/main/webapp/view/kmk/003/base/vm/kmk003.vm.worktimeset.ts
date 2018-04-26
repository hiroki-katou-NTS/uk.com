module nts.uk.at.view.kmk003.a {
    import WorkTimeSettingDto = service.model.worktimeset.WorkTimeSettingDto;
    import WorkTimeDivisionDto = service.model.worktimeset.WorkTimeDivisionDto;
    import WorkTimeDisplayNameDto = service.model.worktimeset.WorkTimeDisplayNameDto;
    import WorkTimeDisplayModeDto = service.model.worktimeset.WorkTimeDisplayModeDto;
    import ManageEntryExitDto = service.model.worktimeset.ManageEntryExitDto;
    
    export module viewmodel {
        export module worktimeset {
            
            export class WorkTimeDivisionModel {
                workTimeDailyAtr: KnockoutObservable<number>;
                workTimeMethodSet: KnockoutObservable<number>;

                constructor() {
                    this.workTimeDailyAtr = ko.observable(0);
                    this.workTimeMethodSet = ko.observable(0);
                }

                updateData(data: WorkTimeDivisionDto) {
                    this.workTimeDailyAtr(data.workTimeDailyAtr);
                    this.workTimeMethodSet(data.workTimeMethodSet);
                }

                toDto(): WorkTimeDivisionDto {
                    var dataDTO: WorkTimeDivisionDto = {
                        workTimeDailyAtr: this.workTimeDailyAtr(),
                        workTimeMethodSet: this.workTimeMethodSet()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.workTimeDailyAtr(0);
                    this.workTimeMethodSet(0);    
                }
            }

            export class WorkTimeDisplayNameModel {
                workTimeName: KnockoutObservable<string>;
                workTimeAbName: KnockoutObservable<string>;
                workTimeSymbol: KnockoutObservable<string>;

                constructor() {
                    this.workTimeName = ko.observable('');
                    this.workTimeAbName = ko.observable('');
                    this.workTimeSymbol = ko.observable('');
                }

                updateData(data: WorkTimeDisplayNameDto) {
                    this.workTimeName(data.workTimeName);
                    this.workTimeAbName(data.workTimeAbName);
                    this.workTimeSymbol(data.workTimeSymbol);
                }

                toDto(): WorkTimeDisplayNameDto {
                    var dataDTO: WorkTimeDisplayNameDto = {
                        workTimeName: this.workTimeName(),
                        workTimeAbName: this.workTimeAbName(),
                        workTimeSymbol: this.workTimeSymbol()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.workTimeName('');
                    this.workTimeAbName('');
                    this.workTimeSymbol('');    
                }
            }
            
            export class WorkTimeDisplayModeModel {
                worktimeCode: KnockoutObservable<string>;
                displayMode: KnockoutObservable<number>;
                
                constructor() {
                    this.worktimeCode = ko.observable('');
                    this.displayMode = ko.observable(1);
                }

                updateData(data: WorkTimeDisplayModeDto) {
                    this.worktimeCode(data.worktimeCode);
                    this.displayMode(data.displayMode);
                }

                toDto(): WorkTimeDisplayModeDto {
                    var dataDTO: WorkTimeDisplayModeDto = {
                        worktimeCode: this.worktimeCode(),
                        displayMode: this.displayMode()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.worktimeCode('');
                    this.displayMode(1); 
                }
            }
            
            export class ManageEntryExitModel {
                useClassification: KnockoutObservable<number>;
                
                constructor() {
                    this.useClassification = ko.observable(0);
                }

                updateData(data: ManageEntryExitDto) {
                    this.useClassification(data.useClassification);
                }

                toDto(): ManageEntryExitDto {
                    var dataDTO: ManageEntryExitDto = {
                        useClassification: this.useClassification()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.useClassification(0); 
                }
            }
            
            export class WorkTimeSettingModel {
                worktimeCode: KnockoutObservable<string>;
                workTimeDivision: WorkTimeDivisionModel;
                isAbolish: KnockoutObservable<boolean>;
                colorCode: KnockoutObservable<string>;
                workTimeDisplayName: WorkTimeDisplayNameModel;
                memo: KnockoutObservable<string>;
                note: KnockoutObservable<string>;
                siftCodeOption: KnockoutObservable<any>;
                isUpdateMode: KnockoutObservable<boolean>;
                siftNameOption: KnockoutObservable<any>;
                siftShortNameOption: KnockoutObservable<any>;
                siftSymbolNameOption: KnockoutObservable<any>;
                siftRemarkOption: KnockoutObservable<any>;
                memoOption: KnockoutObservable<any>;

                // flag
                isFlex: KnockoutComputed<boolean>;
                isRegularWork: KnockoutComputed<boolean>;
                isFlow: KnockoutComputed<boolean>;
                isFixed: KnockoutComputed<boolean>;
                isDiffTime: KnockoutComputed<boolean>;

                constructor() {
                    var self = this;
                    self.worktimeCode = ko.observable('');
                    self.workTimeDivision = new WorkTimeDivisionModel();
                    self.isAbolish = ko.observable(false);
                    self.colorCode = ko.observable('');
                    self.workTimeDisplayName = new WorkTimeDisplayNameModel();
                    self.memo = ko.observable('');
                    self.note = ko.observable('');
                    self.siftCodeOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                        width: "30"
                    }));
                    self.siftNameOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                        width: "100"
                    }));
                    self.siftShortNameOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                        width: "60"
                    }));
                    self.siftSymbolNameOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                        width: "30"
                    }));
                    self.siftRemarkOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                        width: "300"
                    }));
                    self.memoOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                        width: "300"
                    }));
                    self.isUpdateMode = ko.observable(false);
                    self.initComputed();
                }

                initComputed(): void {
                    let self = this;
                    self.isFlex = ko.computed(() => {
                        return self.workTimeDivision.workTimeDailyAtr() == 1;
                    });
                    self.isRegularWork = ko.computed(() => {
                        return self.workTimeDivision.workTimeDailyAtr() == 0;
                    });
                    self.isFixed = ko.computed(() => {
                        return self.isRegularWork() && self.workTimeDivision.workTimeMethodSet() == 0;
                    });
                    self.isDiffTime = ko.computed(() => {
                        return self.isRegularWork() && self.workTimeDivision.workTimeMethodSet() == 1;
                    });
                    self.isFlow = ko.computed(() => {
                        return self.isRegularWork() && self.workTimeDivision.workTimeMethodSet() == 2;
                    });
                }

                updateData(data: WorkTimeSettingDto) {
                    this.worktimeCode(data.worktimeCode);
                    this.workTimeDivision.updateData(data.workTimeDivision);
                    this.isAbolish(data.isAbolish);
                    this.colorCode(data.colorCode);
                    this.workTimeDisplayName.updateData(data.workTimeDisplayName);
                    this.memo(data.memo);
                    this.note(data.note);
                }

                toDto(): WorkTimeSettingDto {
                    var dataDTO: WorkTimeSettingDto = {
                        worktimeCode: this.worktimeCode(),
                        workTimeDivision: this.workTimeDivision.toDto(),
                        isAbolish: this.isAbolish(),
                        colorCode: this.colorCode(),
                        workTimeDisplayName: this.workTimeDisplayName.toDto(),
                        memo: this.memo(),
                        note: this.note()
                    };
                    return dataDTO;
                }

                updateMode(isUpdateMode: boolean) {
                    this.isUpdateMode(isUpdateMode);
                }

                resetWorkTimeDivision(): void {
                    this.workTimeDivision.resetData();
                }
                
                resetData(){
                    this.worktimeCode('');
                    this.isAbolish(false);   
                    this.colorCode(''); 
                    this.workTimeDisplayName.resetData();
                    this.memo('');
                    this.note('');
                }
            }
        }
    }
}