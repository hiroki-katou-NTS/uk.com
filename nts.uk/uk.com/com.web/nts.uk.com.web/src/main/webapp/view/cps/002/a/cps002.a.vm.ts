module cps002.a.vm {
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;

    export class ViewModel {

        date: KnockoutObservable<Date> = ko.observable(new Date());

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

        currentEmployee: KnockoutObservable<Employee> = ko.observable(new Employee());

        currentCode = ko.observable(1);

        categoryList: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: text('Enum_CategoryType_SINGLEINFO') },
            { code: 2, name: text('Enum_CategoryType_MULTIINFO') },
            { code: 3, name: text('Enum_CategoryType_CONTINUOUSHISTORY') },
            { code: 4, name: text('Enum_CategoryType_NODUPLICATEHISTORY') },
            { code: 5, name: text('Enum_CategoryType_DUPLICATEHISTORY') },
            { code: 6, name: text('Enum_CategoryType_CONTINUOUSHISTORY') }
        ]);

        currentCategoryId = ko.observable(1);

        ccgcomponent: any = {
            baseDate: ko.observable(new Date()),
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

            self.selectedId.subscribe((newValue) => {
               
            });
        }

        next() {
            let self = this;

            if (self.selectedId() === 3) {
                $('#emp_reg_info_wizard').ntsWizard("goto", 2);
                return;
            }

            $('#emp_reg_info_wizard').ntsWizard("next");
        }

        prev() {
            let self = this;
            if (self.selectedId() === 3) {
                $('#emp_reg_info_wizard').ntsWizard("goto", 0);
                return;
            }
            $('#emp_reg_info_wizard').ntsWizard("prev");
        }

      

        finish() {

            let self = this;

            nts.uk.ui.windows.sub.modal('/view/cps/002/h/index.xhtml', { title: '' }).onClosed(function(): any {
            });
        }

        OpenEModal() {

            let self = this;

            nts.uk.ui.windows.sub.modal('/view/cps/002/e/index.xhtml', { title: '' }).onClosed(function(): any {
            });
        }

        OpenFModal() {

            let self = this;

            nts.uk.ui.windows.sub.modal('/view/cps/002/f/index.xhtml', { title: '' }).onClosed(function(): any {
            });
        }
        OpenGModal() {

            let self = this;

            nts.uk.ui.windows.sub.modal('/view/cps/002/g/index.xhtml', { title: '' }).onClosed(function(): any {
            });
        }

        genCategoryTypeText() {
            let self = this,
                currentCtgType = 0;

            switch (currentCtgType) {
                case 1: return text('Enum_CategoryType_SINGLEINFO');
                case 2: return text('Enum_CategoryType_MULTIINFO');
                case 3: return text('Enum_CategoryType_CONTINUOUSHISTORY');
                case 4: return text('Enum_CategoryType_NODUPLICATEHISTORY');
                case 5: return text('Enum_CategoryType_DUPLICATEHISTORY');
                case 6: return text('Enum_CategoryType_CONTINUOUSHISTORY');
                default: return '';

            }
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

    class Employee {

        employeeName: KnockoutObservable<string> = ko.observable("");
        employeeCode: KnockoutObservable<string> = ko.observable("");
        hireDate: KnockoutObservable<Date> = ko.observable(new Date());
        cardNo: KnockoutObservable<string> = ko.observable("");

        constructor(param?) {


        }

    }

}