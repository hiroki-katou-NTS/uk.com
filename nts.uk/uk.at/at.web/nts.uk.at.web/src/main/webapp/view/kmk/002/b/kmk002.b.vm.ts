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
                    console.log(self.empCondition);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            public save(): void {

            }
        }

        class EmpCondition {
            optionalItemNo: string;
            empConditions: Array<Condition>;

            constructor() {
                this.optionalItemNo = '';
                this.empConditions = new Array();
            }

            public fromDto(dto: EmpConditionDto): void {
                let self = this;
                self.optionalItemNo = dto.optionalItemNo;
                self.empConditions = dto.empConditions.map(item => new Condition(item));
            }

            public toDto(): EmpConditionDto {
                return null;
            }

        }

        class Condition {
            empCd: string;
            empName: string;
            empApplicableAtr: KnockoutObservable<number>;

            switchds: KnockoutObservableArray<any>;

            constructor(dto: ConditionDto) {
                this.empCd = dto.empCd;
                this.empName = dto.empName;
                this.empApplicableAtr = ko.observable(dto.empApplicableAtr);
                this.switchds = ko.observableArray([
                    { code: '0', name: nts.uk.resource.getText("KMK002_15") }, // not used
                    { code: '1', name: nts.uk.resource.getText("KMK002_14") } // used
                ]);
            }

        }
    }
}