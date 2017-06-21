module nts.uk.com.view.ccg.share.ccg {

    import ListType = kcp.share.list.ListType;
    import TreeType = kcp.share.tree.TreeType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;

    export class PersonModel {
        personId: string;

        personName: string;
    }

    export interface GroupOption {
        // クイック検索タブ
        isQuickSearchTab: boolean;
        // 参照可能な社員すべて
        isAllReferableEmployee: boolean;
        //自分だけ
        isOnlyMe: boolean;
        //おなじ部門の社員
        isEmployeeOfDepartment: boolean;
        //おなじ＋配下部門の社員
        isEmployeeDepartmentFollow: boolean;
        
        
        // 詳細検索タブ
        isAdvancedSearchTab: boolean;
        //複数選択 
        isMutipleCheck: boolean;

        onSearchAllClicked: (data: PersonModel[]) => void;

        onSearchOnlyClicked: (data: PersonModel) => void;
    }


    /**
    * Screen Model.
    */
    export class ListGroupScreenModel {
        isMultiple: boolean;
        tabs: KnockoutObservableArray<NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        selectedCode: KnockoutObservableArray<string>;
        baseDate: KnockoutObservable<Date>;
        employments: any;
        classifications: any;
        jobtitles: any;
        workplaces: any;
        employeeinfo: any;
        onSearchAllClicked: (data: PersonModel[]) => void;
        onSearchOnlyClicked: (data: PersonModel) => void;


        constructor() {
            var self = this;
            self.isMultiple = false;
            self.selectedCode = ko.observableArray([]);
            self.baseDate = ko.observable(new Date());
            self.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: nts.uk.resource.getText("CCG001_3"),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: nts.uk.resource.getText("CCG001_4"),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                }
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.employments = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYMENT,
                selectedCode: self.selectedCode,
                isDialog: true
            };

            self.classifications = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.Classification,
                selectedCode: self.selectedCode,
                isDialog: true
            }


            self.jobtitles = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.JOB_TITLE,
                selectedCode: this.selectedCode,
                isDialog: false,
                baseDate: self.baseDate,
            }

            self.workplaces = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                treeType: TreeType.WORK_PLACE,
                selectedCode: self.selectedCode,
                baseDate: self.baseDate,
                isDialog: false
            }



        }
        /**
         * Init component.
         */
        public init($input: JQuery, data: GroupOption): JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            var self = this;
            self.isMultiple = data.isMutipleCheck;
            self.onSearchAllClicked = data.onSearchAllClicked;
            self.onSearchOnlyClicked = data.onSearchOnlyClicked;
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/ccg/share/ccg.xhtml').serialize();
            $input.load(webserviceLocator, function() {
                ko.cleanNode($input[0]);
                ko.applyBindings(self, $input[0]);
                $('#employeeList').ntsListComponent(self.employments);
                $('#classificationList').ntsListComponent(self.classifications);
                $('#jobtitleList').ntsListComponent(self.jobtitles);
                $(".accordion").accordion({
                    active: false,
                    collapsible: true
                });
                dfd.resolve();
            });

            return dfd.promise();
        }

        searchAllEmployee(): void {
            var self = this;
            service.findAllPerson().done(data => {
                self.onSearchAllClicked(data);
            });
        }

        searchDataEmployee(): void {
            var self = this;
            service.findAllPerson().done(data => {
                self.employeeinfo = {
                    isShowAlreadySet: false,
                    isMultiSelect: self.isMultiple,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.toUnitModelList(data),
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                }  
                $('#employeeinfo').ntsListComponent(self.employeeinfo); 
            });
            
        }
        
        getEmployeeLogin(): void{
            var self = this;
            service.getPersonLogin().done(data => {
                self.onSearchOnlyClicked(data);
            });    
        }
        
        public toUnitModelList(dataList: PersonModel[]): KnockoutObservableArray<UnitModel> {
            var dataRes: UnitModel[] = [];

            for (var item: PersonModel of dataList) {
                dataRes.push({
                    code: item.personId,
                    name: item.personName
                });
            }
            return ko.observableArray(dataRes);
        }
        
        public toUnitModel(data: PersonModel): KnockoutObservable<UnitModel> {
            var dataRes: UnitModel ={code: data.personId, name: data.personName};
            return ko.observable(dataRes);
        }


    }


    /**
    * Service,
    */
    export module service {

        // Service paths.
        var servicePath = {
            findAllPerson: "basic/person/getallperson",
            getPersonLogin: "basic/person/getpersonlogin"
        }

        /**
         * Find person list
         */
        export function findAllPerson(): JQueryPromise<Array<PersonModel>> {
            return nts.uk.request.ajax('com', servicePath.findAllPerson);
        }
        
        // get person by login employee code
        export function getPersonLogin(): JQueryPromise<PersonModel> {
            return nts.uk.request.ajax('com', servicePath.getPersonLogin);
        } 

    }


    /**
     * Defined Jquery interface.
    */
    interface JQuery {

        /**
         * Nts list component.
         * This Function used after apply binding only.
         */
        ntsGroupComponent(option: nts.uk.com.view.ccg.share.ccg.GroupOption): JQueryPromise<void>;
    }

    (function($: any) {
        $.fn.ntsGroupComponent = function(option: nts.uk.com.view.ccg.share.ccg.GroupOption): JQueryPromise<void> {

            // Return.
            return new nts.uk.com.view.ccg.share.ccg.ListGroupScreenModel().init(this, option);
        }

    } (jQuery));
}