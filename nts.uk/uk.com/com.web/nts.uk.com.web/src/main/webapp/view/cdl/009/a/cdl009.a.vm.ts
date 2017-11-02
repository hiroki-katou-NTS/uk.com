module nts.uk.com.view.cdl009.a {
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            multiSelectedTree: KnockoutObservableArray<string>;
            selectedWorkplace: KnockoutObservable<string>;
            isMultiSelect: KnockoutObservable<boolean>;
            selecType: KnockoutObservable<SelectType>;
            referenceDate: KnockoutObservable<Date>;
            target: KnockoutObservable<TargetClassification>;
            treeGrid: any;
            listComponentOpt: any;
            employeeList: KnockoutObservableArray<any>;
            
            isIncumbent: KnockoutObservable<boolean>;
            isLeaveOfAbsence: KnockoutObservable<boolean>;
            isHoliday: KnockoutObservable<boolean>;
            isRetirement: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                var params = getShared('CDL009Params');
                self.multiSelectedTree = ko.observableArray([]);
                self.selectedWorkplace = ko.observable('');
                //
                self.isMultiSelect = ko.observable(params.isMultiSelect);
                self.selecType = ko.observable(params.selecType ? params.selecType : SelectType.NO_SELECT);
                if (self.isMultiSelect()) {
                    self.multiSelectedTree(params.selectedCodes ? params.selectedCodes : []);
                }
                else {
                    self.selectedWorkplace(params.selectedCodes);
                }
                self.referenceDate = ko.observable(params.referenceDate ? params.referenceDate : new Date());
                self.target = ko.observable(params.target ? params.target : TargetClassification.WORKPLACE);

                self.employeeList = ko.observableArray<any>();
                
                // Initial listComponentOption
                self.treeGrid = {
                    isMultiSelect: true,
                    treeType: TreeType.WORK_PLACE,
                    selectType: self.selecType(),
                    baseDate: self.referenceDate,
                    selectedCode: null,
                    isShowSelectButton: true,
                    isDialog: true,
                    maxRows: 12,
                    tabindex: 1
                };
                self.listComponentOpt = {
                    isMultiSelect: self.isMultiSelect(),
                    listType: ListType.EMPLOYEE,
                    selectType: SelectType.NO_SELECT,
                    selectedCode: ko.observable(),
                    isDialog: true,
                    employeeInputList: self.employeeList,
                    maxRows: 12,
                    tabindex: 3,
                };
                if (self.isMultiSelect()) {
                    self.treeGrid.selectedCode = self.multiSelectedTree;
                }
                else {
                    self.treeGrid.selectedCode = self.selectedWorkplace;
                }
                
                self.isIncumbent = ko.observable(false);
                self.isLeaveOfAbsence = ko.observable(false);
                self.isHoliday = ko.observable(false);
                self.isRetirement = ko.observable(false);
            }

            /**
             * Search
             */
            private searchEmp(): void {
                let self = this;
                if (self.isMultiSelect() && self.multiSelectedTree().length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_643" }).then(() => nts.uk.ui.windows.close());
                    return;
                }

                if (!self.isMultiSelect() && !self.selectedWorkplace()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_643" }).then(() => nts.uk.ui.windows.close());
                    return;
                }


            }

            /**
             * Close dialog.
             */
            closeDialog(): void {
                setShared('CDL009Cancel', true);
                nts.uk.ui.windows.close();
            }

            /**
             * Decide Employment
             */
            decideData(): void {
                let self = this;
                if (self.isMultiSelect() && self.multiSelectedTree().length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_640" }).then(() => nts.uk.ui.windows.close());
                    return;
                }
                var isNoSelectRowSelected = $("#jobtitle").isNoSelectRowSelected();
                if (!self.isMultiSelect() && !self.selectedWorkplace() && !isNoSelectRowSelected) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_640" }).then(() => nts.uk.ui.windows.close());
                    return;
                }
                setShared('CDL009Output', self.isMultiSelect() ? self.multiSelectedTree() : self.selectedWorkplace());
                close();
            }

            /**
             * function check employment
             */
            public checkExistWorkplace(code: string, data: any[]): boolean {
                for (var item of data) {
                    if (code === item.code) {
                        return true;
                    }
                }
                return false;
            }
        }

        /**
        * Tree Type
        */
        export class TreeType {
            static WORK_PLACE = 1;
            static DEPARTMENT = 2;
        }
        /**
        * List Type
        */
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        /**
         * class SelectType
         */
        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        /**
     * Class TargetClassification
     */
        export class TargetClassification {
            static WORKPLACE = 1;
            static DEPARTMENT = 2;
        }

        export interface UnitModel {
            workplaceId: string;
            code: string;
            name: string;
            nodeText?: string;
            level: number;
            heirarchyCode: string;
            isAlreadySetting?: boolean;
            childs: Array<UnitModel>;
        }
    }
}