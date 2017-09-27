module cps002.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;

    export class ViewModel {
        stepList = [
            { content: '.step-1' },
            { content: '.step-2' },
            { content: '.step-3' },
            { content: '.step-4' }
        ];
        date: KnockoutObservable<Date> = ko.observable(nts.uk.time.UTCDate(2000, 0, 1));
        simpleValue: KnockoutObservable<String> = ko.observable('pikamieo');
        itemList: KnockoutObservableArray<any> = ko.observableArray([
            new BoxModel(1, text('CPS002_26')),
            new BoxModel(2, text('CPS002_27')),
            new BoxModel(3, text('CPS002_28'))
        ]);
        selectedId: KnockoutObservable<number> = ko.observable(1);
        enable: KnockoutObservable<boolean> = ko.observable(true);
        selectedCode: KnockoutObservable<number> = ko.observable(1);
        columns = ko.observableArray([
            { headerText: text('CPS002_44'), key: 'id', width: 40, },
            { headerText: text('CPS002_45'), key: 'name', width: 130, },
        ]);
        currentCode = ko.observable(1);

        ccgcomponent: any = {
            baseDate: ko.observable(new Date()),
            //Show/hide options
            isQuickSearchTab: ko.observable(true),
            isAdvancedSearchTab: ko.observable(true),
            isAllReferableEmployee: ko.observable(true),
            isOnlyMe: ko.observable(true),
            isEmployeeOfWorkplace: ko.observable(true),
            isEmployeeWorkplaceFollow: ko.observable(true),
            isMutipleCheck: ko.observable(true),
            isSelectAllEmployee: ko.observable(true),
            onSearchAllClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onSearchOnlyClicked: (data: any) => {
                let self = this;
                console.log(data);
            },
            onSearchOfWorkplaceClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onSearchWorkplaceChildClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onApplyEmployee: (dataEmployee: Array<any>) => {
                let self = this;
            }
        };
        constructor() {
            let self = this;
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
        }

        next() {
            let self = this;

            $('#emp_reg_info_wizard').ntsWizard("next");
            if (self.selectedId() === 3) {
                $('#emp_reg_info_wizard').ntsWizard("goto", 2);
            }

        }

        prev() {
            let self = this;

            $('#emp_reg_info_wizard').ntsWizard("prev");


        }

        getStep() {
            if (nts.uk.ui._viewModel === undefined) {
                return 0;
            }
            return $('#emp_reg_info_wizard').ntsWizard("getCurrentStep");
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

}