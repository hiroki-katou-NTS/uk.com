module nts.uk.at.view.kfp001.d {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            aggrFrameCode: KnockoutObservable<string>;
            optionalAggrName: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            peopleNo: KnockoutObservable<number>;
            mode: KnockoutObservable<boolean>;
            listEmp: KnockoutObservableArray<any>;
            executionId: KnockoutObservable<string>;
            listSelect: KnockoutObservableArray<any>;
            listSelectedEmpId : KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.aggrFrameCode = ko.observable('');
                self.optionalAggrName = ko.observable('');
                self.startDate = ko.observable('');
                self.endDate = ko.observable('');
                self.peopleNo = ko.observable(0);
                self.mode = ko.observable(false);
                self.listEmp = ko.observableArray([]);
                self.listSelect = ko.observableArray([]);
                self.executionId = ko.observable('');
                self.listSelectedEmpId = ko.observableArray([]); 
            }
            start() {
               

            }

            addData() {
                let self = this;
                let listEmployeeId = _.map(_.filter(self.listEmp(), (v) => _.includes(self.listSelect(), v.employeeCode)), (item) => {
                return item.employeeId;    
                });
                self.listSelectedEmpId(listEmployeeId);

                let listEmployee = [];
                _.forEach(self.listEmp(), function(item) {
                    listEmployee.push(item.employeeId);
                });

                let targetDto = {
                    executionEmpId: self.executionId(),
                    employeeId: self.listSelectedEmpId(),
                    state: 0
                }

                let executionDto = {
                    aggrId: 0,
                    aggrFrameCode: self.aggrFrameCode(),
                    executionAtr: 1,
                    executionStatus: 0,
                    presenceOfError: 1
                }
                let aggrPeriodDto = {
                    aggrFrameCode: self.aggrFrameCode(),
                    optionalAggrName: self.optionalAggrName(),
                    startDate: moment(self.startDate()).utc(),
                    endDate: moment(self.endDate()).utc(),
                }
                var addAggrPeriodCommand = {
                    mode: self.mode(),
                    aggrPeriodCommand: aggrPeriodDto,
                    targetCommand: targetDto,
                    executionCommand: executionDto

                }

                service.addOptionalAggrPeriod(addAggrPeriodCommand).done(function(data) {
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                })
            }

            opendScreenF() {
                nts.uk.request.jump("/view/kfp/001/b/index.xhtml");
            }

            prevC() {
                $("#wizard").ntsWizard("prev").done(function() {
                });
            }

        }

        export module model {
            export interface IOptionalAggrDto {
                mode?: boolean;
                optionalAggrPeriod?: IOptionalAggrPeriodDto;
                aggrPeriodExcution?: IAggrPeriodExcutionDto;
                aggrPeriodTarget?: IPeriodTargetDto;
            }
            export class OptionalAggrDto {
                mode: KnockoutObservable<boolean>;
                optionalAggrPeriod: KnockoutObservable<OptionalAggrPeriodDto>;
                aggrPeriodExcution: KnockoutObservable<AggrPeriodExcutionDto>;
                aggrPeriodTarget: KnockoutObservable<PeriodTargetDto>;
                constructor(param: IOptionalAggrDto) {
                    this.mode = ko.observable(param.mode || null);
                    this.optionalAggrPeriod = ko.observable(param.optionalAggrPeriod ? new OptionalAggrPeriodDto(param.optionalAggrPeriod) : new OptionalAggrPeriodDto({}));
                }
            }



            export interface IOptionalAggrPeriodDto {
                aggrFrameCode?: string;
                optionalAggrName?: string;
                startDate?: number;
                endDate?: number;

            }
            export class OptionalAggrPeriodDto {
                aggrFrameCode: KnockoutObservable<string>;
                optionalAggrName: KnockoutObservable<string>;
                startDate: KnockoutObservable<number>;
                endDate: KnockoutObservable<number>;
                constructor(param: IOptionalAggrPeriodDto) {
                    this.aggrFrameCode = ko.observable(param.aggrFrameCode || null);
                    this.optionalAggrName = ko.observable(param.optionalAggrName || null);
                    this.startDate = ko.observable(param.startDate || null);
                    this.endDate = ko.observable(param.endDate || null);
                }
            }

            export interface IAggrPeriodExcutionDto {
                aggrFrameCode?: string;
                startDateTime?: number;
                endDateTime?: number;
                executionAtr: number;
                executionStatus: number;
                presenceOfError: number;

            }
            export class AggrPeriodExcutionDto {
                aggrFrameCode: KnockoutObservable<string>;
                startDateTime: KnockoutObservable<number>;
                endDateTime: KnockoutObservable<number>;
                executionAtr: KnockoutObservable<number>;
                executionStatus: KnockoutObservable<number>;
                presenceOfError: KnockoutObservable<number>;
                constructor(param: IAggrPeriodExcutionDto) {
                    this.aggrFrameCode = ko.observable(param.aggrFrameCode || null);
                    this.startDateTime = ko.observable(param.startDateTime || null);
                    this.endDateTime = ko.observable(param.endDateTime || null);
                    this.executionAtr = ko.observable(param.executionAtr || null);
                    this.executionStatus = ko.observable(param.executionStatus || null);
                    this.presenceOfError = ko.observable(param.presenceOfError || null);
                }
            }

            export interface IPeriodTargetDto {
                aggrId?: string;
                employeeId?: string;
                state?: number;
            }
            export class PeriodTargetDto {
                aggrId: KnockoutObservable<string>;
                employeeId: KnockoutObservable<string>;
                state: KnockoutObservable<number>;

                constructor(param: IPeriodTargetDto) {
                    this.aggrId = ko.observable(param.aggrId || null);
                    this.employeeId = ko.observable(param.employeeId || null);
                    this.state = ko.observable(param.state || null);

                }
            }

            export enum ExecutionStatus {
                // 0:完了
                Done = 0,
                // 1:完了（エラーあり）
                DoneWitdError = 1,
                // 2:中断終了
                EndOfInterruption = 2,
                // 3:処理中 
                Processing = 3,
                // 4:中断開始
                StartOfInterruption = 4,
                // 5:実行中止
                StopExecution = 5
            }
        }

    }
}
