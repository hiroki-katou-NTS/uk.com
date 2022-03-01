/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.f {
    @bean()
    export class ViewModel extends ko.ViewModel {
        dataSource: GridItem[];

        constructor(params: GridItem[]) {
            super();
            const vm = this;

            vm.dataSource = params;

            $("#grid2").ntsGrid({
                // width: '970px',
                height: 220,
                dataSource: vm.dataSource,
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    {
                        headerText: vm.$i18n('KDL016_14'),
                        key: "periodDisplay",
                        dataType: "string",
                        width: '180px',
                        template: '<div style="float:left">${periodDisplay} </div>'
                    },
                    {
                        headerText: vm.$i18n('KDL016_16'),
                        key: "employeeDisplay",
                        dataType: "string",
                        width: '220px',
                        template: '<div class="limited-label">${employeeDisplay} </div>'
                    },
                    {
                        headerText: vm.$i18n('KDL016_36'),
                        key: "errorMessage",
                        dataType: "string",
                        width: '270px',
                        template: '<div class="limited-label">${errorMessage} </div>'
                    }
                ],
            });
        };

        created(params: any) {
            const vm = this;

        }

        mounted() {
            const vm = this;
            $('#F5-btn').focus();
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }
    }

    class GridItem {
        id: number;
        periodDisplay: string;
        employeeDisplay: string;
        errorMessage: string;

        constructor(index: number, periodDisplay: string, employeeDisplay: string, errorMessage: string) {
            this.id = index;
            this.periodDisplay = periodDisplay;
            this.employeeDisplay = employeeDisplay;
            this.errorMessage = errorMessage;
        }
    }
}