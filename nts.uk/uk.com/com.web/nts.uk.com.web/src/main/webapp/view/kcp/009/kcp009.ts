module kcp009.viewmodel {

    export class ScreenModel {
        empList: KnockoutObservableArray<EmployeeModel>;
        systemType: SystemType;
        isDisplayOrganizationName: boolean;
        targetBtnText: string;

        selectedItem: KnockoutObservable<string>;
        empDisplayCode: KnockoutObservable<string>;
        empBusinessName: KnockoutObservable<string>;
        selectedNumberOfPeople: KnockoutObservable<string>;
        selectedOrdinalNumber: KnockoutObservable<number>;
        organizationDesignation: KnockoutObservable<string>;
        organizationName: KnockoutObservable<string>;
        isActivePreviousBtn: KnockoutObservable<boolean>;
        isActiveNextBtn: KnockoutObservable<boolean>;
        isActivePersonalProfile: KnockoutObservable<boolean>;
        keySearch: KnockoutObservable<string>;
        isDisplay: KnockoutObservable<boolean>;

        
        constructor() {
            var self = this;
            self.empList = ko.observableArray([]);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.empDisplayCode = ko.observable(null);
            self.empBusinessName = ko.observable(null);
            self.organizationName = ko.observable(null);

            self.keySearch = ko.observable(null);
            self.isDisplay = ko.observable(true);
        }

        // Initialize Component
        public init($input: JQuery, data: ComponentOption): JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            $(document).undelegate('#list-box_grid', 'iggriddatarendered');
            ko.cleanNode($input[0]);
            var self = this;
            
            // System Reference Type
            self.systemType = data.systemReference;
            if (data.employeeInputList().length > 1) {
                data.employeeInputList().sort(function(left, right) {
                    return left.code == right.code ?
                        0 : (left.code < right.code ? -1 : 1)
                });
            }
            self.empList(data.employeeInputList());
            self.selectedItem = data.selectedItem;
            
            // Set SelectedItem: First Item
            self.selectedItem(data.employeeInputList().length > 0 ? data.employeeInputList()[0].id : null);
            
            // Initial Binding from Selected Item
            if (self.selectedItem()) {
                var currentItem = self.empList().filter((item) => {
                    return item.id == self.selectedItem();
                })[0];
                if (currentItem) {
                    self.empDisplayCode(currentItem.code);
                    self.empBusinessName(currentItem.businessName);
                    if (data.systemReference == SystemType.EMPLOYMENT) {
                        self.organizationName(currentItem.workplaceName);
                    } else {
                        self.organizationName(currentItem.depName);
                    }
                }
            }
            self.targetBtnText = data.targetBtnText;
            self.isDisplayOrganizationName = data.isDisplayOrganizationName;
            if (data.isDisplayOrganizationName) {
                if (data.systemReference == SystemType.EMPLOYMENT) {
                    // Set Organization Designation if System Reference is Employment
                    self.organizationDesignation = nts.uk.resource.getText("Com_Workplace");

                    // Set Organization name
                    if (data.employeeInputList().length > 0) {
                        self.organizationName(data.employeeInputList()[0].workplaceName);
                    } else {
                        self.organizationName(null);
                    }
                } else {
                    // Set Organization Designation if System Reference is others
                    self.organizationDesignation = nts.uk.resource.getText("Com_Department");

                    // Set Organization name
                    if (data.employeeInputList().length > 0) {
                        self.organizationName(data.employeeInputList()[0].depName);
                    } else {
                        self.organizationName(null);
                    }
                }
            } else {
                self.organizationDesignation = null;
                self.organizationName(null);
            }

            // SelectedItem Subscribe
            self.selectedItem.subscribe(function(value: string) {
                if (value) {
                    var currentItem = self.empList().filter((item) => {
                        return item.id == value;
                    })[0];
                    if (currentItem) {
                        self.empDisplayCode(currentItem.code);
                        self.empBusinessName(currentItem.businessName);
                        if (data.systemReference == SystemType.EMPLOYMENT) {
                            self.organizationName(currentItem.workplaceName);
                        } else {
                            self.organizationName(currentItem.depName);
                        }
                    }
                } else {
                    self.empDisplayCode("");
                    self.empBusinessName("");
                    self.organizationName("");
//                    self.selectedOrdinalNumber(0);
                }
            });
            // Selected OrdinalNumber
            self.selectedOrdinalNumber = ko.computed(function() {
                if ((self.empList().length > 0)) {
                    var currentItem = self.empList().filter((item) => {
                        return item.id == self.selectedItem();
                    })[0];
                    return self.empList().indexOf(currentItem) + 1;
                } else {
                    return 0;
                }
            });

            self.isActivePersonalProfile = ko.computed(function() {
                return data.employeeInputList.length > 0;
            }, self);
            self.isActivePreviousBtn = ko.computed(function() {
                return (self.empList().length > 0) && self.selectedOrdinalNumber() > 1;
            }, self);
            self.isActiveNextBtn = ko.computed(function() {
                return (self.empList().length > 0) && self.selectedOrdinalNumber() < self.empList().length;
            }, self);
            self.selectedNumberOfPeople = ko.computed(function() {
                return self.selectedOrdinalNumber().toString() + "/" + self.empList().length.toString();
            });
            // End of Initialize variables

            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/009/kcp009.xhtml').serialize();
            $input.load(webserviceLocator, function() {
                //$input.find('#list-box').empty();
                ko.applyBindings(self, $input[0]);

                // Add profile Icon
                var iconLink = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                    .mergeRelativePath('/view/kcp/share/icon/7.png').serialize();
                $('#profile-icon').attr('style', "background: url('" + iconLink + "'); width: 30px; height: 30px; background-size: 30px 30px;");

                // Icon for Previous Button
                var prevIconLink = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                    .mergeRelativePath('/view/kcp/share/icon/9.png').serialize();
                $('#prev-btn').attr('style', "background: url('" + prevIconLink + "'); width: 30px; height: 30px; background-size: 30px 30px;");

                // Icon for Next Button
                var nextIconLink = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                    .mergeRelativePath('/view/kcp/share/icon/10.png').serialize();
                $('#next-btn').attr('style', "background: url('" + nextIconLink + "'); width: 30px; height: 30px; background-size: 30px 30px;");

                // Toggle employee list
                $('#items-list').ntsPopup({
                    position: {
                        my: 'left top',
                        at: 'left bottom',
                        of: $('#function-tr')
                    }
                    dismissible: false
                });
                // Toggle
                $('#btn_show_list').click(function() {
                    $('#items-list').ntsPopup('toggle');
                });

                dfd.resolve();
            });
            return dfd.promise();
        }

        // showEmpList
        private showEmpList(): void {
            var self = this;
            $("#items-list").toggle();
        }

        private searchEmp(): void {
            let self = this;
            //TODO: Search...Temp position
            //            // Get All Employee
            //            var allEmp: Array<service.model.EmployeeSearchData> = [];
            //            service.findAllEmployee().done(function(data: Array<service.model.EmployeeSearchData>) {
            //                allEmp = data;
            //            });
            //            
            //            var existItem = allEmp.filter((item) => {
            //                // empCode
            //                return item.employeeCode == self.keySearch();
            //            })[0];
            //            
            //            if (existItem) {
            //                // Get Workplace...
            //                if (data.systemReference == SystemType.EMPLOYMENT) {
            //                    
            //                }
            //            } else {
            //                self.selectedItem(null);
            //            }
            // End Of Searching
        }

        // Previous Employee
        private previousEmp(): void {
            var self = this;
            var currentItem = self.empList().filter((item) => {
                return item.id == self.selectedItem();
            })[0];
            var nextId = self.empList()[self.empList().indexOf(currentItem) - 1].id;
            self.selectedItem(nextId);
        }

        // Method to Next Employee
        private nextEmp(): void {
            var self = this;
            var currentItem = self.empList().filter((item) => {
                return item.id == self.selectedItem();
            })[0];
            var prevId = self.empList()[self.empList().indexOf(currentItem) + 1].id;
            self.selectedItem(prevId);
        }
    }

    /**
     * Interface ComponentOption
     */
    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
    }

    /**
     * Class SystemType
     */
    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
    /**
     * Interface EmployeeModel
     */
    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }

    /**
     * Module Service
     */
    export module service {
        var paths: any = {
            // 
            findAllEmployee: 'basic/organization/employee/allemployee'
        }

        export function findAllEmployee(): JQueryPromise<Array<model.EmployeeSearchData>> {
            return nts.uk.request.ajax(paths.findAllEmployee, new Date());
        }

        /**
         * Module Model
         */
        export module model {
            export class EmployeeSearchData {
                employeeId: string;
                employeeCode: string;
                employeeName: string;
                workplaceCode: string;
                workplaceId: string;
                workplaceName: string;
            }
        }
    }
}
/**
 * Defined Jquery interface.
 */
interface JQuery {
    ntsLoadListComponent(option: kcp009.viewmodel.ComponentOption): JQueryPromise<void>;
}

(function($: any) {
    $.fn.ntsLoadListComponent = function(option: kcp009.viewmodel.ComponentOption): JQueryPromise<void> {

        // Return.
        return new kcp009.viewmodel.ScreenModel().init(this, option);
    }

} (jQuery));