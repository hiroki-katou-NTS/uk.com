/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.f {
    @bean()
    export class ViewModel extends ko.ViewModel {
        dataSource: GridItem[];
        f1Text : KnockoutObservable<string> = ko.observable("");

        constructor(params: ResultExcecute) {
            super();
            const vm = this;

            vm.dataSource = params.gridItems;
            vm.f1Text = ko.observable(params.action == Action.REGISTER ? vm.$i18n('KDL016_34') : vm.$i18n('KDL016_47'));

            $("#grid2").ntsGrid({
                // width: '970px',
                height: "288px",
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
                        width: '265px',
                        template: '<div class="limited-label">${employeeDisplay} </div>'
                    },
                    {
                        headerText: vm.$i18n('KDL016_36'),
                        key: "errorMessage",
                        dataType: "string",
                        width: '320px',
                        template: '<div class="limited-label">${errorMessage} </div>'
                    }
                ],
                features: [
                    {
                        name: "Resizing"
                    }
                ]
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

    interface ResultExcecute {
        action: number;
        gridItems: GridItem[]
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

    enum Action {
        REGISTER = 1,
        UPDATE = 2,
        DELETE = 3
    }
}