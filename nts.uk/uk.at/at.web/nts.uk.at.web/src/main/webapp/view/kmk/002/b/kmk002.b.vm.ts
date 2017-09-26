module nts.uk.at.view.kmk002.b {
    export module viewmodel {

        import EmpConditionDto = service.model.EmpConditionDto;
        import ConditionDto = service.model.ConditionDto;

        export class ScreenModel {
            empCondition: EmpCondition;

            constructor() {
                this.empCondition = new EmpCondition();
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.find('001').done(res => {
                    //TODO testing.
                    self.empCondition.fromDto(res);
                    self.empCondition.initNtsGrid();

                    console.log(self.empCondition);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            public applyAll(): void {
                let self = this;
                self.empCondition.applyAll();
                self.empCondition.updateNtsGrid();
            }

            public notApplyAll(): void {
                let self = this;
                self.empCondition.notApplyAll();
                self.empCondition.updateNtsGrid();
            }

            public save(): void {
                let self = this;
                let dto = self.empCondition.toDto();
                console.log(dto);
            }

        }

        class EmpCondition {
            optionalItemNo: string;
            empConditions: Array<Condition>;

            switchds: Array<any>;

            constructor() {
                this.optionalItemNo = '';
                this.empConditions = new Array();
                this.switchds = [
                    { code: 1, name: nts.uk.resource.getText("KMK002_51") }, // apply
                    { code: 0, name: nts.uk.resource.getText("KMK002_52") } // not apply
                ];
            }

            public fromDto(dto: EmpConditionDto): void {
                let self = this;
                self.optionalItemNo = dto.optionalItemNo;
                self.empConditions = dto.empConditions.map(item => new Condition(item));
            }

            public toDto(): EmpConditionDto {
                let self = this;
                let dto = <EmpConditionDto>{};

                dto.optionalItemNo = self.optionalItemNo;
                dto.empConditions = self.empConditions.map(item => item.toDto());

                return dto;
            }

            public applyAll(): void {
                let self = this;
                console.log(self.empConditions);
                _.each(self.empConditions, item => item.apply() );
                console.log(self.empConditions);
            }

            public notApplyAll(): void {
                let self = this;
                console.log(self.empConditions);
                _.each(self.empConditions, item => item.notApply() );
                console.log(self.empConditions);
            }

            public updateNtsGrid(): void {
                let self = this;
                _.each(self.empConditions, item => {
                    $("#grid-emp-condition").ntsGrid("updateRow", item.empCd, item);
                });
            }

            public initNtsGrid(): void {
                let self = this;
                $("#grid-emp-condition").ntsGrid({
                    width: '300px',
                    height: '400px',
                    dataSource: this.empConditions,
                    primaryKey: 'empCd',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: '', key: 'empCd', dataType: 'string', width: '50px', hidden: true },
                        { headerText: nts.uk.resource.getText("KMK002_49"), key: 'empName', dataType: 'string', width: '100px' },
                        { headerText: nts.uk.resource.getText("KMK002_50"), key: 'empApplicableAtr', dataType: 'number', width: '150px', ntsControl: 'SwitchButtons' },
                    ],
                    features: [{ name: 'Resizing' }],
                    ntsFeatures: [{ name: 'CopyPaste' }],
                    ntsControls: [{ name: 'SwitchButtons', options: this.switchds,
                        optionsValue: 'code', optionsText: 'name', controlType: 'SwitchButtons', enable: true
                    }]
                });
            }

        }

        class Condition {
            empCd: string;
            empName: string;
            empApplicableAtr: number;

            constructor(dto: ConditionDto) {
                this.empCd = dto.empCd;
                this.empName = dto.empName;
                this.empApplicableAtr = 0;
            }

            public apply(): void {
                this.empApplicableAtr = 1;
            }

            public notApply(): void {
                this.empApplicableAtr = 0;
            }

            public toDto(): ConditionDto {
                let self = this;
                let dto = <ConditionDto>{};
                dto.empCd = self.empCd;
                dto.empName = self.empName;
                dto.empApplicableAtr = self.empApplicableAtr;
                return dto;
            }

        }
    }
}